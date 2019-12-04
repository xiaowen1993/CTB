package com.ctb.mobile.rest.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_drug_list")
public class DrugList {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * ҩƷ����
     */
    @Column(name = "DRUG_NAME")
    private String drugName;

    /**
     * ҩƷ���루Ψһͨ�ñ��룬����ҩ����룩
     */
    @Column(name = "DRUG_CODE")
    private String drugCode;

    /**
     * ҩƷͨ������China Approved Drug Names,��ƣ�CADN��
     */
    @Column(name = "CADN")
    private String cadn;

    /**
     * ��׼�ĺ�
     */
    @Column(name = "APPROVAL_NO")
    private String approvalNo;

    /**
     * ������ҵ
     */
    @Column(name = "MANUFACTURER")
    private String manufacturer;

    /**
     * ���
     */
    @Column(name = "SPECIFICATION")
    private String specification;

    /**
     * �÷�����
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
     * ��ȡҩƷ����
     *
     * @return DRUG_NAME - ҩƷ����
     */
    public String getDrugName() {
        return drugName;
    }

    /**
     * ����ҩƷ����
     *
     * @param drugName ҩƷ����
     */
    public void setDrugName(String drugName) {
        this.drugName = drugName == null ? null : drugName.trim();
    }

    /**
     * ��ȡҩƷ���루Ψһͨ�ñ��룬����ҩ����룩
     *
     * @return DRUG_CODE - ҩƷ���루Ψһͨ�ñ��룬����ҩ����룩
     */
    public String getDrugCode() {
        return drugCode;
    }

    /**
     * ����ҩƷ���루Ψһͨ�ñ��룬����ҩ����룩
     *
     * @param drugCode ҩƷ���루Ψһͨ�ñ��룬����ҩ����룩
     */
    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode == null ? null : drugCode.trim();
    }

    /**
     * ��ȡҩƷͨ������China Approved Drug Names,��ƣ�CADN��
     *
     * @return CADN - ҩƷͨ������China Approved Drug Names,��ƣ�CADN��
     */
    public String getCadn() {
        return cadn;
    }

    /**
     * ����ҩƷͨ������China Approved Drug Names,��ƣ�CADN��
     *
     * @param cadn ҩƷͨ������China Approved Drug Names,��ƣ�CADN��
     */
    public void setCadn(String cadn) {
        this.cadn = cadn == null ? null : cadn.trim();
    }

    /**
     * ��ȡ��׼�ĺ�
     *
     * @return APPROVAL_NO - ��׼�ĺ�
     */
    public String getApprovalNo() {
        return approvalNo;
    }

    /**
     * ������׼�ĺ�
     *
     * @param approvalNo ��׼�ĺ�
     */
    public void setApprovalNo(String approvalNo) {
        this.approvalNo = approvalNo == null ? null : approvalNo.trim();
    }

    /**
     * ��ȡ������ҵ
     *
     * @return MANUFACTURER - ������ҵ
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * ����������ҵ
     *
     * @param manufacturer ������ҵ
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer == null ? null : manufacturer.trim();
    }

    /**
     * ��ȡ���
     *
     * @return SPECIFICATION - ���
     */
    public String getSpecification() {
        return specification;
    }

    /**
     * ���ù��
     *
     * @param specification ���
     */
    public void setSpecification(String specification) {
        this.specification = specification == null ? null : specification.trim();
    }

    /**
     * ��ȡ�÷�����
     *
     * @return USAGE - �÷�����
     */
    public String getUsage() {
        return usage;
    }

    /**
     * �����÷�����
     *
     * @param usage �÷�����
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