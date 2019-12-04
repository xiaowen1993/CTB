package com.ctb.syncdata.rest.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctb.syncdata.rest.entity.vo.HospitalPlatformVO;
import com.ctb.syncdata.rest.mapper.HospitalMapper;
import com.ctb.syncdata.rest.services.HospitalService;

@Service
public class HospitalServiceImpl implements HospitalService {
    
    @Autowired
    private HospitalMapper hospitalMapper;
    
    public List<HospitalPlatformVO> findHospitalPlatformSettingsByCode(String... hospitalCodes) {
        
        return hospitalMapper.findHospitalPlatformSettingsByCode(hospitalCodes);
    }
    
}
