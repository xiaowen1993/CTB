/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月24日
 * Created by ckm
 */
package com.ctb.mobile.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ctb.framework.commons.redis.RedisClient;

/**
 * 
 * @ClassName: com.ctb.mobile.cache.ReInvokeLockCache
 * @Description: TODO(支付回调防止重复调用)
 * @author cwq
 * @date 2019年4月29日 上午11:30:31
 */
@Component
public class ReInvokeLockCache {
    
    // private static Logger logger = LoggerFactory.getLogger(ReInvokeLockCache.class);
    
    public static final String PAY_CALLBACK_LOCK = "pay.callback.lock";
    
    public static final String CACHE_KEY_SPLIT_CHAR = ":";
    
    @Autowired
    private RedisClient redisClient;
    
    private final int EXPIRE_TIME = 1800;
    
    public ReInvokeLockCache() {
        super();
    }
    
    /**
     * 支付回调加锁 false 已经上锁[上锁失败] true 未上锁[上锁成功]
     */
    public boolean getLock(String orderNo) {
        
        String key = getCallbackLockKey(orderNo);
        
        return redisClient.setIfAbsent(key, orderNo, EXPIRE_TIME);
    }
    
    /**
     * 支付回调解锁
     */
    public void delLock(String orderNo) {
        redisClient.del(getCallbackLockKey(orderNo));
    }
    
    private String getCallbackLockKey(String orderNo) {
        return PAY_CALLBACK_LOCK.concat(CACHE_KEY_SPLIT_CHAR).concat(orderNo);
    }
    
}
