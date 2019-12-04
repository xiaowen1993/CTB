package com.ctb.commons.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.util.StringUtils;

@Table(name = "biz_prescription_record")
public class PrescriptionRecord implements Serializable, Comparable<PrescriptionRecord> {
    
    /**
     *
     */
    
    private static final long serialVersionUID = 2164015577704508354L;
    
    @Id
    @Column(name = "ID")
    private String id;
    
    /**
     * 医院ID
     */
    @NotNull(message = "hospitalId 不能为空")
    @Column(name = "HOSPITAL_ID")
    private String hospitalId;
    
    /**
     * 医院code
     */
    @NotNull(message = "hospitalCode 不能为空")
    @Column(name = "HOSPITAL_CODE")
    private String hospitalCode;
    
    /**
     * 医院名称
     */
    @NotNull(message = "hospitalName 不能为空")
    @Column(name = "HOSPITAL_NAME")
    private String hospitalName;
    
    /**
     * 分院ID
     */
    @Column(name = "HOSPITAL_BRANCH_ID")
    private String hospitalBranchId;
    
    /**
     * 分院code
     */
    @Column(name = "HOSPITAL_BRANCH_CODE")
    private String hospitalBranchCode;
    
    /**
     * 门诊ID
     */
    @NotNull(message = "mzFeeId 不能为空")
    @Column(name = "MZ_FEE_ID")
    private String mzFeeId;
    
    /**
     * 患者唯一标识
     */
    @Column(name = "UNION_ID")
    @NotNull(message = "unionId 不能为空")
    private String unionId;
    
    /**
     * 本公众号微信openId
     */
    @NotNull(message = "openId 不能为空")
    @Column(name = "OPEN_ID")
    public String openId;
    
    /**
     * 交易方式 ：微信支付（01），支付宝支付（02），微信支付（健康易app）（03）
     */
    @Column(name = "TRADE_MODE")
    private String tradeMode;
    
    /**
     * 平台类型(2位) 微信服务窗（01），支付宝服务窗（02），健康易（03）
     */
    @Column(name = "PLATFORM_MODE")
    private String platformMode;
    
    /**
     * 第三方支付单号
     */
    @Column(name = "AGT_ORD_NUM")
    private String agtOrdNum;
    
    /**
     * 第三方退款支付单号
     */
    @Column(name = "AGT_REFUND_ORD_NUM")
    private String agtRefundOrdNum;
    
    /**
     * 第三方支付时间
     */
    @Column(name = "PAY_TIME")
    private Long payTime;
    
    /**
     * 第三方退费时间
     */
    @Column(name = "REFUND_TIME")
    private Long refundTime;
    
    /**
     * 支付状态：1：未支付, 2：已支付,3：已退费
     */
    @Column(name = "PAY_STATUS")
    private Integer payStatus;
    
    /**
     * 订单状态：0待缴费,1已缴费,2缴费失败,3缴费异常
     */
    @Column(name = "PRESCRIPTION_STATUS")
    private Integer prescriptionStatus;
    
    /**
     * 药店状态：1：未配药, 2：已配药,3：已发药
     */
    @Column(name = "PHARMACY_STATUS")
    private Integer pharmacyStatus;
    
    /**
     * 订单号
     */
    @Column(name = "ORDER_NO")
    private String orderNo;
    
    /**
     * 退费订单号
     */
    @Column(name = "REFUND_ORDER_NO")
    private String refundOrderNo;
    
    /**
     * 处方单号hash值
     */
    @Column(name = "ORDER_NO_HASH_VAL")
    private Long orderNoHashVal;
    
    /**
     * 是否已被支付回调 1 是 0 否
     */
    @Column(name = "IS_HAD_CALL_BACK")
    private Integer isHadCallBack;
    
    /**
     * 总金额
     */
    @NotNull(message = "totalFee 不能为空")
    @Column(name = "TOTAL_FEE")
    private String totalFee;
    
    /**
     * 支付金额
     */
    @NotNull(message = "payFee 不能为空")
    @Column(name = "PAY_FEE")
    private String payFee;
    
    /**
     * 折扣金额
     */
    @Column(name = "DISCOUNT_FEE")
    private String discountFee;
    /**
     * 接诊科室代码
     */
    // @NotNull(message="deptCode 不能为空")
    @Column(name = "DEPT_CODE")
    private String deptCode;
    
