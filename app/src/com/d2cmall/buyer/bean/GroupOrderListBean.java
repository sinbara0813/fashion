package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rookie on 2018/6/27.
 */

public class GroupOrderListBean extends BaseBean implements Serializable{

    /**
     * data : {"myCollageList":{"next":false,"total":11,"previous":false,"index":1,"pageSize":20,"list":[{"productId":195201,"color":"黑色","orderSn":"CMO1530153568578","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":34,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530152484771","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":32,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530151578942","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":31,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530150939071","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":28,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530150233415","groupId":10,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530349273000,"groupSn":"G1530157486970","memberCount":2,"initiatorName":"D2C白菜","id":10,"headPic":"/member_head/59/859/9a62e649636545578ff0534b8461dbcc.jpg","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":859},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":26,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530149438582","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":25,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530105528266","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":24,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530157486967","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":16,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530157486967","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":15,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530157486967","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":14,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530157486967","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":7,"skuId":385720,"status":-2,"memberId":2908360}]}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * myCollageList : {"next":false,"total":11,"previous":false,"index":1,"pageSize":20,"list":[{"productId":195201,"color":"黑色","orderSn":"CMO1530153568578","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":34,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530152484771","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":32,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530151578942","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":31,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530150939071","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":28,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530150233415","groupId":10,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530349273000,"groupSn":"G1530157486970","memberCount":2,"initiatorName":"D2C白菜","id":10,"headPic":"/member_head/59/859/9a62e649636545578ff0534b8461dbcc.jpg","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":859},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":26,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530149438582","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":25,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530105528266","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":24,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530157486967","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":16,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530157486967","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":15,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530157486967","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":14,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530157486967","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":7,"skuId":385720,"status":-2,"memberId":2908360}]}
         */

        private CollageOrders collageOrders;

        public CollageOrders getMyCollageList() {
            return collageOrders;
        }

        public void setMyCollageList(CollageOrders collageOrders) {
            this.collageOrders = collageOrders;
        }

        public static class CollageOrders implements Serializable{
            /**
             * next : false
             * total : 11
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"productId":195201,"color":"黑色","orderSn":"CMO1530153568578","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":34,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530152484771","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":32,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530151578942","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":31,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530150939071","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":28,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530150233415","groupId":10,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530349273000,"groupSn":"G1530157486970","memberCount":2,"initiatorName":"D2C白菜","id":10,"headPic":"/member_head/59/859/9a62e649636545578ff0534b8461dbcc.jpg","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":859},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":26,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530149438582","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":25,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530105528266","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":24,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530157486967","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":16,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530157486967","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":15,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530157486967","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":14,"skuId":385720,"status":-2,"memberId":2908360},{"productId":195201,"color":"黑色","orderSn":"CMO1530157486967","groupId":11,"loginCode":"15888897624","memberName":null,"payParams":"ALIPAY,WXAPPPAY,WALLET","type":0,"headPic":null,"promotionId":6,"productName":"amorebkk ploy 泰国潮牌一字领露肩T恤","collageGroup":{"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360},"totalAmount":699,"productImage":"/2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg","totalPay":699,"size":"均码","statusName":"超时关闭","id":7,"skuId":385720,"status":-2,"memberId":2908360}]
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

            public static class ListBean implements Serializable{
                /**
                 * productId : 195201
                 * color : 黑色
                 * orderSn : CMO1530153568578
                 * groupId : 11
                 * loginCode : 15888897624
                 * memberName : null
                 * payParams : ALIPAY,WXAPPPAY,WALLET
                 * type : 0
                 * headPic : null
                 * promotionId : 6
                 * productName : amorebkk ploy 泰国潮牌一字领露肩T恤
                 * collageGroup : {"currentMemberCount":0,"productId":195201,"endDate":1530350111000,"groupSn":"G1530157486968","memberCount":2,"initiatorName":"考拉🐨","id":11,"headPic":"/app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea","payMemberCount":0,"promotionId":6,"status":4,"initiatorMemberId":2908360}
                 * totalAmount : 699.0
                 * productImage : /2018/04/14/0803285be43e95454564e66e4bf9501f634787.jpg
                 * totalPay : 699.0
                 * size : 均码
                 * statusName : 超时关闭
                 * id : 34
                 * skuId : 385720
                 * status : -2
                 * memberId : 2908360
                 */

                private int productId;//商品ID
                private String color;//颜色
                private String orderSn;//订单号
                private int groupId;//团ID
                private String loginCode;//登录账号
                private String memberName;//账户昵称
                private String payParams;//可用支付参数
                private int type;//不知道什么意思
                private String headPic;//头像
                private int promotionId;//
                private String productName;//商品名称
                private Long endPayTime;//最后支付结束时间

                public Long getEndPayTime() {
                    return endPayTime;
                }

                public void setEndPayTime(long endPayTime) {
                    this.endPayTime = endPayTime;
                }

                private CollageGroupBean collageGroup;//团队Bean
                private double totalAmount;//支付金额
                private String productImage;//商品图片
                private double totalPay;
                private String size;//尺码
                private String statusName;//状态文本
                private int id;//拼团活动id
                private int skuId;//skuid
                @SerializedName("status")
                private int statusX;//-8退款成功,-2超时关闭,-1待退款,1待支付,4拼团中,8拼团成功
                private int memberId;//会员id

                public int getProductId() {
                    return productId;
                }

