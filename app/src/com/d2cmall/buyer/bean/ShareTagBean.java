package com.d2cmall.buyer.bean;


import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

public class ShareTagBean extends BaseBean {

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * id : 1
         * name : 时尚达人
         * sort : 0
         */

        private List<TagListEntity> tagList;

        public List<TagListEntity> getTagList() {
            return tagList;
        }

        public void setTagList(List<TagListEntity> tagList) {
            this.tagList = tagList;
        }

        public static class TagListEntity {
            private long id;
            private String name;
            private int sort;

            public long getId() {
                return id;
            }

            public void setId(long id) {
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
