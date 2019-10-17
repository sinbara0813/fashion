package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * 订单明细列表Bean类
 * Author: Blend
 * Date: 2016/06/14 09:30
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class OrderItemsBean extends BaseBean {

    /**
     * items : {"index":1,"pageSize":20,"total":4,"previous":false,"next":false,"list":[{"orderSn":"Q14593456050052527","type":"ordinary","paymentType":"COD","paymentTypeName":"货到付款","deliveryCorpCode":"shunfeng","deliveryCorpName":"顺丰速运","deliverySn":"783162803692","id":151529,"productPrice":278,"quantity":1,"promotionPrice":139,"totalPrice":278,"itemStatus":"交易完成","itemStatusCode":8,"skuSn":"JB0125420900202004","productId":123811,"skuId":167028,"productImg":"/model/1603/82d14e1b344079e4cdaec92491f223e1","productName":"WDMD 包贝尔  男女同款 搞怪逗趣款圆领长袖上衣","color":"黑色","size":"XL码","designerId":10389,"designer":"WDMD","isComment":1,"isCod":1,"aftering":"none","crowdItemId":0,"orderPromotionId":0,"goodPromotionId":887,"combinationPromotionId":0},{"orderSn":"Q14593456050052527","type":"ordinary","paymentType":"COD","paymentTypeName":"货到付款","deliveryCorpCode":"shunfeng","deliveryCorpName":"顺丰速运","deliverySn":"783166555898","id":151530,"productPrice":168,"quantity":1,"promotionPrice":84,"totalPrice":168,"itemStatus":"交易完成","itemStatusCode":8,"skuSn":"JB0125320900502004","productId":118190,"skuId":152119,"productImg":"/model/1511/7ef6d23d7e5384b3e8297d28c3dfaf87","productName":"WDMD 包贝尔 男款 极简街头主义黑色包字印花前后不对称下摆上衣","color":"黑色","size":"XL码","designerId":10389,"designer":"WDMD","isComment":1,"isCod":1,"aftering":"none","crowdItemId":0,"orderPromotionId":0,"goodPromotionId":887,"combinationPromotionId":0},{"orderSn":"Q14593456050052527","type":"ordinary","paymentType":"COD","paymentTypeName":"货到付款","deliveryCorpCode":"shunfeng","deliveryCorpName":"顺丰速运","deliverySn":"783162803692","id":151532,"productPrice":298,"quantity":1,"promotionPrice":149,"totalPrice":298,"itemStatus":"交易完成","itemStatusCode":8,"skuSn":"JB0125406000101504","productId":123812,"skuId":167032,"productImg":"/model/1603/103d6a45af0fe6a5a58b3f1b9d4be341","productName":"WDMD 包贝尔  简约款趣味印花休闲衬衫","color":"白色","size":"XL码","designerId":10389,"designer":"WDMD","isComment":1,"isCod":1,"aftering":"none","crowdItemId":0,"orderPromotionId":0,"goodPromotionId":887,"combinationPromotionId":0},{"orderSn":"Q14593456050052527","type":"ordinary","paymentType":"COD","paymentTypeName":"货到付款","deliveryCorpCode":"shunfeng","deliveryCorpName":"顺丰速运","deliverySn":"783168124574","id":151533,"productPrice":338,"quantity":1,"promotionPrice":169,"totalPrice":338,"itemStatus":"交易完成","itemStatusCode":8,"skuSn":"JB0125491100102099","productId":120465,"skuId":157894,"productImg":"/model/1603/75b3a2e7eb7955a44f40e598a8cf43a8","productName":"   WDMD 包贝尔 潮人时尚黑色背包","color":"黑色","size":"均码","designerId":10389,"designer":"WDMD","isComment":1,"isCod":1,"aftering":"none","crowdItemId":0,"orderPromotionId":0,"goodPromotionId":887,"combinationPromotionId":0}]}
     */

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * index : 1
         * pageSize : 20
         * total : 4
         * previous : false
         * next : false
         * list : [{"orderSn":"Q14593456050052527","type":"ordinary","paymentType":"COD","paymentTypeName":"货到付款","deliveryCorpCode":"shunfeng","deliveryCorpName":"顺丰速运","deliverySn":"783162803692","id":151529,"productPrice":278,"quantity":1,"promotionPrice":139,"totalPrice":278,"itemStatus":"交易完成","itemStatusCode":8,"skuSn":"JB0125420900202004","productId":123811,"skuId":167028,"productImg":"/model/1603/82d14e1b344079e4cdaec92491f223e1","productName":"WDMD 包贝尔  男女同款 搞怪逗趣款圆领长袖上衣","color":"黑色","size":"XL码","designerId":10389,"designer":"WDMD","isComment":1,"isCod":1,"aftering":"none","crowdItemId":0,"orderPromotionId":0,"goodPromotionId":887,"combinationPromotionId":0},{"orderSn":"Q14593456050052527","type":"ordinary","paymentType":"COD","paymentTypeName":"货到付款","deliveryCorpCode":"shunfeng","deliveryCorpName":"顺丰速运","deliverySn":"783166555898","id":151530,"productPrice":168,"quantity":1,"promotionPrice":84,"totalPrice":168,"itemStatus":"交易完成","itemStatusCode":8,"skuSn":"JB0125320900502004","productId":118190,"skuId":152119,"productImg":"/model/1511/7ef6d23d7e5384b3e8297d28c3dfaf87","productName":"WDMD 包贝尔 男款 极简街头主义黑色包字印花前后不对称下摆上衣","color":"黑色","size":"XL码","designerId":10389,"designer":"WDMD","isComment":1,"isCod":1,"aftering":"none","crowdItemId":0,"orderPromotionId":0,"goodPromotionId":887,"combinationPromotionId":0},{"orderSn":"Q14593456050052527","type":"ordinary","paymentType":"COD","paymentTypeName":"货到付款","deliveryCorpCode":"shunfeng","deliveryCorpName":"顺丰速运","deliverySn":"783162803692","id":151532,"productPrice":298,"quantity":1,"promotionPrice":149,"totalPrice":298,"itemStatus":"交易完成","itemStatusCode":8,"skuSn":"JB0125406000101504","productId":123812,"skuId":167032,"productImg":"/model/1603/103d6a45af0fe6a5a58b3f1b9d4be341","productName":"WDMD 包贝尔  简约款趣味印花休闲衬衫","color":"白色","size":"XL码","designerId":10389,"designer":"WDMD","isComment":1,"isCod":1,"aftering":"none","crowdItemId":0,"orderPromotionId":0,"goodPromotionId":887,"combinationPromotionId":0},{"orderSn":"Q14593456050052527","type":"ordinary","paymentType":"COD","paymentTypeName":"货到付款","deliveryCorpCode":"shunfeng","deliveryCorpName":"顺丰速运","deliverySn":"783168124574","id":151533,"productPrice":338,"quantity":1,"promotionPrice":169,"totalPrice":338,"itemStatus":"交易完成","itemStatusCode":8,"skuSn":"JB0125491100102099","productId":120465,"skuId":157894,"productImg":"/model/1603/75b3a2e7eb7955a44f40e598a8cf43a8","productName":"   WDMD 包贝尔 潮人时尚黑色背包","color":"黑色","size":"均码","designerId":10389,"designer":"WDMD","isComment":1,"isCod":1,"aftering":"none","crowdItemId":0,"orderPromotionId":0,"goodPromotionId":887,"combinationPromotionId":0}]
         */

        private OrderItemsEntity items;
        private int point;
        public int getPoint() {
            return point;
        }

        public void setPoint(int point) {
            this.point = point;
        }

        public OrderItemsEntity getItems() {
            return items;
        }

        public void setItems(OrderItemsEntity items) {
            this.items = items;
        }

        public static class OrderItemsEntity {
            private int index;
            private int pageSize;
            private int total;
            private boolean previous;
            private boolean next;

            private List<OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity> list;

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

            public boolean isNext() {
                return next;
            }

            public void setNext(boolean next) {
                this.next = next;
            }

            public List<OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity> getList() {
                return list;
            }

            public void setList(List<OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity> list) {
                this.list = list;
            }
        }

    }
}
