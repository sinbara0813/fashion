package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Fixme
 * Author: hrb
 * Date: 2016/06/29 16:12
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CombProductBean extends BaseBean {

    /**
     * productComb : {"inernalSN":"H02761205002","name":"HUI BY ERAN HUI 惠文龙 LOGO织带元素系列 宽条纹休闲西装套装","subTitle":"宽条纹休闲西装套装+廓体条纹西装套装西裤","id":103423,"price":5000,"originalCost":5460,"mark":1,"products":[{"id":121308,"name":"HUI BY ERAN HUI 惠文龙 LOGO织带元素系列 宽条纹休闲西装（主打款）","sn":"H02761205002","remark":"","imgs":["/model/1601/ba8b39120c6be8d31de39f76dda04d05","/model/1601/5f9ef0abc7525f99779ad828633caca3","/model/1601/f0a4d951987abc1a88e57a3116572161","/model/1601/cd73f9bca5336a436a4f191c680a5882","/model/1601/dd5431635f19ace017e043dcf27396ab"],"colors":[{"id":90,"groupId":0,"name":"颜色","value":"黑色","code":"020","img":"/model/1601/ba8b39120c6be8d31de39f76dda04d05"}],"sizes":[{"id":26,"groupId":0,"name":"尺码","value":"S码","code":"01","img":""},{"id":27,"groupId":0,"name":"尺码","value":"M码","code":"02","img":""}],"mark":1,"store":9,"isTopical":false,"isCrowd":false,"isCart":1,"isCod":1,"designerId":10136,"designerPic":"/model/1603/99fd18d628cbe4684f408af287646ba2","price":3280,"originalCost":3280,"status":0},{"id":121309,"name":"HUI BY ERAN HUI 惠文龙 LOGO织带元素系列 廓体条纹西装套装西裤（主打款）","sn":"H02761081003","remark":"","imgs":["/model/1601/370f074df4af9af566d347707e7ae999","/model/1601/ba8b39120c6be8d31de39f76dda04d05","/model/1601/5f9ef0abc7525f99779ad828633caca3","/model/1601/f0a4d951987abc1a88e57a3116572161","/model/1601/cd73f9bca5336a436a4f191c680a5882"],"colors":[{"id":90,"name":"颜色","value":"黑色","code":"020","groupId":0,"img":"/model/1601/370f074df4af9af566d347707e7ae999"}],"sizes":[{"id":26,"name":"尺码","value":"S码","code":"01","groupId":0,"img":""},{"id":27,"name":"尺码","value":"M码","code":"02","groupId":0,"img":""}],"mark":1,"store":8,"isTopical":false,"isCrowd":false,"isCart":1,"isCod":1,"designerId":10136,"designerPic":"/model/1603/99fd18d628cbe4684f408af287646ba2","price":2180,"originalCost":2180,"status":0}]}
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
         * inernalSN : H02761205002
         * name : HUI BY ERAN HUI 惠文龙 LOGO织带元素系列 宽条纹休闲西装套装
         * subTitle : 宽条纹休闲西装套装+廓体条纹西装套装西裤
         * id : 103423
         * price : 5000.0
         * originalCost : 5460.0
         * mark : 1
         * products : [{"id":121308,"name":"HUI BY ERAN HUI 惠文龙 LOGO织带元素系列 宽条纹休闲西装（主打款）","sn":"H02761205002","remark":"","imgs":["/model/1601/ba8b39120c6be8d31de39f76dda04d05","/model/1601/5f9ef0abc7525f99779ad828633caca3","/model/1601/f0a4d951987abc1a88e57a3116572161","/model/1601/cd73f9bca5336a436a4f191c680a5882","/model/1601/dd5431635f19ace017e043dcf27396ab"],"colors":[{"id":90,"groupId":0,"name":"颜色","value":"黑色","code":"020","img":"/model/1601/ba8b39120c6be8d31de39f76dda04d05"}],"sizes":[{"id":26,"groupId":0,"name":"尺码","value":"S码","code":"01","img":""},{"id":27,"groupId":0,"name":"尺码","value":"M码","code":"02","img":""}],"mark":1,"store":9,"isTopical":false,"isCrowd":false,"isCart":1,"isCod":1,"designerId":10136,"designerPic":"/model/1603/99fd18d628cbe4684f408af287646ba2","price":3280,"originalCost":3280,"status":0},{"id":121309,"name":"HUI BY ERAN HUI 惠文龙 LOGO织带元素系列 廓体条纹西装套装西裤（主打款）","sn":"H02761081003","remark":"","imgs":["/model/1601/370f074df4af9af566d347707e7ae999","/model/1601/ba8b39120c6be8d31de39f76dda04d05","/model/1601/5f9ef0abc7525f99779ad828633caca3","/model/1601/f0a4d951987abc1a88e57a3116572161","/model/1601/cd73f9bca5336a436a4f191c680a5882"],"colors":[{"id":90,"name":"颜色","value":"黑色","code":"020","groupId":0,"img":"/model/1601/370f074df4af9af566d347707e7ae999"}],"sizes":[{"id":26,"name":"尺码","value":"S码","code":"01","groupId":0,"img":""},{"id":27,"name":"尺码","value":"M码","code":"02","groupId":0,"img":""}],"mark":1,"store":8,"isTopical":false,"isCrowd":false,"isCart":1,"isCod":1,"designerId":10136,"designerPic":"/model/1603/99fd18d628cbe4684f408af287646ba2","price":2180,"originalCost":2180,"status":0}]
         */

        private ProductCombBean productComb;

        public ProductCombBean getProductComb() {
            return productComb;
        }

        public void setProductComb(ProductCombBean productComb) {
            this.productComb = productComb;
        }

        public static class ProductCombBean {
            private String inernalSN;
            private String name;
            private String subTitle;
            private int id;
            private double price;
            private double originalCost;
            private int mark;

            public int getStore() {
                return store;
            }

            public void setStore(int store) {
                this.store = store;
            }

            private int store;
            private List<ProductDetailBean.DataBean.ProductBean> products;

            public String getInernalSN() {
                return inernalSN;
            }

            public void setInernalSN(String inernalSN) {
                this.inernalSN = inernalSN;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSubTitle() {
                return subTitle;
            }

            public void setSubTitle(String subTitle) {
                this.subTitle = subTitle;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public double getOriginalCost() {
                return originalCost;
            }

            public void setOriginalCost(double originalCost) {
                this.originalCost = originalCost;
            }

            public int getMark() {
                return mark;
            }

            public void setMark(int mark) {
                this.mark = mark;
            }

            public List<ProductDetailBean.DataBean.ProductBean> getProducts() {
                return products;
            }

            public void setProducts(List<ProductDetailBean.DataBean.ProductBean> products) {
                this.products = products;
            }

        }
    }
}
