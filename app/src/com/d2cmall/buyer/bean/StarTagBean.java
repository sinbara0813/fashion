package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/14 16:44
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class StarTagBean extends BaseBean {

    /**
     * data : {"tags":[{"name":"炫彩发光鞋","id":39},{"name":"小时代热卖商品","id":40},{"name":"李欣霓 \u201c最好的时光\u201d 满1000减300","id":41}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<TagsBean> tags;

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public static class TagsBean {
            /**
             * name : 炫彩发光鞋
             * id : 39
             */

            private String name;
            private int id;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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
