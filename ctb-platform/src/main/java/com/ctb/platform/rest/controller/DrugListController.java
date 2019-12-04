/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月22日
 * Created by hhy
 */
package com.ctb.platform.rest.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.DrugList;
import com.ctb.commons.entity.Pharmacy;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.framework.commons.utils.PinyinUtils;
import com.ctb.platform.rest.service.DrugListService;
import com.ctb.platform.rest.service.impl.DrugListServiceImpl;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: com.ctb.platform.rest.controller.DrugListController
 * @Description: 药品白名单管理controller
 * @author hhy
 * @date 2019年3月22日 下午4:04:26
 */

@RefreshScope
@RestController
@RequestMapping("/drugList")
public class DrugListController {
    
    private static Logger logger = LoggerFactory.getLogger(DrugListController.class);
    
    @Autowired
    private DrugListService drugListService;
    
    /**
     * 
     * @Title: list
     * @Description: 获取药品白名单列表
     * @author hhy
     * @date 2019年3月25日 上午10:47:02
     * @param hospitalId
     * @param drugName
     * @param drugCode
     * @param hisDrugCode
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list")
    public RespBody list(
//    		@RequestParam(required = true, value = "hospitalId") String hospitalId,
//             @RequestParam(required=false, value="hospitalCode") String hospitalCode,
            @RequestParam(required = false, value = "drugName", defaultValue = "") String drugName,
            @RequestParam(required = false, value = "drugCode", defaultValue = "") String drugCode,
            @RequestParam(required = false, value = "cadn", defaultValue = "") String cadn,
//            @RequestParam(required = false, value = "hisDrugCode", defaultValue = "") String hisDrugCode,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        
        try {
            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("hospitalId", hospitalId);
//            map.put("hospitalCode", hospitalCode);
            map.put("drugName", drugName);
            map.put("drugCode", drugCode);
//            map.put("hisDrugCode", hisDrugCode);
            map.put("cadn", cadn);
            
            List<DrugList> drugList = drugListService.queryDrugListPagedByExample(map, page, size);
            PageInfo<DrugList> pageInfo = new PageInfo<>(drugList);
            if (drugList == null) {
                return new RespBody(Status.ERROR, "获取药品名单列表失败");
            }
            Map<String, Object> resMap = new HashMap<String, Object>();
            resMap.put("total", pageInfo.getTotal());
            resMap.put("drugList", drugList);
            return new RespBody(Status.OK, resMap);
        } catch (Exception e) {
            logger.error("获取药品白名单列表失败");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "获取药品白名单列表失败");
        }
        
    }
    
    /**
     * 
     * @Title: save
     * @Description: 新增药品白名单
     * @author hhy
     * @date 2019年3月25日 下午3:43:27
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
    public RespBody save(
//    		@RequestParam(required = true, value = "hospitalId") String hospitalId,
//            @RequestParam(required=true, value="hospitalCode") String hospitalCode,
            @RequestParam(required = true, value = "drugName") String drugName,
            @RequestParam(required = true, value = "drugCode") String drugCode,
            @RequestParam(required = true, value = "cadn") String cadn,
            @RequestParam(required = true, value = "approvalNo") String approvalNo,
            @RequestParam(required = true, value = "manufacturer") String manufacturer,
            @RequestParam(required = true, value = "specification") String specification,
//            @RequestParam(required = true, value = "hisDrugCode") String hisDrugCode,
            @RequestParam(required = false, value = "usage", defaultValue = "") String usage) throws Exception {
        
        try {
            Map<String, Object> map = new HashMap<String, Object>();
//                map.put("hospitalId", hospitalId);
            map.put("drugName", drugName);
            map.put("drugCode", drugCode);
            map.put("cadn", cadn);
            map.put("approvalNo", approvalNo);
            map.put("manufacturer", manufacturer);
            map.put("specification", specification);
            map.put("usage", usage);
            
            //非空验证
            String message = isCheck(map);
            if(! message.equals("OK")) {
            	return new RespBody(Status.ERROR,message);
            }
            
            // 判断该药品是否已经存在
            List<DrugList> list = drugListService.queryDrugListByExample(map);
            if (list != null && list.size() > 0) {
                return new RespBody(Status.ERROR, "添加药品名单失败，该药品已存在");
            }
            
            DrugList drugList = new DrugList();
            String id = PKGenerator.generateId();
            drugList.setId(id);
//            drugList.setHospitalId(hospitalId);
//            drugList.setHospitalCode(hospitalCode);
//            drugList.setHisDrugCode(hisDrugCode);
            drugList.setDrugCode(drugCode);
            drugList.setDrugName(drugName);
            drugList.setCadn(cadn);
            drugList.setApprovalNo(approvalNo);
            drugList.setManufacturer(manufacturer);
            drugList.setSpecification(specification);
            drugList.setUsage(usage);
            drugList.setCt(new Date());
            drugList.setEt(new Date());
            drugList.setCp("");
            drugList.setEp("");
            int res = drugListService.saveDrugList(drugList);
            if (res == 1) {
                return new RespBody(Status.OK, "添加药品名单成功");
            } else {
                return new RespBody(Status.ERROR, "添加药品名单失败");
            }
        } catch (Exception e) {
            logger.error("添加药品名单失败");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "添加药品名单失败");
        }
        
    }
    
    /**
     * 
     * @Title: toDel
     * @Description: 删除药品白名单
     * @author hhy
     * @date 2019年3月25日 下午3:43:19
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toDel")
    public RespBody toDel(@RequestParam(required = true, value = "hospitalId") String hospitalId,
            @RequestParam(required = true, value = "hospitalCode") String hospitalCode,
            @RequestParam(required = true, value = "ids") String[] ids) throws Exception {
        try {
            int res = drugListService.deleteDrugList(ids, hospitalCode);
            if (res == ids.length) {
                return new RespBody(Status.OK, "删除药品白名单成功");
            } else {
                return new RespBody(Status.ERROR, "删除药品白名单失败");
            }
        } catch (Exception e) {
            logger.error("删除药品白名单失败");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "删除药品白名单失败");
        }
    }
    
    
    /**
     * 非空验证
     */
    public String isCheck(Map<String, Object> param) {
    	String  message = "OK";
    	
        if (StringUtils.isBlank(param.get("drugName").toString())) {
        	message = "添加药品名单失败，药品名称不能为空";
        	return message; 
        }
        if (StringUtils.isBlank(param.get("cadn").toString())) {
        	message = "添加药品名单失败，通用名称不能为空";
        	return message; 
        } 
        if (StringUtils.isBlank(param.get("approvalNo").toString())) {
        	message =  "添加药品名单失败，批准文号不能为空";
        	return message; 
        } 
        if (StringUtils.isBlank(param.get("manufacturer").toString())) {
        	message =  "添加药品名单失败，生产企业不能为空";
        	return message; 
        } 
        if (StringUtils.isBlank(param.get("specification").toString())) {
        	message =  "添加药品名单失败，规格不能为空";
        	return message; 
        } 
        if (StringUtils.isBlank(param.get("usage").toString())) {
        	message = "添加药品名单失败，用法用量不能为空";
        	return message; 
        }
        if (StringUtils.isBlank(param.get("drugCode").toString())) {
        	message = "添加药品名单失败，药品通用编码不能为空";
        	return message; 
        } 
		return message; 
    }
    
