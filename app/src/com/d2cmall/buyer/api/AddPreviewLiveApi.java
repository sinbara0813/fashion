package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.api.BaseApi;

/**
 * Fixme
 * Author: hrb
 * Date: 2017/03/23 14:00
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class AddPreviewLiveApi extends BaseApi {

    private String title;
    private String cover;
    private String previewDate;
    private String productIds;

    @Override
    protected String getPath() {
        return Constants.ADD_PREVIEW_LIVE_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPreviewDate() {
        return previewDate;
    }

    public void setPreviewDate(String previewDate) {
        this.previewDate = previewDate;
    }

    public String getProductIds() {
        return productIds;
    }

    public void setProductIds(String productIds) {
        this.productIds = productIds;
    }
}
