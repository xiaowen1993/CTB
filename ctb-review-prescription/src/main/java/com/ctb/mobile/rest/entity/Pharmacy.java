package com.ctb.mobile.rest.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_pharmacy")
public class Pharmacy {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * ҩ������
     */
    @Column(name = "NAME")
    private String name;

    /**
     * ҩ�����
     */
    @Column(name = "CODE")
    private String code;

    /**
     * Ĭ��1������״̬
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * ����Ա����
     */
    @Column(name = "CONTACT_NAME")
    private String contactName;

    /**
     * ����Ա�绰
     */
    @Column(name = "CONTACT_TEL")
    private String contactTel;

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
     * ��ȡҩ������
     *
     * @return NAME - ҩ������
     */
    public String getName() {
        return name;
    }

    /**
     * ����ҩ������
     *
     * @param name ҩ������
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * ��ȡҩ�����
     *
     * @return CODE - ҩ�����
     */
    public String getCode() {
        return code;
    }

    /**
     * ����ҩ�����
     *
     * @param code ҩ�����
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * ��ȡĬ��1������״̬
     *
     * @return STATUS - Ĭ��1������״̬
     */
    public String getStatus() {
        return status;
    }

    /**
     * ����Ĭ��1������״̬
     *
     * @param status Ĭ��1������״̬
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * ��ȡ����Ա����
     *
     * @return CONTACT_NAME - ����Ա����
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * ���ù���Ա����
     *
     * @param contactName ����Ա����
     */
    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    /**
     * ��ȡ����Ա�绰
     *
     * @return CONTACT_TEL - ����Ա�绰
     */
    public String getContactTel() {
        return contactTel;
    }

    /**
     * ���ù���Ա�绰
     *
     * @param contactTel ����Ա�绰
     */
    public void setContactTel(String contactTel) {
        this.contactTel = contactTel == null ? null : contactTel.trim();
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