/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月2日
 * Created by ckm
 * U2FsdGVkX19hf++ld87hozY8TDDInJD/gD/pFKJZJjU=
 */

package com.ctb.mobile.rest.service;

import java.util.List;
import java.util.Map;

import com.ctb.commons.entity.DataPaidMzFee;
import com.ctb.commons.entity.DataPayMzFee;
import com.ctb.commons.entity.DataPayMzFeeDetail;
import com.ctb.framework.commons.entity.RespBody;

/**
 * @ClassName: com.ctb.mobile.rest.service.impl.DataPayMzFeeService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月2日 下午4:41:43
 */

public interface DataPayMZFeeService {
    
    //public List <DataPayMzFeeDetail> queryDataPayMzFeeListByExample(Map <String, Object> map);
    
    //public List <DataPayMzFee> queryDataPayMzFeeListPagedByExample(Map <String, Object> map, Integer page, Integer pageSize);
    
    public int insertDataPayMzFee(DataPayMzFee dataPayMzFee);
    
    public List<DataPayMzFee> getDataPayMzFeeListByYX(String [] hospitalCodes, String unionId, String openId, String cardNo);

    
    /**
	 * 待缴费列表
	 * @Title: dataPaidMzFees
	 * @Description: TODO(通过unionId查询处方平台所有合作医院获取代缴费列表)
	 * @author ckm
	 * @date 2019年4月2日 下午4:46:34
	 * @param unionId(平台患者唯一unionId)
	 * @return
	 */
	public List<DataPaidMzFee> dataPaidMzFees(String unionId);
	
	/**
	 * 
	 * @Title: dataPayMzFee
	 * @Description: TODO(查找订单)
	 * @author ckm
	 * @date 2019年4月24日 下午8:13:26
	 * @param unionId(患者唯一unionID)
	 * @param mzFeeId(门诊Id)
	 * @param hospitalCode(医院code)
	 * @param cardNo(就诊卡号)
	 * @return
	 */
	public DataPayMzFee dataPayMzFee(String unionId,String mzFeeId,String hospitalCode,String cardNo);
	
	/**
	 * 
	 * @Title: saveDataPayMzFeeAndDataPayMzFeeDetails
	 * @Description: TODO(DataPayMzFee、List<DataPayMzFeeDetail>入库事务)
	 * @author ckm
	 * @date 2019年4月24日 下午8:13:52
	 * @param dataPayMzFee
	 * @param dataPayMzFeeDetails
	 * @return
	 */
	public int saveDataPayMzFeeAndDataPayMzFeeDetails(DataPayMzFee dataPayMzFee,List<DataPayMzFeeDetail> dataPayMzFeeDetails);	
	
	/**
	 * 获取处方头部信息
	 * @Title: getContainMZFee
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月12日 下午3:39:01
	 * @param hospitalCode(医院code)
	 * @param unionId(患者唯一unionId)
	 * @param openId(处方外流公众号openID)
	 * @param cardNo(就诊卡号)
	 * @param mzFeeId(门诊ID)
	 * @return
	 */
	public List<Map<String, Object>> getContainMZFee(String hospitalCode, String unionId, String openId,
			String cardNo, String mzFeeId);
	
	/**
	 * 门诊处方详情
	 * @Title: mzDetails
	 * @Description: TODO(门诊处方查看详情)
	 * @author ckm
	 * @date 2019年5月6日 上午9:21:26
	 * @param openId(处方外流公众号openID)
	 * @param hospitalOpenId(医院公众号openId)
	 * @param unionId(患者唯一unionID)
	 * @param hospitalCode(医院code)
	 * @param branchHospitalCode(分院code)
	 * @param cardNo(就诊卡号)
	 * @param cardType(诊疗卡类型)
	 * @param mzFeeId(门诊ID)
	 * @return
	 */
	public RespBody mzDetails(String openId,String hospitalOpenId,String unionId,String hospitalCode,String branchHospitalCode,String cardNo,String cardType,String mzFeeId);;
}
