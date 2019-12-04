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

import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.entity.DrugList;
import com.ctb.commons.entity.Hospital;
import com.ctb.commons.entity.HospitalDrug;
import com.ctb.commons.entity.Pharmacy;
import com.ctb.commons.entity.PharmacyUser;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.platform.rest.service.HospitalDrugService;
import com.ctb.platform.rest.service.impl.HospitalDrugServiceImpl;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: com.ctb.platform.rest.controller.HospitalDrugController
 * @Description: 医院药品名单管理controller
 * @author Qin
 * @date 2019年11月4日 
 */

@RefreshScope
@RestController
@RequestMapping("/hospitalDrug")
public class HospitalDrugController {
    private static Logger logger = LoggerFactory.getLogger(HospitalDrugController.class);
    
    @Autowired
    private HospitalDrugServiceImpl hospitalDrugServiceImpl;
    
    /**
     * 
     * @Title: list
     * @Description: 获取医院药品名单列表
     * @author Qin
     * @date 2019年11月4日 
     * @param drugName
     * @param drugCode
     * @param hisDrugCode
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/list")
    public RespBody list(
            @RequestParam(required = false, value = "hospitalName", defaultValue = "") String hospitalName,
            @RequestParam(required = false, value = "drugName", defaultValue = "") String drugName,
            @RequestParam(required = false, value = "hisDrugCode", defaultValue = "") String hisDrugCode,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
	        try {
	            Map<String, Object> map = new HashMap<String, Object>();
	            map.put("hospitalName", hospitalName);
	            map.put("drugName", drugName);
	            map.put("hisDrugCode", hisDrugCode);
	            map.put("page", (page-1)*size);
	            map.put("pageSize", size);
	            
	            List<HospitalDrug> hospitalDrugList = hospitalDrugServiceImpl.queryHospitalDurgLists(map);
//	            PageInfo<HospitalDrug> pageInfo = new PageInfo<>(hospitalDrugList);
//	            if (hospitalDrugList == null) {
//	                return new RespBody(Status.ERROR, "获取药品名单列表失败");
//	            }
	            //获取总记录数
	            int total = hospitalDrugServiceImpl.queryHospitalDurgCount(map);
	            Map<String, Object> resMap = new HashMap<String, Object>();
	            resMap.put("total", total);
	            resMap.put("hospitalDrugList", hospitalDrugList);
	            return new RespBody(Status.OK, resMap);
	        }catch(Exception e) {
	            logger.error("获取医院药品名单列表失败");
	            e.printStackTrace();
	            return new RespBody(Status.ERROR, "获取医院药品名单列表失败");
	        }
    }
    
    
    /**
     * 
     * @Title: save
     * @Description: 新增医院药品
     * @author Qin
     * @date 2019年11月6日
     * @param hospitalId
     * @param drugId
     * @param type
     * @param hisDrugCode
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public RespBody save(
            @RequestParam(required = true, value = "hospitalId") String hospitalId,
            @RequestParam(required = true, value = "drugId") String drugId,
            @RequestParam(required = true, value = "hisDrugCode") String hisDrugCode,
            @RequestParam(required = false, value = "type") String type,
            @RequestParam(required = false, value = "specialDrugs") String specialDrugs) throws Exception {
        
        try {
            Map<String, Object> map = new HashMap<String, Object>();
//                map.put("hospitalId", hospitalId);
            map.put("hospitalId", hospitalId);
            map.put("drugId", drugId);
            map.put("hisDrugCode", hisDrugCode);
            map.put("type", type);
            map.put("specialDrugs", specialDrugs);
            
            //非空验证
            String message = isCheck(map);
            if(! message.equals("OK")) {
            	return new RespBody(Status.ERROR,message);
            }
            
            // 判断该药品是否已经存在
            int num = hospitalDrugServiceImpl.queryHospitalDurg(map);
            if (num > 0) {
                return new RespBody(Status.ERROR, "添加药品名单失败，该药品已存在");
            }
            
            HospitalDrug hospitalDrug = new HospitalDrug();
            String id = PKGenerator.generateId();
            hospitalDrug.setId(id);
            hospitalDrug.setHospitalId(hospitalId);
            hospitalDrug.setDrugId(drugId);
            hospitalDrug.setHisDrugCode(hisDrugCode);
            hospitalDrug.setType(type);
            hospitalDrug.setSpecialDrugs(specialDrugs);

            int res = hospitalDrugServiceImpl.saveHospitalDrug(hospitalDrug);
            if (res == 1) {
                return new RespBody(Status.OK, "添加医院药品名单成功");
            } else {
                return new RespBody(Status.ERROR, "添加医院药品名单失败");
            }
        } catch (Exception e) {
            logger.error("添加医院药品名单失败");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "添加医院药品名单失败");
        }
    }
    
    /**
     * 非空验证
     */
    public String isCheck(Map<String, Object> param) {
    	String  message = "OK";
    	
        if (StringUtils.isBlank(param.get("hospitalId").toString())) {
        	message = "添加医院药品名单失败，医院名称不能为空";
        	return message; 
        }
        if (StringUtils.isBlank(param.get("drugId").toString())) {
        	message = "添加医院药品名单失败，药品名称不能为空";
        	return message; 
        } 
        if (StringUtils.isBlank(param.get("hisDrugCode").toString())) {
        	message =  "添加医院药品名单失败，医院自编码不能为空";
        	return message; 
        } 
        if (StringUtils.isBlank(param.get("type").toString())) {
        	message =  "添加医院药品名单失败，类型不能为空";
        	return message; 
        } 
		return message; 
    }
    
