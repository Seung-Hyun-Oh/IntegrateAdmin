package com.concentrix.lgintegratedadmin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Kafka 메시지 발송을 담당하는 서비스 클래스입니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * 특정 토픽으로 메시지를 발송합니다.
     *
     * @param topic   대상 토픽
     * @param message 발송할 메시지
     */
    public void sendMessage(String topic, String message) {
        log.info("Sending message to Kafka topic: {} -> {}", topic, message);
        kafkaTemplate.send(topic, message)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Successfully sent message to topic: {} with offset: {}", 
                                topic, result.getRecordMetadata().offset());
                    } else {
                        log.error("Failed to send message to topic: {}", topic, ex);
                    }
                });
    }
}
