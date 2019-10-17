package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CouponCountsBean extends BaseBean {

    /**
     * data : {"myCoupons":{"next":false,"total":1,"previous":false,"index":1,"pageSize":40,"list":[{"amount":50,"code":"S1487580108881-1947173","name":"优惠劵jh1","remark":"满100-50","id":3642343,"expiredate":"2017/02/28 00:00","type":"CASH","isExpired":0,"needAmount":100,"enabledate":"2017/02/20 00:00","url":null,"status":"CLAIMED"}]}}
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
         * myCoupons : {"next":false,"total":1,"previous":false,"index":1,"pageSize":40,"list":[{"amount":50,"code":"S1487580108881-1947173","name":"优惠劵jh1","remark":"满100-50","id":3642343,"expiredate":"2017/02/28 00:00","type":"CASH","isExpired":0,"needAmount":100,"enabledate":"2017/02/20 00:00","url":null,"status":"CLAIMED"}]}
         */

        private MyCouponsBean myCoupons;

        public MyCouponsBean getMyCoupons() {
            return myCoupons;
        }

        public void setMyCoupons(MyCouponsBean myCoupons) {
            this.myCoupons = myCoupons;
        }

        public static class MyCouponsBean {
            /**
             * next : false
             * total : 1
             * previous : false
             * index : 1
             * pageSize : 40
             * list : [{"amount":50,"code":"S1487580108881-1947173","name":"优惠劵jh1","remark":"满100-50","id":3642343,"expiredate":"2017/02/28 00:00","type":"CASH","isExpired":0,"needAmount":100,"enabledate":"2017/02/20 00:00","url":null,"status":"CLAIMED"}]
             */

            private boolean next;
            private int total;
            private boolean previous;
            private int index;
            private int pageSize;
            private List<ListBean> list;

            public boolean isNext() {
                return next;
            }

            public void setNext(boolean next) {
                this.next = next;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public boolean isPrevious() {
                return previous;
            }

            public void setPrevious(boolean previous) {
                this.previous = previous;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * amount : 50
                 * code : S1487580108881-1947173
                 * name : 优惠劵jh1
                 * remark : 满100-50
                 * id : 3642343
                 * expiredate : 2017/02/28 00:00
                 * type : CASH
                 * isExpired : 0
                 * needAmount : 100
                 * enabledate : 2017/02/20 00:00
                 * url : null
                 * status : CLAIMED
                 */

                private int amount;
                private String code;
                private String name;
                private String remark;
                private int id;
                private String expiredate;
                private String type;
                private int isExpired;
                private int needAmount;
                private String enabledate;
                private Object url;
                @SerializedName("status")
                private String statusX;

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

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
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

                public String getStatusX() {
                    return statusX;
                }

                public void setStatusX(String statusX) {
                    this.statusX = statusX;
                }
            }
        }
    }
}
