package com.d2cmall.buyer.api;

import com.d2cmall.buyer.Constants;

/**
 * 发表评论
 * Author: Blend
 * Date: 16/4/26 15:11
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CommentInsertApi extends BaseApi {

    private Long memberShareUserId;
    private Long sourceId;
    private String content;
    private long toCommentId;

    public void setMemberShareUserId(Long memberShareUserId) {
        this.memberShareUserId = memberShareUserId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setToCommentId(long toCommentId) {
        this.toCommentId = toCommentId;
    }

    @Override
    protected String getPath() {
        return Constants.EXPLORE_COMMENT_INSERT_URL;
    }

    @Override
    public Method requestMethod() {
        return Method.POST;
    }

}
