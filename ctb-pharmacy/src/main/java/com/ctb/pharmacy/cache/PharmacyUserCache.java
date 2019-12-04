/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年6月5日
 * Created by ckm
 */
package com.ctb.pharmacy.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.PharmacyUser;
import com.ctb.framework.commons.redis.RedisClient;
import com.ctb.pharmacy.rest.controller.PharmacyUserController;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

/**
 * @ClassName: com.ctb.pharmacy.cache.PharmacyUserCache
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年6月5日 上午11:18:17
 */
@Component
public class PharmacyUserCache {

	private static Logger logger = LoggerFactory.getLogger(PharmacyUserCache.class);

	@Autowired
	private RedisClient redisClient;

	// 过期时间，单位（秒）
	private static int TIME_OUT = 60 * 60 * 24 * 3;

	public void addToken(PharmacyUser pharmacyUser, String access_token) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(pharmacyUser.getId(), access_token);
			redisClient.hset(CacheConstants.PHARMACYUSER_LOGIN_TOKEN_HASH_KEY_PREFIX, access_token, pharmacyUser,
					TIME_OUT);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("class:[{}],method:[{}],errMsg:[{}]", PharmacyUserCache.class, "addToken", e.toString());
		}
	}

	/**
	 * 保存在线职员列表缓存
	 * 
	 * @Title: addLogin
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年6月5日 上午11:30:31
	 * @param pharmacyUser
	 */
	public void addLogin(PharmacyUser pharmacyUser) {
		try {
			JSONArray array = queryList(pharmacyUser);
			if (array != null && !array.isEmpty()) {
				PharmacyUser phusers = null;
				phusers = searchJsonObjects(array, pharmacyUser.getId());
				if (phusers == null) {
					array.add(pharmacyUser);
				}
			} else {
				array = new JSONArray();
				array.add(pharmacyUser);
			}
			loginHash(array, pharmacyUser);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("class:[{}],method:[{}],errMsg:[{}]", PharmacyUserCache.class, "addLogin", e.toString());
		}
	}

	/**
	 * 查询在线职员缓存列表
	 * 
	 * @Title: query
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年6月5日 上午11:27:39
	 * @param pharmacyUser
	 * @return
	 */
	public JSONArray queryList(PharmacyUser pharmacyUser) {
		try {
			JSONArray array = (JSONArray) redisClient.hget(CacheConstants.PHARMACYUSER_LOGIN_HASH_KEY_PREFIX,
					pharmacyUser.getPharmacyId());
			return array;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("class:[{}],method:[{}],errMsg:[{}]", PharmacyUserCache.class, "queryList", e.toString());
		}
		return null;
	}

	/**
	 * 在线缓存
	 * 
	 * @Title: loginHash
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年6月5日 上午11:38:50
	 * @param array
	 * @param pharmacyUser
	 */
	public void loginHash(JSONArray array, PharmacyUser pharmacyUser) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(pharmacyUser.getPharmacyId(), array);
			redisClient.hmset(CacheConstants.PHARMACYUSER_LOGIN_HASH_KEY_PREFIX, map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("class:[{}],method:[{}],errMsg:[{}]", PharmacyUserCache.class, "loginHash", e.toString());
		}
	}

	/**
	 * 登出更新职员在线缓存
	 * 
	 * @Title: updateLogOut
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年6月5日 下午2:06:39
	 * @param pharmacyUser
	 */
	public void updateLogOut(PharmacyUser pharmacyUser) {
		try {
			JSONArray array = queryList(pharmacyUser);
			if (array!=null&&!array.isEmpty()) {
				PharmacyUser phusers = null;
				phusers = searchJsonObjects(array, pharmacyUser.getId());
				if (phusers != null) {
					for (int i = 0; i < array.size(); i++) {
						try {
							JSONObject jsonObject = (JSONObject) array.get(i);
							if (jsonObject.get("id").equals(pharmacyUser.getId())) {
								array.remove(i);
								break;
							}
						} catch (Exception e) {
							continue;
						}
					}
				}
				if(array.isEmpty()) {
					hdel(CacheConstants.PHARMACYUSER_LOGIN_HASH_KEY_PREFIX, pharmacyUser.getPharmacyId());
				}else {
					loginHash(array, pharmacyUser);					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("class:[{}],method:[{}],errMsg:[{}]", PharmacyUserCache.class, "updateLogOut", e.toString());
		}
	}

	/**
	 * 根据id查找
	 * 
	 * @Title: searchJsonObjects
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年6月5日 下午2:07:20
	 * @param array
	 * @param id
	 * @return
	 */
	private PharmacyUser searchJsonObjects(JSONArray array, String id) {
		PharmacyUser pharmacyUser = null;
		try {
			List<PharmacyUser> list = (List<PharmacyUser>) JSONArray.parseArray(JSONObject.toJSONString(array),
					PharmacyUser.class);
			List<PharmacyUser> objects = new ArrayList<PharmacyUser>();
			if (StringUtils.isNotBlank(id) && list.size() > 0) {
				Collection<PharmacyUser> result = Collections2.filter(list, new Predicate<PharmacyUser>() {
					@Override
					public boolean apply(PharmacyUser input) {
						return input.getId().toString().contains(id);
					}
				});
				if (result != null) {
					objects = (List<PharmacyUser>) JSONArray.parseArray(
							JSONObject.toJSONString((List<PharmacyUser>) JSONArray.toJSON(result)), PharmacyUser.class);
					if (objects.size() > 0) {
						pharmacyUser = objects.get(0);
					}
				}
			}
			return pharmacyUser;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("class:[{}],method:[{}],errMsg:[{}]", PharmacyUserCache.class, "searchJsonObjects",
					e.toString());
			return null;
		}
	}

	/**
	 * 删除
	 * 
	 * @Title: hdel
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年6月5日 下午2:07:48
	 * @param key
	 * @param item
	 */
	public void hdel(String key, String item) {
		try {
			if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(item)) {
				redisClient.hdel(key, item);
			}
		} catch (Exception e) {
			logger.error("class:[{}],method:[{}],errMsg:[{}]", PharmacyUserCache.class, "hdel", e.toString());
		}
	}
}
