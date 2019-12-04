package com.ctb.mobile.rest.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_pay_settings")
public class PaySettings {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * ΢��AppId`
     */
    @Column(name = "APP_ID")
    private String appId;

    /**
     * ΢��AppSecret
     */
    @Column(name = "APP_SECRET")
    private String appSecret;

    /**
     * ΢��֧���̻���
     */
    @Column(name = "MCH_ID")
    private String mchId;

    /**
     * ΢��֧����Կ
     */
    @Column(name = "PAY_KEY")
    private String payKey;

    /**
     * ΢��֤��·��
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
     * ��ȡ΢��AppId`
     *
     * @return APP_ID - ΢��AppId`
     */
    public String getAppId() {
        return appId;
    }

    /**
     * ����΢��AppId`
     *
     * @param appId ΢��AppId`
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     * ��ȡ΢��AppSecret
     *
     * @return APP_SECRET - ΢��AppSecret
     */
    public String getAppSecret() {
        return appSecret;
    }

    /**
     * ����΢��AppSecret
     *
     * @param appSecret ΢��AppSecret
     */
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret == null ? null : appSecret.trim();
    }

    /**
     * ��ȡ΢��֧���̻���
     *
     * @return MCH_ID - ΢��֧���̻���
     */
    public String getMchId() {
        return mchId;
    }

    /**
     * ����΢��֧���̻���
     *
     * @param mchId ΢��֧���̻���
     */
    public void setMchId(String mchId) {
        this.mchId = mchId == null ? null : mchId.trim();
    }

    /**
     * ��ȡ΢��֧����Կ
     *
     * @return PAY_KEY - ΢��֧����Կ
     */
    public String getPayKey() {
        return payKey;
    }

    /**
     * ����΢��֧����Կ
     *
     * @param payKey ΢��֧����Կ
     */
    public void setPayKey(String payKey) {
        this.payKey = payKey == null ? null : payKey.trim();
    }

    /**
     * ��ȡ΢��֤��·��
     *
     * @return CERTIFICATE_PATH - ΢��֤��·��
     */
    public String getCertificatePath() {
        return certificatePath;
    }

    /**
     * ����΢��֤��·��
     *
     * @param certificatePath ΢��֤��·��
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