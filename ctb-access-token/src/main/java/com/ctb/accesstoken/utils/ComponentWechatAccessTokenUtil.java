package com.ctb.accesstoken.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ctb.accesstoken.cache.ComponentAccessTokenCache;
import com.ctb.accesstoken.constants.AccesstokenConstants;
import com.ctb.accesstoken.entity.ComponentAccessTokenVo;
import com.ctb.framework.commons.utils.HttpClientUtil;
import com.ctb.framework.commons.utils.SpringContextHolder;

/**
 * 
 * @ClassName: com.ctb.framework.commons.sdk.WechatComponentSDK
 * @Description: TODO(微信第三方平台access_token工具类)
 * @author cwq
 * @date 2019年4月3日 下午5:53:35
 */
public class ComponentWechatAccessTokenUtil {
    
    private static Logger logger = LoggerFactory.getLogger(ComponentWechatAccessTokenUtil.class);
    
  
    
    public static synchronized String getComponentAccessToken(String componentAppId, String componentAppSecret,
            String componentVerifyTicket) {

        String componentAccessToken = "";
        
        ComponentAccessTokenCache componentAccessTokenCache = SpringContextHolder.getBean(ComponentAccessTokenCache.class);
        ComponentAccessTokenVo catVo = componentAccessTokenCache.getTokenByComponentAppId(componentAppId);
        
        if (catVo != null) {
            // 判断超时否
            long get_time = catVo.getGetTime();
            long expires_in = 1000l * ( Long.valueOf(catVo.getExpiresTime()) / 2 );
            if ((System.currentTimeMillis() - get_time) > expires_in) {
                // 超时 - 重拿
                componentAccessToken = getComponentAccessTokenByWechat(componentAppId, componentAppSecret,
                         componentVerifyTicket);
            } else {
                // 没超时
                logger.info("未超时，直接获取缓存的token:".concat(catVo.getComponentAccessToken()));
                componentAccessToken = catVo.getComponentAccessToken();
            }
        } else {
            componentAccessToken = getComponentAccessTokenByWechat(componentAppId, componentAppSecret,
                     componentVerifyTicket);
        }
        return componentAccessToken;    
    }
    
    private static String getComponentAccessTokenByWechat(String componentAppId, String componentAppSecret,
            String componentVerifyTicket) {
        String componentAccessToken = "";
        
        Map<String, String> params = new HashMap<String, String>();
        // 出于安全考虑，在第三方平台创建审核通过后，微信服务器 每隔10分钟会向第三方的消息接收地址推送一次component_verify_ticket，用于获取第三方平台接口调用凭据
        params.put("component_verify_ticket", componentVerifyTicket);
        params.put("component_appid", componentAppId);
        params.put("component_appsecret", componentAppSecret);
        
        String rs = HttpClientUtil.getInstance().get(AccesstokenConstants.WECHAT_GATEWAY.concat("/cgi-bin/component/api_component_token"),
                params);
        if (StringUtils.isNotBlank(rs)) {
            
            JSONObject jo = JSONObject.parseObject(rs);
            if (jo.containsKey("access_token")) {
                
                componentAccessToken = jo.getString("component_access_token");
                String expiresTime = jo.getString("expires_in");
                
                ComponentAccessTokenVo componentAccessTokenVo = new ComponentAccessTokenVo();
                componentAccessTokenVo.setComponentAccessToken(componentAccessToken);
                componentAccessTokenVo.setExpiresTime(expiresTime);
                componentAccessTokenVo.setGetTime(System.currentTimeMillis());
                
                ComponentAccessTokenCache componentAccessTokenCache = SpringContextHolder.getBean(ComponentAccessTokenCache.class);
                componentAccessTokenCache.updateComponentToken(componentAppId, componentAccessTokenVo);
                
            } else {
                logger.error("response is : {}", JSONObject.toJSONString(rs));
            }
            
        } else {
            logger.error("response is null");
        }
        logger.debug("reget wx component access token : {}", componentAccessToken);
        return componentAccessToken;
    }
    
    public static String getComponentLoginPage(String componentAppId, String preAuthCode, String redirectUrl)
            throws UnsupportedEncodingException {
        return getComponentLoginPage(componentAppId, preAuthCode, redirectUrl, null);
    }
    
    public static String getComponentLoginPage(String componentAppId, String preAuthCode, String redirectUrl,
            String authType) throws UnsupportedEncodingException {
        StringBuilder oauth2Url = new StringBuilder();
        
        oauth2Url.append(AccesstokenConstants.WECHAT_MP_GATEWAY)
                .append("/cgi-bin/componentloginpage?component_appid=".concat(componentAppId));
        oauth2Url.append("&pre_auth_code=").append(preAuthCode);
        oauth2Url.append("&redirect_uri=").append(URLEncoder.encode(redirectUrl, "utf-8"));
        // 要授权的帐号类型，
        // 1则商户扫码后，手机端仅展示公众号、
        // 2表示仅展示小程序，
        // 3表示公众号和小程序都展示。
        // 如果为未制定，则默认小程序和公众号都展示。第三方平台开发者可以使用本字段来控制授权的帐号类型。
        if (StringUtils.isNotBlank(authType)) {
            oauth2Url.append("&auth_type=").append(authType);
        }
        
        return oauth2Url.toString();
    }
    
    public static String getComponentPreAuthCode(String componentAppId, String componentAccessToken){
        String componentPreAuthCode = "";
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("component_appid", componentAppId);
        logger.info("getComponentPreAuthCode.params: {}", params);
        
        String rs = HttpClientUtil.getInstance().get(AccesstokenConstants.WECHAT_GATEWAY.concat(
                "/cgi-bin/component/api_create_preauthcode?component_access_token=".concat(componentAccessToken)),
                params);
        if (StringUtils.isNotBlank(rs)) {
            
            JSONObject jo = JSONObject.parseObject(rs);
            logger.info("getComponentPreAuthCode.response: {}", jo);
            
            componentPreAuthCode = jo.getString("pre_auth_code");
        }
        
        return componentPreAuthCode;
    }
}
