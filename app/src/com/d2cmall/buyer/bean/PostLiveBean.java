package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

/**
 * 直播信息Bean
 * Author: Blend
 * Date: 2016/08/03 13:07
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class PostLiveBean extends BaseBean {

    /**
     * live : {"cover":"11111","designerId":10647,"watched":0,"streamId":"LVSID16121481169576228","nickname":"Pon Pon。","vcount":0,"id":4,"title":"主播很懒，什么都没有留下","headPic":"/app/a/16/09/22/9fc8c4ced6673c47da98b7431f990e0d","channelId":"LVCID16121481169576227","memberId":927483,"designerName":"余音"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private LiveListBean.DataBean.LivesBean.ListBean live;

        public LiveListBean.DataBean.LivesBean.ListBean getLive() {
            return live;
        }

        public void setLive(LiveListBean.DataBean.LivesBean.ListBean live) {
            this.live = live;
        }

    }
}
