package com.ctb.mobile.interceptor;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.ctb.commons.constants.BizConstant;
import com.ctb.framework.commons.config.SystemConfig;
import com.ctb.framework.commons.utils.MD5Utils;
import com.ctb.framework.commons.utils.NetworkUtil;
import com.ctb.mobile.sdk.WechatSDK;

/**
 * 
 * @ClassName: com.ctb.mobile.interceptor.OpenIdInterceptor
 * @Description: TODO(获取OpenId的拦截器)
 * @author cwq
 * @date 2019年4月3日 下午4:56:40
 */
public class OpenIdInterceptor implements HandlerInterceptor {
    
    private static Logger logger = LoggerFactory.getLogger(OpenIdInterceptor.class);
    
    private final static String openIdKey = SystemConfig.getStringValue("OPENID_PRIVATE_KEY");
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception exception) throws Exception {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String url = String.valueOf(request.getRequestURL());
        String queryString = request.getQueryString();
        HttpSession session = request.getSession();
        String ip = NetworkUtil.getIpAddress(request);
        String appId = request.getParameter("appId");
        String referer = request.getHeader("referer");
        String openId = "";
        boolean isPass = false;
        
        if (StringUtils.isBlank(appId)) {
            return isPass;
        }
        
        /** 1.request中是否存在openid **/
        if (StringUtils.isNotBlank(request.getParameter(appId.concat(BizConstant.OPENID)))) {
            // 过滤掉特殊字符
            openId = request.getParameter(appId.concat(BizConstant.OPENID)).replaceAll("%2B|%20| ", "+");
            String encryptedOpenIdInReq = request.getParameter(openId);
            String newEncrypt = getEncryptedOpenId(openId);
            // 如果request中有加密openId 则直接与当前加密的openId 比对即可
            if (StringUtils.isNotBlank(encryptedOpenIdInReq) && encryptedOpenIdInReq.equals(newEncrypt)) {
                isPass = true;
                appId = request.getParameter("appId");
                session.setAttribute(appId + BizConstant.OPENID, openId);
                session.setAttribute(openId, newEncrypt);
                logger.info("请求者IP:{}...openId={},appId={},referer={}", ip, openId, appId, referer);
                return verify(request, response, isPass, appId, openId);
            } else {
                // 获取session中的openId和加密后的openId进行比较，看是否是一样的
                String openIdInSession = (String) session.getAttribute(appId.concat(BizConstant.OPENID));
                // 当openIdInSession 不为空时 判断request域和session中的openId是否一致
                if (StringUtils.isNotBlank(openIdInSession) && openIdInSession.equals(openId)) {
                    // 判断session域是否是伪造的
                    String encryptedOpenId = (String) session.getAttribute(openId);
                    // 如果session中的加密openId 和 当前加密的openId是一致的，说明不是伪造的
                    if (StringUtils.isNotBlank(encryptedOpenId) && encryptedOpenId.equals(getEncryptedOpenId(openId))) {
                        isPass = true;
                        appId = request.getParameter("appId");
                        logger.info("OpenIdInterceptor.请求者IP:{}...openId={},appId={},referer={}", ip, openId, appId,
                                referer);
                        return verify(request, response, isPass, appId, openId);
                    }
                }
                // 若以上条件均不满足， 则通过第二种方式获取 ：从session获取
            }
        }
        /*** 2.session中是否存在openid **/
        if (session.getAttribute(appId.concat(BizConstant.OPENID)) != null
                && !"".equals(session.getAttribute(appId.concat(BizConstant.OPENID)))) {
            openId = (String) session.getAttribute(appId.concat(BizConstant.OPENID));
            // 比较session中的加密openId 和 当前加密的openId 是否一致
            String encryptedOpenId = (String) session.getAttribute(openId);
            if (StringUtils.isNotBlank(encryptedOpenId) && encryptedOpenId.equals(getEncryptedOpenId(openId))) {
                isPass = true;
                logger.info("OpenIdInterceptor.请求者IP:{}...openId={},appId={},referer={}", ip, openId, appId, referer);
                return verify(request, response, isPass, appId, openId);
            }
            // 若以上条件均不满足， 则通过第三种方式获取 ：从微信接口获取
        }
        /*** 3.调用微信接口获取 */
        // 3.1判断是否重复请求微信服务器拿openId的次数达到上限
        String getOpenIdTime = "0";
        String _getOpenIdTime = request.getParameter("getOpenIdTime");
        if (_getOpenIdTime != null) {
            getOpenIdTime = _getOpenIdTime;
        }
        int getOpenIdTimeInt = Integer.valueOf(getOpenIdTime);
        if (getOpenIdTimeInt > SystemConfig.getIntegerValue("MAX_GET_OPENID_TIMES").intValue()) {
            response.sendRedirect("http://ai-pharmacy.citybaby.com/mobile/500.html");
            return false;
        } else {
            String code = request.getParameter("code"); // code用来换取用户的OPENID
            String appSecret = SystemConfig.getStringValue("APP_SECRET"); // 处方平台开发者密码
            
            if (logger.isDebugEnabled()) {
                logger.debug("getOpenId.code: " + code);
            }
            
            // 当code不为空字符串，用code去换取openid
            if (StringUtils.isNotBlank(code)) {
                openId = WechatSDK.getOpenId(appId, appSecret, code);
                logger.info("用code换取到openId:{} ", openId);
                session.setAttribute(appId + BizConstant.OPENID, openId);
                session.setAttribute(openId, getEncryptedOpenId(openId));
                isPass = true;
                return verify(request, response, isPass, appId, openId);
            } else {
                /**
                 * 当code为空的时候,需要访问授权地址再获取code,之后会重定向回来再次进入拦截器， 此时已经有code值回传
                 */
                // 尝试获取openId次数
                String backUrl = url.concat("?").concat(queryString);
                getOpenIdTimeInt++;
                if (getOpenIdTimeInt == 1) {
                    backUrl = backUrl.concat("&getOpenIdTime=") + getOpenIdTimeInt;
                } else {
                    backUrl = backUrl.replaceAll("getOpenIdTime=\\d", "getOpenIdTime=" + getOpenIdTimeInt);
                }
                // 跳转微信授权获取 openId
                String redirectUrl = WechatSDK.getOAuth2(appId, backUrl);
                if (logger.isDebugEnabled()) {
                    logger.debug("getOpenId.redirectUrl: " + redirectUrl);
                }
                
                response.sendRedirect(redirectUrl);
                return false;
            }
        }
    }
    
    private Boolean verify(HttpServletRequest request, HttpServletResponse response, Boolean isPass, String appId,
            String openId) {
        Boolean flag = true;
        
        return flag;
    }
    
    /***
     * openId加密
     */
    private static String getEncryptedOpenId(String openId) {
        String encryptedOpenId = null;
        try {
            encryptedOpenId = MD5Utils.getMd5String32(openId.concat(openIdKey));
        } catch (NoSuchAlgorithmException e) {
            if (logger.isErrorEnabled()) {
                logger.error("encrypt openid is error, make transfer error. openId={}, errorMsg={}, " + "cause by: {}.",
                        new Object[] { openId, e.getMessage(), e.getCause() });
            }
        }
        return encryptedOpenId;
    }
    
}
