/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年7月2日
 * Created by hhy
 */
package com.ctb.platform.rest.service;

import java.util.List;

import com.ctb.commons.entity.PharmacistUser;

/**
 * @ClassName: com.ctb.platform.rest.service.PharmacistService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年7月2日 下午4:57:24
 */

public interface PharmacistUserService {
    
    public int savePharmacist(PharmacistUser pharmacist);
    
    public int updatePharmacist(PharmacistUser pharmacist);
    
    public int updateStatus(PharmacistUser pharmacist);
    
    public int delPharmacist(String id);
    
    public PharmacistUser queryPharmacist(PharmacistUser pharmacist);
    
    public int deleteByIds(String[] ids);
    
    public int deleteByBranchPharmacyIds(String [] branchPharmacyIds);
    
    public List<PharmacistUser> queryList(String name, Integer page,Integer pageSize);
    
    
}
