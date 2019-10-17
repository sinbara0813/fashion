package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.io.Serializable;
import java.util.List;

/**
 * 换货Bean类
 * Author: Blend
 * Date: 16/4/22 10:35
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ExchangesBean extends BaseBean implements Serializable{


    /**
     * data : {"exchanges":{"next":false,"total":6,"previous":false,"index":1,"pageSize":20,"list":[{"orderSn":"Q15065664271833142","refundReason":"收到商品破损","productSize":"M码","productImg":"/2016/08/19/21474162f211b51a7dc980bcad2cdf4197dc03.jpg","backAddress":{},"memo":"4","productName":"一口价商品-测试-请勿拍","exchangeSn":"R1506673677601","totalAmount":1000,"statusName":"等待用户收货","productColor":"蓝色","id":6583,"createDate":"2017/09/29 16:27:58","statusCode":4},{"orderSn":"Q15052944529023142","refundReason":"收到商品破损","productSize":"XXXXXL码","productImg":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","backAddress":{},"memo":"23232323","productName":"测试商品 请勿购买","exchangeSn":"R1505294472677","totalAmount":0.01,"statusName":"换货成功","productColor":"条文","id":6581,"createDate":"2017/09/13 17:21:13","statusCode":8},{"orderSn":"Q15052940866833142","refundReason":"商品需要维修","productSize":"XXXXXL码","productImg":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","backAddress":{},"memo":"11111","productName":"测试商品 请勿购买","exchangeSn":"R1505294130328","totalAmount":0.01,"statusName":"换货成功","productColor":"条文","id":6580,"createDate":"2017/09/13 17:15:30","statusCode":8},{"orderSn":"Q15052838065363142","refundReason":"商品需要维修","productSize":"均码","productImg":"/2017/08/15/103430fdd9237976be3881d866ce911ad7d629.jpg","backAddress":{},"memo":"121312312323","productName":"PinkBell 杏色丝绒出街款家居服","exchangeSn":"R1505283825782","totalAmount":418,"statusName":"换货成功","productColor":"杏色","id":6579,"createDate":"2017/09/13 14:23:46","statusCode":8},{"orderSn":"Q14824597567373142","refundReason":"收到商品破损","productSize":"XXXL码","productImg":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","backAddress":{"address":"浙江省杭州市萧山区瓜沥镇临港工业园区瓜港东路89号亚都蛋糕公司附属楼三楼送货上楼","consignee":"D2C销退组","mobile":"4008-403-666"},"memo":"测试","productName":"测试商品 请勿购买","exchangeSn":"R1482459902163","totalAmount":0.01,"statusName":"商家关闭换货","productColor":"条文","id":6178,"createDate":"2016/12/23 10:25:02","statusCode":-1},{"orderSn":"Q14823046921843142","refundReason":"收到商品破损","productSize":"XXXXXL码","productImg":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","backAddress":{"address":"浙江省杭州市萧山区瓜沥镇临港工业园区瓜港东路89号亚都蛋糕公司附属楼三楼送货上楼","consignee":"D2C销退组","mobile":"4008-403-666"},"memo":"张林测试换货流程PC端","productName":"测试商品 请勿购买","exchangeSn":"R1482306536040","totalAmount":0.01,"statusName":"换货成功","productColor":"条文","id":6094,"createDate":"2016/12/21 15:48:56","statusCode":8}]}}
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
         * exchanges : {"next":false,"total":6,"previous":false,"index":1,"pageSize":20,"list":[{"orderSn":"Q15065664271833142","refundReason":"收到商品破损","productSize":"M码","productImg":"/2016/08/19/21474162f211b51a7dc980bcad2cdf4197dc03.jpg","backAddress":{},"memo":"4","productName":"一口价商品-测试-请勿拍","exchangeSn":"R1506673677601","totalAmount":1000,"statusName":"等待用户收货","productColor":"蓝色","id":6583,"createDate":"2017/09/29 16:27:58","statusCode":4},{"orderSn":"Q15052944529023142","refundReason":"收到商品破损","productSize":"XXXXXL码","productImg":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","backAddress":{},"memo":"23232323","productName":"测试商品 请勿购买","exchangeSn":"R1505294472677","totalAmount":0.01,"statusName":"换货成功","productColor":"条文","id":6581,"createDate":"2017/09/13 17:21:13","statusCode":8},{"orderSn":"Q15052940866833142","refundReason":"商品需要维修","productSize":"XXXXXL码","productImg":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","backAddress":{},"memo":"11111","productName":"测试商品 请勿购买","exchangeSn":"R1505294130328","totalAmount":0.01,"statusName":"换货成功","productColor":"条文","id":6580,"createDate":"2017/09/13 17:15:30","statusCode":8},{"orderSn":"Q15052838065363142","refundReason":"商品需要维修","productSize":"均码","productImg":"/2017/08/15/103430fdd9237976be3881d866ce911ad7d629.jpg","backAddress":{},"memo":"121312312323","productName":"PinkBell 杏色丝绒出街款家居服","exchangeSn":"R1505283825782","totalAmount":418,"statusName":"换货成功","productColor":"杏色","id":6579,"createDate":"2017/09/13 14:23:46","statusCode":8},{"orderSn":"Q14824597567373142","refundReason":"收到商品破损","productSize":"XXXL码","productImg":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","backAddress":{"address":"浙江省杭州市萧山区瓜沥镇临港工业园区瓜港东路89号亚都蛋糕公司附属楼三楼送货上楼","consignee":"D2C销退组","mobile":"4008-403-666"},"memo":"测试","productName":"测试商品 请勿购买","exchangeSn":"R1482459902163","totalAmount":0.01,"statusName":"商家关闭换货","productColor":"条文","id":6178,"createDate":"2016/12/23 10:25:02","statusCode":-1},{"orderSn":"Q14823046921843142","refundReason":"收到商品破损","productSize":"XXXXXL码","productImg":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","backAddress":{"address":"浙江省杭州市萧山区瓜沥镇临港工业园区瓜港东路89号亚都蛋糕公司附属楼三楼送货上楼","consignee":"D2C销退组","mobile":"4008-403-666"},"memo":"张林测试换货流程PC端","productName":"测试商品 请勿购买","exchangeSn":"R1482306536040","totalAmount":0.01,"statusName":"换货成功","productColor":"条文","id":6094,"createDate":"2016/12/21 15:48:56","statusCode":8}]}
         */

        private ExchangeListBean exchanges;

        public ExchangeListBean getExchanges() {
            return exchanges;
        }

        public void setExchanges(ExchangeListBean exchanges) {
            this.exchanges = exchanges;
        }

        public static class ExchangeListBean implements Serializable{
            /**
             * next : false
             * total : 6
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"orderSn":"Q15065664271833142","refundReason":"收到商品破损","productSize":"M码","productImg":"/2016/08/19/21474162f211b51a7dc980bcad2cdf4197dc03.jpg","backAddress":{},"memo":"4","productName":"一口价商品-测试-请勿拍","exchangeSn":"R1506673677601","totalAmount":1000,"statusName":"等待用户收货","productColor":"蓝色","id":6583,"createDate":"2017/09/29 16:27:58","statusCode":4},{"orderSn":"Q15052944529023142","refundReason":"收到商品破损","productSize":"XXXXXL码","productImg":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","backAddress":{},"memo":"23232323","productName":"测试商品 请勿购买","exchangeSn":"R1505294472677","totalAmount":0.01,"statusName":"换货成功","productColor":"条文","id":6581,"createDate":"2017/09/13 17:21:13","statusCode":8},{"orderSn":"Q15052940866833142","refundReason":"商品需要维修","productSize":"XXXXXL码","productImg":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","backAddress":{},"memo":"11111","productName":"测试商品 请勿购买","exchangeSn":"R1505294130328","totalAmount":0.01,"statusName":"换货成功","productColor":"条文","id":6580,"createDate":"2017/09/13 17:15:30","statusCode":8},{"orderSn":"Q15052838065363142","refundReason":"商品需要维修","productSize":"均码","productImg":"/2017/08/15/103430fdd9237976be3881d866ce911ad7d629.jpg","backAddress":{},"memo":"121312312323","productName":"PinkBell 杏色丝绒出街款家居服","exchangeSn":"R1505283825782","totalAmount":418,"statusName":"换货成功","productColor":"杏色","id":6579,"createDate":"2017/09/13 14:23:46","statusCode":8},{"orderSn":"Q14824597567373142","refundReason":"收到商品破损","productSize":"XXXL码","productImg":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","backAddress":{"address":"浙江省杭州市萧山区瓜沥镇临港工业园区瓜港东路89号亚都蛋糕公司附属楼三楼送货上楼","consignee":"D2C销退组","mobile":"4008-403-666"},"memo":"测试","productName":"测试商品 请勿购买","exchangeSn":"R1482459902163","totalAmount":0.01,"statusName":"商家关闭换货","productColor":"条文","id":6178,"createDate":"2016/12/23 10:25:02","statusCode":-1},{"orderSn":"Q14823046921843142","refundReason":"收到商品破损","productSize":"XXXXXL码","productImg":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","backAddress":{"address":"浙江省杭州市萧山区瓜沥镇临港工业园区瓜港东路89号亚都蛋糕公司附属楼三楼送货上楼","consignee":"D2C销退组","mobile":"4008-403-666"},"memo":"张林测试换货流程PC端","productName":"测试商品 请勿购买","exchangeSn":"R1482306536040","totalAmount":0.01,"statusName":"换货成功","productColor":"条文","id":6094,"createDate":"2016/12/21 15:48:56","statusCode":8}]
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

            public static class ListBean implements Identifiable,Serializable{
                /**
                 * orderSn : Q15065664271833142
                 * refundReason : 收到商品破损
                 * productSize : M码
                 * productImg : /2016/08/19/21474162f211b51a7dc980bcad2cdf4197dc03.jpg
                 * backAddress : {}
                 * memo : 4
                 * productName : 一口价商品-测试-请勿拍
                 * exchangeSn : R1506673677601
                 * totalAmount : 1000.0
                 * statusName : 等待用户收货
                 * productColor : 蓝色
                 * id : 6583
                 * createDate : 2017/09/29 16:27:58
                 * statusCode : 4
                 */

                private String orderSn;
                private String refundReason;
                private String productSize;
                private String productImg;
                private BackAddressBean backAddress;
                private String memo;
                private String productName;
                private String exchangeSn;
                private double totalAmount;
                private String statusName;
                private String productColor;
                private long id;
                private String createDate;
                private int statusCode;

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

                public BackAddressBean getBackAddress() {
                    return backAddress;
                }

                public void setBackAddress(BackAddressBean backAddress) {
                    this.backAddress = backAddress;
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

                public static class BackAddressBean implements Serializable{
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
}
