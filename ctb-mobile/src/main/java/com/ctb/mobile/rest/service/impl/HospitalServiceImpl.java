/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月1日
 * Created by cwq
 */
package com.ctb.mobile.rest.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctb.commons.entity.Hospital;
import com.ctb.commons.entity.User;
import com.ctb.mobile.rest.service.HospitalService;
import com.ctb.resources.mapper.biz.HospitalMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: com.ctb.mobile.rest.service.impl.HospitalServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月1日 下午9:53:21
 */
@Service
public class HospitalServiceImpl implements HospitalService {
    
    private static Logger logger = LoggerFactory.getLogger(HospitalServiceImpl.class);
    
    @Autowired
    public HospitalMapper hospitalMapper;
    
    public List<Hospital> getHospitals() {
        // TODO Auto-generated method stub
        return hospitalMapper.getHospitals();
    }
    
    public Hospital getHospitalByCode(String code) {
        Hospital hospital = null;
        
        try {
            Example example = new Example(Hospital.class);
            Example.Criteria criteria = example.createCriteria();
            
            if (!StringUtils.isEmpty(code)) {
                criteria.andEqualTo("code", code);
            }
            
            hospital = hospitalMapper.selectOneByExample(example);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error("find hospital from database error, make transfer error. hospitalCode={}, errorMsg={}, "
                        + "cause by: {}.", new Object[] { code, e.getMessage(), e.getCause() });
            }
        }
        
        return hospital;
    }
}
