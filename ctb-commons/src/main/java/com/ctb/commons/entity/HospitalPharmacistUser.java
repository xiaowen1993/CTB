/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年7月10日
 * Created by hhy
 */
package com.ctb.commons.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: com.ctb.commons.entity.HospitalPharmacistUser
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年7月10日 上午11:37:29
 */

@Table(name = "sys_hospital_pharmacist_user")
public class HospitalPharmacistUser  implements Serializable{
    
    /**
     *
     */
        
    private static final long serialVersionUID = -7208799648736819360L;

    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 医院ID
     */
    @Column(name = "HOSPITAL_CODE")
    private String hospitalCode;
    
    /**
     * 医院CODE
     */
    /*
     * @Column(name = "HOSPITAL_CODE") private String hospitalCode;
     */

    /**
     * 药房ID
     */
    @Column(name = "PHARMACIST_ID")
    private String pharmacistId;
    
    /**
     * 药房CODE
     */
    /*
     * @Column(name = "PHARMACY_CODE") private String pharmacyCode;
     */

    @Column(name = "STATUS")
    private String status;

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
     * @return STATUS
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getPharmacistId() {
        return pharmacistId;
    }

    public void setPharmacistId(String pharmacistId) {
        this.pharmacistId = pharmacistId;
    }

    /*
     * public String getHospitalCode() { return hospitalCode; }
     * 
     * public void setHospitalCode(String hospitalCode) { this.hospitalCode = hospitalCode; }
     * 
     * public String getPharmacyCode() { return pharmacyCode; }
     * 
     * public void setPharmacyCode(String pharmacyCode) { this.pharmacyCode = pharmacyCode; }
     */
      
    
    
}
