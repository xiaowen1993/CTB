package com.ctb.syncdata.rest.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ctb.data.persistence.mapper.CtbMapper;
import com.ctb.syncdata.rest.entity.HospitalPlatformSettings;

@Mapper
public interface HospitalPlatformSettingsMapper extends CtbMapper<HospitalPlatformSettings> {
}