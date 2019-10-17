package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2018/9/10.
 */

public class UpdateBrandCategoryBean extends BaseBean {

    /**
     * data : {"topCateArray":[{"name":"女装","count":6,"id":10001},{"name":"男装","count":3,"id":10002},{"name":"生活家居","count":7,"id":10003}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<TopCateArrayBean> topCateArray;

        public List<TopCateArrayBean> getTopCateArray() {
            return topCateArray;
        }

        public void setTopCateArray(List<TopCateArrayBean> topCateArray) {
            this.topCateArray = topCateArray;
        }

        public static class TopCateArrayBean {
            /**
             * name : 女装
             * count : 6
             * id : 10001
             */

            private String name;
            private int count;
            private int id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
