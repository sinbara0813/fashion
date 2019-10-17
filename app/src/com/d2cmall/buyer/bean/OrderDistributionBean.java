package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * 订单返利Bean
 * Author: Blend
 * Date: 2016/07/13 18:08
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class OrderDistributionBean extends BaseBean {

    /**
     * partnerBills : {"index":1,"pageSize":20,"total":9,"previous":false,"next":false,"list":[{"id":341,"createDate":"2016/07/13 13:26:23","orderSn":"Q14683878386320074","productSn":"JP00862200015","productQuantity":1,"nickName":"1587931****","ratio":85,"rebates":211.65,"actualAmount":249,"status":"订单未完成"},{"id":339,"createDate":"2016/07/12 13:03:08","orderSn":"Q14683000426040062","productSn":"I00352200001","productQuantity":1,"nickName":"刘莉","ratio":5,"rebates":29.95,"actualAmount":599,"status":"订单未完成"},{"id":338,"createDate":"2016/07/11 20:06:03","orderSn":"Q14682389955630062","productSn":"Z00541088001","productQuantity":1,"nickName":"刘莉","ratio":5,"rebates":0,"actualAmount":0.02,"status":"订单未完成"},{"id":337,"createDate":"2016/07/11 20:02:28","orderSn":"Q14682387835760062","productSn":"Z00541088001","productQuantity":1,"nickName":"刘莉","ratio":5,"rebates":0,"actualAmount":0.02,"status":"订单未完成"},{"id":336,"createDate":"2016/07/11 19:37:55","orderSn":"Q14682373218780062","productSn":"Z00541088001","productQuantity":1,"nickName":"刘莉","ratio":5,"rebates":0,"actualAmount":0.02,"status":"订单未完成"},{"id":335,"createDate":"2016/07/07 17:17:17","orderSn":"Q14678832877300056","productSn":"JQ00861081001","productQuantity":1,"nickName":"云之彼端","ratio":85,"rebates":833,"actualAmount":980,"status":"已结算"},{"id":333,"createDate":"2016/07/07 17:17:04","orderSn":"Q14678832751440056","productSn":"JW00261082002","productQuantity":1,"nickName":"云之彼端","ratio":85,"rebates":1691.5,"actualAmount":1990,"status":"已结算"},{"id":331,"createDate":"2016/07/07 17:16:50","orderSn":"Q14678832597460056","productSn":"JA02962084002","productQuantity":1,"nickName":"云之彼端","ratio":85,"rebates":1172.15,"actualAmount":1379,"status":"已结算"},{"id":329,"createDate":"2016/07/07 17:16:36","orderSn":"Q14678832453800056","productSn":"JZ03761209001","productQuantity":1,"nickName":"云之彼端","ratio":85,"rebates":1343,"actualAmount":1580,"status":"已结算"}]}
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
         * total : 9
         * previous : false
         * next : false
         * list : [{"id":341,"createDate":"2016/07/13 13:26:23","orderSn":"Q14683878386320074","productSn":"JP00862200015","productQuantity":1,"nickName":"1587931****","ratio":85,"rebates":211.65,"actualAmount":249,"status":"订单未完成"},{"id":339,"createDate":"2016/07/12 13:03:08","orderSn":"Q14683000426040062","productSn":"I00352200001","productQuantity":1,"nickName":"刘莉","ratio":5,"rebates":29.95,"actualAmount":599,"status":"订单未完成"},{"id":338,"createDate":"2016/07/11 20:06:03","orderSn":"Q14682389955630062","productSn":"Z00541088001","productQuantity":1,"nickName":"刘莉","ratio":5,"rebates":0,"actualAmount":0.02,"status":"订单未完成"},{"id":337,"createDate":"2016/07/11 20:02:28","orderSn":"Q14682387835760062","productSn":"Z00541088001","productQuantity":1,"nickName":"刘莉","ratio":5,"rebates":0,"actualAmount":0.02,"status":"订单未完成"},{"id":336,"createDate":"2016/07/11 19:37:55","orderSn":"Q14682373218780062","productSn":"Z00541088001","productQuantity":1,"nickName":"刘莉","ratio":5,"rebates":0,"actualAmount":0.02,"status":"订单未完成"},{"id":335,"createDate":"2016/07/07 17:17:17","orderSn":"Q14678832877300056","productSn":"JQ00861081001","productQuantity":1,"nickName":"云之彼端","ratio":85,"rebates":833,"actualAmount":980,"status":"已结算"},{"id":333,"createDate":"2016/07/07 17:17:04","orderSn":"Q14678832751440056","productSn":"JW00261082002","productQuantity":1,"nickName":"云之彼端","ratio":85,"rebates":1691.5,"actualAmount":1990,"status":"已结算"},{"id":331,"createDate":"2016/07/07 17:16:50","orderSn":"Q14678832597460056","productSn":"JA02962084002","productQuantity":1,"nickName":"云之彼端","ratio":85,"rebates":1172.15,"actualAmount":1379,"status":"已结算"},{"id":329,"createDate":"2016/07/07 17:16:36","orderSn":"Q14678832453800056","productSn":"JZ03761209001","productQuantity":1,"nickName":"云之彼端","ratio":85,"rebates":1343,"actualAmount":1580,"status":"已结算"}]
         */

        private PartnerBillsEntity partnerBills;

        public PartnerBillsEntity getPartnerBills() {
            return partnerBills;
        }

        public void setPartnerBills(PartnerBillsEntity partnerBills) {
            this.partnerBills = partnerBills;
        }

        public static class PartnerBillsEntity {
            private int index;
            private int pageSize;
            private int total;
            private boolean previous;
            private boolean next;
            /**
             * id : 341
             * createDate : 2016/07/13 13:26:23
             * orderSn : Q14683878386320074
             * productSn : JP00862200015
             * productQuantity : 1
             * nickName : 1587931****
             * ratio : 85.0
             * rebates : 211.65
             * actualAmount : 249.0
             * status : 订单未完成
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
                private String orderSn;
                private String productSn;
                private int productQuantity;
                private String nickName;
                private int ratio;
                private double rebates;
                private double actualAmount;
                private String status;

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

                public String getOrderSn() {
                    return orderSn;
                }

                public void setOrderSn(String orderSn) {
                    this.orderSn = orderSn;
                }

                public String getProductSn() {
                    return productSn;
                }

                public void setProductSn(String productSn) {
                    this.productSn = productSn;
                }

                public int getProductQuantity() {
                    return productQuantity;
                }

                public void setProductQuantity(int productQuantity) {
                    this.productQuantity = productQuantity;
                }

                public String getNickName() {
                    return nickName;
                }

                public void setNickName(String nickName) {
                    this.nickName = nickName;
                }

                public int getRatio() {
                    return ratio;
                }

                public void setRatio(int ratio) {
                    this.ratio = ratio;
                }

                public double getRebates() {
                    return rebates;
                }

                public void setRebates(double rebates) {
                    this.rebates = rebates;
                }

                public double getActualAmount() {
                    return actualAmount;
                }

                public void setActualAmount(double actualAmount) {
                    this.actualAmount = actualAmount;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }
            }
        }
    }
}
