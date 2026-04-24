package com.concentrix.lgintegratedadmin.controller;

import com.concentrix.lgintegratedadmin.controller.api.KafkaApi;
import com.concentrix.lgintegratedadmin.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * Kafka 연동 테스트를 위한 컨트롤러입니다.
 */
@RestController
@RequiredArgsConstructor
public class KafkaController implements KafkaApi {

    private final KafkaProducerService producerService;

    /**
     * 특정 토픽으로 메시지를 전송합니다.
     *
     * @param topic   대상 토픽
     * @param message 전송할 메시지
     * @return 결과 메시지
     */
    @Override
    public String sendMessage(String topic, String message) {
        producerService.sendMessage(topic, message);
        return "Message sent to topic '" + topic + "': " + message;
    }
}
