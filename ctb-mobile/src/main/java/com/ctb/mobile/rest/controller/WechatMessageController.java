/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月10日
 * Created by ckm
 */
package com.ctb.mobile.rest.controller;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.User;
import com.ctb.commons.entity.UserInfo;
import com.ctb.framework.commons.config.SystemConfig;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.redis.RedisClient;
import com.ctb.framework.commons.utils.RequestUtil;
import com.ctb.framework.commons.utils.aes.WXBizMsgCrypt;
import com.ctb.mobile.api.YXWOutClient;
import com.ctb.mobile.commons.constants.SDKPublicConstants;
import com.ctb.mobile.rest.entity.vo.TextMessageVo;
import com.ctb.mobile.rest.service.DrugListService;
import com.ctb.mobile.rest.service.HospitalService;
import com.ctb.mobile.rest.service.UserService;
import com.ctb.mobile.sdk.WechatConstant;
import com.ctb.mobile.sdk.WechatPushMsgSDK;
import com.ctb.mobile.sdk.WechatConstant;
import com.ctb.mobile.sdk.WechatSDK;
import com.thoughtworks.xstream.XStream;

/**
 * @ClassName: com.ctb.mobile.rest.controller.WeChatSDKController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月10日 上午11:09:01
 */
@RefreshScope
@RestController
@RequestMapping("/wechatMsg")
public class WechatMessageController {

	private static Logger logger = LoggerFactory.getLogger(WechatMessageController.class);

	@Autowired
	public DrugListService drugListService;

	@Autowired
	public HospitalService hospitalService;

	@Autowired
	private UserService userService;

	@Autowired
	private RedisClient redisClient;

	@Autowired
	private YXWOutClient yxwOutClient;

