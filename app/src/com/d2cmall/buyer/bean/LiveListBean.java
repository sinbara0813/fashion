package com.d2cmall.buyer.bean;

import com.d2cmall.buyer.base.BaseBean;

import java.util.List;

/**
 * Fixme
 * Author: Blend
 * Date: 2017/03/23 16:25
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class LiveListBean extends BaseBean{


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private LivesBean lives;

        public LivesBean getLives() {
            return lives;
        }

        public void setLives(LivesBean lives) {
            this.lives = lives;
        }


        public static class LivesBean {

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

            public static class ListBean implements Identifiable{
                /**
                 * designerId : 10037
                 * brandName : Annakiki
                 * streamId : LVSID17031490257057236
                 * playerCount : 5
                 * sex : 男
                 * title : 哈哈哈
                 * replayUrl :
                 * headPic : /app/a/17/03/08/05E79029A9BF76D766E60D5CD698BEEB
                 * previewContent :
                 * designerName : 杨子
                 * cover : /app/a/17/03/23/7EE56ED80C1E05837AAEDD7129201F9A
                 * picUrl :
                 * watched : 0
                 * previewDate : 1970/01/01 08:00:00
                 * onlineNums : 2
                 * nickname : 啊啊
                 * vcount : 0
                 * id : 2543
                 * beginTime : 1970/01/01 08:00:00
                 * endTime : 1970/01/01 08:00:00
                 * channelId : LVCID17031490257057236
                 * status : 1
                 * memberId : 849778
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
                private long memberId;
                private String hlsUrl;//HLS播放地址
                private String streamId;
                private int vcount;
                private int vfans;

                public int getVcount() {
                    return vcount;
                }

                public void setVcount(int vcount) {
                    this.vcount = vcount;
                }

                public int getVfans() {
                    return vfans;
                }

                public void setVfans(int vfans) {
                    this.vfans = vfans;
                }

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

                public long getId() {
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

                public long getMemberId() {
                    return memberId;
                }

                public void setMemberId(long memberId) {
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
}
