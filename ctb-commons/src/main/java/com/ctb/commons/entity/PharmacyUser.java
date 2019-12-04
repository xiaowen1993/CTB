package com.ctb.commons.entity;

import java.io.Serializable;

import javax.persistence.*;

@Table(name = "pharmacy_user")
public class PharmacyUser implements Serializable{
    
    /**
     *
     */
    	
    private static final long serialVersionUID = 2731414770727326066L;

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "PHARMACY_ID")
    private String pharmacyId;

    /**
     * 药店用户登录名
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 登录名密码
     */
    @Column(name = "PASSWORD")
    private String password;
    
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "STATUS")
    private String status;

    /**
     * 最后登录时间
     */
    @Column(name = "LAST_LOGIN_TIME")
    private Long lastLoginTime;

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
     * @return PHARMACY_ID
     */
    public String getPharmacyId() {
        return pharmacyId;
    }

    /**
     * @param pharmacyId
     */
    public void setPharmacyId(String pharmacyId) {
        this.pharmacyId = pharmacyId == null ? null : pharmacyId.trim();
    }

    /**
     * 获取药店用户登录名
     *
     * @return NAME - 药店用户登录名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置药店用户登录名
     *
     * @param name 药店用户登录名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取登录名密码
     *
     * @return PASSWORD - 登录名密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置登录名密码
     *
     * @param password 登录名密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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

    /**
     * 获取最后登录时间
     *
     * @return LAST_LOGIN_TIME - 最后登录时间
     */
    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置最后登录时间
     *
     * @param lastLoginTime 最后登录时间
     */
    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}