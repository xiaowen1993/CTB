package com.ctb.commons.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Table(name = "sys_hospital")
public class Hospital implements Serializable{
    
    /**
     *
     */
    	
    private static final long serialVersionUID = -7445773231289017494L;

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CODE")
    private String code;

    @Column(name = "BRANCH_NAME")
    private String branchName;

    @Column(name = "BRANCH_CODE")
    private String branchCode;

    @Column(name = "CONTACT_NAME")
    private String contactName;

    @Column(name = "CONTACT_TEL")
    private String contactTel;

    private List <String> pharmacyIds;

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
     * @return BRANCH_NAME
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * @param branchName
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
    }

    /**
     * @return BRANCH_CODE
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * @param branchCode
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode == null ? null : branchCode.trim();
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

    public List<String> getPharmacyIds() {
        return pharmacyIds;
    }

    public void setPharmacyIds(List<String> pharmacyIds) {
        this.pharmacyIds = pharmacyIds;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
    
    public  Hospital(String name, String code, String contactName, String contactTel) {
        super();
        this.name = name;
        this.code = code;
        this.contactName = contactName;
        this.contactTel = contactTel;
    }

	public Hospital() {
		// TODO Auto-generated constructor stub
		super();
	}
    
}