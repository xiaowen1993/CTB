package com.ctb.commons.entity;

import java.io.Serializable;

import javax.persistence.*;

@Table(name = "sys_pharmacy_pay_settings")
public class PharmacyPaySettings implements Serializable{
    
    /**
     *
     */
    	
    private static final long serialVersionUID = 3707082640050845425L;

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "PAY_SETTINGS_ID")
    private String paySettingsId;

    @Column(name = "PHARMACY_ID")
    private String pharmacyId;
    
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
     * @return PAY_SETTINGS_ID
     */
    public String getPaySettingsId() {
        return paySettingsId;
    }

    /**
     * @param paySettingsId
     */
    public void setPaySettingsId(String paySettingsId) {
        this.paySettingsId = paySettingsId == null ? null : paySettingsId.trim();
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

	public PharmacyPaySettings() {
		super();
	}

	public PharmacyPaySettings(String id) {
		super();
		this.id = id;
	}

	public PharmacyPaySettings(String id, String pharmacyId) {
		super();
		this.id = id;
		this.pharmacyId = pharmacyId;
	}

    public PharmacyPaySettings(String id, String paySettingsId, String pharmacyId,
            /* String pharmacyCode, */ String status) {
		super();
		this.id = id;
		this.paySettingsId = paySettingsId;
		this.pharmacyId = pharmacyId;
//		this.pharmacyCode = pharmacyCode;
		this.status = status;
	}

    /*
     * public String getPharmacyCode() { return pharmacyCode; }
     * 
     * public void setPharmacyCode(String pharmacyCode) { this.pharmacyCode = pharmacyCode; }
     */
	
}