/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月10日
 * Created by cwq
 */
package com.ctb.mobile.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ctb.framework.commons.entity.RespBody;

/**
 * @ClassName: com.ctb.mobile.api.YXWoutClient
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月10日 上午10:28:07
 */

@FeignClient(value = "ctb-sync-his-data-service", path = "/yxwout")
public interface YXWOutClient {
    
    @RequestMapping(value = "/loadMZListData", method = RequestMethod.GET)
    public RespBody loadMZListData(@RequestParam(required = true, value = "hospitalCodes") String[] hospitalCodes,
            @RequestParam(required = true, value = "unionId") String unionId,
            @RequestParam(required = false, value = "openId") String openId,
            @RequestParam(required = false, value = "cardNo") String cardNo);
    
    @RequestMapping(value = "/loadMZDetailsData", method = RequestMethod.GET)
    public RespBody loadMZDetailsData(@RequestParam(required = false, value = "openId") String openId,
            @RequestParam(required = true, value = "hospitalCode") String hospitalCode,
            @RequestParam(required = false, value = "branchHospitalCode") String branchHospitalCode,
            @RequestParam(required = true, value = "cardNo") String cardNo,
            @RequestParam(required = false, value = "cardType") String cardType,
            @RequestParam(required = true, value = "mzFeeId") String mzFeeId);
    
    @RequestMapping(value = "/loadMedicalCardInfo", method = RequestMethod.GET)
    public RespBody loadMedicalCardInfo(@RequestParam(required = true, value = "openId") String openId,
            @RequestParam(required = true, value = "hospitalCode") String hospitalCode,
            @RequestParam(required = true, value = "cardNo") String cardNo);
}
