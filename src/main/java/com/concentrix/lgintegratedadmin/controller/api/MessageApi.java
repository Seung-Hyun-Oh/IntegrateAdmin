package com.concentrix.lgintegratedadmin.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Message API", description = "다국어 메시지 테스트를 위한 API")
@RequestMapping("/api")
public interface MessageApi {

    @Operation(summary = "환영 메시지 조회", description = "헤더의 Accept-Language 값에 따라 다국어 환영 인사를 반환합니다.")
    @GetMapping(value = "/welcome")
    String getWelcome(@Parameter(hidden = true) java.util.Locale locale);

    @Operation(summary = "사용자별 인사말", description = "이름을 파라미터로 받아 다국어 인사말을 반환합니다.")
    @GetMapping("/userGreet/{name}")
    String getUser(
            @Parameter(description = "사용자 이름", example = "Alice")
            @PathVariable String name);
}
