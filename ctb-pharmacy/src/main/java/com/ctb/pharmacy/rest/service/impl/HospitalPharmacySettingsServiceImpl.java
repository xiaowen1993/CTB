/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年7月2日
 * Created by hhy
 */
package com.ctb.pharmacy.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctb.commons.entity.HospitalPharmacySettings;
import com.ctb.pharmacy.rest.service.HospitalPharmacySettingsService;
import com.ctb.resources.mapper.biz.HospitalPharmacySettingsMapper;
import com.ctb.resources.mapper.biz.PharmacyPaySettingsMapper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @ClassName: com.ctb.pharmacy.rest.service.impl.PharmacyPaySettingsServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年7月2日 下午5:34:06
 */
@Service
public class HospitalPharmacySettingsServiceImpl implements HospitalPharmacySettingsService {
    
    @Autowired
    private HospitalPharmacySettingsMapper  HospitalPharmacySettingsMapper;
    
    @Override
    public List<HospitalPharmacySettings> list(String pharmacyId) {
        
        List<HospitalPharmacySettings> ids = new ArrayList<HospitalPharmacySettings>();
        Example e = new Example(HospitalPharmacySettings.class);
        Criteria c = e.createCriteria();
        c.andEqualTo("pharmacyId", pharmacyId);
        ids = HospitalPharmacySettingsMapper.selectByExample(e);
        
        return ids;
    }
    
}
