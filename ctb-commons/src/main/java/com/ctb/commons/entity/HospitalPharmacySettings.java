package com.ctb.commons.entity;

import java.io.Serializable;

import javax.persistence.*;

@Table(name = "sys_hospital_pharmacy_settings")
public class HospitalPharmacySettings implements Serializable{
    
    /**
     *
     */
    	
    private static final long serialVersionUID = -7208799648736819360L;

    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 医院ID
     */
    @Column(name = "HOSPITAL_ID")
    private String hospitalId;
    
    /**
     * 医院CODE
     */
    /*
     * @Column(name = "HOSPITAL_CODE") private String hospitalCode;
     */

    /**
     * 药房ID
     */
    @Column(name = "PHARMACY_ID")
    private String pharmacyId;
    
    /**
     * 药房CODE
     */
    /*
     * @Column(name = "PHARMACY_CODE") private String pharmacyCode;
     */

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
     * 获取医院ID
     *
     * @return HOSPITAL_ID - 医院ID
     */
    public String getHospitalId() {
        return hospitalId;
    }

    /**
     * 设置医院ID
     *
     * @param hospitalId 医院ID
     */
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId == null ? null : hospitalId.trim();
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
        this.status = status;
    }

    /*
     * public String getHospitalCode() { return hospitalCode; }
     * 
     * public void setHospitalCode(String hospitalCode) { this.hospitalCode = hospitalCode; }
     * 
     * public String getPharmacyCode() { return pharmacyCode; }
     * 
     * public void setPharmacyCode(String pharmacyCode) { this.pharmacyCode = pharmacyCode; }
     */
      
}