/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月22日
 * Created by ckm
 */
package com.ctb.platform.rest.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.entity.Pharmacy;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.utils.PinyinUtils;
import com.ctb.platform.rest.service.PharmacyService;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: com.ctb.platform.rest.controller.PharmacyController
 * @Description: TODO(药房管理Controller)
 * @author ckm
 * @date 2019年3月22日 下午3:43:03
 */
@RefreshScope
@RestController
@RequestMapping("/pharmacy")
public class PharmacyController {

	private static Logger logger = LoggerFactory.getLogger(PharmacyController.class);

	@Autowired
	EurekaDiscoveryClient discoveryClient;

	@Autowired
	private PharmacyService pharmacyService;

	/**
	 * 
	 * @Title: List
	 * @Description: TODO(获取药房列表)
	 * @author ckm
	 * @date 2019年3月25日 上午11:04:05
	 * @param name
	 * @param code
	 * @param contactName
	 * @param contactTel
	 * @param searchKey
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public RespBody list(@RequestParam(required = false, value = "name", defaultValue = "") String name,
			@RequestParam(required = false, value = "code", defaultValue = "") String code,
			@RequestParam(required = false, value = "contactName", defaultValue = "") String contactName,
			@RequestParam(required = false, value = "contactTel", defaultValue = "") String contactTel,
			@RequestParam(required = false, value = "searchKey", defaultValue = "") String searchKey,
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
		try {
			Pharmacy pharmacy = new Pharmacy(name, code, contactName, contactTel);
			List<Pharmacy> pharmacies = pharmacyService.queryPharmacyListPaged(pharmacy, page, size, searchKey);
			PageInfo<Pharmacy> pageInfo = new PageInfo<Pharmacy>(pharmacies);
			JSONObject object = new JSONObject();
			object.put("total", pageInfo.getTotal());
			object.put("pharmacysList", pharmacies);
			return new RespBody(Status.OK, object);
		} catch (Exception e) {
			logger.error("获取药房列表失败!" + e.toString());
			return new RespBody(Status.ERROR, "获取药房列表失败，数据获取异常！");
		}
	}

	/**
	 * 
	 * @Title: List
	 * @Description: TODO(获取药房列表)
	 * @author ckm
	 * @date 2019年3月25日 上午11:04:05
	 * @param name
	 * @param code
	 * @param contactName
	 * @param contactTel
	 * @param searchKey
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/all")
	public RespBody all(@RequestParam(required = false, value = "name", defaultValue = "") String name,
			@RequestParam(required = false, value = "code", defaultValue = "") String code,
			@RequestParam(required = false, value = "contactName", defaultValue = "") String contactName,
			@RequestParam(required = false, value = "contactTel", defaultValue = "") String contactTel,
			@RequestParam(required = false, value = "searchKey", defaultValue = "") String searchKey) {
		try {
			Pharmacy pharmacy = new Pharmacy(name, code, contactName, contactTel);
			List<Pharmacy> pharmacies = pharmacyService.queryPharmacyList(pharmacy, searchKey);
			PageInfo<Pharmacy> pageInfo = new PageInfo<Pharmacy>(pharmacies);
			JSONObject object = new JSONObject();
			object.put("total", pageInfo.getTotal());
			object.put("pharmacysList", pharmacies);
			return new RespBody(Status.OK, object);
		} catch (Exception e) {
			logger.error("获取药房列表总数失败!" + e.toString());
			return new RespBody(Status.ERROR, "获取药房列表总数失败，数据获取异常！");
		}
	}

	/**
	 * 
	 * @Title: save
	 * @Description: TODO(保存药房基本信息)
	 * @author ckm
	 * @date 2019年3月25日 上午11:04:45
	 * @param id
	 * @param name
	 * @param code
	 * @param contactName
	 * @param contactTel
	 * @param status
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public RespBody save(@RequestParam(required = false, value = "id", defaultValue = "") String id,
			@RequestParam(required = false, value = "name", defaultValue = "") String name,
//			@RequestParam(required = false, value = "code", defaultValue = "") String code,
			@RequestParam(required = false, value = "contactName", defaultValue = "") String contactName,
			@RequestParam(required = false, value = "contactTel", defaultValue = "") String contactTel,
			@RequestParam(required = false, value = "status", defaultValue = "1") String status,
			HttpServletRequest request) {// status=1-true,0-false
	    
	    if (StringUtils.isBlank(name)) {
	        return new RespBody(Status.PROMPT, "保存药房信息失败，药房名不能能为空");
        }
	    
	    String code = PinyinUtils.getChineseFirstWord(name);
        Pharmacy pharmacy = new Pharmacy(id, name,  code,  status, contactName, contactTel);
		try {

			boolean isUniqueName = pharmacyService.isUniqueName(pharmacy);
			if (isUniqueName) {
				return new RespBody(Status.PROMPT, "保存药房信息失败，药房名:'" + name + "'已经存在！");
			}
			boolean isUniqueCode = pharmacyService.isUniqueCode(pharmacy);
			if (isUniqueCode) {
				return new RespBody(Status.PROMPT, "保存药房信息失败，药房code:" + code + "已经存在！");
			}
			int res = pharmacyService.savePharmacy(pharmacy);
			if (res == 1) {
				return new RespBody(Status.OK, pharmacy.getId());
			} else {
				return new RespBody(Status.PROMPT, "保存药店信息失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存药店失败：" + e.toString());
			return new RespBody(Status.ERROR, "保存药店信息失败，数据保存异常！");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/toEdit")
	public RespBody update(@RequestParam(required = true) String id,
			@RequestParam(required = false, value = "name", defaultValue = "") String name,
//			@RequestParam(required = false, value = "code", defaultValue = "") String code,
			@RequestParam(required = false, value = "contactName", defaultValue = "") String contactName,
			@RequestParam(required = false, value = "contactTel", defaultValue = "") String contactTel,
			@RequestParam(required = false, value = "status", defaultValue = "") String status,
			HttpServletRequest request) {// status=1-true,0-false
	    
	    if (StringUtils.isBlank(name)) {
            return new RespBody(Status.PROMPT, "保存药房信息失败，药房名不能能为空");
        }
        
        String code = PinyinUtils.getChineseFirstWord(name);
		Pharmacy pharmacy = new Pharmacy(id, name, code, status, contactName, contactTel);
		try {
			pharmacy.setEt(new Date());
			int res = pharmacyService.updatePharmacy(pharmacy);
			if (res == 1) {
				return new RespBody(Status.OK, "修改药房信息成功！");
			} else {
				return new RespBody(Status.PROMPT, "修改药房信息失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改药房信息失败：" + e.toString());
			return new RespBody(Status.ERROR, "修改药房信息失败，数据保存异常！");
		}
	}

	/**
	 * 
	 * @Title: queryPharmacy
	 * @Description: TODO(查询药房信息)
	 * @author ckm
	 * @date 2019年3月25日 上午11:05:08
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPharmacy")
	public RespBody queryPharmacy(@RequestParam(required = true, value = "id") String id) {
		try {
			Pharmacy pharmacy = new Pharmacy(id);
			Pharmacy pharmacyInfo = pharmacyService.queryPharmacyOne(pharmacy);
			return new RespBody(Status.OK, pharmacyInfo);
		} catch (Exception e) {
			logger.error("获取药房信息失败!:" + e.toString());
			return new RespBody(Status.ERROR, "获取药房信息失败，数据获取异常！");
		}
	}

	/**
	 * 
	 * @Title: del
	 * @Description: TODO(删除药房信息)
	 * @author ckm
	 * @date 2019年3月25日 上午11:05:29
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/del")
	public RespBody del(@RequestParam(required = true) String[] id) {
		try {
			int res = pharmacyService.deletePharmacy(id);
			if (res == 1) {
				return new RespBody(Status.OK, "删除药房信息成功！");
			} else {
				return new RespBody(Status.PROMPT, "删除药房信息失败！");
			}
		} catch (Exception e) {
			logger.error("删除药店失败！：" + e.toString());
			return new RespBody(Status.ERROR, "删除药店失败，删除操作异常！");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/editStatus")
	public RespBody editStatus(@RequestParam(required = true, value = "pharmacyId") String pharmacyId,
			@RequestParam(required = true, value = "status") String status) {
		try {
			Pharmacy pharmacy = new Pharmacy(pharmacyId);
			if (StringUtils.isNotBlank(status)) {
				pharmacy.setStatus(status);
			}
			int res = pharmacyService.updatePharmacy(pharmacy);
			if (res == 1) {
				return new RespBody(Status.OK, pharmacy);
			} else {
				return new RespBody(Status.PROMPT, "修改药房状态失败!");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("修改药店状态失败,修改数据异常！" + e.toString());
			return new RespBody(Status.ERROR, "修改药店状态失败,修改数据异常！");
		}

	}

}