	/**
	 * 
	 * @Title: WxHandeler
	 * @Description: TODO(微信对于公众号的一些事件回调处理方法)
	 * @author ckm
	 * @date 2019年4月8日 下午3:11:14
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/wxHandeler")
	public void wxHandeler(HttpServletRequest request, HttpServletResponse response) {
		try {
			String respMessage = processRequest(request, response);
			PrintWriter out = response.getWriter();
			out.println(respMessage);
			out.flush();
			out.close();
		} catch (Exception e2) {
			logger.error("关注/取消关注异常！:::" + e2.toString());
		}
	}

	/**
	 * 处理过程
	 * 
	 * @Title: processRequest
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月10日 上午11:33:27
	 * @param request
	 * @param response
	 * @return
	 */
	public String processRequest(HttpServletRequest request, HttpServletResponse response) {
		String respMessage = null;
		try {
			// step1：验证URL

			Map<String, String> paramsMap = RequestUtil.getRequestParams(request);
			logger.info("wechat message paramsMap：{}", paramsMap);
			if (!CollectionUtils.isEmpty(paramsMap)) {

				String signature = paramsMap.get("signature");
				String echostr = paramsMap.get("echostr");

				String timestamp = paramsMap.get("timestamp");
				String nonce = paramsMap.get("nonce");
				String token = SDKPublicConstants.WECHAT_COMPONENT_TOKEN;

				String sign = WechatSDK.getSha1Sign(token, nonce, timestamp);
				logger.info("sign: {}", sign);
				String mzFeeId = "";
				String cardNo = "";
				// 和URL中的签名比较是否相等
				if (signature.equals(sign)) {
					// 解密
					respMessage = echostr;

					// step2：提取微信推送参数-解密
					Map<String, String> requestMap = RequestUtil.parseXml(request);
					String fromUserName = requestMap.get("FromUserName");
					String toUserName = requestMap.get("ToUserName");
					String msgType = requestMap.get("MsgType");
					String eventType = requestMap.get("Event");
					String createTime = requestMap.get("CreateTime");
					String eventKey = requestMap.get("EventKey");
					String ticket = requestMap.get("Ticket");
					// step3：消息排重
					JSONObject val = (JSONObject) redisClient.hget(CacheConstants.CACHE_WECHAT_EVENT_HASH_KEY_PREFIX,
							fromUserName);
					if (val != null) {
						String key=fromUserName+createTime;
						String cacheKey=val.get("FromUserName")+(String) val.get("CreateTime");
						if (StringUtils.isNotBlank(key)&&key.equals(cacheKey)) {
							// 排重返回空字符串，微信不再重复推送，只推送一次
							return "";
						} 
					}
					// step4：处理消息事件
					if (WechatConstant.REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {// 如果为事件类型
						UserInfo userInfo = null;
						// setp4-1：获取微信用户信息

						User user = new User();
						user.setOpenId(fromUserName);

						if (WechatConstant.EVENT_TYPE_SUBSCRIBE.equals(eventType)) {// 处理订阅事件
							// step4-3：更新关注状态为：1-已关注
							user.setStatus("1");
							
							JSONObject userInfoJsonObject = WechatSDK.getUserInfo(SystemConfig.getStringValue("APP_ID"),
									SystemConfig.getStringValue("APP_SECRET"), fromUserName);
							user.setUnionId(userInfoJsonObject.get("unionid") + "");
							if (StringUtils.isNotBlank(eventKey)) {// 扫码推送消息事件
								// step4-2：调用外部接口获取处方平台绑卡实体userInfo信息
								System.out.println("eventKey:" + eventKey);
								String valStr = eventKey.replace("qrscene_", "");
								if (StringUtils.isNotBlank(valStr)) {
									userInfo = getUserInfo(valStr);
									userInfo.setRefUnionId(userInfoJsonObject.get("unionid") + "");
									JSONObject jsonObject = JSONObject.parseObject(valStr);
									if (!jsonObject.isEmpty()) {
										mzFeeId = (String) jsonObject.get("mzfeeId");
									}
								}
							}
						} else if (WechatConstant.EVENT_TYPE_UNSUBSCRIBE.equals(eventType)) {// 处理取消订阅事件
							// step4-3：更新关注状态为：0-已取消关注

							user.setStatus("0");
						} else if (WechatConstant.EVENT_TYPE_SCAN.equals(eventType)) {// 微信已关注
							return respMessage;
						}
						// step4-4：更新关注状态
						int res= 0 ;
						if(WechatConstant.EVENT_TYPE_UNSUBSCRIBE.equals(eventType)||WechatConstant.EVENT_TYPE_SUBSCRIBE.equals(eventType)) {						    
						    res = userService.saveAndUpdate(user, userInfo);
						}else if(WechatConstant.REG_MESSAGE_TYPE_TEMPLATESENDJOBFINISH.equals(eventType)) {
	                        //模版消息发送成功微信返回不处理、
						    System.out.println("模版消息发送成功微信返回不处理、");
						    return "";
	                    }
						if (res == 1) {
							Map<String, Object> setMap = new HashMap<String, Object>();
							setMap.put(fromUserName, requestMap);
							redisClient.hmset(CacheConstants.CACHE_WECHAT_EVENT_HASH_KEY_PREFIX, setMap, 300);
							// step4-5：关注成功公众号自动消息回复
							if ("1".equals(user.getStatus())) {
								String content = "欢迎" + userInfo.getName() + "来到" + userInfo.getHospitalName();
								WechatPushMsgSDK.sendCustomMsg(SystemConfig.getStringValue("APP_ID"),
										SystemConfig.getStringValue("APP_SECRET"), fromUserName, content);
								// respMessage = sendText(fromUserName, toUserName,
								// WechatConstant.WECHAT_FOCUS_TIP);

								StringBuffer stringBuffer = new StringBuffer();
								stringBuffer.append(SystemConfig.getStringValue("PRESCRIPTION_DETAIL_REQUEST_ADDRESS"));
								stringBuffer.append("&unionId=").append(userInfo.getRefUnionId());
								stringBuffer.append("&hospitalCode=").append(userInfo.getHospitalCode());
								stringBuffer.append("&hospitalOpenId=").append(userInfo.getHisOpenId());
								stringBuffer.append("&branchHospitalCode=").append(userInfo.getBranchCode());
								stringBuffer.append("&mzFeeId=").append(mzFeeId);
								stringBuffer.append("&cardNo=").append(userInfo.getCardNo());
								stringBuffer.append("&cardType=").append(userInfo.getCardType());

								WechatPushMsgSDK.sendPayMZFeeMsg(SystemConfig.getStringValue("APP_ID"),
										SystemConfig.getStringValue("APP_SECRET"), fromUserName,
										stringBuffer.toString(), userInfo.getCardNo(), userInfo.getName(), mzFeeId);

							}
						}
					} else if (WechatConstant.REQ_MESSAGE_TYPE_TEXT.equals(msgType)) {

						String content = "本公众号暂不支持对文字消息作出回复！";
						WechatPushMsgSDK.sendCustomMsg(SystemConfig.getStringValue("APP_ID"),
								SystemConfig.getStringValue("APP_SECRET"), fromUserName, content);

					} else if (WechatConstant.REQ_MESSAGE_TYPE_LOCATION.equals(msgType)) {

						String content = "本公众号暂不支持对理位置消息作出回复！";
						WechatPushMsgSDK.sendCustomMsg(SystemConfig.getStringValue("APP_ID"),
								SystemConfig.getStringValue("APP_SECRET"), fromUserName, content);

					} else if (WechatConstant.REQ_MESSAGE_TYPE_VOICE.equals(msgType)) {

						String content = "本公众号暂不支持对音频消息作出回复！";
						WechatPushMsgSDK.sendCustomMsg(SystemConfig.getStringValue("APP_ID"),
								SystemConfig.getStringValue("APP_SECRET"), fromUserName, content);

					} else if (WechatConstant.REQ_MESSAGE_TYPE_IMAGE.equals(msgType)) {

						String content = "本公众号暂不支持对图片消息作出回复！";
						WechatPushMsgSDK.sendCustomMsg(SystemConfig.getStringValue("APP_ID"),
								SystemConfig.getStringValue("APP_SECRET"), fromUserName, content);

					} else if (WechatConstant.REQ_MESSAGE_TYPE_LINK.equals(msgType)) {

						String content = "本公众号暂不支持对链接消息作出回复！";
						WechatPushMsgSDK.sendCustomMsg(SystemConfig.getStringValue("APP_ID"),
								SystemConfig.getStringValue("APP_SECRET"), fromUserName, content);

					}
				}

			}

		} catch (Exception e) {
			logger.error("关注/取消关注失败！:::" + e.toString());
		}
		return respMessage;
	}

	/**
	 * 自动回复消息
	 * 
	 * @Title: sendText
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月10日 下午12:06:39
	 * @param fromUserName
	 * @param toUserName
	 * @param content
	 * @return
	 */
	private String sendText(String fromUserName, String toUserName, String content) {
		TextMessageVo textMessage = new TextMessageVo();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(WechatConstant.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setContent(content);
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	private UserInfo getUserInfo(String valStr) {
		JSONObject jsonObject = JSONObject.parseObject(valStr);
		String hospitalCode = (String) jsonObject.get("hospitalCode");
		String hospitalOpenId = (String) jsonObject.get("hospitalOpenId");
		String cardNo = (String) jsonObject.get("cardNo");
		// step1:通过医享网接口查询绑卡信息
		RespBody respBody = yxwOutClient.loadMedicalCardInfo(hospitalOpenId, hospitalCode, cardNo);
		LinkedHashMap<String, String> linkedHashMap = (LinkedHashMap<String, String>) respBody.getMessage();
		JSONObject getObject = (JSONObject) JSONObject.toJSON(linkedHashMap);
		UserInfo userInfo = JSONObject.toJavaObject(getObject, UserInfo.class);
		userInfo.setHisOpenId(hospitalOpenId);
		return userInfo;
	}
}
