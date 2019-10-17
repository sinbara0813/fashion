package com.d2cmall.buyer.bean;


import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

public class PromotionBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private String wapBanner;
        private PromotionContentBean promotion;
        private ProductsBean products;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private String name;

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        private String picture;
        private String content;

        public String getWapBanner() {
            return wapBanner;
        }

        public void setWapBanner(String wapBanner) {
            this.wapBanner = wapBanner;
        }

        public PromotionContentBean getPromotion() {
            return promotion;
        }

        public void setPromotion(PromotionContentBean promotion) {
            this.promotion = promotion;
        }

        public ProductsBean getProducts() {
            return products;
        }

        public void setProducts(ProductsBean products) {
            this.products = products;
        }

        public static class PromotionContentBean {
            /**
             * promotionUrl : /promotion/4004
             * promotionName : 情人节 5w 限时6折！
             * promotionType : 0
             * prefix : null
             * promotionScope : 0
             * wapBanner : <p><img src="http://img.d2c.cn/2017/11/28/023958d8581f31c0a0b11f8204295690e4eb9b.jpg"><br></p>
             * startTime : 2017/11/17 00:00:00
             * endTime : 2017/12/09 00:00:00
             * type : 0
             * advance : 0
             */

            private String promotionUrl;
            private String promotionName;
            private int promotionType;
            private String prefix;
            private int promotionScope;
            private String wapBanner;
            private String startTime;
            private String endTime;
            private int type;
            private int advance;
            private String backgroundUrl;
            private String orderPromotionName;

            public String getBackgroundUrl() {
                return backgroundUrl;
            }

            public void setBackgroundUrl(String backgroundUrl) {
                this.backgroundUrl = backgroundUrl;
            }

            public boolean getStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }

            private boolean status;

            public String getPromotionTypeName() {
                return promotionTypeName;
            }

            public void setPromotionTypeName(String promotionTypeName) {
                this.promotionTypeName = promotionTypeName;
            }

            public String getPromotionSulo() {
                return promotionSulo;
            }

            public void setPromotionSulo(String promotionSulo) {
                this.promotionSulo = promotionSulo;
            }

            private String promotionTypeName;
            private String promotionSulo;

            public String getPromotionUrl() {
                return promotionUrl;
            }

            public void setPromotionUrl(String promotionUrl) {
                this.promotionUrl = promotionUrl;
            }

            public String getPromotionName() {
                return promotionName;
            }

            public void setPromotionName(String promotionName) {
                this.promotionName = promotionName;
            }

            public int getPromotionType() {
                return promotionType;
            }

            public void setPromotionType(int promotionType) {
                this.promotionType = promotionType;
            }

            public String getPrefix() {
                return prefix;
            }

            public void setPrefix(String prefix) {
                this.prefix = prefix;
            }

            public int getPromotionScope() {
                return promotionScope;
            }

            public void setPromotionScope(int promotionScope) {
                this.promotionScope = promotionScope;
            }

            public String getWapBanner() {
                return wapBanner;
            }

            public void setWapBanner(String wapBanner) {
                this.wapBanner = wapBanner;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getAdvance() {
                return advance;
            }

            public void setAdvance(int advance) {
                this.advance = advance;
            }
        }

        public static class ProductsBean {

            private boolean next;
            private int total;
            private boolean previous;
            private int index;
            private int pageSize;
            private List<GoodsBean.DataBean.ProductsBean.ListBean> list;

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

            public List<GoodsBean.DataBean.ProductsBean.ListBean> getList() {
                return list;
            }

            public void setList(List<GoodsBean.DataBean.ProductsBean.ListBean> list) {
                this.list = list;
            }

        }
    }
}
