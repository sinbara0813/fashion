package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.Date;
import java.util.List;

public class CartListBean extends BaseBean {

    /**
     * data : {"cart":[{"sizeId":1,"goodPromotionId":0,"originalPrice":268,"color":"白色","flashPromotion":{"promotionType":"0","endDate":"2017/12/03 00:00:00","session":"01:00","promotionScope":0,"sessionName":"冰天雪地场","name":"白菜专用测试商品限时购","statusName":"已开抢","id":10,"limitQuantity":5,"startDate":"2017/11/29 00:00:00"},"promotionPrice":30,"totalPrice":238,"colorId":1,"freezeStore":0,"productName":"ZXL 森森   白色双独角兽帆布抱枕","orderPromotion":{"promotionUrl":"/promotion/6325","promotionName":"白菜专用测试订单活动","promotionType":3,"promotionTypeName":"满减上不封顶","name":"白菜专用测试订单活动","id":6325,"promotionId":6325},"id":2462548,"after":1,"skuId":254147,"designerId":10676,"quantity":1,"productId":154468,"productImg":"/2017/03/29/032558e50b95a42148ab8bfd71d1be53374e76.jpg","designer":"ZXL","orderPromotionId":6325,"availableStore":2,"size":"45x45cm","minPrice":238,"flashPromotionId":10,"productPrice":268,"mark":1,"choiceOrderPromotions":[{"promotionUrl":"/promotion/6325","promotionName":"白菜专用测试订单活动","promotionType":3,"promotionTypeName":"满减上不封顶","name":"白菜专用测试订单活动","id":6325,"promotionId":6325}]},{"sizeId":27,"goodPromotionId":0,"originalPrice":299,"color":"灰色","promotionPrice":0,"totalPrice":598,"colorId":97,"freezeStore":0,"productName":"VINCI ZHANG 张勋 网纱睫毛趣味表情短袖T恤上衣","orderPromotion":{"promotionUrl":"/promotion/3862","promotionName":"2件9折！3件85折！","promotionType":5,"promotionTypeName":"满件折扣","name":"2件9折！3件85折！","id":3862,"promotionId":3862},"id":2462333,"after":1,"skuId":267831,"designerId":11057,"quantity":2,"productId":156542,"productImg":"/2017/05/03/0723439080fff9e4864476fa4ffc536bcc1ecd.jpg","designer":"VINCI ZHANG","orderPromotionId":3862,"availableStore":4,"size":"M码","minPrice":299,"flashPromotionId":0,"productPrice":299,"mark":1,"choiceOrderPromotions":[{"promotionUrl":"/promotion/3862","promotionName":"2件9折！3件85折！","promotionType":5,"promotionTypeName":"满件折扣","name":"2件9折！3件85折！","id":3862,"promotionId":3862}]},{"sizeId":1,"goodPromotionId":0,"originalPrice":268,"color":"黑色","flashPromotion":{"promotionType":"0","endDate":"2017/12/03 00:00:00","session":"01:00","promotionScope":0,"sessionName":"冰天雪地场","name":"白菜专用测试商品限时购","statusName":"已开抢","id":10,"limitQuantity":5,"startDate":"2017/11/29 00:00:00"},"promotionPrice":30,"totalPrice":238,"colorId":1,"freezeStore":0,"productName":"ZXL 森森   黑色独角兽黑色帆布抱枕","orderPromotion":{"promotionUrl":"/promotion/6325","promotionName":"白菜专用测试订单活动","promotionType":3,"promotionTypeName":"满减上不封顶","name":"白菜专用测试订单活动","id":6325,"promotionId":6325},"id":2462549,"after":1,"skuId":254143,"designerId":10676,"quantity":1,"productId":154464,"productImg":"/2017/03/28/080222dc34a04ab51bb69b0f7502087c2cc403.jpg","designer":"ZXL","orderPromotionId":6325,"availableStore":2,"size":"45x45cm","minPrice":238,"flashPromotionId":10,"productPrice":268,"mark":1,"choiceOrderPromotions":[{"promotionUrl":"/promotion/6325","promotionName":"白菜专用测试订单活动","promotionType":3,"promotionTypeName":"满减上不封顶","name":"白菜专用测试订单活动","id":6325,"promotionId":6325}]},{"sizeId":1,"goodPromotionId":0,"originalPrice":268,"color":"蓝色","flashPromotion":{"promotionType":"0","endDate":"2017/12/03 00:00:00","session":"01:00","promotionScope":0,"sessionName":"冰天雪地场","name":"白菜专用测试商品限时购","statusName":"已开抢","id":10,"limitQuantity":5,"startDate":"2017/11/29 00:00:00"},"promotionPrice":30,"totalPrice":238,"colorId":1,"freezeStore":0,"productName":"ZXL 森森   蓝色云端独角兽帆布抱枕","orderPromotion":{"promotionUrl":"/promotion/6325","promotionName":"白菜专用测试订单活动","promotionType":3,"promotionTypeName":"满减上不封顶","name":"白菜专用测试订单活动","id":6325,"promotionId":6325},"id":2462551,"after":1,"skuId":254142,"designerId":10676,"quantity":1,"productId":154463,"productImg":"/2017/03/28/075945bd75c7b8621ca25c3ee9b30b8a91c9b9.jpg","designer":"ZXL","orderPromotionId":6325,"availableStore":3,"size":"45x45cm","minPrice":238,"flashPromotionId":10,"productPrice":268,"mark":1,"choiceOrderPromotions":[{"promotionUrl":"/promotion/6325","promotionName":"白菜专用测试订单活动","promotionType":3,"promotionTypeName":"满减上不封顶","name":"白菜专用测试订单活动","id":6325,"promotionId":6325}]},{"sizeId":1,"goodPromotionId":0,"originalPrice":268,"color":"蓝色","flashPromotion":{"promotionType":"0","endDate":"2017/12/03 00:00:00","session":"01:00","promotionScope":0,"sessionName":"冰天雪地场","name":"白菜专用测试商品限时购","statusName":"已开抢","id":10,"limitQuantity":5,"startDate":"2017/11/29 00:00:00"},"promotionPrice":30,"totalPrice":238,"colorId":1,"freezeStore":0,"productName":"ZXL 森森    蓝色独角兽帆布抱枕","orderPromotion":{"promotionUrl":"/promotion/6325","promotionName":"白菜专用测试订单活动","promotionType":3,"promotionTypeName":"满减上不封顶","name":"白菜专用测试订单活动","id":6325,"promotionId":6325},"id":2462550,"after":1,"skuId":254141,"designerId":10676,"quantity":1,"productId":154462,"productImg":"/2017/03/28/075905b8ecc244d06f8af34f7ae713f7f52950.jpg","designer":"ZXL","orderPromotionId":6325,"availableStore":3,"size":"45x45cm","minPrice":238,"flashPromotionId":10,"productPrice":268,"mark":1,"choiceOrderPromotions":[{"promotionUrl":"/promotion/6325","promotionName":"白菜专用测试订单活动","promotionType":3,"promotionTypeName":"满减上不封顶","name":"白菜专用测试订单活动","id":6325,"promotionId":6325}]}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private int compareStore;
        private List<CartBean> cart;

        public List<CartBean> getCart() {
            return cart;
        }

        public void setCart(List<CartBean> cart) {
            this.cart = cart;
        }

        public int getCompareStore() {
            return compareStore;
        }

        public void setCompareStore(int compareStore) {
            this.compareStore = compareStore;
        }

        public static class CartBean {
            /**
             * sizeId : 1
             * goodPromotionId : 0
             * originalPrice : 268.0
             * color : 白色
             * flashPromotion : {"promotionType":"0","endDate":"2017/12/03 00:00:00","session":"01:00","promotionScope":0,"sessionName":"冰天雪地场","name":"白菜专用测试商品限时购","statusName":"已开抢","id":10,"limitQuantity":5,"startDate":"2017/11/29 00:00:00"}
             * promotionPrice : 30.0
             * totalPrice : 238.0
             * colorId : 1
             * freezeStore : 0
             * productName : ZXL 森森   白色双独角兽帆布抱枕
             * orderPromotion : {"promotionUrl":"/promotion/6325","promotionName":"白菜专用测试订单活动","promotionType":3,"promotionTypeName":"满减上不封顶","name":"白菜专用测试订单活动","id":6325,"promotionId":6325}
             * id : 2462548
             * after : 1
             * skuId : 254147
             * designerId : 10676
             * quantity : 1
             * productId : 154468
             * productImg : /2017/03/29/032558e50b95a42148ab8bfd71d1be53374e76.jpg
             * designer : ZXL
             * orderPromotionId : 6325
             * availableStore : 2
             * size : 45x45cm
             * minPrice : 238.0
             * flashPromotionId : 10
             * productPrice : 268.0
             * mark : 1
             * choiceOrderPromotions : [{"promotionUrl":"/promotion/6325","promotionName":"白菜专用测试订单活动","promotionType":3,"promotionTypeName":"满减上不封顶","name":"白菜专用测试订单活动","id":6325,"promotionId":6325}]
             */

