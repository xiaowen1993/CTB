/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月4日
 * Created by hhy
 */
package com.ctb.mobile.sdk;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.ctb.framework.commons.config.SystemConfig;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.utils.HttpClientUtil;
import com.ctb.framework.commons.utils.SpringContextHolder;
import com.ctb.mobile.api.AccesstokenClient;
import com.ctb.mobile.commons.constants.SDKPublicConstants;

/**
 * @ClassName: com.ctb.framework.commons.sdk.WechatPushMsgSDK
 * @Description: 微信消息推送
 * @author hhy
 * @date 2019年4月4日 上午9:24:26
 */

public class WechatPushMsgSDK {
    
    public static JSONObject sendPayMZFeeMsg(String appId, String appSecret, String openId, String url, String cardNo,
            String name, String mzFeeId) {
        
        Map<String, String> templateMap = new HashMap<String, String>();
        templateMap.put("openId", openId);
        templateMap.put("templateId", WechatConstant.DATAPAYMZFEE_TEMPLATEID);
        templateMap.put("url", url);
        templateMap.put("first", "您好！您有一项新的处方门诊缴费。");
        templateMap.put("keyword1", cardNo);
        templateMap.put("keyword2", name);
        templateMap.put("keyword3", mzFeeId + "处方订单");
        templateMap.put("remark", "处方将于24小时后失效，请尽快支付。如有任何疑问，请到门诊收费处咨询。");
        return WechatPushMsgSDK.sendTemplateMsg(SystemConfig.getStringValue("APP_ID"),
                SystemConfig.getStringValue("APP_SECRET"), templateMap);
    }
    
    /**
     * 
     * @Title: sendTemplateMsg
     * @Description: 发送模板消息
     * @author hhy
     * @date 2019年4月4日 下午3:02:29
     * @param appId
     * @param appSecret
     * @param map
     *            包含openId 、templateId、url、first、keyword、remark
     * @return
     */
    public static JSONObject sendTemplateMsg(String appId, String appSecret, Map<String, String> map) {
        String openId = map.get("openId");
        String templateId = map.get("templateId");
        String url = map.get("url");
        map.remove("openId");
        map.remove("templateId");
        map.remove("url");
        String msg = getTemplateMsg(openId, templateId, url, map);
        JSONObject result = null;
        
        AccesstokenClient accesstokenClient = SpringContextHolder.getBean(AccesstokenClient.class);
        RespBody res = accesstokenClient.getAccessToken(appId, appSecret);
        if (StringUtils.equals(res.getStatus().name(), Status.OK.name())) {
            String accessToken = res.getMessage().toString();
            System.out.println("accessToken:::" + accessToken);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(SDKPublicConstants.WECHAT_GATEWAY)
                    .append("/cgi-bin/message/template/send?access_token=").append(accessToken);
            String rs = HttpClientUtil.getInstance().post(stringBuffer.toString(), msg);
            if (StringUtils.isNotBlank(rs)) {
                result = JSONObject.parseObject(rs);
            }
        }
        
        return result;
    }
    
    /**
     * 
     * @Title: getTemplateMsg
     * @Description: 组装模板消息
     * @author hhy
     * @date 2019年4月4日 下午3:02:44
     * @param openId
     * @param templateId
     * @param url
     * @param map
     * @return
     */
    private static String getTemplateMsg(String openId, String templateId, String url, Map<String, String> map) {
        
        JSONObject json = new JSONObject();
        json.put("touser", openId);
        json.put("template_id", templateId);
        json.put("url", url);// 填写url可查看详情
        JSONObject dd = new JSONObject();
        for (String key : map.keySet()) {
            JSONObject temp = new JSONObject();
            temp.put("value", map.get(key));
            temp.put("color", "#00FFFF");
            dd.put(key, temp);
        }
        json.put("data", dd);
        return json.toJSONString();
    }
    
    /**
     * 
     * @Title: sendCustomMsg
     * @Description: 发送客服消息
     * @author hhy
     * @date 2019年4月4日 下午3:03:13
     * @param appId
     * @param appSecret
     * @param map-->包含openId
     *            和 content
     * @return
     */
    public static JSONObject sendCustomMsg(String appId, String appSecret, String openId, String content) {
        String msg = getCustomMsg(openId, content);
        JSONObject result = null;
        
        AccesstokenClient accesstokenClient = SpringContextHolder.getBean(AccesstokenClient.class);
        RespBody res = accesstokenClient.getAccessToken(appId, appSecret);
        if (StringUtils.equals(res.getStatus().name(), Status.OK.name())) {
            String accessToken = res.getMessage().toString();
            System.out.println("accessToken:::" + accessToken);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(SDKPublicConstants.WECHAT_GATEWAY).append("/cgi-bin/message/custom/send?access_token=")
                    .append(accessToken);
            String rs = HttpClientUtil.getInstance().post(stringBuffer.toString(), msg);
            System.out.println(rs);
            if (StringUtils.isNotBlank(rs)) {
                result = JSONObject.parseObject(rs);
            }
        }
        
        return result;
    }
    
    /**
     * 
     * @Title: getCustomMsg
     * @Description: 获取客服消息消息体
     * @author hhy
     * @date 2019年4月4日 下午3:03:36
     * @param openId
     * @param content
     * @return
     */
    private static String getCustomMsg(String openId, String content) {
        JSONObject json = new JSONObject();
        json.put("touser", openId);
        json.put("msgtype", "text");
        JSONObject conJson = new JSONObject();
        conJson.put("content", content);
        json.put("text", conJson);
        return json.toJSONString();
    }
    
}
