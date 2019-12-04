/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月30日
 * Created by ckm
 */
package com.ctb.mobile.rest.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.entity.PaySettings;
import com.ctb.mobile.cache.PharmacyPaySettingCache;
import com.ctb.mobile.rest.service.PaySettingService;
import com.ctb.resources.mapper.biz.PaySettingsMapper;

/**
 * @ClassName: com.ctb.mobile.rest.service.impl.PharmacyPaySettingServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月30日 下午12:05:58
 */
@Service
public class PaySettingServiceImpl implements PaySettingService{

	@Autowired
	private PaySettingsMapper paySettingsMapper;
	
	@Autowired
	private PharmacyPaySettingCache pharmacyPaySettingCache;
 
	
	@Override
	public PaySettings queryPaySettings(String pharmacyId,String PharmacyCode) {
		PaySettings paySettings=null;
		JSONObject jsonObject = pharmacyPaySettingCache.getPharmacyPaySetting(pharmacyId, PharmacyCode);
        if(jsonObject!=null) {
        	paySettings=JSONObject.toJavaObject(jsonObject, PaySettings.class);
        }else {        	
        	Map<String, String> param=new HashMap<String, String>();
        	param.put("pharmacyId", pharmacyId);
        	paySettings= paySettingsMapper.findPaySettingByPharmacyId(param);
        }
		return paySettings;
	}

}
