/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月8日
 * Created by ckm
 */
package com.ctb.mobile.rest.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctb.commons.entity.User;
import com.ctb.commons.entity.UserInfo;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.mobile.rest.controller.CTBServerController;
import com.ctb.mobile.rest.service.UserInfoService;
import com.ctb.resources.mapper.biz.UserInfoMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: com.ctb.mobile.rest.service.impl.UserInfoServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月8日 下午7:51:46
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
	
	private static Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

	
	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Override
	public boolean isUnique(UserInfo userInfo) {
		try {
			Example example = new Example(UserInfo.class);
			Example.Criteria criteria = example.createCriteria();
			if(StringUtils.isNotBlank(userInfo.getRefUnionId())) {
				criteria.andEqualTo("refUnionId", userInfo.getRefUnionId());				
			}
			if(StringUtils.isNotBlank(userInfo.getHisOpenId())) {
				criteria.andEqualTo("hisOpenId", userInfo.getHisOpenId());				
			}
			if(StringUtils.isNotBlank(userInfo.getCardNo())) {
				criteria.andEqualTo("cardNo", userInfo.getCardNo());				
			}
			UserInfo userInfoEntity= userInfoMapper.selectOneByExample(example);
			if(userInfoEntity!=null) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			logger.error("查询userInfo失败,查询异常！：：："+e.toString());
			return false;
		}
	}

	@Override
	public int save(UserInfo userInfo) {
		try {			
			userInfo.setId(PKGenerator.generateId());
			return userInfoMapper.insert(userInfo);
		} catch (Exception e) {
			logger.error("保存用户详情失败，保存异常！：：："+e.toString());
			return 0;
		}
	}

}
