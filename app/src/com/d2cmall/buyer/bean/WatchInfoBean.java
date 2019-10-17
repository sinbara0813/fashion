package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * 观看直播Bean
 * Author: Blend
 * Date: 2017/01/11 15:22
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class WatchInfoBean extends BaseBean {
    /**
     * data : {"live":{"designerId":10687,"brandName":"10112","streamId":"LVSID17011484119213803","playerCount":0,"sex":"女","title":"主播很懒，什么都没有留下","replayUrl":"","headPic":"/app/a/16/12/15/d9041d2607aaac93ce302d57cb77b3a5","designerName":"朱超凡","cover":"/app/f/17/01/11/6a9d4ad7ecf1469d93b96d9699154eb0","picUrl":"http://pic.wsdemo.zego.im/livestream/zegotest-2873169708-LVSID17011484119213803.jpg","watched":0,"onlineNums":0,"nickname":"37-1","vcount":0,"id":1145,"beginTime":"","endTime":"","channelId":"LVCID17011484119213803","status":1,"memberId":1947004}}
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
         * live : {"designerId":10687,"brandName":"10112","streamId":"LVSID17011484119213803","playerCount":0,"sex":"女","title":"主播很懒，什么都没有留下","replayUrl":"","headPic":"/app/a/16/12/15/d9041d2607aaac93ce302d57cb77b3a5","designerName":"朱超凡","cover":"/app/f/17/01/11/6a9d4ad7ecf1469d93b96d9699154eb0","picUrl":"http://pic.wsdemo.zego.im/livestream/zegotest-2873169708-LVSID17011484119213803.jpg","watched":0,"onlineNums":0,"nickname":"37-1","vcount":0,"id":1145,"beginTime":"","endTime":"","channelId":"LVCID17011484119213803","status":1,"memberId":1947004}
         */

        private LiveListBean.DataBean.LivesBean.ListBean live;

        public LiveListBean.DataBean.LivesBean.ListBean getLive() {
            return live;
        }

        public void setLive(LiveListBean.DataBean.LivesBean.ListBean live) {
            this.live = live;
        }

    }
}
