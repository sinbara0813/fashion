package com.d2cmall.buyer.bean;

public class HttpErrorBean {

    private int status;
    private String msg;
    private String jsonData;

    public HttpErrorBean(int status, String msg, String jsonData) {
        this.status = status;
        this.msg = msg;
        this.jsonData = jsonData;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}
