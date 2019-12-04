package com.ctb.syncdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import tk.mybatis.spring.annotation.MapperScan;

@EnableDiscoveryClient
@EnableFeignClients
@EnableTransactionManagement
@MapperScan(basePackages = "com.ctb.syncdata.rest.mapper")
@ComponentScan(basePackages = {"com.ctb"})
@SpringBootApplication
@EnableCircuitBreaker
public class SyncDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyncDataApplication.class, args);
    }

}
