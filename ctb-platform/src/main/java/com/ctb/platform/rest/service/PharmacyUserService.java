/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月25日
 * Created by ckm
 */
package com.ctb.platform.rest.service;

import java.util.List;

import com.ctb.commons.entity.PharmacyUser;

/**
 * @ClassName: com.ctb.platform.rest.service.PharmacyUserService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年3月25日 下午5:39:52
 */

public interface PharmacyUserService {

    /**
     * 
     * @Title: savePharmacyUser
     * @Description: 保存账号
     * @author hhy
     * @date 2019年3月27日 下午3:35:34
     * @param pharmacyUser
     * @return
     */
	public int savePharmacyUser(PharmacyUser pharmacyUser);
	
	/**
	 * 
	 * @Title: updatePharmacyUser
	 * @Description: 修改帐号
	 * @author hhy
	 * @date 2019年3月27日 下午3:35:57
	 * @param pharmacyUser
	 * @return
	 */
	public int updatePharmacyUser(PharmacyUser pharmacyUser);
	
	/**
	 * 
	 * @Title: delPharmacyUser
	 * @Description: 通过ID删除账号
	 * @author hhy
	 * @date 2019年3月27日 下午3:36:11
	 * @param id
	 * @return
	 */
	public int delPharmacyUser(String id);

	/**
	 * 
	 * @Title: queryPharmacyUser
	 * @Description: 通过条件查询某条账号信息
	 * @author hhy
	 * @date 2019年3月27日 下午3:37:03
	 * @param pharmacyUser
	 * @return
	 */
	public PharmacyUser queryPharmacyUser(PharmacyUser pharmacyUser);
	
	/**
	 * 
	 * @Title: pharmacyUsersList
	 * @Description: 分页查询账号列表
	 * @author hhy
	 * @date 2019年3月27日 下午3:37:45
	 * @param pharmacyUser
	 * @param searchKey
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<PharmacyUser> pharmacyUsersList(PharmacyUser pharmacyUser,String searchKey,Integer page,Integer pageSize);
	
	/**
	 * 
	 * @Title: deleteByIds
	 * @Description: 批量删除账号
	 * @author hhy
	 * @date 2019年3月27日 下午3:38:05
	 * @param ids
	 * @return
	 */
	public int deleteByIds(String[] ids);
	
	 /**
     * 
     * @Title: deleteByBranchPharmacyIds
     * @Description: 通过分店ID批量删除
     * @author hhy
     * @date 2019年3月27日 下午3:38:05
     * @param ids
     * @return
     */
	public int deleteByBranchPharmacyIds(String [] branchPharmacyIds);
}
