/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月2日
 * Created by cwq
 */
package com.ctb.pharmacy.rest.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ctb.commons.constants.CacheConstants;
import com.ctb.commons.entity.DataPaidMzFeeDetail;
import com.ctb.commons.entity.DrugList;
import com.ctb.framework.commons.redis.RedisClient;
import com.ctb.pharmacy.rest.entity.vo.DrugDetailVo;
import com.ctb.pharmacy.rest.service.DrugListService;
import com.ctb.resources.mapper.biz.DataPaidMzFeeDetailMapper;
import com.ctb.resources.mapper.biz.DataPaidMzFeeMapper;
import com.ctb.resources.mapper.biz.DrugListMapper;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName: com.ctb.mobile.rest.service.impl.DrugListServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年4月2日 下午2:27:32
 */

@Service
public class DrugListServiceImpl implements DrugListService {

	private static Logger logger = LoggerFactory.getLogger(DrugListServiceImpl.class);

	@Autowired
	public DrugListMapper drugListMapper;

	@Autowired
	public DataPaidMzFeeDetailMapper dataPaidMzFeeDetailMapper;

	@Autowired
	private RedisClient redisClient;

	public List<DrugList> matchDrugs(String hospitalId, String[] itemIds) {
		// TODO Auto-generated method stub
		List<DrugList> drugLists = null;

		try {

			Example example = new Example(DrugList.class);
			Example.Criteria criteria = example.createCriteria();

			criteria.andEqualTo("id", hospitalId);
			criteria.andIn("hisDrugCode", Arrays.asList(itemIds));

			drugLists = drugListMapper.selectByExample(example);

		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error(
						"match DrugList from database error, make transfer error. hospitalId={}, itemIds={}, errorMsg={}, "
								+ "cause by: {}.",
						new Object[] { hospitalId, itemIds, e.getMessage(), e.getCause() });
			}
		}

		return drugLists;
	}

	/**
	 * 获取待配药详情里面的药品列表
	 */
	@Override
	public DrugList getDrug(String hospitalCode, String itemId) {
		try {
			List<JSONObject> val = (List<JSONObject>) redisClient
					.hget(CacheConstants.CACHE_HOSPITAL_DRUGLIST_HASH_KEY_PREFIX, hospitalCode);
			if (val != null) {
				Collection<JSONObject> result = contain(val, itemId);
				if (result.size() > 0) {
					String jsonStr = result.toString();
					JSONArray array = JSONArray.parseArray(jsonStr);
					return JSONObject.toJavaObject(array.getJSONObject(0), DrugList.class);
				}
			}
		} catch (Exception e) {
			logger.error(e.toString());
			return null;
		}
		return null;
	}

	private Collection<JSONObject> contain(List<JSONObject> array, String itemId) {
		try {
			Collection<JSONObject> result = null;
			if (array.size() > 0) {
				/**
				  * 将hisDrugCode字段属性一个个与itemId的值比较进行过滤
				 */
				result = Collections2.filter(array, new Predicate<JSONObject>() {
					@Override
					public boolean apply(JSONObject input) {
						return input.get("hisDrugCode").toString().contains(itemId);
					}
				});
			}
			return result;
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return array;
	}

	@Override
	public List<DrugDetailVo> detailVos(String hospitalCode, String mzfeeId) {
		try {
			List<DrugDetailVo> detailVos = new ArrayList<DrugDetailVo>();

			Example example = new Example(DataPaidMzFeeDetail.class);
			Example.Criteria criteria = example.createCriteria();
			criteria.andEqualTo("mzFeeId", mzfeeId);
			List<DataPaidMzFeeDetail> feeDetails = dataPaidMzFeeDetailMapper.selectByExample(example);
			if (feeDetails.size() > 0) {
				for (int i = 0; i < feeDetails.size(); i++) {
					DrugList drugList = getDrug(hospitalCode, feeDetails.get(i).getItemId());
					DrugDetailVo detailVo = new DrugDetailVo(drugList.getId(), drugList.getHospitalId(),
                            /* drugList.getHospitalCode(), */ drugList.getDrugName(), drugList.getDrugCode(),
							drugList.getCadn(), drugList.getApprovalNo(), drugList.getManufacturer(),
							drugList.getSpecification(), feeDetails.get(i).getItemNumber(), drugList.getHisDrugCode(),
							drugList.getUsage());
					if(detailVo!=null) {						
						detailVos.add(detailVo);
					}
				}
			}
			return detailVos;
		} catch (Exception e) {
			logger.error(e.toString());
			return new ArrayList<DrugDetailVo>();
		}
	}

}
