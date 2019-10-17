package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2017/9/14.
 */

public class ScreenBean extends BaseBean {

    /**
     * data : {"filter":{"prroductType":["spot","custom","presell"],"isPromotion":true,"isAfter":false,"isSubscribe":false},"category":[{"topCatagory":{"id":4,"name":"生活家居","code":"0004","sequence":2,"status":null,"pic":null,"eName":null},"productCategory":[{"code":"80","children":[{"code":"803","children":[],"topId":4,"name":"代步车","id":1988,"pic":null,"parentId":1927},{"code":"802","children":[],"topId":4,"name":"环保袋","id":1980,"pic":null,"parentId":1927},{"code":"804","children":[],"topId":4,"name":"雨衣","id":2024,"pic":null,"parentId":1927},{"code":"801","children":[],"topId":4,"name":"口罩","id":1970,"pic":null,"parentId":1927}],"topId":4,"name":"出行","id":1927,"pic":null,"parentId":null},{"code":"222","children":[],"topId":4,"name":"枕头","id":1954,"pic":null,"parentId":null},{"code":"90","children":[],"topId":4,"name":"水","id":1935,"pic":null,"parentId":null},{"code":"300","children":[],"topId":4,"name":"生活用品","id":1827,"pic":null,"parentId":null},{"code":"20","children":[{"code":"210","children":[],"topId":4,"name":"首饰盒","id":1952,"pic":null,"parentId":1739},{"code":"209","children":[],"topId":4,"name":"指甲油","id":1926,"pic":null,"parentId":1739},{"code":"208","children":[],"topId":4,"name":"美容护肤/美体/精油","id":1849,"pic":null,"parentId":1739},{"code":"204","children":[],"topId":4,"name":"面膜","id":1774,"pic":null,"parentId":1739},{"code":"211","children":[],"topId":4,"name":"化妆袋","id":1996,"pic":null,"parentId":1739},{"code":"212","children":[],"topId":4,"name":"个人护理","id":2041,"pic":null,"parentId":1739},{"code":"201","children":[],"topId":4,"name":"香水","id":1740,"pic":null,"parentId":1739},{"code":"205","children":[],"topId":4,"name":"护肤品","id":1812,"pic":null,"parentId":1739},{"code":"203","children":[],"topId":4,"name":"口红","id":1742,"pic":null,"parentId":1739}],"topId":4,"name":"美妆","id":1739,"pic":null,"parentId":null},{}]}]}
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
         * filter : {"prroductType":["spot","custom","presell"],"isPromotion":true,"isAfter":false,"isSubscribe":false}
         * category : [{"topCatagory":{"id":4,"name":"生活家居","code":"0004","sequence":2,"status":null,"pic":null,"eName":null},"productCategory":[{"code":"80","children":[{"code":"803","children":[],"topId":4,"name":"代步车","id":1988,"pic":null,"parentId":1927},{"code":"802","children":[],"topId":4,"name":"环保袋","id":1980,"pic":null,"parentId":1927},{"code":"804","children":[],"topId":4,"name":"雨衣","id":2024,"pic":null,"parentId":1927},{"code":"801","children":[],"topId":4,"name":"口罩","id":1970,"pic":null,"parentId":1927}],"topId":4,"name":"出行","id":1927,"pic":null,"parentId":null},{"code":"222","children":[],"topId":4,"name":"枕头","id":1954,"pic":null,"parentId":null},{"code":"90","children":[],"topId":4,"name":"水","id":1935,"pic":null,"parentId":null},{"code":"300","children":[],"topId":4,"name":"生活用品","id":1827,"pic":null,"parentId":null},{"code":"20","children":[{"code":"210","children":[],"topId":4,"name":"首饰盒","id":1952,"pic":null,"parentId":1739},{"code":"209","children":[],"topId":4,"name":"指甲油","id":1926,"pic":null,"parentId":1739},{"code":"208","children":[],"topId":4,"name":"美容护肤/美体/精油","id":1849,"pic":null,"parentId":1739},{"code":"204","children":[],"topId":4,"name":"面膜","id":1774,"pic":null,"parentId":1739},{"code":"211","children":[],"topId":4,"name":"化妆袋","id":1996,"pic":null,"parentId":1739},{"code":"212","children":[],"topId":4,"name":"个人护理","id":2041,"pic":null,"parentId":1739},{"code":"201","children":[],"topId":4,"name":"香水","id":1740,"pic":null,"parentId":1739},{"code":"205","children":[],"topId":4,"name":"护肤品","id":1812,"pic":null,"parentId":1739},{"code":"203","children":[],"topId":4,"name":"口红","id":1742,"pic":null,"parentId":1739}],"topId":4,"name":"美妆","id":1739,"pic":null,"parentId":null},{}]}]
         */

