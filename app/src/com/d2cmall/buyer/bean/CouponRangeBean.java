package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/8/7.
 * Description : CouponRangeBean
 */
//优惠券的适用范围,后台给不了具体优惠券使用商品或店铺,所以相当于数据是合并的
public class CouponRangeBean extends BaseBean {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private CouponBean coupon;

        private BrandsBean brands;
        /**
         * products : {"next":true,"total":63,"previous":false,"index":1,"pageSize":20,"list":[{"soonPromotion":{},"img":"/2018/08/01/085749372c32a0730ca6cc3998f53579e151c4.png","originalPrice":2,"sellPrice":2,"soonPrice":2,"isCart":1,"flashPrice":0,"categoryName":"体重秤","colors":[{"img":"/2018/08/01/085733372c32a0730ca6cc3998f53579e151c4.png","code":0,"groupId":0,"name":"颜色","id":1,"value":"自定义"}],"firstRatio":0.3,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":0,"groupId":0,"name":"尺码","id":1,"value":"自定义"}],"productTradeType":"COMMON","price":2,"collagePrice":0,"recomScore":16100,"mainPic":"/2018/08/01/085749372c32a0730ca6cc3998f53579e151c4.png","isTopical":true,"productSellType":"SPOT","id":195528,"sn":"223423","brand":"Awaylee","designerId":10061,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":2,"secondRatio":0.3,"store":1,"designer":"李薇","isSubscribe":0,"consults":0,"minPrice":2,"name":"2","collectioned":0,"maxPrice":2,"isAfter":1,"mark":1,"categoryId":2211},{"soonPromotion":{},"img":"/2018/07/17/23743f3457d9fcbacc9f193336d55497","originalPrice":599,"sellPrice":500,"soonPrice":500,"isCart":1,"flashPrice":0,"categoryName":"移动电源","colors":[{"img":"/2018/07/17/23743f3457d9fcbacc9f193336d55497","code":0,"groupId":0,"name":"颜色","id":1,"value":"默认"}],"firstRatio":0.1,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":0,"groupId":0,"name":"尺码","id":1,"value":"默认"}],"productTradeType":"CROSS","price":500,"collagePrice":0,"recomScore":13850,"mainPic":"/2018/07/17/23743f3457d9fcbacc9f193336d55497","isTopical":true,"productSellType":"SPOT","id":195407,"sn":"51828965","brand":"考拉品牌001","designerId":11442,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":500,"secondRatio":0.1,"store":1,"designer":"考拉设计师","isSubscribe":1,"consults":0,"minPrice":500,"name":"考啊","collectioned":0,"maxPrice":500,"isAfter":1,"mark":1,"categoryId":2213},{"soonPromotion":{},"img":"/2018/07/17/93c165a936add6a0e94d330fccb7fda1","originalPrice":0.01,"sellPrice":0.01,"soonPrice":0.01,"isCart":1,"flashPrice":0,"categoryName":"灯","colors":[{"img":"/2018/07/17/93c165a936add6a0e94d330fccb7fda1","code":"0","groupId":0,"name":"颜色","id":0,"value":"默认"}],"firstRatio":0.01,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":0,"value":"默认"}],"productTradeType":"CROSS","price":0.01,"collagePrice":0,"recomScore":13700,"mainPic":"/2018/07/17/93c165a936add6a0e94d330fccb7fda1","isTopical":true,"productSellType":"SPOT","id":195399,"sn":"58726790","brand":"1011","designerId":10687,"comments":0,"isSpot":true,"grossRatio":0.01,"salePrice":0.01,"secondRatio":0.01,"store":1,"designer":"逝月","isSubscribe":0,"consults":0,"minPrice":0.01,"name":"kua-auto-20170331211257552","collectioned":0,"maxPrice":0.01,"isAfter":1,"mark":1,"categoryId":2212},{"soonPromotion":{},"img":"/2018/07/10/0803422092b6f21284894ab426fcba6b3445dc.jpg","originalPrice":0.01,"sellPrice":0.01,"soonPrice":0.01,"isCart":1,"flashPrice":2,"categoryName":"水果饮料啦","colors":[{"img":"/2018/07/10/0803392092b6f21284894ab426fcba6b3445dc.jpg","code":0,"groupId":0,"name":"蓝精灵主","id":1,"value":"自定义"}],"firstRatio":0.1,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":0,"groupId":0,"name":"蓝精灵副","id":1,"value":"自定义"}],"productTradeType":"COMMON","price":0.01,"collagePrice":0.01,"recomScore":12800,"mainPic":"/2018/07/10/0803422092b6f21284894ab426fcba6b3445dc.jpg","isTopical":true,"productSellType":"SPOT","id":195398,"sn":"201807101603","brand":"10x1","designerId":10617,"comments":0,"isSpot":true,"grossRatio":0.1,"salePrice":0.01,"secondRatio":0.1,"store":1,"designer":"时晨昳","isSubscribe":0,"consults":0,"minPrice":0.01,"name":"测试201807101603","collectioned":0,"maxPrice":0.01,"isAfter":1,"mark":1,"categoryId":2272},{"soonPromotion":{},"img":"/2018/06/25/0347314046ec1cbd9700b224a01e6449deadc1.png","originalPrice":2,"sellPrice":2,"soonPrice":2,"isCart":1,"flashPrice":0,"categoryName":"运动鞋","colors":[{"img":"/2018/06/25/0347184d30825a147a0b6fdcbfaceedadf4127.png","code":0,"groupId":0,"name":"颜色","id":1,"value":"自定义"}],"firstRatio":0.2,"partnerSales":2,"subTitle":"","sizes":[{"img":"","code":0,"groupId":0,"name":"尺码","id":1,"value":"自定义"}],"productTradeType":"COMMON","price":2,"collagePrice":0,"recomScore":12552,"mainPic":"/2018/06/25/0347314046ec1cbd9700b224a01e6449deadc1.png","isTopical":true,"productSellType":"SPOT","id":195395,"sn":"2","brand":"ZHANGSHUAI","designerId":10053,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":2,"secondRatio":0.3,"store":1,"designer":"张帅","isSubscribe":0,"consults":0,"minPrice":2,"name":"2","collectioned":0,"maxPrice":2,"isAfter":1,"mark":1,"categoryId":2188},{"soonPromotion":{},"img":"","originalPrice":310,"sellPrice":310,"soonPrice":310,"isCart":1,"flashPrice":30,"categoryName":"耳机耳麦","colors":[{"img":"/2018/06/2693c165a936add6a0e94d330fccb7fda1","code":"0","groupId":0,"name":"【勿动】尺码","id":1,"value":"L"}],"firstRatio":0.01,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"【勿动】销售属性","id":1,"value":"蓝色"}],"productTradeType":"CROSS","price":310,"collagePrice":0,"recomScore":10100,"mainPic":"","isTopical":false,"productSellType":"SPOT","id":195393,"sn":"58735682","brand":"1011","designerId":10687,"comments":0,"isSpot":false,"grossRatio":0.01,"salePrice":310,"secondRatio":0.01,"store":1,"designer":"逝月","isSubscribe":0,"consults":0,"minPrice":310,"name":"迪奥唇膏-auto-20170425193245156","collectioned":0,"maxPrice":310,"isAfter":1,"mark":1,"categoryId":2214},{"soonPromotion":{},"img":"/2018/06/2623743f3457d9fcbacc9f193336d55497","originalPrice":599,"sellPrice":599,"soonPrice":599,"isCart":1,"flashPrice":100,"categoryName":"腰包","colors":[{"img":"/2018/06/2623743f3457d9fcbacc9f193336d55497","code":"0","groupId":0,"name":"【勿动】销售属性","id":1,"value":"棕色"}],"firstRatio":0.01,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"【勿动】销售属性","id":1,"value":"默认"}],"productTradeType":"CROSS","price":599,"collagePrice":0,"recomScore":10100,"mainPic":"/2018/06/2623743f3457d9fcbacc9f193336d55497","isTopical":true,"productSellType":"SPOT","id":195394,"sn":"51841203","brand":"1011","designerId":10687,"comments":0,"isSpot":false,"grossRatio":0.01,"salePrice":599,"secondRatio":0.01,"store":1,"designer":"逝月","isSubscribe":0,"consults":0,"minPrice":599,"name":"迪奥唇膏-auto-20161106142718177","collectioned":0,"maxPrice":599,"isAfter":1,"mark":1,"categoryId":2204},{"soonPromotion":{},"img":"/2018/06/1593c165a936add6a0e94d330fccb7fda1","originalPrice":270,"sellPrice":270,"soonPrice":270,"isCart":1,"flashPrice":0,"categoryName":"笔筒","colors":[{"img":"/2018/06/1593c165a936add6a0e94d330fccb7fda1","code":0,"groupId":0,"name":"笔筒测试","id":1,"value":"A"}],"firstRatio":0.01,"partnerSales":0,"subTitle":"","sizes":[],"productTradeType":"CROSS","price":270,"collagePrice":0,"recomScore":9050,"mainPic":"/2018/06/1593c165a936add6a0e94d330fccb7fda1","isTopical":true,"productSellType":"SPOT","id":195390,"sn":"58735570","brand":"1011","designerId":10687,"comments":0,"isSpot":false,"grossRatio":0.01,"salePrice":270,"secondRatio":0.01,"store":1,"designer":"逝月","isSubscribe":0,"consults":0,"minPrice":270,"name":"【勿动】单元测试专用","collectioned":0,"maxPrice":270,"isAfter":1,"mark":1,"categoryId":2270},{"soonPromotion":{},"img":"/model/1601/842719b74c6a52dcd758b50eb2332f47","originalPrice":1999,"sellPrice":1199,"soonPrice":1199,"isCart":0,"flashPrice":599,"categoryName":"大衣","colors":[{"img":"/model/1601/842719b74c6a52dcd758b50eb2332f47","code":"138","groupId":0,"name":"颜色","id":238,"value":"藏蓝"}],"firstRatio":0.2,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"productTradeType":"COMMON","price":1199,"collagePrice":0,"recomScore":6476,"mainPic":"/model/1601/842719b74c6a52dcd758b50eb2332f47","isTopical":true,"productSellType":"SPOT","id":113655,"sn":"L00753220003","brand":"Awaylee","designerId":10061,"comments":116,"isSpot":true,"grossRatio":1,"salePrice":1199,"secondRatio":0.05,"store":1,"designer":"李薇","isSubscribe":0,"consults":6,"minPrice":1199,"name":"Awaylee李薇 明星同款女神新装 清新女神郭碧婷同款海底世界系列 翻领落肩长袖海洋元素印花藏蓝色廓形外套","collectioned":0,"maxPrice":1199,"isAfter":0,"mark":1,"categoryId":1695},{"soonPromotion":{},"img":"/2018/01/02/060538054573a583ba7fbc8cd4082a681547e1.jpg","originalPrice":590,"sellPrice":590,"soonPrice":590,"isCart":1,"flashPrice":0,"categoryName":"休闲鞋","colors":[{"img":"/2018/01/02/060525f663f1ed239a6502c7f824b8a21e3535.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"},{"img":"/2018/01/02/060528ac536bb70f7bfe0e0db6bd969ee6ba61.jpg","code":"0","groupId":0,"name":"颜色","id":2,"value":"蓝色"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"26"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"28"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"30"},{"img":"","code":"0","groupId":0,"name":"尺码","id":4,"value":"32"}],"productTradeType":"COMMON","price":590,"collagePrice":0,"recomScore":4005,"mainPic":"/2018/01/02/060538054573a583ba7fbc8cd4082a681547e1.jpg","isTopical":false,"productSellType":"SPOT","id":185390,"sn":"18S17L514390","brand":"HOUSE OF AVENUES","designerId":10941,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":590,"secondRatio":0.16,"store":1,"designer":"JOYCE MAK","isSubscribe":0,"consults":0,"minPrice":590,"name":"HOUSE OF AVENUES 蝴蝶结童鞋","collectioned":0,"maxPrice":590,"isAfter":1,"mark":1,"categoryId":2192},{"soonPromotion":{},"img":"/2016/11/17/090520f810ae0b2db66c15fdf97185d6c20672.jpg","originalPrice":679,"sellPrice":679,"soonPrice":679,"isCart":1,"flashPrice":0,"categoryName":"休闲裤","colors":[{"img":"/2016/11/18/060638f810ae0b2db66c15fdf97185d6c20672.jpg","code":"020","groupId":0,"name":"颜色","id":90,"value":"黑色"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"productTradeType":"COMMON","price":679,"collagePrice":0,"recomScore":2491,"mainPic":"/2016/11/17/090520f810ae0b2db66c15fdf97185d6c20672.jpg","isTopical":false,"productSellType":"SPOT","id":142927,"sn":"M00163080002","brand":"MA by MA STUDIO","designerId":10012,"comments":11,"isSpot":true,"grossRatio":1,"salePrice":679,"secondRatio":0.2,"store":1,"designer":"mashama","isSubscribe":0,"consults":1,"minPrice":679,"name":"我的新衣 MA BY MA STUDIO MASHAMA 冰雪奇缘系列 林志玲 战队 经典黑色修身显瘦皮裤长裤","collectioned":0,"maxPrice":679,"isAfter":1,"mark":1,"categoryId":1617},{"soonPromotion":{},"img":"/pi/44/100944/e88cd14215c43d8f05a6d374c36ab6ef","originalPrice":1839,"sellPrice":1839,"soonPrice":1839,"isCart":1,"flashPrice":460,"categoryName":"连衣裙","colors":[{"img":"/sp/44/100944/e88cd14215c43d8f05a6d374c36ab6ef","code":"035","groupId":0,"name":"颜色","id":46,"value":"米黄"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"productTradeType":"COMMON","price":1839,"collagePrice":0,"recomScore":2310,"mainPic":"/pi/44/100944/e88cd14215c43d8f05a6d374c36ab6ef","isTopical":false,"productSellType":"SPOT","id":100944,"sn":"SY341020023","brand":"五叠衣","designerId":10050,"comments":7,"isSpot":true,"grossRatio":1,"salePrice":1839,"secondRatio":0.2,"store":1,"designer":"杨宝志","isSubscribe":0,"consults":0,"minPrice":1839,"name":"五叠衣 杨宝志  数码图案无袖时尚连衣裙 SY341020023","collectioned":0,"maxPrice":1839,"isAfter":1,"mark":1,"categoryId":1588},{"soonPromotion":{},"img":"/model/1604/f612300d6cb44a2c76ee68098ddc7ece","originalPrice":299,"sellPrice":299,"soonPrice":299,"isCart":1,"flashPrice":105,"categoryName":"T恤","colors":[{"img":"/model/1604/f612300d6cb44a2c76ee68098ddc7ece","code":"015","groupId":0,"name":"颜色","id":89,"value":"白色"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"10","groupId":0,"name":"尺码","id":150,"value":"XS码"},{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"productTradeType":"COMMON","price":299,"collagePrice":0,"recomScore":2159,"mainPic":"/model/1604/f612300d6cb44a2c76ee68098ddc7ece","isTopical":false,"productSellType":"SPOT","id":126855,"sn":"D00461200001","brand":"Liiiiz","designerId":10545,"comments":6,"isSpot":true,"grossRatio":1,"salePrice":299,"secondRatio":0.2,"store":1,"designer":"Liiiiz","isSubscribe":0,"consults":0,"minPrice":299,"name":"LIIIIZ 经典百搭款型蓝色房屋状相框立体拼接白色短袖T恤","collectioned":0,"maxPrice":299,"isAfter":1,"mark":1,"categoryId":1591},{"soonPromotion":{},"img":"/2017/07/17/0912107db6c579aa9f0b079458781b236b994a.jpg","originalPrice":138,"sellPrice":138,"soonPrice":138,"isCart":1,"flashPrice":0,"categoryName":"收纳整合","colors":[{"img":"/2017/07/17/0911587db6c579aa9f0b079458781b236b994a.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"玫红"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"均码"}],"productTradeType":"COMMON","price":138,"collagePrice":0.01,"recomScore":2121,"mainPic":"/2017/07/17/0912107db6c579aa9f0b079458781b236b994a.jpg","isTopical":true,"productSellType":"SPOT","id":166089,"sn":"TA510801145ZZ","brand":"多样屋","designerId":10665,"comments":6,"isSpot":false,"grossRatio":1,"salePrice":138,"secondRatio":0.2,"store":1,"designer":"多样屋","isSubscribe":0,"consults":0,"minPrice":138,"name":"多样屋迷你首饰盒/玫红","collectioned":0,"maxPrice":138,"isAfter":1,"mark":1,"categoryId":2218},{"soonPromotion":{},"img":"/pi/1504/407a22ceb78dcf9f323aaee93a085352","originalPrice":1326,"sellPrice":1326,"soonPrice":1326,"isCart":1,"flashPrice":0,"categoryName":"雨鞋","colors":[{"img":"/sp/1504/0f580652fcb183062bd5c071d04c40cb","code":"020","groupId":"0","name":"颜色","id":"90","value":"黑色"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"37","groupId":"0","name":"尺码","id":"138","value":"37码"},{"img":"","code":"38","groupId":"0","name":"尺码","id":"139","value":"38码"},{"img":"","code":"39","groupId":"0","name":"尺码","id":"140","value":"39码"},{"img":"","code":"40","groupId":"0","name":"尺码","id":"141","value":"40码"}],"productTradeType":"COMMON","price":1326,"collagePrice":0,"recomScore":2098,"mainPic":"/pi/1504/407a22ceb78dcf9f323aaee93a085352","isTopical":false,"productSellType":"SPOT","id":109178,"sn":"JZ02351831007","brand":"吾玩","designerId":10225,"comments":4,"isSpot":true,"grossRatio":1,"salePrice":1326,"secondRatio":0.13,"store":1,"designer":"张驰 文刀李","isSubscribe":1,"consults":3,"minPrice":1326,"name":"Rubbersoul  文刀李  Rubber Soul橡胶系列   天然橡胶传统手工舒适感老虎TIGER印饰时尚感雨雪长靴   ","collectioned":0,"maxPrice":1326,"isAfter":0,"mark":1,"categoryId":2185},{"soonPromotion":{},"img":"/pi/47/100947/bfa6af76d5fff0794de63a15d76e202a","originalPrice":1429,"sellPrice":1429,"soonPrice":1429,"isCart":1,"flashPrice":357,"categoryName":"半身裙","colors":[{"img":"/sp/47/100947/bfa6af76d5fff0794de63a15d76e202a","code":"035","groupId":0,"name":"颜色","id":46,"value":"米黄"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"productTradeType":"COMMON","price":1429,"collagePrice":0,"recomScore":2084,"mainPic":"/pi/47/100947/bfa6af76d5fff0794de63a15d76e202a","isTopical":false,"productSellType":"SPOT","id":100947,"sn":"SY341090026","brand":"五叠衣","designerId":10050,"comments":3,"isSpot":true,"grossRatio":1,"salePrice":1429,"secondRatio":0.2,"store":1,"designer":"杨宝志","isSubscribe":0,"consults":1,"minPrice":1429,"name":"五叠衣 杨宝志 文艺图案中腰百褶个性半身裙SY341090026","collectioned":0,"maxPrice":1429,"isAfter":1,"mark":1,"categoryId":1684},{"soonPromotion":{},"img":"/pi/16/101116/34578fd4bdaa54f34e2b3981c9cd6fff","originalPrice":1049,"sellPrice":1049,"soonPrice":1049,"isCart":1,"flashPrice":263,"categoryName":"雪纺衫","colors":[{"img":"/sp/16/101116/34578fd4bdaa54f34e2b3981c9cd6fff","code":"035","groupId":0,"name":"颜色","id":46,"value":"米黄"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"productTradeType":"COMMON","price":1049,"collagePrice":0,"recomScore":2069,"mainPic":"/pi/16/101116/34578fd4bdaa54f34e2b3981c9cd6fff","isTopical":false,"productSellType":"SPOT","id":101116,"sn":"SY341209001","brand":"五叠衣","designerId":10050,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1049,"secondRatio":0.2,"store":1,"designer":"杨宝志","isSubscribe":0,"consults":0,"minPrice":1049,"name":"五叠衣 杨宝志 时尚彩绘修身无袖上衣SY341209001","collectioned":0,"maxPrice":1049,"isAfter":1,"mark":1,"categoryId":1603},{"soonPromotion":{},"img":"/model/1511/a2677e1a07f69b57b148ce0e7ceb89a1","originalPrice":1249,"sellPrice":1249,"soonPrice":1249,"isCart":1,"flashPrice":0,"categoryName":"卫衣","colors":[{"img":"/model/1511/a2677e1a07f69b57b148ce0e7ceb89a1","code":"020","groupId":0,"name":"颜色","id":90,"value":"黑色"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"10","groupId":0,"name":"尺码","id":150,"value":"XS码"},{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"productTradeType":"COMMON","price":1249,"collagePrice":0,"recomScore":2049,"mainPic":"/model/1511/a2677e1a07f69b57b148ce0e7ceb89a1","isTopical":false,"productSellType":"SPOT","id":117475,"sn":"T00453280010","brand":"Tee Library","designerId":10236,"comments":1,"isSpot":true,"grossRatio":1,"salePrice":1249,"secondRatio":0.2,"store":1,"designer":"Tee Library","isSubscribe":1,"consults":0,"minPrice":1249,"name":"Tee Library 圆领收口长袖个性图案拼接设计黑色女士休闲短款卫衣","collectioned":0,"maxPrice":1249,"isAfter":1,"mark":1,"categoryId":1688},{"soonPromotion":{},"img":"/model/1511/c757978065f9233871467016a4e00e8c","originalPrice":609,"sellPrice":609,"soonPrice":609,"isCart":1,"flashPrice":0,"categoryName":"T恤","colors":[{"img":"/model/1511/c757978065f9233871467016a4e00e8c","code":"015","groupId":0,"name":"颜色","id":89,"value":"白色"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"},{"img":"","code":"04","groupId":0,"name":"尺码","id":29,"value":"XL码"}],"productTradeType":"COMMON","price":609,"collagePrice":0,"recomScore":2041,"mainPic":"/model/1511/c757978065f9233871467016a4e00e8c","isTopical":false,"productSellType":"SPOT","id":117464,"sn":"T00453200008","brand":"Tee Library","designerId":10236,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":609,"secondRatio":0.2,"store":1,"designer":"Tee Library","isSubscribe":1,"consults":0,"minPrice":609,"name":"Tee Library 圆领短袖个性印花图案男士白色纯棉休闲短款T恤","collectioned":0,"maxPrice":609,"isAfter":1,"mark":1,"categoryId":1702},{"soonPromotion":{},"img":"/2018/01/24/110812f01217515b8fb1c64092fabbfeb4020d.jpg","originalPrice":1999,"sellPrice":1993,"soonPrice":1993,"isCart":1,"flashPrice":0,"categoryName":"外套","colors":[{"img":"/2018/01/24/110732f01217515b8fb1c64092fabbfeb4020d.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"藏蓝"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"S"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"M"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"L"}],"productTradeType":"COMMON","price":1993,"collagePrice":0,"recomScore":2040,"mainPic":"/2018/01/24/110812f01217515b8fb1c64092fabbfeb4020d.jpg","isTopical":false,"productSellType":"SPOT","id":187364,"sn":"L00753220003","brand":"砍价商城","designerId":11276,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":1993,"secondRatio":0.2,"store":1,"designer":"砍价商城","isSubscribe":0,"consults":0,"minPrice":1993,"name":"砍价商城  Awaylee李薇 明星同款女神新装 清新女神郭碧婷同款海底世界系列 翻领落肩长袖海洋元素印花藏蓝色廓形外套","collectioned":0,"maxPrice":1993,"isAfter":1,"mark":1,"categoryId":1969}]}
         */

