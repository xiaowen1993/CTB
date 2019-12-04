package com.ctb.syncdata.rest.services;

import java.util.List;

import com.ctb.syncdata.rest.entity.vo.HospitalPlatformVO;

public interface HospitalService {
    
    public List<HospitalPlatformVO> findHospitalPlatformSettingsByCode(String... hospitalCodes);
}