            private int sizeId;
            private int goodPromotionId;
            private double originalPrice;
            private String color;
            private FlashPromotionBean flashPromotion;
            private double promotionPrice;
            private double totalPrice;
            private int colorId;
            private int freezeStore;
            private String productName;
            private OrderPromotionBean orderPromotion;
            private OrderPromotionBean goodPromotion;
            private int id;
            private int after;
            private int skuId;
            private int designerId;
            private int quantity;
            private int oldQuantity;
            private long productId;
            private String productImg;
            private String designer;
            private int orderPromotionId;
            private int availableStore;
            private String size;
            private double minPrice;
            private int flashPromotionId;
            private double productPrice;
            private int mark;
            private double oldPrice;
            private boolean showPromotion;
            private boolean showbg;
            private boolean isGroupFirst;
            private boolean isGroupLast;
            private boolean isInvaledFirst;
            private String productTradeType;
            private int taxation;
            private List<OrderPromotionBean> choiceOrderPromotions;

            public String getProductTradeType() {
                return productTradeType;
            }

            public void setProductTradeType(String productTradeType) {
                this.productTradeType = productTradeType;
            }

            public int getTaxation() {
                return taxation;
            }

            public void setTaxation(int taxation) {
                this.taxation = taxation;
            }

            public int getSizeId() {
                return sizeId;
            }

