package com.d2cmall.buyer.bean;

import android.content.Intent;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2018/6/26.
 */

public class GroupListBean extends BaseBean {

    /**
     * data : {"collageList":{"next":false,"total":6,"previous":false,"index":1,"pageSize":20,"list":[{"sharePic":null,"product":{"img":"/product/12/100012/IMG_1229","originalPrice":609,"sellPrice":609,"soonPrice":609,"isCart":1,"flashPrice":0,"categoryName":"7分裤","colors":[{"img":"/sp/12/100012/6819e3393156457fceb6b464d82caf01","code":"058","groupId":"0","name":"颜色","id":"93","value":"绿色"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":609,"collagePrice":0,"recomScore":0,"mainPic":"/product/12/100012/IMG_1229","isTopical":false,"productSellType":"SPOT","id":null,"sn":"SZ233084014","brand":"玖拾玖度","designerId":10006,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":609,"secondRatio":0.24,"store":1,"designer":"张义超","isSubscribe":1,"consults":0,"collagePromotionId":17,"minPrice":609,"name":"玖拾玖度 张义超 休闲拼接抽带七分裤 99331010","collectioned":0,"maxPrice":609,"isAfter":1,"mark":1,"categoryId":1621},"shareContent":null,"shareTitle":null,"endDate":1530201600000,"memberCount":3,"id":17,"begainDate":1530115200000,"collagePromotionName":"测试库存，sku","promotionStatus":1},{"sharePic":null,"product":{"img":"/2018/06/2293c165a936add6a0e94d330fccb7fda1","originalPrice":250,"sellPrice":250,"soonPrice":250,"isCart":1,"flashPrice":0,"categoryName":"电脑包","colors":[{"img":"/2018/06/2293c165a936add6a0e94d330fccb7fda1","code":"0","groupId":0,"name":"【勿动】尺码","id":1,"value":"L"}],"firstRatio":0.01,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"【勿动】销售属性","id":1,"value":"肉粉色"}],"price":250,"collagePrice":0,"recomScore":0,"mainPic":"/2018/06/2293c165a936add6a0e94d330fccb7fda1","isTopical":false,"productSellType":"SPOT","id":null,"sn":"26765638","brand":"1011","designerId":10687,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":250,"secondRatio":0.01,"store":1,"designer":"朱超凡","isSubscribe":0,"consults":0,"collagePromotionId":11,"minPrice":250,"name":"迪奥唇膏-auto-20160912140241401","collectioned":0,"maxPrice":250,"isAfter":1,"mark":0,"categoryId":2203},"shareContent":null,"shareTitle":null,"endDate":1530288000000,"memberCount":6,"id":11,"begainDate":1529942400000,"collagePromotionName":"订单","promotionStatus":1},{"sharePic":null,"product":{"img":"/2018/04/14/07591796da29346d26de60f3b165a95a91a7d7.jpg","originalPrice":1199,"sellPrice":1199,"soonPrice":500,"isCart":1,"flashPrice":0,"categoryName":"连衣裙","colors":[{"img":"/2018/04/14/07591596da29346d26de60f3b165a95a91a7d7.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"印花"}],"promotionId":9127,"firstRatio":0.05,"partnerSales":0,"promotionTypeName":"特价","subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"均码"}],"price":500,"collagePrice":0,"recomScore":0,"mainPic":"/2018/04/14/07591796da29346d26de60f3b165a95a91a7d7.jpg","isTopical":false,"productSellType":"SPOT","id":null,"sn":"A86","brand":"amorebkk","designerId":11195,"promotionType":4,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":1199,"secondRatio":0.2,"store":1,"designer":"ploy","isSubscribe":0,"consults":0,"collagePromotionId":16,"minPrice":500,"name":"amorebkk ploy 泰国潮牌钉珠亮片网纱长裙两件套","collectioned":0,"maxPrice":1199,"isAfter":0,"mark":1,"categoryId":1588},"shareContent":null,"shareTitle":null,"endDate":1530201600000,"memberCount":3,"id":16,"begainDate":1530174600000,"collagePromotionName":"测试组团时间","promotionStatus":1},{"sharePic":null,"product":{"img":"/2018/04/15/133219afe2e247287fdbd245a2dabbb12d9100.jpg","originalPrice":3200,"sellPrice":800,"soonPrice":800,"isCart":1,"flashPrice":0,"categoryName":"斗篷","colors":[{"img":"/2018/04/15/132930afe2e247287fdbd245a2dabbb12d9100.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑白"}],"firstRatio":0.04,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"均码"}],"price":800,"collagePrice":123,"recomScore":11225,"mainPic":"/2018/04/15/133219afe2e247287fdbd245a2dabbb12d9100.jpg","isTopical":true,"productSellType":"CUSTOM","id":null,"sn":"sr3000","brand":"SI RAN","designerId":10669,"comments":17,"isSpot":false,"grossRatio":1,"salePrice":800,"secondRatio":0.18,"store":1,"designer":"思然","isSubscribe":0,"consults":0,"collagePromotionId":13,"minPrice":800,"name":"Si Ran 黑白纯羊毛斗篷","collectioned":0,"maxPrice":800,"isAfter":0,"mark":1,"categoryId":1754},"shareContent":null,"shareTitle":null,"endDate":1530288000000,"memberCount":6,"id":13,"begainDate":1530115200000,"collagePromotionName":"测试一下价格修改","promotionStatus":1},{"sharePic":null,"product":{"img":"/2018/04/14/080159ace35171cad316f77434b5f958f2adab.jpg","originalPrice":1380,"sellPrice":1380,"soonPrice":1380,"isCart":1,"flashPrice":0,"categoryName":"连衣裙","colors":[{"img":"/2018/04/14/080151ace35171cad316f77434b5f958f2adab.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"裸粉色"}],"firstRatio":0.03,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"S"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"M"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"L"}],"price":1380,"collagePrice":1280,"recomScore":5900,"mainPic":"/2018/04/14/080159ace35171cad316f77434b5f958f2adab.jpg","isTopical":false,"productSellType":"CUSTOM","id":null,"sn":"TRD18SS002D","brand":"SHUR RUITZ","designerId":10207,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":1380,"secondRatio":0.2,"store":1,"designer":"石芮子","isSubscribe":0,"consults":0,"collagePromotionId":14,"minPrice":1380,"name":"SHUR RUITZ 石芮子 麻缎亮面七分袖连衣裙","collectioned":0,"maxPrice":1380,"isAfter":1,"mark":1,"categoryId":1588},"shareContent":null,"shareTitle":null,"endDate":1530374400000,"memberCount":2,"id":14,"begainDate":1530028800000,"collagePromotionName":"新的拼团","promotionStatus":1},{"sharePic":null,"product":{"img":"/2018/04/13/02243312da45c02c6906be3f4a8bb7d07d800e.jpg","originalPrice":5880,"sellPrice":5880,"soonPrice":5880,"isCart":1,"flashPrice":0,"categoryName":"套装","colors":[{"img":"/2018/04/13/02242912da45c02c6906be3f4a8bb7d07d800e.jpg","code":"024","groupId":0,"name":"颜色","id":158,"value":"黑白"}],"firstRatio":0.03,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"price":5880,"recomScore":5000,"mainPic":"/2018/04/13/02243312da45c02c6906be3f4a8bb7d07d800e.jpg","isTopical":false,"productSellType":"CUSTOM","id":null,"sn":"JZ00481010002","brand":"ZHANGSHUAI","designerId":10053,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":5880,"secondRatio":0.16,"store":1,"designer":"张帅","isSubscribe":0,"consults":0,"minPrice":5880,"name":"ZHANGSHUAI 张帅 春装黑白拼色套装 女装","collectioned":0,"maxPrice":5880,"isAfter":1,"mark":1,"categoryId":1749},"shareContent":null,"shareTitle":null,"endDate":1530288000000,"memberCount":2,"id":15,"begainDate":1530028800000,"collagePromotionName":"我是一个伟大的平团","promotionStatus":1}]}}
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
         * collageList : {"next":false,"total":6,"previous":false,"index":1,"pageSize":20,"list":[{"sharePic":null,"product":{"img":"/product/12/100012/IMG_1229","originalPrice":609,"sellPrice":609,"soonPrice":609,"isCart":1,"flashPrice":0,"categoryName":"7分裤","colors":[{"img":"/sp/12/100012/6819e3393156457fceb6b464d82caf01","code":"058","groupId":"0","name":"颜色","id":"93","value":"绿色"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":609,"collagePrice":0,"recomScore":0,"mainPic":"/product/12/100012/IMG_1229","isTopical":false,"productSellType":"SPOT","id":null,"sn":"SZ233084014","brand":"玖拾玖度","designerId":10006,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":609,"secondRatio":0.24,"store":1,"designer":"张义超","isSubscribe":1,"consults":0,"collagePromotionId":17,"minPrice":609,"name":"玖拾玖度 张义超 休闲拼接抽带七分裤 99331010","collectioned":0,"maxPrice":609,"isAfter":1,"mark":1,"categoryId":1621},"shareContent":null,"shareTitle":null,"endDate":1530201600000,"memberCount":3,"id":17,"begainDate":1530115200000,"collagePromotionName":"测试库存，sku","promotionStatus":1},{"sharePic":null,"product":{"img":"/2018/06/2293c165a936add6a0e94d330fccb7fda1","originalPrice":250,"sellPrice":250,"soonPrice":250,"isCart":1,"flashPrice":0,"categoryName":"电脑包","colors":[{"img":"/2018/06/2293c165a936add6a0e94d330fccb7fda1","code":"0","groupId":0,"name":"【勿动】尺码","id":1,"value":"L"}],"firstRatio":0.01,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"【勿动】销售属性","id":1,"value":"肉粉色"}],"price":250,"collagePrice":0,"recomScore":0,"mainPic":"/2018/06/2293c165a936add6a0e94d330fccb7fda1","isTopical":false,"productSellType":"SPOT","id":null,"sn":"26765638","brand":"1011","designerId":10687,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":250,"secondRatio":0.01,"store":1,"designer":"朱超凡","isSubscribe":0,"consults":0,"collagePromotionId":11,"minPrice":250,"name":"迪奥唇膏-auto-20160912140241401","collectioned":0,"maxPrice":250,"isAfter":1,"mark":0,"categoryId":2203},"shareContent":null,"shareTitle":null,"endDate":1530288000000,"memberCount":6,"id":11,"begainDate":1529942400000,"collagePromotionName":"订单","promotionStatus":1},{"sharePic":null,"product":{"img":"/2018/04/14/07591796da29346d26de60f3b165a95a91a7d7.jpg","originalPrice":1199,"sellPrice":1199,"soonPrice":500,"isCart":1,"flashPrice":0,"categoryName":"连衣裙","colors":[{"img":"/2018/04/14/07591596da29346d26de60f3b165a95a91a7d7.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"印花"}],"promotionId":9127,"firstRatio":0.05,"partnerSales":0,"promotionTypeName":"特价","subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"均码"}],"price":500,"collagePrice":0,"recomScore":0,"mainPic":"/2018/04/14/07591796da29346d26de60f3b165a95a91a7d7.jpg","isTopical":false,"productSellType":"SPOT","id":null,"sn":"A86","brand":"amorebkk","designerId":11195,"promotionType":4,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":1199,"secondRatio":0.2,"store":1,"designer":"ploy","isSubscribe":0,"consults":0,"collagePromotionId":16,"minPrice":500,"name":"amorebkk ploy 泰国潮牌钉珠亮片网纱长裙两件套","collectioned":0,"maxPrice":1199,"isAfter":0,"mark":1,"categoryId":1588},"shareContent":null,"shareTitle":null,"endDate":1530201600000,"memberCount":3,"id":16,"begainDate":1530174600000,"collagePromotionName":"测试组团时间","promotionStatus":1},{"sharePic":null,"product":{"img":"/2018/04/15/133219afe2e247287fdbd245a2dabbb12d9100.jpg","originalPrice":3200,"sellPrice":800,"soonPrice":800,"isCart":1,"flashPrice":0,"categoryName":"斗篷","colors":[{"img":"/2018/04/15/132930afe2e247287fdbd245a2dabbb12d9100.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑白"}],"firstRatio":0.04,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"均码"}],"price":800,"collagePrice":123,"recomScore":11225,"mainPic":"/2018/04/15/133219afe2e247287fdbd245a2dabbb12d9100.jpg","isTopical":true,"productSellType":"CUSTOM","id":null,"sn":"sr3000","brand":"SI RAN","designerId":10669,"comments":17,"isSpot":false,"grossRatio":1,"salePrice":800,"secondRatio":0.18,"store":1,"designer":"思然","isSubscribe":0,"consults":0,"collagePromotionId":13,"minPrice":800,"name":"Si Ran 黑白纯羊毛斗篷","collectioned":0,"maxPrice":800,"isAfter":0,"mark":1,"categoryId":1754},"shareContent":null,"shareTitle":null,"endDate":1530288000000,"memberCount":6,"id":13,"begainDate":1530115200000,"collagePromotionName":"测试一下价格修改","promotionStatus":1},{"sharePic":null,"product":{"img":"/2018/04/14/080159ace35171cad316f77434b5f958f2adab.jpg","originalPrice":1380,"sellPrice":1380,"soonPrice":1380,"isCart":1,"flashPrice":0,"categoryName":"连衣裙","colors":[{"img":"/2018/04/14/080151ace35171cad316f77434b5f958f2adab.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"裸粉色"}],"firstRatio":0.03,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"S"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"M"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"L"}],"price":1380,"collagePrice":1280,"recomScore":5900,"mainPic":"/2018/04/14/080159ace35171cad316f77434b5f958f2adab.jpg","isTopical":false,"productSellType":"CUSTOM","id":null,"sn":"TRD18SS002D","brand":"SHUR RUITZ","designerId":10207,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":1380,"secondRatio":0.2,"store":1,"designer":"石芮子","isSubscribe":0,"consults":0,"collagePromotionId":14,"minPrice":1380,"name":"SHUR RUITZ 石芮子 麻缎亮面七分袖连衣裙","collectioned":0,"maxPrice":1380,"isAfter":1,"mark":1,"categoryId":1588},"shareContent":null,"shareTitle":null,"endDate":1530374400000,"memberCount":2,"id":14,"begainDate":1530028800000,"collagePromotionName":"新的拼团","promotionStatus":1},{"sharePic":null,"product":{"img":"/2018/04/13/02243312da45c02c6906be3f4a8bb7d07d800e.jpg","originalPrice":5880,"sellPrice":5880,"soonPrice":5880,"isCart":1,"flashPrice":0,"categoryName":"套装","colors":[{"img":"/2018/04/13/02242912da45c02c6906be3f4a8bb7d07d800e.jpg","code":"024","groupId":0,"name":"颜色","id":158,"value":"黑白"}],"firstRatio":0.03,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"price":5880,"recomScore":5000,"mainPic":"/2018/04/13/02243312da45c02c6906be3f4a8bb7d07d800e.jpg","isTopical":false,"productSellType":"CUSTOM","id":null,"sn":"JZ00481010002","brand":"ZHANGSHUAI","designerId":10053,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":5880,"secondRatio":0.16,"store":1,"designer":"张帅","isSubscribe":0,"consults":0,"minPrice":5880,"name":"ZHANGSHUAI 张帅 春装黑白拼色套装 女装","collectioned":0,"maxPrice":5880,"isAfter":1,"mark":1,"categoryId":1749},"shareContent":null,"shareTitle":null,"endDate":1530288000000,"memberCount":2,"id":15,"begainDate":1530028800000,"collagePromotionName":"我是一个伟大的平团","promotionStatus":1}]}
         */

