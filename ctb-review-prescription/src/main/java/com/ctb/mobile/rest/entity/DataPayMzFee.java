package com.ctb.mobile.rest.entity;

import javax.persistence.*;

@Table(name = "data_pay_mz_fee")
public class DataPayMzFee {
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
     * ����ʱ��
     */
    @Column(name = "CLINIC_TIME")
    private String clinicTime;

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
     * ҽ������ 1���Էѣ�2ҽ����3�����Ժ�����
     */
    @Column(name = "PAY_TYPE")
    private String payType;

    /**
     * Ӧ�����
     */
    @Column(name = "PAY_AMOUT")
    private String payAmout;

    /**
     * ҽ�����
     */
    @Column(name = "MEDICARE_AMOUT")
    private String medicareAmout;

    /**
     * �ܽ��
     */
    @Column(name = "TOTAL_AMOUT")
    private String totalAmout;

    /**
     * �籣����������ˮ��
     */
    @Column(name = "SS_CLINIC_NO")
    private String ssClinicNo;

    /**
     * �籣���㵥�ݺ�
     */
    @Column(name = "SS_BILL_NUMBER")
    private String ssBillNumber;

    /**
     * �Ƿ�����ҽ������
     */
    @Column(name = "CAN_USE_INSURANCE")
    private String canUseInsurance;

    /**
     * ��������
     */
    @Column(name = "RECIPE_TYPE")
    private String recipeType;

    /**
     * ����ID��
     */
    @Column(name = "RECIPE_ID")
    private String recipeId;

    /**
     * ��󴦷��� 
     */
    @Column(name = "MZ_BILL_ID")
    private String mzBillId;

    /**
     * ���˺ϼ�
     */
    @Column(name = "INSURANCE_AMOUT")
    private String insuranceAmout;

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
     * ��ȡ����ʱ��
     *
     * @return CLINIC_TIME - ����ʱ��
     */
    public String getClinicTime() {
        return clinicTime;
    }

    /**
     * ��������ʱ��
     *
     * @param clinicTime ����ʱ��
     */
    public void setClinicTime(String clinicTime) {
        this.clinicTime = clinicTime == null ? null : clinicTime.trim();
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
     * ��ȡҽ������ 1���Էѣ�2ҽ����3�����Ժ�����
     *
     * @return PAY_TYPE - ҽ������ 1���Էѣ�2ҽ����3�����Ժ�����
     */
    public String getPayType() {
        return payType;
    }

    /**
     * ����ҽ������ 1���Էѣ�2ҽ����3�����Ժ�����
     *
     * @param payType ҽ������ 1���Էѣ�2ҽ����3�����Ժ�����
     */
    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    /**
     * ��ȡӦ�����
     *
     * @return PAY_AMOUT - Ӧ�����
     */
    public String getPayAmout() {
        return payAmout;
    }

    /**
     * ����Ӧ�����
     *
     * @param payAmout Ӧ�����
     */
    public void setPayAmout(String payAmout) {
        this.payAmout = payAmout == null ? null : payAmout.trim();
    }

    /**
     * ��ȡҽ�����
     *
     * @return MEDICARE_AMOUT - ҽ�����
     */
    public String getMedicareAmout() {
        return medicareAmout;
    }

    /**
     * ����ҽ�����
     *
     * @param medicareAmout ҽ�����
     */
    public void setMedicareAmout(String medicareAmout) {
        this.medicareAmout = medicareAmout == null ? null : medicareAmout.trim();
    }

    /**
     * ��ȡ�ܽ��
     *
     * @return TOTAL_AMOUT - �ܽ��
     */
    public String getTotalAmout() {
        return totalAmout;
    }

    /**
     * �����ܽ��
     *
     * @param totalAmout �ܽ��
     */
    public void setTotalAmout(String totalAmout) {
        this.totalAmout = totalAmout == null ? null : totalAmout.trim();
    }

    /**
     * ��ȡ�籣����������ˮ��
     *
     * @return SS_CLINIC_NO - �籣����������ˮ��
     */
    public String getSsClinicNo() {
        return ssClinicNo;
    }

    /**
     * �����籣����������ˮ��
     *
     * @param ssClinicNo �籣����������ˮ��
     */
    public void setSsClinicNo(String ssClinicNo) {
        this.ssClinicNo = ssClinicNo == null ? null : ssClinicNo.trim();
    }

    /**
     * ��ȡ�籣���㵥�ݺ�
     *
     * @return SS_BILL_NUMBER - �籣���㵥�ݺ�
     */
    public String getSsBillNumber() {
        return ssBillNumber;
    }

    /**
     * �����籣���㵥�ݺ�
     *
     * @param ssBillNumber �籣���㵥�ݺ�
     */
    public void setSsBillNumber(String ssBillNumber) {
        this.ssBillNumber = ssBillNumber == null ? null : ssBillNumber.trim();
    }

    /**
     * ��ȡ�Ƿ�����ҽ������
     *
     * @return CAN_USE_INSURANCE - �Ƿ�����ҽ������
     */
    public String getCanUseInsurance() {
        return canUseInsurance;
    }

    /**
     * �����Ƿ�����ҽ������
     *
     * @param canUseInsurance �Ƿ�����ҽ������
     */
    public void setCanUseInsurance(String canUseInsurance) {
        this.canUseInsurance = canUseInsurance == null ? null : canUseInsurance.trim();
    }

    /**
     * ��ȡ��������
     *
     * @return RECIPE_TYPE - ��������
     */
    public String getRecipeType() {
        return recipeType;
    }

    /**
     * ���ô�������
     *
     * @param recipeType ��������
     */
    public void setRecipeType(String recipeType) {
        this.recipeType = recipeType == null ? null : recipeType.trim();
    }

    /**
     * ��ȡ����ID��
     *
     * @return RECIPE_ID - ����ID��
     */
    public String getRecipeId() {
        return recipeId;
    }

    /**
     * ���ô���ID��
     *
     * @param recipeId ����ID��
     */
    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId == null ? null : recipeId.trim();
    }

    /**
     * ��ȡ��󴦷��� 
     *
     * @return MZ_BILL_ID - ��󴦷��� 
     */
    public String getMzBillId() {
        return mzBillId;
    }

    /**
     * ������󴦷��� 
     *
     * @param mzBillId ��󴦷��� 
     */
    public void setMzBillId(String mzBillId) {
        this.mzBillId = mzBillId == null ? null : mzBillId.trim();
    }

    /**
     * ��ȡ���˺ϼ�
     *
     * @return INSURANCE_AMOUT - ���˺ϼ�
     */
    public String getInsuranceAmout() {
        return insuranceAmout;
    }

    /**
     * ���ü��˺ϼ�
     *
     * @param insuranceAmout ���˺ϼ�
     */
    public void setInsuranceAmout(String insuranceAmout) {
        this.insuranceAmout = insuranceAmout == null ? null : insuranceAmout.trim();
    }
}