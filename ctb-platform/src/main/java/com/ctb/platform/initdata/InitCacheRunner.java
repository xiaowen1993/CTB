/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月29日
 * Created by cwq
 */
package com.ctb.platform.initdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ctb.commons.entity.Pharmacy;
import com.ctb.commons.entity.PharmacyPaySettings;
import com.ctb.platform.rest.service.BranchPharmacyService;
import com.ctb.platform.rest.service.DrugBlackListService;
import com.ctb.platform.rest.service.DrugListService;
import com.ctb.platform.rest.service.HospitalDrugService;
import com.ctb.platform.rest.service.HospitalService;
import com.ctb.platform.rest.service.PharmacyPaySettingsService;
import com.ctb.platform.rest.service.PharmacyService;

/**
 * @ClassName: com.ctb.platform.initdata.InitCacheRunner
 * @Description: TODO(这里用一句话描述这个类的作用)  初始化缓存
 * @author cwq
 * @date 2019年3月29日 下午4:32:16
 */

@Component
public class InitCacheRunner implements CommandLineRunner {

	@Autowired
	private PharmacyService pharmacyService;
	@Autowired
	private BranchPharmacyService branchPharmacyService;
	@Autowired
	private PharmacyPaySettingsService pharmacyPaySettingsService;
	
	@Autowired 
	private DrugBlackListService drugBlackListService;
	
	@Autowired
	private DrugListService drugListService;
	
	@Autowired
	private HospitalService hospitalService;
	
	@Autowired
	private HospitalDrugService hospitalDrugService;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub

		/**
		 * 初始化药房缓存
		 */
		pharmacyService.initPharmacyRedisCache();

		/**
		 * 初始化药房-分店缓存
		 */
		branchPharmacyService.initBranchPharmacyRedisCache();

		/**
		 * 初始化药房分店经纬度缓存
		 */
		branchPharmacyService.initLocation();
		
		/**
		 * 初始化药房-支付配置缓存
		 */
		pharmacyPaySettingsService.initPharmacyPaysettingsCache();
		
        /**
         * 初始化药品黑名单缓存
         */
        //drugBlackListService.initHospitalDrugBlackListCache();
        
        /**
         * 初始化药品目录缓存
         */
        drugListService.initDrugListCache();
        
        /**
         * 初始化医院缓存
         */
        hospitalService.initHospitalCache();
        
        /**
         * 初始化医院药品黑白名单缓存
         */
        hospitalDrugService.initHospitalDrugListCache();
	}

}
