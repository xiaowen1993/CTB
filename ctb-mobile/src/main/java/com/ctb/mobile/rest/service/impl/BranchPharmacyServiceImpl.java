/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月1日
 * Created by hhy
 */
package com.ctb.mobile.rest.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.BranchPharmacy;
import com.ctb.commons.entity.Hospital;
import com.ctb.commons.entity.HospitalPharmacySettings;
import com.ctb.commons.entity.Pharmacy;
import com.ctb.framework.commons.redis.RedisClient;
import com.ctb.mobile.cache.BranchPharmacyCache;
import com.ctb.mobile.rest.service.BranchPharmacyService;
import com.ctb.resources.mapper.biz.BranchPharmacyMapper;
import com.ctb.resources.mapper.biz.HospitalMapper;
import com.ctb.resources.mapper.biz.HospitalPharmacySettingsMapper;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: com.ctb.mobile.rest.service.impl.BranchPharmacyServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年4月1日 下午8:30:19
 */
@Service
public class BranchPharmacyServiceImpl implements BranchPharmacyService {
    
    private static Logger logger = LoggerFactory.getLogger(BranchPharmacyServiceImpl.class);
    
    @Autowired
    private BranchPharmacyMapper branchPharmacyMapper;
    
    @Autowired
    private HospitalMapper hospitalMapper;
    
    @Autowired
    private HospitalPharmacySettingsMapper hospitalPharmacySettingsMapper;
    
    @Autowired
    private RedisClient redisClient;
    
    @Autowired
    private BranchPharmacyCache branchPharmacyCache;
    
    public BranchPharmacy selectOne(BranchPharmacy branchPharmacy) {
        return branchPharmacyMapper.selectOne(branchPharmacy);
    }
    
    @Override
    public List<JSONObject> loactionList(String hospitalCode, String latitude, String longitude, String radius) {
    	List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
        try {
            Hospital hospital=new Hospital();
            JSONObject hospitalObj = (JSONObject) redisClient.hget(CacheConstants.CACHE_HOSPITAL_HASH_KEY_PREFIX, hospitalCode);
            if(hospitalObj == null) {
                Example hospitalExample = new Example(Hospital.class);
                Example.Criteria hospitalExampleCriteria = hospitalExample.createCriteria();
                hospitalExampleCriteria.andEqualTo("hospitalCode", hospitalCode);
                hospital = hospitalMapper.selectOneByExample(hospitalExample);
                if (hospital == null || StringUtils.isBlank(hospital.getId())) {
                    return new ArrayList<JSONObject>();
                }
            }else {
            	hospital=JSONObject.toJavaObject(hospitalObj, Hospital.class);
            }
            
            // setp1：通过hospitalId查找合作药房
            Example hospitalPharmacySettingsExample = new Example(HospitalPharmacySettings.class);
            Example.Criteria criteria = hospitalPharmacySettingsExample.createCriteria();
            
            criteria.andEqualTo("hospitalId", hospital.getId());
            criteria.andEqualTo("status", "1");
            List<HospitalPharmacySettings> hospitalPharmacySettings = hospitalPharmacySettingsMapper
                    .selectByExample(hospitalPharmacySettingsExample);
            if (!CollectionUtils.isEmpty(hospitalPharmacySettings) && StringUtils.isNotBlank(longitude)
                    && StringUtils.isNotBlank(latitude) && StringUtils.isNotBlank(radius)) {
                 
                // setp2-1：通过药房id，缓存匹配符合条件的地理位置信息
                for (int i = 0; i < hospitalPharmacySettings.size(); i++) {
                	Pharmacy pharmacy=new Pharmacy();
                    JSONObject pharmacyObj = branchPharmacyCache.getPharmacy(hospitalPharmacySettings.get(i).getPharmacyId());
                    if(pharmacyObj!=null) {
                    	pharmacy=JSONObject.toJavaObject(pharmacyObj, Pharmacy.class);
                    }else {
                    	return new ArrayList<JSONObject>();
                    }
                    GeoResults<RedisGeoCommands.GeoLocation<Object>> GeoResults=branchPharmacyCache.geoLocation(longitude, latitude, radius, pharmacy.getId());
                    if (!GeoResults.getContent().isEmpty()) {
                        List<GeoResult<GeoLocation<Object>>> geoResults2 = GeoResults.getContent();
                        if (geoResults2.size() > 0) {
                            for (GeoResult<GeoLocation<Object>> geoResult : geoResults2) {
                                JSONObject jsonObject = (JSONObject) geoResult.getContent().getName();
                                jsonObject.put("metric", geoResult.getDistance().getMetric());
                                jsonObject.put("distanceUnit", geoResult.getDistance().getUnit());
                                jsonObject.put("distance", geoResult.getDistance().getValue());
                                jsonObject.put("pharmacyCode", pharmacy.getCode());
                                // 第三步：获取符合条件的地理位置信息,返回
                                if ("1".equals(jsonObject.get("status"))) {
                                    jsonObjects.add(jsonObject);
                                }
                            }
                        }
                    }
                }
                return jsonObjects;
            }
        } catch (Exception e) {
            logger.error("获取附近药房信息失败,获取信息异常！:::" + e.toString());
            return new ArrayList<JSONObject>();
        }
        return new ArrayList<JSONObject>();
    }
    
