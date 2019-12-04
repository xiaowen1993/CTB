/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月8日
 * Created by hhy
 */
package com.ctb.mobile.rest.service.impl;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.entity.DataPayMzFee;
import com.ctb.commons.entity.DataPayMzFeeDetail;
import com.ctb.commons.entity.DrugList;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.mobile.api.YXWOutClient;
import com.ctb.mobile.rest.service.DataPayMZFeeDetailService;
import com.ctb.mobile.rest.service.DrugListService;
import com.ctb.resources.mapper.biz.DataPayMzFeeDetailMapper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @ClassName: com.ctb.mobile.rest.service.impl.DataPayMzFeeDetailImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年4月8日 上午9:48:27
 */

@Service
public class DataPayMZFeeDetailServiceImpl implements DataPayMZFeeDetailService{
    
    private Logger logger = LoggerFactory.getLogger(DataPayMZFeeDetailServiceImpl.class);
    
    @Autowired
    private DataPayMzFeeDetailMapper dataPayMzFeeDetailMapper;
    
    @Autowired
    private DrugListService drugListService;
    
    @Autowired
    private YXWOutClient yXWOutClient;

    @Override
    public List<DataPayMzFeeDetail> queryDataPayMzFeeDetailByExample(Map<String, Object> map) {
        List<DataPayMzFeeDetail> list = null;
        Example exampel = new Example(DataPayMzFeeDetail.class);
        Criteria criteria = exampel.createCriteria();
        for (String key : map.keySet()) {
            // value有值时才进行判断
            if (map.get(key) != null && StringUtils.isNotBlank(map.get(key).toString())) {
                criteria.andEqualTo(key, map.get(key));
            }
        }
        
        try {
            list = dataPayMzFeeDetailMapper.selectByExample(exampel);
        } catch (Exception e) {
            System.out.println("queryDataPaidMzFeeDetailByExample出错：：：");
            e.printStackTrace();
        }
        return list;
    
    }

    @Override
    public List<DataPayMzFeeDetail> getDataPayMZFeeDetailListByYX(String openId, String hospitalCode,
            String branchHospitalCode, String cardNo, String cardType, String mzFeeId) {
        // 获取详情
        List<DataPayMzFeeDetail> MZDetailList = new ArrayList<DataPayMzFeeDetail>();
        System.out.println(
                "loadMZDetailsData入参：：：openId=" + openId + "&hospitalCode=" + hospitalCode + "&branchHospitalCode="
                        + branchHospitalCode + "&cardNo=" + cardNo + "&cardType=" + cardType + "&mzFeeId=" + mzFeeId);
        RespBody MZDetailsRes = yXWOutClient.loadMZDetailsData(openId, hospitalCode, branchHospitalCode, cardNo,
                cardType, mzFeeId);
        System.out.println("loadMZDetailsData=====" + MZDetailsRes.getMessage());
        
        if (Status.OK.equals(MZDetailsRes.getStatus())) {
            List<Map<String, Object>> MZDetails = (List<Map<String, Object>>) MZDetailsRes.getMessage();
            String jsonStr = JSONObject.toJSONString(MZDetails);
            List<DataPayMzFeeDetail> dataPayMzFeeDetails = JSONArray.parseArray(jsonStr, DataPayMzFeeDetail.class);
            for (DataPayMzFeeDetail dataPayMzFeeDetail : dataPayMzFeeDetails) {
                dataPayMzFeeDetail.setId(PKGenerator.generateId());
                DrugList drugList = drugListService.getDrug(hospitalCode, dataPayMzFeeDetail.getItemId());
                if (drugList == null) {// 拿不到药品则说明不能外流 只要有一个药品不能外流则返回空
                    System.out.println(dataPayMzFeeDetail.getItemId() + "不在" + hospitalCode + "白名单中");
                    return null;
                }
                
            }
            return dataPayMzFeeDetails;
        }
        return MZDetailList;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public int insertDataPayMzFeeDetailList(List<DataPayMzFeeDetail> list) {
        int res = 0;
        try {
            for (DataPayMzFeeDetail dataPayMzFeeDetail : list) {
                // 删了再插入
                Example example = new Example(DataPayMzFeeDetail.class);
                Criteria criteria = example.createCriteria();
                criteria.andEqualTo("mzFeeId", dataPayMzFeeDetail.getMzFeeId());
                criteria.andEqualTo("itemId", dataPayMzFeeDetail.getItemId());
                dataPayMzFeeDetailMapper.deleteByExample(example);
                res += dataPayMzFeeDetailMapper.insert(dataPayMzFeeDetail);
            }
            return res;
        } catch (Exception e) {
            logger.error("insertDataPayMzFeeDetailList异常:::");
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
            return 0;
        }
    }

    
}
