/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年6月19日
 * Created by hhy
 */
package com.ctb.review.prescription;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import tk.mybatis.spring.annotation.MapperScan;

/**
 * @ClassName: com.ctb.review.prescription.ReviewPrescriptionApplication
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年6月19日 上午9:20:36
 */
@EnableDiscoveryClient
@EnableFeignClients
@EnableTransactionManagement 
@MapperScan(basePackages = "com.ctb.resources.mapper.*")
@ComponentScan(basePackages = {"com.ctb"})
@SpringBootApplication
@EnableCircuitBreaker
public class ReviewPrescriptionApplication{
    public static void main(String[] args) {
        SpringApplication.run(ReviewPrescriptionApplication.class, args);
    }
}
