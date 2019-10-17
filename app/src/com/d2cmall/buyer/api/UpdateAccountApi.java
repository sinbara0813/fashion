package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

public class UpdateAccountApi extends BaseApi {

    private String nickname;
    private String birthDay;
    private String email;
    private String headPic;
    private String sex;
    private String background;

    public void setBackground(String background){this.background=background;}

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    protected String getPath() {
        return Constants.UPDATE_ACCOUNT_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
