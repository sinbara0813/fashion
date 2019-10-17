package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by rookie on 2017/8/3.
 */

public class BrandDetailBean extends BaseBean {

    /**
     * data : {"headPics":["/app/a/17/08/31/54268c1ebe0683233a8e1045529ee9a3","http://wx.qlogo.cn/mmopen/oLMOrkqyewGCTssD3qZO7NNN6MPiaZv31aRHbib5stl1h81rXsKQw2V9C4ic4h8v3CKPyNML3lRia7B04iaQrRnv9dDMjdZGw0Jg2/0","http://q.qlogo.cn/qqapp/101265483/4C0E9B9FDAC7D34863AC594B51CED139/100","","","","/app/a/17/01/03/d2a1b2833649fe40cd644bfb8fb51642","http://wx.qlogo.cn/mmopen/PiajxSqBRaEJX1nfG6HIM06ibGBPTYlKUy1PSlB43G8SZJsDEiahTTm4HMX2grGwK1icQU3sia7CNd88WoeJxIfMGeA/0","","http://wx.qlogo.cn/mmopen/I2fxKtgmKrTGsCYPLibibJxASLiayMAI6bVjkD7q21hSIrelV98F6YlzUuIgub54Zibib2w9FQuib7ia4sxpmblY8JscukZd7Okz7Dm/0"],"brand":{"id":10687,"code":null,"name":"1011","designers":"朱超凡","country":"1","style":"65","designArea":"7","pageGroup":"0-9","operation":null,"market":null,"seo":null,"slogan":null,"introPic":"/2017/03/17/024744eff0bf5cc92ee083dd51971e560b1e79.jpg","bigPic":null,"headPic":"/2016/12/07/02413182aa331146130a74f8b119acdf9ae0d7.png","signPic":"/2017/03/17/024854e180dee53a8a7320cae53457dfc55e3c.jpg","backPic":null,"intro":"<p>法国1011高级女装品牌，是由来自法国设计师团队一手创立。曾多次参加巴黎高级女装时装周，致力于推广最先进时尚潮流的同事，改变顾客对传统服装的价值观。主要针对于20-35岁女性，以欧美简约建筑风为基础。在提高功能性的前提下，大幅度让顾客满足对服装的一切要求。<\/p>","description":null,"moreIntro":null,"drafts":null,"designerStore":null,"fans":551,"vfans":0,"tfans":null,"subscribe":null,"recommend":0,"mark":1,"tags":null,"sortDate":1468382440000,"domain":"-1354882","attentioned":0,"appIntro":"<h1>cessdasdasdadasd<\/h1><h1><\/h1>","video":null,"signPics":["/2017/03/17/024854e180dee53a8a7320cae53457dfc55e3c.jpg"]}}
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
         * headPics : ["/app/a/17/08/31/54268c1ebe0683233a8e1045529ee9a3","http://wx.qlogo.cn/mmopen/oLMOrkqyewGCTssD3qZO7NNN6MPiaZv31aRHbib5stl1h81rXsKQw2V9C4ic4h8v3CKPyNML3lRia7B04iaQrRnv9dDMjdZGw0Jg2/0","http://q.qlogo.cn/qqapp/101265483/4C0E9B9FDAC7D34863AC594B51CED139/100","","","","/app/a/17/01/03/d2a1b2833649fe40cd644bfb8fb51642","http://wx.qlogo.cn/mmopen/PiajxSqBRaEJX1nfG6HIM06ibGBPTYlKUy1PSlB43G8SZJsDEiahTTm4HMX2grGwK1icQU3sia7CNd88WoeJxIfMGeA/0","","http://wx.qlogo.cn/mmopen/I2fxKtgmKrTGsCYPLibibJxASLiayMAI6bVjkD7q21hSIrelV98F6YlzUuIgub54Zibib2w9FQuib7ia4sxpmblY8JscukZd7Okz7Dm/0"]
         * brand : {"id":10687,"code":null,"name":"1011","designers":"朱超凡","country":"1","style":"65","designArea":"7","pageGroup":"0-9","operation":null,"market":null,"seo":null,"slogan":null,"introPic":"/2017/03/17/024744eff0bf5cc92ee083dd51971e560b1e79.jpg","bigPic":null,"headPic":"/2016/12/07/02413182aa331146130a74f8b119acdf9ae0d7.png","signPic":"/2017/03/17/024854e180dee53a8a7320cae53457dfc55e3c.jpg","backPic":null,"intro":"<p>法国1011高级女装品牌，是由来自法国设计师团队一手创立。曾多次参加巴黎高级女装时装周，致力于推广最先进时尚潮流的同事，改变顾客对传统服装的价值观。主要针对于20-35岁女性，以欧美简约建筑风为基础。在提高功能性的前提下，大幅度让顾客满足对服装的一切要求。<\/p>","description":null,"moreIntro":null,"drafts":null,"designerStore":null,"fans":551,"vfans":0,"tfans":null,"subscribe":null,"recommend":0,"mark":1,"tags":null,"sortDate":1468382440000,"domain":"-1354882","attentioned":0,"appIntro":"<h1>cessdasdasdadasd<\/h1><h1><\/h1>","video":null,"signPics":["/2017/03/17/024854e180dee53a8a7320cae53457dfc55e3c.jpg"]}
         */

