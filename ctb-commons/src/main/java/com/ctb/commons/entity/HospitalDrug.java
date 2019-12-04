package com.ctb.commons.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "sys_hospital_drug")
public class HospitalDrug implements Serializable{

	  /**
     * 编号
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 医院编号
     */
    @Column(name = "HOSPITAL_ID")
    private String hospitalId;
    
    
    /**
     * 药品编号
     */
    @Column(name = "DRUG_ID")
    private String drugId;
    
    /**
  *HIS自编药品编码
     */
    @Column(name = "HIS_DRUG_CODE")
    private String hisDrugCode;
    
    /**
     *  类型
     */
    @Column(name = "TYPE")
    private String type;
    
    /**
     * 医院名称
     * @return
     */
    private String hospitalName;
    
    /**
     * 药品名称
     * @return
     */
    private String drugName;
    
    /**
     * 是否启用黑白名单
     */
    @Column(name = "STATUS")
    private String status;
    
    /**
     * 是否属于特殊药品
     */
    @Column(name = "SPECIAL_DRUGS")
    private String specialDrugs;
    
    /**
     * 是否开启特殊药品
     */
    @Column(name = "SPECIAL_STATUS")
    private String specialStatus;
    
    
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getDrugId() {
		return drugId;
	}

	public void setDrugId(String drugId) {
		this.drugId = drugId;
	}

	public String getHisDrugCode() {
		return hisDrugCode;
	}

	public void setHisDrugCode(String hisDrugCode) {
		this.hisDrugCode = hisDrugCode == null ? null : hisDrugCode.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSpecialDrugs() {
		return specialDrugs;
	}

	public void setSpecialDrugs(String specialDrugs) {
		this.specialDrugs = specialDrugs;
	}

	public String getSpecialStatus() {
		return specialStatus;
	}

	public void setSpecialStatus(String specialStatus) {
		this.specialStatus = specialStatus;
	}
}
