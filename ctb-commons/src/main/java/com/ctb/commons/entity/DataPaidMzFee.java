package com.ctb.commons.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

@Table(name = "data_paid_mz_fee")
public class DataPaidMzFee implements Serializable{
    
    /**
     *
     */
    	
    private static final long serialVersionUID = 5929716022671953908L;

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
     * 医院代码
     */
    @Column(name = "HOSPITAL_CODE")
    private String hospitalCode;

    /**
     * 医院名称
     */
    @Column(name = "HOSPITAL_NAME")
    private String hospitalName;

    /**
     * 分院编号
     */
    @Column(name = "BRANCH_ID")
    private String branchId;

    /**
     * 分院代码
     */
    @Column(name = "BRANCH_CODE")
    private String branchCode;

    /**
     * 分院名称
     */
    @Column(name = "BRANCH_NAME")
    private String branchName;

    /**
     * 入库时间
     */
    @Column(name = "STORAGE_TIME")
    private Long storageTime;

    /**
     * 病人名称
     */
    @Column(name = "PATIENT_NAME")
    private String patientName;

    /**
     * 就诊卡类型
     */
    @Column(name = "CARD_TYPE")
    private Integer cardType;

    /**
     * 就诊卡号
     */
    @Column(name = "CARD_NO")
    private String cardNo;

    /**
     * 门诊待缴费编号
     */
    @Column(name = "MZ_FEE_ID")
    private String mzFeeId;

    /**
     * 医院交易流水号
     */
    @Column(name = "HIS_ORD_NUM")
    private String hisOrdNum;

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
     * 支付金额
     */
    @Column(name = "PAY_AMOUT")
    private String payAmout;

    /**
     * 支付时间
     */
    @Column(name = "PAY_TIME")
    private String payTime;

    /**
     * 支付关系
     */
    @Column(name = "PAY_MODE")
    private String payMode;

    /**
     * 支付关系
     */
    @Column(name = "RECEIPT_NUM")
    private String receiptNum;

    /**
     * 条形码
     */
    @Column(name = "BARCODE")
    private String barcode;

    /**
     * 取药信息
     */
    @Column(name = "HIS_MESSAGE")
    private String hisMessage;

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
     * 获取医院编号
     *
     * @return HOSPITAL_ID - 医院编号
     */
    public String getHospitalId() {
        return hospitalId;
    }

