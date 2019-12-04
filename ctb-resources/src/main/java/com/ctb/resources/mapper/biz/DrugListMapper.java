package com.ctb.resources.mapper.biz;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ctb.commons.entity.DrugList;
import com.ctb.commons.entity.HospitalDrug;
import com.ctb.data.persistence.mapper.CtbMapper;

@Repository
public interface DrugListMapper extends CtbMapper<DrugList> {
	
	/**
	 * 修改药品信息
	 * @Title: updateDrugList
	 * @Description: 修改药品信息
	 * @author Qin
	 * @date 2019年11月11日 
	 * @param param
	 * @return
	 */
    public List<DrugList> queryDrugListByExample(Map <String, Object> map);
	
	/**
	 * 初始化医院，药品缓存
	 * @Title: selectByRedis
	 * @Description: 查询医院药品列表显示名称
	 * @author Qin
	 * @date 2019年11月7日 
	 * @param param
	 * @return
	 */
	public List <Map<String,Object>> selectDrugByRedis(@Param("hospitalId")String hospitalId);
	
	/**
	 * 查询药品列表
	 * @Title: queryDurgLists
	 * @Description: 查询药品列表
	 * @author Qin
	 * @date 2019年11月11日 
	 * @param param
	 * @return
	 */
	public List <DrugList>  queryDurgLists(Map<String, Object> param);
	
	/**
	 * 保存药品信息
	 * @Title: saveDrugList
	 * @Description: 保存药品信息
	 * @author Qin
	 * @date 2019年11月11日 
	 * @param param
	 * @return
	 */
	public int  saveDrugList(DrugList drugList);
	
	/**
	 * 修改药品信息
	 * @Title: updateDrugList
	 * @Description: 修改药品信息
	 * @author Qin
	 * @date 2019年11月11日 
	 * @param param
	 * @return
	 */
	public int  updateDrugList(DrugList drugList);
}