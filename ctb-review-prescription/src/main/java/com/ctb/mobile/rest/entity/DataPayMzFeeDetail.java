package com.ctb.mobile.rest.entity;

import javax.persistence.*;

@Table(name = "data_pay_mz_fee_detail")
public class DataPayMzFeeDetail {
    /**
     * ���
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * ������ɷѱ��
     */
    @Column(name = "MZ_FEE_ID")
    private String mzFeeId;

    /**
     * ��Ŀʱ��
     */
    @Column(name = "ITEM_TIME")
    private String itemTime;

    /**
     * ��Ŀ���
     */
    @Column(name = "ITEM_ID")
    private String itemId;

    /**
     * ��Ŀ����
     */
    @Column(name = "ITEM_NAME")
    private String itemName;

    /**
     * ��Ŀ�ѱ�
     */
    @Column(name = "ITEM_TYPE")
    private String itemType;

    /**
     * ��Ŀ��λ
     */
    @Column(name = "ITEM_UNIT")
    private String itemUnit;

    /**
     * ��Ŀ����
     */
    @Column(name = "ITEM_PRICE")
    private String itemPrice;

    /**
     * ��Ŀ���
     */
    @Column(name = "ITEM_SPEC")
    private String itemSpec;

    /**
     * ��Ŀ����
     */
    @Column(name = "ITEM_NUMBER")
    private String itemNumber;

    /**
     * ��Ŀ�ܼ�
     */
    @Column(name = "ITEM_TOTAL_FEE")
    private String itemTotalFee;

    /**
     * ��������
     */
    @Column(name = "DEPT_NAME")
    private String deptName;

    /**
     * ҽ������
     */
    @Column(name = "DOCTOR_NAME")
    private String doctorName;

    /**
     * ҽ������
     */
    @Column(name = "DOCTOR_CODE")
    private String doctorCode;

    /**
     * �籣ͳһҩƷ��������Ŀ����
     */
    @Column(name = "SOCIAL_INSURANCE_ITEMID")
    private String socialInsuranceItemid;

    /**
     * ҽ��������Ŀ����
     */
    @Column(name = "MEDICARE_ITEMID")
    private String medicareItemid;

    /**
     * ��ȡ���
     *
     * @return ID - ���
     */
    public String getId() {
        return id;
    }

    /**
     * ���ñ��
     *
     * @param id ���
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * ��ȡ������ɷѱ��
     *
     * @return MZ_FEE_ID - ������ɷѱ��
     */
    public String getMzFeeId() {
        return mzFeeId;
    }

    /**
     * ����������ɷѱ��
     *
     * @param mzFeeId ������ɷѱ��
     */
    public void setMzFeeId(String mzFeeId) {
        this.mzFeeId = mzFeeId == null ? null : mzFeeId.trim();
    }

    /**
     * ��ȡ��Ŀʱ��
     *
     * @return ITEM_TIME - ��Ŀʱ��
     */
    public String getItemTime() {
        return itemTime;
    }

    /**
     * ������Ŀʱ��
     *
     * @param itemTime ��Ŀʱ��
     */
    public void setItemTime(String itemTime) {
        this.itemTime = itemTime == null ? null : itemTime.trim();
    }

    /**
     * ��ȡ��Ŀ���
     *
     * @return ITEM_ID - ��Ŀ���
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * ������Ŀ���
     *
     * @param itemId ��Ŀ���
     */
    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    /**
     * ��ȡ��Ŀ����
     *
     * @return ITEM_NAME - ��Ŀ����
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * ������Ŀ����
     *
     * @param itemName ��Ŀ����
     */
    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    /**
     * ��ȡ��Ŀ�ѱ�
     *
     * @return ITEM_TYPE - ��Ŀ�ѱ�
     */
    public String getItemType() {
        return itemType;
    }

