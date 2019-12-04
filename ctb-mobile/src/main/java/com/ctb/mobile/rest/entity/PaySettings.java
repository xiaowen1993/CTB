package com.ctb.mobile.rest.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_pay_settings")
public class PaySettings {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 微信AppId`
     */
    @Column(name = "APP_ID")
    private String appId;

    /**
     * 微信AppSecret
     */
    @Column(name = "APP_SECRET")
    private String appSecret;

    /**
     * 微信支付商户号
     */
    @Column(name = "MCH_ID")
    private String mchId;

    /**
     * 微信支付密钥
     */
    @Column(name = "PAY_KEY")
    private String payKey;

    /**
     * 微信证书路径
     */
    @Column(name = "CERTIFICATE_PATH")
    private String certificatePath;

    @Column(name = "CP")
    private String cp;

    @Column(name = "CT")
    private Date ct;

    @Column(name = "EP")
    private String ep;

    @Column(name = "ET")
    private Date et;

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
     * 获取微信AppId`
     *
     * @return APP_ID - 微信AppId`
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置微信AppId`
     *
     * @param appId 微信AppId`
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     * 获取微信AppSecret
     *
     * @return APP_SECRET - 微信AppSecret
     */
    public String getAppSecret() {
        return appSecret;
    }

    /**
     * 设置微信AppSecret
     *
     * @param appSecret 微信AppSecret
     */
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret == null ? null : appSecret.trim();
    }

    /**
     * 获取微信支付商户号
     *
     * @return MCH_ID - 微信支付商户号
     */
    public String getMchId() {
        return mchId;
    }

    /**
     * 设置微信支付商户号
     *
     * @param mchId 微信支付商户号
     */
    public void setMchId(String mchId) {
        this.mchId = mchId == null ? null : mchId.trim();
    }

    /**
     * 获取微信支付密钥
     *
     * @return PAY_KEY - 微信支付密钥
     */
    public String getPayKey() {
        return payKey;
    }

    /**
     * 设置微信支付密钥
     *
     * @param payKey 微信支付密钥
     */
    public void setPayKey(String payKey) {
        this.payKey = payKey == null ? null : payKey.trim();
    }

    /**
     * 获取微信证书路径
     *
     * @return CERTIFICATE_PATH - 微信证书路径
     */
    public String getCertificatePath() {
        return certificatePath;
    }

    /**
     * 设置微信证书路径
     *
     * @param certificatePath 微信证书路径
     */
    public void setCertificatePath(String certificatePath) {
        this.certificatePath = certificatePath == null ? null : certificatePath.trim();
    }

    /**
     * @return CP
     */
    public String getCp() {
        return cp;
    }

    /**
     * @param cp
     */
    public void setCp(String cp) {
        this.cp = cp == null ? null : cp.trim();
    }

    /**
     * @return CT
     */
    public Date getCt() {
        return ct;
    }

    /**
     * @param ct
     */
    public void setCt(Date ct) {
        this.ct = ct;
    }

    /**
     * @return EP
     */
    public String getEp() {
        return ep;
    }

    /**
     * @param ep
     */
    public void setEp(String ep) {
        this.ep = ep == null ? null : ep.trim();
    }

    /**
     * @return ET
     */
    public Date getEt() {
        return et;
    }

    /**
     * @param et
     */
    public void setEt(Date et) {
        this.et = et;
    }
}