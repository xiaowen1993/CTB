/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月2日
 * Created by cwq
 */
package com.ctb.mobile.rest.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.BizConstant;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.DrugList;
import com.ctb.commons.entity.Hospital;
import com.ctb.commons.entity.UserInfo;
import com.ctb.framework.commons.config.SystemConfig;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.redis.RedisClient;
import com.ctb.mobile.api.AccesstokenClient;
import com.ctb.mobile.api.YXWOutClient;
import com.ctb.mobile.rest.entity.vo.WechatQRCodeVo;
import com.ctb.mobile.rest.service.DataPayMZFeeService;
import com.ctb.mobile.rest.service.DrugListService;
import com.ctb.mobile.rest.service.HospitalService;
import com.ctb.mobile.rest.service.UserInfoService;
import com.ctb.mobile.rest.service.UserService;
import com.ctb.mobile.sdk.WechatSDK;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

/**
 * @ClassName: com.ctb.mobile.rest.controller.OutSiteController
 * @Description: TODO(yxw数据交互类)
 * @author cwq
 * @date 2019年4月2日 上午11:00:14
 */
@RefreshScope
@RestController
@RequestMapping("/outsite")
public class CTBServerController {

	private static Logger logger = LoggerFactory.getLogger(CTBServerController.class);

	@Autowired
	public DrugListService drugListService;

	@Autowired
	public HospitalService hospitalService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private DataPayMZFeeService dataPayMZFeeService;
	
	@Autowired
	private AccesstokenClient accesstokenClient;

	@Autowired
	private RedisClient redisClient;

	@Autowired
	private YXWOutClient yxwOutClient;
	
//	@Autowired
//	private PayMZFeeRecordController payMZFeeRecordController;

	@ResponseBody
	@RequestMapping("/matchDrugs")
	public RespBody matchDrugs(@RequestParam(required = true, value = "openId") String openId,
			@RequestParam(required = true, value = "hospitalCode") String hospitalCode,
			@RequestParam(required = true, value = "itemIds") String[] itemIds) {

		Hospital hospital = hospitalService.getHospitalByCode(hospitalCode);

		if (hospital == null || hospital.getStatus() == null
				|| StringUtils.equals(hospital.getStatus(), BizConstant.HOSPITAL_INVALID_STATUS)) {
			return new RespBody(Status.PROMPT, "该医院未上线处方平台。");
		}
//        List<DrugList> drugLists = drugListService.matchDrugs(hospital.getId(), itemIds);

		JSONArray jsonArray = (JSONArray) redisClient.hget(CacheConstants.CACHE_HOSPITAL_DRUGLIST_HASH_KEY_PREFIX,
				hospital.getCode());

		if (!CollectionUtils.isEmpty(jsonArray)) {

			List<DrugList> drugLists = JSONObject.parseArray(jsonArray.toJSONString(), DrugList.class);

			Collection<DrugList> result = Collections2.filter(drugLists, new Predicate<DrugList>() {
				@Override
				public boolean apply(DrugList drugList) {

					return Arrays.asList(itemIds).contains(drugList.getHisDrugCode());
				}
			});

			if (result.size() != itemIds.length) {
				return new RespBody(Status.OK, false);
			}

		} else {
			return new RespBody(Status.OK, false);
		}

		return new RespBody(Status.OK, true);
	}


