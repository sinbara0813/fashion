package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.util.Util;

import java.util.List;

public class RecommendListBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private RecommendsBean recommends;

        public RecommendsBean getRecommends() {
            return recommends;
        }

        public void setRecommends(RecommendsBean recommends) {
            this.recommends = recommends;
        }

        public static class RecommendsBean {

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

                private long id;
                private String name;
                private String sn;
                private String subTitle;
                private String img;
                private double price;
                private double minPrice;
                private double salePrice;
                private double sellPrice;
                private double originalPrice;
                private double flashPrice;
                private double soonPrice;
                private int store;
                private int mark;
                private boolean isSpot;
                private int isCart;
                private int isAfter;
                private int isSubscribe;
                private String productSellType;
                private String brand;
                private int designerId;
                private String designer;
                private int categoryId;
                private String categoryName;
                private int partnerSales;
                private String promotionId;
                private String clazz;
                private List<ProductsBean> products;
                private boolean isLongClick;
                private String searchFrom;
                private String orderPromotionType;
                private long orderPromotionId;
                private String orderPromotionTypeName;
                private  String promotionTypeName;
                private String productTradeType;//贸易类型 COMMON(一般) CROSS(跨境)
                private String productImageCover;

                public String getProductTradeType() {
                    return productTradeType;
                }

                public void setProductTradeType(String productTradeType) {
                    this.productTradeType = productTradeType;
                }

                public Integer getFlashPromotionId() {
                    return flashPromotionId;
                }

                public void setFlashPromotionId(Integer flashPromotionId) {
                    this.flashPromotionId = flashPromotionId;
                }

                private Integer flashPromotionId;

                public String getProductImageCover() {
                    return productImageCover;
                }

                public void setProductImageCover(String productImageCover) {
                    if (!Util.isEmpty(productImageCover)){
                        this.img=productImageCover;
                    }
                    this.productImageCover = productImageCover;
                }

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
                public String getSearchFrom() {
                    return searchFrom;
                }

                public void setSearchFrom(String searchFrom) {
                    this.searchFrom = searchFrom;
                }

                public boolean isLongClick() {
                    return isLongClick;
                }

                public void setLongClick(boolean longClick) {
                    isLongClick = longClick;
                }

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getSn() {
                    return sn;
                }

                public void setSn(String sn) {
                    this.sn = sn;
                }

                public String getSubTitle() {
                    return subTitle;
                }

                public void setSubTitle(String subTitle) {
                    this.subTitle = subTitle;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
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

                public double getSalePrice() {
                    return salePrice;
                }

                public void setSalePrice(double salePrice) {
                    this.salePrice = salePrice;
                }

                public double getSellPrice() {
                    return sellPrice;
                }

                public void setSellPrice(double sellPrice) {
                    this.sellPrice = sellPrice;
                }

                public double getOriginalPrice() {
                    return originalPrice;
                }

                public void setOriginalPrice(double originalPrice) {
                    this.originalPrice = originalPrice;
                }

                public double getFlashPrice() {
                    return flashPrice;
                }

                public void setFlashPrice(double flashPrice) {
                    this.flashPrice = flashPrice;
                }

                public double getSoonPrice() {
                    return soonPrice;
                }

                public void setSoonPrice(int soonPrice) {
                    this.soonPrice = soonPrice;
                }

                public int getStore() {
                    return store;
                }

                public void setStore(int store) {
                    this.store = store;
                }

                public int getMark() {
                    return mark;
                }

                public void setMark(int mark) {
                    this.mark = mark;
                }

                public boolean isIsSpot() {
                    return isSpot;
                }

                public void setIsSpot(boolean isSpot) {
                    this.isSpot = isSpot;
                }

                public int getIsCart() {
                    return isCart;
                }

                public void setIsCart(int isCart) {
                    this.isCart = isCart;
                }

                public int getIsAfter() {
                    return isAfter;
                }

                public void setIsAfter(int isAfter) {
                    this.isAfter = isAfter;
                }

                public int getIsSubscribe() {
                    return isSubscribe;
                }

                public void setIsSubscribe(int isSubscribe) {
                    this.isSubscribe = isSubscribe;
                }

                public String getProductSellType() {
                    return productSellType;
                }

                public void setProductSellType(String productSellType) {
                    this.productSellType = productSellType;
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

                public String getDesigner() {
                    return designer;
                }

                public void setDesigner(String designer) {
                    this.designer = designer;
                }

                public int getCategoryId() {
                    return categoryId;
                }

                public void setCategoryId(int categoryId) {
                    this.categoryId = categoryId;
                }

                public String getCategoryName() {
                    return categoryName;
                }

                public void setCategoryName(String categoryName) {
                    this.categoryName = categoryName;
                }

                public int getPartnerSales() {
                    return partnerSales;
                }

                public void setPartnerSales(int partnerSales) {
                    this.partnerSales = partnerSales;
                }

                public String getPromotionId() {
                    return promotionId;
                }

                public void setPromotionId(String promotionId) {
                    this.promotionId = promotionId;
                }

                public String getClazz() {
                    return clazz;
                }

                public void setClazz(String clazz) {
                    this.clazz = clazz;
                }

                public List<ProductsBean> getProducts() {
                    return products;
                }

                public void setProducts(List<ProductsBean> products) {
                    this.products = products;
                }

                public static class ProductsBean {

                    private int id;
                    private String img;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getImg() {
                        return img;
                    }

                    public void setImg(String img) {
                        this.img = img;
                    }

                }
            }
        }
    }
}
