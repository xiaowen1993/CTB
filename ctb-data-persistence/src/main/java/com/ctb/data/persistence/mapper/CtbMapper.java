package com.ctb.data.persistence.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.common.ids.DeleteByIdsMapper;

public interface CtbMapper <T> extends Mapper<T>, MySqlMapper<T>, DeleteByIdsMapper<T> {

}
