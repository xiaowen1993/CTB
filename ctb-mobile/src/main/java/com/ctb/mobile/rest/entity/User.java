package com.ctb.mobile.rest.entity;

import javax.persistence.*;

@Table(name = "user")
public class User {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 患者唯一标识
     */
    @Column(name = "UNION_ID")
    private String unionId;

    /**
     * AI处方公众号openid
     */
    @Column(name = "OPEN_ID")
    private String openId;

    /**
     * 1-关注 ,0已取消关注
     */
    @Column(name = "STATUS")
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
     * 获取患者唯一标识
     *
     * @return UNION_ID - 患者唯一标识
     */
    public String getUnionId() {
        return unionId;
    }

    /**
     * 设置患者唯一标识
     *
     * @param unionId 患者唯一标识
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
     * 获取1-关注 ,0已取消关注
     *
     * @return STATUS - 1-关注 ,0已取消关注
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置1-关注 ,0已取消关注
     *
     * @param status 1-关注 ,0已取消关注
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}