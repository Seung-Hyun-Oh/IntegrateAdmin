package com.concentrix.lgintegratedadmin.controller;

import com.concentrix.lgintegratedadmin.controller.api.EmailApi;
import com.concentrix.lgintegratedadmin.dto.EmailRequestDto;
import com.concentrix.lgintegratedadmin.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor // Lombok으로 의존성 주입 ( 이게 최신 트렌트야, 왜냐하면 소스 간결화, 하지만 메서드 만들어서 주입해도 무관해 )
public class EmailController implements EmailApi {

    private final EmailService emailService;

    @Override
    public String sendEmail(EmailRequestDto requestDto) {
        emailService.sendSimpleEmail(requestDto.getTo(), requestDto.getSubject(), requestDto.getText());
        return "Email sent successfully";
    }
}

