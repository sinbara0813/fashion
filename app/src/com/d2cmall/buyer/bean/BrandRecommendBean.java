package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者:Created by sinbara on 2019/1/8.
 * 邮箱:hrb940258169@163.com
 */

public class BrandRecommendBean extends BaseBean {

    /**
     * message :
     * data : null
     * datas : {}
     * list : [{"orderPromotionTypeName":null,"sellPrice":856,"promotionId":null,"firstRatio":0.02,"promotionTypeName":null,"productTradeType":"COMMON","sizes":[{"img":"","code":0,"groupId":0,"name":"尺码","id":1,"value":"S"},{"img":"","code":0,"groupId":0,"name":"尺码","id":2,"value":"M"}],"subTitle":null,"price":856,"recomScore":190780,"isTopical":true,"mainPic":"/2018/11/10/0310190b6b3446317720ed033d1e3b0eeccc1f.jpg","productSellType":"CUSTOM","id":223126,"brand":"MYSTRIOUS","designerId":11886,"grossRatio":1,"productId":null,"isSubscribe":0,"orderPromotionId":null,"consults":0,"flashPromotionId":null,"minPrice":856,"name":" Mysterious原创高级定制2018秋装修身高腰开叉连衣裙","collectioned":0,"maxPrice":856,"orderPromotionType":null,"isAfter":1,"soonPromotion":{},"img":"/2018/11/10/0310190b6b3446317720ed033d1e3b0eeccc1f.jpg","originalPrice":856,"isCart":1,"soonPrice":856,"flashPrice":515,"categoryName":"连衣裙","colors":[{"img":"/2018/11/10/0310170b6b3446317720ed033d1e3b0eeccc1f.jpg","code":0,"groupId":0,"name":"颜色","id":1,"value":"黑色"}],"partnerSales":0,"searchFrom":null,"collagePrice":0,"sn":"MY20181216","class":"com.d2c.product.search.view.ProductVO","promotionType":null,"comments":0,"isSpot":false,"salePrice":856,"secondRatio":0.2,"designer":"美佐","store":1,"collagePromotionId":null,"categoryId":1588,"mark":1},{"orderPromotionTypeName":null,"sellPrice":1580,"promotionId":null,"firstRatio":0.02,"promotionTypeName":null,"productTradeType":"COMMON","sizes":[{"img":"","code":0,"groupId":0,"name":"尺码","id":1,"value":"S"},{"img":"","code":0,"groupId":0,"name":"尺码","id":2,"value":"M"}],"subTitle":null,"price":1580,"recomScore":99000,"isTopical":false,"mainPic":"/2018/11/27/055955d8e99bc0c809e588701fcdbb7ca976d6.jpg","productSellType":"CUSTOM","id":224408,"brand":"MYSTRIOUS","designerId":11886,"grossRatio":1,"productId":null,"isSubscribe":0,"orderPromotionId":null,"consults":0,"flashPromotionId":null,"minPrice":1580,"name":"Mysterious原创高级定制2018兔毛披肩修身高领长款针织裙（披肩+连衣裙）","collectioned":0,"maxPrice":1580,"orderPromotionType":null,"isAfter":1,"soonPromotion":{},"img":"/2018/11/27/055955d8e99bc0c809e588701fcdbb7ca976d6.jpg","originalPrice":1580,"isCart":1,"soonPrice":1580,"flashPrice":0,"categoryName":"连衣裙","colors":[{"img":"/2018/11/27/055952d8e99bc0c809e588701fcdbb7ca976d6.jpg","code":0,"groupId":0,"name":"颜色","id":1,"value":"灰色"}],"partnerSales":0,"searchFrom":null,"collagePrice":0,"sn":"MY2018121","class":"com.d2c.product.search.view.ProductVO","promotionType":null,"comments":0,"isSpot":false,"salePrice":1580,"secondRatio":0.2,"designer":"美佐","store":1,"collagePromotionId":null,"categoryId":1588,"mark":1}]
     * total : 2
     * index : 1
     * pageCount : 1
     * pageSize : 2
     * previous : false
     * next : false
     */

    private List<ProductDetailBean.DataBean.RecommendProductsBean> list;

    public List<ProductDetailBean.DataBean.RecommendProductsBean> getList() {
        return list;
    }

    public void setList(List<ProductDetailBean.DataBean.RecommendProductsBean> list) {
        this.list = list;
    }
}
