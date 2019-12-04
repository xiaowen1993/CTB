/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年5月28日
 * Created by ckm
 */
package com.ctb.pharmacy.rest.websocket;

/**
 * @ClassName: com.ctb.unsdg.platform.boot.a
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年5月28日 上午10:08:34
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 开启WebSocket支持
 * 
 * @ClassName: com.ctb.pharmacy.rest.websocket.WebSocketConfig
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年5月30日 下午4:25:44
 */
@Configuration
public class WebSocketConfig {

	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
}