        private FilterBean filter;
        private List<CategoryBean> category;

        public FilterBean getFilter() {
            return filter;
        }

        public void setFilter(FilterBean filter) {
            this.filter = filter;
        }

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public static class FilterBean {
            /**
             * prroductType : ["spot","custom","presell"]
             * isPromotion : true
             * isAfter : false
             * isSubscribe : false
             */

            private boolean presell;
            private boolean isPromotion;
            private boolean spot;
            private boolean custom;
            private boolean isAfter;
            private boolean isSubscribe;

            public boolean isPromotion() {
                return isPromotion;
            }

            public void setPromotion(boolean promotion) {
                isPromotion = promotion;
            }

            public boolean isAfter() {
                return isAfter;
            }

            public void setAfter(boolean after) {
                isAfter = after;
            }

            public boolean isSubscribe() {
                return isSubscribe;
            }

            public void setSubscribe(boolean subscribe) {
                isSubscribe = subscribe;
            }

            public boolean isSPOT() {
                return spot;
            }

            public void setSPOT(boolean SPOT) {
                this.spot = SPOT;
            }

            public boolean isPRESELL() {
                return presell;
            }

            public void setPRESELL(boolean PRESELL) {
                this.presell = PRESELL;
            }

            public boolean isCUSTOM() {
                return custom;
            }

            public void setCUSTOM(boolean CUSTOM) {
                this.custom = CUSTOM;
            }
        }

        public static class CategoryBean {
            /**
             * topCatagory : {"id":4,"name":"生活家居","code":"0004","sequence":2,"status":null,"pic":null,"eName":null}
             * productCategory : [{"code":"80","children":[{"code":"803","children":[],"topId":4,"name":"代步车","id":1988,"pic":null,"parentId":1927},{"code":"802","children":[],"topId":4,"name":"环保袋","id":1980,"pic":null,"parentId":1927},{"code":"804","children":[],"topId":4,"name":"雨衣","id":2024,"pic":null,"parentId":1927},{"code":"801","children":[],"topId":4,"name":"口罩","id":1970,"pic":null,"parentId":1927}],"topId":4,"name":"出行","id":1927,"pic":null,"parentId":null},{"code":"222","children":[],"topId":4,"name":"枕头","id":1954,"pic":null,"parentId":null},{"code":"90","children":[],"topId":4,"name":"水","id":1935,"pic":null,"parentId":null},{"code":"300","children":[],"topId":4,"name":"生活用品","id":1827,"pic":null,"parentId":null},{"code":"20","children":[{"code":"210","children":[],"topId":4,"name":"首饰盒","id":1952,"pic":null,"parentId":1739},{"code":"209","children":[],"topId":4,"name":"指甲油","id":1926,"pic":null,"parentId":1739},{"code":"208","children":[],"topId":4,"name":"美容护肤/美体/精油","id":1849,"pic":null,"parentId":1739},{"code":"204","children":[],"topId":4,"name":"面膜","id":1774,"pic":null,"parentId":1739},{"code":"211","children":[],"topId":4,"name":"化妆袋","id":1996,"pic":null,"parentId":1739},{"code":"212","children":[],"topId":4,"name":"个人护理","id":2041,"pic":null,"parentId":1739},{"code":"201","children":[],"topId":4,"name":"香水","id":1740,"pic":null,"parentId":1739},{"code":"205","children":[],"topId":4,"name":"护肤品","id":1812,"pic":null,"parentId":1739},{"code":"203","children":[],"topId":4,"name":"口红","id":1742,"pic":null,"parentId":1739}],"topId":4,"name":"美妆","id":1739,"pic":null,"parentId":null},{}]
             */

