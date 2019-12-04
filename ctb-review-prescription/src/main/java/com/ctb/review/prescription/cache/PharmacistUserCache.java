/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年7月10日
 * Created by hhy
 */
package com.ctb.review.prescription.cache;

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
import com.ctb.commons.entity.PharmacistUser;
import com.ctb.framework.commons.redis.RedisClient;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

/**
 * @ClassName: com.ctb.review.prescription.cache.PharmacistUserCache
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年7月10日 下午3:53:47
 */
@Component
public class PharmacistUserCache {

    private static Logger logger = LoggerFactory.getLogger(PharmacistUserCache.class);

    @Autowired
    private RedisClient redisClient;

    // 过期时间，单位（秒）
    private static int TIME_OUT = 60 * 60 * 24 * 3;

    public void addToken(PharmacistUser pharmacistUser, String access_token) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(pharmacistUser.getId(), access_token);
            redisClient.hset(CacheConstants.PHARMACISTUSER_LOGIN_TOKEN_HASH_KEY_PREFIX, access_token, pharmacistUser,
                    TIME_OUT);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("class:[{}],method:[{}],errMsg:[{}]", PharmacistUserCache.class, "addToken", e.toString());
        }
    }

    /**
     * 保存在线职员列表缓存
     * 
     * @Title: addLogin
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author ckm
     * @date 2019年6月5日 上午11:30:31
     * @param 
     */
    public void addLogin(PharmacistUser pharmacistUser) {
        try {
            List <String> hospitalCodes = pharmacistUser.getHospitalCodes();
            for (String hospitalCode : hospitalCodes) {
                JSONArray array = (JSONArray) redisClient.hget(CacheConstants.PHARMACISTUSER_LOGIN_HASH_KEY_PREFIX,
                        hospitalCode);
                if (array != null && !array.isEmpty()) {
                    PharmacistUser phusers = null;
                    phusers = searchJsonObjects(array, pharmacistUser.getId());// 为啥传了那个对象进来了，还要去查缓存？
                    if (phusers == null) {
                        array.add(pharmacistUser);
                    } else {// 在线缓存中已存在 直接结束，不用继续保存了
                        return;
                    }
                } else {
                    array = new JSONArray();
                    array.add(pharmacistUser);
                }
                loginHash(array, hospitalCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("class:[{}],method:[{}],errMsg:[{}]", PharmacistUserCache.class, "addLogin", e.toString());
        }
    }

    /**
     * 查询在线职员缓存列表
     * 
     * @Title: query
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author ckm
     * @date 2019年6月5日 上午11:27:39
     * @param 
     * @return
     */
    /*public JSONArray queryList(String hospitalCode) {
        try {
                JSONArray array = (JSONArray) redisClient.hget(CacheConstants.PHARMACISTUSER_LOGIN_HASH_KEY_PREFIX,
                        hospitalCode);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("class:[{}],method:[{}],errMsg:[{}]", PharmacistUserCache.class, "queryList", e.toString());
        }
        return null;
    }*/

    /**
     * 在线缓存
     * 
     * @Title: loginHash
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author ckm
     * @date 2019年6月5日 上午11:38:50
     * @param array
     * @param 
     */
    public void loginHash(JSONArray array, String hospitalCode) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(hospitalCode, array);
            redisClient.hmset(CacheConstants.PHARMACISTUSER_LOGIN_HASH_KEY_PREFIX, map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("class:[{}],method:[{}],errMsg:[{}]", PharmacistUserCache.class, "loginHash", e.toString());
        }
    }

    /**
     * 登出更新职员在线缓存
     * 
     * @Title: updateLogOut
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author ckm
     * @date 2019年6月5日 下午2:06:39
     * @param 
     */
    public void updateLogOut(PharmacistUser pharmacistUser) {
        try {
            List<String> hospitalCodes = pharmacistUser.getHospitalCodes();
            for (String hospitalCode : hospitalCodes) {
                JSONArray array = (JSONArray) redisClient.hget(CacheConstants.PHARMACISTUSER_LOGIN_HASH_KEY_PREFIX,
                        hospitalCode);
                if (array != null && !array.isEmpty()) {
                    PharmacistUser phusers = null;
                    phusers = searchJsonObjects(array, pharmacistUser.getId());
                    if (phusers != null) {
                        for (int i = 0; i < array.size(); i++) {
                            try {
                                JSONObject jsonObject = (JSONObject) array.get(i);
                                if (jsonObject.get("id").equals(pharmacistUser.getId())) {
                                    array.remove(i);
                                    break;
                                }
                            } catch (Exception e) {
                                continue;
                            }
                        }
                    }
                    if (array.isEmpty()) {
                        hdel(CacheConstants.PHARMACISTUSER_LOGIN_HASH_KEY_PREFIX, hospitalCode);
                    } else {
                        loginHash(array, hospitalCode);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("class:[{}],method:[{}],errMsg:[{}]", PharmacistUserCache.class, "updateLogOut", e.toString());
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
    private PharmacistUser searchJsonObjects(JSONArray array, String id) {
        PharmacistUser pharmacistUser = null;
        try {
            List<PharmacistUser> list = (List<PharmacistUser>) JSONArray.parseArray(JSONObject.toJSONString(array),
                    PharmacistUser.class);
            List<PharmacistUser> objects = new ArrayList<PharmacistUser>();
            if (StringUtils.isNotBlank(id) && list.size() > 0) {
                Collection<PharmacistUser> result = Collections2.filter(list, new Predicate<PharmacistUser>() {
                    @Override
                    public boolean apply(PharmacistUser input) {
                        return input.getId().toString().contains(id);
                    }
                });
                if (result != null) {
                    objects = (List<PharmacistUser>) JSONArray.parseArray(
                            JSONObject.toJSONString((List<PharmacistUser>) JSONArray.toJSON(result)),
                            PharmacistUser.class);
                    if (objects.size() > 0) {
                        pharmacistUser = objects.get(0);
                    }
                }
            }
            return pharmacistUser;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("class:[{}],method:[{}],errMsg:[{}]", PharmacistUserCache.class, "searchJsonObjects",
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
            logger.error("class:[{}],method:[{}],errMsg:[{}]", PharmacistUserCache.class, "hdel", e.toString());
        }
    }
}
