/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月29日
 * Created by cwq
 */
package com.ctb.review.prescription.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.BizConstant;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.PrescriptionRecord;
import com.ctb.framework.commons.redis.RedisClient;

/**
 * @ClassName: com.ctb.mobile.cache.PrescriptionRecordCache
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月29日 上午10:20:03
 */
@Component
public class PrescriptionRecordCache {
    
    @Autowired
    private RedisClient redisClient;
    
    @Autowired
    private SimplePrescriptionRecordCache simplePrescriptionRecordCache;
    
    /**
     * 
     * @Title: getPrescriptionRecordByOrderNo
     * @Description: TODO(根据OrderNo获取订单缓存)
     * @author cwq
     * @date 2019年4月3日 下午2:41:06
     * @param OrderNo
     * @return
     */
    public PrescriptionRecord getPrescriptionRecordByOrderNo(String orderNo) {
        
        PrescriptionRecord prescriptionRecord = null;
        if (StringUtils.isNotBlank(orderNo)) {
            String cacheKey = getPrescriptionRecordCacheKey();
            JSONObject val = (JSONObject) redisClient.hget(cacheKey, orderNo);
            
            if (val!=null) {
                prescriptionRecord = JSONObject.toJavaObject(val, PrescriptionRecord.class);
            }
        }
        
        return prescriptionRecord;
    }
    
    /**
     * 
     * @Title: updatePrescriptionRecord
     * @Description: TODO( 更新订单缓存)
     * @author cwq
     * @date 2019年4月3日 下午2:42:02
     * @param appId
     * @param accessToken
     */
    public void updatePrescriptionRecord(PrescriptionRecord prescriptionRecord) {
        
        String cacheKey = getPrescriptionRecordCacheKey();
        
        if (prescriptionRecord != null && StringUtils.isNotBlank(prescriptionRecord.getOrderNo())) {
            
            redisClient.hset(cacheKey, prescriptionRecord.getOrderNo(), prescriptionRecord, BizConstant.PRESCRIPTION_RECORD_EXPIRES_TIME);
            //缓存简易订单
            simplePrescriptionRecordCache.updateSimplePrescriptionRecord(prescriptionRecord);
        }
    }
    
    /**
     * 
     * @Title: removePrescriptionRecord
     * @Description: TODO(删除订单缓存)
     * @author cwq
     * @date 2019年4月29日 上午10:46:09
     * @param OrderNo
     */
    public void removePrescriptionRecord(String OrderNo) {
        
        String cacheKey = getPrescriptionRecordCacheKey();
        
        if (StringUtils.isNotBlank(OrderNo)) {
            
            redisClient.hdel(cacheKey, OrderNo);
            
            //删除简易订单缓存
            simplePrescriptionRecordCache.removeSimplePrescriptionRecord(OrderNo);
        }
    }
    
    
    /**
     * 
     * @Title: getPrescriptionRecordCacheKey
     * @Description: TODO(得到PrescriptionRecord缓存的key)
     * @author cwq
     * @date 2019年4月3日 下午2:44:29
     * @return
     */
    public String getPrescriptionRecordCacheKey() {
        return CacheConstants.PRESCRIPTION_RECORD_HASH_KEY_PREFIX;
    }
    

}
