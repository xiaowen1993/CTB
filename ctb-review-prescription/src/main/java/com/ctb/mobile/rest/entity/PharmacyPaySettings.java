package com.ctb.mobile.rest.entity;

import javax.persistence.*;

@Table(name = "sys_pharmacy_pay_settings")
public class PharmacyPaySettings {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 公众号信息表ID
     */
    @Column(name = "PAY_SETTINGS_ID")
    private String paySettingsId;

    /**
     * 药房ID
     */
    @Column(name = "PHARMACY_ID")
    private String pharmacyId;

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
     * 获取公众号信息表ID
     *
     * @return PAY_SETTINGS_ID - 公众号信息表ID
     */
    public String getPaySettingsId() {
        return paySettingsId;
    }

    /**
     * 设置公众号信息表ID
     *
     * @param paySettingsId 公众号信息表ID
     */
    public void setPaySettingsId(String paySettingsId) {
        this.paySettingsId = paySettingsId == null ? null : paySettingsId.trim();
    }

    /**
     * 获取药房ID
     *
     * @return PHARMACY_ID - 药房ID
     */
    public String getPharmacyId() {
        return pharmacyId;
    }

    /**
     * 设置药房ID
     *
     * @param pharmacyId 药房ID
     */
    public void setPharmacyId(String pharmacyId) {
        this.pharmacyId = pharmacyId == null ? null : pharmacyId.trim();
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
}