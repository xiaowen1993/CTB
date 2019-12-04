package com.ctb.mobile.rest.entity.vo;

/**
 * 
 * @ClassName: com.ctb.mobile.rest.entity.vo.WechatPayAsynResponse
 * @Description: TODO(微信支付异步回调)
 * @author cwq
 * @date 2019年4月28日 下午8:30:25
 */
public class WechatPayAsyncResponse extends PayAsyncResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4621508727532642803L;

	/**
	 * appid
	 */
	private String appId;
	
	/**
	 * openid
	 */
	private String openId;
	
	/**
	 * trade_type
	 */
	private String tradeType;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public WechatPayAsyncResponse() {
		super();
	}

	public WechatPayAsyncResponse(String resultCode, String resultMsg) {
		super();
		this.resultCode = resultCode;
		this.resultMsg = resultMsg;
	}

}
