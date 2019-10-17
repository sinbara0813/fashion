package com.d2cmall.buyer.bean;


import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

public class SquareBean extends BaseBean {


    /**
     * data : {"content":[{"sequence":0,"sectionValues":[{"sequence":0,"sectionDefId":389,"shortTitle":"直播","id":8363,"frontPic":"/2018/01/05/044113d3369c87de62c8dadbfc7e9da5f4072d.gif","url":"http://m.d2cmall.com/zegolive/watch/443?share=bWVtYmVySWQ9MjgyODIyMyZkZXZpY2U9aW9z"}],"visible":0,"more":0,"id":1,"moduleId":72,"title":"直播","type":1,"moreUrl":null,"properties":{"top":0,"left":0,"bottom":10,"width":"750","right":0,"height":"290"}}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ContentBean> content;

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class ContentBean {
            /**
             * sequence : 0
             * sectionValues : [{"sequence":0,"sectionDefId":389,"shortTitle":"直播","id":8363,"frontPic":"/2018/01/05/044113d3369c87de62c8dadbfc7e9da5f4072d.gif","url":"http://m.d2cmall.com/zegolive/watch/443?share=bWVtYmVySWQ9MjgyODIyMyZkZXZpY2U9aW9z"}]
             * visible : 0
             * more : 0
             * id : 1
             * moduleId : 72
             * title : 直播
             * type : 1
             * moreUrl : null
             * properties : {"top":0,"left":0,"bottom":10,"width":"750","right":0,"height":"290"}
             */

            private int sequence;
            private int visible;
            private int more;
            private int id;
            private int moduleId;
            private String title;
            private int type;
            private Object moreUrl;
            private PropertiesBean properties;
            private List<SectionValuesBean> sectionValues;

            public int getSequence() {
                return sequence;
            }

            public void setSequence(int sequence) {
                this.sequence = sequence;
            }

            public int getVisible() {
                return visible;
            }

            public void setVisible(int visible) {
                this.visible = visible;
            }

            public int getMore() {
                return more;
            }

            public void setMore(int more) {
                this.more = more;
            }

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

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public Object getMoreUrl() {
                return moreUrl;
            }

            public void setMoreUrl(Object moreUrl) {
                this.moreUrl = moreUrl;
            }

            public PropertiesBean getProperties() {
                return properties;
            }

            public void setProperties(PropertiesBean properties) {
                this.properties = properties;
            }

            public List<SectionValuesBean> getSectionValues() {
                return sectionValues;
            }

            public void setSectionValues(List<SectionValuesBean> sectionValues) {
                this.sectionValues = sectionValues;
            }

            public static class PropertiesBean {
                /**
                 * top : 0
                 * left : 0
                 * bottom : 10
                 * width : 750
                 * right : 0
                 * height : 290
                 */

                private int top;
                private int left;
                private int bottom;
                private String width;
                private int right;
                private String height;

                public int getTop() {
                    return top;
                }

                public void setTop(int top) {
                    this.top = top;
                }

                public int getLeft() {
                    return left;
                }

                public void setLeft(int left) {
                    this.left = left;
                }

                public int getBottom() {
                    return bottom;
                }

                public void setBottom(int bottom) {
                    this.bottom = bottom;
                }

                public String getWidth() {
                    return width;
                }

                public void setWidth(String width) {
                    this.width = width;
                }

                public int getRight() {
                    return right;
                }

                public void setRight(int right) {
                    this.right = right;
                }

                public String getHeight() {
                    return height;
                }

                public void setHeight(String height) {
                    this.height = height;
                }
            }

            public static class SectionValuesBean {
                /**
                 * sequence : 0
                 * sectionDefId : 389
                 * shortTitle : 直播
                 * id : 8363
                 * frontPic : /2018/01/05/044113d3369c87de62c8dadbfc7e9da5f4072d.gif
                 * url : http://m.d2cmall.com/zegolive/watch/443?share=bWVtYmVySWQ9MjgyODIyMyZkZXZpY2U9aW9z
                 */

                private int sequence;
                private int sectionDefId;
                private String shortTitle;
                private int id;
                private String frontPic;
                private String url;

                public int getSequence() {
                    return sequence;
                }

                public void setSequence(int sequence) {
                    this.sequence = sequence;
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

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getFrontPic() {
                    return frontPic;
                }

                public void setFrontPic(String frontPic) {
                    this.frontPic = frontPic;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }
}
