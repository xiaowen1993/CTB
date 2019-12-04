/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月8日
 * Created by hhy
 */
package com.ctb.mobile.rest.service;

import java.util.List;
import java.util.Map;

import com.ctb.commons.entity.DataPayMzFee;
import com.ctb.commons.entity.DataPayMzFeeDetail;

/**
 * @ClassName: com.ctb.mobile.rest.service.DataPayMzFeeDetailService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年4月8日 上午9:47:25
 */

public interface DataPayMZFeeDetailService {
    
    public List <DataPayMzFeeDetail> queryDataPayMzFeeDetailByExample(Map<String, Object> map);

    public List <DataPayMzFeeDetail>  getDataPayMZFeeDetailListByYX(String openId, String hospitalCode, String branchHospitalCode, String cardNo, String cardType, String mzFeeId);
    
    public int insertDataPayMzFeeDetailList(List <DataPayMzFeeDetail> list);
    
}
