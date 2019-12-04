package com.ctb.accesstoken.cache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.ctb.accesstoken.constants.AccesstokenConstants;
import com.ctb.accesstoken.entity.AccessTokenVo;
import com.ctb.framework.commons.redis.RedisClient;

/**
 * 
 * @ClassName: com.ctb.framework.commons.sdk.AccessTokenCache
 * @Description: TODO(微信accessToken管理)
 * @author cwq
 * @date 2019年4月3日 下午2:29:27
 */
@Component
public class AccessTokenCache {
    
    @Autowired
    private RedisClient redisClient;
    
    /**
     * 
     * @Title: getTokenByAppId
     * @Description: TODO(根据APPID获取)
     * @author cwq
     * @date 2019年4月3日 下午2:41:06
     * @param appId
     * @return
     */
    public AccessTokenVo getTokenByAppId(String appId) {
        
        AccessTokenVo accessTokenVo = null;
        if (StringUtils.isNotBlank(appId)) {
            String cacheKey = getTokenCacheKey();
            String val = (String) redisClient.hget(cacheKey, appId);
            
            if (StringUtils.isNotBlank(val)) {
                accessTokenVo = JSON.parseObject(val, AccessTokenVo.class);
            }
        }
        
        return accessTokenVo;
    }
    
    /**
     * 
     * @Title: updateToken
     * @Description: TODO( 更新token)
     * @author cwq
     * @date 2019年4月3日 下午2:42:02
     * @param appId
     * @param accessToken
     */
    public void updateToken(String appId, AccessTokenVo accessTokenVo) {
        
        String cacheKey = getTokenCacheKey();
        
        if (StringUtils.isNotBlank(appId) && accessTokenVo != null
                && StringUtils.isNotBlank(accessTokenVo.getAccessToken())
                && StringUtils.isNotBlank(accessTokenVo.getExpiresTime())) {
            
            redisClient.hset(cacheKey, appId, JSON.toJSONString(accessTokenVo));
        }
    }
    
    /**
     * 
     * @Title: getTokenCacheKey
     * @Description: TODO(得到token缓存的key)
     * @author cwq
     * @date 2019年4月3日 下午2:44:29
     * @return
     */
    public String getTokenCacheKey() {
        return AccesstokenConstants.CACHE_WECHAT_TOKEN_KEY_PREFIX;
    }
    
}
