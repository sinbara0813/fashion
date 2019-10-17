package com.d2cmall.buyer.bean;


import com.d2cmall.buyer.base.BaseBean;

public class DesignerShareBean extends BaseBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {

        private ShareBean.DataEntity.MemberSharesEntity membershares;

        public ShareBean.DataEntity.MemberSharesEntity getMemberShares() {
            return membershares;
        }

        public void setMemberShares(ShareBean.DataEntity.MemberSharesEntity membershares) {
            this.membershares = membershares;
        }

    }
}
