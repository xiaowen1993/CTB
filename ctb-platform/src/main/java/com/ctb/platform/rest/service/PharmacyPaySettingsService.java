/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月23日
 * Created by ckm
 */
package com.ctb.platform.rest.service;

import java.util.List;

import com.ctb.commons.entity.PaySettings;
import com.ctb.commons.entity.Pharmacy;
import com.ctb.commons.entity.PharmacyPaySettings;

/**
 * @ClassName: com.ctb.platform.rest.service.PharmacyPaySettingsService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年3月23日 上午10:52:41
 */

public interface PharmacyPaySettingsService {
    
    /**
     * 
     * @Title: queryPharmacyPaySettingsOne
     * @Description: TODO(查询)
     * @author ckm
     * @date 2019年4月1日 上午10:19:22
     * @param paySettings
     * @return
     */
    public PaySettings queryPharmacyPaySettingsOne(PharmacyPaySettings paySettings);
    
    public PharmacyPaySettings queryOne(PharmacyPaySettings pharmacyPaySettings);
    
    /**
     * 
     * @Title: save
     * @Description: TODO(保存-启用事务)
     * @author ckm
     * @date 2019年4月1日 上午10:21:51
     * @param pharmacyPaySettings
     * @param paySettings
     * @return
     */
    public int save(PharmacyPaySettings pharmacyPaySettings, PaySettings paySettings);
    
    public List<PaySettings> queryPaySettinsList(String pharmacyId);
    
    /**
     * 
     * @Title: update
     * @Description: TODO(更新-启用事务)
     * @author ckm
     * @date 2019年4月1日 上午10:20:39
     * @param pharmacyPaySettings
     * @param paySettings
     * @return
     */
    public int update(String pharmacyId, PaySettings paySettings);
    
    /**
     * 
     * @Title: initPharmacyPaysettingsCache
     * @Description: TODO(初始化药房支付配置缓存)
     * @author ckm
     * @date 2019年4月1日 上午10:24:17
     */
    public void initPharmacyPaysettingsCache();
    
    /**
     * 
     * @Title: saveAndUpdatePharmacyPaySettingsCache
     * @Description: TODO(保存/更新药房支付配置缓存)
     * @author ckm
     * @date 2019年4月1日 上午10:24:48
     * @param pharmacyPaySettings
     * @param paySettings
     */
    public void saveAndUpdatePharmacyPaySettingsCache(Pharmacy pharmacy, PaySettings paySettings);
}
