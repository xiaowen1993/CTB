/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月17日
 * Created by cwq
 */
package com.ctb.accesstoken.rest.controller;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ctb.accesstoken.utils.WechatAccessTokenUtil;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;

/**
 * @ClassName: com.ctb.accesstoken.rest.controller.JSTicketController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月17日 下午4:13:51
 */
@RefreshScope
@RestController
public class JSTicketController {
    private static Logger logger = LoggerFactory.getLogger(JSTicketController.class);
    
    /**
     * 
     * @Title: getJSTicket
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author cwq
     * @date 2019年4月17日 下午4:16:04
     * @param appId
     * @param appSecret
     * @return
     */
    @ResponseBody
    @RequestMapping("/getJSTicket")
    public RespBody getJSTicket(@RequestParam(required = true, value = "appId") String appId,
            @RequestParam(required = true, value = "appSecret") String appSecret) {
        
        String ticket = WechatAccessTokenUtil.getJSTicket(appId, appSecret);
        
        if (StringUtils.isNotBlank(ticket)) {
            return new RespBody(Status.OK, ticket);
        } else {
            return new RespBody(Status.PROMPT, ticket);
        }
        
    }
    
    /**
     * 
     * @Title: getJSTicketSign
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author cwq
     * @date 2019年4月17日 下午5:05:24
     * @param url
     * @param ticket
     * @param timestamp
     * @param noncestr
     * @return
     */
    @ResponseBody
    @RequestMapping("/getJSTicketSign")
    public RespBody getJSTicketSign(@RequestParam(required = true, value = "url") String url,
            @RequestParam(required = true, value = "ticket") String ticket,
            @RequestParam(required = true, value = "timestamp") String timestamp,
            @RequestParam(required = true, value = "noncestr") String noncestr) {
        
        String sign = WechatAccessTokenUtil.getJSTicketSign(url, ticket, timestamp, noncestr);
        
        if (StringUtils.isNotBlank(sign)) {
            return new RespBody(Status.OK, sign);
        } else {
            return new RespBody(Status.PROMPT, sign);
        }
        
    }
    
    @ResponseBody
    @RequestMapping("/getJSSDKParams")
    public RespBody getJSSDKParams(@RequestParam(required = true, value = "appId") String appId,
            @RequestParam(required = false, value = "appSecret") String appSecret,
            @RequestParam(required = true, value = "url") String url,
            @RequestParam(required = false, value = "ticket") String ticket) {
        
        if (StringUtils.isBlank(ticket)) {
            if (!StringUtils.isBlank(appSecret)) {
                ticket = WechatAccessTokenUtil.getJSTicket(appId, appSecret);
            }else {
                return new RespBody(Status.PROMPT, "the appSecret params is null.");
            }
        }
        
        if (StringUtils.isBlank(ticket)) {
            return new RespBody(Status.PROMPT, "the ticket params is null.");
        }
        
        Map<String, String> jsSDKParams = WechatAccessTokenUtil.getJSSDKParams(appId, url, ticket);
        
        if (!CollectionUtils.isEmpty(jsSDKParams)) {
            return new RespBody(Status.OK, jsSDKParams);
        } else {
            return new RespBody(Status.PROMPT, jsSDKParams);
        }
        
    }
    
}
