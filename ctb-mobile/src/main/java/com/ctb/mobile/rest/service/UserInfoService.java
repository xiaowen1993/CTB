/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月8日
 * Created by ckm
 */
package com.ctb.mobile.rest.service;

import com.ctb.commons.entity.UserInfo;

/**
 * @ClassName: com.ctb.mobile.rest.service.UserInfoService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月8日 下午7:51:18
 */

public interface UserInfoService {

	/**
	 * 	判断是否存在数据
	 * @Title: isUnique
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月8日 下午8:10:42
	 * @param userInfo
	 * @return
	 */
	public boolean isUnique(UserInfo userInfo);
	
	/**
	 * 	保存
	 * @Title: save
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月8日 下午8:11:16
	 * @param userInfo
	 * @return
	 */
	public int save(UserInfo userInfo);
}
