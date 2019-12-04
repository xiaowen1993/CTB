/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月27日
 * Created by ckm
 */
package com.ctb.platform.rest.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.entity.DataPaidMzFee;
import com.ctb.commons.entity.DataPayMzFee;
import com.ctb.commons.entity.PrescriptionRecord;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.generator.HashSplitTableGenerator;
import com.ctb.platform.rest.service.DataPaidMzFeeService;
import com.ctb.platform.rest.service.DataPayMzFeeService;
import com.ctb.platform.rest.service.PrescriotionRecordService;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: com.ctb.platform.rest.controller.PrescriotionRecordController
 * @Description: TODO(订单管理)
 * @author ckm
 * @date 2019年3月27日 下午7:07:00
 */
@RefreshScope
@RestController
@RequestMapping("/prescriotionRecord")
public class PrescriotionRecordController {
	private static Logger logger = LoggerFactory.getLogger(PrescriotionRecordController.class);

	@Autowired
	EurekaDiscoveryClient discoveryClient;

	@Autowired
	private PrescriotionRecordService prescriotionRecordService;

	@Autowired
	private DataPaidMzFeeService dataPaidMzFeeService;

	@Autowired
	private DataPayMzFeeService dataPayMzFeeService;

	/**
	 * 
	 * @Title: list
	 * @Description: TODO(分页订单列表)
	 * @author ckm
	 * @date 2019年3月27日 下午8:15:22
	 * @param pharmacyId
	 * @param hospitalId
	 * @param payStatus
	 * @param prescriptionStatus
	 * @param pharmacyStatus
	 * @param orderNo
	 * @param cardNo
	 * @param startTime
	 * @param endTime
	 * @param page
	 * @param size
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public RespBody list(@RequestParam(required = false, value = "pharmacyId", defaultValue = "") String pharmacyId,
			@RequestParam(required = false, value = "hospitalId", defaultValue = "") String hospitalId,
			@RequestParam(required = false, value = "payStatus", defaultValue = "") String payStatus,
			@RequestParam(required = false, value = "prescriptionStatus", defaultValue = "") String prescriptionStatus,
			@RequestParam(required = false, value = "pharmacyStatus", defaultValue = "") String pharmacyStatus,
			@RequestParam(required = false, value = "orderNo", defaultValue = "") String orderNo,
			@RequestParam(required = false, value = "cardNo", defaultValue = "") String cardNo,
			@RequestParam(required = false, value = "startTime", defaultValue = "") String startTime,
			@RequestParam(required = false, value = "endTime", defaultValue = "") String endTime,
			@RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
			@RequestParam(required = false, value = "size", defaultValue = "10") Integer size) {
		try {
		    
		    Map<String, Object> map = new HashMap<String, Object>();
            map.put("pharmacyId", pharmacyId);
            map.put("hospitalId", hospitalId);
            map.put("payStatus", payStatus);
            map.put("prescriptionStatus", prescriptionStatus);
            map.put("pharmacyStatus", pharmacyStatus);
            map.put("orderNo", orderNo);
            map.put("cardNo", cardNo);
            map.put("startTime", startTime);
            map.put("endTime", endTime);
            map.put("startNum", (page - 1)*size);
            map.put("pageSize", size);
            int total = prescriotionRecordService.total(map);
            List<PrescriptionRecord> prescriptionRecordsList = prescriotionRecordService.findListByParams(map);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("total", total);
            jsonObject.fluentPut("prescriptionRecordsList", prescriptionRecordsList);
            return new RespBody(Status.OK, jsonObject);

		    /*
			PrescriptionRecord entity = new PrescriptionRecord(hospitalId, orderNo, cardNo, pharmacyId);
			if (StringUtils.isNotBlank(payStatus)) {
				entity.setPayStatus(Integer.valueOf(payStatus));
			}
			if (StringUtils.isNotBlank(prescriptionStatus)) {
				entity.setPrescriptionStatus(Integer.valueOf(prescriptionStatus));
			}
			if (StringUtils.isNotBlank(pharmacyStatus)) {
				entity.setPharmacyStatus(Integer.valueOf(pharmacyStatus));
			}
			List<PrescriptionRecord> prescriptionRecordsList = prescriotionRecordService
					.prescriptionRecordsListPage(entity, startTime, endTime, page, size);
			PageInfo<PrescriptionRecord> pageInfo = new PageInfo<>(prescriptionRecordsList);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("total", pageInfo.getTotal());
			jsonObject.fluentPut("prescriptionRecordsList", prescriptionRecordsList);
			return new RespBody(Status.OK, jsonObject);*/
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询订单列表失败，查询数据异常！:::" + e.toString());
			return new RespBody(Status.ERROR, "查询订单列表失败，查询异常！");
		}
	}

	/**
	 * 
	 * @Title: all
	 * @Description: TODO(所有订单列表)
	 * @author ckm
	 * @date 2019年3月27日 下午8:15:04
	 * @param pharmacyId
	 * @param hospitalId
	 * @param payStatus
	 * @param prescriptionStatus
	 * @param pharmacyStatus
	 * @param orderNo
	 * @param cardNo
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/all")
	public RespBody all(@RequestParam(required = false, value = "pharmacyId", defaultValue = "") String pharmacyId,
			@RequestParam(required = false, value = "hospitalId", defaultValue = "") String hospitalId,
			@RequestParam(required = false, value = "payStatus", defaultValue = "") String payStatus,
			@RequestParam(required = false, value = "prescriptionStatus", defaultValue = "") String prescriptionStatus,
			@RequestParam(required = false, value = "pharmacyStatus", defaultValue = "") String pharmacyStatus,
			@RequestParam(required = false, value = "orderNo", defaultValue = "") String orderNo,
			@RequestParam(required = false, value = "cardNo", defaultValue = "") String cardNo,
			@RequestParam(required = false, value = "startTime", defaultValue = "") String startTime,
			@RequestParam(required = false, value = "endTime", defaultValue = "") String endTime) {
		try {
		    Map<String, Object> map = new HashMap<String, Object>();
		    map.put("pharmacyId", pharmacyId);
	        map.put("hospitalId", hospitalId);
	        map.put("payStatus", payStatus);
	        map.put("prescriptionStatus", prescriptionStatus);
	        map.put("pharmacyStatus", pharmacyStatus);
	        map.put("orderNo", orderNo);
	        map.put("cardNo", cardNo);
	        map.put("startTime", startTime);
	        map.put("endTime", endTime);
	        int total = prescriotionRecordService.total(map);
	        List<PrescriptionRecord> prescriptionRecordsList = prescriotionRecordService.findListByParams(map);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("total", total);
			jsonObject.fluentPut("prescriptionRecordsList", prescriptionRecordsList);
			return new RespBody(Status.OK, jsonObject);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询订单列表失败，查询数据异常！:::" + e.toString());
			return new RespBody(Status.ERROR, "查询订单列表失败，查询异常！");
		}
	}

	/**
	 * 
	 * @Title: updateStatus
	 * @Description: TODO(修改状态：业务状态、支付状态、药房配取状态、审药状态)
	 * @author ckm
	 * @date 2019年3月27日 下午8:14:14
	 * @param id
	 * @param payStatus
	 * @param prescriptionStatus
	 * @param pharmacyStatus
	 * @param reviewStatus
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateStatus")
	public RespBody updateStatus(@RequestParam(required = true, value = "id") String id,
			@RequestParam(required = false, value = "payStatus", defaultValue = "") String payStatus,
			@RequestParam(required = false, value = "prescriptionStatus", defaultValue = "") String prescriptionStatus,
			@RequestParam(required = false, value = "pharmacyStatus", defaultValue = "") String pharmacyStatus,
			@RequestParam(required = false, value = "reviewStatus", defaultValue = "") String reviewStatus,
            HttpServletRequest request) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            List<PrescriptionRecord> recordList = prescriotionRecordService.findListByParams(map);
            if (CollectionUtils.isEmpty(recordList)) {// 没有该订单
                return new RespBody(Status.PROMPT, "该订单不存在");
            }
            
            PrescriptionRecord entity = recordList.get(0);
            String hashTableName = HashSplitTableGenerator.getPrescriptionRecordHashTable(entity.getOpenId(), true);
            entity.setHashTableName(hashTableName);
            
            if (StringUtils.isNotBlank(payStatus)) {
                entity.setPayStatus(Integer.valueOf(payStatus));
            }
            if (StringUtils.isNotBlank(prescriptionStatus)) {
                entity.setPrescriptionStatus(Integer.valueOf(prescriptionStatus));
            }
            if (StringUtils.isNotBlank(pharmacyStatus)) {
                entity.setPharmacyStatus(Integer.valueOf(pharmacyStatus));
            }
            if (StringUtils.isNotBlank(reviewStatus)) {
                entity.setReviewStatus(Integer.valueOf(reviewStatus));
            }
            PrescriptionRecord record = prescriotionRecordService.updatePrescriptionRecord(entity);
            
            if (record.getId() != null) {
                return new RespBody(Status.OK, "修改状态成功");
            } else {
                return new RespBody(Status.ERROR, "修改状态失败！");
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("修改状态失败！" + e.toString());
            return new RespBody(Status.ERROR, "修改状态失败！");
        }
    }

	/**
	 * 
	 * @Title: queryDetail
	 * @Description: TODO(订单信息详情)
	 * @author ckm
	 * @date 2019年3月27日 下午8:13:56
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryDetail")
	public RespBody queryDetail(@RequestParam(required = true, value = "id") String id) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", id);
            List<PrescriptionRecord> recordList = prescriotionRecordService.findListByParams(map);
			if(!CollectionUtils.isEmpty(recordList)) {
			    return new RespBody(Status.OK, recordList.get(0));
			}
            return new RespBody(Status.PROMPT, "查不到该订单！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询订单详情信息失败，获取信息异常！:::" + e.toString());
			return new RespBody(Status.ERROR, "查询订单详情信息失败，获取信息异常！");
		}
	}

	/**
	 * 
	 * @Title: refundFee
	 * @Description: TODO(订单退费)
	 * @author ckm
	 * @date 2019年3月27日 下午8:13:36
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/refundFee")
	public RespBody refundFee(@RequestParam(required = true, value = "id") String id) {
		try {
			return new RespBody(Status.OK, "退费成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("退费失败，退费异常！:::" + e.toString());
			return new RespBody(Status.ERROR, "退费失败，退费异常！");
		}
	}

	/**
	 * 
	 * @Title: thirdPayment
	 * @Description: TODO(查看第三方支付详情)
	 * @author ckm
	 * @date 2019年3月27日 下午8:13:09
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/thirdPayment")
	public RespBody thirdPayment(@RequestParam(required = true, value = "id") String id) {
		try {
			return new RespBody(Status.OK, "退费成功！");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("退费失败，退费异常！:::" + e.toString());
			return new RespBody(Status.ERROR, "退费失败，退费异常！");
		}
	}

	/**
	 * 
	 * @Title: queryDataPayMzFee
	 * @Description: TODO(未支付处方信息)
	 * @author ckm
	 * @date 2019年3月27日 下午8:55:49
	 * @param mzfeeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryDataPayMzFee")
	public RespBody queryDataPayMzFee(@RequestParam(required = true, value = "mzFeeId") String mzFeeId) {
		try {
			DataPayMzFee entity = new DataPayMzFee(mzFeeId);
			DataPayMzFee dataPayMzFee = dataPayMzFeeService.queryDataPayMzFee(entity);
			return new RespBody(Status.OK, dataPayMzFee);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询未支付处方单信息失败，查询异常!:::" + e.toString());
			return new RespBody(Status.ERROR, "查询未支付处方单信息失败，查询异常!");
		}
	}

	/**
	 * 
	 * @Title: queryDataPaidMzFee
	 * @Description: TODO(已支付处方信息)
	 * @author ckm
	 * @date 2019年3月27日 下午8:56:07
	 * @param mzfeeId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryDataPaidMzFee")
	public RespBody queryDataPaidMzFee(@RequestParam(required = true, value = "mzFeeId") String mzFeeId) {
		try {
			DataPaidMzFee entity = new DataPaidMzFee(mzFeeId);
			DataPaidMzFee dataPaidMzFee = dataPaidMzFeeService.queryDataPaidMzFee(entity);
			return new RespBody(Status.OK, dataPaidMzFee);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询已支付处方单信息失败，查询异常!:::" + e.toString());
			return new RespBody(Status.ERROR, "查询已支付处方单信息失败，查询异常!");
		}
	}
}
