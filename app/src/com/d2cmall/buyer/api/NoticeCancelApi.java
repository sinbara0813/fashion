package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * Created by Administrator on 2018/3/23.
 * Description : NoticeCancelApi
 */

public class NoticeCancelApi extends BaseApi {
    @Override
    protected String getPath() {
        return Constants.GET_MAIN_NOTICE_CANCEL_URL;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    private long articleId;
    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
