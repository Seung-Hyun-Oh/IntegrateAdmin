package com.concentrix.lgintegratedadmin.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Kafka API", description = "Kafka 메시지 전송 테스트용 API")
@RequestMapping("/api/kafka")
public interface KafkaApi {

    @Operation(summary = "Kafka 메시지 전송", description = "지정된 토픽으로 문자열 메시지를 전송합니다.")
    @GetMapping("/send")
    String sendMessage(
            @RequestParam(defaultValue = "test-topic") String topic,
            @RequestParam String message);
}