            public void setSizeId(int sizeId) {
                this.sizeId = sizeId;
            }

            public boolean isInvaledFirst() {
                return isInvaledFirst;
            }

            public boolean isGroupLast() {
                return isGroupLast;
            }

            public void setGroupLast(boolean groupLast) {
                isGroupLast = groupLast;
            }

            public void setInvaledFirst(boolean invaledFirst) {
                isInvaledFirst = invaledFirst;
            }

            public int getGoodPromotionId() {
                return goodPromotionId;
            }

            public void setGoodPromotionId(int goodPromotionId) {
                this.goodPromotionId = goodPromotionId;
            }

            public double getOriginalPrice() {
                return originalPrice;
            }

            public void setOriginalPrice(double originalPrice) {
                this.originalPrice = originalPrice;
            }

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public FlashPromotionBean getFlashPromotion() {
                return flashPromotion;
            }

            public void setFlashPromotion(FlashPromotionBean flashPromotion) {
                this.flashPromotion = flashPromotion;
            }

            public double getPromotionPrice() {
                return promotionPrice;
            }

            public void setPromotionPrice(double promotionPrice) {
                this.promotionPrice = promotionPrice;
            }

            public double getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(double totalPrice) {
                this.totalPrice = totalPrice;
            }

            public int getColorId() {
                return colorId;
            }

            public void setColorId(int colorId) {
                this.colorId = colorId;
            }

            public int getFreezeStore() {
                return freezeStore;
            }

