/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年7月11日
 * Created by hhy
 */
package com.ctb.review.prescription.rest.service;

import java.util.List;

import com.ctb.commons.entity.Hospital;

/**
 * @ClassName: com.ctb.review.prescription.rest.service.HospitalService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年7月11日 下午2:31:43
 */

public interface HospitalService {
    
    public List<Hospital> getAll(String pharmacistId);
    
}
