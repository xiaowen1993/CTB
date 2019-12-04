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

import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.Hospital;
import com.ctb.commons.entity.HospitalDrug;
import com.ctb.commons.entity.HospitalPharmacySettings;
import com.ctb.commons.entity.Pharmacy;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.framework.commons.redis.RedisClient;
import com.ctb.platform.rest.service.HospitalService;
import com.ctb.platform.rest.service.PharmacyService;
import com.ctb.resources.mapper.biz.HospitalMapper;
import com.ctb.resources.mapper.biz.HospitalPharmacySettingsMapper;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class HospitalServiceImpl implements HospitalService{
    
    private static Logger logger = LoggerFactory.getLogger(HospitalServiceImpl.class);
    
    @Autowired
    private HospitalMapper hospitalMapper;
    
    @Autowired
    private HospitalPharmacySettingsMapper hospitalPharmacySettingsMapper;
    
    @Autowired
    private PharmacyService pharmacyService;
    
    @Autowired
    private RedisClient redisClient;
     

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int saveHospital(Hospital hospital) throws Exception {
        
        int res = 0;
        try {
            res = hospitalMapper.insert(hospital);
            List <String> list = hospital.getPharmacyIds();
            int hpsCount = 0;
            for(String pharmacyId: list) {//遍历对象一个个插入
                Pharmacy  pharmacy = pharmacyService.queryPharmacyOne(new Pharmacy(pharmacyId));
                HospitalPharmacySettings hps = new HospitalPharmacySettings();
                hps.setId(PKGenerator.generateId());
                hps.setHospitalId(hospital.getId());
//                hps.setHospitalCode(hospital.getCode());
                hps.setPharmacyId(pharmacyId);
                hps.setStatus("1");
//                hps.setPharmacyCode(pharmacy.getCode());
                hospitalPharmacySettingsMapper.insert(hps);
                hpsCount++;
            }
            if(hpsCount != list.size()) {
               //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚
            }
            
            redisClient.hset(CacheConstants.CACHE_HOSPITAL_HASH_KEY_PREFIX, hospital.getCode(), hospital);
        }catch(Exception e) {
            logger.error("执行saveHospital异常：：：code" + hospital.getCode());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚
            return 0;
        }
        return res;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int updateHospital(Hospital hospital) {
        int res = 0;
        try {
            Hospital oldHospital = hospitalMapper.selectByPrimaryKey(hospital.getId());
            redisClient.hdel(CacheConstants.CACHE_HOSPITAL_HASH_KEY_PREFIX, oldHospital.getCode());
            
            res = hospitalMapper.updateByPrimaryKeySelective(hospital);
            List <String> list = hospital.getPharmacyIds();
            int hpsCount = 0;
            //修改的时候先删再加
            HospitalPharmacySettings hospitalPharmacySetting = new HospitalPharmacySettings();
            hospitalPharmacySetting.setHospitalId(hospital.getId());
            hospitalPharmacySettingsMapper.delete(hospitalPharmacySetting);
            
            for(String pharmacyId: list) {
                Pharmacy  pharmacy = pharmacyService.queryPharmacyOne(new Pharmacy(pharmacyId));
                HospitalPharmacySettings hps = new HospitalPharmacySettings();
                hps.setId(PKGenerator.generateId());
                hps.setStatus("1");
                hps.setHospitalId(hospital.getId());
                hps.setPharmacyId(pharmacyId);
//                hps.setHospitalCode(hospital.getCode());
//                hps.setPharmacyCode(pharmacy.getCode());
                hospitalPharmacySettingsMapper.insert(hps);
                hpsCount++;
            }
            if(hpsCount != list.size()) {
                //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚
             }
            
            redisClient.hset(CacheConstants.CACHE_HOSPITAL_HASH_KEY_PREFIX, hospital.getCode(), hospital);
        }catch(Exception e) {
            logger.error("执行updateHospital异常：：：code" + hospital.getCode());
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚
            return 0;
        }
        return res;
    }

    @Override
    public void deleteHospital(String hospitalId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Hospital queryHospitalById(String hospitalId) {
        try {
            return hospitalMapper.selectByPrimaryKey(hospitalId);
        } catch (Exception e) {
            logger.error("执行queryHospitalById异常：：：hospitalId=" + hospitalId);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Hospital> queryHospitalListPagedByExample(Map <String, Object> map, Integer page, Integer pageSize) {
        
        List<Hospital> list = null;
        /*
         * page = page==0 ? 0: page * pageSize; RowBounds rb = new RowBounds(page, pageSize);
         */
        PageHelper.startPage(page, pageSize);
        Example example = new Example(Hospital.class);
        example.setOrderByClause("CT DESC");
        Criteria criteria = example.createCriteria();
        for(String key : map.keySet()) {
            //value有值时才进行判断
            if(map.get(key)!=null && StringUtils.isNotBlank(map.get(key).toString())){
                if(key.equals("name")) {//名字要模糊匹配
                    criteria.andLike(key, "%" + map.get(key) + "%");
                    continue;
                }
                criteria.andEqualTo(key, map.get(key));
            }
        }

        try {
            list = hospitalMapper.selectByExample(example);
            for(Hospital hospital:list) {
                List<String> pharmacyIds = new ArrayList<String>();
                List<HospitalPharmacySettings> ids = new ArrayList<HospitalPharmacySettings>();
                Example e = new Example(HospitalPharmacySettings.class);
                Criteria c = e.createCriteria();
                c.andEqualTo("hospitalId", hospital.getId());
                ids = hospitalPharmacySettingsMapper.selectByExample(e);
                
                for(HospitalPharmacySettings id: ids) {
                    pharmacyIds.add(id.getPharmacyId());
                }
                hospital.setPharmacyIds(pharmacyIds);
            }
        } catch (Exception e) {
            logger.error("执行queryHospitalListPagedByExample异常：：：");
            e.printStackTrace();
        }
    
        return list;
    }

    @Override
    public List<Hospital> queryHospitalListByExample(Map<String, Object> map) {
        
        List<Hospital> list = null;
        Example example = new Example(Hospital.class);
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("CT DESC");
        for(String key : map.keySet()) {
            //value有值时才进行判断
            if(map.get(key)!=null && StringUtils.isNotBlank(map.get(key).toString())){
                if(key.equals("name")) {//名字要模糊匹配
                    criteria.andLike(key, "%" + map.get(key) + "%");
                    continue;
                }
                criteria.andEqualTo(key, map.get(key));
            }
        }
        try {
            list = hospitalMapper.selectByExample(example);
            for(Hospital hospital:list) {
                List<String> pharmacyIds = new ArrayList<String>();
                List<HospitalPharmacySettings> ids = new ArrayList<HospitalPharmacySettings>();
                Example e = new Example(HospitalPharmacySettings.class);
                Criteria c = e.createCriteria();
                c.andEqualTo("hospitalId", hospital.getId());
                ids = hospitalPharmacySettingsMapper.selectByExample(e);
                for(HospitalPharmacySettings id: ids) {
                    pharmacyIds.add(id.getPharmacyId());
                }
                hospital.setPharmacyIds(pharmacyIds);
            }
        } catch (Exception e) {
            logger.error("执行queryHospitalByExample异常：：：");
            e.printStackTrace();
        }
        
        return list;
    }

    @Override
    public void initHospitalCache() {
        List <Hospital> hospitals = hospitalMapper.selectAll();
        Map <String, Object> cacheMap = new HashMap<String, Object>();
        for (Hospital hospital : hospitals) {
            cacheMap.put(hospital.getCode(), hospital);
        }
        redisClient.hmset(CacheConstants.CACHE_HOSPITAL_HASH_KEY_PREFIX, cacheMap);
    }

    @Override
    public List<Hospital> getAll() {
        Example example = new Example(Hospital.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", 1);
        example.setOrderByClause("CT DESC");
        return hospitalMapper.selectByExample(example);
    }

	@Override
	public List<Hospital> queryHospitalList(Hospital hospital, String search) {
		try {
			Example example = new Example(Pharmacy.class);
			Example.Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(hospital.getName())) {
				criteria.andLike("name", "%" + hospital.getName() + "%");
			}
			if (StringUtils.isNotBlank(hospital.getCode())) {
				criteria.andEqualTo("code", hospital.getCode());
			}
			if (StringUtils.isNotBlank(String.valueOf(hospital.getStatus()))) {
				criteria.andEqualTo("status", hospital.getStatus());
			}
			if (StringUtils.isNotBlank(hospital.getContactName())) {
				criteria.andLike("contactName", "%" + hospital.getContactName() + "%");
			}
			if (StringUtils.isNotBlank(hospital.getContactTel())) {
				criteria.andEqualTo("contactTel", hospital.getContactTel());
			}
			if (StringUtils.isNotBlank(search)) {
				criteria.orLike("name", "%" + search + "%");
				criteria.orLike("contactName", "%" + search + "%");
			}
			List<Hospital> hospitalList = hospitalMapper.selectByExample(example);
			return hospitalList;
		} catch (Exception e) {
			System.out.println("获取药房总数失败：：：");
			e.printStackTrace();
			return new ArrayList<Hospital>();
		}		// TODO Auto-generated method stub
	}
}
