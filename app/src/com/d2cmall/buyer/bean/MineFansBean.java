package com.d2cmall.buyer.bean;


import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Fixme
 * Author: LWJ
 * desc:
 * Date: 2017/09/11 19:39
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class MineFansBean extends BaseBean {

    /**
     * data : {"myFans":{"next":false,"total":4,"previous":false,"index":1,"pageSize":20,"list":[{"memberShare":"我加话题了吗？","nickName":"D2C白菜","follow":0,"headPic":"/member_head/59/859/9a62e649636545578ff0534b8461dbcc.jpg","friends":0,"memberId":859},{"memberShare":"我加话题了吗？","nickName":"李慧萍","follow":0,"headPic":"/app/a/17/09/02/0e1887e5f4ef5e34b301f0e3536ea3e5","friends":0,"memberId":2905570},{"memberShare":"我加话题了吗？","nickName":"何厚铧很尴尬","follow":0,"headPic":"/app/a/17/09/05/BAEF72B45D29F97209ED3667E48C4F66","friends":0,"memberId":849778},{"memberShare":"我加话题了吗？","nickName":"开心兔cicy","follow":2,"headPic":"/app/a/17/07/28/A2726DF754170776FE5063FDA339EBEB","friends":1,"memberId":1097732}]}}
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
         * myFans : {"next":false,"total":4,"previous":false,"index":1,"pageSize":20,"list":[{"memberShare":"我加话题了吗？","nickName":"D2C白菜","follow":0,"headPic":"/member_head/59/859/9a62e649636545578ff0534b8461dbcc.jpg","friends":0,"memberId":859},{"memberShare":"我加话题了吗？","nickName":"李慧萍","follow":0,"headPic":"/app/a/17/09/02/0e1887e5f4ef5e34b301f0e3536ea3e5","friends":0,"memberId":2905570},{"memberShare":"我加话题了吗？","nickName":"何厚铧很尴尬","follow":0,"headPic":"/app/a/17/09/05/BAEF72B45D29F97209ED3667E48C4F66","friends":0,"memberId":849778},{"memberShare":"我加话题了吗？","nickName":"开心兔cicy","follow":2,"headPic":"/app/a/17/07/28/A2726DF754170776FE5063FDA339EBEB","friends":1,"memberId":1097732}]}
         */

        private MyFansBean myFans;

        public MyFansBean getMyFans() {
            return myFans;
        }

        public void setMyFans(MyFansBean myFans) {
            this.myFans = myFans;
        }

        public static class MyFansBean {
            /**
             * next : false
             * total : 4
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"memberShare":"我加话题了吗？","nickName":"D2C白菜","follow":0,"headPic":"/member_head/59/859/9a62e649636545578ff0534b8461dbcc.jpg","friends":0,"memberId":859},{"memberShare":"我加话题了吗？","nickName":"李慧萍","follow":0,"headPic":"/app/a/17/09/02/0e1887e5f4ef5e34b301f0e3536ea3e5","friends":0,"memberId":2905570},{"memberShare":"我加话题了吗？","nickName":"何厚铧很尴尬","follow":0,"headPic":"/app/a/17/09/05/BAEF72B45D29F97209ED3667E48C4F66","friends":0,"memberId":849778},{"memberShare":"我加话题了吗？","nickName":"开心兔cicy","follow":2,"headPic":"/app/a/17/07/28/A2726DF754170776FE5063FDA339EBEB","friends":1,"memberId":1097732}]
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
                 * memberShare : 我加话题了吗？
                 * nickName : D2C白菜
                 * follow : 0
                 * headPic : /member_head/59/859/9a62e649636545578ff0534b8461dbcc.jpg
                 * friends : 0
                 * memberId : 859
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
