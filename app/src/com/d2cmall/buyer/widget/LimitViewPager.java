package com.d2cmall.buyer.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/27 14:31
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class LimitViewPager extends ViewPager {

    private boolean isCanScroll=true;

    public LimitViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LimitViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isCanScroll&&super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isCanScroll&&super.onTouchEvent(ev);
    }

    public void setCanScroll(boolean is){
        isCanScroll=is;
    }
}
