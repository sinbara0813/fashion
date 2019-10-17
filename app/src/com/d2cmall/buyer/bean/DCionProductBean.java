package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.Date;

/**
 * Created by Administrator on 2018/8/9.
 * Description : DCionProductBean
 */

public class DCionProductBean extends BaseBean {

    /**
     * data : {"pointProduct":{"amount":0,"productId":null,"endDate":1536422400000,"name":"测试外部兑换","description":"<p>1111<\/p>","id":12,"introPic":"/2018/08/16/09015988909a8c1a4022ff1ca89e8223d779f5.jpg","type":"CARD","detailPic":"/2018/08/16/09020303505d4974c92636336c34ca7fc4f539.jpg","point":1,"startDate":1534348800000},"card":{"sourceId":107,"code":"3333333.0","useDate":1534412371000,"sn":"PE1534412371022"}}
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
         * pointProduct : {"amount":0,"productId":null,"endDate":1536422400000,"name":"测试外部兑换","description":"<p>1111<\/p>","id":12,"introPic":"/2018/08/16/09015988909a8c1a4022ff1ca89e8223d779f5.jpg","type":"CARD","detailPic":"/2018/08/16/09020303505d4974c92636336c34ca7fc4f539.jpg","point":1,"startDate":1534348800000}
         * card : {"sourceId":107,"code":"3333333.0","useDate":1534412371000,"sn":"PE1534412371022"}
         */

        private PointProductBean pointProduct;
        private CardBean card;

        public PointProductBean getPointProduct() {
            return pointProduct;
        }

        public void setPointProduct(PointProductBean pointProduct) {
            this.pointProduct = pointProduct;
        }

        public CardBean getCard() {
            return card;
        }

        public void setCard(CardBean card) {
            this.card = card;
        }

        public static class PointProductBean {
            /**
             * amount : 0.0
             * productId : null
             * endDate : 1536422400000
             * name : 测试外部兑换
             * description : <p>1111</p>
             * id : 12
             * introPic : /2018/08/16/09015988909a8c1a4022ff1ca89e8223d779f5.jpg
             * type : CARD
             * detailPic : /2018/08/16/09020303505d4974c92636336c34ca7fc4f539.jpg
             * point : 1
             * startDate : 1534348800000
             */

            private double amount;
            private int productId;
            private Date endDate;
            private String name;
            private String description;
            private int id;
            private String introPic;
            private String type;
            private String detailPic;
            private int point;
            private Date startDate;

            private int count;
            
            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
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

            public Date getEndDate() {
                return endDate;
            }

            public void setEndDate(Date endDate) {
                this.endDate = endDate;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIntroPic() {
                return introPic;
            }

            public void setIntroPic(String introPic) {
                this.introPic = introPic;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDetailPic() {
                return detailPic;
            }

            public void setDetailPic(String detailPic) {
                this.detailPic = detailPic;
            }

            public int getPoint() {
                return point;
            }

            public void setPoint(int point) {
                this.point = point;
            }

            public Date getStartDate() {
                return startDate;
            }

            public void setStartDate(Date startDate) {
                this.startDate = startDate;
            }
        }

        public static class CardBean {
            /**
             * sourceId : 107
             * code : 3333333.0
             * useDate : 1534412371000
             * sn : PE1534412371022
             */

            private int sourceId;
            private String code;
            private Date useDate;
            private String sn;

            public int getSourceId() {
                return sourceId;
            }

            public void setSourceId(int sourceId) {
                this.sourceId = sourceId;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public Date getUseDate() {
                return useDate;
            }

            public void setUseDate(Date useDate) {
                this.useDate = useDate;
            }

            public String getSn() {
                return sn;
            }

            public void setSn(String sn) {
                this.sn = sn;
            }
        }
    }
}
