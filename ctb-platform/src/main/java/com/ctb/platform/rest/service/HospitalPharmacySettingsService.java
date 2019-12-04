/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月25日
 * Created by hhy
 */
package com.ctb.platform.rest.service;

import java.util.List;

import com.ctb.commons.entity.HospitalPharmacySettings;

/**
 * @ClassName: com.ctb.platform.rest.service.HospitalPharmacySettingsService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年3月25日 下午7:15:22
 */

public interface HospitalPharmacySettingsService {
    
    public int saveHospitalPharmacySettings(List <HospitalPharmacySettings> list);
}
