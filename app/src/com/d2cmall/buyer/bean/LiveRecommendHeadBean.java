package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Fixme
 * Author: hrb
 * Date: 2017/01/12 14:59
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class LiveRecommendHeadBean extends BaseBean {

    /**
     * promotions : []
     * coupons : []
     * designer : {"isCod":1,"intro":"","name":"10112","likeCount":371,"recommend":1,"id":10687,"introPic":"/model/1607/b760e873d6795394f80b2a79c7df94b5","headPic":"/2016/10/26/0514146fb5b2c84e74e8f4857daba606d61e71.jpg","brand":"朱超凡","slogan":"","isAfter":0,"isSubscribe":1}
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
         * isCod : 1
         * intro :
         * name : 10112
         * likeCount : 371
         * recommend : 1
         * id : 10687
         * introPic : /model/1607/b760e873d6795394f80b2a79c7df94b5
         * headPic : /2016/10/26/0514146fb5b2c84e74e8f4857daba606d61e71.jpg
         * brand : 朱超凡
         * slogan :
         * isAfter : 0
         * isSubscribe : 1
         */

        private DesignerBean designer;
        private List<ProductDetailBean.DataBean.PromotionsBean> promotions;
        private List<ProductDetailBean.DataBean.CouponsEntity> coupons;

        public DesignerBean getDesigner() {
            return designer;
        }

        public void setDesigner(DesignerBean designer) {
            this.designer = designer;
        }

        public List<ProductDetailBean.DataBean.PromotionsBean> getPromotions() {
            return promotions;
        }

        public void setPromotions(List<ProductDetailBean.DataBean.PromotionsBean> promotions) {
            this.promotions = promotions;
        }

        public List<ProductDetailBean.DataBean.CouponsEntity> getCoupons() {
            return coupons;
        }

        public void setCoupons(List<ProductDetailBean.DataBean.CouponsEntity> coupons) {
            this.coupons = coupons;
        }

        public static class DesignerBean {
            private int isCod;
            private String intro;
            private String name;
            private int likeCount;
            private int recommend;
            private long id;
            private String introPic;
            private String headPic;
            private String brand;
            private String slogan;
            private int isAfter;
            private int isSubscribe;

            public int getIsCod() {
                return isCod;
            }

            public void setIsCod(int isCod) {
                this.isCod = isCod;
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

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
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

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public String getSlogan() {
                return slogan;
            }

            public void setSlogan(String slogan) {
                this.slogan = slogan;
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
        }
    }
}
