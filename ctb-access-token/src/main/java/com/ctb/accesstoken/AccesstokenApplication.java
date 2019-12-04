package com.ctb.accesstoken;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.ctb"})
@SpringBootApplication
@EnableCircuitBreaker
public class AccesstokenApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccesstokenApplication.class, args);
    }
}