/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月1日
 * Created by hhy
 */
package com.ctb.review.prescription.rest.service;

import java.util.List;
import java.util.Map;

import com.ctb.commons.entity.PrescriptionRecord;

/**
 * 
 * @ClassName: com.ctb.pharmacy.rest.service.PrescriptionService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月16日 上午11:06:27
 */
public interface PrescriptionService {
    
   public PrescriptionRecord getOrder(String orderNo,String mzFeeId,String cardNo,String hospitalCode);
    
   public int updateOrder(PrescriptionRecord prescriptionRecord);
      
   public List <PrescriptionRecord> findListByParams(Map map);
   
   public PrescriptionRecord queryByOrderNo(String orderNo,String hashTableName);
   
   public PrescriptionRecord  findRecordByOrderNo(String orderNo);
   
   public int updatePrescriptionRecord(PrescriptionRecord prescriptionRecord);
   
}