package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

public class ChangePasswordApi extends BaseApi {

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    @Override
    protected String getPath() {
        return Constants.CHANGE_PASSWORD_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
