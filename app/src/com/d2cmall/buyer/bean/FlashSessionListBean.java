package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/12/12 15:32
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FlashSessionListBean extends BaseBean {

    /**
     * data : {"currentId":73,"list":[{"promotionType":"0","endDate":"2017/12/13 00:00:00","session":"20:00","sessionName":"","limitQuantity":9999,"startTimeStamp":1512993600000,"promotionScope":0,"name":"测试","statusName":"已开抢","endTimeStamp":1513094400000,"id":73,"flashUrl":"/flashpromotion/product/session","startDate":"2017/12/11 20:00:00"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * currentId : 73
         * list : [{"promotionType":"0","endDate":"2017/12/13 00:00:00","session":"20:00","sessionName":"","limitQuantity":9999,"startTimeStamp":1512993600000,"promotionScope":0,"name":"测试","statusName":"已开抢","endTimeStamp":1513094400000,"id":73,"flashUrl":"/flashpromotion/product/session","startDate":"2017/12/11 20:00:00"}]
         */

        private int currentId;
        private List<ListBean> list;

        public int getCurrentId() {
            return currentId;
        }

        public void setCurrentId(int currentId) {
            this.currentId = currentId;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * promotionType : 0
             * endDate : 2017/12/13 00:00:00
             * session : 20:00
             * sessionName :
             * limitQuantity : 9999
             * startTimeStamp : 1512993600000
             * promotionScope : 0
             * name : 测试
             * statusName : 已开抢
             * endTimeStamp : 1513094400000
             * id : 73
             * flashUrl : /flashpromotion/product/session
             * startDate : 2017/12/11 20:00:00
             */

            private String promotionType;
            private String endDate;
            private String session;
            private String sessionName;
            private String sessionRemark;
            private int limitQuantity;
            private long startTimeStamp;
            private int promotionScope;
            private String name;
            private String statusName;
            private long endTimeStamp;
            private int id;
            private String flashUrl;
            private String startDate;

            public String getPromotionType() {
                return promotionType;
            }

            public void setPromotionType(String promotionType) {
                this.promotionType = promotionType;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }

            public String getSession() {
                return session;
            }

            public void setSession(String session) {
                this.session = session;
            }

            public String getSessionName() {
                return sessionName;
            }

            public String getSessionRemark() {
                return sessionRemark;
            }

            public void setSessionRemark(String sessionRemark) {
                this.sessionRemark = sessionRemark;
            }

            public void setSessionName(String sessionName) {
                this.sessionName = sessionName;
            }

            public int getLimitQuantity() {
                return limitQuantity;
            }

            public void setLimitQuantity(int limitQuantity) {
                this.limitQuantity = limitQuantity;
            }

            public long getStartTimeStamp() {
                return startTimeStamp;
            }

            public void setStartTimeStamp(long startTimeStamp) {
                this.startTimeStamp = startTimeStamp;
            }

            public int getPromotionScope() {
                return promotionScope;
            }

            public void setPromotionScope(int promotionScope) {
                this.promotionScope = promotionScope;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStatusName() {
                return statusName;
            }

            public void setStatusName(String statusName) {
                this.statusName = statusName;
            }

            public long getEndTimeStamp() {
                return endTimeStamp;
            }

            public void setEndTimeStamp(long endTimeStamp) {
                this.endTimeStamp = endTimeStamp;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getFlashUrl() {
                return flashUrl;
            }

            public void setFlashUrl(String flashUrl) {
                this.flashUrl = flashUrl;
            }

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }
        }
    }
}
