/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年6月21日
 * Created by hhy
 */
package com.ctb.pharmacy.rest.entity.vo;

import java.io.Serializable;

/**
 * @ClassName: com.ctb.pharmacy.rest.entity.vo.YXDrugDetailVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年6月21日 上午11:43:23
 */

public class YXDrugDetailVo implements Serializable{

    
    /**
     *
     */
        
    private static final long serialVersionUID = -3681328439033201005L;


    /**
     * 药品名称
     */
    private String drugName;

    /**
     * 药品编码（唯一通用编码，国家药物编码）
     */
    private String drugCode;

    /**
     * 药品通用名（China Approved Drug Names,简称：CADN）
     */
    private String cadn;

    /**
     * 批准文号
     */
    private String approvalNo;

    /**
     * 生产企业
     */
    private String manufacturer;

    /**
     * 规格
     */
    private String specification;
    
    private String number;

    /**
     * HIS自编药品编码
     */
    private String hisDrugCode;

    /**
     * 用法用量
     */
    private String usage;
    
    
    private String mzFeeId;


    private String itemTime;


    private String itemId;


    private String itemName;


    private String itemType;


    private String itemUnit;


    private String itemPrice;


    private String itemSpec;


    private String itemNumber;


    private String itemTotalFee;


    private String deptName;


    private String doctorName;


    private String doctorCode;


    private String socialInsuranceItemid;


    private String medicareItemid;




    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public String getCadn() {
        return cadn;
    }

    public void setCadn(String cadn) {
        this.cadn = cadn;
    }

    public String getApprovalNo() {
        return approvalNo;
    }

    public void setApprovalNo(String approvalNo) {
        this.approvalNo = approvalNo;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHisDrugCode() {
        return hisDrugCode;
    }

    public void setHisDrugCode(String hisDrugCode) {
        this.hisDrugCode = hisDrugCode;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getMzFeeId() {
        return mzFeeId;
    }

    public void setMzFeeId(String mzFeeId) {
        this.mzFeeId = mzFeeId;
    }

    public String getItemTime() {
        return itemTime;
    }

    public void setItemTime(String itemTime) {
        this.itemTime = itemTime;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getItemTotalFee() {
        return itemTotalFee;
    }

    public void setItemTotalFee(String itemTotalFee) {
        this.itemTotalFee = itemTotalFee;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }

    public String getSocialInsuranceItemid() {
        return socialInsuranceItemid;
    }

    public void setSocialInsuranceItemid(String socialInsuranceItemid) {
        this.socialInsuranceItemid = socialInsuranceItemid;
    }

    public String getMedicareItemid() {
        return medicareItemid;
    }

    public void setMedicareItemid(String medicareItemid) {
        this.medicareItemid = medicareItemid;
    }

    
    
}
