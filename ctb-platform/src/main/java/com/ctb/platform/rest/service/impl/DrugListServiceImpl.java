/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月22日
 * Created by hhy
 */
package com.ctb.platform.rest.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.DrugList;
import com.ctb.commons.entity.Hospital;
import com.ctb.commons.entity.Pharmacy;
import com.ctb.framework.commons.redis.RedisClient;
import com.ctb.platform.rest.service.DrugListService;
import com.ctb.resources.mapper.biz.DrugListMapper;
import com.ctb.resources.mapper.biz.HospitalMapper;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @ClassName: com.ctb.platform.rest.service.impl.DrugListServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年3月22日 下午4:25:40
 */

@Service
public class DrugListServiceImpl implements DrugListService {
    
    private static Logger logger = LoggerFactory.getLogger(DrugListServiceImpl.class);
    
    @Autowired
    private DrugListMapper drugListMapper;
    
    @Autowired
    HospitalMapper hospitalMapper;
    
    @Autowired
    private RedisClient redisClient;
    
    @Override
    public int saveDrugList(DrugList drugList) throws Exception {
        int res = 0;
        try {
            res = drugListMapper.saveDrugList(drugList);
            if (res == 1) {
            	initDrugListCache();   //初始化缓存
                
//                Hospital hospital = hospitalMapper.selectByPrimaryKey(drugList.getHospitalId());
                
//                logger.debug("保存药品白名单到缓存:::" + drugList.getId());
//                List<DrugList> list = (List<DrugList>) redisClient
//                        .hget(CacheConstants.CACHE_HOSPITAL_DRUGLIST_HASH_KEY_PREFIX, hospital.getCode());
//                list.add(drugList);
//                redisClient.hset(CacheConstants.CACHE_HOSPITAL_DRUGLIST_HASH_KEY_PREFIX, hospital.getCode(), list);
            }
        } catch (Exception e) {
            logger.error("执行saveDrugList异常：：：DrugCode" + drugList.getDrugCode());
            e.printStackTrace();
        }
        return res;
    }
    
    @Override
    public int deleteDrugList(String[] drugListIds, String hospitalCode) {
        
//        redisClient.hdel(CacheConstants.CACHE_HOSPITAL_DRUGLIST_HASH_KEY_PREFIX, hospitalCode);
        
        String ids = String.join("','", drugListIds);
        try {
            int res = drugListMapper.deleteByIds("'" + ids + "'");
            if (res == drugListIds.length) {
                
//                JSONObject jsonObject = (JSONObject) redisClient.hget(CacheConstants.CACHE_HOSPITAL_HASH_KEY_PREFIX, hospitalCode);
//                Hospital hospital = JSONObject.toJavaObject(jsonObject, Hospital.class);
//                if(hospital == null) {
//                    logger.info("deleteDrugList:::缓存中没有医院信息:" + hospitalCode);
//                    return 0;
//                }
//                
//                Example example = new Example(DrugList.class);
//                Criteria criteria = example.createCriteria();
//                criteria.andEqualTo("hospitalId", hospital.getId());
//                List<DrugList> drugLists = drugListMapper.selectByExample(example);
//                redisClient.hset(CacheConstants.CACHE_HOSPITAL_DRUGLIST_HASH_KEY_PREFIX, hospitalCode, drugLists);
            }
            return res;
        } catch (Exception e) {
            logger.error("执行deleteDrugList异常:::" + ids);
            e.printStackTrace();
            return 0;
        }
    }
    
    @Override
    public List<DrugList> queryDrugListPagedByExample(Map<String, Object> map, Integer page, Integer pageSize) {
        
        PageHelper.startPage(page, pageSize);
//        Example example = new Example(DrugList.class);
//        Criteria criteria = example.createCriteria();
//        for (String key : map.keySet()) {
//            // value有值时才进行判断
//            if (map.get(key) != null && StringUtils.isNotBlank(map.get(key).toString())) {
//                if (key.equals("drugName")) {// 名字要模糊匹配
//                    criteria.andLike(key, "%" + map.get(key) + "%");
//                    continue;
//                }
//                if (key.equals("cadn")) {
//                    criteria.andLike(key, "%" + map.get(key) + "%");
//                    continue;
//                }
//                criteria.andEqualTo(key, map.get(key));
//            }
//        }
        
        List<DrugList> list = null;
        try {
            list = drugListMapper.queryDurgLists(map);
        } catch (Exception e) {
            logger.error("执行queryDrugListPagedByExample异常：：：");
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * 查询药品是否存在
     */
    @Override
    public List<DrugList> queryDrugListByExample(Map<String, Object> map) {
        
        List<DrugList> list = null;
//        Example exampel = new Example(DrugList.class);
 //       Criteria criteria = exampel.createCriteria();
//        for (String key : map.keySet()) {
            // value有值时才进行判断
//            if (map.get(key) != null && StringUtils.isNotBlank(map.get(key).toString())) {
//                if (key.equals("drugName")) {// 名字要模糊匹配
//                    criteria.andLike(key, "%" + map.get(key) + "%");
//                    continue;
//                }
//                if (key.equals("cadn")) {
//                    criteria.andLike(key, "%" + map.get(key) + "%");
//                    continue;
//                }
//                criteria.andEqualTo(key, map.get(key));
//            }
//        }
        try {
            list = drugListMapper.queryDrugListByExample(map);
        } catch (Exception e) {
            logger.error("执行queryDrugListByExample异常：：：");
            e.printStackTrace();
        }
        return list;
    }
    
    @Override
    public void initDrugListCache() {
        try {
            
            List<Hospital> hospitals = hospitalMapper.selectAll();   //查询所有医院
            Map<String, Object> drugListCacheMap = new HashMap<String, Object>();
            
            for (Hospital hospital : hospitals) {
                
//                Example exampel = new Example(DrugList.class);
//                Criteria criteria = exampel.createCriteria();
//                criteria.andEqualTo("hospitalId", hospital.getId());
                
                List<Map<String,Object>> drugLists = drugListMapper.selectDrugByRedis(hospital.getId().toString());
                if(!CollectionUtils.isEmpty(drugLists)) {
                    drugListCacheMap.put(hospital.getCode(), drugLists);
                }
            }
            
            redisClient.hmset(CacheConstants.CACHE_HOSPITAL_DRUGLIST_HASH_KEY_PREFIX, drugListCacheMap);
            
        } catch (Exception e) {
            logger.error("初始化药品目录缓存出错");
            e.printStackTrace();
        }
    }

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int updateDrugList(DrugList drugList) {
		// TODO Auto-generated method stub
		try {
			int res = drugListMapper.updateDrugList(drugList);
			if (res == 1) {
	            
				//saveAndUpdateDrugListRedisCache(drugList);
				initDrugListCache();    
			}
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
			logger.error("修改药房信息失败，修改数据异常！" + e.toString());
			return 0;
		}
	}
	
	/**
	 * 存入缓存
	 * @param pharmacy
	 */
	@Override
	public void saveAndUpdateDrugListRedisCache(DrugList drugList) {
//		try {
//			Map<String, Object> setMap = new HashMap<String, Object>();
//			setMap.put(drugList.getId() + CacheConstants.CACHE_KEY_SPLIT_CHAR + drugList.getDrugCode(), drugList);
//			redisClient.hmset(CacheConstants.CACHE_PHARMACY_HASH_KEY_PREFIX, setMap);
//		} catch (Exception e) {
//			// TODO: handle exception
//			logger.error("保存更新药房缓存失败！:::" + e.toString());
//		}

	}
}
