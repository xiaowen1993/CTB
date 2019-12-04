package com.ctb.mobile.sdk;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.utils.HttpClientUtil;
import com.ctb.framework.commons.utils.SpringContextHolder;
import com.ctb.mobile.api.AccesstokenClient;
import com.ctb.mobile.commons.constants.SDKPublicConstants;
import com.ctb.mobile.rest.entity.vo.WechatQRCodeVo;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;

/**
 * 
 * @ClassName: com.ctb.framework.commons.sdk.WechatSDK
 * @Description: TODO( 微信SDK)
 * @author cwq
 * @date 2019年4月2日 下午7:47:03
 */
public class WechatSDK {
    
    private static Logger logger = LoggerFactory.getLogger(WechatSDK.class);
    
    /**
     * 
     * @Title: getOAuth2
     * @Description: TODO(获取授权跳转地址)
     * @author cwq
     * @date 2019年4月3日 下午2:54:19
     * @param appId
     * @param redirectUrl
     *            授权回调地址
     * @return 授权跳转地址
     * @throws UnsupportedEncodingException
     */
    public static String getOAuth2(String appId, String redirectUrl) throws UnsupportedEncodingException {
        StringBuffer oauth2Url = new StringBuffer();
        oauth2Url.append(SDKPublicConstants.WECHAT_OPENAUTH_GATEWAY)
                .append("/connect/oauth2/authorize?response_type=code").append("&scope=snsapi_base&state=getOpenId")
                .append("&appid=").append(appId).append("&redirect_uri=")
                .append(URLEncoder.encode(redirectUrl, "utf-8")).append("#wechat_redirect");
        return oauth2Url.toString();
    }
    
    /**
     * 
     * @Title: getOpenId
     * @Description: TODO(微信隐式获取OpenId)
     * @author cwq
     * @date 2019年4月3日 下午2:53:42
     * @param appId
     * @param appSecret
     *            微信密钥
     * @param code
     *            授权跳转后用request.getParameter("code")取到
     * @return
     */
    public static String getOpenId(String appId, String appSecret, String code) {
        String openid = "";
        StringBuffer url = new StringBuffer();
        url.append(SDKPublicConstants.WECHAT_GATEWAY).append("/sns/oauth2/access_token?appid=").append(appId)
                .append("&secret=").append(appSecret).append("&code=").append(code)
                .append("&grant_type=authorization_code");
        /**
         * { "access_token":"ACCESS_TOKEN", "expires_in":7200, "refresh_token":"REFRESH_TOKEN", "openid":"OPENID",
         * "scope":"SCOPE" }
         **/
        String rs = HttpClientUtil.getInstance().get(url.toString(), null);
        if (StringUtils.isNotBlank(rs)) {
            JSONObject jo = JSONObject.parseObject(rs);
            openid = jo.getString("openid");
        }
        return openid;
    }
    
