package com.d2cmall.buyer.bean;

import java.io.Serializable;


public class VideoEditInfo implements Serializable {

    public String path; //图片的sd卡路径
    public long time;//图片所在视频的时间  毫秒
    public String outPutPath;

    public VideoEditInfo() {
    }


    @Override
    public String toString() {
        return "VideoEditInfo{" +
                "path='" + path + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
