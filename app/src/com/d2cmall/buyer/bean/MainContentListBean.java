package com.d2cmall.buyer.bean;


import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Created by rookie on 2017/8/24.
 */

public class MainContentListBean {

    /**
     * next : false
     * total : 11
     * previous : false
     * subModule : {"parent":"MAIN","sequence":999999,"isDefault":1,"isCategory":0,"color":"000000","webUrl":"","id":109,"title":"推荐","categoryIds":null,"direction":"TOP"}
     * index : 1
     * pageSize : 40
     * version : 109
     * content : [{"sequence":10,"sectionValues":[],"visible":1,"id":1,"moduleId":109,"title":"轮播图","type":1,"properties":{"top":"6","left":"6","bottom":"6","width":"750","right":"6","height":"300"}},{"sequence":9,"sectionValues":[{"sequence":12,"sectionDefId":463,"shortTitle":"客服","id":2713,"frontPic":"/2017/07/18/075151fc407564636443fd1641ef4274e76361.gif","url":"/product/164271"},{"sequence":10,"sectionDefId":463,"shortTitle":"实体店","id":2712,"frontPic":"/2017/07/18/075158fc407564636443fd1641ef4274e76361.gif","url":"/page/store"},{"sequence":0,"sectionDefId":463,"shortTitle":"222","id":2734,"frontPic":"/2017/07/14/035208fc407564636443fd1641ef4274e76361.gif","url":"/auction/product/84"}],"visible":1,"id":1,"moduleId":109,"title":"快速入口","type":4,"properties":{"top":0,"left":"10","bottom":"0","num":4,"right":"10"}},{"relationType":null,"visible":0,"more":0,"relationId":null,"title":"单一块","type":5,"content":null,"sequence":8,"sectionValues":[{"originalPrice":null,"shortTitle":"testgif","list":[],"frontPic":"/2017/07/11/09474835d70d65fbaec1bee455186d3111b494.gif","url":"/productComb/103493","videoPic":null,"videoPath":null,"sequence":12,"price":null,"sectionDefId":464,"endPic":null,"id":1,"brand":null,"longTitle":"Where What Who 互然设计师品牌 由张峰Bono和唐丹妮Danni创立于2009年， 品牌以六七十年代摇滚精神为灵魂， 设计中大量融合了复古与现代元素更擅将各种文化天马行空的重置再生，以轻灵直白的设计语言，乐观玩味的态度，逐渐确立了简约多变，实用自我的品牌风格"}],"watched":0,"id":1,"moduleId":109,"moreUrl":null,"properties":{"top":0,"left":0,"bottom":10,"width":"750","right":0,"height":"410"}},{"relationType":null,"visible":0,"more":0,"relationId":null,"title":"单一块","type":6,"content":null,"sequence":8,"sectionValues":[{"originalPrice":null,"shortTitle":"hello world","list":[],"frontPic":"/2017/07/11/0925532af61c33f5e8651152b33cac55c09690.gif","url":"/page/MMP4","videoPic":null,"videoPath":null,"sequence":11,"price":null,"sectionDefId":464,"endPic":null,"id":1,"brand":null,"longTitle":null}],"watched":0,"id":1,"moduleId":109,"moreUrl":null,"properties":{"top":0,"left":0,"bottom":10,"width":"750","right":0,"height":"410"}},{},{},{},{},{},{},{"sequence":0,"sectionValues":[{"sequence":1,"sectionDefId":465,"shortTitle":"2","id":2736,"frontPic":"/2017/07/14/035304ff0239a5d06b6e4b48d83c80e9ab4a5f.gif","url":"/page/store"}],"visible":0,"id":1,"moduleId":109,"title":"排列","type":3,"properties":{"top":0,"left":0,"bottom":10,"num":3,"width":"750","right":0,"height":"100"}}]
     */

    private boolean next;
    private int total;
    private boolean previous;
    private SubModuleBean subModule;
    private int index;
    private int pageSize;
    private int version;
    private List<ContentBean> content;

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isPrevious() {
        return previous;
    }

    public void setPrevious(boolean previous) {
        this.previous = previous;
    }

    public SubModuleBean getSubModule() {
        return subModule;
    }

    public void setSubModule(SubModuleBean subModule) {
        this.subModule = subModule;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class SubModuleBean {
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
        private Object categoryId;
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

        public Object getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Object categoryId) {
            this.categoryId = categoryId;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }
    }