	/**
	 * 判断关注
	 * 
	 * @Title: isFocusOn
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月3日 下午5:43:30
	 * @param unionId
	 * @param opneId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/isFocusOn")
	public RespBody isFocusOn(@RequestParam(required = true, value = "unionId") String unionId,
			@RequestParam(required = true, value = "hospitalOpenId") String hospitalOpenId,
			@RequestParam(required = true, value = "hospitalCode") String hospitalCode,
			@RequestParam(required = false, value = "branchCode") String branchCode,
			@RequestParam(required = true, value = "mzfeeId") String mzfeeId,
			@RequestParam(required = true, value = "cardNo") String cardNo,
			@RequestParam(required = false, value = "cardType") String cardType) {
		try {
			String appId=SystemConfig.getStringValue("APP_ID");
			String appSecret=SystemConfig.getStringValue("APP_SECRET");
			//step1：获取accesstoken
			RespBody res = accesstokenClient.getAccessToken(appId, appSecret);
			String assesstoken = "";
			if (StringUtils.equals(res.getStatus().name(), Status.OK.name())) {
				assesstoken = res.getMessage().toString();
				if (StringUtils.isBlank(assesstoken)) {
					return new RespBody(Status.PROMPT, "获取assesstoken失败！");
				}
			} else {
				return new RespBody(Status.PROMPT, "获取assesstoken失败！");
			}

			// step2：判断用户关注状态
			boolean isfocusOn = userService.isFocuseOn(unionId);
			JSONObject jsonObject = new JSONObject();
			if (isfocusOn) {// 已关注
				// step3：判断是否存在userInfo信息
				UserInfo userInfo = new UserInfo();
				userInfo.setRefUnionId(unionId);
				userInfo.setHisOpenId(hospitalOpenId);
				userInfo.setCardNo(cardNo);
				boolean isUnquieUserInfo = userInfoService.isUnique(userInfo);
				if (!isUnquieUserInfo) {// 数据库不存在userInfo信息
					// step4：调用医享网接口获取用户绑卡信息
					RespBody respBody = yxwOutClient.loadMedicalCardInfo(hospitalOpenId,hospitalCode, cardNo);
					//RespBody respBody = yxwOutClient.loadMedicalCardInfo("U2FsdGVkX19hf++ld87hozY8TDDInJD/gD/pFKJZJjU=", "adadwerfwerw", "123456");
					if (Status.OK.equals(respBody.getStatus()) && respBody.getMessage() != null) {
						Map<String, String> getMap = (HashMap<String, String>) respBody.getMessage();
						if (getMap != null) {
							JSONObject object = (JSONObject) JSONObject.toJSON(getMap);
							userInfo = JSONObject.toJavaObject(object, UserInfo.class);
							// step5：userInfo数据入库
							String str = ObjectUtils.toString(userInfo, "");
							if (StringUtils.isNotBlank(str)) {
								userInfo.setRefUnionId(unionId);
								userInfo.setHisOpenId(hospitalOpenId);
								userInfoService.save(userInfo);
							}
						}
					}				
				}
				// step6：返回查看处方url进入订单流程
				StringBuffer stringBuffer=new StringBuffer();
				stringBuffer.append(SystemConfig.getStringValue("PRESCRIPTION_DETAIL_REQUEST_ADDRESS"));
				stringBuffer.append("&unionId=").append(unionId);
				stringBuffer.append("&hospitalCode=").append(hospitalCode);
				stringBuffer.append("&hospitalOpenId=").append(hospitalOpenId);
				stringBuffer.append("&branchHospitalCode=").append(branchCode);
				stringBuffer.append("&mzFeeId=").append(mzfeeId);
				stringBuffer.append("&cardNo=").append(cardNo);
				stringBuffer.append("&cardType=").append(cardType);
				jsonObject.put("status", 1);
				jsonObject.put("payMZFeeDeatilUrl", stringBuffer.toString());
				return new RespBody(Status.OK, jsonObject);

			} else {// 未关注或者已取消关注-进入引导关注流程
				if (StringUtils.equals(res.getStatus().name(), Status.OK.name())) {
					// step7：生成微信临时二维码
					JSONObject object = new JSONObject();
					object.put("hospitalCode", hospitalCode);
					object.put("unionId", unionId);
					object.put("hospitalOpenId", hospitalOpenId);
					object.put("mzfeeId", mzfeeId);
					object.put("cardNo", cardNo);
					Map<String, String> map = new HashMap<String, String>();// 自定义参数
					map.put("scene_str", JSONObject.toJSONString(object));

					WechatQRCodeVo wechatQRCodeVo = getWechatQRCodeVo(unionId, assesstoken, 259200, map);
					// step8：未关注状态，需返回引导关注所需关注二维码ticket
					if (wechatQRCodeVo != null) {
						jsonObject.put("status", 0);
						jsonObject.put("ticket", wechatQRCodeVo.getTicket());
						return new RespBody(Status.OK, jsonObject);
					} else {
						return new RespBody(Status.PROMPT, "获取ticket失败！");
					}
				} else {
					return res;
				}
			}
		} catch (Exception e) {
			logger.error("查询关注状态失败，查询异常！:::" + e.toString());
			return new RespBody(Status.ERROR, "查询关注状态失败！");
		}
	}

	
	/**
	 * 二维码ticket维护
	 */
	public WechatQRCodeVo getWechatQRCodeVo(String unionId, String assesstoken, int expire_seconds,
			Map<String, String> map) {
		WechatQRCodeVo wechatQRCodeVo = null;
		JSONObject val = (JSONObject) redisClient.hget(CacheConstants.CACHE_TICKET_HASH_KEY_PREFIX, unionId);
		if (val != null) {
			wechatQRCodeVo = JSONObject.toJavaObject(val, WechatQRCodeVo.class);
			long get_time = wechatQRCodeVo.getGetTime();
			long expires_in = 1000L * (Long.valueOf(wechatQRCodeVo.getExpireSeconds()) - 1000l);
			if ((System.currentTimeMillis() - get_time) > expires_in) {
				// 超时 - 重拿
				wechatQRCodeVo = WechatSDK.createTempTicket(assesstoken, 259200, map);
			}
			return wechatQRCodeVo;
		} else {
			wechatQRCodeVo = WechatSDK.createTempTicket(assesstoken, 259200, map);
			if (wechatQRCodeVo != null) {
				Map<String, Object> setMap = new HashMap<String, Object>();
				setMap.put(unionId, wechatQRCodeVo);
				redisClient.hmset(CacheConstants.CACHE_TICKET_HASH_KEY_PREFIX, setMap);
				return wechatQRCodeVo;
			}
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/mzDetail")
	public RespBody mzDetail(@RequestParam(required = true, value = "openId", defaultValue = "") String openId,
			@RequestParam(required = false, value = "hospitalOpenId", defaultValue = "") String hospitalOpenId,
			@RequestParam(required = true, value = "unionId", defaultValue = "") String unionId,
			@RequestParam(required = true, value = "hospitalCode", defaultValue = "") String hospitalCode,
			@RequestParam(required = false, value = "branchHospitalCode", defaultValue = "") String branchHospitalCode,
			@RequestParam(required = true, value = "cardNo", defaultValue = "") String cardNo,
			@RequestParam(required = true, value = "cardType", defaultValue = "") String cardType,
			@RequestParam(required = true, value = "mzFeeId", defaultValue = "") String mzFeeId) {
		try {
			RespBody body=dataPayMZFeeService.mzDetails(openId, hospitalOpenId, unionId, hospitalCode, branchHospitalCode, cardNo, cardType, mzFeeId);
		    return body;
		} catch (Exception e) {
			logger.error("time:{},调用 --mzDetail exception:{}",new Date().toString(),e.toString());
			return new RespBody(Status.ERROR, " 暂无处方信息！ 	");
		}
	}
}
