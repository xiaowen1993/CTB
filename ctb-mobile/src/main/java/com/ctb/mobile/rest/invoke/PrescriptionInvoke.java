/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月29日
 * Created by cwq
 */
package com.ctb.mobile.rest.invoke;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ctb.commons.constants.BizConstant;
import com.ctb.commons.constants.TradeConstant;
import com.ctb.commons.entity.PrescriptionRecord;
import com.ctb.framework.commons.generator.HashSplitTableGenerator;
import com.ctb.framework.commons.utils.DateUtil;
import com.ctb.framework.commons.utils.SpringContextHolder;
import com.ctb.mobile.cache.PrescriptionRecordCache;
import com.ctb.mobile.cache.ReInvokeLockCache;
import com.ctb.mobile.rest.entity.payrefund.WechatPayAsynResponse;
import com.ctb.mobile.rest.entity.payrefund.WechatPayRefund;
import com.ctb.mobile.rest.entity.payrefund.WechatPayRefundResponse;
import com.ctb.mobile.rest.entity.vo.PayAsyncResponse;
import com.ctb.mobile.rest.service.PrescriptionService;
import com.ctb.mobile.rest.service.RefundService;

/**
 * @ClassName: com.ctb.mobile.rest.invoke.PrescriptionInvoke
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月29日 上午9:38:01
 */

public class PrescriptionInvoke {
    
    private static Logger logger = LoggerFactory.getLogger(PrescriptionInvoke.class);
    
    private static PrescriptionService prescriptionService = SpringContextHolder.getBean(PrescriptionService.class);
    private static RefundService refundService = SpringContextHolder.getBean(RefundService.class);
    
    private static PrescriptionRecordCache prescriptionRecordCache = SpringContextHolder
            .getBean(PrescriptionRecordCache.class);
    private static ReInvokeLockCache reInvokeLockCache = SpringContextHolder.getBean(ReInvokeLockCache.class);
    
    public static void prescriptionPayment(WechatPayAsynResponse payAsyncRsp) {
        
        PrescriptionRecord record = prescriptionService.findRecordByOrderNo(payAsyncRsp.getOrderNo());
        
        if (StringUtils.equals(payAsyncRsp.getResultCode(), TradeConstant.TRADE_STATE_SUCCESS)) {// 支付成功
            
            boolean hasLock = false;
            
            hasLock = reInvokeLockCache.getLock(payAsyncRsp.getOrderNo());
            
            // 重复回调 不处理
            if ( (record.getIsHadCallBack() != null && record.getIsHadCallBack().intValue() == BizConstant.IS_HAD_CALLBACK_YES) || !hasLock ) {
                logger.info("【处方外流订单支付回调】 第3方交易平台回调重复调用 不处理.orderNo:{}", new Object[] { payAsyncRsp.getOrderNo() });
                return;
            }
            //设置支付时间
            if (StringUtils.isNotBlank(payAsyncRsp.getTradeTime())) {
                Date payTime = DateUtil.StringToDate(payAsyncRsp.getTradeTime());
                if (payTime == null) {
                    logger.error(
                            "【处方外流订单支付回调】rx PayMent 支付回调:payMent success.orderNo:{},tradeTime:{},Parse tradeTime is error.use systime.");
                    record.setPayTime(System.currentTimeMillis());
                }
                record.setPayTime(payTime.getTime());
            } else {
                logger.error("【处方外流订单支付回调】rx PayMent 支付回调:payMent success.orderNo:{},tradeTime is null.use systime.");
                record.setPayTime(System.currentTimeMillis());
            }
            
            /** 第3方支付成功 记录第3方交易平台返回的订单号 ***/
            record.setAgtOrdNum(payAsyncRsp.getAgtOrderNo());
            // 加入支付成功日志信息
            StringBuffer infoSb = new StringBuffer();
            infoSb.append("【处方外流订单支付回调】第3方支付成功,第3方订单号-").append(payAsyncRsp.getAgtOrderNo()).append(",支付时间-")
                    .append(payAsyncRsp.getTradeTime());
            record.addLogInfo(infoSb.toString());
            record.setPayStatus(TradeConstant.PAY_ORDER_STATE_PAYMENT);
            record.setIsHadCallBack(BizConstant.IS_HAD_CALLBACK_YES);
            
            record.setHashTableName( HashSplitTableGenerator.getPrescriptionRecordHashTable(record.getOpenId(), true) );
            prescriptionService.updateStatusForPay(record);
        }
        
        if (StringUtils.equals(payAsyncRsp.getTradeState(), TradeConstant.TRADE_STATE_EXCEPTION)) {
            /** 支付失败的情况 不记录到数据库 只写入缓存 用于把支付失败的原因展示给用户 **/
            record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO);
            record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
            logger.info("【处方外流订单支付回调】第3方交易平台支付失败的情况 不记录到数据库 ,只写入缓存.orderNo:{}",
                    new Object[] { payAsyncRsp.getOrderNo() });
            
            prescriptionRecordCache.updatePrescriptionRecord(record);
            
        } else if (StringUtils.equals(payAsyncRsp.getTradeState(), TradeConstant.TRADE_STATE_REFUND)
                || StringUtils.equals(payAsyncRsp.getTradeState(), TradeConstant.TRADE_STATE_NOTPAY)
                || StringUtils.equals(payAsyncRsp.getTradeState(), TradeConstant.TRADE_STATE_CLOSED)) {
            // 不处理
        } else {
            
            // boolean isStartRefundThread = false;
            // 判断是否支付超时了，若是支付超时则进入退费
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(record.getCreateTime());
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            
            Long overtime = calendar.getTimeInMillis();
            long nowtime = System.currentTimeMillis();
            if (
            	(record.getPrescriptionStatus() != null && record.getPrescriptionStatus().intValue() == BizConstant.ORDER_STATE_NORMAL_PAY_TIMEOUT_CANCEL)
                || (overtime != null && overtime <= nowtime)
                ) {
                logger.info("【处方外流订单支付回调】该订单已经超过支付时间，已经进入超时状态，将发起退费 {}", record.getOrderNo());
                record.addLogInfo("【处方外流订单支付回调】该订单已经超过支付时间，已经进入超时状态，将发起退费 ");
                // TODO 第3方平台退费
                WechatPayRefund refund = (WechatPayRefund) prescriptionService.buildRefundInfo(record);
                WechatPayRefundResponse wechatPayRefundResponse = refundService.wechatPayRefund(refund);
                
                if (StringUtils.equals(wechatPayRefundResponse.getResultCode(), BizConstant.METHOD_INVOKE_SUCCESS)) {
                    refundSuccessHandle(record);
                } else {
                    logger.warn("invoke trade refund failure.orderNo:{}.exec handleHisException.", record.getOrderNo());
                    refundExceptionHandle(record);
                }
                
            } else {
                paySuccessHandle(record);
                
            }
        }
        
