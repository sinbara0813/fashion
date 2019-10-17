package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/12/13 15:08
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FlashBrandListBean extends BaseBean {

    /**
     * data : {"brands":[{"promotionType":"1","endDate":"2017/12/22 00:00:00","session":"13:00","sessionName":"","sessionRemark":"","limitQuantity":9999,"remaining":115164230,"products":[{"img":"/2017/12/04/05281586e32bf6f6d175e2556eebecf74beef2.jpg","originalPrice":1000,"promotionPrice":1000,"sellPrice":1000,"soonPrice":1000,"isCart":1,"flashPrice":30,"categoryName":"眼镜","colors":[{"img":"/2017/12/04/0528077d884e0c262b58e00320bb99cb382931.png","code":"0","groupId":0,"name":"颜色","id":1,"value":"X"}],"firstRatio":0,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"X"}],"price":30,"recomScore":0,"isTopical":true,"productSellType":"SPOT","id":170122,"brand":"1011","designerId":10687,"comments":1,"isSpot":false,"grossRatio":1,"salePrice":1000,"secondRatio":0.2,"store":0,"designer":"武学凯","consults":0,"isCrowd":false,"minPrice":30,"name":"测试 201712041327","collectioned":0,"mark":1,"categoryId":1633},{"img":"/2017/12/01/074350c14c1d34f7878d42fff481ac24655b71.jpg","originalPrice":1000,"promotionPrice":1000,"sellPrice":1000,"soonPrice":1000,"isCart":1,"flashPrice":40,"categoryName":"高跟鞋","colors":[{"img":"/2017/12/01/0743487d884e0c262b58e00320bb99cb382931.png","code":"0","groupId":0,"name":"颜色","id":1,"value":"红色"}],"firstRatio":0.2,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"X"}],"price":40,"recomScore":0,"isTopical":false,"productSellType":"SPOT","id":170115,"brand":"1011","designerId":10687,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":1000,"secondRatio":0.3,"store":0,"designer":"武学凯","consults":0,"isCrowd":false,"minPrice":40,"name":"测试 test201712011543","collectioned":0,"mark":1,"categoryId":1672},{"img":"/2017/08/24/10150206bf0a8281886622eccb10b25f450c4b.jpg","originalPrice":7980,"promotionPrice":7980,"sellPrice":7980,"soonPrice":7980,"isCart":1,"flashPrice":111,"categoryName":"旅行包","colors":[{"img":"/2017/08/24/1008580e3d35fadfa29e9ca8aa59edce7e4d6a.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"粉银"},{"img":"/2017/08/24/10090106bf0a8281886622eccb10b25f450c4b.jpg","code":"0","groupId":0,"name":"颜色","id":2,"value":"黑红"},{"img":"/2017/08/24/10090393b679283a0d8d468e5939de2b702ff5.jpg","code":"0","groupId":0,"name":"颜色","id":3,"value":"黑蓝"},{"img":"/2017/08/24/1009064bef186c2375c1c469f6faa8e73eb446.jpg","code":"0","groupId":0,"name":"颜色","id":4,"value":"黑香槟"},{"img":"/2017/08/24/1009087000cd03ccb873112b41b2f44d571bfb.jpg","code":"0","groupId":0,"name":"颜色","id":5,"value":"黑银"},{"img":"/2017/08/24/100912613e6cec14068195f189d0af4e612a0b.jpg","code":"0","groupId":0,"name":"颜色","id":6,"value":"红香槟"},{"img":"/2017/08/24/10091611a6844a41a99e8aaf3d997fc07d97d1.jpg","code":"0","groupId":0,"name":"颜色","id":7,"value":"咖啡黑"},{"img":"/2017/08/24/101139525ae14bd079cc4e38fe1907519f92a4.jpg","code":"0","groupId":0,"name":"颜色","id":8,"value":"蓝黑"},{"img":"/2017/08/24/101150590535268fa0202f74545f720c83d492.jpg","code":"0","groupId":0,"name":"颜色","id":9,"value":"香槟黑"},{"img":"/2017/08/24/101207a890f1042d1ffa40900c0f0821d881ff.jpg","code":"0","groupId":0,"name":"颜色","id":10,"value":"烟灰黑"},{"img":"/2017/08/24/101222acafdb145316e5727dbdb6bf9bcd2e37.jpg","code":"0","groupId":0,"name":"颜色","id":11,"value":"紫银"}],"firstRatio":0,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"260*490*800"}],"price":111,"recomScore":3800,"isTopical":false,"productSellType":"SPOT","id":169992,"brand":"ZENESSE","designerId":11221,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":7980,"secondRatio":0.2,"store":1,"designer":"爵尼诗","consults":0,"isCrowd":false,"minPrice":111,"name":"美国zenesse品牌拉杆箱罗马彩虹系列行李箱29寸","collectioned":0,"mark":1,"categoryId":1658},{"img":"/2017/08/24/090612d5185248051497e9ee97b9a787ddb179.jpg","originalPrice":1480,"promotionPrice":1480,"sellPrice":1480,"soonPrice":1480,"isCart":1,"flashPrice":100,"categoryName":"针织衫","colors":[{"img":"/2017/08/24/090609d5185248051497e9ee97b9a787ddb179.jpg","code":"020","groupId":0,"name":"颜色","id":90,"value":"黑色"}],"firstRatio":0,"sizes":[{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"04","groupId":0,"name":"尺码","id":29,"value":"XL码"}],"price":100,"recomScore":0,"isTopical":false,"productSellType":"SPOT","id":169973,"brand":"KATY HUANG STYLE","designerId":10472,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":1480,"secondRatio":0.2,"store":0,"designer":"黄芸芸","consults":0,"isCrowd":false,"minPrice":100,"name":"KATY HUANG STYLE 黄芸芸 立体暗条纹不规则花边黑色针织衫","collectioned":0,"mark":1,"categoryId":1590},{"img":"/2017/08/24/075723f14826e4c9c079b6b00531544de6c701.jpg","originalPrice":1399,"promotionPrice":1399,"sellPrice":1399,"soonPrice":1399,"isCart":1,"flashPrice":100,"categoryName":"连衣裙","colors":[{"img":"/2017/08/24/075720f14826e4c9c079b6b00531544de6c701.jpg","code":"119","groupId":0,"name":"颜色","id":109,"value":"印花"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"price":100,"recomScore":2600,"isTopical":false,"productSellType":"SPOT","id":169966,"brand":"DANDE","designerId":10063,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":1399,"secondRatio":0.2,"store":1,"designer":"Dandy(张丹丹）","consults":0,"isCrowd":false,"minPrice":100,"name":"单喜 张丹丹 印花长裙","collectioned":0,"mark":1,"categoryId":1588},{"img":"/2017/08/24/073914de939278d1cce37036661b3468252472.jpg","originalPrice":499,"promotionPrice":499,"sellPrice":499,"soonPrice":499,"isCart":1,"flashPrice":200,"categoryName":"半身裙","colors":[{"img":"/2017/08/24/073909de939278d1cce37036661b3468252472.jpg","code":"020","groupId":0,"name":"颜色","id":90,"value":"黑色"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"}],"price":200,"recomScore":2600,"isTopical":false,"productSellType":"SPOT","id":169961,"brand":"DANDE","designerId":10063,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":499,"secondRatio":0.2,"store":1,"designer":"Dandy(张丹丹）","consults":0,"isCrowd":false,"minPrice":200,"name":"单喜\t张丹丹 黑白半裙","collectioned":0,"mark":1,"categoryId":1684}],"startTimeStamp":1513746000000,"promotionScope":1,"name":"测试闪退","statusName":"已开抢","endTimeStamp":1513872000000,"id":106,"flashUrl":"/flashpromotion/product/session","startDate":"2017/12/20 13:00:00","brandPic":"/2017/12/20/03450589cb3d0bb1db73e54d15c5027e4ab271.jpg"}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<BrandsBean> brands;

        public List<BrandsBean> getBrands() {
            return brands;
        }

        public void setBrands(List<BrandsBean> brands) {
            this.brands = brands;
        }

        public static class BrandsBean {
            /**
             * promotionType : 1
             * endDate : 2017/12/22 00:00:00
             * session : 13:00
             * sessionName :
             * sessionRemark :
             * limitQuantity : 9999
             * remaining : 115164230
             * products : [{"img":"/2017/12/04/05281586e32bf6f6d175e2556eebecf74beef2.jpg","originalPrice":1000,"promotionPrice":1000,"sellPrice":1000,"soonPrice":1000,"isCart":1,"flashPrice":30,"categoryName":"眼镜","colors":[{"img":"/2017/12/04/0528077d884e0c262b58e00320bb99cb382931.png","code":"0","groupId":0,"name":"颜色","id":1,"value":"X"}],"firstRatio":0,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"X"}],"price":30,"recomScore":0,"isTopical":true,"productSellType":"SPOT","id":170122,"brand":"1011","designerId":10687,"comments":1,"isSpot":false,"grossRatio":1,"salePrice":1000,"secondRatio":0.2,"store":0,"designer":"武学凯","consults":0,"isCrowd":false,"minPrice":30,"name":"测试 201712041327","collectioned":0,"mark":1,"categoryId":1633},{"img":"/2017/12/01/074350c14c1d34f7878d42fff481ac24655b71.jpg","originalPrice":1000,"promotionPrice":1000,"sellPrice":1000,"soonPrice":1000,"isCart":1,"flashPrice":40,"categoryName":"高跟鞋","colors":[{"img":"/2017/12/01/0743487d884e0c262b58e00320bb99cb382931.png","code":"0","groupId":0,"name":"颜色","id":1,"value":"红色"}],"firstRatio":0.2,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"X"}],"price":40,"recomScore":0,"isTopical":false,"productSellType":"SPOT","id":170115,"brand":"1011","designerId":10687,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":1000,"secondRatio":0.3,"store":0,"designer":"武学凯","consults":0,"isCrowd":false,"minPrice":40,"name":"测试 test201712011543","collectioned":0,"mark":1,"categoryId":1672},{"img":"/2017/08/24/10150206bf0a8281886622eccb10b25f450c4b.jpg","originalPrice":7980,"promotionPrice":7980,"sellPrice":7980,"soonPrice":7980,"isCart":1,"flashPrice":111,"categoryName":"旅行包","colors":[{"img":"/2017/08/24/1008580e3d35fadfa29e9ca8aa59edce7e4d6a.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"粉银"},{"img":"/2017/08/24/10090106bf0a8281886622eccb10b25f450c4b.jpg","code":"0","groupId":0,"name":"颜色","id":2,"value":"黑红"},{"img":"/2017/08/24/10090393b679283a0d8d468e5939de2b702ff5.jpg","code":"0","groupId":0,"name":"颜色","id":3,"value":"黑蓝"},{"img":"/2017/08/24/1009064bef186c2375c1c469f6faa8e73eb446.jpg","code":"0","groupId":0,"name":"颜色","id":4,"value":"黑香槟"},{"img":"/2017/08/24/1009087000cd03ccb873112b41b2f44d571bfb.jpg","code":"0","groupId":0,"name":"颜色","id":5,"value":"黑银"},{"img":"/2017/08/24/100912613e6cec14068195f189d0af4e612a0b.jpg","code":"0","groupId":0,"name":"颜色","id":6,"value":"红香槟"},{"img":"/2017/08/24/10091611a6844a41a99e8aaf3d997fc07d97d1.jpg","code":"0","groupId":0,"name":"颜色","id":7,"value":"咖啡黑"},{"img":"/2017/08/24/101139525ae14bd079cc4e38fe1907519f92a4.jpg","code":"0","groupId":0,"name":"颜色","id":8,"value":"蓝黑"},{"img":"/2017/08/24/101150590535268fa0202f74545f720c83d492.jpg","code":"0","groupId":0,"name":"颜色","id":9,"value":"香槟黑"},{"img":"/2017/08/24/101207a890f1042d1ffa40900c0f0821d881ff.jpg","code":"0","groupId":0,"name":"颜色","id":10,"value":"烟灰黑"},{"img":"/2017/08/24/101222acafdb145316e5727dbdb6bf9bcd2e37.jpg","code":"0","groupId":0,"name":"颜色","id":11,"value":"紫银"}],"firstRatio":0,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"260*490*800"}],"price":111,"recomScore":3800,"isTopical":false,"productSellType":"SPOT","id":169992,"brand":"ZENESSE","designerId":11221,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":7980,"secondRatio":0.2,"store":1,"designer":"爵尼诗","consults":0,"isCrowd":false,"minPrice":111,"name":"美国zenesse品牌拉杆箱罗马彩虹系列行李箱29寸","collectioned":0,"mark":1,"categoryId":1658},{"img":"/2017/08/24/090612d5185248051497e9ee97b9a787ddb179.jpg","originalPrice":1480,"promotionPrice":1480,"sellPrice":1480,"soonPrice":1480,"isCart":1,"flashPrice":100,"categoryName":"针织衫","colors":[{"img":"/2017/08/24/090609d5185248051497e9ee97b9a787ddb179.jpg","code":"020","groupId":0,"name":"颜色","id":90,"value":"黑色"}],"firstRatio":0,"sizes":[{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"04","groupId":0,"name":"尺码","id":29,"value":"XL码"}],"price":100,"recomScore":0,"isTopical":false,"productSellType":"SPOT","id":169973,"brand":"KATY HUANG STYLE","designerId":10472,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":1480,"secondRatio":0.2,"store":0,"designer":"黄芸芸","consults":0,"isCrowd":false,"minPrice":100,"name":"KATY HUANG STYLE 黄芸芸 立体暗条纹不规则花边黑色针织衫","collectioned":0,"mark":1,"categoryId":1590},{"img":"/2017/08/24/075723f14826e4c9c079b6b00531544de6c701.jpg","originalPrice":1399,"promotionPrice":1399,"sellPrice":1399,"soonPrice":1399,"isCart":1,"flashPrice":100,"categoryName":"连衣裙","colors":[{"img":"/2017/08/24/075720f14826e4c9c079b6b00531544de6c701.jpg","code":"119","groupId":0,"name":"颜色","id":109,"value":"印花"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"price":100,"recomScore":2600,"isTopical":false,"productSellType":"SPOT","id":169966,"brand":"DANDE","designerId":10063,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":1399,"secondRatio":0.2,"store":1,"designer":"Dandy(张丹丹）","consults":0,"isCrowd":false,"minPrice":100,"name":"单喜 张丹丹 印花长裙","collectioned":0,"mark":1,"categoryId":1588},{"img":"/2017/08/24/073914de939278d1cce37036661b3468252472.jpg","originalPrice":499,"promotionPrice":499,"sellPrice":499,"soonPrice":499,"isCart":1,"flashPrice":200,"categoryName":"半身裙","colors":[{"img":"/2017/08/24/073909de939278d1cce37036661b3468252472.jpg","code":"020","groupId":0,"name":"颜色","id":90,"value":"黑色"}],"firstRatio":0,"sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"}],"price":200,"recomScore":2600,"isTopical":false,"productSellType":"SPOT","id":169961,"brand":"DANDE","designerId":10063,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":499,"secondRatio":0.2,"store":1,"designer":"Dandy(张丹丹）","consults":0,"isCrowd":false,"minPrice":200,"name":"单喜\t张丹丹 黑白半裙","collectioned":0,"mark":1,"categoryId":1684}]
             * startTimeStamp : 1513746000000
             * promotionScope : 1
             * name : 测试闪退
             * statusName : 已开抢
             * endTimeStamp : 1513872000000
             * id : 106
             * flashUrl : /flashpromotion/product/session
             * startDate : 2017/12/20 13:00:00
             * brandPic : /2017/12/20/03450589cb3d0bb1db73e54d15c5027e4ab271.jpg
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
            private List<FlashProductListBean.DataBean.ProductsBean.ListBean> products;

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

            public List<FlashProductListBean.DataBean.ProductsBean.ListBean> getProducts() {
                return products;
            }

            public void setProducts(List<FlashProductListBean.DataBean.ProductsBean.ListBean> products) {
                this.products = products;
            }

            public static class ProductsBean {
                /**
                 * img : /2017/12/04/05281586e32bf6f6d175e2556eebecf74beef2.jpg
                 * originalPrice : 1000
                 * promotionPrice : 1000
                 * sellPrice : 1000
                 * soonPrice : 1000
                 * isCart : 1
                 * flashPrice : 30
                 * categoryName : 眼镜
                 * colors : [{"img":"/2017/12/04/0528077d884e0c262b58e00320bb99cb382931.png","code":"0","groupId":0,"name":"颜色","id":1,"value":"X"}]
                 * firstRatio : 0
                 * sizes : [{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"X"}]
                 * price : 30
                 * recomScore : 0
                 * isTopical : true
                 * productSellType : SPOT
                 * id : 170122
                 * brand : 1011
                 * designerId : 10687
                 * comments : 1
                 * isSpot : false
                 * grossRatio : 1
                 * salePrice : 1000
                 * secondRatio : 0.2
                 * store : 0
                 * designer : 武学凯
                 * consults : 0
                 * isCrowd : false
                 * minPrice : 30
                 * name : 测试 201712041327
                 * collectioned : 0
                 * mark : 1
                 * categoryId : 1633
                 */

                private String img;
                private double flashPrice;
                private long id;
                private double salePrice;
                private int promotionId;
                private double originalPrice;

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public double getFlashPrice() {
                    return flashPrice;
                }

                public void setFlashPrice(double flashPrice) {
                    this.flashPrice = flashPrice;
                }

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public double getSalePrice() {
                    return salePrice;
                }

                public void setSalePrice(double salePrice) {
                    this.salePrice = salePrice;
                }

                public int getPromotionId() {
                    return promotionId;
                }

                public void setPromotionId(int promotionId) {
                    this.promotionId = promotionId;
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
