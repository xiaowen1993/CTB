/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月1日
 * Created by hhy
 */
package com.ctb.mobile.rest.service;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.entity.BranchPharmacy;

/**
 * @ClassName: com.ctb.mobile.rest.service.BranchPharmacyService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年4月1日 下午8:32:10
 */

public interface BranchPharmacyService {

	public BranchPharmacy selectOne(BranchPharmacy branchPharmacy);

	/**
	 * 获取附近药房信息
	 * 
	 * @Title: loactionList
	 * @Description: TODO(获取附近药房信息)
	 * @author ckm
	 * @date 2019年4月2日 下午3:44:27
	 * @param hospital
	 * @param latitude
	 * @param longitude
	 * @param radius
	 * @return
	 */
	public List<JSONObject> loactionList(String hospitalCode, String latitude, String longitude,
			String radius);

	/**
	 * 地理位置信息距离升序
	 * 
	 * @Title: sortJsonObjects
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月3日 下午3:12:46
	 * @param list
	 * @return
	 */
	public List<JSONObject> sortJsonObjects(List<JSONObject> list);

	/**
	 * 搜索
	 * 
	 * @Title: searchJsonObjects
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月4日 上午10:57:49
	 * @param list
	 * @param key
	 * @return
	 */
	public List<JSONObject> searchJsonObjects(List<JSONObject> list, String name);

	/**
	 * 无法获取用户定位信息查找list
	 * 
	 * @Title: listWithOutLatAndLng
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月18日 上午9:28:46
	 * @param hospitalId
	 * @param hospitalCode
	 * @return
	 */
	public List<JSONObject> listWithOutLatAndLng(String hospitalCode);
	
	public BranchPharmacy queryOne(String pharmacyId,String pharmacyCode,String pharmacyBranchcode);

}
