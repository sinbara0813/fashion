package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2018/11/26.
 * Description : MyWearCollocationBean
 */

public class MyWearCollocationBean extends BaseBean {

    /**
     * data : {"myWardrobeCollocations":{"next":false,"total":2,"previous":false,"index":1,"pageSize":20,"list":[{"temp":null,"city":null,"loginCode":"15888897624","description":"咯啦咯啦咯了","video":null,"headPic":"/app/a/17/11/10/ad6159e38a1927d03aee7524e0ce5222","timeLength":0,"nickname":"考拉🐨","id":6,"camera":"OPPO R7","pics":["/app/f/18/11/23/8356ae0177974645ad77362c5d618203"],"open":0,"createDate":"2018-11-23 14:00:19"},{"temp":null,"city":null,"loginCode":"15888897624","description":"llll","video":null,"headPic":"/app/a/17/11/10/ad6159e38a1927d03aee7524e0ce5222","timeLength":0,"nickname":"考拉🐨","id":5,"camera":"OPPO R7","pics":["/app/f/18/11/23/641185d6dc032c2c4eeccf55f5515ebc"],"open":0,"createDate":"2018-11-23 10:00:17"}]}}
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
         * myWardrobeCollocations : {"next":false,"total":2,"previous":false,"index":1,"pageSize":20,"list":[{"temp":null,"city":null,"loginCode":"15888897624","description":"咯啦咯啦咯了","video":null,"headPic":"/app/a/17/11/10/ad6159e38a1927d03aee7524e0ce5222","timeLength":0,"nickname":"考拉🐨","id":6,"camera":"OPPO R7","pics":["/app/f/18/11/23/8356ae0177974645ad77362c5d618203"],"open":0,"createDate":"2018-11-23 14:00:19"},{"temp":null,"city":null,"loginCode":"15888897624","description":"llll","video":null,"headPic":"/app/a/17/11/10/ad6159e38a1927d03aee7524e0ce5222","timeLength":0,"nickname":"考拉🐨","id":5,"camera":"OPPO R7","pics":["/app/f/18/11/23/641185d6dc032c2c4eeccf55f5515ebc"],"open":0,"createDate":"2018-11-23 10:00:17"}]}
         */

        private MyWardrobeCollocationsBean myWardrobeCollocations;

        public MyWardrobeCollocationsBean getMyWardrobeCollocations() {
            return myWardrobeCollocations;
        }

        public void setMyWardrobeCollocations(MyWardrobeCollocationsBean myWardrobeCollocations) {
            this.myWardrobeCollocations = myWardrobeCollocations;
        }

        public static class MyWardrobeCollocationsBean {
            /**
             * next : false
             * total : 2
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"temp":null,"city":null,"loginCode":"15888897624","description":"咯啦咯啦咯了","video":null,"headPic":"/app/a/17/11/10/ad6159e38a1927d03aee7524e0ce5222","timeLength":0,"nickname":"考拉🐨","id":6,"camera":"OPPO R7","pics":["/app/f/18/11/23/8356ae0177974645ad77362c5d618203"],"open":0,"createDate":"2018-11-23 14:00:19"},{"temp":null,"city":null,"loginCode":"15888897624","description":"llll","video":null,"headPic":"/app/a/17/11/10/ad6159e38a1927d03aee7524e0ce5222","timeLength":0,"nickname":"考拉🐨","id":5,"camera":"OPPO R7","pics":["/app/f/18/11/23/641185d6dc032c2c4eeccf55f5515ebc"],"open":0,"createDate":"2018-11-23 10:00:17"}]
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
                 * temp : null
                 * city : null
                 * loginCode : 15888897624
                 * description : 咯啦咯啦咯了
                 * video : null
                 * headPic : /app/a/17/11/10/ad6159e38a1927d03aee7524e0ce5222
                 * timeLength : 0
                 * nickname : 考拉🐨
                 * id : 6
                 * camera : OPPO R7
                 * pics : ["/app/f/18/11/23/8356ae0177974645ad77362c5d618203"]
                 * open : 0
                 * createDate : 2018-11-23 14:00:19
                 */

                private String temp;
                private String city;
                private String loginCode;
                private String description;
                private String video;
                private String headPic;
                private int timeLength;
                private String nickname;
                private int id;
                private String camera;
                private int open;
                private String createDate;

                private long memberId;

                public long getMemberId() {
                    return memberId;
                }

                public void setMemberId(long memberId) {
                    this.memberId = memberId;
                }

                public String getTransactionTime() {
                    return transactionTime;
                }

                public void setTransactionTime(String transactionTime) {
                    this.transactionTime = transactionTime;
                }

                private String transactionTime;


                private int curDayTotal;//当天有多少张图片

                public int getCurDayTotal() {
                    return curDayTotal;
                }

                public void setCurDayTotal(int curDayTotal) {
                    this.curDayTotal = curDayTotal;
                }
                private List<String> pics;

                public String getTemp() {
                    return temp;
                }

                public void setTemp(String temp) {
                    this.temp = temp;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getLoginCode() {
                    return loginCode;
                }

                public void setLoginCode(String loginCode) {
                    this.loginCode = loginCode;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getVideo() {
                    return video;
                }

                public void setVideo(String video) {
                    this.video = video;
                }

                public String getHeadPic() {
                    return headPic;
                }

                public void setHeadPic(String headPic) {
                    this.headPic = headPic;
                }

                public int getTimeLength() {
                    return timeLength;
                }

                public void setTimeLength(int timeLength) {
                    this.timeLength = timeLength;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getCamera() {
                    return camera;
                }

                public void setCamera(String camera) {
                    this.camera = camera;
                }

                public int getOpen() {
                    return open;
                }

                public void setOpen(int open) {
                    this.open = open;
                }

                public String getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(String createDate) {
                    this.createDate = createDate;
                }

                public List<String> getPics() {
                    return pics;
                }

                public void setPics(List<String> pics) {
                    this.pics = pics;
                }
            }
        }
    }
}
