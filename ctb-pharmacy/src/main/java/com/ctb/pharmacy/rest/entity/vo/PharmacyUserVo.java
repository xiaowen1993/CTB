/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年5月31日
 * Created by ckm
 */
package com.ctb.pharmacy.rest.entity.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @ClassName: com.ctb.pharmacy.rest.entity.vo.PharmacyUserVo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年5月31日 上午9:12:40
 */

public class PharmacyUserVo implements Serializable {

	/**
	 *
	 */

	private static final long serialVersionUID = -6274840900180421681L;

	private String id;
	
	private String pharmacyId;
	
	private String pharmacyCode;
	
	private String email;

	private String name;
	 
	public String getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(String pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getPharmacyCode() {
        return pharmacyCode;
    }

    public void setPharmacyCode(String pharmacyCode) {
        this.pharmacyCode = pharmacyCode;
    }

    public PharmacyUserVo() {
		super();
	}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
