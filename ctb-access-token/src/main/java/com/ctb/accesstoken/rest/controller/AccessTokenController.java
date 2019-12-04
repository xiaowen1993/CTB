/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月4日
 * Created by cwq
 */
package com.ctb.accesstoken.rest.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ctb.accesstoken.utils.WechatAccessTokenUtil;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;

/**
 * @ClassName: com.ctb.accesstoken.rest.controller.AccessTokenController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月4日 上午11:05:40
 */
@RefreshScope
@RestController
public class AccessTokenController {
    
    private static Logger logger = LoggerFactory.getLogger(AccessTokenController.class);
    
    /**
     * 
     * @Title: getAccessToken
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author cwq
     * @date 2019年4月4日 下午12:05:36
     * @param appId
     * @param appSecret
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAccessToken")
    public RespBody getAccessToken(@RequestParam(required = true, value = "appId") String appId,
            @RequestParam(required = true, value = "appSecret") String appSecret) {
        
        String accessToken = WechatAccessTokenUtil.getAccessToken(appId, appSecret);
        
        if (StringUtils.isNotBlank(accessToken)) {
            return new RespBody(Status.OK, accessToken);
        }else {
            return new RespBody(Status.PROMPT, accessToken);
        }
        
    }
}
