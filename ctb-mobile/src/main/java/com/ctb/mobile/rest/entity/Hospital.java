package com.ctb.mobile.rest.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_hospital")
public class Hospital {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 医院名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 医院编码
     */
    @Column(name = "CODE")
    private String code;

    /**
     * 分院名称
     */
    @Column(name = "BRANCH_NAME")
    private String branchName;

    /**
     * 分院编码
     */
    @Column(name = "BRANCH_CODE")
    private String branchCode;

    /**
     * 管理员姓名
     */
    @Column(name = "CONTACT_NAME")
    private String contactName;

    /**
     * 管理员电话
     */
    @Column(name = "CONTACT_TEL")
    private String contactTel;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CP")
    private String cp;

    @Column(name = "CT")
    private Date ct;

    @Column(name = "EP")
    private String ep;

    @Column(name = "ET")
    private Date et;

    @Column(name = "APP_ID")
    private String appId;

    @Column(name = "APP_SECRET")
    private String appSecret;

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
     * 获取医院名称
     *
     * @return NAME - 医院名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置医院名称
     *
     * @param name 医院名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取医院编码
     *
     * @return CODE - 医院编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置医院编码
     *
     * @param code 医院编码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取分院名称
     *
     * @return BRANCH_NAME - 分院名称
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * 设置分院名称
     *
     * @param branchName 分院名称
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
    }

    /**
     * 获取分院编码
     *
     * @return BRANCH_CODE - 分院编码
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * 设置分院编码
     *
     * @param branchCode 分院编码
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode == null ? null : branchCode.trim();
    }

    /**
     * 获取管理员姓名
     *
     * @return CONTACT_NAME - 管理员姓名
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * 设置管理员姓名
     *
     * @param contactName 管理员姓名
     */
    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    /**
     * 获取管理员电话
     *
     * @return CONTACT_TEL - 管理员电话
     */
    public String getContactTel() {
        return contactTel;
    }

    /**
     * 设置管理员电话
     *
     * @param contactTel 管理员电话
     */
    public void setContactTel(String contactTel) {
        this.contactTel = contactTel == null ? null : contactTel.trim();
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
        this.status = status == null ? null : status.trim();
    }

    /**
     * @return CP
     */
    public String getCp() {
        return cp;
    }

    /**
     * @param cp
     */
    public void setCp(String cp) {
        this.cp = cp == null ? null : cp.trim();
    }

    /**
     * @return CT
     */
    public Date getCt() {
        return ct;
    }

    /**
     * @param ct
     */
    public void setCt(Date ct) {
        this.ct = ct;
    }

    /**
     * @return EP
     */
    public String getEp() {
        return ep;
    }

    /**
     * @param ep
     */
    public void setEp(String ep) {
        this.ep = ep == null ? null : ep.trim();
    }

    /**
     * @return ET
     */
    public Date getEt() {
        return et;
    }

    /**
     * @param et
     */
    public void setEt(Date et) {
        this.et = et;
    }

    /**
     * @return APP_ID
     */
    public String getAppId() {
        return appId;
    }

    /**
     * @param appId
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     * @return APP_SECRET
     */
    public String getAppSecret() {
        return appSecret;
    }

    /**
     * @param appSecret
     */
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret == null ? null : appSecret.trim();
    }
}