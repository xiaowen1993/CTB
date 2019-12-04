/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月29日
 * Created by hhy
 */
package com.ctb.commons.entity;

import java.io.Serializable;

/**
 * @ClassName: com.ctb.commons.entity.SimplePrescriptionRecord
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年4月29日 下午6:02:26
 */

public class SimplePrescriptionRecord  implements Serializable {
    
    
    /**
     *
     */
    	
    private static final long serialVersionUID = 8765004892352867855L;

    private String id;
    
    private String hospitalCode;
    
    private String mzFeeId;
    
    private Integer payStatus;
    
    private String unionId;
    
    private String openId;
    
    private String orderNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getMzFeeId() {
        return mzFeeId;
    }

    public void setMzFeeId(String mzFeeId) {
        this.mzFeeId = mzFeeId;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    
}
