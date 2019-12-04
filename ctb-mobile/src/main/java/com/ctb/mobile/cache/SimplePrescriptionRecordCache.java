/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月30日
 * Created by hhy
 */
package com.ctb.mobile.cache;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.BizConstant;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.PrescriptionRecord;
import com.ctb.commons.entity.SimplePrescriptionRecord;
import com.ctb.framework.commons.redis.RedisClient;

/**
 * @ClassName: com.ctb.mobile.cache.SimplePrescriptionRecordCache
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年4月30日 上午9:05:34
 */

@Component
public class SimplePrescriptionRecordCache {
    
    @Autowired
    private RedisClient redisClient;
    
    @Autowired
    private PrescriptionRecordCache prescriptionRecordCache;
    
    public SimplePrescriptionRecord getSimplePrescriptionRecord(String hospitalCode, String mzFeeId) {
        SimplePrescriptionRecord simplePrescriptionRecord = null;
        String cacheKey = getSimplePrescriptionRecordCacheKey();
        String itemKey = hospitalCode + CacheConstants.CACHE_KEY_SPLIT_CHAR + mzFeeId;
        JSONObject json = (JSONObject) redisClient.hget(cacheKey, itemKey);
        if (json != null && StringUtils.isNotBlank(json.toJSONString())) {
            simplePrescriptionRecord = JSONObject.toJavaObject(json, SimplePrescriptionRecord.class);
        }
        return simplePrescriptionRecord;
    }
    
    /**
     * 
     * @Title: updateSimplePrescriptionRecord
     * @Description: TODO 新增/更新 简易订单缓存
     * @author hhy
     * @date 2019年4月30日 上午9:16:01
     * @param prescriptionRecord
     */
    public void updateSimplePrescriptionRecord(PrescriptionRecord prescriptionRecord) {

        String cacheKey = getSimplePrescriptionRecordCacheKey();
        if (prescriptionRecord != null && StringUtils.isNotBlank(prescriptionRecord.getHospitalCode())
                && StringUtils.isNotBlank(prescriptionRecord.getMzFeeId())) {
           
            SimplePrescriptionRecord simplePrescriptionRecord = new SimplePrescriptionRecord();
            BeanUtils.copyProperties(prescriptionRecord, simplePrescriptionRecord);
            String itemKey = prescriptionRecord.getHospitalCode() + CacheConstants.CACHE_KEY_SPLIT_CHAR + prescriptionRecord.getMzFeeId(); 
            redisClient.hset(cacheKey, itemKey, simplePrescriptionRecord, BizConstant.SIMPLE_PRESCRIPTION_RECORD_EXPIRES_TIME);
            
        }
        
    }
    
    /**
     * 
     * @Title: removeSimplePrescriptionRecord
     * @Description: TODO删除简易订单缓存
     * @author hhy
     * @date 2019年4月30日 上午9:14:45
     * @param orderNo
     */
    public void removeSimplePrescriptionRecord(String orderNo) {
        PrescriptionRecord record = prescriptionRecordCache.getPrescriptionRecordByOrderNo(orderNo);
        if(record!=null) {
            String cacheKey = getSimplePrescriptionRecordCacheKey();
            String itemKey = record.getHospitalCode() + CacheConstants.CACHE_KEY_SPLIT_CHAR + record.getMzFeeId();
            redisClient.hdel(cacheKey, itemKey);
        }
    }
    
    /**
     * 
     * @Title: getSimplePrescriptionRecordCacheKey
     * @Description: TODO获取简易订单缓存key
     * @author hhy
     * @date 2019年4月30日 上午9:10:21
     * @return
     */
    public String getSimplePrescriptionRecordCacheKey() {
       return CacheConstants.SIMPLE_PRESCRIPTION_RECORD_HASH_KEY_PREFIX;
    }
    
    
}