        private CollageListBean collageList;

        public CollageListBean getCollageList() {
            return collageList;
        }

        public void setCollageList(CollageListBean collageList) {
            this.collageList = collageList;
        }

        public static class CollageListBean {
            /**
             * next : false
             * total : 6
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"sharePic":null,"product":{"img":"/product/12/100012/IMG_1229","originalPrice":609,"sellPrice":609,"soonPrice":609,"isCart":1,"flashPrice":0,"categoryName":"7分裤","colors":[{"img":"/sp/12/100012/6819e3393156457fceb6b464d82caf01","code":"058","groupId":"0","name":"颜色","id":"93","value":"绿色"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":609,"collagePrice":0,"recomScore":0,"mainPic":"/product/12/100012/IMG_1229","isTopical":false,"productSellType":"SPOT","id":null,"sn":"SZ233084014","brand":"玖拾玖度","designerId":10006,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":609,"secondRatio":0.24,"store":1,"designer":"张义超","isSubscribe":1,"consults":0,"collagePromotionId":17,"minPrice":609,"name":"玖拾玖度 张义超 休闲拼接抽带七分裤 99331010","collectioned":0,"maxPrice":609,"isAfter":1,"mark":1,"categoryId":1621},"shareContent":null,"shareTitle":null,"endDate":1530201600000,"memberCount":3,"id":17,"begainDate":1530115200000,"collagePromotionName":"测试库存，sku","promotionStatus":1},{"sharePic":null,"product":{"img":"/2018/06/2293c165a936add6a0e94d330fccb7fda1","originalPrice":250,"sellPrice":250,"soonPrice":250,"isCart":1,"flashPrice":0,"categoryName":"电脑包","colors":[{"img":"/2018/06/2293c165a936add6a0e94d330fccb7fda1","code":"0","groupId":0,"name":"【勿动】尺码","id":1,"value":"L"}],"firstRatio":0.01,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"【勿动】销售属性","id":1,"value":"肉粉色"}],"price":250,"collagePrice":0,"recomScore":0,"mainPic":"/2018/06/2293c165a936add6a0e94d330fccb7fda1","isTopical":false,"productSellType":"SPOT","id":null,"sn":"26765638","brand":"1011","designerId":10687,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":250,"secondRatio":0.01,"store":1,"designer":"朱超凡","isSubscribe":0,"consults":0,"collagePromotionId":11,"minPrice":250,"name":"迪奥唇膏-auto-20160912140241401","collectioned":0,"maxPrice":250,"isAfter":1,"mark":0,"categoryId":2203},"shareContent":null,"shareTitle":null,"endDate":1530288000000,"memberCount":6,"id":11,"begainDate":1529942400000,"collagePromotionName":"订单","promotionStatus":1},{"sharePic":null,"product":{"img":"/2018/04/14/07591796da29346d26de60f3b165a95a91a7d7.jpg","originalPrice":1199,"sellPrice":1199,"soonPrice":500,"isCart":1,"flashPrice":0,"categoryName":"连衣裙","colors":[{"img":"/2018/04/14/07591596da29346d26de60f3b165a95a91a7d7.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"印花"}],"promotionId":9127,"firstRatio":0.05,"partnerSales":0,"promotionTypeName":"特价","subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"均码"}],"price":500,"collagePrice":0,"recomScore":0,"mainPic":"/2018/04/14/07591796da29346d26de60f3b165a95a91a7d7.jpg","isTopical":false,"productSellType":"SPOT","id":null,"sn":"A86","brand":"amorebkk","designerId":11195,"promotionType":4,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":1199,"secondRatio":0.2,"store":1,"designer":"ploy","isSubscribe":0,"consults":0,"collagePromotionId":16,"minPrice":500,"name":"amorebkk ploy 泰国潮牌钉珠亮片网纱长裙两件套","collectioned":0,"maxPrice":1199,"isAfter":0,"mark":1,"categoryId":1588},"shareContent":null,"shareTitle":null,"endDate":1530201600000,"memberCount":3,"id":16,"begainDate":1530174600000,"collagePromotionName":"测试组团时间","promotionStatus":1},{"sharePic":null,"product":{"img":"/2018/04/15/133219afe2e247287fdbd245a2dabbb12d9100.jpg","originalPrice":3200,"sellPrice":800,"soonPrice":800,"isCart":1,"flashPrice":0,"categoryName":"斗篷","colors":[{"img":"/2018/04/15/132930afe2e247287fdbd245a2dabbb12d9100.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑白"}],"firstRatio":0.04,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"均码"}],"price":800,"collagePrice":123,"recomScore":11225,"mainPic":"/2018/04/15/133219afe2e247287fdbd245a2dabbb12d9100.jpg","isTopical":true,"productSellType":"CUSTOM","id":null,"sn":"sr3000","brand":"SI RAN","designerId":10669,"comments":17,"isSpot":false,"grossRatio":1,"salePrice":800,"secondRatio":0.18,"store":1,"designer":"思然","isSubscribe":0,"consults":0,"collagePromotionId":13,"minPrice":800,"name":"Si Ran 黑白纯羊毛斗篷","collectioned":0,"maxPrice":800,"isAfter":0,"mark":1,"categoryId":1754},"shareContent":null,"shareTitle":null,"endDate":1530288000000,"memberCount":6,"id":13,"begainDate":1530115200000,"collagePromotionName":"测试一下价格修改","promotionStatus":1},{"sharePic":null,"product":{"img":"/2018/04/14/080159ace35171cad316f77434b5f958f2adab.jpg","originalPrice":1380,"sellPrice":1380,"soonPrice":1380,"isCart":1,"flashPrice":0,"categoryName":"连衣裙","colors":[{"img":"/2018/04/14/080151ace35171cad316f77434b5f958f2adab.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"裸粉色"}],"firstRatio":0.03,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"S"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"M"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"L"}],"price":1380,"collagePrice":1280,"recomScore":5900,"mainPic":"/2018/04/14/080159ace35171cad316f77434b5f958f2adab.jpg","isTopical":false,"productSellType":"CUSTOM","id":null,"sn":"TRD18SS002D","brand":"SHUR RUITZ","designerId":10207,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":1380,"secondRatio":0.2,"store":1,"designer":"石芮子","isSubscribe":0,"consults":0,"collagePromotionId":14,"minPrice":1380,"name":"SHUR RUITZ 石芮子 麻缎亮面七分袖连衣裙","collectioned":0,"maxPrice":1380,"isAfter":1,"mark":1,"categoryId":1588},"shareContent":null,"shareTitle":null,"endDate":1530374400000,"memberCount":2,"id":14,"begainDate":1530028800000,"collagePromotionName":"新的拼团","promotionStatus":1},{"sharePic":null,"product":{"img":"/2018/04/13/02243312da45c02c6906be3f4a8bb7d07d800e.jpg","originalPrice":5880,"sellPrice":5880,"soonPrice":5880,"isCart":1,"flashPrice":0,"categoryName":"套装","colors":[{"img":"/2018/04/13/02242912da45c02c6906be3f4a8bb7d07d800e.jpg","code":"024","groupId":0,"name":"颜色","id":158,"value":"黑白"}],"firstRatio":0.03,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"price":5880,"recomScore":5000,"mainPic":"/2018/04/13/02243312da45c02c6906be3f4a8bb7d07d800e.jpg","isTopical":false,"productSellType":"CUSTOM","id":null,"sn":"JZ00481010002","brand":"ZHANGSHUAI","designerId":10053,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":5880,"secondRatio":0.16,"store":1,"designer":"张帅","isSubscribe":0,"consults":0,"minPrice":5880,"name":"ZHANGSHUAI 张帅 春装黑白拼色套装 女装","collectioned":0,"maxPrice":5880,"isAfter":1,"mark":1,"categoryId":1749},"shareContent":null,"shareTitle":null,"endDate":1530288000000,"memberCount":2,"id":15,"begainDate":1530028800000,"collagePromotionName":"我是一个伟大的平团","promotionStatus":1}]
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
                 * sharePic : null
                 * product : {"img":"/product/12/100012/IMG_1229","originalPrice":609,"sellPrice":609,"soonPrice":609,"isCart":1,"flashPrice":0,"categoryName":"7分裤","colors":[{"img":"/sp/12/100012/6819e3393156457fceb6b464d82caf01","code":"058","groupId":"0","name":"颜色","id":"93","value":"绿色"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}],"price":609,"collagePrice":0,"recomScore":0,"mainPic":"/product/12/100012/IMG_1229","isTopical":false,"productSellType":"SPOT","id":null,"sn":"SZ233084014","brand":"玖拾玖度","designerId":10006,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":609,"secondRatio":0.24,"store":1,"designer":"张义超","isSubscribe":1,"consults":0,"collagePromotionId":17,"minPrice":609,"name":"玖拾玖度 张义超 休闲拼接抽带七分裤 99331010","collectioned":0,"maxPrice":609,"isAfter":1,"mark":1,"categoryId":1621}
                 * shareContent : null
                 * shareTitle : null
                 * endDate : 1530201600000
                 * memberCount : 3
                 * id : 17
                 * begainDate : 1530115200000
                 * collagePromotionName : 测试库存，sku
                 * promotionStatus : 1
                 */

