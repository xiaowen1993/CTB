/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月16日
 * Created by ckm
 */
package com.ctb.pharmacy.rest.entity.vo;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.ctb.pharmacy.rest.entity.RecordEntity;

/**
 * @ClassName: com.ctb.pharmacy.rest.entity.vo.DrugVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月16日 下午2:50:03
 */

public class DrugDetailVo implements Serializable{

	
	/**
	 *
	 */
		
	private static final long serialVersionUID = -3681328439033201005L;

	private String id;

	/**
	 * 医院ID
	 */
	private String hospitalId;

	/**
	 * 医院CODE
	 */
	private String hospitalCode;

	/**
	 * 药品名称
	 */
	private String drugName;

	/**
	 * 药品编码（唯一通用编码，国家药物编码）
	 */
	private String drugCode;

	/**
	 * 药品通用名（China Approved Drug Names,简称：CADN）
	 */
	private String cadn;

	/**
	 * 批准文号
	 */
	private String approvalNo;

	/**
	 * 生产企业
	 */
	private String manufacturer;

	/**
	 * 规格
	 */
	private String specification;
	
	private String number;

	/**
	 * HIS自编药品编码
	 */
	private String hisDrugCode;

	/**
	 * 用法用量
	 */
	private String usage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public String getHospitalCode() {
		return hospitalCode;
	}

	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getCadn() {
		return cadn;
	}

	public void setCadn(String cadn) {
		this.cadn = cadn;
	}

	public String getApprovalNo() {
		return approvalNo;
	}

	public void setApprovalNo(String approvalNo) {
		this.approvalNo = approvalNo;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getHisDrugCode() {
		return hisDrugCode;
	}

	public void setHisDrugCode(String hisDrugCode) {
		this.hisDrugCode = hisDrugCode;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public DrugDetailVo() {
		super();
	}

    public DrugDetailVo(String id, String hospitalId, /* String hospitalCode, */ String drugName, String drugCode, String cadn,
			String approvalNo, String manufacturer, String specification, String number, String hisDrugCode,
			String usage) {
		super();
		this.id = id;
		this.hospitalId = hospitalId;
//		this.hospitalCode = hospitalCode;
		this.drugName = drugName;
		this.drugCode = drugCode;
		this.cadn = cadn;
		this.approvalNo = approvalNo;
		this.manufacturer = manufacturer;
		this.specification = specification;
		this.number = number;
		this.hisDrugCode = hisDrugCode;
		this.usage = usage;
	}
	
	
	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
        DrugVo drug = new DrugVo();
        drug.setOrderNo("123");
        
        RecordVo record = new RecordVo();
        record.setOrderNo("1111");
        
        RecordEntity entity = new RecordEntity();
        BeanUtils.copyProperties(drug, entity);
        
        
        System.out.println(entity.getOrderNo());
        System.out.println(drug.getOrderNo());
        
    }
	
	
	
}
