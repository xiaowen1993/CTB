/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年5月30日
 * Created by ckm
 */
package com.ctb.pharmacy.rest.service;

import com.ctb.commons.entity.PharmacyUser;

/**
 * @ClassName: com.ctb.pharmacy.rest.service.PharmacyUserService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年5月30日 下午5:51:51
 */

public interface PharmacyUserService {

	public PharmacyUser query(PharmacyUser pharmacyUser);
	
	public int update(PharmacyUser pharmacyUser);
	
	public PharmacyUser queryByPharmacyId(String pharmacyId);
}
