/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月4日
 * Created by cwq
 */
package com.ctb.accesstoken.entity;

import java.io.Serializable;

/**
 * @ClassName: com.ctb.accesstoken.entity.JSTicketVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月4日 下午12:59:56
 */

public class JSTicketVo implements Serializable {
    
    /**
     *
     */
    
    private static final long serialVersionUID = -7105466211890551654L;
    
    private long getTime;
    
    private String expiresTime;
    
    private String ticket;
    
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
    
    public String getTicket() {
        return ticket;
    }
    
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
    
}
