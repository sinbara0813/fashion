package com.d2cmall.buyer.api;

import static com.d2cmall.buyer.Constants.EXPLORE_COMMENT_INSERT_URL;

/**
 * Created by rookie on 2017/8/29.
 */

public class PostReviewApi extends BaseApi {
    public long memberShareUserId;
    public long sourceId;
    public String content;
    public long  toCommentId;
    @Override
    protected String getPath() {
        return EXPLORE_COMMENT_INSERT_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }
}
