package com.concentrix.lgintegratedadmin.controller;

import com.concentrix.lgintegratedadmin.controller.api.FcmApi;
import com.concentrix.lgintegratedadmin.dto.FcmRequestDto;
import com.concentrix.lgintegratedadmin.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FcmController implements FcmApi {

    private final FcmService fcmService;

    @Override
    public ResponseEntity<String> pushMessage(FcmRequestDto requestDto) {
        try {
            String response = fcmService.sendNotification(requestDto);
            return ResponseEntity.ok("성공적으로 전송되었습니다: " + response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("전송 실패: " + e.getMessage());
        }
    }
}
