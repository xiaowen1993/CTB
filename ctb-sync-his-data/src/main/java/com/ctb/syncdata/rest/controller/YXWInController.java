/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月30日
 * Created by cwq
 */
package com.ctb.syncdata.rest.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.sql.visitor.functions.Now;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.syncdata.api.CTBServerClient;
import com.ctb.syncdata.rest.entity.vo.HospitalPlatformVO;
import com.ctb.syncdata.rest.services.HospitalService;

/**
 * @ClassName: com.ctb.syncdata.rest.controller.YXWController
 * @Description: TODO(医享网取ctb数据)
 * @author cwq
 * @date 2019年3月30日 上午9:44:47
 */

@RefreshScope
@RestController
@RequestMapping("/yxwin")
public class YXWInController {

	private static Logger logger = LoggerFactory.getLogger(YXWInController.class);

	@Autowired
	private HospitalService hospitalService;

	@Autowired
	private CTBServerClient ctbServerClient;

	/**
	 * 
	 * @Title: validateOrder
	 * @Description: TODO(验证订单是否能外流，是否显示处方外流的按钮)
	 * @author cwq
	 * @date 2019年3月30日 上午10:20:03
	 * @param openId
	 * @param hospitalCode 医院code（小写首字母）
	 * @param platformCode wechat：标准平台-微信；hisAlipay：医院项目-支付宝；alipay：标准平台-支付宝；hisWechat：医院项目-微信
	 * @param itemIds      处方明细ID集合
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/validateOrder")
	public RespBody validateOrder(@RequestParam(required = true, value = "openId") String openId,
			@RequestParam(required = true, value = "hospitalCode") String hospitalCode,
			@RequestParam(required = true, value = "itemIds") String[] itemIds) {

		List<HospitalPlatformVO> hspVols = hospitalService.findHospitalPlatformSettingsByCode(hospitalCode);
		if (!CollectionUtils.isEmpty(hspVols)) {

			RespBody respBody = ctbServerClient.matchDrugs(openId, hospitalCode, itemIds);

			return respBody;
		} else {
			return new RespBody(Status.PROMPT, "该医院未上线处方外流。");
		}

	};

	/**
	 * 
	 * @Title: validFocusOn
	 * @Description: TODO(验证是否有关注公众号)
	 * @author ckm
	 * @date 2019年5月5日 上午11:31:52
	 * @param unionId(用户唯一unionId)
	 * @param hospitalOpenId(医院公众号openID)
	 * @param hospitalCode(医院code)
	 * @param branchCode(分院code)
	 * @param mzfeeId(门诊ID)
	 * @param cardNo(就诊卡号)
	 * @param cardType(诊疗卡类型)
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/validFocusOn")
	public RespBody validFocusOn(@RequestParam(required = true, value = "unionId") String unionId,
			@RequestParam(required = true, value = "hospitalOpenId") String hospitalOpenId,
			@RequestParam(required = true, value = "hospitalCode") String hospitalCode,
			@RequestParam(required = false, value = "branchCode") String branchCode,
			@RequestParam(required = true, value = "mzfeeId") String mzfeeId,
			@RequestParam(required = true, value = "cardNo") String cardNo,
			@RequestParam(required = false, value = "cardType") String cardType) {
		try {
			RespBody respBody = ctbServerClient.isFocusOn(unionId, hospitalOpenId, hospitalCode, branchCode, mzfeeId,
					cardNo, cardType);
			return respBody;
		} catch (Exception e) {
			logger.error("time:{},医享网--validFocusOn exception:{}",new Date().toString(),e.toString());
			return new RespBody(Status.PROMPT, "查询是否关注失败！");
		}

	}
	
	@ResponseBody
	@RequestMapping(value = "/mzDetail")
	public RespBody mzDetail(@RequestParam(required = true, value = "openId", defaultValue = "") String openId,
			@RequestParam(required = false, value = "hospitalOpenId", defaultValue = "") String hospitalOpenId,
			@RequestParam(required = true, value = "unionId", defaultValue = "") String unionId,
			@RequestParam(required = true, value = "hospitalCode", defaultValue = "") String hospitalCode,
			@RequestParam(required = false, value = "branchHospitalCode", defaultValue = "") String branchHospitalCode,
			@RequestParam(required = true, value = "cardNo", defaultValue = "") String cardNo,
			@RequestParam(required = true, value = "cardType", defaultValue = "") String cardType,
			@RequestParam(required = true, value = "mzFeeId", defaultValue = "") String mzFeeId) {
		try {
			RespBody respBody=ctbServerClient.mzDetail(openId, hospitalOpenId, unionId, hospitalCode, branchHospitalCode, cardNo, cardType, mzFeeId);
			return respBody;
		} catch (Exception e) {
			logger.error("time:{},医享网-- mzDetail exception:{}",new Date().toString(),e.toString());
			return new RespBody(Status.PROMPT,"暂无处方信息！");
		}		
		
	}
	
}