        // 释放锁
        reInvokeLockCache.delLock(payAsyncRsp.getOrderNo());
    }
    
    /**
     * 
     * @Title: paySuccessHandle
     * @Description: TODO(第三方缴费成功后修改状态处理)
     * @author cwq
     * @date 2019年5月5日 下午8:11:06
     * @param record
     */
    private static void paySuccessHandle(PrescriptionRecord record) {
        record.setPayStatus(TradeConstant.PAY_ORDER_STATE_PAYMENT);
        record.setPrescriptionStatus(BizConstant.ORDER_STATE_PAY_SUCCESS);
        record.setPharmacyStatus(BizConstant.PRESCRIPTION_STATE_NOT_DISPENSING);
        record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_NO);
        record.setIsHandleSuccess(BizConstant.HANDLED_SUCCESS);
        
        record.addLogInfo("第3方支付成功,状态变更为已缴费[ORDER_STATE_PAY_SUCCESS=1]");
        prescriptionService.updateStatusForPay(record);
    }
    
    /**
     * 
     * @Title: refundHandleException
     * @Description: TODO(第3方退费出现异常处理)
     * @author cwq
     * @date 2019年5月5日 下午7:02:50
     * @param record
     */
    private static void refundExceptionHandle(PrescriptionRecord record) {
        record.setPharmacyStatus(BizConstant.ORDER_STATE_EXCEPTION_REFUND);
        record.setIsException(BizConstant.IS_HAPPEN_EXCEPTION_YES);
        record.setIsHandleSuccess(BizConstant.HANDLED_FAILURE);
        record.setHandleCount(0);
        record.addLogInfo("调用第3方交易平台退费接口退费异常,状态变更为第3方交易平台退费异常[ORDER_STATE_EXCEPTION_REFUND=4];");
        
        prescriptionService.updateStatusForPay(record);
    }
    
    /**
     * 
     * @Title: refundSuccessHandle
     * @Description: TODO(第3方退费成功后处理)
     * @author cwq
     * @date 2019年5月5日 下午8:12:06
     * @param record
     */
    private static void refundSuccessHandle(PrescriptionRecord record) {
        record.setAgtRefundOrdNum(record.getAgtOrdNum());
        record.setPayStatus(TradeConstant.PAY_ORDER_STATE_REFUND);
        record.setRefundTime(System.currentTimeMillis());
        String oldLog = record.getHandleLog() == null ? "" : record.getHandleLog();
        record.setHandleLog(oldLog + " " + DateUtil.dateToString(new Date()) + " 第3方交易平台退费成功;");
        
        prescriptionService.updateStatusForPay(record);
    }
}
