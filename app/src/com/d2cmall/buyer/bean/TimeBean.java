package com.d2cmall.buyer.bean;

/**
 * 类似微信动态里的时间格式
 * Author: Blend
 * Date: 2016/06/01 17:24
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class TimeBean {

    private String month;
    private String day;
    private boolean isTodayOrYesterday;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isTodayOrYesterday() {
        return isTodayOrYesterday;
    }

    public void setTodayOrYesterday(boolean todayOrYesterday) {
        isTodayOrYesterday = todayOrYesterday;
    }
}
