package com.travel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.travel")
@Slf4j
public class CnTravelApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(CnTravelApiApplication.class, args);
        log.info("😄😄😄cn-travel-api启动成功😄😄😄");
    }
}
