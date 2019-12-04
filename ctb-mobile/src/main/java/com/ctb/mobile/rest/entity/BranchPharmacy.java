package com.ctb.mobile.rest.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_branch_pharmacy")
public class BranchPharmacy {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * ����ҩ���ܵ�ID
     */
    @Column(name = "PHARMACY_ID")
    private String pharmacyId;

    /**
     * ҩ��ֵ����
     */
    @Column(name = "CODE")
    private String code;

    /**
     * ҩ��ֵ�����
     */
    @Column(name = "NAME")
    private String name;

    /**
     * ҩ���ַ
     */
    @Column(name = "ADDRESS")
    private String address;

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

    /**
     * �ֵ�γ��
     */
    @Column(name = "LATITUDE")
    private String latitude;

    /**
     * �ֵ꾭��
     */
    @Column(name = "LONGITUDE")
    private String longitude;

    /**
     * Ӫҵʱ��
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
     * ��ȡ����ҩ���ܵ�ID
     *
     * @return PHARMACY_ID - ����ҩ���ܵ�ID
     */
    public String getPharmacyId() {
        return pharmacyId;
    }

    /**
     * ��������ҩ���ܵ�ID
     *
     * @param pharmacyId ����ҩ���ܵ�ID
     */
    public void setPharmacyId(String pharmacyId) {
        this.pharmacyId = pharmacyId == null ? null : pharmacyId.trim();
    }

    /**
     * ��ȡҩ��ֵ����
     *
     * @return CODE - ҩ��ֵ����
     */
    public String getCode() {
        return code;
    }

    /**
     * ����ҩ��ֵ����
     *
     * @param code ҩ��ֵ����
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * ��ȡҩ��ֵ�����
     *
     * @return NAME - ҩ��ֵ�����
     */
    public String getName() {
        return name;
    }

    /**
     * ����ҩ��ֵ�����
     *
     * @param name ҩ��ֵ�����
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * ��ȡҩ���ַ
     *
     * @return ADDRESS - ҩ���ַ
     */
    public String getAddress() {
        return address;
    }

    /**
     * ����ҩ���ַ
     *
     * @param address ҩ���ַ
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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
     * ��ȡ�ֵ�γ��
     *
     * @return LATITUDE - �ֵ�γ��
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * ���÷ֵ�γ��
     *
     * @param latitude �ֵ�γ��
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    /**
     * ��ȡ�ֵ꾭��
     *
     * @return LONGITUDE - �ֵ꾭��
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * ���÷ֵ꾭��
     *
     * @param longitude �ֵ꾭��
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    /**
     * ��ȡӪҵʱ��
     *
     * @return BUSINESS_HOURS - Ӫҵʱ��
     */
    public String getBusinessHours() {
        return businessHours;
    }

    /**
     * ����Ӫҵʱ��
     *
     * @param businessHours Ӫҵʱ��
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