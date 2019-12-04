/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月9日
 * Created by ckm
 */
package com.ctb.mobile.rest.entity.vo;

/**
 * @ClassName: com.ctb.mobile.rest.entity.vo.TextMessage
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月9日 下午2:41:58
 */

public class TextMessageVo extends BaseMessage {

	private String Content;
	
	private String MsgId;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
	
	
	
	
}
