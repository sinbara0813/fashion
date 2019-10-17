package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 * Description : RefundBeanData
 */

public class RefundBeanData extends BaseBean{

    /**
     * data : {"reshipLogs":[{"logType":1,"creator":null,"createDate":1515028672000,"info":"申请退货单"}],"reship":{"backMobile":"4008403666","quantity":1,"orderSn":"Q15148830381458360","refundReason":"七天无理由退货","productSize":"L码","productImg":"/2016/10/11/033107037280d749050f1134d920eb3310af90.jpg","backAddress":"浙江省杭州市西湖区莲花峰路12号D2C","reshipSn":"R1515028819678","memo":"衣服","orderPayType":7,"productName":"ZeroSecond Lee Taewoo 简约设计斜插口袋长款外套","paymentType":"WALLET","tradeAmount":1646,"backConsignee":"D2C销退组","payType":"钱包支付","statusName":"正在申请退货","productColor":"卡其","id":51504,"evidences":["/app/c/18/01/04/42bffcfbb1514ad262d1aad430b258c8"],"refundId":110015,"productPrice":1777,"customerMemo":null,"createDate":"2018/01/04 09:20:20","statusCode":0},"refundLogs":[{"logType":1,"creator":null,"createDate":1515028672000,"info":"退货退款"}],"refund":{"quantity":1,"orderSn":"Q15148830381458360","backAccountSn":"2908360","refundReason":"退货退款","productSize":"L码","productImg":"/2016/10/11/033107037280d749050f1134d920eb3310af90.jpg","memo":"衣服","backAccountName":"P180115148828976723697","productName":"ZeroSecond Lee Taewoo 简约设计斜插口袋长款外套","applyAmount":1646,"paymentType":"WALLET","totalAmount":1646,"payType":"钱包支付","statusName":"正在申请退款","productColor":"卡其","id":110015,"evidences":null,"refundSn":"R1515028819684","backAccountType":"WALLET","productPrice":1777,"customerMemo":null,"createDate":"2018/01/04 09:20:20","statusCode":0}}
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
         * reshipLogs : [{"logType":1,"creator":null,"createDate":1515028672000,"info":"申请退货单"}]
         * reship : {"backMobile":"4008403666","quantity":1,"orderSn":"Q15148830381458360","refundReason":"七天无理由退货","productSize":"L码","productImg":"/2016/10/11/033107037280d749050f1134d920eb3310af90.jpg","backAddress":"浙江省杭州市西湖区莲花峰路12号D2C","reshipSn":"R1515028819678","memo":"衣服","orderPayType":7,"productName":"ZeroSecond Lee Taewoo 简约设计斜插口袋长款外套","paymentType":"WALLET","tradeAmount":1646,"backConsignee":"D2C销退组","payType":"钱包支付","statusName":"正在申请退货","productColor":"卡其","id":51504,"evidences":["/app/c/18/01/04/42bffcfbb1514ad262d1aad430b258c8"],"refundId":110015,"productPrice":1777,"customerMemo":null,"createDate":"2018/01/04 09:20:20","statusCode":0}
         * refundLogs : [{"logType":1,"creator":null,"createDate":1515028672000,"info":"退货退款"}]
         * refund : {"quantity":1,"orderSn":"Q15148830381458360","backAccountSn":"2908360","refundReason":"退货退款","productSize":"L码","productImg":"/2016/10/11/033107037280d749050f1134d920eb3310af90.jpg","memo":"衣服","backAccountName":"P180115148828976723697","productName":"ZeroSecond Lee Taewoo 简约设计斜插口袋长款外套","applyAmount":1646,"paymentType":"WALLET","totalAmount":1646,"payType":"钱包支付","statusName":"正在申请退款","productColor":"卡其","id":110015,"evidences":null,"refundSn":"R1515028819684","backAccountType":"WALLET","productPrice":1777,"customerMemo":null,"createDate":"2018/01/04 09:20:20","statusCode":0}
         */

        private ReshipBean reship;
        private RefundBean refund;
        private List<ReshipLogsBean> reshipLogs;
        private List<RefundLogsBean> refundLogs;

        public ReshipBean getReship() {
            return reship;
        }

        public void setReship(ReshipBean reship) {
            this.reship = reship;
        }

        public RefundBean getRefund() {
            return refund;
        }

        public void setRefund(RefundBean refund) {
            this.refund = refund;
        }

        public List<ReshipLogsBean> getReshipLogs() {
            return reshipLogs;
        }

        public void setReshipLogs(List<ReshipLogsBean> reshipLogs) {
            this.reshipLogs = reshipLogs;
        }

        public List<RefundLogsBean> getRefundLogs() {
            return refundLogs;
        }

        public void setRefundLogs(List<RefundLogsBean> refundLogs) {
            this.refundLogs = refundLogs;
        }

