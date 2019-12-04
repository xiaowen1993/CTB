/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月29日
 * Created by cwq
 */
package com.ctb.mobile.rest.service;

import com.ctb.mobile.rest.entity.payrefund.WechatPayRefund;
import com.ctb.mobile.rest.entity.payrefund.WechatPayRefundResponse;

/**
 * @ClassName: com.ctb.mobile.rest.service.RefundService
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月29日 下午8:50:19
 */

public interface RefundService {
   
    /**
     * 
     * @Title: wechatPayRefund
     * @Description: TODO(微信退费)
     * @author cwq
     * @date 2019年5月5日 下午5:17:03
     * @param refund
     * @return
     */
    public WechatPayRefundResponse wechatPayRefund(WechatPayRefund refund);
}
