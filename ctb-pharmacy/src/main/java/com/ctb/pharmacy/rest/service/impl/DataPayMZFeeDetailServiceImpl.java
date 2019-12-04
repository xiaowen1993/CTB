/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年5月6日
 * Created by hhy
 */
package com.ctb.pharmacy.rest.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctb.commons.entity.DataPayMzFeeDetail;
import com.ctb.pharmacy.rest.service.DataPayMZFeeDetailService;
import com.ctb.resources.mapper.biz.DataPayMzFeeDetailMapper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


/**
 * @ClassName: com.ctb.pharmacy.rest.service.impl.DataPayMZFeeDetailServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年5月6日 下午4:42:51
 */
@Service
public class DataPayMZFeeDetailServiceImpl implements DataPayMZFeeDetailService{
    
    @Autowired
    private DataPayMzFeeDetailMapper dataPayMzFeeDetailMapper;

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
    
}
