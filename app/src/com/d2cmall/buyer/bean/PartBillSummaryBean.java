package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/4/17 14:29
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class PartBillSummaryBean extends BaseBean {

    /**
     * data : {"masterData":[{"amount":780,"rebate":15.6,"count":1,"status":1},{"amount":45557,"rebate":911.14,"count":8,"status":8}],"partnerData":[{"amount":7370,"rebate":509.6,"count":3,"status":8}],"superData":[],"parentData":[]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<PartnerDataBean> masterData;
        private List<PartnerDataBean> partnerData;
        private List<PartnerDataBean> superData;
        private List<PartnerDataBean> parentData;

        public List<PartnerDataBean> getMasterData() {
            return masterData;
        }

        public void setMasterData(List<PartnerDataBean> masterData) {
            this.masterData = masterData;
        }

        public List<PartnerDataBean> getPartnerData() {
            return partnerData;
        }

        public void setPartnerData(List<PartnerDataBean> partnerData) {
            this.partnerData = partnerData;
        }

        public List<PartnerDataBean> getSuperData() {
            return superData;
        }

        public void setSuperData(List<PartnerDataBean> superData) {
            this.superData = superData;
        }

        public List<PartnerDataBean> getParentData() {
            return parentData;
        }

        public void setParentData(List<PartnerDataBean> parentData) {
            this.parentData = parentData;
        }

        public static class PartnerDataBean {
            /**
             * amount : 7370.0
             * rebate : 509.6
             * count : 3
             * status : 8
             */

            private double amount;
            private double rebate;
            private int count;
            @SerializedName("status")
            private int statusX;

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public double getRebate() {
                return rebate;
            }

            public void setRebate(double rebate) {
                this.rebate = rebate;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getStatusX() {
                return statusX;
            }

            public void setStatusX(int statusX) {
                this.statusX = statusX;
            }
        }
    }
}
