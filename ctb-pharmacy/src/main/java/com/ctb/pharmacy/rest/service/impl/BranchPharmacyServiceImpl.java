/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年6月19日
 * Created by hhy
 */
package com.ctb.pharmacy.rest.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctb.commons.entity.BranchPharmacy;
import com.ctb.commons.entity.Hospital;
import com.ctb.pharmacy.rest.service.BranchPharmacyService;
import com.ctb.resources.mapper.biz.BranchPharmacyMapper;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @ClassName: com.ctb.pharmacy.rest.service.impl.BranchPharmacyServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年6月19日 下午6:39:21
 */

@Service
public class BranchPharmacyServiceImpl implements BranchPharmacyService{

    @Autowired
    private BranchPharmacyMapper branchPharmacyMapper;
    
    @Override
    public BranchPharmacy queryBranchPharmacy(BranchPharmacy branchPharmacy) {
        // TODO Auto-generated method stub
        Example example = new Example(BranchPharmacy.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(branchPharmacy.getId())) {
            criteria.andEqualTo("id", branchPharmacy.getId());
        }
        if (StringUtils.isNotBlank(branchPharmacy.getPharmacyId())) {
            criteria.andEqualTo("pharmacyId", branchPharmacy.getPharmacyId());
        }
        if (StringUtils.isNotBlank(branchPharmacy.getName())) {
            criteria.andEqualTo("name", branchPharmacy.getName());
        }
        if (StringUtils.isNotBlank(branchPharmacy.getCode())) {
            criteria.andEqualTo("code", branchPharmacy.getCode());
        }
        if (StringUtils.isNotBlank(branchPharmacy.getAddress())) {
            criteria.andEqualTo("address", branchPharmacy.getAddress());
        }
        if (StringUtils.isNotBlank(String.valueOf(branchPharmacy.getStatus()))) {
            criteria.andEqualTo("status", branchPharmacy.getStatus());
        }
        
        BranchPharmacy branchPharmacyInfo = branchPharmacyMapper.selectOneByExample(example);
        return branchPharmacyInfo;
    }

    @Override
    public List<BranchPharmacy> branchPharmacys() {
        
        Example example = new Example(BranchPharmacy.class);
        Criteria criteria = example.createCriteria();
        example.setOrderByClause("CT DESC");
        criteria.andEqualTo("status", 1);
        return branchPharmacyMapper.selectByExample(example);
    }
    
}
