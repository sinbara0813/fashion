package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/5.
 * Description : ExchangeProductDetialBean
 */

public class ExchangeProductDetialBean extends BaseBean {

    /**
     * data : {"exchangeLogs":[{"logType":0,"creator":"韩仁兵","createDate":1515131483000,"info":"客服代用户换货"}],"exchange":{"backMobile":"4008403666","quantity":1,"orderSn":"Q15150293189398360","refundReason":"商品需要维修","productSize":"L码","productImg":"/2017/11/07/013333fd5efdf4241c38c3fd9a9287fc266c53.jpg","backAddress":"浙江省杭州市西湖区莲花峰路12号D2C","memo":"换货楼","productName":"JE.LES 唐佳 瑜伽兔 立体兔耳 兔子卫衣（男女同款）","exchangeSn":"R1515131635774","totalAmount":980,"backConsignee":"D2C销退组","statusName":"正在申请换货","productColor":"黑色","id":6657,"productPrice":980,"createDate":"2018/01/05 13:53:56","statusCode":0}}
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
         * exchangeLogs : [{"logType":0,"creator":"韩仁兵","createDate":1515131483000,"info":"客服代用户换货"}]
         * exchange : {"backMobile":"4008403666","quantity":1,"orderSn":"Q15150293189398360","refundReason":"商品需要维修","productSize":"L码","productImg":"/2017/11/07/013333fd5efdf4241c38c3fd9a9287fc266c53.jpg","backAddress":"浙江省杭州市西湖区莲花峰路12号D2C","memo":"换货楼","productName":"JE.LES 唐佳 瑜伽兔 立体兔耳 兔子卫衣（男女同款）","exchangeSn":"R1515131635774","totalAmount":980,"backConsignee":"D2C销退组","statusName":"正在申请换货","productColor":"黑色","id":6657,"productPrice":980,"createDate":"2018/01/05 13:53:56","statusCode":0}
         */

        private ExchangeBean exchange;
        private List<ExchangeLogsBean> exchangeLogs;

        public ExchangeBean getExchange() {
            return exchange;
        }

        public void setExchange(ExchangeBean exchange) {
            this.exchange = exchange;
        }

        public List<ExchangeLogsBean> getExchangeLogs() {
            return exchangeLogs;
        }

        public void setExchangeLogs(List<ExchangeLogsBean> exchangeLogs) {
            this.exchangeLogs = exchangeLogs;
        }

        public static class ExchangeBean implements Serializable{
            /**
             * backMobile : 4008403666
             * quantity : 1
             * orderSn : Q15150293189398360
             * refundReason : 商品需要维修
             * productSize : L码
             * productImg : /2017/11/07/013333fd5efdf4241c38c3fd9a9287fc266c53.jpg
             * backAddress : 浙江省杭州市西湖区莲花峰路12号D2C
             * memo : 换货楼
             * productName : JE.LES 唐佳 瑜伽兔 立体兔耳 兔子卫衣（男女同款）
             * exchangeSn : R1515131635774
             * totalAmount : 980.0
             * backConsignee : D2C销退组
             * statusName : 正在申请换货
             * productColor : 黑色
             * id : 6657
             * productPrice : 980.0
             * createDate : 2018/01/05 13:53:56
             * statusCode : 0
             */

            private String backMobile;
            private int quantity;
            private String orderSn;
            private String refundReason;
            private String productSize;
            private String productImg;

            public BackAddressBean getBackAddress() {
                return backAddress;
            }

            public void setBackAddress(BackAddressBean backAddress) {
                this.backAddress = backAddress;
            }

            private BackAddressBean backAddress;
            private String memo;
            private String productName;
            private String exchangeSn;
            private double totalAmount;
            private String backConsignee;
            private String statusName;
            private String productColor;
            private int id;
            private double productPrice;
            private String createDate;
            private int statusCode;
            public Date getCloseDate() {
                return closeDate;
            }
            public static class BackAddressBean {
                /**
                 * address : 浙江省杭州市西湖区莲花峰路12号D2C
                 * consignee : D2C销退组
                 * mobile : 4008403666
                 */

                private String address;
                private String consignee;
                private String mobile;

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getConsignee() {
                    return consignee;
                }

                public void setConsignee(String consignee) {
                    this.consignee = consignee;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }
            }

            public void setCloseDate(Date closeDate) {
                this.closeDate = closeDate;
            }

