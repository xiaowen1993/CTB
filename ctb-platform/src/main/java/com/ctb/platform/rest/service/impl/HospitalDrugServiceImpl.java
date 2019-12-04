package com.ctb.platform.rest.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.DrugList;
import com.ctb.commons.entity.Hospital;
import com.ctb.commons.entity.HospitalDrug;
import com.ctb.commons.entity.Pharmacy;
import com.ctb.commons.entity.PharmacyUser;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.framework.commons.redis.RedisClient;
import com.ctb.platform.rest.service.HospitalDrugService;
import com.ctb.resources.mapper.biz.HospitalDrugMapper;
import com.ctb.resources.mapper.biz.HospitalMapper;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @ClassName: com.ctb.platform.rest.service.HospitalDrugServiceImpl
 * @Description: 医院药品名单service
 * @author Qin
 * @date 2019年11月4日 
 */
@Service
public class HospitalDrugServiceImpl implements HospitalDrugService{
    private static Logger logger = LoggerFactory.getLogger(HospitalDrugServiceImpl.class);
    
    @Autowired
    private HospitalDrugMapper hospitalDrugMapper;
    
    @Autowired
    HospitalMapper hospitalMapper;
    
    @Autowired
    private RedisClient redisClient;
	
	@Override
	public List<HospitalDrug> queryHospitalDurgLists(Map<String, Object> param) {
//		int page = Integer.parseInt(param.get("page").toString());
//		int pageSize = Integer.parseInt(param.get("pageSize").toString());
//		PageHelper.startPage(page, pageSize);  
		List<HospitalDrug> list = null;
	        try {
	            list = hospitalDrugMapper.queryHospitalDurgLists(param);
	        } catch (Exception e) {
	            logger.error("执行queryDrugListPagedByExample异常：：：");
	            e.printStackTrace();
	        }
	        return list;
	}


	@Override
	public int saveHospitalDrug(HospitalDrug hospitalDrug) throws Exception {
		try {
			hospitalDrug.setId(PKGenerator.generateId());
			int res = hospitalDrugMapper.insertSelective(hospitalDrug);
			if (res == 1) {
				// saveAndUpdatePharmacyRedisCache(hospitalDrug);  存入缓存
				initHospitalDrugListCache();//存入缓存
			}
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
			logger.error("保存药房信息失败，保存数据异常！" + e.toString());
			return 0;
		}
	}


//	@Override
//	public int saveHospitalDrugList(HospitalDrug hospitalDrug) throws Exception {
//        int res = 0;
//        try {
//            res = hospitalDrugMapper.insert(hospitalDrug);
//            if (res == 1) {
//                
////                Hospital hospital = hospitalMapper.selectByPrimaryKey(drugList.getHospitalId());
//                
////                logger.debug("保存药品白名单到缓存:::" + hospitalDrug.getId());
////                List<DrugList> list = (List<DrugList>) redisClient
////                        .hget(CacheConstants.CACHE_HOSPITAL_DRUGLIST_HASH_KEY_PREFIX, hospital.getCode());
////                list.add(drugList);
////                redisClient.hset(CacheConstants.CACHE_HOSPITAL_DRUGLIST_HASH_KEY_PREFIX, hospital.getCode(), list);
//            }
//        } catch (Exception e) {
//            logger.error("执行saveDrugList异常：：：DrugCode" + hospitalDrug.getHisDrugCode());
//            e.printStackTrace();
//        }
//        return res;
//	}


	@Override
	public int queryHospitalDurg(Map<String, Object> map) {
		int num = 0;
	        try {
	            num = hospitalDrugMapper.queryHospitalDurg(map);
	        } catch (Exception e) {
	            logger.error("执行queryDrugListPagedByExample异常：：：");
	            e.printStackTrace();
	        }
	        return num;
	}

	@Override
	public int updateHospitalDrugList(HospitalDrug hospitalDrug) {
		try {
			int res = hospitalDrugMapper.updateHospitalDrugList(hospitalDrug);
			if (res == 1) {
	            
				//saveAndUpdateHospitalDrugListRedisCache(hospitalDrug);
				initHospitalDrugListCache();//存入缓存
			}
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
			logger.error("修改药房信息失败，修改数据异常！" + e.toString());
			return 0;
		}
	}


//	@Override
//	public void saveAndUpdateHospitalDrugListRedisCache(HospitalDrug hospitalDrug) {
//		// TODO Auto-generated method stub
//		try {
//			Map<String, Object> setMap = new HashMap<String, Object>();
//			setMap.put(hospitalDrug.getId() + CacheConstants.CACHE_KEY_SPLIT_CHAR + hospitalDrug.getHisDrugCode(), hospitalDrug);
//			redisClient.hmset(CacheConstants.CACHE_PHARMACY_HASH_KEY_PREFIX, setMap);
//		} catch (Exception e) {
//			// TODO: handle exception
//			logger.error("保存更新药房缓存失败！:::" + e.toString());
//		}
//	}


	@Override
	public int queryHospitalDurgCount(Map <String, Object> map) {
		try {
			int res = hospitalDrugMapper.queryHospitalDurgCount(map);
			if (res == 1) {
				// saveAndUpdatePharmacyRedisCache(hospitalDrug);  存入缓存
			}
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
			logger.error("查询药房信息失败，查询数据异常！" + e.toString());
			return 0;
		}
	}
	
	
    @Override
    public void initHospitalDrugListCache() {
        try {
            
            List<Hospital> hospitals = hospitalMapper.selectAll();   //查询所有医院
            Map<String, Object> hospitalDrugListCacheMap = new HashMap<String, Object>();
            
            for (Hospital hospital : hospitals) {
                
                List<HospitalDrug> hospitalDrugLists = hospitalDrugMapper.selectHospitalDrugByRedis(hospital.getId().toString());
                if(!CollectionUtils.isEmpty(hospitalDrugLists)) {
                	hospitalDrugListCacheMap.put(hospital.getCode(), hospitalDrugLists);
                }
            }
            
            redisClient.hmset(CacheConstants.CACHE_HOSPITAL_DRUG_HASH_KEY_PREFIX, hospitalDrugListCacheMap);
            
        } catch (Exception e) {
            logger.error("初始化医院药品黑白名单缓存出错");
            e.printStackTrace();
        }
    }

}
