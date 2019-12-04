/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年6月19日
 * Created by hhy
 */
package com.ctb.pharmacy.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ctb.commons.entity.BranchPharmacy;
import com.ctb.commons.entity.Hospital;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.pharmacy.rest.service.BranchPharmacyService;
import com.ctb.pharmacy.rest.service.HospitalService;


/**
 * @ClassName: com.ctb.pharmacy.rest.controller.HospitalController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年6月19日 下午8:07:45
 */

@RefreshScope
@RestController
@RequestMapping("/hospital")
public class HospitalController {
    
    @Autowired
    private HospitalService hospitalService;
    
    @Autowired
    private BranchPharmacyService branchPharmacyService;
    
    @RequestMapping(value = "/list")
    public RespBody list(@RequestParam(required = true, value = "branchPharmacyCode") String branchPharmacyCode) {
        
        try {
            
            BranchPharmacy branchPharmacy = new BranchPharmacy();
            branchPharmacy.setCode(branchPharmacyCode);
            BranchPharmacy entity = branchPharmacyService.queryBranchPharmacy(branchPharmacy);
            
            List<Hospital> hospitals = hospitalService.getHospitals(entity.getPharmacyId());
            
            return new RespBody(Status.OK, hospitals);
        } catch (Exception e) {
            e.printStackTrace();
            return new RespBody(Status.ERROR, "获取医院列表出错");
        }
    }
}
