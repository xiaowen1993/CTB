package com.ctb.commons.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_branch_pharmacy")
public class BranchPharmacy implements Serializable {
    
    /**
     *
     */
    
    private static final long serialVersionUID = 7191110466911782268L;
    
    @Id
    @Column(name = "ID")
    private String id;
    
    @Column(name = "PHARMACY_ID")
    private String pharmacyId;
    
    /*
     * @Column(name = "PHARMACY_CODE") private String pharmacyCode;
     */
    
    @Column(name = "CODE")
    private String code;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "ADDRESS")
    private String address;
    
    @Column(name = "CONTACT_NAME")
    private String contactName;
    
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
     * @return CODE
     */
    public String getCode() {
        return code;
    }
    
    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }
    
    /**
     * @return NAME
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
    /**
     * @return ADDRESS
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
    
    /**
     * @return CONTACT_NAME
     */
    public String getContactName() {
        return contactName;
    }
    
    /**
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }
    
    /**
     * @return CONTACT_TEL
     */
    public String getContactTel() {
        return contactTel;
    }
    
    /**
     * @param contactTel
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
     * @param latitude
     *            分店纬度
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
     * @param longitude
     *            分店经度
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
     * @param businessHours
     *            营业时间
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
        this.status = status;
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
    
    public BranchPharmacy() {
        super();
    }
    
    public BranchPharmacy(String pharmacyId, String code, String name, String address, String status) {
        super();
        this.pharmacyId = pharmacyId;
        this.code = code;
        this.name = name;
        this.address = address;
        this.status = status;
    }
    
    public BranchPharmacy(String id) {
        super();
        this.id = id;
    }
    
    public BranchPharmacy(String code, String name, String status) {
        super();
        this.code = code;
        this.name = name;
        this.status = status;
    }
    
    public BranchPharmacy(String pharmacyId, String code, String name, String status) {
        super();
        this.pharmacyId = pharmacyId;
        this.code = code;
        this.name = name;
        this.status = status;
    }
    
    public BranchPharmacy(String id, String pharmacyId, String code, String name, String address, String contactName,
            String contactTel, String latitude, String longitude, String businessHours) {
        super();
        this.id = id;
        this.pharmacyId = pharmacyId;
        this.code = code;
        this.name = name;
        this.address = address;
        this.contactTel = contactTel;
        this.contactName = contactName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.businessHours = businessHours;
    }
    
    /*
     * public BranchPharmacy(String id, String pharmacyId, String pharmacyCode, String code, String name, String
     * address, String contactName, String contactTel, String latitude, String longitude, String businessHours) {
     * super(); this.id = id; this.pharmacyId = pharmacyId; // this.pharmacyCode = pharmacyCode; this.code = code;
     * this.name = name; this.address = address; this.contactName = contactName; this.contactTel = contactTel;
     * this.latitude = latitude; this.longitude = longitude; this.businessHours = businessHours; }
     */
    
    public BranchPharmacy(String id, String status) {
        super();
        this.id = id;
        this.status = status;
    }
    
    /*
     * public String getPharmacyCode() { return pharmacyCode; }
     * 
     * public void setPharmacyCode(String pharmacyCode) { this.pharmacyCode = pharmacyCode; }
     */
    
}