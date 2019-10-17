package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**重新上传身份证照片
 * Created by Administrator on 2018/6/5.
 * Description : AddCertificationApi
 */

public class UploadIDCardPicsApi extends BaseApi {
    //身份证正面
    private String frontPic;
    // 身份证反面
    private String behindPic;

    private long id;

    public void setId(long id) {
        this.id = id;
    }


    public void setFrontPic(String frontPic) {
        this.frontPic = frontPic;
    }

    public void setBehindPic(String behindPic) {
        this.behindPic = behindPic;
    }


    @Override
    protected String getPath() {
        return Constants.CERTIFICATION_REUPLOAD;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
