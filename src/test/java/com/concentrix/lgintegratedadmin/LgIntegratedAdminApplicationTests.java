package com.concentrix.lgintegratedadmin;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("Context loading fails due to complex external dependencies (Redis, Kafka, DB) in unit test environment")
@SpringBootTest
class LgIntegratedAdminApplicationTests {

    @Test
    void contextLoads() {
    }

}
