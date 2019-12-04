/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月16日
 * Created by ckm
 */
package com.ctb.pharmacy.rest.controller;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.BizConstant;
import com.ctb.commons.constants.TradeConstant;
import com.ctb.commons.entity.DataPayMzFee;
import com.ctb.commons.entity.DataPayMzFeeDetail;
import com.ctb.commons.entity.DrugList;
import com.ctb.commons.entity.HospitalPharmacySettings;
import com.ctb.commons.entity.PrescriptionQrcode;
import com.ctb.commons.entity.PrescriptionQrcodeScanLog;
import com.ctb.commons.entity.PrescriptionRecord;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.generator.HashSplitTableGenerator;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.pharmacy.cache.DataPayMzFeeCache;
import com.ctb.pharmacy.rest.entity.vo.DrugDetailVo;
import com.ctb.pharmacy.rest.entity.vo.DrugVo;
import com.ctb.pharmacy.rest.entity.vo.RecordVo;
import com.ctb.pharmacy.rest.entity.vo.YXDrugDetailVo;
import com.ctb.pharmacy.rest.service.DataPayMZFeeDetailService;
import com.ctb.pharmacy.rest.service.DrugListService;
import com.ctb.pharmacy.rest.service.HospitalPharmacySettingsService;
import com.ctb.pharmacy.rest.service.PharmacyUserService;
import com.ctb.pharmacy.rest.service.PrescriptionService;
import com.ctb.pharmacy.runnable.SendEmailRunnable;
import com.ctb.resources.mapper.biz.PrescriptionQrcodeMapper;
import com.ctb.resources.mapper.biz.PrescriptionQrcodeScanLogMapper;

import tk.mybatis.mapper.util.StringUtil;

/**
 * @ClassName: com.ctb.pharmacy.rest.controller.PrescriptionIndex
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月16日 下午2:17:37
 */
@RefreshScope
@RestController
@RequestMapping(value = "/prescriptionIndex")
public class PrescriptionIndexController {

	private static Logger logger = LoggerFactory.getLogger(PrescriptionIndexController.class);

	@Autowired
	private PrescriptionService prescriptionService;

	@Autowired
	private DrugListService drugListService;
	
    
    @Autowired
    private DataPayMZFeeDetailService dataPayMZFeeDetailService;
    
    @Autowired
    private DataPayMzFeeCache dataPayMzFeeCache;
    
    @Autowired
    private PharmacyUserService pharmacyUserService;
    
    @Autowired
    private HospitalPharmacySettingsService hospitalPharmacySettingsService;
    
	@Autowired
	private PrescriptionQrcodeMapper prescriptionQrcodeMapper;
	
	@Autowired
	private PrescriptionQrcodeScanLogMapper prescriptionQrcodeScanLogMapper;


