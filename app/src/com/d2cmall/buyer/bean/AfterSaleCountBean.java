package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Created by rookie on 2017/9/29.
 */

public class AfterSaleCountBean extends BaseBean {

    /**
     * data : {"exchangeCount":{"processCount":0,"totalCount":0},"refundCount":{"processCount":1,"totalCount":9},"reshipCount":{"processCount":2,"totalCount":5}}
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
         * exchangeCount : {"processCount":0,"totalCount":0}
         * refundCount : {"processCount":1,"totalCount":9}
         * reshipCount : {"processCount":2,"totalCount":5}
         */

        private ExchangeCountBean exchangeCount;
        private RefundCountBean refundCount;
        private ReshipCountBean reshipCount;

        public ExchangeCountBean getExchangeCount() {
            return exchangeCount;
        }

        public void setExchangeCount(ExchangeCountBean exchangeCount) {
            this.exchangeCount = exchangeCount;
        }

        public RefundCountBean getRefundCount() {
            return refundCount;
        }

        public void setRefundCount(RefundCountBean refundCount) {
            this.refundCount = refundCount;
        }

        public ReshipCountBean getReshipCount() {
            return reshipCount;
        }

        public void setReshipCount(ReshipCountBean reshipCount) {
            this.reshipCount = reshipCount;
        }

        public static class ExchangeCountBean {
            /**
             * processCount : 0
             * totalCount : 0
             */

            private int processCount;
            private int totalCount;

            public int getProcessCount() {
                return processCount;
            }

            public void setProcessCount(int processCount) {
                this.processCount = processCount;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }
        }

        public static class RefundCountBean {
            /**
             * processCount : 1
             * totalCount : 9
             */

            private int processCount;
            private int totalCount;

            public int getProcessCount() {
                return processCount;
            }

            public void setProcessCount(int processCount) {
                this.processCount = processCount;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }
        }

        public static class ReshipCountBean {
            /**
             * processCount : 2
             * totalCount : 5
             */

            private int processCount;
            private int totalCount;

            public int getProcessCount() {
                return processCount;
            }

            public void setProcessCount(int processCount) {
                this.processCount = processCount;
            }

            public int getTotalCount() {
                return totalCount;
            }

            public void setTotalCount(int totalCount) {
                this.totalCount = totalCount;
            }
        }
    }
}