        @SerializedName("products")
        private ProductsBean productsX;

        public CouponBean getCoupon() {
            return coupon;
        }

        public void setCoupon(CouponBean coupon) {
            this.coupon = coupon;
        }


        public BrandsBean getBrands() {
            return brands;
        }

        public void setBrands(BrandsBean brands) {
            this.brands = brands;
        }

        public ProductsBean getProductsX() {
            return productsX;
        }

        public void setProductsX(ProductsBean productsX) {
            this.productsX = productsX;
        }


        public static class CouponBean {
            public Date getExpirestamp() {
                return expirestamp;
            }

            public void setExpirestamp(Date expirestamp) {
                this.expirestamp = expirestamp;
            }

            public Date getEnablestamp() {
                return enablestamp;
            }

            public void setEnablestamp(Date enablestamp) {
                this.enablestamp = enablestamp;
            }

            /**
             * amount : 50
             * code : S1533282105032-2908360
             * remark : 随便写
             * expiredate : 2018/08/13 15:41
             * type : DISCOUNT
             * enabledate : 2018/08/03 15:41
             * url : null
             * transfer : 1
             * price : null
             * name : 测试优惠券Url
             * id : 9945859
             * isExpired : 0
             * needAmount : 2000
             * status : CLAIMED
             * memberId : 2908360
             */
            private Date expirestamp;
            private Date enablestamp;
            private double amount;
            private String code;
            private String remark;
            private String expiredate;
            private String type;
            private String enabledate;
            private String url;
            private int transfer;
            private Double price;
            private String name;
            private int id;
            private int isExpired;
            private double needAmount;
            @SerializedName("status")
            private String statusX;
            private int memberId;

