/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月26日
 * Created by hhy
 */
package com.ctb.platform.rest.service;

import com.ctb.commons.entity.PaySettings;

/**
 * @ClassName: com.ctb.platform.rest.service.PaySettingsService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年3月26日 下午9:49:09
 */

public interface PaySettingsService {

	public PaySettings selectById(String id);

}
