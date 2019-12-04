/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月25日
 * Created by hhy
 */
package com.ctb.pharmacy.rest.service;

import java.util.List;

import com.ctb.commons.entity.DataPaidMzFee;
import com.ctb.commons.entity.PrescriptionRecord;
/**
 * 
 * @ClassName: com.ctb.pharmacy.rest.service.DataPaidMZFeeService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月16日 上午10:37:04
 */
public interface DataPaidMZFeeService {

      public List<DataPaidMzFee> getDataPaidMzFeeListByYX();
      
      public List <DataPaidMzFee> saveDataPaidMzFeeByRunnable(List<PrescriptionRecord> records);
    
}
