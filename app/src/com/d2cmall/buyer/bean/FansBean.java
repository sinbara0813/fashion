package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

public class FansBean extends BaseBean {

    /**
     * myFans : {"index":1,"pageSize":40,"total":1,"previous":false,"next":false,"list":[{"memberId":858774,"headPic":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLCJibtMPJ1VwUSvmcZYpGq3ZmCKQz7nnYHTDicY4spsaibJIjJVJMZbJtia0f1AFHbTwBWzKnI83rxwKg/0","nickName":"123456789012345678901234567890123456789012345678901234567890"}]}
     */

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * index : 1
         * pageSize : 40
         * total : 1
         * previous : false
         * next : false
         * list : [{"memberId":858774,"headPic":"http://wx.qlogo.cn/mmopen/ajNVdqHZLLCJibtMPJ1VwUSvmcZYpGq3ZmCKQz7nnYHTDicY4spsaibJIjJVJMZbJtia0f1AFHbTwBWzKnI83rxwKg/0","nickName":"123456789012345678901234567890123456789012345678901234567890"}]
         */

        private MyFansEntity myFans;

        public void setMyFans(MyFansEntity myFans) {
            this.myFans = myFans;
        }

        public MyFansEntity getMyFans() {
            return myFans;
        }

        public static class MyFansEntity {
            private int index;
            private int pageSize;
            private int total;
            private boolean previous;
            private boolean next;
            /**
             * memberId : 858774
             * headPic : http://wx.qlogo.cn/mmopen/ajNVdqHZLLCJibtMPJ1VwUSvmcZYpGq3ZmCKQz7nnYHTDicY4spsaibJIjJVJMZbJtia0f1AFHbTwBWzKnI83rxwKg/0
             * nickName : 123456789012345678901234567890123456789012345678901234567890
             */

            private List<ListEntity> list;

            public void setIndex(int index) {
                this.index = index;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public void setPrevious(boolean previous) {
                this.previous = previous;
            }

            public void setNext(boolean next) {
                this.next = next;
            }

            public void setList(List<ListEntity> list) {
                this.list = list;
            }

            public int getIndex() {
                return index;
            }

            public int getPageSize() {
                return pageSize;
            }

            public int getTotal() {
                return total;
            }

            public boolean isPrevious() {
                return previous;
            }

            public boolean isNext() {
                return next;
            }

            public List<ListEntity> getList() {
                return list;
            }

            public static class ListEntity implements Identifiable {
                private long memberId;
                private String headPic;
                private String nickName;
                private int follow;

                public void setMemberId(long memberId) {
                    this.memberId = memberId;
                }

                public void setHeadPic(String headPic) {
                    this.headPic = headPic;
                }

                public void setNickName(String nickName) {
                    this.nickName = nickName;
                }

                public long getMemberId() {
                    return memberId;
                }

                public String getHeadPic() {
                    return headPic;
                }

                public String getNickName() {
                    return nickName;
                }

                @Override
                public long getId() {
                    return memberId;
                }

                public int getFollow() {
                    return follow;
                }

                public void setFollow(int follow) {
                    this.follow = follow;
                }
            }
        }
    }
}
