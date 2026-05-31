package com.converzai.cabportal;

import com.converzai.cabportal.service.CabManagementPortal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CabPortalApplication {
    public static void main(String[] args) {
        SpringApplication.run(CabPortalApplication.class, args);
    }

    @Bean
    public CabManagementPortal cabManagementPortal() {
        return new CabManagementPortal();
    }
}
