/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月25日
 * Created by hhy
 */
package com.ctb.platform.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.redis.RedisClient;
import com.ctb.platform.initdata.InitCacheRunner;
import com.ctb.platform.rest.service.BranchPharmacyService;
import com.ctb.platform.rest.service.PharmacyService;
import com.ctb.platform.rest.service.UserService;

/**
 * @ClassName: com.ctb.platform.rest.controller.UserController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年3月25日 下午5:28:33
 */

@RefreshScope
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PharmacyService pharmacyService;
    
    @Autowired
    private BranchPharmacyService branchPharmacyService;
    
    @Autowired
    private RedisClient redisClient;
    
    @Autowired
    private InitCacheRunner initCacheRunner;
    
    @RequestMapping(value = "/login")
    public RespBody login() {
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", 1);
        map.put("username", "admin");
        map.put("password", "123456");
        map.put("avatar", "");
        map.put("name", "admin");
        // id: 1,
        // username: 'admin',
        // password: '123456',
        // avatar: 'https: //raw.githubusercontent.com/taylorchen709/markdown-images/master/vueadmin/user.png',
        // name: '张某某'
        // List<U> list = userService.queryUserListByExample(map);
        return new RespBody(Status.OK, map);
    }
    
   /* 
    @RequestMapping(value = "/updatePharmacyCach")
    public RespBody updatePharmacyCach() {
        List<Pharmacy> list = pharmacyService.querPharmacyList(new Pharmacy());
        for (Pharmacy pharmacy : list) {
            BranchPharmacy branchPharmacy = new BranchPharmacy();
            branchPharmacy.setPharmacyId(pharmacy.getId());
            List<BranchPharmacy> branchPharmacyList = branchPharmacyService.queryBranchPharmacyList(branchPharmacy);
            redisClient.hset("pharmacy_branchPharmacy", pharmacy.getId(), branchPharmacyList);
        }
        return new RespBody(Status.OK, "ok");
    }*/
    
    @RequestMapping(value = "/login1")
    public RespBody login1() throws Exception {
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        
        initCacheRunner.run();
        
        
        return new RespBody(Status.OK, map);
    }
    
}
