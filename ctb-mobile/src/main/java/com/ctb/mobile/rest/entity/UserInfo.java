package com.ctb.mobile.rest.entity;

import javax.persistence.*;

@Table(name = "user_info")
public class UserInfo {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 开放平台用户统一ID
     */
    @Column(name = "REF_UNION_ID")
    private String refUnionId;

    /**
     * 医院服务号用户openid
     */
    @Column(name = "HIS_OPEN_ID")
    private String hisOpenId;

    /**
     * 姓名
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 性别(1男，2女)
     */
    @Column(name = "SEX")
    private Integer sex;

    /**
     *  年龄
     */
    @Column(name = "AGE")
    private Integer age;

    /**
     * 出生日期
     */
    @Column(name = "BIRTH")
    private String birth;

    /**
     * 手机号码
     */
    @Column(name = "MOBILE")
    private String mobile;

    /**
     * 证件类型（配置）
     */
    @Column(name = "ID_TYPE")
    private Integer idType;

    /**
     * 证件号码
     */
    @Column(name = "ID_NO")
    private String idNo;

    /**
     * 地址
     */
    @Column(name = "ADDRESS")
    private String address;

    /**
     * 卡类型（配置）
     */
    @Column(name = "CARD_TYPE")
    private Integer cardType;

    /**
     * 就诊卡号
     */
    @Column(name = "CARD_NO")
    private String cardNo;

    /**
     * 监护人名称
     */
    @Column(name = "GUARD_NAME")
    private String guardName;

    /**
     * 监护人证件类型（配置）
     */
    @Column(name = "GUARD_ID_TYPE")
    private Integer guardIdType;

    /**
     * 监护人证件号码
     */
    @Column(name = "GUARD_ID_NO")
    private String guardIdNo;

    /**
     * 监护人手机号码
     */
    @Column(name = "GUARD_MOBILE")
    private String guardMobile;

    /**
     * 医院编号
     */
    @Column(name = "HOSPITAL_ID")
    private String hospitalId;

    /**
     * 医院代码
     */
    @Column(name = "HOSPITAL_CODE")
    private String hospitalCode;

    /**
     * 医院名称
     */
    @Column(name = "HOSPITAL_NAME")
    private String hospitalName;

    /**
     * 分院编号
     */
    @Column(name = "BRANCH_ID")
    private String branchId;

    /**
     * 分院代码
     */
    @Column(name = "BRANCH_CODE")
    private String branchCode;

    /**
     * 分院名称
     */
    @Column(name = "BRANCH_NAME")
    private String branchName;

    /**
     * 平台类型
     */
    @Column(name = "PLATFORM_MODE")
    private String platformMode;

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
     * 获取开放平台用户统一ID
     *
     * @return REF_UNION_ID - 开放平台用户统一ID
     */
    public String getRefUnionId() {
        return refUnionId;
    }

    /**
     * 设置开放平台用户统一ID
     *
     * @param refUnionId 开放平台用户统一ID
     */
    public void setRefUnionId(String refUnionId) {
        this.refUnionId = refUnionId == null ? null : refUnionId.trim();
    }

    /**
     * 获取医院服务号用户openid
     *
     * @return HIS_OPEN_ID - 医院服务号用户openid
     */
    public String getHisOpenId() {
        return hisOpenId;
    }

    /**
     * 设置医院服务号用户openid
     *
     * @param hisOpenId 医院服务号用户openid
     */
    public void setHisOpenId(String hisOpenId) {
        this.hisOpenId = hisOpenId == null ? null : hisOpenId.trim();
    }

    /**
     * 获取姓名
     *
     * @return NAME - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取性别(1男，2女)
     *
     * @return SEX - 性别(1男，2女)
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置性别(1男，2女)
     *
     * @param sex 性别(1男，2女)
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取 年龄
     *
     * @return AGE -  年龄
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置 年龄
     *
     * @param age  年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取出生日期
     *
     * @return BIRTH - 出生日期
     */
    public String getBirth() {
        return birth;
    }

    /**
     * 设置出生日期
     *
     * @param birth 出生日期
     */
    public void setBirth(String birth) {
        this.birth = birth == null ? null : birth.trim();
    }

    /**
     * 获取手机号码
     *
     * @return MOBILE - 手机号码
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号码
     *
     * @param mobile 手机号码
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取证件类型（配置）
     *
     * @return ID_TYPE - 证件类型（配置）
     */
    public Integer getIdType() {
        return idType;
    }

