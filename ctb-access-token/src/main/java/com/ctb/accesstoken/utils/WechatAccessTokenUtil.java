package com.ctb.accesstoken.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ctb.accesstoken.cache.AccessTokenCache;
import com.ctb.accesstoken.cache.JSTicketCache;
import com.ctb.accesstoken.constants.AccesstokenConstants;
import com.ctb.accesstoken.entity.AccessTokenVo;
import com.ctb.accesstoken.entity.JSTicketVo;
import com.ctb.framework.commons.utils.HttpClientUtil;
import com.ctb.framework.commons.utils.SpringContextHolder;

/**
 * 
 * @ClassName: com.ctb.framework.commons.sdk.WechatSDK
 * @Description: TODO( 微信access_token工具类)
 * @author cwq
 * @date 2019年4月2日 下午7:47:03
 */
public class WechatAccessTokenUtil {
    
    private static Logger logger = LoggerFactory.getLogger(WechatAccessTokenUtil.class);
    
    /**
     * 
     * @Title: getAccessToken
     * @Description: TODO(去内存获取AccessToken，有超时机制)
     * @author cwq
     * @date 2019年4月3日 下午2:45:43
     * @param appId
     * @param appSecret
     * @return
     */
    public static synchronized String getAccessToken(String appId, String appSecret) {
        String accessToken = "";
        
        AccessTokenCache accessTokenCache = SpringContextHolder.getBean(AccessTokenCache.class);
        AccessTokenVo atVo = accessTokenCache.getTokenByAppId(appId);
        
        if (atVo != null) {
            // 判断超时否
            long get_time = atVo.getGetTime();
            long expires_in = 1000l * ( Long.valueOf(atVo.getExpiresTime()) / 2 );
            if ((System.currentTimeMillis() - get_time) > expires_in) {
                // 超时 - 重拿
                accessToken = getAccessTokenByWechat(appId, appSecret);
            } else {
                // 没超时
                logger.info("未超时，直接获取缓存的token:".concat(atVo.getAccessToken()));
                accessToken = atVo.getAccessToken();
            }
        } else {
            accessToken = getAccessTokenByWechat(appId, appSecret);
        }
        return accessToken;
    }
    
    /**
     * 
     * @Title: getAccessTokenByWechat
     * @Description: TODO(去微信服务器获取AccessToken)
     * @author cwq
     * @date 2019年4月3日 下午2:52:32
     * @param appId
     * @param appSecret
     * @return
     */
    private static String getAccessTokenByWechat(String appId, String appSecret) {
        String accessToken = "";

        StringBuffer url = new StringBuffer();
        url.append(AccesstokenConstants.WECHAT_GATEWAY).append("/cgi-bin/token?grant_type=client_credential")
                .append("&appid=").append(appId).append("&secret=").append(appSecret);
        logger.info("getAccessTokenByWechat.url:".concat(url.toString()));
        
        String rs = HttpClientUtil.getInstance().get(url.toString(), null);
        if (StringUtils.isNotBlank(rs)) {
            
            JSONObject jo = JSONObject.parseObject(rs);
            if (jo.containsKey("access_token")) {
                
                accessToken = jo.getString("access_token");
                String expiresTime = jo.getString("expires_in");
                
                AccessTokenVo accessTokenVo = new AccessTokenVo();
                accessTokenVo.setAccessToken(accessToken);
                accessTokenVo.setExpiresTime(expiresTime);
                accessTokenVo.setGetTime(System.currentTimeMillis());
                
                AccessTokenCache accessTokenCache = SpringContextHolder.getBean(AccessTokenCache.class);
                accessTokenCache.updateToken(appId, accessTokenVo);
                
            } else {
                logger.debug(rs.toString());
            }
            
        } else {
            logger.debug(rs.toString());
        }
        
        logger.debug("reget wx access token : {}", accessToken);
        return accessToken;
        
    }
    
