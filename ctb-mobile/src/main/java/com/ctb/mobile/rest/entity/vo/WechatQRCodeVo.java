/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月3日
 * Created by ckm
 */
package com.ctb.mobile.rest.entity.vo;

import java.io.Serializable;

/**
 * @ClassName: com.ctb.framework.commons.sdk.a
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月3日 下午6:39:57
 */
public class WechatQRCodeVo implements Serializable {
    
    /**
     *
     */
    
    private static final long serialVersionUID = -9098847248847736980L;
    
    private long getTime;
    
    private String ticket;
    
    private String expireSeconds;
    
    public long getGetTime() {
        return getTime;
    }
    
    public void setGetTime(long getTime) {
        this.getTime = getTime;
    }
    
    public String getTicket() {
        return ticket;
    }
    
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
    
    public String getExpireSeconds() {
        return expireSeconds;
    }
    
    public void setExpireSeconds(String expireSeconds) {
        this.expireSeconds = expireSeconds;
    }
    
    public WechatQRCodeVo() {
        super();
    }
    
}