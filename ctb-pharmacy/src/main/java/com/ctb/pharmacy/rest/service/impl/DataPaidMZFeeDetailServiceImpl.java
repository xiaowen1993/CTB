/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月28日
 * Created by hhy
 */
package com.ctb.pharmacy.rest.service.impl;

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

import com.ctb.commons.entity.DataPaidMzFeeDetail;
import com.ctb.commons.entity.DataPayMzFeeDetail;
import com.ctb.commons.entity.PrescriptionRecord;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.pharmacy.rest.service.DataPaidMZFeeDetailService;
import com.ctb.resources.mapper.biz.DataPaidMzFeeDetailMapper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @ClassName: com.ctb.mobile.rest.service.impl.DataPaidMzFeeDetailServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年3月28日 上午9:47:41
 */

@Service
public class DataPaidMZFeeDetailServiceImpl implements DataPaidMZFeeDetailService{
    
    private static Logger logger = LoggerFactory.getLogger(DataPaidMZFeeDetailServiceImpl.class);
    
    @Autowired
    private DataPaidMzFeeDetailMapper dataPaidMzFeeDetailMapper;
    
    
    public int selectCount(DataPaidMzFeeDetail dataPaidMzFeeDetail) {
        
        return dataPaidMzFeeDetailMapper.selectCount(dataPaidMzFeeDetail);
    }
    

    @Override
    public List<DataPaidMzFeeDetail> queryDataPaidMzFeeDetailByExample(Map<String, Object> map) {

        List<DataPaidMzFeeDetail> list = null;
        Example exampel = new Example(DataPaidMzFeeDetail.class);
        Criteria criteria = exampel.createCriteria();
        for (String key : map.keySet()) {
            // value有值时才进行判断
            if (map.get(key) != null && StringUtils.isNotBlank(map.get(key).toString())) {
                criteria.andEqualTo(key, map.get(key));
            }
        }
        
        try {
            list = dataPaidMzFeeDetailMapper.selectByExample(exampel);
        } catch (Exception e) {
            System.out.println("queryDataPaidMzFeeDetailByExample出错：：：");
            e.printStackTrace();
        }
        return list;
    
    }


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public int savePaidMZFeeDetailList(List<DataPaidMzFeeDetail> list) {
        int res = 0;
        try {
            for (DataPaidMzFeeDetail dataPaidMzFeeDetail : list) {
                res += dataPaidMzFeeDetailMapper.insert(dataPaidMzFeeDetail);
            }
            return res;
        } catch (Exception e) {
            logger.error("savePaidMZFeeDetailList异常");
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
            return -1;
        }
    }


    @Override
    public List<DataPaidMzFeeDetail> getDataPaidMZFeeDetailListByYX(String openId, String hospitalCode,
            String branchHospitalCode, String cardNo, String cardType, String mzFeeId) {
        // TODO Auto-generated method stub
        return null;
    }

    
}
