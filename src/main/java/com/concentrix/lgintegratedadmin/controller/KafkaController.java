package com.concentrix.lgintegratedadmin.controller;

import com.concentrix.lgintegratedadmin.service.KafkaProducerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kafka 연동 테스트를 위한 컨트롤러입니다.
 */
@Tag(name = "Kafka API", description = "Kafka 메시지 전송 테스트용 API")
@RestController
@RequestMapping("/api/kafka")
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaProducerService producerService;

    /**
     * 특정 토픽으로 메시지를 전송합니다.
     *
     * @param topic   대상 토픽
     * @param message 전송할 메시지
     * @return 결과 메시지
     */
    @Operation(summary = "Kafka 메시지 전송", description = "지정된 토픽으로 문자열 메시지를 전송합니다.")
    @GetMapping("/send")
    public String sendMessage(
            @RequestParam(defaultValue = "test-topic") String topic,
            @RequestParam String message) {
        producerService.sendMessage(topic, message);
        return "Message sent to topic '" + topic + "': " + message;
    }
}
