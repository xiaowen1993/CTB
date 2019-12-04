/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年7月10日
 * Created by hhy
 */
package com.ctb.review.prescription.rest.controller;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.PharmacistUser;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.utils.MD5Utils;
import com.ctb.framework.commons.utils.TokenUtils;
import com.ctb.review.prescription.cache.PharmacistUserCache;
import com.ctb.review.prescription.rest.service.PharmacistUserService;

import net.sf.json.JSONObject;

/**
 * @ClassName: com.ctb.review.prescription.rest.controller.PharmacistUserContoller
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年7月10日 下午5:54:35
 */

@RefreshScope
@RestController
@RequestMapping(value = "/pharmacistUser")
public class PharmacistUserController {
    
    private static Logger logger = LoggerFactory.getLogger(PharmacistUserController.class);
    
    @Autowired
    private PharmacistUserService pharmacistUserService;
    
    @Autowired
    private PharmacistUserCache pharmacistUserCache;
    
    /**
     * 药房职员登录系统
     * 
     * @Title: query
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author ckm
     * @date 2019年6月3日 下午5:52:05
     * @param name
     * @param password
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public RespBody login(@RequestParam(required = true, value = "loginName") String loginName,
            @RequestParam(required = true, value = "password") String password, HttpServletRequest request,
            HttpServletResponse response) {
        PharmacistUser querydata = new PharmacistUser();
        HttpSession session = request.getSession(true);// 创建会话
        
        try {
            querydata.setLoginName(loginName);
            querydata = pharmacistUserService.queryPharmacist(querydata);
            if (querydata == null) {
                return new RespBody(Status.PROMPT, "登录账户不存在！");
            }
            if (querydata.getPassword().equals(MD5Utils.getMd5String32(password))) {
                
                
                querydata.setLastLoginTime(new java.util.Date().getTime());
                pharmacistUserService.updatePharmacist(querydata);// 更新登录时间
                // Redis保存药房员工登录信息-start
                pharmacistUserCache.addLogin(querydata);
                // 自定义生成登录成功状态令牌token
                String access_token = TokenUtils.getToken(querydata.getId());
                // 返回 access_token到客户端
                session.setAttribute("access_token", access_token);
                Cookie cookie = new Cookie("access_token", access_token);
                response.addCookie(cookie);
                //
                pharmacistUserCache.addToken(querydata, access_token);
                querydata.setPassword("");
                
                JSONObject json = JSONObject.fromObject(querydata);
                json.remove("password");
                return new RespBody(Status.OK, json);
            } else {
                return new RespBody(Status.PROMPT, "密码校验失败！");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("class:[{}],method:[{}],errMsg:[{}]", PharmacistUserController.class, "login", e.toString());
            return new RespBody(Status.PROMPT, "查询异常！");
        }
    }
    
    /**
     * 药房职员登出系统
     * 
     * @Title: logOut
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author ckm
     * @date 2019年6月3日 下午5:51:28
     * @param id
     * @param pharmacyId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/logOut")
    @ResponseBody
    public RespBody logOut(@RequestParam(required = true, value = "id") String id,
            HttpServletRequest httpServletRequest, HttpServletResponse response) {
        try {
            HttpSession httpSession = httpServletRequest.getSession(true);
            String access_token = (String) httpSession.getAttribute("access_token");
            PharmacistUser pharmacistUser = new PharmacistUser();
            pharmacistUser.setId(id);
            pharmacistUser = pharmacistUserService.queryPharmacist(pharmacistUser);
            pharmacistUserCache.updateLogOut(pharmacistUser);
            pharmacistUserCache.hdel(CacheConstants.PHARMACYUSER_LOGIN_TOKEN_HASH_KEY_PREFIX, access_token);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("class:[{}],method:[{}],errMsg:[{}]", PharmacistUserController.class, "logOut", e.toString());
            return new RespBody(Status.ERROR, "用户登出失败！");
        }
        return new RespBody(Status.OK, "用户登出成功！");
    }
}
