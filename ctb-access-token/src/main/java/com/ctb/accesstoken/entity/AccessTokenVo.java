package com.ctb.accesstoken.entity;

import java.io.Serializable;

/**
 * 
 * @ClassName: com.ctb.framework.commons.sdk.AccessTokenVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月3日 下午2:29:06
 */
public class AccessTokenVo implements Serializable{
    
    
    /**
     *
     */
    	
    private static final long serialVersionUID = 2525619698932640570L;
    
    private long getTime;
    private String expiresTime;
    private String accessToken;
    
    public long getGetTime() {
        return getTime;
    }
    
    public void setGetTime(long getTime) {
        this.getTime = getTime;
    }
    
    public String getExpiresTime() {
        return expiresTime;
    }
    
    public void setExpiresTime(String expiresTime) {
        this.expiresTime = expiresTime;
    }
    
    public String getAccessToken() {
        return accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public AccessTokenVo(long getTime, String expiresTime, String accessToken) {
        super();
        this.getTime = getTime;
        this.expiresTime = expiresTime;
        this.accessToken = accessToken;
    }
    
    public AccessTokenVo() {
        super();
    }
    
}
