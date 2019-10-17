package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2018/3/26.
 */

public class ScreenNewBean extends BaseBean {

    /**
     * data : {"filter":{"categorys":[{"sourceId":1616,"type":"typeproductcategory","name":"牛仔裤","parentId":"1"},{"sourceId":1596,"type":"typeproductcategory","name":"马甲","parentId":"1"},{"sourceId":1591,"type":"typeproductcategory","name":"T恤","parentId":"1"},{"sourceId":2082,"type":"typeproductcategory","name":"帽子","parentId":"9"},{"sourceId":2063,"type":"typeproductcategory","name":"长裤","parentId":"1"},{"sourceId":1969,"type":"typeproductcategory","name":"外套","parentId":"1"},{"sourceId":1617,"type":"typeproductcategory","name":"休闲裤","parentId":"1"},{"sourceId":1605,"type":"typeproductcategory","name":"衬衫","parentId":"1"},{"sourceId":1782,"type":"typeproductcategory","name":"上衣","parentId":"1"},{"sourceId":1720,"type":"typeproductcategory","name":"羽绒服","parentId":"6"},{"sourceId":1698,"type":"typeproductcategory","name":"休闲西装","parentId":"1"},{"sourceId":1601,"type":"typeproductcategory","name":"上衣","parentId":"1"},{"sourceId":2084,"type":"typeproductcategory","name":"袜子","parentId":"9"},{"sourceId":1602,"type":"typeproductcategory","name":"针织衫","parentId":"1"},{"sourceId":1619,"type":"typeproductcategory","name":"短裤","parentId":"1"},{"sourceId":1690,"type":"typeproductcategory","name":"连体裤","parentId":"1"},{"sourceId":1688,"type":"typeproductcategory","name":"卫衣","parentId":"1"},{"sourceId":1695,"type":"typeproductcategory","name":"大衣","parentId":"1"},{"sourceId":1588,"type":"typeproductcategory","name":"连衣裙","parentId":"1"},{"sourceId":1684,"type":"typeproductcategory","name":"半身裙","parentId":"1"},{"sourceId":1622,"type":"typeproductcategory","name":"9分裤","parentId":"1"},{"sourceId":1595,"type":"typeproductcategory","name":"背心","parentId":"1"},{"sourceId":1610,"type":"typeproductcategory","name":"长款大衣","parentId":"1"},{"sourceId":1590,"type":"typeproductcategory","name":"针织衫","parentId":"1"},{"sourceId":1608,"type":"typeproductcategory","name":"毛衣","parentId":"1"},{"sourceId":1696,"type":"typeproductcategory","name":"羽绒服","parentId":"1"}],"designers":[{"sourceId":10918,"type":"typedesigner","name":"D2C","parentId":null},{"sourceId":10925,"type":"typedesigner","name":"DevilBeauty","parentId":null},{"sourceId":11190,"type":"typedesigner","name":"RECIEW_LONDON","parentId":null},{"sourceId":10952,"type":"typedesigner","name":"ALeaLea","parentId":null},{"sourceId":11265,"type":"typedesigner","name":"Angel.wow","parentId":null},{"sourceId":10053,"type":"typedesigner","name":"ZHANGSHUAI","parentId":null},{"sourceId":10352,"type":"typedesigner","name":"JE.LES","parentId":null},{"sourceId":10718,"type":"typedesigner","name":"FORELE","parentId":null},{"sourceId":10144,"type":"typedesigner","name":"COMME MOI","parentId":null},{"sourceId":10513,"type":"typedesigner","name":"YOUG X","parentId":null}],"service":{"presell":true,"isPromotion":true,"spot":true,"custom":true,"isAfter":true,"isSubscribe":true},"tops":[{"sourceId":1,"type":"typetopcategory","name":"女装","parentId":null},{"sourceId":6,"type":"typetopcategory","name":"男装","parentId":null},{"sourceId":3,"type":"typetopcategory","name":"首饰","parentId":null},{"sourceId":9,"type":"typetopcategory","name":"配饰","parentId":null}]}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * filter : {"categorys":[{"sourceId":1616,"type":"typeproductcategory","name":"牛仔裤","parentId":"1"},{"sourceId":1596,"type":"typeproductcategory","name":"马甲","parentId":"1"},{"sourceId":1591,"type":"typeproductcategory","name":"T恤","parentId":"1"},{"sourceId":2082,"type":"typeproductcategory","name":"帽子","parentId":"9"},{"sourceId":2063,"type":"typeproductcategory","name":"长裤","parentId":"1"},{"sourceId":1969,"type":"typeproductcategory","name":"外套","parentId":"1"},{"sourceId":1617,"type":"typeproductcategory","name":"休闲裤","parentId":"1"},{"sourceId":1605,"type":"typeproductcategory","name":"衬衫","parentId":"1"},{"sourceId":1782,"type":"typeproductcategory","name":"上衣","parentId":"1"},{"sourceId":1720,"type":"typeproductcategory","name":"羽绒服","parentId":"6"},{"sourceId":1698,"type":"typeproductcategory","name":"休闲西装","parentId":"1"},{"sourceId":1601,"type":"typeproductcategory","name":"上衣","parentId":"1"},{"sourceId":2084,"type":"typeproductcategory","name":"袜子","parentId":"9"},{"sourceId":1602,"type":"typeproductcategory","name":"针织衫","parentId":"1"},{"sourceId":1619,"type":"typeproductcategory","name":"短裤","parentId":"1"},{"sourceId":1690,"type":"typeproductcategory","name":"连体裤","parentId":"1"},{"sourceId":1688,"type":"typeproductcategory","name":"卫衣","parentId":"1"},{"sourceId":1695,"type":"typeproductcategory","name":"大衣","parentId":"1"},{"sourceId":1588,"type":"typeproductcategory","name":"连衣裙","parentId":"1"},{"sourceId":1684,"type":"typeproductcategory","name":"半身裙","parentId":"1"},{"sourceId":1622,"type":"typeproductcategory","name":"9分裤","parentId":"1"},{"sourceId":1595,"type":"typeproductcategory","name":"背心","parentId":"1"},{"sourceId":1610,"type":"typeproductcategory","name":"长款大衣","parentId":"1"},{"sourceId":1590,"type":"typeproductcategory","name":"针织衫","parentId":"1"},{"sourceId":1608,"type":"typeproductcategory","name":"毛衣","parentId":"1"},{"sourceId":1696,"type":"typeproductcategory","name":"羽绒服","parentId":"1"}],"designers":[{"sourceId":10918,"type":"typedesigner","name":"D2C","parentId":null},{"sourceId":10925,"type":"typedesigner","name":"DevilBeauty","parentId":null},{"sourceId":11190,"type":"typedesigner","name":"RECIEW_LONDON","parentId":null},{"sourceId":10952,"type":"typedesigner","name":"ALeaLea","parentId":null},{"sourceId":11265,"type":"typedesigner","name":"Angel.wow","parentId":null},{"sourceId":10053,"type":"typedesigner","name":"ZHANGSHUAI","parentId":null},{"sourceId":10352,"type":"typedesigner","name":"JE.LES","parentId":null},{"sourceId":10718,"type":"typedesigner","name":"FORELE","parentId":null},{"sourceId":10144,"type":"typedesigner","name":"COMME MOI","parentId":null},{"sourceId":10513,"type":"typedesigner","name":"YOUG X","parentId":null}],"service":{"presell":true,"isPromotion":true,"spot":true,"custom":true,"isAfter":true,"isSubscribe":true},"tops":[{"sourceId":1,"type":"typetopcategory","name":"女装","parentId":null},{"sourceId":6,"type":"typetopcategory","name":"男装","parentId":null},{"sourceId":3,"type":"typetopcategory","name":"首饰","parentId":null},{"sourceId":9,"type":"typetopcategory","name":"配饰","parentId":null}]}
         */

