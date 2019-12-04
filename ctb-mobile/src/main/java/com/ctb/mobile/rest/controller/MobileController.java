package com.ctb.mobile.rest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ctb.commons.entity.Hospital;
import com.ctb.framework.commons.config.SystemConfig;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.redis.RedisClient;
import com.ctb.mobile.api.YXWOutClient;
import com.ctb.mobile.rest.service.HospitalService;
import com.ctb.mobile.rest.service.UserService;
import com.ctb.mobile.sdk.WechatSDK;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

@RefreshScope
@RestController
public class MobileController {
    
    private static Logger logger = LoggerFactory.getLogger(MobileController.class);
    
    @Autowired
    EurekaDiscoveryClient discoveryClient;
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    private RedisClient redisClient;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    public HospitalService hospitalService;
    
    @Autowired
    private YXWOutClient yxwOutClient;
    
    @Value("${msg:unknown}")
    private String msg;
    
    @GetMapping(value = "/")
    public String printServiceB() {
        ServiceInstance serviceInstance = discoveryClient.getLocalServiceInstance();
        return serviceInstance.getServiceId() + " (" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + ")"
                + "===>Say " + msg;
    }
    
    @GetMapping(value = "test")
    @ResponseBody
    public String test() {
        
        return "aaaaaa";
    }
    
    @RequestMapping("/redisTest")
    public String redisTest() {
        try {
            
            redisTemplate.opsForValue().set("test-key", "redis测试内容", 2, TimeUnit.SECONDS);// 缓存有效期2秒
            
            logger.info("从Redis中读取数据：" + redisTemplate.opsForValue().get("test-key").toString());
            
            TimeUnit.SECONDS.sleep(3);
            
            logger.info("等待3秒后尝试读取过期的数据：" + redisTemplate.opsForValue().get("test-key"));
            
            redisClient.set("test-key", "xxxxxxxxxxx", 2);
            logger.info("从Redis中读取数据：" + redisClient.get("test-key"));
            TimeUnit.SECONDS.sleep(3);
            logger.info("等待3秒后尝试读取过期的数据：" + redisClient.get("test-key"));
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        return "OK";
    }
    
    @ResponseBody
    @RequestMapping("/queryUserById")
    public RespBody queryUserById(String userId) {
        
        return new RespBody(Status.OK, userService.queryUserById(userId));
    }
    
    @ResponseBody
    @RequestMapping("/queryHospitals")
    public RespBody queryHospitals() {
        
        return new RespBody(Status.OK, hospitalService.getHospitals());
    }
    
    @ResponseBody
    @RequestMapping("/testYXW")
    public RespBody testYXW() {
        
        RespBody rsp = yxwOutClient.loadMZListData(new String[] { "gzhszhyy" }, "123456", "", "");
        
        return rsp;
    }
    
}