/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月27日
 * Created by hhy
 */
package com.ctb.platform.rest.controller;

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

import com.ctb.commons.entity.PharmacyUser;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.framework.commons.utils.MD5Utils;
import com.ctb.platform.rest.service.PharmacyUserService;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: com.ctb.platform.rest.controller.PharmacyUserController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年3月27日 上午11:46:14
 */

@RefreshScope
@RestController
@RequestMapping("/pharmacyUser")
public class PharmacyUserController {
    
    private static Logger logger = LoggerFactory.getLogger(PharmacyUserController.class);
    
    @Autowired
    private PharmacyUserService pharmacyUserService;
    
    /**
     * 
     * @Title: list
     * @Description: 获取账户列表
     * @author hhy
     * @date 2019年3月27日 下午3:28:50
     * @param pharmacyId
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list")
    public RespBody list(
            @RequestParam(required=true, value="branchPharmacyId") String branchPharmacyId,
            @RequestParam(required=false, value="name", defaultValue="") String name,
            @RequestParam(required=false, value="page", defaultValue="1") Integer page,
            @RequestParam(required = false, value = "size", defaultValue = "10") Integer size) {
        
        try {
            PharmacyUser pharmacyUser = new PharmacyUser();
            pharmacyUser.setPharmacyId(branchPharmacyId);
            pharmacyUser.setName(name);
            List<PharmacyUser> pharmacyUserList = pharmacyUserService.pharmacyUsersList(pharmacyUser, "", page,
                    size);
            if (pharmacyUserList == null) {
                return new RespBody(Status.ERROR, "获取账号列表失败");
            }
            PageInfo<PharmacyUser> pageInfo = new PageInfo<>(pharmacyUserList);
            Map<String, Object> resMap = new HashMap<String, Object>();
            resMap.put("total", pageInfo.getTotal());
            resMap.put("pharmacyUserList", pharmacyUserList);
            return new RespBody(Status.OK, resMap);
        } catch (Exception e) {
            logger.error("获取账号列表失败:::");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "获取账号列表失败");
        }
    }
    
    /**
     * 
     * @Title: save
     * @Description: 增加账户
     * @author hhy
     * @date 2019年3月27日 下午3:29:13
     * @param pharmacyId
     * @param name
     * @param password
     * @param status
     * @return
     */
    @RequestMapping(value = "/save")
    public RespBody save(
            @RequestParam(required=true, value="pharmacyId") String pharmacyId,
            @RequestParam(required=true, value="name") String name,
            @RequestParam(required=true, value="password") String password,
            @RequestParam(required=false, value="email") String email,
            @RequestParam(required = false, value = "status", defaultValue = "1") String status) {
        
        try {
            PharmacyUser user = new PharmacyUser();
            user.setName(name);
            PharmacyUser pharmacyUser = pharmacyUserService.queryPharmacyUser(user);
            if (pharmacyUser != null) {
                return new RespBody(Status.PROMPT, "新增帐号失败,该名字已存在");
            }
            
            user.setId(PKGenerator.generateId());
            user.setPharmacyId(pharmacyId);
            user.setName(name);
            user.setEmail(email);
            user.setPassword(MD5Utils.getMd5String32(password));
            user.setStatus(status);
            int res = pharmacyUserService.savePharmacyUser(user);
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
    
    /**
     * 
     * @Title: toEdit
     * @Description: 修改账号信息
     * @author hhy
     * @date 2019年3月27日 下午3:29:39
     * @param id
     * @param pharmacyId
     * @param name
     * @param password
     * @param status
     * @return
     */
    @RequestMapping(value = "/toEdit")
    public RespBody toEdit(
            @RequestParam(required=true, value="id") String id,
            @RequestParam(required=true, value="pharmacyId") String pharmacyId,
            @RequestParam(required=true, value="name") String name,
            @RequestParam(required=true, value="password") String password,
            @RequestParam(required=false, value="email") String email,
            @RequestParam(required=true, value = "status") String status) {
        
        try {
            if (StringUtils.isBlank(id)) {
                return new RespBody(Status.ERROR, "修改帐号失败ID不为空");
            } else {
                PharmacyUser user = new PharmacyUser();
                user.setName(name);
                PharmacyUser pharmacyUser = pharmacyUserService.queryPharmacyUser(user);
                if (pharmacyUser != null && !pharmacyUser.getId().equals(id)) {
                    return new RespBody(Status.PROMPT, "修改帐号失败,该名字已存在");
                }
                
                user.setId(id);
                user.setName("");//置为空，不然就算ID一样也会找不到
                pharmacyUser = pharmacyUserService.queryPharmacyUser(user);
                if (pharmacyUser == null) {
                    return new RespBody(Status.ERROR, "修改帐号失败,ID不存在");
                }
                
                if(pharmacyUser.getPassword().equals(password)) {//如果相同则不进行MD5加密
                    pharmacyUser.setPassword(password);
                }else {
                    pharmacyUser.setPassword(MD5Utils.getMd5String32(password));
                }
                
                pharmacyUser.setPharmacyId(pharmacyId);
                pharmacyUser.setName(name);
                pharmacyUser.setEmail(email);
                pharmacyUser.setStatus(status);
                int res = pharmacyUserService.updatePharmacyUser(pharmacyUser);
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
                PharmacyUser user = new PharmacyUser();
                user.setId(id);
                PharmacyUser pharmacyUser = pharmacyUserService.queryPharmacyUser(user);
                if (pharmacyUser == null) {
                    return new RespBody(Status.ERROR, "修改状态失败,ID不存在");
                }
                
                pharmacyUser.setStatus(status);
                int res = pharmacyUserService.updatePharmacyUser(pharmacyUser);
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
    
    
    
    /**
     * 
     * @Title: toDel
     * @Description: 删除账号
     * @author hhy
     * @date 2019年3月25日 下午3:43:19
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toDel")
    public RespBody toDel(
            @RequestParam(required = true, value = "ids") String[] ids) throws Exception {
        try {
            if(ids.length == 0) {
                return new RespBody(Status.ERROR, "删除账号失败,ID不能为空");
            }
            int res = pharmacyUserService.deleteByIds(ids);
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
