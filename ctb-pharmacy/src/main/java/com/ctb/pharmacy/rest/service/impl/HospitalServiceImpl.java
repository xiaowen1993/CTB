/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年6月19日
 * Created by hhy
 */
package com.ctb.pharmacy.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctb.commons.entity.Hospital;
import com.ctb.commons.entity.HospitalPharmacySettings;
import com.ctb.pharmacy.rest.service.HospitalPharmacySettingsService;
import com.ctb.pharmacy.rest.service.HospitalService;
import com.ctb.resources.mapper.biz.HospitalMapper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @ClassName: com.ctb.pharmacy.rest.service.impl.HospitalServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年6月19日 下午8:10:11
 */

@Service
public class HospitalServiceImpl implements HospitalService{
    
    @Autowired
    private HospitalMapper hospitalMapper;
    
    @Autowired
    private HospitalPharmacySettingsService hospitalPharmacySettingsService;

    @Override
    public List<Hospital> getHospitals(String pharmacyId) {
        
        
        List <HospitalPharmacySettings>  hospitalPharmacySettings = hospitalPharmacySettingsService.list(pharmacyId);
        List <String> hospitalIds = new ArrayList<String>();
        
        for(HospitalPharmacySettings hospitalPharmacySetting: hospitalPharmacySettings) {
            hospitalIds.add(hospitalPharmacySetting.getHospitalId());
        }
        
        Example example = new Example(Hospital.class);
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("CT DESC");
        criteria.andEqualTo("status", 1);
        criteria.andIn("id", hospitalIds);
        return hospitalMapper.selectByExample(example);
    }
    
}
