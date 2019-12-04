/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月28日
 * Created by hhy
 */
package com.ctb.mobile.rest.service;

import java.util.List;
import java.util.Map;

import com.ctb.commons.entity.DataPaidMzFeeDetail;
import com.ctb.commons.entity.PrescriptionRecord;

/**
 * @ClassName: com.ctb.mobile.rest.service.DatapaidMzFeeDetailService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年3月28日 上午9:44:54
 */

public interface DataPaidMZFeeDetailService {
    
    public int selectCount(DataPaidMzFeeDetail dataPaidMzFeeDetail);
    
    public int savePaidMZFeeDetailList(List <DataPaidMzFeeDetail> list);
     
    public List <DataPaidMzFeeDetail> queryDataPaidMzFeeDetailByExample(Map<String, Object> map);

}
