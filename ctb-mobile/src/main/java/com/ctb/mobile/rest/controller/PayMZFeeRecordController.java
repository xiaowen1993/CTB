/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月10日
 * Created by cwq
 */
package com.ctb.mobile.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ctb.commons.constants.BizConstant;
import com.ctb.commons.constants.TradeConstant;
import com.ctb.commons.entity.DataPayMzFee;
import com.ctb.commons.entity.DataPayMzFeeDetail;
import com.ctb.commons.entity.DrugList;
import com.ctb.commons.entity.PrescriptionRecord;
import com.ctb.commons.entity.SimplePrescriptionRecord;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.generator.HashSplitTableGenerator;
import com.ctb.mobile.cache.HospitalCache;
import com.ctb.mobile.cache.SimplePrescriptionRecordCache;
import com.ctb.mobile.rest.service.DataPayMZFeeDetailService;
import com.ctb.mobile.rest.service.DataPayMZFeeService;
import com.ctb.mobile.rest.service.DrugListService;
import com.ctb.mobile.rest.service.PrescriptionService;
import com.ctb.mobile.rest.service.impl.DrugListServiceImpl;

/**
 * @ClassName: com.ctb.mobile.rest.controller.PayMZFeeRecordController
 * @Description: TODO(代缴费记录查询)
 * @author cwq
 * @date 2019年4月10日 下午5:28:35
 */

@RefreshScope
@RestController
@RequestMapping("/payMZFeeRecord")
public class PayMZFeeRecordController {

	private static Logger logger = LoggerFactory.getLogger(PayMZFeeRecordController.class);

	@Autowired
	private DataPayMZFeeService dataPayMZFeeService;

	@Autowired
	private DataPayMZFeeDetailService dataPayMZFeeDetailService;
	
	@Autowired
	private PrescriptionService prescriptionService;
	
	@Autowired
	private HospitalCache hospitalCache;
	
	@Autowired
	private SimplePrescriptionRecordCache simplePrescriptionRecordCache;

	/**
	 * 
	 * @Title: payMZFeeList
	 * @Description: 获取待缴费门诊列表
	 * @author hhy
	 * @date 2019年4月8日 上午10:20:24
	 * @param unionId
	 * @return
	 */
	@RequestMapping(value = "/payMzFeeList")
	public RespBody payMzFeeList(@RequestParam(required = true, value = "unionId") String unionId,
			@RequestParam(required = true, value = "openId",defaultValue="") String openId,
			@RequestParam(required = false, value = "cardNo",defaultValue="") String cardNo) {
        try {
            String [] hospitalCodes = hospitalCache.getHospitalCodes();
            List<DataPayMzFee> dataPayMzFeeList = dataPayMZFeeService
                    .getDataPayMzFeeListByYX(hospitalCodes, unionId, openId, cardNo);
            
            List<DataPayMzFee> resList = new ArrayList<DataPayMzFee>();
            for (DataPayMzFee dataPayMzFee : dataPayMzFeeList) {//判断待缴费列表中是否已支付
                
                SimplePrescriptionRecord record = simplePrescriptionRecordCache.getSimplePrescriptionRecord(dataPayMzFee.getHospitalCode(), dataPayMzFee.getMzFeeId());
                
                if(record==null) {
                    
                    String hashTableName = HashSplitTableGenerator.getPrescriptionRecordHashTable(openId, true);
                    PrescriptionRecord prescriptionRecord = prescriptionService.isUniquePrescription(dataPayMzFee.getMzFeeId(), dataPayMzFee.getCardNo(), dataPayMzFee.getHospitalCode(), hashTableName);
                    if(prescriptionRecord == null || prescriptionRecord.getPayStatus() !=TradeConstant.PAY_ORDER_STATE_PAYMENT) {//订单不存在 或者该订单状态不为已支付---则显示
                        resList.add(dataPayMzFee);                                          
                    }
                }else if(record!=null && record.getPayStatus()!=TradeConstant.PAY_ORDER_STATE_PAYMENT) {
                    resList.add(dataPayMzFee);
                }
            }
            
            return new RespBody(Status.OK, resList);
        } catch (Exception e) {
            logger.error("获取待缴费列表异常:::unionId=" + unionId);
            e.printStackTrace();
            return new RespBody(Status.ERROR, "获取待缴费列表异常");
        }

    }

	@ResponseBody
	@RequestMapping(value = "/detail")
	public RespBody detail(@RequestParam(required = true, value = "openId", defaultValue = "") String openId,
			@RequestParam(required = false, value = "hospitalOpenId", defaultValue = "") String hospitalOpenId,
			@RequestParam(required = true, value = "unionId", defaultValue = "") String unionId,
			@RequestParam(required = true, value = "hospitalCode", defaultValue = "") String hospitalCode,
			@RequestParam(required = false, value = "branchHospitalCode", defaultValue = "") String branchHospitalCode,
			@RequestParam(required = true, value = "cardNo", defaultValue = "") String cardNo,
			@RequestParam(required = true, value = "cardType", defaultValue = "") String cardType,
			@RequestParam(required = true, value = "mzFeeId", defaultValue = "") String mzFeeId) {
		RespBody respBody=dataPayMZFeeService.mzDetails(openId, hospitalOpenId, unionId, hospitalCode, branchHospitalCode, cardNo, cardType, mzFeeId);
		return respBody;
	}

	/**
	 * 
	 * @Title: drugDetail
	 * @Description: 药品详情
	 * @author hhy
	 * @date 2019年4月8日 上午10:20:24
	 * @param unionId
	 * @return
	 */
	@RequestMapping(value = "/drugDetail")
	public RespBody drugDetail(@RequestParam(required = true, value = "mzFeeId") String mzFeeId) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mzFeeId", mzFeeId);
		List<DataPayMzFeeDetail> list = dataPayMZFeeDetailService.queryDataPayMzFeeDetailByExample(map);
		if (list != null) {
			return new RespBody(Status.OK, list);
		} else {
			return new RespBody(Status.PROMPT, "暂无数据！");
		}
	}

	public static void main(String[] args) {
		DrugListService drugListService = new DrugListServiceImpl();
		DrugList drugList = drugListService.getDrug("cea2adee54944b6facef484b9a51986b",
				"bab1f5908e504b2ebbf36248b80ad2b5");
		System.out.println(drugList);
	}

}
