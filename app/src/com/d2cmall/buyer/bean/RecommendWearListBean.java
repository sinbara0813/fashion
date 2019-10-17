package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 * Description : RecommendWearListBean
 */

public class RecommendWearListBean extends BaseBean {

    /**
     * data : {"recommends":{"next":false,"total":1,"previous":false,"index":1,"pageSize":20,"list":[{"temp":"1.10","city":"1","loginCode":"5","description":"3","video":"2","headPic":"1","timeLength":1,"nickname":"b","id":1,"camera":"2","pics":[],"open":1,"createDate":"2018-11-19 17:08:28"}]}}
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
         * recommends : {"next":false,"total":1,"previous":false,"index":1,"pageSize":20,"list":[{"temp":"1.10","city":"1","loginCode":"5","description":"3","video":"2","headPic":"1","timeLength":1,"nickname":"b","id":1,"camera":"2","pics":[],"open":1,"createDate":"2018-11-19 17:08:28"}]}
         */

        private RecommendsBean recommends;

        public RecommendsBean getRecommends() {
            return recommends;
        }

        public void setRecommends(RecommendsBean recommends) {
            this.recommends = recommends;
        }

        public static class RecommendsBean {
            /**
             * next : false
             * total : 1
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"temp":"1.10","city":"1","loginCode":"5","description":"3","video":"2","headPic":"1","timeLength":1,"nickname":"b","id":1,"camera":"2","pics":[],"open":1,"createDate":"2018-11-19 17:08:28"}]
             */

            private boolean next;
            private int total;
            private boolean previous;
            private int index;
            private int pageSize;
            private List<MyWearCollocationBean.DataBean.MyWardrobeCollocationsBean.ListBean> list;

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

            public List<MyWearCollocationBean.DataBean.MyWardrobeCollocationsBean.ListBean> getList() {
                return list;
            }

            public void setList(List<MyWearCollocationBean.DataBean.MyWardrobeCollocationsBean.ListBean> list) {
                this.list = list;
            }

        }
    }
}
