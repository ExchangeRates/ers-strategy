package com.wcreators.ersstrategy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ErsStrategyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErsStrategyApplication.class, args);
    }

}
