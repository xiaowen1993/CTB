/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月24日
 * Created by http://sp.yx129.cn ckm
 */
package com.ctb.mobile.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.BizConstant;
import com.ctb.framework.commons.generator.OrderNoGenerator;
import com.ctb.mobile.rest.entity.payrefund.WechatPayAsynResponse;
import com.ctb.mobile.rest.invoke.PrescriptionInvoke;

/**
 * @ClassName: com.ctb.mobile.rest.controller.PayMentCallBack
 * @Description: TODO(支付回调(微信) 支付完成后，微信会把相关支付结果和用户信息发送给商户，商户需要接收处理，并返回应答。
 *               对后台通知交互时，如果微信收到商户的应答不是成功或超时，微信认为通知失败，微信会通过一定的策略定期重新发起通知，尽可能提高通知的成功率，但微信不保证通知最终能成功。
 *               （通知频率为15/15/30/180/1800/1800/1800/1800/3600，单位：秒） 注意：同样的通知可能会多次发送给商户系统。商户系统必须能够正确处理重复的通知。
 *               推荐的做法是，当收到通知进行处理时，首先检查对应业务数据的状态，判断该通知是否已经处理过，如果没有处理过再进行处理，如果处理过直接返回结果成功。
 *               在对业务数据进行状态检查和处理之前，要采用数据锁进行并发控制，以避免函数重入造成的数据混乱。
 *               特别提醒：商户系统对于支付结果通知的内容一定要做签名验证,并校验返回的订单金额是否与商户侧的订单金额一致，防止数据泄漏导致出现“假通知”，造成资金损失。)
 * @author ckm
 * @date 2019年4月24日 上午9:08:48
 */
@RefreshScope
@RestController
@RequestMapping("/p")
public class PayMentAsyncCallBackController {
    
    private static Logger logger = LoggerFactory.getLogger(PayMentAsyncCallBackController.class);
    
    @RequestMapping(value = "/n")
    public void notifyWeChatPay(HttpServletRequest request, HttpServletResponse response, @RequestBody WechatPayAsynResponse payAsynResponse) {
        try {

            if (StringUtils.equals(payAsynResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
                logger.info("【支付回调】payMent success info", JSONObject.toJSONString(payAsynResponse));
                if (Integer.valueOf( OrderNoGenerator.getBizType(payAsynResponse.getOrderNo()) ).intValue() == BizConstant.BIZ_TYPE_PRESCRIPTION) {//处方外流
                    PrescriptionInvoke.prescriptionPayment(payAsynResponse);
                }

            } else if (StringUtils.equals(payAsynResponse.getResultCode(), BizConstant.METHOD_INVOKE_FAILURE)) {
                logger.info("【支付回调】payMent failure info", JSONObject.toJSONString(payAsynResponse));
            }
            
        } catch (Exception e) {
            logger.error("async callback is fial, errorMsg={}, " + "cause by: {}.",
                    new Object[] { e.getMessage(), e.getCause() });
        }
    }
    
    
}
