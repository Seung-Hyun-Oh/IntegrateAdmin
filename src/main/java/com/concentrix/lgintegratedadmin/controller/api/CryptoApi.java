package com.concentrix.lgintegratedadmin.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Jasypt 암호화 API", description = "설정값(yml) 암복호화 테스트를 위한 관리자 도구")
@RequestMapping("/api/crypto")
public interface CryptoApi {

    @Operation(summary = "문자열 암호화", description = "평문을 Jasypt 키로 암호화하여 ENC(...) 형식으로 반환합니다.")
    @GetMapping("/encrypt")
    String encrypt(
            @Parameter(description = "암호화할 평문(DB 패스워드 등)", example = "myPassword123")
            @RequestParam String text);

    @Operation(summary = "문자열 복호화", description = "암호화된 문자열을 평문으로 복호화하여 확인합니다.")
    @GetMapping("/decrypt")
    String decrypt(
            @Parameter(description = "복호화할 암호문 (ENC 접두사 제외)", example = "vK7X9b/4S9f...")
            @RequestParam String encryptedText);
}
