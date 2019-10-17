package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/12/29 11:13
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ConsultDetailBean extends BaseBean {

    /**
     * data : {"product":{"img":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","originalPrice":6200,"promotionPrice":0.01,"sellPrice":0.01,"soonPrice":0.01,"isCart":1,"flashPrice":0,"categoryName":"1","colors":[{"img":"/sp/4/101504/cbbc4ed1576ac2817354c96828302fcf","code":"120","groupId":0,"name":"颜色","id":110,"value":"条文"}],"firstRatio":0,"sizes":[{"img":"","code":"08","groupId":0,"name":"尺码","id":377,"value":"XXXXXL码"},{"img":"","code":"07","groupId":0,"name":"尺码","id":376,"value":"XXXXL码"},{"img":"","code":"06","groupId":0,"name":"尺码","id":31,"value":"XXXL码"},{"img":"","code":"05","groupId":0,"name":"尺码","id":30,"value":"XXL码"}],"price":0.01,"recomScore":86719,"isTopical":false,"productSellType":"SPOT","id":101504,"brand":"设计师-测试专用","designerId":10942,"comments":73,"isSpot":false,"grossRatio":1,"salePrice":0.01,"secondRatio":0.2,"store":1,"designer":"设计师-测试专用","consults":7,"isCrowd":false,"minPrice":0.01,"name":"测试商品 请勿购买","collectioned":0,"mark":1,"categoryIds":1800},"consult":{"question":"hhhhhhhjhhh","nickName":"真的很厉害哦","replyDate":"2017/12/29 11:12:04","recomend":0,"productPic":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","id":26258,"reply":"阿斯顿发大水","headPic":"/app/a/17/12/18/eb5fc0716a069ba9d9d788dc44a410b3","inernalSn":"Z00541088001","productName":"测试商品 请勿购买","createDate":"2017/12/29 11:13:03"}}
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
         * product : {"img":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","originalPrice":6200,"promotionPrice":0.01,"sellPrice":0.01,"soonPrice":0.01,"isCart":1,"flashPrice":0,"categoryName":"1","colors":[{"img":"/sp/4/101504/cbbc4ed1576ac2817354c96828302fcf","code":"120","groupId":0,"name":"颜色","id":110,"value":"条文"}],"firstRatio":0,"sizes":[{"img":"","code":"08","groupId":0,"name":"尺码","id":377,"value":"XXXXXL码"},{"img":"","code":"07","groupId":0,"name":"尺码","id":376,"value":"XXXXL码"},{"img":"","code":"06","groupId":0,"name":"尺码","id":31,"value":"XXXL码"},{"img":"","code":"05","groupId":0,"name":"尺码","id":30,"value":"XXL码"}],"price":0.01,"recomScore":86719,"isTopical":false,"productSellType":"SPOT","id":101504,"brand":"设计师-测试专用","designerId":10942,"comments":73,"isSpot":false,"grossRatio":1,"salePrice":0.01,"secondRatio":0.2,"store":1,"designer":"设计师-测试专用","consults":7,"isCrowd":false,"minPrice":0.01,"name":"测试商品 请勿购买","collectioned":0,"mark":1,"categoryIds":1800}
         * consult : {"question":"hhhhhhhjhhh","nickName":"真的很厉害哦","replyDate":"2017/12/29 11:12:04","recomend":0,"productPic":"/pi/4/101504/cbbc4ed1576ac2817354c96828302fcf","id":26258,"reply":"阿斯顿发大水","headPic":"/app/a/17/12/18/eb5fc0716a069ba9d9d788dc44a410b3","inernalSn":"Z00541088001","productName":"测试商品 请勿购买","createDate":"2017/12/29 11:13:03"}
         */

        private ProductBean product;
        private ConsultBean consult;

        public ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductBean product) {
            this.product = product;
        }

        public ConsultBean getConsult() {
            return consult;
        }

        public void setConsult(ConsultBean consult) {
            this.consult = consult;
        }

        public static class ProductBean {
            /**
             * img : /pi/4/101504/cbbc4ed1576ac2817354c96828302fcf
             * originalPrice : 6200
             * promotionPrice : 0.01
             * sellPrice : 0.01
             * soonPrice : 0.01
             * isCart : 1
             * flashPrice : 0
             * categoryName : 1
             * colors : [{"img":"/sp/4/101504/cbbc4ed1576ac2817354c96828302fcf","code":"120","groupId":0,"name":"颜色","id":110,"value":"条文"}]
             * firstRatio : 0
             * sizes : [{"img":"","code":"08","groupId":0,"name":"尺码","id":377,"value":"XXXXXL码"},{"img":"","code":"07","groupId":0,"name":"尺码","id":376,"value":"XXXXL码"},{"img":"","code":"06","groupId":0,"name":"尺码","id":31,"value":"XXXL码"},{"img":"","code":"05","groupId":0,"name":"尺码","id":30,"value":"XXL码"}]
             * price : 0.01
             * recomScore : 86719
             * isTopical : false
             * productSellType : SPOT
             * id : 101504
             * brand : 设计师-测试专用
             * designerId : 10942
             * comments : 73
             * isSpot : false
             * grossRatio : 1
             * salePrice : 0.01
             * secondRatio : 0.2
             * store : 1
             * designer : 设计师-测试专用
             * consults : 7
             * isCrowd : false
             * minPrice : 0.01
             * name : 测试商品 请勿购买
             * collectioned : 0
             * mark : 1
             * categoryIds : 1800
             */

            private String img;
            private double originalPrice;
            private double promotionPrice;
            private double sellPrice;
            private double soonPrice;
            private int isCart;
            private double flashPrice;
            private String categoryName;
            private double firstRatio;
            private double price;
            private int recomScore;
            private boolean isTopical;
            private String productSellType;
            private long id;
            private String brand;
            private int designerId;
            private int comments;
            private boolean isSpot;
            private double grossRatio;
            private double salePrice;
            private double secondRatio;
            private int store;
            private String designer;
            private int consults;
            private boolean isCrowd;
            private double minPrice;
            private String name;
            private int collectioned;
            private int mark;
            private int categoryId;
            private List<ColorsBean> colors;
            private List<SizesBean> sizes;
            private long promotionId;

            public long getPromotionId() {
                return promotionId;
            }

            public void setPromotionId(long promotionId) {
                this.promotionId = promotionId;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public double getOriginalPrice() {
                return originalPrice;
            }

            public void setOriginalPrice(double originalPrice) {
                this.originalPrice = originalPrice;
            }

            public double getPromotionPrice() {
                return promotionPrice;
            }

            public void setPromotionPrice(double promotionPrice) {
                this.promotionPrice = promotionPrice;
            }

            public double getSellPrice() {
                return sellPrice;
            }

            public void setSellPrice(double sellPrice) {
                this.sellPrice = sellPrice;
            }

            public double getSoonPrice() {
                return soonPrice;
            }

            public void setSoonPrice(double soonPrice) {
                this.soonPrice = soonPrice;
            }

            public int getIsCart() {
                return isCart;
            }

            public void setIsCart(int isCart) {
                this.isCart = isCart;
            }

            public double getFlashPrice() {
                return flashPrice;
            }

            public void setFlashPrice(double flashPrice) {
                this.flashPrice = flashPrice;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public double getFirstRatio() {
                return firstRatio;
            }

            public void setFirstRatio(double firstRatio) {
                this.firstRatio = firstRatio;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getRecomScore() {
                return recomScore;
            }

            public void setRecomScore(int recomScore) {
                this.recomScore = recomScore;
            }

            public boolean isIsTopical() {
                return isTopical;
            }

            public void setIsTopical(boolean isTopical) {
                this.isTopical = isTopical;
            }

            public String getProductSellType() {
                return productSellType;
            }

            public void setProductSellType(String productSellType) {
                this.productSellType = productSellType;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public int getDesignerId() {
                return designerId;
            }

            public void setDesignerId(int designerId) {
                this.designerId = designerId;
            }

            public int getComments() {
                return comments;
            }

            public void setComments(int comments) {
                this.comments = comments;
            }

            public boolean isIsSpot() {
                return isSpot;
            }

            public void setIsSpot(boolean isSpot) {
                this.isSpot = isSpot;
            }

            public double getGrossRatio() {
                return grossRatio;
            }

            public void setGrossRatio(double grossRatio) {
                this.grossRatio = grossRatio;
            }

            public double getSalePrice() {
                return salePrice;
            }

            public void setSalePrice(double salePrice) {
                this.salePrice = salePrice;
            }

            public double getSecondRatio() {
                return secondRatio;
            }

            public void setSecondRatio(double secondRatio) {
                this.secondRatio = secondRatio;
            }

            public int getStore() {
                return store;
            }

            public void setStore(int store) {
                this.store = store;
            }

            public String getDesigner() {
                return designer;
            }

            public void setDesigner(String designer) {
                this.designer = designer;
            }

            public int getConsults() {
                return consults;
            }

            public void setConsults(int consults) {
                this.consults = consults;
            }

            public boolean isIsCrowd() {
                return isCrowd;
            }

            public void setIsCrowd(boolean isCrowd) {
                this.isCrowd = isCrowd;
            }

            public double getMinPrice() {
                return minPrice;
            }

            public void setMinPrice(double minPrice) {
                this.minPrice = minPrice;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCollectioned() {
                return collectioned;
            }

            public void setCollectioned(int collectioned) {
                this.collectioned = collectioned;
            }

            public int getMark() {
                return mark;
            }

            public void setMark(int mark) {
                this.mark = mark;
            }

            public int getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(int categoryId) {
                this.categoryId = categoryId;
            }

            public List<ColorsBean> getColors() {
                return colors;
            }

            public void setColors(List<ColorsBean> colors) {
                this.colors = colors;
            }

            public List<SizesBean> getSizes() {
                return sizes;
            }

            public void setSizes(List<SizesBean> sizes) {
                this.sizes = sizes;
            }

            public static class ColorsBean {
                /**
                 * img : /sp/4/101504/cbbc4ed1576ac2817354c96828302fcf
                 * code : 120
                 * groupId : 0
                 * name : 颜色
                 * id : 110
                 * value : 条文
                 */

                private String img;
                private String code;
                private int groupId;
                private String name;
                private int id;
                private String value;

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public int getGroupId() {
                    return groupId;
                }

                public void setGroupId(int groupId) {
                    this.groupId = groupId;
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

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class SizesBean {
                /**
                 * img :
                 * code : 08
                 * groupId : 0
                 * name : 尺码
                 * id : 377
                 * value : XXXXXL码
                 */

                private String img;
                private String code;
                private int groupId;
                private String name;
                private int id;
                private String value;

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public int getGroupId() {
                    return groupId;
                }

                public void setGroupId(int groupId) {
                    this.groupId = groupId;
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

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }

        public static class ConsultBean {
            /**
             * question : hhhhhhhjhhh
             * nickName : 真的很厉害哦
             * replyDate : 2017/12/29 11:12:04
             * recomend : 0
             * productPic : /pi/4/101504/cbbc4ed1576ac2817354c96828302fcf
             * id : 26258
             * reply : 阿斯顿发大水
             * headPic : /app/a/17/12/18/eb5fc0716a069ba9d9d788dc44a410b3
             * inernalSn : Z00541088001
             * productName : 测试商品 请勿购买
             * createDate : 2017/12/29 11:13:03
             */

            private String question;
            private String nickName;
            private String replyDate;
            private int recomend;
            private String productPic;
            private int id;
            private String reply;
            private String headPic;
            private String inernalSn;
            private String productName;
            private String createDate;

            public String getQuestion() {
                return question;
            }

            public void setQuestion(String question) {
                this.question = question;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getReplyDate() {
                return replyDate;
            }

            public void setReplyDate(String replyDate) {
                this.replyDate = replyDate;
            }

            public int getRecomend() {
                return recomend;
            }

            public void setRecomend(int recomend) {
                this.recomend = recomend;
            }

            public String getProductPic() {
                return productPic;
            }

            public void setProductPic(String productPic) {
                this.productPic = productPic;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getReply() {
                return reply;
            }

            public void setReply(String reply) {
                this.reply = reply;
            }

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }

            public String getInernalSn() {
                return inernalSn;
            }

            public void setInernalSn(String inernalSn) {
                this.inernalSn = inernalSn;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }
        }
    }
}