    /**
     * 
     * @Title: getJSTicket
     * @Description: TODO(去内存获取JSTicket，有超时机制，有synchronized)
     * @author cwq
     * @date 2019年4月3日 下午2:55:04
     * @param appId
     * @param appSecret
     * @return
     */
    public static synchronized String getJSTicket(String appId, String appSecret) {
        
        String ticket = "";
        
        JSTicketCache jsTicketCache = SpringContextHolder.getBean(JSTicketCache.class);
        JSTicketVo jstVo = jsTicketCache.getJSTicket(appId);
        
        if (jstVo != null) {
            // 判断超时否
            long get_time = jstVo.getGetTime();
            long expires_in = 1000l * ( Long.valueOf(jstVo.getExpiresTime()) / 2 );
            if ((System.currentTimeMillis() - get_time) > expires_in) {
                // 超时 - 重拿
                ticket = getJSTicketByWechat(appId, appSecret);
            } else {
                // 没超时
                logger.info("未超时，直接获取缓存的token:".concat(jstVo.getTicket()));
                ticket = jstVo.getTicket();
            }
        } else {
            ticket = getJSTicketByWechat(appId, appSecret);
        }
        return ticket;
        
    }
    
    /**
     * 
     * @Title: getJSTicketByWechat
     * @Description: TODO(去微信服务器获取JSTicket)
     * @author cwq
     * @date 2019年4月3日 下午2:55:15
     * @param appId
     * @param appSecret
     * @return
     */
    private static String getJSTicketByWechat(String appId, String appSecret) {
        String ticket = "";
        // {"errcode":40013,"errmsg":"invalid appid"}
        StringBuffer url = new StringBuffer();
        url.append(AccesstokenConstants.WECHAT_GATEWAY).append("/cgi-bin/ticket/getticket?type=jsapi")
                .append("&access_token=").append(getAccessToken(appId, appSecret));
        
        String rs = HttpClientUtil.getInstance().get(url.toString(), null);
        if (StringUtils.isNotBlank(rs)) {
            
            JSONObject jo = JSONObject.parseObject(rs);
            if (jo.containsKey("ticket")) {
                
                ticket = jo.getString("ticket");
                String expiresTime = jo.getString("expires_in");
                
                JSTicketVo jsTicketVo = new JSTicketVo();
                jsTicketVo.setTicket(ticket);
                jsTicketVo.setExpiresTime(expiresTime);
                jsTicketVo.setGetTime(System.currentTimeMillis());
                
                JSTicketCache jsTicketCache = SpringContextHolder.getBean(JSTicketCache.class);
                jsTicketCache.updateJSTicket(appId, jsTicketVo);
                
            } else {
                logger.debug(rs.toString());
            }
            
        } else {
            logger.debug(rs.toString());
        }
        logger.debug("reget wx JSTicket : {}", ticket);
        return ticket;
    }
    
    /**
     * 获取 Wechat JSTicket 签名
     * 
     */
    public static String getJSTicketSign(String url, String ticket, String timestamp, String noncestr) {
        return getJSSDKParams(null, url, ticket, timestamp, noncestr).get("signature");
    }

    public static Map<String, String> getJSSDKParams(String appId, String url, String ticket) {
        return getJSSDKParams(appId, url, ticket, null, null);
    }

    /**
     * 获取 Wechat JSSDK config 所需要的参数, 返回值 key 全小写
     * 签名生成规则如下：参与签名的字段包括noncestr（随机字符串）, 有效的jsapi_ticket, timestamp（时间戳）, url（当前网页的URL，不包含#及其后面部分） 。
     * 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）后，使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串string1。
     * 这里需要注意的是所有参数名均为小写字符。对string1作sha1加密，字段名和字段值都采用原始值，不进行URL 转义。
     */
    public static Map<String, String> getJSSDKParams(String appId, String url, String ticket, String timestamp, String noncestr) {
        Map<String, String> params = new HashMap<>();
        params.put("url", url);
        params.put("jsapi_ticket", ticket);

        if (StringUtils.isBlank(timestamp)) {
            timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        }
        if (StringUtils.isBlank(noncestr)) {
            noncestr = com.ctb.framework.commons.utils.StringUtils.genGUID("@guid32");
        }

        params.put("timestamp", timestamp);
        params.put("noncestr", noncestr);

        StringBuilder sign = new StringBuilder();

        String[] keys = new String[params.size()];
        params.keySet().toArray(keys);

        Arrays.sort(keys);

        for (String k : keys) {
            String value = params.get(k);
            if (StringUtils.isNotBlank(value) && !"sign".equals(k)) {
                sign.append(k).append("=").append(params.get(k)).append("&");
            }
        }

        params.put("signature", DigestUtils.shaHex(StringUtils.stripEnd(sign.toString(), "&")));
        if (StringUtils.isNotBlank(appId)) {
            params.put("appid", appId);
        }

        return params;
    }
    
}