        private FilterBean filter;

        public FilterBean getFilter() {
            return filter;
        }

        public void setFilter(FilterBean filter) {
            this.filter = filter;
        }

        public static class FilterBean {
            /**
             * categorys : [{"sourceId":1616,"type":"typeproductcategory","name":"牛仔裤","parentId":"1"},{"sourceId":1596,"type":"typeproductcategory","name":"马甲","parentId":"1"},{"sourceId":1591,"type":"typeproductcategory","name":"T恤","parentId":"1"},{"sourceId":2082,"type":"typeproductcategory","name":"帽子","parentId":"9"},{"sourceId":2063,"type":"typeproductcategory","name":"长裤","parentId":"1"},{"sourceId":1969,"type":"typeproductcategory","name":"外套","parentId":"1"},{"sourceId":1617,"type":"typeproductcategory","name":"休闲裤","parentId":"1"},{"sourceId":1605,"type":"typeproductcategory","name":"衬衫","parentId":"1"},{"sourceId":1782,"type":"typeproductcategory","name":"上衣","parentId":"1"},{"sourceId":1720,"type":"typeproductcategory","name":"羽绒服","parentId":"6"},{"sourceId":1698,"type":"typeproductcategory","name":"休闲西装","parentId":"1"},{"sourceId":1601,"type":"typeproductcategory","name":"上衣","parentId":"1"},{"sourceId":2084,"type":"typeproductcategory","name":"袜子","parentId":"9"},{"sourceId":1602,"type":"typeproductcategory","name":"针织衫","parentId":"1"},{"sourceId":1619,"type":"typeproductcategory","name":"短裤","parentId":"1"},{"sourceId":1690,"type":"typeproductcategory","name":"连体裤","parentId":"1"},{"sourceId":1688,"type":"typeproductcategory","name":"卫衣","parentId":"1"},{"sourceId":1695,"type":"typeproductcategory","name":"大衣","parentId":"1"},{"sourceId":1588,"type":"typeproductcategory","name":"连衣裙","parentId":"1"},{"sourceId":1684,"type":"typeproductcategory","name":"半身裙","parentId":"1"},{"sourceId":1622,"type":"typeproductcategory","name":"9分裤","parentId":"1"},{"sourceId":1595,"type":"typeproductcategory","name":"背心","parentId":"1"},{"sourceId":1610,"type":"typeproductcategory","name":"长款大衣","parentId":"1"},{"sourceId":1590,"type":"typeproductcategory","name":"针织衫","parentId":"1"},{"sourceId":1608,"type":"typeproductcategory","name":"毛衣","parentId":"1"},{"sourceId":1696,"type":"typeproductcategory","name":"羽绒服","parentId":"1"}]
             * designers : [{"sourceId":10918,"type":"typedesigner","name":"D2C","parentId":null},{"sourceId":10925,"type":"typedesigner","name":"DevilBeauty","parentId":null},{"sourceId":11190,"type":"typedesigner","name":"RECIEW_LONDON","parentId":null},{"sourceId":10952,"type":"typedesigner","name":"ALeaLea","parentId":null},{"sourceId":11265,"type":"typedesigner","name":"Angel.wow","parentId":null},{"sourceId":10053,"type":"typedesigner","name":"ZHANGSHUAI","parentId":null},{"sourceId":10352,"type":"typedesigner","name":"JE.LES","parentId":null},{"sourceId":10718,"type":"typedesigner","name":"FORELE","parentId":null},{"sourceId":10144,"type":"typedesigner","name":"COMME MOI","parentId":null},{"sourceId":10513,"type":"typedesigner","name":"YOUG X","parentId":null}]
             * service : {"presell":true,"isPromotion":true,"spot":true,"custom":true,"isAfter":true,"isSubscribe":true}
             * tops : [{"sourceId":1,"type":"typetopcategory","name":"女装","parentId":null},{"sourceId":6,"type":"typetopcategory","name":"男装","parentId":null},{"sourceId":3,"type":"typetopcategory","name":"首饰","parentId":null},{"sourceId":9,"type":"typetopcategory","name":"配饰","parentId":null}]
             */

