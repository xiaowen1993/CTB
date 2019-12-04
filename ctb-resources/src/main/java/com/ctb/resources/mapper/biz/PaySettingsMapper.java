package com.ctb.resources.mapper.biz;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ctb.commons.entity.PaySettings;
import com.ctb.data.persistence.mapper.CtbMapper;

@Repository
public interface PaySettingsMapper extends CtbMapper<PaySettings> {

	/**
	 * 支付配置查询
	 * @Title: findPaySettingByPharmacyId
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author ckm
	 * @date 2019年4月30日 下午2:46:27
	 * @param param
	 * @return
	 */
	public PaySettings findPaySettingByPharmacyId(Map<String, String> param);
}