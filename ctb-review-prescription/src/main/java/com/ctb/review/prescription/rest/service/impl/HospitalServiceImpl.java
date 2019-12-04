/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年7月11日
 * Created by hhy
 */
package com.ctb.review.prescription.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctb.commons.entity.Hospital;
import com.ctb.commons.entity.HospitalPharmacistUser;
import com.ctb.resources.mapper.biz.HospitalMapper;
import com.ctb.review.prescription.rest.service.HospitalPharmacistUserService;
import com.ctb.review.prescription.rest.service.HospitalService;
import com.ctb.review.prescription.rest.service.PharmacistUserService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @ClassName: com.ctb.review.prescription.rest.service.impl.HospitalServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年7月11日 下午2:31:54
 */
@Service
public class HospitalServiceImpl implements HospitalService{
    
    @Autowired
    private HospitalMapper hospitalMapper;
    
    @Autowired
    private HospitalPharmacistUserService hospitalPharmacistUserService;
    
    @Override
    public List<Hospital> getAll(String pharmacistId) {
        
        HospitalPharmacistUser hospitalPharmacistUser = new HospitalPharmacistUser();
        hospitalPharmacistUser.setPharmacistId(pharmacistId);
        
        List <HospitalPharmacistUser> list = hospitalPharmacistUserService.queryList(hospitalPharmacistUser);
        List <String> hospicalCodes = new ArrayList<String>();
        for(HospitalPharmacistUser entity:list) {
            hospicalCodes.add(entity.getHospitalCode());
        }
        
        Example example = new Example(Hospital.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", 1);
        criteria.andIn("code", hospicalCodes);
        example.setOrderByClause("CT DESC");
        return hospitalMapper.selectByExample(example);
    }
    
}
