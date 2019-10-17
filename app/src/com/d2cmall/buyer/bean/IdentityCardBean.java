package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/8/28.
 */

public class IdentityCardBean extends BaseBean {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private OrderConfirmBean.DataEntity.MemberCertificationBean memberCertification;

        public OrderConfirmBean.DataEntity.MemberCertificationBean getMemberCertification() {
            return memberCertification;
        }

        public void setMemberCertification(OrderConfirmBean.DataEntity.MemberCertificationBean memberCertification) {
            this.memberCertification = memberCertification;
        }


    }
}
