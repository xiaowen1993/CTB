package com.ctb.platform.rest.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.BranchPharmacy;
import com.ctb.commons.entity.HospitalPharmacySettings;
import com.ctb.commons.entity.Pharmacy;
import com.ctb.framework.commons.generator.PKGenerator;
import com.ctb.framework.commons.redis.RedisClient;
import com.ctb.platform.rest.service.BranchPharmacyService;
import com.ctb.platform.rest.service.PharmacyService;
import com.ctb.resources.mapper.biz.HospitalPharmacySettingsMapper;
import com.ctb.resources.mapper.biz.PharmacyMapper;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

@Service
public class PharmacyServiceImpl implements PharmacyService {

	private static Logger logger = LoggerFactory.getLogger(PharmacyServiceImpl.class);

	@Autowired
	private PharmacyMapper pharmacyMapper;

	@Autowired
	private BranchPharmacyService branchPharmacyService;
	
	@Autowired
	private HospitalPharmacySettingsMapper hospitalPharmacySettingsMapper;

	@Autowired
	private RedisClient redisClient;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int savePharmacy(Pharmacy pharmacy) {
		try {
			pharmacy.setId(PKGenerator.generateId());
			pharmacy.setCt(new Date());
			pharmacy.setEt(new Date());
			int res = pharmacyMapper.insertSelective(pharmacy);
			if (res == 1)
				saveAndUpdatePharmacyRedisCache(pharmacy);
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
			logger.error("保存药房信息失败，保存数据异常！" + e.toString());
			return 0;
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int updatePharmacy(Pharmacy pharmacy) {
		try {
			int res = pharmacyMapper.updateByPrimaryKey(pharmacy);
			if (res == 1) {
                /*
                 * Pharmacy pharmacy2=pharmacyMapper.selectByPrimaryKey(pharmacy.getId());
                 * 
                 * Example example = new Example(HospitalPharmacySettings.class); Example.Criteria criteria =
                 * example.createCriteria(); criteria.andEqualTo("pharmacyId", pharmacy2.getId());
                 * List<HospitalPharmacySettings> hospitalPharmacySettings =
                 * hospitalPharmacySettingsMapper.selectByExample(example); if(hospitalPharmacySettings.size()>0) { for
                 * (HospitalPharmacySettings hospitalPharmacySettings2 : hospitalPharmacySettings) {
                 * hospitalPharmacySettings2.setPharmacyCode(pharmacy2.getCode()); }
                 * hospitalPharmacySettingsMapper.batchUpdate(hospitalPharmacySettings); }
                 */
	            
			    saveAndUpdatePharmacyRedisCache(pharmacy);
			}
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
			logger.error("修改药房信息失败，修改数据异常！" + e.toString());
			return 0;
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public int deletePharmacy(String[] ids) {
		try {
			String idsList = String.join("','", ids);
			List<Pharmacy> pharmacies = queryPharmaciesByIds(ids);
			String[] queryIdlist = new String[pharmacies.size()];
			if (pharmacies.size() > 0) {
				for (int i = 0; i < pharmacies.size(); i++) {
					queryIdlist[i] = pharmacies.get(i).getId();
				}
			}
			List<BranchPharmacy> branchPharmacies = branchPharmacyService.queryBranchPharmacyByIds(queryIdlist);
			String[] delIdlist = new String[branchPharmacies.size()];
			if (branchPharmacies.size() > 0) {
				for (int i = 0; i < branchPharmacies.size(); i++) {
					delIdlist[i] = branchPharmacies.get(i).getId();
				}
			}
//			branchPharmacyService.delBranchPharmacy(delIdlist);
			int res = pharmacyMapper.deleteByIds("'" + idsList + "'");
			if (res == 1) {
				initPharmacyRedisCache();
			} else {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
			}
			return res;
		} catch (Exception e) {
			// TODO: handle exception
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); // 手动回滚
			logger.error("删除药房信息失败，数据删除异常！" + e.toString());
			return 0;
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Pharmacy> querPharmacyList(Pharmacy pharmacy) {
		try {
			Example example = new Example(Pharmacy.class);
			Example.Criteria criteria = example.createCriteria();
			// 拼接查询条件
			if (StringUtils.isNotBlank(pharmacy.getName())) {
				criteria.andEqualTo("name", pharmacy.getName());
			}
			if (StringUtils.isNotBlank(pharmacy.getCode())) {
				criteria.andEqualTo("code", pharmacy.getCode());
			}
			if (StringUtils.isNotBlank(String.valueOf(pharmacy.getStatus()))) {
				criteria.andEqualTo("status", pharmacy.getStatus());
			}
			if (StringUtils.isNotBlank(pharmacy.getContactName())) {
				criteria.andEqualTo("contactName", pharmacy.getContactName());
			}
			if (StringUtils.isNotBlank(pharmacy.getContactTel())) {
				criteria.andEqualTo("contactTel", pharmacy.getContactTel());
			}
			example.orderBy("ct").desc();
			List<Pharmacy> pharmacyList = pharmacyMapper.selectByExample(example);
			return pharmacyList;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("获取药房列表失败，获取数据异常！");
			return new ArrayList<Pharmacy>();
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Pharmacy> queryPharmacyListPaged(Pharmacy pharmacy, Integer page, Integer pageSize, String search) {
		try {
			PageHelper.startPage(page, pageSize);
			Example example = new Example(Pharmacy.class);
			Example.Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(pharmacy.getName())) {
				criteria.andLike("name", "%" + pharmacy.getName() + "%");
			}
			if (StringUtils.isNotBlank(pharmacy.getCode())) {
				criteria.andEqualTo("code", pharmacy.getCode());
			}
			if (StringUtils.isNotBlank(String.valueOf(pharmacy.getStatus()))) {
				criteria.andEqualTo("status", pharmacy.getStatus());
			}
			if (StringUtils.isNotBlank(pharmacy.getContactName())) {
				criteria.andLike("contactName", "%" + pharmacy.getContactName() + "%");
			}
			if (StringUtils.isNotBlank(pharmacy.getContactTel())) {
				criteria.andEqualTo("contactTel", pharmacy.getContactTel());
			}
			if (StringUtils.isNotBlank(search)) {
				criteria.orLike("name", "%" + search + "%");
				criteria.orLike("contactName", "%" + search + "%");
			}
			example.orderBy("ct").desc();
			List<Pharmacy> pharmacyList = pharmacyMapper.selectByExample(example);
			return pharmacyList;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("分页查询药房列表失败,获取数据异常！" + e.toString());
			return new ArrayList<Pharmacy>();
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public boolean isUniqueCode(Pharmacy pharmacy) {
		Example example = new Example(Pharmacy.class);
		Example.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(pharmacy.getCode())) {
			criteria.andEqualTo("code", pharmacy.getCode());
		}
		Pharmacy entity = pharmacyMapper.selectOneByExample(example);
		if (entity == null) {
			return false;
		} else {
			if (StringUtils.isNotBlank(pharmacy.getId())) {
				if (!entity.getId().equals(pharmacy.getId())) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public boolean isUniqueName(Pharmacy pharmacy) {
		Example example = new Example(Pharmacy.class);
		Example.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(pharmacy.getName())) {
			criteria.andEqualTo("name", pharmacy.getName());
		}
		Pharmacy entity = pharmacyMapper.selectOneByExample(example);
		if (entity == null) {
			return false;
		} else {
			if (StringUtils.isNotBlank(pharmacy.getId())) {
				if (!entity.getId().equals(pharmacy.getId())) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		}
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Pharmacy queryPharmacyOne(Pharmacy pharmacy) {
		try {
			Example example = new Example(Pharmacy.class);
			Example.Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(pharmacy.getId())) {
				criteria.andEqualTo("id", pharmacy.getId());
			}
			if (StringUtils.isNotBlank(pharmacy.getName())) {
				criteria.andEqualTo("name", pharmacy.getName());
			}
			if (StringUtils.isNotBlank(pharmacy.getCode())) {
				criteria.andEqualTo("code", pharmacy.getCode());
			}
			if (StringUtils.isNotBlank(pharmacy.getContactName())) {
				criteria.orEqualTo("contactName", pharmacy.getContactName());
			}
			if (StringUtils.isNotBlank(pharmacy.getContactTel())) {
				criteria.orEqualTo("contactTel", pharmacy.getContactTel());
			}
			Pharmacy pharmacyInfo = pharmacyMapper.selectOneByExample(example);
			return pharmacyInfo;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("获取药房信息失败,获取信息异常！");
			return new Pharmacy();
		}
	}

	@Override
	public int total(Pharmacy pharmacy, String searchKey) {
		try {
			Example example = new Example(Pharmacy.class);
			Example.Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(pharmacy.getName())) {
				criteria.andLike("name", "%" + pharmacy.getName() + "%");
			}
			if (StringUtils.isNotBlank(pharmacy.getCode())) {
				criteria.andEqualTo("code", pharmacy.getCode());
			}
			if (StringUtils.isNotBlank(String.valueOf(pharmacy.getStatus()))) {
				criteria.andEqualTo("status", pharmacy.getStatus());
			}
			if (StringUtils.isNotBlank(pharmacy.getContactName())) {
				criteria.andLike("contactName", "%" + pharmacy.getContactName() + "%");
			}
			if (StringUtils.isNotBlank(pharmacy.getContactTel())) {
				criteria.andEqualTo("contactTel", pharmacy.getContactTel());
			}
			if (StringUtils.isNotBlank(searchKey)) {
				criteria.orLike("name", "%" + searchKey + "%");
				criteria.orLike("contactName", "%" + searchKey + "%");
			}
			int total = pharmacyMapper.selectCountByExample(example);
			return total;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("查询记录总数失败，数据查询异常！" + e.toString());
			return 0;
		}
	}

	@Override
	public List<Pharmacy> queryPharmacyList(Pharmacy pharmacy, String search) {

		try {
			Example example = new Example(Pharmacy.class);
			Example.Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(pharmacy.getName())) {
				criteria.andLike("name", "%" + pharmacy.getName() + "%");
			}
			if (StringUtils.isNotBlank(pharmacy.getCode())) {
				criteria.andEqualTo("code", pharmacy.getCode());
			}
			if (StringUtils.isNotBlank(String.valueOf(pharmacy.getStatus()))) {
				criteria.andEqualTo("status", pharmacy.getStatus());
			}
			if (StringUtils.isNotBlank(pharmacy.getContactName())) {
				criteria.andLike("contactName", "%" + pharmacy.getContactName() + "%");
			}
			if (StringUtils.isNotBlank(pharmacy.getContactTel())) {
				criteria.andEqualTo("contactTel", pharmacy.getContactTel());
			}
			if (StringUtils.isNotBlank(search)) {
				criteria.orLike("name", "%" + search + "%");
				criteria.orLike("contactName", "%" + search + "%");
			}
			List<Pharmacy> pharmacyList = pharmacyMapper.selectByExample(example);
			return pharmacyList;
		} catch (Exception e) {
			System.out.println("获取药房总数失败：：：");
			e.printStackTrace();
			return new ArrayList<Pharmacy>();
		}
	}

	@Override
	public void initPharmacyRedisCache() {
		try {
			List<Pharmacy> pharmacies = querPharmacyList(new Pharmacy());
			if (pharmacies.size() > 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (Pharmacy pharmacy2 : pharmacies) {
					map.put(pharmacy2.getId(), pharmacy2);
				}
				redisClient.del(CacheConstants.CACHE_PHARMACY_HASH_KEY_PREFIX);
				redisClient.hmset(CacheConstants.CACHE_PHARMACY_HASH_KEY_PREFIX, map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("初始化药房缓存失败！:::" + e.toString());
		}
	}

	@Override
	public void saveAndUpdatePharmacyRedisCache(Pharmacy pharmacy) {
		try {
			Map<String, Object> setMap = new HashMap<String, Object>();
			setMap.put(pharmacy.getId() + CacheConstants.CACHE_KEY_SPLIT_CHAR + pharmacy.getCode(), pharmacy);
			redisClient.hmset(CacheConstants.CACHE_PHARMACY_HASH_KEY_PREFIX, setMap);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("保存更新药房缓存失败！:::" + e.toString());
		}

	}

	public List<Pharmacy> queryPharmaciesByIds(String[] ids) {
		try {
			Example example = new Example(Pharmacy.class);
			Example.Criteria criteria = example.createCriteria();
			criteria.andIn("id", Arrays.asList(ids));
			List<Pharmacy> pharmacies = pharmacyMapper.selectByExample(example);
			return pharmacies;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("根据ids查询药房信息失败，查询数据异常！:::" + e.toString());
			return new ArrayList<Pharmacy>();
		}
	}
}
