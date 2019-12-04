package com.ctb.framework.commons.hash;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.ctb.framework.commons.utils.Hashing;
import com.ctb.framework.commons.utils.MurmurHash;

/**
 * 
 * @ClassName: com.ctb.framework.commons.hash.HashingTableNameGenerator
 * @Description: TODO(hash一致性算法分表)
 * @author cwq
 * @date 2019年3月22日 下午4:27:43
 */
public class HashingTableNameGenerator {
    /**
     * 虚拟节点数
     */
    public static final int DUMMY_NODE_NUM = 3000;
    
    /**
     * MurmurHash算法
     */
    public static final Hashing MURMUR_HASH = new MurmurHash();
    
    /**
     * hash存储
     */
    private static final Map<String, TreeMap<Long, String>> resources = new LinkedHashMap<String, TreeMap<Long, String>>();
    public static final int subTableCount = 8;
    
    /**
     * AI处方记录的表名
     */
    public static final String PRESCRIPTION_RECORD_TABLE_NAME = "BIZ_PRESCRIPTION_RECORD";
    
    public static String[] tableNames = new String[] { PRESCRIPTION_RECORD_TABLE_NAME };
    
    static {
        TreeMap<Long, String> nodes = null;
        List<String> subTableNames = null;
        for (String tableName : tableNames) {
            subTableNames = new ArrayList<String>();
            for (int i = 1; i < subTableCount + 1; i++) {
                subTableNames.add(tableName.concat("_") + i);
            }
            nodes = new TreeMap<Long, String>();
            for (int i = 0; i != subTableNames.size(); ++i) {
                final String subTableName = subTableNames.get(i);
                for (int n = 0; n < DUMMY_NODE_NUM; n++) {
                    nodes.put(MURMUR_HASH.hash("hashing" + i + "-table-" + n), subTableName);
                }
            }
            resources.put(tableName, nodes);
        }
    }
    
    public static String getHashTableName(String tableName, String openId) {
        String hashTableName = null;
        TreeMap<Long, String> nodes = resources.get(tableName);
        SortedMap<Long, String> tail = nodes.tailMap(MURMUR_HASH.hash(openId));
        if (tail.isEmpty()) {
            hashTableName = nodes.get(nodes.firstKey());
        } else {
            hashTableName = tail.get(tail.firstKey());
        }
        return hashTableName;
    }
    
    /**
     * 
     * @Title: getPrescriptionHashTable
     * @Description: TODO(根据openId为关键值 得到AI处方订单的 hash分表)
     * @author cwq
     * @date 2019年3月22日 下午4:40:06
     * @param openId
     * @return
     */
    public static String getPrescriptionHashTable(String openId) {
        return getHashTableName(PRESCRIPTION_RECORD_TABLE_NAME, openId);
    }
    
}
