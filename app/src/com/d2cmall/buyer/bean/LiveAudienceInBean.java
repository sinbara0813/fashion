package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2018/1/4.
 */

public class LiveAudienceInBean extends BaseBean {

    /**
     * data : {"realtime":{"headers":[{"headPic":"/app/a/17/12/07/4381b2e2083375acaf84603f9f03a099","memberId":2865965},{"headPic":"/app/a/18/01/02/BF116FC4D430686C7CB00FEA34B516C7","memberId":3003869}],"count":2}}
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
         * realtime : {"headers":[{"headPic":"/app/a/17/12/07/4381b2e2083375acaf84603f9f03a099","memberId":2865965},{"headPic":"/app/a/18/01/02/BF116FC4D430686C7CB00FEA34B516C7","memberId":3003869}],"count":2}
         */

        private RealtimeBean realtime;

        public RealtimeBean getRealtime() {
            return realtime;
        }

        public void setRealtime(RealtimeBean realtime) {
            this.realtime = realtime;
        }

        public static class RealtimeBean {
            /**
             * headers : [{"headPic":"/app/a/17/12/07/4381b2e2083375acaf84603f9f03a099","memberId":2865965},{"headPic":"/app/a/18/01/02/BF116FC4D430686C7CB00FEA34B516C7","memberId":3003869}]
             * count : 2
             */

            private int count;
            private List<HeadersBean> headers;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public List<HeadersBean> getHeaders() {
                return headers;
            }

            public void setHeaders(List<HeadersBean> headers) {
                this.headers = headers;
            }

            public static class HeadersBean {
                /**
                 * headPic : /app/a/17/12/07/4381b2e2083375acaf84603f9f03a099
                 * memberId : 2865965
                 */

                private String headPic;
                private long memberId;

                public String getHeadPic() {
                    return headPic;
                }

                public void setHeadPic(String headPic) {
                    this.headPic = headPic;
                }

                public long getMemberId() {
                    return memberId;
                }

                public void setMemberId(long memberId) {
                    this.memberId = memberId;
                }
            }
        }
    }
}
