/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月1日
 * Created by hhy
 */
package com.ctb.pharmacy.rest.service;

import java.util.List;

import com.ctb.commons.entity.BranchPharmacy;

/**
 * 
 * @ClassName: com.ctb.pharmacy.rest.service.BranchPharmacyService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月16日 上午10:36:44
 */

public interface BranchPharmacyService {
    
    /**
     * 
     * @Title: queryBranchPharmacy
     * @Description: TODO(查询)
     * @author ckm
     * @date 2019年4月1日 上午9:08:55
     * @param branchPharmacy
     * @return
     */
    public BranchPharmacy queryBranchPharmacy(BranchPharmacy branchPharmacy);

    public List <BranchPharmacy> branchPharmacys();
	
}
