package com.d2cmall.buyer.bean;


import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

public class CrowdBean extends BaseBean {

    /**
     * crowdItems : {"index":1,"pageSize":40,"total":4,"previous":false,"next":false,"list":[{"id":125167,"productId":125167,"img":"/model/1604/090c0a2880a8dd7c351b2c2879640b00","name":"Chrisou by Dan  欧阳丹  湖南卫视主持人 刘烨同款  立体裁剪圆领无袖牛仔连衣裙 ","currentPrice":2280,"originalCost":2280,"store":4},{"id":125169,"productId":125169,"img":"/model/1603/5d7e39b9169433321145faab15edebc3","name":"Chrisou by Dan  欧阳丹  陈妍希同款  性感V领设计真丝立体裁剪连衣裙 ","currentPrice":2880,"originalCost":2880,"store":4},{"id":125170,"productId":125170,"img":"/model/1603/8ccb0320ae851ede659422cf16663881","name":"Chrisou by Dan  欧阳丹  激光切割贴花真丝露肩连衣裙  ","currentPrice":2580,"originalCost":2580,"store":0},{"id":125172,"productId":125172,"img":"/model/1603/2dc49bdfc788177376a21892b26b02e9","name":"Chrisou by Dan  欧阳丹   昕薇同款 珍珠装饰立体裁剪连衣裙 ","currentPrice":3680,"originalCost":3680,"store":0}]}
     * crowd : {"id":429,"name":"Chrisou by Dan  欧阳丹 2016ss新品首发","beginCrowd":"2016/04/12 10:00:00","endCrowd":"2016/05/31 10:00:00","img":"/model/1604/47794ba9ce2996730fe2885381122a46"}
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
         * pageSize : 40
         * total : 4
         * previous : false
         * next : false
         * list : [{"id":125167,"productId":125167,"img":"/model/1604/090c0a2880a8dd7c351b2c2879640b00","name":"Chrisou by Dan  欧阳丹  湖南卫视主持人 刘烨同款  立体裁剪圆领无袖牛仔连衣裙 ","currentPrice":2280,"originalCost":2280,"store":4},{"id":125169,"productId":125169,"img":"/model/1603/5d7e39b9169433321145faab15edebc3","name":"Chrisou by Dan  欧阳丹  陈妍希同款  性感V领设计真丝立体裁剪连衣裙 ","currentPrice":2880,"originalCost":2880,"store":4},{"id":125170,"productId":125170,"img":"/model/1603/8ccb0320ae851ede659422cf16663881","name":"Chrisou by Dan  欧阳丹  激光切割贴花真丝露肩连衣裙  ","currentPrice":2580,"originalCost":2580,"store":0},{"id":125172,"productId":125172,"img":"/model/1603/2dc49bdfc788177376a21892b26b02e9","name":"Chrisou by Dan  欧阳丹   昕薇同款 珍珠装饰立体裁剪连衣裙 ","currentPrice":3680,"originalCost":3680,"store":0}]
         */

        private CrowdItemsEntity crowdItems;
        /**
         * id : 429
         * name : Chrisou by Dan  欧阳丹 2016ss新品首发
         * beginCrowd : 2016/04/12 10:00:00
         * endCrowd : 2016/05/31 10:00:00
         * img : /model/1604/47794ba9ce2996730fe2885381122a46
         */

        private CrowdEntity crowd;
        private String mobileCode;

        public CrowdItemsEntity getCrowdItems() {
            return crowdItems;
        }

        public void setCrowdItems(CrowdItemsEntity crowdItems) {
            this.crowdItems = crowdItems;
        }

        public CrowdEntity getCrowd() {
            return crowd;
        }

        public void setCrowd(CrowdEntity crowd) {
            this.crowd = crowd;
        }

        public String getMobileCode() {
            return mobileCode;
        }

        public void setMobileCode(String mobileCode) {
            this.mobileCode = mobileCode;
        }

        public static class CrowdItemsEntity {
            private int index;
            private int pageSize;
            private int total;
            private boolean previous;
            private boolean next;
            /**
             * id : 125167
             * productId : 125167
             * img : /model/1604/090c0a2880a8dd7c351b2c2879640b00
             * name : Chrisou by Dan  欧阳丹  湖南卫视主持人 刘烨同款  立体裁剪圆领无袖牛仔连衣裙
             * currentPrice : 2280.0
             * originalCost : 2280.0
             * store : 4
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
                private long productId;
                private String img;
                private String name;
                private double currentPrice;
                private double originalCost;
                private int store;

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public long getProductId() {
                    return productId;
                }

                public void setProductId(long productId) {
                    this.productId = productId;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public double getCurrentPrice() {
                    return currentPrice;
                }

                public void setCurrentPrice(double currentPrice) {
                    this.currentPrice = currentPrice;
                }

                public double getOriginalCost() {
                    return originalCost;
                }

                public void setOriginalCost(double originalCost) {
                    this.originalCost = originalCost;
                }

                public int getStore() {
                    return store;
                }

                public void setStore(int store) {
                    this.store = store;
                }
            }
        }

        public static class CrowdEntity {
            private int id;
            private String name;
            private String beginCrowd;
            private String endCrowd;
            private String img;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getBeginCrowd() {
                return beginCrowd;
            }

            public void setBeginCrowd(String beginCrowd) {
                this.beginCrowd = beginCrowd;
            }

            public String getEndCrowd() {
                return endCrowd;
            }

            public void setEndCrowd(String endCrowd) {
                this.endCrowd = endCrowd;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
