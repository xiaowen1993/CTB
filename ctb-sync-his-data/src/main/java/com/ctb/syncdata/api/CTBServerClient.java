/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月2日
 * Created by cwq
 */
package com.ctb.syncdata.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ctb.framework.commons.entity.RespBody;

/**
 * @ClassName: com.ctb.syncdata.api.OutSiteClient
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月2日 下午5:50:04
 */

@FeignClient(value = "ctb-mobile-service", path = "/outsite")
public interface CTBServerClient {

	@RequestMapping(value = "/matchDrugs", method = RequestMethod.GET)
	public RespBody matchDrugs(@RequestParam(required = true, value = "openId") String openId,
			@RequestParam(required = true, value = "hospitalCode") String hospitalCode,
			@RequestParam(required = true, value = "itemIds") String[] itemIds);

	@RequestMapping(value = "/isFocusOn")
	public RespBody isFocusOn(@RequestParam(required = true, value = "unionId") String unionId,
			@RequestParam(required = true, value = "hospitalOpenId") String hospitalOpenId,
			@RequestParam(required = true, value = "hospitalCode") String hospitalCode,
			@RequestParam(required = false, value = "branchCode") String branchCode,
			@RequestParam(required = true, value = "mzfeeId") String mzfeeId,
			@RequestParam(required = true, value = "cardNo") String cardNo,
			@RequestParam(required = false, value = "cardType") String cardType);

	@RequestMapping(value = "/mzDetail")
	public RespBody mzDetail(@RequestParam(required = true, value = "openId", defaultValue = "") String openId,
			@RequestParam(required = false, value = "hospitalOpenId", defaultValue = "") String hospitalOpenId,
			@RequestParam(required = true, value = "unionId", defaultValue = "") String unionId,
			@RequestParam(required = true, value = "hospitalCode", defaultValue = "") String hospitalCode,
			@RequestParam(required = false, value = "branchHospitalCode", defaultValue = "") String branchHospitalCode,
			@RequestParam(required = true, value = "cardNo", defaultValue = "") String cardNo,
			@RequestParam(required = true, value = "cardType", defaultValue = "") String cardType,
			@RequestParam(required = true, value = "mzFeeId", defaultValue = "") String mzFeeId);

}
