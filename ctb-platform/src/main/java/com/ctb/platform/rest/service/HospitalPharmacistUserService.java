/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年7月10日
 * Created by hhy
 */
package com.ctb.platform.rest.service;

import java.util.List;

import com.ctb.commons.entity.HospitalPharmacistUser;

/**
 * @ClassName: com.ctb.platform.rest.service.HospitalPharmacistUserService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年7月10日 上午11:42:55
 */

public interface HospitalPharmacistUserService {
 
    public List <HospitalPharmacistUser> queryList(HospitalPharmacistUser hospitalPharmacistUser);
    
    public int save(HospitalPharmacistUser hospitalPharmacistUser);
    
    public int update(HospitalPharmacistUser hospitalPharmacistUser);
    
    public HospitalPharmacistUser queryOne(HospitalPharmacistUser hospitalPharmacistUser);

    public int delete(HospitalPharmacistUser hospitalPharmacistUser);
    
    public int deleteByPharmacistIds(String [] pharmacistIds);
}
