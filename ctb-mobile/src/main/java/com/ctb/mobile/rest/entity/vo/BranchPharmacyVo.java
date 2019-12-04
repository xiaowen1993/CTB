/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月23日
 * Created by ckm
 */
package com.ctb.mobile.rest.entity.vo;

import java.io.Serializable;

/**
 * @ClassName: com.ctb.mobile.rest.entity.vo.BranchPharmacyVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月23日 下午4:37:15
 */

public class BranchPharmacyVo implements Serializable{

	private static final long serialVersionUID = 6147070756398660422L;

	/**
	 * 药店名称
	 */
	private String name;
	
	/**
	 * 药店地址
	 */
	private String address;
	
	/**
	 * 纬度
	 */
	private String latitude;
	
	/**
	 * 经度
	 */
	private String longitude;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public BranchPharmacyVo() {
		super();
	}

	public BranchPharmacyVo(String name, String address, String latitude, String longitude) {
		super();
		this.name = name;
		this.address = address;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	
}
