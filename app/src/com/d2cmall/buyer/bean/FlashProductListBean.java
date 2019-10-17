package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/12/12 17:13
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FlashProductListBean extends BaseBean {

    /**
     * data : {"flashPromotion":{"promotionType":"0","endDate":"2017/12/29 00:00:00","session":"10:00","sessionName":"","sessionRemark":"","limitQuantity":9999,"remaining":34321447,"startTimeStamp":1514340000000,"promotionScope":0,"name":"商品","statusName":"已开抢","endTimeStamp":1514476800000,"id":116,"flashUrl":"/flashpromotion/product/session","startDate":"2017/12/27 10:00:00","brandPic":""},"brandFlashPromotions":[{"promotionType":"0","endDate":"2017/12/29 00:00:00","session":"10:00","sessionName":"","sessionRemark":"","limitQuantity":9999,"remaining":34321434,"products":[{"productId":169946,"originalPrice":750,"promotionPrice":750,"name":"SmartandJOy Nathalie Blanc 孔雀羽毛图案优雅半身长裙","isOver":true,"pic":"/2017/08/24/064106c898694850f10d2175321f6693440e02.jpg","store":0,"flashPrice":11,"brand":"SmartandJOy"}],"startTimeStamp":1514340000000,"promotionScope":0,"name":"商品","statusName":"已开抢","endTimeStamp":1514476800000,"id":116,"flashUrl":"/flashpromotion/product/session","startDate":"2017/12/27 10:00:00","brandPic":""}],"products":{"next":false,"total":1,"previous":false,"index":1,"pageSize":40,"list":[{"img":"/2017/11/29/065354c14c1d34f7878d42fff481ac24655b71.jpg","originalPrice":100,"promotionPrice":50,"sellPrice":50,"soonPrice":50,"isCart":1,"flashPrice":2,"categoryName":"针织衫","colors":[{"img":"/2017/11/29/065352601ddca070efdec8792c3309145c3095.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"blue"}],"firstRatio":0.1,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"X"}],"price":2,"recomScore":29500,"isTopical":true,"productSellType":"CUSTOM","id":170105,"brand":"1011","designerId":10687,"comments":10,"isSpot":false,"grossRatio":0.3,"salePrice":50,"secondRatio":0.2,"store":1,"designer":"武学凯","consults":0,"isCrowd":false,"minPrice":2,"name":"测试正常商品 201711291453","collectioned":0,"mark":1,"categoryIds":1705}]}}
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
         * flashPromotion : {"promotionType":"0","endDate":"2017/12/29 00:00:00","session":"10:00","sessionName":"","sessionRemark":"","limitQuantity":9999,"remaining":34321447,"startTimeStamp":1514340000000,"promotionScope":0,"name":"商品","statusName":"已开抢","endTimeStamp":1514476800000,"id":116,"flashUrl":"/flashpromotion/product/session","startDate":"2017/12/27 10:00:00","brandPic":""}
         * brandFlashPromotions : [{"promotionType":"0","endDate":"2017/12/29 00:00:00","session":"10:00","sessionName":"","sessionRemark":"","limitQuantity":9999,"remaining":34321434,"products":[{"productId":169946,"originalPrice":750,"promotionPrice":750,"name":"SmartandJOy Nathalie Blanc 孔雀羽毛图案优雅半身长裙","isOver":true,"pic":"/2017/08/24/064106c898694850f10d2175321f6693440e02.jpg","store":0,"flashPrice":11,"brand":"SmartandJOy"}],"startTimeStamp":1514340000000,"promotionScope":0,"name":"商品","statusName":"已开抢","endTimeStamp":1514476800000,"id":116,"flashUrl":"/flashpromotion/product/session","startDate":"2017/12/27 10:00:00","brandPic":""}]
         * products : {"next":false,"total":1,"previous":false,"index":1,"pageSize":40,"list":[{"img":"/2017/11/29/065354c14c1d34f7878d42fff481ac24655b71.jpg","originalPrice":100,"promotionPrice":50,"sellPrice":50,"soonPrice":50,"isCart":1,"flashPrice":2,"categoryName":"针织衫","colors":[{"img":"/2017/11/29/065352601ddca070efdec8792c3309145c3095.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"blue"}],"firstRatio":0.1,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"X"}],"price":2,"recomScore":29500,"isTopical":true,"productSellType":"CUSTOM","id":170105,"brand":"1011","designerId":10687,"comments":10,"isSpot":false,"grossRatio":0.3,"salePrice":50,"secondRatio":0.2,"store":1,"designer":"武学凯","consults":0,"isCrowd":false,"minPrice":2,"name":"测试正常商品 201711291453","collectioned":0,"mark":1,"categoryIds":1705}]}
         */

        private FlashPromotionBean flashPromotion;
        private ProductsBean products;
        private List<BrandFlashPromotionsBean> brandFlashPromotions;

        public FlashPromotionBean getFlashPromotion() {
            return flashPromotion;
        }

        public void setFlashPromotion(FlashPromotionBean flashPromotion) {
            this.flashPromotion = flashPromotion;
        }

        public ProductsBean getProducts() {
            return products;
        }

        public void setProducts(ProductsBean products) {
            this.products = products;
        }

        public List<BrandFlashPromotionsBean> getBrandFlashPromotions() {
            return brandFlashPromotions;
        }

        public void setBrandFlashPromotions(List<BrandFlashPromotionsBean> brandFlashPromotions) {
            this.brandFlashPromotions = brandFlashPromotions;
        }

        public static class FlashPromotionBean {
            /**
             * promotionType : 0
             * endDate : 2017/12/29 00:00:00
             * session : 10:00
             * sessionName :
             * sessionRemark :
             * limitQuantity : 9999
             * remaining : 34321447
             * startTimeStamp : 1514340000000
             * promotionScope : 0
             * name : 商品
             * statusName : 已开抢
             * endTimeStamp : 1514476800000
             * id : 116
             * flashUrl : /flashpromotion/product/session
             * startDate : 2017/12/27 10:00:00
             * brandPic :
             */

            private String promotionType;
            private String endDate;
            private String session;
            private String sessionName;
            private String sessionRemark;
            private int limitQuantity;
            private String remaining;
            private long startTimeStamp;
            private int promotionScope;
            private String name;
            private String statusName;
            private long endTimeStamp;
            private int id;
            private String flashUrl;
            private String startDate;
            private String brandPic;
            private int pv;

            public int getPv() {
                return pv;
            }

            public void setPv(int pv) {
                this.pv = pv;
            }

            public String getPromotionType() {
                return promotionType;
            }

            public void setPromotionType(String promotionType) {
                this.promotionType = promotionType;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }

            public String getSession() {
                return session;
            }

            public void setSession(String session) {
                this.session = session;
            }

            public String getSessionName() {
                return sessionName;
            }

            public void setSessionName(String sessionName) {
                this.sessionName = sessionName;
            }

            public String getSessionRemark() {
                return sessionRemark;
            }

            public void setSessionRemark(String sessionRemark) {
                this.sessionRemark = sessionRemark;
            }

            public int getLimitQuantity() {
                return limitQuantity;
            }

            public void setLimitQuantity(int limitQuantity) {
                this.limitQuantity = limitQuantity;
            }

            public String getRemaining() {
                return remaining;
            }

            public void setRemaining(String remaining) {
                this.remaining = remaining;
            }

            public long getStartTimeStamp() {
                return startTimeStamp;
            }

            public void setStartTimeStamp(long startTimeStamp) {
                this.startTimeStamp = startTimeStamp;
            }

            public int getPromotionScope() {
                return promotionScope;
            }

            public void setPromotionScope(int promotionScope) {
                this.promotionScope = promotionScope;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStatusName() {
                return statusName;
            }

            public void setStatusName(String statusName) {
                this.statusName = statusName;
            }

            public long getEndTimeStamp() {
                return endTimeStamp;
            }

            public void setEndTimeStamp(long endTimeStamp) {
                this.endTimeStamp = endTimeStamp;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getFlashUrl() {
                return flashUrl;
            }

            public void setFlashUrl(String flashUrl) {
                this.flashUrl = flashUrl;
            }

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }

            public String getBrandPic() {
                return brandPic;
            }

            public void setBrandPic(String brandPic) {
                this.brandPic = brandPic;
            }
        }

        public static class ProductsBean {
            /**
             * next : false
             * total : 1
             * previous : false
             * index : 1
             * pageSize : 40
             * list : [{"img":"/2017/11/29/065354c14c1d34f7878d42fff481ac24655b71.jpg","originalPrice":100,"promotionPrice":50,"sellPrice":50,"soonPrice":50,"isCart":1,"flashPrice":2,"categoryName":"针织衫","colors":[{"img":"/2017/11/29/065352601ddca070efdec8792c3309145c3095.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"blue"}],"firstRatio":0.1,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"X"}],"price":2,"recomScore":29500,"isTopical":true,"productSellType":"CUSTOM","id":170105,"brand":"1011","designerId":10687,"comments":10,"isSpot":false,"grossRatio":0.3,"salePrice":50,"secondRatio":0.2,"store":1,"designer":"武学凯","consults":0,"isCrowd":false,"minPrice":2,"name":"测试正常商品 201711291453","collectioned":0,"mark":1,"categoryIds":1705}]
             */

            private boolean next;
            private int total;
            private boolean previous;
            private int index;
            private int pageSize;
            private List<ListBean> list;

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

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * img : /2017/11/29/065354c14c1d34f7878d42fff481ac24655b71.jpg
                 * originalPrice : 100
                 * promotionPrice : 50
                 * sellPrice : 50
                 * soonPrice : 50
                 * isCart : 1
                 * flashPrice : 2
                 * categoryName : 针织衫
                 * colors : [{"img":"/2017/11/29/065352601ddca070efdec8792c3309145c3095.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"blue"}]
                 * firstRatio : 0.1
                 * sizes : [{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"X"}]
                 * price : 2
                 * recomScore : 29500
                 * isTopical : true
                 * productSellType : CUSTOM
                 * id : 170105
                 * brand : 1011
                 * designerId : 10687
                 * comments : 10
                 * isSpot : false
                 * grossRatio : 0.3
                 * salePrice : 50
                 * secondRatio : 0.2
                 * store : 1
                 * designer : 武学凯
                 * consults : 0
                 * isCrowd : false
                 * minPrice : 2
                 * name : 测试正常商品 201711291453
                 * collectioned : 0
                 * mark : 1
                 * categoryIds : 1705
                 */

                private String img;
                private double originalPrice;
                private double promotionPrice;
                private double sellPrice;
                private double soonPrice;
                private int isCart;
                private double flashPrice;
                private String categoryName;
                private double firstRatio;
                private double price;
                private int recomScore;
                private boolean isTopical;
                private String productSellType;
                private long id;
                private String brand;
                private int designerId;
                private int comments;
                private boolean isSpot;
                private double grossRatio;
                private double salePrice;
                private double secondRatio;
                private int store;
                private String designer;
                private int consults;
                private boolean isCrowd;
                private double minPrice;
                private String name;
                private String subTitle;
                private int collectioned;
                private int mark;
                private int categoryId;
                private List<ColorsBean> colors;
                private List<SizesBean> sizes;
                private int promotionId;
                private int pv;
                private String orderPromotionTypeName;
                private Integer flashStock;
                private Integer flashSellStock;

                public Integer getFlashStock() {
                    return flashStock;
                }

                public void setFlashStock(Integer flashStock) {
                    this.flashStock = flashStock;
                }

                public Integer getFlashSellStock() {
                    return flashSellStock;
                }

                public void setFlashSellStock(Integer flashSellStock) {
                    this.flashSellStock = flashSellStock;
                }

                public String getOrderPromotionTypeName() {
                    return orderPromotionTypeName;
                }

                public void setOrderPromotionTypeName(String orderPromotionTypeName) {
                    this.orderPromotionTypeName = orderPromotionTypeName;
                }

                public int getPv() {
                    return pv;
                }

                public void setPv(int pv) {
                    this.pv = pv;
                }

                public String getSubTitle() {
                    return subTitle;
                }

                public void setSubTitle(String subTitle) {
                    this.subTitle = subTitle;
                }

                public int getPromotionId() {
                    return promotionId;
                }

                public void setPromotionId(int promotionId) {
                    this.promotionId = promotionId;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public double getOriginalPrice() {
                    return originalPrice;
                }

                public void setOriginalPrice(double originalPrice) {
                    this.originalPrice = originalPrice;
                }

                public double getPromotionPrice() {
                    return promotionPrice;
                }

                public void setPromotionPrice(double promotionPrice) {
                    this.promotionPrice = promotionPrice;
                }

                public double getSellPrice() {
                    return sellPrice;
                }

                public void setSellPrice(double sellPrice) {
                    this.sellPrice = sellPrice;
                }

                public double getSoonPrice() {
                    return soonPrice;
                }

                public void setSoonPrice(double soonPrice) {
                    this.soonPrice = soonPrice;
                }

                public int getIsCart() {
                    return isCart;
                }

                public void setIsCart(int isCart) {
                    this.isCart = isCart;
                }

                public double getFlashPrice() {
                    return flashPrice;
                }

                public void setFlashPrice(double flashPrice) {
                    this.flashPrice = flashPrice;
                }

                public String getCategoryName() {
                    return categoryName;
                }

                public void setCategoryName(String categoryName) {
                    this.categoryName = categoryName;
                }

                public double getFirstRatio() {
                    return firstRatio;
                }

                public void setFirstRatio(double firstRatio) {
                    this.firstRatio = firstRatio;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public int getRecomScore() {
                    return recomScore;
                }

                public void setRecomScore(int recomScore) {
                    this.recomScore = recomScore;
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

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public String getBrand() {
                    return brand;
                }

                public void setBrand(String brand) {
                    this.brand = brand;
                }

                public int getDesignerId() {
                    return designerId;
                }

                public void setDesignerId(int designerId) {
                    this.designerId = designerId;
                }

                public int getComments() {
                    return comments;
                }

                public void setComments(int comments) {
                    this.comments = comments;
                }

                public boolean isIsSpot() {
                    return isSpot;
                }

                public void setIsSpot(boolean isSpot) {
                    this.isSpot = isSpot;
                }

                public double getGrossRatio() {
                    return grossRatio;
                }

                public void setGrossRatio(double grossRatio) {
                    this.grossRatio = grossRatio;
                }

                public double getSalePrice() {
                    return salePrice;
                }

                public void setSalePrice(double salePrice) {
                    this.salePrice = salePrice;
                }

                public double getSecondRatio() {
                    return secondRatio;
                }

                public void setSecondRatio(double secondRatio) {
                    this.secondRatio = secondRatio;
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

                public int getCollectioned() {
                    return collectioned;
                }

                public void setCollectioned(int collectioned) {
                    this.collectioned = collectioned;
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
                     * img : /2017/11/29/065352601ddca070efdec8792c3309145c3095.jpg
                     * code : 0
                     * groupId : 0
                     * name : 颜色
                     * id : 1
                     * value : blue
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
                     * value : X
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

        public static class BrandFlashPromotionsBean {
            /**
             * promotionType : 0
             * endDate : 2017/12/29 00:00:00
             * session : 10:00
             * sessionName :
             * sessionRemark :
             * limitQuantity : 9999
             * remaining : 34321434
             * products : [{"productId":169946,"originalPrice":750,"promotionPrice":750,"name":"SmartandJOy Nathalie Blanc 孔雀羽毛图案优雅半身长裙","isOver":true,"pic":"/2017/08/24/064106c898694850f10d2175321f6693440e02.jpg","store":0,"flashPrice":11,"brand":"SmartandJOy"}]
             * startTimeStamp : 1514340000000
             * promotionScope : 0
             * name : 商品
             * statusName : 已开抢
             * endTimeStamp : 1514476800000
             * id : 116
             * flashUrl : /flashpromotion/product/session
             * startDate : 2017/12/27 10:00:00
             * brandPic :
             */

            private String promotionType;
            private String endDate;
            private String session;
            private String sessionName;
            private String sessionRemark;
            private int limitQuantity;
            private long startTimeStamp;
            private int promotionScope;
            private String name;
            private String statusName;
            private long endTimeStamp;
            private int id;
            private String flashUrl;
            private String startDate;
            private String brandPic;
            private List<ProductsBean.ListBean> products;
            private int pv;
            public boolean notAdd = true;

            public int getPv() {
                return pv;
            }

            public void setPv(int pv) {
                this.pv = pv;
            }

            public String getPromotionType() {
                return promotionType;
            }

            public void setPromotionType(String promotionType) {
                this.promotionType = promotionType;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }

            public String getSession() {
                return session;
            }

            public void setSession(String session) {
                this.session = session;
            }

            public String getSessionName() {
                return sessionName;
            }

            public void setSessionName(String sessionName) {
                this.sessionName = sessionName;
            }

            public String getSessionRemark() {
                return sessionRemark;
            }

            public void setSessionRemark(String sessionRemark) {
                this.sessionRemark = sessionRemark;
            }

            public int getLimitQuantity() {
                return limitQuantity;
            }

            public void setLimitQuantity(int limitQuantity) {
                this.limitQuantity = limitQuantity;
            }


            public long getStartTimeStamp() {
                return startTimeStamp;
            }

            public void setStartTimeStamp(long startTimeStamp) {
                this.startTimeStamp = startTimeStamp;
            }

            public int getPromotionScope() {
                return promotionScope;
            }

            public void setPromotionScope(int promotionScope) {
                this.promotionScope = promotionScope;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getStatusName() {
                return statusName;
            }

            public void setStatusName(String statusName) {
                this.statusName = statusName;
            }

            public long getEndTimeStamp() {
                return endTimeStamp;
            }

            public void setEndTimeStamp(long endTimeStamp) {
                this.endTimeStamp = endTimeStamp;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getFlashUrl() {
                return flashUrl;
            }

            public void setFlashUrl(String flashUrl) {
                this.flashUrl = flashUrl;
            }

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }

            public String getBrandPic() {
                return brandPic;
            }

            public void setBrandPic(String brandPic) {
                this.brandPic = brandPic;
            }

            public List<ProductsBean.ListBean> getProducts() {
                return products;
            }

            public void setProducts(List<ProductsBean.ListBean> products) {
                this.products = products;
            }
        }
    }
}