    /**
     * 接诊医生代码
     */
    // @NotNull(message="doctorCode 不能为空")
    @Column(name = "DOCTOR_CODE")
    private String doctorCode;
    
    /**
     * 医生名称
     */
    @NotNull(message = "doctorName 不能为空")
    @Column(name = "DOCTOR_NAME")
    private String doctorName;
    
    /**
     * 处方科室
     */
    @NotNull(message = "deptName 不能为空")
    @Column(name = "DEPT_NAME")
    private String deptName;
    
    /**
     * 取药方式：物流配送 ；药店自取
     */
    @Column(name = "RECORD_TYPE")
    private String recordType;
    
    /**
     * 就诊卡类型
     */
    @Column(name = "CARD_TYPE")
    private String cardType;
    
    /**
     * 就诊卡号
     */
    @NotNull(message = "cardNo 不能为空")
    @Column(name = "CARD_NO")
    private String cardNo;
    
    /**
     * 就诊人姓名
     */
    @NotNull(message = "patientName 不能为空")
    @Column(name = "PATIENT_NAME")
    private String patientName;
    
    /**
     * 临床诊断
     */
    @Column(name = "DIAGNOSE")
    private String diagnose;
    
    /**
     * 药店ID
     */
    @NotNull(message = "pharmacyId 不能为空")
    @Column(name = "PHARMACY_ID")
    private String pharmacyId;
    
    /**
     * 药店CODE
     */
    // @NotNull(message="pharmacyCode 不能为空")
    @Column(name = "PHARMACY_CODE")
    private String pharmacyCode;
    
    /**
     * 药店-分店ID
     */
    @NotNull(message = "pharmacyBranchId 不能为空")
    @Column(name = "PHARMACY_BRANCH_ID")
    private String pharmacyBranchId;
    
    /**
     * 药店-分店CODE
     */
    @NotNull(message = "pharmacyBranchCode 不能为空")
    @Column(name = "PHARMACY_BRANCH_CODE")
    private String pharmacyBranchCode;
    
    /**
     * 审查处方医师ID
     */
    @Column(name = "REVIEW_PHYSICIANS_ID")
    private String reviewPhysiciansId;
    
    /**
     * 审查状态
     */
    @Column(name = "REVIEW_STATUS")
    private Integer reviewStatus;
    
    /**
     * 审查意见
     */
    @Column(name = "REVIEW_MESSAGES")
    private String reviewMessages;
    
    /**
     * 审药时间
     */
    @Column(name = "REVIEW_TIME")
    private Long reviewTime;
    
    /**
     * 是否发生异常 0 否 1是
     */
    @Column(name = "IS_EXCEPTION")
    private Integer isException;
    
    /**
     * 是否处理成功 1是 0 否
     */
    @Column(name = "IS_HANDLE_SUCCESS")
    private Integer isHandleSuccess;
    
    /**
     * 处理次数
     */
    @Column(name = "HANDLE_COUNT")
    private Integer handleCount;
    
    /**
     * 异常处理日志
     */
    @Column(name = "HANDLE_LOG")
    private String handleLog;
    
    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Long createTime;
    
    /**
     * 更新时间
     */
    @Column(name = "UPDATE_TIME")
    private Long updateTime;
    
    /**
     * 入参平台类型，不入库
     */
    
    @NotNull(message = "platformModeType 不能为空")
    @Transient
    private String platformModeType;
    
    /**
     * 入参支付类型，不入库
     */
    @Transient
    @NotNull(message = "tradeModeType 不能为空")
    private String tradeModeType;
    
    /**
     * 实体类对应的hash子表 该属性只在数据库操作时定位tableName 不入库
     */
    @Transient
    private String hashTableName;
    
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
     * 获取医院ID
     *
     * @return HOSPITAL_ID - 医院ID
     */
    public String getHospitalId() {
        return hospitalId;
    }
    
