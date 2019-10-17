package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/13 19:42
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class TopRecommendBean extends BaseBean {

    /**
     * message :
     * datas : {}
     * list : [{"designerId":11071,"img":"/2017/08/01/0840291fd6c2c8f9d472f3c81e5d0b9d7cb564.jpg","comments":0,"originalPrice":1669,"isSpot":false,"promotionPrice":1669,"store":1,"designer":"Huge Lee","isCart":1,"categoryName":"高跟鞋","colors":[{"img":"/2017/08/01/0840251fd6c2c8f9d472f3c81e5d0b9d7cb564.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"34"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"35"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"36"},{"img":"","code":"0","groupId":0,"name":"尺码","id":4,"value":"37"},{"img":"","code":"0","groupId":0,"name":"尺码","id":5,"value":"38"},{"img":"","code":"0","groupId":0,"name":"尺码","id":6,"value":"39"}],"price":1669,"minPrice":1669,"name":"Lucia Tacci 黑色波点交叉带8.5cm高跟凉鞋","recomScore":11000,"collectioned":null,"isTopical":false,"productSellType":"CUSTOM","id":167415,"mark":1,"categoryIds":1672},{"designerId":10593,"img":"/2017/07/15/074228c93fd32b784dee9df0ca0733d77d6372.jpg","comments":0,"originalPrice":256,"isSpot":false,"promotionPrice":256,"store":1,"designer":"胡天行/胡天戈","isCart":1,"categoryName":"戒指","colors":[{"img":"/2017/07/15/074226c93fd32b784dee9df0ca0733d77d6372.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"银色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"F"}],"price":256,"minPrice":256,"name":"CENA HOO 罗马复刻戒指","recomScore":9300,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":166009,"mark":1,"categoryIds":1728},{"designerId":10691,"img":"/2017/07/19/061555ee4a3d50ce252baf226e5f106fe140f4.jpg","comments":0,"originalPrice":998,"isSpot":false,"promotionPrice":998,"store":1,"designer":"JUJU LI","isCart":1,"categoryName":"家居服","colors":[{"img":"/2017/07/19/061552ee4a3d50ce252baf226e5f106fe140f4.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"婴儿蓝"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"XS"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"S"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"M"},{"img":"","code":"0","groupId":0,"name":"尺码","id":4,"value":"L"}],"price":998,"minPrice":998,"name":"Not Just PajamaJuju Li婴儿蓝真丝睡衣套装（上衣+长裤+短裤）","recomScore":9750,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":166195,"mark":1,"categoryIds":1940},{"designerId":10310,"img":"/2017/08/22/0527120f9aaa58cdb76b765024d596398ea428.jpg","comments":0,"originalPrice":998,"isSpot":false,"promotionPrice":998,"store":1,"designer":"春子","isCart":1,"categoryName":"斜挎包","colors":[{"img":"/2017/08/22/0527100f9aaa58cdb76b765024d596398ea428.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"天蓝色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"W18*H17*D5cm"}],"price":998,"minPrice":998,"name":"[ 独本 ] 小恶魔 mini号斜挎包","recomScore":13100,"collectioned":null,"isTopical":true,"productSellType":"SPOT","id":169713,"mark":1,"categoryIds":1986},{"designerId":11061,"img":"/2017/08/24/05460125bb28d7a9ecb95e16da974dbfb696c9.jpg","comments":0,"originalPrice":529,"isSpot":false,"promotionPrice":219,"store":1,"designer":"船长","isCart":1,"categoryName":"卫衣","colors":[{"img":"/2017/08/24/0545559d8a2f809e6e94294e7e2563a5f0130e.jpg","code":"020","groupId":0,"name":"颜色","id":90,"value":"黑色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"},{"img":"","code":"04","groupId":0,"name":"尺码","id":29,"value":"XL码"},{"img":"","code":"05","groupId":0,"name":"尺码","id":30,"value":"XXL码"}],"price":219,"minPrice":219,"name":"HEYMAN 何七 原创潮牌鸡年创意图案印花卫衣","recomScore":13300,"collectioned":null,"isTopical":false,"productSellType":"SPOT","id":169932,"mark":1,"categoryIds":1703}]
     * total : 5
     * index : 1
     * pageCount : 1
     * pageSize : 5
     * next : false
     */

    private String message;
    private DatasBean datas;
    private int total;
    private int index;
    private int pageCount;
    private int pageSize;
    private boolean next;
    private List<ListBean> list;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class DatasBean {
    }

    public static class ListBean {
        /**
         * designerId : 11071
         * img : /2017/08/01/0840291fd6c2c8f9d472f3c81e5d0b9d7cb564.jpg
         * comments : 0
         * originalPrice : 1669.0
         * isSpot : false
         * promotionPrice : 1669.0
         * store : 1
         * designer : Huge Lee
         * isCart : 1
         * categoryName : 高跟鞋
         * colors : [{"img":"/2017/08/01/0840251fd6c2c8f9d472f3c81e5d0b9d7cb564.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"}]
         * consults : 0
         * isCrowd : false
         * sizes : [{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"34"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"35"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"36"},{"img":"","code":"0","groupId":0,"name":"尺码","id":4,"value":"37"},{"img":"","code":"0","groupId":0,"name":"尺码","id":5,"value":"38"},{"img":"","code":"0","groupId":0,"name":"尺码","id":6,"value":"39"}]
         * price : 1669.0
         * minPrice : 1669.0
         * name : Lucia Tacci 黑色波点交叉带8.5cm高跟凉鞋
         * recomScore : 11000.0
         * collectioned : null
         * isTopical : false
         * productSellType : CUSTOM
         * id : 167415
         * mark : 1
         * categoryIds : 1672
         */

        private int designerId;
        private String img;
        private int comments;
        private double originalPrice;
        private boolean isSpot;
        private double promotionPrice;
        private String orderPromotionType;
        private long orderPromotionId;
        private String orderPromotionTypeName;
        private  String promotionTypeName;
        public Integer getFlashPromotionId() {
            return flashPromotionId;
        }

        public void setFlashPromotionId(Integer flashPromotionId) {
            this.flashPromotionId = flashPromotionId;
        }

        private Integer flashPromotionId;
        public String getPromotionTypeName() {
            return promotionTypeName;
        }

        public void setPromotionTypeName(String promotionTypeName) {
            this.promotionTypeName = promotionTypeName;
        }

        public String getOrderPromotionType() {
            return orderPromotionType;
        }

        public void setOrderPromotionType(String orderPromotionType) {
            this.orderPromotionType = orderPromotionType;
        }

        public long getOrderPromotionId() {
            return orderPromotionId;
        }

        public void setOrderPromotionId(long orderPromotionId) {
            this.orderPromotionId = orderPromotionId;
        }

        public String getOrderPromotionTypeName() {
            return orderPromotionTypeName;
        }

        public void setOrderPromotionTypeName(String orderPromotionTypeName) {
            this.orderPromotionTypeName = orderPromotionTypeName;
        }
        public double getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(double salePrice) {
            this.salePrice = salePrice;
        }

        private double salePrice;

        public int getPromotionId() {
            return promotionId;
        }

        public void setPromotionId(int promotionId) {
            this.promotionId = promotionId;
        }

        private int promotionId;
        private int store;
        private String designer;
        private int isCart;
        private String categoryName;
        private int consults;
        private boolean isCrowd;
        private double price;
        private double minPrice;
        private String name;
        private double recomScore;
        private Object collectioned;
        private boolean isTopical;
        private String productSellType;
        private int id;
        private int mark;
        private int categoryId;
        private List<ColorsBean> colors;
        private List<SizesBean> sizes;


        public int getDesignerId() {
            return designerId;
        }

        public void setDesignerId(int designerId) {
            this.designerId = designerId;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public double getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(double originalPrice) {
            this.originalPrice = originalPrice;
        }

        public boolean isIsSpot() {
            return isSpot;
        }

        public void setIsSpot(boolean isSpot) {
            this.isSpot = isSpot;
        }

        public double getPromotionPrice() {
            return promotionPrice;
        }

        public void setPromotionPrice(double promotionPrice) {
            this.promotionPrice = promotionPrice;
        }

        public int getStore() {
            return store;
        }

        public void setStore(int store) {
            this.store = store;
        }

        public String getDesigner() {
            return designer;
        }

        public void setDesigner(String designer) {
            this.designer = designer;
        }

        public int getIsCart() {
            return isCart;
        }

        public void setIsCart(int isCart) {
            this.isCart = isCart;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public int getConsults() {
            return consults;
        }

        public void setConsults(int consults) {
            this.consults = consults;
        }

        public boolean isIsCrowd() {
            return isCrowd;
        }

        public void setIsCrowd(boolean isCrowd) {
            this.isCrowd = isCrowd;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public double getMinPrice() {
            return minPrice;
        }

        public void setMinPrice(double minPrice) {
            this.minPrice = minPrice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getRecomScore() {
            return recomScore;
        }

        public void setRecomScore(double recomScore) {
            this.recomScore = recomScore;
        }

        public Object getCollectioned() {
            return collectioned;
        }

        public void setCollectioned(Object collectioned) {
            this.collectioned = collectioned;
        }

        public boolean isIsTopical() {
            return isTopical;
        }

        public void setIsTopical(boolean isTopical) {
            this.isTopical = isTopical;
        }

        public String getProductSellType() {
            return productSellType;
        }

        public void setProductSellType(String productSellType) {
            this.productSellType = productSellType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMark() {
            return mark;
        }

        public void setMark(int mark) {
            this.mark = mark;
        }

        public int getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(int categoryId) {
            this.categoryId = categoryId;
        }

        public List<ColorsBean> getColors() {
            return colors;
        }

        public void setColors(List<ColorsBean> colors) {
            this.colors = colors;
        }

        public List<SizesBean> getSizes() {
            return sizes;
        }

        public void setSizes(List<SizesBean> sizes) {
            this.sizes = sizes;
        }

        public static class ColorsBean {
            /**
             * img : /2017/08/01/0840251fd6c2c8f9d472f3c81e5d0b9d7cb564.jpg
             * code : 0
             * groupId : 0
             * name : 颜色
             * id : 1
             * value : 黑色
             */

            private String img;
            private String code;
            private int groupId;
            private String name;
            private int id;
            private String value;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getGroupId() {
                return groupId;
            }

            public void setGroupId(int groupId) {
                this.groupId = groupId;
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

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class SizesBean {
            /**
             * img :
             * code : 0
             * groupId : 0
             * name : 尺码
             * id : 1
             * value : 34
             */

            private String img;
            private String code;
            private int groupId;
            private String name;
            private int id;
            private String value;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getGroupId() {
                return groupId;
            }

            public void setGroupId(int groupId) {
                this.groupId = groupId;
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

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
