/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年4月10日
 * Created by ckm
 */
package com.ctb.mobile.datastorage.runnable;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ctb.commons.entity.DataPayMzFee;
import com.ctb.commons.entity.DataPayMzFeeDetail;

/**
 * @ClassName: com.ctb.mobile.rest.thread.SaveDataThreadPool
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ckm
 * @date 2019年4月10日 上午9:50:33
 */

public class SavePayMZFeeAndDetailsThreadPool {

	private Logger logger = LoggerFactory.getLogger(SavePayMZFeeAndDetailsThreadPool.class);

	private DataPayMzFee dataPayMzFee;

	private List<DataPayMzFeeDetail> dataPayMzFeeDetails;

	public SavePayMZFeeAndDetailsThreadPool() {
		super();
	}

	public SavePayMZFeeAndDetailsThreadPool(DataPayMzFee dataPayMzFee, List<DataPayMzFeeDetail> dataPayMzFeeDetails) {
		super();
		this.dataPayMzFee = dataPayMzFee;
		this.dataPayMzFeeDetails = dataPayMzFeeDetails;
	}

	public void dorun() throws Exception {
		try {
			int nThreads = Runtime.getRuntime().availableProcessors();
			ExecutorService fixedThreadPool = Executors.newFixedThreadPool(nThreads);
			fixedThreadPool.execute(new SavePayMZFeeAndDetailsTask(dataPayMzFee, dataPayMzFeeDetails));
		//	fixedThreadPool.shutdown();
		} catch (Exception e) {
			logger.error("SavePayMZFeeAndDetailsThreadPool线程执行异常！:::" + e.toString());
		}

	}
}
