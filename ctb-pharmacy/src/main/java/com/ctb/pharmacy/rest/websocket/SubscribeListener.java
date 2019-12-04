/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年5月31日
 * Created by ckm
 */
package com.ctb.pharmacy.rest.websocket;

/**
 * @ClassName: com.ctb.pharmacy.rest.websocket.a
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年5月31日 下午4:21:30
 */

import java.io.IOException;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author 描述：订阅监听类
 */
public class SubscribeListener implements MessageListener {

	private static Logger logger = LoggerFactory.getLogger(SubscribeListener.class);
	
	private StringRedisTemplate stringRedisTemplate;

	private Session session;

	/**
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.redis.connection.MessageListener#onMessage(org.springframework.data.redis.connection.Message,
	 *      byte[])
	 */
	@Override
	public void onMessage(Message message, byte[] pattern) {
		String msg = new String(message.getBody());
		//System.out.println(new String(pattern) + "主题发布：" + msg);
		if (null != session) {
			try {
				session.getBasicRemote().sendText(msg);
			} catch (IOException e) {
				e.printStackTrace();
				logger.error("class:[{}],method:[{}],errMsg:[{}]", SubscribeListener.class, "onMessage", e.toString());
			}
		}
	}

	public StringRedisTemplate getStringRedisTemplate() {
		return stringRedisTemplate;
	}

	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}