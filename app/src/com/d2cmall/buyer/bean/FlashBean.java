package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/12/15 17:22
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FlashBean extends BaseBean {

    /**
     * data : {"flashPromotion":{"promotionType":"0","endDate":"2017/12/16 00:00:00","session":"20:00","sessionName":"","sessionRemark":"","limitQuantity":9999,"remaining":23954865,"startTimeStamp":1513339200000,"promotionScope":0,"name":"测试","statusName":"即将开始","endTimeStamp":1513353600000,"id":81,"flashUrl":"/flashpromotion/product/session","startDate":"2017/12/15 20:00:00","brandPic":""},"pic":"/2017/08/24/071616f3fd1f723167e82f5fb719b1f87b8c46.jpg"}
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
         * flashPromotion : {"promotionType":"0","endDate":"2017/12/16 00:00:00","session":"20:00","sessionName":"","sessionRemark":"","limitQuantity":9999,"remaining":23954865,"startTimeStamp":1513339200000,"promotionScope":0,"name":"测试","statusName":"即将开始","endTimeStamp":1513353600000,"id":81,"flashUrl":"/flashpromotion/product/session","startDate":"2017/12/15 20:00:00","brandPic":""}
         * pic : /2017/08/24/071616f3fd1f723167e82f5fb719b1f87b8c46.jpg
         */

        private FlashPromotionBean flashPromotion;
        private String pic;

        public FlashPromotionBean getFlashPromotion() {
            return flashPromotion;
        }

        public void setFlashPromotion(FlashPromotionBean flashPromotion) {
            this.flashPromotion = flashPromotion;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public static class FlashPromotionBean {
            /**
             * promotionType : 0
             * endDate : 2017/12/16 00:00:00
             * session : 20:00
             * sessionName :
             * sessionRemark :
             * limitQuantity : 9999
             * remaining : 23954865
             * startTimeStamp : 1513339200000
             * promotionScope : 0
             * name : 测试
             * statusName : 即将开始
             * endTimeStamp : 1513353600000
             * id : 81
             * flashUrl : /flashpromotion/product/session
             * startDate : 2017/12/15 20:00:00
             * brandPic :
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
            private String brandPic;

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

            public void setSessionName(String sessionName) {
                this.sessionName = sessionName;
            }

            public String getSessionRemark() {
                return sessionRemark;
            }

            public void setSessionRemark(String sessionRemark) {
                this.sessionRemark = sessionRemark;
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

            public String getBrandPic() {
                return brandPic;
            }

            public void setBrandPic(String brandPic) {
                this.brandPic = brandPic;
            }
        }
    }
}