	@ResponseBody
	@RequestMapping(value = "/update")
	public RespBody update(@RequestParam(required = true) String id,
			@RequestParam(required = false, value = "drugName", defaultValue = "") String drugName,
			@RequestParam(required = false, value = "cadn", defaultValue = "") String cadn,
			@RequestParam(required = false, value = "approvalNo", defaultValue = "") String approvalNo,
			@RequestParam(required = false, value = "manufacturer", defaultValue = "") String manufacturer,
			@RequestParam(required = false, value = "specification", defaultValue = "") String specification,
			@RequestParam(required = false, value = "usage", defaultValue = "") String usage,
			@RequestParam(required = false, value = "drugCode", defaultValue = "") String drugCode,
			HttpServletRequest request) {// status=1-true,0-false
	    
        
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("drugName", drugName);
		map.put("drugCode", drugCode);
		map.put("cadn", cadn);
		map.put("approvalNo", approvalNo);
		map.put("manufacturer", manufacturer);
		map.put("specification", specification);
		map.put("usage", usage);
		//map.put("id", id);
		  
		//非空验证
		String message = isCheck(map);
		if(! message.equals("OK")) {
			return new RespBody(Status.ERROR,message);
		}
		
        // 判断该药品是否已经存在
        List<DrugList> list = drugListService.queryDrugListByExample(map);
        if (list != null && list.size() > 0) {
            return new RespBody(Status.ERROR, "修改药品名单失败，该药品已存在");
        }
		
		DrugList drugList = new DrugList(id,drugName,drugCode,cadn,approvalNo,manufacturer,specification,usage);
		try {
			drugList.setEt(new Date());
			int res = drugListService.updateDrugList(drugList);
			if (res == 1) {
				return new RespBody(Status.OK, "修改药房信息成功！");
			} else {
				return new RespBody(Status.ERROR, "修改药房信息失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改药房信息失败：" + e.toString());
			return new RespBody(Status.ERROR, "修改药房信息失败，数据保存异常！");
		}
	}
	
}
