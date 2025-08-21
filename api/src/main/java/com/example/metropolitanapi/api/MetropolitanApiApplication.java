package com.example.metropolitanapi.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = "com.example.metropolitanapi")
@EntityScan(basePackages = "com.example.metropolitanapi")
@EnableJpaRepositories(basePackages = "com.example.metropolitanapi")

public class MetropolitanApiApplication {
    public static void main(String[] args) { SpringApplication.run(MetropolitanApiApplication.class, args); }
}