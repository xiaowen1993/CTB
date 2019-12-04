package com.ctb.syncdata.rest.entity;

import javax.persistence.*;

@Table(name = "platform")
public class Platform {
    /**
     * 编号
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 平台名字
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 平台代码
     */
    @Column(name = "CODE")
    private Integer code;

    /**
     * 平台代码简拼
     */
    @Column(name = "SHORT_NAME")
    private String shortName;

    /**
     * 获取编号
     *
     * @return ID - 编号
     */
    public String getId() {
        return id;
    }

    /**
     * 设置编号
     *
     * @param id 编号
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 获取平台名字
     *
     * @return NAME - 平台名字
     */
    public String getName() {
        return name;
    }

    /**
     * 设置平台名字
     *
     * @param name 平台名字
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取平台代码
     *
     * @return CODE - 平台代码
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 设置平台代码
     *
     * @param code 平台代码
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 获取平台代码简拼
     *
     * @return SHORT_NAME - 平台代码简拼
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 设置平台代码简拼
     *
     * @param shortName 平台代码简拼
     */
    public void setShortName(String shortName) {
        this.shortName = shortName == null ? null : shortName.trim();
    }
}