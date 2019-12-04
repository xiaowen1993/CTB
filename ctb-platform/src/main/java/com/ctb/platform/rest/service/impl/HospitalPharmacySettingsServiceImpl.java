/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月25日
 * Created by hhy
 */
package com.ctb.platform.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctb.commons.entity.HospitalPharmacySettings;
import com.ctb.platform.rest.service.HospitalPharmacySettingsService;
import com.ctb.resources.mapper.biz.HospitalPharmacySettingsMapper;

/**
 * @ClassName: com.ctb.platform.rest.service.impl.HospitalPharmacySettingsServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年3月25日 下午7:15:49
 */

@Service
public class HospitalPharmacySettingsServiceImpl implements HospitalPharmacySettingsService{

    @Autowired
    private HospitalPharmacySettingsMapper hospitalPharmacySettingsMapper;
    
    @Override
    public int saveHospitalPharmacySettings(List <HospitalPharmacySettings> list) {
        
        int res = 0;
        for(HospitalPharmacySettings hps: list) {
            res += hospitalPharmacySettingsMapper.insert(hps);
        }
        
        return res;
    }
    
}
