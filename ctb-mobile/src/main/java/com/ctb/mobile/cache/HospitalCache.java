/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月29日
 * Created by hhy
 */
package com.ctb.mobile.cache;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.Hospital;
import com.ctb.framework.commons.redis.RedisClient;

/**
 * @ClassName: com.ctb.mobile.cache.HospitalCache
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年4月29日 下午3:11:48
 */
@Component
public class HospitalCache {
    
    @Autowired
    private RedisClient redisClient;
    
    public String[] getHospitalCodes() {
        Set<Object> set = redisClient.hKeys(CacheConstants.CACHE_HOSPITAL_HASH_KEY_PREFIX);
        if (!CollectionUtils.isEmpty(set)) {
            return set.toArray(new String[set.size()]);
        }
        return null;
    }
    
    /**
     * 
     * @Title: getHospital
     * @Description: 获取医院缓存信息
     * @author hhy
     * @date 2019年4月29日 下午3:27:03
     * @param hospitalCode
     */
    public Hospital getHospital(String hospitalCode) {
        Hospital hospital = null;
        if (StringUtils.isNotBlank(hospitalCode)) {
            String cacheKey = getHospitalCacheKey();
            JSONObject json = (JSONObject) redisClient.hget(cacheKey, hospitalCode);
            if (json != null && StringUtils.isNotBlank(json.toJSONString())) {
                hospital = JSON.parseObject(json.toJSONString(), Hospital.class);
            }
        }
        return hospital;
    }
    
    /**
     * 
     * @Title: updateHospital
     * @Description: 更新医院缓存信息
     * @author hhy
     * @date 2019年4月29日 下午3:24:18
     * @param hospital
     */
    public void updateHospital(Hospital hospital) {
        String cacheKey = getHospitalCacheKey();
        if (hospital != null && StringUtils.isNotBlank(hospital.getCode())) {
            redisClient.hset(cacheKey, hospital.getCode(), hospital);
        }
    }
    
    /**
     * 
     * @Title: getHospitalCacheKey
     * @Description: TODO获取医院缓存key
     * @author hhy
     * @date 2019年4月29日 下午3:14:09
     * @return
     */
    public String getHospitalCacheKey() {
        return CacheConstants.CACHE_HOSPITAL_HASH_KEY_PREFIX;
    }
    
}
