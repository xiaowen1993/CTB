/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月29日
 * Created by cwq
 */
package com.ctb.accesstoken.initdata;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.ctb.framework.commons.config.SystemConfig;
import com.ctb.framework.commons.config.CtbPropertyPlaceholderConfigurer;
import com.ctb.framework.commons.utils.SpringContextHolder;

/**
 * @ClassName: com.ctb.platform.initdata.InitCacheRunner
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年3月29日 下午4:32:16
 */

@Component
public class InitdataRunner implements CommandLineRunner {
    
    @Override
    public void run(String... args) throws Exception {
        // TODO Auto-generated method stub
        
        /**
         * 初始化设置需要读取properties的文件
         */
        CtbPropertyPlaceholderConfigurer propsConfig = SpringContextHolder
                .getBean(CtbPropertyPlaceholderConfigurer.class);
        propsConfig.setSystemPropertiesModeName("SYSTEM_PROPERTIES_MODE_OVERRIDE");
        propsConfig.setIgnoreResourceNotFound(false);
        propsConfig.setLocations(new Resource[] { new ClassPathResource("service.properties"), });
        
        /**
         * 初始化properties配置文件
         */
        SystemConfig.loadSystemConfig();
        
    }
    
}
