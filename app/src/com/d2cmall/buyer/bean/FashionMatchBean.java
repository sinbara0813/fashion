package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * 作者:Created by sinbara on 2018/11/23.
 * 邮箱:hrb940258169@163.com
 */

public class FashionMatchBean extends BaseBean {

    /**
     * data : {"myWardrobeCollocations":{"next":false,"total":3,"previous":false,"index":1,"pageSize":20,"list":[{"temp":"13","city":"杭州市","loginCode":"111","description":"云翻雨覆发货吃吃吃","video":null,"headPic":"/app/a/18/11/21/faae0cdfba23e584bf915fe3659004cf","timeLength":0,"nickname":"唱将","id":11,"camera":"OPPO R9m","pics":["/app/f/18/11/23/96a5ce7e0a046bbfaddd2df46d40cc8d","/app/f/18/11/23/f12ab659fde32912b1babd11c19d1799"],"open":0,"createDate":"2018/11/23 17:04:01"},{"temp":null,"city":null,"loginCode":"111","description":"刚回家看看快快快该喝喝","video":null,"headPic":"/app/a/18/11/21/faae0cdfba23e584bf915fe3659004cf","timeLength":0,"nickname":"唱将","id":10,"camera":"OPPO R9m","pics":["/app/f/18/11/23/eb5206c65538130e3e807660508439e0","/app/f/18/11/23/2c9ac241b26fed16ab1f9725106ecab0","/app/f/18/11/23/3a28ca1da4909a679a5be521412c2ddf","/app/f/18/11/23/5dfff53af28ff65c9ebc71c402c8f3fb"],"open":0,"createDate":"2018/11/23 16:52:59"},{"temp":"16","city":"杭州市","loginCode":"111","description":"苟富贵汇聚回个话viv","video":null,"headPic":"/app/a/18/11/21/faae0cdfba23e584bf915fe3659004cf","timeLength":0,"nickname":"唱将","id":9,"camera":"OPPO R9m","pics":["/app/f/18/11/23/f19694954ae9fbf47d23df606e42dd1b","/app/f/18/11/23/2b614a71891395bba27ed93c04258e2c","/app/f/18/11/23/83f7ca0e09eb9bce938393aca692ccad","/app/f/18/11/23/28128d1d33f935908db6aaf4f8cb9083"],"open":0,"createDate":"2018/11/23 16:10:21"}]}}
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
         * myWardrobeCollocations : {"next":false,"total":3,"previous":false,"index":1,"pageSize":20,"list":[{"temp":"13","city":"杭州市","loginCode":"111","description":"云翻雨覆发货吃吃吃","video":null,"headPic":"/app/a/18/11/21/faae0cdfba23e584bf915fe3659004cf","timeLength":0,"nickname":"唱将","id":11,"camera":"OPPO R9m","pics":["/app/f/18/11/23/96a5ce7e0a046bbfaddd2df46d40cc8d","/app/f/18/11/23/f12ab659fde32912b1babd11c19d1799"],"open":0,"createDate":"2018/11/23 17:04:01"},{"temp":null,"city":null,"loginCode":"111","description":"刚回家看看快快快该喝喝","video":null,"headPic":"/app/a/18/11/21/faae0cdfba23e584bf915fe3659004cf","timeLength":0,"nickname":"唱将","id":10,"camera":"OPPO R9m","pics":["/app/f/18/11/23/eb5206c65538130e3e807660508439e0","/app/f/18/11/23/2c9ac241b26fed16ab1f9725106ecab0","/app/f/18/11/23/3a28ca1da4909a679a5be521412c2ddf","/app/f/18/11/23/5dfff53af28ff65c9ebc71c402c8f3fb"],"open":0,"createDate":"2018/11/23 16:52:59"},{"temp":"16","city":"杭州市","loginCode":"111","description":"苟富贵汇聚回个话viv","video":null,"headPic":"/app/a/18/11/21/faae0cdfba23e584bf915fe3659004cf","timeLength":0,"nickname":"唱将","id":9,"camera":"OPPO R9m","pics":["/app/f/18/11/23/f19694954ae9fbf47d23df606e42dd1b","/app/f/18/11/23/2b614a71891395bba27ed93c04258e2c","/app/f/18/11/23/83f7ca0e09eb9bce938393aca692ccad","/app/f/18/11/23/28128d1d33f935908db6aaf4f8cb9083"],"open":0,"createDate":"2018/11/23 16:10:21"}]}
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
             * total : 3
             * previous : false
             * index : 1
             * pageSize : 20
             * list : [{"temp":"13","city":"杭州市","loginCode":"111","description":"云翻雨覆发货吃吃吃","video":null,"headPic":"/app/a/18/11/21/faae0cdfba23e584bf915fe3659004cf","timeLength":0,"nickname":"唱将","id":11,"camera":"OPPO R9m","pics":["/app/f/18/11/23/96a5ce7e0a046bbfaddd2df46d40cc8d","/app/f/18/11/23/f12ab659fde32912b1babd11c19d1799"],"open":0,"createDate":"2018/11/23 17:04:01"},{"temp":null,"city":null,"loginCode":"111","description":"刚回家看看快快快该喝喝","video":null,"headPic":"/app/a/18/11/21/faae0cdfba23e584bf915fe3659004cf","timeLength":0,"nickname":"唱将","id":10,"camera":"OPPO R9m","pics":["/app/f/18/11/23/eb5206c65538130e3e807660508439e0","/app/f/18/11/23/2c9ac241b26fed16ab1f9725106ecab0","/app/f/18/11/23/3a28ca1da4909a679a5be521412c2ddf","/app/f/18/11/23/5dfff53af28ff65c9ebc71c402c8f3fb"],"open":0,"createDate":"2018/11/23 16:52:59"},{"temp":"16","city":"杭州市","loginCode":"111","description":"苟富贵汇聚回个话viv","video":null,"headPic":"/app/a/18/11/21/faae0cdfba23e584bf915fe3659004cf","timeLength":0,"nickname":"唱将","id":9,"camera":"OPPO R9m","pics":["/app/f/18/11/23/f19694954ae9fbf47d23df606e42dd1b","/app/f/18/11/23/2b614a71891395bba27ed93c04258e2c","/app/f/18/11/23/83f7ca0e09eb9bce938393aca692ccad","/app/f/18/11/23/28128d1d33f935908db6aaf4f8cb9083"],"open":0,"createDate":"2018/11/23 16:10:21"}]
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
                 * temp : 13
                 * city : 杭州市
                 * loginCode : 111
                 * description : 云翻雨覆发货吃吃吃
                 * video : null
                 * headPic : /app/a/18/11/21/faae0cdfba23e584bf915fe3659004cf
                 * timeLength : 0
                 * nickname : 唱将
                 * id : 11
                 * camera : OPPO R9m
                 * pics : ["/app/f/18/11/23/96a5ce7e0a046bbfaddd2df46d40cc8d","/app/f/18/11/23/f12ab659fde32912b1babd11c19d1799"]
                 * open : 0
                 * createDate : 2018/11/23 17:04:01
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
                private long transTime;
                private String camera;
                private int open;
                private String createDate;
                private String transactionTime;

                public long getTransTime() {
                    if (transactionTime==null)
                        return 0;
                    return DateUtil.toDate(transactionTime).getTime();
                }

                public String getTransactionTime() {
                    return transactionTime;
                }

                public void setTransactionTime(String transactionTime) {
                    this.transactionTime = transactionTime;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                private String weather;
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
