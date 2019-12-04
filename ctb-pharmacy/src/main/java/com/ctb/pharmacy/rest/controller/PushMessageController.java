/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年5月28日
 * Created by ckm
 */
package com.ctb.pharmacy.rest.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.entity.PharmacyUser;
import com.ctb.pharmacy.cache.PharmacyUserCache;
import com.ctb.pharmacy.rest.websocket.WebsocketEndpoint;

@RefreshScope
@RestController
@RequestMapping("/pushMessage")
public class PushMessageController {

	private static Logger logger = LoggerFactory.getLogger(PushMessageController.class);

	@Autowired
	WebsocketEndpoint websocketEndpoint;

	@Autowired
	private PharmacyUserCache pharmacyUserCache;

	@ResponseBody
	@RequestMapping("/pushOrder")
	public void pushOrder(String pharmacyId, String message) {
		try {
			PharmacyUser pharmacyUser = new PharmacyUser();
			pharmacyUser.setPharmacyId(pharmacyId);
			JSONArray array = pharmacyUserCache.queryList(pharmacyUser);
			if (array != null && !array.isEmpty()) {
				for (Object object : array) {
					JSONObject jsonObject=(JSONObject) object;
					String id=(String) jsonObject.get("id");
					websocketEndpoint.sendMessage(message, id, pharmacyId);
				}
			}
		} catch (IOException e) {
			logger.error("class:[{}],method:[{}],errMsg:[{}]", PushMessageController.class, "pushOrder", e.toString());
			e.printStackTrace();
		}
	}

}