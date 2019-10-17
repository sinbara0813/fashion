package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/11 13:51
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainSpecialBean1 extends BaseBean {

    /**
     * message :
     * datas : {}
     * list : [{"designerId":10905,"img":"/2017/05/19/035105a537461a90beb79ff3b90233d71177fe.jpg","comments":0,"originalPrice":1390,"isSpot":false,"promotionPrice":1390,"store":1,"designer":"Chris Ma","isCart":1,"colors":[{"img":"/2017/05/19/035053a537461a90beb79ff3b90233d71177fe.jpg","code":"058","groupId":0,"name":"颜色","id":93,"value":"绿色"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"10","groupId":0,"name":"尺码","id":150,"value":"XS码"},{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"}],"price":1390,"minPrice":1390,"name":"MACQ CHRIS MA   绿色手钉钻套装","recomScore":null,"collectioned":null,"isTopical":false,"productSellType":"CUSTOM","id":160385,"mark":1},{"designerId":10944,"img":"/2017/08/02/061828f22c7855576e9b208fcde4423cec68fe.jpg","comments":0,"originalPrice":428,"isSpot":false,"promotionPrice":428,"store":1,"designer":"Hera velling","isCart":1,"colors":[{"img":"/2017/08/02/061825f22c7855576e9b208fcde4423cec68fe.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":"自定义"}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"F"}],"price":428,"minPrice":428,"name":"HeraVelling 荷叶边宽松立领白色长袖衬衫上衣","recomScore":null,"collectioned":null,"isTopical":true,"productSellType":"CUSTOM","id":167467,"mark":1},{"designerId":10944,"img":"/2017/06/07/083233884862b13bd0be0c9c7a62fa8dd0d316.jpg","comments":0,"originalPrice":428,"isSpot":false,"promotionPrice":428,"store":1,"designer":"Hera velling","isCart":1,"colors":[{"img":"/2017/06/07/083212884862b13bd0be0c9c7a62fa8dd0d316.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":""}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"S"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"M"},{"img":"","code":"0","groupId":0,"name":"尺码","id":3,"value":"L"}],"price":428,"minPrice":428,"name":"HeraVelling波点高腰阔腿裤","recomScore":null,"collectioned":null,"isTopical":false,"productSellType":"CUSTOM","id":162515,"mark":1},{"designerId":10944,"img":"/2017/06/07/082527dc84df0a0b8880b92c8f293da9cc4b70.jpg","comments":0,"originalPrice":728,"isSpot":false,"promotionPrice":728,"store":1,"designer":"Hera velling","isCart":1,"colors":[{"img":"/2017/06/07/082516dc84df0a0b8880b92c8f293da9cc4b70.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":""}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"F"}],"price":728,"minPrice":728,"name":"HeraVelling天真蓝条纹伞形衬衫连衣裙","recomScore":null,"collectioned":null,"isTopical":false,"productSellType":"CUSTOM","id":162512,"mark":1},{"designerId":10944,"img":"/2017/05/03/093440ac17aa137e609c88f137e26c2b760208.jpg","comments":2,"originalPrice":458,"isSpot":false,"promotionPrice":458,"store":1,"designer":"Hera velling","isCart":1,"colors":[{"img":"/2017/05/03/093437ac17aa137e609c88f137e26c2b760208.jpg","code":"0","groupId":0,"name":"颜色","id":1,"value":""}],"consults":0,"isCrowd":false,"sizes":[{"img":"","code":"0","groupId":0,"name":"尺码","id":1,"value":"S"},{"img":"","code":"0","groupId":0,"name":"尺码","id":2,"value":"M"}],"price":458,"minPrice":458,"name":"HeraVelling穿了腿长一米二的大开衩喇叭裤","recomScore":null,"collectioned":null,"isTopical":false,"productSellType":"CUSTOM","id":158702,"mark":1}]
     * total : 5
     * index : 1
     * pageCount : 1
     * pageSize : 5
     * next : false
     */

    private String message;
    private DatasBean datas;
    private int total;
    private int index;
    private int pageCount;
    private int pageSize;
    private boolean next;
    private List<ListBean> list;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class DatasBean {
    }

    public static class ListBean {
        /**
         * designerId : 10905
         * img : /2017/05/19/035105a537461a90beb79ff3b90233d71177fe.jpg
         * comments : 0
         * originalPrice : 1390.0
         * isSpot : false
         * promotionPrice : 1390.0
         * store : 1
         * designer : Chris Ma
         * isCart : 1
         * colors : [{"img":"/2017/05/19/035053a537461a90beb79ff3b90233d71177fe.jpg","code":"058","groupId":0,"name":"颜色","id":93,"value":"绿色"}]
         * consults : 0
         * isCrowd : false
         * sizes : [{"img":"","code":"10","groupId":0,"name":"尺码","id":150,"value":"XS码"},{"img":"","code":"01","groupId":0,"name":"尺码","id":26,"value":"S码"},{"img":"","code":"02","groupId":0,"name":"尺码","id":27,"value":"M码"}]
         * price : 1390.0
         * minPrice : 1390.0
         * name : MACQ CHRIS MA   绿色手钉钻套装
         * recomScore : null
         * collectioned : null
         * isTopical : false
         * productSellType : CUSTOM
         * id : 160385
         * mark : 1
         */

        private int designerId;
        private String img;
        private int comments;
        private double originalPrice;
        private boolean isSpot;
        private double promotionPrice;
        private int store;
        private String designer;
        private int isCart;
        private int consults;
        private boolean isCrowd;
        private double price;
        private double minPrice;
        private String name;
        private String recomScore;
        private String collectioned;
        private boolean isTopical;
        private String productSellType;
        private int id;
        private int mark;
        private List<ColorsBean> colors;
        private List<SizesBean> sizes;

        public int getDesignerId() {
            return designerId;
        }

        public void setDesignerId(int designerId) {
            this.designerId = designerId;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public double getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(double originalPrice) {
            this.originalPrice = originalPrice;
        }

        public boolean isIsSpot() {
            return isSpot;
        }

        public void setIsSpot(boolean isSpot) {
            this.isSpot = isSpot;
        }

        public double getPromotionPrice() {
            return promotionPrice;
        }

        public void setPromotionPrice(double promotionPrice) {
            this.promotionPrice = promotionPrice;
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

        public int getIsCart() {
            return isCart;
        }

        public void setIsCart(int isCart) {
            this.isCart = isCart;
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

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
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

        public Object getRecomScore() {
            return recomScore;
        }

        public void setRecomScore(String recomScore) {
            this.recomScore = recomScore;
        }

        public Object getCollectioned() {
            return collectioned;
        }

        public void setCollectioned(String collectioned) {
            this.collectioned = collectioned;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMark() {
            return mark;
        }

        public void setMark(int mark) {
            this.mark = mark;
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
             * img : /2017/05/19/035053a537461a90beb79ff3b90233d71177fe.jpg
             * code : 058
             * groupId : 0
             * name : 颜色
             * id : 93
             * value : 绿色
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
             * code : 10
             * groupId : 0
             * name : 尺码
             * id : 150
             * value : XS码
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
}
