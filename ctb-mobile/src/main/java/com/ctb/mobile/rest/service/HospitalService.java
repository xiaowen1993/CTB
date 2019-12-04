/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月1日
 * Created by cwq
 */
package com.ctb.mobile.rest.service;

import java.util.List;

import com.ctb.commons.entity.Hospital;

/**
 * @ClassName: com.ctb.mobile.rest.service.HospitalService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月1日 下午9:53:04
 */

public interface HospitalService {
    
    public List<Hospital> getHospitals();
    
    public Hospital getHospitalByCode(String code);
}
