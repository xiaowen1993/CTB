/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年7月10日
 * Created by hhy
 */
package com.ctb.review.prescription.rest.service;

import java.util.List;

import com.ctb.commons.entity.PharmacistUser;

/**
 * @ClassName: com.ctb.review.prescription.rest.service.PharmacistUserService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年7月10日 下午5:57:26
 */

public interface PharmacistUserService {
    
    public int savePharmacist(PharmacistUser pharmacist);
    
    public int updatePharmacist(PharmacistUser pharmacist);
    
    public int updateStatus(PharmacistUser pharmacist);
    
    public int delPharmacist(String id);
    
    public PharmacistUser queryPharmacist(PharmacistUser pharmacist);
    
    public List<PharmacistUser> pharmacistList(PharmacistUser pharmacist,String searchKey,Integer page,Integer pageSize);

    public int deleteByIds(String[] ids);
    
    public int deleteByBranchPharmacyIds(String [] branchPharmacyIds);
    
    public List<PharmacistUser> queryList(String name, Integer page,Integer pageSize);
    
    
}
