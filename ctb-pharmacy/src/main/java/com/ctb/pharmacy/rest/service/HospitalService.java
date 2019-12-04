/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月1日
 * Created by cwq
 */
package com.ctb.pharmacy.rest.service;

import java.util.List;

import com.ctb.commons.entity.Hospital;


/**
 * 
 * @ClassName: com.ctb.pharmacy.rest.service.HospitalService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月16日 上午11:06:05
 */
public interface HospitalService {
    
    public List<Hospital> getHospitals(String pharmacyId);
    
}
