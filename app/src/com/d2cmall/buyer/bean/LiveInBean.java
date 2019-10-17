package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 进入房间bean
 * Author: hrb
 * Date: 2017/02/21 11:52
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class LiveInBean extends BaseBean {
    /**
     * data : {"realTime":{"watchingCount":3,"headPics":[{"headPic":"/app/a/17/01/16/798e50894b205dd8f10be3d517a14330","memberId":844769}],"vcount":1},"coupon":{"id":81,"createDate":1493258076000,"creator":"zhanglin","modifyDate":1493274267000,"lastModifyMan":"baicai","code":"1493258076228","cipher":"20170427","name":"优惠券组 20170427","randomNum":50,"rates":"1:1:1","fixDefIds":"632","randomDefIds":null,"quantity":3000,"claimed":1,"claimLimit":200,"enabledate":null,"expiredate":null,"status":1,"pcCode":null,"wapCode":null,"claimStart":1493259900000,"claimEnd":1493519280000,"redirectUrl":null,"totalNum":51,"over":false},"barrageAmount":1,"follow":0,"type":1,"live":{"streamId":"LVSID17041493274196229","playerCount":0,"redRatio":1,"title":"Yhggg","previewContent":"","cover":"/app/f/17/04/27/0ffa853549a4cafd6881b3d1a0a83b4d","picUrl":"http://pic.wsdemo.zego.im/livestream/zegotest-2873169708-LVSID17041493274196229.jpg","previewDate":"","nickname":"匿名_795049","vcount":0,"id":3622,"beginTime":"","channelId":"LVCID17041493274196229","memberId":795049,"designerId":10061,"brandName":"Awaylee","sex":"男","replayUrl":"","headPic":"/member_head/49/795049/c5f02e1d5f2843ce8d77663f038d92cd.png","designerName":"李薇","watchRatio":1,"watched":0,"onlineNums":0,"endTime":"","status":1}}
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
         * realTime : {"watchingCount":3,"headPics":[{"headPic":"/app/a/17/01/16/798e50894b205dd8f10be3d517a14330","memberId":844769}],"vcount":1}
         * coupon : {"id":81,"createDate":1493258076000,"creator":"zhanglin","modifyDate":1493274267000,"lastModifyMan":"baicai","code":"1493258076228","cipher":"20170427","name":"优惠券组 20170427","randomNum":50,"rates":"1:1:1","fixDefIds":"632","randomDefIds":null,"quantity":3000,"claimed":1,"claimLimit":200,"enabledate":null,"expiredate":null,"status":1,"pcCode":null,"wapCode":null,"claimStart":1493259900000,"claimEnd":1493519280000,"redirectUrl":null,"totalNum":51,"over":false}
         * barrageAmount : 1
         * follow : 0
         * type : 1
         * live : {"streamId":"LVSID17041493274196229","playerCount":0,"redRatio":1,"title":"Yhggg","previewContent":"","cover":"/app/f/17/04/27/0ffa853549a4cafd6881b3d1a0a83b4d","picUrl":"http://pic.wsdemo.zego.im/livestream/zegotest-2873169708-LVSID17041493274196229.jpg","previewDate":"","nickname":"匿名_795049","vcount":0,"id":3622,"beginTime":"","channelId":"LVCID17041493274196229","memberId":795049,"designerId":10061,"brandName":"Awaylee","sex":"男","replayUrl":"","headPic":"/member_head/49/795049/c5f02e1d5f2843ce8d77663f038d92cd.png","designerName":"李薇","watchRatio":1,"watched":0,"onlineNums":0,"endTime":"","status":1}
         */

        private RealTimeBean realTime;
        private CouponBean coupon;
        private int barrageAmount;
        private int follow;
        private int type;
        private LiveBean live;

        public RealTimeBean getRealTime() {
            return realTime;
        }

        public void setRealTime(RealTimeBean realTime) {
            this.realTime = realTime;
        }

        public CouponBean getCoupon() {
            return coupon;
        }

        public void setCoupon(CouponBean coupon) {
            this.coupon = coupon;
        }

        public int getBarrageAmount() {
            return barrageAmount;
        }

        public void setBarrageAmount(int barrageAmount) {
            this.barrageAmount = barrageAmount;
        }

        public int getFollow() {
            return follow;
        }

        public void setFollow(int follow) {
            this.follow = follow;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public LiveBean getLive() {
            return live;
        }

        public void setLive(LiveBean live) {
            this.live = live;
        }

        public static class RealTimeBean {
            /**
             * watchingCount : 3
             * headPics : [{"headPic":"/app/a/17/01/16/798e50894b205dd8f10be3d517a14330","memberId":844769}]
             * vcount : 1
             */

            private int watchingCount;
            private int vcount;
            private List<HeadPicsBean> headPics;

            public int getWatchingCount() {
                return watchingCount;
            }

            public void setWatchingCount(int watchingCount) {
                this.watchingCount = watchingCount;
            }

            public int getVcount() {
                return vcount;
            }

            public void setVcount(int vcount) {
                this.vcount = vcount;
            }

            public List<HeadPicsBean> getHeadPics() {
                return headPics;
            }

            public void setHeadPics(List<HeadPicsBean> headPics) {
                this.headPics = headPics;
            }

            public static class HeadPicsBean {
                /**
                 * headPic : /app/a/17/01/16/798e50894b205dd8f10be3d517a14330
                 * memberId : 844769
                 */

                private String headPic;
                private long memberId;

                public String getHeadPic() {
                    return headPic;
                }

                public void setHeadPic(String headPic) {
                    this.headPic = headPic;
                }

                public long getMemberId() {
                    return memberId;
                }

                public void setMemberId(long memberId) {
                    this.memberId = memberId;
                }
            }
        }

        public static class CouponBean {
            /**
             * id : 81
             * createDate : 1493258076000
             * creator : zhanglin
             * modifyDate : 1493274267000
             * lastModifyMan : baicai
             * code : 1493258076228
             * cipher : 20170427
             * name : 优惠券组 20170427
             * randomNum : 50
             * rates : 1:1:1
             * fixDefIds : 632
             * randomDefIds : null
             * quantity : 3000
             * claimed : 1
             * claimLimit : 200
             * enabledate : null
             * expiredate : null
             * status : 1
             * pcCode : null
             * wapCode : null
             * claimStart : 1493259900000
             * claimEnd : 1493519280000
             * redirectUrl : null
             * totalNum : 51
             * over : false
             */

            private int id;
            private long createDate;
            private String creator;
            private long modifyDate;
            private String lastModifyMan;
            private String code;
            private String cipher;
            private String name;
            private int randomNum;
            private String rates;
            private String fixDefIds;
            private int quantity;
            private int claimed;
            private int claimLimit;
            private String enabledate;
            private String expiredate;
            @SerializedName("status")
            private int statusX;
            private String pcCode;
            private String wapCode;
            private long claimStart;
            private long claimEnd;
            private String redirectUrl;
            private int totalNum;
            private boolean over;
            private int needAmount;
            private int amount;
            private String type;
            private boolean random;

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public int getNeedAmount() {
                return needAmount;
            }

            public void setNeedAmount(int needAmount) {
                this.needAmount = needAmount;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public long getCreateDate() {
                return createDate;
            }

            public void setCreateDate(long createDate) {
                this.createDate = createDate;
            }

            public String getCreator() {
                return creator;
            }

            public void setCreator(String creator) {
                this.creator = creator;
            }

            public long getModifyDate() {
                return modifyDate;
            }

            public void setModifyDate(long modifyDate) {
                this.modifyDate = modifyDate;
            }

            public String getLastModifyMan() {
                return lastModifyMan;
            }

            public void setLastModifyMan(String lastModifyMan) {
                this.lastModifyMan = lastModifyMan;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getCipher() {
                return cipher;
            }

            public void setCipher(String cipher) {
                this.cipher = cipher;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getRandomNum() {
                return randomNum;
            }

            public void setRandomNum(int randomNum) {
                this.randomNum = randomNum;
            }

            public String getRates() {
                return rates;
            }

            public void setRates(String rates) {
                this.rates = rates;
            }

            public String getFixDefIds() {
                return fixDefIds;
            }

            public void setFixDefIds(String fixDefIds) {
                this.fixDefIds = fixDefIds;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public int getClaimed() {
                return claimed;
            }

            public void setClaimed(int claimed) {
                this.claimed = claimed;
            }

            public int getClaimLimit() {
                return claimLimit;
            }

            public void setClaimLimit(int claimLimit) {
                this.claimLimit = claimLimit;
            }

            public String getEnabledate() {
                return enabledate;
            }

            public void setEnabledate(String enabledate) {
                this.enabledate = enabledate;
            }

            public Object getExpiredate() {
                return expiredate;
            }

            public void setExpiredate(String expiredate) {
                this.expiredate = expiredate;
            }

            public int getStatusX() {
                return statusX;
            }

            public void setStatusX(int statusX) {
                this.statusX = statusX;
            }

            public Object getPcCode() {
                return pcCode;
            }

            public void setPcCode(String pcCode) {
                this.pcCode = pcCode;
            }

            public Object getWapCode() {
                return wapCode;
            }

            public void setWapCode(String wapCode) {
                this.wapCode = wapCode;
            }

            public long getClaimStart() {
                return claimStart;
            }

            public void setClaimStart(long claimStart) {
                this.claimStart = claimStart;
            }

            public long getClaimEnd() {
                return claimEnd;
            }

            public void setClaimEnd(long claimEnd) {
                this.claimEnd = claimEnd;
            }

            public Object getRedirectUrl() {
                return redirectUrl;
            }

            public void setRedirectUrl(String redirectUrl) {
                this.redirectUrl = redirectUrl;
            }

            public int getTotalNum() {
                return totalNum;
            }

            public void setTotalNum(int totalNum) {
                this.totalNum = totalNum;
            }

            public boolean isOver() {
                return over;
            }

            public void setOver(boolean over) {
                this.over = over;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public boolean isRandom() {
                return random;
            }

            public void setRandom(boolean random) {
                this.random = random;
            }
        }

        public static class LiveBean {
            /**
             * streamId : LVSID17041493274196229
             * playerCount : 0
             * redRatio : 1
             * title : Yhggg
             * previewContent :
             * cover : /app/f/17/04/27/0ffa853549a4cafd6881b3d1a0a83b4d
             * picUrl : http://pic.wsdemo.zego.im/livestream/zegotest-2873169708-LVSID17041493274196229.jpg
             * previewDate :
             * nickname : 匿名_795049
             * vcount : 0
             * id : 3622
             * beginTime :
             * channelId : LVCID17041493274196229
             * memberId : 795049
             * designerId : 10061
             * brandName : Awaylee
             * sex : 男
             * replayUrl :
             * headPic : /member_head/49/795049/c5f02e1d5f2843ce8d77663f038d92cd.png
             * designerName : 李薇
             * watchRatio : 1
             * watched : 0
             * onlineNums : 0
             * endTime :
             * status : 1
             */

            private String streamId;
            private int playerCount;
            private int redRatio;
            private String title;
            private String previewContent;
            private String cover;
            private String picUrl;
            private String previewDate;
            private String nickname;
            private int vcount;
            private int id;
            private String beginTime;
            private String channelId;
            private int memberId;
            private int designerId;
            private String brandName;
            private String sex;
            private String replayUrl;
            private String headPic;
            private String designerName;
            private int watchRatio;
            private int watched;
            private int onlineNums;
            private String endTime;
            @SerializedName("status")
            private int statusX;

            public String getStreamId() {
                return streamId;
            }

            public void setStreamId(String streamId) {
                this.streamId = streamId;
            }

            public int getPlayerCount() {
                return playerCount;
            }

            public void setPlayerCount(int playerCount) {
                this.playerCount = playerCount;
            }

            public int getRedRatio() {
                return redRatio;
            }

            public void setRedRatio(int redRatio) {
                this.redRatio = redRatio;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPreviewContent() {
                return previewContent;
            }

            public void setPreviewContent(String previewContent) {
                this.previewContent = previewContent;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }

            public String getPreviewDate() {
                return previewDate;
            }

            public void setPreviewDate(String previewDate) {
                this.previewDate = previewDate;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getVcount() {
                return vcount;
            }

            public void setVcount(int vcount) {
                this.vcount = vcount;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getBeginTime() {
                return beginTime;
            }

            public void setBeginTime(String beginTime) {
                this.beginTime = beginTime;
            }

            public String getChannelId() {
                return channelId;
            }

            public void setChannelId(String channelId) {
                this.channelId = channelId;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public int getDesignerId() {
                return designerId;
            }

            public void setDesignerId(int designerId) {
                this.designerId = designerId;
            }

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getReplayUrl() {
                return replayUrl;
            }

            public void setReplayUrl(String replayUrl) {
                this.replayUrl = replayUrl;
            }

            public String getHeadPic() {
                return headPic;
            }

            public void setHeadPic(String headPic) {
                this.headPic = headPic;
            }

            public String getDesignerName() {
                return designerName;
            }

            public void setDesignerName(String designerName) {
                this.designerName = designerName;
            }

            public int getWatchRatio() {
                return watchRatio;
            }

            public void setWatchRatio(int watchRatio) {
                this.watchRatio = watchRatio;
            }

            public int getWatched() {
                return watched;
            }

            public void setWatched(int watched) {
                this.watched = watched;
            }

            public int getOnlineNums() {
                return onlineNums;
            }

            public void setOnlineNums(int onlineNums) {
                this.onlineNums = onlineNums;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public int getStatusX() {
                return statusX;
            }

            public void setStatusX(int statusX) {
                this.statusX = statusX;
            }
        }
    }
}
