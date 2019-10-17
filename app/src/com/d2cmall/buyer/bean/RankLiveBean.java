package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rookie on 2017/9/18.
 */

public class RankLiveBean extends BaseBean implements Serializable{



    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private HotLivesBean hotLives;

        public HotLivesBean getHotLives() {
            return hotLives;
        }

        public void setHotLives(HotLivesBean hotLives) {
            this.hotLives = hotLives;
        }

        public static class HotLivesBean {
            private boolean next;
            private int total;
            private boolean previous;
            private int index;
            private int pageSize;
            private List<LiveListBean.DataBean.LivesBean.ListBean> list;

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

            public List<LiveListBean.DataBean.LivesBean.ListBean> getList() {
                return list;
            }

            public void setList(List<LiveListBean.DataBean.LivesBean.ListBean> list) {
                this.list = list;
            }

        }
    }
}
