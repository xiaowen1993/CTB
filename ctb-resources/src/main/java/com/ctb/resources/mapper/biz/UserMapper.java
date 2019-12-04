package com.ctb.resources.mapper.biz;

import org.springframework.stereotype.Repository;

import com.ctb.commons.entity.User;
import com.ctb.data.persistence.mapper.CtbMapper;

@Repository
public interface UserMapper extends CtbMapper<User> {
}