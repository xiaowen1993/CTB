/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年7月10日
 * Created by hhy
 */
package com.ctb.platform.rest.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ctb.commons.entity.HospitalPharmacistUser;
import com.ctb.commons.entity.HospitalPharmacySettings;
import com.ctb.platform.rest.service.HospitalPharmacistUserService;
import com.ctb.resources.mapper.biz.HospitalPharmacistUserMapper;

import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: com.ctb.platform.rest.service.impl.HospitalPharmacistUserImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年7月10日 上午11:43:05
 */

@Service
public class HospitalPharmacistUserServiceImpl implements HospitalPharmacistUserService{

    private static Logger logger = LoggerFactory.getLogger(HospitalPharmacistUserServiceImpl.class);
    
    @Autowired
    private HospitalPharmacistUserMapper hospitalPharmacistUserMapper;
    
    @Override
    public List<HospitalPharmacistUser> queryList(HospitalPharmacistUser hospitalPharmacistUser) {
        // TODO Auto-generated method stub
        try {
            Example example=new Example(HospitalPharmacistUser.class);
            Example.Criteria criteria=example.createCriteria();
            //拼接查询条件
            criteria.andEqualTo("status", 1);
            if(StringUtils.isNotBlank(hospitalPharmacistUser.getId())) {
                criteria.andEqualTo("id",hospitalPharmacistUser.getId());
            }
            if(StringUtils.isNotBlank(hospitalPharmacistUser.getHospitalCode())) {
                criteria.andEqualTo("hospitalCode",hospitalPharmacistUser.getHospitalCode());
            }
            if(StringUtils.isNotBlank(hospitalPharmacistUser.getPharmacistId())) {
                criteria.andEqualTo("pharmacistId",hospitalPharmacistUser.getPharmacistId());
            }
            List <HospitalPharmacistUser> list =hospitalPharmacistUserMapper.selectByExample(example);
            return list;
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("查询药师列表信息失败，获取数据异常！");
            e.printStackTrace();
            return null; 
        }
    }

    @Override
    public int save(HospitalPharmacistUser hospitalPharmacistUser) {
        
        int res = 0;
        try {
         res += hospitalPharmacistUserMapper.insert(hospitalPharmacistUser);
        
        }catch (Exception e) {
            logger.error("save异常::CODE:{}::ID:{}" , hospitalPharmacistUser.getHospitalCode(),hospitalPharmacistUser.getPharmacistId());
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public int update(HospitalPharmacistUser hospitalPharmacistUser) {
        // TODO Auto-generated method stub
        int res = 0;
        try {
            Example example=new Example(HospitalPharmacistUser.class);
            Example.Criteria criteria=example.createCriteria();
            //拼接查询条件
            criteria.andEqualTo("status", 1);
            if(StringUtils.isNotBlank(hospitalPharmacistUser.getHospitalCode())) {
                criteria.andEqualTo("hospitalCode",hospitalPharmacistUser.getHospitalCode());
            }
            if(StringUtils.isNotBlank(hospitalPharmacistUser.getPharmacistId())) {
                criteria.andEqualTo("pharmacistId",hospitalPharmacistUser.getPharmacistId());
            }
            res = hospitalPharmacistUserMapper.updateByExampleSelective(hospitalPharmacistUser, example);
            return res;
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("查询药师列表信息失败，获取数据异常！");
            e.printStackTrace();
            return 0; 
        }
    }

    @Override
    public HospitalPharmacistUser queryOne(HospitalPharmacistUser hospitalPharmacistUser) {
        // TODO Auto-generated method stub
        try {
            Example example=new Example(HospitalPharmacistUser.class);
            Example.Criteria criteria=example.createCriteria();
            //拼接查询条件
            criteria.andEqualTo("status", 1);
            if(StringUtils.isNotBlank(hospitalPharmacistUser.getId())) {
                criteria.andEqualTo("id",hospitalPharmacistUser.getId());
            }
            if(StringUtils.isNotBlank(hospitalPharmacistUser.getHospitalCode())) {
                criteria.andEqualTo("hospitalCode",hospitalPharmacistUser.getHospitalCode());
            }
            if(StringUtils.isNotBlank(hospitalPharmacistUser.getPharmacistId())) {
                criteria.andEqualTo("pharmacistId",hospitalPharmacistUser.getPharmacistId());
            }
            return hospitalPharmacistUserMapper.selectOneByExample(example);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("查询药师列表信息失败，获取数据异常！");
            e.printStackTrace();
            return null; 
        }
    }

    @Override
    public int delete(HospitalPharmacistUser hospitalPharmacistUser) {
        // TODO Auto-generated method stub
        try {
            Example example=new Example(HospitalPharmacistUser.class);
            Example.Criteria criteria=example.createCriteria();
            //拼接查询条件
            if(StringUtils.isNotBlank(hospitalPharmacistUser.getId())) {
                criteria.andEqualTo("id",hospitalPharmacistUser.getId());
            }
            if(StringUtils.isNotBlank(hospitalPharmacistUser.getHospitalCode())) {
                criteria.andEqualTo("hospitalCode",hospitalPharmacistUser.getHospitalCode());
            }
            if(StringUtils.isNotBlank(hospitalPharmacistUser.getPharmacistId())) {
                criteria.andEqualTo("pharmacistId",hospitalPharmacistUser.getPharmacistId());
            }
            return hospitalPharmacistUserMapper.deleteByExample(example);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("查询药师列表信息失败，获取数据异常！");
            e.printStackTrace();
            return 0; 
        }
    }

    @Override
    public int deleteByPharmacistIds(String [] pharmacistIds) {
        int res = 0;
        try {
            Example example = new Example(HospitalPharmacistUser.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andIn("pharmacistId", Arrays.asList(pharmacistIds));
            res = hospitalPharmacistUserMapper.deleteByExample(example);
        } catch (Exception e) {
            // TODO: handle exception
            return 0;
        }
        return res;
    }
    
}
