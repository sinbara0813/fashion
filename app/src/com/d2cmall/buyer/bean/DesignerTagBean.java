package com.d2cmall.buyer.bean;


import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

public class DesignerTagBean extends BaseBean {


    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * id : 55
         * name : 设计师推荐
         * sort : 0
         */

        private List<TagsEntity> tags;

        public List<TagsEntity> getTags() {
            return tags;
        }

        public void setTags(List<TagsEntity> tags) {
            this.tags = tags;
        }

        public static class TagsEntity {
            private int id;
            private String name;
            private int sort;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }
        }
    }
}
