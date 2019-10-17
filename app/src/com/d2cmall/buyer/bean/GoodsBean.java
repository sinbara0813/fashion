package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.io.Serializable;
import java.util.List;

public class GoodsBean extends BaseBean implements Serializable {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private ProductsBean products;
        private List<RecSearchBean> recSearch;

        public ProductsBean getProducts() {
            return products;
        }

        public void setProducts(ProductsBean products) {
            this.products = products;
        }
        public List<RecSearchBean> getRecSearch() {
            return recSearch;
        }

        public void setRecSearch(List<RecSearchBean> recSearch) {
            this.recSearch = recSearch;
        }
        public static class RecSearchBean {
            /**
             * name : 大码卫衣
             * promotionId : 9977
             */

            private String name;
            private long promotionId;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public long getPromotionId() {
                return promotionId;
            }

            public void setPromotionId(long promotionId) {
                this.promotionId = promotionId;
            }
        }

        public static class ProductsBean implements Serializable {

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

            public static class ListBean implements Identifiable {
                public Integer getPromotionId() {
                  return promotionId==null?0:promotionId;
                }

                public void setPromotionId(Integer promotionId) {
                    this.promotionId = promotionId;
                }

                private Integer promotionId;
                private String img;
                private double soonPrice;

                public double getSoonPrice() {
                    return soonPrice;
                }

                public void setSoonPrice(double soonPrice) {
                    this.soonPrice = soonPrice;
                }

                private double originalPrice;
                private double promotionPrice;
                private int isCart;
                private String categoryName;
                private double price;
                private boolean isTopical;
                private String productSellType;
                private long id;
                private long designerId;
                private int promotionType;
                private int comments;
                private boolean isSpot;
                private double salePrice;
                private int store;
                private String designer;
                private int consults;
                private boolean isCrowd;
                private double minPrice;
                private String name;
                private String brand;
                private String collectioned;
                private int mark;
                private int categoryId;
                public boolean isEmpty;
                private String productTradeType;//贸易类型 COMMON(一般) CROSS(跨境)
                private SoonPromotion soonPromotion;

                public SoonPromotion getSoonPromotion() {
                    return soonPromotion;
                }

                public void setSoonPromotion(SoonPromotion soonPromotion) {
                    this.soonPromotion = soonPromotion;
                }

                public static class SoonPromotion implements Identifiable {

                    private Double soonPromotionPrice;//活动价
                    private Long soonPromotionDate;//活动开始时间
                    private String soonPromotionPrefix;//活动小标题

                    public Double getSoonPromotionPrice() {
                        return soonPromotionPrice;
                    }

                    public void setSoonPromotionPrice(Double soonPromotionPrice) {
                        this.soonPromotionPrice = soonPromotionPrice;
                    }

                    public Long getSoonPromotionDate() {
                        return soonPromotionDate;
                    }

                    public void setSoonPromotionDate(Long soonPromotionDate) {
                        this.soonPromotionDate = soonPromotionDate;
                    }

                    public String getSoonPromotionPrefix() {
                        return soonPromotionPrefix;
                    }

                    public void setSoonPromotionPrefix(String soonPromotionPrefix) {
                        this.soonPromotionPrefix = soonPromotionPrefix;
                    }

                    @Override
                    public long getId() {
                        return 0;
                    }
                }


                public String getProductTradeType() {
                    return productTradeType;
                }

                public void setProductTradeType(String productTradeType) {
                    this.productTradeType = productTradeType;
                }

                private List<ColorsBean> colors;
                private List<SizesBean> sizes;

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

                private boolean isLongClick;
                private String orderPromotionType;
                private long orderPromotionId;
                private String orderPromotionTypeName;

                private  String promotionTypeName;




                public String getOrderPromotionType() {
                    return orderPromotionType;
                }

                public void setOrderPromotionType(String orderPromotionType) {
                    this.orderPromotionType = orderPromotionType;
                }


                public void setOrderPromotionId(long orderPromotionId) {
                    this.orderPromotionId=orderPromotionId;
                }
                public Long getOrderPromotionId() {
                    return orderPromotionId;
                }

                public void setOrderPromotionId(Long orderPromotionId) {
                    this.orderPromotionId = orderPromotionId;
                }


                public String getOrderPromotionTypeName() {
                    return orderPromotionTypeName;
                }

                public void setOrderPromotionTypeName(String orderPromotionTypeName) {
                    this.orderPromotionTypeName = orderPromotionTypeName;
                }

                public boolean isLongClick() {
                    return isLongClick;
                }

                public void setLongClick(boolean longClick) {
                    isLongClick = longClick;
                }

                public String getBrand() {
                    return brand;
                }

                public void setBrand(String brand) {
                    this.brand = brand;
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

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
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

                public long getDesignerId() {
                    return designerId;
                }

                public void setDesignerId(long designerId) {
                    this.designerId = designerId;
                }

                public int getPromotionType() {
                    return promotionType;
                }

                public void setPromotionType(int promotionType) {
                    this.promotionType = promotionType;
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

                public double getSalePrice() {
                    return salePrice;
                }

                public void setSalePrice(double salePrice) {
                    this.salePrice = salePrice;
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

                public String getCollectioned() {
                    return collectioned;
                }

                public void setCollectioned(String collectioned) {
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

                public static class ColorsBean implements Serializable {
                    /**
                     * img : /2017/09/13/085320f380c5a11deffcb3b1700a1864ab9757.jpg
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

                public static class SizesBean implements Serializable {
                    /**
                     * img :
                     * code : 0
                     * groupId : 0
                     * name : 尺码
                     * id : 2
                     * value : XL
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
    }
}
