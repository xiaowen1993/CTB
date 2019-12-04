package com.ctb.framework.commons.redis;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metric;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoRadiusCommandArgs;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import redis.clients.jedis.Protocol;
import redis.clients.util.SafeEncoder;

@Component
public class RedisClient {
    
    
    private static RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    // =============================common============================
    /**
     * 指定缓存失效时间
     * 
     * @param key
     *            键
     * @param time
     *            时间(秒)
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 根据key 获取过期时间
     * 
     * @param key
     *            键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
    
    /**
     * 判断key是否存在
     * 
     * @param key
     *            键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 删除缓存
     * 
     * @param key
     *            可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }
    
    // ============================String=============================
    /**
     * 普通缓存获取
     * 
     * @param key
     *            键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }
    
    /**
     * 普通缓存放入
     * 
     * @param key
     *            键
     * @param value
     *            值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        
    }
    
    /**
     * 普通缓存放入并设置时间
     * 
     * @param key
     *            键
     * @param value
     *            值
     * @param time
     *            时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 递增
     * 
     * @param key
     *            键
     * @param by
     *            要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }
    
    /**
     * 递减
     * 
     * @param key
     *            键
     * @param by
     *            要减少几(小于0)
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }
    
    // ================================Map=================================
    /**
     * HashGet
     * 
     * @param key
     *            键 不能为null
     * @param item
     *            项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }
    
    /**
     * 获取hashKey对应的所有键值
     * 
     * @param key
     *            键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }
    
    /**
     * HashSet
     * 
     * @param key
     *            键
     * @param map
     *            对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * HashSet 并设置时间
     * 
     * @param key
     *            键
     * @param map
     *            对应多个键值
     * @param time
     *            时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * 
     * @param key
     *            键
     * @param item
     *            项
     * @param value
     *            值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * 
     * @param key
     *            键
     * @param item
     *            项
     * @param value
     *            值
     * @param time
     *            时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 删除hash表中的值
     * 
     * @param key
     *            键 不能为null
     * @param item
     *            项 可以使多个 不能为null
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }
    
    /**
     * 判断hash表中是否有该项的值
     * 
     * @param key
     *            键 不能为null
     * @param item
     *            项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }
    
    /**
     * 返回hash表中所有field集合
     * 
     * @param key
     *            键 不能为null
     * @return 所有hash表的field值（集合）
     */
    public Set<Object> hKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }
    
    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * 
     * @param key
     *            键
     * @param item
     *            项
     * @param by
     *            要增加几(大于0)
     * @return
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }
    
    /**
     * hash递减
     * 
     * @param key
     *            键
     * @param item
     *            项
     * @param by
     *            要减少记(小于0)
     * @return
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }
    
    // ============================set=============================
    /**
     * 根据key获取Set中的所有值
     * 
     * @param key
     *            键
     * @return
     */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 根据value从一个set中查询,是否存在
     * 
     * @param key
     *            键
     * @param value
     *            值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 将数据放入set缓存
     * 
     * @param key
     *            键
     * @param values
     *            值 可以是多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * 将set数据放入缓存
     * 
     * @param key
     *            键
     * @param time
     *            时间(秒)
     * @param values
     *            值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0)
                expire(key, time);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * 获取set缓存的长度
     * 
     * @param key
     *            键
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * 移除值为value的
     * 
     * @param key
     *            键
     * @param values
     *            值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    // ===============================list=================================
    
    /**
     * 获取list缓存的内容
     * 
     * @param key
     *            键
     * @param start
     *            开始
     * @param end
     *            结束 0 到 -1代表所有值
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 获取list缓存的长度
     * 
     * @param key
     *            键
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * 通过索引 获取list中的值
     * 
     * @param key
     *            键
     * @param index
     *            索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 将list放入缓存
     * 
     * @param key
     *            键
     * @param value
     *            值
     * @param time
     *            时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 将list放入缓存
     * 
     * @param key
     *            键
     * @param value
     *            值
     * @param time
     *            时间(秒)
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 将list放入缓存
     * 
     * @param key
     *            键
     * @param value
     *            值
     * @param time
     *            时间(秒)
     * @return
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 将list放入缓存
     * 
     * @param key
     *            键
     * @param value
     *            值
     * @param time
     *            时间(秒)
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 根据索引修改list中的某条数据
     * 
     * @param key
     *            键
     * @param index
     *            索引
     * @param value
     *            值
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 移除N个值为value
     * 
     * @param key
     *            键
     * @param count
     *            移除多少个
     * @param value
     *            值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * 
     * @Title: geoAdd
     * @Description: TODO( 命令：将指定的地理空间位置（纬度、经度、名称）添加到指定的key中。 描述：将指定的地理空间位置（纬度、经度、名称）添加到指定的key中。 返回值：添加到sorted
     *               set元素的数目，但不包括已更新score的元素。 )
     * @author cwq
     * @date 2019年3月29日 上午10:05:32
     * @param key
     * @param longitude
     * @param latitude
     * @param member
     * @return
     */
    public boolean geoAdd(String key, Point point, Object member) {
        try {
            redisTemplate.opsForGeo().geoAdd(key, point, member);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 
     * @Title: geoRemove
     * @Description: TODO(将地理空间位置成员删除)
     * @author cwq
     * @date 2019年3月29日 上午10:29:36
     * @param key
     * @param members
     * @return
     */
    public long geoRemove(String key, Object... members) {
        try {
            Long count = redisTemplate.opsForGeo().geoRemove(key, members);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * 
     * @Title: geoAddMemberCoordinateMap
     * @Description: TODO(批量添加空间位置信息)
     * @author cwq
     * @date 2019年3月29日 上午10:36:34
     * @param key
     * @param memberCoordinateMap
     * @return
     */
    public boolean geoAddMemberCoordinateMap(String key, Map<Object, Point> memberCoordinateMap) {
        try {
            redisTemplate.opsForGeo().geoAdd(key, memberCoordinateMap);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 
     * @Title: geoDist
     * @Description: TODO(返回两个给定位置之间的距离。如果两个位置之间的其中一个不存在， 那么命令返回空值。默认单位的：m 表示单位为米。)
     * @author cwq
     * @date 2019年3月29日 上午10:47:52
     * @param key
     * @param member1
     * @param member2
     * @return
     */
    public Distance geoDist(String key, Object member1, Object member2) {
        try {
            Distance distance = redisTemplate.opsForGeo().geoDist(key, member1, member2);
            return distance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 
     * @Title: geoDist
     * @Description: TODO(返回两个给定位置之间的距离。如果两个位置之间的其中一个不存在， 那么命令返回空值。指定单位的参数 unit 必须是以下单位的其中一个：m 表示单位为米。km 表示单位为千米。mi
     *               表示单位为英里。ft 表示单位为英尺。)
     * @author cwq
     * @date 2019年3月29日 上午10:47:52
     * @param key
     * @param member1
     * @param member2
     * @return
     */
    public Distance geoDist(String key, Object member1, Object member2, Metric metric) {
        try {
            Distance distance = redisTemplate.opsForGeo().geoDist(key, member1, member2, metric);
            return distance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 
     * @Title: geoPos
     * @Description: TODO(从key里返回所有给定位置元素的位置（经度和纬度）。GEOPOS 命令返回一个数组， 数组中的每个项都由两个元素组成： 第一个元素为给定位置元素的经度，
     *               而第二个元素则为给定位置元素的纬度。当给定的位置元素不存在时， 对应的数组项为空值。)
     * @author cwq
     * @date 2019年3月29日 上午11:08:07
     * @param key
     * @param members
     * @return
     */
    public List<Point> geoPos(String key, Object... members) {
        try {
            List<Point> points = redisTemplate.opsForGeo().geoPos(key, members);
            return points;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 
     * @Title: geoHash
     * @Description: TODO(返回一个或多个位置元素的 Geohash
     *               表示。通常使用表示位置的元素使用不同的技术，使用Geohash位置52点整数编码。由于编码和解码过程中所使用的初始最小和最大坐标不同，编码的编码也不同于标准。此命令返回一个标准的Geohash
     *               一个数组， 数组的每个项都是一个 geohash 。 命令返回的 geohash 的位置与用户给定的位置元素的位置一一对应。)
     * @author cwq
     * @date 2019年3月29日 上午11:10:27
     * @param key
     * @param members
     * @return
     */
    public List<String> geoHash(String key, Object... members) {
        try {
            List<String> geohashs = redisTemplate.opsForGeo().geoHash(key, members);
            return geohashs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 
     * @Title: geoRadius
     * @Description: TODO(以给定的经纬度为中心， 返回键包含的位置元素当中， 与中心的距离不超过给定最大距离的所有位置元素。)
     * @author cwq
     * @date 2019年3月29日 上午11:19:00
     * @param key
     * @param within
     * @return
     */
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadius(String key, Circle within) {
        try {
            GeoResults<RedisGeoCommands.GeoLocation<Object>> geoResults = redisTemplate.opsForGeo().geoRadius(key,
                    within);
            return geoResults;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 
     * @Title: geoRadius
     * @Description: TODO(以给定的经纬度为中心， 返回键包含的位置元素当中， 与中心的距离不超过给定最大距离的所有位置元素。 WITHDIST: 在返回位置元素的同时， 将位置元素与中心之间的距离也一并返回。
     *               距离的单位和用户给定的范围单位保持一致。 WITHCOORD: 将位置元素的经度和维度也一并返回。 ASC: 根据中心的位置， 按照从近到远的方式返回位置元素。 DESC: 根据中心的位置，
     *               按照从远到近的方式返回位置元素。 在默认情况下， GEORADIUS 命令会返回所有匹配的位置元素。 虽然用户可以使用 COUNT <count> 选项去获取前 N 个匹配元素，
     *               但是因为命令在内部可能会需要对所有被匹配的元素进行处理， 所以在对一个非常大的区域进行搜索时， 即使只使用 COUNT 选项去获取少量元素， 命令的执行速度也可能会非常慢。 但是从另一方面来说，
     *               使用 COUNT 选项去减少需要返回的元素数量， 对于减少带宽来说仍然是非常有用的。 )
     * @author cwq
     * @date 2019年3月29日 上午11:19:00
     * @param key
     * @param within
     * @return
     */
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadius(String key, Circle within,
            GeoRadiusCommandArgs args) {
        try {
            GeoResults<RedisGeoCommands.GeoLocation<Object>> geoResults = redisTemplate.opsForGeo().geoRadius(key,
                    within, args);
            return geoResults;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 
     * @Title: geoRadiusbymember
     * @Description: TODO(以给定的经纬度为中心， 返回键包含的位置元素当中， 与中心的距离不超过给定最大距离的所有位置元素。)
     * @author cwq
     * @date 2019年3月29日 上午11:30:32
     * @param key
     * @param member
     * @param distance
     * @return
     */
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadiusByMember(String key, Object member,
            Distance distance) {
        try {
            GeoResults<RedisGeoCommands.GeoLocation<Object>> geoResults = redisTemplate.opsForGeo()
                    .geoRadiusByMember(key, member, distance);
            return geoResults;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 
     * @Title: geoRadiusByMember
     * @Description: TODO(以给定的经纬度为中心， 返回键包含的位置元素当中， 与中心的距离不超过给定最大距离的所有位置元素。)
     * @author cwq
     * @date 2019年3月29日 上午11:32:10
     * @param key
     * @param member
     * @param radius
     * @return
     */
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadiusByMember(String key, Object member,
            double radius) {
        try {
            GeoResults<RedisGeoCommands.GeoLocation<Object>> geoResults = redisTemplate.opsForGeo()
                    .geoRadiusByMember(key, member, radius);
            return geoResults;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 
     * @Title: geoRadiusByMember
     * @Description: TODO(以给定的经纬度为中心， 返回键包含的位置元素当中， 与中心的距离不超过给定最大距离的所有位置元素。参数可选择。)
     * @author cwq
     * @date 2019年3月29日 上午11:33:19
     * @param key
     * @param member
     * @param distance
     * @param args
     * @return
     */
    public GeoResults<RedisGeoCommands.GeoLocation<Object>> geoRadiusByMember(String key, Object member,
            Distance distance, GeoRadiusCommandArgs args) {
        try {
            GeoResults<RedisGeoCommands.GeoLocation<Object>> geoResults = redisTemplate.opsForGeo()
                    .geoRadiusByMember(key, member, distance, args);
            return geoResults;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static boolean setIfAbsent(final String key, final Serializable value, final long exptime) {
        Boolean b = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                Object obj = connection.execute("set", keySerializer.serialize(key), valueSerializer.serialize(value),
                        SafeEncoder.encode("NX"), SafeEncoder.encode("EX"), Protocol.toByteArray(exptime));
                return obj != null;
            }
        });
        return b;
    }
}