    @ResponseBody
	@RequestMapping(value = "/getOrderPharmacyStatus")
	public RespBody getOrderPharmacyStatus(@RequestParam(required = true, value = "orderNo") String orderNo,
			@RequestParam(required = true, value = "mzFeeId") String mzFeeId,
			@RequestParam(required = true, value = "orderNo") String cardNo,
			@RequestParam(required = true, value = "orderNo") String hospitalCode) {
		try {
			JSONObject jsonObject = new JSONObject();
			PrescriptionRecord prescriptionRecord = prescriptionService.getOrder(orderNo, mzFeeId, cardNo,
					hospitalCode);
			if (prescriptionRecord != null) {

				DrugVo drugVo = new DrugVo(prescriptionRecord.getOrderNo(), prescriptionRecord.getPlatformMode(),
						prescriptionRecord.getMzFeeId(), "", prescriptionRecord.getPatientName(),
						prescriptionRecord.getCreateTime() + "");

				if (BizConstant.ORDER_STATE_PAY_SUCCESS == prescriptionRecord.getPrescriptionStatus()
						&& TradeConstant.PAY_ORDER_STATE_PAYMENT == prescriptionRecord.getPayStatus()
						&& BizConstant.REVIEW_STATE_AUDITED == prescriptionRecord.getReviewStatus()) {
					// 已缴费-已支付-已审核
					if (BizConstant.PRESCRIPTION_STATE_DISPENSED == prescriptionRecord.getPrescriptionStatus()) {
						// 已配药--获取药品信息
						List<DrugDetailVo> detailVos = drugListService.detailVos(hospitalCode, mzFeeId);
						if (drugVo != null) {
							drugVo.setDetails(detailVos);
						}
						jsonObject.put("drugVo", drugVo);
						jsonObject.put("prescriptionStatus", prescriptionRecord.getPrescriptionStatus());
					} else if (BizConstant.PRESCRIPTION_STATE_NOT_DISPENSING == prescriptionRecord
							.getPrescriptionStatus()) {
						// 未配药--返回未配药状态--进入配药界面
						jsonObject.put("prescriptionStatus", prescriptionRecord.getPrescriptionStatus());
					}
					return new RespBody(Status.OK, jsonObject);
				} else {
					return new RespBody(Status.PROMPT, "暂无数据！");
				}
			} else {
				return new RespBody(Status.PROMPT, "暂无数据！");
			}
		} catch (Exception e) {
			logger.error(e.toString());
			return new RespBody(Status.ERROR, "查询发药异常！");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/confirmSend")
	public RespBody confirmSend(
            @RequestParam(required = true, value = "orderNo") String orderNo) {
        
        try {
            PrescriptionRecord record = prescriptionService.findRecordByOrderNo(orderNo);
            if (record != null) {
                String hashTableName = HashSplitTableGenerator.getPrescriptionRecordHashTable(record.getOpenId(), true);
                record.setHashTableName(hashTableName);
                record.setPharmacyStatus(BizConstant.PRESCRIPTION_STATE_SEND_DISPENSED);
                prescriptionService.updateOrder(record);
            }
            //用线程发送发药成功模板（发药成功也是就是取药成功）
            
            return new RespBody(Status.OK, "发药成功");
        } catch (Exception e) {
            return new RespBody(Status.ERROR, "发药失败");
        }
    }
	
	   /*@ResponseBody
	    @RequestMapping(value = "/confirmSend")
	    public RespBody confirmSend(@RequestParam(required = true, value = "id") String id,
	            @RequestParam(required = true, value = "orderNo") String orderNo,
	            @RequestParam(required = true, value = "prescriptionStatus") String prescriptionStatus) {
	        try {
	            PrescriptionRecord prescriptionRecord = new PrescriptionRecord();
	            prescriptionRecord.setId(id);
	            prescriptionRecord.setOrderNo(orderNo);
	            prescriptionRecord.setPrescriptionStatus(Integer.valueOf(prescriptionStatus));
	            int res = prescriptionService.updateOrder(prescriptionRecord);
	            if (res == 1) {
	                return new RespBody(Status.OK, "发药成功！");
	            }
	        } catch (Exception e) {
	            logger.error("发药异常:::" + e.toString());
	            return new RespBody(Status.ERROR, "发药异常！");
	        }
	        return new RespBody(Status.PROMPT, "发药失败！");
	    }*/
	

    /**
     * 
     * @Title: count
     * @Description: 订单统计
     * @author hhy
     * @date 2019年4月16日 下午3:24:55
     * @param pharmacyId
     * @param startTime
     * @param endTime
     * @param hospitalId
     * @param pharmacyBranchId
     * @param payStatus
     * @return
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    @RequestMapping(value = "/count")
    public RespBody count(@RequestParam(required = false, value = "pharmacyCode") String pharmacyCode,
            @RequestParam(required = false, value = "date") String [] date,
            @RequestParam(required = false, value = "startTime") String startTime,
            @RequestParam(required = false, value = "endTime") String endTime,
            @RequestParam(required = false, value = "hospitalCode") String hospitalCode,
            @RequestParam(required = true, value = "branchPharmacyCode") String branchPharmacyCode,
            @RequestParam(required = false, value = "pharmacyStatus") String pharmacyStatus,
            @RequestParam(required = false, value = "sortType") String sortType,
            @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
            @RequestParam(required = false, value = "size", defaultValue = "10") Integer size)
            throws IllegalAccessException, InvocationTargetException {
        
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pharmacyCode", pharmacyCode);
            
            if (date != null && date.length == 2) {
                map.put("startTime", date[0].substring(2, 12));
                map.put("endTime", date[1].substring(0, date[1].length() - 2));
            }
            
            map.put("hospitalCode", hospitalCode);
            map.put("pharmacyBranchCode", branchPharmacyCode);
            map.put("pharmacyStatus", pharmacyStatus);
            if (StringUtil.isNotEmpty(sortType)) {
                map.put("sortType", sortType);// 要转为startTime和endTime的形式么
            }
            // map.put("startNum", (page - 1)*size);
            // map.put("pageSize", size);
            List<PrescriptionRecord> list = prescriptionService.findListByParams(map);
            
            if (CollectionUtils.isNotEmpty(list)) {
                Thread thread = new Thread(new SendEmailRunnable(getRecords(list), branchPharmacyCode));
                thread.start();
                return new RespBody(Status.OK, getRecords(list));
            } else {
                return new RespBody(Status.PROMPT, "暂无数据");
            }
        } catch (Exception e) {
            logger.error("获取统计异常");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "获取统计异常");
        }
    }
    
    /**
     * 
     * @Title: search
     * @Description: 订单查询
     * @author hhy
     * @date 2019年4月16日 下午3:25:12
     * @param pharmacyId
     * @param startTime
     * @param endTime
     * @param hospitalId
     * @param pharmacyStatus
     * @return
     */
    @RequestMapping(value = "/search")
    public RespBody search(@RequestParam(required = true, value = "branchPharmacyCode") String branchPharmacyCode,
            @RequestParam(required = false, value = "startTime") String startTime,
            @RequestParam(required = false, value = "endTime") String endTime,
            @RequestParam(required = false, value = "sortType") String sortType,
            @RequestParam(required = false, value = "hospitalCode") String hospitalCode,
            @RequestParam(required = false, value = "pharmacyStatus") String pharmacyStatus,
            @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
            @RequestParam(required = false, value = "size", defaultValue = "10") Integer size) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pharmacyBranchCode", branchPharmacyCode);
        if(StringUtil.isNotEmpty(sortType)) {
            map.put("sortType", sortType);//要转为startTime和endTime的形式么
        }
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("hospitalCode", hospitalCode);
        map.put("pharmacyStatus", pharmacyStatus);
        map.put("startNum", (page - 1)*size);
        map.put("pageSize", size);
        List<PrescriptionRecord> list = prescriptionService.findListByParams(map);
        
        // 拿到后发送邮件
        
        return new RespBody(Status.OK, list);
    }
    
    
    @RequestMapping(value = "/detail")
    public RespBody detail(@RequestParam(required = true, value = "orderNo") String orderNo,
            @RequestParam(required = false, value = "hospitalCode") String hospitalCode) {
        
      PrescriptionRecord record = prescriptionService.findRecordByOrderNo(orderNo);
      
      JSONObject json = new JSONObject();
      if(record!=null) {

          // 重新组装返回数据
          json.put("record", record);
          
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
              json.put("drugList", details);
          } else {// 如果缓存中没有，从数据库中找
              
              Map<String, Object> map = new HashMap<String, Object>();
              map.put("mzFeeId", record.getMzFeeId());
              List<DataPayMzFeeDetail> details = dataPayMZFeeDetailService.queryDataPayMzFeeDetailByExample(map);
              for (DataPayMzFeeDetail dataPayMzFeeDetail : details) {
                  DrugList drugList = drugListService.getDrug(record.getHospitalCode(),
                          dataPayMzFeeDetail.getItemId());
                  if (drugList != null) {
                      dataPayMzFeeDetail.setUsage(drugList.getUsage());
                      dataPayMzFeeDetail.setCadn(drugList.getCadn());
                      dataPayMzFeeDetail.setSpecification(drugList.getSpecification());
                      dataPayMzFeeDetail.setDrugName(drugList.getDrugName());
                  }
              }
              json.put("drugList", details);
          }
      }
      return new RespBody(Status.OK, json);
  }
    
