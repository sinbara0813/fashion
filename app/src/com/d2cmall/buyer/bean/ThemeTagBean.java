package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2017/9/7.
 */

public class ThemeTagBean extends BaseBean {

    /**
     * data : {"countTags":[{"name":"全部","count":4,"id":13,"pic":"/2017/09/05/085342e6878cc88c13a6526e741a0b55e00f10.png"},{"name":"达人推荐","count":3,"id":4,"pic":"/2017/09/05/081549d383c3c0021dafa1180e2d8c2d2fb7c1.png"},{"name":"礼物指南","count":0,"id":12,"pic":"/2017/09/05/085244d9a8b776202b4f37a9e984bd7d6f4fed.png"},{"name":"男得好货","count":1,"id":11,"pic":"/2017/09/05/085204b6ff919712e8405369cc4efc0ad36cfa.png"},{"name":"穿衣搭配","count":0,"id":10,"pic":"/2017/09/05/085019aee0d46ac1eafaf36965bb7804a23e0e.png"},{"name":"明星衣橱","count":0,"id":9,"pic":"/2017/09/05/0849272a5d9d18648409a99444481533664051.png"},{"name":"新奇酷玩","count":0,"id":8,"pic":"/2017/09/05/084859052c999b933b11adf70953d506d504b9.png"},{"name":"最in潮品","count":0,"id":7,"pic":"/2017/09/05/084706c4eb4b69a17a6c3554f9fdb3d922aef3.png"},{"name":"品质生活","count":0,"id":2,"pic":"/2017/09/05/081613f09934515c232b9fe613dc05c2a1d01a.png"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<CountTagsBean> countTags;

        public List<CountTagsBean> getCountTags() {
            return countTags;
        }

        public void setCountTags(List<CountTagsBean> countTags) {
            this.countTags = countTags;
        }

        public static class CountTagsBean {
            /**
             * name : 全部
             * count : 4
             * id : 13
             * pic : /2017/09/05/085342e6878cc88c13a6526e741a0b55e00f10.png
             */

            private String name;
            private int count;
            private int id;
            private String pic;

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

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }
        }
    }
}
