/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月27日
 * Created by ckm
 */
package com.ctb.platform.rest.service;

import java.util.List;
import java.util.Map;

import com.ctb.commons.entity.PrescriptionRecord;

/**
 * @ClassName: com.ctb.platform.rest.service.PrescriotionRecordService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年3月27日 下午4:17:26
 */

public interface PrescriotionRecordService {

	public int update(PrescriptionRecord prescriptionRecord);

	public int total(PrescriptionRecord prescriptionRecord, String startTime, String endTime);

	public PrescriptionRecord queryDetail(PrescriptionRecord prescriptionRecord);

	public List<PrescriptionRecord> prescriptionRecordsListPage(PrescriptionRecord prescriptionRecord, String startTime,
			String endTime, Integer page, Integer pageSize);

	public List<PrescriptionRecord> prescriptionRecordsList(PrescriptionRecord prescriptionRecord, String startTime,
			String endTime);
	
    public List <PrescriptionRecord> findListByParams(Map map);
    
    public int total(Map map);
    
    public PrescriptionRecord queryByOrderNo(String orderNo,String hashTableName);
    
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


}
