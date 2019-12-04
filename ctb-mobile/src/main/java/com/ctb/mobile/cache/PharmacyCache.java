/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月29日
 * Created by hhy
 */
package com.ctb.mobile.cache;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.Pharmacy;
import com.ctb.framework.commons.redis.RedisClient;

/**
 * @ClassName: com.ctb.mobile.cache.PharmacyCache
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年4月29日 下午3:58:42
 */
@Component
public class PharmacyCache {
    
    @Autowired
    private RedisClient redisClient;
    
    /**
     * 
     * @Title: getPharmacy
     * @Description: 从缓存获取药店信息
     * @author hhy
     * @date 2019年4月29日 下午4:15:02
     * @param pharmacyId
     * @return
     */
    public Pharmacy getPharmacy(String pharmacyId) {
        Pharmacy pharmacy = null;
        String cacheKey = getPharmacyCacheKey();
        if (StringUtils.isNotBlank(pharmacyId)) {
            JSONObject json = (JSONObject) redisClient.hget(cacheKey, pharmacyId);
            if (json != null && StringUtils.isNotBlank(json.toJSONString())) {
                pharmacy = JSON.parseObject(json.toJSONString(), Pharmacy.class);
            }
        }
        return pharmacy;
    }
    
    /**
     * 
     * @Title: updatePharmacy
     * @Description: 修改药店缓存信息
     * @author hhy
     * @date 2019年4月29日 下午4:08:39
     * @param pharmacy
     */
    public void updatePharmacy(Pharmacy pharmacy) {
        String cacheKey = getPharmacyCacheKey();
        if (pharmacy != null && StringUtils.isNotBlank(pharmacy.getId())) {
            redisClient.hset(cacheKey, pharmacy.getId(), pharmacy);
        }
    }
    
    /**
     * 
     * @Title: removePharmacy
     * @Description: 删除药店缓存
     * @author hhy
     * @date 2019年4月29日 下午4:29:59
     * @param pharmacyId
     */
    public void removePharmacy(String pharmacyId) {
        String cacheKey = getPharmacyCacheKey();
        if (StringUtils.isNotBlank(pharmacyId)) {
            redisClient.hdel(cacheKey, pharmacyId);
        }
    }
    
    /**
     * 
     * @Title: getPharmacyCacheKey
     * @Description: 获取药店缓存
     * @author hhy
     * @date 2019年4月29日 下午4:00:28
     * @return
     */
    public String getPharmacyCacheKey() {
        return CacheConstants.CACHE_PHARMACY_HASH_KEY_PREFIX;
    }
}
