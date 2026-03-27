package com.concentrix.lgintegratedadmin.config;

import com.concentrix.lgintegratedadmin.domain.Product;
import com.concentrix.lgintegratedadmin.dto.ProductDto;
import com.concentrix.lgintegratedadmin.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ProductBatchConfig {

    private final ProductMapper productMapper;
    private final List<ProductDto> sharedProductList = new ArrayList<>();

    public void setProducts(List<ProductDto> products) {
        synchronized (sharedProductList) {
            sharedProductList.clear();
            sharedProductList.addAll(products);
        }
    }

    @Bean
    public Job productImportJob(JobRepository jobRepository, Step productImportStep) {
        return new JobBuilder("productImportJob", jobRepository)
                .start(productImportStep)
                .build();
    }

    @Bean
    public Step productImportStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("productImportStep", jobRepository)
                .<ProductDto, Product>chunk(100, transactionManager)
                .reader(productReader())
                .processor(productProcessor())
                .writer(productWriter())
                .build();
    }

    @Bean
    public ListItemReader<ProductDto> productReader() {
        synchronized (sharedProductList) {
            return new ListItemReader<>(new ArrayList<>(sharedProductList));
        }
    }

    @Bean
    public ItemProcessor<ProductDto, Product> productProcessor() {
        return dto -> Product.builder()
                .productCode(dto.getProductCode())
                .productName(dto.getProductName())
                .price(dto.getPrice())
                .stockQuantity(dto.getStockQuantity())
                .category(dto.getCategory())
                .build();
    }

    @Bean
    public ItemWriter<Product> productWriter() {
        return chunk -> {
            List<? extends Product> items = chunk.getItems();
            log.info("Writing chunk of {} products", items.size());
            productMapper.bulkInsertProducts((List<Product>) items);
        };
    }
}
