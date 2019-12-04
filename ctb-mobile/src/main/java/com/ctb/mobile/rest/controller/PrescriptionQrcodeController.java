/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月10日
 * Created by cwq
 */
package com.ctb.mobile.rest.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.BizConstant;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.BranchPharmacy;
import com.ctb.commons.entity.DataPaidMzFeeDetail;
import com.ctb.commons.entity.DataPayMzFee;
import com.ctb.commons.entity.DataPayMzFeeDetail;
import com.ctb.commons.entity.DrugList;
import com.ctb.commons.entity.PaySettings;
import com.ctb.commons.entity.Pharmacy;
import com.ctb.commons.entity.PrescriptionRecord;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.generator.HashSplitTableGenerator;
import com.ctb.framework.commons.generator.OrderNoGenerator;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.framework.commons.redis.RedisClient;
import com.ctb.mobile.cache.DataPayMzFeeCache;
import com.ctb.mobile.cache.PharmacyCache;
import com.ctb.mobile.datastorage.runnable.PayMZFeeRunnable;
import com.ctb.mobile.rest.entity.vo.BranchPharmacyVo;
import com.ctb.mobile.rest.entity.vo.PayDrugDetailVo;
import com.ctb.mobile.rest.entity.vo.PrescriptionVo;
import com.ctb.mobile.rest.service.BranchPharmacyService;
import com.ctb.mobile.rest.service.DataPaidMZFeeDetailService;
import com.ctb.mobile.rest.service.DataPayMZFeeDetailService;
import com.ctb.mobile.rest.service.DataPayMZFeeService;
import com.ctb.mobile.rest.service.DrugListService;
import com.ctb.mobile.rest.service.PaySettingService;
import com.ctb.mobile.rest.service.PharmacyService;
import com.ctb.mobile.rest.service.PrescriptionQrcodeService;
import com.ctb.mobile.rest.service.PrescriptionService;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

/**
 * @ClassName: com.ctb.mobile.rest.controller.PrescriptionIndexController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月10日 下午5:31:22
 */
@RefreshScope
@RestController
@RequestMapping("/prescriptionQrcode")
public class PrescriptionQrcodeController {

	private static Logger logger = LoggerFactory.getLogger(PrescriptionQrcodeController.class);

	@Autowired
	private PrescriptionQrcodeService prescriptionQrcodeService;
	

	/**
	 * 更新订单号
	 * @param orderNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateByOrderNo")
	public RespBody updateByOrderNo(@RequestParam(required = true, value ="orderNo")String orderNo,
			HttpServletRequest request) {
		
		try {
			if(StringUtils.isNotEmpty(orderNo)) {
				String value = prescriptionQrcodeService.updateByOrderNo(orderNo);
				
				//配置路径地址
				String ip = request.getRemoteAddr();
				
				String url = "http://"+ip+":80/404?value="+value;
				
				Map<String, String> res = new HashMap<String,String>();
				res.put("url", url);
				
				return new RespBody(Status.OK, res);
			}
			return new RespBody(Status.ERROR, "二维码生成失败，订单号为空！");
		} catch (Exception e) {
			// TODO: handle exception
			return new RespBody(Status.ERROR, "二维码生成失败！");
			
		}

	}

	
	
	
	
	
}
