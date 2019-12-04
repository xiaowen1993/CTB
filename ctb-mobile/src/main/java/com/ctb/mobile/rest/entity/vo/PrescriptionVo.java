/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月23日
 * Created by ckm
 */
package com.ctb.mobile.rest.entity.vo;

import java.io.Serializable;
import java.util.List;
/**
 * @ClassName: com.ctb.mobile.rest.entity.vo.PrescriptionVo
 * @Description: TODO()U2FsdGVkX19hf++ld87hozY8TDDInJD/gD/pFKJZJjU=
 * @author ckm
 * @date 2019年4月23日 下午4:42:04
 */

public class PrescriptionVo implements Serializable{

	/**
	 *
	 */

	private static final long serialVersionUID = -6638979624355287252L;

	/**
	 * 患者唯一标识
	 */
	private String unionId;

	/**
	 * 医院公众号微信openId
	 */
	private String hospitalOpenId;

	/**
	 * 本公众号微信openId
	 */
	private String openId;

	/**
	 * 医院id
	 */
	private String hospitalId;

	/**
	 * 医院名称
	 */
	private String hospitalName;

	/**
	 * 医院code
	 */
	private String hospitalCode;

	/**
	 * 分院id
	 */
	private String hospitalBranchId;

	/**
	 * 分院code
	 */
	private String hospitalBranchCode;

	/**
	 * 门诊id
	 */
	private String mzFeeId;

	/**
	 * mzBillId
	 */
	private String mzBillId;

	/**
	 * 就诊卡号
	 */
	private String cardNo;

	/**
	 * 就诊卡类型
	 */
	private String cardType;

	/**
	 * 就诊人姓名
	 */
	private String patientName;

	/**
	 * 就诊人性别
	 */
	private String sex;

	/**
	 * 就诊人年龄
	 */
	private String age;

	/**
	 * 诊疗判断
	 */
	private String diagnosis;

	/**
	 * 接诊科室代码
	 */
	private String deptCode;

	/**
	 * 处方科室
	 */
	private String deptName;

	/**
	 * 医生名称
	 */
	private String doctorName;

	/**
	 * 接诊医生代码
	 */
	private String doctorCode;

	/**
	 * 总金额
	 */
	private String totalFee;

	/**
	 * 支付金额
	 */
	private String payFee;
	
	/**
	 * 应付金额
	 */
	private String payMent;

	/**
	 * 折扣金额
	 */
	private String discountFee;

	/**
	 * 门诊时间
	 */
	private String clinicTime;

	/**
	 * 药物明细列表
	 */
	private List<PayDrugDetailVo> payMZFDetails;
	/**
	 * 药房信息object
	 */
	private BranchPharmacyVo branchPharmacyVo;

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getHospitalOpenId() {
		return hospitalOpenId;
	}

	public void setHospitalOpenId(String hospitalOpenId) {
		this.hospitalOpenId = hospitalOpenId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
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

	public String getHospitalBranchId() {
		return hospitalBranchId;
	}

	public void setHospitalBranchId(String hospitalBranchId) {
		this.hospitalBranchId = hospitalBranchId;
	}

	public String getHospitalBranchCode() {
		return hospitalBranchCode;
	}

	public void setHospitalBranchCode(String hospitalBranchCode) {
		this.hospitalBranchCode = hospitalBranchCode;
	}

	public String getMzFeeId() {
		return mzFeeId;
	}

	public void setMzFeeId(String mzFeeId) {
		this.mzFeeId = mzFeeId;
	}

	public String getMzBillId() {
		return mzBillId;
	}

	public void setMzBillId(String mzBillId) {
		this.mzBillId = mzBillId;
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

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
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

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getPayFee() {
		return payFee;
	}

	public void setPayFee(String payFee) {
		this.payFee = payFee;
	}

	public String getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(String discountFee) {
		this.discountFee = discountFee;
	}

	public String getClinicTime() {
		return clinicTime;
	}

	public void setClinicTime(String clinicTime) {
		this.clinicTime = clinicTime;
	}

	public List<PayDrugDetailVo> getPayMZFDetails() {
		return payMZFDetails;
	}

	public void setPayMZFDetails(List<PayDrugDetailVo> payMZFDetails) {
		this.payMZFDetails = payMZFDetails;
	}

	public BranchPharmacyVo getBranchPharmacyVo() {
		return branchPharmacyVo;
	}

	public void setBranchPharmacyVo(BranchPharmacyVo branchPharmacyVo) {
		this.branchPharmacyVo = branchPharmacyVo;
	}
	
	public String getPayMent() {
		return payMent;
	}

	public void setPayMent(String payMent) {
		this.payMent = payMent;
	}

	public PrescriptionVo() {
		super();
	}


}
