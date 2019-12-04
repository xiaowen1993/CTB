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

import com.ctb.commons.entity.DataPaidMzFee;
import com.ctb.commons.entity.DataPaidMzFeeDetail;
import com.ctb.commons.entity.DataPayMzFee;
import com.ctb.commons.entity.DataPayMzFeeDetail;
import com.ctb.platform.rest.service.DataPaidMzFeeService;
import com.ctb.resources.mapper.biz.DataPaidMzFeeDetailMapper;
import com.ctb.resources.mapper.biz.DataPaidMzFeeMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: com.ctb.platform.rest.service.impl.DataPaidMzFeeServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年3月27日 下午8:41:25
 */
@Service
public class DataPaidMzFeeServiceImpl implements DataPaidMzFeeService {

	private static Logger logger = LoggerFactory.getLogger(DataPayMzFeeServiceImpl.class);

	@Autowired
	private DataPaidMzFeeMapper dataPaidMzFeeMapper;
	@Autowired
	private DataPaidMzFeeDetailMapper dataPaidMzFeeDetailMapper;

	@Override
	public DataPaidMzFee queryDataPaidMzFee(DataPaidMzFee dataPaidMzFee) {
		try {
			Example example=new Example(DataPayMzFee.class);
			Example.Criteria criteria=example.createCriteria();
			if(StringUtils.isNotBlank(dataPaidMzFee.getMzFeeId())){
		        criteria.andEqualTo("mzFeeId",dataPaidMzFee.getMzFeeId());
		    }
			if(StringUtils.isNotBlank(dataPaidMzFee.getHospitalId())){
		        criteria.andEqualTo("hospitalId",dataPaidMzFee.getHospitalId());
		    }
			if(StringUtils.isNotBlank(dataPaidMzFee.getBranchId())){
		        criteria.andEqualTo("branchId",dataPaidMzFee.getBranchId());
		    }
			DataPaidMzFee dataPaidMzFee2Info=dataPaidMzFeeMapper.selectOneByExample(example);
			if(dataPaidMzFee2Info!=null) {
				Example example2=new Example(DataPayMzFeeDetail.class);
				Example.Criteria criteria2=example.createCriteria();
				if(StringUtils.isNotBlank(dataPaidMzFee.getMzFeeId())){
			        criteria2.andEqualTo("mzFeeId",dataPaidMzFee.getMzFeeId());
			    }
				List<DataPaidMzFeeDetail> dataPayMzFeeDetails=dataPaidMzFeeDetailMapper.selectByExample(example2);
				if(dataPayMzFeeDetails.size()>0) {
					dataPaidMzFee2Info.setDataPaidMzFeeDetail(dataPayMzFeeDetails);
				}
			}
			return dataPaidMzFee2Info;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询处方信息失败,查询异常！:::"+e.toString());
			return new DataPaidMzFee();
		}
	}

}
