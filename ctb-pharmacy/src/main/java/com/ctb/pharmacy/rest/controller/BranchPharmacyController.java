/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年6月27日
 * Created by hhy
 */
package com.ctb.pharmacy.rest.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.entity.BranchPharmacy;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.pharmacy.rest.service.BranchPharmacyService;
import com.github.pagehelper.PageInfo;


/**
 * @ClassName: com.ctb.pharmacy.rest.controller.BranchPharmacyController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年6月27日 下午3:34:24
 */

@RefreshScope
@RestController
@RequestMapping("/branchPharmacy")
public class BranchPharmacyController {
    
    private static Logger logger = LoggerFactory.getLogger(BranchPharmacyController.class);
    
    @Autowired
    private BranchPharmacyService branchPharmacyService;
    
    @ResponseBody
    @RequestMapping(value = "/list")
    public RespBody list() {
        try {

            List<BranchPharmacy> branchPharmacies = branchPharmacyService.branchPharmacys();
            return new RespBody(Status.OK, branchPharmacies);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("获取药房分店列表失败，获取数据异常！:" + e.toString());
            return new RespBody(Status.ERROR, "获取药房分店列表失败，获取数据异常！");
        }
    }
    
}
