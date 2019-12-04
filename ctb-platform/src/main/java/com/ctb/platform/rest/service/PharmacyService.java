package com.ctb.platform.rest.service;

import java.util.List;

import com.ctb.commons.entity.Pharmacy;

public interface PharmacyService {

	public int savePharmacy(Pharmacy pharmacy);

	public void saveAndUpdatePharmacyRedisCache(Pharmacy pharmacy);

	public int updatePharmacy(Pharmacy pharmacy);

	public int deletePharmacy(String[] id);

	public Pharmacy queryPharmacyOne(Pharmacy pharmacy);

	public List<Pharmacy> querPharmacyList(Pharmacy pharmacy);

	public List<Pharmacy> queryPharmacyListPaged(Pharmacy pharmacy, Integer page, Integer pageSize, String search);

	public boolean isUniqueCode(Pharmacy pharmacy);

	public boolean isUniqueName(Pharmacy pharmacy);

	public int total(Pharmacy pharmacy, String searchKey);

	public List<Pharmacy> queryPharmacyList(Pharmacy pharmacy, String search);

	/**
	 * 
	 * @Title: initPharmacyRedisCache
	 * @Description: TODO(初始化药房缓存)
	 * @author ckm
	 * @date 2019年4月1日 上午10:27:40
	 */
	public void initPharmacyRedisCache();
}
