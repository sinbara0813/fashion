package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/12/26.
 * Description : CouponSuccessBean
 */

public class CouponSuccessBean extends BaseBean {

    /**
     * data : {"coupon":{"amount":100,"code":"S1545817794326-2908360","remark":"优惠券","expiredate":"2019/01/05 18:49","type":"CASH","enabledate":"2018/12/26 17:49","url":null,"enablestamp":1545817794326,"transfer":0,"checkAssociation":"PRODUCT","price":null,"name":"测试单品优惠券","expirestamp":1546685394326,"id":10204929,"isExpired":0,"needAmount":101,"toRedirect":true,"status":"CLAIMED","memberId":2908360}}
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
         * coupon : {"amount":100,"code":"S1545817794326-2908360","remark":"优惠券","expiredate":"2019/01/05 18:49","type":"CASH","enabledate":"2018/12/26 17:49","url":null,"enablestamp":1545817794326,"transfer":0,"checkAssociation":"PRODUCT","price":null,"name":"测试单品优惠券","expirestamp":1546685394326,"id":10204929,"isExpired":0,"needAmount":101,"toRedirect":true,"status":"CLAIMED","memberId":2908360}
         */

        private CouponBean coupon;

        public CouponBean getCoupon() {
            return coupon;
        }

        public void setCoupon(CouponBean coupon) {
            this.coupon = coupon;
        }

        public static class CouponBean {
            /**
             * amount : 100
             * code : S1545817794326-2908360
             * remark : 优惠券
             * expiredate : 2019/01/05 18:49
             * type : CASH
             * enabledate : 2018/12/26 17:49
             * url : null
             * enablestamp : 1545817794326
             * transfer : 0
             * checkAssociation : PRODUCT
             * price : null
             * name : 测试单品优惠券
             * expirestamp : 1546685394326
             * id : 10204929
             * isExpired : 0
             * needAmount : 101
             * toRedirect : true
             * status : CLAIMED
             * memberId : 2908360
             */

            private int amount;
            private String code;
            private String remark;
            private String expiredate;
            private String type;
            private String enabledate;
            private Object url;
            private long enablestamp;
            private int transfer;
            private String checkAssociation;
            private Object price;
            private String name;
            private long expirestamp;
            private int id;
            private int isExpired;
            private int needAmount;
            private boolean toRedirect;
            @SerializedName("status")
            private String statusX;
            private int memberId;

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getExpiredate() {
                return expiredate;
            }

            public void setExpiredate(String expiredate) {
                this.expiredate = expiredate;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getEnabledate() {
                return enabledate;
            }

            public void setEnabledate(String enabledate) {
                this.enabledate = enabledate;
            }

            public Object getUrl() {
                return url;
            }

            public void setUrl(Object url) {
                this.url = url;
            }

            public long getEnablestamp() {
                return enablestamp;
            }

            public void setEnablestamp(long enablestamp) {
                this.enablestamp = enablestamp;
            }

            public int getTransfer() {
                return transfer;
            }

            public void setTransfer(int transfer) {
                this.transfer = transfer;
            }

            public String getCheckAssociation() {
                return checkAssociation;
            }

            public void setCheckAssociation(String checkAssociation) {
                this.checkAssociation = checkAssociation;
            }

            public Object getPrice() {
                return price;
            }

            public void setPrice(Object price) {
                this.price = price;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public long getExpirestamp() {
                return expirestamp;
            }

            public void setExpirestamp(long expirestamp) {
                this.expirestamp = expirestamp;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsExpired() {
                return isExpired;
            }

            public void setIsExpired(int isExpired) {
                this.isExpired = isExpired;
            }

            public int getNeedAmount() {
                return needAmount;
            }

            public void setNeedAmount(int needAmount) {
                this.needAmount = needAmount;
            }

            public boolean isToRedirect() {
                return toRedirect;
            }

            public void setToRedirect(boolean toRedirect) {
                this.toRedirect = toRedirect;
            }

            public String getStatusX() {
                return statusX;
            }

            public void setStatusX(String statusX) {
                this.statusX = statusX;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }
        }
    }
}
