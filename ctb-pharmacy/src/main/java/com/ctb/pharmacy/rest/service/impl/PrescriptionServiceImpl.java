/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月1日
 * Created by hhy
 */
package com.ctb.pharmacy.rest.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;
import com.ctb.commons.constants.BizConstant;
import com.ctb.commons.constants.TradeConstant;
import com.ctb.commons.entity.PrescriptionRecord;
import com.ctb.framework.commons.exception.SystemException;
import com.ctb.framework.commons.generator.HashSplitTableGenerator;
import com.ctb.framework.commons.generator.OrderNoGenerator;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.pharmacy.cache.PrescriptionRecordCache;
import com.ctb.pharmacy.rest.service.PrescriptionService;
import com.ctb.resources.mapper.biz.DataPaidMzFeeMapper;
import com.ctb.resources.mapper.biz.PrescriptionRecordMapper;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @ClassName: com.ctb.mobile.rest.service.impl.PrescriptionRecordServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年4月1日 下午4:41:53
 */

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

	private static Logger logger = LoggerFactory.getLogger(PrescriptionServiceImpl.class);

    private ForkJoinPool queryPool = new ForkJoinPool();
	
	@Autowired
	public PrescriptionRecordMapper prescriptionRecordMapper;

	@Autowired
	public DataPaidMzFeeMapper dataPaidMzFeeMapper;
	
	@Autowired
	private PrescriptionRecordCache prescriptionRecordCache;
 
	@Override
	public PrescriptionRecord getOrder(String orderNo, String mzfeeId, String cardNo, String hospitalCode) {
		try {
			Example example = new Example(PrescriptionRecord.class);
			Example.Criteria criteria = example.createCriteria();

			if (StringUtils.isNotBlank(orderNo))
				criteria.andEqualTo("orderNo", orderNo);
			if (StringUtils.isNotBlank(mzfeeId))
				criteria.andEqualTo("mzfeeId", mzfeeId);
			if (StringUtils.isNotBlank(cardNo))
				criteria.andEqualTo("cardNo", cardNo);
			if (StringUtils.isNotBlank(hospitalCode))
				criteria.andEqualTo("hospitalCode", hospitalCode);

			PrescriptionRecord entity = prescriptionRecordMapper.selectOneByExample(example);
			if (entity != null) {
				return entity;
			}
		} catch (Exception e) {
			logger.error("" + e.toString());
		}
		return null;
	}



	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int updateOrder(PrescriptionRecord prescriptionRecord) {
		try {
			int res = prescriptionRecordMapper.updatePrescriptionRecord(prescriptionRecord);
			if (res == 1) {
			    prescriptionRecordCache.updatePrescriptionRecord(prescriptionRecord);
				return res;
			} else {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
			}

		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
		}
		return 0;
	}
	

    @Override
    public List<PrescriptionRecord> findListByParams(Map map) {
        // Map param = new HashMap(map);
        Map<String, Object> param = new HashMap<String, Object>(map);
        
        String sort = (String) map.get("sortType");
        if(sort!=null) {
            param.put("sortType", sort);
        }else {
            param.put("sortType", "DESC");
        }
        
        List<ShardMeta> shardMetas = generateShardMetas();
        Integer pageSize = (Integer) param.get("pageSize");
        boolean findRange = StringUtils.isBlank((String) param.get("orderNo")) && pageSize != null;
        Integer startNum = findRange ? (Integer) param.get("startNum") : 0;
        Integer subStartNum = startNum / shardMetas.size();
        
        List<PrescriptionRecord> allOrders = new ArrayList<PrescriptionRecord>();
        try {
            // 指定订单号则跳过
            if (findRange) {
                param.put("subStartNum", subStartNum);
                // 提交第一次查询，查询每个分片的订单范围
                List<Future<List<Long>>> rangeTasks = submitRangeQuery(param, shardMetas, queryPool);
                long minTime = Long.MAX_VALUE;
                long maxTime = Long.MIN_VALUE;
                Iterator<ShardMeta> iterator = shardMetas.iterator();
                // 根据每个分片的订单范围计算出全局的订单范围
                for (int i = 0; i < rangeTasks.size(); i++) {
                    ShardMeta shardMeta = iterator.next();
                    List<Long> timestamps = rangeTasks.get(i).get();
                    if (timestamps.isEmpty()) {
                        continue;
                    }
                    
                    shardMeta.setMaxTime(timestamps.get(0));
                    shardMeta.setMinTime(timestamps.get(timestamps.size() - 1));
                    if (maxTime < shardMeta.getMaxTime()) {
                        maxTime = shardMeta.getMaxTime();
                    }
                    if (minTime > shardMeta.getMinTime()) {
                        minTime = shardMeta.getMinTime();
                    }
                }
                param.put("startTime", minTime);
                param.put("endTime", maxTime);
            }
            
            // 提交第二次查询，查询每个分片符合范围的订单
            List<Future<List<PrescriptionRecord>>> orderTasks = submitOrderQuery(param, shardMetas, queryPool);
            int offset = 0;
            Iterator<ShardMeta> iterator = shardMetas.iterator();
            List<ShardMeta> offsetShards = new ArrayList<ShardMeta>();
            // 合并所有分片的结果
            for (Future<List<PrescriptionRecord>> orderTask : orderTasks) {
                ShardMeta shardMeta = iterator.next();
                List<PrescriptionRecord> orderViews = orderTask.get();
                if (findRange) {
                    if (shardMeta.getMaxTime() == Long.MIN_VALUE) {
                        offsetShards.add(shardMeta);
                    } else {
                        int shardAheadCount = getAheadCount(orderViews, shardMeta.getMaxTime());
                        // 累加全局偏移量
                        offset += subStartNum - shardAheadCount;
                        // 删除不需要后补齐查询的分片信息
                        if (orderViews.size() - shardAheadCount < pageSize)
                            iterator.remove();
                    }
                }
                allOrders.addAll(orderViews);
            }
            
            if (!findRange || allOrders.isEmpty())
                return allOrders;
            /*
            // 进行分片偏移量查询
            if (!offsetShards.isEmpty()) {
                Object startTime = param.get("startTime");
                param.put("startTime", param.get("endTime"));
                param.put("endTime", map.get("endTime"));
                
                List<Future<Long>> offsetTasks = submitOffsetQuery(param, offsetShards, queryPool);
                for (Future<Long> offsetTask : offsetTasks)
                    offset += offsetTask.get();
                
                param.put("startTime", startTime);
            }
            
            // 进行第三次查询
            int targetOffset = startNum - offset;
            int endIndex = targetOffset + pageSize;
            if (endIndex > allOrders.size() && !shardMetas.isEmpty()) {
                int backfill = endIndex - allOrders.size();
                param.put("backfill", backfill);
                param.put("endTime", param.get("startTime"));
                param.put("startTime", map.get("startTime"));
                orderTasks = submitOrderQuery(param, shardMetas, queryPool);
                for (Future<List<PrescriptionRecord>> orderTask : orderTasks)
                    allOrders.addAll(orderTask.get());
            }
            
            */
            int targetOffset = startNum;
            int endIndex = startNum + pageSize;
            
            if (allOrders.size() <= targetOffset)
                return Collections.EMPTY_LIST;
            
            
            // 对合并后的结果统一排序，并根据全局偏移量定位分页的第一条订单的偏移量，提取分页结果
                    
            sortByCreateTime(allOrders);
            return allOrders.subList(targetOffset, Math.min(endIndex, allOrders.size()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException(e);
        }
    }
    
    protected class ShardMeta {
        private String tableName;
        private long minTime;
        private long maxTime;
        
        public ShardMeta(String tableName) {
            this.tableName = tableName;
            minTime = Long.MAX_VALUE;
            maxTime = Long.MIN_VALUE;
        }
        
        public String getTableName() {
            return tableName;
        }
        
        public long getMinTime() {
            return minTime;
        }
        
        public void setMinTime(long minTime) {
            this.minTime = minTime;
        }
        
        public long getMaxTime() {
            return maxTime;
        }
        
        public void setMaxTime(long maxTime) {
            this.maxTime = maxTime;
        }
    }
    
    /**
     * 生成分片信息
     *
     * @param orderType
     *            查询的订单类型
     * @return
     */
    private List<ShardMeta> generateShardMetas() {
        
        List<ShardMeta> shardMetas = new LinkedList<ShardMeta>();
        for (int j = 1; j <= HashSplitTableGenerator.subTableCount; j++) {
            shardMetas.add(new ShardMeta(HashSplitTableGenerator.PRESCRIPTION_RECORD_TABLE_NAME + "_" + j));
        }
        return shardMetas;
    }
    
    /**
     * 查询分片中符合范围（创建时间）的订单
     *
     * @param map
     * @param shardMetas
     * @return
     */
    private List<Future<List<PrescriptionRecord>>> submitOrderQuery(Map map, List<ShardMeta> shardMetas,
            ExecutorService queryPool) {
        List<Future<List<PrescriptionRecord>>> tasks = new ArrayList<Future<List<PrescriptionRecord>>>();
        for (ShardMeta shardMeta : shardMetas) {
            final Map<Object, Object> param = new HashMap<Object, Object>(map);
            param.put("tableName", shardMeta.getTableName());
            tasks.add(queryPool.submit(new Callable<List<PrescriptionRecord>>() {
                @Override
                public List<PrescriptionRecord> call() throws Exception {
                    return prescriptionRecordMapper.findListByParams(param);
                }
            }));
        }
        return tasks;
    }
    
    /**
     * 查询分片的订单偏移量
     *
     * @param map
     * @param shardMetas
     * @return
     */
    private List<Future<Long>> submitOffsetQuery(Map map, List<ShardMeta> shardMetas, ExecutorService queryPool) {
        List<Future<Long>> tasks = new ArrayList<Future<Long>>();
        for (ShardMeta shardMeta : shardMetas) {
            final Map<Object, Object> param = new HashMap<Object, Object>(map);
            param.put("tableName", shardMeta.getTableName());
            tasks.add(queryPool.submit(new Callable<Long>() {
                @Override
                public Long call() throws Exception {
                    return prescriptionRecordMapper.findOffsetByParams(param);
                }
            }));
        }
        return tasks;
    }
    
    /**
     * 查询分片的订单范围（创建时间）
     *
     * @param map
     * @param shardMetas
     * @return
     */
    private List<Future<List<Long>>> submitRangeQuery(Map map, List<ShardMeta> shardMetas, ExecutorService queryPool) {
        List<Future<List<Long>>> tasks = new ArrayList<Future<List<Long>>>();
        for (ShardMeta shardMeta : shardMetas) {
            final Map<Object, Object> param = new HashMap<Object, Object>(map);
            param.put("tableName", shardMeta.getTableName());
            tasks.add(queryPool.submit(new Callable<List<Long>>() {
                @Override
                public List<Long> call() throws Exception {
                    return prescriptionRecordMapper.findRangeByParams(param);
                }
            }));
        }
        return tasks;
    }
    
    /**
     * 倒序排列
     *
     * @param orderViews
     */
    private void sortByCreateTime(List<PrescriptionRecord> orderViews) {
        Collections.sort(orderViews);
    }
    
    /**
     * 计算分片的偏移量（索引值）
     *
     * @param orderViews
     * @param target
     * @return
     */
    private int getAheadCount(List<PrescriptionRecord> orderViews, long target) {
        int aheadCount = 0;
        for (int i = 0; i < orderViews.size(); i++) {
            if (orderViews.get(i).getCreateTime() > target) {
                aheadCount++;
            }
        }
        
        return aheadCount;
    }
    
    @Override
    public PrescriptionRecord queryByOrderNo(String orderNo, String hashTableName) {
        PrescriptionRecord prescriptionRecord = null;
        try {
            Map<String, String> params = new HashMap<String, String>();
            params.put("orderNo", orderNo);
            params.put("hashTableName", hashTableName);
            
            prescriptionRecord = prescriptionRecordMapper.findPrescriptionByOrderNo(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return prescriptionRecord;
    }
    
    @Override
    public PrescriptionRecord findRecordByOrderNo(String orderNo) {
        logger.info("orderNo:{} ", new Object[] { orderNo });
        
        PrescriptionRecord record = null;
        try {
            
            record = prescriptionRecordCache.getPrescriptionRecordByOrderNo(orderNo);
            
            if (record == null) {
                
                Integer splitKeyVal = Integer.valueOf(OrderNoGenerator.getHashVal(orderNo));
                String hashTableName = HashSplitTableGenerator.getPrescriptionRecordHashTable(splitKeyVal, false);
                
                record = queryByOrderNo(orderNo, hashTableName);
                logger.info("prescriptionRecord json:{}", JSON.toJSONString(record));
                
                if (record != null) {
                    prescriptionRecordCache.updatePrescriptionRecord(record);
                }
            }
            
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return record;        
    }

}