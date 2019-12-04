package com.ctb.mobile.rest.entity.payrefund;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 微信支付异步回调
 * 
 * @author YangXuewen
 *
 */
@XmlRootElement(name="xml")
public class WechatPayAsynResponse extends PayAsynResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4621508727532642803L;

	/**
	 * appid
	 */
	@XmlElement(name="appid")
	private String appId;
	
	/**
	 * openid
	 */
	@XmlElement(name="openid")
	private String openId;
	
	/**
	 * trade_type
	 */
	@XmlElement(name="trade_type")
	private String tradeType;

	@XmlTransient
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	@XmlTransient
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	@XmlTransient
	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public WechatPayAsynResponse() {
		super();
	}

	public WechatPayAsynResponse(String resultCode, String resultMsg) {
		super();
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

}
