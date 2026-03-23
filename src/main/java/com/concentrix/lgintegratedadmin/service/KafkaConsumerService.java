package com.concentrix.lgintegratedadmin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Kafka 메시지 수신을 담당하는 서비스 클래스입니다.
 */
@Slf4j
@Service
public class KafkaConsumerService {

    /**
     * 특정 토픽에서 메시지를 수신합니다.
     * 테스트용으로 'test-topic'을 구독합니다.
     *
     * @param message 수신된 메시지
     */
    @KafkaListener(topics = "test-topic", groupId = "integrated-admin-group")
    public void consume(String message) {
        log.info("Received message from Kafka topic 'test-topic': {}", message);
    }
}
