/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月12日
 * Created by ckm
 */
package com.ctb.mobile.rest.entity.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import com.ctb.commons.entity.DataPayMzFeeDetail;

/**
 * @ClassName: com.ctb.mobile.rest.entity.vo.PayMZFDetailVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月12日 上午9:04:16
 */

public class PayMZFDetailVo implements Serializable {

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
	private List<DataPayMzFeeDetail> payMZFDetails;

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getClinicTime() {
		return clinicTime;
	}

	public void setClinicTime(String clinicTime) {
		this.clinicTime = clinicTime;
	}
	
	public String getMzBillId() {
		return mzBillId;
	}

	public void setMzBillId(String mzBillId) {
		this.mzBillId = mzBillId;
	}

	public List<DataPayMzFeeDetail> getPayMZFDetails() {
		return payMZFDetails;
	}

	public void setPayMZFDetails(List<DataPayMzFeeDetail> payMZFDetails) {
		this.payMZFDetails = payMZFDetails;
	}
	
	public String getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(String discountFee) {
		this.discountFee = discountFee;
	}

	public PayMZFDetailVo() {
		super();
	}

	public PayMZFDetailVo(String unionId, String hospitalOpenId, String openId, String hospitalId, String hospitalName,
			String hospitalCode, String hospitalBranchId, String hospitalBranchCode, String mzFeeId, String cardNo,
			String cardType, String patientName, String sex, String age, String diagnosis, String deptCode,
			String deptName, String doctorName, String doctorCode, String totalFee, String payFee,
			List<DataPayMzFeeDetail> payMZFDetails,String mzBillId,String discountFee) {
		super();
		this.unionId = unionId;
		this.hospitalOpenId = hospitalOpenId;
		this.openId = openId;
		this.hospitalId = hospitalId;
		this.hospitalName = hospitalName;
		this.hospitalCode = hospitalCode;
		this.hospitalBranchId = hospitalBranchId;
		this.hospitalBranchCode = hospitalBranchCode;
		this.mzFeeId = mzFeeId;
		this.cardNo = cardNo;
		this.cardType = cardType;
		this.patientName = patientName;
		this.sex = sex;
		this.age = age;
		this.diagnosis = diagnosis;
		this.deptCode = deptCode;
		this.deptName = deptName;
		this.doctorName = doctorName;
		this.doctorCode = doctorCode;
		this.totalFee = totalFee;
		this.payFee = payFee;
		this.payMZFDetails = payMZFDetails;
		this.mzBillId=mzBillId;
		this.discountFee=discountFee;
	}

	public PayMZFDetailVo(Map<String, Object> map, List<DataPayMzFeeDetail> details, String hosptialOpenId,
			String openId, String unionId) {
		super();
		this.unionId = unionId == null ? null : unionId.trim();
		this.hospitalOpenId = hosptialOpenId == null ? null : hosptialOpenId.trim();
		this.openId = openId == null ? null : openId.trim();
		this.hospitalId = map.get("hospitalId") == null ? null : map.get("hospitalId").toString();
		this.hospitalName = map.get("hospitalName") == null ? null : map.get("hospitalName").toString();
		this.hospitalCode = map.get("hospitalCode") == null ? null : map.get("hospitalCode").toString();
		this.hospitalBranchId = map.get("branchId") == null ? null : map.get("branchId").toString();
		this.hospitalBranchCode = map.get("branchCode") == null ? null : map.get("branchCode").toString();
		this.mzFeeId = map.get("mzFeeId") == null ? null : map.get("mzFeeId").toString();
		this.cardNo = map.get("cardNo") == null ? null : map.get("cardNo").toString();
		this.cardType = map.get("cardType") == null ? null : map.get("cardType").toString();
		this.patientName = map.get("name") == null ? null : map.get("name").toString();
		this.sex = map.get("sex") == null ? null : map.get("sex").toString();
		this.age = map.get("age") == null ? null : map.get("age").toString();
		this.diagnosis = map.get("diagnosis") == null ? null : map.get("diagnosis").toString();
		this.deptCode = map.get("deptCode") == null ? null : map.get("deptCode").toString();
		this.deptName = map.get("deptName") == null ? null : map.get("deptName").toString();
		this.doctorName = map.get("doctorName") == null ? null : map.get("doctorName").toString();
		this.doctorCode = map.get("doctorCode") == null ? null : map.get("doctorCode").toString();
		this.totalFee = map.get("totalAmout") == null ? null : map.get("totalAmout").toString();
		this.payFee = map.get("payAmout") == null ? null : map.get("payAmout").toString();
		this.clinicTime=map.get("clinicTime") == null ? null : map.get("clinicTime").toString();
		this.payMZFDetails = details;
		this.mzBillId=map.get("mzBillId") == null ? null : map.get("mzBillId").toString();
	}

}
