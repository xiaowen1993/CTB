package com.ctb.resources.mapper.biz;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ctb.commons.entity.Hospital;
import com.ctb.data.persistence.mapper.CtbMapper;

@Repository
public interface HospitalMapper extends CtbMapper<Hospital> {
    
    List<Hospital> getHospitals();
}