package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2018/1/3 15:35
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class BrandUpdateListBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private BrandsBean brands;

        public BrandsBean getBrands() {
            return brands;
        }

        public void setBrands(BrandsBean brands) {
            this.brands = brands;
        }

        public static class BrandsBean {

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

                private String introPic;
                private String headPic;
                private String name;
                private int id;
                private String brand;
                private int attentioned;
                private int count;
                private List<ProductsBean> products;

                public String getIntroPic() {
                    return introPic;
                }

                public void setIntroPic(String introPic) {
                    this.introPic = introPic;
                }

                public String getHeadPic() {
                    return headPic;
                }

                public void setHeadPic(String headPic) {
                    this.headPic = headPic;
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

                public String getBrand() {
                    return brand;
                }

                public void setBrand(String brand) {
                    this.brand = brand;
                }

                public int getAttentioned() {
                    return attentioned;
                }

                public void setAttentioned(int attentioned) {
                    this.attentioned = attentioned;
                }

                public List<ProductsBean> getProducts() {
                    return products;
                }

                public void setProducts(List<ProductsBean> products) {
                    this.products = products;
                }

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public static class ProductsBean {

                    private String img;
                    private int id;
                    private double minPrice;
                    private double salePrice;
                    private double originalPrice;
                    private int promotionId;

                    public String getImg() {
                        return img;
                    }

                    public void setImg(String img) {
                        this.img = img;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
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

                    public double getOriginalPrice() {
                        return originalPrice;
                    }

                    public void setOriginalPrice(double originalPrice) {
                        this.originalPrice = originalPrice;
                    }

                    public int getPromotionId() {
                        return promotionId;
                    }

                    public void setPromotionId(int promotionId) {
                        this.promotionId = promotionId;
                    }
                }
            }
        }
    }
}
