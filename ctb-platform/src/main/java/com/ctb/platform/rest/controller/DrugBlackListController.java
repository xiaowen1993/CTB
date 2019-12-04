/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月23日
 * Created by hhy
 */
package com.ctb.platform.rest.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ctb.commons.entity.DrugBlackList;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.platform.rest.service.DrugBlackListService;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: com.ctb.platform.rest.controller.DrugBlackListController
 * @Description: 药品黑名单管理controller
 * @author hhy
 * @date 2019年3月23日 上午10:04:02
 */

@RefreshScope
@RestController
@RequestMapping("/drugBlackList")
public class DrugBlackListController {
    
    private static Logger logger = LoggerFactory.getLogger(DrugBlackListController.class);
    
    @Autowired
    private DrugBlackListService drugBlackListService;
    
    /**
     * 
     * @Title: list
     * @Description: 获取药品黑名单
     * @author hhy
     * @date 2019年3月25日 上午10:19:25
     * @param hospitalId
     *            必选
     * @param drugName
     * @param drugCode
     * @param hisDrugCode
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list")
    public RespBody list(@RequestParam(required = true, value = "hospitalId") String hospitalId,
            // @RequestParam(required=false, value="hospitalCode") String hospitalCode,
            @RequestParam(required = false, value = "drugName", defaultValue = "") String drugName,
            @RequestParam(required = false, value = "drugCode", defaultValue = "") String drugCode,
            @RequestParam(required = false, value = "cadn", defaultValue = "") String cadn,
            @RequestParam(required = false, value = "hisDrugCode", defaultValue = "") String hisDrugCode,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("hospitalId", hospitalId);
            // map.put("hospitalCode", hospitalCode);
            map.put("drugName", drugName);
            map.put("drugCode", drugCode);
            map.put("hisDrugCode", hisDrugCode);
            map.put("cadn", cadn);
            
            List<DrugBlackList> drugBlackList = drugBlackListService.queryDrugBlackListPagedByExample(map, page, size);
            PageInfo<DrugBlackList> pageInfo = new PageInfo<>(drugBlackList);
            if (drugBlackList == null) {
                return new RespBody(Status.ERROR, "获取药品黑名单列表失败");
            }
            
            Map<String, Object> resMap = new HashMap<String, Object>();
            resMap.put("total", pageInfo.getTotal());
            resMap.put("drugBlackList", drugBlackList);
            return new RespBody(Status.OK, resMap);
        } catch (Exception e) {
            logger.error("获取药品黑名单列表失败");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "获取药品黑名单列表失败");
        }
        
    }
    
    /**
     * 
     * @Title: save
     * @Description: 新增药品黑名单
     * @author hhy
     * @date 2019年3月25日 下午3:43:04
     * @param hospitalId
     * @param drugName
     * @param drugCode
     * @param cadn
     * @param approvalNo
     * @param manufacturer
     * @param specification
     * @param hisDrugCode
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public RespBody save(@RequestParam(required = true, value = "hospitalId") String hospitalId,
            // @RequestParam(required=true, value="hospitalCode") String hospitalCode,
            @RequestParam(required = true, value = "drugName") String drugName,
            @RequestParam(required = true, value = "drugCode") String drugCode,
            @RequestParam(required = true, value = "cadn") String cadn,
            @RequestParam(required = true, value = "approvalNo") String approvalNo,
            @RequestParam(required = true, value = "manufacturer") String manufacturer,
            @RequestParam(required = true, value = "specification") String specification,
            @RequestParam(required = true, value = "hisDrugCode") String hisDrugCode,
            @RequestParam(required = false, value = "usage", defaultValue = "") String usage) throws Exception {
        
        try {
            if (StringUtils.isBlank(drugCode)) {
                new RespBody(Status.ERROR, "添加药品黑名单失败，药品通用编码不能为空");
            } else {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("hospitalId", hospitalId);
                map.put("drugCode", drugCode);
                // 判断该药品是否已经存在
                List<DrugBlackList> list = drugBlackListService.queryDrugBlackListByExample(map);
                if (list != null && list.size() > 0) {
                    return new RespBody(Status.ERROR, "添加药品黑名单失败，该药品已存在");
                }
            }
            
            DrugBlackList drugBlackList = new DrugBlackList();
            String id = PKGenerator.generateId();
            drugBlackList.setId(id);
            drugBlackList.setHospitalId(hospitalId);
            // drugBlackList.setHospitalCode(hospitalCode);
            drugBlackList.setHisDrugCode(hisDrugCode);
            drugBlackList.setDrugCode(drugCode);
            drugBlackList.setDrugName(drugName);
            drugBlackList.setCadn(cadn);
            drugBlackList.setApprovalNo(approvalNo);
            drugBlackList.setManufacturer(manufacturer);
            drugBlackList.setSpecification(specification);
            drugBlackList.setUsage(usage);
            drugBlackList.setCt(new Date());
            drugBlackList.setEt(new Date());
            drugBlackList.setCp("");
            drugBlackList.setEp("");
            
            int res = drugBlackListService.saveDrugBlackList(drugBlackList);
            if (res == 1) {
                return new RespBody(Status.OK, "添加药品黑名单成功");
            } else {
                return new RespBody(Status.ERROR, "添加药品黑名单失败");
            }
        } catch (Exception e) {
            logger.error("添加药品黑名单失败");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "添加药品黑名单失败");
        }
        
    }
    
    /**
     * 
     * @Title: toDel
     * @Description: 删除药品黑名单
     * @author hhy
     * @date 2019年3月25日 下午3:43:12
     * @param drugBlackListId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toDel")
    public RespBody toDel(@RequestParam(required = true, value = "hospitalId") String hospitalId,
            @RequestParam(required = true, value = "hospitalCode") String hospitalCode,
            @RequestParam(required = true, value = "ids") String[] ids) throws Exception {
        
        try {
            int res = drugBlackListService.deleteDrugBlackList(ids, hospitalCode);
            if (res == ids.length) {
                return new RespBody(Status.OK, "删除药品黑名单成功");
            } else {
                return new RespBody(Status.ERROR, "删除药品黑名单失败");
            }
        } catch (Exception e) {
            logger.error("删除药品黑名单失败");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "删除药品黑名单失败");
        }
    }
    
}
