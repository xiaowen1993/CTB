package com.ctb.mobile.rest.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_hospital")
public class Hospital {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * ҽԺ����
     */
    @Column(name = "NAME")
    private String name;

    /**
     * ҽԺ����
     */
    @Column(name = "CODE")
    private String code;

    /**
     * ��Ժ����
     */
    @Column(name = "BRANCH_NAME")
    private String branchName;

    /**
     * ��Ժ����
     */
    @Column(name = "BRANCH_CODE")
    private String branchCode;

    /**
     * ����Ա����
     */
    @Column(name = "CONTACT_NAME")
    private String contactName;

    /**
     * ����Ա�绰
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
     * ��ȡҽԺ����
     *
     * @return NAME - ҽԺ����
     */
    public String getName() {
        return name;
    }

    /**
     * ����ҽԺ����
     *
     * @param name ҽԺ����
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * ��ȡҽԺ����
     *
     * @return CODE - ҽԺ����
     */
    public String getCode() {
        return code;
    }

    /**
     * ����ҽԺ����
     *
     * @param code ҽԺ����
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * ��ȡ��Ժ����
     *
     * @return BRANCH_NAME - ��Ժ����
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * ���÷�Ժ����
     *
     * @param branchName ��Ժ����
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
    }

    /**
     * ��ȡ��Ժ����
     *
     * @return BRANCH_CODE - ��Ժ����
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * ���÷�Ժ����
     *
     * @param branchCode ��Ժ����
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode == null ? null : branchCode.trim();
    }

    /**
     * ��ȡ����Ա����
     *
     * @return CONTACT_NAME - ����Ա����
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * ���ù���Ա����
     *
     * @param contactName ����Ա����
     */
    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    /**
     * ��ȡ����Ա�绰
     *
     * @return CONTACT_TEL - ����Ա�绰
     */
    public String getContactTel() {
        return contactTel;
    }

    /**
     * ���ù���Ա�绰
     *
     * @param contactTel ����Ա�绰
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