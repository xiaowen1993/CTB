package com.ctb.mobile.rest.entity;

import javax.persistence.*;

@Table(name = "pharmacy_user")
public class PharmacyUser {
    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "PHARMACY_ID")
    private String pharmacyId;

    /**
     * ҩ���û���¼��
     */
    @Column(name = "NAME")
    private String name;

    /**
     * ��¼������
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * ����
     */
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "STATUS")
    private String status;

    /**
     * ����¼ʱ��
     */
    @Column(name = "LAST_LOGIN_TIME")
    private Long lastLoginTime;

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
     * @return PHARMACY_ID
     */
    public String getPharmacyId() {
        return pharmacyId;
    }

    /**
     * @param pharmacyId
     */
    public void setPharmacyId(String pharmacyId) {
        this.pharmacyId = pharmacyId == null ? null : pharmacyId.trim();
    }

    /**
     * ��ȡҩ���û���¼��
     *
     * @return NAME - ҩ���û���¼��
     */
    public String getName() {
        return name;
    }

    /**
     * ����ҩ���û���¼��
     *
     * @param name ҩ���û���¼��
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * ��ȡ��¼������
     *
     * @return PASSWORD - ��¼������
     */
    public String getPassword() {
        return password;
    }

    /**
     * ���õ�¼������
     *
     * @param password ��¼������
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * ��ȡ����
     *
     * @return EMAIL - ����
     */
    public String getEmail() {
        return email;
    }

    /**
     * ��������
     *
     * @param email ����
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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
        this.status = status == null ? null : status.trim();
    }

    /**
     * ��ȡ����¼ʱ��
     *
     * @return LAST_LOGIN_TIME - ����¼ʱ��
     */
    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * ��������¼ʱ��
     *
     * @param lastLoginTime ����¼ʱ��
     */
    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}