package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Created by Administrator on 2018/1/20.
 * Description : PartnerSaleDataBean
 */

public class PartnerSaleDataBean extends BaseBean {

    /**
     * data : {"allData":{"amount":27372.2,"rebate":12113.58,"count":21},"todayData":{"amount":0,"rebate":0,"count":0}}
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
         * allData : {"amount":27372.2,"rebate":12113.58,"count":21}
         * todayData : {"amount":0,"rebate":0,"count":0}
         */

        private AllDataBean allData;
        private TodayDataBean todayData;

        public AllDataBean getAllData() {
            return allData;
        }

        public void setAllData(AllDataBean allData) {
            this.allData = allData;
        }

        public TodayDataBean getTodayData() {
            return todayData;
        }

        public void setTodayData(TodayDataBean todayData) {
            this.todayData = todayData;
        }

        public static class AllDataBean {
            /**
             * amount : 27372.2
             * rebate : 12113.58
             * count : 21
             */

            private double amount;
            private double rebate;
            private int count;

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
        }

        public static class TodayDataBean {
            /**
             * amount : 0.0
             * rebate : 0.0
             * count : 0
             */

            private double amount;
            private double rebate;
            private int count;

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
        }


            /**
             * allData : {"amount":50947.89,"rebate":8265.083,"count":52}
             * todayChildrenData : {"amount":0,"rebate":0,"count":0}
             * todayData : {"amount":3918,"rebate":783.6,"count":5}
             * allChildrenData : {"amount":158205.2,"rebate":42255.33,"count":42}
             */

            private TodayChildrenDataBean todayChildrenData;
            private AllChildrenDataBean allChildrenData;

            public TodayChildrenDataBean getTodayChildrenData() {
                return todayChildrenData;
            }

            public void setTodayChildrenData(TodayChildrenDataBean todayChildrenData) {
                this.todayChildrenData = todayChildrenData;
            }

            public AllChildrenDataBean getAllChildrenData() {
                return allChildrenData;
            }

            public void setAllChildrenData(AllChildrenDataBean allChildrenData) {
                this.allChildrenData = allChildrenData;
            }


            public static class TodayChildrenDataBean {
                /**
                 * amount : 0
                 * rebate : 0
                 * count : 0
                 */

                private double amount;
                private double rebate;
                private int count;

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
            }


            public static class AllChildrenDataBean {
                /**
                 * amount : 158205.2
                 * rebate : 42255.33
                 * count : 42
                 */

                private double amount;
                private double rebate;
                private int count;

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
            }
        }

}
