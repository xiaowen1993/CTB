/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月29日
 * Created by ckm
 */
package com.ctb.mobile.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoRadiusCommandArgs;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.Pharmacy;
import com.ctb.framework.commons.redis.RedisClient;

/**
 * @ClassName: com.ctb.mobile.cache.BranchPharmacyCache
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月29日 上午11:24:18
 */
@Component
public class BranchPharmacyCache {

	@Autowired
	private RedisClient redisClient;

	/**
	 * 获取药房缓存
	 * 
	 * @Title: getPharmacy
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月29日 下午8:17:42
	 * @param pharmacyId
	 * @return
	 */
	public JSONObject getPharmacy(String pharmacyId) {
		JSONObject pharmacyObj = (JSONObject) redisClient.hget(getPharmacyCacheKey(), pharmacyId);
		return pharmacyObj;
	}

	/**
	 * 获取药房分店缓存集合
	 * 
	 * @Title: getBranchPharmacys
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月29日 下午8:17:02
	 * @param pharmacyId
	 * @param pharmacyCode
	 * @return
	 */
	public JSONArray getBranchPharmacys(String pharmacyId, String pharmacyCode) {
		JSONArray array = (JSONArray) redisClient.hget(getBranchPharmacyCacheKey(),
				pharmacyId + getKeySplitCharCacheKey() + pharmacyCode);
		return array;
	}

	/**
	 * 获取经纬度缓存集合
	 * 
	 * @Title: geoLocation
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月29日 下午8:16:22
	 * @param longitude
	 * @param latitude
	 * @param radius
	 * @param pharmacyId
	 * @return
	 */
	public GeoResults<RedisGeoCommands.GeoLocation<Object>> geoLocation(String longitude, String latitude,
			String radius, String pharmacyId) {
		GeoRadiusCommandArgs args = GeoRadiusCommandArgs.newGeoRadiusArgs();
		args.sortAscending();
		args.includeCoordinates();
		args.hasSortDirection();
		args.includeDistance();
		Circle circle = new Circle(new Point(Double.parseDouble(longitude), Double.parseDouble(latitude)),
				Integer.valueOf(radius));
		GeoResults<RedisGeoCommands.GeoLocation<Object>> GeoResults = redisClient
				.geoRadius(getLocationCacheKey() + pharmacyId, circle, args);
		return GeoResults;
	}

	/**
	 * 获取药房总店缓存集合
	 * 
	 * @Title: getPharmacyHm
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月29日 下午8:15:50
	 * @return
	 */
	public List<Pharmacy> getPharmacyHm() {
		List<Pharmacy> pharmacies = new ArrayList<Pharmacy>();
		Map<Object, Object> map = redisClient.hmget(getPharmacyCacheKey());
		if (map != null) {
			Iterator<Entry<Object, Object>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<Object, Object> entry = iterator.next();
				List<JSONObject> objects = (List<JSONObject>) entry.getValue();
				if (objects.size() > 0) {
					for (JSONObject jsonObject : objects) {
						Pharmacy pharmacy = JSONObject.toJavaObject(jsonObject, Pharmacy.class);
						if (pharmacy != null) {
							pharmacies.add(pharmacy);
						}
					}
				}
			}
		}
		return pharmacies;
	}

	/**
	 * 药房缓存(ConcurrentHashMap)
	 * @Title: getPharmacyConcurrentHashMap
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月30日 上午11:02:53
	 * @return
	 */
	public ConcurrentHashMap<String, Pharmacy> getPharmacyConcurrentHashMap() {
		Map<Object, Object> map = redisClient.hmget(getPharmacyCacheKey());
		ConcurrentHashMap<String, Pharmacy> voMap = new ConcurrentHashMap<String, Pharmacy>();
		if (!map.isEmpty() && map != null) {
			Iterator<Entry<Object, Object>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<Object, Object> entry = iterator.next();
				JSONObject objectKey = (JSONObject) entry.getValue();
				Pharmacy pharmacy = JSONObject.toJavaObject(objectKey, Pharmacy.class);
				voMap.put(entry.getKey().toString(), pharmacy);
			}
		}
		return voMap;

	}

	/**
	 * get药房缓存key
	 * 
	 * @Title: getPharmacyCacheKey
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月29日 下午8:36:56
	 * @return
	 */
	public String getPharmacyCacheKey() {
		return CacheConstants.CACHE_PHARMACY_HASH_KEY_PREFIX;
	}

	/**
	 * get药房对应所有分店缓存key
	 * 
	 * @Title: getBranchPharmacyCacheKey
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月29日 下午8:37:21
	 * @return
	 */
	public String getBranchPharmacyCacheKey() {
		return CacheConstants.CACHE_BRANCH_PHARMACY_CODE_HASH_KEY_PREFIX;
	}

	/**
	 * get缓存key的分割符
	 * 
	 * @Title: getKeySplitCharCacheKey
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月29日 下午8:38:33
	 * @return
	 */
	public String getKeySplitCharCacheKey() {
		return CacheConstants.CACHE_KEY_SPLIT_CHAR;
	}

	/**
	 * get药房分店location缓存key
	 * 
	 * @Title: getLocationCacheKey
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月29日 下午8:38:49
	 * @return
	 */
	public String getLocationCacheKey() {
		return CacheConstants.CACHE_LOCATION_HASH_KEY_PREFIX;
	}

}
