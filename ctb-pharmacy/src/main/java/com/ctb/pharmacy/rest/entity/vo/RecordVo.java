/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年6月21日
 * Created by hhy
 */
package com.ctb.pharmacy.rest.entity.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: com.ctb.pharmacy.rest.entity.vo.RecordVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年6月21日 上午10:50:07
 */

public class RecordVo implements Serializable{
    
    
    /**
     *
     */
    	
    private static final long serialVersionUID = 1L;

    private String orderNo;
    
    private String hospitalCode;
    
    private String hospitalName;
    
    private String doctorCode;
    
    private String doctorName;
    
    private String deptCode;
    
    private String deptName;
    
    private String cardNo;
    
    private String patientName;
    
    private String mobile;
    
    private List<YXDrugDetailVo> details;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }


    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }


    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<YXDrugDetailVo> getDetails() {
        return details;
    }

    public void setDetails(List<YXDrugDetailVo> details) {
        this.details = details;
    }
    
    
}
