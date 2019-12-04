/**
 * <html>
 *   <body>
 *     <p>project name AI药方信息共享平台</p>
 *     <p>Copyright(C)版权所有 -  广东城市宠儿新商务有限公司.</p>
 *     <p>All rights reserved.</p>
 *     <p>Created on 2019年3月29日</p>
 *     <p>Created by cwq</p>
 *     <p>@version V1.1</p>
 *   </body>
 * </html>
 */
package com.ctb.platform;

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
@MapperScan(basePackages = "com.ctb.resources.mapper.*")
@ComponentScan(basePackages = {"com.ctb"})
@SpringBootApplication
@EnableCircuitBreaker
public class PlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformApplication.class, args);
    }
}