    public static class ContentBean {
        /**
         * sequence : 10
         * sectionValues : []
         * visible : 1
         * id : 1
         * moduleId : 109
         * title : 轮播图
         * type : 1
         * properties : {"top":"6","left":"6","bottom":"6","width":"750","right":"6","height":"300"}
         * relationType : null
         * more : 0
         * relationId : null
         * content : null
         * watched : 0
         * moreUrl : null
         */

        private int sequence;
        private int visible;
        private int id;
        private int moduleId;
        private String title;
        private int type;
        private PropertiesBean properties;
        private String relationType;
        private int more;
        private String relationId;
        private String content;
        private int watched;
        private String moreUrl;
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

        public PropertiesBean getProperties() {
            return properties;
        }

        public void setProperties(PropertiesBean properties) {
            this.properties = properties;
        }

        public String getRelationType() {
            return relationType;
        }

        public void setRelationType(String relationType) {
            this.relationType = relationType;
        }

        public int getMore() {
            return more;
        }

        public void setMore(int more) {
            this.more = more;
        }

        public String getRelationId() {
            return relationId;
        }

        public void setRelationId(String relationId) {
            this.relationId = relationId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getWatched() {
            return watched;
        }

        public void setWatched(int watched) {
            this.watched = watched;
        }

        public String getMoreUrl() {
            return moreUrl;
        }

        public void setMoreUrl(String moreUrl) {
            this.moreUrl = moreUrl;
        }

        public List<SectionValuesBean> getSectionValues() {
            return sectionValues;
        }

        public void setSectionValues(List<SectionValuesBean> sectionValues) {
            this.sectionValues = sectionValues;
        }

        public static class SectionValuesBean{
            private int sectionDefId;
            private String shortTitle;
            private String url;
            private String frontPic;
            private int sequence;
            private String longTitle;
            private String price;
            private String originalPrice;
            private List<SectionValuesListBean> list;

            public static class SectionValuesListBean implements Identifiable{
                private long id;
                private String shortTitle;
                private String frontPic;
                private int sequence;
                private String properties;
                private String url;

                @Override
                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public String getShortTitle() {
                    return shortTitle;
                }

                public void setShortTitle(String shortTitle) {
                    this.shortTitle = shortTitle;
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

                public String getProperties() {
                    return properties;
                }

                public void setProperties(String properties) {
                    this.properties = properties;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
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

            public String getLongTitle() {
                return longTitle;
            }

            public void setLongTitle(String longTitle) {
                this.longTitle = longTitle;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getOriginalPrice() {
                return originalPrice;
            }

            public void setOriginalPrice(String originalPrice) {
                this.originalPrice = originalPrice;
            }

            public List<SectionValuesListBean> getList() {
                return list;
            }

            public void setList(List<SectionValuesListBean> list) {
                this.list = list;
            }
        }

        public static class PropertiesBean {
            /**
             * top : 6
             * left : 6
             * bottom : 6
             * width : 750
             * right : 6
             * height : 300
             */

            private String top;
            private String left;
            private String bottom;
            private String width;
            private String right;
            private String height;
            private String num;

            public int getTop() {
                if (Util.isEmpty(top)){
                    return 0;
                }else {
                    return Integer.valueOf(top);
                }
            }

            public void setTop(String top) {
                this.top = top;
            }

            public int getLeft() {
                if (Util.isEmpty(left)){
                    return 0;
                }else {
                    return Integer.valueOf(left);
                }
            }

            public void setLeft(String left) {
                this.left = left;
            }

            public int getBottom() {
                if (Util.isEmpty(bottom)){
                    return 0;
                }else {
                    return Integer.valueOf(bottom);
                }
            }

            public void setBottom(String bottom) {
                this.bottom = bottom;
            }

            public int getWidth() {
                if (Util.isEmpty(width)){
                    return 0;
                }else {
                    return Integer.valueOf(width);
                }
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public int getRight() {
                if (Util.isEmpty(right)){
                    return 0;
                }else {
                    return Integer.valueOf(right);
                }
            }

            public void setRight(String right) {
                this.right = right;
            }

            public int getHeight() {
                if (Util.isEmpty(height)){
                    return 0;
                }else {
                    return Integer.valueOf(height);
                }
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public int getNum() {
                if (Util.isEmpty(num)){
                    return 0;
                }else {
                    return Integer.valueOf(num);
                }
            }

            public void setNum(String num) {
                this.num = num;
            }
        }
    }
}
