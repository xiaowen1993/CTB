package com.ctb.platform.rest.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ctb.commons.entity.DrugList;
import com.ctb.commons.entity.Hospital;
import com.ctb.commons.entity.HospitalDrug;
import com.ctb.commons.entity.Pharmacy;
import com.ctb.commons.entity.PharmacyUser;

/**
 * @ClassName: com.ctb.platform.rest.service.HospitalDrugService
 * @Description: 医院药品名单service
 * @author Qin
 * @date 2019年11月4日 
 */
public interface HospitalDrugService {
	
	/**
	 * 查询医院药品列表
	 * @Title: queryHospitalDurgLists
	 * @Description: 手动编写sql 查询医院药品列表
	 * @author Qinckm
	 * @date 2019年11月5日 
	 * @param param
	 * @return
	 */
	public List <HospitalDrug> queryHospitalDurgLists(Map<String, Object> param);
	
    /**
     * 
     * @Title: saveHospitalDrug
     * @Description: 新增医院药品
     * @author Qin
     * @date 2019年11月6日 
     * @param 
     * @return
     * @throws Exception
     */
    public int saveHospitalDrug(HospitalDrug hospitalDrug) throws Exception;
    
    
//    /**
//     * 
//     * @Title: saveDrug
//     * @Description: 新增医院药品
//     * @author Qin
//     * @date 2019年11月6日 
//     * @param drugList
//     * @return
//     * @throws Exception
//     */
//    public int saveHospitalDrugList(HospitalDrug hospitalDrug) throws Exception;

    /**
     * 
     * @Title: queryHospitalDurg
     * @Description: 获取医院药品名单列表,查询是否相同记录
     * @author Qin
     * @date 2019年11月6日 
     * @param map
     * @return
     */
	public int queryHospitalDurg(Map <String, Object> map);
		
    /**
     * 
     * @Title: updateHospitalDrugList
     * @Description: 修改药品信息
     * @author Qin
     * @date 2019年11月5日
     */
    public int updateHospitalDrugList(HospitalDrug hospitalDrug);
    
    /**
     * 
     * @Title: saveAndUpdateDrugListRedisCache
     * @Description: 缓存药品信息
     * @author Qin
     * @date 2019年11月5日
     */
	//public void saveAndUpdateHospitalDrugListRedisCache(HospitalDrug hospitalDrug);
	
	
    /**
     * 
     * @Title: queryHospitalDurgCount
     * @Description: 查询总记录数
     * @author Qin
     * @date 2019年11月7日
     */
	public int queryHospitalDurgCount(Map <String, Object> map);


    /**
     * 
     * @Title: initHospitalDrugListCache
     * @Description: 初始化药品黑白名单缓存
     * @author Qin
     * @date 2019年3月30日 下午12:10:49
     */
    public void initHospitalDrugListCache();
	
	

}
