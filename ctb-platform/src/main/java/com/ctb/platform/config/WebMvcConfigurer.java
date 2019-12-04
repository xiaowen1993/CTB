package com.ctb.platform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ctb.framework.commons.interceptor.ReqInfoLogInterceptor;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new ReqInfoLogInterceptor()).addPathPatterns("/**");

        super.addInterceptors(registry);
    }
}
