/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月29日
 * Created by cwq
 */
package com.ctb.commons.constants;

/**
 * @ClassName: com.ctb.commons.constants.BizConstant
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年3月29日 下午3:30:32
 */

public class BizConstant {
    /**
     * 医院的状态 禁用
     */
    public static final String HOSPITAL_INVALID_STATUS = "0";
    
    /**
     * 医院的状态 启用
     */
    public static final String HOSPITAL_VALID_STATUS = "1";
    
    /**
     * openId的KEY 从session的属性中直接获取
     */
    public final static String OPENID = "openId";
    
    /**
     * 业务类型-外流处方订单
     */
    public final static Integer BIZ_TYPE_PRESCRIPTION = 4;
    
    /**
     * 订单类型-1支付
     */
    public static final int ORDER_TYPE_PAYMENT = 1;
    
    /**
     * 订单类型-2退费
     */
    public static final int ORDER_TYPE_REFUND = 2;
    
    /**
     * 订单类型-3部分退费
     */
    public static final int ORDER_TYPE_REFUND_PART = 3;
    
    /**
     * 业务状态： 待缴费
     */
    public static final int ORDER_STATE_NO_PAY = 0;
    
    /**
     * 业务状态： 已缴费
     */
    public static final int ORDER_STATE_PAY_SUCCESS = 1;
    
    /**
     * 业务状态：第三方缴费失败(要查询第三方接口，看有没有缴费)
     */
    public static final int ORDER_STATE_EXCEPTION_PAY = 2;
    
    /**
     * 业务状态：支付超过规定时长
     */
    public static final int ORDER_STATE_NORMAL_PAY_TIMEOUT_CANCEL = 3;
    
    /**
     * 业务状态: 退费异常(第三方缴费成功，订单超时退费，退费给用户失败/异常，这个需要走人工流程)
     */
    public static final int ORDER_STATE_EXCEPTION_REFUND = 4;

    /**
     * 后台退费:成功
     */
    public static final int ORDER_STATE_PLATFORM_REFUND_SUCCESS = 5;

    /**
     * 后台退费:失败
     */
    public static final int ORDER_STATE_PLATFORM_REFUND_FAIL = 6;
    
    /**
     * 配药状态：0-未配药
     */
    public static final int PRESCRIPTION_STATE_NOT_DISPENSING = 0;
    
    /**
     * 配药状态：1-已配药
     */
    public static final int PRESCRIPTION_STATE_DISPENSED = 1;
    
    /**
     * 配药状态：2-已发药
     */
    public static final int PRESCRIPTION_STATE_SEND_DISPENSED = 2;
    
    /**
     * 审核状态：0-未审核
     */
    public static final int REVIEW_STATE_UNAUDITED = 0;
    
    /**
     * 审核状态：1-已审核
     */
    public static final int REVIEW_STATE_AUDITED = 1;
    
    /**
     * 审核状态：2-未通过
     */
    public static final int REVIEW_STATE_AUDIT_NOT_PASS = 2;
    
    /**
     * 平台类型代码 ：aiPharmacy
     */
    public static final String MODE_TYPE_AI_PHARMACY = "aiPharmacy";
    
    /**
     * 平台类型值：25
     */
    public static final int MODE_TYPE_AI_PHARMACY_VAL = 25;
    
    /**
     * 支付类型类型值：1
     */
    public static final int TRADE_MODE_WECHAT_VAL = 1;// 微信支付
    
    /**
     * 支付类型类型代码：wechat
     */
    public static final String TRADE_MODE_WECHAT = "wechat";
    
    /**
     * 生成订单所在的服务器编号-默认值
     */
    public static final String ORDER_SERVER_NO = "00";
    
    /**
     * 调用方法执行结果是否成功
     */
    public static final String METHOD_INVOKE_RES_ISSUCCESS_KEY = "isSuccess";
    
    public static final String METHOD_INVOKE_SUCCESS = "SUCCESS";
    public static final String METHOD_INVOKE_FAILURE = "fail";
    public static final String METHOD_INVOKE_EXCEPTION = "exception";
    
    /**
     * 是否被第三方回调
     */
    public static final int IS_HAD_CALLBACK_YES = 1;
    public static final int IS_HAD_CALLBACK_NO = 0;
    
    /**
     * 处方订单存入缓存的过期时间（单位：秒）
     */
    public static final long PRESCRIPTION_RECORD_EXPIRES_TIME = 604800;
    
    /**
     * 简易订单存入缓存的过期时间（单位：秒）
     */
    public static final long SIMPLE_PRESCRIPTION_RECORD_EXPIRES_TIME = 253800;
    
    /**
     * 发生异常
     */
    public static final int IS_HAPPEN_EXCEPTION_YES = 1;
    
    /**
     * 未发生异常
     */
    public static final int IS_HAPPEN_EXCEPTION_NO = 0;
    /**
     * 异常处理/方法调用 已进行
     */
    public static final int HANDLED_HAD_HANDLED = 1;
    
    /**
     * 异常处理/方法调用 未进行
     */
    public static final int HANDLED_NO_HANDLED = 0;
    
    /**
     * 异常处理/方法调用 成功
     */
    public static final int HANDLED_SUCCESS = 1;
    
    /**
     * 异常处理/方法调用 失败
     */
    public static final int HANDLED_FAILURE = 0;
    
    /**
     * 异常处理/方法调用 超过最大次数(最大次数 3次)
     */
    public static final int HANDLED_TIME_OVER_MAXNUM = 3;
    
    /** 公共传递参数定义 **/
    public static final String URL_PARAM_CHAR_FIRST = "?";
    public static final String URL_PARAM_CHAR_ASSGIN = "=";
    public static final String URL_PARAM_CHAR_CONCAT = "&";
    public static final String URL_PARAM_ORDER_NO = "orderNo";
    public static final String URL_PARAM_OPEN_ID = "openId";
    
}
