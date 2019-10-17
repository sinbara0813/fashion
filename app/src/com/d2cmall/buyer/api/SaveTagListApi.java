package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * 批量上传标签
 * Author: hrb
 * Date: 2016/07/29 16:21
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class SaveTagListApi extends BaseApi {

    private long shareId;
    private String picTag;

    @Override
    protected String getPath() {
        return Constants.UPDATE_PIC_TAG_LIST_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    public long getShareId() {
        return shareId;
    }

    public void setShareId(long shareId) {
        this.shareId = shareId;
    }

    public String getPicTag() {
        return picTag;
    }

    public void setPicTag(String picTag) {
        this.picTag = picTag;
    }
}
