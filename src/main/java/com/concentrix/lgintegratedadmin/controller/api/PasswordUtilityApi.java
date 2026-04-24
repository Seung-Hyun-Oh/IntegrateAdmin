package com.concentrix.lgintegratedadmin.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Password Utility", description = "비밀번호 암호화 생성 및 검증 도구")
@RequestMapping("/api/utils")
public interface PasswordUtilityApi {

    @Operation(summary = "DB 저장용 비밀번호 생성", description = "평문을 입력하면 BCrypt로 암호화된 해시를 반환합니다.")
    @GetMapping("/encode")
    String encode(
            @Parameter(description = "암호화할 평문 비밀번호") @RequestParam String password);

    @Operation(summary = "비밀번호 일치 여부 검증", description = "평문과 DB 해시값이 일치하는지 테스트합니다.")
    @PostMapping("/match")
    boolean match(
            @Parameter(description = "평문 비밀번호") @RequestParam String raw,
            @Parameter(description = "DB에 저장된 해시값") @RequestParam String encoded);
}
