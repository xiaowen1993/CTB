package com.ctb.mobile.utils;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.ctb.commons.constants.BizConstant;
import com.ctb.commons.entity.PrescriptionRecord;
import com.ctb.framework.commons.config.SystemConfig;
import com.ctb.mobile.rest.entity.payrefund.Pay;
import com.ctb.mobile.rest.entity.payrefund.Refund;
import com.ctb.mobile.rest.entity.payrefund.WechatPay;
import com.ctb.mobile.rest.entity.payrefund.WechatPayOrderQuery;
import com.ctb.mobile.rest.entity.payrefund.WechatPayRefund;

/**
 * 
 * @ClassName: com.ctb.mobile.utils.TradeCommonHoder
 * @Description: TODO(交易公共处理类)
 * @author cwq
 * @date 2019年4月29日 下午6:04:03
 */
public class TradeCommonHoder {
	private static Logger logger = LoggerFactory.getLogger(TradeCommonHoder.class);

	/**
	 * 构建微信支付信息-处方平台
	 * 描述: TODO
	 * @author Caiwq
	 * @date 2017年7月12日 
	 * @param record
	 * @return
	 */
	public static WechatPay buildWechatPayInfo(PrescriptionRecord record) {
		WechatPay wechatPay = new WechatPay();

		Pay pay = buildPayBaseInfo(record);
		BeanUtils.copyProperties(pay, wechatPay);

		Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(record.getUpdateTime());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        
		Long timeout = calendar.getTimeInMillis() - record.getUpdateTime();
		wechatPay.setTimeout(String.valueOf(timeout / 1000));

		wechatPay.setComponentOauth2(false);
		
		return wechatPay;
	}


	/**
	 * 
	 * @Title: buildWechatPayRefundInfo
	 * @Description: TODO(构建微信退费信息-处方平台)
	 * @author cwq
	 * @date 2019年4月29日 下午8:29:28
	 * @param record
	 * @return
	 */
	public static WechatPayRefund buildWechatPayRefundInfo(PrescriptionRecord record) {
		WechatPayRefund wechatPayRefund = new WechatPayRefund();
		Refund refund = buildRefundBaseInfo(record);
		BeanUtils.copyProperties(refund, wechatPayRefund);

		return wechatPayRefund;
	}

	/**
	 * 
	 * @Title: buildPayBaseInfo
	 * @Description: TODO(构建基础支付信息)
	 * @author cwq
	 * @date 2019年4月29日 下午6:41:22
	 * @param object
	 * @return
	 */
	public static Pay buildPayBaseInfo(Object object) {
		Pay pay = new Pay();

		if (object instanceof PrescriptionRecord) {
		    PrescriptionRecord record = (PrescriptionRecord) object;

			StringBuffer sb = new StringBuffer();
			sb.append("处方外流：");
			sb.append(record.getDeptName().concat("-").concat(record.getDoctorName()));

			pay.setCode(record.getPharmacyCode());
			pay.setTradeMode(String.valueOf(record.getTradeMode()));
			pay.setOrderNo(record.getOrderNo());
			pay.setTotalFee(String.valueOf(record.getTotalFee()));
			pay.setBody(sb.toString());
			pay.setPaySuccessPageUrl(SystemConfig.getStringValue("PAY_SUCCESS_URL_DOMAIN") + SystemConfig.getStringValue("PAY_SUCCESS_URL_PATH"));
			pay.setNotifyUrl(SystemConfig.getStringValue("SYNC_NOTIFY_URL_DOMAIN") + SystemConfig.getStringValue("SYNC_NOTIFY_URL_PATH"));

		}

		return pay;
	}

	/**
	 * 
	 * @Title: buildRefundBaseInfo
	 * @Description: TODO(构建基础退费信息)
	 * @author cwq
	 * @date 2019年4月29日 下午6:41:42
	 * @param object
	 * @return
	 */
	public static Refund buildRefundBaseInfo(Object object) {
		Refund refund = new Refund();

		if (object instanceof PrescriptionRecord) {
		    PrescriptionRecord record = (PrescriptionRecord) object;

			StringBuffer sb = new StringBuffer();
			sb.append(BizConstant.URL_PARAM_ORDER_NO + BizConstant.URL_PARAM_CHAR_ASSGIN + record.getOrderNo());
			sb.append(BizConstant.URL_PARAM_CHAR_CONCAT + BizConstant.URL_PARAM_OPEN_ID + BizConstant.URL_PARAM_CHAR_ASSGIN
					+ record.getOpenId());

			refund.setCode(record.getPharmacyCode());
			refund.setTradeMode(String.valueOf(record.getTradeMode()));
			refund.setOrderNo(record.getOrderNo());
			refund.setAgtOrderNo(record.getAgtOrdNum());
			refund.setRefundOrderNo(String.valueOf(record.getRefundOrderNo()));
			refund.setTotalFee(String.valueOf(record.getTotalFee()));
			refund.setRefundFee(String.valueOf(record.getTotalFee()));
			refund.setRefundDesc(sb.toString());
		} 
		
		return refund;
	}

	/**
	 * 
	 * @Title: buildWechatPayOrderQueryInfo
	 * @Description: TODO( 构建微信订单查询信息-处方平台)
	 * @author cwq
	 * @date 2019年4月29日 下午8:18:14
	 * @param record
	 * @return
	 */
	public static WechatPayOrderQuery buildWechatPayOrderQueryInfo(PrescriptionRecord record) {
		WechatPayOrderQuery wechatPayOrderQuery = new WechatPayOrderQuery();
		wechatPayOrderQuery.setCode(record.getPharmacyCode());
		wechatPayOrderQuery.setTradeMode(String.valueOf(record.getTradeMode()));
		wechatPayOrderQuery.setOrderNo(record.getOrderNo());
		wechatPayOrderQuery.setAgtOrderNo(record.getAgtOrdNum());

		return wechatPayOrderQuery;
	}

}
