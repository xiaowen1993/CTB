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
@RequestMapping("/prescriptionIndex")
public class PrescriptionIndexController {

	private static Logger logger = LoggerFactory.getLogger(PrescriptionIndexController.class);

	@Autowired
	private PrescriptionService prescriptionService;

	@Autowired
	private BranchPharmacyService branchPharmacyService;

	@Autowired
	private DataPaidMZFeeDetailService dataPaidMZFeeDetailService;

	@Autowired
	private PharmacyService pharmacyService;

	@Autowired
	private DrugListService drugListService;

	@Autowired
	private DataPayMZFeeDetailService dataPayMZFeeDetailService;

	@Autowired
	private DataPayMZFeeService dataPayMZFeeService;

	@Autowired
	private RedisClient redisClient;

	@Autowired
	private PharmacyCache pharmacyCache;

	@Autowired
	private DataPayMzFeeCache dataPayMzFeeCache;

	@Autowired
	private PaySettingService paySettingService;

	/**
	 * 
	 * @Title: generateOrder
	 * @Description: TODO(创建订单)
	 * @author ckm
	 * @date 2019年4月24日 上午11:05:34
	 * @param request
	 * @param record
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/generateOrder")
	public RespBody generateOrder(HttpServletRequest request, @Validated @RequestBody PrescriptionRecord record) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		PrescriptionRecord prescriptionRecord = new PrescriptionRecord();
		try {
			// 当订单已经存在 且金额不相等 则需要重新去拿 并且入库
			boolean isHaveRecord = false;

			if (StringUtils.equals(record.getPlatformModeType(), BizConstant.MODE_TYPE_AI_PHARMACY)) {
				record.setPlatformMode(BizConstant.MODE_TYPE_AI_PHARMACY_VAL + "");
			}
			if (StringUtils.equals(record.getTradeModeType(), BizConstant.TRADE_MODE_WECHAT)) {
				record.setTradeMode(BizConstant.TRADE_MODE_WECHAT_VAL + "");
			}

			String hashTableName = HashSplitTableGenerator.getPrescriptionRecordHashTable(record.getOpenId(), true);
			record.setHashTableName(hashTableName);

			// step1：判断mzfeeId、hospitalId、hospitalCode是否已生成处方订单数据
			PrescriptionRecord entity = prescriptionService.isUniquePrescription(record.getMzFeeId(),
					record.getCardNo(), record.getHospitalCode(), record.getHashTableName());
			if (entity != null) {// step2-1:已存在处方订单
				if (!record.getTotalFee().equals(entity.getTotalFee())) {
					isHaveRecord = true;// 金额不等 则不为重复订单需要重新去拿数据
				}

				if (record.getTotalFee().equals(entity.getTotalFee())
						&& record.getPharmacyBranchCode().equals(entity.getPharmacyBranchCode())) {
					// 判断是否还是选择同一家药店
					record.setUpdateTime(new Date().getTime());
					BeanUtils.copyProperties(entity, prescriptionRecord);
				} else {
					record.setId(entity.getId());
					prescriptionRecord = prescriptionService.updatePrescriptionRecord(record);
				}
			} else {// step2-2:数据库不存在处方订单
				// step2-2-1:生成处方订单
				prescriptionRecord = prescriptionService.generateOrder(record);
				isHaveRecord = true;
			}
			// step3:利用prescriptionRecord构造第三方支付信息
			if (prescriptionRecord != null) {
				resultMap.put("pharmacyCode", prescriptionRecord.getPharmacyCode());
				resultMap.put("openId", record.getOpenId());
				resultMap.put("orderNo", prescriptionRecord.getOrderNo());

				// step4:生成订单成功返回值
				PaySettings paySettings = paySettingService.queryPaySettings(prescriptionRecord.getPharmacyId(),
						prescriptionRecord.getPharmacyCode());
				if (paySettings != null) {
					resultMap.put("appId", paySettings.getAppId());
				}
			}
			if (!resultMap.isEmpty()) {
				Thread thread = new Thread(new PayMZFeeRunnable(prescriptionRecord, isHaveRecord));
				thread.start();

				return new RespBody(Status.OK, resultMap);
			} else {
				return new RespBody(Status.PROMPT, "生成订单失败！");
			}
		} catch (Exception e) {
			logger.error("生成处方订单失败！:::" + e.toString());
			e.printStackTrace();
			return new RespBody(Status.ERROR, "生成处方订单失败！");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/queryPayOrder")
	public RespBody queryPayOrder(@RequestParam(required = true, value = "openId") String openId,
			@RequestParam(required = true, value = "orderNo") String orderNo) {
		try {
			PrescriptionVo detailVo = new PrescriptionVo();
			BranchPharmacyVo branchPharmacyVo = new BranchPharmacyVo();
			List<PayDrugDetailVo> detailVos = new ArrayList<PayDrugDetailVo>();
			String hashTableName = HashSplitTableGenerator.getPrescriptionRecordHashTable(openId, true);
			// setp1:查找数据库prescription数据
			PrescriptionRecord prescriptionRecord = prescriptionService.queryByOrderNo(orderNo, hashTableName);
			if (prescriptionRecord != null) {
				BeanUtils.copyProperties(prescriptionRecord, detailVo);

				// step2:根据step1订单数据中pharmacyId、pharmacyCode、pharmacyBranchCode缓存查找具体药店信息
				BranchPharmacy branchPharmacy = branchPharmacyService.queryOne(prescriptionRecord.getPharmacyId(),
						prescriptionRecord.getPharmacyCode(), prescriptionRecord.getPharmacyBranchCode());
				if (branchPharmacy != null)
					BeanUtils.copyProperties(branchPharmacy, branchPharmacyVo);

				// step3:根据mzFeeId查找DataPayMzFeeDatail获取List<DataPayMzFeeDatail>待缴费药品信息
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("mzFeeId", prescriptionRecord.getMzFeeId());
				
				DataPayMzFee  dataPayMzFee = dataPayMzFeeCache.getDataPayMzFee(prescriptionRecord.getHospitalCode(), prescriptionRecord.getCardNo(), prescriptionRecord.getMzFeeId());
				List<DataPayMzFeeDetail> dataPayMzFeeDetails = dataPayMzFee.getDataPayMzFeeDetails();
				//List<DataPayMzFeeDetail> dataPayMzFeeDetails = dataPayMZFeeDetailService
				//		.queryDataPayMzFeeDetailByExample(map);
				if (dataPayMzFeeDetails != null && !dataPayMzFeeDetails.isEmpty() && dataPayMzFeeDetails.size() > 0) {
					for (DataPayMzFeeDetail dataPayMzFeeDetail : dataPayMzFeeDetails) {
						PayDrugDetailVo drugDetailVo = new PayDrugDetailVo();
						BeanUtils.copyProperties(dataPayMzFeeDetail, drugDetailVo);
						if (drugDetailVo != null) {
							// step3-1:根据hospitalCode、ItemId查询药品白名单缓存获取固定数据
							DrugList drugList = drugListService.getDrug(prescriptionRecord.getHospitalCode(),
									dataPayMzFeeDetail.getItemId());
							drugDetailVo.setUsage(drugList.getUsage());
							drugDetailVo.setCadn(drugList.getCadn());
							drugDetailVo.setSpecification(drugList.getSpecification());
							drugDetailVo.setDrugName(drugList.getDrugName());
							detailVos.add(drugDetailVo);
						}
					}
				}

				// 金额计算
				if (StringUtils.isNotBlank(detailVo.getTotalFee())) {

					// 应付金额
					if (StringUtils.isNotBlank(detailVo.getDiscountFee())) {
						detailVo.setPayMent(
								(Double.valueOf(detailVo.getTotalFee()) - Double.valueOf(detailVo.getDiscountFee()))
										+ "");
					} else {
						detailVo.setPayMent(Double.valueOf(detailVo.getTotalFee()) + "");
					}
				}

				detailVo.setBranchPharmacyVo(branchPharmacyVo);
				detailVo.setPayMZFDetails(detailVos);
				return new RespBody(Status.OK, detailVo);
			} else {
				return new RespBody(Status.PROMPT, "暂无数据");
			}
		} catch (Exception e) {
			logger.error("查询代缴费订单失败，查询异常！:::" + e.toString());
			e.printStackTrace();
			return new RespBody(Status.ERROR);
		}
	}

	/**
	 * 
	 * @Title: detail
	 * @Description: 订单详情
	 * @author hhy
	 * @date 2019年4月8日 上午10:20:58
	 * @param orderNo
	 * @return
	 */
	@RequestMapping(value = "/detail")
	public RespBody detail(@RequestParam(required = true, value = "orderNo") String orderNo,
	        @RequestParam(required = true, value = "openId") String openId) {

		try {

			String hashTableName = HashSplitTableGenerator.getPrescriptionRecordHashTable(openId, true);
			PrescriptionRecord record = prescriptionService.queryByOrderNo(orderNo, hashTableName);
			JSONObject json = new JSONObject();
			if (record != null) {
			    json.put("record", record);
				// 从缓存拿药店名字信息
				Pharmacy entity = pharmacyCache.getPharmacy(record.getPharmacyId());
				if (entity != null) {
					System.out.println("从缓存拿药店信息");
					json.put("pharmacy", entity);
				} else {
					Pharmacy pharmacy = pharmacyService.selectByPrimaryKey(record.getPharmacyId());
					if (pharmacy != null) {
					    json.put("pharmacy", pharmacy);

					}
				}

				// 分店信息
				BranchPharmacy branchPharmacy = branchPharmacyService
						.selectOne(new BranchPharmacy(record.getPharmacyBranchId()));
				if (branchPharmacy != null) {
				    json.put("pharmacyBranch", branchPharmacy);
				}

				// 从待缴费那拿数据
				DataPayMzFee dataPayMzFee = dataPayMzFeeCache.getDataPayMzFee(record.getHospitalCode(),
						record.getCardNo(), record.getMzFeeId());

				if (dataPayMzFee != null) {
				    List <DataPayMzFeeDetail> details = dataPayMzFee.getDataPayMzFeeDetails();
                    for (DataPayMzFeeDetail dataPayMzFeeDetail : details) {
                        DrugList drugList = drugListService.getDrug(record.getHospitalCode(), dataPayMzFeeDetail.getItemId());
                        if (drugList != null) {
                            dataPayMzFeeDetail.setUsage(drugList.getUsage());
                            dataPayMzFeeDetail.setCadn(drugList.getCadn());
                            dataPayMzFeeDetail.setSpecification(drugList.getSpecification());
                            dataPayMzFeeDetail.setDrugName(drugList.getDrugName());
                        }
                    }   
                    json.put("details", details);
				} else {// 如果缓存中没有，则调一遍待缴费列表 这样缓存中就有了
					dataPayMZFeeService.getDataPayMzFeeListByYX(new String[] { record.getHospitalCode() },
							record.getUnionId(), record.getOpenId(), record.getCardNo());
					// 获取待缴费列表的时候 已经放入缓存 直接拿就可以
					dataPayMzFee = dataPayMzFeeCache.getDataPayMzFee(record.getHospitalCode(), record.getCardNo(),
							record.getMzFeeId());
					List <DataPayMzFeeDetail> details = dataPayMzFee.getDataPayMzFeeDetails();
                    for (DataPayMzFeeDetail dataPayMzFeeDetail : details) {
                        DrugList drugList = drugListService.getDrug(record.getHospitalCode(), dataPayMzFeeDetail.getItemId());
                        if (drugList != null) {
                            dataPayMzFeeDetail.setUsage(drugList.getUsage());
                            dataPayMzFeeDetail.setCadn(drugList.getCadn());
                            dataPayMzFeeDetail.setSpecification(drugList.getSpecification());
                            dataPayMzFeeDetail.setDrugName(drugList.getDrugName());
                        }
                    }   
                    json.put("details", details);
				}

			}
			return new RespBody(Status.OK, json);
		} catch (Exception e) {
			logger.error("获取订单详情异常");
			e.printStackTrace();
			return new RespBody(Status.ERROR, "获取订单详情异常");
		}

	}

