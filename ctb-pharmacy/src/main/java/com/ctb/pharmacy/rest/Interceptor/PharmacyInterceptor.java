/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年6月3日
 * Created by ckm
 */
package com.ctb.pharmacy.rest.Interceptor;

/**
 * @ClassName: com.ctb.pharmacy.rest.Interceptor.a
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年6月3日 下午3:15:13
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.framework.commons.redis.RedisClient;
import com.ctb.framework.commons.utils.SpringContextHolder;

/**
 * 拦截器
 */

public class PharmacyInterceptor implements HandlerInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(PharmacyInterceptor.class);
    
    private RedisClient redisClient = SpringContextHolder.getBean(RedisClient.class);
    
    // 在请求处理之前进行调用（Controller方法调用之前
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
            throws Exception {
        try {
            HttpSession httpSession = httpServletRequest.getSession(true);
            String access_token = (String) httpSession.getAttribute("access_token");
            if (StringUtils.isNotBlank(access_token)) {
                JSONObject jsonObject = (JSONObject) redisClient
                        .hget(CacheConstants.PHARMACYUSER_LOGIN_TOKEN_HASH_KEY_PREFIX, access_token);
                if (jsonObject != null) {
                    return true;
                } else {
                    httpServletResponse.sendError(HttpStatus.SC_UNAUTHORIZED, "您已经太长时间没有操作,请刷新页面!");
                    return false;
                }
            } else {
                httpServletRequest.getRequestDispatcher("/parmacyUser/index").forward(httpServletRequest,
                        httpServletResponse);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("class:[{}],method:[{}],errMsg:[{}]", PharmacyInterceptor.class, "preHandle", e.toString());
            return false;
        }
    }
    
    // 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
            ModelAndView modelAndView) throws Exception {
        // System.out.println("postHandle被调用\n");
    }
    
    // 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
            Object o, Exception e) throws Exception {
        // System.out.println("afterCompletion被调用\n");
    }
}