/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月23日
 * Created by ckm
 */
package com.ctb.platform.rest.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ctb.commons.entity.PaySettings;
import com.ctb.commons.entity.Pharmacy;
import com.ctb.commons.entity.PharmacyPaySettings;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.platform.rest.service.PaySettingsService;
import com.ctb.platform.rest.service.PharmacyPaySettingsService;

/**
 * @ClassName: com.ctb.platform.rest.controller.PharmacyPaySettingsController
 * @Description: TODO(药房支付信息管理Controller)
 * @author ckm
 * @date 2019年3月23日 上午11:19:13
 */
@RefreshScope
@RestController
@RequestMapping(value = "/paySetting")
public class PharmacyPaySettingsController {
    
    private static Logger logger = LoggerFactory.getLogger(PharmacyPaySettingsController.class);
    @Autowired
    EurekaDiscoveryClient discoveryClient;
    @Autowired
    private PharmacyPaySettingsService pharmacyPaySettingsService;
    
    @Autowired
    private PaySettingsService paySettingsService;
    
    /**
     * 
     * @Title: save
     * @Description: TODO(保存药房支付信息)
     * @author ckm
     * @date 2019年3月25日 上午11:05:46
     * @param request
     * @param pharmacyId
     * @param appId
     * @param appSecret
     * @param mchId
     * @param payKey
     * @param certificatePath
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate")
    public RespBody save(HttpServletRequest request, @RequestParam(required = false, value = "id") String id,
            @RequestParam(required = true, value = "pharmacyId") String pharmacyId,
            // @RequestParam(required = true, value = "pharmacyCode") String pharmacyCode,
            @RequestParam(required = true, value = "appId") String appId,
            @RequestParam(required = true, value = "appSecret") String appSecret,
            @RequestParam(required = true, value = "mchId") String mchId,
            @RequestParam(required = true, value = "payKey") String payKey,
            @RequestParam(required = true, value = "certificatePath") String certificatePath,
            @RequestParam(required = false, value = "status") String status) {
        try {
            
            PharmacyPaySettings pps = new PharmacyPaySettings("", pharmacyId);
            PharmacyPaySettings ppsInfo = pharmacyPaySettingsService.queryOne(pps);
            if (ppsInfo != null) {// 存在'药房-支付配置'关系数据,调用update
                
                PaySettings paySettings= pharmacyPaySettingsService.queryPharmacyPaySettingsOne(ppsInfo);
                paySettings.setAppId(appId);
                paySettings.setAppSecret(appSecret);
                paySettings.setMchId(mchId);
                paySettings.setPayKey(payKey);
                paySettings.setCertificatePath(certificatePath);
                
                
                int res = pharmacyPaySettingsService.update(pharmacyId, paySettings);
                if (res == 1) {
                    return new RespBody(Status.OK, "修改药房支付信息成功！");
                } else {
                    return new RespBody(Status.PROMPT, "修改药房支付信息失败！");
                }
                
            } else {// 不存在'药房-支付配置关系记录'，调用save
                PaySettings paySettings = new PaySettings(PKGenerator.generateId(), appId, appSecret, mchId, payKey,
                        certificatePath);
                paySettings.setCt(new Date());
                paySettings.setCp("");
                paySettings.setEp("");
                paySettings.setEt(new Date());
                PharmacyPaySettings pharmacyPaySettings = new PharmacyPaySettings(PKGenerator.generateId(),
                        paySettings.getId(), pharmacyId, /* pharmacyCode, */ "1");
                int res = pharmacyPaySettingsService.save(pharmacyPaySettings, paySettings);
                if (res == 2) {
                    return new RespBody(Status.OK, paySettings);
                } else {
                    return new RespBody(Status.PROMPT, "保存药房支付信息失败！");
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("保存药方支付信息失败，数据保存异常！");
            return new RespBody(Status.ERROR, "保存药房支付信息失败，数据保存异常！");
        }
    }
    
    @ResponseBody
    @RequestMapping(value = "/queryPharmacyPaySettings")
    public RespBody queryPharmacyPaySettings(@RequestParam(required = true) String pharmacyId) {
        try {
            PharmacyPaySettings pharmacyPaySettings = new PharmacyPaySettings();
            pharmacyPaySettings.setPharmacyId(pharmacyId);
            PaySettings paySettings = pharmacyPaySettingsService.queryPharmacyPaySettingsOne(pharmacyPaySettings);
            return new RespBody(Status.OK, paySettings);
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("查询支付配置信息失败，查询异常！" + e.toString());
            return new RespBody(Status.ERROR, "查询支付配置信息失败，查询异常！");
        }
    }
}
