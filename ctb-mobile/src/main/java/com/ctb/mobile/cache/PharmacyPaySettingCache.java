/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月30日
 * Created by ckm
 */
package com.ctb.mobile.cache;

import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.PaySettings;
import com.ctb.framework.commons.redis.RedisClient;
import com.ctb.mobile.rest.controller.CTBServerController;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

/**
 * @ClassName: com.ctb.mobile.cache.PharmacyPaySettingCache
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月30日 上午11:49:31
 */
@Component
public class PharmacyPaySettingCache {
    private static Logger logger = LoggerFactory.getLogger(PharmacyPaySettingCache.class);

	@Autowired
	private RedisClient redisClient;
	/**
	 * 查找药房支付配置
	 * 
	 * @Title: getPharmacyPaySetting
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月29日 下午8:36:33
	 * @param pharmacyId
	 * @param pharmacyCode
	 * @return
	 */
	public JSONObject getPharmacyPaySetting(String pharmacyId, String pharmacyCode) {
		JSONObject jsonObject = (JSONObject) redisClient.hget(getPharmacyPaySettingCacheKay(),
				pharmacyId + getKeySplitCharCacheKey() + pharmacyCode);
		return jsonObject;
	}

	
	 /**
	  * 
	  * @Title: getPharmacyPaySetting
	  * @Description: TODO(查找药房支付配置)
	  * @author cwq
	  * @date 2019年5月5日 上午11:09:41
	  * @param pharmacyCode
	  * @return
	  */
    public JSONObject getPharmacyPaySetting(String pharmacyCode) {
        
        JSONObject jsonObject = null;
        try {
            Set<Object> fields = redisClient.hKeys(getPharmacyPaySettingCacheKay());
            
            Collection<Object> result = Collections2.filter(fields, new Predicate<Object>() {
                @Override
                public boolean apply(Object obj) {
                    
                    return ((String) obj).contains(pharmacyCode);
                }
            });
            
            if (!CollectionUtils.isEmpty(result)) {
                 jsonObject = (JSONObject) redisClient.hget(getPharmacyPaySettingCacheKay(),
                        result.toArray()[0].toString());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        
        return jsonObject;
    }
    
	/**
	 * get缓存key的分割符
	 * 
	 * @Title: getKeySplitCharCacheKey
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月29日 下午8:38:33
	 * @return
	 */
	public String getKeySplitCharCacheKey() {
		return CacheConstants.CACHE_KEY_SPLIT_CHAR;
	}
	
	/**
	 * get药房-支付配置缓存key
	 * 
	 * @Title: getPharmacyPaySettingCacheKay
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月29日 下午8:39:07
	 * @return
	 */
	public String getPharmacyPaySettingCacheKay() {
		return CacheConstants.CACHE_PHARMACY_PAYSETTINGS_HASH_KEY_PREFIX;
	}
}