        public static class ReshipBean {
            public double getApplyAmount() {
                return applyAmount;
            }

            public void setApplyAmount(double applyAmount) {
                this.applyAmount = applyAmount;
            }

            /**
             * backMobile : 4008403666
             * quantity : 1
             * orderSn : Q15148830381458360
             * refundReason : 七天无理由退货
             * productSize : L码
             * productImg : /2016/10/11/033107037280d749050f1134d920eb3310af90.jpg
             * backAddress : 浙江省杭州市西湖区莲花峰路12号D2C
             * reshipSn : R1515028819678
             * memo : 衣服
             * orderPayType : 7
             * productName : ZeroSecond Lee Taewoo 简约设计斜插口袋长款外套
             * paymentType : WALLET
             * tradeAmount : 1646.0
             * backConsignee : D2C销退组
             * payType : 钱包支付
             * statusName : 正在申请退货
             * productColor : 卡其
             * id : 51504
             * evidences : ["/app/c/18/01/04/42bffcfbb1514ad262d1aad430b258c8"]
             * refundId : 110015
             * productPrice : 1777.0
             * customerMemo : null
             * createDate : 2018/01/04 09:20:20
             * statusCode : 0
             */
            private double applyAmount;
            private String backMobile;

            public BackAddressBean getBackAddress() {
                return backAddress;
            }

            public void setBackAddress(BackAddressBean backAddress) {
                this.backAddress = backAddress;
            }

            private BackAddressBean backAddress;
            private String backConsignee;
            private int quantity;
            private String orderSn;
            private String refundReason;
            private String productSize;
            private String productImg;
            private String reshipSn;
            private String memo;
            private int orderPayType;
            private String productName;
            private String paymentType;
            private double tradeAmount;
            private String payType;
            private String statusName;
            private String productColor;
            private int id;
            private int refundId;
            private double productPrice;
            private String customerMemo;
            private Date createDate;

            public long getDeliveryExpiredDay() {
                return deliveryExpiredDay;
            }

            public void setDeliveryExpiredDay(long deliveryExpiredDay) {
                this.deliveryExpiredDay = deliveryExpiredDay;
            }

            private long deliveryExpiredDay;
            private int statusCode;
            private ArrayList<String> evidences;
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
            public int getReceived() {
                return received;
            }

            public void setReceived(int received) {
                this.received = received;
            }

            private int received;
            public Date getCloseDate() {
                return closeDate;
            }

            public void setCloseDate(Date closeDate) {
                this.closeDate = closeDate;
            }

            private Date closeDate;
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

            public String getDeliveryCorpCode() {
                return deliveryCorpCode;
            }

            public void setDeliveryCorpCode(String deliveryCorpCode) {
                this.deliveryCorpCode = deliveryCorpCode;
            }

            private String deliveryCorpCode;
            private String deliverySn;
            private String deliveryCorpName;

            public int getActualQuantity() {
                return actualQuantity;
            }

            public void setActualQuantity(int actualQuantity) {
                this.actualQuantity = actualQuantity;
            }

            private int actualQuantity;
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



            public String getReshipSn() {
                return reshipSn;
            }

            public void setReshipSn(String reshipSn) {
                this.reshipSn = reshipSn;
            }

            public String getMemo() {
                return memo;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }

            public int getOrderPayType() {
                return orderPayType;
            }

