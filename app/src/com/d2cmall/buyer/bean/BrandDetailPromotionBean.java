package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/8/7.
 * Description : BrandDetailPromotionBean
 */

public class BrandDetailPromotionBean extends BaseBean {

    /**
     * data : {"promotions":[],"couponDefs":[{"enableDate":"2018/08/02 00:00","amount":12,"couponName":"折扣 设计师","remark":"1","type":"DISCOUNT","price":null,"name":"折扣 设计师","activeDay":0,"expireDate":"2018/08/31 00:00","id":1231,"free":1,"isClaim":true,"needAmount":0}],"articles":[{"hits":0,"backgroundUrl":null,"articleTemplateId":5,"name":"Thailand11D180404","publishDate":1533019694000,"id":12084,"title":"新潮与经典-11位泰国设计师的时尚洞见","picture":"/2018/04/04/050615d57bedb4e70280731262c24e12dd4cd1.jpg","content":null}]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<CouponDefsBean> couponDefs;
        private List<ArticlesBean> articles;
        private List<PromotionsBean> promotions;
        private List<PromotionsBean> otherPromotions;


        public List<CouponDefsBean> getCouponDefs() {
            return couponDefs;
        }

        public void setCouponDefs(List<CouponDefsBean> couponDefs) {
            this.couponDefs = couponDefs;
        }

        public List<ArticlesBean> getArticles() {
            return articles;
        }

        public void setArticles(List<ArticlesBean> articles) {
            this.articles = articles;
        }

        public List<PromotionsBean> getPromotions() {
            return promotions;
        }

        public void setPromotions(List<PromotionsBean> promotions) {
            this.promotions = promotions;
        }

        public List<PromotionsBean> getOtherPromotions() {
            return otherPromotions;
        }

        public void setOtherPromotions(List<PromotionsBean> otherPromotions) {
            this.otherPromotions = otherPromotions;
        }


        public static class CouponDefsBean {
            /**
             * enableDate : 2018/08/02 00:00
             * amount : 12
             * couponName : 折扣 设计师
             * remark : 1
             * type : DISCOUNT
             * price : null
             * name : 折扣 设计师
             * activeDay : 0
             * expireDate : 2018/08/31 00:00
             * id : 1231
             * free : 1
             * isClaim : true
             * needAmount : 0
             */

            private String enableDate;
            private double amount;
            private String couponName;
            private String remark;
            private String type;
            private double price;
            private String name;
            private int activeDay;
            private String expireDate;
            private long id;
            private int free;
            private boolean isClaim;
            private double needAmount;

            public String getEnableDate() {
                return enableDate;
            }