            public boolean isToRedirect() {
                return toRedirect;
            }

            public void setToRedirect(boolean toRedirect) {
                this.toRedirect = toRedirect;
            }

            private boolean toRedirect;
            private String checkAssociation;

            public String getCheckAssociation() {
                return checkAssociation;
            }

            public void setCheckAssociation(String checkAssociation) {
                this.checkAssociation = checkAssociation;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getExpiredate() {
                return expiredate;
            }

            public void setExpiredate(String expiredate) {
                this.expiredate = expiredate;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getEnabledate() {
                return enabledate;
            }

            public void setEnabledate(String enabledate) {
                this.enabledate = enabledate;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getTransfer() {
                return transfer;
            }

            public void setTransfer(int transfer) {
                this.transfer = transfer;
            }

            public Double getPrice() {
                return price;
            }

            public void setPrice(Double price) {
                this.price = price;
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

            public int getIsExpired() {
                return isExpired;
            }

            public void setIsExpired(int isExpired) {
                this.isExpired = isExpired;
            }

            public double getNeedAmount() {
                return needAmount;
            }

            public void setNeedAmount(double needAmount) {
                this.needAmount = needAmount;
            }

            public String getStatusX() {
                return statusX;
            }

            public void setStatusX(String statusX) {
                this.statusX = statusX;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }
        }

        public static class BrandsBean {
            /**
             * next : false
             * total : 2
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"backgroundUrl":"/2018/05/09/06132428d7021c0909a5c0999d1758f268a3f0.jpg","appIntro":"<p><img src=\"https://img.d2c.cn/2018/07/25/0351437ba960c886041024f0cc02a2e2ecfa2d.png\"><br><\/p>","signPics":["/2017/03/17/024854e180dee53a8a7320cae53457dfc55e3c.jpg"],"likeCount":594,"recommend":0,"introPic":"/2017/03/17/024854e180dee53a8a7320cae53457dfc55e3c.jpg","video":"/2018/08/06/0138438025e19165567cb08808c8a3e28ea7a2.mp4","headPic":"/2016/12/07/02413182aa331146130a74f8b119acdf9ae0d7.png","fans":594,"videoPic":null,"intro":"<p>法国1011高级女装品牌，是由来自法国设计师团队一手创立。曾多次参加巴黎高级女装时装周，致力于推广最先进时尚潮流的同事，改变顾客对传统服装的价值观。主要针对于20-35岁女性，以欧美简约建筑风为基础。在提高功能性的前提下，大幅度让顾客满足对服装的一切要求。<\/p>","name":"1011","id":10687,"brand":"逝月","slogan":null,"attentioned":0},{"backgroundUrl":null,"appIntro":null,"signPics":["/2016/12/21/08505500d81a8c2386afabce37ccc87500d65d.jpg","/2016/12/21/0214355e5e4fd86b09a06e5718be2da0988eb2.jpg"],"likeCount":557,"recommend":0,"introPic":"/2016/12/21/08505500d81a8c2386afabce37ccc87500d65d.jpg,/2016/12/21/0214355e5e4fd86b09a06e5718be2da0988eb2.jpg","video":"/2018/08/06/0512258025e19165567cb08808c8a3e28ea7a2.mp4","headPic":"/2016/12/21/021337ab8ebbac01a5af7c82104dc7369396fd.jpg","fans":557,"videoPic":null,"intro":"","name":"17s.t.even.een","id":10911,"brand":"李荣浩","slogan":null,"attentioned":0}]
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
                 * backgroundUrl : /2018/05/09/06132428d7021c0909a5c0999d1758f268a3f0.jpg
                 * appIntro : <p><img src="https://img.d2c.cn/2018/07/25/0351437ba960c886041024f0cc02a2e2ecfa2d.png"><br></p>
                 * signPics : ["/2017/03/17/024854e180dee53a8a7320cae53457dfc55e3c.jpg"]
                 * likeCount : 594
                 * recommend : 0
                 * introPic : /2017/03/17/024854e180dee53a8a7320cae53457dfc55e3c.jpg
                 * video : /2018/08/06/0138438025e19165567cb08808c8a3e28ea7a2.mp4
                 * headPic : /2016/12/07/02413182aa331146130a74f8b119acdf9ae0d7.png
                 * fans : 594
                 * videoPic : null
                 * intro : <p>法国1011高级女装品牌，是由来自法国设计师团队一手创立。曾多次参加巴黎高级女装时装周，致力于推广最先进时尚潮流的同事，改变顾客对传统服装的价值观。主要针对于20-35岁女性，以欧美简约建筑风为基础。在提高功能性的前提下，大幅度让顾客满足对服装的一切要求。</p>
                 * name : 1011
                 * id : 10687
                 * brand : 逝月
                 * slogan : null
                 * attentioned : 0
                 */

                private String backgroundUrl;
                private String appIntro;
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
                private String slogan;
                private int attentioned;
                private List<ProductDetailBean.DataBean.ProductBean> products;
                public List<ProductDetailBean.DataBean.ProductBean> getProducts() {
                    return products;
                }

                public void setProducts(List<ProductDetailBean.DataBean.ProductBean> products) {
                    this.products = products;
                }
                public String getBackgroundUrl() {
                    return backgroundUrl;
                }

                public void setBackgroundUrl(String backgroundUrl) {
                    this.backgroundUrl = backgroundUrl;
                }

                public String getAppIntro() {
                    return appIntro;
                }

                public void setAppIntro(String appIntro) {
                    this.appIntro = appIntro;
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

                public String getVideo() {
                    return video;
                }

                public void setVideo(String video) {
                    this.video = video;
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

                public String getVideoPic() {
                    return videoPic;
                }

                public void setVideoPic(String videoPic) {
                    this.videoPic = videoPic;
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

                public String getSlogan() {
                    return slogan;
                }

                public void setSlogan(String slogan) {
                    this.slogan = slogan;
                }

                public int getAttentioned() {
                    return attentioned;
                }

                public void setAttentioned(int attentioned) {
                    this.attentioned = attentioned;
                }

            }
        }

        public static class ProductsBean {
            /**
             * next : true
             * total : 63
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"soonPromotion":{},"img":"/2018/08/01/085749372c32a0730ca6cc3998f53579e151c4.png","originalPrice":2,"sellPrice":2,"soonPrice":2,"isCart":1,"flashPrice":0,"categoryName":"体重秤","colors":[{"img":"/2018/08/01/085733372c32a0730ca6cc3998f53579e151c4.png","code":0,"groupId":0,"name":"颜色","id":1,"value":"自定义"}],"firstRatio":0.3,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":0,"groupId":0,"name":"尺码","id":1,"value":"自定义"}],"productTradeType":"COMMON","price":2,"collagePrice":0,"recomScore":16100,"mainPic":"/2018/08/01/085749372c32a0730ca6cc3998f53579e151c4.png","isTopical":true,"productSellType":"SPOT","id":195528,"sn":"223423","brand":"Awaylee","designerId":10061,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":2,"secondRatio":0.3,"store":1,"designer":"李薇","isSubscribe":0,"consults":0,"minPrice":2,"name":"2","collectioned":0,"maxPrice":2,"isAfter":1,"mark":1,"categoryId":2211},{"soonPromotion":{},"img":"/2018/07/17/23743f3457d9fcbacc9f193336d55497","originalPrice":599,"sellPrice":500,"soonPrice":500,"isCart":1,"flashPrice":0,"categoryName":"移动电源","colors":[{"img":"/2018/07/17/23743f3457d9fcbacc9f193336d55497","code":0,"groupId":0,"name":"颜色","id":1,"value":"默认"}],"firstRatio":0.1,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":0,"groupId":0,"name":"尺码","id":1,"value":"默认"}],"productTradeType":"CROSS","price":500,"collagePrice":0,"recomScore":13850,"mainPic":"/2018/07/17/23743f3457d9fcbacc9f193336d55497","isTopical":true,"productSellType":"SPOT","id":195407,"sn":"51828965","brand":"考拉品牌001","designerId":11442,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":500,"secondRatio":0.1,"store":1,"designer":"考拉设计师","isSubscribe":1,"consults":0,"minPrice":500,"name":"考啊","collectioned":0,"maxPrice":500,"isAfter":1,"mark":1,"categoryId":2213},{"soonPromotion":{},"img":"/2018/07/17/93c165a936add6a0e94d330fccb7fda1","originalPrice":0.01,"sellPrice":0.01,"soonPrice":0.01,"isCart":1,"flashPrice":0,"categoryName":"灯","colors":[{"img":"/2018/07/17/93c165a936add6a0e94d330fccb7fda1","code":"0","groupId":0,"name":"颜色","id":0,"value":"默认"}],"firstRatio":0.01,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":0,"value":"默认"}],"productTradeType":"CROSS","price":0.01,"collagePrice":0,"recomScore":13700,"mainPic":"/2018/07/17/93c165a936add6a0e94d330fccb7fda1","isTopical":true,"productSellType":"SPOT","id":195399,"sn":"58726790","brand":"1011","designerId":10687,"comments":0,"isSpot":true,"grossRatio":0.01,"salePrice":0.01,"secondRatio":0.01,"store":1,"designer":"逝月","isSubscribe":0,"consults":0,"minPrice":0.01,"name":"kua-auto-20170331211257552","collectioned":0,"maxPrice":0.01,"isAfter":1,"mark":1,"categoryId":2212},{"soonPromotion":{},"img":"/2018/07/10/0803422092b6f21284894ab426fcba6b3445dc.jpg","originalPrice":0.01,"sellPrice":0.01,"soonPrice":0.01,"isCart":1,"flashPrice":2,"categoryName":"水果饮料啦","colors":[{"img":"/2018/07/10/0803392092b6f21284894ab426fcba6b3445dc.jpg","code":0,"groupId":0,"name":"蓝精灵主","id":1,"value":"自定义"}],"firstRatio":0.1,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":0,"groupId":0,"name":"蓝精灵副","id":1,"value":"自定义"}],"productTradeType":"COMMON","price":0.01,"collagePrice":0.01,"recomScore":12800,"mainPic":"/2018/07/10/0803422092b6f21284894ab426fcba6b3445dc.jpg","isTopical":true,"productSellType":"SPOT","id":195398,"sn":"201807101603","brand":"10x1","designerId":10617,"comments":0,"isSpot":true,"grossRatio":0.1,"salePrice":0.01,"secondRatio":0.1,"store":1,"designer":"时晨昳","isSubscribe":0,"consults":0,"minPrice":0.01,"name":"测试201807101603","collectioned":0,"maxPrice":0.01,"isAfter":1,"mark":1,"categoryId":2272},{"soonPromotion":{},"img":"/2018/06/25/0347314046ec1cbd9700b224a01e6449deadc1.png","originalPrice":2,"sellPrice":2,"soonPrice":2,"isCart":1,"flashPrice":0,"categoryName":"运动鞋","colors":[{"img":"/2018/06/25/0347184d30825a147a0b6fdcbfaceedadf4127.png","code":0,"groupId":0,"name":"颜色","id":1,"value":"自定义"}],"firstRatio":0.2,"partnerSales":2,"subTitle":"","sizes":[{"img":"","code":0,"groupId":0,"name":"尺码","id":1,"value":"自定义"}],"productTradeType":"COMMON","price":2,"collagePrice":0,"recomScore":12552,"mainPic":"/2018/06/25/0347314046ec1cbd9700b224a01e6449deadc1.png","isTopical":true,"productSellType":"SPOT","id":195395,"sn":"2","brand":"ZHANGSHUAI","designerId":10053,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":2,"secondRatio":0.3,"store":1,"designer":"张帅","isSubscribe":0,"consults":0,"minPrice":2,"name":"2","collectioned":0,"maxPrice":2,"isAfter":1,"mark":1,"categoryId":2188},{"soonPromotion":{},"img":"","originalPrice":310,"sellPrice":310,"soonPrice":310,"isCart":1,"flashPrice":30,"categoryName":"耳机耳麦","colors":[{"img":"/2018/06/2693c165a936add6a0e94d330fccb7fda1","code":"0","groupId":0,"name":"【勿动】尺码","id":1,"value":"L"}],"firstRatio":0.01,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"【勿动】销售属性","id":1,"value":"蓝色"}],"productTradeType":"CROSS","price":310,"collagePrice":0,"recomScore":10100,"mainPic":"","isTopical":false,"productSellType":"SPOT","id":195393,"sn":"58735682","brand":"1011","designerId":10687,"comments":0,"isSpot":false,"grossRatio":0.01,"salePrice":310,"secondRatio":0.01,"store":1,"designer":"逝月","isSubscribe":0,"consults":0,"minPrice":310,"name":"迪奥唇膏-auto-20170425193245156","collectioned":0,"maxPrice":310,"isAfter":1,"mark":1,"categoryId":2214},{"soonPromotion":{},"img":"/2018/06/2623743f3457d9fcbacc9f193336d55497","originalPrice":599,"sellPrice":599,"soonPrice":599,"isCart":1,"flashPrice":100,"categoryName":"腰包","colors":[{"img":"/2018/06/2623743f3457d9fcbacc9f193336d55497","code":"0","groupId":0,"name":"【勿动】销售属性","id":1,"value":"棕色"}],"firstRatio":0.01,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"【勿动】销售属性","id":1,"value":"默认"}],"productTradeType":"CROSS","price":599,"collagePrice":0,"recomScore":10100,"mainPic":"/2018/06/2623743f3457d9fcbacc9f193336d55497","isTopical":true,"productSellType":"SPOT","id":195394,"sn":"51841203","brand":"1011","designerId":10687,"comments":0,"isSpot":false,"grossRatio":0.01,"salePrice":599,"secondRatio":0.01,"store":1,"designer":"逝月","isSubscribe":0,"consults":0,"minPrice":599,"name":"迪奥唇膏-auto-20161106142718177","collectioned":0,"maxPrice":599,"isAfter":1,"mark":1,"categoryId":2204},{"soonPromotion":{},"img":"/2018/06/1593c165a936add6a0e94d330fccb7fda1","originalPrice":270,"sellPrice":270,"soonPrice":270,"isCart":1,"flashPrice":0,"categoryName":"笔筒","colors":[{"img":"/2018/06/1593c165a936add6a0e94d330fccb7fda1","code":0,"groupId":0,"name":"笔筒测试","id":1,"value":"A"}],"firstRatio":0.01,"partnerSales":0,"subTitle":"","sizes":[],"productTradeType":"CROSS","price":270,"collagePrice":0,"recomScore":9050,"mainPic":"/2018/06/1593c165a936add6a0e94d330fccb7fda1","isTopical":true,"productSellType":"SPOT","id":195390,"sn":"58735570","brand":"1011","designerId":10687,"comments":0,"isSpot":false,"grossRatio":0.01,"salePrice":270,"secondRatio":0.01,"store":1,"designer":"逝月","isSubscribe":0,"consults":0,"minPrice":270,"name":"【勿动】单元测试专用","collectioned":0,"maxPrice":270,"isAfter":1,"mark":1,"categoryId":2270},{"soonPromotion":{},"img":"/model/1601/842719b74c6a52dcd758b50eb2332f47","originalPrice":1999,"sellPrice":1199,"soonPrice":1199,"isCart":0,"flashPrice":599,"categoryName":"大衣","colors":[{"img":"/model/1601/842719b74c6a52dcd758b50eb2332f47","code":"138","groupId":0,"name":"颜色","id":238,"value":"藏蓝"}],"firstRatio":0.2,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"productTradeType":"COMMON","price":1199,"collagePrice":0,"recomScore":6476,"mainPic":"/model/1601/842719b74c6a52dcd758b50eb2332f47","isTopical":true,"productSellType":"SPOT","id":113655,"sn":"L00753220003","brand":"Awaylee","designerId":10061,"comments":116,"isSpot":true,"grossRatio":1,"salePrice":1199,"secondRatio":0.05,"store":1,"designer":"李薇","isSubscribe":0,"consults":6,"minPrice":1199,"name":"Awaylee李薇 明星同款女神新装 清新女神郭碧婷同款海底世界系列 翻领落肩长袖海洋元素印花藏蓝色廓形外套","collectioned":0,"maxPrice":1199,"isAfter":0,"mark":1,"categoryId":1695},{"soonPromotion":{},"img":"/2018/01/02/060538054573a583ba7fbc8cd4082a681547e1.jpg","originalPrice":590,"sellPrice":590,"soonPrice":590,"isCart":1,"flashPrice":0,"categoryName":"休闲鞋","colors":[{"img":"/2018/01/02/060525f663f1ed239a6502c7f824b8a21e3535.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"黑色"},{"img":"/2018/01/02/060528ac536bb70f7bfe0e0db6bd969ee6ba61.jpg","code":"0","groupId":0,"name":"颜色","id":2,"value":"蓝色"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"26"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"28"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"30"},{"img":"","code":"0","groupId":0,"name":"尺码","id":4,"value":"32"}],"productTradeType":"COMMON","price":590,"collagePrice":0,"recomScore":4005,"mainPic":"/2018/01/02/060538054573a583ba7fbc8cd4082a681547e1.jpg","isTopical":false,"productSellType":"SPOT","id":185390,"sn":"18S17L514390","brand":"HOUSE OF AVENUES","designerId":10941,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":590,"secondRatio":0.16,"store":1,"designer":"JOYCE MAK","isSubscribe":0,"consults":0,"minPrice":590,"name":"HOUSE OF AVENUES 蝴蝶结童鞋","collectioned":0,"maxPrice":590,"isAfter":1,"mark":1,"categoryId":2192},{"soonPromotion":{},"img":"/2016/11/17/090520f810ae0b2db66c15fdf97185d6c20672.jpg","originalPrice":679,"sellPrice":679,"soonPrice":679,"isCart":1,"flashPrice":0,"categoryName":"休闲裤","colors":[{"img":"/2016/11/18/060638f810ae0b2db66c15fdf97185d6c20672.jpg","code":"020","groupId":0,"name":"颜色","id":90,"value":"黑色"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"productTradeType":"COMMON","price":679,"collagePrice":0,"recomScore":2491,"mainPic":"/2016/11/17/090520f810ae0b2db66c15fdf97185d6c20672.jpg","isTopical":false,"productSellType":"SPOT","id":142927,"sn":"M00163080002","brand":"MA by MA STUDIO","designerId":10012,"comments":11,"isSpot":true,"grossRatio":1,"salePrice":679,"secondRatio":0.2,"store":1,"designer":"mashama","isSubscribe":0,"consults":1,"minPrice":679,"name":"我的新衣 MA BY MA STUDIO MASHAMA 冰雪奇缘系列 林志玲 战队 经典黑色修身显瘦皮裤长裤","collectioned":0,"maxPrice":679,"isAfter":1,"mark":1,"categoryId":1617},{"soonPromotion":{},"img":"/pi/44/100944/e88cd14215c43d8f05a6d374c36ab6ef","originalPrice":1839,"sellPrice":1839,"soonPrice":1839,"isCart":1,"flashPrice":460,"categoryName":"连衣裙","colors":[{"img":"/sp/44/100944/e88cd14215c43d8f05a6d374c36ab6ef","code":"035","groupId":0,"name":"颜色","id":46,"value":"米黄"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"productTradeType":"COMMON","price":1839,"collagePrice":0,"recomScore":2310,"mainPic":"/pi/44/100944/e88cd14215c43d8f05a6d374c36ab6ef","isTopical":false,"productSellType":"SPOT","id":100944,"sn":"SY341020023","brand":"五叠衣","designerId":10050,"comments":7,"isSpot":true,"grossRatio":1,"salePrice":1839,"secondRatio":0.2,"store":1,"designer":"杨宝志","isSubscribe":0,"consults":0,"minPrice":1839,"name":"五叠衣 杨宝志  数码图案无袖时尚连衣裙 SY341020023","collectioned":0,"maxPrice":1839,"isAfter":1,"mark":1,"categoryId":1588},{"soonPromotion":{},"img":"/model/1604/f612300d6cb44a2c76ee68098ddc7ece","originalPrice":299,"sellPrice":299,"soonPrice":299,"isCart":1,"flashPrice":105,"categoryName":"T恤","colors":[{"img":"/model/1604/f612300d6cb44a2c76ee68098ddc7ece","code":"015","groupId":0,"name":"颜色","id":89,"value":"白色"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"10","groupId":0,"name":"尺码","id":150,"value":"XS码"},{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"productTradeType":"COMMON","price":299,"collagePrice":0,"recomScore":2159,"mainPic":"/model/1604/f612300d6cb44a2c76ee68098ddc7ece","isTopical":false,"productSellType":"SPOT","id":126855,"sn":"D00461200001","brand":"Liiiiz","designerId":10545,"comments":6,"isSpot":true,"grossRatio":1,"salePrice":299,"secondRatio":0.2,"store":1,"designer":"Liiiiz","isSubscribe":0,"consults":0,"minPrice":299,"name":"LIIIIZ 经典百搭款型蓝色房屋状相框立体拼接白色短袖T恤","collectioned":0,"maxPrice":299,"isAfter":1,"mark":1,"categoryId":1591},{"soonPromotion":{},"img":"/2017/07/17/0912107db6c579aa9f0b079458781b236b994a.jpg","originalPrice":138,"sellPrice":138,"soonPrice":138,"isCart":1,"flashPrice":0,"categoryName":"收纳整合","colors":[{"img":"/2017/07/17/0911587db6c579aa9f0b079458781b236b994a.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"玫红"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"均码"}],"productTradeType":"COMMON","price":138,"collagePrice":0.01,"recomScore":2121,"mainPic":"/2017/07/17/0912107db6c579aa9f0b079458781b236b994a.jpg","isTopical":true,"productSellType":"SPOT","id":166089,"sn":"TA510801145ZZ","brand":"多样屋","designerId":10665,"comments":6,"isSpot":false,"grossRatio":1,"salePrice":138,"secondRatio":0.2,"store":1,"designer":"多样屋","isSubscribe":0,"consults":0,"minPrice":138,"name":"多样屋迷你首饰盒/玫红","collectioned":0,"maxPrice":138,"isAfter":1,"mark":1,"categoryId":2218},{"soonPromotion":{},"img":"/pi/1504/407a22ceb78dcf9f323aaee93a085352","originalPrice":1326,"sellPrice":1326,"soonPrice":1326,"isCart":1,"flashPrice":0,"categoryName":"雨鞋","colors":[{"img":"/sp/1504/0f580652fcb183062bd5c071d04c40cb","code":"020","groupId":"0","name":"颜色","id":"90","value":"黑色"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"37","groupId":"0","name":"尺码","id":"138","value":"37码"},{"img":"","code":"38","groupId":"0","name":"尺码","id":"139","value":"38码"},{"img":"","code":"39","groupId":"0","name":"尺码","id":"140","value":"39码"},{"img":"","code":"40","groupId":"0","name":"尺码","id":"141","value":"40码"}],"productTradeType":"COMMON","price":1326,"collagePrice":0,"recomScore":2098,"mainPic":"/pi/1504/407a22ceb78dcf9f323aaee93a085352","isTopical":false,"productSellType":"SPOT","id":109178,"sn":"JZ02351831007","brand":"吾玩","designerId":10225,"comments":4,"isSpot":true,"grossRatio":1,"salePrice":1326,"secondRatio":0.13,"store":1,"designer":"张驰 文刀李","isSubscribe":1,"consults":3,"minPrice":1326,"name":"Rubbersoul  文刀李  Rubber Soul橡胶系列   天然橡胶传统手工舒适感老虎TIGER印饰时尚感雨雪长靴   ","collectioned":0,"maxPrice":1326,"isAfter":0,"mark":1,"categoryId":2185},{"soonPromotion":{},"img":"/pi/47/100947/bfa6af76d5fff0794de63a15d76e202a","originalPrice":1429,"sellPrice":1429,"soonPrice":1429,"isCart":1,"flashPrice":357,"categoryName":"半身裙","colors":[{"img":"/sp/47/100947/bfa6af76d5fff0794de63a15d76e202a","code":"035","groupId":0,"name":"颜色","id":46,"value":"米黄"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"productTradeType":"COMMON","price":1429,"collagePrice":0,"recomScore":2084,"mainPic":"/pi/47/100947/bfa6af76d5fff0794de63a15d76e202a","isTopical":false,"productSellType":"SPOT","id":100947,"sn":"SY341090026","brand":"五叠衣","designerId":10050,"comments":3,"isSpot":true,"grossRatio":1,"salePrice":1429,"secondRatio":0.2,"store":1,"designer":"杨宝志","isSubscribe":0,"consults":1,"minPrice":1429,"name":"五叠衣 杨宝志 文艺图案中腰百褶个性半身裙SY341090026","collectioned":0,"maxPrice":1429,"isAfter":1,"mark":1,"categoryId":1684},{"soonPromotion":{},"img":"/pi/16/101116/34578fd4bdaa54f34e2b3981c9cd6fff","originalPrice":1049,"sellPrice":1049,"soonPrice":1049,"isCart":1,"flashPrice":263,"categoryName":"雪纺衫","colors":[{"img":"/sp/16/101116/34578fd4bdaa54f34e2b3981c9cd6fff","code":"035","groupId":0,"name":"颜色","id":46,"value":"米黄"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"productTradeType":"COMMON","price":1049,"collagePrice":0,"recomScore":2069,"mainPic":"/pi/16/101116/34578fd4bdaa54f34e2b3981c9cd6fff","isTopical":false,"productSellType":"SPOT","id":101116,"sn":"SY341209001","brand":"五叠衣","designerId":10050,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":1049,"secondRatio":0.2,"store":1,"designer":"杨宝志","isSubscribe":0,"consults":0,"minPrice":1049,"name":"五叠衣 杨宝志 时尚彩绘修身无袖上衣SY341209001","collectioned":0,"maxPrice":1049,"isAfter":1,"mark":1,"categoryId":1603},{"soonPromotion":{},"img":"/model/1511/a2677e1a07f69b57b148ce0e7ceb89a1","originalPrice":1249,"sellPrice":1249,"soonPrice":1249,"isCart":1,"flashPrice":0,"categoryName":"卫衣","colors":[{"img":"/model/1511/a2677e1a07f69b57b148ce0e7ceb89a1","code":"020","groupId":0,"name":"颜色","id":90,"value":"黑色"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"10","groupId":0,"name":"尺码","id":150,"value":"XS码"},{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"}],"productTradeType":"COMMON","price":1249,"collagePrice":0,"recomScore":2049,"mainPic":"/model/1511/a2677e1a07f69b57b148ce0e7ceb89a1","isTopical":false,"productSellType":"SPOT","id":117475,"sn":"T00453280010","brand":"Tee Library","designerId":10236,"comments":1,"isSpot":true,"grossRatio":1,"salePrice":1249,"secondRatio":0.2,"store":1,"designer":"Tee Library","isSubscribe":1,"consults":0,"minPrice":1249,"name":"Tee Library 圆领收口长袖个性图案拼接设计黑色女士休闲短款卫衣","collectioned":0,"maxPrice":1249,"isAfter":1,"mark":1,"categoryId":1688},{"soonPromotion":{},"img":"/model/1511/c757978065f9233871467016a4e00e8c","originalPrice":609,"sellPrice":609,"soonPrice":609,"isCart":1,"flashPrice":0,"categoryName":"T恤","colors":[{"img":"/model/1511/c757978065f9233871467016a4e00e8c","code":"015","groupId":0,"name":"颜色","id":89,"value":"白色"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"},{"img":"","code":"03","groupId":0,"name":"尺码","id":28,"value":"L码"},{"img":"","code":"04","groupId":0,"name":"尺码","id":29,"value":"XL码"}],"productTradeType":"COMMON","price":609,"collagePrice":0,"recomScore":2041,"mainPic":"/model/1511/c757978065f9233871467016a4e00e8c","isTopical":false,"productSellType":"SPOT","id":117464,"sn":"T00453200008","brand":"Tee Library","designerId":10236,"comments":0,"isSpot":true,"grossRatio":1,"salePrice":609,"secondRatio":0.2,"store":1,"designer":"Tee Library","isSubscribe":1,"consults":0,"minPrice":609,"name":"Tee Library 圆领短袖个性印花图案男士白色纯棉休闲短款T恤","collectioned":0,"maxPrice":609,"isAfter":1,"mark":1,"categoryId":1702},{"soonPromotion":{},"img":"/2018/01/24/110812f01217515b8fb1c64092fabbfeb4020d.jpg","originalPrice":1999,"sellPrice":1993,"soonPrice":1993,"isCart":1,"flashPrice":0,"categoryName":"外套","colors":[{"img":"/2018/01/24/110732f01217515b8fb1c64092fabbfeb4020d.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"藏蓝"}],"firstRatio":0.05,"partnerSales":0,"subTitle":"","sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"S"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"M"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"L"}],"productTradeType":"COMMON","price":1993,"collagePrice":0,"recomScore":2040,"mainPic":"/2018/01/24/110812f01217515b8fb1c64092fabbfeb4020d.jpg","isTopical":false,"productSellType":"SPOT","id":187364,"sn":"L00753220003","brand":"砍价商城","designerId":11276,"comments":0,"isSpot":false,"grossRatio":1,"salePrice":1993,"secondRatio":0.2,"store":1,"designer":"砍价商城","isSubscribe":0,"consults":0,"minPrice":1993,"name":"砍价商城  Awaylee李薇 明星同款女神新装 清新女神郭碧婷同款海底世界系列 翻领落肩长袖海洋元素印花藏蓝色廓形外套","collectioned":0,"maxPrice":1993,"isAfter":1,"mark":1,"categoryId":1969}]
             */

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
