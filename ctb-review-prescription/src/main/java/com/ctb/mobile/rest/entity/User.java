package com.ctb.mobile.rest.entity;

import javax.persistence.*;

@Table(name = "user")
public class User {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * ����Ψһ��ʶ
     */
    @Column(name = "UNION_ID")
    private String unionId;

    /**
     * AI�������ں�openid
     */
    @Column(name = "OPEN_ID")
    private String openId;

    /**
     * 1-��ע ,0��ȡ����ע
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
     * ��ȡ����Ψһ��ʶ
     *
     * @return UNION_ID - ����Ψһ��ʶ
     */
    public String getUnionId() {
        return unionId;
    }

    /**
     * ���û���Ψһ��ʶ
     *
     * @param unionId ����Ψһ��ʶ
     */
    public void setUnionId(String unionId) {
        this.unionId = unionId == null ? null : unionId.trim();
    }

    /**
     * ��ȡAI�������ں�openid
     *
     * @return OPEN_ID - AI�������ں�openid
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * ����AI�������ں�openid
     *
     * @param openId AI�������ں�openid
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * ��ȡ1-��ע ,0��ȡ����ע
     *
     * @return STATUS - 1-��ע ,0��ȡ����ע
     */
    public String getStatus() {
        return status;
    }

    /**
     * ����1-��ע ,0��ȡ����ע
     *
     * @param status 1-��ע ,0��ȡ����ע
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}