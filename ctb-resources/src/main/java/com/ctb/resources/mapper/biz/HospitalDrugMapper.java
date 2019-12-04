package com.ctb.resources.mapper.biz;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ctb.commons.entity.Hospital;
import com.ctb.commons.entity.HospitalDrug;
import com.ctb.commons.entity.PaySettings;
import com.ctb.commons.entity.PrescriptionRecord;
import com.ctb.data.persistence.mapper.CtbMapper;

@Repository
public interface HospitalDrugMapper extends CtbMapper<HospitalDrug>{

	/**
	 * 查询医院药品列表
	 * @Title: queryHospitalDurgLists
	 * @Description: 查询医院药品列表显示名称
	 * @author Qin
	 * @date 2019年11月4日 
	 * @param param
	 * @return
	 */
	public List <HospitalDrug> queryHospitalDurgLists(Map<String, Object> param);
	
	
	/**
	 * 查询医院药品是否存在
	 * @Title: queryHospitalDurg
	 * @Description: 查询医院药品列表显示名称
	 * @author Qin
	 * @date 2019年11月6日 
	 * @param param
	 * @return
	 */
	public int queryHospitalDurg(Map<String, Object> param);
	
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
     * @Title: queryHospitalDurgCount
     * @Description: 查询总记录数
     * @author Qin
     * @date 2019年11月7日
     */
	public int queryHospitalDurgCount(Map <String, Object> map);
	
	
	/**
	 * 初始化医院，药品缓存
	 * @Title: selectByRedis
	 * @Description: 缓存医院药品名称
	 * @author Qin
	 * @date 2019年11月9日 
	 * @param param
	 * @return
	 */
	public List <HospitalDrug> selectHospitalDrugByRedis(@Param("hospitalId")String hospitalId);
}
