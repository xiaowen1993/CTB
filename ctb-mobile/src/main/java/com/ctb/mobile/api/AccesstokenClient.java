/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月2日
 * Created by cwq
 */
package com.ctb.mobile.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ctb.framework.commons.entity.RespBody;

/**
 * @ClassName: com.ctb.syncdata.api.OutSiteClient
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月2日 下午5:50:04
 */

@FeignClient(value = "ctb-access-token-service", path = "/")
public interface AccesstokenClient {
    
    @RequestMapping(value = "/getAccessToken", method = RequestMethod.GET)
    public RespBody getAccessToken(@RequestParam(required = true, value = "appId") String appId,
            @RequestParam(required = true, value = "appSecret") String appSecret);
    
    @RequestMapping(value = "/getComponentAccessToken", method = RequestMethod.GET)
    public RespBody getComponentAccessToken(
            @RequestParam(required = true, value = "componentAppId") String componentAppId,
            @RequestParam(required = true, value = "componentAppSecret") String componentAppSecret);
    
    @RequestMapping(value = "/getJSTicket", method = RequestMethod.GET)
    public RespBody getJSTicket(@RequestParam(required = true, value = "appId") String appId,
            @RequestParam(required = true, value = "appSecret") String appSecret);
    
    @RequestMapping(value = "/getJSTicketSign", method = RequestMethod.GET)
    public RespBody getJSTicketSign(@RequestParam(required = true, value = "url") String url,
            @RequestParam(required = true, value = "ticket") String ticket,
            @RequestParam(required = true, value = "timestamp") String timestamp,
            @RequestParam(required = true, value = "noncestr") String noncestr);
    
    @RequestMapping(value = "/getJSSDKParams", method = RequestMethod.GET)
    public RespBody getJSSDKParams(@RequestParam(required = false, value = "appId") String appId,
            @RequestParam(required = false, value = "appSecret") String appSecret,
            @RequestParam(required = true, value = "url") String url);
}
