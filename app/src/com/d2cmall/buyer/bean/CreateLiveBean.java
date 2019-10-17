package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.io.Serializable;

/**
 * Created by rookie on 2017/12/27.
 */

public class CreateLiveBean extends BaseBean implements Serializable{

    /**
     * data : {"live":{"designerId":17,"rtmpUrl":"rtmp://pili-live-rtmp.appserver.d2cmall.com/d2cmall/LVSID17121514365999254","sex":"男","title":"捏捏","replayUrl":"","headPic":"/app/a/17/12/22/179efe42fc80892a5d034fdc07c8cb76","designerName":"魏腾飞","cover":"/app/f/17/12/27/6c2b2cfb718aa47eb3e155dab19fd6c5","pushUrl":"rtmp://pili-publish.appserver.d2cmall.com/d2cmall/LVSID17121514365999254?e=1514369599&token=Q1sVShGMxx-VSrVv9BWVIyNbFDwhtOTsDmltazMy:3NzlrjGsFB4TNt97TXPSM4frcao=","hdlUrl":"http://pili-live-hdl.appserver.d2cmall.com/d2cmall/LVSID17121514365999254.flv","nickname":"你明明","id":6,"memberType":2,"status":4,"memberId":2906707,"hlsUrl":"http://pili-live-hls.appserver.d2cmall.com/d2cmall/LVSID17121514365999254.m3u8"}}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * live : {"designerId":17,"rtmpUrl":"rtmp://pili-live-rtmp.appserver.d2cmall.com/d2cmall/LVSID17121514365999254","sex":"男","title":"捏捏","replayUrl":"","headPic":"/app/a/17/12/22/179efe42fc80892a5d034fdc07c8cb76","designerName":"魏腾飞","cover":"/app/f/17/12/27/6c2b2cfb718aa47eb3e155dab19fd6c5","pushUrl":"rtmp://pili-publish.appserver.d2cmall.com/d2cmall/LVSID17121514365999254?e=1514369599&token=Q1sVShGMxx-VSrVv9BWVIyNbFDwhtOTsDmltazMy:3NzlrjGsFB4TNt97TXPSM4frcao=","hdlUrl":"http://pili-live-hdl.appserver.d2cmall.com/d2cmall/LVSID17121514365999254.flv","nickname":"你明明","id":6,"memberType":2,"status":4,"memberId":2906707,"hlsUrl":"http://pili-live-hls.appserver.d2cmall.com/d2cmall/LVSID17121514365999254.m3u8"}
         */

        private LiveListBean.DataBean.LivesBean.ListBean live;

        public LiveListBean.DataBean.LivesBean.ListBean getLive() {
            return live;
        }

        public void setLive(LiveListBean.DataBean.LivesBean.ListBean live) {
            this.live = live;
        }

        public static class LiveBean implements Serializable{
            /**
             * designerId : 17
             * rtmpUrl : rtmp://pili-live-rtmp.appserver.d2cmall.com/d2cmall/LVSID17121514365999254
             * sex : 男
             * title : 捏捏
             * replayUrl :
             * headPic : /app/a/17/12/22/179efe42fc80892a5d034fdc07c8cb76
             * designerName : 魏腾飞
             * cover : /app/f/17/12/27/6c2b2cfb718aa47eb3e155dab19fd6c5
             * pushUrl : rtmp://pili-publish.appserver.d2cmall.com/d2cmall/LVSID17121514365999254?e=1514369599&token=Q1sVShGMxx-VSrVv9BWVIyNbFDwhtOTsDmltazMy:3NzlrjGsFB4TNt97TXPSM4frcao=
             * hdlUrl : http://pili-live-hdl.appserver.d2cmall.com/d2cmall/LVSID17121514365999254.flv
             * nickname : 你明明
             * id : 6
             * memberType : 2
             * status : 4
             * memberId : 2906707
             * hlsUrl : http://pili-live-hls.appserver.d2cmall.com/d2cmall/LVSID17121514365999254.m3u8
             */

            private int designerId;
            private String rtmpUrl;//rtmp播放地址
            private String sex;
            private String title;
            private String replayUrl;//录像回看地址
            private String headPic;
            private String designerName;
            private String cover;
            private String pushUrl;//推流地址
            private String hdlUrl;//hdl播放地址
            private String nickname;
            private int id;
            private int memberType;
            private int status;//直播状态 0:初始创建 4：直播开始 8：往期回顾
            private int memberId;
            private String hlsUrl;//HLS播放地址
            private String streamId;

            public String getStreamId() {
                return streamId;
            }

            public void setStreamId(String streamId) {
                this.streamId = streamId;
            }

            public int getDesignerId() {
                return designerId;
            }

            public void setDesignerId(int designerId) {
                this.designerId = designerId;
            }

            public String getRtmpUrl() {
                return rtmpUrl;
            }

            public void setRtmpUrl(String rtmpUrl) {
                this.rtmpUrl = rtmpUrl;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
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

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public String getPushUrl() {
                return pushUrl;
            }

            public void setPushUrl(String pushUrl) {
                this.pushUrl = pushUrl;
            }

            public String getHdlUrl() {
                return hdlUrl;
            }

            public void setHdlUrl(String hdlUrl) {
                this.hdlUrl = hdlUrl;
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

            public int getMemberType() {
                return memberType;
            }

            public void setMemberType(int memberType) {
                this.memberType = memberType;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int statusX) {
                this.status = statusX;
            }

            public int getMemberId() {
                return memberId;
            }

            public void setMemberId(int memberId) {
                this.memberId = memberId;
            }

            public String getHlsUrl() {
                return hlsUrl;
            }

            public void setHlsUrl(String hlsUrl) {
                this.hlsUrl = hlsUrl;
            }
        }
    }
}
