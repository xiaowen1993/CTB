/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月2日
 * Created by ckm
 */
package com.ctb.mobile.rest.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.BizConstant;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.DataPaidMzFee;
import com.ctb.commons.entity.DataPayMzFee;
import com.ctb.commons.entity.DataPayMzFeeDetail;
import com.ctb.commons.entity.DrugList;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.framework.commons.redis.RedisClient;
import com.ctb.mobile.api.YXWOutClient;
import com.ctb.mobile.rest.entity.vo.PayMZFDetailVo;
import com.ctb.mobile.rest.service.DataPayMZFeeDetailService;
import com.ctb.mobile.rest.service.DataPayMZFeeService;
import com.ctb.mobile.rest.service.DrugListService;
import com.ctb.resources.mapper.biz.DataPayMzFeeDetailMapper;
import com.ctb.resources.mapper.biz.DataPayMzFeeMapper;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @ClassName: com.ctb.mobile.rest.service.impl.DataPayMzFeeServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月2日 下午4:49:35
 */
@Service
public class DataPayMZFeeServiceImpl implements DataPayMZFeeService {
    
    private Logger logger = LoggerFactory.getLogger(DataPayMZFeeServiceImpl.class);
    
    @Autowired
    private DataPayMzFeeMapper dataPayMzFeeMapper;
    
    @Autowired
    private DataPayMzFeeDetailMapper dataPayMzFeeDetailMapper;
    
    @Autowired
    private YXWOutClient yXWOutClient;
    
    @Autowired
    private RedisClient redisClient;
    
    @Autowired
    private DataPayMZFeeDetailService dataPayMZFeeDetailService;
      
    @Autowired
	private DrugListService drugListService;
    