                public void setProductId(int productId) {
                    this.productId = productId;
                }

                public String getColor() {
                    return color;
                }

                public void setColor(String color) {
                    this.color = color;
                }

                public String getOrderSn() {
                    return orderSn;
                }

                public void setOrderSn(String orderSn) {
                    this.orderSn = orderSn;
                }

                public int getGroupId() {
                    return groupId;
                }

                public void setGroupId(int groupId) {
                    this.groupId = groupId;
                }

                public String getLoginCode() {
                    return loginCode;
                }

                public void setLoginCode(String loginCode) {
                    this.loginCode = loginCode;
                }

                public String getMemberName() {
                    return memberName;
                }

                public void setMemberName(String memberName) {
                    this.memberName = memberName;
                }

                public String getPayParams() {
                    return payParams;
                }

                public void setPayParams(String payParams) {
                    this.payParams = payParams;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getHeadPic() {
                    return headPic;
                }

                public void setHeadPic(String headPic) {
                    this.headPic = headPic;
                }

                public int getPromotionId() {
                    return promotionId;
                }

                public void setPromotionId(int promotionId) {
                    this.promotionId = promotionId;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public CollageGroupBean getCollageGroup() {
                    return collageGroup;
                }

                public void setCollageGroup(CollageGroupBean collageGroup) {
                    this.collageGroup = collageGroup;
                }

                public double getTotalAmount() {
                    return totalAmount;
                }

                public void setTotalAmount(double totalAmount) {
                    this.totalAmount = totalAmount;
                }

                public String getProductImage() {
                    return productImage;
                }

                public void setProductImage(String productImage) {
                    this.productImage = productImage;
                }

                public double getTotalPay() {
                    return totalPay;
                }

                public void setTotalPay(double totalPay) {
                    this.totalPay = totalPay;
                }

                public String getSize() {
                    return size;
                }

                public void setSize(String size) {
                    this.size = size;
                }

                public String getStatusName() {
                    return statusName;
                }

                public void setStatusName(String statusName) {
                    this.statusName = statusName;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getSkuId() {
                    return skuId;
                }

                public void setSkuId(int skuId) {
                    this.skuId = skuId;
                }

                public int getStatusX() {
                    return statusX;
                }

                public void setStatusX(int statusX) {
                    this.statusX = statusX;
                }

                public int getMemberId() {
                    return memberId;
                }

                public void setMemberId(int memberId) {
                    this.memberId = memberId;
                }

                public static class CollageGroupBean {
                    /**
                     * currentMemberCount : 0
                     * productId : 195201
                     * endDate : 1530350111000
                     * groupSn : G1530157486968
                     * memberCount : 2
                     * initiatorName : 考拉🐨
                     * id : 11
                     * headPic : /app/a/18/06/14/9c8c621afcc15b796b9b04f2e633e6ea
                     * payMemberCount : 0
                     * promotionId : 6
                     * status : 4
                     * initiatorMemberId : 2908360
                     */

                    private int currentMemberCount;//当前参与进来的人数(包括未支付的人)
                    private int productId;//商品id
                    private long endDate;//团队解散的时间
                    private String groupSn;//团队编号
                    private int memberCount;//拼团需要的总人数
                    private String initiatorName;//团长名称
                    private int id;//团队id
                    private String headPic;//团长头像
                    private int payMemberCount;//已近支付的人数
                    private int promotionId;
                    @SerializedName("status")
                    private int statusX;
                    private int initiatorMemberId;//团长id

                    public int getCurrentMemberCount() {
                        return currentMemberCount;
                    }

                    public void setCurrentMemberCount(int currentMemberCount) {
                        this.currentMemberCount = currentMemberCount;
                    }

                    public int getProductId() {
                        return productId;
                    }

                    public void setProductId(int productId) {
                        this.productId = productId;
                    }

                    public long getEndDate() {
                        return endDate;
                    }

                    public void setEndDate(long endDate) {
                        this.endDate = endDate;
                    }

                    public String getGroupSn() {
                        return groupSn;
                    }

                    public void setGroupSn(String groupSn) {
                        this.groupSn = groupSn;
                    }

                    public int getMemberCount() {
                        return memberCount;
                    }

                    public void setMemberCount(int memberCount) {
                        this.memberCount = memberCount;
                    }

                    public String getInitiatorName() {
                        return initiatorName;
                    }

                    public void setInitiatorName(String initiatorName) {
                        this.initiatorName = initiatorName;
                    }

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getHeadPic() {
                        return headPic;
                    }

                    public void setHeadPic(String headPic) {
                        this.headPic = headPic;
                    }

                    public int getPayMemberCount() {
                        return payMemberCount;
                    }

                    public void setPayMemberCount(int payMemberCount) {
                        this.payMemberCount = payMemberCount;
                    }

                    public int getPromotionId() {
                        return promotionId;
                    }

                    public void setPromotionId(int promotionId) {
                        this.promotionId = promotionId;
                    }

                    public int getStatusX() {
                        return statusX;
                    }

                    public void setStatusX(int statusX) {
                        this.statusX = statusX;
                    }

                    public int getInitiatorMemberId() {
                        return initiatorMemberId;
                    }

                    public void setInitiatorMemberId(int initiatorMemberId) {
                        this.initiatorMemberId = initiatorMemberId;
                    }
                }
            }
        }
    }
}
