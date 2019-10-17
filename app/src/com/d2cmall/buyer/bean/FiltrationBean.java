package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * 筛选Bean
 * Author: Blend
 * Date: 2016/11/04 16:13
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class FiltrationBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * sourceId : 10502
         * type : typedesigner
         * name : BENOIT MISSOLIN
         * parentId : null
         */

        private List<TypedesignerBean> typedesigner;
        /**
         * sourceId : 4
         * type : typetopcategory
         * name : 生活家居
         * parentId : null
         */

        private List<TypetopcategoryBean> typetopcategory;
        /**
         * sourceId : 1952
         * type : typeproductcategory
         * name : 首饰盒
         * parentId : 1739
         */

        private List<TypeproductcategoryBean> typeproductcategory;

        public List<TypedesignerBean> getTypedesigner() {
            return typedesigner;
        }

        public void setTypedesigner(List<TypedesignerBean> typedesigner) {
            this.typedesigner = typedesigner;
        }

        public List<TypetopcategoryBean> getTypetopcategory() {
            return typetopcategory;
        }

        public void setTypetopcategory(List<TypetopcategoryBean> typetopcategory) {
            this.typetopcategory = typetopcategory;
        }

        public List<TypeproductcategoryBean> getTypeproductcategory() {
            return typeproductcategory;
        }

        public void setTypeproductcategory(List<TypeproductcategoryBean> typeproductcategory) {
            this.typeproductcategory = typeproductcategory;
        }

        public static class TypedesignerBean {
            private long sourceId;
            private String type;
            private String name;
            private Object parentId;

            public long getSourceId() {
                return sourceId;
            }

            public void setSourceId(long sourceId) {
                this.sourceId = sourceId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getParentId() {
                return parentId;
            }

            public void setParentId(Object parentId) {
                this.parentId = parentId;
            }
        }

        public static class TypetopcategoryBean {
            private int sourceId;
            private String type;
            private String name;
            private Object parentId;
            private boolean isChecked;

            public int getSourceId() {
                return sourceId;
            }

            public void setSourceId(int sourceId) {
                this.sourceId = sourceId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getParentId() {
                return parentId;
            }

            public void setParentId(Object parentId) {
                this.parentId = parentId;
            }

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }
        }

        public static class TypeproductcategoryBean {
            private int sourceId;
            private String type;
            private String name;
            private String parentId;

            public int getSourceId() {
                return sourceId;
            }

            public void setSourceId(int sourceId) {
                this.sourceId = sourceId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }
        }
    }
}
