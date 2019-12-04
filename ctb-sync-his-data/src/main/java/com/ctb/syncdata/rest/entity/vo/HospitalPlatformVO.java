/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年3月30日
 * Created by cwq
 */
package com.ctb.syncdata.rest.entity.vo;

import java.io.Serializable;

/**
 * @ClassName: com.ctb.syncdata.rest.entity.vo.HospitalPlatformVO
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author cwq
 * @date 2019年3月30日 上午11:59:37
 */

public class HospitalPlatformVO implements Serializable {
    
    /**
     *
     */
    
    private static final long serialVersionUID = -4402026043067786778L;
    
    private String id;
    private String name;
    private String code;
    private Integer state;
    private String appId;
    
    private String platformName;
    private Integer platformCode;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public Integer getState() {
        return state;
    }
    
    public void setState(Integer state) {
        this.state = state;
    }
    
    public String getAppId() {
        return appId;
    }
    
    public void setAppId(String appId) {
        this.appId = appId;
    }
    
    public String getPlatformName() {
        return platformName;
    }
    
    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }
    
    public Integer getPlatformCode() {
        return platformCode;
    }
    
    public void setPlatformCode(Integer platformCode) {
        this.platformCode = platformCode;
    }
    
}
