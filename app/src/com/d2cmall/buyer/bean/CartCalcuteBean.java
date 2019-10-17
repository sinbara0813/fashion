package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/7 12:31
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CartCalcuteBean extends BaseBean {

    /**
     * data : {"cart":{"totalAmount":586.01,"shippingRates":0,"items":[{"designerId":10942,"goodPromotionId":0,"quantity":1,"productId":101504,"originalPrice":6200,"promotionPrice":0,"totalPrice":0.01,"productImg":"/sp/4/101504/cbbc4ed1576ac2817354c96828302fcf","designer":"设计师-测试专用","productName":"测试商品 请勿购买","orderPromotionId":4043,"promotions":[{"promotionUrl":"/promotion/4043","promotionName":"RPD 型品 满300减50 满500减100 满1000减300","promotionType":"ORDER","promotionId":4043}],"id":2460063,"after":1,"endTime":0,"productPrice":0.01,"choiceOrderPromotions":[{"id":4043,"createDate":1503044936000,"creator":"罗浩","modifyDate":1504670227000,"lastModifyMan":"罗浩","solution":"300-50,500-100,1000-300","promotionType":2,"promotionScope":1,"name":"RPD 型品 满300减50 满500减100 满1000减300","enable":true,"startTime":1503158400000,"endTime":1505923200000,"timing":1,"customCode":null,"mobileCode":null,"drafts":null,"description":"RPD 型品满减","display":false,"deleted":false,"banner":null,"pcBanner":null,"wapBanner":null,"whole":false,"tagId":49,"sort":1000000091,"smallPic":"/2017/08/18/082834530eb96a4d05576e15c19e8278691052.jpg","promotionScopeName":"订单优惠","over":false,"enumPromotionType":"X_OFF_Y_STEP","enumPromotionScope":"ORDER"}]},{"designerId":11227,"goodPromotionId":0,"quantity":1,"productId":169634,"originalPrice":258,"promotionPrice":0,"totalPrice":258,"productImg":"/2017/08/21/073311dd189632ee810c848ec38db67880ec17.jpg","designer":"Easejoyce","productName":"意斯贝EASEJOYCE设计师品牌夜莺珍珠翅膀锁骨短项链925纯银镀金创意气质女","orderPromotionId":0,"promotions":[],"id":2460010,"after":1,"endTime":0,"productPrice":258,"choiceOrderPromotions":[]},{"designerId":10944,"goodPromotionId":0,"quantity":1,"productId":167504,"originalPrice":328,"promotionPrice":0,"totalPrice":328,"productImg":"/2017/08/02/0705377620dac6b1cfca1a8e1f39c047636aca.jpg","designer":"Hera velling","productName":"HeraVelling 重工渐变百褶 荷叶边露肩吊带上衣","orderPromotionId":0,"promotions":[],"id":2459989,"after":1,"endTime":0,"productPrice":328,"choiceOrderPromotions":[]}],"promotionAmount":0}}
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
         * cart : {"totalAmount":586.01,"shippingRates":0,"items":[{"designerId":10942,"goodPromotionId":0,"quantity":1,"productId":101504,"originalPrice":6200,"promotionPrice":0,"totalPrice":0.01,"productImg":"/sp/4/101504/cbbc4ed1576ac2817354c96828302fcf","designer":"设计师-测试专用","productName":"测试商品 请勿购买","orderPromotionId":4043,"promotions":[{"promotionUrl":"/promotion/4043","promotionName":"RPD 型品 满300减50 满500减100 满1000减300","promotionType":"ORDER","promotionId":4043}],"id":2460063,"after":1,"endTime":0,"productPrice":0.01,"choiceOrderPromotions":[{"id":4043,"createDate":1503044936000,"creator":"罗浩","modifyDate":1504670227000,"lastModifyMan":"罗浩","solution":"300-50,500-100,1000-300","promotionType":2,"promotionScope":1,"name":"RPD 型品 满300减50 满500减100 满1000减300","enable":true,"startTime":1503158400000,"endTime":1505923200000,"timing":1,"customCode":null,"mobileCode":null,"drafts":null,"description":"RPD 型品满减","display":false,"deleted":false,"banner":null,"pcBanner":null,"wapBanner":null,"whole":false,"tagId":49,"sort":1000000091,"smallPic":"/2017/08/18/082834530eb96a4d05576e15c19e8278691052.jpg","promotionScopeName":"订单优惠","over":false,"enumPromotionType":"X_OFF_Y_STEP","enumPromotionScope":"ORDER"}]},{"designerId":11227,"goodPromotionId":0,"quantity":1,"productId":169634,"originalPrice":258,"promotionPrice":0,"totalPrice":258,"productImg":"/2017/08/21/073311dd189632ee810c848ec38db67880ec17.jpg","designer":"Easejoyce","productName":"意斯贝EASEJOYCE设计师品牌夜莺珍珠翅膀锁骨短项链925纯银镀金创意气质女","orderPromotionId":0,"promotions":[],"id":2460010,"after":1,"endTime":0,"productPrice":258,"choiceOrderPromotions":[]},{"designerId":10944,"goodPromotionId":0,"quantity":1,"productId":167504,"originalPrice":328,"promotionPrice":0,"totalPrice":328,"productImg":"/2017/08/02/0705377620dac6b1cfca1a8e1f39c047636aca.jpg","designer":"Hera velling","productName":"HeraVelling 重工渐变百褶 荷叶边露肩吊带上衣","orderPromotionId":0,"promotions":[],"id":2459989,"after":1,"endTime":0,"productPrice":328,"choiceOrderPromotions":[]}],"promotionAmount":0}
         */

        private CartBean cart;

        public CartBean getCart() {
            return cart;
        }

        public void setCart(CartBean cart) {
            this.cart = cart;
        }

        public static class CartBean {
            /**
             * totalAmount : 586.01
             * shippingRates : 0
             * items : [{"designerId":10942,"goodPromotionId":0,"quantity":1,"productId":101504,"originalPrice":6200,"promotionPrice":0,"totalPrice":0.01,"productImg":"/sp/4/101504/cbbc4ed1576ac2817354c96828302fcf","designer":"设计师-测试专用","productName":"测试商品 请勿购买","orderPromotionId":4043,"promotions":[{"promotionUrl":"/promotion/4043","promotionName":"RPD 型品 满300减50 满500减100 满1000减300","promotionType":"ORDER","promotionId":4043}],"id":2460063,"after":1,"endTime":0,"productPrice":0.01,"choiceOrderPromotions":[{"id":4043,"createDate":1503044936000,"creator":"罗浩","modifyDate":1504670227000,"lastModifyMan":"罗浩","solution":"300-50,500-100,1000-300","promotionType":2,"promotionScope":1,"name":"RPD 型品 满300减50 满500减100 满1000减300","enable":true,"startTime":1503158400000,"endTime":1505923200000,"timing":1,"customCode":null,"mobileCode":null,"drafts":null,"description":"RPD 型品满减","display":false,"deleted":false,"banner":null,"pcBanner":null,"wapBanner":null,"whole":false,"tagId":49,"sort":1000000091,"smallPic":"/2017/08/18/082834530eb96a4d05576e15c19e8278691052.jpg","promotionScopeName":"订单优惠","over":false,"enumPromotionType":"X_OFF_Y_STEP","enumPromotionScope":"ORDER"}]},{"designerId":11227,"goodPromotionId":0,"quantity":1,"productId":169634,"originalPrice":258,"promotionPrice":0,"totalPrice":258,"productImg":"/2017/08/21/073311dd189632ee810c848ec38db67880ec17.jpg","designer":"Easejoyce","productName":"意斯贝EASEJOYCE设计师品牌夜莺珍珠翅膀锁骨短项链925纯银镀金创意气质女","orderPromotionId":0,"promotions":[],"id":2460010,"after":1,"endTime":0,"productPrice":258,"choiceOrderPromotions":[]},{"designerId":10944,"goodPromotionId":0,"quantity":1,"productId":167504,"originalPrice":328,"promotionPrice":0,"totalPrice":328,"productImg":"/2017/08/02/0705377620dac6b1cfca1a8e1f39c047636aca.jpg","designer":"Hera velling","productName":"HeraVelling 重工渐变百褶 荷叶边露肩吊带上衣","orderPromotionId":0,"promotions":[],"id":2459989,"after":1,"endTime":0,"productPrice":328,"choiceOrderPromotions":[]}]
             * promotionAmount : 0
             */

            private double totalAmount;
            private double shippingRates;
            private double promotionAmount;
            private List<CartListBean.DataBean.CartBean> items;

            public double getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(double totalAmount) {
                this.totalAmount = totalAmount;
            }

            public double getShippingRates() {
                return shippingRates;
            }

            public void setShippingRates(double shippingRates) {
                this.shippingRates = shippingRates;
            }

            public double getPromotionAmount() {
                return promotionAmount;
            }

            public void setPromotionAmount(double promotionAmount) {
                this.promotionAmount = promotionAmount;
            }

            public List<CartListBean.DataBean.CartBean> getItems() {
                return items;
            }

            public void setItems(List<CartListBean.DataBean.CartBean> items) {
                this.items = items;
            }

        }
    }
}