    @ResponseBody
	@RequestMapping(value = "/update")
	public RespBody update(@RequestParam(required = true) String id,
            @RequestParam(required = true, value = "hospitalId") String hospitalId,
            @RequestParam(required = true, value = "drugId") String drugId,
            @RequestParam(required = true, value = "hisDrugCode") String hisDrugCode,
            @RequestParam(required = false, value = "type") String type,
            @RequestParam(required = false, value = "specialDrugs") String specialDrugs) throws Exception {
	    
        
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("id", id);
        map.put("hospitalId", hospitalId);
        map.put("drugId", drugId);
        map.put("hisDrugCode", hisDrugCode);
        map.put("type", type);
        map.put("specialDrugs", specialDrugs);
		  
		//非空验证
		String message = isCheck(map);
		if(! message.equals("OK")) {
			return new RespBody(Status.ERROR,message);
		}
		
        // 判断该药品是否已经存在
        int num = hospitalDrugServiceImpl.queryHospitalDurg(map);
        if (num > 0) {
            return new RespBody(Status.ERROR, "修改失败，该药品已存在");
        }
        
        HospitalDrug hospitalDrug = new HospitalDrug();
        hospitalDrug.setId(id);
        hospitalDrug.setHospitalId(hospitalId);
        hospitalDrug.setDrugId(drugId);
        hospitalDrug.setHisDrugCode(hisDrugCode);
        hospitalDrug.setType(type);
        hospitalDrug.setSpecialDrugs(specialDrugs);
		
		try {
			int res = hospitalDrugServiceImpl.updateHospitalDrugList(hospitalDrug);
			if (res == 1) {
				return new RespBody(Status.OK, "修改信息成功！");
			} else {
				return new RespBody(Status.ERROR, "修改信息失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改药房信息失败：" + e.toString());
			return new RespBody(Status.ERROR, "修改信息失败，数据保存异常！");
		}
    }
    
    @RequestMapping(value = "/editStatus")
    public RespBody editStatus(
            @RequestParam(required=true, value="id") String id,
            @RequestParam(required=false, value = "status") String status,
            @RequestParam(required=false, value = "specialStatus") String specialStatus) {
        
        try {
            if (StringUtils.isBlank(id)) {
                return new RespBody(Status.ERROR, "修改失败ID不为空");
            } else {
//            	HospitalDrug hospitalDrug = new HospitalDrug();
//            	hospitalDrug.setId(id);
        		Map<String, Object> map = new HashMap<String, Object>();
        		map.put("id", id);
            	int num = hospitalDrugServiceImpl.queryHospitalDurgCount(map);
                if (num == 0) {
                    return new RespBody(Status.ERROR, "修改状态失败,ID不存在");
                }
                
                HospitalDrug hospitalDrugs = new HospitalDrug();
                if(StringUtils.isNotEmpty(status)) {
                	hospitalDrugs.setStatus(status);
                }
                if(StringUtils.isNotEmpty(specialStatus)) {
                	hospitalDrugs.setSpecialStatus(specialStatus);
                }
                hospitalDrugs.setId(id);
                
                int res = hospitalDrugServiceImpl.updateHospitalDrugList(hospitalDrugs);
                if (res == 1) {
                    return new RespBody(Status.OK, "修改状态成功");
                }
                return new RespBody(Status.ERROR, "修改状态失败");
            }
            
        } catch (Exception e) {
            logger.error("修改异常:::");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "修改异常");
        }
    }
}
