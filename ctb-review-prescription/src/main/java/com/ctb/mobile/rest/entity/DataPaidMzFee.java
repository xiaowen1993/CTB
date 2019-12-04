package com.ctb.mobile.rest.entity;

import javax.persistence.*;

@Table(name = "data_paid_mz_fee")
public class DataPaidMzFee {
    /**
     * ���
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * ҽԺ���
     */
    @Column(name = "HOSPITAL_ID")
    private String hospitalId;

    /**
     * ҽԺ����
     */
    @Column(name = "HOSPITAL_CODE")
    private String hospitalCode;

    /**
     * ҽԺ����
     */
    @Column(name = "HOSPITAL_NAME")
    private String hospitalName;

    /**
     * ��Ժ���
     */
    @Column(name = "BRANCH_ID")
    private String branchId;

    /**
     * ��Ժ����
     */
    @Column(name = "BRANCH_CODE")
    private String branchCode;

    /**
     * ��Ժ����
     */
    @Column(name = "BRANCH_NAME")
    private String branchName;

    /**
     * ���ʱ��
     */
    @Column(name = "STORAGE_TIME")
    private Long storageTime;

    /**
     * ��������
     */
    @Column(name = "PATIENT_NAME")
    private String patientName;

    /**
     * ���￨����
     */
    @Column(name = "CARD_TYPE")
    private Integer cardType;

    /**
     * ���￨��
     */
    @Column(name = "CARD_NO")
    private String cardNo;

    /**
     * ������ɷѱ��
     */
    @Column(name = "MZ_FEE_ID")
    private String mzFeeId;

    /**
     * ҽԺ������ˮ��
     */
    @Column(name = "HIS_ORD_NUM")
    private String hisOrdNum;

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
     * ֧�����
     */
    @Column(name = "PAY_AMOUT")
    private String payAmout;

    /**
     * ֧��ʱ��
     */
    @Column(name = "PAY_TIME")
    private String payTime;

    /**
     * ֧����ϵ
     */
    @Column(name = "PAY_MODE")
    private String payMode;

    /**
     * �վݺ�
     */
    @Column(name = "RECEIPT_NUM")
    private String receiptNum;

    /**
     * ������
     */
    @Column(name = "BARCODE")
    private String barcode;

    /**
     * ȡҩ��Ϣ
     */
    @Column(name = "HIS_MESSAGE")
    private String hisMessage;

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
     * ��ȡҽԺ���
     *
     * @return HOSPITAL_ID - ҽԺ���
     */
    public String getHospitalId() {
        return hospitalId;
    }

