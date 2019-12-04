/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月25日
 * Created by hhy
 */
package com.ctb.mobile.rest.service;

import java.util.List;

import com.ctb.commons.entity.DataPaidMzFee;
import com.ctb.commons.entity.DataPaidMzFeeDetail;
import com.ctb.commons.entity.PrescriptionRecord;

/**
 * @ClassName: com.ctb.mobile.rest.service.DataPaidMZFeeService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年3月25日 下午5:09:33
 */

public interface DataPaidMZFeeService {

    
//    public List <DataPaidMzFeeDetail> queryDataPaidMzFeeListByExample(Map <String, Object> map);
//    
//    public List <DataPaidMzFee> queryDataPaidMzFeeListPagedByExample(Map <String, Object> map, Integer page, Integer pageSize);
//    
//    public int selectCountByExample(Map <String, Object> map);
                
      public int batchInsert(DataPaidMzFee dataPaidMzFee,List<DataPaidMzFeeDetail> dataPaidMzFeeDetails);
    
}
