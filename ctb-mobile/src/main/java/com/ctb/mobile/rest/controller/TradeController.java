/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月10日
 * Created by cwq
 */
package com.ctb.mobile.rest.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;
import org.springframework.data.redis.hash.DecoratingStringHashMapper;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.entity.PrescriptionRecord;
import com.ctb.framework.commons.config.SystemConfig;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.utils.HttpClientUtil;
import com.ctb.framework.commons.utils.JO;
import com.ctb.mobile.cache.PharmacyPaySettingCache;
import com.ctb.mobile.cache.PrescriptionRecordCache;
import com.ctb.mobile.rest.entity.payrefund.WechatPay;
import com.ctb.mobile.rest.entity.payrefund.WechatPayRefund;
import com.ctb.mobile.rest.entity.payrefund.WechatPayRefundResponse;
import com.ctb.mobile.rest.service.PrescriptionService;
import com.fasterxml.jackson.databind.util.BeanUtil;

/**
 * @ClassName: com.ctb.mobile.rest.controller.PayController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月10日 下午5:46:38
 */

@RefreshScope
@RestController
@RequestMapping("/trade")
public class TradeController {
    
    private static Logger logger = LoggerFactory.getLogger(TradeController.class);
    
    @Autowired
    private PharmacyPaySettingCache pharmacyPaySettingCache;
    
    @Autowired
    private PrescriptionRecordCache prescriptionRecordCache;
    
    @Autowired
    private PrescriptionService prescriptionService;
    
    @ResponseBody
    @RequestMapping("/getPaySettingCache")
    public RespBody getPaySettingCache(HttpServletRequest request, HttpServletResponse response) {
        
        String pharmacyCode = request.getParameter("pharmacyCode");
        JSONObject jsonObject = pharmacyPaySettingCache.getPharmacyPaySetting(pharmacyCode);
        
        if (jsonObject != null) {
            return new RespBody(Status.OK, jsonObject);
        } else {
            return new RespBody(Status.PROMPT, "pharmacyCode:" + pharmacyCode + ", query pay setting cache is null.");
        }
    }
    
    @ResponseBody
    @RequestMapping("/payment")
    public RespBody payment(HttpServletRequest request, HttpServletResponse response, String tradeOpenid,
            String orderNo) {
        RespBody respBody = new RespBody(Status.PROMPT, "网络繁忙，请稍受重试！");
        
        try {
            PrescriptionRecord record = prescriptionRecordCache.getPrescriptionRecordByOrderNo(orderNo);
            WechatPay pay = (WechatPay) prescriptionService.buildPayInfo(record);
            pay.setOpenId(tradeOpenid);
            
            String url = SystemConfig.getStringValue("TRADE_URL_PREFIX")
                    .concat(SystemConfig.getStringValue("TRADE_RESTFUL_PAYMENT_PATH"));
            
            HashMapper<WechatPay, String, String> mapper = new DecoratingStringHashMapper<WechatPay>(
                    new BeanUtilsHashMapper<WechatPay>(WechatPay.class));
            
            String res = HttpClientUtil.getInstance().post(url, mapper.toHash(pay));
            
            if (StringUtils.isNotBlank(res)) {
                respBody = JSONObject.parseObject(res, RespBody.class);
                
            }
            
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        
        return respBody;
    }
    
}
