package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2017/10/31.
 */

public class RedPacketBean extends BaseBean {


    /**
     * data : {"redPacketsItems":{"next":true,"total":6,"previous":false,"index":1,"pageSize":3,"list":[{"amount":-926,"businessName":"下单支付","statusName":"等待支付","id":14,"createDate":"2017/11/01 09:49:29"},{"amount":-569,"businessName":"下单支付","statusName":"等待支付","id":12,"createDate":"2017/11/01 09:44:43"},{"amount":-999,"businessName":"下单支付","statusName":"等待支付","id":10,"createDate":"2017/10/31 11:33:12"}]},"account":{"totalAmount":68956,"setPassword":true,"freezeAmount":0,"availableAmount":68956,"redAmount":0,"mobile":"100000000001","isRed":true,"id":942915,"redDate":"2017/11/12 00:00:00"}}
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
         * redPacketsItems : {"next":true,"total":6,"previous":false,"index":1,"pageSize":3,"list":[{"amount":-926,"businessName":"下单支付","statusName":"等待支付","id":14,"createDate":"2017/11/01 09:49:29"},{"amount":-569,"businessName":"下单支付","statusName":"等待支付","id":12,"createDate":"2017/11/01 09:44:43"},{"amount":-999,"businessName":"下单支付","statusName":"等待支付","id":10,"createDate":"2017/10/31 11:33:12"}]}
         * account : {"totalAmount":68956,"setPassword":true,"freezeAmount":0,"availableAmount":68956,"redAmount":0,"mobile":"100000000001","isRed":true,"id":942915,"redDate":"2017/11/12 00:00:00"}
         */

        private RedPacketsItemsBean redPacketsItems;
        private AccountBean account;

        public RedPacketsItemsBean getRedPacketsItems() {
            return redPacketsItems;
        }

        public void setRedPacketsItems(RedPacketsItemsBean redPacketsItems) {
            this.redPacketsItems = redPacketsItems;
        }

        public AccountBean getAccount() {
            return account;
        }

        public void setAccount(AccountBean account) {
            this.account = account;
        }

        public static class RedPacketsItemsBean {
            /**
             * next : true
             * total : 6
             * previous : false
             * index : 1
             * pageSize : 3
             * list : [{"amount":-926,"businessName":"下单支付","statusName":"等待支付","id":14,"createDate":"2017/11/01 09:49:29"},{"amount":-569,"businessName":"下单支付","statusName":"等待支付","id":12,"createDate":"2017/11/01 09:44:43"},{"amount":-999,"businessName":"下单支付","statusName":"等待支付","id":10,"createDate":"2017/10/31 11:33:12"}]
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
                 * amount : -926.0
                 * businessName : 下单支付
                 * statusName : 等待支付
                 * id : 14
                 * createDate : 2017/11/01 09:49:29
                 */

                private double amount;
                private String businessName;
                private String statusName;
                private int id;
                private String createDate;

                public double getAmount() {
                    return amount;
                }

                public void setAmount(double amount) {
                    this.amount = amount;
                }

                public String getBusinessName() {
                    return businessName;
                }

                public void setBusinessName(String businessName) {
                    this.businessName = businessName;
                }

                public String getStatusName() {
                    return statusName;
                }

                public void setStatusName(String statusName) {
                    this.statusName = statusName;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(String createDate) {
                    this.createDate = createDate;
                }
            }
        }

        public static class AccountBean {
            /**
             * totalAmount : 68956.0
             * setPassword : true
             * freezeAmount : 0.0
             * availableAmount : 68956.0
             * redAmount : 0.0
             * mobile : 100000000001
             * isRed : true
             * id : 942915
             * redDate : 2017/11/12 00:00:00
             */

            private double totalAmount;
            private boolean setPassword;
            private double freezeAmount;
            private double availableAmount;
            private double redAmount;
            private String mobile;
            private boolean isRed;
            private int id;
            private String redDate;

            public double getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(double totalAmount) {
                this.totalAmount = totalAmount;
            }

            public boolean isSetPassword() {
                return setPassword;
            }

            public void setSetPassword(boolean setPassword) {
                this.setPassword = setPassword;
            }

            public double getFreezeAmount() {
                return freezeAmount;
            }

            public void setFreezeAmount(double freezeAmount) {
                this.freezeAmount = freezeAmount;
            }

            public double getAvailableAmount() {
                return availableAmount;
            }

            public void setAvailableAmount(double availableAmount) {
                this.availableAmount = availableAmount;
            }

            public double getRedAmount() {
                return redAmount;
            }

            public void setRedAmount(double redAmount) {
                this.redAmount = redAmount;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public boolean isIsRed() {
                return isRed;
            }

            public void setIsRed(boolean isRed) {
                this.isRed = isRed;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getRedDate() {
                return redDate;
            }

            public void setRedDate(String redDate) {
                this.redDate = redDate;
            }
        }
    }
}
