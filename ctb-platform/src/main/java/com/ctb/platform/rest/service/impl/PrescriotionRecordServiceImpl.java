/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月27日
 * Created by ckm
 */
package com.ctb.platform.rest.service.impl;

import java.util.ArrayList;
import java.util.Collections;
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

import com.ctb.commons.entity.PrescriptionRecord;
import com.ctb.framework.commons.exception.SystemException;
import com.ctb.framework.commons.generator.HashSplitTableGenerator;
import com.ctb.platform.rest.service.PrescriotionRecordService;
import com.ctb.resources.mapper.biz.PrescriptionRecordMapper;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: com.ctb.platform.rest.service.impl.PrescriotionRecordServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年3月27日 下午4:22:44
 */
@Service
public class PrescriotionRecordServiceImpl implements PrescriotionRecordService {

	private static Logger logger = LoggerFactory.getLogger(PrescriotionRecordServiceImpl.class);

	@Autowired
	private PrescriptionRecordMapper prescriptionRecordMapper;
	
	private ForkJoinPool queryPool = new ForkJoinPool();

	@Override
	public int update(PrescriptionRecord prescriptionRecord) {
		try {
			int res = prescriptionRecordMapper.updateByPrimaryKeySelective(prescriptionRecord);
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("修改订单失败，修改数据异常！:::" + e.toString());
			;
			return 0;
		}
	}

