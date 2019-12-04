/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月4日
 * Created by cwq
 */
package com.ctb.accesstoken.rest.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ctb.accesstoken.cache.ComponentVerifyTicketCache;
import com.ctb.accesstoken.utils.ComponentWechatAccessTokenUtil;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;

/**
 * @ClassName: com.ctb.accesstoken.rest.controller.ComponentAccessTokenController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月4日 上午11:05:18
 */

@RefreshScope
@RestController
public class ComponentAccessTokenController {
    
    @Autowired
    private ComponentVerifyTicketCache componentVerifyTicketCache;
    
    /**
     * 
     * @Title: getComponentAccessToken
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author cwq
     * @date 2019年4月4日 下午12:05:44
     * @param componentAppId
     * @param componentAppSecret
     * @param componentVerifyTicket
     * @return
     */
    @ResponseBody
    @RequestMapping("/getComponentAccessToken")
    public RespBody getComponentAccessToken(
            @RequestParam(required = true, value = "componentAppId") String componentAppId,
            @RequestParam(required = true, value = "componentAppSecret") String componentAppSecret) {
        
        String componentVerifyTicket = componentVerifyTicketCache.getComponentVerifyTicketByAppId(componentAppId);
        if (StringUtils.isNotBlank(componentVerifyTicket)) {
            
            String componentAccessToken = ComponentWechatAccessTokenUtil.getComponentAccessToken(componentAppId,
                    componentAppSecret, componentVerifyTicket);
            
            if (StringUtils.isNotBlank(componentAccessToken)) {
                return new RespBody(Status.OK, componentAccessToken);
            }else {
                return new RespBody(Status.PROMPT, componentAccessToken);
            }
            
        } else {
            return new RespBody(Status.PROMPT, "component_Verify_Ticket为空。");
        }
        
    }
}
