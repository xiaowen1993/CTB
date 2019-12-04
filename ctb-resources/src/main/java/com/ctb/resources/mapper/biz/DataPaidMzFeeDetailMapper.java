package com.ctb.resources.mapper.biz;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ctb.commons.entity.DataPaidMzFeeDetail;
import com.ctb.data.persistence.mapper.CtbMapper;

@Repository
public interface DataPaidMzFeeDetailMapper extends CtbMapper<DataPaidMzFeeDetail> {
	
	public int batchInsert(List<DataPaidMzFeeDetail>dataPaidMzFeeDetails);
	
}