            public void setEnableDate(String enableDate) {
                this.enableDate = enableDate;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public String getCouponName() {
                return couponName;
            }

            public void setCouponName(String couponName) {
                this.couponName = couponName;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getActiveDay() {
                return activeDay;
            }

            public void setActiveDay(int activeDay) {
                this.activeDay = activeDay;
            }

            public String getExpireDate() {
                return expireDate;
            }

            public void setExpireDate(String expireDate) {
                this.expireDate = expireDate;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public int getFree() {
                return free;
            }

            public void setFree(int free) {
                this.free = free;
            }

            public boolean isIsClaim() {
                return isClaim;
            }

            public void setIsClaim(boolean isClaim) {
                this.isClaim = isClaim;
            }

            public double getNeedAmount() {
                return needAmount;
            }

            public void setNeedAmount(double needAmount) {
                this.needAmount = needAmount;
            }
        }

        public static class ArticlesBean {
            /**
             * hits : 0
             * backgroundUrl : null
             * articleTemplateId : 5
             * name : Thailand11D180404
             * publishDate : 1533019694000
             * id : 12084
             * title : 新潮与经典-11位泰国设计师的时尚洞见
             * picture : /2018/04/04/050615d57bedb4e70280731262c24e12dd4cd1.jpg
             * content : null
             */

            private int hits;
            private String backgroundUrl;
            private int articleTemplateId;
            private String name;
            private Date publishDate;
            private int id;
            private String title;
            private String picture;

            public String getBrandPic() {
                return brandPic;
            }

            public void setBrandPic(String brandPic) {
                this.brandPic = brandPic;
            }

            private String brandPic;
            private String content;

            public int getHits() {
                return hits;
            }

            public void setHits(int hits) {
                this.hits = hits;
            }

            public String getBackgroundUrl() {
                return backgroundUrl;
            }

            public void setBackgroundUrl(String backgroundUrl) {
                this.backgroundUrl = backgroundUrl;
            }

            public int getArticleTemplateId() {
                return articleTemplateId;
            }

            public void setArticleTemplateId(int articleTemplateId) {
                this.articleTemplateId = articleTemplateId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Date getPublishDate() {
                return publishDate;
            }

            public void setPublishDate(Date publishDate) {
                this.publishDate = publishDate;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPicture() {
                return picture;
            }

            public void setPicture(String picture) {
                this.picture = picture;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        public static class PromotionsBean {
            /**
             * backgroundUrl : null
             * promotionType : 3
             * prefix : null
             * type : 3
             * advance : 0
             * promotionUrl : /promotion/9787
             * promotionName : 订单活动满减上不封顶
             * promotionTypeName : 满减上不封顶
             * solution : 100-50
             * promotionSulo : 每满100减50
             * promotionScope : 1
             * wapBanner : null
             * startTime : 2018/07/14 00:00:00
             * id : 9787
             * endTime : 2018/09/29 00:00:00
             * status : true
             * brandPic : null
             */

            private String backgroundUrl;
            private int promotionType;
            private String prefix;
            private int type;
            private int advance;
            private String promotionUrl;
            private String promotionName;
            private String promotionTypeName;
            private String solution;
            private String promotionSulo;
            private int promotionScope;
            private String wapBanner;
            private Date startTime;
            private int id;
            private Date endTime;
            private boolean status;
            private String brandPic;

            public String getBackgroundUrl() {
                return backgroundUrl;
            }

            public void setBackgroundUrl(String backgroundUrl) {
                this.backgroundUrl = backgroundUrl;
            }

            public int getPromotionType() {
                return promotionType;
            }

            public void setPromotionType(int promotionType) {
                this.promotionType = promotionType;
            }

            public String getPrefix() {
                return prefix;
            }

            public void setPrefix(String prefix) {
                this.prefix = prefix;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getAdvance() {
                return advance;
            }

            public void setAdvance(int advance) {
                this.advance = advance;
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

            public String getPromotionTypeName() {
                return promotionTypeName;
            }

            public void setPromotionTypeName(String promotionTypeName) {
                this.promotionTypeName = promotionTypeName;
            }

            public String getSolution() {
                return solution;
            }

            public void setSolution(String solution) {
                this.solution = solution;
            }

            public String getPromotionSulo() {
                return promotionSulo;
            }

            public void setPromotionSulo(String promotionSulo) {
                this.promotionSulo = promotionSulo;
            }

            public int getPromotionScope() {
                return promotionScope;
            }

            public void setPromotionScope(int promotionScope) {
                this.promotionScope = promotionScope;
            }

            public String getWapBanner() {
                return wapBanner;
            }

            public void setWapBanner(String wapBanner) {
                this.wapBanner = wapBanner;
            }

            public Date getStartTime() {
                return startTime;
            }

            public void setStartTime(Date startTime) {
                this.startTime = startTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Date getEndTime() {
                return endTime;
            }

            public void setEndTime(Date endTime) {
                this.endTime = endTime;
            }

            public boolean isStatus() {
                return status;
            }

            public void setStatus(boolean status) {
                this.status = status;
            }

            public String getBrandPic() {
                return brandPic;
            }

            public void setBrandPic(String brandPic) {
                this.brandPic = brandPic;
            }
        }

    }
}
