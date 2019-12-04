/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月23日
 * Created by ckm
 */
package com.ctb.platform.rest.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.BranchPharmacy;
import com.ctb.commons.entity.Pharmacy;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.framework.commons.redis.RedisClient;
import com.ctb.platform.rest.service.BranchPharmacyService;
import com.ctb.platform.rest.service.PharmacyUserService;
import com.ctb.resources.mapper.biz.BranchPharmacyMapper;
import com.ctb.resources.mapper.biz.PharmacyMapper;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: com.ctb.platform.rest.service.impl.BranchPharmacyServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年3月23日 上午11:27:40
 */
@Service
public class BranchPharmacyServiceImpl implements BranchPharmacyService {
    
    private static Logger logger = LoggerFactory.getLogger(BranchPharmacyServiceImpl.class);
    
    @Autowired
    private BranchPharmacyMapper branchPharmacyMapper;
    
    @Autowired
    private PharmacyMapper pharmacyMapper;
    
    @Autowired
    private PharmacyUserService pharmacyUserService;
    
    @Autowired
    private RedisClient redisClient;
    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<BranchPharmacy> queryBranchPharmacyListPaged(BranchPharmacy branchPharmacy, Integer page,
            Integer pageSize, String searchKey) {
        PageHelper.startPage(page, pageSize);
        Example example = new Example(BranchPharmacy.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(branchPharmacy.getId())) {
            criteria.andEqualTo("id", branchPharmacy.getId());
        }
        if (StringUtils.isNotBlank(branchPharmacy.getPharmacyId())) {
            criteria.andEqualTo("pharmacyId", branchPharmacy.getPharmacyId());
        }
        if (StringUtils.isNotBlank(branchPharmacy.getName())) {
            criteria.andLike("name", "%" + branchPharmacy.getName() + "%");
        }
        if (StringUtils.isNotBlank(branchPharmacy.getCode())) {
            criteria.andEqualTo("code", branchPharmacy.getCode());
        }
        if (StringUtils.isNotBlank(branchPharmacy.getAddress())) {
            criteria.andEqualTo("address", branchPharmacy.getAddress());
        }
        if (StringUtils.isNotBlank(String.valueOf(branchPharmacy.getStatus()))) {
            criteria.andEqualTo("status", branchPharmacy.getStatus());
        }
        
        if (StringUtils.isNotBlank(searchKey)) {
            criteria.orLike("name", "%" + searchKey + "%");
            criteria.orLike("code", "%" + searchKey + "%");
            criteria.orLike("address", "%" + searchKey + "%");
        }
        example.orderBy("ct").desc();
        List<BranchPharmacy> pharmacyList = branchPharmacyMapper.selectByExample(example);
        return pharmacyList;
    }
    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public int saveBranchPharmacy(BranchPharmacy branchPharmacy) {
        try {
            branchPharmacy.setId(PKGenerator.generateId());
            int res = branchPharmacyMapper.insertSelective(branchPharmacy);
            if (res == 1) {
                Pharmacy pharmacy = pharmacyMapper.selectByPrimaryKey(branchPharmacy.getPharmacyId());
                saveAndUpdateBranchPharmacyRedisCache(pharmacy.getCode(), branchPharmacy);
                initByExample(branchPharmacy.getPharmacyId());
            }
            return res;
        } catch (Exception e) {
            // TODO: handle exception
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
            logger.error("保存药房分店信息失败，保存数据异常！" + e.toString());
            return 0;
        }
    }
    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public int updateBranchPharmacy(BranchPharmacy branchPharmacy) {
        // TODO Auto-generated method stub
        try {
            int res = branchPharmacyMapper.updateByPrimaryKeySelective(branchPharmacy);
            if (res == 1) {
                Pharmacy pharmacy = pharmacyMapper.selectByPrimaryKey(branchPharmacy.getPharmacyId());
                saveAndUpdateBranchPharmacyRedisCache(pharmacy.getCode(), branchPharmacy);
                initByExample(branchPharmacy.getPharmacyId());
            }
            return res;
        } catch (Exception e) {
            // TODO: handle exception
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
            logger.error("修改药房分店信息失败，修改数据异常！");
            return 0;
        }
    }
    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public int delBranchPharmacy(String[] ids, String pharmacyId) {
        // TODO Auto-generated method stub
        try {
            Pharmacy pharmacy = pharmacyMapper.selectByPrimaryKey(pharmacyId);
            
            String idLists = String.join("','", ids);
            pharmacyUserService.deleteByBranchPharmacyIds(ids);
            
            int res = branchPharmacyMapper.deleteByIds("'" + idLists + "'");
            if (res == 1) {
        	    Example example = new Example(BranchPharmacy.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("pharmacyId", pharmacyId);  
                List<BranchPharmacy> branchPharmacys = branchPharmacyMapper.selectByExample(example);
                if(branchPharmacys.size()>0&&!branchPharmacys.isEmpty()) {                	
                	redisClient.hset(CacheConstants.CACHE_BRANCH_PHARMACY_CODE_HASH_KEY_PREFIX,
                			pharmacy.getId() + CacheConstants.CACHE_KEY_SPLIT_CHAR + pharmacy.getCode(), branchPharmacys);
                	initByExample(pharmacyId);
                }
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
            }
            
            // String idLists = String.join("','", ids);
            // int res = branchPharmacyMapper.deleteByIds("'" + idLists + "'");
            // // if(res==1)redisTemplate.delete("branchPharmacy_"+id);
            return res;
        } catch (Exception e) {
            // TODO: handle exception
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
            logger.error("删除药房分店信息失败，删除数据异常！" + e.toString());
            return 0;
        }
    }
    
    @Override
    public BranchPharmacy queryBranchPharmacy(BranchPharmacy branchPharmacy) {
        // TODO Auto-generated method stub
        Example example = new Example(BranchPharmacy.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(branchPharmacy.getId())) {
            criteria.andEqualTo("id", branchPharmacy.getId());
        }
        if (StringUtils.isNotBlank(branchPharmacy.getPharmacyId())) {
            criteria.andEqualTo("pharmacyId", branchPharmacy.getPharmacyId());
        }
        if (StringUtils.isNotBlank(branchPharmacy.getName())) {
            criteria.andEqualTo("name", branchPharmacy.getName());
        }
        if (StringUtils.isNotBlank(branchPharmacy.getCode())) {
            criteria.andEqualTo("code", branchPharmacy.getCode());
        }
        if (StringUtils.isNotBlank(branchPharmacy.getAddress())) {
            criteria.andEqualTo("address", branchPharmacy.getAddress());
        }
        if (StringUtils.isNotBlank(String.valueOf(branchPharmacy.getStatus()))) {
            criteria.andEqualTo("status", branchPharmacy.getStatus());
        }
        
        BranchPharmacy branchPharmacyInfo = branchPharmacyMapper.selectOneByExample(example);
        return branchPharmacyInfo;
    }
    
    @Override
    public boolean isUniqueName(BranchPharmacy branchPharmacy) {
        Example example = new Example(BranchPharmacy.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(branchPharmacy.getId())) {
            criteria.andEqualTo("name", branchPharmacy.getName());
        }
        BranchPharmacy branchPharmacyInfo = branchPharmacyMapper.selectOneByExample(example);
        if (branchPharmacyInfo == null) {
            return false;
        } else {
            if (StringUtils.isNotBlank(branchPharmacy.getId())) {
                if (!branchPharmacyInfo.getId().equals(branchPharmacy.getId())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
    }
    
    @Override
    public boolean isUniqueCode(BranchPharmacy branchPharmacy) {
        Example example = new Example(BranchPharmacy.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(branchPharmacy.getId())) {
            criteria.andEqualTo("code", branchPharmacy.getCode());
        }
        BranchPharmacy branchPharmacyInfo = branchPharmacyMapper.selectOneByExample(example);
        if (branchPharmacyInfo == null) {
            return false;
        } else {
            if (StringUtils.isNotBlank(branchPharmacy.getId())) {
                if (!branchPharmacyInfo.getId().equals(branchPharmacy.getId())) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
    }
    
    @Override
    public int total(BranchPharmacy branchPharmacy, String searchKey) {
        // TODO Auto-generated method stub
        try {
            Example example = new Example(BranchPharmacy.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(branchPharmacy.getId())) {
                criteria.andEqualTo("id", branchPharmacy.getId());
            }
            if (StringUtils.isNotBlank(branchPharmacy.getPharmacyId())) {
                criteria.andEqualTo("pharmacyId", branchPharmacy.getPharmacyId());
            }
            if (StringUtils.isNotBlank(branchPharmacy.getName())) {
                criteria.andLike("name", "%" + branchPharmacy.getName() + "%");
            }
            if (StringUtils.isNotBlank(branchPharmacy.getCode())) {
                criteria.andEqualTo("code", branchPharmacy.getCode());
            }
            if (StringUtils.isNotBlank(branchPharmacy.getAddress())) {
                criteria.andEqualTo("address", branchPharmacy.getAddress());
            }
            if (StringUtils.isNotBlank(String.valueOf(branchPharmacy.getStatus()))) {
                criteria.andEqualTo("status", branchPharmacy.getStatus());
            }
            
            if (StringUtils.isNotBlank(searchKey)) {
                criteria.orLike("name", "%" + searchKey + "%");
                criteria.orLike("code", "%" + searchKey + "%");
                criteria.orLike("address", "%" + searchKey + "%");
            }
            return branchPharmacyMapper.selectCountByExample(example);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("查询药房分店记录数失败,数据查询异常！" + e.toString());
            return 0;
        }
    }
    
    @Override
    public List<BranchPharmacy> querBranchPharmacyList(BranchPharmacy branchPharmacy) {
        Example example = new Example(BranchPharmacy.class);
        Example.Criteria criteria = example.createCriteria();
        
        if (StringUtils.isNotBlank(branchPharmacy.getPharmacyId())) {
            criteria.andEqualTo("pharmacyId", branchPharmacy.getPharmacyId());
        }
        example.orderBy("ct").desc();
        List<BranchPharmacy> pharmacyList = branchPharmacyMapper.selectByExample(example);
        return pharmacyList;
    }
    
    @Override
    public List<BranchPharmacy> queryBranchPharmacyByIds(String[] ids) {
        try {
            Example example = new Example(BranchPharmacy.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andIn("pharmacyId", Arrays.asList(ids));
            List<BranchPharmacy> branchPharmacies = branchPharmacyMapper.selectByExample(example);
            return branchPharmacies;
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("根据药房ids查询分店信息失败，查询过程异常！:::" + e.toString());
            return new ArrayList<BranchPharmacy>();
        }
    }
    
    @Override
    public void initBranchPharmacyRedisCache() {
        try {
            
            List<Pharmacy> pharmacys = pharmacyMapper.selectAll();
            Map<String, Object> cacheMap = new HashMap<String, Object>();
            
            for (Pharmacy pharmacy : pharmacys) {
                Example example = new Example(BranchPharmacy.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("pharmacyId", pharmacy.getId());
                
                List<BranchPharmacy> branchPharmacies = branchPharmacyMapper.selectByExample(example);
                if (!CollectionUtils.isEmpty(branchPharmacies)) {
                    cacheMap.put(pharmacy.getId() + CacheConstants.CACHE_KEY_SPLIT_CHAR + pharmacy.getCode(), branchPharmacies);
                }
                
            }
            
            redisClient.hmset(CacheConstants.CACHE_BRANCH_PHARMACY_CODE_HASH_KEY_PREFIX, cacheMap);
            
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("初始化药房-分店缓存失败，初始化缓存异常!:::" + e.toString());
        }
    }
    
    @Override
    public void saveAndUpdateBranchPharmacyRedisCache(String pharmacyCode, BranchPharmacy branchPharmacy) {
        try {
            JSONObject getPharmacyObject = (JSONObject) redisClient.hget(CacheConstants.CACHE_PHARMACY_HASH_KEY_PREFIX,
                    branchPharmacy.getPharmacyId() + CacheConstants.CACHE_KEY_SPLIT_CHAR + pharmacyCode);
            
            Map<String, Object> setMap = new HashMap<String, Object>();
            if (getPharmacyObject != null) {
                List<BranchPharmacy> branchPharmacies = querBranchPharmacyList(branchPharmacy);
                if (branchPharmacies.size() > 0)
                    setMap.put(getPharmacyObject.get("id") + ":" + getPharmacyObject.get("code"), branchPharmacies);
            }
            if (!setMap.isEmpty())
                redisClient.hmset(CacheConstants.CACHE_BRANCH_PHARMACY_CODE_HASH_KEY_PREFIX, setMap);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("保存、更新药房-分店缓存失败，缓存过程异常！:::" + e.toString());
        }
    }
    
    @Override
    public void initLocation() {
        // TODO Auto-generated method stub
        try {
            Map<Object, Object> getBranchPharmacyObject = redisClient
                    .hmget(CacheConstants.CACHE_BRANCH_PHARMACY_CODE_HASH_KEY_PREFIX);
            if (getBranchPharmacyObject != null) {
                Iterator<Entry<Object, Object>> iterator = getBranchPharmacyObject.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map<Object, Point> setMap = new HashMap<Object, Point>();
                    Entry<Object, Object> entry = iterator.next();
                    List<JSONObject> objects = (List<JSONObject>) entry.getValue();
                    if (objects.size() > 0) {
                        for (JSONObject jsonObject : objects) {
                            Point point = new Point(Double.parseDouble(jsonObject.get("longitude") + ""),
                                    Double.parseDouble(jsonObject.get("latitude") + ""));
                            setMap.put(jsonObject, point);
                        }
                    }
                    if (setMap != null) {
                        redisClient.geoAddMemberCoordinateMap(CacheConstants.CACHE_LOCATION_HASH_KEY_PREFIX 
                                + objects.get(0).get("pharmacyId"), setMap);
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("初始化地理位置失败，缓存过程异常！:::" + e.toString());
        }
    }
    
    public void initByExample(String pharmacyId) {
    	Example example = new Example(BranchPharmacy.class);
    	Example.Criteria criteria = example.createCriteria();
    	criteria.andEqualTo("pharmacyId", pharmacyId);
    	List<BranchPharmacy> branchPharmacies=branchPharmacyMapper.selectByExample(example);
    	if(branchPharmacies.size()>0 && !branchPharmacies.isEmpty()) {
    		Map<Object, Point> setMap = new HashMap<Object, Point>();
    		 for (BranchPharmacy branchPharmacy2 : branchPharmacies) {
    			 Point point = new Point(Double.parseDouble(branchPharmacy2.getLongitude() + ""),
    					 Double.parseDouble(branchPharmacy2.getLatitude() + ""));
    			 setMap.put(branchPharmacy2, point);
				
			}
    		if(setMap!=null&&!setMap.isEmpty()) {    			
    			redisClient.del(CacheConstants.CACHE_LOCATION_HASH_KEY_PREFIX 
    					+ pharmacyId);
    			redisClient.geoAddMemberCoordinateMap(CacheConstants.CACHE_LOCATION_HASH_KEY_PREFIX 
    					+ pharmacyId, setMap);
    		}
    	}
    	
    }
}
