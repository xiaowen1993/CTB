/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月30日
 * Created by hhy
 */
package com.ctb.pharmacy.cache;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.BizConstant;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.DataPayMzFee;
import com.ctb.commons.entity.DataPayMzFeeDetail;
import com.ctb.framework.commons.redis.RedisClient;

/**
 * @ClassName: com.ctb.mobile.cache.DataPayMzFeeCache
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年4月30日 上午11:37:18
 */

@Component
public class DataPayMzFeeCache {
    
    @Autowired
    private RedisClient redisClient;
    
    /**
     * 
     * @Title: getDataPayMzFee
     * @Description: 获取待缴费列表
     * @author hhy
     * @date 2019年4月30日 上午11:54:26
     * @param hospitalCode
     * @param cardNo
     * @param mzFeeId
     * @return
     */
    public DataPayMzFee getDataPayMzFee(String hospitalCode, String cardNo, String mzFeeId) {
        DataPayMzFee dataPayMzFee = null;
        String cacheKey = getDataPayMzFeeCacheKey();
        String itemKey = hospitalCode + CacheConstants.CACHE_KEY_SPLIT_CHAR + cardNo
                + CacheConstants.CACHE_KEY_SPLIT_CHAR + mzFeeId;
        
        JSONObject json = (JSONObject) redisClient.hget(cacheKey, itemKey);
        if (json != null) {
            List<DataPayMzFeeDetail> dataPayMzFeeDetails = JSONArray.parseArray(JSONObject.toJSONString(json.get("dataPayMzFeeDetails")), DataPayMzFeeDetail.class);
            dataPayMzFee = JSONObject.toJavaObject(json, DataPayMzFee.class);
            dataPayMzFee.setDataPayMzFeeDetails(dataPayMzFeeDetails);
        }
        return dataPayMzFee;
    }    
    
    /**
     * 
     * @Title: updateDataPayMzFee
     * @Description: 添加/修改待缴费列表缓存
     * @author hhy
     * @date 2019年4月30日 上午11:46:18
     * @param dataPayMzFee
     */
    public void updateDataPayMzFee(DataPayMzFee dataPayMzFee) {
        if (dataPayMzFee != null && StringUtils.isNotBlank(dataPayMzFee.getHospitalCode())
                && StringUtils.isNotBlank(dataPayMzFee.getCardNo())
                && StringUtils.isNotBlank(dataPayMzFee.getMzFeeId())) {
            
            String cacheKey = getDataPayMzFeeCacheKey();
            String itemKey = dataPayMzFee.getHospitalCode() + CacheConstants.CACHE_KEY_SPLIT_CHAR
                    + dataPayMzFee.getCardNo() + CacheConstants.CACHE_KEY_SPLIT_CHAR + dataPayMzFee.getMzFeeId();
            redisClient.hset(cacheKey, itemKey, dataPayMzFee, BizConstant.PRESCRIPTION_RECORD_EXPIRES_TIME);
        
        }
    }
    
    /**
     * 
     * @Title: getDataPayMzFeeCacheKey
     * @Description: TODO获取待缴费列表缓存key
     * @author hhy
     * @date 2019年4月30日 上午11:38:45
     * @return
     */
    public String getDataPayMzFeeCacheKey() {
        return CacheConstants.CACHE_HOSPITAL_CARDNO_MZFEE_HASH_KEY_PREFIX;
    }
    
}
