/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月25日
 * Created by hhy
 */
package com.ctb.mobile.rest.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ctb.commons.entity.DataPaidMzFee;
import com.ctb.commons.entity.DataPaidMzFeeDetail;
import com.ctb.commons.entity.DataPayMzFeeDetail;
import com.ctb.commons.entity.PrescriptionRecord;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.mobile.api.YXWOutClient;
import com.ctb.mobile.rest.controller.PayMZFeeRecordController;
import com.ctb.mobile.rest.service.DataPaidMZFeeDetailService;
import com.ctb.mobile.rest.service.DataPaidMZFeeService;
import com.ctb.resources.mapper.biz.DataPaidMzFeeDetailMapper;
import com.ctb.resources.mapper.biz.DataPaidMzFeeMapper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @ClassName: com.ctb.mobile.rest.service.impl.DataPaidMzFeeServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年3月25日 下午5:12:24
 */

@Service
public class DataPaidMZFeeServiceImpl implements DataPaidMZFeeService{

    private static Logger logger = LoggerFactory.getLogger(DataPaidMZFeeServiceImpl.class);

    @Autowired
    private DataPaidMzFeeDetailMapper dataPaidMzFeeDetailMapper;
    
    @Autowired
    private DataPaidMzFeeMapper dataPaidMzFeeMapper;
    
    @Autowired
    private DataPaidMZFeeDetailService dataPaidMZFeeDetailService;
    
    @Autowired
    private YXWOutClient yXWOutClient;

	@Override
    @Transactional(propagation = Propagation.SUPPORTS)
	public int batchInsert(DataPaidMzFee dataPaidMzFee, List<DataPaidMzFeeDetail> dataPaidMzFeeDetails) {
		try {
			Example example = new Example(DataPaidMzFee.class);
	        Criteria criteria = example.createCriteria();
	        criteria.andEqualTo("hospitalCode", dataPaidMzFee.getHospitalCode());
	        criteria.andEqualTo("cardNo", dataPaidMzFee.getCardNo());
	        criteria.andEqualTo("mzFeeId", dataPaidMzFee.getMzFeeId());
			DataPaidMzFee paidMzFee = dataPaidMzFeeMapper.selectOneByExample(example);
			int res=0;
			if(paidMzFee==null) {
				dataPaidMzFee.setId(PKGenerator.generateId());
				res+= dataPaidMzFeeMapper.insert(dataPaidMzFee);
				res+= dataPaidMzFeeDetailMapper.batchInsert(dataPaidMzFeeDetails);
			}
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("batchInsert 异常"+e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
    		return 0;
		}
	}
    
 /*   @Override
    public List<DataPaidMzFee> queryDataPaidMzFeeListPagedByExample(Map<String, Object> map, Integer page,
            Integer pageSize) {

        List<DataPaidMzFee> list = null;
        PageHelper.startPage(page, pageSize);
        Example exampel = new Example(DataPaidMzFee.class);
        exampel.setOrderByClause("storageTime DESC");//根据入库时间排序
        Criteria criteria = exampel.createCriteria();
        for (String key : map.keySet()) {
            // value有值时才进行判断
            if (map.get(key) != null && StringUtils.isNotBlank(map.get(key).toString())) {
                criteria.andEqualTo(key, map.get(key));
            }
        }
        
        try {
            list = dataPaidMzFeeMapper.selectByExample(exampel);
        } catch (Exception e) {
            System.out.println("queryDataPayMzFeeListPagedByExample出错：：：");
            e.printStackTrace();
        }
        return list;
    
    }

    @Override
    public List<DataPaidMzFeeDetail> queryDataPaidMzFeeListByExample(Map<String, Object> map) {

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
            System.out.println("queryDataPayMzFeeListPagedByExample出错：：：");
            e.printStackTrace();
        }
        return list;
    
    
    }

    @Override
    public int selectCountByExample(Map<String, Object> map) {
        
        Example example = new Example(DataPaidMzFee.class);
        Criteria criteria = example.createCriteria();
        for (String key : map.keySet()) {
            // value有值时才进行判断
            if (map.get(key) != null && StringUtils.isNotBlank(map.get(key).toString())) {
                criteria.andEqualTo(key, map.get(key));
            }
        }
        
        try {
            return dataPaidMzFeeMapper.selectCountByExample(example);
        } catch (Exception e) {
            System.out.println("selectCountByExample出错：：：");
            e.printStackTrace();
            return 0;
        }

    }
    
*/
    
}