            private ServiceBean service;
            private List<CategorysBean> categorys;
            private List<DesignersBean> designers;
            private List<TopsBean> tops;

            public ServiceBean getService() {
                return service;
            }

            public void setService(ServiceBean service) {
                this.service = service;
            }

            public List<CategorysBean> getCategorys() {
                return categorys;
            }

            public void setCategorys(List<CategorysBean> categorys) {
                this.categorys = categorys;
            }

            public List<DesignersBean> getDesigners() {
                return designers;
            }

            public void setDesigners(List<DesignersBean> designers) {
                this.designers = designers;
            }

            public List<TopsBean> getTops() {
                return tops;
            }

            public void setTops(List<TopsBean> tops) {
                this.tops = tops;
            }

            public static class ServiceBean {
                /**
                 * presell : true
                 * isPromotion : true
                 * spot : true
                 * custom : true
                 * isAfter : true
                 * isSubscribe : true
                 */

                private boolean presell;
                private boolean isPromotion;
                private boolean spot;
                private boolean custom;
                private boolean isAfter;
                private boolean isSubscribe;

                public boolean isPresell() {
                    return presell;
                }

                public void setPresell(boolean presell) {
                    this.presell = presell;
                }

                public boolean isIsPromotion() {
                    return isPromotion;
                }

