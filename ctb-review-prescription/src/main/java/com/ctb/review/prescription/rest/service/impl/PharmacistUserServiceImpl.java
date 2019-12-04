/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年7月10日
 * Created by hhy
 */
package com.ctb.review.prescription.rest.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ctb.commons.entity.HospitalPharmacistUser;
import com.ctb.commons.entity.PharmacistUser;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.resources.mapper.biz.PharmacistUserMapper;
import com.ctb.review.prescription.rest.service.HospitalPharmacistUserService;
import com.ctb.review.prescription.rest.service.PharmacistUserService;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: com.ctb.review.prescription.rest.service.impl.PharmacistUserServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年7月10日 下午5:57:36
 */

@Service
public class PharmacistUserServiceImpl implements PharmacistUserService{
    
    private static Logger logger = LoggerFactory.getLogger(PharmacistUserServiceImpl.class);
    
    @Autowired
    private PharmacistUserMapper pharmacistMapper;
    
    @Autowired
    private HospitalPharmacistUserService hospitalPharmacistUserService;
    
    public List<PharmacistUser> pharmacistList(PharmacistUser pharmacist,String searchKey,Integer page,Integer pageSize){
        try {
            PageHelper.startPage(page, pageSize);
            Example example=new Example(PharmacistUser.class);
            Example.Criteria criteria=example.createCriteria();
            //拼接查询条件
            if(StringUtils.isNotBlank(String.valueOf(pharmacist.getStatus()))) {
                criteria.andEqualTo("status", pharmacist.getStatus());
            }
            if(StringUtils.isNotBlank(String.valueOf(pharmacist.getName()))) {
                criteria.andEqualTo("name", pharmacist.getName());
            }
            if(StringUtils.isNotBlank(searchKey)) {
                criteria.orLike("name", "%"+searchKey+"%");
            }
            List<PharmacistUser> pharmacyUsers = pharmacistMapper.selectByExample(example);
            
            for(PharmacistUser pharmacistUser: pharmacyUsers) {
                List <String> hospitalCodes = new ArrayList<String>();
                HospitalPharmacistUser hospitalPharmacistUser = new HospitalPharmacistUser();
                hospitalPharmacistUser.setPharmacistId(pharmacistUser.getId());;
                List <HospitalPharmacistUser> list = hospitalPharmacistUserService.queryList(hospitalPharmacistUser);
                for(HospitalPharmacistUser entity:list) {
                    hospitalCodes.add(entity.getHospitalCode());
                }
                pharmacistUser.setHospitalCodes(hospitalCodes);
            }
            
            return pharmacyUsers;
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("查询药房用户信息失败,查询数据异常！"+e.toString());
            return new ArrayList<PharmacistUser>();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int savePharmacist(PharmacistUser pharmacist) {
        try {
            int res = pharmacistMapper.insertSelective(pharmacist);
            if (res == 1) {
                List<String> codes = pharmacist.getHospitalCodes();
                for (String code : codes) {
                    
                    HospitalPharmacistUser entity = new HospitalPharmacistUser();
                    entity.setId(PKGenerator.generateId());
                    entity.setHospitalCode(code);
                    entity.setPharmacistId(pharmacist.getId());
                    entity.setStatus("1");
                    res = hospitalPharmacistUserService.save(entity);
                    if (res != 1) {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
                    }
                }
            }
            return res;
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("保存药房职员用户失败，保存数据异常！" + e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
            return 0;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int updatePharmacist(PharmacistUser pharmacist) {
        try {
            int res = pharmacistMapper.updateByPrimaryKeySelective(pharmacist);
            if (res == 1) {

                HospitalPharmacistUser entity = new HospitalPharmacistUser();
                entity.setPharmacistId(pharmacist.getId());
                hospitalPharmacistUserService.delete(entity);
                
                List<String> codes = pharmacist.getHospitalCodes();
                for (String code : codes) {
                    
                    entity.setId(PKGenerator.generateId());
                    entity.setHospitalCode(code);
                    entity.setPharmacistId(pharmacist.getId());
                    entity.setStatus("1");
                    res = hospitalPharmacistUserService.save(entity);
                    if (res != 1) {
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
                    }
                }
            }
            return res;
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("修改药房职员用户失败，修改数据异常！" + e.toString());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚
            return 0;
        }
    }

    @Override
    public int delPharmacist(String id) {
        // TODO Auto-generated method stub
    try {
        int res=pharmacistMapper.deleteByPrimaryKey(id);
        //if(res==1)redisTemplate.delete("pharmacyUser_"+id);
        return res;
    } catch (Exception e) {
        // TODO: handle exception
        logger.error("删除药房职员信息失败,删除数据异常！"+e.toString());
        return 0;
    }
    }

    @Override
    public PharmacistUser queryPharmacist(PharmacistUser pharmacist) {
        // TODO Auto-generated method stub
        try {
            Example example=new Example(PharmacistUser.class);
            Example.Criteria criteria=example.createCriteria();
            //拼接查询条件
            if(StringUtils.isNotBlank(pharmacist.getId())) {
                criteria.andEqualTo("id",pharmacist.getId());
            }
            if(StringUtils.isNotBlank(pharmacist.getLoginName())) {
                criteria.andEqualTo("loginName",pharmacist.getLoginName());
            }
            PharmacistUser pharmacistInfo=pharmacistMapper.selectOneByExample(example);
            
            if (pharmacistInfo != null) {
                HospitalPharmacistUser entity = new HospitalPharmacistUser();
                entity.setPharmacistId(pharmacistInfo.getId());
                List<HospitalPharmacistUser> list = hospitalPharmacistUserService.queryList(entity);
                List<String> hospitalCodes = new ArrayList<String>();
                for (HospitalPharmacistUser hospitalCode : list) {
                    hospitalCodes.add(hospitalCode.getHospitalCode());
                }
                pharmacistInfo.setHospitalCodes(hospitalCodes);
            }
            
            return pharmacistInfo;
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("查询药房用户信息失败，获取数据异常！"+e.toString());
            return null; 
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteByIds(String[] ids) {
        String id = String.join("','", ids);
        try {
            hospitalPharmacistUserService.deleteByPharmacistIds(ids);
            int res = pharmacistMapper.deleteByIds("'" + id + "'");
            return res;
        } catch (Exception e) {
            logger.error("执行deleteByIds异常:::" + id);
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动回滚
            return 0;
        }
    }

    @Override
    public int deleteByBranchPharmacyIds(String[] branchPharmacyIds) {
        
        Example example=new Example(PharmacistUser.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andIn("pharmacyId", Arrays.asList(branchPharmacyIds));
        try {
            int res = pharmacistMapper.deleteByExample(example);
            return res;
        } catch (Exception e) {
            logger.error("执行deleteByBranchPharmacyIds异常:::" + branchPharmacyIds.toString());
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<PharmacistUser> queryList(String name, Integer page,Integer pageSize) {
        try {
            PageHelper.startPage(page, pageSize);
            Example example=new Example(PharmacistUser.class);
            Example.Criteria criteria=example.createCriteria();
            //拼接查询条件
            if(StringUtils.isNotBlank(name)) {
                criteria.andLike("name", name);
            }
            criteria.andEqualTo("status", 1);
            List<PharmacistUser> pharmacyUsers = pharmacistMapper.selectByExample(example);
            for(PharmacistUser pharmacistUser: pharmacyUsers) {
                List <String> hospitalCodes = new ArrayList<String>();
                HospitalPharmacistUser hospitalPharmacistUser = new HospitalPharmacistUser();
                hospitalPharmacistUser.setPharmacistId(pharmacistUser.getId());;
                List <HospitalPharmacistUser> list = hospitalPharmacistUserService.queryList(hospitalPharmacistUser);
                for(HospitalPharmacistUser entity:list) {
                    hospitalCodes.add(entity.getHospitalCode());
                }
                pharmacistUser.setHospitalCodes(hospitalCodes);
            }
            
            return pharmacyUsers;
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("查询药房用户信息失败,查询数据异常！"+e.toString());
            return new ArrayList<PharmacistUser>();
        }
    }

    @Override
    public int updateStatus(PharmacistUser pharmacist) {
        int res = 0;
        try {
         res = pharmacistMapper.updateByPrimaryKeySelective(pharmacist);
        }catch (Exception e) {
            logger.error("updateStatus异常==={}", pharmacist.getId());
            e.printStackTrace();
            return 0;
        }
        return res;
    }
    
}
