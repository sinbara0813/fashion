package com.d2cmall.buyer.bean;


import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

public class SquareCarouselBean extends BaseBean {

    /**
     * subModule : {"id":5,"parent":"SQUARE","direction":"TOP","title":"推荐","webUrl":"","color":"0,230,255","sequence":0,"isDefault":1}
     * content : [{"id":41,"moduleId":5,"title":"朋友圈的banner","visible":0,"type":1,"sequence":0,"properties":{"width":"750","height":"290","top":"0","bottom":"10","left":"0","right":"0"},"sectionValues":[{"id":59,"sectionDefId":41,"shortTitle":"789","url":"/product/121001","frontPic":"/model/1603/f5e1dd3a2fe86b897a4fd33c9a850d48","sequence":2,"properties":{}},{"id":58,"sectionDefId":41,"shortTitle":"456","url":"/product/121001","frontPic":"/model/1603/75b87b28d3d6e8841b3fa0b339c5a4ed","sequence":1,"properties":{}},{"id":57,"sectionDefId":41,"shortTitle":"123","url":"/product/121001","frontPic":"/model/1603/2e5baa7f9da7ce0c2b254e3fe60ac457","sequence":0,"properties":{}}]}]
     */

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * id : 5
         * parent : SQUARE
         * direction : TOP
         * title : 推荐
         * webUrl :
         * color : 0,230,255
         * sequence : 0
         * isDefault : 1
         */

        private SubModuleEntity subModule;
        /**
         * id : 41
         * moduleId : 5
         * title : 朋友圈的banner
         * visible : 0
         * type : 1
         * sequence : 0
         * properties : {"width":"750","height":"290","top":"0","bottom":"10","left":"0","right":"0"}
         * sectionValues : [{"id":59,"sectionDefId":41,"shortTitle":"789","url":"/product/121001","frontPic":"/model/1603/f5e1dd3a2fe86b897a4fd33c9a850d48","sequence":2,"properties":{}},{"id":58,"sectionDefId":41,"shortTitle":"456","url":"/product/121001","frontPic":"/model/1603/75b87b28d3d6e8841b3fa0b339c5a4ed","sequence":1,"properties":{}},{"id":57,"sectionDefId":41,"shortTitle":"123","url":"/product/121001","frontPic":"/model/1603/2e5baa7f9da7ce0c2b254e3fe60ac457","sequence":0,"properties":{}}]
         */

        private List<ContentEntity> content;

        public SubModuleEntity getSubModule() {
            return subModule;
        }

        public void setSubModule(SubModuleEntity subModule) {
            this.subModule = subModule;
        }

        public List<ContentEntity> getContent() {
            return content;
        }

        public void setContent(List<ContentEntity> content) {
            this.content = content;
        }

        public static class SubModuleEntity {
            private int id;
            private String parent;
            private String direction;
            private String title;
            private String webUrl;
            private String color;
            private int sequence;
            private int isDefault;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getParent() {
                return parent;
            }

            public void setParent(String parent) {
                this.parent = parent;
            }

            public String getDirection() {
                return direction;
            }

            public void setDirection(String direction) {
                this.direction = direction;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getWebUrl() {
                return webUrl;
            }

            public void setWebUrl(String webUrl) {
                this.webUrl = webUrl;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
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
        }

        public static class ContentEntity {
            private int id;
            private int moduleId;
            private String title;
            private int visible;
            private int type;
            private int sequence;
            /**
             * width : 750
             * height : 290
             * top : 0
             * bottom : 10
             * left : 0
             * right : 0
             */

            private PropertiesEntity properties;
            /**
             * id : 59
             * sectionDefId : 41
             * shortTitle : 789
             * url : /product/121001
             * frontPic : /model/1603/f5e1dd3a2fe86b897a4fd33c9a850d48
             * sequence : 2
             * properties : {}
             */

            private List<SectionValuesEntity> sectionValues;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getModuleId() {
                return moduleId;
            }

            public void setModuleId(int moduleId) {
                this.moduleId = moduleId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getVisible() {
                return visible;
            }

            public void setVisible(int visible) {
                this.visible = visible;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getSequence() {
                return sequence;
            }

            public void setSequence(int sequence) {
                this.sequence = sequence;
            }

            public PropertiesEntity getProperties() {
                return properties;
            }

            public void setProperties(PropertiesEntity properties) {
                this.properties = properties;
            }

            public List<SectionValuesEntity> getSectionValues() {
                return sectionValues;
            }

            public void setSectionValues(List<SectionValuesEntity> sectionValues) {
                this.sectionValues = sectionValues;
            }

            public static class PropertiesEntity {
                private String width;
                private String height;
                private String top;
                private String bottom;
                private String left;
                private String right;

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }

                public String getTop() {
                    return top;
                }

                public void setTop(String top) {
                    this.top = top;
                }

                public String getBottom() {
                    return bottom;
                }

                public void setBottom(String bottom) {
                    this.bottom = bottom;
                }

                public String getLeft() {
                    return left;
                }

                public void setLeft(String left) {
                    this.left = left;
                }

                public String getRight() {
                    return right;
                }

                public void setRight(String right) {
                    this.right = right;
                }
            }

            public static class SectionValuesEntity {
                private int id;
                private int sectionDefId;
                private String shortTitle;
                private String url;
                private String frontPic;
                private int sequence;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getSectionDefId() {
                    return sectionDefId;
                }

                public void setSectionDefId(int sectionDefId) {
                    this.sectionDefId = sectionDefId;
                }

                public String getShortTitle() {
                    return shortTitle;
                }

                public void setShortTitle(String shortTitle) {
                    this.shortTitle = shortTitle;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getFrontPic() {
                    return frontPic;
                }

                public void setFrontPic(String frontPic) {
                    this.frontPic = frontPic;
                }

                public int getSequence() {
                    return sequence;
                }

                public void setSequence(int sequence) {
                    this.sequence = sequence;
                }
            }
        }
    }
}
