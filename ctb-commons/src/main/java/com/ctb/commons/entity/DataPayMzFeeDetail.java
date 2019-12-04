package com.ctb.commons.entity;

import java.io.Serializable;

import javax.persistence.*;

@Table(name = "data_pay_mz_fee_detail")
public class DataPayMzFeeDetail implements Serializable{
    
    /**
     *
     */
    	
    private static final long serialVersionUID = -5712449027259845756L;

    /**
     * 编号
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 门诊待缴费编号
     */
    @Column(name = "MZ_FEE_ID")
    private String mzFeeId;

    /**
     * 项目时间
     */
    @Column(name = "ITEM_TIME")
    private String itemTime;

    /**
     * 项目编号
     */
    @Column(name = "ITEM_ID")
    private String itemId;

    /**
     * 项目名称
     */
    @Column(name = "ITEM_NAME")
    private String itemName;

    /**
     * 项目费别
     */
    @Column(name = "ITEM_TYPE")
    private String itemType;

    /**
     * 项目单位
     */
    @Column(name = "ITEM_UNIT")
    private String itemUnit;

    /**
     * 项目单价
     */
    @Column(name = "ITEM_PRICE")
    private String itemPrice;

    /**
     * 项目规格
     */
    @Column(name = "ITEM_SPEC")
    private String itemSpec;

    /**
     * 项目数量
     */
    @Column(name = "ITEM_NUMBER")
    private String itemNumber;

    /**
     * 项目总价
     */
    @Column(name = "ITEM_TOTAL_FEE")
    private String itemTotalFee;

    /**
     * 科室名称
     */
    @Column(name = "DEPT_NAME")
    private String deptName;

    /**
     * 医生名称
     */
    @Column(name = "DOCTOR_NAME")
    private String doctorName;

    /**
     * 医生代码
     */
    @Column(name = "DOCTOR_CODE")
    private String doctorCode;

    /**
     * 社保统一药品或诊疗项目编码
     */
    @Column(name = "SOCIAL_INSURANCE_ITEMID")
    private String socialInsuranceItemid;

    /**
     * 医保结算项目代码
     */
    @Column(name = "MEDICARE_ITEMID")
    private String medicareItemid;

    /**
     * 通用名
     */
    @Transient
    private String cadn;
    
    /**
     * 	商品名
     */
    @Transient
    private String drugName;
    /**
     * 	规格
     */
    @Transient
    private String specification;
    
    /**
     * 获取编号
     *
     * @return ID - 编号
     */
    public String getId() {
        return id;
    }

    /**
     * 设置编号
     *
     * @param id 编号
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取门诊待缴费编号
     *
     * @return MZ_FEE_ID - 门诊待缴费编号
     */
    public String getMzFeeId() {
        return mzFeeId;
    }

    /**
     * 设置门诊待缴费编号
     *
     * @param mzFeeId 门诊待缴费编号
     */
    public void setMzFeeId(String mzFeeId) {
        this.mzFeeId = mzFeeId == null ? null : mzFeeId.trim();
    }

    /**
     * 获取项目时间
     *
     * @return ITEM_TIME - 项目时间
     */
    public String getItemTime() {
        return itemTime;
    }

    /**
     * 设置项目时间
     *
     * @param itemTime 项目时间
     */
    public void setItemTime(String itemTime) {
        this.itemTime = itemTime == null ? null : itemTime.trim();
    }

    /**
     * 获取项目编号
     *
     * @return ITEM_ID - 项目编号
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * 设置项目编号
     *
     * @param itemId 项目编号
     */
    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    /**
     * 获取项目名称
     *
     * @return ITEM_NAME - 项目名称
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * 设置项目名称
     *
     * @param itemName 项目名称
     */
    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    /**
     * 获取项目费别
     *
     * @return ITEM_TYPE - 项目费别
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * 设置项目费别
     *
     * @param itemType 项目费别
     */
    public void setItemType(String itemType) {
        this.itemType = itemType == null ? null : itemType.trim();
    }

    /**
     * 获取项目单位
     *
     * @return ITEM_UNIT - 项目单位
     */
    public String getItemUnit() {
        return itemUnit;
    }

    /**
     * 设置项目单位
     *
     * @param itemUnit 项目单位
     */
    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit == null ? null : itemUnit.trim();
    }

    /**
     * 获取项目单价
     *
     * @return ITEM_PRICE - 项目单价
     */
    public String getItemPrice() {
        return itemPrice;
    }

    /**
     * 设置项目单价
     *
     * @param itemPrice 项目单价
     */
    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice == null ? null : itemPrice.trim();
    }

    /**
     * 获取项目规格
     *
     * @return ITEM_SPEC - 项目规格
     */
    public String getItemSpec() {
        return itemSpec;
    }

    /**
     * 设置项目规格
     *
     * @param itemSpec 项目规格
     */
    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec == null ? null : itemSpec.trim();
    }

    /**
     * 获取项目数量
     *
     * @return ITEM_NUMBER - 项目数量
     */
    public String getItemNumber() {
        return itemNumber;
    }

    /**
     * 设置项目数量
     *
     * @param itemNumber 项目数量
     */
    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber == null ? null : itemNumber.trim();
    }

    /**
     * 获取项目总价
     *
     * @return ITEM_TOTAL_FEE - 项目总价
     */
    public String getItemTotalFee() {
        return itemTotalFee;
    }

    /**
     * 设置项目总价
     *
     * @param itemTotalFee 项目总价
     */
    public void setItemTotalFee(String itemTotalFee) {
        this.itemTotalFee = itemTotalFee == null ? null : itemTotalFee.trim();
    }

    /**
     * 获取科室名称
     *
     * @return DEPT_NAME - 科室名称
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * 设置科室名称
     *
     * @param deptName 科室名称
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    /**
     * 获取医生名称
     *
     * @return DOCTOR_NAME - 医生名称
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * 设置医生名称
     *
     * @param doctorName 医生名称
     */
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName == null ? null : doctorName.trim();
    }

    /**
     * 获取医生代码
     *
     * @return DOCTOR_CODE - 医生代码
     */
    public String getDoctorCode() {
        return doctorCode;
    }

    /**
     * 设置医生代码
     *
     * @param doctorCode 医生代码
     */
    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode == null ? null : doctorCode.trim();
    }

    /**
     * 获取社保统一药品或诊疗项目编码
     *
     * @return SOCIAL_INSURANCE_ITEMID - 社保统一药品或诊疗项目编码
     */
    public String getSocialInsuranceItemid() {
        return socialInsuranceItemid;
    }

    /**
     * 设置社保统一药品或诊疗项目编码
     *
     * @param socialInsuranceItemid 社保统一药品或诊疗项目编码
     */
    public void setSocialInsuranceItemid(String socialInsuranceItemid) {
        this.socialInsuranceItemid = socialInsuranceItemid == null ? null : socialInsuranceItemid.trim();
    }

    /**
     * 获取医保结算项目代码
     *
     * @return MEDICARE_ITEMID - 医保结算项目代码
     */
    public String getMedicareItemid() {
        return medicareItemid;
    }

    /**
     * 设置医保结算项目代码
     *
     * @param medicareItemid 医保结算项目代码
     */
    public void setMedicareItemid(String medicareItemid) {
        this.medicareItemid = medicareItemid == null ? null : medicareItemid.trim();
    }
    
    @Transient
    public String usage;

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getCadn() {
		return cadn;
	}

	public void setCadn(String cadn) {
		this.cadn = cadn;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
    
	
    
;
}