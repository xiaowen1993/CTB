/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年7月1日
 * Created by hhy
 */
package com.ctb.pharmacy.rest.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ctb.pharmacy.rest.entity.vo.YXDrugDetailVo;
import com.ctb.pharmacy.utils.DateUtil;

/**
 * @ClassName: com.ctb.pharmacy.rest.entity.RecordEntity
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年7月1日 上午10:33:14
 */

public class RecordEntity {
    private String discountFee;
    //private Long updateTime;
    private String updateTime;
    //private Long createTime;
    private String createTime;
    private String handleLog;
    //private Integer handleCount;
    private String handleCount;
    //private Integer isHandleSuccess;
    private String isHandleSuccess;
    //private Integer isException;
    private String isException;
    //private Date reviewTime;
    private String reviewTime;
    private String reviewMessages;
    //private Integer reviewStatus;
    private String reviewStatus;
    //private Integer reviewPhysiciansId;
    private String reviewPhysiciansId;
    private String pharmacyBranchCode;
    private String pharmacyBranchId;
    private String pharmacyCode;
    private String pharmacyId;
    private String diagnose;
    private String patientName;
    private String cardNo;
    private String cardType;
    private String recordType;
    private String deptName;
    private String doctorName;
    private String doctorCode;
    private String deptCode;
    private String payFee;
    private String totalFee;
    //private Integer isHadCallBack;
    private String isHadCallBack;
    //private Long orderNoHashVal;
    private String orderNoHashVal;
    private String refundOrderNo;
    private String orderNo;
    //private Integer pharmacyStatus;
    private String pharmacyStatus;
    //private Integer prescriptionStatus;
    private String prescriptionStatus;
    //private Integer payStatus;
    private String payStatus;
    //private Long refundTime;
    private String refundTime;
    //private Long payTime;
    private String payTime;
    private String agtRefundOrdNum;
    private String agtOrdNum;
    private String platformMode;
    private String tradeMode;
    public String openId;
    private String unionId;
    private String mzFeeId;
    private String hospitalBranchCode;
    private String hospitalBranchId;
    private String hospitalName;
    private String hospitalCode;
    private String hospitalId;
    
    public String getDiscountFee() {
        return discountFee;
    }
    
    public void setDiscountFee(String discountFee) {
        this.discountFee = discountFee;
    }
    
