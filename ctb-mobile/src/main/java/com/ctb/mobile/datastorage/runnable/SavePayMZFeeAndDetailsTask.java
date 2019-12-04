/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月10日
 * Created by ckm
 */
package com.ctb.mobile.datastorage.runnable;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ctb.commons.entity.DataPayMzFee;
import com.ctb.commons.entity.DataPayMzFeeDetail;
import com.ctb.framework.commons.utils.SpringContextHolder;
import com.ctb.mobile.rest.service.DataPayMZFeeService;

/**
 * @ClassName: com.ctb.mobile.rest.thread.SaveTask
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月10日 上午9:49:21
 */

public class SavePayMZFeeAndDetailsTask implements Runnable {

	private Logger logger = LoggerFactory.getLogger(SavePayMZFeeAndDetailsTask.class);

    private DataPayMZFeeService dataPayMZFeeService = SpringContextHolder.getBean(DataPayMZFeeService.class);
    
	private DataPayMzFee dataPayMzFee;

	private List<DataPayMzFeeDetail> dataPayMzFeeDetails;

	public SavePayMZFeeAndDetailsTask() {
		super();
	}

	public SavePayMZFeeAndDetailsTask(DataPayMzFee dataPayMzFee, List<DataPayMzFeeDetail> dataPayMzFeeDetails) {
		super();
		this.dataPayMzFee = dataPayMzFee;
		this.dataPayMzFeeDetails = dataPayMzFeeDetails;
	}

	@Override
	public void run() {
		try {
			 dataPayMZFeeService.saveDataPayMzFeeAndDataPayMzFeeDetails(dataPayMzFee, dataPayMzFeeDetails);
		} catch (Exception e) {
			logger.error(e.toString());
			System.err.println(e.toString());
		}
	}

}