	/**
	 * 
	 * @Title: records
	 * @Description: 获取订单列表
	 * @author hhy
	 * @date 2019年4月8日 上午10:20:24
	 * @param unionId
	 * @param cardNo
	 * @param hospitalId
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping(value = "/records")
	public RespBody records(@RequestParam(required = true, value = "unionId") String unionId,
	        @RequestParam(required = true, value = "openId") String openId) {

		try {

			String hashTableName = HashSplitTableGenerator.getPrescriptionRecordHashTable(openId, true);
			List<PrescriptionRecord> recordList = prescriptionService.findRecordsByOpenId(openId, hashTableName);

			List<JSONObject> resList = new ArrayList<JSONObject>();
			for (PrescriptionRecord record : recordList) {
				// 重新组装返回数据
				String str = JSONObject.toJSONString(record);
				JSONObject json = new JSONObject();
				json = JSONObject.parseObject(str);
				// 从缓存拿药店名字信息
				String pharmacyKey = record.getPharmacyId();
				JSONObject jsonObj = (JSONObject) redisClient.hget(CacheConstants.CACHE_PHARMACY_HASH_KEY_PREFIX,
						pharmacyKey);
				if (jsonObj != null) {
					System.out.println("从缓存拿药店信息");
					json.put("pharmacyId", jsonObj.get("id"));
					json.put("pharmacyCode", jsonObj.get("code"));
					json.put("pharmacyName", jsonObj.get("name"));
				} else {
					Pharmacy pharmacy = pharmacyService.selectByPrimaryKey(record.getPharmacyId());
					if (pharmacy != null) {
						json.put("pharmacyId", pharmacy.getId());
						json.put("pharmacyCode", pharmacy.getCode());
						json.put("pharmacyName", pharmacy.getName());
					}
				}

				// 分店信息
				BranchPharmacy branchPharmacy = branchPharmacyService
						.selectOne(new BranchPharmacy(record.getPharmacyBranchId()));
				if (branchPharmacy != null) {
					json.put("pharmacyBranchId", branchPharmacy.getId());
					json.put("pharmacyBranchCode", branchPharmacy.getCode());
					json.put("pharmacyBranchName", branchPharmacy.getName());
				}

				// 从待缴费那拿数据
				DataPayMzFee dataPayMzFee = dataPayMzFeeCache.getDataPayMzFee(record.getHospitalCode(),
						record.getCardNo(), record.getMzFeeId());

				if (dataPayMzFee != null) {
				    List <DataPayMzFeeDetail> details = dataPayMzFee.getDataPayMzFeeDetails();
                    for (DataPayMzFeeDetail dataPayMzFeeDetail : details) {
                        DrugList drugList = drugListService.getDrug(record.getHospitalCode(), dataPayMzFeeDetail.getItemId());
                        if (drugList != null) {
                            dataPayMzFeeDetail.setUsage(drugList.getUsage());
                            dataPayMzFeeDetail.setCadn(drugList.getCadn());
                            dataPayMzFeeDetail.setSpecification(drugList.getSpecification());
                            dataPayMzFeeDetail.setDrugName(drugList.getDrugName());
                        }
                    }   
                    json.put("details", details);
				} else {// 如果缓存中没有，则调一遍待缴费列表 这样缓存中就有了
					dataPayMZFeeService.getDataPayMzFeeListByYX(new String[] { record.getHospitalCode() },
							record.getUnionId(), record.getOpenId(), record.getCardNo());
					// 获取待缴费列表的时候 已经放入缓存 直接拿就可以
					dataPayMzFee = dataPayMzFeeCache.getDataPayMzFee(record.getHospitalCode(), record.getCardNo(),
							record.getMzFeeId());
					
					List <DataPayMzFeeDetail> details = dataPayMzFee.getDataPayMzFeeDetails();
					for (DataPayMzFeeDetail dataPayMzFeeDetail : details) {
	                    DrugList drugList = drugListService.getDrug(record.getHospitalCode(), dataPayMzFeeDetail.getItemId());
	                    if (drugList != null) {
	                        dataPayMzFeeDetail.setUsage(drugList.getUsage());
	                        dataPayMzFeeDetail.setCadn(drugList.getCadn());
	                        dataPayMzFeeDetail.setSpecification(drugList.getSpecification());
	                        dataPayMzFeeDetail.setDrugName(drugList.getDrugName());
	                    }
	                }   
					json.put("details", details);
				}

				resList.add(json);
			}

			Map<String, Object> resMap = new HashMap<String, Object>();
			resMap.put("list", resList);
			return new RespBody(Status.OK, resList);
		} catch (Exception e) {
			logger.error("获取已缴费订单列表异常:::");
			e.printStackTrace();
			return new RespBody(Status.ERROR, "获取已缴费订单列表异常！");
		}
	}

	public List<BranchPharmacy> getBranchPharmacy(String pharmacyCode, String pharmacyId, String branchPharmacyid) {
		JSONArray jsonArray = (JSONArray) redisClient.hget(CacheConstants.CACHE_BRANCH_PHARMACY_CODE_HASH_KEY_PREFIX,
				pharmacyId + ":" + pharmacyCode);
		List<BranchPharmacy> branchPharmacys = jsonArray.toJavaList(BranchPharmacy.class);

		Collections2.filter(branchPharmacys, new Predicate<BranchPharmacy>() {
			@Override
			public boolean apply(BranchPharmacy branchPharmacy) {
				return branchPharmacy.getId().contains(branchPharmacyid);
			}
		});
		return branchPharmacys;
	}
}
