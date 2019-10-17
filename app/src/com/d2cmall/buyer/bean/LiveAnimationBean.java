package com.d2cmall.buyer.bean;

public class LiveAnimationBean {

    private String nickname;
    private String content;
    private String url;
    private String head;
    public int count;
    public int time=200;
    private boolean currentStart;

    public boolean isCurrentStart() {
        return currentStart;
    }

    public void setCurrentStart(boolean currentStart) {
        this.currentStart = currentStart;
    }

    public LiveAnimationBean(String head, String nickname, String content, String url) {
        this.head = head;
        this.nickname = nickname;
        this.content = content;
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}