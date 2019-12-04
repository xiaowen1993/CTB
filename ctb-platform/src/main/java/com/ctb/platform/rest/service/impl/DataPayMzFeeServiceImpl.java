/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月27日
 * Created by ckm
 */
package com.ctb.platform.rest.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctb.commons.entity.DataPayMzFee;
import com.ctb.commons.entity.DataPayMzFeeDetail;
import com.ctb.platform.rest.service.DataPayMzFeeService;
import com.ctb.resources.mapper.biz.DataPayMzFeeDetailMapper;
import com.ctb.resources.mapper.biz.DataPayMzFeeMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: com.ctb.platform.rest.service.impl.DataPayMzFeeServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年3月27日 下午8:27:02
 */
@Service
public class DataPayMzFeeServiceImpl implements DataPayMzFeeService {
	
    private static Logger logger = LoggerFactory.getLogger(DataPayMzFeeServiceImpl.class);

	@Autowired
	private DataPayMzFeeMapper dataPayMzFeeMapper;
	@Autowired
	private DataPayMzFeeDetailMapper dataPayMzFeeDetailMapper;
	
	@Override
	public DataPayMzFee queryDataPayMzFee(DataPayMzFee dataPayMzFee) {
		try {
			Example example=new Example(DataPayMzFee.class);
			Example.Criteria criteria=example.createCriteria();
			if(StringUtils.isNotBlank(dataPayMzFee.getMzFeeId())){
		        criteria.andEqualTo("mzFeeId",dataPayMzFee.getMzFeeId());
		    }
			if(StringUtils.isNotBlank(dataPayMzFee.getHospitalId())){
		        criteria.andEqualTo("hospitalId",dataPayMzFee.getHospitalId());
		    }
			if(StringUtils.isNotBlank(dataPayMzFee.getBranchId())){
		        criteria.andEqualTo("branchId",dataPayMzFee.getBranchId());
		    }
			DataPayMzFee dataPayMzFee2Info=dataPayMzFeeMapper.selectOneByExample(example);
			if(dataPayMzFee2Info!=null) {
				Example example2=new Example(DataPayMzFeeDetail.class);
				Example.Criteria criteria2=example.createCriteria();
				if(StringUtils.isNotBlank(dataPayMzFee.getMzFeeId())){
			        criteria2.andEqualTo("mzFeeId",dataPayMzFee.getMzFeeId());
			    }
				List<DataPayMzFeeDetail> dataPayMzFeeDetails=dataPayMzFeeDetailMapper.selectByExample(example2);
				if(dataPayMzFeeDetails.size()>0) {
					dataPayMzFee2Info.setDataPayMzFeeDetails(dataPayMzFeeDetails);
				}
			}
			return dataPayMzFee2Info;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询处方信息失败,查询异常！:::"+e.toString());
			return new DataPayMzFee();
		}
	}

}
