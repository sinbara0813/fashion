package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * 我关注的会员Bean类
 * Author: Blend
 * Date: 16/4/26 20:58
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FollowsBean extends BaseBean {

    /**
     * data : {"myFollows":{"next":true,"total":14,"previous":false,"index":1,"pageSize":12,"list":[{"memberShare":"PG one 万磁王也穿了我们设计师同款！好激动！迷妹我要冷静下😍😍😍😍😍😍","nickName":"Dope4ever","follow":1,"headPic":"/app/a/17/07/28/B0576655CA529C125A04955021DBF9D2","friends":0,"memberId":1829635},{"memberShare":"暂无动态","nickName":"开心兔cicy","follow":2,"headPic":"/app/a/17/07/28/A2726DF754170776FE5063FDA339EBEB","friends":1,"memberId":1097732},{"memberShare":"#Galtiscopio迦堤# \n\n迦堤AMOUREUX系列腕表，用不凡的工艺颠覆你的视觉👑\n你从未见过的崭新技术，我们将一颗颗Swarovski的水晶如繁星般散落在表身上，今年你绝对要拥有的新系列💫\n👉🏻七夕活动期间85折优惠👈🏻","nickName":"夏小楠","follow":1,"headPic":"/app/a/17/05/26/92D7803EDD900F70012FE5ED98EABFEF","friends":0,"memberId":2839515},{"memberShare":"七夕情侣款穿什么？\n    \n       👇👇👇\n\n    个性骷髅两面印花潮款T\n\n  ^_^ oversize 男友风 \n\n           ","nickName":"LUNA","follow":1,"headPic":"/app/a/16/08/17/e8dd3a36f21811bc1d85cee6f19e9c7c","friends":0,"memberId":947949},{"memberShare":"炒鸡美貌！！","nickName":"匿名_2873968","follow":1,"headPic":null,"friends":0,"memberId":2873968},{"memberShare":"marie elie 买家秀","nickName":"匿名_2872666","follow":1,"headPic":"/app/a/17/08/04/BB331129F43A821034CE40940CAD3FA0","friends":0,"memberId":137982},{"memberShare":"#THEROOM 推荐#夸张配饰向来深受潮人和时尚咖的宠爱 灵感源于自然中的阔叶 富有动态美 根根枝叶充满生命力 这片阔叶是否能代表你向往随性自由的心？","nickName":"THEROOM设计师品牌概念店","follow":1,"headPic":"/member_head/77/929377/715ae129584b4b869eb0e38fcae0cfe5.png","friends":0,"memberId":929377},{"memberShare":"暂无动态","nickName":"希澈AVORI","follow":1,"headPic":null,"friends":0,"memberId":2668238},{"memberShare":"暂无动态","nickName":"💖李小美💖🌸","follow":1,"headPic":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLD7XPM9R899oShibtX8mqArzeEk4tuZkNtH4K7rm6Kmfbr9R32p53zA5ian0GXS2PotDO0Fuz3JUnxw/0","friends":0,"memberId":2377226},{"memberShare":"美哭了的#安悦溪#！花千骨里的糖宝，穿着我们V.Charm Gold仲夏夜之梦新款的化身小仙女！🕊","nickName":"V.Charm设计师刘阳","follow":1,"headPic":"http://wx.qlogo.cn/mmopen/PiajxSqBRaELsRv3zJVp8T1q2Acibh0XpZUu9oV8XNsImZS9yV4qrhoJthK9Y6RPEmSOG2l8yApgYA7iaxym6VkjQ/0","friends":0,"memberId":77832},{"memberShare":"张帅白色","nickName":"徐菲阳","follow":1,"headPic":"/app/a/17/05/22/205B5BA380F5F97CE7CC45C82D345D0F","friends":0,"memberId":24530},{"memberShare":"#最美买家秀#在我心中一下","nickName":"匿名_2397","follow":1,"headPic":null,"friends":0,"memberId":2397}]}}
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
         * myFollows : {"next":true,"total":14,"previous":false,"index":1,"pageSize":12,"list":[{"memberShare":"PG one 万磁王也穿了我们设计师同款！好激动！迷妹我要冷静下😍😍😍😍😍😍","nickName":"Dope4ever","follow":1,"headPic":"/app/a/17/07/28/B0576655CA529C125A04955021DBF9D2","friends":0,"memberId":1829635},{"memberShare":"暂无动态","nickName":"开心兔cicy","follow":2,"headPic":"/app/a/17/07/28/A2726DF754170776FE5063FDA339EBEB","friends":1,"memberId":1097732},{"memberShare":"#Galtiscopio迦堤# \n\n迦堤AMOUREUX系列腕表，用不凡的工艺颠覆你的视觉👑\n你从未见过的崭新技术，我们将一颗颗Swarovski的水晶如繁星般散落在表身上，今年你绝对要拥有的新系列💫\n👉🏻七夕活动期间85折优惠👈🏻","nickName":"夏小楠","follow":1,"headPic":"/app/a/17/05/26/92D7803EDD900F70012FE5ED98EABFEF","friends":0,"memberId":2839515},{"memberShare":"七夕情侣款穿什么？\n    \n       👇👇👇\n\n    个性骷髅两面印花潮款T\n\n  ^_^ oversize 男友风 \n\n           ","nickName":"LUNA","follow":1,"headPic":"/app/a/16/08/17/e8dd3a36f21811bc1d85cee6f19e9c7c","friends":0,"memberId":947949},{"memberShare":"炒鸡美貌！！","nickName":"匿名_2873968","follow":1,"headPic":null,"friends":0,"memberId":2873968},{"memberShare":"marie elie 买家秀","nickName":"匿名_2872666","follow":1,"headPic":"/app/a/17/08/04/BB331129F43A821034CE40940CAD3FA0","friends":0,"memberId":137982},{"memberShare":"#THEROOM 推荐#夸张配饰向来深受潮人和时尚咖的宠爱 灵感源于自然中的阔叶 富有动态美 根根枝叶充满生命力 这片阔叶是否能代表你向往随性自由的心？","nickName":"THEROOM设计师品牌概念店","follow":1,"headPic":"/member_head/77/929377/715ae129584b4b869eb0e38fcae0cfe5.png","friends":0,"memberId":929377},{"memberShare":"暂无动态","nickName":"希澈AVORI","follow":1,"headPic":null,"friends":0,"memberId":2668238},{"memberShare":"暂无动态","nickName":"💖李小美💖🌸","follow":1,"headPic":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLD7XPM9R899oShibtX8mqArzeEk4tuZkNtH4K7rm6Kmfbr9R32p53zA5ian0GXS2PotDO0Fuz3JUnxw/0","friends":0,"memberId":2377226},{"memberShare":"美哭了的#安悦溪#！花千骨里的糖宝，穿着我们V.Charm Gold仲夏夜之梦新款的化身小仙女！🕊","nickName":"V.Charm设计师刘阳","follow":1,"headPic":"http://wx.qlogo.cn/mmopen/PiajxSqBRaELsRv3zJVp8T1q2Acibh0XpZUu9oV8XNsImZS9yV4qrhoJthK9Y6RPEmSOG2l8yApgYA7iaxym6VkjQ/0","friends":0,"memberId":77832},{"memberShare":"张帅白色","nickName":"徐菲阳","follow":1,"headPic":"/app/a/17/05/22/205B5BA380F5F97CE7CC45C82D345D0F","friends":0,"memberId":24530},{"memberShare":"#最美买家秀#在我心中一下","nickName":"匿名_2397","follow":1,"headPic":null,"friends":0,"memberId":2397}]}
         */

        private MyFollowsBean myFollows;

        public MyFollowsBean getMyFollows() {
            return myFollows;
        }

        public void setMyFollows(MyFollowsBean myFollows) {
            this.myFollows = myFollows;
        }

        public static class MyFollowsBean {
            /**
             * next : true
             * total : 14
             * previous : false
             * index : 1
             * pageSize : 12
             * list : [{"memberShare":"PG one 万磁王也穿了我们设计师同款！好激动！迷妹我要冷静下😍😍😍😍😍😍","nickName":"Dope4ever","follow":1,"headPic":"/app/a/17/07/28/B0576655CA529C125A04955021DBF9D2","friends":0,"memberId":1829635},{"memberShare":"暂无动态","nickName":"开心兔cicy","follow":2,"headPic":"/app/a/17/07/28/A2726DF754170776FE5063FDA339EBEB","friends":1,"memberId":1097732},{"memberShare":"#Galtiscopio迦堤# \n\n迦堤AMOUREUX系列腕表，用不凡的工艺颠覆你的视觉👑\n你从未见过的崭新技术，我们将一颗颗Swarovski的水晶如繁星般散落在表身上，今年你绝对要拥有的新系列💫\n👉🏻七夕活动期间85折优惠👈🏻","nickName":"夏小楠","follow":1,"headPic":"/app/a/17/05/26/92D7803EDD900F70012FE5ED98EABFEF","friends":0,"memberId":2839515},{"memberShare":"七夕情侣款穿什么？\n    \n       👇👇👇\n\n    个性骷髅两面印花潮款T\n\n  ^_^ oversize 男友风 \n\n           ","nickName":"LUNA","follow":1,"headPic":"/app/a/16/08/17/e8dd3a36f21811bc1d85cee6f19e9c7c","friends":0,"memberId":947949},{"memberShare":"炒鸡美貌！！","nickName":"匿名_2873968","follow":1,"headPic":null,"friends":0,"memberId":2873968},{"memberShare":"marie elie 买家秀","nickName":"匿名_2872666","follow":1,"headPic":"/app/a/17/08/04/BB331129F43A821034CE40940CAD3FA0","friends":0,"memberId":137982},{"memberShare":"#THEROOM 推荐#夸张配饰向来深受潮人和时尚咖的宠爱 灵感源于自然中的阔叶 富有动态美 根根枝叶充满生命力 这片阔叶是否能代表你向往随性自由的心？","nickName":"THEROOM设计师品牌概念店","follow":1,"headPic":"/member_head/77/929377/715ae129584b4b869eb0e38fcae0cfe5.png","friends":0,"memberId":929377},{"memberShare":"暂无动态","nickName":"希澈AVORI","follow":1,"headPic":null,"friends":0,"memberId":2668238},{"memberShare":"暂无动态","nickName":"💖李小美💖🌸","follow":1,"headPic":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLD7XPM9R899oShibtX8mqArzeEk4tuZkNtH4K7rm6Kmfbr9R32p53zA5ian0GXS2PotDO0Fuz3JUnxw/0","friends":0,"memberId":2377226},{"memberShare":"美哭了的#安悦溪#！花千骨里的糖宝，穿着我们V.Charm Gold仲夏夜之梦新款的化身小仙女！🕊","nickName":"V.Charm设计师刘阳","follow":1,"headPic":"http://wx.qlogo.cn/mmopen/PiajxSqBRaELsRv3zJVp8T1q2Acibh0XpZUu9oV8XNsImZS9yV4qrhoJthK9Y6RPEmSOG2l8yApgYA7iaxym6VkjQ/0","friends":0,"memberId":77832},{"memberShare":"张帅白色","nickName":"徐菲阳","follow":1,"headPic":"/app/a/17/05/22/205B5BA380F5F97CE7CC45C82D345D0F","friends":0,"memberId":24530},{"memberShare":"#最美买家秀#在我心中一下","nickName":"匿名_2397","follow":1,"headPic":null,"friends":0,"memberId":2397}]
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
                /**
                 * memberShare : PG one 万磁王也穿了我们设计师同款！好激动！迷妹我要冷静下😍😍😍😍😍😍
                 * nickName : Dope4ever
                 * follow : 1
                 * headPic : /app/a/17/07/28/B0576655CA529C125A04955021DBF9D2
                 * friends : 0
                 * memberId : 1829635
                 */

                private String memberShare;
                private String nickName;
                private int follow;
                private String headPic;
                private int friends;
                private int memberId;

                public String getMemberShare() {
                    return memberShare;
                }

                public void setMemberShare(String memberShare) {
                    this.memberShare = memberShare;
                }

                public String getNickName() {
                    return nickName;
                }

                public void setNickName(String nickName) {
                    this.nickName = nickName;
                }

                public int getFollow() {
                    return follow;
                }

                public void setFollow(int follow) {
                    this.follow = follow;
                }

                public String getHeadPic() {
                    return headPic;
                }

                public void setHeadPic(String headPic) {
                    this.headPic = headPic;
                }

                public int getFriends() {
                    return friends;
                }

                public void setFriends(int friends) {
                    this.friends = friends;
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
