package com.ctb.mobile.rest.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_drug_list")
public class DrugList {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 药品名称
     */
    @Column(name = "DRUG_NAME")
    private String drugName;

    /**
     * 药品编码（唯一通用编码，国家药物编码）
     */
    @Column(name = "DRUG_CODE")
    private String drugCode;

    /**
     * 药品通用名（China Approved Drug Names,简称：CADN）
     */
    @Column(name = "CADN")
    private String cadn;

    /**
     * 批准文号
     */
    @Column(name = "APPROVAL_NO")
    private String approvalNo;

    /**
     * 生产企业
     */
    @Column(name = "MANUFACTURER")
    private String manufacturer;

    /**
     * 规格
     */
    @Column(name = "SPECIFICATION")
    private String specification;

    /**
     * 用法用量
     */
    @Column(name = "USAGE")
    private String usage;

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
     * 获取药品名称
     *
     * @return DRUG_NAME - 药品名称
     */
    public String getDrugName() {
        return drugName;
    }

    /**
     * 设置药品名称
     *
     * @param drugName 药品名称
     */
    public void setDrugName(String drugName) {
        this.drugName = drugName == null ? null : drugName.trim();
    }

    /**
     * 获取药品编码（唯一通用编码，国家药物编码）
     *
     * @return DRUG_CODE - 药品编码（唯一通用编码，国家药物编码）
     */
    public String getDrugCode() {
        return drugCode;
    }

    /**
     * 设置药品编码（唯一通用编码，国家药物编码）
     *
     * @param drugCode 药品编码（唯一通用编码，国家药物编码）
     */
    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode == null ? null : drugCode.trim();
    }

    /**
     * 获取药品通用名（China Approved Drug Names,简称：CADN）
     *
     * @return CADN - 药品通用名（China Approved Drug Names,简称：CADN）
     */
    public String getCadn() {
        return cadn;
    }

    /**
     * 设置药品通用名（China Approved Drug Names,简称：CADN）
     *
     * @param cadn 药品通用名（China Approved Drug Names,简称：CADN）
     */
    public void setCadn(String cadn) {
        this.cadn = cadn == null ? null : cadn.trim();
    }

    /**
     * 获取批准文号
     *
     * @return APPROVAL_NO - 批准文号
     */
    public String getApprovalNo() {
        return approvalNo;
    }

    /**
     * 设置批准文号
     *
     * @param approvalNo 批准文号
     */
    public void setApprovalNo(String approvalNo) {
        this.approvalNo = approvalNo == null ? null : approvalNo.trim();
    }

    /**
     * 获取生产企业
     *
     * @return MANUFACTURER - 生产企业
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * 设置生产企业
     *
     * @param manufacturer 生产企业
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer == null ? null : manufacturer.trim();
    }

    /**
     * 获取规格
     *
     * @return SPECIFICATION - 规格
     */
    public String getSpecification() {
        return specification;
    }

    /**
     * 设置规格
     *
     * @param specification 规格
     */
    public void setSpecification(String specification) {
        this.specification = specification == null ? null : specification.trim();
    }

    /**
     * 获取用法用量
     *
     * @return USAGE - 用法用量
     */
    public String getUsage() {
        return usage;
    }

    /**
     * 设置用法用量
     *
     * @param usage 用法用量
     */
    public void setUsage(String usage) {
        this.usage = usage == null ? null : usage.trim();
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