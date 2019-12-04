/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月10日
 * Created by ckm
 */
package com.ctb.mobile.sdk;

/**
 * @ClassName: com.ctb.mobile.sdk.WechatConstant
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月10日 上午11:19:26
 */

public class WechatConstant {

	/**
	 * 关注成功消息提示
	 */
    public static String WECHAT_FOCUS_TIP = "非常感谢您的关注！";
    
    /**
	 * 请求消息类型：推送event
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型：subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型：unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	
	/**
	 * 微信扫码已关注
	 */
	public static final String EVENT_TYPE_SCAN="SCAN";
	
	/**
	 * 请求消息类型：文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型：文本text
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";
	
	/**
	 * 请求消息类型：地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
	
	/**
	 * 请求消息类型：音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	
	/**
	 * 请求消息类型：图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	
	/**
	 * 请求消息类型：链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 模版消息发送成功返回事件
	 */
	public static final String  REG_MESSAGE_TYPE_TEMPLATESENDJOBFINISH="TEMPLATESENDJOBFINISH";
	
	/**
     * 待缴费消息推送模板ID
     */
	public static final String DATAPAYMZFEE_TEMPLATEID ="i2MutbiOjD3r08N1-NY-nYCcJFizKLTyU5WH5XbCJU8";
	
	/**
     * 缴费成功模板ID
     */
    public static final String PAYSUCESS_TEMPLATEID ="";
    
    /**
     * 退费成功模板ID
     */
    public static final String REFUNDSUCCESS_TEMPLATEID ="";
    
    /**
     * 待取药模板ID
     */
    public static final String TOTAKEDRUGS_TEMPLATEID ="";
    
    /**
     * 取药成功模板ID
     */
    public static final String TAKEDRUGSSUCCESS_TEMPLATEID ="";

}
