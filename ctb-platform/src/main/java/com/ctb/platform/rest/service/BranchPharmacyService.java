/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月23日
 * Created by ckm
 */
package com.ctb.platform.rest.service;

import java.util.List;

import com.ctb.commons.entity.BranchPharmacy;
import com.ctb.commons.entity.Pharmacy;

/**
 * @ClassName: com.ctb.platform.rest.service.BranchPharmacyService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年3月23日 上午11:27:22
 */

public interface BranchPharmacyService {

	/**
	 * 
	 * @Title: saveBranchPharmacy
	 * @Description: TODO(保存药房分店、关系)
	 * @author ckm
	 * @date 2019年4月1日 上午9:06:45
	 * @param branchPharmacy
	 * @return
	 */
	public int saveBranchPharmacy(BranchPharmacy branchPharmacy);

	/**
	 * 
	 * @Title: saveAndUpdateBranchPharmacyRedisCache
	 * @Description: TODO(更新/保存缓存)
	 * @author ckm
	 * @date 2019年4月1日 上午9:07:32
	 * @param branchPharmacy
	 */
    public void saveAndUpdateBranchPharmacyRedisCache(String pharmacyCode, BranchPharmacy branchPharmacy);

	/**
	 * 
	 * @Title: updateBranchPharmacy
	 * @Description: TODO(更新药房分店信息)
	 * @author ckm
	 * @date 2019年4月1日 上午9:08:08
	 * @param branchPharmacy
	 * @return
	 */
	public int updateBranchPharmacy(BranchPharmacy branchPharmacy);

	/**
	 * 
	 * @Title: delBranchPharmacy
	 * @Description: TODO(删除)
	 * @author ckm
	 * @date 2019年4月1日 上午9:08:37
	 * @param ids
	 * @return
	 */
	public int delBranchPharmacy(String[] ids, String pharmacyId);

	/**
	 * 
	 * @Title: queryBranchPharmacy
	 * @Description: TODO(查询)
	 * @author ckm
	 * @date 2019年4月1日 上午9:08:55
	 * @param branchPharmacy
	 * @return
	 */
	public BranchPharmacy queryBranchPharmacy(BranchPharmacy branchPharmacy);

	/**
	 * 
	 * @Title: isUniqueName
	 * @Description: TODO(判断name是否存在)
	 * @author ckm
	 * @date 2019年4月1日 上午9:09:18
	 * @param branchPharmacy
	 * @return
	 */
	public boolean isUniqueName(BranchPharmacy branchPharmacy);

	/**
	 * 
	 * @Title: isUniqueCode
	 * @Description: TODO(判断code是否存在)
	 * @author ckm
	 * @date 2019年4月1日 上午9:09:40
	 * @param branchPharmacy
	 * @return
	 */
	public boolean isUniqueCode(BranchPharmacy branchPharmacy);

	public int total(BranchPharmacy branchPharmacy, String searchKey);

	/**
	 * 
	 * @Title: queryBranchPharmacyListPaged
	 * @Description: TODO(分页查询list)
	 * @author ckm
	 * @date 2019年4月1日 上午9:10:01
	 * @param branchPharmacy
	 * @param page
	 * @param pageSize
	 * @param searchKey
	 * @return
	 */
	public List<BranchPharmacy> queryBranchPharmacyListPaged(BranchPharmacy branchPharmacy, Integer page,
			Integer pageSize, String searchKey);

	/**
	 * 
	 * @Title: querBranchPharmacyList
	 * @Description: TODO(查询list-不分页)
	 * @author ckm
	 * @date 2019年4月1日 上午9:10:26
	 * @param branchPharmacy
	 * @return
	 */
	public List<BranchPharmacy> querBranchPharmacyList(BranchPharmacy branchPharmacy);

	/**
	 * 
	 * @Title: initBranchPharmacyRedisCache
	 * @Description: TODO(初始化缓存)
	 * @author ckm
	 * @date 2019年4月1日 上午9:10:53
	 * @param pharmacyId
	 */
	public void initBranchPharmacyRedisCache();

	/**
	 * 
	 * @Title: queryBranchPharmacyByIds
	 * @Description: TODO(根据药房ids 查询分店列表)
	 * @author ckm
	 * @date 2019年4月1日 上午9:46:31
	 * @param ids
	 * @return
	 */
	public List<BranchPharmacy> queryBranchPharmacyByIds(String[] ids);
	
	/**
	 * 初始化经纬度信息
	 * @Title: initLocation
	 * @Description: TODO(初始化经纬度信息)
	 * @author ckm
	 * @date 2019年4月1日 下午7:13:49
	 */
	public void initLocation();

}
