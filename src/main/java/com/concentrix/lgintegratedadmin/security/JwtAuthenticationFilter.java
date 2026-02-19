package com.concentrix.lgintegratedadmin.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * JWT 인증 필터
 * OncePerRequestFilter를 상속받아 모든 요청당 단 한 번만 실행됨을 보장합니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. 요청 헤더(Authorization)에서 "Bearer {token}" 추출
        String token = jwtTokenProvider.resolveToken(request);

        // 2. 토큰이 존재하고 유효성 검사(만료일, 서명 등)를 통과하면 인증 처리
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰 내부 정보를 기반으로 Authentication(인증) 객체 생성
            Authentication authentication = jwtTokenProvider.getAuthentication(token);

            // 현재 요청 스레드의 SecurityContext에 인증 정보 저장 (이후 Controller 등에서 사용 가능)
            SecurityContextHolder.getContext().setAuthentication(authentication);

            log.debug("JWT 토큰 인증 성공: {}", authentication.getName());
        }
        // 3. 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    /**
     * 필터링을 거치지 않을 경로 설정
     * 2026년 표준: 로그인이 필요한 서비스 외의 공개 API는 필터 실행 자체를 방지하여 성능 향상
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        // 로그인, 회원가입, Swagger UI 등은 JWT 검증 제외
        boolean bool = path.startsWith("/login") || path.startsWith("/signup") || path.startsWith("/swagger-ui");

        bool = bool || path.startsWith("/v3/api-docs");
        bool = bool || path.startsWith("/saml2");
        bool = bool || path.startsWith("/crypto");

        return bool;
    }
}