    /**
     * ����ҽԺ���
     *
     * @param hospitalId ҽԺ���
     */
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId == null ? null : hospitalId.trim();
    }

    /**
     * ��ȡҽԺ����
     *
     * @return HOSPITAL_CODE - ҽԺ����
     */
    public String getHospitalCode() {
        return hospitalCode;
    }

    /**
     * ����ҽԺ����
     *
     * @param hospitalCode ҽԺ����
     */
    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode == null ? null : hospitalCode.trim();
    }

    /**
     * ��ȡҽԺ����
     *
     * @return HOSPITAL_NAME - ҽԺ����
     */
    public String getHospitalName() {
        return hospitalName;
    }

    /**
     * ����ҽԺ����
     *
     * @param hospitalName ҽԺ����
     */
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName == null ? null : hospitalName.trim();
    }

    /**
     * ��ȡ��Ժ���
     *
     * @return BRANCH_ID - ��Ժ���
     */
    public String getBranchId() {
        return branchId;
    }

    /**
     * ���÷�Ժ���
     *
     * @param branchId ��Ժ���
     */
    public void setBranchId(String branchId) {
        this.branchId = branchId == null ? null : branchId.trim();
    }

    /**
     * ��ȡ��Ժ����
     *
     * @return BRANCH_CODE - ��Ժ����
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * ���÷�Ժ����
     *
     * @param branchCode ��Ժ����
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode == null ? null : branchCode.trim();
    }

    /**
     * ��ȡ��Ժ����
     *
     * @return BRANCH_NAME - ��Ժ����
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * ���÷�Ժ����
     *
     * @param branchName ��Ժ����
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
    }

    /**
     * ��ȡ���ʱ��
     *
     * @return STORAGE_TIME - ���ʱ��
     */
    public Long getStorageTime() {
        return storageTime;
    }

    /**
     * �������ʱ��
     *
     * @param storageTime ���ʱ��
     */
    public void setStorageTime(Long storageTime) {
        this.storageTime = storageTime;
    }

    /**
     * ��ȡ��������
     *
     * @return PATIENT_NAME - ��������
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * ���ò�������
     *
     * @param patientName ��������
     */
    public void setPatientName(String patientName) {
        this.patientName = patientName == null ? null : patientName.trim();
    }

    /**
     * ��ȡ���￨����
     *
     * @return CARD_TYPE - ���￨����
     */
    public Integer getCardType() {
        return cardType;
    }

    /**
     * ���þ��￨����
     *
     * @param cardType ���￨����
     */
    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    /**
     * ��ȡ���￨��
     *
     * @return CARD_NO - ���￨��
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * ���þ��￨��
     *
     * @param cardNo ���￨��
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
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
     * ��ȡҽԺ������ˮ��
     *
     * @return HIS_ORD_NUM - ҽԺ������ˮ��
     */
    public String getHisOrdNum() {
        return hisOrdNum;
    }

    /**
     * ����ҽԺ������ˮ��
     *
     * @param hisOrdNum ҽԺ������ˮ��
     */
    public void setHisOrdNum(String hisOrdNum) {
        this.hisOrdNum = hisOrdNum == null ? null : hisOrdNum.trim();
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
     * ��ȡ֧�����
     *
     * @return PAY_AMOUT - ֧�����
     */
    public String getPayAmout() {
        return payAmout;
    }

    /**
     * ����֧�����
     *
     * @param payAmout ֧�����
     */
    public void setPayAmout(String payAmout) {
        this.payAmout = payAmout == null ? null : payAmout.trim();
    }

    /**
     * ��ȡ֧��ʱ��
     *
     * @return PAY_TIME - ֧��ʱ��
     */
    public String getPayTime() {
        return payTime;
    }

    /**
     * ����֧��ʱ��
     *
     * @param payTime ֧��ʱ��
     */
    public void setPayTime(String payTime) {
        this.payTime = payTime == null ? null : payTime.trim();
    }

    /**
     * ��ȡ֧����ϵ
     *
     * @return PAY_MODE - ֧����ϵ
     */
    public String getPayMode() {
        return payMode;
    }

    /**
     * ����֧����ϵ
     *
     * @param payMode ֧����ϵ
     */
    public void setPayMode(String payMode) {
        this.payMode = payMode == null ? null : payMode.trim();
    }

    /**
     * ��ȡ�վݺ�
     *
     * @return RECEIPT_NUM - �վݺ�
     */
    public String getReceiptNum() {
        return receiptNum;
    }

    /**
     * �����վݺ�
     *
     * @param receiptNum �վݺ�
     */
    public void setReceiptNum(String receiptNum) {
        this.receiptNum = receiptNum == null ? null : receiptNum.trim();
    }

    /**
     * ��ȡ������
     *
     * @return BARCODE - ������
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * ����������
     *
     * @param barcode ������
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    /**
     * ��ȡȡҩ��Ϣ
     *
     * @return HIS_MESSAGE - ȡҩ��Ϣ
     */
    public String getHisMessage() {
        return hisMessage;
    }

    /**
     * ����ȡҩ��Ϣ
     *
     * @param hisMessage ȡҩ��Ϣ
     */
    public void setHisMessage(String hisMessage) {
        this.hisMessage = hisMessage == null ? null : hisMessage.trim();
    }
}