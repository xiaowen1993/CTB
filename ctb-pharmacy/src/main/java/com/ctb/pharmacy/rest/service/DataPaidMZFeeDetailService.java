/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月28日
 * Created by hhy
 */
package com.ctb.pharmacy.rest.service;

import java.util.List;
import java.util.Map;

import com.ctb.commons.entity.DataPaidMzFeeDetail;

/**
 * 
 * @ClassName: com.ctb.pharmacy.rest.service.DataPaidMZFeeDetailService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月16日 上午10:36:51
 */

public interface DataPaidMZFeeDetailService {
    
    public int selectCount(DataPaidMzFeeDetail dataPaidMzFeeDetail);
    
    public int savePaidMZFeeDetailList(List <DataPaidMzFeeDetail> list);
     
    public List <DataPaidMzFeeDetail> queryDataPaidMzFeeDetailByExample(Map<String, Object> map);

    public List <DataPaidMzFeeDetail> getDataPaidMZFeeDetailListByYX(String openId, String hospitalCode, String branchHospitalCode, String cardNo, String cardType, String mzFeeId);
}
