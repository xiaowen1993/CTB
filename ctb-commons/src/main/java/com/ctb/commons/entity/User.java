package com.ctb.commons.entity;

import java.io.Serializable;

import javax.persistence.*;

@Table(name = "user")
public class User implements Serializable{
    
    /**
     *
     */
    	
    private static final long serialVersionUID = -3146537158027312286L;

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "UNION_ID")
    private String unionId;

    /**
     * AI处方公众号openid
     */
    @Column(name = "OPEN_ID")
    private String openId;

    /**
     * 关注状态 1-已关注，0-已取消关注
     */
    @Column(name = "STATUS" )
    private String status;
    
    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return UNION_ID
     */
    public String getUnionId() {
        return unionId;
    }

    /**
     * @param unionId
     */
    public void setUnionId(String unionId) {
        this.unionId = unionId == null ? null : unionId.trim();
    }

    /**
     * 获取AI处方公众号openid
     *
     * @return OPEN_ID - AI处方公众号openid
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置AI处方公众号openid
     *
     * @param openId AI处方公众号openid
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * @return STATUS
     */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
    
    
}