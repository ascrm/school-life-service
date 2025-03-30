package com.school;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SchoolLifeServiceApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SchoolLifeServiceApiApplication.class, args);
        log.info("😄😄😄school-life-service-api启动成功😄😄😄");
    }
}
