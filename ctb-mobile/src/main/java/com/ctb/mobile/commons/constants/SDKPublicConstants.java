package com.ctb.mobile.commons.constants;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ctb.framework.commons.config.SystemConfig;

/**
 * 
 * @ClassName: com.ctb.framework.commons.sdk.SDKPublicArgs
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月2日 下午7:49:48
 */
public class SDKPublicConstants {
    public static Logger logger = LoggerFactory.getLogger(SDKPublicConstants.class);
    
    /**
     * 存储 Wechat Access token
     */
    public static Map<String, JSONObject> wechatAccessTokenMap = new HashMap<String, JSONObject>();
    
    /**
     * 存储 Wechat 第三方平台  Access token
     */
    public static Map<String, JSONObject> wechatComponentAccessTokenMap = new HashMap<String, JSONObject>();
    
    /**
     * 存储 Wechat JS ticket
     */
    public static Map<String, JSONObject> wechatJSTicketMap = new HashMap<String, JSONObject>();
    
    public static final String PLATFORM_WECHAT = "wechat";
    
    /**
     * openId 在 Cookie 里面的有效天数
     */
    public static final String OPENID_COOKIE_MAX_AGE = SystemConfig.getStringValue("OPENID_COOKIE_MAX_AGE");
    
    /** 
     * 微信 OpenAuth 网关
     */
    public static final String WECHAT_OPENAUTH_GATEWAY = SystemConfig.getStringValue("WECHAT_OPENAUTH_GATEWAY");
    
    
    public static final String WECHAT_MP_GATEWAY = SystemConfig.getStringValue("WECHAT_MP_GATEWAY");
    
    /** 
     * 微信网关
     */
    public static final String WECHAT_GATEWAY = SystemConfig.getStringValue("WECHAT_GATEWAY");
    
    public static final String CHARSET_UTF_8 = "utf-8";
    
    /** 
     * 授权访问令牌的授权类型
     */
    public static final String GRANT_TYPE = "authorization_code";
    
    /**
     * 微信公众号token缓存前缀
     */
    public static final String CACHE_WECHAT_TOKEN_KEY_PREFIX = "wechat.token.hash";
    
    /**
     * 存储 Wechat 第三方平台 component_verify_ticket  出于安全考虑，在第三方平台创建审核通过后，微信服务器 每隔10分钟会向第三方的消息接收地址推送一次component_verify_ticket，用于获取第三方平台接口调用凭据。
     */
    public static final String CACHE_WECHAT_COMPONENT_VERIFY_TICKET_KEY_PREFIX = "wechat.component.verify.ticket";
    
    /**
     * 第三方平台token缓存前缀
     */
    public static final String CACHE_COMPONENT_WECHAT_TOKEN_KEY_PREFIX = "component.wechat.token.hash";
    
    /**
     * 微信OAuth2授权scope - snsapi_base
     */
    public static final String WECHAT_OAUTH2_SCOPE_SNSAPI_BASE = "snsapi_base";

    /**
     * 微信OAuth2授权scope - snsapi_userinfo
     */
    public static final String WECHAT_OAUTH2_SCOPE_SNSAPI_USERINFO = "snsapi_userinfo";

    /**
     * 微信OAuth2授权grant_type - authorization_code
     */
    public static final String WECHAT_OAUTH2_GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";

    /**
     * 微信第三方授权OAuth2授权，重定向后会带上state参数，开发者可以填写任意参数值，最多128字节
     */
    public static final String WECHAT_COMPONENT_OAUTH2_STATE = "componentOAuth2";
    

    public static final String WECHAT_COMPONENT_TOKEN = SystemConfig.getStringValue("WECHAT_COMPONENT_TOKEN");
    public static final String WECHAT_COMPONENT_APPID = SystemConfig.getStringValue("WECHAT_COMPONENT_APPID");
    public static final String WECHAT_COMPONENT_APPSECRET = SystemConfig.getStringValue("WECHAT_COMPONENT_APPSECRET");
    public static final String WECHAT_COMPONENT_ENCODING_AES_KEY = SystemConfig.getStringValue("WECHAT_COMPONENT_ENCODING_AES_KEY");
}
