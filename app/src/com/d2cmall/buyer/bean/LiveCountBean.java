package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Fixme
 * Author: hrb
 * Date: 2017/03/29 17:11
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class LiveCountBean extends BaseBean {

    /**
     * realTime : {"watchingCount":2,"headPics":[{"headPic":"","memberId":1947153}],"playerCount":4}
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
         * watchingCount : 2
         * headPics : [{"headPic":"","memberId":1947153}]
         * playerCount : 4
         */

        private RealTimeBean realTime;

        public RealTimeBean getRealTime() {
            return realTime;
        }

        public void setRealTime(RealTimeBean realTime) {
            this.realTime = realTime;
        }

        public static class RealTimeBean {
            private int watchingCount;
            private int playerCount;
            /**
             * headPic :
             * memberId : 1947153
             */

            private List<HeadPicsBean> headPics;

            public int getWatchingCount() {
                return watchingCount;
            }

            public void setWatchingCount(int watchingCount) {
                this.watchingCount = watchingCount;
            }

            public int getPlayerCount() {
                return playerCount;
            }

            public void setPlayerCount(int playerCount) {
                this.playerCount = playerCount;
            }

            public List<HeadPicsBean> getHeadPics() {
                return headPics;
            }

            public void setHeadPics(List<HeadPicsBean> headPics) {
                this.headPics = headPics;
            }

            public static class HeadPicsBean {
                private String headPic;
                private int memberId;

                public String getHeadPic() {
                    return headPic;
                }

                public void setHeadPic(String headPic) {
                    this.headPic = headPic;
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
}
