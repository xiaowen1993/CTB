/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年5月30日
 * Created by ckm
 */
package com.ctb.pharmacy.rest.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.entity.PharmacyUser;
import com.ctb.pharmacy.rest.service.PharmacyUserService;
import com.ctb.resources.mapper.biz.PharmacyUserMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: com.ctb.pharmacy.rest.service.impl.pharmacyUserServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年5月30日 下午5:52:43
 */
@Service
public class PharmacyUserServiceImpl implements PharmacyUserService {

	private static Logger logger = LoggerFactory.getLogger(PharmacyUserServiceImpl.class);

	@Autowired
	private PharmacyUserMapper pharmacyUserMapper;

	@Override
	public int update(PharmacyUser pharmacyUser) {
		return pharmacyUserMapper.updateByPrimaryKey(pharmacyUser);
	}

	@Override
	public PharmacyUser query(PharmacyUser pharmacyUser) {
		try {
			Example example = new Example(PharmacyUser.class);
			Example.Criteria criteria = example.createCriteria();

			if (StringUtils.isNotBlank(pharmacyUser.getName())) {				
				criteria.andEqualTo("name", pharmacyUser.getName());
			}
                criteria.andEqualTo("status", "1");
			PharmacyUser entity = pharmacyUserMapper.selectOneByExample(example);
			if (entity != null) {
				return entity;
			}
		} catch (Exception e) {
			logger.error("查询药房用户异常，pharmacy:[{}],errMsg:[{}]",JSONObject.toJSON(pharmacyUser) ,e.toString());
		}
		return null;
	}

    @Override
    public PharmacyUser queryByPharmacyId(String pharmacyId) {
        Example example = new Example(PharmacyUser.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("pharmacyId", pharmacyId);
        criteria.andEqualTo("status", 1);
        List <PharmacyUser> entitys = pharmacyUserMapper.selectByExample(example);
        return CollectionUtils.isNotEmpty(entitys)?entitys.get(0):null;
    }
}