                private String sharePic;
                private ProductBean product;
                private String shareContent;
                private String shareTitle;
                private long endDate;
                private int memberCount;
                private int id;
                private long begainDate;
                private String collagePromotionName;
                private int promotionStatus;

                public String getSharePic() {
                    return sharePic;
                }

                public void setSharePic(String sharePic) {
                    this.sharePic = sharePic;
                }

                public ProductBean getProduct() {
                    return product;
                }

                public void setProduct(ProductBean product) {
                    this.product = product;
                }

                public String getShareContent() {
                    return shareContent;
                }

                public void setShareContent(String shareContent) {
                    this.shareContent = shareContent;
                }

                public String getShareTitle() {
                    return shareTitle;
                }

                public void setShareTitle(String shareTitle) {
                    this.shareTitle = shareTitle;
                }

                public long getEndDate() {
                    return endDate;
                }

                public void setEndDate(long endDate) {
                    this.endDate = endDate;
                }

                public int getMemberCount() {
                    return memberCount;
                }

                public void setMemberCount(int memberCount) {
                    this.memberCount = memberCount;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public long getBegainDate() {
                    return begainDate;
                }

                public void setBegainDate(long begainDate) {
                    this.begainDate = begainDate;
                }

                public String getCollagePromotionName() {
                    return collagePromotionName;
                }

                public void setCollagePromotionName(String collagePromotionName) {
                    this.collagePromotionName = collagePromotionName;
                }

                public int getPromotionStatus() {
                    return promotionStatus;
                }

                public void setPromotionStatus(int promotionStatus) {
                    this.promotionStatus = promotionStatus;
                }

                public static class ProductBean {
                    /**
                     * img : /product/12/100012/IMG_1229
                     * originalPrice : 609.0
                     * sellPrice : 609.0
                     * soonPrice : 609.0
                     * isCart : 1
                     * flashPrice : 0.0
                     * categoryName : 7分裤
                     * colors : [{"img":"/sp/12/100012/6819e3393156457fceb6b464d82caf01","code":"058","groupId":"0","name":"颜色","id":"93","value":"绿色"}]
                     * firstRatio : 0.05
                     * partnerSales : 0
                     * subTitle :
                     * sizes : [{"img":"","code":"01","groupId":"0","name":"尺码","id":"26","value":"S码"},{"img":"","code":"02","groupId":"0","name":"尺码","id":"27","value":"M码"},{"img":"","code":"03","groupId":"0","name":"尺码","id":"28","value":"L码"},{"img":"","code":"04","groupId":"0","name":"尺码","id":"29","value":"XL码"}]
                     * price : 609.0
                     * collagePrice : 0.0
                     * recomScore : 0.0
                     * mainPic : /product/12/100012/IMG_1229
                     * isTopical : false
                     * productSellType : SPOT
                     * id : null
                     * sn : SZ233084014
                     * brand : 玖拾玖度
                     * designerId : 10006
                     * comments : 0
                     * isSpot : false
                     * grossRatio : 1.0
                     * salePrice : 609.0
                     * secondRatio : 0.24
                     * store : 1
                     * designer : 张义超
                     * isSubscribe : 1
                     * consults : 0
                     * collagePromotionId : 17
                     * minPrice : 609.0
                     * name : 玖拾玖度 张义超 休闲拼接抽带七分裤 99331010
                     * collectioned : 0
                     * maxPrice : 609.0
                     * isAfter : 1
                     * mark : 1
                     * categoryId : 1621
                     */

                    private String img;
                    private double originalPrice;
                    private double sellPrice;
                    private double soonPrice;
                    private int isCart;
                    private double flashPrice;
                    private String categoryName;
                    private double firstRatio;
                    private int partnerSales;
                    private String subTitle;
                    private double price;
                    private double collagePrice;
                    private double recomScore;
                    private String mainPic;
                    private boolean isTopical;
                    private String productSellType;
                    private Integer id;
                    private String sn;
                    private String brand;
                    private int designerId;
                    private int comments;
                    private boolean isSpot;
                    private double grossRatio;
                    private double salePrice;
                    private double secondRatio;
                    private int store;
                    private String designer;
                    private int isSubscribe;
                    private int consults;
                    private int collagePromotionId;
                    private double minPrice;
                    private String name;
                    private int collectioned;
                    private double maxPrice;
                    private int isAfter;
                    private int mark;
                    private int categoryId;
                    private List<ColorsBean> colors;
                    private List<SizesBean> sizes;

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

                    public int getPartnerSales() {
                        return partnerSales;
                    }

                    public void setPartnerSales(int partnerSales) {
                        this.partnerSales = partnerSales;
                    }

                    public String getSubTitle() {
                        return subTitle;
                    }

                    public void setSubTitle(String subTitle) {
                        this.subTitle = subTitle;
                    }

                    public double getPrice() {
                        return price;
                    }

                    public void setPrice(double price) {
                        this.price = price;
                    }

                    public double getCollagePrice() {
                        return collagePrice;
                    }

                    public void setCollagePrice(double collagePrice) {
                        this.collagePrice = collagePrice;
                    }

                    public double getRecomScore() {
                        return recomScore;
                    }

                    public void setRecomScore(double recomScore) {
                        this.recomScore = recomScore;
                    }

                    public String getMainPic() {
                        return mainPic;
                    }

                    public void setMainPic(String mainPic) {
                        this.mainPic = mainPic;
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

                    public Integer getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getSn() {
                        return sn;
                    }

                    public void setSn(String sn) {
                        this.sn = sn;
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

                    public int getIsSubscribe() {
                        return isSubscribe;
                    }

                    public void setIsSubscribe(int isSubscribe) {
                        this.isSubscribe = isSubscribe;
                    }

                    public int getConsults() {
                        return consults;
                    }

                    public void setConsults(int consults) {
                        this.consults = consults;
                    }

                    public int getCollagePromotionId() {
                        return collagePromotionId;
                    }

                    public void setCollagePromotionId(int collagePromotionId) {
                        this.collagePromotionId = collagePromotionId;
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

                    public double getMaxPrice() {
                        return maxPrice;
                    }

                    public void setMaxPrice(double maxPrice) {
                        this.maxPrice = maxPrice;
                    }

                    public int getIsAfter() {
                        return isAfter;
                    }

                    public void setIsAfter(int isAfter) {
                        this.isAfter = isAfter;
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
                         * img : /sp/12/100012/6819e3393156457fceb6b464d82caf01
                         * code : 058
                         * groupId : 0
                         * name : 颜色
                         * id : 93
                         * value : 绿色
                         */

                        private String img;
                        private String code;
                        private String groupId;
                        private String name;
                        private String id;
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

                        public String getGroupId() {
                            return groupId;
                        }

                        public void setGroupId(String groupId) {
                            this.groupId = groupId;
                        }

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public String getId() {
                            return id;
                        }

                        public void setId(String id) {
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
                         * code : 01
                         * groupId : 0
                         * name : 尺码
                         * id : 26
                         * value : S码
                         */

                        private String img;
                        private String code;
                        private String groupId;
                        private String name;
                        private String id;
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

                        public String getGroupId() {
                            return groupId;
                        }

                        public void setGroupId(String groupId) {
                            this.groupId = groupId;
                        }

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public String getId() {
                            return id;
                        }

                        public void setId(String id) {
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
}
