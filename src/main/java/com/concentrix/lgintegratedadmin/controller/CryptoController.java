package com.concentrix.lgintegratedadmin.controller;

import com.concentrix.lgintegratedadmin.controller.api.CryptoApi;
import com.concentrix.lgintegratedadmin.service.EncryptionService;
import org.springframework.web.bind.annotation.RestController;

/**
 * [운영 주의] Jasypt 프로퍼티 암호화 도구
 * 이 컨트롤러는 보안상 로컬 IP(localhost)에서만 호출 가능하도록 SecurityConfig에서 제한됨.
 */
@RestController
public class CryptoController implements CryptoApi {

    private final EncryptionService encryptionService;

    // 생성자 주입
    public CryptoController(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Override
    public String encrypt(String text) {
        String encrypted = encryptionService.encrypt(text);
        // application.yml에 바로 복사해서 사용할 수 있도록 ENC() 포맷으로 반환
        return "ENC(" + encrypted + ")";
    }

    @Override
    public String decrypt(String encryptedText) {
        return encryptionService.decrypt(encryptedText);
    }
}
