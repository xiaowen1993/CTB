/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月16日
 * Created by ckm
 */
package com.ctb.pharmacy.rest.entity.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: com.ctb.pharmacy.rest.entity.vo.DrugDetail
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月16日 下午2:53:23
 */

public class DrugVo implements Serializable{

	
	/**
	 *
	 */
		
	private static final long serialVersionUID = 2064890734753783363L;

	private String orderNo;
	
	private String platformMode;
	
	private String mzfeeId;
	
	private String number;
	
	private String patientName;
	
	private String createTime;
	
	private List<DrugDetailVo> details;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPlatformMode() {
		return platformMode;
	}

	public void setPlatformMode(String platformMode) {
		this.platformMode = platformMode;
	}

	public String getMzfeeId() {
		return mzfeeId;
	}

	public void setMzfeeId(String mzfeeId) {
		this.mzfeeId = mzfeeId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<DrugDetailVo> getDetails() {
		return details;
	}

	public void setDetails(List<DrugDetailVo> details) {
		this.details = details;
	}

	public DrugVo() {
		super();
	}

	
	
	public DrugVo(String orderNo, String platformMode, String mzfeeId, String number, String patientName,
			String createTime) {
		super();
		this.orderNo = orderNo;
		this.platformMode = platformMode;
		this.mzfeeId = mzfeeId;
		this.number = number;
		this.patientName = patientName;
		this.createTime = createTime;
	}

	public DrugVo(String orderNo, String platformMode, String mzfeeId, String number, String patientName,
			String createTime, List<DrugDetailVo> details) {
		super();
		this.orderNo = orderNo;
		this.platformMode = platformMode;
		this.mzfeeId = mzfeeId;
		this.number = number;
		this.patientName = patientName;
		this.createTime = createTime;
		this.details = details;
	}
}
