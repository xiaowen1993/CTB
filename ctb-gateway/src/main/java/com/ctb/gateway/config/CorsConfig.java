/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月23日
 * Created by cwq
 */
package com.ctb.gateway.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @ClassName: com.ctb.gateway.config.CorsConfig
 * @Description: TODO(springboot设置cors跨域请求的配置)
 * @author cwq
 * @date 2019年3月23日 上午11:18:00
 */

@Configuration
public class CorsConfig {
    @Bean
    public FilterRegistrationBean corsFilter() {
        
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 允许cookies跨域
        config.addAllowedOrigin("*");// #允许向该服务器提交请求的URI，*表示全部允许
        config.addAllowedHeader("*");// #允许访问的头信息,*表示全部
        config.setMaxAge(18000L);// 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.addAllowedMethod("*");// 允许提交请求的方法，*表示全部允许，也可以单独设置GET、PUT等
        /*
         * config.addAllowedMethod("HEAD"); config.addAllowedMethod("GET");// 允许Get的请求方法 config.addAllowedMethod("PUT");
         * config.addAllowedMethod("POST"); config.addAllowedMethod("DELETE"); config.addAllowedMethod("PATCH");
         */
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        
        bean.setOrder(0);
        return bean;
        
    }
}
