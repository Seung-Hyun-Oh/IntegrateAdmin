package com.concentrix.lgintegratedadmin.service;

import com.concentrix.lgintegratedadmin.dto.ProductDto;
import com.concentrix.lgintegratedadmin.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 상품 목록을 Kafka 토픽으로 전송합니다.
     * @param topic 전송할 토픽명
     * @param productList 상품 목록
     */
    public void sendProductList(String topic, List<ProductDto> productList) {
        log.info("Sending {} products to topic: {}", productList.size(), topic);
        for (ProductDto product : productList) {
            try {
                String message = JsonUtil.toJson(product);
                kafkaTemplate.send(topic, product.getProductCode(), message);
            } catch (Exception e) {
                log.error("Failed to serialize or send product: {}", product, e);
            }
        }
    }
}
