/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月16日
 * Created by ckm
 */
package com.ctb.review.prescription.rest.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.BizConstant;
import com.ctb.commons.entity.DataPayMzFee;
import com.ctb.commons.entity.DataPayMzFeeDetail;
import com.ctb.commons.entity.DrugList;
import com.ctb.commons.entity.HospitalPharmacistUser;
import com.ctb.commons.entity.PrescriptionRecord;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.generator.HashSplitTableGenerator;
import com.ctb.review.prescription.cache.DataPayMzFeeCache;
import com.ctb.review.prescription.rest.entity.vo.RecordVo;
import com.ctb.review.prescription.rest.entity.vo.YXDrugDetailVo;
import com.ctb.review.prescription.rest.service.DataPayMZFeeDetailService;
import com.ctb.review.prescription.rest.service.DrugListService;
import com.ctb.review.prescription.rest.service.HospitalPharmacistUserService;
import com.ctb.review.prescription.rest.service.PharmacistUserService;
import com.ctb.review.prescription.rest.service.PrescriptionService;

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
    private DataPayMzFeeCache dataPayMzFeeCache;
    
    @Autowired
    private DrugListService drugListService;
    
    @Autowired
    private DataPayMZFeeDetailService dataPayMZFeeDetailService;
    
    @Autowired
    private HospitalPharmacistUserService hospitalPharmacistUserService;

    
    /**
     * 
     * @Title: reviewList
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author hhy
     * @date 2019年6月19日 下午3:52:31
     * @param reviewStatus
     * @param hospitalCode
     * @param hospitalId
     * @return
     */
    @RequestMapping(value = "/list")
    public RespBody list(@RequestParam(required = true, value = "pharmacistId") String pharmacistId,
            @RequestParam(required = false, value = "reviewStatus") String reviewStatus,
            @RequestParam(required = false, value = "hospitalCode", defaultValue="") String hospitalCode,
            @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
            @RequestParam(required = false, value = "size", defaultValue = "10") Integer size) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        map.put("reviewPhysiciansId", pharmacistId);
        map.put("reviewStatus", reviewStatus);
        map.put("hospitalCode", hospitalCode);
        map.put("startNum", (page - 1)*size);
        map.put("pageSize", size);
        List<PrescriptionRecord> records = prescriptionService.findListByParams(map);
        
        List<PrescriptionRecord> res = new ArrayList<PrescriptionRecord>();
        if(StringUtils.isEmpty(hospitalCode)) {//hospitalCode为空则查找属于该药师的hospitalCode
            HospitalPharmacistUser hospitalPharmacistUser = new HospitalPharmacistUser();
            hospitalPharmacistUser.setPharmacistId(pharmacistId);
            List <HospitalPharmacistUser> list = hospitalPharmacistUserService.queryList(hospitalPharmacistUser);
            List <String> hospitalCodes = new ArrayList<String>();
            for(HospitalPharmacistUser entity:list) {
                hospitalCodes.add(entity.getHospitalCode());
            }
            for(PrescriptionRecord record:records) {
                if(hospitalCodes.contains(record.getHospitalCode())) {
                    res.add(record);
                }
            }
            return new RespBody(Status.OK, res);
        }
        
        return new RespBody(Status.OK, records);
    }
    
    
    /**
     * 
     * @Title: detail
     * @Description: 代配药详情
     * @author hhy
     * @date 2019年5月6日 下午3:26:00
     * @param orderNo
     * @param hospitalId
     * @return
     */
    @RequestMapping(value = "/detail")
    public RespBody detail(@RequestParam(required = true, value = "orderNo") String orderNo) {
        
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
    
    
    
    
    /*@RequestMapping(value = "/detail")
    public RespBody detail(@RequestParam(required = true, value = "orderNo") String orderNo) {
        
        PrescriptionRecord record = prescriptionService.findRecordByOrderNo(orderNo);
        RecordVo vo = new RecordVo();
        if (record != null) {
            
            BeanUtils.copyProperties(record, vo);
            
            // 从待缴费那拿数据
            DataPayMzFee dataPayMzFee = dataPayMzFeeCache.getDataPayMzFee(record.getHospitalCode(), record.getCardNo(),
                    record.getMzFeeId());
            
            List<YXDrugDetailVo> detailVos = new ArrayList<YXDrugDetailVo>();
            if (dataPayMzFee != null) {
                List<DataPayMzFeeDetail> details = dataPayMzFee.getDataPayMzFeeDetails();
                for (DataPayMzFeeDetail dataPayMzFeeDetail : details) {
                    YXDrugDetailVo detailVo = new YXDrugDetailVo();
                    DrugList drugList = drugListService.getDrug(record.getHospitalCode(),
                            dataPayMzFeeDetail.getItemId());
                    
                    if (drugList != null) {
                        detailVo.setNumber(dataPayMzFeeDetail.getItemNumber());
                        BeanUtils.copyProperties(drugList, detailVo);
                    }
                    detailVos.add(detailVo);
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
                    }
                    detailVos.add(detailVo);
                }
            }
            vo.setDetails(detailVos);
        }
        return new RespBody(Status.OK, vo);
        
    }*/
    
    
    /**
     * 
     * @Title: update
     * @Description: 
     * @author hhy
     * @date 2019年5月6日 下午3:26:00
     * @param orderNo
     * @param hospitalId
     * @return
     */
    @RequestMapping(value = "/update")
    public RespBody update(@RequestParam(required = true, value = "orderNo") String orderNo,
            @RequestParam(required = false, value = "pharmacistId",defaultValue="") String pharmacistId,
            @RequestParam(required = false, value = "reviewStatus",defaultValue="0") Integer reviewStatus,//0未审核 1 审核成功 2 审核不成功
            @RequestParam(required = false, value = "reviewMessages", defaultValue = "") String reviewMessages) {
        
        try {
            PrescriptionRecord record = prescriptionService.findRecordByOrderNo(orderNo);
            if (record != null) {
                String hashTableName = HashSplitTableGenerator.getPrescriptionRecordHashTable(record.getOpenId(), true);
                record.setHashTableName(hashTableName);
                
                record.setReviewPhysiciansId(pharmacistId);
                record.setReviewMessages(reviewMessages);
                record.setReviewStatus(reviewStatus);
                record.setReviewTime(new Date().getTime());
                int res = prescriptionService.updatePrescriptionRecord(record);
                if (res == 1) {
                    return new RespBody(Status.OK, "保存成功");
                }
            }
        } catch (Exception e) {
            logger.error("保存更改异常");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "保存异常");
        }
        return new RespBody(Status.ERROR, "保存失败");
    }
    
    
    @RequestMapping(value = "/records")
    public RespBody records(@RequestParam(required = true, value = "cardNo") String cardNo,
            @RequestParam(required = true, value = "hospitalCode") String hospitalCode,
            @RequestParam(required = true, value = "sortType") String sortType,
            @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
            @RequestParam(required = false, value = "size", defaultValue = "10") Integer size) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cardNo", cardNo);
        map.put("hospitalCode", hospitalCode);
        map.put("startNum", (page - 1)*size);
        map.put("pageSize", size);
        if(StringUtil.isNotEmpty(sortType)) {
            map.put("sortType", sortType);//要转为startTime和endTime的形式么
        }
        List<PrescriptionRecord> list = prescriptionService.findListByParams(map);
        return new RespBody(Status.OK, list);
    }
    
}
