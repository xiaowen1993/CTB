/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月4日
 * Created by cwq
 */
package com.ctb.accesstoken.cache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.ctb.accesstoken.constants.AccesstokenConstants;
import com.ctb.accesstoken.entity.JSTicketVo;
import com.ctb.framework.commons.redis.RedisClient;

/**
 * @ClassName: com.ctb.accesstoken.cache.JSTicketCache
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月4日 下午12:51:21
 */
@Component
public class JSTicketCache {
    @Autowired
    private RedisClient redisClient;
    
    /**
     * 
     * @Title: getJSTicket
     * @Description: TODO(根据APPID获取)
     * @author cwq
     * @date 2019年4月3日 下午2:41:06
     * @param appId
     * @return
     */
    public JSTicketVo getJSTicket(String appId) {
        
        JSTicketVo jsTicketVo = null;
        if (StringUtils.isNotBlank(appId)) {
            String cacheKey = getJSTicketCacheKey();
            String val = (String) redisClient.hget(cacheKey, appId);
            
            if (StringUtils.isNotBlank(val)) {
                jsTicketVo = JSON.parseObject(val, JSTicketVo.class);
            }
        }
        
        return jsTicketVo;
    }
    
    /**
     * 
     * @Title: updateJSTicket
     * @Description: TODO( 更新JSTicket)
     * @author cwq
     * @date 2019年4月3日 下午2:42:02
     * @param appId
     * @param jsTicketVo
     */
    public void updateJSTicket(String appId, JSTicketVo jsTicketVo) {
        
        String cacheKey = getJSTicketCacheKey();
        
        if (StringUtils.isNotBlank(appId) && jsTicketVo != null
                && StringUtils.isNotBlank(jsTicketVo.getTicket())
                && StringUtils.isNotBlank(jsTicketVo.getExpiresTime())) {
            
            redisClient.hset(cacheKey, appId, JSON.toJSONString(jsTicketVo));
        }
    }
    
    /**
     * 
     * @Title: getJSTicketCacheKey
     * @Description: TODO(得到ticket缓存的key)
     * @author cwq
     * @date 2019年4月3日 下午2:44:29
     * @return
     */
    public String getJSTicketCacheKey() {
        return AccesstokenConstants.CACHE_WECHAT_JSTICKET_KEY_PREFIX;
    }
}
