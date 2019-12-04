/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月30日
 * Created by ckm
 */
package com.ctb.mobile.rest.service;

import com.ctb.commons.entity.PaySettings;

/**
 * @ClassName: com.ctb.mobile.rest.service.PharmacyPaySettingService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月30日 下午12:05:42
 */

public interface PaySettingService {

	public PaySettings queryPaySettings(String pharmacyId,String PharmacyCode);
}
