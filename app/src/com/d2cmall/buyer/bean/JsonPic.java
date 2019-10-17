package com.d2cmall.buyer.bean;

public class JsonPic implements Identifiable {

    private long id;
    private int width;
    private int height;
    private long bucketId;
    private String mediaPath;
    private String uploadPath;
    private String videoPic;
    private String taskIds;
    private boolean isVideo;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
        if (!mediaPath.endsWith("jpg")&&!mediaPath.endsWith("png")){
            isVideo=true;
        }else {
            isVideo=false;
        }
    }

    public long getBucketId() {
        return bucketId;
    }

    public void setBucketId(long bucketId) {
        this.bucketId = bucketId;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public String getVideoPic() {
        return videoPic;
    }

    public void setVideoPic(String videoPic) {
        this.videoPic = videoPic;
    }

    public void setTaskIds(String taskIds) {
        this.taskIds = taskIds;
    }

    public String getTaskIds() {
        return taskIds;
    }
}
