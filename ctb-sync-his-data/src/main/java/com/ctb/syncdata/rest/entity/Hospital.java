package com.ctb.syncdata.rest.entity;

import java.io.Serializable;

import javax.persistence.*;

@Table(name = "hospital")
public class Hospital implements Serializable{
    
    /**
     *
     */
    	
    private static final long serialVersionUID = 5337215958126426818L;

    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 医院名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 医院代码
     */
    @Column(name = "CODE")
    private String code;

    /**
     * 0已签约1已上线...
     */
    @Column(name = "STATE")
    private Integer state;

    /**
     * 获取ID
     *
     * @return ID - ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取医院名称
     *
     * @return NAME - 医院名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置医院名称
     *
     * @param name 医院名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取医院代码
     *
     * @return CODE - 医院代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置医院代码
     *
     * @param code 医院代码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取0已签约1已上线...
     *
     * @return STATE - 0已签约1已上线...
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置0已签约1已上线...
     *
     * @param state 0已签约1已上线...
     */
    public void setState(Integer state) {
        this.state = state;
    }
}