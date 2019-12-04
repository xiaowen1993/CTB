package com.ctb.syncdata.rest.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ctb.data.persistence.mapper.CtbMapper;
import com.ctb.syncdata.rest.entity.Platform;

@Mapper
public interface PlatformMapper extends CtbMapper<Platform> {
}