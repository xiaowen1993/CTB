package com.ctb.commons.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_pharmacy")
public class Pharmacy implements Serializable {
    
    /**
     *
     */
    
    private static final long serialVersionUID = -9183319777173468931L;
    
    @Id
    @Column(name = "ID")
    private String id;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "CODE")
    private String code;
    
    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "CONTACT_NAME")
    private String contactName;
    
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
     * @return NAME
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
    /**
     * @return CODE
     */
    public String getCode() {
        return code;
    }
    
    /**
     * @param code
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
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
     * @return CONTACT_NAME
     */
    public String getContactName() {
        return contactName;
    }
    
    /**
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }
    
    /**
     * @return CONTACT_TEL
     */
    public String getContactTel() {
        return contactTel;
    }
    
    /**
     * @param contactTel
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
    
    public Pharmacy() {
        super();
    }
    
    public Pharmacy(String id) {
        super();
        this.id = id;
    }
    
    public Pharmacy(String name, String code, String contactName, String contactTel) {
        super();
        this.name = name;
        this.code = code;
        this.contactName = contactName;
        this.contactTel = contactTel;
    }
    
    public Pharmacy(String id, String name, String code, String status, String contactName, String contactTel) {
        super();
        this.id = id;
        this.name = name;
        this.code = code;
        this.status = status;
        this.contactName = contactName;
        this.contactTel = contactTel;
    }
    
}