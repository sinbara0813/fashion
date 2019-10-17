package com.d2cmall.buyer.api;

import static com.d2cmall.buyer.Constants.CHANGE_BIND_PHONE;

/**
 * Created by rookie on 2017/11/28.
 */

public class ChangePhoneApi extends BaseApi {

    private String newMobile;
    private String code;
    private String nationCode;

    public String getNewMobile() {
        return newMobile;
    }

    public void setNewMobile(String newMobile) {
        this.newMobile = newMobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNationCode() {
        return nationCode;
    }

    public void setNationCode(String nationCode) {
        this.nationCode = nationCode;
    }

    @Override
    protected String getPath() {
        return CHANGE_BIND_PHONE;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