    /**
     * 
     * @Title: toDispenseList
     * @Description: 代配药列表
     * @author hhy
     * @date 2019年5月6日 下午3:25:46
     * @param pharmacyId
     * @param payTime
     * @param hospitalId
     * @return
     */
    @RequestMapping(value = "/toDispenseList")
    public RespBody toDispenseList(@RequestParam(required = true, value = "branchPharmacyCode") String branchPharmacyCode,
            @RequestParam(required = false, value = "sortType") String sortType,
            @RequestParam(required = false, value = "hospitalCode") String hospitalCode,
            @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
            @RequestParam(required = false, value = "size", defaultValue = "10") Integer size) {
        
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pharmacyBranchCode", branchPharmacyCode);
            if (StringUtil.isNotEmpty(sortType)) {
                map.put("sortType", sortType);// 要转为startTime和endTime的形式么
            }
            map.put("pharmacyStatus", BizConstant.PRESCRIPTION_STATE_NOT_DISPENSING);
            // map.put("reviewStatus", BizConstant.REVIEW_STATE_AUDITED);
            map.put("hospitalCode", hospitalCode);
            map.put("startNum", (page - 1) * size);
            map.put("pageSize", size);
            List<PrescriptionRecord> list = prescriptionService.findListByParams(map);
            if (CollectionUtils.isEmpty(list)) {
                return new RespBody(Status.PROMPT, "暂无数据");
            } else {
                return new RespBody(Status.OK, getRecords(list));
            }
        } catch (Exception e) {
            logger.error("获取代配药列表出现异常");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "获取代配药列表出现异常");
        }
    }
    
    /**
     * 
     * @Title: toDispenseDetail
     * @Description: 待配药详情
     * @author hhy
     * @date 2019年5月6日 下午3:26:00
     * @param orderNo
     * @param hospitalId
     * @return
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    @RequestMapping(value = "/toDispenseDetail")
    public RespBody toDispenseDetail(@RequestParam(required = true, value = "value") String value,HttpServletRequest request) throws IllegalAccessException, InvocationTargetException {
        
    	System.out.println(value);
    	String ip = request.getRemoteAddr();  //返回发出请求的IP地址
    	
    	String orderNo = null;    //订单号
    	Date createTime;          //二维码生效时间
    	Date expirationTime ;     //过效时间
    	String prescriptionQrcodeId = null;  //订单ID
    	//通过value值  查看对应的订单号
 		PrescriptionQrcode qrcode = new PrescriptionQrcode();
 		qrcode.setValue(value);
 		
 		PrescriptionQrcode prescriptionQrcode = prescriptionQrcodeMapper.selectOne(qrcode);
 		orderNo = prescriptionQrcode.getOrderNo();   
 		createTime = prescriptionQrcode.getCreateTime();
 		expirationTime = prescriptionQrcode.getExpirationTime();
 		prescriptionQrcodeId = prescriptionQrcode.getId();
 		//生产二维码日志记录仪
		PrescriptionQrcodeScanLog qcrodeScanLog = new PrescriptionQrcodeScanLog();
		qcrodeScanLog.setId(PKGenerator.generateId());
		qcrodeScanLog.setPrescriptionQrcodeId(prescriptionQrcodeId);
		qcrodeScanLog.setAddress(ip);
		qcrodeScanLog.setOrderNo(orderNo);
		qcrodeScanLog.setValue(value);
		qcrodeScanLog.setCreateTime(createTime);;
		qcrodeScanLog.setExpirationTime(expirationTime);
		qcrodeScanLog.setValue(value);
		qcrodeScanLog.setScanTime(new Date());
		qcrodeScanLog.setScanUserId("0a556fcd2b4b432194d431fa37bafd44");    //先给定
 		
    	//判断过效时间与当前时间,当前时间小于失效时间 返回-1  大于1  等于0
 		int compreTo = new Date().compareTo(expirationTime);
 		//二维码失效
 		if(compreTo != -1) {
 			//生成日志，查看二维码状态
 			qcrodeScanLog.setState(0);
 			prescriptionQrcodeScanLogMapper.insert(qcrodeScanLog);
 			return new RespBody(Status.ERROR, "二维码失效，请重新获取");
 		}else {
 			qcrodeScanLog.setState(1);
 			prescriptionQrcodeScanLogMapper.insert(qcrodeScanLog);
 		}
    	
        PrescriptionRecord record = prescriptionService.findRecordByOrderNo(orderNo);
        
        JSONObject json = new JSONObject();
        if(record!=null) {

            // 重新组装返回数据
            json.put("record", record);
            
            // 从待缴费那拿数据
            DataPayMzFee dataPayMzFee = dataPayMzFeeCache.getDataPayMzFee(record.getHospitalCode(),
                    record.getCardNo(), record.getMzFeeId());

            List <YXDrugDetailVo> detailVos = new ArrayList<YXDrugDetailVo>();
            if (dataPayMzFee != null) {
                List <DataPayMzFeeDetail> details = dataPayMzFee.getDataPayMzFeeDetails();
                for (DataPayMzFeeDetail dataPayMzFeeDetail : details) {
                    YXDrugDetailVo detailVo = new YXDrugDetailVo();
                    DrugList drugList = drugListService.getDrug(record.getHospitalCode(), dataPayMzFeeDetail.getItemId());
                    
                    if (drugList != null) {
                        detailVo.setNumber(dataPayMzFeeDetail.getItemNumber());
                        BeanUtils.copyProperties(dataPayMzFeeDetail, detailVo);
                        BeanUtils.copyProperties(drugList, detailVo);
                        detailVos.add(detailVo);
                    }
                }
            } 
            else {// 如果缓存中没有，从数据库中找
                
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("mzFeeId", record.getMzFeeId());
                List<DataPayMzFeeDetail> details = dataPayMZFeeDetailService.queryDataPayMzFeeDetailByExample(map);
                for (DataPayMzFeeDetail dataPayMzFeeDetail : details) {
                    DrugList drugList = drugListService.getDrug(record.getHospitalCode(),dataPayMzFeeDetail.getItemId());
                    YXDrugDetailVo detailVo = new YXDrugDetailVo();
                    if (drugList != null) {
                        detailVo.setNumber(dataPayMzFeeDetail.getItemNumber());
                        BeanUtils.copyProperties(dataPayMzFeeDetail, detailVo);
                        BeanUtils.copyProperties(drugList, detailVo);
                        detailVos.add(detailVo);
                    }
                }
            }
            json.put("drugList", detailVos);
        }
        return new RespBody(Status.OK, json);
    }
    
    
    @RequestMapping(value = "/checkDrug")
    public RespBody checkDrug(@RequestParam(required = true, value = "orderNo") String orderNo) {
        
        try {
            PrescriptionRecord record = prescriptionService.findRecordByOrderNo(orderNo);
            if (record != null) {
                String hashTableName = HashSplitTableGenerator.getPrescriptionRecordHashTable(record.getOpenId(), true);
                record.setHashTableName(hashTableName);
                record.setPharmacyStatus(BizConstant.PRESCRIPTION_STATE_DISPENSED);
                prescriptionService.updateOrder(record);
            }
            //用线程推送配药成功模板（配药成功去取药）
            
            return new RespBody(Status.OK, "配药成功");
        } catch (Exception e) {
            return new RespBody(Status.ERROR, "配药失败");
        }
    }
    
    
    
    @RequestMapping(value = "/drugList")
    public RespBody drugList(@RequestParam(required = true, value = "orderNo") String orderNo) throws IllegalAccessException, InvocationTargetException {
          
        PrescriptionRecord record = prescriptionService.findRecordByOrderNo(orderNo);
        RecordVo vo = new RecordVo();
        if(record!=null) {
            
            BeanUtils.copyProperties(record, vo);
            
            // 从待缴费那拿数据
            DataPayMzFee dataPayMzFee = dataPayMzFeeCache.getDataPayMzFee(record.getHospitalCode(),
                    record.getCardNo(), record.getMzFeeId());

            List <YXDrugDetailVo> detailVos = new ArrayList<YXDrugDetailVo>();
            if (dataPayMzFee != null) {
                List <DataPayMzFeeDetail> details = dataPayMzFee.getDataPayMzFeeDetails();
                for (DataPayMzFeeDetail dataPayMzFeeDetail : details) {
                    YXDrugDetailVo detailVo = new YXDrugDetailVo();
                    DrugList drugList = drugListService.getDrug(record.getHospitalCode(), dataPayMzFeeDetail.getItemId());
                    
                    if (drugList != null) {
                        detailVo.setNumber(dataPayMzFeeDetail.getItemNumber());
                        BeanUtils.copyProperties(drugList, detailVo);
                        detailVos.add(detailVo);
                    }
                }
            } else {// 如果缓存中没有，从数据库中找
                
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("mzFeeId", record.getMzFeeId());
                List<DataPayMzFeeDetail> details = dataPayMZFeeDetailService.queryDataPayMzFeeDetailByExample(map);
                for (DataPayMzFeeDetail dataPayMzFeeDetail : details) {
                    DrugList drugList = drugListService.getDrug(record.getHospitalCode(),
                            dataPayMzFeeDetail.getItemId());
                    YXDrugDetailVo detailVo = new YXDrugDetailVo();
                    if (drugList != null) {
                        detailVo.setNumber(dataPayMzFeeDetail.getItemNumber());
                        BeanUtils.copyProperties(drugList, detailVo);
                        detailVos.add(detailVo);
                    }
                }
            }
            vo.setDetails(detailVos);
        }
        return new RespBody(Status.OK, vo);
    }
    
    
    @RequestMapping(value = "/drugDetail")
    public RespBody drugDetail(@RequestParam(required = true, value = "orderNo") String orderNo,
            @RequestParam(required = true, value = "hospitalCode") String hospitalCode,
            @RequestParam(required = true, value = "itemIds") String itemIds,
            @RequestParam(required = true, value = "RecordVo") RecordVo recordVo) throws IllegalAccessException, InvocationTargetException {
          
        PrescriptionRecord record = prescriptionService.findRecordByOrderNo(recordVo.getOrderNo());
        RecordVo vo = new RecordVo();
        if(record!=null) {
            
            BeanUtils.copyProperties(record, vo);
            
            // 从待缴费那拿数据
            DataPayMzFee dataPayMzFee = dataPayMzFeeCache.getDataPayMzFee(record.getHospitalCode(),
                    record.getCardNo(), record.getMzFeeId());

            List <YXDrugDetailVo> detailVos = new ArrayList<YXDrugDetailVo>();
            if (dataPayMzFee != null) {
                List <DataPayMzFeeDetail> details = dataPayMzFee.getDataPayMzFeeDetails();
                for (DataPayMzFeeDetail dataPayMzFeeDetail : details) {
                    YXDrugDetailVo detailVo = new YXDrugDetailVo();
                    DrugList drugList = drugListService.getDrug(record.getHospitalCode(), dataPayMzFeeDetail.getItemId());
                    
                    if (drugList != null) {
                        detailVo.setNumber(dataPayMzFeeDetail.getItemNumber());
                        BeanUtils.copyProperties(drugList, detailVo);
                        detailVos.add(detailVo);
                    }
                }
            } else {// 如果缓存中没有，从数据库中找
                
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("mzFeeId", record.getMzFeeId());
                List<DataPayMzFeeDetail> details = dataPayMZFeeDetailService.queryDataPayMzFeeDetailByExample(map);
                for (DataPayMzFeeDetail dataPayMzFeeDetail : details) {
                    DrugList drugList = drugListService.getDrug(record.getHospitalCode(),
                            dataPayMzFeeDetail.getItemId());
                    YXDrugDetailVo detailVo = new YXDrugDetailVo();
                    if (drugList != null) {
                        detailVo.setNumber(dataPayMzFeeDetail.getItemNumber());
                        BeanUtils.copyProperties(drugList, detailVo);
                        detailVos.add(detailVo);
                    }
                }
            }
            vo.setDetails(detailVos);
        }
        return new RespBody(Status.OK, vo);
    }
    
    public List<PrescriptionRecord> getRecords(List<PrescriptionRecord> list) {
        List<PrescriptionRecord> res = new ArrayList<PrescriptionRecord>();
        List<HospitalPharmacySettings> hospitalPharmacySettings = hospitalPharmacySettingsService
                .list(list.get(0).getPharmacyId());
        List<String> hospitalIds = new ArrayList<String>();
        
        for (HospitalPharmacySettings hospitalPharmacySetting : hospitalPharmacySettings) {
            hospitalIds.add(hospitalPharmacySetting.getHospitalId());
        }
        
        for (PrescriptionRecord record : list) {
            if (hospitalIds.contains(record.getHospitalId())) {
                res.add(record);
            }
        }
        return res;
    }
}
