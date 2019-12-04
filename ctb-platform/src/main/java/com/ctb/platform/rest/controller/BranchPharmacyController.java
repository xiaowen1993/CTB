/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月25日
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
import com.ctb.commons.entity.BranchPharmacy;
import com.ctb.framework.commons.entity.RespBody;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.framework.commons.utils.PinyinUtils;
import com.ctb.platform.rest.service.BranchPharmacyService;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName: com.ctb.platform.rest.controller.BranchPharmacyController
 * @Description: TODO(药房分店管理Controller)
 * @author ckm
 * @date 2019年3月25日 上午10:16:49
 */

@RefreshScope
@RestController
@RequestMapping("/branchPharmacy")
public class BranchPharmacyController {

	private static Logger logger = LoggerFactory.getLogger(BranchPharmacyController.class);

	@Autowired
	EurekaDiscoveryClient discoveryClient;

	@Autowired
	private BranchPharmacyService branchPharmacyService;

	/**
	 * 
	 * @Title: list
	 * @Description: TODO(药房分店列表)
	 * @author ckm
	 * @date 2019年3月25日 上午11:22:28
	 * @param name
	 * @param code
	 * @param status
	 * @param searchKey
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	public RespBody list(@RequestParam(required = false, value = "pharmacyId", defaultValue = "") String pharmacyId,
			@RequestParam(required = false, value = "name", defaultValue = "") String name,
			@RequestParam(required = false, value = "code", defaultValue = "") String code,
			@RequestParam(required = false, value = "status", defaultValue = "") String status,
			@RequestParam(required = false, value = "searchKey", defaultValue = "") String searchKey,
			@RequestParam(required = false, value = "page", defaultValue = "1") Integer page,
			@RequestParam(required = false, value = "size", defaultValue = "10") Integer size) {
		try {
			BranchPharmacy branchPharmacy = new BranchPharmacy(pharmacyId, code, name, status);
			if (StringUtils.isNotBlank(status)) {
				branchPharmacy.setStatus(status);
			}
			List<BranchPharmacy> branchPharmacies = branchPharmacyService.queryBranchPharmacyListPaged(branchPharmacy,
					page, size, searchKey);
			PageInfo<BranchPharmacy> pageInfo = new PageInfo<>(branchPharmacies);
			JSONObject object = new JSONObject();
			object.put("total", pageInfo.getTotal());
			object.put("branchPharmaciesList", branchPharmacies);
			return new RespBody(Status.OK, object);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("获取药房分店列表失败，获取数据异常！:" + e.toString());
			return new RespBody(Status.ERROR, "获取药房分店列表失败，获取数据异常！");
		}
	}

	/**
	 * 
	 * @Title: queryBranchPharmacy
	 * @Description: TODO(查询药房分店信息)
	 * @author ckm
	 * @date 2019年3月25日 上午11:22:55
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryBranchPharmacy")
	public RespBody queryBranchPharmacy(@RequestParam(required = true, value = "id") String id) {
		try {
			BranchPharmacy bp = new BranchPharmacy(id);
			BranchPharmacy branchPharmacy = branchPharmacyService.queryBranchPharmacy(bp);
			return new RespBody(Status.OK, branchPharmacy);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("获取药房分店信息失败，获取信息异常！");
			return new RespBody(Status.ERROR, "获取药房分店信息失败，获取信息异常！");
		}
	}

	/**
	 * 
	 * @Title: save
	 * @Description: TODO(保存药房分店)
	 * @author ckm
	 * @date 2019年3月25日 下午2:55:11
	 * @param id
	 * @param pharmacyId
	 * @param code
	 * @param name
	 * @param address
	 * @param tel
	 * @param businessHours
	 * @param latitude
	 * @param longitude
	 * @param status
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public RespBody save(@RequestParam(required = false, value = "id", defaultValue = "") String id,
//			@RequestParam(required = true, value = "pharmacyCode", defaultValue = "") String pharmacyCode,
			@RequestParam(required = true, value = "pharmacyId", defaultValue = "") String pharmacyId,
			@RequestParam(required = false, value = "code", defaultValue = "") String code,
			@RequestParam(required = false, value = "name", defaultValue = "") String name,
			@RequestParam(required = false, value = "address", defaultValue = "") String address,
			@RequestParam(required = false, value = "contactName", defaultValue = "") String contactName,
			@RequestParam(required = false, value = "contactTel", defaultValue = "") String contactTel,
			@RequestParam(required = false, value = "businessHours", defaultValue = "") String businessHours,
			@RequestParam(required = false, value = "latitude", defaultValue = "") String latitude,
			@RequestParam(required = false, value = "longitude", defaultValue = "") String longitude,
			@RequestParam(required = false, value = "status", defaultValue = "1") String status,
			HttpServletRequest request) {
		try {
			int res = 0;
			if(StringUtils.isNotBlank(name))code = PinyinUtils.getChineseFirstWord(name);
            BranchPharmacy branchPharmacy = new BranchPharmacy(id, pharmacyId, /* pharmacyCode, */ code, name, address, contactName, contactTel, latitude, longitude, businessHours);
			branchPharmacy.setId(PKGenerator.generateId());
			if (StringUtils.isNotBlank(status)) {
				branchPharmacy.setStatus(status);
			} else {
				branchPharmacy.setStatus("1");
			}
			// 检查name 、code 是否存在，保持唯一性
			boolean isUniqueName = branchPharmacyService.isUniqueName(branchPharmacy);
			if (isUniqueName) {
				return new RespBody(Status.PROMPT, "保存药房分店信息失败，药房分店:" + name + "已经存在！");
			}
			boolean isUniqueCode = branchPharmacyService.isUniqueName(branchPharmacy);
			if (isUniqueCode) {
				return new RespBody(Status.PROMPT, "保存药房分店信息失败，药房分店code:" + name + "已经存在！");
			}
			branchPharmacy.setCt(new Date());
			branchPharmacy.setCp("");
			branchPharmacy.setEt(new Date());
			branchPharmacy.setEp("");
			res = branchPharmacyService.saveBranchPharmacy(branchPharmacy);

			if (res == 1) {
				return new RespBody(Status.OK, branchPharmacy.getId());
			} else {
				return new RespBody(Status.ERROR, "保存药房分店失败！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("保存药房分店失败，保存数据异常!：{}" , e.toString());
			return new RespBody(Status.ERROR, "保存药房分店失败，保存数据异常！");
		}
	}

	/**
	 * 
	 * @Title: toEdit
	 * @Description: TODO(修改药房分店)
	 * @author ckm
	 * @date 2019年3月27日 下午7:12:21
	 * @param id
	 * @param pharmacyId
	 * @param code
	 * @param name
	 * @param address
	 * @param contactName
	 * @param contactTel
	 * @param businessHours
	 * @param latitude
	 * @param longitude
	 * @param status
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/toEdit")
	public RespBody toEdit(@RequestParam(required = true, value = "id") String id,
			@RequestParam(required = true, value = "pharmacyId", defaultValue = "") String pharmacyId,
//			@RequestParam(required = true, value = "pharmacyCode", defaultValue = "") String pharmacyCode,
			@RequestParam(required = false, value = "code", defaultValue = "") String code,
			@RequestParam(required = false, value = "name", defaultValue = "") String name,
			@RequestParam(required = false, value = "address", defaultValue = "") String address,
			@RequestParam(required = false, value = "contactName", defaultValue = "") String contactName,
			@RequestParam(required = false, value = "contactTel", defaultValue = "") String contactTel,
			@RequestParam(required = false, value = "businessHours", defaultValue = "") String businessHours,
			@RequestParam(required = false, value = "latitude", defaultValue = "") String latitude,
			@RequestParam(required = false, value = "longitude", defaultValue = "") String longitude,
			@RequestParam(required = false, value = "status", defaultValue = "") String status,
			HttpServletRequest request) {
		try {
			int res = 0;
			if(StringUtils.isNotBlank(name))code = PinyinUtils.getChineseFirstWord(name);
            BranchPharmacy branchPharmacy = new BranchPharmacy(id, pharmacyId, /* pharmacyCode, */ code, name, address, contactName, contactTel, latitude, longitude, businessHours);
			if (StringUtils.isNotBlank(status)) {
				branchPharmacy.setStatus(status);
			}
			boolean isUniqueName = branchPharmacyService.isUniqueName(branchPharmacy);
			if (isUniqueName) {
				return new RespBody(Status.PROMPT, "修改药房分店信息失败，药房分店名:'" + name + "'已经存在！");
			}
			boolean isUniqueCode = branchPharmacyService.isUniqueName(branchPharmacy);
			if (isUniqueCode) {
				return new RespBody(Status.PROMPT, "保存药房分店信息失败，药房分店code:" + name + "已经存在！");
			}
			branchPharmacy.setEt(new Date());
			branchPharmacy.setEp("");
			res = branchPharmacyService.updateBranchPharmacy(branchPharmacy);
			if (res == 1) {
				return new RespBody(Status.OK, branchPharmacy.getId());
			} else {
				return new RespBody(Status.ERROR, "保存药房分店失败！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("保存药房分店失败，保存数据异常！" + e.toString());
			return new RespBody(Status.ERROR, "保存药房分店失败，保存数据异常！");
		}
	}

	/**
	 * 
	 * @Title: editStatus
	 * @Description: TODO(修改药房分店状态)
	 * @author ckm
	 * @date 2019年3月25日 下午3:02:48
	 * @param id
	 * @param status
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/editStatus")
	public RespBody editStatus(@RequestParam(required = true, value = "id") String id,
			@RequestParam(required = true, value = "pharmacyId", defaultValue = "") String pharmacyId,
//			@RequestParam(required = true, value = "pharmacyCode", defaultValue = "") String pharmacyCode,
			@RequestParam(required = true, value = "status") String status, HttpServletRequest request) {
		try {
			BranchPharmacy branchPharmacy = new BranchPharmacy(id, status);
//			branchPharmacy.setPharmacyCode(pharmacyCode);
			branchPharmacy.setPharmacyId(pharmacyId);
			branchPharmacy.setEp("");
			branchPharmacy.setEt(new Date());
			int res = branchPharmacyService.updateBranchPharmacy(branchPharmacy);
			if (res == 1) {
				return new RespBody(Status.OK, "修改药房分店状态成功！");
			} else {
				return new RespBody(Status.PROMPT, "修改药房分店状态失败！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("修改药房分店状态信息失败,修改状态数据异常！" + e.toString());
			return new RespBody(Status.ERROR, "修改药房分店状态信息失败,修改状态数据异常！");
		}
	}

	/**
	 * 
	 * @Title: del
	 * @Description: TODO(删除药房分店)
	 * @author ckm
	 * @date 2019年3月27日 下午7:11:59
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/toDel")
	public RespBody del(@RequestParam(required = true, value = "pharmacyId") String pharmacyId,
            @RequestParam(required = true, value = "pharmacyCode") String pharmacyCode,
            @RequestParam(required = true, value = "ids") String[] ids) {
		try {
			int res = branchPharmacyService.delBranchPharmacy(ids, pharmacyId);
			if (res != 0) {
				return new RespBody(Status.OK, "删除药房分店成功！");
			} else {
				return new RespBody(Status.PROMPT, "删除药房分店失败！");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("删除药房分店失败,删除数据异常！" + e.toString());
			return new RespBody(Status.ERROR, "删除药房分店失败,删除数据异常！");
		}
	}
}
