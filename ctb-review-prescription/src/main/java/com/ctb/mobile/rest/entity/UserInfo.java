package com.ctb.mobile.rest.entity;

import javax.persistence.*;

@Table(name = "user_info")
public class UserInfo {
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * ����ƽ̨�û�ͳһID
     */
    @Column(name = "REF_UNION_ID")
    private String refUnionId;

    /**
     * ҽԺ������û�openid
     */
    @Column(name = "HIS_OPEN_ID")
    private String hisOpenId;

    /**
     * ����
     */
    @Column(name = "NAME")
    private String name;

    /**
     * �Ա�(1�У�2Ů)
     */
    @Column(name = "SEX")
    private Integer sex;

    /**
     *  ����
     */
    @Column(name = "AGE")
    private Integer age;

    /**
     * ��������
     */
    @Column(name = "BIRTH")
    private String birth;

    /**
     * �ֻ�����
     */
    @Column(name = "MOBILE")
    private String mobile;

    /**
     * ֤�����ͣ����ã�
     */
    @Column(name = "ID_TYPE")
    private Integer idType;

    /**
     * ֤������
     */
    @Column(name = "ID_NO")
    private String idNo;

    /**
     * ��ַ
     */
    @Column(name = "ADDRESS")
    private String address;

    /**
     * �����ͣ����ã�
     */
    @Column(name = "CARD_TYPE")
    private Integer cardType;

    /**
     * ���￨��
     */
    @Column(name = "CARD_NO")
    private String cardNo;

    /**
     * �໤������
     */
    @Column(name = "GUARD_NAME")
    private String guardName;

    /**
     * �໤��֤�����ͣ����ã�
     */
    @Column(name = "GUARD_ID_TYPE")
    private Integer guardIdType;

    /**
     * �໤��֤������
     */
    @Column(name = "GUARD_ID_NO")
    private String guardIdNo;

    /**
     * �໤���ֻ�����
     */
    @Column(name = "GUARD_MOBILE")
    private String guardMobile;

    /**
     * ҽԺ���
     */
    @Column(name = "HOSPITAL_ID")
    private String hospitalId;

    /**
     * ҽԺ����
     */
    @Column(name = "HOSPITAL_CODE")
    private String hospitalCode;

    /**
     * ҽԺ����
     */
    @Column(name = "HOSPITAL_NAME")
    private String hospitalName;

    /**
     * ��Ժ���
     */
    @Column(name = "BRANCH_ID")
    private String branchId;

    /**
     * ��Ժ����
     */
    @Column(name = "BRANCH_CODE")
    private String branchCode;

    /**
     * ��Ժ����
     */
    @Column(name = "BRANCH_NAME")
    private String branchName;

    /**
     * ƽ̨����
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
     * ��ȡ����ƽ̨�û�ͳһID
     *
     * @return REF_UNION_ID - ����ƽ̨�û�ͳһID
     */
    public String getRefUnionId() {
        return refUnionId;
    }

    /**
     * ���ÿ���ƽ̨�û�ͳһID
     *
     * @param refUnionId ����ƽ̨�û�ͳһID
     */
    public void setRefUnionId(String refUnionId) {
        this.refUnionId = refUnionId == null ? null : refUnionId.trim();
    }

    /**
     * ��ȡҽԺ������û�openid
     *
     * @return HIS_OPEN_ID - ҽԺ������û�openid
     */
    public String getHisOpenId() {
        return hisOpenId;
    }

    /**
     * ����ҽԺ������û�openid
     *
     * @param hisOpenId ҽԺ������û�openid
     */
    public void setHisOpenId(String hisOpenId) {
        this.hisOpenId = hisOpenId == null ? null : hisOpenId.trim();
    }

    /**
     * ��ȡ����
     *
     * @return NAME - ����
     */
    public String getName() {
        return name;
    }

    /**
     * ��������
     *
     * @param name ����
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * ��ȡ�Ա�(1�У�2Ů)
     *
     * @return SEX - �Ա�(1�У�2Ů)
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * �����Ա�(1�У�2Ů)
     *
     * @param sex �Ա�(1�У�2Ů)
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * ��ȡ ����
     *
     * @return AGE -  ����
     */
    public Integer getAge() {
        return age;
    }

    /**
     * ���� ����
     *
     * @param age  ����
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * ��ȡ��������
     *
     * @return BIRTH - ��������
     */
    public String getBirth() {
        return birth;
    }

    /**
     * ���ó�������
     *
     * @param birth ��������
     */
    public void setBirth(String birth) {
        this.birth = birth == null ? null : birth.trim();
    }

    /**
     * ��ȡ�ֻ�����
     *
     * @return MOBILE - �ֻ�����
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * �����ֻ�����
     *
     * @param mobile �ֻ�����
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * ��ȡ֤�����ͣ����ã�
     *
     * @return ID_TYPE - ֤�����ͣ����ã�
     */
    public Integer getIdType() {
        return idType;
    }