    /**
     * 设置医院编号
     *
     * @param hospitalId 医院编号
     */
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId == null ? null : hospitalId.trim();
    }

    /**
     * 获取医院代码
     *
     * @return HOSPITAL_CODE - 医院代码
     */
    public String getHospitalCode() {
        return hospitalCode;
    }

    /**
     * 设置医院代码
     *
     * @param hospitalCode 医院代码
     */
    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode == null ? null : hospitalCode.trim();
    }

    /**
     * 获取医院名称
     *
     * @return HOSPITAL_NAME - 医院名称
     */
    public String getHospitalName() {
        return hospitalName;
    }

    /**
     * 设置医院名称
     *
     * @param hospitalName 医院名称
     */
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName == null ? null : hospitalName.trim();
    }

    /**
     * 获取分院编号
     *
     * @return BRANCH_ID - 分院编号
     */
    public String getBranchId() {
        return branchId;
    }

    /**
     * 设置分院编号
     *
     * @param branchId 分院编号
     */
    public void setBranchId(String branchId) {
        this.branchId = branchId == null ? null : branchId.trim();
    }

    /**
     * 获取分院代码
     *
     * @return BRANCH_CODE - 分院代码
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * 设置分院代码
     *
     * @param branchCode 分院代码
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode == null ? null : branchCode.trim();
    }

    /**
     * 获取分院名称
     *
     * @return BRANCH_NAME - 分院名称
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * 设置分院名称
     *
     * @param branchName 分院名称
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
    }

    /**
     * 获取入库时间
     *
     * @return STORAGE_TIME - 入库时间
     */
    public Long getStorageTime() {
        return storageTime;
    }

    /**
     * 设置入库时间
     *
     * @param storageTime 入库时间
     */
    public void setStorageTime(Long storageTime) {
        this.storageTime = storageTime;
    }

    /**
     * 获取病人名称
     *
     * @return PATIENT_NAME - 病人名称
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * 设置病人名称
     *
     * @param patientName 病人名称
     */
    public void setPatientName(String patientName) {
        this.patientName = patientName == null ? null : patientName.trim();
    }

    /**
     * 获取就诊卡类型
     *
     * @return CARD_TYPE - 就诊卡类型
     */
    public Integer getCardType() {
        return cardType;
    }

    /**
     * 设置就诊卡类型
     *
     * @param cardType 就诊卡类型
     */
    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    /**
     * 获取就诊卡号
     *
     * @return CARD_NO - 就诊卡号
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * 设置就诊卡号
     *
     * @param cardNo 就诊卡号
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
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
     * 获取医院交易流水号
     *
     * @return HIS_ORD_NUM - 医院交易流水号
     */
    public String getHisOrdNum() {
        return hisOrdNum;
    }

    /**
     * 设置医院交易流水号
     *
     * @param hisOrdNum 医院交易流水号
     */
    public void setHisOrdNum(String hisOrdNum) {
        this.hisOrdNum = hisOrdNum == null ? null : hisOrdNum.trim();
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
     * 获取支付金额
     *
     * @return PAY_AMOUT - 支付金额
     */
    public String getPayAmout() {
        return payAmout;
    }

    /**
     * 设置支付金额
     *
     * @param payAmout 支付金额
     */
    public void setPayAmout(String payAmout) {
        this.payAmout = payAmout == null ? null : payAmout.trim();
    }

    /**
     * 获取支付时间
     *
     * @return PAY_TIME - 支付时间
     */
    public String getPayTime() {
        return payTime;
    }

    /**
     * 设置支付时间
     *
     * @param payTime 支付时间
     */
    public void setPayTime(String payTime) {
        this.payTime = payTime == null ? null : payTime.trim();
    }

    /**
     * 获取支付关系
     *
     * @return PAY_MODE - 支付关系
     */
    public String getPayMode() {
        return payMode;
    }

    /**
     * 设置支付关系
     *
     * @param payMode 支付关系
     */
    public void setPayMode(String payMode) {
        this.payMode = payMode == null ? null : payMode.trim();
    }

    /**
     * 获取支付关系
     *
     * @return RECEIPT_NUM - 支付关系
     */
    public String getReceiptNum() {
        return receiptNum;
    }

    /**
     * 设置支付关系
     *
     * @param receiptNum 支付关系
     */
    public void setReceiptNum(String receiptNum) {
        this.receiptNum = receiptNum == null ? null : receiptNum.trim();
    }

    /**
     * 获取条形码
     *
     * @return BARCODE - 条形码
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * 设置条形码
     *
     * @param barcode 条形码
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    /**
     * 获取取药信息
     *
     * @return HIS_MESSAGE - 取药信息
     */
    public String getHisMessage() {
        return hisMessage;
    }

    /**
     * 设置取药信息
     *
     * @param hisMessage 取药信息
     */
    public void setHisMessage(String hisMessage) {
        this.hisMessage = hisMessage == null ? null : hisMessage.trim();
    }
    
    /**
     * 已支付处方明细
     */
    private List<DataPaidMzFeeDetail> dataPaidMzFeeDetails;

	public List<DataPaidMzFeeDetail> getDataPaidMzFeeDetail() {
		return dataPaidMzFeeDetails;
	}

	public void setDataPaidMzFeeDetail(List<DataPaidMzFeeDetail> dataPaidMzFeeDetail) {
		this.dataPaidMzFeeDetails = dataPaidMzFeeDetail;
	}

	public DataPaidMzFee(String mzFeeId) {
		super();
		this.mzFeeId = mzFeeId;
	}

	public DataPaidMzFee() {
		super();
	}

    
	
    
}