        private BrandBean brand;
        private List<String> headPics;

        public BrandBean getBrand() {
            return brand;
        }

        public void setBrand(BrandBean brand) {
            this.brand = brand;
        }

        public List<String> getHeadPics() {
            return headPics;
        }

        public void setHeadPics(List<String> headPics) {
            this.headPics = headPics;
        }

        public static class BrandBean {
            /**
             * id : 10687
             * code : null
             * name : 1011
             * designers : 朱超凡
             * country : 1
             * style : 65
             * designArea : 7
             * pageGroup : 0-9
             * operation : null
             * market : null
             * seo : null
             * slogan : null
             * introPic : /2017/03/17/024744eff0bf5cc92ee083dd51971e560b1e79.jpg
             * bigPic : null
             * headPic : /2016/12/07/02413182aa331146130a74f8b119acdf9ae0d7.png
             * signPic : /2017/03/17/024854e180dee53a8a7320cae53457dfc55e3c.jpg
             * backPic : null
             * intro : <p>法国1011高级女装品牌，是由来自法国设计师团队一手创立。曾多次参加巴黎高级女装时装周，致力于推广最先进时尚潮流的同事，改变顾客对传统服装的价值观。主要针对于20-35岁女性，以欧美简约建筑风为基础。在提高功能性的前提下，大幅度让顾客满足对服装的一切要求。</p>
             * description : null
             * moreIntro : null
             * drafts : null
             * designerStore : null
             * fans : 551
             * vfans : 0
             * tfans : null
             * subscribe : null
             * recommend : 0
             * mark : 1
             * tags : null
             * sortDate : 1468382440000
             * domain : -1354882
             * attentioned : 0
             * appIntro : <h1>cessdasdasdadasd</h1><h1></h1>
             * video : null
             * signPics : ["/2017/03/17/024854e180dee53a8a7320cae53457dfc55e3c.jpg"]
             */

            private int id;
            private String code;
            private String name;
            private String designers;
            private String country;
            private String style;
            private String designArea;
            private String pageGroup;
            private String operation;
            private String market;
            private String seo;
            private String slogan;
            private String introPic;
            private String bigPic;
            private String headPic;
            private String signPic;
            private String backPic;
            private String intro;
            private String description;
            private String moreIntro;
            private String drafts;
            private String designerStore;
            private int fans;
            private int vfans;
            private String tfans;
            private String subscribe;
            private int recommend;
            private int mark;
            private String tags;
            private long sortDate;
            private String domain;
            private int attentioned;
            private String appIntro;
            private String video;