            private TopCatagoryBean topCatagory;
            private List<ProductCategoryBean> productCategory;

            public TopCatagoryBean getTopCatagory() {
                return topCatagory;
            }

            public void setTopCatagory(TopCatagoryBean topCatagory) {
                this.topCatagory = topCatagory;
            }

            public List<ProductCategoryBean> getProductCategory() {
                return productCategory;
            }

            public void setProductCategory(List<ProductCategoryBean> productCategory) {
                this.productCategory = productCategory;
            }

            public static class TopCatagoryBean {
                /**
                 * id : 4
                 * name : 生活家居
                 * code : 0004
                 * sequence : 2
                 * status : null
                 * pic : null
                 * eName : null
                 */

                private int id;
                private String name;
                private String code;
                private int sequence;
                private String status;
                private String pic;
                private String eName;

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

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public int getSequence() {
                    return sequence;
                }

                public void setSequence(int sequence) {
                    this.sequence = sequence;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public String getEName() {
                    return eName;
                }

                public void setEName(String eName) {
                    this.eName = eName;
                }
            }

            public static class ProductCategoryBean {
                /**
                 * code : 80
                 * children : [{"code":"803","children":[],"topId":4,"name":"代步车","id":1988,"pic":null,"parentId":1927},{"code":"802","children":[],"topId":4,"name":"环保袋","id":1980,"pic":null,"parentId":1927},{"code":"804","children":[],"topId":4,"name":"雨衣","id":2024,"pic":null,"parentId":1927},{"code":"801","children":[],"topId":4,"name":"口罩","id":1970,"pic":null,"parentId":1927}]
                 * topId : 4
                 * name : 出行
                 * id : 1927
                 * pic : null
                 * parentId : null
                 */

                private String code;
                private int topId;
                private String name;
                private int id;
                private String pic;
                private long parentId;
                private List<ChildrenBean> children;

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public int getTopId() {
                    return topId;
                }

                public void setTopId(int topId) {
                    this.topId = topId;
                }

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

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public long getParentId() {
                    return parentId;
                }

                public void setParentId(long parentId) {
                    this.parentId = parentId;
                }

                public List<ChildrenBean> getChildren() {
                    return children;
                }

                public void setChildren(List<ChildrenBean> children) {
                    this.children = children;
                }

                public static class ChildrenBean {
                    /**
                     * code : 803
                     * children : []
                     * topId : 4
                     * name : 代步车
                     * id : 1988
                     * pic : null
                     * parentId : 1927
                     */

                    private String code;
                    private int topId;
                    private String name;
                    private int id;
                    private String pic;
                    private int parentId;
                    private List<ChildrenBean2> children;

                    public String getCode() {
                        return code;
                    }

                    public void setCode(String code) {
                        this.code = code;
                    }

                    public int getTopId() {
                        return topId;
                    }

                    public void setTopId(int topId) {
                        this.topId = topId;
                    }

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

                    public String getPic() {
                        return pic;
                    }

                    public void setPic(String pic) {
                        this.pic = pic;
                    }

                    public int getParentId() {
                        return parentId;
                    }

                    public void setParentId(int parentId) {
                        this.parentId = parentId;
                    }

                    public List<ChildrenBean2> getChildren() {
                        return children;
                    }

                    public void setChildren(List<ChildrenBean2> children) {
                        this.children = children;
                    }

                    public static class ChildrenBean2{
                        private String code;
                        private int topId;
                        private String name;
                        private int id;
                        private String pic;
                        private int parentId;
                        private List<String> children;

                        public String getCode() {
                            return code;
                        }

                        public void setCode(String code) {
                            this.code = code;
                        }

                        public int getTopId() {
                            return topId;
                        }

                        public void setTopId(int topId) {
                            this.topId = topId;
                        }

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

                        public String getPic() {
                            return pic;
                        }

                        public void setPic(String pic) {
                            this.pic = pic;
                        }

                        public int getParentId() {
                            return parentId;
                        }

                        public void setParentId(int parentId) {
                            this.parentId = parentId;
                        }

                        public List<String> getChildren() {
                            return children;
                        }

                        public void setChildren(List<String> children) {
                            this.children = children;
                        }

                    }
                }
            }
        }
    }
}