    @Override
    public List<JSONObject> sortJsonObjects(List<JSONObject> list) {
        if (!list.isEmpty() && list.size() > 0) {
            Collections.sort(list, new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject o1, JSONObject o2) {
                    return o1.getInteger("distance").compareTo(o2.getInteger("distance"));
                }
            });
        }
        return list;
    }
       
    public List<JSONObject> searchJsonObjects(List<JSONObject> list, String name) {
        List<JSONObject> objects = new ArrayList<JSONObject>();
        if (StringUtils.isNotBlank(name)&&list.size() > 0) {
        	Collection<JSONObject> result = Collections2.filter(list, new Predicate<JSONObject>() {
                @Override
                public boolean apply(JSONObject input) {
                    return input.get("name").toString().contains(name);
                }
            });
            objects = (List<JSONObject>) JSONArray.toJSON(result);
        }
        return objects;
    }
    
    @Override
    public List<JSONObject> listWithOutLatAndLng(String hospitalCode) {
        try {
            // TODO Auto-generated method stub
            // setp1：通过hospitalId查找合作药房
            JSONObject jsonObject = (JSONObject) redisClient.hget(CacheConstants.CACHE_HOSPITAL_HASH_KEY_PREFIX, hospitalCode);
            Hospital hospital =  JSONObject.toJavaObject(jsonObject, Hospital.class);
            
            List<JSONObject> jsonObjects = new ArrayList<JSONObject>();
            Example example = new Example(HospitalPharmacySettings.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(hospital.getId()))
                criteria.andEqualTo("hospitalId", hospital.getId());
            criteria.andEqualTo("status", "1");
            List<HospitalPharmacySettings> hospitalPharmacySettings = hospitalPharmacySettingsMapper
                    .selectByExample(example);
            if (hospitalPharmacySettings.size() > 0) {
                // setp2-1：通过药房id，缓存匹配符合条件的地理位置信息
                // setp2-2无法获取用户经纬度则查找医院合作药房随机返回一个
                for (int i = 0; i < hospitalPharmacySettings.size(); i++) {
                    JSONObject obj = branchPharmacyCache.getPharmacy(hospitalPharmacySettings.get(i).getPharmacyId());
                    Pharmacy pharmacy = new Pharmacy();
                    if(obj!=null&&!obj.isEmpty()) {
                    	pharmacy=JSONObject.toJavaObject(obj, Pharmacy.class);
                    }
                    JSONArray array=new JSONArray();
                    if(pharmacy!=null) {
                    	array =branchPharmacyCache.getBranchPharmacys(pharmacy.getId(), pharmacy.getCode());
                    }
                    if (array != null && array.size() > 0) {
                        List<JSONObject> objects = (List<JSONObject>) JSONArray
                                .parseArray(JSONObject.toJSONString(array), JSONObject.class);
                        if (objects.size() > 0) {
                            for (JSONObject jsonObject2 : objects) {
                                jsonObject2.put("metric", "");
                                jsonObject2.put("distanceUnit", "");
                                jsonObject2.put("distance", "");
                                jsonObject2.put("pharmacyCode", StringUtils.isNotBlank(pharmacy.getCode())?pharmacy.getCode():"");
                                if ("1".equals(jsonObject2.get("status"))) {
                                    jsonObjects.add(jsonObject2);
                                }
                            }
                        }
                    }
                }
                return jsonObjects;
            }
        } catch (Exception e) {
            logger.error("获取附近药房信息失败,获取信息异常！:::" + e.toString());
            return new ArrayList<JSONObject>();
        }
        return new ArrayList<JSONObject>();
    }

    @Override
	public BranchPharmacy queryOne(String pharmacyId,String pharmacyCode,String pharmacyBranchcode) {
		try {
			BranchPharmacy branchPharmacy = new BranchPharmacy();
			JSONArray array = branchPharmacyCache.getBranchPharmacys(pharmacyId, pharmacyCode);
			if (array != null && array.size() > 0) {
				List<BranchPharmacy> list = (List<BranchPharmacy>) JSONArray.parseArray(JSONObject.toJSONString(array),
						BranchPharmacy.class);
				if (list.size() > 0) {
					Collection<BranchPharmacy> result = Collections2.filter(list, new Predicate<BranchPharmacy>() {
						@Override
						public boolean apply(BranchPharmacy input) {
							return input.getCode().toString().contains(pharmacyBranchcode);
						}
					});
					if(result!=null) {					
						List<BranchPharmacy> branchPharmacies = (List<BranchPharmacy>) JSONArray.parseArray(JSONObject.toJSONString((List<BranchPharmacy>) JSONArray.toJSON(result)),
								BranchPharmacy.class);	
						if(branchPharmacies.size()>0) {	
							branchPharmacy = branchPharmacies.get(0);
						}
					}
				}
			}
			return branchPharmacy;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    
}
