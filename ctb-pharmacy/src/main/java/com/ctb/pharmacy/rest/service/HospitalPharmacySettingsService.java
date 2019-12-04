/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年7月2日
 * Created by hhy
 */
package com.ctb.pharmacy.rest.service;

import java.util.ArrayList;
import java.util.List;

import com.ctb.commons.entity.HospitalPharmacySettings;


/**
 * @ClassName: com.ctb.pharmacy.rest.service.HospitalPharmacySettingsService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年7月2日 下午5:33:51
 */

public interface HospitalPharmacySettingsService {
    
   public List<HospitalPharmacySettings> list(String pharmacyId);

    
}
