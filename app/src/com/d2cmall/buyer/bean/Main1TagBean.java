package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Name: d2c3.0
 * Anthor: hrb
 * Date: 2017/8/29 13:03
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class Main1TagBean extends BaseBean {
    private MainTagBean.DataEntity data;

    public void setData(MainTagBean.DataEntity data) {
        this.data = data;
    }

    public MainTagBean.DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        private List<SubModulesBean> subModules;

        public List<SubModulesBean> getSubModules() {
            return subModules;
        }

        public void setSubModules(List<SubModulesBean> subModules) {
            this.subModules = subModules;
        }

        public static class SubModulesBean {
            /**
             * parent : MAIN
             * sequence : 999999
             * isDefault : 1
             * isCategory : 0
             * color : 000000
             * webUrl :
             * id : 109
             * title : 推荐
             * categoryIds : null
             * direction : TOP
             */

            private String parent;
            private int sequence;
            private int isDefault;
            private int isCategory;
            private String color;
            private String webUrl;
            private int id;
            private String title;
            private String categoryId;
            private String direction;

            public String getParent() {
                return parent;
            }

            public void setParent(String parent) {
                this.parent = parent;
            }

            public int getSequence() {
                return sequence;
            }

            public void setSequence(int sequence) {
                this.sequence = sequence;
            }

            public int getIsDefault() {
                return isDefault;
            }

            public void setIsDefault(int isDefault) {
                this.isDefault = isDefault;
            }

            public int getIsCategory() {
                return isCategory;
            }

            public void setIsCategory(int isCategory) {
                this.isCategory = isCategory;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getWebUrl() {
                return webUrl;
            }

            public void setWebUrl(String webUrl) {
                this.webUrl = webUrl;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }
        }
    }
}
