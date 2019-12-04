/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年7月2日
 * Created by hhy
 */
package com.ctb.platform.rest.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ctb.commons.entity.PharmacistUser;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.framework.commons.utils.MD5Utils;
import com.ctb.platform.rest.service.HospitalPharmacistUserService;
import com.ctb.platform.rest.service.PharmacistUserService;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: com.ctb.platform.rest.controller.PharmacistController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年7月2日 下午4:56:16
 */
@RefreshScope
@RestController
@RequestMapping("/pharmacistUser")
public class PharmacistController {
    
    private static Logger logger = LoggerFactory.getLogger(PharmacistController.class);
    
    
    @Autowired
    private PharmacistUserService pharmacistService;
    
    @Autowired
    private HospitalPharmacistUserService hospitalPharmacistUserService;
    
    @RequestMapping(value = "/list")
    public RespBody list(
            @RequestParam(required=false, value="name", defaultValue="") String name,
            @RequestParam(required=false, value="page", defaultValue="1") Integer page,
            @RequestParam(required = false, value = "size", defaultValue = "10") Integer size) {
        
        try {

            List<PharmacistUser> pharmacistList = pharmacistService.queryList(name, page, size);
            if (pharmacistList == null) {
                return new RespBody(Status.ERROR, "获取药师账号列表失败");
            }
            
            PageInfo<PharmacistUser> pageInfo = new PageInfo<>(pharmacistList);
            Map<String, Object> resMap = new HashMap<String, Object>();
            resMap.put("total", pageInfo.getTotal());
            resMap.put("pharmacistList", pharmacistList);
            return new RespBody(Status.OK, resMap);
        } catch (Exception e) {
            logger.error("获取账号列表失败:::");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "获取账号列表失败");
        }
    }
    
    @RequestMapping(value = "/save")
    public RespBody save(
            @RequestParam(required=true, value="hospitalCodes") String [] hospitalCodes,
            @RequestParam(required=true, value="name") String name,
            @RequestParam(required=true, value="loginName") String loginName,
            @RequestParam(required=true, value="password") String password,
            @RequestParam(required=false, value="email") String email,
            @RequestParam(required = false, value = "status", defaultValue = "1") String status) {
        
        try {
            PharmacistUser user = new PharmacistUser();
            user.setLoginName(loginName);
            PharmacistUser pharmacist = pharmacistService.queryPharmacist(user);
            if (pharmacist != null) {
                return new RespBody(Status.PROMPT, "新增帐号失败,该名字已存在");
            }
            
            user.setId(PKGenerator.generateId());
            user.setName(name);
            user.setEmail(email);
            user.setLoginName(loginName);
            user.setPassword(MD5Utils.getMd5String32(password));
            user.setStatus(status);
            if (hospitalCodes != null && hospitalCodes.length > 0) {
                user.setHospitalCodes(Arrays.asList(hospitalCodes));
            }
            int res = pharmacistService.savePharmacist(user);
            if (res == 1) {
                return new RespBody(Status.OK, "新增帐号成功");
            }
            return new RespBody(Status.ERROR, "新增账号失败");
        } catch (Exception e) {
            logger.error("新增账号异常:::");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "新增账号异常");
        }
    }
    
    
    @RequestMapping(value = "/toEdit")
    public RespBody toEdit(
            @RequestParam(required=true, value="id") String id,
            @RequestParam(required=true, value="hospitalCodes") String [] hospitalCodes,
            @RequestParam(required=true, value="name") String name,
            @RequestParam(required=true, value="loginName") String loginName,
            @RequestParam(required=true, value="password") String password,
            @RequestParam(required=false, value="email") String email,
            @RequestParam(required = false, value = "status", defaultValue = "1") String status)  {
        
        try {
            if (StringUtils.isBlank(id)) {
                return new RespBody(Status.ERROR, "修改帐号失败ID不为空");
            } else {
                PharmacistUser user = new PharmacistUser();
                user.setLoginName(loginName);
                PharmacistUser pharmacist = pharmacistService.queryPharmacist(user);
                if (pharmacist != null && !pharmacist.getId().equals(id)) {
                    return new RespBody(Status.PROMPT, "修改帐号失败,该名字已存在");
                }
                
                user.setId(id);
                //user.setName("");//置为空，不然就算ID一样也会找不到
                user.setLoginName("");
                pharmacist = pharmacistService.queryPharmacist(user);
                if (pharmacist == null) {
                    return new RespBody(Status.ERROR, "修改帐号失败,ID不存在");
                }
                
                if(pharmacist.getPassword().equals(password)) {//如果相同则不进行MD5加密
                    pharmacist.setPassword(password);
                }else {
                    pharmacist.setPassword(MD5Utils.getMd5String32(password));
                }
                
                pharmacist.setName(name);
                pharmacist.setLoginName(loginName);
                pharmacist.setEmail(email);
                pharmacist.setStatus(status);
                if (hospitalCodes != null && hospitalCodes.length > 0) {
                    pharmacist.setHospitalCodes(Arrays.asList(hospitalCodes));
                }
                int res = pharmacistService.updatePharmacist(pharmacist);
                if (res == 1) {
                    return new RespBody(Status.OK, "修改帐号成功");
                }
                return new RespBody(Status.ERROR, "修改帐号失败");
            }
            
        } catch (Exception e) {
            logger.error("修改帐号异常:::");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "修改帐号异常");
        }
    }
    
    
    @RequestMapping(value = "/editStatus")
    public RespBody editStatus(
            @RequestParam(required=true, value="id") String id,
            @RequestParam(required=true, value = "status") String status) {
        
        try {
            if (StringUtils.isBlank(id)) {
                return new RespBody(Status.ERROR, "修改帐号失败ID不为空");
            } else {
                PharmacistUser user = new PharmacistUser();
                user.setId(id);
                PharmacistUser pharmacist = pharmacistService.queryPharmacist(user);
                if (pharmacist == null) {
                    return new RespBody(Status.ERROR, "修改状态失败,ID不存在");
                }
                
                pharmacist.setStatus(status);
                int res = pharmacistService.updateStatus(pharmacist);
                if (res == 1) {
                    return new RespBody(Status.OK, "修改状态成功");
                }
                return new RespBody(Status.ERROR, "修改状态失败");
            }
            
        } catch (Exception e) {
            logger.error("修改帐号异常:::");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "修改帐号异常");
        }
    }
    
    
    @RequestMapping(value = "/toDel")
    public RespBody toDel(
            @RequestParam(required = true, value = "ids") String[] ids) throws Exception {
        try {
            if(ids.length == 0) {
                return new RespBody(Status.ERROR, "删除账号失败,ID不能为空");
            }
            int res = pharmacistService.deleteByIds(ids);
            if (res == ids.length) {
                return new RespBody(Status.OK, "删除账号成功");
            } else {
                return new RespBody(Status.ERROR, "删除账号失败");
            }
        } catch (Exception e) {
            logger.error("删除账号失败：：：");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "删除账号失败");
        }
    }
    
}
