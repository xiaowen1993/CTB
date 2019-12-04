/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月2日
 * Created by ckm
 */
package com.ctb.mobile.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.mobile.rest.service.BranchPharmacyService;

/**
 * @ClassName: com.ctb.mobile.rest.controller.BranchPharmacyController
 * @Description: TODO(用户端药房Controller)
 * @author ckm
 * @date 2019年4月2日 上午9:44:12
 */
@RefreshScope
@RestController
@RequestMapping("/branchPharmacy")
public class BranchPharmacyController {

	private static Logger logger = LoggerFactory.getLogger(BranchPharmacyController.class);

	@Autowired
	private BranchPharmacyService branchPharmacyService;

	@ResponseBody
	@RequestMapping(value = "/location")
	public RespBody location(@RequestParam(required = true, value = "hospitalCode") String hospitalCode,
			@RequestParam(required = false, value = "longitude", defaultValue = "") String longitude,
			@RequestParam(required = false, value = "latitude", defaultValue = "") String latitude,
			@RequestParam(required = false, value = "radius", defaultValue = "5000") String radius) {
		try {
			List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
			if (StringUtils.isNotBlank(latitude) && StringUtils.isNotBlank(longitude)) {
				jsonObjects = branchPharmacyService.loactionList(hospitalCode, latitude, longitude, radius);
				if(jsonObjects.size()==0) {
					jsonObjects = branchPharmacyService.listWithOutLatAndLng(hospitalCode);
					if(jsonObjects!=null&&!jsonObjects.isEmpty()&&jsonObjects.size()>0) {
						int num = (int) Math.random() * jsonObjects.size();
						jsonObjects = jsonObjects.subList(num, num + 1);					
					}
				}
			} else {
				jsonObjects = branchPharmacyService.listWithOutLatAndLng(hospitalCode);
				if(jsonObjects!=null&&!jsonObjects.isEmpty()&&jsonObjects.size()>0) {
					int num = (int) Math.random() * jsonObjects.size();
					jsonObjects = jsonObjects.subList(num, num + 1);					
				}
			}
			if (jsonObjects.size() > 0) {
				jsonObjects = branchPharmacyService.sortJsonObjects(jsonObjects);
				return new RespBody(Status.OK, jsonObjects);
			} else {
				return new RespBody(Status.PROMPT, "获取附近药房地理位置信息为空！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("获取附近药房地理位置信息失败,获取信息异常！:::" + e.toString());
			return new RespBody(Status.ERROR, "获取附近药房地理位置信息失败,获取信息异常！");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/search")
	public RespBody search(@RequestParam(required = true, value = "hospitalCode") String hospitalCode,
			@RequestParam(required = false, value = "longitude", defaultValue = "") String longitude,
			@RequestParam(required = false, value = "latitude", defaultValue = "") String latitude,
			@RequestParam(required = true, value = "name") String name,
			@RequestParam(required = false, value = "radius", defaultValue = "5000") String radius) {
		try {
			List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
			if (StringUtils.isNotBlank(latitude) && StringUtils.isNotBlank(longitude)) {
				jsonObjects = branchPharmacyService.loactionList(hospitalCode, latitude, longitude, radius);
			} else {
				jsonObjects = branchPharmacyService.listWithOutLatAndLng(hospitalCode);
			}
			if (jsonObjects.size() > 0) {
				jsonObjects = branchPharmacyService.searchJsonObjects(jsonObjects, name);
				if (jsonObjects.size() > 0) {
					return new RespBody(Status.OK, jsonObjects);
				} else {
					return new RespBody(Status.PROMPT, "搜索药房地理位置信息为空！");
				}
			} else {
				return new RespBody(Status.PROMPT, "搜索药房地理位置信息为空！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("搜索药房信息失败,获取信息异常！:::" + e.toString());
			return new RespBody(Status.ERROR, "搜索药房地理位置信息失败,获取信息异常！");
		}
	}

}
