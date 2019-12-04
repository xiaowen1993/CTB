/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月10日
 * Created by cwq
 */
package com.ctb.mobile.datastorage.runnable;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.DataPayMzFee;
import com.ctb.commons.entity.DataPayMzFeeDetail;
import com.ctb.commons.entity.PrescriptionRecord;
import com.ctb.framework.commons.redis.RedisClient;
import com.ctb.framework.commons.utils.SpringContextHolder;
import com.ctb.mobile.cache.DataPayMzFeeCache;
import com.ctb.mobile.rest.service.DataPayMZFeeService;

/**
 * @ClassName: com.ctb.mobile.datastorage.runnable.PaidMzFeeDetailRunnable
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月10日 下午12:11:41
 */

public class PayMZFeeRunnable implements Runnable {
    
    private Logger logger = LoggerFactory.getLogger(PayMZFeeRunnable.class);
    
    private RedisClient redisClient = SpringContextHolder.getBean(RedisClient.class);
    
    private DataPayMzFeeCache dataPayMzFeeCache = SpringContextHolder.getBean(DataPayMzFeeCache.class);
    
    private DataPayMZFeeService dataPayMZFeeService = SpringContextHolder.getBean(DataPayMZFeeService.class);
    
    private PrescriptionRecord record = null;
    
    private boolean isHaveRecord = false;
    
    public PayMZFeeRunnable() {
        super();
    }
    
    public PayMZFeeRunnable(PrescriptionRecord record, boolean isHaveRecord) {
        super();
        this.record = record;
        this.isHaveRecord = isHaveRecord;
    }
    
    @Override
    public void run() {
        
        DataPayMzFee dataPayMzFees = null;
        String cacheKey = record.getHospitalCode() + CacheConstants.CACHE_KEY_SPLIT_CHAR + record.getCardNo()
                + CacheConstants.CACHE_KEY_SPLIT_CHAR + record.getMzFeeId();
        
        if (isHaveRecord) {// 需要重新去拿并入库
            // 金额不同 或者之前缓存中不存在该门诊订单 则重新查。
            dataPayMZFeeService.getDataPayMzFeeListByYX(
                    new String[] { record.getHospitalCode() }, record.getUnionId(), record.getOpenId(),
                    record.getCardNo());

            dataPayMzFees = dataPayMzFeeCache.getDataPayMzFee(record.getHospitalCode(), record.getCardNo(), record.getMzFeeId());
            int res = dataPayMZFeeService.saveDataPayMzFeeAndDataPayMzFeeDetails(dataPayMzFees, dataPayMzFees.getDataPayMzFeeDetails());
            if (res == 0) {// 插入有异常 删除缓存
                redisClient.hdel(CacheConstants.CACHE_HOSPITAL_CARDNO_MZFEE_HASH_KEY_PREFIX, cacheKey);
            }
        }
        
    }
    
}
