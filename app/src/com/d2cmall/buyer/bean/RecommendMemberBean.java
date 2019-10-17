package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * 推荐会员Bean
 * Author: Blend
 * Date: 2016/07/13 18:45
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class RecommendMemberBean extends BaseBean {

    /**
     * memberList : {"index":1,"pageSize":20,"total":2,"previous":false,"next":false,"list":[{"id":56,"name":"15869170410","nickname":"云之彼端","headPic":"/app/a/16/07/05/4300c3a304e5aa40e9771dff73128e8c","recommendDate":"","recommendRebates":10}]}
     */

    private DataEntity data;

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * index : 1
         * pageSize : 20
         * total : 2
         * previous : false
         * next : false
         * list : [{"id":56,"name":"15869170410","nickname":"云之彼端","headPic":"/app/a/16/07/05/4300c3a304e5aa40e9771dff73128e8c","recommendDate":"","recommendRebates":10}]
         */

        private MemberListEntity memberList;

        public MemberListEntity getMemberList() {
            return memberList;
        }

        public void setMemberList(MemberListEntity memberList) {
            this.memberList = memberList;
        }

        public static class MemberListEntity {
            private int index;
            private int pageSize;
            private int total;
            private boolean previous;
            private boolean next;
            /**
             * id : 56
             * name : 15869170410
             * nickname : 云之彼端
             * headPic : /app/a/16/07/05/4300c3a304e5aa40e9771dff73128e8c
             * recommendDate :
             * recommendRebates : 10
             */

            private List<ListEntity> list;

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

            public boolean isNext() {
                return next;
            }

            public void setNext(boolean next) {
                this.next = next;
            }

            public List<ListEntity> getList() {
                return list;
            }

            public void setList(List<ListEntity> list) {
                this.list = list;
            }

            public static class ListEntity implements Identifiable {
                private long id;
                private String name;
                private String nickname;
                private String headPic;
                private String recommendDate;
                private double recommendRebates;

                public long getId() {
                    return id;
                }

                public void setId(long id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public String getHeadPic() {
                    return headPic;
                }

                public void setHeadPic(String headPic) {
                    this.headPic = headPic;
                }

                public String getRecommendDate() {
                    return recommendDate;
                }

                public void setRecommendDate(String recommendDate) {
                    this.recommendDate = recommendDate;
                }

                public double getRecommendRebates() {
                    return recommendRebates;
                }

                public void setRecommendRebates(double recommendRebates) {
                    this.recommendRebates = recommendRebates;
                }
            }
        }
    }
}
