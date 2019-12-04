/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月12日
 * Created by cwq
 */
package com.ctb.mobile.rest.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.BranchPharmacy;
import com.ctb.commons.entity.DrugList;
import com.ctb.commons.entity.PaySettings;
import com.ctb.commons.entity.Pharmacy;
import com.ctb.commons.entity.PharmacyPaySettings;
import com.ctb.framework.commons.config.SystemConfig;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.redis.RedisClient;
import com.ctb.framework.commons.utils.JO;
import com.ctb.mobile.api.AccesstokenClient;
import com.ctb.mobile.sdk.WechatSDK;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

/**
 * @ClassName: com.ctb.mobile.rest.controller.OpenIdController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月12日 下午5:42:05
 */

@RefreshScope
@RestController
@RequestMapping("/wechat")
public class WechatController {
    
    @Autowired
    private AccesstokenClient accesstokenClient;
    
    @Autowired
    private RedisClient redisClient;
    
    @ResponseBody
    @RequestMapping("/getOpenId")
    public RespBody getOpenId(@RequestParam(required = true, value = "code") String code) {
   
    	    
        String appId = SystemConfig.getStringValue("APP_ID"); // 处方平台开发者密码
        String appSecret = SystemConfig.getStringValue("APP_SECRET"); // 处方平台开发者密码。
        String openId = WechatSDK.getOpenId(appId, appSecret, code);
        if (StringUtils.isBlank(openId)) {
            return new RespBody(Status.PROMPT, "request openid is null");
        } else {
            
            return new RespBody(Status.OK, new HashMap<String, String>() {
                {
                    put("openId", openId);
                }
            });
        }
    }
    
    @ResponseBody
    @RequestMapping("/getPharmacyOpenId")
    public RespBody getPharmacyOpenId(@RequestParam(required = true, value = "code") String code,
            @RequestParam(required = true, value = "pharmacyCode") String pharmacyCode) {
        
        Set<Object> fields = redisClient.hKeys(CacheConstants.CACHE_PHARMACY_PAYSETTINGS_HASH_KEY_PREFIX);
        
        Collection<Object> result = Collections2.filter(fields, new Predicate<Object>() {
            @Override
            public boolean apply(Object obj) {
                
                return ((String) obj).contains(pharmacyCode);
            }
        });
        
        if (!CollectionUtils.isEmpty(result)) {
            Object object = redisClient.hget(CacheConstants.CACHE_PHARMACY_PAYSETTINGS_HASH_KEY_PREFIX,
                    result.toArray()[0].toString());
            System.out.println(JSONObject.toJSONString(object));
            PaySettings paySettings = JSONObject.parseObject(JSONObject.toJSONString(object), PaySettings.class);
            
            String appId = paySettings.getAppId(); // 药店开发者密码
            String appSecret = paySettings.getAppSecret(); // 药店开发者密码
            
            String openId = WechatSDK.getOpenId(appId, appSecret, code);
            if (StringUtils.isBlank(openId)) {
                return new RespBody(Status.PROMPT, "request openid is null");
            } else {
                
                return new RespBody(Status.OK, new HashMap<String, String>() {
                    {
                        put("openId", openId);
                    }
                });
            }
            
        } else {
            return new RespBody(Status.PROMPT, "request branchPharmacyCode is not config pay params");
        }
        
    }
    
    @ResponseBody
    @RequestMapping("/getJSSDKParams")
    public RespBody getJSSDKParams(@RequestParam(required = false, value = "appId") String appId,
            @RequestParam(required = false, value = "appSecret") String appSecret,
            @RequestParam(required = true, value = "url") String url) {
        appId = StringUtils.defaultIfBlank(appId, SystemConfig.getStringValue("APP_ID"));// 处方平台开发者密码
        appSecret = StringUtils.defaultIfBlank(appSecret, SystemConfig.getStringValue("APP_SECRET"));// 处方平台开发者密码
        
        return accesstokenClient.getJSSDKParams(appId, appSecret, url);
    }
    
    @ResponseBody
    @RequestMapping("/getUserInfo")
    public RespBody getUserInfo(@RequestParam(required = true, value = "openId") String openId) {
    	
        String appId = SystemConfig.getStringValue("APP_ID"); // 处方平台开发者密码
        String appSecret = SystemConfig.getStringValue("APP_SECRET"); // 处方平台开发者密码
        
        JSONObject jsonObject = WechatSDK.getUserInfo(appId, appSecret, openId);
        
        if (jsonObject != null) {
            return new RespBody(Status.OK, new HashMap<String, String>() {
                {
                    put("unionId", jsonObject.getString("unionid"));
                }
            });
        } else {
            return new RespBody(Status.PROMPT, "response wechat userinfo is null");
        }
    }
    
}
