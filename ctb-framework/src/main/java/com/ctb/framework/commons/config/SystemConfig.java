package com.ctb.framework.commons.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ctb.framework.commons.utils.SpringContextHolder;

/**
 * 
 * @ClassName: com.ctb.framework.commons.config.SystemConfig
 * @Description: TODO(系统参数初始化)
 * @author cwq
 * @date 2019年4月3日 下午3:33:28
 */
public class SystemConfig {
    private static Logger logger = LoggerFactory.getLogger(SystemConfig.class);
    /**
     * 系统参数集合
     */
    private static Map<String, String> systemConfigMap = new HashMap<String, String>();
    
    /**
     * 业务规则集合
     */
    
    private SystemConfig() {
        
    }
    
    /**
     * 加载系统配置文件
     */
    public static void loadSystemConfig() {
        CtbPropertyPlaceholderConfigurer propsConfig = SpringContextHolder
                .getBean(CtbPropertyPlaceholderConfigurer.class);
        
        try {
            Properties props = propsConfig.mergeProperties();
            systemConfigMap.putAll(propsConfig.convertPropsToMap(props));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (logger.isDebugEnabled()) {
            logger.debug("system params:{}", systemConfigMap);
        }
    }
    
    /**
     * 添加参数
     * 
     * @param key
     * @param value
     */
    public static void put(String key, String value) {
        systemConfigMap.put(key, value);
    }
    
    /**
     * 根据key值返回value
     * 
     * @param key
     * @return
     */
    private static String getValue(String key) {
        return systemConfigMap.get(key);
    }
    
    /**
     * 根据key值返回String类型的value.
     */
    public static String getStringValue(String key) {
        return getValue(key);
    }
    
    /**
     * 根据key值返回Integer类型的value.如果都為Null則返回Default值，如果内容错误则抛出异常
     */
    public static String getStringValue(String key, String defaultValue) {
        String value = getValue(key);
        return value != null ? value : defaultValue;
    }
    
    /**
     * 根据key值返回Integer类型的value.如果都為Null或内容错误则抛出异常.
     */
    public static Integer getIntegerValue(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Integer.valueOf(value);
    }
    
    /**
     * 根据key值返回Integer类型的value.如果都為Null則返回Default值，如果内容错误则抛出异常
     */
    public static Integer getInteger(String key, Integer defaultValue) {
        String value = getValue(key);
        return value != null ? Integer.valueOf(value) : defaultValue;
    }
    
    /**
     * 根据key值返回Double类型的value.如果都為Null或内容错误则抛出异常.
     */
    public static Double getDoubleValue(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Double.valueOf(value);
    }
    
    /**
     * 根据key值返回Double类型的value.如果都為Null則返回Default值，如果内容错误则抛出异常
     */
    public static Double getDoubleValue(String key, Integer defaultValue) {
        String value = getValue(key);
        return value != null ? Double.valueOf(value) : defaultValue;
    }
    
    /**
     * 根据key值返回Boolean类型的value.如果都為Null抛出异常,如果内容不是true/false则返回false.
     */
    public static Boolean getBooleanValue(String key) {
        String value = getValue(key);
        if (value == null) {
            throw new NoSuchElementException();
        }
        return Boolean.valueOf(value);
    }
    
    /**
     * 根据key值返回Boolean类型的value.如果都為Null則返回Default值,如果内容不为true/false则返回false.
     */
    public static Boolean getBooleanValue(String key, boolean defaultValue) {
        String value = getValue(key);
        return value != null ? Boolean.valueOf(value) : defaultValue;
    }
}
