package com.ctb.accesstoken.cache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ctb.accesstoken.constants.AccesstokenConstants;
import com.ctb.framework.commons.redis.RedisClient;

/**
 * 
 * @ClassName: com.ctb.mobile.rest.utils.ComponentVerifyTicketCacheUtil
 * @Description: TODO(第三方平台component_verify_ticket缓存工具类)
 * @author cwq
 * @date 2019年4月3日 下午7:43:27
 */
@Component
public class ComponentVerifyTicketCache {
    
    @Autowired
    private RedisClient redisClient;
    
    public String getComponentVerifyTicketByAppId(String componentAppId) {
        
        String componentVerifyTicket = "";
        if (StringUtils.isNotBlank(componentAppId)) {
            String cacheKey = getComponentVerifyTicketCacheKey();
            String val = (String) redisClient.hget(cacheKey, componentAppId);
            
            if (StringUtils.isNotBlank(val)) {
                componentVerifyTicket = val;
            }
        }
        
        return componentVerifyTicket;
    }
    
    /**
     * 更新component token
     */
    public void updateComponentVerifyTicket(String componentAppId, String componentVerifyTicket) {
        
        String cacheKey = getComponentVerifyTicketCacheKey();
        if (StringUtils.isNotBlank(componentAppId) && StringUtils.isNotBlank(componentVerifyTicket)) {
             redisClient.hset(cacheKey, componentAppId, componentVerifyTicket);
        }
        
    }
    
    /**
     * 得到component token缓存的key
     * 
     * @return
     */
    public String getComponentVerifyTicketCacheKey() {
        return AccesstokenConstants.CACHE_WECHAT_COMPONENT_VERIFY_TICKET_KEY_PREFIX;
    }
    
}