    /**
     * 设置医院ID
     *
     * @param hospitalId
     *            医院ID
     */
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId == null ? null : hospitalId.trim();
    }
    
    /**
     * 获取医院code
     *
     * @return HOSPITAL_CODE - 医院code
     */
    public String getHospitalCode() {
        return hospitalCode;
    }
    
    /**
     * 设置医院code
     *
     * @param hospitalCode
     *            医院code
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
     * @param hospitalName
     *            医院名称
     */
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName == null ? null : hospitalName.trim();
    }
    
    /**
     * 获取分院ID
     *
     * @return HOSPITAL_BRANCH_ID - 分院ID
     */
    public String getHospitalBranchId() {
        return hospitalBranchId;
    }
    
    /**
     * 设置分院ID
     *
     * @param hospitalBranchId
     *            分院ID
     */
    public void setHospitalBranchId(String hospitalBranchId) {
        this.hospitalBranchId = hospitalBranchId == null ? null : hospitalBranchId.trim();
    }
    
    /**
     * 获取分院code
     *
     * @return HOSPITAL_BRANCH_CODE - 分院code
     */
    public String getHospitalBranchCode() {
        return hospitalBranchCode;
    }
    
    /**
     * 设置分院code
     *
     * @param hospitalBranchCode
     *            分院code
     */
    public void setHospitalBranchCode(String hospitalBranchCode) {
        this.hospitalBranchCode = hospitalBranchCode == null ? null : hospitalBranchCode.trim();
    }
    
    /**
     * 获取患者唯一标识
     *
     * @return UNION_ID - 患者唯一标识
     */
    public String getUnionId() {
        return unionId;
    }
    
    /**
     * 设置患者唯一标识
     *
     * @param unionId
     *            患者唯一标识
     */
    public void setUnionId(String unionId) {
        this.unionId = unionId == null ? null : unionId.trim();
    }
    
    /**
     * 获取本公众号微信openId
     *
     * @return OPEN_ID - 本公众号微信openId
     */
    public String getOpenId() {
        return openId;
    }
    
    /**
     * 设置本公众号微信openId
     *
     * @param openId
     *            本公众号微信openId
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }
    
   
    public String getTradeMode() {
        return tradeMode;
    }
    
    public void setTradeMode(String tradeMode) {
        this.tradeMode = tradeMode;
    }
    
    /**
     * 获取平台类型 01微信，02支付宝，03健康易
     *
     * @return PLATFORM_MODE - 平台类型 01微信，02支付宝，03健康易
     */
    public String getPlatformMode() {
        return platformMode;
    }
    
    /**
     * 设置平台类型 01微信，02支付宝，03健康易
     *
     * @param platformMode
     *            平台类型 01微信，02支付宝，03健康易
     */
    public void setPlatformMode(String platformMode) {
        this.platformMode = platformMode;
    }
    
    /**
     * 获取第三方支付单号
     *
     * @return AGT_ORD_NUM - 第三方支付单号
     */
    public String getAgtOrdNum() {
        return agtOrdNum;
    }
    
    /**
     * 设置第三方支付单号
     *
     * @param agtOrdNum
     *            第三方支付单号
     */
    public void setAgtOrdNum(String agtOrdNum) {
        this.agtOrdNum = agtOrdNum == null ? null : agtOrdNum.trim();
    }
    
    /**
     * 获取第三方退款支付单号
     *
     * @return AGT_REFUND_ORD_NUM - 第三方退款支付单号
     */
    public String getAgtRefundOrdNum() {
        return agtRefundOrdNum;
    }
    
    /**
     * 设置第三方退款支付单号
     *
     * @param agtRefundOrdNum
     *            第三方退款支付单号
     */
    public void setAgtRefundOrdNum(String agtRefundOrdNum) {
        this.agtRefundOrdNum = agtRefundOrdNum == null ? null : agtRefundOrdNum.trim();
    }
    
    /**
     * 获取第三方支付时间
     *
     * @return PAY_TIME - 第三方支付时间
     */
    public Long getPayTime() {
        return payTime;
    }
    
    /**
     * 设置第三方支付时间
     *
     * @param payTime
     *            第三方支付时间
     */
    public void setPayTime(Long payTime) {
        this.payTime = payTime;
    }
    
    /**
     * 获取第三方退费时间
     *
     * @return REFUND_TIME - 第三方退费时间
     */
    public Long getRefundTime() {
        return refundTime;
    }
    
    /**
     * 设置第三方退费时间
     *
     * @param refundTime
     *            第三方退费时间
     */
    public void setRefundTime(Long refundTime) {
        this.refundTime = refundTime;
    }
    
    /**
     * 获取支付状态：1：未支付, 2：已支付,3：已退费
     *
     * @return PAY_STATUS - 支付状态：1：未支付, 2：已支付,3：已退费
     */
    public Integer getPayStatus() {
        return payStatus;
    }
    
    /**
     * 设置支付状态：1：未支付, 2：已支付,3：已退费
     *
     * @param payStatus
     *            支付状态：1：未支付, 2：已支付,3：已退费
     */
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }
    
    /**
     * 获取订单状态：0待缴费,1已缴费,2缴费失败,3缴费异常
     *
     * @return PRESCRIPTION_STATUS - 订单状态：0待缴费,1已缴费,2缴费失败,3缴费异常
     */
    public Integer getPrescriptionStatus() {
        return prescriptionStatus;
    }
    
    /**
     * 设置订单状态：0待缴费,1已缴费,2缴费失败,3缴费异常
     *
     * @param prescriptionStatus
     *            订单状态：0待缴费,1已缴费,2缴费失败,3缴费异常
     */
    public void setPrescriptionStatus(Integer prescriptionStatus) {
        this.prescriptionStatus = prescriptionStatus;
    }
    
    /**
     * 获取药店状态：1：未配药, 2：已配药,3：已发药
     *
     * @return PHARMACY_STATUS - 药店状态：1：未配药, 2：已配药,3：已发药
     */
    public Integer getPharmacyStatus() {
        return pharmacyStatus;
    }
    
    /**
     * 设置药店状态：1：未配药, 2：已配药,3：已发药
     *
     * @param pharmacyStatus
     *            药店状态：1：未配药, 2：已配药,3：已发药
     */
    public void setPharmacyStatus(Integer pharmacyStatus) {
        this.pharmacyStatus = pharmacyStatus;
    }
    
    /**
     * 获取处方单号
     *
     * @return ORDER_NO - 处方单号
     */
    public String getOrderNo() {
        return orderNo;
    }
    
    /**
     * 设置处方单号
     *
     * @param orderNo
     *            处方单号
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }
    
    /**
     * 获取退费订单号
     *
     * @return REFUND_ORDER_NO - 退费订单号
     */
    public String getRefundOrderNo() {
        return refundOrderNo;
    }
    
    /**
     * 设置退费订单号
     *
     * @param refundOrderNo
     *            退费订单号
     */
    public void setRefundOrderNo(String refundOrderNo) {
        this.refundOrderNo = refundOrderNo == null ? null : refundOrderNo.trim();
    }
    
    /**
     * 获取处方单号hash值
     *
     * @return ORDER_NO_HASH_VAL - 处方单号hash值
     */
    public Long getOrderNoHashVal() {
        return orderNoHashVal;
    }
    
    /**
     * 设置处方单号hash值
     *
     * @param orderNoHashVal
     *            处方单号hash值
     */
    public void setOrderNoHashVal(Long orderNoHashVal) {
        this.orderNoHashVal = orderNoHashVal;
    }
    
    /**
     * 获取是否已被支付回调 1 是 0 否
     *
     * @return IS_HAD_CALL_BACK - 是否已被支付回调 1 是 0 否
     */
    public Integer getIsHadCallBack() {
        return isHadCallBack;
    }
    
    /**
     * 设置是否已被支付回调 1 是 0 否
     *
     * @param isHadCallBack
     *            是否已被支付回调 1 是 0 否
     */
    public void setIsHadCallBack(Integer isHadCallBack) {
        this.isHadCallBack = isHadCallBack;
    }
    
    /**
     * 获取总金额
     *
     * @return TOTAL_FEE - 总金额
     */
    public String getTotalFee() {
        return totalFee;
    }
    
    /**
     * 设置总金额
     *
     * @param totalFee
     *            总金额
     */
    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee == null ? null : totalFee.trim();
    }
    
    /**
     * 获取支付金额
     *
     * @return PAY_FEE - 支付金额
     */
    public String getPayFee() {
        return payFee;
    }
    
    /**
     * 设置支付金额
     *
     * @param payFee
     *            支付金额
     */
    public void setPayFee(String payFee) {
        this.payFee = payFee == null ? null : payFee.trim();
    }
    
    /**
     * 获取接诊科室代码
     *
     * @return DEPT_CODE - 接诊科室代码
     */
    public String getDeptCode() {
        return deptCode;
    }
    
    /**
     * 设置接诊科室代码
     *
     * @param deptCode
     *            接诊科室代码
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }
    
    /**
     * 获取接诊医生代码
     *
     * @return DOCTOR_CODE - 接诊医生代码
     */
    public String getDoctorCode() {
        return doctorCode;
    }
    
    /**
     * 设置接诊医生代码
     *
     * @param doctorCode
     *            接诊医生代码
     */
    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode == null ? null : doctorCode.trim();
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
     * @param doctorName
     *            医生名称
     */
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName == null ? null : doctorName.trim();
    }
    
    /**
     * 获取处方科室
     *
     * @return DEPT_NAME - 处方科室
     */
    public String getDeptName() {
        return deptName;
    }
    
    /**
     * 设置处方科室
     *
     * @param deptName
     *            处方科室
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }
    
    /**
     * 获取取药方式：物流配送 ；药店自取
     *
     * @return RECORD_TYPE - 取药方式：物流配送 ；药店自取
     */
    public String getRecordType() {
        return recordType;
    }
    
    /**
     * 设置取药方式：物流配送 ；药店自取
     *
     * @param recordType
     *            取药方式：物流配送 ；药店自取
     */
    public void setRecordType(String recordType) {
        this.recordType = recordType == null ? null : recordType.trim();
    }
    
    /**
     * 获取就诊卡类型
     *
     * @return CARD_TYPE - 就诊卡类型
     */
    public String getCardType() {
        return cardType;
    }
    
    /**
     * 设置就诊卡类型
     *
     * @param cardType
     *            就诊卡类型
     */
    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
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
     * @param cardNo
     *            就诊卡号
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    
    /**
     * 获取就诊人姓名
     *
     * @return PATIENT_NAME - 就诊人姓名
     */
    public String getPatientName() {
        return patientName;
    }
    
    /**
     * 设置就诊人姓名
     *
     * @param patientName
     *            就诊人姓名
     */
    public void setPatientName(String patientName) {
        this.patientName = patientName == null ? null : patientName.trim();
    }
    
    /**
     * 获取临床诊断
     *
     * @return DIAGNOSE - 临床诊断
     */
    public String getDiagnose() {
        return diagnose;
    }
    
    /**
     * 设置临床诊断
     *
     * @param diagnose
     *            临床诊断
     */
    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose == null ? null : diagnose.trim();
    }
    
    /**
     * 获取药店ID
     *
     * @return PHARMACY_ID - 药店ID
     */
    public String getPharmacyId() {
        return pharmacyId;
    }
    
    /**
     * 设置药店ID
     *
     * @param pharmacyId
     *            药店ID
     */
    public void setPharmacyId(String pharmacyId) {
        this.pharmacyId = pharmacyId == null ? null : pharmacyId.trim();
    }
    
    /**
     * 获取药店-分店ID
     *
     * @return PHARMACY_BRANCH_ID - 药店-分店ID
     */
    public String getPharmacyBranchId() {
        return pharmacyBranchId;
    }
    
    /**
     * 设置药店-分店ID
     *
     * @param pharmacyBranchId
     *            药店-分店ID
     */
    public void setPharmacyBranchId(String pharmacyBranchId) {
        this.pharmacyBranchId = pharmacyBranchId == null ? null : pharmacyBranchId.trim();
    }
    
    
    public String getReviewPhysiciansId() {
        return reviewPhysiciansId;
    }

    public void setReviewPhysiciansId(String reviewPhysiciansId) {
        this.reviewPhysiciansId = reviewPhysiciansId;
    }

    /**
     * 获取审查状态
     *
     * @return REVIEW_STATUS - 审查状态
     */
    public Integer getReviewStatus() {
        return reviewStatus;
    }
    
    /**
     * 设置审查状态
     *
     * @param reviewStatus
     *            审查状态
     */
    public void setReviewStatus(Integer reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
    
    /**
     * 获取审查意见
     *
     * @return REVIEW_MESSAGES - 审查意见
     */
    public String getReviewMessages() {
        return reviewMessages;
    }
    
    /**
     * 设置审查意见
     *
     * @param reviewMessages
     *            审查意见
     */
    public void setReviewMessages(String reviewMessages) {
        this.reviewMessages = reviewMessages == null ? null : reviewMessages.trim();
    }

    public Long getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Long reviewTime) {
        this.reviewTime = reviewTime;
    }

    /**
     * 获取是否发生异常 0 否 1是
     *
     * @return IS_EXCEPTION - 是否发生异常 0 否 1是
     */
    public Integer getIsException() {
        return isException;
    }
    
    /**
     * 设置是否发生异常 0 否 1是
     *
     * @param isException
     *            是否发生异常 0 否 1是
     */
    public void setIsException(Integer isException) {
        this.isException = isException;
    }
    
    /**
     * 获取是否处理成功 1是 0 否
     *
     * @return IS_HANDLE_SUCCESS - 是否处理成功 1是 0 否
     */
    public Integer getIsHandleSuccess() {
        return isHandleSuccess;
    }
    
    /**
     * 设置是否处理成功 1是 0 否
     *
     * @param isHandleSuccess
     *            是否处理成功 1是 0 否
     */
    public void setIsHandleSuccess(Integer isHandleSuccess) {
        this.isHandleSuccess = isHandleSuccess;
    }
    
    /**
     * 获取处理次数
     *
     * @return HANDLE_COUNT - 处理次数
     */
    public Integer getHandleCount() {
        return handleCount;
    }
    
    /**
     * 设置处理次数
     *
     * @param handleCount
     *            处理次数
     */
    public void setHandleCount(Integer handleCount) {
        this.handleCount = handleCount;
    }
    
    /**
     * 获取异常处理日志
     *
     * @return HANDLE_LOG - 异常处理日志
     */
    public String getHandleLog() {
        return handleLog;
    }
    
    /**
     * 设置异常处理日志
     *
     * @param handleLog
     *            异常处理日志
     */
    public void setHandleLog(String handleLog) {
        this.handleLog = handleLog == null ? null : handleLog.trim();
    }
    
    /**
     * 获取创建时间
     *
     * @return CREATE_TIME - 创建时间
     */
    public Long getCreateTime() {
        return createTime;
    }
    
    /**
     * 设置创建时间
     *
     * @param createTime
     *            创建时间
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    
    /**
     * 获取更新时间
     *
     * @return UPDATE_TIME - 更新时间
     */
    public Long getUpdateTime() {
        return updateTime;
    }
    
    /**
     * 设置更新时间
     *
     * @param updateTime
     *            更新时间
     */
    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
    
    public PrescriptionRecord() {
        super();
    }
    
    public PrescriptionRecord(String id) {
        super();
        this.id = id;
    }
    
    public PrescriptionRecord(String hospitalId, String orderNo, String cardNo, String pharmacyId) {
        super();
        this.hospitalId = hospitalId;
        this.orderNo = orderNo;
        this.cardNo = cardNo;
        this.pharmacyId = pharmacyId;
    }
    
    public String getPharmacyCode() {
        return pharmacyCode;
    }
    
    public void setPharmacyCode(String pharmacyCode) {
        this.pharmacyCode = pharmacyCode;
    }
    
    public String getPharmacyBranchCode() {
        return pharmacyBranchCode;
    }
    
    public void setPharmacyBranchCode(String pharmacyBranchCode) {
        this.pharmacyBranchCode = pharmacyBranchCode;
    }
    
    public String getMzFeeId() {
        return mzFeeId;
    }
    
    public void setMzFeeId(String mzFeeId) {
        this.mzFeeId = mzFeeId;
    }
    
    public String getPlatformModeType() {
        return platformModeType;
    }
    
    public void setPlatformModeType(String platformModeType) {
        this.platformModeType = platformModeType;
    }
    
    public String getTradeModeType() {
        return tradeModeType;
    }
    
    public void setTradeModeType(String tradeModeType) {
        this.tradeModeType = tradeModeType;
    }
    
    public String getHashTableName() {
        return hashTableName;
    }
    
    public void setHashTableName(String hashTableName) {
        this.hashTableName = hashTableName;
    }
    
    public String getDiscountFee() {
        return discountFee;
    }
    
    public void setDiscountFee(String discountFee) {
        this.discountFee = discountFee;
    }
    
    public void addLogInfo(String logMsg) {
        
        StringBuilder sbLog = new StringBuilder();
        if (handleCount != null) {
            sbLog.append("HandleCount:" + handleCount + ",");
        }
        sbLog.append("HandleDate:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        sbLog.append(",HandleMsg:" + logMsg);
        
        if (!StringUtils.isEmpty(handleLog)) {
            if (handleLog.endsWith(";")) {
                setHandleLog(handleLog + sbLog.toString());
            } else {
                setHandleLog(handleLog + ";" + sbLog.toString());
            }
        } else {
            setHandleLog(sbLog.toString());
        }
    }
    
    @Override
    public int compareTo(PrescriptionRecord o) {
        if (o == null) {
            return 1;
        }
        return Long.compare(o.getCreateTime(), this.createTime);
    }
    
}