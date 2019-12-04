/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月25日
 * Created by ckm
 */
package com.ctb.platform.rest.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctb.commons.entity.PharmacyUser;
import com.ctb.platform.rest.service.PharmacyUserService;
import com.ctb.resources.mapper.biz.PharmacyUserMapper;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: com.ctb.platform.rest.service.impl.PharmacyUserServiceImpl
 * @Description: TODO(药房后台用户实现)
 * @author ckm
 * @date 2019年3月25日 下午5:44:23
 */

@Service
public class PharmacyUserServiceImpl implements PharmacyUserService {

	private static Logger logger = LoggerFactory.getLogger(PharmacyUserServiceImpl.class);
	
	@Autowired
	private PharmacyUserMapper pharmacyUserMapper;
	
	
	@Override
	public int savePharmacyUser(PharmacyUser pharmacyUser) {
		try {
			int res=pharmacyUserMapper.insertSelective(pharmacyUser);
			//if(res==1)redisTemplate.opsForValue().set("pharmacyUser_"+pharmacyUser.getId(), pharmacyUser, 0);
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("保存药房职员用户失败，保存数据异常！"+e.toString());
			return 0;
		}
	}

	@Override
	public int updatePharmacyUser(PharmacyUser pharmacyUser) {
		try {
		    int res= pharmacyUserMapper.updateByPrimaryKeySelective(pharmacyUser);
			//int res=pharmacyUserMapper.updateByPrimaryKey(pharmacyUser);
			//if(res==1)redisTemplate.opsForValue().set("pharmacyUser_"+pharmacyUser.getId(), pharmacyUser, 0);
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("修改药房职员用户失败，修改数据异常！"+e.toString());
			return 0;
		}
	}

	@Override
	public int delPharmacyUser(String id) {
		// TODO Auto-generated method stub
	try {
		int res=pharmacyUserMapper.deleteByPrimaryKey(id);
		//if(res==1)redisTemplate.delete("pharmacyUser_"+id);
		return res;
	} catch (Exception e) {
		// TODO: handle exception
		logger.error("删除药房职员信息失败,删除数据异常！"+e.toString());
		return 0;
	}
	}
	

	@Override
	public PharmacyUser queryPharmacyUser(PharmacyUser pharmacyUser) {
		// TODO Auto-generated method stub
		try {
			Example example=new Example(PharmacyUser.class);
            Example.Criteria criteria=example.createCriteria();
            //拼接查询条件
            if(StringUtils.isNotBlank(pharmacyUser.getId())) {
                criteria.andEqualTo("id",pharmacyUser.getId());
            }
            if(StringUtils.isNotBlank(pharmacyUser.getPharmacyId())) {
    	        criteria.andEqualTo("pharmacyId",pharmacyUser.getPharmacyId());
    	    }
            if(StringUtils.isNotBlank(pharmacyUser.getName())) {
    	        criteria.andEqualTo("name",pharmacyUser.getName());
    	    }
            PharmacyUser pharmacyUserInfo=pharmacyUserMapper.selectOneByExample(example);
            return pharmacyUserInfo;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询药房用户信息失败，获取数据异常！"+e.toString());
			return new PharmacyUser(); 
		}
	}

	@Override
	public List<PharmacyUser> pharmacyUsersList(PharmacyUser pharmacyUser, String searchKey, Integer page,
			Integer pageSize) {
		try {
		    PageHelper.startPage(page, pageSize);
			Example example=new Example(PharmacyUser.class);
	        Example.Criteria criteria=example.createCriteria();
	        //拼接查询条件
	        if(StringUtils.isNotBlank(pharmacyUser.getPharmacyId())) {
		        criteria.andEqualTo("pharmacyId",pharmacyUser.getPharmacyId());
		    }
	        if(StringUtils.isNotBlank(String.valueOf(pharmacyUser.getStatus()))) {
		    	criteria.andEqualTo("status", pharmacyUser.getStatus());
		    }
	        if(StringUtils.isNotBlank(String.valueOf(pharmacyUser.getName()))) {
                criteria.andEqualTo("name", pharmacyUser.getName());
            }
	        if(StringUtils.isNotBlank(searchKey)) {
	        	criteria.orLike("name", "%"+searchKey+"%");
	        }
	        List<PharmacyUser> pharmacyUsers=pharmacyUserMapper.selectByExample(example);
	        return pharmacyUsers;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询药房用户信息失败,查询数据异常！"+e.toString());
			return new ArrayList<PharmacyUser>();
		}
	}

    @Override
    public int deleteByIds(String[] ids) {
        String id = String.join("','", ids);
        try {
            int res = pharmacyUserMapper.deleteByIds("'" + id + "'");
            return res;
        } catch (Exception e) {
            logger.error("执行deleteByIds异常:::" + id);
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteByBranchPharmacyIds(String[] branchPharmacyIds) {
        
        Example example=new Example(PharmacyUser.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andIn("pharmacyId", Arrays.asList(branchPharmacyIds));
        try {
            int res = pharmacyUserMapper.deleteByExample(example);
            return res;
        } catch (Exception e) {
            logger.error("执行deleteByBranchPharmacyIds异常:::" + branchPharmacyIds.toString());
            e.printStackTrace();
            return 0;
        }
    }
}