    /**
     * ������Ŀ�ѱ�
     *
     * @param itemType ��Ŀ�ѱ�
     */
    public void setItemType(String itemType) {
        this.itemType = itemType == null ? null : itemType.trim();
    }

    /**
     * ��ȡ��Ŀ��λ
     *
     * @return ITEM_UNIT - ��Ŀ��λ
     */
    public String getItemUnit() {
        return itemUnit;
    }

    /**
     * ������Ŀ��λ
     *
     * @param itemUnit ��Ŀ��λ
     */
    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit == null ? null : itemUnit.trim();
    }

    /**
     * ��ȡ��Ŀ����
     *
     * @return ITEM_PRICE - ��Ŀ����
     */
    public String getItemPrice() {
        return itemPrice;
    }

    /**
     * ������Ŀ����
     *
     * @param itemPrice ��Ŀ����
     */
    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice == null ? null : itemPrice.trim();
    }

    /**
     * ��ȡ��Ŀ���
     *
     * @return ITEM_SPEC - ��Ŀ���
     */
    public String getItemSpec() {
        return itemSpec;
    }

    /**
     * ������Ŀ���
     *
     * @param itemSpec ��Ŀ���
     */
    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec == null ? null : itemSpec.trim();
    }

    /**
     * ��ȡ��Ŀ����
     *
     * @return ITEM_NUMBER - ��Ŀ����
     */
    public String getItemNumber() {
        return itemNumber;
    }

    /**
     * ������Ŀ����
     *
     * @param itemNumber ��Ŀ����
     */
    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber == null ? null : itemNumber.trim();
    }

    /**
     * ��ȡ��Ŀ�ܼ�
     *
     * @return ITEM_TOTAL_FEE - ��Ŀ�ܼ�
     */
    public String getItemTotalFee() {
        return itemTotalFee;
    }

    /**
     * ������Ŀ�ܼ�
     *
     * @param itemTotalFee ��Ŀ�ܼ�
     */
    public void setItemTotalFee(String itemTotalFee) {
        this.itemTotalFee = itemTotalFee == null ? null : itemTotalFee.trim();
    }

    /**
     * ��ȡ��������
     *
     * @return DEPT_NAME - ��������
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * ���ÿ�������
     *
     * @param deptName ��������
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    /**
     * ��ȡҽ������
     *
     * @return DOCTOR_NAME - ҽ������
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * ����ҽ������
     *
     * @param doctorName ҽ������
     */
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName == null ? null : doctorName.trim();
    }

    /**
     * ��ȡҽ������
     *
     * @return DOCTOR_CODE - ҽ������
     */
    public String getDoctorCode() {
        return doctorCode;
    }

    /**
     * ����ҽ������
     *
     * @param doctorCode ҽ������
     */
    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode == null ? null : doctorCode.trim();
    }

    /**
     * ��ȡ�籣ͳһҩƷ��������Ŀ����
     *
     * @return SOCIAL_INSURANCE_ITEMID - �籣ͳһҩƷ��������Ŀ����
     */
    public String getSocialInsuranceItemid() {
        return socialInsuranceItemid;
    }

    /**
     * �����籣ͳһҩƷ��������Ŀ����
     *
     * @param socialInsuranceItemid �籣ͳһҩƷ��������Ŀ����
     */
    public void setSocialInsuranceItemid(String socialInsuranceItemid) {
        this.socialInsuranceItemid = socialInsuranceItemid == null ? null : socialInsuranceItemid.trim();
    }

    /**
     * ��ȡҽ��������Ŀ����
     *
     * @return MEDICARE_ITEMID - ҽ��������Ŀ����
     */
    public String getMedicareItemid() {
        return medicareItemid;
    }

    /**
     * ����ҽ��������Ŀ����
     *
     * @param medicareItemid ҽ��������Ŀ����
     */
    public void setMedicareItemid(String medicareItemid) {
        this.medicareItemid = medicareItemid == null ? null : medicareItemid.trim();
    }
}