    @Override
    public List<DataPaidMzFee> dataPaidMzFees(String unionId) {
        // TODO Auto-generated method stub
        
        return null;
    }
    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public int saveDataPayMzFeeAndDataPayMzFeeDetails(DataPayMzFee dataPayMzFee,
            List<DataPayMzFeeDetail> dataPayMzFeeDetails) {
        int res = 0;
        try {
        	Example example = new Example(DataPayMzFee.class);
            Criteria criteria = example.createCriteria();
            criteria.andEqualTo("cardNo", dataPayMzFee.getCardNo());
            criteria.andEqualTo("hospitalCode", dataPayMzFee.getHospitalCode());
            criteria.andEqualTo("mzFeeId", dataPayMzFee.getMzFeeId());
            criteria.andEqualTo("branchCode", dataPayMzFee.getBranchCode());
            dataPayMzFeeMapper.deleteByExample(example);
            res += dataPayMzFeeMapper.insert(dataPayMzFee);

            
            String[] mzfeeids=new String[dataPayMzFeeDetails.size()];
            for (int i = 0; i < dataPayMzFeeDetails.size(); i++) {
            	mzfeeids[i]=dataPayMzFeeDetails.get(i).getMzFeeId();
			}
        	Example example1 = new Example(DataPayMzFeeDetail.class);
            Criteria criteria1 = example1.createCriteria();
            criteria1.andIn("mzFeeId", Arrays.asList(mzfeeids));
            dataPayMzFeeDetailMapper.deleteByExample(example1);
			res += dataPayMzFeeDetailMapper.batchInsert(dataPayMzFeeDetails);
            if (res < 2) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
            }
            return res;
        } catch (Exception e) {
            logger.error("dataPayMzFee:::" + JSONObject.toJSONString(dataPayMzFee) + ",dataPayMzFeeDetails:::"
                    + JSONObject.toJSON(dataPayMzFeeDetails));
            logger.error("保存数据失败，保存异常!:::" + e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
        }
        return 0;
    }
    
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public int insertDataPayMzFee(DataPayMzFee dataPayMzFee) {
        int res = 0;
        try {
            Example example = new Example(DataPayMzFee.class);
            Criteria criteria = example.createCriteria();
            criteria.andEqualTo("cardNo", dataPayMzFee.getCardNo());
            criteria.andEqualTo("hospitalCode", dataPayMzFee.getHospitalCode());
            criteria.andEqualTo("mzFeeId", dataPayMzFee.getMzFeeId());
            criteria.andEqualTo("branchCode", dataPayMzFee.getBranchCode());
            int delRes = dataPayMzFeeMapper.deleteByExample(example);
            if (delRes == 1) {
                System.out.println("del:::" + dataPayMzFee.getCardNo());
            }
            int payFeeRes = dataPayMzFeeMapper.insert(dataPayMzFee);
            if (!CollectionUtils.isEmpty(dataPayMzFee.getDataPayMzFeeDetails())) {
                
                int detailRes = dataPayMZFeeDetailService
                        .insertDataPayMzFeeDetailList(dataPayMzFee.getDataPayMzFeeDetails());
                if (payFeeRes != 1 || detailRes == 0) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
                }
            }
            res += payFeeRes;
            return res;
        } catch (Exception e) {
            logger.error("insertDataPayMzFeeList异常：：：");
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
            return 0;
        }
    }
    
    @Override
    public List<DataPayMzFee> getDataPayMzFeeListByYX(String[] hospitalCodes, String unionId, String openId,
            String cardNo) {
        
        try {
            List<DataPayMzFee> dataPayMzFeeList = new ArrayList<DataPayMzFee>();
            System.out.println("loadMZListData入参：：：hospitalCodes=" + hospitalCodes.toString() + "&unionId=" + unionId + "&openId=" + openId
                        + "&cardNo=" + cardNo);
            RespBody MZListRes = yXWOutClient.loadMZListData(hospitalCodes, unionId, openId, cardNo);
            System.out.println("loadMZListData===" + MZListRes.getMessage());
            if (Status.OK.equals(MZListRes.getStatus())) {
                
                List<Map<String, Object>> list = (List<Map<String, Object>>) MZListRes.getMessage();
                for (Map<String, Object> map : list) {//遍历拿到每个医院的map
                    for (String key : map.keySet()) {//这个key只有一个，即医院hopitalCode
                        List<Map<String, Object>> UserInfoList = (List<Map<String, Object>>) map.get(key);//拿到所有用户信息的map
                        for (Map<String, Object> UserInfoMap : UserInfoList) {//遍历拿单个用户信息
                            DataPayMzFee dataPayMzFee = new DataPayMzFee(UserInfoMap);
                            dataPayMzFee.setId(PKGenerator.generateId());
                            cardNo = (String) UserInfoMap.get("cardNo");
                            openId = (String) UserInfoMap.get("openId");
                            
                            List<Map<String, Object>> MZDataList = (List<Map<String, Object>>) UserInfoMap//这个map拿待缴费列表
                                    .get("MZDataList");
                            for (Map<String, Object> MZDataMap : MZDataList) {//遍历待缴费列表 以便去拿待缴费详情
                                DataPayMzFee resDataPayMzFee = new DataPayMzFee(dataPayMzFee, MZDataMap);
                                
                                //调用待缴费详情需要的参数
                                String cardType = (String) UserInfoMap.get("cardType");
                                String hospitalCode = (String) UserInfoMap.get("hospitalCode");
                                String mzFeeId = (String) MZDataMap.get("mzFeeId");
                                String branchHospitalCode = (String) MZDataMap.get("branchHospitalCode");

                                List<DataPayMzFeeDetail> MZDetailList = dataPayMZFeeDetailService
                                        .getDataPayMZFeeDetailListByYX(openId, hospitalCode, branchHospitalCode, cardNo,
                                                cardType, mzFeeId);
                                if(MZDetailList!=null) {
                                    resDataPayMzFee.setDataPayMzFeeDetails(MZDetailList);
                                    dataPayMzFeeList.add(resDataPayMzFee);
                                }
                                
                                String cacheKey = key + CacheConstants.CACHE_KEY_SPLIT_CHAR + cardNo + CacheConstants.CACHE_KEY_SPLIT_CHAR + mzFeeId;
                                //拿到所有该用户下的待缴费列表后放到缓存
                                redisClient.hset(CacheConstants.CACHE_HOSPITAL_CARDNO_MZFEE_HASH_KEY_PREFIX, cacheKey, resDataPayMzFee, BizConstant.PRESCRIPTION_RECORD_EXPIRES_TIME);

                            }
                        }
                    }
                }
            } else {
                logger.error("loadMZListData异常：" + MZListRes.getMessage().toString());
            }
            return dataPayMzFeeList;
        } catch (Exception e) {
            logger.error("getDataPayMzFeeListByYX异常:::unionId=" + unionId);
            e.printStackTrace();
            return new ArrayList<DataPayMzFee>();
        }
    }
    
    @Override
    public List<Map<String, Object>> getContainMZFee(String hospitalCodes, String unionId, String openId, String cardNo,
            String mzFeeId) {
        try {
            String[] codes = { hospitalCodes };
            List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
            List<Map<String, Object>> containList = new ArrayList<Map<String, Object>>();
            String openId_test = "U2FsdGVkX19hf++ld87hozY8TDDInJD/gD/pFKJZJjU=";
            RespBody respBody = yXWOutClient.loadMZListData(codes, unionId, openId, cardNo);
            if (Status.OK.equals(respBody.getStatus())) {
                List<Map<String, Object>> list = (List<Map<String, Object>>) respBody.getMessage();
                if (list.size() > 0) {
                    for (Iterator iterator = list.iterator(); iterator.hasNext();) {
                        Map<String, Object> map = (Map<String, Object>) iterator.next();
                        maps = (List<Map<String, Object>>) map.get(hospitalCodes);
                        if (maps!=null&&!maps.isEmpty()&&maps.size() > 0) {
                        	String sex=(String) maps.get(0).get("sex");String age=(String) maps.get(0).get("age");
                        	containList=(List<Map<String, Object>>) maps.get(0).get("MZDataList");
                        	for (int i = 0; i < containList.size(); i++) {
                        		Map<String, Object> map2=containList.get(i);
                        		map2.put("sex", sex);
                        		map2.put("age", age);
                        		containList.clear();
                        		containList.add(map2);
							}
                            break;
                        }
                    }
                    maps = contain(containList, mzFeeId);
                }
            }
            return maps;
        } catch (Exception e) {
            logger.error("获取处方头部信息,程序异常！:::" + e.toString());
            return new ArrayList<Map<String, Object>>();
        }
    }
    
    /**
     * 获取mzFeeId处方头部信息
     * 
     * @Title: contain
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author ckm
     * @date 2019年4月12日 下午3:40:25
     * @param list
     * @param mzFeeId
     * @return
     */
    private List<Map<String, Object>> contain(List<Map<String, Object>> list, String mzFeeId) {
        List<Map<String, Object>> objects = new ArrayList<Map<String, Object>>();
        if (list.size() > 0) {
            Collection<Map<String, Object>> result = Collections2.filter(list, new Predicate<Map<String, Object>>() {
                @Override
                public boolean apply(Map<String, Object> input) {
                    return input.get("mzFeeId").toString().contains(mzFeeId);
                }
            });
            objects = (List<Map<String, Object>>) JSONArray.toJSON(result);
        }
        return objects;
    }

    @Override
	public DataPayMzFee dataPayMzFee(String unionId, String mzFeeId, String hospitalCode, String cardNo) {
		Example example = new Example(DataPayMzFee.class);
		Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(unionId)) {
			criteria.andEqualTo("hospitalCode", hospitalCode);
		}
		if (StringUtils.isNotBlank(mzFeeId)) {
			criteria.andEqualTo("mzFeeId", mzFeeId);
		}
		if (StringUtils.isNotBlank(hospitalCode)) {
			criteria.andEqualTo("hospitalCode", hospitalCode);
		}
		if (StringUtils.isNotBlank(cardNo)) {
			criteria.andEqualTo("cardNo", cardNo);
		}
		DataPayMzFee dataPayMzFee = dataPayMzFeeMapper.selectOneByExample(example);
		return dataPayMzFee;
	}

	@Override
	public RespBody mzDetails(String openId, String hospitalOpenId, String unionId, String hospitalCode,
			String branchHospitalCode, String cardNo, String cardType, String mzFeeId) {
		try {
			PayMZFDetailVo detailVo = null;
			Map<String, Object> map = null;
			// step1:对外接口
			List<Map<String, Object>> maps =getContainMZFee(hospitalCode, unionId, hospitalOpenId,
					cardNo, mzFeeId);
			if (maps.size() > 0) {
				map = maps.get(0);
			}
			if (map != null) {
			    List<DataPayMzFeeDetail> dataPayMzFeeDetails = dataPayMZFeeDetailService.getDataPayMZFeeDetailListByYX(
			            hospitalOpenId, hospitalCode, branchHospitalCode, cardNo, cardType, mzFeeId);
				if(!dataPayMzFeeDetails.isEmpty()&&dataPayMzFeeDetails.size()>0) {				    
				    for (DataPayMzFeeDetail dataPayMzFeeDetail : dataPayMzFeeDetails) {
				        dataPayMzFeeDetail.setId(PKGenerator.generateId());
				        DrugList drugList = drugListService.getDrug(hospitalCode, dataPayMzFeeDetail.getItemId());
				        if (drugList != null) {
				            dataPayMzFeeDetail.setUsage(drugList.getUsage());
				            dataPayMzFeeDetail.setCadn(drugList.getCadn());
				            dataPayMzFeeDetail.setSpecification(drugList.getSpecification());
				            dataPayMzFeeDetail.setDrugName(drugList.getDrugName());
				        }
				    }				
				}
                detailVo = new PayMZFDetailVo(map, dataPayMzFeeDetails, hospitalOpenId, openId, unionId);
			}
			if (detailVo != null) {
				return new RespBody(Status.OK, detailVo);
			} else {
				return new RespBody(Status.PROMPT, "暂无数据！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询待缴费详情失败，查询异常!:::" + e.toString());
			return new RespBody(Status.ERROR, "查询待缴费详情失败,查询异常!");
		}
	}
}