    /**
     * 
     * @Title: getJSTicketSign
     * @Description: TODO(WECHAT JSSDK 生成JS-SDK权限验证的签名
     *               生成签名之前必须先了解一下jsapi_ticket，jsapi_ticket是公众号用于调用微信JS接口的临时票据。正常情况下，jsapi_ticket的有效期为7200秒
     *               ，通过access_token来获取。由于获取jsapi_ticket的api调用次数非常有限，频繁刷新jsapi_ticket会导致api调用受限，影响自身业务，开发者必须在自己的服务全局缓存jsapi_ticket
     *               。 )
     * 
     * @1.获取access_token
     * @2.采用http GET方式请求获得jsapi_ticket https://api.weixin.qq.com/cgi-bin/ticket/getticket
     *           ?access_token=ACCESS_TOKEN&type=jsapi @成功返回如下JSON： { "errcode":0, "errmsg":"ok", "ticket":
     *           "bxLdikRXVbTPdHSM05e5u5sUoXNKd8-41ZO3MhKoyN5OfkWITDGgnr2fwJ0m9E8NYzWKVZvdVtaUgWvsdshFKA" ,
     *           "expires_in":7200 }
     * @3.获得jsapi_ticket之后，就可以生成JS-SDK权限验证的签名了。
     *                                          
     *                                          @author cwq
     * @date 2019年4月3日 下午2:55:45
     * @param ticket
     * @param url
     * @return Map<String, String> (url,jsapi_ticket,nonceStr,timestamp,signature)
     */
    public static Map<String, String> getJSTicketSign(String ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = createNonceStr();
        String timestamp = createTimestamp();
        String string1;
        String signature = "";
        
        // 注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=".concat(ticket).concat("&noncestr=").concat(nonce_str).concat("&timestamp=")
                .concat(timestamp).concat("&url=").concat(url);
        // SDKPublicArgs.logger.debug(string1);
        
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        ret.put("url", url);
        ret.put("jsapi_ticket", ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        
        return ret;
    }
    
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
    
    private static String createNonceStr() {
        return UUID.randomUUID().toString();
    }
    
    private static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
    
    /**
     * 生成签名
     * 签名生成的通用步骤如下：
        第一步，设所有发送或者接收到的数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），
        使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。
        特别注意以下重要规则：
        ◆ 参数名ASCII码从小到大排序（字典序）；
        ◆ 如果参数的值为空不参与签名；
        ◆ 参数名区分大小写；
        ◆ 验证调用返回或微信主动通知签名时，传送的sign参数不参与签名，将生成的签名与该sign值作校验。
        ◆ 微信接口可能增加字段，验证签名时必须支持增加的扩展字段
        第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值signValue。
     * 
     * @param params
     * @param key
     * @return
     * @throws Exception
     */
    public static String getSign(Map<String, String> params, String key) {
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
        if (StringUtils.isNotBlank(key)) {
            sign.append("key=").append(key);
        }
        logger.info("sign: {}", sign);

        return DigestUtils.md5Hex(sign.toString()).toUpperCase();
    }

    public static String getSha1Sign(String... params) {
        StringBuilder sign = new StringBuilder();

        Arrays.sort(params);

        sign.append(StringUtils.join(params));

        return DigestUtils.sha1Hex(sign.toString());
    }
    
    /**
     * 
     * @Title: checkSign
     * @Description: TODO(通过验证微信前面判断请求是否来自微信，防止他人恶意推送支付结果)
     * @author cwq
     * @date 2019年4月3日 下午2:57:41
     * @param params
     * @param key
     * @return
     * @throws Exception
     */
    public static boolean checkSign(Map<String, String> params, String key) {
        return params.get("sign").equals(getSign(params, key));
    }
    
    /**
     * 获取微信用户信息
     * 
     * @param appId
     * @param appSecret
     * @param openId
     * @return
     */
    public static JSONObject getUserInfo(String appId, String appSecret, String openId) {
        JSONObject result = null;
        // String accessToken = getAccessToken(appId, appSecret);
        AccesstokenClient accesstokenClient = SpringContextHolder.getBean(AccesstokenClient.class);
        RespBody res = accesstokenClient.getAccessToken(appId, appSecret);
        
        if (StringUtils.equals(res.getStatus().name(), Status.OK.name())) {
            String accessToken = res.getMessage().toString();
            
            StringBuffer url = new StringBuffer();
            url.append(SDKPublicConstants.WECHAT_GATEWAY).append("/cgi-bin/user/info?access_token=").append(accessToken)
                    .append("&openid=").append(openId).append("&lang=zh_CN");
            
            String rs = HttpClientUtil.getInstance().get(url.toString(), null);
            if (StringUtils.isNotBlank(rs)) {
                result = JSONObject.parseObject(rs);
            }
            
        }
        
        return result;
    }
    
    /**
     * 
     * @param accessToken
     *            接口访问凭证
     * @param expireSeconds
     *            二维码有效时间 单位为秒 最长 1800秒 （30天）
     * @param sceneId
     *            场景id 临时二维码场景值Id为32位非0整数 永久二维码的场景值ID取1~10000 也就是说永久二维码一共可以创建10000个 临时的多
     * @return
     */
    public static WechatQRCodeVo createTempTicket(String accessToken, int expireSeconds,
            Map<String, String> scene_str) {
        
        WechatQRCodeVo wechatQRCodeVo = null;
        
        if (StringUtils.isBlank(accessToken)) {
            return wechatQRCodeVo;
        }
        
        Map<String, Map<String, String>> sceneMap = new HashMap<String, Map<String, String>>();
        sceneMap.put("scene", scene_str);
        
        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("expire_seconds", expireSeconds);
        paramsMap.put("action_name", "QR_STR_SCENE");
        paramsMap.put("action_info", sceneMap);
        
        String requestUrl = SDKPublicConstants.WECHAT_GATEWAY.concat("/cgi-bin/qrcode/create?access_token=").concat(accessToken);
        String ticketJsonStr = HttpClientUtil.getInstance().post(requestUrl, JSONObject.toJSONString(paramsMap));
        
        JSONObject jo = JSONObject.parseObject(ticketJsonStr);
        if (jo.containsKey("ticket")) {
            
            wechatQRCodeVo = new WechatQRCodeVo();
            wechatQRCodeVo.setTicket(jo.getString("ticket"));
            wechatQRCodeVo.setExpireSeconds(jo.getString("expire_seconds"));
            wechatQRCodeVo.setGetTime(System.currentTimeMillis());
        }
        
        return wechatQRCodeVo;
    }
    
}
