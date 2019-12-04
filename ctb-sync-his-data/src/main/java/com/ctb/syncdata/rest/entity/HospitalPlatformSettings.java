package com.ctb.syncdata.rest.entity;

import javax.persistence.*;

@Table(name = "hospital_platform_settings")
public class HospitalPlatformSettings {
    /**
     * ID
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 医院ID
     */
    @Column(name = "HOSPITAL_ID")
    private String hospitalId;

    /**
     * 平台ID
     */
    @Column(name = "PLATFORM_ID")
    private String platformId;

    /**
     * 应用平台ID
     */
    @Column(name = "APP_ID")
    private String appId;

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
     * 获取医院ID
     *
     * @return HOSPITAL_ID - 医院ID
     */
    public String getHospitalId() {
        return hospitalId;
    }

    /**
     * 设置医院ID
     *
     * @param hospitalId 医院ID
     */
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId == null ? null : hospitalId.trim();
    }

    /**
     * 获取平台ID
     *
     * @return PLATFORM_ID - 平台ID
     */
    public String getPlatformId() {
        return platformId;
    }

    /**
     * 设置平台ID
     *
     * @param platformId 平台ID
     */
    public void setPlatformId(String platformId) {
        this.platformId = platformId == null ? null : platformId.trim();
    }

    /**
     * 获取应用平台ID
     *
     * @return APP_ID - 应用平台ID
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置应用平台ID
     *
     * @param appId 应用平台ID
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }
}