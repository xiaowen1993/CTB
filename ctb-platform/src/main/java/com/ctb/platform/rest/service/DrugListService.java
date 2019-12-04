/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月22日
 * Created by hhy
 */
package com.ctb.platform.rest.service;

import java.util.List;
import java.util.Map;

import com.ctb.commons.entity.DrugList;

/**
 * @ClassName: com.ctb.platform.rest.service.durgList
 * @Description: 药品白名单service
 * @author hhy
 * @date 2019年3月22日 下午4:16:57
 */

public interface DrugListService {
    
    /**
     * 
     * @Title: saveDrug
     * @Description: 新增药品白名单
     * @author hhy
     * @date 2019年3月23日 下午12:19:53
     * @param drugList
     * @return
     * @throws Exception
     */
    public int saveDrugList(DrugList drugList) throws Exception;

    /**
     * 
     * @Title: deleteDurg
     * @Description: 根据drugId删除药品白名单
     * @author hhy
     * @date 2019年3月23日 下午12:20:18
     * @param drugListId
     * @return
     */
    public int deleteDrugList(String [] drugListIds, String hospitalCode);


    /**
     * 
     * @Title: queryDrugListByExample
     * @Description: 获取药品名单列表
     * @author hhy
     * @date 2019年3月25日 下午3:44:02
     * @param map
     * @return
     */
    public List<DrugList> queryDrugListByExample(Map <String, Object> map);

    /**
     * 
     * @Title: queryDrugListPaged
     * @Description: 获取药品白名单列表----分页
     * @author hhy
     * @date 2019年3月23日 下午12:22:17
     * @param map
     * @param page
     * @param pageSize
     * @return
     */
    public List<DrugList> queryDrugListPagedByExample(Map <String, Object> map, Integer page, Integer pageSize);
    

    /**
     * 
     * @Title: initDrugListRedisCache
     * @Description: 初始化药品白名单缓存
     * @author hhy
     * @date 2019年3月30日 下午12:10:49
     */
    public void initDrugListCache();
    
    
    /**
     * 
     * @Title: updateDrugList
     * @Description: 修改药品信息
     * @author Qin
     * @date 2019年11月5日
     */
    public int updateDrugList(DrugList drugList);

    /**
     * 
     * @Title: saveAndUpdateDrugListRedisCache
     * @Description: 缓存药品信息
     * @author Qin
     * @date 2019年11月5日
     */
	public void saveAndUpdateDrugListRedisCache(DrugList drugList);
        
}