            private String videoPic;

            public String getVideoPic() {
                return videoPic;
            }

            public void setVideoPic(String videoPic) {
                this.videoPic = videoPic;
            }
            private List<String> signPics;
            private String backgroundUrl;

            public String getBackgroundUrl() {
                return backgroundUrl;
            }

            public void setBackgroundUrl(String backgroundUrl) {
                this.backgroundUrl = backgroundUrl;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

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

            public String getDesigners() {
                return designers;
            }

            public void setDesigners(String designers) {
                this.designers = designers;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getStyle() {
                return style;
            }

            public void setStyle(String style) {
                this.style = style;
            }

            public String getDesignArea() {
                return designArea;
            }

            public void setDesignArea(String designArea) {
                this.designArea = designArea;
            }

            public String getPageGroup() {
                return pageGroup;
            }

            public void setPageGroup(String pageGroup) {
                this.pageGroup = pageGroup;
            }

            public String getOperation() {
                return operation;
            }

            public void setOperation(String operation) {
                this.operation = operation;
            }

            public String getMarket() {
                return market;
            }

            public void setMarket(String market) {
                this.market = market;
            }

            public String getSeo() {
                return seo;
            }

            public void setSeo(String seo) {
                this.seo = seo;
            }

            public String getSlogan() {
                return slogan;
            }

            public void setSlogan(String slogan) {
                this.slogan = slogan;
            }

            public String getIntroPic() {
                return introPic;
            }

            public void setIntroPic(String introPic) {
                this.introPic = introPic;
            }

            public String getBigPic() {
                return bigPic;
            }

            public void setBigPic(String bigPic) {
                this.bigPic = bigPic;
            }

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }

            public String getSignPic() {
                return signPic;
            }

            public void setSignPic(String signPic) {
                this.signPic = signPic;
            }

            public String getBackPic() {
                return backPic;
            }

            public void setBackPic(String backPic) {
                this.backPic = backPic;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getMoreIntro() {
                return moreIntro;
            }

            public void setMoreIntro(String moreIntro) {
                this.moreIntro = moreIntro;
            }

            public String getDrafts() {
                return drafts;
            }

            public void setDrafts(String drafts) {
                this.drafts = drafts;
            }

            public String getDesignerStore() {
                return designerStore;
            }

            public void setDesignerStore(String designerStore) {
                this.designerStore = designerStore;
            }

            public int getFans() {
                return fans;
            }

            public void setFans(int fans) {
                this.fans = fans;
            }

            public int getVfans() {
                return vfans;
            }

            public void setVfans(int vfans) {
                this.vfans = vfans;
            }

            public String getTfans() {
                return tfans;
            }

            public void setTfans(String tfans) {
                this.tfans = tfans;
            }

            public String getSubscribe() {
                return subscribe;
            }

            public void setSubscribe(String subscribe) {
                this.subscribe = subscribe;
            }

            public int getRecommend() {
                return recommend;
            }

            public void setRecommend(int recommend) {
                this.recommend = recommend;
            }

            public int getMark() {
                return mark;
            }

            public void setMark(int mark) {
                this.mark = mark;
            }

            public String getTags() {
                return tags;
            }

            public void setTags(String tags) {
                this.tags = tags;
            }

            public long getSortDate() {
                return sortDate;
            }

            public void setSortDate(long sortDate) {
                this.sortDate = sortDate;
            }

            public String getDomain() {
                return domain;
            }

            public void setDomain(String domain) {
                this.domain = domain;
            }

            public int getAttentioned() {
                return attentioned;
            }

            public void setAttentioned(int attentioned) {
                this.attentioned = attentioned;
            }

            public String getAppIntro() {
                return appIntro;
            }

            public void setAppIntro(String appIntro) {
                this.appIntro = appIntro;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public List<String> getSignPics() {
                return signPics;
            }

            public void setSignPics(List<String> signPics) {
                this.signPics = signPics;
            }
        }
    }
}
