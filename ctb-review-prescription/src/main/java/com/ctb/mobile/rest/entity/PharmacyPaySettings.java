package com.ctb.mobile.rest.entity;

import javax.persistence.*;

@Table(name = "sys_pharmacy_pay_settings")
public class PharmacyPaySettings {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * ���ں���Ϣ��ID
     */
    @Column(name = "PAY_SETTINGS_ID")
    private String paySettingsId;

    /**
     * ҩ��ID
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
     * ��ȡ���ں���Ϣ��ID
     *
     * @return PAY_SETTINGS_ID - ���ں���Ϣ��ID
     */
    public String getPaySettingsId() {
        return paySettingsId;
    }

    /**
     * ���ù��ں���Ϣ��ID
     *
     * @param paySettingsId ���ں���Ϣ��ID
     */
    public void setPaySettingsId(String paySettingsId) {
        this.paySettingsId = paySettingsId == null ? null : paySettingsId.trim();
    }

    /**
     * ��ȡҩ��ID
     *
     * @return PHARMACY_ID - ҩ��ID
     */
    public String getPharmacyId() {
        return pharmacyId;
    }

    /**
     * ����ҩ��ID
     *
     * @param pharmacyId ҩ��ID
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