package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rookie on 2018/4/17.
 */

public class BuyerTeamBean extends BaseBean {

    /**
     * data : {"amData":[{"level":1,"count":2,"status":1},{"level":2,"count":1,"status":-1},{"level":2,"count":1,"status":0},{"level":2,"count":2,"status":1}],"todayData":[{"level":1,"count":2},{"level":2,"count":4}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<BuyerDataBean> amData;
        private List<BuyerDataBean> dmData;
        public List<BuyerDataBean> getBuyerData() {
            return buyerData;
        }

        public void setBuyerData(List<BuyerDataBean> buyerData) {
            this.buyerData = buyerData;
        }

        private List<BuyerDataBean> buyerData;
        private List<TodayDataBean> todayData;

        public List<BuyerDataBean> getAmData() {
            return amData;
        }

        public List<BuyerDataBean> getDmData() {
            return dmData;
        }

        public void setDmData(List<BuyerDataBean> dmData) {
            this.dmData = dmData;
        }

        public void setAmData(List<BuyerDataBean> amData) {
            this.amData = amData;
        }

        public List<TodayDataBean> getTodayData() {
            return todayData;
        }

        public void setTodayData(List<TodayDataBean> todayData) {
            this.todayData = todayData;
        }

        public static class BuyerDataBean {
            /**
             * level : 1
             * count : 2
             * status : 1
             */

            private int level;
            private int count;
            //0:试用期 -1:关店(可重开)  -9：永久关店  1：正常
            @SerializedName("status")
            private int statusX;

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
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

        public static class TodayDataBean {
            /**
             * level : 1
             * count : 2
             */

            private int level;
            private int count;

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
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
