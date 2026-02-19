package com.concentrix.lgintegratedadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class LgIntegratedAdminApplication {

//    static {
//        System.setProperty("javax.xml.accessExternalDTD", "all");
//        System.setProperty("javax.xml.accessExternalSchema", "all");
//    }

    public static void main(String[] args) {
        SpringApplication.run(LgIntegratedAdminApplication.class, args);
    }

}
