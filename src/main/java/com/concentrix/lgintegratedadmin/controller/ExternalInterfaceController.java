package com.concentrix.lgintegratedadmin.controller;

import com.concentrix.lgintegratedadmin.controller.api.ExternalInterfaceApi;
import com.concentrix.lgintegratedadmin.util.ApiResponse;
import com.concentrix.lgintegratedadmin.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ExternalInterfaceController implements ExternalInterfaceApi {

    private final ApiUtil apiUtil;

    @Override
    public ApiResponse<Object> getExternalPaymentStatus(String transactionId) {

        // 외부 시스템 URL (예시)
        String targetUrl = "api.external-pg.com" + transactionId;

        try {
            // ApiUtil의 GET 메서드 활용
            Object result = apiUtil.get(targetUrl, Object.class);
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("외부 시스템 통신 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @Override
    public ApiResponse<Map<String, Object>> requestApprove(Map<String, Object> paymentData) {

        String targetUrl = "api.external-pg.com";

        try {
            // ApiUtil의 POST 메서드 활용 (동기 방식)
            @SuppressWarnings("unchecked")
            Map<String, Object> response = apiUtil.post(targetUrl, paymentData, Map.class);

            return ApiResponse.success(response);
        } catch (Exception e) {
            // 타임아웃 및 통신 에러 처리
            return ApiResponse.error("결제 승인 요청 실패: " + e.getMessage());
        }
    }

    @Override
    public Mono<ApiResponse<Map<String, Object>>> requestApproveAsync(Map<String, Object> paymentData) {
        String targetUrl = "api.external-pg.com"; // URL 프로토콜 명시 권장

        return apiUtil.postAsync(targetUrl, paymentData, Map.class)
            .map(res -> {
                log.info("비동기 결과값: {}", res);
                return ApiResponse.success((Map<String, Object>) res);
            })
            .onErrorResume(e -> {
                log.error("에러 발생: {}", e.getMessage());
                return Mono.just(ApiResponse.error("결제 승인 요청 실패: " + e.getMessage()));
            });
    }

    @Override
    public ApiResponse<Map<String, Object>> requestApproveAsync1(Map<String, Object> paymentData) {

        String targetUrl = "api.external-pg.com";

        try {
            // subscribe 대신 block()을 사용하여 응답이 올 때까지 기다림
            @SuppressWarnings("unchecked")
            Map<String, Object> response = (Map<String, Object>) apiUtil.postAsync(targetUrl, paymentData, Map.class)
                    .block(); // 비동기 함수를 호출하되, 여기서 결과가 나올 때까지 대기

            log.info("결과값 수신 완료: {}", response);
            return ApiResponse.success(response);
        } catch (Exception e) {
            log.error("통신 에러: {}", e.getMessage());
            return ApiResponse.error("결제 승인 요청 실패: " + e.getMessage());
        }
    }
}
