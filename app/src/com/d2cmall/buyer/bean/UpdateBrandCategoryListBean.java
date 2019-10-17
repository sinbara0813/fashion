package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2018/9/10.
 */

public class UpdateBrandCategoryListBean extends BaseBean {

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
                private int count;
                private int likeCount;
                private int recommend;
                private String introPic;
                private String video;
                private String headPic;
                private int fans;
                private String videoPic;
                private String intro;
                private String name;
                private int id;
                private String brand;
                private int attentioned;
                private List<ProductsBean> products;

                public String getVideo() {
                    return video;
                }

                public void setVideo(String video) {
                    this.video = video;
                }

                public String getVideoPic() {
                    return videoPic;
                }

                public void setVideoPic(String videoPic) {
                    this.videoPic = videoPic;
                }

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public int getLikeCount() {
                    return likeCount;
                }

                public void setLikeCount(int likeCount) {
                    this.likeCount = likeCount;
                }

                public int getRecommend() {
                    return recommend;
                }

                public void setRecommend(int recommend) {
                    this.recommend = recommend;
                }

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

                public int getFans() {
                    return fans;
                }

                public void setFans(int fans) {
                    this.fans = fans;
                }

                public String getIntro() {
                    return intro;
                }

                public void setIntro(String intro) {
                    this.intro = intro;
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

                public static class ProductsBean{
                    private double sellPrice;
                    private Integer promotionId;
                    private String mainPic;
                    private long id;
                    private String img;
                    private double minPrice;
                    private double originalPrice;

                    public double getSellPrice() {
                        return sellPrice;
                    }

                    public void setSellPrice(double sellPrice) {
                        this.sellPrice = sellPrice;
                    }

                    public Integer getPromotionId() {
                        return promotionId;
                    }

                    public void setPromotionId(Integer promotionId) {
                        this.promotionId = promotionId;
                    }

                    public String getMainPic() {
                        return mainPic;
                    }

                    public void setMainPic(String mainPic) {
                        this.mainPic = mainPic;
                    }

                    public long getId() {
                        return id;
                    }

                    public void setId(long id) {
                        this.id = id;
                    }

                    public String getImg() {
                        return img;
                    }

                    public void setImg(String img) {
                        this.img = img;
                    }

                    public double getMinPrice() {
                        return minPrice;
                    }

                    public void setMinPrice(double minPrice) {
                        this.minPrice = minPrice;
                    }

                    public double getOriginalPrice() {
                        return originalPrice;
                    }

                    public void setOriginalPrice(double originalPrice) {
                        this.originalPrice = originalPrice;
                    }
                }
            }
        }
    }
}
