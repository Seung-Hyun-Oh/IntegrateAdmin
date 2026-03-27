package com.concentrix.lgintegratedadmin.service;

import com.concentrix.lgintegratedadmin.config.ProductBatchConfig;
import com.concentrix.lgintegratedadmin.dto.ProductDto;
import com.concentrix.lgintegratedadmin.util.JsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Kafka 메시지 수신을 담당하는 서비스 클래스입니다.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final JobLauncher jobLauncher;
    private final Job productImportJob;
    private final ProductBatchConfig productBatchConfig;
    
    // 수신된 데이터를 임시로 보관할 큐 (실제 운영 환경에선 Redis나 DB 임시 테이블 권장)
    private final ConcurrentLinkedQueue<ProductDto> productQueue = new ConcurrentLinkedQueue<>();

    /**
     * 특정 토픽에서 메시지를 수신합니다.
     *
     * @param message 수신된 메시지
     */
    @KafkaListener(topics = "product-topic", groupId = "integrated-admin-group")
    public void consumeProduct(String message) {
        log.info("Received product message from Kafka: {}", message);
        try {
            ProductDto productDto = JsonUtil.fromJson(message, ProductDto.class);
            productQueue.offer(productDto);
            
            // 특정 개수 이상 쌓이면 배치 실행 (예: 100개)
            if (productQueue.size() >= 100) {
                runBatchJob();
            }
        } catch (Exception e) {
            log.error("Failed to process Kafka message: {}", message, e);
        }
    }

    /**
     * 배치 작업을 실행합니다.
     */
    public synchronized void runBatchJob() {
        if (productQueue.isEmpty()) return;

        log.info("Starting Batch Job for {} products", productQueue.size());
        
        List<ProductDto> itemsToProcess = new ArrayList<>();
        ProductDto item;
        while ((item = productQueue.poll()) != null) {
            itemsToProcess.add(item);
        }

        try {
            // 배치 설정에 데이터 전달
            productBatchConfig.setProducts(itemsToProcess);

            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .addInt("itemCount", itemsToProcess.size())
                    .toJobParameters();
            
            jobLauncher.run(productImportJob, jobParameters);
            log.info("Batch Job submitted successfully");
        } catch (Exception e) {
            log.error("Failed to run Batch Job", e);
            // 실패 시 큐에 다시 넣거나 에러 로그 남김
            productQueue.addAll(itemsToProcess);
        }
    }

    @KafkaListener(topics = "test-topic", groupId = "integrated-admin-group")
    public void consume(String message) {
        log.info("Received message from Kafka topic 'test-topic': {}", message);
    }
}
