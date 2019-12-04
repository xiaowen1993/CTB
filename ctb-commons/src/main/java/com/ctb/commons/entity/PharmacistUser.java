/**
 * Copyright(C)版权所有 - 2019 广东城市宠儿新商务有限公司.
 * All rights reserved.
 * Created on 2019年7月2日
 * Created by hhy
 */
package com.ctb.commons.entity;
import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
/**
 * @ClassName: com.ctb.commons.entity.Pharmacist
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author hhy
 * @date 2019年7月2日 下午4:37:42
 */

@Table(name = "pharmacist_user")
public class PharmacistUser implements Serializable{
    
    /**
     *
     */
        
    private static final long serialVersionUID = 2731414770727326066L;

    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 药店用户登录名
     */
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "LOGIN_NAME")
    private String loginName;

    /**
     * 登录名密码
     */
    @Column(name = "PASSWORD")
    private String password;
    
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "STATUS")
    private String status;

    /**
     * 最后登录时间
     */
    @Column(name = "LAST_LOGIN_TIME")
    private Long lastLoginTime;
    
    @Transient
    private List<String> hospitalCodes;

    /**
     * @return ID
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取药店用户登录名
     *
     * @return NAME - 药店用户登录名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置药店用户登录名
     *
     * @param name 药店用户登录名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取登录名密码
     *
     * @return PASSWORD - 登录名密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置登录名密码
     *
     * @param password 登录名密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * @return STATUS
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取最后登录时间
     *
     * @return LAST_LOGIN_TIME - 最后登录时间
     */
    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置最后登录时间
     *
     * @param lastLoginTime 最后登录时间
     */
    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getHospitalCodes() {
        return hospitalCodes;
    }

    public void setHospitalCodes(List<String> hospitalCodes) {
        this.hospitalCodes = hospitalCodes;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    

}