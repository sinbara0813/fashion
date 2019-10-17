package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2017/11/1.
 */

public class RedPacketListBean extends BaseBean {

    /**
     * data : {"redPacketsItems":{"next":false,"total":6,"previous":false,"index":1,"pageSize":40,"list":[{"amount":-926,"businessName":"下单支付","statusName":"等待支付","id":14,"createDate":"2017/11/01 09:49:29"},{"amount":-569,"businessName":"下单支付","statusName":"等待支付","id":12,"createDate":"2017/11/01 09:44:43"},{"amount":-999,"businessName":"下单支付","statusName":"等待支付","id":10,"createDate":"2017/10/31 11:33:12"},{"amount":-329,"businessName":"下单支付","statusName":"等待支付","id":9,"createDate":"2017/10/31 11:27:47"},{"amount":-209,"businessName":"下单支付","statusName":"等待支付","id":7,"createDate":"2017/10/31 11:09:39"},{"amount":-100,"businessName":"下单支付","statusName":"等待支付","id":6,"createDate":"2017/10/31 11:02:24"}]}}
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
         * redPacketsItems : {"next":false,"total":6,"previous":false,"index":1,"pageSize":40,"list":[{"amount":-926,"businessName":"下单支付","statusName":"等待支付","id":14,"createDate":"2017/11/01 09:49:29"},{"amount":-569,"businessName":"下单支付","statusName":"等待支付","id":12,"createDate":"2017/11/01 09:44:43"},{"amount":-999,"businessName":"下单支付","statusName":"等待支付","id":10,"createDate":"2017/10/31 11:33:12"},{"amount":-329,"businessName":"下单支付","statusName":"等待支付","id":9,"createDate":"2017/10/31 11:27:47"},{"amount":-209,"businessName":"下单支付","statusName":"等待支付","id":7,"createDate":"2017/10/31 11:09:39"},{"amount":-100,"businessName":"下单支付","statusName":"等待支付","id":6,"createDate":"2017/10/31 11:02:24"}]}
         */

        private RedPacketsItemsBean redPacketsItems;

        public RedPacketsItemsBean getRedPacketsItems() {
            return redPacketsItems;
        }

        public void setRedPacketsItems(RedPacketsItemsBean redPacketsItems) {
            this.redPacketsItems = redPacketsItems;
        }

        public static class RedPacketsItemsBean {
            /**
             * next : false
             * total : 6
             * previous : false
             * index : 1
             * pageSize : 40
             * list : [{"amount":-926,"businessName":"下单支付","statusName":"等待支付","id":14,"createDate":"2017/11/01 09:49:29"},{"amount":-569,"businessName":"下单支付","statusName":"等待支付","id":12,"createDate":"2017/11/01 09:44:43"},{"amount":-999,"businessName":"下单支付","statusName":"等待支付","id":10,"createDate":"2017/10/31 11:33:12"},{"amount":-329,"businessName":"下单支付","statusName":"等待支付","id":9,"createDate":"2017/10/31 11:27:47"},{"amount":-209,"businessName":"下单支付","statusName":"等待支付","id":7,"createDate":"2017/10/31 11:09:39"},{"amount":-100,"businessName":"下单支付","statusName":"等待支付","id":6,"createDate":"2017/10/31 11:02:24"}]
             */

            private boolean next;
            private int total;
            private boolean previous;
            private int index;
            private int pageSize;
            private List<RedPacketBean.DataBean.RedPacketsItemsBean.ListBean> list;

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

            public List<RedPacketBean.DataBean.RedPacketsItemsBean.ListBean> getList() {
                return list;
            }

            public void setList(List<RedPacketBean.DataBean.RedPacketsItemsBean.ListBean> list) {
                this.list = list;
            }

        }
    }
}
