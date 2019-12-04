package com.ctb.mobile.rest.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_branch_pharmacy")
public class BranchPharmacy {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 连锁药店总店ID
     */
    @Column(name = "PHARMACY_ID")
    private String pharmacyId;

    /**
     * 药店分店编码
     */
    @Column(name = "CODE")
    private String code;

    /**
     * 药店分店名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 药店地址
     */
    @Column(name = "ADDRESS")
    private String address;

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

    /**
     * 分店纬度
     */
    @Column(name = "LATITUDE")
    private String latitude;

    /**
     * 分店经度
     */
    @Column(name = "LONGITUDE")
    private String longitude;

    /**
     * 营业时间
     */
    @Column(name = "BUSINESS_HOURS")
    private String businessHours;

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
     * 获取连锁药店总店ID
     *
     * @return PHARMACY_ID - 连锁药店总店ID
     */
    public String getPharmacyId() {
        return pharmacyId;
    }

    /**
     * 设置连锁药店总店ID
     *
     * @param pharmacyId 连锁药店总店ID
     */
    public void setPharmacyId(String pharmacyId) {
        this.pharmacyId = pharmacyId == null ? null : pharmacyId.trim();
    }

    /**
     * 获取药店分店编码
     *
     * @return CODE - 药店分店编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置药店分店编码
     *
     * @param code 药店分店编码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取药店分店名称
     *
     * @return NAME - 药店分店名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置药店分店名称
     *
     * @param name 药店分店名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取药店地址
     *
     * @return ADDRESS - 药店地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置药店地址
     *
     * @param address 药店地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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
     * 获取分店纬度
     *
     * @return LATITUDE - 分店纬度
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * 设置分店纬度
     *
     * @param latitude 分店纬度
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    /**
     * 获取分店经度
     *
     * @return LONGITUDE - 分店经度
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * 设置分店经度
     *
     * @param longitude 分店经度
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    /**
     * 获取营业时间
     *
     * @return BUSINESS_HOURS - 营业时间
     */
    public String getBusinessHours() {
        return businessHours;
    }

    /**
     * 设置营业时间
     *
     * @param businessHours 营业时间
     */
    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours == null ? null : businessHours.trim();
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
}