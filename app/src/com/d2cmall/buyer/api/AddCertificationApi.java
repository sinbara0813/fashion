package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/6/5.
 * Description : AddCertificationApi
 */

public class AddCertificationApi extends BaseApi {
    private String realName;//姓名
    //身份证号
    private String identityCard;
    //身份证正面
    private String frontPic;
    // 身份证反面
    private String behindPic;
    // 是否是默认
    private Integer isdefault;

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public void setFrontPic(String frontPic) {
        this.frontPic = frontPic;
    }

    public void setBehindPic(String behindPic) {
        this.behindPic = behindPic;
    }

    public void setIsdefault(Integer isdefault) {
        this.isdefault = isdefault;
    }

    @Override
    protected String getPath() {
        return Constants.CERTIFICATION_CERTIFICATE;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