    /**
     * ����֤�����ͣ����ã�
     *
     * @param idType ֤�����ͣ����ã�
     */
    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    /**
     * ��ȡ֤������
     *
     * @return ID_NO - ֤������
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * ����֤������
     *
     * @param idNo ֤������
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo == null ? null : idNo.trim();
    }

    /**
     * ��ȡ��ַ
     *
     * @return ADDRESS - ��ַ
     */
    public String getAddress() {
        return address;
    }

    /**
     * ���õ�ַ
     *
     * @param address ��ַ
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * ��ȡ�����ͣ����ã�
     *
     * @return CARD_TYPE - �����ͣ����ã�
     */
    public Integer getCardType() {
        return cardType;
    }

    /**
     * ���ÿ����ͣ����ã�
     *
     * @param cardType �����ͣ����ã�
     */
    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    /**
     * ��ȡ���￨��
     *
     * @return CARD_NO - ���￨��
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * ���þ��￨��
     *
     * @param cardNo ���￨��
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    /**
     * ��ȡ�໤������
     *
     * @return GUARD_NAME - �໤������
     */
    public String getGuardName() {
        return guardName;
    }

    /**
     * ���ü໤������
     *
     * @param guardName �໤������
     */
    public void setGuardName(String guardName) {
        this.guardName = guardName == null ? null : guardName.trim();
    }

    /**
     * ��ȡ�໤��֤�����ͣ����ã�
     *
     * @return GUARD_ID_TYPE - �໤��֤�����ͣ����ã�
     */
    public Integer getGuardIdType() {
        return guardIdType;
    }

    /**
     * ���ü໤��֤�����ͣ����ã�
     *
     * @param guardIdType �໤��֤�����ͣ����ã�
     */
    public void setGuardIdType(Integer guardIdType) {
        this.guardIdType = guardIdType;
    }

    /**
     * ��ȡ�໤��֤������
     *
     * @return GUARD_ID_NO - �໤��֤������
     */
    public String getGuardIdNo() {
        return guardIdNo;
    }

    /**
     * ���ü໤��֤������
     *
     * @param guardIdNo �໤��֤������
     */
    public void setGuardIdNo(String guardIdNo) {
        this.guardIdNo = guardIdNo == null ? null : guardIdNo.trim();
    }

    /**
     * ��ȡ�໤���ֻ�����
     *
     * @return GUARD_MOBILE - �໤���ֻ�����
     */
    public String getGuardMobile() {
        return guardMobile;
    }

    /**
     * ���ü໤���ֻ�����
     *
     * @param guardMobile �໤���ֻ�����
     */
    public void setGuardMobile(String guardMobile) {
        this.guardMobile = guardMobile == null ? null : guardMobile.trim();
    }

    /**
     * ��ȡҽԺ���
     *
     * @return HOSPITAL_ID - ҽԺ���
     */
    public String getHospitalId() {
        return hospitalId;
    }

    /**
     * ����ҽԺ���
     *
     * @param hospitalId ҽԺ���
     */
    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId == null ? null : hospitalId.trim();
    }

    /**
     * ��ȡҽԺ����
     *
     * @return HOSPITAL_CODE - ҽԺ����
     */
    public String getHospitalCode() {
        return hospitalCode;
    }

    /**
     * ����ҽԺ����
     *
     * @param hospitalCode ҽԺ����
     */
    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode == null ? null : hospitalCode.trim();
    }

    /**
     * ��ȡҽԺ����
     *
     * @return HOSPITAL_NAME - ҽԺ����
     */
    public String getHospitalName() {
        return hospitalName;
    }

    /**
     * ����ҽԺ����
     *
     * @param hospitalName ҽԺ����
     */
    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName == null ? null : hospitalName.trim();
    }

    /**
     * ��ȡ��Ժ���
     *
     * @return BRANCH_ID - ��Ժ���
     */
    public String getBranchId() {
        return branchId;
    }

    /**
     * ���÷�Ժ���
     *
     * @param branchId ��Ժ���
     */
    public void setBranchId(String branchId) {
        this.branchId = branchId == null ? null : branchId.trim();
    }

    /**
     * ��ȡ��Ժ����
     *
     * @return BRANCH_CODE - ��Ժ����
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * ���÷�Ժ����
     *
     * @param branchCode ��Ժ����
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode == null ? null : branchCode.trim();
    }

    /**
     * ��ȡ��Ժ����
     *
     * @return BRANCH_NAME - ��Ժ����
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * ���÷�Ժ����
     *
     * @param branchName ��Ժ����
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
    }

    /**
     * ��ȡƽ̨����
     *
     * @return PLATFORM_MODE - ƽ̨����
     */
    public String getPlatformMode() {
        return platformMode;
    }

    /**
     * ����ƽ̨����
     *
     * @param platformMode ƽ̨����
     */
    public void setPlatformMode(String platformMode) {
        this.platformMode = platformMode == null ? null : platformMode.trim();
    }
}