            public void setFreezeStore(int freezeStore) {
                this.freezeStore = freezeStore;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public OrderPromotionBean getOrderPromotion() {
                return orderPromotion;
            }

            public void setOrderPromotion(OrderPromotionBean orderPromotion) {
                this.orderPromotion = orderPromotion;
            }

            public double getOldPrice() {
                return oldPrice;
            }

            public void setOldPrice(double oldPrice) {
                this.oldPrice = oldPrice;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getAfter() {
                return after;
            }

            public void setAfter(int after) {
                this.after = after;
            }

            public int getSkuId() {
                return skuId;
            }

            public void setSkuId(int skuId) {
                this.skuId = skuId;
            }

            public int getDesignerId() {
                return designerId;
            }

            public void setDesignerId(int designerId) {
                this.designerId = designerId;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public int getOldQuantity() {
                return oldQuantity;
            }

            public void setOldQuantity(int oldQuantity) {
                this.oldQuantity = oldQuantity;
            }

            public long getProductId() {
                return productId;
            }

            public void setProductId(long productId) {
                this.productId = productId;
            }

            public String getProductImg() {
                return productImg;
            }

            public void setProductImg(String productImg) {
                this.productImg = productImg;
            }

            public String getDesigner() {
                return designer;
            }

            public void setDesigner(String designer) {
                this.designer = designer;
            }

            public int getOrderPromotionId() {
                return orderPromotionId;
            }

            public void setOrderPromotionId(int orderPromotionId) {
                this.orderPromotionId = orderPromotionId;
            }

            public int getAvailableStore() {
                return availableStore;
            }

            public void setAvailableStore(int availableStore) {
                this.availableStore = availableStore;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                if (size==null){
                    this.size="";
                }else {
                    this.size = size;
                }
            }

            public double getMinPrice() {
                return minPrice;
            }

            public void setMinPrice(double minPrice) {
                this.minPrice = minPrice;
            }

            public int getFlashPromotionId() {
                return flashPromotionId;
            }

            public void setFlashPromotionId(int flashPromotionId) {
                this.flashPromotionId = flashPromotionId;
            }

            public double getProductPrice() {
                return productPrice;
            }

            public void setProductPrice(double productPrice) {
                this.productPrice = productPrice;
            }

            public int getMark() {
                return mark;
            }

            public void setMark(int mark) {
                this.mark = mark;
            }

            public boolean isGroupFirst() {
                return isGroupFirst;
            }

            public void setGroupFirst(boolean groupFirst) {
                isGroupFirst = groupFirst;
            }

            public List<OrderPromotionBean> getChoiceOrderPromotions() {
                return choiceOrderPromotions;
            }

            public void setChoiceOrderPromotions(List<OrderPromotionBean> choiceOrderPromotions) {
                this.choiceOrderPromotions = choiceOrderPromotions;
            }

            public OrderPromotionBean getGoodPromotion() {
                return goodPromotion;
            }

            public void setGoodPromotion(OrderPromotionBean goodPromotion) {
                this.goodPromotion = goodPromotion;
            }

            public void setShowPromotion(boolean showPromotion) {
                this.showPromotion = showPromotion;
            }

            public boolean isShowPromotion() {
                return showPromotion;
            }

            public boolean isShowbg() {
                return showbg;
            }

            public void setShowbg(boolean showbg) {
                this.showbg = showbg;
            }

            public static class FlashPromotionBean {
                /**
                 * promotionType : 0
                 * endDate : 2017/12/03 00:00:00
                 * session : 01:00
                 * promotionScope : 0
                 * sessionName : 冰天雪地场
                 * name : 白菜专用测试商品限时购
                 * statusName : 已开抢
                 * id : 10
                 * limitQuantity : 5
                 * startDate : 2017/11/29 00:00:00
                 */
                private String promotionType;
                private Date endDate;
                private String session;
                private int promotionScope;
                private String sessionName;
                private String name;
                private String statusName;
                private int id;
                private int limitQuantity;
                private Date startDate;
                private long startTimeStamp;
                private long endTimeStamp;
                private String flashUrl;
                private String priceBackPic;

                public String getPriceBackPic() {
                    return priceBackPic;
                }

                public void setPriceBackPic(String priceBackPic) {
                    this.priceBackPic = priceBackPic;
                }

                public String getFlashUrl() {
                    return flashUrl;
                }

                public void setFlashUrl(String flashUrl) {
                    this.flashUrl = flashUrl;
                }

                public String getPromotionType() {
                    return promotionType;
                }

                public void setPromotionType(String promotionType) {
                    this.promotionType = promotionType;
                }

                public Date getEndDate() {
                    return endDate;
                }

                public void setEndDate(Date endDate) {
                    this.endDate = endDate;
                }

                public String getSession() {
                    return session;
                }

                public void setSession(String session) {
                    this.session = session;
                }

                public int getPromotionScope() {
                    return promotionScope;
                }

                public void setPromotionScope(int promotionScope) {
                    this.promotionScope = promotionScope;
                }

                public String getSessionName() {
                    return sessionName;
                }

                public void setSessionName(String sessionName) {
                    this.sessionName = sessionName;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getStatusName() {
                    return statusName;
                }

                public void setStatusName(String statusName) {
                    this.statusName = statusName;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getLimitQuantity() {
                    return limitQuantity;
                }

                public void setLimitQuantity(int limitQuantity) {
                    this.limitQuantity = limitQuantity;
                }

                public Date getStartDate() {
                    return startDate;
                }

                public void setStartDate(Date startDate) {
                    this.startDate = startDate;
                }

                public long getStartTimeStamp() {
                    return startTimeStamp;
                }

                public void setStartTimeStamp(long startTimeStamp) {
                    this.startTimeStamp = startTimeStamp;
                }

                public long getEndTimeStamp() {
                    return endTimeStamp;
                }

                public void setEndTimeStamp(long endTimeStamp) {
                    this.endTimeStamp = endTimeStamp;
                }
            }

            public static class SoonPromotionBean {
                private String promotionType;
                private Date endTime;
                private String prefix;
                private int promotionScope;
                private String promotionName;
                private int id;
                private Date startTime;
                private String promotionUrl;
                public int advance;
                private String priceBackPic;

                public int getAdvance() {
                    return advance;
                }

                public void setAdvance(int advance) {
                    this.advance = advance;
                }

                public String getPromotionType() {
                    return promotionType;
                }

                public void setPromotionType(String promotionType) {
                    this.promotionType = promotionType;
                }

                public Date getEndTime() {
                    return endTime;
                }

                public void setEndTime(Date endTime) {
                    this.endTime = endTime;
                }

                public String getPrefix() {
                    return prefix;
                }

                public void setPrefix(String prefix) {
                    this.prefix = prefix;
                }

                public int getPromotionScope() {
                    return promotionScope;
                }

                public void setPromotionScope(int promotionScope) {
                    this.promotionScope = promotionScope;
                }

                public String getPromotionName() {
                    return promotionName;
                }

                public void setPromotionName(String promotionName) {
                    this.promotionName = promotionName;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public Date getStartTime() {
                    return startTime;
                }

                public void setStartTime(Date startTime) {
                    this.startTime = startTime;
                }

                public String getPromotionUrl() {
                    return promotionUrl;
                }

                public void setPromotionUrl(String promotionUrl) {
                    this.promotionUrl = promotionUrl;
                }

                public String getPriceBackPic() {
                    return priceBackPic;
                }

                public void setPriceBackPic(String priceBackPic) {
                    this.priceBackPic = priceBackPic;
                }
            }

            public static class OrderPromotionBean {
                /**
                 * promotionUrl : /promotion/6325
                 * promotionName : 白菜专用测试订单活动
                 * promotionType : 3
                 * promotionTypeName : 满减上不封顶
                 * name : 白菜专用测试订单活动
                 * id : 6325
                 * promotionId : 6325
                 */

                private String promotionUrl;
                private String promotionName;
                private String promotionType;
                private String promotionTypeName;
                private String name;
                private int id;
                private String promotionSulo;
                private int promotionId;
                private String solution;

                public String getSolution() {
                    return solution;
                }

                public void setSolution(String solution) {
                    this.solution = solution;
                }

                public String getPromotionUrl() {
                    return promotionUrl;
                }

                public void setPromotionUrl(String promotionUrl) {
                    this.promotionUrl = promotionUrl;
                }

                public String getPromotionName() {
                    return promotionName;
                }

                public void setPromotionName(String promotionName) {
                    this.promotionName = promotionName;
                }

                public String getPromotionType() {
                    return promotionType;
                }

                public void setPromotionType(String promotionType) {
                    this.promotionType = promotionType;
                }

                public String getPromotionTypeName() {
                    return promotionTypeName;
                }

                public void setPromotionTypeName(String promotionTypeName) {
                    this.promotionTypeName = promotionTypeName;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getPromotionId() {
                    return promotionId;
                }

                public void setPromotionId(int promotionId) {
                    this.promotionId = promotionId;
                }

                public String getPromotionSulo() {
                    return promotionSulo;
                }

                public void setPromotionSulo(String promotionSulo) {
                    this.promotionSulo = promotionSulo;
                }

                public static class SolutionBean{
                    private double price;
                    private int count;

                    public double getPrice() {
                        return price;
                    }

                    public void setPrice(double price) {
                        this.price = price;
                    }

                    public int getCount() {
                        return count;
                    }

                    public void setCount(int count) {
                        this.count = count;
                    }
                }
            }

            public static class ChoiceOrderPromotionsBean {
                /**
                 * promotionUrl : /promotion/6325
                 * promotionName : 白菜专用测试订单活动
                 * promotionType : 3
                 * promotionTypeName : 满减上不封顶
                 * name : 白菜专用测试订单活动
                 * id : 6325
                 * promotionId : 6325
                 */

                private String promotionUrl;
                private String promotionName;
                private int promotionType;
                private String promotionTypeName;
                private String name;
                private int id;
                private int promotionId;

                public String getPromotionUrl() {
                    return promotionUrl;
                }

                public void setPromotionUrl(String promotionUrl) {
                    this.promotionUrl = promotionUrl;
                }

                public String getPromotionName() {
                    return promotionName;
                }

                public void setPromotionName(String promotionName) {
                    this.promotionName = promotionName;
                }

                public int getPromotionType() {
                    return promotionType;
                }

                public void setPromotionType(int promotionType) {
                    this.promotionType = promotionType;
                }

                public String getPromotionTypeName() {
                    return promotionTypeName;
                }

                public void setPromotionTypeName(String promotionTypeName) {
                    this.promotionTypeName = promotionTypeName;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getPromotionId() {
                    return promotionId;
                }

                public void setPromotionId(int promotionId) {
                    this.promotionId = promotionId;
                }
            }
        }
    }
}
