package com.ctb.platform.rest.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
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
import com.ctb.commons.entity.Hospital;
import com.ctb.commons.entity.Pharmacy;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.framework.commons.utils.PinyinUtils;
import com.ctb.platform.rest.service.HospitalService;
import com.ctb.resources.mapper.biz.HospitalMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @ClassName: com.ctb.platform.rest.controller.HospitalController
 * @Description: 医院管理controller
 * @author hhy
 * @date 2019年3月22日 下午4:04:48
 */
@RefreshScope
@RestController
@RequestMapping("/hospital")
public class HospitalController {
    
    private static Logger logger = LoggerFactory.getLogger(HospitalController.class);
    
    @Autowired
    private HospitalService hospitalService;
    
    /**
     * 
     * @Title: hospitalList
     * @Description: 获取医院列表
     * @author hhy
     * @date 2019年3月25日 上午9:14:35
     * @param code
     *            通过code查询 非必选 默认值 空字符串
     * @param name
     *            通过name查询 非必选 默认值 空字符串
     * @param page
     *            页数从0开始 默认为0
     * @param size
     *            每页数量 默认10
     * @return
     */
    @RequestMapping(value = "/list")
    public RespBody hospitalList(@RequestParam(required = false, value = "code", defaultValue = "") String code,
            @RequestParam(required = false, value = "name", defaultValue = "") String name,
            @RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
            @RequestParam(required = false, value = "size", defaultValue = "10") Integer size) {
        
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("status", 1);
            map.put("code", code);
            map.put("name", name);
            List<Hospital> hospitalList = hospitalService.queryHospitalListPagedByExample(map, page, size);
            PageInfo<Hospital> pageInfo = new PageInfo<>(hospitalList);
            
            if (hospitalList == null) {
                return new RespBody(Status.ERROR, "获取医院列表失败");
            }
            
            Map<String, Object> resMap = new HashMap<String, Object>();
            resMap.put("total", pageInfo.getTotal());
            resMap.put("hospitalList", hospitalList);
            return new RespBody(Status.OK, resMap);
        } catch (Exception e) {
            logger.error("获取医院列表失败:::");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "获取医院列表失败");
        }
    }
    
    
    @RequestMapping(value = "/all")
    public RespBody all() {
        
        try {
            List <Hospital> list = hospitalService.getAll();
            if (CollectionUtils.isEmpty(list)) {
                return new RespBody(Status.PROMPT, "暂无数据");
            }
            return new RespBody(Status.OK, list);
        } catch (Exception e) {
            logger.error("获取所有医院失败:::");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "获取所有医院失败");
        }
    }
    
    /**
     * 
     * @Title: getHospital
     * @Description: 根据医院ID获取医院信息
     * @author hhy
     * @date 2019年3月23日 上午11:45:40
     * @param hospitalId
     * @return
     */
    @RequestMapping(value = "/getHospital")
    public RespBody getHospital(String hospitalId) {
        
        try {
            Hospital hospital = hospitalService.queryHospitalById(hospitalId);
            if (hospital == null) {
                return new RespBody(Status.ERROR, "获取医院详情失败");
            }
            return new RespBody(Status.OK, hospital);
        } catch (Exception e) {
            logger.error("获取医院信息失败:::");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "获取医院详情失败");
        }
    }
    
    /**
     * 
     * @Title: save
     * @Description: 添加医院
     * @author hhy
     * @date 2019年3月25日 上午10:42:53
     * @param code
     * @param name
     * @param branchCode
     * @param branchName
     * @param contactName
     * @param contactTel
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public RespBody save(
            // @RequestParam(required=true, value="code") String code,
            @RequestParam(required = true, value = "name") String name,
            @RequestParam(required = false, value = "appId", defaultValue = "") String appId,
            @RequestParam(required = false, value = "appSecret", defaultValue = "") String appSecret,
            @RequestParam(required = true, value = "branchCode") String branchCode,
            @RequestParam(required = true, value = "branchName") String branchName,
            @RequestParam(required = false, value = "contactName") String contactName,
            @RequestParam(required = false, value = "contactTel") String contactTel,
            @RequestParam(required = false, value = "pharmacyIds") String[] pharmacyIds) throws Exception {
        
        try {
            
            String code = PinyinUtils.getChineseFirstWord(name);
            // 添加前先判断该code是否已存在 报错或者有对象时都不为null
            if (StringUtils.isBlank(code)) {
                return new RespBody(Status.ERROR, "新增医院失败,医院编码不能为空");
            } else {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("code", code);
                List<Hospital> list = hospitalService.queryHospitalListByExample(map);
                if (list != null && list.size() > 0) {
                    return new RespBody(Status.ERROR, "新增医院失败,该医院编码已存在");
                }
            }
            
            Hospital hospital = new Hospital();
            String id = PKGenerator.generateId();
            hospital.setId(id);
            hospital.setCode(code);
            hospital.setName(name);
            hospital.setBranchCode(branchCode);
            hospital.setBranchName(branchName);
            hospital.setContactName(contactName);
            hospital.setContactTel(contactTel);
            hospital.setStatus("1");// 新建医院默认为启用状态
            hospital.setCt(new Date());
            hospital.setEt(new Date());
            hospital.setEp("");
            hospital.setCp("");
            hospital.setAppId(appId);
            hospital.setAppSecret(appSecret);
            
            List<String> list = new ArrayList<String>();
            if (pharmacyIds != null && pharmacyIds.length > 0) {
                list = Arrays.asList(pharmacyIds);
            }
            hospital.setPharmacyIds(list);
            int res = hospitalService.saveHospital(hospital);
            
            if (res == 1) {
                return new RespBody(Status.OK, "新增医院成功");
            } else {
                return new RespBody(Status.ERROR, "新增医院失败");
            }
        } catch (Exception e) {
            logger.error("新增医院失败:::");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "新增医院失败");
        }
        
    }
    
    /**
     * 
     * @Title: toEdit
     * @Description: 修改医院信息
     * @author hhy
     * @date 2019年3月25日 上午10:45:05
     * @param hospitalId
     * @param code
     * @param name
     * @param branchCode
     * @param branchName
     * @param contactName
     * @param contactTel
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/toEdit")
    public RespBody toEdit(@RequestParam(required = true, value = "id") String id,
//            @RequestParam(required = true, value = "code") String code,
            @RequestParam(required = false, value = "appId", defaultValue = "") String appId,
            @RequestParam(required = false, value = "appSecret", defaultValue = "") String appSecret,
            @RequestParam(required = true, value = "name") String name,
            @RequestParam(required = true, value = "branchCode") String branchCode,
            @RequestParam(required = true, value = "branchName") String branchName,
            @RequestParam(required = false, value = "contactName") String contactName,
            @RequestParam(required = false, value = "contactTel") String contactTel,
            @RequestParam(required = false, value = "pharmacyIds") String[] pharmacyIds) throws Exception {
        
        try {
            
            String code = PinyinUtils.getChineseFirstWord(name);
            Hospital hospital = hospitalService.queryHospitalById(id);
            if (hospital == null) {
                return new RespBody(Status.ERROR, "修改医院失败，该医院不存在");
            }
            hospital.setCode(code);
            hospital.setName(name);
            hospital.setBranchCode(branchCode);
            hospital.setBranchName(branchName);
            hospital.setContactName(contactName);
            hospital.setContactTel(contactTel);
            hospital.setEt(new Date());
            hospital.setEp("");
            hospital.setAppId(appId);
            hospital.setAppSecret(appSecret);
            
            List<String> list = new ArrayList<String>();
            if (pharmacyIds != null && pharmacyIds.length > 0) {
                list = Arrays.asList(pharmacyIds);
            }
            hospital.setPharmacyIds(list);
            int res = hospitalService.updateHospital(hospital);
            if (res == 1) {
                return new RespBody(Status.OK, "修改医院成功");
            } else {
                return new RespBody(Status.ERROR, "修改医院失败");
            }
        } catch (Exception e) {
            logger.error("修改医院信息失败:::");
            e.printStackTrace();
            return new RespBody(Status.ERROR, "修改医院失败");
        }
    }
    
    
	/**
	 * 
	 * @Title: List
	 * @Description: TODO(获取医院列表)
	 * @author Qin
	 * @date 2019年11月6日 
	 * @param name
	 * @param code
	 * @param contactName
	 * @param contactTel
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/hospitalAll")
	public RespBody all(@RequestParam(required = false, value = "name", defaultValue = "") String name,
			@RequestParam(required = false, value = "code", defaultValue = "") String code,
			@RequestParam(required = false, value = "contactName", defaultValue = "") String contactName,
			@RequestParam(required = false, value = "contactTel", defaultValue = "") String contactTel,
			@RequestParam(required = false, value = "searchKey", defaultValue = "") String searchKey) {
		try {
			Hospital hospital = new Hospital(name, code, contactName, contactTel);
			List<Hospital> hospitals = hospitalService.queryHospitalList(hospital, searchKey);
			PageInfo<Hospital> pageInfo = new PageInfo<Hospital>(hospitals);
			JSONObject object = new JSONObject();
			object.put("total", pageInfo.getTotal());
			object.put("hospitalList", hospitals);
			return new RespBody(Status.OK, object);
		} catch (Exception e) {
			logger.error("获取药房列表总数失败!" + e.toString());
			return new RespBody(Status.ERROR, "获取药房列表总数失败，数据获取异常！");
		}
	}
}