            private Date closeDate;
            public String getDeliveryCorpCode() {
                return deliveryCorpCode;
            }

            public void setDeliveryCorpCode(String deliveryCorpCode) {
                this.deliveryCorpCode = deliveryCorpCode;
            }

            public String getDeliverySn() {
                return deliverySn;
            }

            public void setDeliverySn(String deliverySn) {
                this.deliverySn = deliverySn;
            }

            public String getDeliveryCorpName() {
                return deliveryCorpName;
            }

            public void setDeliveryCorpName(String deliveryCorpName) {
                this.deliveryCorpName = deliveryCorpName;
            }

            private String deliveryCorpCode;
            private String deliverySn;
            private String deliveryCorpName;

            public String getProductSn() {
                return productSn;
            }

            public void setProductSn(String productSn) {
                this.productSn = productSn;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            private String productSn;//换货新的sn
            private String color;//换货新的颜色

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            private String size;//换货新的尺寸
            public String getExchangeDeliveryCode() {
                return exchangeDeliveryCode;
            }

            public void setExchangeDeliveryCode(String exchangeDeliveryCode) {
                this.exchangeDeliveryCode = exchangeDeliveryCode;
            }

            public String getExchangeDeliverySn() {
                return exchangeDeliverySn;
            }

            public void setExchangeDeliverySn(String exchangeDeliverySn) {
                this.exchangeDeliverySn = exchangeDeliverySn;
            }

            public String getExchangeDeliveryCorp() {
                return exchangeDeliveryCorp;
            }

            public void setExchangeDeliveryCorp(String exchangeDeliveryCorp) {
                this.exchangeDeliveryCorp = exchangeDeliveryCorp;
            }

            private String exchangeDeliveryCode;
            private String exchangeDeliverySn;
            private String exchangeDeliveryCorp;
            public String getCustomerMemo() {
                return customerMemo;
            }

            public void setCustomerMemo(String customerMemo) {
                this.customerMemo = customerMemo;
            }

            private String customerMemo;
            public String getBackMobile() {
                return backMobile;
            }

            public void setBackMobile(String backMobile) {
                this.backMobile = backMobile;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getOrderSn() {
                return orderSn;
            }

            public void setOrderSn(String orderSn) {
                this.orderSn = orderSn;
            }

            public String getRefundReason() {
                return refundReason;
            }

            public void setRefundReason(String refundReason) {
                this.refundReason = refundReason;
            }

            public String getProductSize() {
                return productSize;
            }

            public void setProductSize(String productSize) {
                this.productSize = productSize;
            }

            public String getProductImg() {
                return productImg;
            }

            public void setProductImg(String productImg) {
                this.productImg = productImg;
            }



            public String getMemo() {
                return memo;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getExchangeSn() {
                return exchangeSn;
            }

            public void setExchangeSn(String exchangeSn) {
                this.exchangeSn = exchangeSn;
            }

            public double getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(double totalAmount) {
                this.totalAmount = totalAmount;
            }

            public String getBackConsignee() {
                return backConsignee;
            }

            public void setBackConsignee(String backConsignee) {
                this.backConsignee = backConsignee;
            }

            public String getStatusName() {
                return statusName;
            }

            public void setStatusName(String statusName) {
                this.statusName = statusName;
            }

            public String getProductColor() {
                return productColor;
            }

            public void setProductColor(String productColor) {
                this.productColor = productColor;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public double getProductPrice() {
                return productPrice;
            }

            public void setProductPrice(double productPrice) {
                this.productPrice = productPrice;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public int getStatusCode() {
                return statusCode;
            }

            public void setStatusCode(int statusCode) {
                this.statusCode = statusCode;
            }
        }

        public static class ExchangeLogsBean implements Serializable{
            /**
             * logType : 0
             * creator : 韩仁兵
             * createDate : 1515131483000
             * info : 客服代用户换货
             */

            private int logType;
            private String creator;
            private Date createDate;
            private String info;

            public int getLogType() {
                return logType;
            }

            public void setLogType(int logType) {
                this.logType = logType;
            }

            public String getCreator() {
                return creator;
            }

            public void setCreator(String creator) {
                this.creator = creator;
            }

            public Date getCreateDate() {
                return createDate;
            }

            public void setCreateDate(Date createDate) {
                this.createDate = createDate;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }
        }
    }
}
