/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月23日
 * Created by ckm
 */
package com.ctb.platform.rest.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.BranchPharmacy;
import com.ctb.commons.entity.PaySettings;
import com.ctb.commons.entity.Pharmacy;
import com.ctb.commons.entity.PharmacyPaySettings;
import com.ctb.framework.commons.redis.RedisClient;
import com.ctb.platform.rest.service.PharmacyPaySettingsService;
import com.ctb.resources.mapper.biz.PaySettingsMapper;
import com.ctb.resources.mapper.biz.PharmacyMapper;
import com.ctb.resources.mapper.biz.PharmacyPaySettingsMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: com.ctb.platform.rest.service.impl.PharmacyPaySettingsServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年3月23日 上午10:57:11
 */
@Service
public class PharmacyPaySettingsServiceImpl implements PharmacyPaySettingsService {
    
    private static Logger logger = LoggerFactory.getLogger(PharmacyPaySettingsServiceImpl.class);
    
    @Autowired
    private PharmacyPaySettingsMapper pharmacyPaySettingsMapper;
    
    @Autowired
    private PaySettingsMapper paySettingsMapper;
    
    @Autowired
    private PharmacyMapper pharmacyMapper;
    
    @Autowired
    private RedisClient redisClient;
    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PaySettings queryPharmacyPaySettingsOne(PharmacyPaySettings paySettings) {
        try {
            Example example = new Example(PharmacyPaySettings.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(paySettings.getPharmacyId())) {
                criteria.andEqualTo("pharmacyId", paySettings.getPharmacyId());
            }
            
            PharmacyPaySettings pharmacyPaySettingsInfo = pharmacyPaySettingsMapper.selectOneByExample(example);
            if (pharmacyPaySettingsInfo != null) {
                Example examplePaySettings = new Example(PaySettings.class);
                Example.Criteria criteriaPaySettings = examplePaySettings.createCriteria();
                criteriaPaySettings.andEqualTo("id", pharmacyPaySettingsInfo.getPaySettingsId());
                PaySettings paySettingsInfo = paySettingsMapper.selectOneByExample(examplePaySettings);
                return paySettingsInfo;
            }
            return new PaySettings();
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("查询药房支付信息失败，查询数据异常！" + e.toString());
            return new PaySettings();
        }
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int save(PharmacyPaySettings pharmacyPaySettings, PaySettings paySettings) {
        
        int res = 0;
        try {
            res += paySettingsMapper.insertSelective(paySettings);
            res += pharmacyPaySettingsMapper.insertSelective(pharmacyPaySettings);
            if (res == 2) {
                Pharmacy pharmacy = pharmacyMapper.selectByPrimaryKey(pharmacyPaySettings.getPharmacyId());
                saveAndUpdatePharmacyPaySettingsCache(pharmacy, paySettings);
            }
            
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
            e.printStackTrace();
            return 0;
        }
        return res;
    }
    
    @Override
    public List<PaySettings> queryPaySettinsList(String pharmacyId) {
        try {
            Example example = new Example(PharmacyPaySettings.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("pharmacyId", pharmacyId);
            List<PharmacyPaySettings> pharmacyPaySettingsList = pharmacyPaySettingsMapper.selectByExample(example);
            List<String> list = new ArrayList<>();
            if (pharmacyPaySettingsList.size() > 0) {
                for (int i = 0; i < pharmacyPaySettingsList.size(); i++) {
                    list.add(pharmacyPaySettingsList.get(i).getPaySettingsId());
                }
            }
            if (list.size() > 0) {
                Iterable it = (Iterable) list.iterator();
                Example examplePaySettings = new Example(PaySettings.class);
                Example.Criteria criteriaPaySettings = examplePaySettings.createCriteria();
                criteria.andIn("pharmacyId", it);
                List<PaySettings> paySettingsList = paySettingsMapper.selectByExample(example);
                return paySettingsList;
            }
            return new ArrayList<PaySettings>();
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("获取药房支付信息列表失败，获取数据异常！");
            return new ArrayList<PaySettings>();
        }
    }
    
    @Override
    public int update(String pharmacyId, PaySettings paySettings) {
        int res = 0;
        try {
            res = paySettingsMapper.updateByPrimaryKey(paySettings);
            if (res == 1) {
                Pharmacy pharmacy = pharmacyMapper.selectByPrimaryKey(pharmacyId);
                saveAndUpdatePharmacyPaySettingsCache(pharmacy, paySettings);
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
            e.printStackTrace();
            return 0;
        }
        return res;
    }
    
    @Override
    public PharmacyPaySettings queryOne(PharmacyPaySettings pharmacyPaySettings) {
        try {
            Example example = new Example(PharmacyPaySettings.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(pharmacyPaySettings.getPharmacyId())) {
                criteria.andEqualTo("pharmacyId", pharmacyPaySettings.getPharmacyId());
            }
            PharmacyPaySettings pharmacyPaySettingsInfo = pharmacyPaySettingsMapper.selectOneByExample(example);
            return pharmacyPaySettingsInfo;
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("查询'药房-支付'关系信息失败,查询信息异常！" + e.toString());
            return null;
            
        }
    }
    
    @Override
    public void initPharmacyPaysettingsCache() {
        // TODO Auto-generated method stub
        /*
         * Map<Object, Object> getPharmacyMap = (Map<Object, Object>) redisClient
         * .hmget(CacheConstants.CACHE_PHARMACY_HASH_KEY_PREFIX); if (getPharmacyMap != null) { Iterator<Entry<Object,
         * Object>> iterator = getPharmacyMap.entrySet().iterator(); Map<String, Object> setMap = new HashMap<String,
         * Object>(); while (iterator.hasNext()) { Entry<Object, Object> entry = iterator.next(); JSONObject object =
         * (JSONObject) entry.getValue(); PharmacyPaySettings entity = new PharmacyPaySettings();
         * entity.setPharmacyId(String.valueOf(object.get("id"))); PaySettings paySettings =
         * queryPharmacyPaySettingsOne(entity); if (StringUtils.isNotBlank(paySettings.getId())) {
         * setMap.put(object.get("id") + ":" + object.get("code"), paySettings); } } if (!setMap.isEmpty())
         * redisClient.hmset(CacheConstants.CACHE_PHARMACY_PAYSETTINGS_HASH_KEY_PREFIX, setMap); }
         */
        
        try {
            
            List<Pharmacy> pharmacys = pharmacyMapper.selectAll();
            Map<String, Object> cacheMap = new HashMap<String, Object>();
            
            for (Pharmacy pharmacy : pharmacys) {
                Example example = new Example(PharmacyPaySettings.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("pharmacyId", pharmacy.getId());
                
                PharmacyPaySettings pharmacyPaySettings = pharmacyPaySettingsMapper.selectOneByExample(example);
                if(pharmacyPaySettings!=null) {
                    PaySettings paySettings = paySettingsMapper.selectByPrimaryKey(pharmacyPaySettings.getPaySettingsId());
                    cacheMap.put(pharmacy.getId() + CacheConstants.CACHE_KEY_SPLIT_CHAR + pharmacy.getCode(), paySettings);
                }

            }
            
            redisClient.hmset(CacheConstants.CACHE_PHARMACY_PAYSETTINGS_HASH_KEY_PREFIX, cacheMap);
            
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("初始化药房-分店缓存失败，初始化缓存异常!:::" + e.toString());
        }
    }
    
    @Override
    public void saveAndUpdatePharmacyPaySettingsCache(Pharmacy pharmacy, PaySettings paySettings) {
        // TODO Auto-generated method stub
        /*
         * Map<String, Object> setMap = new HashMap<String, Object>(); // PaySettings paySettingsInfo =
         * queryPharmacyPaySettingsOne(pharmacyPaySettings); if (paySettingsInfo !=
         * null)setMap.put(getPharmacy.get("id") + ":" + getPharmacy.get("code"), paySettingsInfo);
         * if(!setMap.isEmpty())redisClient.hmset(CacheConstants.CACHE_PHARMACY_PAYSETTINGS_HASH_KEY_PREFIX, setMap);
         */
        redisClient.hset(CacheConstants.CACHE_PHARMACY_PAYSETTINGS_HASH_KEY_PREFIX,
                pharmacy.getId() + CacheConstants.CACHE_KEY_SPLIT_CHAR + pharmacy.getCode(), paySettings);
    }
    
}