            public void setOrderPayType(int orderPayType) {
                this.orderPayType = orderPayType;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getPaymentType() {
                return paymentType;
            }

            public void setPaymentType(String paymentType) {
                this.paymentType = paymentType;
            }

            public double getTradeAmount() {
                return tradeAmount;
            }

            public void setTradeAmount(double tradeAmount) {
                this.tradeAmount = tradeAmount;
            }

            public String getBackConsignee() {
                return backConsignee;
            }

            public void setBackConsignee(String backConsignee) {
                this.backConsignee = backConsignee;
            }

            public String getPayType() {
                return payType;
            }

            public void setPayType(String payType) {
                this.payType = payType;
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

            public int getRefundId() {
                return refundId;
            }

            public void setRefundId(int refundId) {
                this.refundId = refundId;
            }

            public double getProductPrice() {
                return productPrice;
            }

            public void setProductPrice(double productPrice) {
                this.productPrice = productPrice;
            }

            public String getCustomerMemo() {
                return customerMemo;
            }

            public void setCustomerMemo(String customerMemo) {
                this.customerMemo = customerMemo;
            }

            public Date getCreateDate() {
                return createDate;
            }

            public void setCreateDate(Date createDate) {
                this.createDate = createDate;
            }

            public int getStatusCode() {
                return statusCode;
            }

            public void setStatusCode(int statusCode) {
                this.statusCode = statusCode;
            }

            public ArrayList<String> getEvidences() {
                return evidences;
            }

            public void setEvidences(ArrayList<String> evidences) {
                this.evidences = evidences;
            }
        }

        public static class RefundBean {
            public String getBackMobile() {
                return backMobile;
            }

            public void setBackMobile(String backMobile) {
                this.backMobile = backMobile;
            }

            public String getBackAddress() {
                return backAddress;
            }

            public void setBackAddress(String backAddress) {
                this.backAddress = backAddress;
            }

            public String getBackConsignee() {
                return backConsignee;
            }

            public void setBackConsignee(String backConsignee) {
                this.backConsignee = backConsignee;
            }

            /**
             * quantity : 1
             * orderSn : Q15148830381458360
             * backAccountSn : 2908360
             * refundReason : 退货退款
             * productSize : L码
             * productImg : /2016/10/11/033107037280d749050f1134d920eb3310af90.jpg
             * memo : 衣服
             * backAccountName : P180115148828976723697
             * productName : ZeroSecond Lee Taewoo 简约设计斜插口袋长款外套
             * applyAmount : 1646.0
             * paymentType : WALLET
             * totalAmount : 1646.0
             * payType : 钱包支付
             * statusName : 正在申请退款
             * productColor : 卡其
             * id : 110015
             * evidences : null
             * refundSn : R1515028819684
             * backAccountType : WALLET
             * productPrice : 1777.0
             * customerMemo : null
             * createDate : 2018/01/04 09:20:20
             * statusCode : 0
             */
            private String backMobile;
            private String backAddress;
            private String backConsignee;
            private int quantity;
            private String orderSn;
            private String backAccountSn;
            private String refundReason;
            private String productSize;
            private String productImg;
            private String memo;
            private String backAccountName;
            private String productName;
            private double applyAmount;
            private String paymentType;
            private double totalAmount;
            private String payType;
            private String statusName;
            private String productColor;
            private int id;
            private ArrayList<String> evidences;
            private String refundSn;
            private String backAccountType;
            private double productPrice;
            private String customerMemo;
            private String createDate;
            private int statusCode;

            public Date getCloseDate() {
                return closeDate;
            }

            public void setCloseDate(Date closeDate) {
                this.closeDate = closeDate;
            }

            private Date closeDate;
            public String getPaySn() {
                return paySn;
            }

            public void setPaySn(String paySn) {
                this.paySn = paySn;
            }

            public Date getPayDate() {
                return payDate;
            }

            public void setPayDate(Date payDate) {
                this.payDate = payDate;
            }

            public double getPayMoney() {
                return payMoney;
            }

            public void setPayMoney(double payMoney) {
                this.payMoney = payMoney;
            }

            //            paySn   payDate  payMoney
            private String paySn;
            private Date payDate;
            private double payMoney;
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

            public String getBackAccountSn() {
                return backAccountSn;
            }

            public void setBackAccountSn(String backAccountSn) {
                this.backAccountSn = backAccountSn;
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

            public String getBackAccountName() {
                return backAccountName;
            }

            public void setBackAccountName(String backAccountName) {
                this.backAccountName = backAccountName;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public double getApplyAmount() {
                return applyAmount;
            }

            public void setApplyAmount(double applyAmount) {
                this.applyAmount = applyAmount;
            }

            public String getPaymentType() {
                return paymentType;
            }

            public void setPaymentType(String paymentType) {
                this.paymentType = paymentType;
            }

            public double getTotalAmount() {
                return totalAmount;
            }

            public void setTotalAmount(double totalAmount) {
                this.totalAmount = totalAmount;
            }

            public String getPayType() {
                return payType;
            }

            public void setPayType(String payType) {
                this.payType = payType;
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

            public ArrayList<String> getEvidences() {
                return evidences;
            }

            public void setEvidences(ArrayList<String> evidences) {
                this.evidences = evidences;
            }

            public String getRefundSn() {
                return refundSn;
            }

            public void setRefundSn(String refundSn) {
                this.refundSn = refundSn;
            }

            public String getBackAccountType() {
                return backAccountType;
            }

            public void setBackAccountType(String backAccountType) {
                this.backAccountType = backAccountType;
            }

            public double getProductPrice() {
                return productPrice;
            }

            public void setProductPrice(double productPrice) {
                this.productPrice = productPrice;
            }

            public String getCustomerMemo() {
                return customerMemo;
            }

            public void setCustomerMemo(String customerMemo) {
                this.customerMemo = customerMemo;
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

        public static class ReshipLogsBean implements Serializable{
            /**
             * logType : 1
             * creator : null
             * createDate : 1515028672000
             * info : 申请退货单
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

        public static class RefundLogsBean implements Serializable{
            /**
             * logType : 1
             * creator : null
             * createDate : 1515028672000
             * info : 退货退款
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