    public String getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Long updateTime) {
        if(updateTime==null) {
            this.updateTime = "";
        }else {
            this.updateTime = DateUtil.stampToDate(updateTime);
        }
    }
    
    public String getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Long createTime) {
        if(createTime==null) {
            
        }else {
            this.createTime = DateUtil.stampToDate(createTime);
        }
    }
    
    public String getHandleLog() {
        return handleLog;
    }
    
    public void setHandleLog(String handleLog) {
        this.handleLog = handleLog;
    }
    
    public String getHandleCount() {
        return handleCount;
    }
    
    public void setHandleCount(Integer handleCount) {
        if(handleCount==null) {
            this.handleCount = "";
        }else {
            this.handleCount = handleCount.toString();
        }
    }
    
    public String getIsHandleSuccess() {
        return isHandleSuccess;
    }
    
    public void setIsHandleSuccess(Integer isHandleSuccess) {
        if(isHandleSuccess==null) {
            this.isHandleSuccess = "";
        }else {
            this.isHandleSuccess = isHandleSuccess.toString();
        }
    }
    
    public String getIsException() {
        return isException;
    }
    
    public void setIsException(Integer isException) {
        if(isException==null) {
            this.isException = "";
        }else {
            this.isException = isException.toString();
        }
    }
    
    public String getReviewTime() {
        return reviewTime;
    }
    
    public void setReviewTime(Long reviewTime) {
        if(reviewTime==null) {
            this.reviewTime = "";
        }else {
            this.reviewTime = DateUtil.stampToDate(reviewTime);
        }
    }
    
    public String getReviewMessages() {
        return reviewMessages;
    }
    
    public void setReviewMessages(String reviewMessages) {
        this.reviewMessages = reviewMessages;
    }
    
    public String getReviewStatus() {
        return reviewStatus;
    }
    
    public void setReviewStatus(Integer reviewStatus) {
        if(reviewStatus == null) {
            this.reviewStatus = "";
        }else {
            this.reviewStatus = reviewStatus.toString();
        }
    }
    
    public String getReviewPhysiciansId() {
        return reviewPhysiciansId;
    }
    
    public void setReviewPhysiciansId(String reviewPhysiciansId) {
         this.reviewPhysiciansId = "";
    }
    
    public String getPharmacyBranchCode() {
        return pharmacyBranchCode;
    }
    
    public void setPharmacyBranchCode(String pharmacyBranchCode) {
        this.pharmacyBranchCode = pharmacyBranchCode;
    }
    
    public String getPharmacyBranchId() {
        return pharmacyBranchId;
    }
    
    public void setPharmacyBranchId(String pharmacyBranchId) {
        this.pharmacyBranchId = pharmacyBranchId;
    }
    
    public String getPharmacyCode() {
        return pharmacyCode;
    }
    
    public void setPharmacyCode(String pharmacyCode) {
        this.pharmacyCode = pharmacyCode;
    }
    
    public String getPharmacyId() {
        return pharmacyId;
    }
    
    public void setPharmacyId(String pharmacyId) {
        this.pharmacyId = pharmacyId;
    }
    
    public String getDiagnose() {
        return diagnose;
    }
    
    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }
    
    public String getPatientName() {
        return patientName;
    }
    
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    
    public String getCardNo() {
        return cardNo;
    }
    
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    
    public String getCardType() {
        return cardType;
    }
    
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    
    public String getRecordType() {
        return recordType;
    }
    
    public void setRecordType(String recordType) {
        this.recordType = recordType;
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
    
    public String getDeptCode() {
        return deptCode;
    }
    
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
    
    public String getPayFee() {
        return payFee;
    }
    
    public void setPayFee(String payFee) {
        this.payFee = payFee;
    }
    
    public String getTotalFee() {
        return totalFee;
    }
    
    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }
    
    public String getIsHadCallBack() {
        return isHadCallBack;
    }
    
    public void setIsHadCallBack(Integer isHadCallBack) {
        if(isHadCallBack == null){
            this.isHadCallBack = "";
        }else {
            this.isHadCallBack = isHadCallBack.toString();
        }
    }
    
    public String getOrderNoHashVal() {
        return orderNoHashVal;
    }
    
    public void setOrderNoHashVal(Long orderNoHashVal) {
        if(orderNoHashVal == null){
            this.orderNoHashVal = "";
        }else {
            this.orderNoHashVal = orderNoHashVal.toString();
        }
    }
    
    public String getRefundOrderNo() {
        return refundOrderNo;
    }
    
    public void setRefundOrderNo(String refundOrderNo) {
        this.refundOrderNo = refundOrderNo;
    }
    
    public String getOrderNo() {
        return orderNo;
    }
    
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    public String getPharmacyStatus() {
        return pharmacyStatus;
    }
    
    public void setPharmacyStatus(Integer pharmacyStatus) {
        if(pharmacyStatus == null){
            this.pharmacyStatus = "";
        }else {
            this.pharmacyStatus = pharmacyStatus.toString();
        }
    }
    
    public String getPrescriptionStatus() {
        return prescriptionStatus;
    }
    
    public void setPrescriptionStatus(Integer prescriptionStatus) {
        if(prescriptionStatus == null){
            this.prescriptionStatus = "";
        }else {
            this.prescriptionStatus = prescriptionStatus.toString();
        }
    }
    
    public String getPayStatus() {
        return payStatus;
    }
    
    public void setPayStatus(Integer payStatus) {
        if(payStatus == null){
            this.payStatus = "";
        }else {
            this.payStatus = payStatus.toString();
        }
    }
    
    public String getRefundTime() {
        return refundTime;
    }
    
    public void setRefundTime(Long refundTime) {
        if(refundTime==null) {
            this.refundTime = "";
        }else {
            this.refundTime = DateUtil.stampToDate(refundTime);
        }
    }
    
    public String getPayTime() {
        return payTime;
    }
    
    public void setPayTime(Long payTime) {
        if(payTime==null) {
            this.payTime = "";
        }else {
            this.payTime = DateUtil.stampToDate(payTime);
        }
    }
    
    public String getAgtRefundOrdNum() {
        return agtRefundOrdNum;
    }
    
    public void setAgtRefundOrdNum(String agtRefundOrdNum) {
        this.agtRefundOrdNum = agtRefundOrdNum;
    }
    
    public String getAgtOrdNum() {
        return agtOrdNum;
    }
    
    public void setAgtOrdNum(String agtOrdNum) {
        this.agtOrdNum = agtOrdNum;
    }
    
    public String getPlatformMode() {
        return platformMode;
    }
    
    public void setPlatformMode(String platformMode) {
        this.platformMode = platformMode;
    }
    
    public String getTradeMode() {
        return tradeMode;
    }
    
    public void setTradeMode(String tradeMode) {
        this.tradeMode = tradeMode;
    }
    
    public String getOpenId() {
        return openId;
    }
    
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    
    public String getUnionId() {
        return unionId;
    }
    
    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
    
    public String getMzFeeId() {
        return mzFeeId;
    }
    
    public void setMzFeeId(String mzFeeId) {
        this.mzFeeId = mzFeeId;
    }
    
    public String getHospitalBranchCode() {
        return hospitalBranchCode;
    }
    
    public void setHospitalBranchCode(String hospitalBranchCode) {
        this.hospitalBranchCode = hospitalBranchCode;
    }
    
    public String getHospitalBranchId() {
        return hospitalBranchId;
    }
    
    public void setHospitalBranchId(String hospitalBranchId) {
        this.hospitalBranchId = hospitalBranchId;
    }
    
    public String getHospitalName() {
        return hospitalName;
    }
    
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }
    
    public String getHospitalCode() {
        return hospitalCode;
    }
    
    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }
    
    public String getHospitalId() {
        return hospitalId;
    }
    
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
    
    public RecordEntity(String discountFee, String updateTime, String createTime, String handleLog, String handleCount,
            String isHandleSuccess, String isException, String reviewTime, String reviewMessages, String reviewStatus,
            String reviewPhysiciansId, String pharmacyBranchCode, String pharmacyBranchId, String pharmacyCode,
            String pharmacyId, String diagnose, String patientName, String cardNo, String cardType, String recordType,
            String deptName, String doctorName, String doctorCode, String deptCode, String payFee, String totalFee,
            String isHadCallBack, String orderNoHashVal, String refundOrderNo, String orderNo, String pharmacyStatus,
            String prescriptionStatus, String payStatus, String refundTime, String payTime, String agtRefundOrdNum,
            String agtOrdNum, String platformMode, String tradeMode, String openId, String unionId, String mzFeeId,
            String hospitalBranchCode, String hospitalBranchId, String hospitalName, String hospitalCode,
            String hospitalId) {
        super();
        this.discountFee = discountFee;
        this.updateTime = updateTime;
        this.createTime = createTime;
        this.handleLog = handleLog;
        this.handleCount = handleCount;
        this.isHandleSuccess = isHandleSuccess;
        this.isException = isException;
        this.reviewTime = reviewTime;
        this.reviewMessages = reviewMessages;
        this.reviewStatus = reviewStatus;
        this.reviewPhysiciansId = reviewPhysiciansId;
        this.pharmacyBranchCode = pharmacyBranchCode;
        this.pharmacyBranchId = pharmacyBranchId;
        this.pharmacyCode = pharmacyCode;
        this.pharmacyId = pharmacyId;
        this.diagnose = diagnose;
        this.patientName = patientName;
        this.cardNo = cardNo;
        this.cardType = cardType;
        this.recordType = recordType;
        this.deptName = deptName;
        this.doctorName = doctorName;
        this.doctorCode = doctorCode;
        this.deptCode = deptCode;
        this.payFee = payFee;
        this.totalFee = totalFee;
        this.isHadCallBack = isHadCallBack;
        this.orderNoHashVal = orderNoHashVal;
        this.refundOrderNo = refundOrderNo;
        this.orderNo = orderNo;
        this.pharmacyStatus = pharmacyStatus;
        this.prescriptionStatus = prescriptionStatus;
        this.payStatus = payStatus;
        this.refundTime = refundTime;
        this.payTime = payTime;
        this.agtRefundOrdNum = agtRefundOrdNum;
        this.agtOrdNum = agtOrdNum;
        this.platformMode = platformMode;
        this.tradeMode = tradeMode;
        this.openId = openId;
        this.unionId = unionId;
        this.mzFeeId = mzFeeId;
        this.hospitalBranchCode = hospitalBranchCode;
        this.hospitalBranchId = hospitalBranchId;
        this.hospitalName = hospitalName;
        this.hospitalCode = hospitalCode;
        this.hospitalId = hospitalId;
    }

    public RecordEntity() {
        super();
    }
    
}
