package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 退款退货Bean类
 * Author: Blend
 * Date: 16/4/22 10:35
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ReshipsBean extends BaseBean implements Serializable{

    /**
     * data : {"reships":{"next":false,"total":3,"previous":false,"index":1,"pageSize":20,"list":[{"orderSn":"Q15051118923245965","refundReason":"商品质量问题","backAccountSn":"2865965","productSize":"组合装","productImg":"/2017/08/22/103846ac1d974c7c8b6578134d7a1a25097a9c.jpg","backAddress":{"address":"浙江省杭州市西湖区莲花峰路12号D2C","consignee":"D2C销退组","mobile":"4008403666"},"reshipSn":"R1505111974395","memo":"","orderPayType":7,"backAccountName":"P170915051115201979478","productName":"鸢苏 睡莲丰润柔顺洗护套组","paymentType":"WALLET","tradeAmount":734,"totalAmount":0,"payType":"钱包支付","statusName":"退款成功","productColor":"组合装","id":45442,"refundId":97977,"backAccountType":"WALLET","createDate":"2017/09/11 14:39:34","statusCode":8},{"orderSn":"Q15049412646025965","refundReason":"收到商品与描述不符","backAccountSn":"2865965","productSize":"M","productImg":"/2017/08/21/05294823d73155280b5d3d2bffa9d6190502d6.jpg","backAddress":{"address":"浙江省杭州市西湖区莲花峰路12号D2C","consignee":"D2C销退组","mobile":"4008403666"},"reshipSn":"R1505111206447","memo":"测试","orderPayType":7,"backAccountName":"P170915049408996639478","productName":"Awaylee 李薇 云朵系列 立体动感的网纱花边卫衣","paymentType":"WALLET","tradeAmount":1198,"totalAmount":0,"payType":"钱包支付","statusName":"正在申请退货","productColor":"黑白","id":45441,"refundId":97975,"backAccountType":"WALLET","createDate":"2017/09/11 14:26:46","statusCode":0},{"orderSn":"Q15049419671295965","refundReason":"七天无理由退货","backAccountSn":"2865965","productSize":"M","productImg":"/2017/08/23/08043285ea7f4d616636d97558c0aab9f1f2b4.jpg","backAddress":{"address":"浙江省杭州市西湖区莲花峰路12号D2C","consignee":"D2C销退组","mobile":"4008403666"},"reshipSn":"R1505111017799","memo":"测试","orderPayType":7,"backAccountName":"P170915049416017899478","productName":"CHABER C+ 刘俊廷C.T.LIU  李溪芮 同款 宝蓝色连帽长袖卫衣","paymentType":"WALLET","tradeAmount":980,"totalAmount":0,"payType":"钱包支付","statusName":"用户取消退货","productColor":"宝蓝","id":45440,"refundId":97974,"backAccountType":"WALLET","createDate":"2017/09/11 14:23:38","statusCode":-2}]}}
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
         * reships : {"next":false,"total":3,"previous":false,"index":1,"pageSize":20,"list":[{"orderSn":"Q15051118923245965","refundReason":"商品质量问题","backAccountSn":"2865965","productSize":"组合装","productImg":"/2017/08/22/103846ac1d974c7c8b6578134d7a1a25097a9c.jpg","backAddress":{"address":"浙江省杭州市西湖区莲花峰路12号D2C","consignee":"D2C销退组","mobile":"4008403666"},"reshipSn":"R1505111974395","memo":"","orderPayType":7,"backAccountName":"P170915051115201979478","productName":"鸢苏 睡莲丰润柔顺洗护套组","paymentType":"WALLET","tradeAmount":734,"totalAmount":0,"payType":"钱包支付","statusName":"退款成功","productColor":"组合装","id":45442,"refundId":97977,"backAccountType":"WALLET","createDate":"2017/09/11 14:39:34","statusCode":8},{"orderSn":"Q15049412646025965","refundReason":"收到商品与描述不符","backAccountSn":"2865965","productSize":"M","productImg":"/2017/08/21/05294823d73155280b5d3d2bffa9d6190502d6.jpg","backAddress":{"address":"浙江省杭州市西湖区莲花峰路12号D2C","consignee":"D2C销退组","mobile":"4008403666"},"reshipSn":"R1505111206447","memo":"测试","orderPayType":7,"backAccountName":"P170915049408996639478","productName":"Awaylee 李薇 云朵系列 立体动感的网纱花边卫衣","paymentType":"WALLET","tradeAmount":1198,"totalAmount":0,"payType":"钱包支付","statusName":"正在申请退货","productColor":"黑白","id":45441,"refundId":97975,"backAccountType":"WALLET","createDate":"2017/09/11 14:26:46","statusCode":0},{"orderSn":"Q15049419671295965","refundReason":"七天无理由退货","backAccountSn":"2865965","productSize":"M","productImg":"/2017/08/23/08043285ea7f4d616636d97558c0aab9f1f2b4.jpg","backAddress":{"address":"浙江省杭州市西湖区莲花峰路12号D2C","consignee":"D2C销退组","mobile":"4008403666"},"reshipSn":"R1505111017799","memo":"测试","orderPayType":7,"backAccountName":"P170915049416017899478","productName":"CHABER C+ 刘俊廷C.T.LIU  李溪芮 同款 宝蓝色连帽长袖卫衣","paymentType":"WALLET","tradeAmount":980,"totalAmount":0,"payType":"钱包支付","statusName":"用户取消退货","productColor":"宝蓝","id":45440,"refundId":97974,"backAccountType":"WALLET","createDate":"2017/09/11 14:23:38","statusCode":-2}]}
         */

        private ReshipsDataBean reships;

        public ReshipsDataBean getReships() {
            return reships;
        }

        public void setReships(ReshipsDataBean reships) {
            this.reships = reships;
        }

        public static class ReshipsDataBean implements Serializable{
            /**
             * next : false
             * total : 3
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"orderSn":"Q15051118923245965","refundReason":"商品质量问题","backAccountSn":"2865965","productSize":"组合装","productImg":"/2017/08/22/103846ac1d974c7c8b6578134d7a1a25097a9c.jpg","backAddress":{"address":"浙江省杭州市西湖区莲花峰路12号D2C","consignee":"D2C销退组","mobile":"4008403666"},"reshipSn":"R1505111974395","memo":"","orderPayType":7,"backAccountName":"P170915051115201979478","productName":"鸢苏 睡莲丰润柔顺洗护套组","paymentType":"WALLET","tradeAmount":734,"totalAmount":0,"payType":"钱包支付","statusName":"退款成功","productColor":"组合装","id":45442,"refundId":97977,"backAccountType":"WALLET","createDate":"2017/09/11 14:39:34","statusCode":8},{"orderSn":"Q15049412646025965","refundReason":"收到商品与描述不符","backAccountSn":"2865965","productSize":"M","productImg":"/2017/08/21/05294823d73155280b5d3d2bffa9d6190502d6.jpg","backAddress":{"address":"浙江省杭州市西湖区莲花峰路12号D2C","consignee":"D2C销退组","mobile":"4008403666"},"reshipSn":"R1505111206447","memo":"测试","orderPayType":7,"backAccountName":"P170915049408996639478","productName":"Awaylee 李薇 云朵系列 立体动感的网纱花边卫衣","paymentType":"WALLET","tradeAmount":1198,"totalAmount":0,"payType":"钱包支付","statusName":"正在申请退货","productColor":"黑白","id":45441,"refundId":97975,"backAccountType":"WALLET","createDate":"2017/09/11 14:26:46","statusCode":0},{"orderSn":"Q15049419671295965","refundReason":"七天无理由退货","backAccountSn":"2865965","productSize":"M","productImg":"/2017/08/23/08043285ea7f4d616636d97558c0aab9f1f2b4.jpg","backAddress":{"address":"浙江省杭州市西湖区莲花峰路12号D2C","consignee":"D2C销退组","mobile":"4008403666"},"reshipSn":"R1505111017799","memo":"测试","orderPayType":7,"backAccountName":"P170915049416017899478","productName":"CHABER C+ 刘俊廷C.T.LIU  李溪芮 同款 宝蓝色连帽长袖卫衣","paymentType":"WALLET","tradeAmount":980,"totalAmount":0,"payType":"钱包支付","statusName":"用户取消退货","productColor":"宝蓝","id":45440,"refundId":97974,"backAccountType":"WALLET","createDate":"2017/09/11 14:23:38","statusCode":-2}]
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

            public static class ListBean implements Serializable {
                /**
                 * orderSn : Q15051118923245965
                 * refundReason : 商品质量问题
                 * backAccountSn : 2865965
                 * productSize : 组合装
                 * productImg : /2017/08/22/103846ac1d974c7c8b6578134d7a1a25097a9c.jpg
                 * backAddress : {"address":"浙江省杭州市西湖区莲花峰路12号D2C","consignee":"D2C销退组","mobile":"4008403666"}
                 * reshipSn : R1505111974395
                 * memo :
                 * orderPayType : 7
                 * backAccountName : P170915051115201979478
                 * productName : 鸢苏 睡莲丰润柔顺洗护套组
                 * paymentType : WALLET
                 * tradeAmount : 734
                 * totalAmount : 0
                 * payType : 钱包支付
                 * statusName : 退款成功
                 * productColor : 组合装
                 * id : 45442
                 * refundId : 97977
                 * backAccountType : WALLET
                 * createDate : 2017/09/11 14:39:34
                 * statusCode : 8
                 */

                private String orderSn;
                private String refundReason;
                private String backAccountSn;
                private String productSize;
                private String productImg;
                private String reshipSn;
                private String memo;
                private int orderPayType;
                private String backAccountName;
                private String productName;
                private String paymentType;
                private double tradeAmount;
                private double totalAmount;
                private String payType;
                private String statusName;
                private String productColor;
                private long id;
                private long refundId;
                private String backAccountType;
                private String createDate;
                private int statusCode;

                public int getReceived() {
                    return received;
                }

                public void setReceived(int received) {
                    this.received = received;
                }

                private int received;
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

                public String getBackAccountSn() {
                    return backAccountSn;
                }

                public void setBackAccountSn(String backAccountSn) {
                    this.backAccountSn = backAccountSn;
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

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public long getRefundId() {
                    return refundId;
                }

                public void setRefundId(long refundId) {
                    this.refundId = refundId;
                }

                public String getBackAccountType() {
                    return backAccountType;
                }

                public void setBackAccountType(String backAccountType) {
                    this.backAccountType = backAccountType;
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
        }
    }
}
