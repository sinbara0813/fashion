package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * 详情Bean类
 * Author: Blend
 * Date: 16/4/26 14:01
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ShareDetailBean extends BaseBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {

        private ShareBean.DataEntity.MemberSharesEntity.ListEntity memberShare;

        public ShareBean.DataEntity.MemberSharesEntity.ListEntity getMemberShare() {
            return memberShare;
        }

        public void setMemberShare(ShareBean.DataEntity.MemberSharesEntity.ListEntity memberShare) {
            this.memberShare = memberShare;
        }
    }
}
