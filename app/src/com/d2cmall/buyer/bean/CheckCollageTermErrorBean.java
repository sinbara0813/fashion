package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * Created by Administrator on 2018/7/2.
 * Description : CheckCollageTermErrorBean
 */

public class CheckCollageTermErrorBean extends BaseBean {

    /**
     * data : {"code":"1001","collageOrder":{"id":404,"createDate":1530502532000,"creator":null,"modifyDate":1530502532000,"lastModifyMan":null,"sn":"Q15305026741911353","groupId":319,"groupSn":"G1530502674191","promotionId":99,"status":1,"productId":168534,"productImage":"/2017/08/12/023907e52e58c7ae16c8bf3cb25bdfca23e10c.jpg","productName":"CANDY CRUSH小糖果系列指甲油多色可选","skuId":298243,"sp1":"{\"img\":\"/2017/08/12/023427d09b32cd217fbee5fa743b2d4dc8e4e9.jpg\",\"code\":\"0\",\"groupId\":0,\"name\":\"颜色\",\"id\":2,\"value\":\"浪漫紫\"}","sp2":"{\"img\":\"\",\"code\":\"0\",\"groupId\":0,\"name\":\"尺码\",\"id\":1,\"value\":\"11ml\"}","memberId":3031353,"memberName":"155489328","loginCode":"15548932896","headPic":"/app/a/18/05/03/a003e33a94bf2d6e45c41c4aec4045a9","type":1,"addressId":390478,"reciver":"1234","contact":"17666128216","province":"浙江省","city":"杭州市","district":"萧山区","address":"浙江省杭州市萧山区飞虹路美哉美城109","paymentId":null,"paymentAmount":0,"paySn":null,"payTime":null,"paymentType":null,"totalAmount":0.01,"shippingRates":0,"partnerId":null,"closeTime":null,"closeMemo":null,"drawee":null,"appTerminal":"APPANDROID","appVersion":"3.2.5","refundPaySn":null,"refundPaymentType":null,"refundMemo":null,"refundTime":null,"sizeValue":"11ml","colorValue":"浪漫紫","paidAmount":0.01,"colorId":2,"sizeId":1}}
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
         * code : 1001
         * collageOrder : {"id":404,"createDate":1530502532000,"creator":null,"modifyDate":1530502532000,"lastModifyMan":null,"sn":"Q15305026741911353","groupId":319,"groupSn":"G1530502674191","promotionId":99,"status":1,"productId":168534,"productImage":"/2017/08/12/023907e52e58c7ae16c8bf3cb25bdfca23e10c.jpg","productName":"CANDY CRUSH小糖果系列指甲油多色可选","skuId":298243,"sp1":"{\"img\":\"/2017/08/12/023427d09b32cd217fbee5fa743b2d4dc8e4e9.jpg\",\"code\":\"0\",\"groupId\":0,\"name\":\"颜色\",\"id\":2,\"value\":\"浪漫紫\"}","sp2":"{\"img\":\"\",\"code\":\"0\",\"groupId\":0,\"name\":\"尺码\",\"id\":1,\"value\":\"11ml\"}","memberId":3031353,"memberName":"155489328","loginCode":"15548932896","headPic":"/app/a/18/05/03/a003e33a94bf2d6e45c41c4aec4045a9","type":1,"addressId":390478,"reciver":"1234","contact":"17666128216","province":"浙江省","city":"杭州市","district":"萧山区","address":"浙江省杭州市萧山区飞虹路美哉美城109","paymentId":null,"paymentAmount":0,"paySn":null,"payTime":null,"paymentType":null,"totalAmount":0.01,"shippingRates":0,"partnerId":null,"closeTime":null,"closeMemo":null,"drawee":null,"appTerminal":"APPANDROID","appVersion":"3.2.5","refundPaySn":null,"refundPaymentType":null,"refundMemo":null,"refundTime":null,"sizeValue":"11ml","colorValue":"浪漫紫","paidAmount":0.01,"colorId":2,"sizeId":1}
         */

        private String code;
        private CollageOrderBean collageOrder;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public CollageOrderBean getCollageOrder() {
            return collageOrder;
        }

        public void setCollageOrder(CollageOrderBean collageOrder) {
            this.collageOrder = collageOrder;
        }

        public static class CollageOrderBean {
            /**
             * id : 404
             * createDate : 1530502532000
             * creator : null
             * modifyDate : 1530502532000
             * lastModifyMan : null
             * sn : Q15305026741911353
             * groupId : 319
             * groupSn : G1530502674191
             * promotionId : 99
             * status : 1
             * productId : 168534
             * productImage : /2017/08/12/023907e52e58c7ae16c8bf3cb25bdfca23e10c.jpg
             * productName : CANDY CRUSH小糖果系列指甲油多色可选
             * skuId : 298243
             * sp1 : {"img":"/2017/08/12/023427d09b32cd217fbee5fa743b2d4dc8e4e9.jpg","code":"0","groupId":0,"name":"颜色","id":2,"value":"浪漫紫"}
             * sp2 : {"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"11ml"}
             * memberId : 3031353
             * memberName : 155489328
             * loginCode : 15548932896
             * headPic : /app/a/18/05/03/a003e33a94bf2d6e45c41c4aec4045a9
             * type : 1
             * addressId : 390478
             * reciver : 1234
             * contact : 17666128216
             * province : 浙江省
             * city : 杭州市
             * district : 萧山区
             * address : 浙江省杭州市萧山区飞虹路美哉美城109
             * paymentId : null
             * paymentAmount : 0
             * paySn : null
             * payTime : null
             * paymentType : null
             * totalAmount : 0.01
             * shippingRates : 0
             * partnerId : null
             * closeTime : null
             * closeMemo : null
             * drawee : null
             * appTerminal : APPANDROID
             * appVersion : 3.2.5
             * refundPaySn : null
             * refundPaymentType : null
             * refundMemo : null
             * refundTime : null
             * sizeValue : 11ml
             * colorValue : 浪漫紫
             * paidAmount : 0.01
             * colorId : 2
             * sizeId : 1
             */

            private int groupId;

            public int getGroupId() {
                return groupId;
            }

            public void setGroupId(int groupId) {
                this.groupId = groupId;
            }

        }
    }
}
