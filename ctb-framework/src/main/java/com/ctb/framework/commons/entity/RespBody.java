package com.ctb.framework.commons.entity;

import java.io.Serializable;

public class RespBody implements Serializable {
    
    private static final long serialVersionUID = 5905715228490291386L;
    
    private String id;
    
    private Status status;

    private Object message;
    
    public RespBody() {
        super();
    }
    
    
    public RespBody(Status status) {
        super();
        this.status = status;
    }
    
    
    public RespBody(String id, Status status) {
        super();
        this.id = id;
        this.status = status;
    }
    
    public RespBody(Status status, Object message) {
        this.status = status;
        this.message = message;
    }
    

    public enum Status {
        OK, ERROR, PROMPT
    }
    

    public void addOK(Object message) {
        this.message = message;
        this.status = Status.OK;
    }
    

    public void addError(Object message) {
        this.message = message;
        this.status = Status.ERROR;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Status getStatus() {
        return status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }
    
    public Object getMessage() {
        return message;
    }
    
    public void setMessage(Object message) {
        this.message = message;
    }
}
