package com.ctb.mobile.rest.entity;

import javax.persistence.*;

@Table(name = "sys_hospital_pharmacy_settings")
public class HospitalPharmacySettings {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * ҽԺID
     */
    @Column(name = "HOSPITAL_ID")
    private String hospitalId;

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
     * ��ȡҽԺID
     *
     * @return HOSPITAL_ID - ҽԺID
     */
    public String getHospitalId() {
        return hospitalId;
    }

    /**
     * ����ҽԺID
     *
     * @param hospitalId ҽԺID
     */
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId == null ? null : hospitalId.trim();
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