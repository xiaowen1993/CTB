/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月22日
 * Created by hhy
 */
package com.ctb.platform.rest.service;

import java.util.List;
import java.util.Map;

import com.ctb.commons.entity.DrugBlackList;

/**
 * @ClassName: com.ctb.platform.rest.service.durgList
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年3月22日 下午4:16:57
 */

public interface DrugBlackListService {
    
    /**
     * 
     * @Title: saveDrugBlackList
     * @Description: 新增黑名单药品
     * @author hhy
     * @date 2019年3月25日 下午3:45:54
     * @param drugBlackList
     * @return
     * @throws Exception
     */
    public int saveDrugBlackList(DrugBlackList drugBlackList) throws Exception;

    /**
     * 
     * @Title: deleteDrugBlackList
     * @Description: 通过黑名单药品ID删除黑名单药品
     * @author hhy
     * @date 2019年3月25日 下午3:46:23
     * @param drugBlackListId
     * @return
     */
    public int deleteDrugBlackList(String [] drugBlackListIds, String hospitalCode);
    
 
    /**
     * 
     * @Title: queryDrugBlackListByExample
     * @Description: 获取黑名单列表
     * @author hhy
     * @date 2019年3月25日 下午3:47:04
     * @param map
     * @return
     */
    public List<DrugBlackList> queryDrugBlackListByExample(Map <String, Object> map);
    
    /**
     * 
     * @Title: queryDrugBlackListPagedByExample
     * @Description: 获取黑名单列表--分页
     * @author hhy
     * @date 2019年3月25日 下午3:47:20
     * @param map
     * @param page
     * @param pageSize
     * @return
     */
    public List<DrugBlackList> queryDrugBlackListPagedByExample(Map <String, Object> map, Integer page, Integer pageSize);

    /**
     * 
     * @Title: initDrugBlackListRedisCache
     * @Description: 初始化药品黑名单缓存
     * @author hhy
     * @date 2019年3月30日 下午12:11:15
     */
    public void initHospitalDrugBlackListCache();
    
}
