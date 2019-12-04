package com.ctb.mobile.sdk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ctb.framework.commons.utils.HttpClientUtil;
import com.ctb.mobile.commons.constants.SDKPublicConstants;

/**
 * 
 * @ClassName: com.ctb.framework.commons.sdk.WechatComponentSDK
 * @Description: TODO(微信第三方平台SDK)
 * @author cwq
 * @date 2019年4月3日 下午5:53:35
 */
public class ComponentWechatSDK {
    
    private static Logger logger = LoggerFactory.getLogger(ComponentWechatSDK.class);
    
    /**
     * 获取微信授权跳转地址（snsapi_base）
     * 
     * @param appId
     * @param redirectUrl
     *            授权回调地址
     * @return 授权跳转地址
     * @throws UnsupportedEncodingException
     */
    public static String getComponentOAuth2(String appId, String componentAppid, String redirectUrl)
            throws UnsupportedEncodingException {
        return getOAuth2(appId, redirectUrl, SDKPublicConstants.WECHAT_OAUTH2_SCOPE_SNSAPI_BASE,
                SDKPublicConstants.WECHAT_COMPONENT_OAUTH2_STATE, componentAppid);
    }
    
    /**
     * 获取微信授权跳转地址
     * 
     * @param appId
     * @param redirectUrl
     *            授权回调地址
     * @param scope
     *            应用授权作用域 snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid）； snsapi_userinfo
     *            （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
     * @param state
     *            重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
     * @param componentAppid
     *            第三方代授权 appId
     * @return 授权跳转地址
     * @throws UnsupportedEncodingException
     */
    public static String getOAuth2(String appId, String redirectUrl, String scope, String state, String componentAppid)
            throws UnsupportedEncodingException {
        StringBuilder oauth2Url = new StringBuilder();
        
        oauth2Url.append(SDKPublicConstants.WECHAT_OPENAUTH_GATEWAY).append("/connect/oauth2/authorize?response_type=code");
        oauth2Url.append("&scope=").append(scope).append("&state=")
                .append(StringUtils.defaultString(state, "getOpenId"));
        oauth2Url.append("&appid=").append(appId);
        oauth2Url.append("&redirect_uri=").append(URLEncoder.encode(redirectUrl, "utf-8"));
        if (StringUtils.isNotBlank(componentAppid)) {
            oauth2Url.append("&component_appid=").append(componentAppid);
        }
        oauth2Url.append("#wechat_redirect");
        
        return oauth2Url.toString();
    }
    
    /**
     * 微信第三方带授权隐式获取OpenId
     * 
     * @param appId
     * @param code
     *            授权跳转后用request.getParameter("code")取到
     * @return { "scope":"snsapi_base", "openid":"o9ymntzFKpxrueMiYC81NAzTKg_8", "expires_in":7200,
     *         "refresh_token":"I6bpMScRgFdej7mrtQOQNfirCaptraRoSo6E5IQ38bRIQXA4mwteWFw1iFMSOHW29GI5ey7JFelB4tjkQGQMtXMp4HaOKjcKts7WqvkeVCM",
     *         "access_token":"bATcaZQFshdVnoRNtqhdJVtJP3JD26BmtC6VEgKR0CZPJGPfu824_2tTrJhx2CpBRxVuZ8SS6NEYzMiqLS07il-jXGGyLIfr1aodHUxIPJA"
     *         }
     * @throws IOException
     * @throws HttpException
     */
    public static String getOpenIdByComponent(String appId, String code, String componentAppId,
            String componentAccessToken){
        String openId = "";
        
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("grant_type", SDKPublicConstants.WECHAT_OAUTH2_GRANT_TYPE_AUTHORIZATION_CODE);
        params.put("appid", appId);
        params.put("component_appid", componentAppId);
        params.put("code", code);
        params.put("component_access_token", componentAccessToken);
        logger.info("getOpenId.params: {}", params);

        String rs = HttpClientUtil.getInstance().get(SDKPublicConstants.WECHAT_GATEWAY.concat("/sns/oauth2/component/access_token"),
                params);
        if (StringUtils.isNotBlank(rs)) {
            
            JSONObject jo = JSONObject.parseObject(rs);
            logger.info("getOpenIdByComponent.response: {}", jo);
            
            openId = jo.getString("openid");
            
            // unionid 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
            // openId = jo.getString("unionid");
        }
        
        return openId;
    }
    
    public static String getUserInfoItem(String openId, String accessToken, String itemKey)  {
        JSONObject jo = getUserInfo(openId, accessToken);
        if (jo != null) {
            return jo.getString(itemKey);
        } else {
            return null;
        }
    }
    
    public static JSONObject getUserInfo(String openId, String accessToken) {
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("access_token", accessToken);
        params.put("openid", openId);
        
        String rs = HttpClientUtil.getInstance().get(SDKPublicConstants.WECHAT_GATEWAY.concat("/cgi-bin/user/info"),
                params);
        if (StringUtils.isNotBlank(rs)) {
            /*
             * { "city":"广州", "country":"中国", "groupid":0, "headimgurl":
             * "http://wx.qlogo.cn/mmopen/Q3auHgzwzM48gy19Q7ojPWftT1nXwgyKhAkqISKKpk5CAJOBDJiaApKwSVwM3xGB0ovuxWmG3NQyh4jJFPfpZ09sic8UibBMViaRRFVVjIsdD4o/0",
             * "language":"zh_CN", "nickname":"homer.yang", "openid":"olMH9tuNZUtz32ObtBFQM2ypEmsk", "province":"广东",
             * "remark":"", "sex":1, "subscribe":1, "subscribe_time":1438677289 }
             */
            JSONObject jo = JSONObject.parseObject(rs);
            return jo;
        } else {
            return null;
        }
    }
}
