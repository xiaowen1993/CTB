/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年7月2日
 * Created by hhy
 */
package com.ctb.pharmacy.runnable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.ctb.commons.entity.BranchPharmacy;
import com.ctb.commons.entity.PharmacyUser;
import com.ctb.commons.entity.PrescriptionRecord;
import com.ctb.framework.commons.utils.SpringContextHolder;
import com.ctb.pharmacy.common.javaMail.SimpleMailSender;
import com.ctb.pharmacy.rest.entity.RecordEntity;
import com.ctb.pharmacy.rest.service.BranchPharmacyService;
import com.ctb.pharmacy.rest.service.PharmacyUserService;
import com.github.pagehelper.util.StringUtil;


/**
 * @ClassName: com.ctb.pharmacy.runnable.SendEmailRunnable
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年7月2日 下午3:32:11
 */

public class SendEmailRunnable implements Runnable{
    private Logger logger = LoggerFactory.getLogger(SendEmailRunnable.class);

    private PharmacyUserService pharmacyUserService = SpringContextHolder.getBean(PharmacyUserService.class);
    
    private BranchPharmacyService branchPharmacyService = SpringContextHolder.getBean(BranchPharmacyService.class);
    
    List <PrescriptionRecord> list = null;
    
    private String branchPharmacyCode=null;
    
    public SendEmailRunnable() {
        super();
    }
    
    public SendEmailRunnable(List<PrescriptionRecord> list,String branchPharmacyCode) {
        super();
        this.list = list;
        this.branchPharmacyCode = branchPharmacyCode;
    }



    @Override
    public void run() {
        // TODO Auto-generated method stub
        //通过
        //
        // 拿到后发送邮件
        BranchPharmacy branchPharmacyEntity = new BranchPharmacy();
        branchPharmacyEntity.setCode(branchPharmacyCode);
        BranchPharmacy branchPharmacy = branchPharmacyService.queryBranchPharmacy(branchPharmacyEntity);
        PharmacyUser user =  pharmacyUserService.queryByPharmacyId(branchPharmacy.getId());
        String toAddress = user.getEmail();
        
        //String toAddress = "jiekoujk@yx129.com"5d2frjHZyxKVB7ci;
        //251106859@qq.com   125996270@qq.com
        //toAddress ="125996270@qq.com";
        
        //toAddress ="huanghy@yx129.com";
        String subject = "处方订单下载";
        String content ="请下载附件";
        Date sendDate = new Date();
        
        if(StringUtil.isEmpty(toAddress)) {
            logger.info("==========发送的目标邮件为空");
        }
        
        List <RecordEntity> entitys = new ArrayList<RecordEntity>();
        for(PrescriptionRecord record:list) {
            RecordEntity entity = new RecordEntity();
            try {
                //BeanUtils.copyProperty(entity, record, getNullPropertyNames(entity));
                copyProperties(entity, record);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //entity.setMobile("1333599");
            entitys.add(entity);
        }
        
        SimpleMailSender.sendMail(toAddress, subject, content, sendDate, entitys);
        
    }
    
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
    
    private static void copyProperties(RecordEntity entity, PrescriptionRecord record) throws IllegalAccessException, InvocationTargetException {
        
        BeanUtils.copyProperties(entity, record);
        entity.setUpdateTime(record.getUpdateTime());
        entity.setCreateTime(record.getCreateTime());
        entity.setHandleCount(record.getHandleCount());
        entity.setIsHandleSuccess(record.getIsHandleSuccess());
        entity.setIsException(record.getIsException());
        entity.setReviewTime(record.getReviewTime());
        entity.setReviewStatus(record.getReviewStatus());
        entity.setReviewPhysiciansId(record.getReviewPhysiciansId());
        entity.setIsHadCallBack(record.getIsHadCallBack());
        entity.setOrderNoHashVal(record.getOrderNoHashVal());
        entity.setPharmacyStatus(record.getPharmacyStatus());
        entity.setPrescriptionStatus(record.getPrescriptionStatus());
        entity.setPayStatus(record.getPayStatus());
        entity.setRefundTime(record.getRefundTime());
        entity.setPayTime(record.getPayTime());
    }

}
