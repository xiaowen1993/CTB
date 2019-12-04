package com.ctb.mobile.rest.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_pharmacy")
public class Pharmacy {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 药房名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 药房编号
     */
    @Column(name = "CODE")
    private String code;

    /**
     * 默认1是正常状态
     */
    @Column(name = "STATUS")
    private String status;

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

    @Column(name = "CP")
    private String cp;

    @Column(name = "CT")
    private Date ct;

    @Column(name = "EP")
    private String ep;

    @Column(name = "ET")
    private Date et;

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
     * 获取药房名称
     *
     * @return NAME - 药房名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置药房名称
     *
     * @param name 药房名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取药房编号
     *
     * @return CODE - 药房编号
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置药房编号
     *
     * @param code 药房编号
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取默认1是正常状态
     *
     * @return STATUS - 默认1是正常状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置默认1是正常状态
     *
     * @param status 默认1是正常状态
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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
}