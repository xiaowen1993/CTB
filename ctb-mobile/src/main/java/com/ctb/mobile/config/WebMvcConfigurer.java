package com.ctb.mobile.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ctb.framework.commons.interceptor.ReqInfoLogInterceptor;
import com.ctb.mobile.interceptor.OpenIdInterceptor;

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new ReqInfoLogInterceptor()).addPathPatterns("/**");
//        registry.addInterceptor(new OpenIdInterceptor()).addPathPatterns("/**");

        super.addInterceptors(registry);
    }
}
