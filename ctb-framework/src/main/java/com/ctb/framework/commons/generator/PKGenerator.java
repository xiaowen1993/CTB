package com.ctb.framework.commons.generator;

import java.util.UUID;

/**
 * 
 * @ClassName: com.ctb.framework.commons.generator.PKGenerator
 * @Description: TODO(主键ID 生成器)
 * @author cwq
 * @date 2019年3月22日 下午4:26:58
 */
public class PKGenerator {
    /**
     * 
     * @Title: generateId
     * @Description: TODO(生成数据库主键ID值)
     * @author cwq
     * @date 2019年3月22日 下午4:42:51
     * @return
     */
    public static String generateId() {
        String s = UUID.randomUUID().toString();
        return s.replaceAll("-", "");
    }
    
}
