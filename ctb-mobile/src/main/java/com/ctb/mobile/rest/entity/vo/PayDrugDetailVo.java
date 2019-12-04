/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月23日
 * Created by ckm
 */
package com.ctb.mobile.rest.entity.vo;

import java.io.Serializable;

/**
 * @ClassName: com.ctb.mobile.rest.entity.vo.PayDrugDetailVo
 * @Description: TODO(药品信息)
 * @author ckm
 * @date 2019年4月23日 下午4:47:39
 */

public class PayDrugDetailVo implements Serializable{
	
	private static final long serialVersionUID = 1925747552508223210L;

	/**
	 * 门诊id
	 */
	private String mzFeeId;
	/**
	 * 项目id
	 */
	private String itemId;
	/**
	 * 项目名称
	 */
	private String itemName;
	/**
	 * 项目类型
	 */
	private String itemType;
	/**
	 * 单位
	 */
	private String itemUnit;
	/**
	 * 单价
	 */
	private String itemPrice;
	/**
	 * 规格
	 */
	private String itemSpec;
	/**
	 * 数量
	 */
	private String itemNumber;
	/**
	 * 总价
	 */
	private String itemTotalFee;
	/**
	 * 用法
	 */
	private String usage;
	
	private String specification;
	/**
	 * 白名单药品名
	 */
	private String drugName;
	/**
	 *白名单 通用名
	 */
	private String cadn;

	public String getMzFeeId() {
		return mzFeeId;
	}

	public void setMzFeeId(String mzFeeId) {
		this.mzFeeId = mzFeeId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemUnit() {
		return itemUnit;
	}

	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemSpec() {
		return itemSpec;
	}

	public void setItemSpec(String itemSpec) {
		this.itemSpec = itemSpec;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getItemTotalFee() {
		return itemTotalFee;
	}

	public void setItemTotalFee(String itemTotalFee) {
		this.itemTotalFee = itemTotalFee;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getCadn() {
		return cadn;
	}

	public void setCadn(String cadn) {
		this.cadn = cadn;
	}

	public PayDrugDetailVo() {
		super();
	}

	public PayDrugDetailVo(String mzFeeId, String itemId, String itemName, String itemType, String itemUnit,
			String itemPrice, String itemSpec, String itemNumber, String itemTotalFee, String usage,
			String specification, String drugName, String cadn) {
		super();
		this.mzFeeId = mzFeeId;
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemType = itemType;
		this.itemUnit = itemUnit;
		this.itemPrice = itemPrice;
		this.itemSpec = itemSpec;
		this.itemNumber = itemNumber;
		this.itemTotalFee = itemTotalFee;
		this.usage = usage;
		this.specification = specification;
		this.drugName = drugName;
		this.cadn = cadn;
	}
	
	
}
