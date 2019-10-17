package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/8/9.
 * Description : DCionExchangeHistoryBean
 */

public class DCionExchangeHistoryBean extends BaseBean {

    /**
     * data : {"records":{"next":false,"total":4,"previous":false,"index":1,"pageSize":20,"list":[{"amount":0,"productId":1,"typeName":"兑换","remark":"1233","pic":"/pi/24/100024/e81c23073caad03bb08db1eb02075c8d,/pi/24/100024/51669046171f2c362ab2adaf4374bea8,/pi/24/100024/70bcc73be62e3baa3e3f648b81b5bc5b,/pi/24/100024/9c51bf412f2b29307808374b20be3edf","transactionTime":1533633879000,"type":"EXCHANGE","transactionSn":"PE1533633874111","productName":"测试","point":2000,"transactionInfo":"积分兑换商品'测试'","direction":-1},{"amount":0,"productId":3,"typeName":"兑换","remark":"1233","pic":"/pi/24/100024/e81c23073caad03bb08db1eb02075c8d,/pi/24/100024/51669046171f2c362ab2adaf4374bea8,/pi/24/100024/70bcc73be62e3baa3e3f648b81b5bc5b,/pi/24/100024/9c51bf412f2b29307808374b20be3edf","transactionTime":1533633727000,"type":"EXCHANGE","transactionSn":"PE1533633723454","productName":"测试","point":2000,"transactionInfo":"积分兑换商品'测试'","direction":-1},{"amount":0,"productId":1,"typeName":"兑换","remark":"213","pic":"/pi/24/100024/e81c23073caad03bb08db1eb02075c8d,/pi/24/100024/51669046171f2c362ab2adaf4374bea8,/pi/24/100024/70bcc73be62e3baa3e3f648b81b5bc5b,/pi/24/100024/9c51bf412f2b29307808374b20be3edf","transactionTime":1533632620000,"type":"EXCHANGE","transactionSn":"PE1533632619747","productName":"测试","point":2000,"transactionInfo":"积分兑换商品'测试'","direction":-1},{"amount":0,"productId":2,"typeName":"兑换","remark":"1233","pic":"/pi/24/100024/e81c23073caad03bb08db1eb02075c8d,/pi/24/100024/51669046171f2c362ab2adaf4374bea8,/pi/24/100024/70bcc73be62e3baa3e3f648b81b5bc5b,/pi/24/100024/9c51bf412f2b29307808374b20be3edf","transactionTime":1533632081000,"type":"EXCHANGE","transactionSn":"PE1533632080851","productName":"测试","point":2000,"transactionInfo":"积分兑换商品'测试'","direction":-1}]}}
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
         * records : {"next":false,"total":4,"previous":false,"index":1,"pageSize":20,"list":[{"amount":0,"productId":1,"typeName":"兑换","remark":"1233","pic":"/pi/24/100024/e81c23073caad03bb08db1eb02075c8d,/pi/24/100024/51669046171f2c362ab2adaf4374bea8,/pi/24/100024/70bcc73be62e3baa3e3f648b81b5bc5b,/pi/24/100024/9c51bf412f2b29307808374b20be3edf","transactionTime":1533633879000,"type":"EXCHANGE","transactionSn":"PE1533633874111","productName":"测试","point":2000,"transactionInfo":"积分兑换商品'测试'","direction":-1},{"amount":0,"productId":3,"typeName":"兑换","remark":"1233","pic":"/pi/24/100024/e81c23073caad03bb08db1eb02075c8d,/pi/24/100024/51669046171f2c362ab2adaf4374bea8,/pi/24/100024/70bcc73be62e3baa3e3f648b81b5bc5b,/pi/24/100024/9c51bf412f2b29307808374b20be3edf","transactionTime":1533633727000,"type":"EXCHANGE","transactionSn":"PE1533633723454","productName":"测试","point":2000,"transactionInfo":"积分兑换商品'测试'","direction":-1},{"amount":0,"productId":1,"typeName":"兑换","remark":"213","pic":"/pi/24/100024/e81c23073caad03bb08db1eb02075c8d,/pi/24/100024/51669046171f2c362ab2adaf4374bea8,/pi/24/100024/70bcc73be62e3baa3e3f648b81b5bc5b,/pi/24/100024/9c51bf412f2b29307808374b20be3edf","transactionTime":1533632620000,"type":"EXCHANGE","transactionSn":"PE1533632619747","productName":"测试","point":2000,"transactionInfo":"积分兑换商品'测试'","direction":-1},{"amount":0,"productId":2,"typeName":"兑换","remark":"1233","pic":"/pi/24/100024/e81c23073caad03bb08db1eb02075c8d,/pi/24/100024/51669046171f2c362ab2adaf4374bea8,/pi/24/100024/70bcc73be62e3baa3e3f648b81b5bc5b,/pi/24/100024/9c51bf412f2b29307808374b20be3edf","transactionTime":1533632081000,"type":"EXCHANGE","transactionSn":"PE1533632080851","productName":"测试","point":2000,"transactionInfo":"积分兑换商品'测试'","direction":-1}]}
         */