	@Override
	public PrescriptionRecord queryDetail(PrescriptionRecord prescriptionRecord) {
		// TODO Auto-generated method stub
		try {
			Example example = new Example(PrescriptionRecord.class);
			Example.Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(prescriptionRecord.getId())) {
				criteria.andEqualTo("id", prescriptionRecord.getId());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getPharmacyId())) {
				criteria.andEqualTo("pharmacyId", prescriptionRecord.getPharmacyId());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getHospitalId())) {
				criteria.andEqualTo("hospitalId", prescriptionRecord.getHospitalId());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getHospitalName())) {
				criteria.andEqualTo("hospitalName", prescriptionRecord.getHospitalName());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getHospitalCode())) {
				criteria.andEqualTo("hospitalCode", prescriptionRecord.getHospitalCode());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getHospitalBranchId())) {
				criteria.andEqualTo("hospitalBranchId", prescriptionRecord.getHospitalBranchId());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getHospitalBranchCode())) {
				criteria.andEqualTo("hospitalBranchCode", prescriptionRecord.getHospitalBranchCode());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getMzFeeId())) {
				criteria.andEqualTo("mzfeeId", prescriptionRecord.getMzFeeId());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getTradeMode()))) {
				criteria.andEqualTo("tradeMode", prescriptionRecord.getTradeMode());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPlatformMode()))) {
				criteria.andEqualTo("platformMode", prescriptionRecord.getPlatformMode());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getAgtOrdNum())) {
				criteria.andEqualTo("agtOrdNum", prescriptionRecord.getAgtOrdNum());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getAgtRefundOrdNum())) {
				criteria.andEqualTo("agtRefundOrdNum", prescriptionRecord.getAgtRefundOrdNum());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPayStatus()))) {
				criteria.andEqualTo("payStatus", prescriptionRecord.getPayStatus());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPharmacyStatus()))) {
				criteria.andEqualTo("prescriptionStatus", prescriptionRecord.getPharmacyStatus());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPharmacyStatus()))) {
				criteria.andEqualTo("pharmacyStatus", prescriptionRecord.getPharmacyStatus());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getOrderNo())) {
				criteria.andEqualTo("orderNo", prescriptionRecord.getOrderNo());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getRefundOrderNo())) {
				criteria.andEqualTo("refundOrderNo", prescriptionRecord.getRefundOrderNo());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getDeptCode())) {
				criteria.andEqualTo("deptCode", prescriptionRecord.getDeptCode());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getDoctorCode())) {
				criteria.andEqualTo("doctorCode", prescriptionRecord.getDoctorCode());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getDoctorName())) {
				criteria.andEqualTo("doctorName", prescriptionRecord.getDoctorName());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getDeptName())) {
				criteria.andEqualTo("deptName", prescriptionRecord.getDeptName());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getRecordType())) {
				criteria.andEqualTo("recordType", prescriptionRecord.getRecordType());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getCardType())) {
				criteria.andEqualTo("cardType", prescriptionRecord.getCardType());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getCardNo()))) {
				criteria.andEqualTo("cardNo", prescriptionRecord.getCardNo());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPatientName()))) {
				criteria.andEqualTo("patientName", prescriptionRecord.getPatientName());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPharmacyId()))) {
				criteria.andEqualTo("pharmacyId", prescriptionRecord.getPharmacyId());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPharmacyBranchId()))) {
				criteria.andEqualTo("pharmacyBranchId", prescriptionRecord.getPharmacyBranchId());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getReviewStatus()))) {
				criteria.andEqualTo("reviewStatus", prescriptionRecord.getReviewStatus());
			}
			PrescriptionRecord prescriptionRecord2Info = prescriptionRecordMapper.selectOneByExample(example);
			return prescriptionRecord2Info;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询订单数据失败，查询数据异常！:::" + e.toString());
			return new PrescriptionRecord();
		}
	}

	@Override
	public List<PrescriptionRecord> prescriptionRecordsListPage(PrescriptionRecord prescriptionRecord, String startTime,
			String endTime, Integer page, Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, pageSize);
		try {
			Example example = new Example(PrescriptionRecord.class);
			Example.Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(prescriptionRecord.getId())) {
				criteria.andEqualTo("id", prescriptionRecord.getId());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getPharmacyId())) {
				criteria.andEqualTo("pharmacyId", prescriptionRecord.getPharmacyId());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getHospitalId())) {
				criteria.andEqualTo("hospitalId", prescriptionRecord.getHospitalId());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getHospitalName())) {
				criteria.andEqualTo("hospitalName", prescriptionRecord.getHospitalName());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getHospitalCode())) {
				criteria.andEqualTo("hospitalCode", prescriptionRecord.getHospitalCode());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getHospitalBranchId())) {
				criteria.andEqualTo("hospitalBranchId", prescriptionRecord.getHospitalBranchId());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getHospitalBranchCode())) {
				criteria.andEqualTo("hospitalBranchCode", prescriptionRecord.getHospitalBranchCode());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getMzFeeId())) {
				criteria.andEqualTo("mzfeeId", prescriptionRecord.getMzFeeId());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getTradeMode()))) {
				criteria.andEqualTo("tradeMode", prescriptionRecord.getTradeMode());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPlatformMode()))) {
				criteria.andEqualTo("platformMode", prescriptionRecord.getPlatformMode());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getAgtOrdNum())) {
				criteria.andEqualTo("agtOrdNum", prescriptionRecord.getAgtOrdNum());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getAgtRefundOrdNum())) {
				criteria.andEqualTo("agtRefundOrdNum", prescriptionRecord.getAgtRefundOrdNum());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPayStatus()))) {
				criteria.andEqualTo("payStatus", prescriptionRecord.getPayStatus());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPharmacyStatus()))) {
				criteria.andEqualTo("prescriptionStatus", prescriptionRecord.getPharmacyStatus());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPharmacyStatus()))) {
				criteria.andEqualTo("pharmacyStatus", prescriptionRecord.getPharmacyStatus());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getOrderNo())) {
				criteria.andEqualTo("orderNo", prescriptionRecord.getOrderNo());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getRefundOrderNo())) {
				criteria.andEqualTo("refundOrderNo", prescriptionRecord.getRefundOrderNo());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getDeptCode())) {
				criteria.andEqualTo("deptCode", prescriptionRecord.getDeptCode());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getDoctorCode())) {
				criteria.andEqualTo("doctorCode", prescriptionRecord.getDoctorCode());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getDoctorName())) {
				criteria.andEqualTo("doctorName", prescriptionRecord.getDoctorName());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getDeptName())) {
				criteria.andEqualTo("deptName", prescriptionRecord.getDeptName());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getRecordType())) {
				criteria.andEqualTo("recordType", prescriptionRecord.getRecordType());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getCardType())) {
				criteria.andEqualTo("cardType", prescriptionRecord.getCardType());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getCardNo()))) {
				criteria.andEqualTo("cardNo", prescriptionRecord.getCardNo());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPatientName()))) {
				criteria.andEqualTo("patientName", prescriptionRecord.getPatientName());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPharmacyId()))) {
				criteria.andEqualTo("pharmacyId", prescriptionRecord.getPharmacyId());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPharmacyBranchId()))) {
				criteria.andEqualTo("pharmacyBranchId", prescriptionRecord.getPharmacyBranchId());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getReviewStatus()))) {
				criteria.andEqualTo("reviewStatus", prescriptionRecord.getReviewStatus());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getCreateTime()))) {
				criteria.andEqualTo("createTime", prescriptionRecord.getCreateTime());
			}
			if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
				criteria.andBetween("createTime", startTime, endTime);
			}
			example.orderBy("createTime").desc();
			List<PrescriptionRecord> prescriptionRecords = prescriptionRecordMapper.selectByExample(example);
			return prescriptionRecords;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询订单列表失败，查询数据异常！:::" + e.toString());
			return new ArrayList<PrescriptionRecord>();
		}
	}

	@Override
	public List<PrescriptionRecord> prescriptionRecordsList(PrescriptionRecord prescriptionRecord, String startTime,
			String endTime) {
		try {
			Example example = new Example(PrescriptionRecord.class);
			Example.Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(prescriptionRecord.getId())) {
				criteria.andEqualTo("id", prescriptionRecord.getId());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getPharmacyId())) {
				criteria.andEqualTo("pharmacyId", prescriptionRecord.getPharmacyId());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getHospitalId())) {
				criteria.andEqualTo("hospitalId", prescriptionRecord.getHospitalId());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getHospitalName())) {
				criteria.andEqualTo("hospitalName", prescriptionRecord.getHospitalName());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getHospitalCode())) {
				criteria.andEqualTo("hospitalCode", prescriptionRecord.getHospitalCode());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getHospitalBranchId())) {
				criteria.andEqualTo("hospitalBranchId", prescriptionRecord.getHospitalBranchId());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getHospitalBranchCode())) {
				criteria.andEqualTo("hospitalBranchCode", prescriptionRecord.getHospitalBranchCode());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getMzFeeId())) {
				criteria.andEqualTo("mzfeeId", prescriptionRecord.getMzFeeId());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getTradeMode()))) {
				criteria.andEqualTo("tradeMode", prescriptionRecord.getTradeMode());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPlatformMode()))) {
				criteria.andEqualTo("platformMode", prescriptionRecord.getPlatformMode());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getAgtOrdNum())) {
				criteria.andEqualTo("agtOrdNum", prescriptionRecord.getAgtOrdNum());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getAgtRefundOrdNum())) {
				criteria.andEqualTo("agtRefundOrdNum", prescriptionRecord.getAgtRefundOrdNum());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPayStatus()))) {
				criteria.andEqualTo("payStatus", prescriptionRecord.getPayStatus());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPharmacyStatus()))) {
				criteria.andEqualTo("prescriptionStatus", prescriptionRecord.getPharmacyStatus());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPharmacyStatus()))) {
				criteria.andEqualTo("pharmacyStatus", prescriptionRecord.getPharmacyStatus());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getOrderNo())) {
				criteria.andEqualTo("orderNo", prescriptionRecord.getOrderNo());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getRefundOrderNo())) {
				criteria.andEqualTo("refundOrderNo", prescriptionRecord.getRefundOrderNo());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getDeptCode())) {
				criteria.andEqualTo("deptCode", prescriptionRecord.getDeptCode());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getDoctorCode())) {
				criteria.andEqualTo("doctorCode", prescriptionRecord.getDoctorCode());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getDoctorName())) {
				criteria.andEqualTo("doctorName", prescriptionRecord.getDoctorName());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getDeptName())) {
				criteria.andEqualTo("deptName", prescriptionRecord.getDeptName());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getRecordType())) {
				criteria.andEqualTo("recordType", prescriptionRecord.getRecordType());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getCardType())) {
				criteria.andEqualTo("cardType", prescriptionRecord.getCardType());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getCardNo()))) {
				criteria.andEqualTo("cardNo", prescriptionRecord.getCardNo());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPatientName()))) {
				criteria.andEqualTo("patientName", prescriptionRecord.getPatientName());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPharmacyId()))) {
				criteria.andEqualTo("pharmacyId", prescriptionRecord.getPharmacyId());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPharmacyBranchId()))) {
				criteria.andEqualTo("pharmacyBranchId", prescriptionRecord.getPharmacyBranchId());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getReviewStatus()))) {
				criteria.andEqualTo("reviewStatus", prescriptionRecord.getReviewStatus());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getCreateTime()))) {
				criteria.andEqualTo("createTime", prescriptionRecord.getCreateTime());
			}
			if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
				criteria.andBetween("createTime", startTime, endTime);
			}
			example.orderBy("createTime").desc();
			List<PrescriptionRecord> prescriptionRecords = prescriptionRecordMapper.selectByExample(example);
			return prescriptionRecords;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询订单列表失败，查询数据异常！:::" + e.toString());
			return new ArrayList<PrescriptionRecord>();
		}
	}

	@Override
	public int total(PrescriptionRecord prescriptionRecord, String startTime, String endTime) {
		// TODO Auto-generated method stub
		try {
			Example example = new Example(PrescriptionRecord.class);
			Example.Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(prescriptionRecord.getId())) {
				criteria.andEqualTo("id", prescriptionRecord.getId());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getPharmacyId())) {
				criteria.andEqualTo("pharmacyId", prescriptionRecord.getPharmacyId());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getHospitalId())) {
				criteria.andEqualTo("hospitalId", prescriptionRecord.getHospitalId());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getHospitalName())) {
				criteria.andEqualTo("hospitalName", prescriptionRecord.getHospitalName());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getHospitalCode())) {
				criteria.andEqualTo("hospitalCode", prescriptionRecord.getHospitalCode());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getHospitalBranchId())) {
				criteria.andEqualTo("hospitalBranchId", prescriptionRecord.getHospitalBranchId());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getHospitalBranchCode())) {
				criteria.andEqualTo("hospitalBranchCode", prescriptionRecord.getHospitalBranchCode());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getMzFeeId())) {
				criteria.andEqualTo("mzfeeId", prescriptionRecord.getMzFeeId());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getTradeMode()))) {
				criteria.andEqualTo("tradeMode", prescriptionRecord.getTradeMode());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPlatformMode()))) {
				criteria.andEqualTo("platformMode", prescriptionRecord.getPlatformMode());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getAgtOrdNum())) {
				criteria.andEqualTo("agtOrdNum", prescriptionRecord.getAgtOrdNum());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getAgtRefundOrdNum())) {
				criteria.andEqualTo("agtRefundOrdNum", prescriptionRecord.getAgtRefundOrdNum());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPayStatus()))) {
				criteria.andEqualTo("payStatus", prescriptionRecord.getPayStatus());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPharmacyStatus()))) {
				criteria.andEqualTo("prescriptionStatus", prescriptionRecord.getPharmacyStatus());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPharmacyStatus()))) {
				criteria.andEqualTo("pharmacyStatus", prescriptionRecord.getPharmacyStatus());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getOrderNo())) {
				criteria.andEqualTo("orderNo", prescriptionRecord.getOrderNo());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getRefundOrderNo())) {
				criteria.andEqualTo("refundOrderNo", prescriptionRecord.getRefundOrderNo());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getDeptCode())) {
				criteria.andEqualTo("deptCode", prescriptionRecord.getDeptCode());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getDoctorCode())) {
				criteria.andEqualTo("doctorCode", prescriptionRecord.getDoctorCode());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getDoctorName())) {
				criteria.andEqualTo("doctorName", prescriptionRecord.getDoctorName());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getDeptName())) {
				criteria.andEqualTo("deptName", prescriptionRecord.getDeptName());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getRecordType())) {
				criteria.andEqualTo("recordType", prescriptionRecord.getRecordType());
			}
			if (StringUtils.isNotBlank(prescriptionRecord.getCardType())) {
				criteria.andEqualTo("cardType", prescriptionRecord.getCardType());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getCardNo()))) {
				criteria.andEqualTo("cardNo", prescriptionRecord.getCardNo());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPatientName()))) {
				criteria.andEqualTo("patientName", prescriptionRecord.getPatientName());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPharmacyId()))) {
				criteria.andEqualTo("pharmacyId", prescriptionRecord.getPharmacyId());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getPharmacyBranchId()))) {
				criteria.andEqualTo("pharmacyBranchId", prescriptionRecord.getPharmacyBranchId());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getReviewStatus()))) {
				criteria.andEqualTo("reviewStatus", prescriptionRecord.getReviewStatus());
			}
			if (StringUtils.isNotBlank(String.valueOf(prescriptionRecord.getCreateTime()))) {
				criteria.andEqualTo("createTime", prescriptionRecord.getCreateTime());
			}
			if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
				criteria.andBetween("createTime", startTime, endTime);
			}
			example.orderBy("createTime").desc();
			return prescriptionRecordMapper.selectCountByExample(example);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询总记录数失败，查询数据异常！:::" + e.toString());
			return 0;
		}
	}
	
    @Override
    public List<PrescriptionRecord> findListByParams(Map map) {
        // Map param = new HashMap(map);
        Map<String, Object> param = new HashMap<String, Object>(map);
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
                // 提交第一次查询，查询每个分片的订单范围--拿到8*size个订单的时间全部时间
                List<Future<List<Long>>> rangeTasks = submitRangeQuery(param, shardMetas, queryPool);
                long minTime = Long.MAX_VALUE;
                long maxTime = Long.MIN_VALUE;
                Iterator<ShardMeta> iterator = shardMetas.iterator();
                // 根据每个分片的订单范围计算出全局的订单范围----即时间范围（拿到最大时间和最小时间）
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
                //allOrders.addAll(orderTask.get());
            }

            if (!findRange || allOrders.isEmpty())
                return allOrders;

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
            // int targetOffset = startNum - offset;  ckm 代码编写
            int targetOffset = startNum;
            int endIndex = targetOffset + pageSize;
            if (endIndex > allOrders.size() && !shardMetas.isEmpty()) {
                int backfill = endIndex - allOrders.size();
                param.put("backfill", backfill);
                param.put("endTime", param.get("startTime"));
                param.put("startTime", map.get("startTime"));
                orderTasks = submitOrderQuery(param, shardMetas, queryPool);
                //for 循环后多了两条记录
//                for (Future<List<PrescriptionRecord>> orderTask : orderTasks)
//                    allOrders.addAll(orderTask.get());
            }

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
     * @param orderType 查询的订单类型
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
        Map<String, String> params = new HashMap<String, String>();
        params.put("orderNo", orderNo);
        params.put("hashTableName", hashTableName);
        PrescriptionRecord prescriptionRecord = prescriptionRecordMapper.findPrescriptionByOrderNo(params);
        return prescriptionRecord;
    }
    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PrescriptionRecord updatePrescriptionRecord(PrescriptionRecord prescriptionRecord) {
        try {
            int res = prescriptionRecordMapper.updatePrescriptionRecord(prescriptionRecord);
            if (res == 1) {
                return prescriptionRecord;
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("更新处方订单失败,更新异常！:::" + e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
            return new PrescriptionRecord();
        }
        return new PrescriptionRecord();
    }

    @Override
    public int total(Map map) {
        Map<String, Object> param = new HashMap<String, Object>(map);
        List<ShardMeta> shardMetas = generateShardMetas();
        List<PrescriptionRecord> allOrders = new ArrayList<PrescriptionRecord>();
        // 提交第二次查询，查询每个分片符合范围的订单
        try {
            List<Future<List<PrescriptionRecord>>> orderTasks = submitOrderQuery(param, shardMetas, queryPool);
            Iterator<ShardMeta> iterator = shardMetas.iterator();
            // 合并所有分片的结果
            for (Future<List<PrescriptionRecord>> orderTask : orderTasks) {
                List<PrescriptionRecord> orderViews = orderTask.get();
                allOrders.addAll(orderViews);
            }
            return allOrders.size();
        } catch (Exception e) {
            return 0;
        }
    }

}
