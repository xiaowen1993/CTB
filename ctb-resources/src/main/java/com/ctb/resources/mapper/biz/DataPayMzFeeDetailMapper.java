package com.ctb.resources.mapper.biz;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ctb.commons.entity.DataPayMzFeeDetail;
import com.ctb.data.persistence.mapper.CtbMapper;

@Repository
public interface DataPayMzFeeDetailMapper extends CtbMapper<DataPayMzFeeDetail> {
	
	public int batchInsert(List<DataPayMzFeeDetail> dataPayMzFeeDetails);
}