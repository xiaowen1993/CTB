/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月9日
 * Created by cwq
 */
package com.ctb.syncdata.commons.constants;

import com.ctb.framework.commons.config.SystemConfig;

/**
 * @ClassName: com.ctb.syncdata.commons.constants.BizConstants
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月9日 下午3:40:25
 */

public class BizConstants {
    /** 标准平台 */
    public static final int PLATFORM_MODE_TYPE_WECHAT_VAL = 1;
    /** 项目医院 */
    public static final int PLATFORM_MODE_TYPE_HIS_WECHAT_VAL = 7;
    
    /** 医院的状态 禁用 */
    public static final int HOSPITAL_INVALID_STATUS = 0;
    
    /** 医院的状态 启用 */
    public static final int HOSPITAL_VALID_STATUS = 1;
    
    /** 标准平台对接处方平台数据请求接口地址 */
    public static final String PLATFORM_SYNC_DATA_GATEWAY = SystemConfig.getStringValue("PLATFORM_SYNC_DATA_GATEWAY");
    
    /** 项目医院对接处方平台数据请求接口地址 */
    public static final String PROJECT_SYNC_DATA_GATEWAY = SystemConfig.getStringValue("PROJECT_SYNC_DATA_GATEWAY");
    
    /**
     * 0:所有/其他
     */
    public static final String ALL_CHANNEL_TYPE_VAL = "0";
    /**
     * 1:微信/微信公众号
     */
    public static final String WECHAT_CHANNEL_TYPE_VAL = "1";
    /**
     * 2:支付宝/支付宝服务窗
     */
    public static final String ALIPAY_CHANNEL_TYPE_VAL = "2";
}
