package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.util.Util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/7/19 17:03
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductDetailBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private ProductBean product;
        private CountryBean country;

        private String weixin;
        private BrandBean brand;

        private List<PromotionsBean> promotions;
        private List<CouponsEntity> coupons;

        private List<RecommendProductsBean> recommendProducts;
        private List<RelationProductsBean> relationProducts;
        private CrowdItemEntity crowdItem;
        private CartListBean.DataBean.CartBean.FlashPromotionBean flashPromotion;
        private CartListBean.DataBean.CartBean.SoonPromotionBean soonPromotion;
        private CollagePromotionsBean collagePromotion;
        private List<ProductRelationBean.DataBean.ProductCombBean> productComb;
        private String ratio="1.0";

        public CountryBean getCountry() {
            return country;
        }

        public void setCountry(CountryBean country) {
            this.country = country;
        }

        public String getRatio() {
            return ratio;
        }

        public void setRatio(String ratio) {
            this.ratio = ratio;
        }

        public String getWeixin() {
            return weixin;
        }

        public void setWeixin(String weixin) {
            this.weixin = weixin;
        }

        public BrandBean getBrand() {
            return brand;
        }

        public void setBrand(BrandBean brand) {
            this.brand = brand;
        }

        public ProductBean getProduct() {
            return product;
        }

        public void setProduct(ProductBean product) {
            this.product = product;
        }

        public List<PromotionsBean> getPromotions() {
            return promotions;
        }

        public void setPromotions(List<PromotionsBean> promotions) {
            this.promotions = promotions;
        }

        public CollagePromotionsBean getCollagePromotion() {
            return collagePromotion;
        }

        public void setCollagePromotion(CollagePromotionsBean collagePromotion) {
            this.collagePromotion = collagePromotion;
        }

        public List<CouponsEntity> getCoupons() {
            return coupons;
        }

        public void setCoupons(List<CouponsEntity> coupons) {
            this.coupons = coupons;
        }

        public List<RecommendProductsBean> getRecommendProducts() {
            return recommendProducts;
        }

        public void setRecommendProducts(List<RecommendProductsBean> recommendProducts) {
            this.recommendProducts = recommendProducts;
        }

        public List<RelationProductsBean> getRelationProducts() {
            return relationProducts;
        }

        public void setRelationProducts(List<RelationProductsBean> relationProducts) {
            this.relationProducts = relationProducts;
        }

        public void setCrowdItem(CrowdItemEntity crowdItem) {
            this.crowdItem = crowdItem;
        }

        public CrowdItemEntity getCrowdItem() {
            return crowdItem;
        }

        public CartListBean.DataBean.CartBean.FlashPromotionBean getFlashPromotion() {
            return flashPromotion;
        }

        public void setFlashPromotion(CartListBean.DataBean.CartBean.FlashPromotionBean flashPromotion) {
            this.flashPromotion = flashPromotion;
        }

        public CartListBean.DataBean.CartBean.SoonPromotionBean getSoonPromotion() {
            return soonPromotion;
        }

        public void setSoonPromotion(CartListBean.DataBean.CartBean.SoonPromotionBean soonPromotion) {
            this.soonPromotion = soonPromotion;
        }
        public List<ProductRelationBean.DataBean.ProductCombBean> getProductComb() {
            return productComb;
        }

        public void setProductComb(List<ProductRelationBean.DataBean.ProductCombBean> productComb) {
            this.productComb = productComb;
        }
        public static class CountryBean {
            /**
             * code : 1
             * name : 中国
             * id : 1
             * sort : 7
             * pic : /2017/09/22/0556447593afaf00af2f345c2bd42b34614a21.jpg
             * pic2 : /2018/06/02/061955fb6cfa0dd8249726bf8ef9bee156b720.png
             */

            private String code;
            private String name;
            private int id;
            private int sort;
            private String pic;
            private String pic2;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
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

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getPic2() {
                return pic2;
            }

            public void setPic2(String pic2) {
                this.pic2 = pic2;
            }
        }

        public static class CollagePromotionsBean{
            private long beginDate;
            private String sharePic;
            private String shareContent;
            private String shareTitle;
            private long endDate;
            private int memberCount;
            private int currentCount;
            private String name;
            private int id;
            private int sort;
            private int status;
            private int promotionStatus;

            public long getBeginDate() {
                return beginDate;
            }

            public void setBeginDate(long beginDate) {
                this.beginDate = beginDate;
            }

            public String getSharePic() {
                return sharePic;
            }

            public void setSharePic(String sharePic) {
                this.sharePic = sharePic;
            }

            public String getShareContent() {
                return shareContent;
            }

            public void setShareContent(String shareContent) {
                this.shareContent = shareContent;
            }

            public String getShareTitle() {
                return shareTitle;
            }

            public void setShareTitle(String shareTitle) {
                this.shareTitle = shareTitle;
            }

            public long getEndDate() {
                return endDate;
            }

            public void setEndDate(long endDate) {
                this.endDate = endDate;
            }

            public int getMemberCount() {
                return memberCount;
            }

            public void setMemberCount(int memberCount) {
                this.memberCount = memberCount;
            }

            public int getCurrentCount() {
                return currentCount;
            }

            public void setCurrentCount(int currentCount) {
                this.currentCount = currentCount;
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

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public int getPromotionStatus() {
                return promotionStatus;
            }

            public void setPromotionStatus(int promotionStatus) {
                this.promotionStatus = promotionStatus;
            }
        }

        public static class CrowdItemEntity {
            private long id;
            private String name;
            private double currentPrice;
            private double originalCost;
            private String beginCrowd;
            private String endCrowd;
            private String sendRemark;

            public void setId(long id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setCurrentPrice(double currentPrice) {
                this.currentPrice = currentPrice;
            }

            public void setOriginalCost(double originalCost) {
                this.originalCost = originalCost;
            }

            public void setBeginCrowd(String beginCrowd) {
                this.beginCrowd = beginCrowd;
            }

            public void setEndCrowd(String endCrowd) {
                this.endCrowd = endCrowd;
            }

            public void setSendRemark(String sendRemark) {
                this.sendRemark = sendRemark;
            }

            public long getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public double getCurrentPrice() {
                return currentPrice;
            }

            public double getOriginalCost() {
                return originalCost;
            }

            public String getBeginCrowd() {
                return beginCrowd;
            }

            public String getEndCrowd() {
                return endCrowd;
            }

            public String getSendRemark() {
                return sendRemark;
            }
        }

        public static class BrandBean {
            /**
             * appIntro : null
             * likeCount : 5800
             * recommend : 1
             * introPic : /2017/06/16/080140bfe9f6e3ea8e5c663da0b5ed69f138ec.jpg
             * video : null
             * headPic : /2017/01/17/023548ae4145cac4c3b25eda47de9b2f5d1d4e.jpg
             * isSubscribe : 0
             * isCod : 0
             * hasAppIntro : 0
             * intro :
             * name : ALeaLea
             * id : 10952
             * brand : 程莉莉
             * slogan :
             * isAfter : 1
             */

            private Object appIntro;
            private int likeCount;
            private int recommend;
            private String introPic;
            private Object video;
            private String headPic;
            private int isSubscribe;
            private int isCod;
            private int hasAppIntro;
            private String intro;
            private String name;
            private int id;
            private String brand;
            private String slogan;
            private int isAfter;
            private String mark;
            private int salesCount;

            public int getSalesCount() {
                return salesCount;
            }

            public void setSalesCount(int salesCount) {
                this.salesCount = salesCount;
            }

            public Object getAppIntro() {
                return appIntro;
            }

            public void setAppIntro(Object appIntro) {
                this.appIntro = appIntro;
            }

            public int getLikeCount() {
                return likeCount;
            }

            public void setLikeCount(int likeCount) {
                this.likeCount = likeCount;
            }

            public int getRecommend() {
                return recommend;
            }

            public void setRecommend(int recommend) {
                this.recommend = recommend;
            }

            public String getIntroPic() {
                return introPic;
            }

            public void setIntroPic(String introPic) {
                this.introPic = introPic;
            }

            public Object getVideo() {
                return video;
            }

            public void setVideo(Object video) {
                this.video = video;
            }

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }

            public boolean getIsSubscribe() {
                if (isSubscribe==1){
                    return true;
                }else {
                    return false;
                }
            }

            public String getMark() {
                return mark;
            }

            public void setMark(String mark) {
                this.mark = mark;
            }

            public void setIsSubscribe(int isSubscribe) {
                this.isSubscribe = isSubscribe;
            }

            public int getIsCod() {
                return isCod;
            }

            public void setIsCod(int isCod) {
                this.isCod = isCod;
            }

            public int getHasAppIntro() {
                return hasAppIntro;
            }

            public void setHasAppIntro(int hasAppIntro) {
                this.hasAppIntro = hasAppIntro;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
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

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public String getSlogan() {
                return slogan;
            }

            public void setSlogan(String slogan) {
                this.slogan = slogan;
            }

            public int getIsAfter() {
                return isAfter;
            }

            public void setIsAfter(int isAfter) {
                this.isAfter = isAfter;
            }
        }

        public static class CouponsEntity implements Serializable, Identifiable {
            private long id;
            private String name;
            private String type;
            private String code;
            private long amount;
            private long needAmount;
            private String remark;
            private String enableDate;
            private String expireDate;
            private String status;
            private String url;
            private int isExpired;
            private String couponName;
            private String activeDay;
            public boolean isExpand;
            public boolean isClaim;

            private long myCouponId;//领取成功对应我的优惠券的id

            public long getMyCouponId() {
                return myCouponId;
            }

            public void setMyCouponId(long myCouponId) {
                this.myCouponId = myCouponId;
            }

            public boolean isExpand() {
                return isExpand;
            }

            public void setExpand(boolean expand) {
                isExpand = expand;
            }

            public void setId(long id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public void setAmount(long amount) {
                this.amount = amount;
            }

            public void setNeedAmount(long needAmount) {
                this.needAmount = needAmount;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public void setEnableDate(String enabledate) {
                this.enableDate = enabledate;
            }

            public void setExpireDate(String expiredate) {
                this.expireDate = expiredate;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setIsExpired(int isExpired) {
                this.isExpired = isExpired;
            }

            public long getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getType() {
                return type;
            }

            public String getCode() {
                return code;
            }

            public long getAmount() {
                return amount;
            }

            public long getNeedAmount() {
                return needAmount;
            }

            public String getRemark() {
                return remark;
            }

            public String getEnableDate() {
                return enableDate;
            }

            public String getExpireDate() {
                return expireDate;
            }

            public String getStatus() {
                return status;
            }

            public String getUrl() {
                return url;
            }

            public int getIsExpired() {
                return isExpired;
            }

            public String getCouponName() {
                return couponName;
            }

            public void setCouponName(String couponName) {
                this.couponName = couponName;
            }

            public String getActiveDay() {
                return activeDay;
            }

            public void setActiveDay(String activeDay) {
                this.activeDay = activeDay;
            }

            public boolean isClaim() {
                return isClaim;
            }

            public void setClaim(boolean claim) {
                isClaim = claim;
            }
        }

        public static class PromotionsBean implements Serializable {
            private long id;
            private String promotionName;
            private String promotionUrl;
            private Date startTime;
            private Date endTime;
            private int promotionScope;
            private String promotionType;
            private String promotionTypeName;
            private String promotionSulo;

            public String getPromotionName() {
                return promotionName;
            }

            public void setPromotionName(String promotionName) {
                this.promotionName = promotionName;
            }

            public String getPromotionUrl() {
                return promotionUrl;
            }

            public void setPromotionUrl(String promotionUrl) {
                this.promotionUrl = promotionUrl;
            }

            public Date getStartTime() {
                return startTime;
            }

            public void setStartTime(Date startTime) {
                this.startTime = startTime;
            }

            public Date getEndTime() {
                return endTime;
            }

            public void setEndTime(Date endTime) {
                this.endTime = endTime;
            }

            public long getId() {
                if (id == 0) {
                    int index = promotionUrl.lastIndexOf("/");
                    String idStr = promotionUrl.substring(index + 1, promotionUrl.length());
                    if (!Util.isEmpty(idStr)) {
                        this.id = Long.valueOf(idStr);
                    }
                }
                return id;
            }

            public int getPromotionScope() {
                return promotionScope;
            }

            public void setPromotionScope(int promotionScope) {
                this.promotionScope = promotionScope;
            }

            public void setId(long id) {
                this.id = id;
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

            public String getPromotionSulo() {
                return promotionSulo;
            }

            public void setPromotionSulo(String promotionSulo) {
                this.promotionSulo = promotionSulo;
            }
        }

        public static class RelationProductsBean implements Identifiable {
            private long id;
            private String img;
            private double price;
            private double minPrice;
            private double originalPrice;
            private String name;
            private boolean isTopical;
            private boolean isCrowd;
            private int store;
            private int mark;
            private List<ProductBean.ColorsBean> colors;
            private List<ProductBean.SizesBean> sizes;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
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

            public double getOriginalPrice() {
                return originalPrice;
            }

            public void setOriginalPrice(double originalPrice) {
                this.originalPrice = originalPrice;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public boolean isIsTopical() {
                return isTopical;
            }

            public void setIsTopical(boolean isTopical) {
                this.isTopical = isTopical;
            }

            public boolean isIsCrowd() {
                return isCrowd;
            }

            public void setIsCrowd(boolean isCrowd) {
                this.isCrowd = isCrowd;
            }

            public int getStore() {
                return store;
            }

            public void setStore(int store) {
                this.store = store;
            }

            public int getMark() {
                return mark;
            }

            public void setMark(int mark) {
                this.mark = mark;
            }

            public List<ProductBean.ColorsBean> getColors() {
                return colors;
            }

            public void setColors(List<ProductBean.ColorsBean> colors) {
                this.colors = colors;
            }

            public List<ProductBean.SizesBean> getSizes() {
                return sizes;
            }

            public void setSizes(List<ProductBean.SizesBean> sizes) {
                this.sizes = sizes;
            }
        }

        public static class ProductBean {
            private String img;
            private double originalPrice;
            private double sellPrice;
            private String taxPrice;
            private double collagePrice;
            private double collageNum;
            private int collageId;
            private int collageStore;
            private String collageName;
            private boolean hasGroup;
            private int promotionStatus;
            private String remark;
            private int isCart;
            private int isCod;
            private double price;
            private BigDecimal soonPrice;
            private boolean isTopical;
            private double originalCost;
            private long id;
            private String sn;
            private int after;
            private String designerPic;
            private long designerId;
            private int comments;
            private int store;
            private boolean isCrowd;
            private double minPrice;
            private String name;
            private String collectioned;
            private int isAfter;
            private int isInstallment;
            private int isSubscribe;
            private int mark;
            private int status;
            private int productStatus;
            private boolean isSpot;
            private String productSellType;
            private String recommendation;
            private String video;
            private String consults;
            private int recomScore;
            private long categoryId;
            private String afterMemo;
            private int estimateDay; //定制天数
            private Date estimateDate; //预售时间戳
            private String  subTitle;

            private boolean hasSelectColor;
            private boolean hasSelectSize;
            private double flashPrice;
            private double secondRatio;
            private double grossRatio;
            private double firstRatio;
            private double ratio=1;
            private String productTradeType;//贸易类型 COMMON(一般) CROSS(跨境)
            private String productSource;//商品来源 D2CMALL(D2C) KAOLA(考拉)
            private int importType;//贸易方式 0:直邮，1、保税，2、海淘 ，3、国内贸易 ，4、个人清关
            private int isTaxation;//税费 1:免税 0:要税
            private int isShipping;//邮费 1:包邮 0:要邮
            private int picStyle;//头图样式 0:长方形，1、正方形

            private int promotionId;
            private String adPic;
            private String adUrl;
            private boolean isFlash;

            public boolean isFlash() {
                return isFlash;
            }

            public void setFlash(boolean flash) {
                isFlash = flash;
            }

            public String getAdPic() {
                return adPic;
            }

            public void setAdPic(String adPic) {
                this.adPic = adPic;
            }

            public String getAdUrl() {
                return adUrl;
            }

            public void setAdUrl(String adUrl) {
                this.adUrl = adUrl;
            }

            public double getTaxPrice() {
                if (!Util.isEmpty(taxPrice)){
                    String[] p=taxPrice.split(",");
                    if (p.length>0){
                        return Double.valueOf(p[0]);
                    }else {
                        return 0;
                    }
                }else {
                    return 0;
                }
            }

            public void setTaxPrice(String taxPrice) {
                this.taxPrice = taxPrice;
            }

            public int getPromotionId() {
                return promotionId;
            }

            public void setPromotionId(int promotionId) {
                this.promotionId = promotionId;
            }
            public String getCollageName() {
                return collageName;
            }

            public void setCollageName(String collageName) {
                this.collageName = collageName;
            }

            public boolean isHasGroup() {
                return hasGroup;
            }

            public void setHasGroup(boolean hasGroup) {
                this.hasGroup = hasGroup;
            }

            public int getCollageStore() {
                return collageStore;
            }

            public void setCollageStore(int collageStore) {
                this.collageStore = collageStore;
            }

            public int getPromotionStatus() {
                return promotionStatus;
            }

            public void setPromotionStatus(int promotionStatus) {
                this.promotionStatus = promotionStatus;
            }

            public String getSubTitle() {
                return subTitle;
            }

            public void setSubTitle(String subTitle) {
                this.subTitle = subTitle;
            }

            public int getCollageId() {
                return collageId;
            }

            public void setCollageId(int collageId) {
                this.collageId = collageId;
            }

            public double getCollageNum() {
                return collageNum;
            }

            public void setCollageNum(double collageNum) {
                this.collageNum = collageNum;
            }

            public double getCollagePrice() {
                return collagePrice;
            }

            public void setCollagePrice(double collagePrice) {
                this.collagePrice = collagePrice;
            }

            public int getPicStyle() {
                return picStyle;
            }

            public void setPicStyle(int picStyle) {
                this.picStyle = picStyle;
            }

            public int getIsTaxation() {
                return isTaxation;
            }

            public void setIsTaxation(int isTaxation) {
                this.isTaxation = isTaxation;
            }

            public int getIsShipping() {
                return isShipping;
            }

            public void setIsShipping(int isShipping) {
                this.isShipping = isShipping;
            }

            public String getProductTradeType() {
                return productTradeType;
            }

            public void setProductTradeType(String productTradeType) {
                this.productTradeType = productTradeType;
            }

            public String getProductSource() {
                return productSource;
            }

            public void setProductSource(String productSource) {
                this.productSource = productSource;
            }

            public int getImportType() {
                return importType;
            }

            public void setImportType(int importType) {
                this.importType = importType;
            }

            public double getSecondRatio() {
                return secondRatio;
            }

            public void setSecondRatio(double secondRatio) {
                this.secondRatio = secondRatio;
            }

            public double getGrossRatio() {
                return grossRatio;
            }

            public double getRatio() {
                return ratio;
            }

            public void setRatio(double ratio) {
                this.ratio = ratio;
            }

            public void setGrossRatio(double grossRatio) {
                this.grossRatio = grossRatio;
            }

            public double getFirstRatio() {
                return firstRatio;
            }

            public void setFirstRatio(double firstRatio) {
                this.firstRatio = firstRatio;
            }

            public double getFlashPrice() {
                return flashPrice;
            }

            public void setFlashPrice(double flashPrice) {
                this.flashPrice = flashPrice;
            }

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            private int num=-1;
            public boolean isHasSelectColor() {
                return hasSelectColor;
            }

            public void setHasSelectColor(boolean hasSelectColor) {
                this.hasSelectColor = hasSelectColor;
            }

            public boolean isHasSelectSize() {
                return hasSelectSize;
            }

            public void setHasSelectSize(boolean hasSelectSize) {
                this.hasSelectSize = hasSelectSize;
            }
            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            private List<ColorsBean> colors;

            private List<SizesBean> sizes;
            private List<String> imgs;

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

            public int getEstimateDay() {
                return estimateDay;
            }

            public void setEstimateDay(int estimateDay) {
                this.estimateDay = estimateDay;
            }

            public Date getEstimateDate() {
                return estimateDate;
            }

            public void setEstimateDate(Date estimateDate) {
                this.estimateDate = estimateDate;
            }

            public double getSellPrice() {
                return sellPrice;
            }

            public void setSellPrice(double sellPrice) {
                this.sellPrice = sellPrice;
            }

            public String getProductSellType() {
                return productSellType;
            }

            public void setProductSellType(String productSellType) {
                this.productSellType = productSellType;
            }

            public BigDecimal getSoonPrice() {
                return soonPrice;
            }

            public void setSoonPrice(BigDecimal soonPrice) {
                this.soonPrice = soonPrice;
            }

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getIsCart() {
                return isCart;
            }

            public void setIsCart(int isCart) {
                this.isCart = isCart;
            }

            public boolean isCod() {
                return isCod > 0;
            }

            public void setIsCod(int isCod) {
                this.isCod = isCod;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public boolean isIsTopical() {
                return isTopical;
            }

            public void setIsTopical(boolean isTopical) {
                this.isTopical = isTopical;
            }

            public double getOriginalCost() {
                return originalCost;
            }

            public void setOriginalCost(double originalCost) {
                this.originalCost = originalCost;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getSn() {
                return sn;
            }

            public void setSn(String sn) {
                this.sn = sn;
            }

            public int getAfter() {
                return after;
            }

            public void setAfter(int after) {
                this.after = after;
            }

            public String getDesignerPic() {
                return designerPic;
            }

            public void setDesignerPic(String designerPic) {
                this.designerPic = designerPic;
            }

            public long getDesignerId() {
                return designerId;
            }

            public void setDesignerId(long designerId) {
                this.designerId = designerId;
            }

            public int getComments() {
                return comments;
            }

            public void setComments(int comments) {
                this.comments = comments;
            }

            public int getStore() {
                return store;
            }

            public void setStore(int store) {
                this.store = store;
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

            public String getCollectioned() {
                return collectioned;
            }

            public void setCollectioned(String collectioned) {
                this.collectioned = collectioned;
            }

            public boolean isAfter() {
                return isAfter > 0;
            }

            public void setIsAfter(int isAfter) {
                this.isAfter = isAfter;
            }

            public int getMark() {
                return mark;
            }

            public void setMark(int mark) {
                this.mark = mark;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
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

            public List<String> getImgs() {
                return imgs;
            }

            public void setImgs(List<String> imgs) {
                this.imgs = imgs;
            }

            public boolean getIsSubscribe() {
                return isSubscribe > 0;
            }

            public void setIsSubscribe(int isSubscribe) {
                this.isSubscribe = isSubscribe;
            }

            public boolean isSpot() {
                return isSpot;
            }

            public void setSpot(boolean spot) {
                isSpot = spot;
            }

            public String getRecommendation() {
                return recommendation;
            }

            public int getIsInstallment() {
                return isInstallment;
            }

            public void setIsInstallment(int isInstallment) {
                this.isInstallment = isInstallment;
            }

            public void setRecommendation(String recommendation) {
                this.recommendation = recommendation;
            }

            public int getProductStatus() {
                return productStatus;
            }

            public void setProductStatus(int productStatus) {
                this.productStatus = productStatus;
            }

            public String getAfterMemo() {
                return afterMemo;
            }

            public void setAfterMemo(String afterMemo) {
                this.afterMemo = afterMemo;
            }

            public static class ColorsBean implements Serializable {
                private String img;
                private String code;
                private long groupId;
                private String name;
                private long id;
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

                public long getGroupId() {
                    return groupId;
                }

                public void setGroupId(long groupId) {
                    this.groupId = groupId;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }

            public static class SizesBean implements Serializable {
                private String img;
                private String code;
                private long groupId;
                private String name;
                private long id;
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

                public long getGroupId() {
                    return groupId;
                }

                public void setGroupId(long groupId) {
                    this.groupId = groupId;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public long getId() {
                    return id;
                }

                public void setId(long id) {
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

        public static class DesignerBean {
            private String intro;
            private String name;
            private int likeCount;
            private int recommend;
            private long id;
            private String introPic;
            private String headPic;
            private String brand;
            private String slogan;
            private int isSubscribe;

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getLikeCount() {
                return likeCount;
            }

            public void setLikeCount(int likeCount) {
                this.likeCount = likeCount;
            }

            public int getRecommend() {
                return recommend;
            }

            public void setRecommend(int recommend) {
                this.recommend = recommend;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getIntroPic() {
                return introPic;
            }

            public void setIntroPic(String introPic) {
                this.introPic = introPic;
            }

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public String getSlogan() {
                return slogan;
            }

            public void setSlogan(String slogan) {
                this.slogan = slogan;
            }

            public boolean getIsSubscribe() {
                return isSubscribe > 0;
            }

            public void setIsSubscribe(int isSubscribe) {
                this.isSubscribe = isSubscribe;
            }
        }

        public static class RecommendProductsBean {
            private long designerId;
            private String img;
            private int comments;
            private double originalPrice;
            private double promotionPrice;

            public int getPromotionId() {
                return promotionId;
            }

            public void setPromotionId(int promotionId) {
                this.promotionId = promotionId;
            }

            public double getSalePrice() {
                return salePrice;
            }

            public void setSalePrice(double salePrice) {
                this.salePrice = salePrice;
            }

            private double salePrice;
            private int promotionId;
            private int store;
            private boolean isSpot;
            private String designer;
            private int isCart;
            private int consults;
            private double price;
            private double minPrice;
            private String name;
            private boolean isTopical;
            private String collectioned;
            private long id;
            private int mark;
            private String categoryName;
            private String brand;
            private boolean isLongClick;
            private String orderPromotionType;
            private long orderPromotionId;
            private String orderPromotionTypeName;
            private  String promotionTypeName;

            private String productTradeType;//贸易类型 COMMON(一般) CROSS(跨境)

            public String getProductTradeType() {
                return productTradeType;
            }

            public void setProductTradeType(String productTradeType) {
                this.productTradeType = productTradeType;
            }
            public Integer getFlashPromotionId() {
                return flashPromotionId;
            }

            public void setFlashPromotionId(Integer flashPromotionId) {
                this.flashPromotionId = flashPromotionId;
            }

            private Integer flashPromotionId;
            public String getPromotionTypeName() {
                return promotionTypeName;
            }

            public void setPromotionTypeName(String promotionTypeName) {
                this.promotionTypeName = promotionTypeName;
            }

            public String getOrderPromotionType() {
                return orderPromotionType;
            }

            public void setOrderPromotionType(String orderPromotionType) {
                this.orderPromotionType = orderPromotionType;
            }

            public long getOrderPromotionId() {
                return orderPromotionId;
            }

            public void setOrderPromotionId(long orderPromotionId) {
                this.orderPromotionId = orderPromotionId;
            }

            public String getOrderPromotionTypeName() {
                return orderPromotionTypeName;
            }

            public void setOrderPromotionTypeName(String orderPromotionTypeName) {
                this.orderPromotionTypeName = orderPromotionTypeName;
            }

            public boolean isLongClick() {
                return isLongClick;
            }

            public void setLongClick(boolean longClick) {
                isLongClick = longClick;
            }

            public int getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(int categoryId) {
                this.categoryId = categoryId;
            }

            private int categoryId;
            private List<ProductBean.ColorsBean> colors;

            private List<ProductBean.SizesBean> sizes;

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

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
            }

            public double getPromotionPrice() {
                return promotionPrice;
            }

            public void setPromotionPrice(int promotionPrice) {
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

            public boolean isIsTopical() {
                return isTopical;
            }

            public void setIsTopical(boolean isTopical) {
                this.isTopical = isTopical;
            }

            public String getCollectioned() {
                return collectioned;
            }

            public void setCollectioned(String collectioned) {
                this.collectioned = collectioned;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public int getMark() {
                return mark;
            }

            public void setMark(int mark) {
                this.mark = mark;
            }

            public List<ProductBean.ColorsBean> getColors() {
                return colors;
            }

            public void setColors(List<ProductBean.ColorsBean> colors) {
                this.colors = colors;
            }

            public List<ProductBean.SizesBean> getSizes() {
                return sizes;
            }

            public void setSizes(List<ProductBean.SizesBean> sizes) {
                this.sizes = sizes;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public long getDesignerId() {
                return designerId;
            }

            public void setDesignerId(long designerId) {
                this.designerId = designerId;
            }

            public void setPromotionPrice(double promotionPrice) {
                this.promotionPrice = promotionPrice;
            }

            public boolean isSpot() {
                return isSpot;
            }

            public void setSpot(boolean spot) {
                isSpot = spot;
            }

            public boolean isTopical() {
                return isTopical;
            }

            public void setTopical(boolean topical) {
                isTopical = topical;
            }
        }
    }
}
