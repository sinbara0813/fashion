package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Fixme
 * Author: PengHong
 * Date: 2016/09/20 17:00
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class DepositDetailBean extends BaseBean {
    /**
     * payItems : {"index":1,"pageSize":10,"total":124,"previous":false,"next":true,"list":[{"id":5674,"sn":"P160914732133015611225","transactionTime":"2016/09/07 09:54:52","transactionInfo":"订单编号:AM1473213549422-0","outAmount":0.98,"inAmount":0,"payTypeName":"支付","businessType":"订单","memo":"订单支付"},{"id":5634,"sn":"P160914731536595571225","transactionTime":"2016/09/06 17:17:47","transactionInfo":"订单编号:Q14731536523525975","outAmount":770.8,"inAmount":0,"payTypeName":"支付","businessType":"订单","memo":"订单支付"},{"id":5633,"sn":"P160914731529922081225","transactionTime":"2016/09/06 17:02:31","transactionInfo":"订单编号:Q14731527339285975","outAmount":615.46,"inAmount":0,"payTypeName":"支付","businessType":"订单","memo":"订单支付"},{"id":5632,"sn":"P160914731528328831225","transactionTime":"2016/09/06 17:02:58","transactionInfo":"订单编号:Q14731527616345975","outAmount":770.79,"inAmount":0,"payTypeName":"支付","businessType":"订单","memo":"订单支付"},{"id":5331,"sn":"P160814725257585271225","transactionTime":"2016/08/29 20:57:57","transactionInfo":"订单编号:AM1472475641967-0","outAmount":488.46,"inAmount":0,"payTypeName":"支付","businessType":"订单","memo":"订单支付"},{"id":5269,"sn":"P160814723165878781225","transactionTime":"2016/08/27 15:28:55","transactionInfo":"充值信息：1472282935253","outAmount":0,"inAmount":0.01,"payTypeName":"充值","businessType":"支付","memo":"充值活动：充值大礼包"},{"id":5268,"sn":"P160814723164743721225","transactionTime":"2016/08/27 15:25:31","transactionInfo":"充值信息：1472282731290","outAmount":0,"inAmount":0.01,"payTypeName":"充值","businessType":"支付","memo":"充值活动：充值大礼包"},{"id":5262,"sn":"P160814723150349271225","transactionTime":"2016/08/27 15:02:49","transactionInfo":"充值信息：1472281368862","outAmount":0,"inAmount":0.01,"payTypeName":"充值","businessType":"支付","memo":"充值活动：充值大礼包"},{"id":5234,"sn":"P160814722897250871225","transactionTime":"2016/08/27 15:58:20","transactionInfo":"充值信息：1472284700041","outAmount":0,"inAmount":0.01,"payTypeName":"充值","businessType":"支付","memo":"充值活动：充值大礼包"},{"id":5232,"sn":"P160814722890147251225","transactionTime":"2016/08/27 15:46:18","transactionInfo":"充值信息：1472283977646","outAmount":0,"inAmount":0.01,"payTypeName":"充值","businessType":"支付","memo":"充值活动：充值大礼包"}]}
     */

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * index : 1
         * pageSize : 10
         * total : 124
         * previous : false
         * next : true
         * list : [{"id":5674,"sn":"P160914732133015611225","transactionTime":"2016/09/07 09:54:52","transactionInfo":"订单编号:AM1473213549422-0","outAmount":0.98,"inAmount":0,"payTypeName":"支付","businessType":"订单","memo":"订单支付"},{"id":5634,"sn":"P160914731536595571225","transactionTime":"2016/09/06 17:17:47","transactionInfo":"订单编号:Q14731536523525975","outAmount":770.8,"inAmount":0,"payTypeName":"支付","businessType":"订单","memo":"订单支付"},{"id":5633,"sn":"P160914731529922081225","transactionTime":"2016/09/06 17:02:31","transactionInfo":"订单编号:Q14731527339285975","outAmount":615.46,"inAmount":0,"payTypeName":"支付","businessType":"订单","memo":"订单支付"},{"id":5632,"sn":"P160914731528328831225","transactionTime":"2016/09/06 17:02:58","transactionInfo":"订单编号:Q14731527616345975","outAmount":770.79,"inAmount":0,"payTypeName":"支付","businessType":"订单","memo":"订单支付"},{"id":5331,"sn":"P160814725257585271225","transactionTime":"2016/08/29 20:57:57","transactionInfo":"订单编号:AM1472475641967-0","outAmount":488.46,"inAmount":0,"payTypeName":"支付","businessType":"订单","memo":"订单支付"},{"id":5269,"sn":"P160814723165878781225","transactionTime":"2016/08/27 15:28:55","transactionInfo":"充值信息：1472282935253","outAmount":0,"inAmount":0.01,"payTypeName":"充值","businessType":"支付","memo":"充值活动：充值大礼包"},{"id":5268,"sn":"P160814723164743721225","transactionTime":"2016/08/27 15:25:31","transactionInfo":"充值信息：1472282731290","outAmount":0,"inAmount":0.01,"payTypeName":"充值","businessType":"支付","memo":"充值活动：充值大礼包"},{"id":5262,"sn":"P160814723150349271225","transactionTime":"2016/08/27 15:02:49","transactionInfo":"充值信息：1472281368862","outAmount":0,"inAmount":0.01,"payTypeName":"充值","businessType":"支付","memo":"充值活动：充值大礼包"},{"id":5234,"sn":"P160814722897250871225","transactionTime":"2016/08/27 15:58:20","transactionInfo":"充值信息：1472284700041","outAmount":0,"inAmount":0.01,"payTypeName":"充值","businessType":"支付","memo":"充值活动：充值大礼包"},{"id":5232,"sn":"P160814722890147251225","transactionTime":"2016/08/27 15:46:18","transactionInfo":"充值信息：1472283977646","outAmount":0,"inAmount":0.01,"payTypeName":"充值","businessType":"支付","memo":"充值活动：充值大礼包"}]
         */

        private PayItemsEntity payItems;

        public PayItemsEntity getPayItems() {
            return payItems;
        }

        public void setPayItems(PayItemsEntity payItems) {
            this.payItems = payItems;
        }

        public static class PayItemsEntity {
            private int index;
            private int pageSize;
            private int total;
            private boolean previous;
            private boolean next;
            /**
             * id : 5674
             * sn : P160914732133015611225
             * transactionTime : 2016/09/07 09:54:52
             * transactionInfo : 订单编号:AM1473213549422-0
             * outAmount : 0.98
             * inAmount : 0
             * payTypeName : 支付
             * businessType : 订单
             * memo : 订单支付
             */

            private List<ListEntity> list;

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

            public boolean isNext() {
                return next;
            }

            public void setNext(boolean next) {
                this.next = next;
            }

            public List<ListEntity> getList() {
                return list;
            }

            public void setList(List<ListEntity> list) {
                this.list = list;
            }

            public static class ListEntity implements Identifiable {
                private long id;
                private String sn;
                private String transactionTime;
                private String transactionInfo;
                private double outAmount;
                private double inAmount;
                private String payTypeName;
                private String businessType;
                private String memo;
                private double outGiftAmount;
                private double inGiftAmount;
                private double limitGiftAmount;

                public double getLimitGiftAmount() {
                    return limitGiftAmount;
                }

                public void setLimitGiftAmount(double limitGiftAmount) {
                    this.limitGiftAmount = limitGiftAmount;
                }

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public String getSn() {
                    return sn;
                }

                public void setSn(String sn) {
                    this.sn = sn;
                }

                public String getTransactionTime() {
                    return transactionTime;
                }

                public void setTransactionTime(String transactionTime) {
                    this.transactionTime = transactionTime;
                }

                public String getTransactionInfo() {
                    return transactionInfo;
                }

                public void setTransactionInfo(String transactionInfo) {
                    this.transactionInfo = transactionInfo;
                }

                public double getOutAmount() {
                    return outAmount;
                }

                public void setOutAmount(double outAmount) {
                    this.outAmount = outAmount;
                }

                public double getInAmount() {
                    return inAmount;
                }

                public void setInAmount(double inAmount) {
                    this.inAmount = inAmount;
                }

                public String getPayTypeName() {
                    return payTypeName;
                }

                public void setPayTypeName(String payTypeName) {
                    this.payTypeName = payTypeName;
                }

                public String getBusinessType() {
                    return businessType;
                }

                public void setBusinessType(String businessType) {
                    this.businessType = businessType;
                }

                public String getMemo() {
                    return memo;
                }

                public void setMemo(String memo) {
                    this.memo = memo;
                }

                public double getOutGiftAmount() {
                    return outGiftAmount;
                }

                public void setOutGiftAmount(double outGiftAmount) {
                    this.outGiftAmount = outGiftAmount;
                }

                public double getInGiftAmount() {
                    return inGiftAmount;
                }

                public void setInGiftAmount(double inGiftAmount) {
                    this.inGiftAmount = inGiftAmount;
                }

            }
        }
    }
}
