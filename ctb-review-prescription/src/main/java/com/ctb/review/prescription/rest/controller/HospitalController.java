/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年7月11日
 * Created by hhy
 */
package com.ctb.review.prescription.rest.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ctb.commons.entity.Hospital;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.review.prescription.rest.service.HospitalService;

/**
 * @ClassName: com.ctb.review.prescription.rest.controller.HospitalController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年7月11日 下午2:27:53
 */

@RefreshScope
@RestController
@RequestMapping("/hospital")
public class HospitalController {
    
    @Autowired
    private HospitalService hospitalService;
    
    @RequestMapping(value = "/all")
    public RespBody all(@RequestParam(required = true, value = "pharmacistId") String pharmacistId) {
        
        try {
            List <Hospital> list = hospitalService.getAll(pharmacistId);
            if (CollectionUtils.isEmpty(list)) {
                return new RespBody(Status.PROMPT, "暂无数据");
            }
            return new RespBody(Status.OK, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new RespBody(Status.ERROR, "获取所有医院失败");
        }
    }
    
}
