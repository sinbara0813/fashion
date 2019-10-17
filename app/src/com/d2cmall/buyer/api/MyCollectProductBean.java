package com.d2cmall.buyer.api;

import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.Identifiable;

import java.util.List;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/12 11:06
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class MyCollectProductBean extends BaseBean {

    /**
     * data : {"myCollections":{"next":false,"total":3,"previous":false,"index":1,"pageSize":40,"list":[{"productId":163888,"nickName":"布谷鸟也要买衣服","productPic":"/2017/06/23/093502aecd7e46866d9a2a312e63310ac036f4.jpg","currentPrice":1500,"headPic":"/app/a/17/07/14/2c2c9b7943e1993a37682ce95c384f0b","productName":"IMMI 庄晓君 17樱桃衬衫","productPrice":1500,"memberId":2865965},{"productId":163101,"nickName":"布谷鸟也要买衣服","productPic":"/2017/06/16/021345dc3ff8bddec0c154c1d5f60e955846bf.jpg","currentPrice":599,"headPic":"/app/a/17/07/14/2c2c9b7943e1993a37682ce95c384f0b","productName":"WF woman David.W 荷叶边开叉露肩衬衫","productPrice":599,"memberId":2865965},{"productId":162916,"nickName":"布谷鸟也要买衣服","productPic":"/2017/07/19/073421edef1f5a7d18ebd804823744dbd36a7c.jpg","currentPrice":1180,"headPic":"/app/a/17/07/14/2c2c9b7943e1993a37682ce95c384f0b","productName":"【明星同款】SI RAN 赵思然 车晓同款 浅蓝白条纹露肩连袖衬衣","productPrice":1180,"memberId":2865965}]}}
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
         * myCollections : {"next":false,"total":3,"previous":false,"index":1,"pageSize":40,"list":[{"productId":163888,"nickName":"布谷鸟也要买衣服","productPic":"/2017/06/23/093502aecd7e46866d9a2a312e63310ac036f4.jpg","currentPrice":1500,"headPic":"/app/a/17/07/14/2c2c9b7943e1993a37682ce95c384f0b","productName":"IMMI 庄晓君 17樱桃衬衫","productPrice":1500,"memberId":2865965},{"productId":163101,"nickName":"布谷鸟也要买衣服","productPic":"/2017/06/16/021345dc3ff8bddec0c154c1d5f60e955846bf.jpg","currentPrice":599,"headPic":"/app/a/17/07/14/2c2c9b7943e1993a37682ce95c384f0b","productName":"WF woman David.W 荷叶边开叉露肩衬衫","productPrice":599,"memberId":2865965},{"productId":162916,"nickName":"布谷鸟也要买衣服","productPic":"/2017/07/19/073421edef1f5a7d18ebd804823744dbd36a7c.jpg","currentPrice":1180,"headPic":"/app/a/17/07/14/2c2c9b7943e1993a37682ce95c384f0b","productName":"【明星同款】SI RAN 赵思然 车晓同款 浅蓝白条纹露肩连袖衬衣","productPrice":1180,"memberId":2865965}]}
         */

        private MyCollectionsBean myCollections;

        public MyCollectionsBean getMyCollections() {
            return myCollections;
        }

        public void setMyCollections(MyCollectionsBean myCollections) {
            this.myCollections = myCollections;
        }

        public static class MyCollectionsBean {
            /**
             * next : false
             * total : 3
             * previous : false
             * index : 1
             * pageSize : 40
             * list : [{"productId":163888,"nickName":"布谷鸟也要买衣服","productPic":"/2017/06/23/093502aecd7e46866d9a2a312e63310ac036f4.jpg","currentPrice":1500,"headPic":"/app/a/17/07/14/2c2c9b7943e1993a37682ce95c384f0b","productName":"IMMI 庄晓君 17樱桃衬衫","productPrice":1500,"memberId":2865965},{"productId":163101,"nickName":"布谷鸟也要买衣服","productPic":"/2017/06/16/021345dc3ff8bddec0c154c1d5f60e955846bf.jpg","currentPrice":599,"headPic":"/app/a/17/07/14/2c2c9b7943e1993a37682ce95c384f0b","productName":"WF woman David.W 荷叶边开叉露肩衬衫","productPrice":599,"memberId":2865965},{"productId":162916,"nickName":"布谷鸟也要买衣服","productPic":"/2017/07/19/073421edef1f5a7d18ebd804823744dbd36a7c.jpg","currentPrice":1180,"headPic":"/app/a/17/07/14/2c2c9b7943e1993a37682ce95c384f0b","productName":"【明星同款】SI RAN 赵思然 车晓同款 浅蓝白条纹露肩连袖衬衣","productPrice":1180,"memberId":2865965}]
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
                public String getDesigners() {
                    return designers;
                }

                public void setDesigners(String designers) {
                    this.designers = designers;
                }

                public int getGoodPromotionId() {
                    return goodPromotionId;
                }

                public void setGoodPromotionId(int goodPromotionId) {
                    this.goodPromotionId = goodPromotionId;
                }

                public double getSalePrice() {
                    return salePrice;
                }

                public void setSalePrice(double salePrice) {
                    this.salePrice = salePrice;
                }

                /**
                 * productId : 163888
                 * nickName : 布谷鸟也要买衣服
                 * productPic : /2017/06/23/093502aecd7e46866d9a2a312e63310ac036f4.jpg
                 * currentPrice : 1500.0
                 * headPic : /app/a/17/07/14/2c2c9b7943e1993a37682ce95c384f0b
                 * productName : IMMI 庄晓君 17樱桃衬衫
                 * productPrice : 1500.0
                 * memberId : 2865965
                 */
                private double salePrice;
                private int goodPromotionId;
                private String designers;
                private int productId;
                private String nickName;
                private String productPic;
                private double currentPrice;
                private String headPic;
                private String productName;
                private double productPrice;
                private int memberId;
                private boolean isSpot;
                private String orderPromotionType;
                private int orderPromotionId;
                private String orderPromotionTypeName;
                private String productTradeType;//贸易类型 COMMON(一般) CROSS(跨境)
                private SoonPromotion soonPromotion;
                private boolean isLongClick;
                private Double collectionPrice;
                public boolean isLongClick() {
                    return isLongClick;
                }

                public void setLongClick(boolean longClick) {
                    isLongClick = longClick;
                }

                public SoonPromotion getSoonPromotion() {
                    return soonPromotion;
                }

                public void setSoonPromotion(SoonPromotion soonPromotion) {
                    this.soonPromotion = soonPromotion;
                }

                public static class SoonPromotion implements Identifiable {

                    private Double soonPromotionPrice;//活动价
                    private Long soonPromotionDate;//活动开始时间
                    private String soonPromotionPrefix;//活动小标题

                    public Double getSoonPromotionPrice() {
                        return soonPromotionPrice;
                    }

                    public void setSoonPromotionPrice(Double soonPromotionPrice) {
                        this.soonPromotionPrice = soonPromotionPrice;
                    }

                    public Long getSoonPromotionDate() {
                        return soonPromotionDate;
                    }

                    public void setSoonPromotionDate(Long soonPromotionDate) {
                        this.soonPromotionDate = soonPromotionDate;
                    }

                    public String getSoonPromotionPrefix() {
                        return soonPromotionPrefix;
                    }

                    public void setSoonPromotionPrefix(String soonPromotionPrefix) {
                        this.soonPromotionPrefix = soonPromotionPrefix;
                    }

                    @Override
                    public long getId() {
                        return 0;
                    }
                }

                public String getProductTradeType() {
                    return productTradeType;
                }

                public void setProductTradeType(String productTradeType) {
                    this.productTradeType = productTradeType;
                }

                public String getOrderPromotionType() {
                    return orderPromotionType;
                }

                public void setOrderPromotionType(String orderPromotionType) {
                    this.orderPromotionType = orderPromotionType;
                }

                public int getOrderPromotionId() {
                    return orderPromotionId;
                }

                public void setOrderPromotionId(int orderPromotionId) {
                    this.orderPromotionId = orderPromotionId;
                }

                public String getOrderPromotionTypeName() {
                    return orderPromotionTypeName;
                }

                public void setOrderPromotionTypeName(String orderPromotionTypeName) {
                    this.orderPromotionTypeName = orderPromotionTypeName;
                }

                public boolean isSpot() {
                    return isSpot;
                }

                public void setSpot(boolean spot) {
                    isSpot = spot;
                }

                public int getProductMark() {
                    return productMark;
                }

                public void setProductMark(int productMark) {
                    this.productMark = productMark;
                }

                private int productMark;
                public int getHasCollected() {
                    return hasCollected;
                }

                public void setHasCollected(int hasCollected) {
                    this.hasCollected = hasCollected;
                }

                private int hasCollected=1;
                public int getStore() {
                    return store;
                }

                public void setStore(int store) {
                    this.store = store;
                }

                private int store;

                public void setOriginalPrice(double originalPrice) {
                    this.originalPrice = originalPrice;
                }

                public double getOriginalPrice() {
                    return originalPrice;
                }

                private double originalPrice;
                public int getProductId() {
                    return productId;
                }

                public void setProductId(int productId) {
                    this.productId = productId;
                }

                public String getNickName() {
                    return nickName;
                }

                public void setNickName(String nickName) {
                    this.nickName = nickName;
                }

                public String getProductPic() {
                    return productPic;
                }

                public void setProductPic(String productPic) {
                    this.productPic = productPic;
                }

                public double getCurrentPrice() {
                    return currentPrice;
                }

                public void setCurrentPrice(double currentPrice) {
                    this.currentPrice = currentPrice;
                }

                public String getHeadPic() {
                    return headPic;
                }

                public void setHeadPic(String headPic) {
                    this.headPic = headPic;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public double getProductPrice() {
                    return productPrice;
                }

                public void setProductPrice(double productPrice) {
                    this.productPrice = productPrice;
                }
                public Double getCollectionPrice() {
                    return collectionPrice==null?0:collectionPrice;
                }

                public void setCollectionPrice(Double collectionPrice) {
                    this.collectionPrice = collectionPrice;
                }


                public int getMemberId() {
                    return memberId;
                }

                public void setMemberId(int memberId) {
                    this.memberId = memberId;
                }
            }
        }
    }
}
