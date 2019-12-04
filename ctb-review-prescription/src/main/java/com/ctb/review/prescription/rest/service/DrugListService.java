/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月2日
 * Created by cwq
 */
package com.ctb.review.prescription.rest.service;

import java.util.List;

import com.ctb.commons.entity.DrugList;
/**
 * 
 * @ClassName: com.ctb.pharmacy.rest.service.DrugListService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月16日 上午10:37:55
 */
public interface DrugListService {
    
    public List<DrugList> matchDrugs(String hospitalId ,String[] itemIds);
    
    public DrugList getDrug(String hospitalCode,String itemId);
    
}