                public void setIsPromotion(boolean isPromotion) {
                    this.isPromotion = isPromotion;
                }

                public boolean isSpot() {
                    return spot;
                }

                public void setSpot(boolean spot) {
                    this.spot = spot;
                }

                public boolean isCustom() {
                    return custom;
                }

                public void setCustom(boolean custom) {
                    this.custom = custom;
                }

                public boolean isIsAfter() {
                    return isAfter;
                }

                public void setIsAfter(boolean isAfter) {
                    this.isAfter = isAfter;
                }

                public boolean isIsSubscribe() {
                    return isSubscribe;
                }

                public void setIsSubscribe(boolean isSubscribe) {
                    this.isSubscribe = isSubscribe;
                }
            }

            public static class CategorysBean {
                /**
                 * sourceId : 1616
                 * type : typeproductcategory
                 * name : 牛仔裤
                 * parentId : 1
                 */

                private int sourceId;
                private String type;
                private String name;
                private String parentId;
                private boolean isSelected;

                public boolean isSelected() {
                    return isSelected;
                }

                public void setSelected(boolean selected) {
                    isSelected = selected;
                }

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

            public static class DesignersBean {
                /**
                 * sourceId : 10918
                 * type : typedesigner
                 * name : D2C
                 * parentId : null
                 */

                private int sourceId;
                private String type;
                private String name;
                private String parentId;
                private boolean isSelected;

                public boolean isSelected() {
                    return isSelected;
                }

                public void setSelected(boolean selected) {
                    isSelected = selected;
                }

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

            public static class TopsBean {
                /**
                 * sourceId : 1
                 * type : typetopcategory
                 * name : 女装
                 * parentId : null
                 */

                private int sourceId;
                private String type;
                private String name;
                private String parentId;
                private boolean isSelected;
                private List<ScreenNewBean.DataBean.FilterBean.CategorysBean> list;

                public List<CategorysBean> getList() {
                    return list;
                }

                public void setList(List<CategorysBean> list) {
                    this.list = list;
                }

                public boolean isSelected() {
                    return isSelected;
                }

                public void setSelected(boolean selected) {
                    isSelected = selected;
                }

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
}
