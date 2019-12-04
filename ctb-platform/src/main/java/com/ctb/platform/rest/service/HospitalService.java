package com.ctb.platform.rest.service;

import java.util.List;
import java.util.Map;

import com.ctb.commons.entity.Hospital;

/**
 * 
 * @ClassName: com.ctb.platform.rest.service.HospitalService
 * @Description: 医院管理service的 interface
 * @author hhy
 * @date 2019年3月26日 下午7:47:45
 */
public interface HospitalService {


    /**
     * 
     * @Title: saveHospital
     * @Description: 新增医院
     * @author hhy
     * @date 2019年3月23日 下午12:16:36
     * @param hospital
     * @return
     * @throws Exception
     */
    public int saveHospital(Hospital hospital) throws Exception;

    /**
     * 
     * @Title: updateHospital
     * @Description: 修改医院信息
     * @author hhy
     * @date 2019年3月23日 下午12:16:58
     * @param hospital
     * @return
     */
    public int updateHospital(Hospital hospital);

    /**
     * 
     * @Title: deleteHospital
     * @Description: 根据hospitalId 删除医院
     * @author hhy
     * @date 2019年3月23日 下午12:17:12
     * @param hospitalId
     */
    public void deleteHospital(String hospitalId);

    /**
     * 
     * @Title: queryHospitalById
     * @Description: 根据hospitalID 查找医院
     * @author hhy
     * @date 2019年3月23日 下午12:17:31
     * @param hospitalId
     * @return
     */
    public Hospital queryHospitalById(String hospitalId);
    
    /**
     * 
     * @Title: queryHospitalListByExample
     * @Description: 获取医院信息列表
     * @author hhy
     * @date 2019年3月25日 下午3:44:51
     * @param map
     * @return
     */
    public List<Hospital> queryHospitalListByExample(Map <String, Object> map);

    /**
     * 
     * @Title: queryHospitalListPaged
     * @Description: 获取医院信息列表---分页
     * @author hhy
     * @date 2019年3月23日 下午12:18:32
     * @param page
     * @param pageSize
     * @return
     */
    public List<Hospital> queryHospitalListPagedByExample(Map <String, Object> map, Integer page, Integer pageSize);
    
    public List<Hospital> getAll();
    
    public void initHospitalCache();
        
    /**
     * 
     * @Title: queryHospitalList
     * @Description: 查询医院列表
     * @author Qin
     * @date 2019年11月6日 
     * @param 
     * @return
     * @throws Exception
     */
	public List<Hospital> queryHospitalList(Hospital hospital, String search);
}
