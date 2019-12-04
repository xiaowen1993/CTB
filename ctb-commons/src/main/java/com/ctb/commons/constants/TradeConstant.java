/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月28日
 * Created by cwq
 */
package com.ctb.commons.constants;

/**
 * @ClassName: com.ctb.commons.constants.TradeConstant
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月28日 下午8:43:38
 */

public class TradeConstant {
    
    public static final String TRADE_STATE_SUCCESS = "SUCCESS";//支付成功
    public static final String TRADE_STATE_REFUND = "REFUND";//退款
    public static final String TRADE_STATE_NOTPAY = "NOTPAY";//未支付
    public static final String TRADE_STATE_CLOSED = "CLOSED";//已关闭
    public static final String TRADE_STATE_EXCEPTION = "EXCEPTION";// 未知状态|异常状态， 需堕入轮询

    public static final String REFUND_STATE_SUCCESS = "SUCCESS";//退款成功
    public static final String REFUND_STATE_FAIL = "FAIL";//退款失败
    
    
    /**
     * 支付状态：0-未支付
     */
    public final static Integer PAY_ORDER_STATE_NOT_PAYMENT = 0;

    /**
     * 支付状态 ：1-已支付
     */
    public static final int PAY_ORDER_STATE_PAYMENT = 1;

    /**
     * 2-已退费
     */
    public static final int PAY_ORDER_STATE_REFUND = 2;
    
}
