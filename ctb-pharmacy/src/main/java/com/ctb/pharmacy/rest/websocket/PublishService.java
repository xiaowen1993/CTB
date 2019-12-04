/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年5月31日
 * Created by ckm
 */
package com.ctb.pharmacy.rest.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.ctb.pharmacy.rest.controller.PushMessageController;

/**
 * @ClassName: com.ctb.pharmacy.rest.websocket.a
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年5月31日 下午4:16:58
 */

@Component
public class PublishService {
	
	private static Logger logger = LoggerFactory.getLogger(PublishService.class);

	@Autowired
	StringRedisTemplate redisTemplate;

	/**
	 * @author ：发布方法
	 * @param channel 消息发布订阅 主题
	 * @param message 消息信息
	 */
	public void publish(String channel, Object message) {
		try {
			// 该方法封装的 connection.publish(rawChannel, rawMessage);
			redisTemplate.convertAndSend(channel, message);
		} catch (Exception e) {
			logger.error("class:[{}],method:[{}],errMsg:[{}]", PublishService.class, "publish", e.toString());
		}
	}
}