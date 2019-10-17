package com.d2cmall.buyer.api;

public class ThirdLoginApi extends BaseApi {

    private String accessToken;
    private String openId;
    private String username;
    private String thirdHeadPic;
    private String sex;
    private String unionId;
    private String app = "App.Buyer.Android";

    @Override
    protected String getPath() {
        return interPath;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setThirdHeadPic(String thirdHeadPic) {
        this.thirdHeadPic = thirdHeadPic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }
}
