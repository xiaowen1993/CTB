/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月1日
 * Created by hhy
 */
package com.ctb.mobile.rest.service;

import java.util.List;
import java.util.Map;

import com.ctb.commons.entity.PrescriptionRecord;

/**
 * @ClassName: com.ctb.mobile.rest.service.PrescriptionRecordService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年4月1日 下午4:41:39
 */

public interface PrescriptionService {
    
    public PrescriptionRecord generateOrder(PrescriptionRecord prescriptionRecord);
    
    public PrescriptionRecord isUniquePrescription(String mzfeeId, String cardNo, String hospitalCode,
            String hashTableName);
    
    
    /**
     * 
     * @Title: updatePrescriptionRecord
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author ckm
     * @date 2019年4月19日 下午2:46:58
     * @param prescriptionRecord
     * @return
     */
    public PrescriptionRecord updatePrescriptionRecord(PrescriptionRecord prescriptionRecord);

    public List <PrescriptionRecord> findListByParams(Map map);
    
    public PrescriptionRecord queryByOrderNo(String orderNo,String hashTableName);
    
    public PrescriptionRecord  findRecordByOrderNo(String orderNo);
    
    public List<PrescriptionRecord>  findRecordsByOpenId(String openId, String hashTableName);
    
    public void updateStatusForPay(PrescriptionRecord record);
    
    public Object buildPayInfo(PrescriptionRecord record);
    
    public Object buildRefundInfo(PrescriptionRecord record);
    
    public Object buildOrderQueryInfo(PrescriptionRecord record);
}
