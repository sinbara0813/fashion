package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Fixme
 * Author: Blend
 * Date: 2016/08/27 23:40
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class LiveCountHeadpicBean extends BaseBean {

    /**
     * realTime : {"watchingCount":2,"headPics":[{"headPic":"/app/a/16/12/15/d9041d2607aaac93ce302d57cb77b3a5","memberId":1947004},{"headPic":"/member_head/49/795049/c5f02e1d5f2843ce8d77663f038d92cd.png","memberId":795049}]}
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
         * headPics : [{"headPic":"/app/a/16/12/15/d9041d2607aaac93ce302d57cb77b3a5","memberId":1947004},{"headPic":"/member_head/49/795049/c5f02e1d5f2843ce8d77663f038d92cd.png","memberId":795049}]
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
            /**
             * headPic : /app/a/16/12/15/d9041d2607aaac93ce302d57cb77b3a5
             * memberId : 1947004
             */

            private List<HeadPicsBean> headPics;

            public int getWatchingCount() {
                return watchingCount;
            }

            public void setWatchingCount(int watchingCount) {
                this.watchingCount = watchingCount;
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