        private RecordsBean records;

        public RecordsBean getRecords() {
            return records;
        }

        public void setRecords(RecordsBean records) {
            this.records = records;
        }

        public static class RecordsBean {
            /**
             * next : false
             * total : 4
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"amount":0,"productId":1,"typeName":"兑换","remark":"1233","pic":"/pi/24/100024/e81c23073caad03bb08db1eb02075c8d,/pi/24/100024/51669046171f2c362ab2adaf4374bea8,/pi/24/100024/70bcc73be62e3baa3e3f648b81b5bc5b,/pi/24/100024/9c51bf412f2b29307808374b20be3edf","transactionTime":1533633879000,"type":"EXCHANGE","transactionSn":"PE1533633874111","productName":"测试","point":2000,"transactionInfo":"积分兑换商品'测试'","direction":-1},{"amount":0,"productId":3,"typeName":"兑换","remark":"1233","pic":"/pi/24/100024/e81c23073caad03bb08db1eb02075c8d,/pi/24/100024/51669046171f2c362ab2adaf4374bea8,/pi/24/100024/70bcc73be62e3baa3e3f648b81b5bc5b,/pi/24/100024/9c51bf412f2b29307808374b20be3edf","transactionTime":1533633727000,"type":"EXCHANGE","transactionSn":"PE1533633723454","productName":"测试","point":2000,"transactionInfo":"积分兑换商品'测试'","direction":-1},{"amount":0,"productId":1,"typeName":"兑换","remark":"213","pic":"/pi/24/100024/e81c23073caad03bb08db1eb02075c8d,/pi/24/100024/51669046171f2c362ab2adaf4374bea8,/pi/24/100024/70bcc73be62e3baa3e3f648b81b5bc5b,/pi/24/100024/9c51bf412f2b29307808374b20be3edf","transactionTime":1533632620000,"type":"EXCHANGE","transactionSn":"PE1533632619747","productName":"测试","point":2000,"transactionInfo":"积分兑换商品'测试'","direction":-1},{"amount":0,"productId":2,"typeName":"兑换","remark":"1233","pic":"/pi/24/100024/e81c23073caad03bb08db1eb02075c8d,/pi/24/100024/51669046171f2c362ab2adaf4374bea8,/pi/24/100024/70bcc73be62e3baa3e3f648b81b5bc5b,/pi/24/100024/9c51bf412f2b29307808374b20be3edf","transactionTime":1533632081000,"type":"EXCHANGE","transactionSn":"PE1533632080851","productName":"测试","point":2000,"transactionInfo":"积分兑换商品'测试'","direction":-1}]
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

            public static class ListBean {
                /**
                 * amount : 0
                 * productId : 1
                 * typeName : 兑换
                 * remark : 1233
                 * pic : /pi/24/100024/e81c23073caad03bb08db1eb02075c8d,/pi/24/100024/51669046171f2c362ab2adaf4374bea8,/pi/24/100024/70bcc73be62e3baa3e3f648b81b5bc5b,/pi/24/100024/9c51bf412f2b29307808374b20be3edf
                 * transactionTime : 1533633879000
                 * type : EXCHANGE
                 * transactionSn : PE1533633874111
                 * productName : 测试
                 * point : 2000
                 * transactionInfo : 积分兑换商品'测试'
                 * direction : -1
                 */

                private double amount;
                private int productId;
                private String typeName;
                private String remark;
                private String pic;
                private Date transactionTime;
                private String type;
                private String transactionSn;
                private String productName;
                private int point;
                private String transactionInfo;
                private int direction;
                private String code;

                private String productType;

                private int productQuantity;

                public int getProductQuantity() {
                    return productQuantity;
                }

                public void setProductQuantity(int productQuantity) {
                    this.productQuantity = productQuantity;
                }
                public String getProductType() {
                    return productType;
                }

                public void setProductType(String productType) {
                    this.productType = productType;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }
                public double getAmount() {
                    return amount;
                }

                public void setAmount(double amount) {
                    this.amount = amount;
                }

                public int getProductId() {
                    return productId;
                }

                public void setProductId(int productId) {
                    this.productId = productId;
                }

                public String getTypeName() {
                    return typeName;
                }

                public void setTypeName(String typeName) {
                    this.typeName = typeName;
                }

                public String getRemark() {
                    return remark;
                }

                public void setRemark(String remark) {
                    this.remark = remark;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public Date getTransactionTime() {
                    return transactionTime;
                }

                public void setTransactionTime(Date transactionTime) {
                    this.transactionTime = transactionTime;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getTransactionSn() {
                    return transactionSn;
                }

                public void setTransactionSn(String transactionSn) {
                    this.transactionSn = transactionSn;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public int getPoint() {
                    return point;
                }

                public void setPoint(int point) {
                    this.point = point;
                }

                public String getTransactionInfo() {
                    return transactionInfo;
                }

                public void setTransactionInfo(String transactionInfo) {
                    this.transactionInfo = transactionInfo;
                }

                public int getDirection() {
                    return direction;
                }

                public void setDirection(int direction) {
                    this.direction = direction;
                }
            }
        }
    }
}
