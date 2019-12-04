package com.ctb.framework.commons.generator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.hpl.sparta.xpath.ThisNodeTest;

/**
 * 
 * @ClassName: com.ctb.framework.commons.generator.HashSplitTableGenerator
 * @Description: TODO(hash取模算法分表表名生成器)
 * @author cwq
 * @date 2019年3月22日 下午4:25:32
 */
public class HashSplitTableGenerator {
    private static final Logger logger = LoggerFactory.getLogger(HashSplitTableGenerator.class);
    
    /**
     * 分表的子表数
     */
    public static final int subTableCount = 8;
    
    /**
     * 处方记录的表名
     */
    public static final String PRESCRIPTION_RECORD_TABLE_NAME = "BIZ_PRESCRIPTION_RECORD";
    
    /**
     * 
     * @Title: getPrescriptionRecordHashTable
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @author cwq
     * @date 2019年4月10日 下午2:45:22
     * @param splitKeyVal
     *            通常是用户的openId
     * @param isNeedUniform
     *            是否需要均匀散列，一般为true
     * @return
     */
    public static String getPrescriptionRecordHashTable(Object splitKeyVal, Boolean isNeedUniform) {
        String hashTableName = getSplitTableName(PRESCRIPTION_RECORD_TABLE_NAME, splitKeyVal, subTableCount,
                isNeedUniform);
        if (logger.isDebugEnabled()) {
            logger.debug("openId:{}  hashTable name:{}", new Object[] { splitKeyVal, hashTableName });
        }
        return hashTableName;
    }
    
    /**
     * 
     * @Title: getSplitTableName
     * @Description: TODO(得到hsah子表名)
     * @author cwq
     * @date 2019年3月22日 下午4:43:31
     * @param tableName
     *            原表名
     * @param splitKeyVal
     *            hash取模的关键值(根据splitKeyVal值来取模)
     * @param subTableCount
     *            要拆分子表总数
     * @param isNeedUniform
     *            是否需要均匀散列
     * @return
     */
    public static String getSplitTableName(String tableName, Object splitKeyVal, Integer subTableCount,
            Boolean isNeedUniform) {
        long hashVal = 0;
        if (splitKeyVal instanceof Number) {
            hashVal = Integer.parseInt(splitKeyVal.toString());
        } else {
            hashVal = splitKeyVal.toString().hashCode();
        }
        
        // 斐波那契（Fibonacci）散列
        if (isNeedUniform) {
            hashVal = (hashVal * 2654435769L) >> 28;
        }
        
        // 避免hashVal超出 MAX_VALUE = 0x7fffffff时变为负数,取绝对值
        // hashVal = Math.abs(hashVal) % subTableCount;
        hashVal = (hashVal & 0xff) % subTableCount;
        String splitableName = tableName + "_" + (hashVal + 1);
        return splitableName;
    }
    
}
