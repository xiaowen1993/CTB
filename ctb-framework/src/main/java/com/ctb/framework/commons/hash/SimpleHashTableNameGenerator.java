package com.ctb.framework.commons.hash;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: com.ctb.framework.commons.hash.SimpleHashTableNameGenerator
 * @Description: TODO(hash取模算法分表)
 * @author cwq
 * @date 2019年3月22日 下午4:30:53
 */
public class SimpleHashTableNameGenerator {
    private static final Logger logger = LoggerFactory.getLogger(SimpleHashTableNameGenerator.class);
    /**
     * 分表的子表数
     */
    public static final int subTableCount = 8;
    
    /**
     * AI处方记录的表名
     */
    public static final String PRESCRIPTION_RECORD_TABLE_NAME = "BIZ_PRESCRIPTION_RECORD";
    
    /**
     * 根据openId为关键值 得到AI处方记录的 hash分表
     * 
     * @param openId
     * @return
     */
    public static String getPrescriptionHashTable(Object splitKeyVal, Boolean isNeedUniform) {
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
     * @date 2019年3月22日 下午4:36:52
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
    private static String getSplitTableName(String tableName, Object splitKeyVal, Integer subTableCount,
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
    
    public static void main(String[] args) {
        System.out.println(getPrescriptionHashTable("b296582f8d6b43b0b02c754a2b67d8c68", true));
        
    }
}
