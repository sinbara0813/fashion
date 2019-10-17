package com.d2cmall.buyer.bean;

import android.icu.math.BigDecimal;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

public class CouponsBean extends BaseBean {

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * index : 1
         * pageSize : 40
         * total : 3
         * previous : false
         * next : false
         * list : [{"id":102592,"name":"50元无门槛现金券","type":"CASH","code":"ac1af412","amount":50,"needAmount":0,"remark":"1、本优惠券仅供在D2C全球设计师集成平台官方网站使用。\r\n2、本券不得叠加使用，一个订单仅限使用一张。\r\n3、本券不可兑现，不可找零。\r\n4、所有解释权仅归D2C全球设计师集成平台所有","enabledate":"15/08/16 22:01","expiredate":"15/09/15 22:01","status":"USED","url":"/page/nvshenxinzhuang","isExpired":1},{"id":57643,"name":"注册赠送优惠券699-100","type":"CASH","code":"1786977a","amount":100,"needAmount":699,"remark":"满699才能使用","enabledate":"15/04/23 10:23","expiredate":"16/04/22 10:23","status":"CLAIMED","isExpired":0},{"id":91533,"name":"注册赠送优惠券699-100","type":"CASH","code":"d8b1572e","amount":100,"needAmount":699,"remark":"满699才能使用","enabledate":"15/07/10 01:07","expiredate":"16/07/09 01:07","status":"CLAIMED","isExpired":0}]
         */

        private MyCouponsEntity myCoupons;

        public void setMyCoupons(MyCouponsEntity myCoupons) {
            this.myCoupons = myCoupons;
        }

        public MyCouponsEntity getMyCoupons() {
            return myCoupons;
        }

        public static class MyCouponsEntity {
            private int index;
            private int pageSize;
            private int total;
            private boolean previous;
            private boolean next;
            /**
             * id : 102592
             * name : 50元无门槛现金券
             * type : CASH
             * code : ac1af412
             * amount : 50
             * needAmount : 0
             * remark : 1、本优惠券仅供在D2C全球设计师集成平台官方网站使用。
             * 2、本券不得叠加使用，一个订单仅限使用一张。
             * 3、本券不可兑现，不可找零。
             * 4、所有解释权仅归D2C全球设计师集成平台所有
             * enabledate : 15/08/16 22:01
             * expiredate : 15/09/15 22:01
             * status : USED
             * url : /page/nvshenxinzhuang
             * isExpired : 1
             */

            private List<ListEntity> list;

            public void setIndex(int index) {
                this.index = index;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public void setPrevious(boolean previous) {
                this.previous = previous;
            }

            public void setNext(boolean next) {
                this.next = next;
            }

            public void setList(List<ListEntity> list) {
                this.list = list;
            }

            public int getIndex() {
                return index;
            }

            public int getPageSize() {
                return pageSize;
            }

            public int getTotal() {
                return total;
            }

            public boolean isPrevious() {
                return previous;
            }

            public boolean isNext() {
                return next;
            }

            public List<ListEntity> getList() {
                return list;
            }

            public static class ListEntity implements Identifiable {
                private long id;
                private String name;
                private String type;
                private String code;
                private long amount;
                private long needAmount;
                private String remark;
                private String enabledate;
                private String expiredate;
                private String status;
                private String url;
                private int isExpired;
                private boolean isExpand;
                private Double price;
                private int transfer;//是否可赠送  0为不可赠送1为可赠送

                public int getTransfer() {
                    return transfer;
                }

                public void setTransfer(int transfer) {
                    this.transfer = transfer;
                }

                public Double getPrice() {
                    return price;
                }

                public void setPrice(Double price) {
                    this.price = price;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public void setAmount(long amount) {
                    this.amount = amount;
                }

                public void setNeedAmount(long needAmount) {
                    this.needAmount = needAmount;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
                }

                public void setEnabledate(String enabledate) {
                    this.enabledate = enabledate;
                }

                public void setExpiredate(String expiredate) {
                    this.expiredate = expiredate;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public void setIsExpired(int isExpired) {
                    this.isExpired = isExpired;
                }

                public long getId() {
                    return id;
                }

                public String getName() {
                    return name;
                }

                public String getType() {
                    return type;
                }

                public String getCode() {
                    return code;
                }

                public long getAmount() {
                    return amount;
                }

                public long getNeedAmount() {
                    return needAmount;
                }

                public String getRemark() {
                    return remark;
                }

                public String getEnabledate() {
                    return enabledate;
                }

                public String getExpiredate() {
                    return expiredate;
                }

                public String getStatus() {
                    return status;
                }

                public String getUrl() {
                    return url;
                }

                public int getIsExpired() {
                    return isExpired;
                }

                public boolean isExpand() {
                    return isExpand;
                }

                public void setExpand(boolean expand) {
                    isExpand = expand;
                }
            }
        }
    }
}
