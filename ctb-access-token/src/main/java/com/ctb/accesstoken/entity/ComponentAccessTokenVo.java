package com.ctb.accesstoken.entity;

import java.io.Serializable;

/**
 * 
 * @ClassName: com.ctb.framework.commons.sdk.AccessTokenVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月3日 下午2:29:06
 */
public class ComponentAccessTokenVo implements Serializable{
    
    
    /**
     *
     */
    	
    private static final long serialVersionUID = -2199193847965307919L;
    
    private long getTime;
    private String expiresTime;
    private String componentAccessToken;
    
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
    
    public String getComponentAccessToken() {
        return componentAccessToken;
    }
    
    public void setComponentAccessToken(String componentAccessToken) {
        this.componentAccessToken = componentAccessToken;
    }
    
    public ComponentAccessTokenVo(long getTime, String expiresTime, String componentAccessToken) {
        super();
        this.getTime = getTime;
        this.expiresTime = expiresTime;
        this.componentAccessToken = componentAccessToken;
    }
    
    public ComponentAccessTokenVo() {
        super();
    }
    
}