    /**
     * 设置证件类型（配置）
     *
     * @param idType 证件类型（配置）
     */
    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    /**
     * 获取证件号码
     *
     * @return ID_NO - 证件号码
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * 设置证件号码
     *
     * @param idNo 证件号码
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo == null ? null : idNo.trim();
    }

    /**
     * 获取地址
     *
     * @return ADDRESS - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取卡类型（配置）
     *
     * @return CARD_TYPE - 卡类型（配置）
     */
    public Integer getCardType() {
        return cardType;
    }

    /**
     * 设置卡类型（配置）
     *
     * @param cardType 卡类型（配置）
     */
    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    /**
     * 获取就诊卡号
     *
     * @return CARD_NO - 就诊卡号
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * 设置就诊卡号
     *
     * @param cardNo 就诊卡号
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    /**
     * 获取监护人名称
     *
     * @return GUARD_NAME - 监护人名称
     */
    public String getGuardName() {
        return guardName;
    }

    /**
     * 设置监护人名称
     *
     * @param guardName 监护人名称
     */
    public void setGuardName(String guardName) {
        this.guardName = guardName == null ? null : guardName.trim();
    }

    /**
     * 获取监护人证件类型（配置）
     *
     * @return GUARD_ID_TYPE - 监护人证件类型（配置）
     */
    public Integer getGuardIdType() {
        return guardIdType;
    }

    /**
     * 设置监护人证件类型（配置）
     *
     * @param guardIdType 监护人证件类型（配置）
     */
    public void setGuardIdType(Integer guardIdType) {
        this.guardIdType = guardIdType;
    }

    /**
     * 获取监护人证件号码
     *
     * @return GUARD_ID_NO - 监护人证件号码
     */
    public String getGuardIdNo() {
        return guardIdNo;
    }

    /**
     * 设置监护人证件号码
     *
     * @param guardIdNo 监护人证件号码
     */
    public void setGuardIdNo(String guardIdNo) {
        this.guardIdNo = guardIdNo == null ? null : guardIdNo.trim();
    }

    /**
     * 获取监护人手机号码
     *
     * @return GUARD_MOBILE - 监护人手机号码
     */
    public String getGuardMobile() {
        return guardMobile;
    }

    /**
     * 设置监护人手机号码
     *
     * @param guardMobile 监护人手机号码
     */
    public void setGuardMobile(String guardMobile) {
        this.guardMobile = guardMobile == null ? null : guardMobile.trim();
    }

    /**
     * 获取医院编号
     *
     * @return HOSPITAL_ID - 医院编号
     */
    public String getHospitalId() {
        return hospitalId;
    }

    /**
     * 设置医院编号
     *
     * @param hospitalId 医院编号
     */
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId == null ? null : hospitalId.trim();
    }

    /**
     * 获取医院代码
     *
     * @return HOSPITAL_CODE - 医院代码
     */
    public String getHospitalCode() {
        return hospitalCode;
    }

    /**
     * 设置医院代码
     *
     * @param hospitalCode 医院代码
     */
    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode == null ? null : hospitalCode.trim();
    }

    /**
     * 获取医院名称
     *
     * @return HOSPITAL_NAME - 医院名称
     */
    public String getHospitalName() {
        return hospitalName;
    }

    /**
     * 设置医院名称
     *
     * @param hospitalName 医院名称
     */
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName == null ? null : hospitalName.trim();
    }

    /**
     * 获取分院编号
     *
     * @return BRANCH_ID - 分院编号
     */
    public String getBranchId() {
        return branchId;
    }

    /**
     * 设置分院编号
     *
     * @param branchId 分院编号
     */
    public void setBranchId(String branchId) {
        this.branchId = branchId == null ? null : branchId.trim();
    }

    /**
     * 获取分院代码
     *
     * @return BRANCH_CODE - 分院代码
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * 设置分院代码
     *
     * @param branchCode 分院代码
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode == null ? null : branchCode.trim();
    }

    /**
     * 获取分院名称
     *
     * @return BRANCH_NAME - 分院名称
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * 设置分院名称
     *
     * @param branchName 分院名称
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
    }

    /**
     * 获取平台类型
     *
     * @return PLATFORM_MODE - 平台类型
     */
    public String getPlatformMode() {
        return platformMode;
    }

    /**
     * 设置平台类型
     *
     * @param platformMode 平台类型
     */
    public void setPlatformMode(String platformMode) {
        this.platformMode = platformMode == null ? null : platformMode.trim();
    }
}