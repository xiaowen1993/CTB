/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年5月31日
 * Created by ckm
 */
package com.ctb.pharmacy.rest.controller;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.BranchPharmacy;
import com.ctb.commons.entity.Pharmacy;
import com.ctb.commons.entity.PharmacyUser;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.utils.MD5Utils;
import com.ctb.framework.commons.utils.TokenUtils;
import com.ctb.pharmacy.cache.BranchPharmacyCache;
import com.ctb.pharmacy.cache.PharmacyUserCache;
import com.ctb.pharmacy.rest.Interceptor.PharmacyInterceptor;
import com.ctb.pharmacy.rest.entity.vo.PharmacyUserVo;
import com.ctb.pharmacy.rest.service.BranchPharmacyService;
import com.ctb.pharmacy.rest.service.PharmacyUserService;

/**
 * @ClassName: com.ctb.pharmacy.rest.controller.PharmacyUserController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年5月31日 上午8:59:09
 */
@RefreshScope
@RestController
@RequestMapping(value = "/parmacyUser")
public class PharmacyUserController {
    
    private static Logger logger = LoggerFactory.getLogger(PharmacyUserController.class);
    
    @Autowired
    private PharmacyUserService pharmacyUserService;
    
    @Autowired
    private PharmacyUserCache pharmacyUserCache;
    
    @Autowired
    private BranchPharmacyService branchPharmacyService;
    
    @Autowired
    private BranchPharmacyCache branchPharmacyCache;
    
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
    public RespBody login(@RequestParam(required = true, value = "name") String name,
            @RequestParam(required = true, value = "password") String password, HttpServletRequest request,
            HttpServletResponse response) {
        PharmacyUser querydata = new PharmacyUser();
        HttpSession session = request.getSession(true);// 创建会话
        
        try {
            querydata.setName(name);
            querydata = pharmacyUserService.query(querydata);
            if (querydata == null) {
                return new RespBody(Status.PROMPT, "登录账户不存在！");
            }
            if (querydata.getPassword().equals(MD5Utils.getMd5String32(password))) {
                
                PharmacyUserVo pharmacyUserVo = new PharmacyUserVo();
                BeanUtils.copyProperties(pharmacyUserVo, querydata);
                
                BranchPharmacy entity = branchPharmacyCache.getBranchPharmacy(querydata.getPharmacyId());
                if(entity==null) {
                    entity = branchPharmacyService.queryBranchPharmacy(new BranchPharmacy(querydata.getPharmacyId()));
                }
                
                pharmacyUserVo.setPharmacyCode(entity.getCode());
                
                querydata.setLastLoginTime(new java.util.Date().getTime());
                pharmacyUserService.update(querydata);// 更新登录时间
                // Redis保存药房员工登录信息-start
                pharmacyUserCache.addLogin(querydata);
                // 自定义生成登录成功状态令牌token
                String access_token = TokenUtils.getToken(querydata.getId());
                // 返回 access_token到客户端
                session.setAttribute("access_token", access_token);
                Cookie cookie = new Cookie("access_token", access_token);
                response.addCookie(cookie);
                //
                pharmacyUserCache.addToken(querydata, access_token);
                return new RespBody(Status.OK, pharmacyUserVo);
            } else {
                return new RespBody(Status.PROMPT, "密码校验失败！");
            }
        } catch (NoSuchAlgorithmException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            logger.error("class:[{}],method:[{}],errMsg:[{}]", PharmacyUserController.class, "login", e.toString());
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
            @RequestParam(required = true, value = "pharmacyId") String pharmacyId,
            HttpServletRequest httpServletRequest, HttpServletResponse response) {
        try {
            HttpSession httpSession = httpServletRequest.getSession(true);
            String access_token = (String) httpSession.getAttribute("access_token");
            PharmacyUser pharmacyUser = new PharmacyUser();
            pharmacyUser.setId(id);
            pharmacyUser.setPharmacyId(pharmacyId);
            pharmacyUserCache.updateLogOut(pharmacyUser);
            pharmacyUserCache.hdel(CacheConstants.PHARMACYUSER_LOGIN_TOKEN_HASH_KEY_PREFIX, access_token);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("class:[{}],method:[{}],errMsg:[{}]", PharmacyUserController.class, "logOut", e.toString());
            return new RespBody(Status.ERROR, "用户登出失败！");
        }
        return new RespBody(Status.OK, "用户登出成功！");
    }
}
