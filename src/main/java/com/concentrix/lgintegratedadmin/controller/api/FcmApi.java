package com.concentrix.lgintegratedadmin.controller.api;

import com.concentrix.lgintegratedadmin.dto.FcmRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "FCM API", description = "푸시 알림 전송 관련 API")
@RequestMapping("/api/v1/fcm")
public interface FcmApi {

    @Operation(summary = "단일 기기 푸시 전송", description = "특정 기기의 FCM 토큰을 사용하여 알림을 전송합니다.")
    @PostMapping("/send")
    ResponseEntity<String> pushMessage(@RequestBody FcmRequestDto requestDto);
}
