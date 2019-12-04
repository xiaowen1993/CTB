package com.ctb.syncdata.rest.mapper;

import java.util.List;

import com.ctb.data.persistence.mapper.CtbMapper;
import com.ctb.syncdata.rest.entity.Hospital;
import com.ctb.syncdata.rest.entity.vo.HospitalPlatformVO;

public interface HospitalMapper extends CtbMapper<Hospital> {
    
    public List<HospitalPlatformVO> findHospitalPlatformSettingsByCode(String... hospitalCodes);
}