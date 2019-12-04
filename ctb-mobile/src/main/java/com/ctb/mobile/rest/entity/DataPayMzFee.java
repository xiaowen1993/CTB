package com.ctb.mobile.rest.entity;

import javax.persistence.*;

@Table(name = "data_pay_mz_fee")
public class DataPayMzFee {
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
     * 门诊时间
     */
    @Column(name = "CLINIC_TIME")
    private String clinicTime;

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
     * 医保类型 1、自费，2医保，3其他以后增加
     */
    @Column(name = "PAY_TYPE")
    private String payType;

    /**
     * 应付金额
     */
    @Column(name = "PAY_AMOUT")
    private String payAmout;

    /**
     * 医保金额
     */
    @Column(name = "MEDICARE_AMOUT")
    private String medicareAmout;

    /**
     * 总金额
     */
    @Column(name = "TOTAL_AMOUT")
    private String totalAmout;

    /**
     * 社保结算门诊流水号
     */
    @Column(name = "SS_CLINIC_NO")
    private String ssClinicNo;

    /**
     * 社保结算单据号
     */
    @Column(name = "SS_BILL_NUMBER")
    private String ssBillNumber;

    /**
     * 是否允许医保结算
     */
    @Column(name = "CAN_USE_INSURANCE")
    private String canUseInsurance;

    /**
     * 处方类型
     */
    @Column(name = "RECIPE_TYPE")
    private String recipeType;

    /**
     * 处方ID号
     */
    @Column(name = "RECIPE_ID")
    private String recipeId;

    /**
     * 最大处方号 
     */
    @Column(name = "MZ_BILL_ID")
    private String mzBillId;

    /**
     * 记账合计
     */
    @Column(name = "INSURANCE_AMOUT")
    private String insuranceAmout;

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
     * 获取门诊时间
     *
     * @return CLINIC_TIME - 门诊时间
     */
    public String getClinicTime() {
        return clinicTime;
    }

    /**
     * 设置门诊时间
     *
     * @param clinicTime 门诊时间
     */
    public void setClinicTime(String clinicTime) {
        this.clinicTime = clinicTime == null ? null : clinicTime.trim();
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
     * 获取医保类型 1、自费，2医保，3其他以后增加
     *
     * @return PAY_TYPE - 医保类型 1、自费，2医保，3其他以后增加
     */
    public String getPayType() {
        return payType;
    }

    /**
     * 设置医保类型 1、自费，2医保，3其他以后增加
     *
     * @param payType 医保类型 1、自费，2医保，3其他以后增加
     */
    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    /**
     * 获取应付金额
     *
     * @return PAY_AMOUT - 应付金额
     */
    public String getPayAmout() {
        return payAmout;
    }

    /**
     * 设置应付金额
     *
     * @param payAmout 应付金额
     */
    public void setPayAmout(String payAmout) {
        this.payAmout = payAmout == null ? null : payAmout.trim();
    }

    /**
     * 获取医保金额
     *
     * @return MEDICARE_AMOUT - 医保金额
     */
    public String getMedicareAmout() {
        return medicareAmout;
    }

    /**
     * 设置医保金额
     *
     * @param medicareAmout 医保金额
     */
    public void setMedicareAmout(String medicareAmout) {
        this.medicareAmout = medicareAmout == null ? null : medicareAmout.trim();
    }

    /**
     * 获取总金额
     *
     * @return TOTAL_AMOUT - 总金额
     */
    public String getTotalAmout() {
        return totalAmout;
    }

    /**
     * 设置总金额
     *
     * @param totalAmout 总金额
     */
    public void setTotalAmout(String totalAmout) {
        this.totalAmout = totalAmout == null ? null : totalAmout.trim();
    }

    /**
     * 获取社保结算门诊流水号
     *
     * @return SS_CLINIC_NO - 社保结算门诊流水号
     */
    public String getSsClinicNo() {
        return ssClinicNo;
    }

    /**
     * 设置社保结算门诊流水号
     *
     * @param ssClinicNo 社保结算门诊流水号
     */
    public void setSsClinicNo(String ssClinicNo) {
        this.ssClinicNo = ssClinicNo == null ? null : ssClinicNo.trim();
    }

    /**
     * 获取社保结算单据号
     *
     * @return SS_BILL_NUMBER - 社保结算单据号
     */
    public String getSsBillNumber() {
        return ssBillNumber;
    }

    /**
     * 设置社保结算单据号
     *
     * @param ssBillNumber 社保结算单据号
     */
    public void setSsBillNumber(String ssBillNumber) {
        this.ssBillNumber = ssBillNumber == null ? null : ssBillNumber.trim();
    }

    /**
     * 获取是否允许医保结算
     *
     * @return CAN_USE_INSURANCE - 是否允许医保结算
     */
    public String getCanUseInsurance() {
        return canUseInsurance;
    }

    /**
     * 设置是否允许医保结算
     *
     * @param canUseInsurance 是否允许医保结算
     */
    public void setCanUseInsurance(String canUseInsurance) {
        this.canUseInsurance = canUseInsurance == null ? null : canUseInsurance.trim();
    }

    /**
     * 获取处方类型
     *
     * @return RECIPE_TYPE - 处方类型
     */
    public String getRecipeType() {
        return recipeType;
    }

    /**
     * 设置处方类型
     *
     * @param recipeType 处方类型
     */
    public void setRecipeType(String recipeType) {
        this.recipeType = recipeType == null ? null : recipeType.trim();
    }

    /**
     * 获取处方ID号
     *
     * @return RECIPE_ID - 处方ID号
     */
    public String getRecipeId() {
        return recipeId;
    }

    /**
     * 设置处方ID号
     *
     * @param recipeId 处方ID号
     */
    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId == null ? null : recipeId.trim();
    }

    /**
     * 获取最大处方号 
     *
     * @return MZ_BILL_ID - 最大处方号 
     */
    public String getMzBillId() {
        return mzBillId;
    }

    /**
     * 设置最大处方号 
     *
     * @param mzBillId 最大处方号 
     */
    public void setMzBillId(String mzBillId) {
        this.mzBillId = mzBillId == null ? null : mzBillId.trim();
    }

    /**
     * 获取记账合计
     *
     * @return INSURANCE_AMOUT - 记账合计
     */
    public String getInsuranceAmout() {
        return insuranceAmout;
    }

    /**
     * 设置记账合计
     *
     * @param insuranceAmout 记账合计
     */
    public void setInsuranceAmout(String insuranceAmout) {
        this.insuranceAmout = insuranceAmout == null ? null : insuranceAmout.trim();
    }
}