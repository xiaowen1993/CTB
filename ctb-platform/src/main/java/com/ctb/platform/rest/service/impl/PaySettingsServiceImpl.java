/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月26日
 * Created by hhy
 */
package com.ctb.platform.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctb.commons.entity.PaySettings;
import com.ctb.platform.rest.service.PaySettingsService;
import com.ctb.resources.mapper.biz.PaySettingsMapper;

/**
 * @ClassName: com.ctb.platform.rest.service.impl.PaySettingsServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年3月26日 下午9:49:20
 */

@Service
public class PaySettingsServiceImpl implements PaySettingsService {

	@Autowired
	private PaySettingsMapper paySettingsMapper;

	public PaySettings selectById(String id) {
		return paySettingsMapper.selectByPrimaryKey(id);
	}

}
