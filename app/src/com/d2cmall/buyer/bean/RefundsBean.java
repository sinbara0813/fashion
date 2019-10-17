package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * 退款Bean类
 * Author: Blend
 * Date: 16/4/22 14:28
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class RefundsBean extends BaseBean {

    /**
     * refunds : {"index":1,"pageSize":20,"total":16,"previous":false,"next":false,"list":[{"id":23792,"createDate":"2016/03/08 11:06:56","refundSn":"R1457406416419","orderSn":"Q14574061866119336","statusName":"退款成功","statusCode":8,"productName":"测试商品","productImg":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","productColor":"条文","productSize":"S码","totalAmount":0.01,"applyAmount":0.01,"backAccountName":"2016030821001004370246969997","payType":"支付宝"},{"id":13796,"createDate":"2015/11/03 10:49:11","refundSn":"R1446518951774","orderSn":"Q14459238347629336","statusName":"退款成功","statusCode":8,"productName":"测试商品","productImg":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","productColor":"条文","productSize":"S码","totalAmount":0.01,"applyAmount":0,"backAccountName":"1008250760201510271351317503","payType":"微信支付"},{"id":12670,"createDate":"2015/11/01 00:36:45","refundSn":"R1446309340753","orderSn":"Q14463091124659336","statusName":"用户取消退款","statusCode":-2,"productName":"测试商品","productImg":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","productColor":"条文","productSize":"S码","totalAmount":0,"applyAmount":0.01,"backAccountName":"1008250760201511011409433144","payType":"微信支付"},{"id":4695,"createDate":"2015/09/16 23:22:55","refundSn":"R1442416951783","orderSn":"Q14424167954489336","statusName":"退款成功","statusCode":8,"productName":"气味图书馆 娄楠石 城市系列 上海 香水","productImg":"/pi/1506/31aeb02e30e62f60436924024e5f0721","productColor":"透明","productSize":"均码","totalAmount":95,"applyAmount":95,"payType":"支付宝"},{"id":4693,"createDate":"2015/09/16 23:01:36","refundSn":"R1442415672873","orderSn":"Q14424150853409336","statusName":"退款成功","statusCode":8,"productName":"almond rocks ska 红色蓝白条纹短袜礼盒","productImg":"/pi/1507/d810368fd3e4cbae2e1affe00d19da55","productColor":"多彩","productSize":"均码","totalAmount":88,"applyAmount":88,"payType":"支付宝"},{"id":2981,"createDate":"2015/08/26 18:09:26","refundSn":"R1440583766820","orderSn":"Q14405836538289336","statusName":"退款成功","statusCode":8,"productName":"气味图书馆 娄楠石 城市系列 北京 香水","productImg":"/pi/1506/d16b157ab96e210d11ec653d18c4d5b1","productColor":"透明","productSize":"均码","totalAmount":145,"applyAmount":145,"payType":"支付宝"},{"id":3101,"createDate":"2015/08/26 21:19:53","refundSn":"R1440595193164","orderSn":"Q14405877176019336","statusName":"退款成功","statusCode":8,"productName":"almond rocks ska 蓝黄青紋短袜","productImg":"/pi/1507/39863f23761681d4acdd94a610bf1120","productColor":"多彩","productSize":"均码","totalAmount":32,"applyAmount":32,"payType":"支付宝"},{"id":3125,"createDate":"2015/08/26 21:38:34","refundSn":"R1440596314930","orderSn":"Q14405961180919336","statusName":"退款成功","statusCode":8,"productName":"almond rocks 设计师ska  多彩红黄底条纹 中筒袜礼盒","productImg":"/pi/1507/a46716062fbe91dfd8f0e092ce5f662f","productColor":"多彩","productSize":"均码","totalAmount":96,"applyAmount":96,"payType":"支付宝"},{"id":2504,"createDate":"2015/08/21 19:59:51","refundSn":"R1440158391460","orderSn":"Q14399867244029336","statusName":"退款成功","statusCode":8,"productName":"苣毒    Daisy  极致简约个性系  粉色小尾巴连衣裙","productImg":"/pi/1506/da9ad824785e3b59c04fc86c3f829a5e","productColor":"粉色","productSize":"S码","totalAmount":269,"applyAmount":269,"payType":"支付宝"},{"id":2483,"createDate":"2015/08/21 14:08:48","refundSn":"R1440137328604","orderSn":"Q14399867244029336","statusName":"商家拒绝退款","statusCode":-1,"productName":"苣毒    Daisy  极致简约个性系  粉色小尾巴连衣裙","productImg":"/pi/1506/da9ad824785e3b59c04fc86c3f829a5e","productColor":"粉色","productSize":"S码","totalAmount":269,"applyAmount":269,"payType":"支付宝"},{"id":1947,"createDate":"2015/07/28 16:49:24","refundSn":"R1438073364354","orderSn":"Q14380724514439336","statusName":"退款成功","statusCode":8,"productName":"almond rocks 设计师ska 英伦深蓝底红波点 中筒袜","productImg":"/pi/1507/c59c70bc7781bd5f8402bcb438c7dab4","productColor":"蓝色","productSize":"均码","totalAmount":36,"applyAmount":36,"payType":"支付宝"},{"id":1949,"createDate":"2015/07/28 17:53:47","refundSn":"R1438077227375","orderSn":"Q14380769711859336","statusName":"退款成功","statusCode":8,"productName":"almond rocks 设计师ska 英伦绿红白条纹 中筒袜","productImg":"/pi/1507/b521078ef2bca3f5ae009b73cf62e82b","productColor":"绿色","productSize":"均码","totalAmount":36,"applyAmount":36,"payType":"支付宝"},{"id":1951,"createDate":"2015/07/28 18:33:26","refundSn":"R1438079606714","orderSn":"Q14380737766209336","statusName":"退款成功","statusCode":8,"productName":"almond rocks 设计师ska  英伦深蓝红黄条纹 中筒袜","productImg":"/pi/1507/a26ea0ef284367abdbf5d6a8ca4333ed","productColor":"蓝色","productSize":"均码","totalAmount":36,"applyAmount":36,"payType":"支付宝"},{"id":1952,"createDate":"2015/07/28 18:37:23","refundSn":"R1438079843353","orderSn":"Q14380712944389336","statusName":"退款成功","statusCode":8,"productName":"almond rocks ska 英伦黑白单条纹中筒袜子","productImg":"/pi/1507/f69387c91a97cde434c50bdafc56c68e","productColor":"黑白","productSize":"均码","totalAmount":36,"applyAmount":36,"payType":"微信支付(app)"},{"id":1948,"createDate":"2015/07/28 16:54:20","refundSn":"R1438073660775","orderSn":"Q14380712944389336","statusName":"商家拒绝退款","statusCode":-1,"productName":"almond rocks ska 英伦黑白单条纹中筒袜子","productImg":"/pi/1507/f69387c91a97cde434c50bdafc56c68e","productColor":"黑白","productSize":"均码","totalAmount":36,"applyAmount":36,"payType":"微信支付(app)"},{"id":1863,"createDate":"2015/07/14 10:23:28","refundSn":"R1436840608890","orderSn":"Q14368402626619336","statusName":"退款成功","statusCode":8,"productName":"almond rocks  设计师ska 英伦黑白竖条纹中筒袜子","productImg":"/pi/1507/71d54f78bd100ce26498fdfcb2821861","productColor":"黑白","productSize":"均码","totalAmount":46,"applyAmount":46,"payType":"微信支付"}]}
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
         * total : 16
         * previous : false
         * next : false
         * list : [{"id":23792,"createDate":"2016/03/08 11:06:56","refundSn":"R1457406416419","orderSn":"Q14574061866119336","statusName":"退款成功","statusCode":8,"productName":"测试商品","productImg":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","productColor":"条文","productSize":"S码","totalAmount":0.01,"applyAmount":0.01,"backAccountName":"2016030821001004370246969997","payType":"支付宝"},{"id":13796,"createDate":"2015/11/03 10:49:11","refundSn":"R1446518951774","orderSn":"Q14459238347629336","statusName":"退款成功","statusCode":8,"productName":"测试商品","productImg":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","productColor":"条文","productSize":"S码","totalAmount":0.01,"applyAmount":0,"backAccountName":"1008250760201510271351317503","payType":"微信支付"},{"id":12670,"createDate":"2015/11/01 00:36:45","refundSn":"R1446309340753","orderSn":"Q14463091124659336","statusName":"用户取消退款","statusCode":-2,"productName":"测试商品","productImg":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","productColor":"条文","productSize":"S码","totalAmount":0,"applyAmount":0.01,"backAccountName":"1008250760201511011409433144","payType":"微信支付"},{"id":4695,"createDate":"2015/09/16 23:22:55","refundSn":"R1442416951783","orderSn":"Q14424167954489336","statusName":"退款成功","statusCode":8,"productName":"气味图书馆 娄楠石 城市系列 上海 香水","productImg":"/pi/1506/31aeb02e30e62f60436924024e5f0721","productColor":"透明","productSize":"均码","totalAmount":95,"applyAmount":95,"payType":"支付宝"},{"id":4693,"createDate":"2015/09/16 23:01:36","refundSn":"R1442415672873","orderSn":"Q14424150853409336","statusName":"退款成功","statusCode":8,"productName":"almond rocks ska 红色蓝白条纹短袜礼盒","productImg":"/pi/1507/d810368fd3e4cbae2e1affe00d19da55","productColor":"多彩","productSize":"均码","totalAmount":88,"applyAmount":88,"payType":"支付宝"},{"id":2981,"createDate":"2015/08/26 18:09:26","refundSn":"R1440583766820","orderSn":"Q14405836538289336","statusName":"退款成功","statusCode":8,"productName":"气味图书馆 娄楠石 城市系列 北京 香水","productImg":"/pi/1506/d16b157ab96e210d11ec653d18c4d5b1","productColor":"透明","productSize":"均码","totalAmount":145,"applyAmount":145,"payType":"支付宝"},{"id":3101,"createDate":"2015/08/26 21:19:53","refundSn":"R1440595193164","orderSn":"Q14405877176019336","statusName":"退款成功","statusCode":8,"productName":"almond rocks ska 蓝黄青紋短袜","productImg":"/pi/1507/39863f23761681d4acdd94a610bf1120","productColor":"多彩","productSize":"均码","totalAmount":32,"applyAmount":32,"payType":"支付宝"},{"id":3125,"createDate":"2015/08/26 21:38:34","refundSn":"R1440596314930","orderSn":"Q14405961180919336","statusName":"退款成功","statusCode":8,"productName":"almond rocks 设计师ska  多彩红黄底条纹 中筒袜礼盒","productImg":"/pi/1507/a46716062fbe91dfd8f0e092ce5f662f","productColor":"多彩","productSize":"均码","totalAmount":96,"applyAmount":96,"payType":"支付宝"},{"id":2504,"createDate":"2015/08/21 19:59:51","refundSn":"R1440158391460","orderSn":"Q14399867244029336","statusName":"退款成功","statusCode":8,"productName":"苣毒    Daisy  极致简约个性系  粉色小尾巴连衣裙","productImg":"/pi/1506/da9ad824785e3b59c04fc86c3f829a5e","productColor":"粉色","productSize":"S码","totalAmount":269,"applyAmount":269,"payType":"支付宝"},{"id":2483,"createDate":"2015/08/21 14:08:48","refundSn":"R1440137328604","orderSn":"Q14399867244029336","statusName":"商家拒绝退款","statusCode":-1,"productName":"苣毒    Daisy  极致简约个性系  粉色小尾巴连衣裙","productImg":"/pi/1506/da9ad824785e3b59c04fc86c3f829a5e","productColor":"粉色","productSize":"S码","totalAmount":269,"applyAmount":269,"payType":"支付宝"},{"id":1947,"createDate":"2015/07/28 16:49:24","refundSn":"R1438073364354","orderSn":"Q14380724514439336","statusName":"退款成功","statusCode":8,"productName":"almond rocks 设计师ska 英伦深蓝底红波点 中筒袜","productImg":"/pi/1507/c59c70bc7781bd5f8402bcb438c7dab4","productColor":"蓝色","productSize":"均码","totalAmount":36,"applyAmount":36,"payType":"支付宝"},{"id":1949,"createDate":"2015/07/28 17:53:47","refundSn":"R1438077227375","orderSn":"Q14380769711859336","statusName":"退款成功","statusCode":8,"productName":"almond rocks 设计师ska 英伦绿红白条纹 中筒袜","productImg":"/pi/1507/b521078ef2bca3f5ae009b73cf62e82b","productColor":"绿色","productSize":"均码","totalAmount":36,"applyAmount":36,"payType":"支付宝"},{"id":1951,"createDate":"2015/07/28 18:33:26","refundSn":"R1438079606714","orderSn":"Q14380737766209336","statusName":"退款成功","statusCode":8,"productName":"almond rocks 设计师ska  英伦深蓝红黄条纹 中筒袜","productImg":"/pi/1507/a26ea0ef284367abdbf5d6a8ca4333ed","productColor":"蓝色","productSize":"均码","totalAmount":36,"applyAmount":36,"payType":"支付宝"},{"id":1952,"createDate":"2015/07/28 18:37:23","refundSn":"R1438079843353","orderSn":"Q14380712944389336","statusName":"退款成功","statusCode":8,"productName":"almond rocks ska 英伦黑白单条纹中筒袜子","productImg":"/pi/1507/f69387c91a97cde434c50bdafc56c68e","productColor":"黑白","productSize":"均码","totalAmount":36,"applyAmount":36,"payType":"微信支付(app)"},{"id":1948,"createDate":"2015/07/28 16:54:20","refundSn":"R1438073660775","orderSn":"Q14380712944389336","statusName":"商家拒绝退款","statusCode":-1,"productName":"almond rocks ska 英伦黑白单条纹中筒袜子","productImg":"/pi/1507/f69387c91a97cde434c50bdafc56c68e","productColor":"黑白","productSize":"均码","totalAmount":36,"applyAmount":36,"payType":"微信支付(app)"},{"id":1863,"createDate":"2015/07/14 10:23:28","refundSn":"R1436840608890","orderSn":"Q14368402626619336","statusName":"退款成功","statusCode":8,"productName":"almond rocks  设计师ska 英伦黑白竖条纹中筒袜子","productImg":"/pi/1507/71d54f78bd100ce26498fdfcb2821861","productColor":"黑白","productSize":"均码","totalAmount":46,"applyAmount":46,"payType":"微信支付"}]
         */

        private RefundsEntity refunds;

        public RefundsEntity getRefunds() {
            return refunds;
        }

        public void setRefunds(RefundsEntity refunds) {
            this.refunds = refunds;
        }

        public static class RefundsEntity {
            private int index;
            private int pageSize;
            private int total;
            private boolean previous;
            private boolean next;
            /**
             * id : 23792
             * createDate : 2016/03/08 11:06:56
             * refundSn : R1457406416419
             * orderSn : Q14574061866119336
             * statusName : 退款成功
             * statusCode : 8
             * productName : 测试商品
             * productImg : /pi/4/101504/cbbc4ed1576ac2817354c96828302fcf
             * productColor : 条文
             * productSize : S码
             * totalAmount : 0.01
             * applyAmount : 0.01
             * backAccountName : 2016030821001004370246969997
             * payType : 支付宝
             */

            private List<ListEntity> list;

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

            public List<ListEntity> getList() {
                return list;
            }

            public void setList(List<ListEntity> list) {
                this.list = list;
            }

            public static class ListEntity implements Identifiable {
                private long id;
                private String createDate;
                private String refundSn;
                private String orderSn;
                private String statusName;
                private int statusCode;
                private String productName;
                private String productImg;
                private String productColor;
                private String productSize;
                private double totalAmount;
                private double applyAmount;
                private String backAccountName;
                private String payType;

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

                public String getRefundSn() {
                    return refundSn;
                }

                public void setRefundSn(String refundSn) {
                    this.refundSn = refundSn;
                }

                public String getOrderSn() {
                    return orderSn;
                }

                public void setOrderSn(String orderSn) {
                    this.orderSn = orderSn;
                }

                public String getStatusName() {
                    return statusName;
                }

                public void setStatusName(String statusName) {
                    this.statusName = statusName;
                }

                public int getStatusCode() {
                    return statusCode;
                }

                public void setStatusCode(int statusCode) {
                    this.statusCode = statusCode;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public String getProductImg() {
                    return productImg;
                }

                public void setProductImg(String productImg) {
                    this.productImg = productImg;
                }

                public String getProductColor() {
                    return productColor;
                }

                public void setProductColor(String productColor) {
                    this.productColor = productColor;
                }

                public String getProductSize() {
                    return productSize;
                }

                public void setProductSize(String productSize) {
                    this.productSize = productSize;
                }

                public double getTotalAmount() {
                    return totalAmount;
                }

                public void setTotalAmount(double totalAmount) {
                    this.totalAmount = totalAmount;
                }

                public double getApplyAmount() {
                    return applyAmount;
                }

                public void setApplyAmount(double applyAmount) {
                    this.applyAmount = applyAmount;
                }

                public String getBackAccountName() {
                    return backAccountName;
                }

                public void setBackAccountName(String backAccountName) {
                    this.backAccountName = backAccountName;
                }

                public String getPayType() {
                    return payType;
                }

                public void setPayType(String payType) {
                    this.payType = payType;
                }
            }
        }
    }
}
