/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月22日
 * Created by hhy
 */
package com.ctb.platform.rest.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.DrugBlackList;
import com.ctb.commons.entity.Hospital;
import com.ctb.framework.commons.redis.RedisClient;
import com.ctb.platform.rest.service.DrugBlackListService;
import com.ctb.resources.mapper.biz.DrugBlackListMapper;
import com.ctb.resources.mapper.biz.HospitalMapper;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @ClassName: com.ctb.platform.rest.service.impl.DrugBlackListServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年3月22日 下午4:25:40
 */

@Service
public class DrugBlackListServiceImpl implements DrugBlackListService {
    
    private static Logger logger = LoggerFactory.getLogger(DrugBlackListServiceImpl.class);
    
    @Autowired
    private DrugBlackListMapper drugBlackListMapper;
    
    @Autowired
    HospitalMapper hospitalMapper;
    
    @Autowired
    private RedisClient redisClient;
    
    @Override
    public int saveDrugBlackList(DrugBlackList drugBlackList) throws Exception {
        
        int res = 0;
        try {
            res = drugBlackListMapper.insert(drugBlackList);
            if (res == 1) {
                Hospital hospital = hospitalMapper.selectByPrimaryKey(drugBlackList.getHospitalId());
                
                List<DrugBlackList> list = (List<DrugBlackList>) redisClient
                        .hget(CacheConstants.CACHE_HOSPITAL_DRUGBLACKLIST_KEY_HASH_PREFIX, hospital.getCode());
                list.add(drugBlackList);
                redisClient.hset(CacheConstants.CACHE_HOSPITAL_DRUGBLACKLIST_KEY_HASH_PREFIX, hospital.getCode(), list);
            }
        } catch (Exception e) {
            logger.error("saveDrugBlackList异常：：：DrugCode" + drugBlackList.getDrugCode());
            e.printStackTrace();
        }
        return res;
    }
    
    @Override
    public int deleteDrugBlackList(String[] drugBlackListIds, String hospitalCode) {
        
//        redisClient.hdel(CacheConstants.CACHE_HOSPITAL_DRUGBLACKLIST_KEY_HASH_PREFIX, hospitalCode);
        
        String ids = String.join("','", drugBlackListIds);
        try {
            int res = drugBlackListMapper.deleteByIds("'" + ids + "'");
            if (res == drugBlackListIds.length) {
                logger.debug("从缓存中删除药品黑名单:::");
                JSONObject jsonObject = (JSONObject) redisClient.hget(CacheConstants.CACHE_HOSPITAL_HASH_KEY_PREFIX, hospitalCode);
                Hospital hospital = JSONObject.toJavaObject(jsonObject, Hospital.class);
                if(hospital == null) {
                    logger.info("deleteDrugList:::缓存中没有医院信息:" + hospitalCode);
                    return 0;
                }
                
                Example example = new Example(DrugBlackList.class);
                Criteria criteria = example.createCriteria();
                criteria.andEqualTo("hospitalId", hospital.getId());
                
                List<DrugBlackList> drugBlackLists = drugBlackListMapper.selectByExample(example);
                redisClient.hset(CacheConstants.CACHE_HOSPITAL_DRUGLIST_HASH_KEY_PREFIX, hospitalCode, drugBlackLists);
            }
            return res;
        } catch (Exception e) {
            logger.error("执行deleteDrugBlackList异常：：：ids" + ids);
            e.printStackTrace();
            return 0;
        }
    }
    
    @Override
    public List<DrugBlackList> queryDrugBlackListPagedByExample(Map<String, Object> map, Integer page,
            Integer pageSize) {
        
        List<DrugBlackList> list = null;
        try {
            PageHelper.startPage(page, pageSize);
            Example example = new Example(DrugBlackList.class);
            example.setOrderByClause("CT DESC");
            Criteria criteria = example.createCriteria();
            for (String key : map.keySet()) {
                // value有值时才进行判断
                if (map.get(key) != null && StringUtils.isNotBlank(map.get(key).toString())) {
                    if (key.equals("drugName")) {// 名字要模糊匹配
                        criteria.andLike(key, "%" + map.get(key) + "%");
                        continue;
                    }
                    if (key.equals("cadn")) {
                        criteria.andLike(key, "%" + map.get(key) + "%");
                        continue;
                    }
                    criteria.andEqualTo(key, map.get(key));
                }
            }
            list = drugBlackListMapper.selectByExample(example);
        } catch (Exception e) {
            logger.error("执行：：：queryDrugBlackListPagedByExample异常");
            e.printStackTrace();
        }
        
        return list;
    }
    
    @Override
    public List<DrugBlackList> queryDrugBlackListByExample(Map<String, Object> map) {
        
        List<DrugBlackList> list = null;
        try {
            Example example = new Example(DrugBlackList.class);
            Criteria criteria = example.createCriteria();
            example.setOrderByClause("CT DESC");
            for (String key : map.keySet()) {
                // value有值时才进行判断
                if (map.get(key) != null && StringUtils.isNotBlank(map.get(key).toString())) {
                    if (key.equals("drugName")) {// 名字要模糊匹配
                        criteria.andLike(key, "%" + map.get(key) + "%");
                        continue;
                    }
                    if (key.equals("cadn")) {
                        criteria.andLike(key, "%" + map.get(key) + "%");
                        continue;
                    }
                    criteria.andEqualTo(key, map.get(key));
                }
            }
            
            list = drugBlackListMapper.selectByExample(example);
        } catch (Exception e) {
            logger.error("秩序queryDrugBlackListByExample异常：：：");
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public void initHospitalDrugBlackListCache() {
//        try {
//            List<Hospital> hospitals = hospitalMapper.selectAll();
//            Map<String, Object> drugListCacheMap = new HashMap<String, Object>();
//            
//            for (Hospital hospital : hospitals) {
//                
//                Example exampel = new Example(DrugBlackList.class);
//                Criteria criteria = exampel.createCriteria();
//                criteria.andEqualTo("hospitalId", hospital.getId());
//                
//                List<DrugBlackList> drugBlackLists = drugBlackListMapper.selectByExample(exampel);
//                if(!CollectionUtils.isEmpty(drugBlackLists)) {
//                    drugListCacheMap.put(hospital.getCode(), drugBlackLists);
//                }
//            }
//            
//            redisClient.hmset(CacheConstants.CACHE_HOSPITAL_DRUGBLACKLIST_KEY_HASH_PREFIX, drugListCacheMap);
//        } catch (Exception e) {
//            logger.error("初始化药品黑名单缓存出错");
//            e.printStackTrace();
//        }
    }
    
}
