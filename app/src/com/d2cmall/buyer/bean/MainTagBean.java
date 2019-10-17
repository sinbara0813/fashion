package com.d2cmall.buyer.bean;


import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

public class MainTagBean extends BaseBean {

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * id : 1
         * parent : MAIN
         * direction : TOP
         * title : 推荐
         * webUrl :
         * color : 184,103,11
         * sequence : 3
         * isDefault : 1
         */

        private List<SubModulesEntity> subModules;

        public void setSubModules(List<SubModulesEntity> subModules) {
            this.subModules = subModules;
        }

        public List<SubModulesEntity> getSubModules() {
            return subModules;
        }

        public static class SubModulesEntity {
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
            private String tPic;
            private String tbPic;

            public String gettPic() {
                return tPic;
            }

            public void settPic(String tPic) {
                this.tPic = tPic;
            }

            public String getTbPic() {
                return tbPic;
            }

            public void setTbPic(String tbPic) {
                this.tbPic = tbPic;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setParent(String parent) {
                this.parent = parent;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setWebUrl(String webUrl) {
                this.webUrl = webUrl;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public void setSequence(int sequence) {
                this.sequence = sequence;
            }

            public void setIsDefault(int isDefault) {
                this.isDefault = isDefault;
            }

            public int getId() {
                return id;
            }

            public String getParent() {
                return parent;
            }

            public String getDirection() {
                return direction;
            }

            public String getTitle() {
                return title;
            }

            public String getWebUrl() {
                return webUrl;
            }

            public String getColor() {
                return color;
            }

            public int getSequence() {
                return sequence;
            }

            public int getIsDefault() {
                return isDefault;
            }

            public int getIsCategory() {
                return isCategory;
            }

            public void setIsCategory(int isCategory) {
                this.isCategory = isCategory;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }
        }
    }
}
