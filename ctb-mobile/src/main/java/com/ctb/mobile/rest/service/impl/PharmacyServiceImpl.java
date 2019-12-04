/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月8日
 * Created by hhy
 */
package com.ctb.mobile.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctb.commons.entity.Pharmacy;
import com.ctb.mobile.rest.service.PharmacyService;
import com.ctb.resources.mapper.biz.PharmacyMapper;

/**
 * @ClassName: com.ctb.mobile.rest.service.impl.PharmacyServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年4月8日 上午10:24:53
 */

@Service
public class PharmacyServiceImpl implements PharmacyService{
    
    @Autowired
    private PharmacyMapper pharmacyMapper;

    @Override
    public Pharmacy selectByPrimaryKey(String pharmacyId) {
        
        return pharmacyMapper.selectByPrimaryKey(pharmacyId);
    }
    
}
