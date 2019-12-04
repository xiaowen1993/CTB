package com.ctb.resources.mapper.biz;

import org.springframework.stereotype.Repository;

import com.ctb.commons.entity.UserInfo;
import com.ctb.data.persistence.mapper.CtbMapper;

@Repository
public interface UserInfoMapper extends CtbMapper<UserInfo> {
}