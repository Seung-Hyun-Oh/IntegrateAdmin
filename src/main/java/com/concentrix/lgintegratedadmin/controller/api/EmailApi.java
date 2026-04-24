package com.concentrix.lgintegratedadmin.controller.api;

import com.concentrix.lgintegratedadmin.dto.EmailRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Email API", description = "메일 발송 관련 API")
@RequestMapping("/api/mail")
public interface EmailApi {

    @Operation(
            summary = "단순 텍스트 이메일 발송",
            description = "수신자에게 제목과 본문을 포함한 메일을 발송합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발송 성공"),
            @ApiResponse(responseCode = "500", description = "메일 서버 오류 또는 설정 미비")
    })
    @PostMapping("/send")
    String sendEmail(@RequestBody EmailRequestDto requestDto);
}
