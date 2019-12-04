package com.ctb.commons.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Table(name = "prescription_qrcode")
public class PrescriptionQrcode implements Serializable{
    
	private static final long serialVersionUID = 5156208675140752423L;
	
	@Id
    @Column(name = "ID")
    private String id;
    
	@Column(name = "ORDER_NO")
	private String orderNo;
	
	@Column(name = "VALUE")
	private String value;
	
	@Column(name = "CREATE_TIME")
	private Date createTime;
	
	@Column(name = "EXPIRATION_TIME")
	private Date expirationTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getExpirationTime() {
		return expirationTime;
	}

	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
	
    
}