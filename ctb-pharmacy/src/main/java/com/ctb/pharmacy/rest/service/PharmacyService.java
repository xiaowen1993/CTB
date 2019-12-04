/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月8日
 * Created by hhy
 */
package com.ctb.pharmacy.rest.service;

import com.ctb.commons.entity.Pharmacy;

/**
 * 
 * @ClassName: com.ctb.pharmacy.rest.service.PharmacyService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月16日 上午11:06:15
 */
public interface PharmacyService {
    
    public Pharmacy selectByPrimaryKey(String pharmacyId);
    
}
