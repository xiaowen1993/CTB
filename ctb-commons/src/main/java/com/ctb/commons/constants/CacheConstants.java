/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月29日
 * Created by cwq
 */
package com.ctb.commons.constants;

/**
 * @ClassName: com.ctb.commons.constants.CacheConstants
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年3月29日 下午12:19:57
 */

public class CacheConstants {
    /**
     * 药房缓存key前缀
     */
    public static final String CACHE_PHARMACY_HASH_KEY_PREFIX = "pharmacy.hash";
    
    /**
     * 药房对应所有分店缓存key前缀
     */
    public static final String CACHE_BRANCH_PHARMACY_CODE_HASH_KEY_PREFIX = "branch.pharmacy.hash";
    
    /**
     * 药房-支付配置缓存key前缀
     */
    public static final String CACHE_PHARMACY_PAYSETTINGS_HASH_KEY_PREFIX = "pharmacy.paySetting.hash";
    
    /**
     * 医院缓存key前缀
     */
    public static final String CACHE_HOSPITAL_HASH_KEY_PREFIX = "hospital.hash";
    
    /**
     * 药品目录缓存key前缀
     */
    public static final String CACHE_HOSPITAL_DRUGLIST_HASH_KEY_PREFIX = "hospital.drugList.hash";
    
    /**
     *医院 药品黑白名单缓存key前缀
     */
    public static final String CACHE_HOSPITAL_DRUG_HASH_KEY_PREFIX = "hospital.drug.hash";
    
    /**
     * 黑名单药品缓存key前缀
     */
    public static final String CACHE_HOSPITAL_DRUGBLACKLIST_KEY_HASH_PREFIX = "hospital.drugBlackList.hash";
    
    /**
     * 药房分店location缓存key前缀
     */
    public static final String CACHE_LOCATION_HASH_KEY_PREFIX = "location:";
    
    /**
     * 关注ticket缓存前缀
     */
    public static final String CACHE_TICKET_HASH_KEY_PREFIX = "ticket";
    
    /**
     * 微信订阅事件缓存key前缀
     */
    public static final String CACHE_WECHAT_EVENT_HASH_KEY_PREFIX = "event";
    
    /**
     * 门诊待缴费列表缓存
     */
    public static final String CACHE_HOSPITAL_CARDNO_MZFEE_HASH_KEY_PREFIX = "hospital.cardNo.MZFee.hash";
    
    /**
     * 缓存key的分割符
     */
    public static final String CACHE_KEY_SPLIT_CHAR = ":";
    
    /**
	 * 支付回调重复锁缓存key
	 */
	public static final String PAY_CALLBACK_LOCK = "pay.callback.lock";
	
	/**
	 * 订单缓存前缀
	 */
	public static final String PRESCRIPTION_RECORD_HASH_KEY_PREFIX="prescription.record.hash";

	   /**
     * 订单缓存前缀
     */
    public static final String SIMPLE_PRESCRIPTION_RECORD_HASH_KEY_PREFIX="simple.prescription.record.hash";

    /**
     * 药房用户登录缓存
     */
    public static final String PHARMACYUSER_LOGIN_HASH_KEY_PREFIX="pharmacyUser.login.hash";
    
    /**
     * 药房职员登录成功access_token缓存前缀
     */
    public static final String PHARMACYUSER_LOGIN_TOKEN_HASH_KEY_PREFIX="pharmacyUser.login.token.hash";
    
    /**
     * 药房用户登录缓存
     */
    public static final String PHARMACISTUSER_LOGIN_HASH_KEY_PREFIX="pharmacistUser.login.hash";
    
    /**
     * 药房职员登录成功access_token缓存前缀
     */
    public static final String PHARMACISTUSER_LOGIN_TOKEN_HASH_KEY_PREFIX="pharmacistUser.login.token.hash";
 }
