/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月29日
 * Created by cwq
 */
package com.ctb.mobile.rest.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ctb.framework.commons.config.SystemConfig;
import com.ctb.framework.commons.entity.RespBody.Status;
import com.ctb.framework.commons.utils.HttpClientUtil;
import com.ctb.mobile.rest.entity.payrefund.WechatPayRefund;
import com.ctb.mobile.rest.entity.payrefund.WechatPayRefundResponse;
import com.ctb.mobile.rest.service.RefundService;

/**
 * @ClassName: com.ctb.mobile.rest.service.impl.RefundServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月29日 下午8:53:32
 */
@Service
public class RefundServiceImpl implements RefundService{


    private static Logger logger = LoggerFactory.getLogger(RefundServiceImpl.class);


    @Override
    public WechatPayRefundResponse wechatPayRefund(WechatPayRefund refund) {
        WechatPayRefundResponse payRefundResponse = null;
        try {
            String url = SystemConfig.getStringValue("TRADE_URL_PREFIX").concat(SystemConfig.getStringValue("TRADE_RESTFUL_REFUND_PATH"));
            String res = HttpClientUtil.getInstance().post(url, JSONObject.toJSONString(refund));
            
            if (StringUtils.isNotBlank(res)) {
                JSONObject resJson = JSONObject.parseObject(res);
                if (StringUtils.equals(resJson.getString("status"), Status.OK.toString())) {
                    payRefundResponse = resJson.getObject("message", WechatPayRefundResponse.class);
                }
            }
            
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
      
        return payRefundResponse;
    }
}
