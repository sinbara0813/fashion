package com.d2cmall.buyer.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoSlideViewPager extends ViewPager {

    private boolean canSlide = false;

    public NoSlideViewPager(Context context) {
        this(context, null);
    }

    public NoSlideViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return canSlide;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return canSlide;
    }

    public boolean canSlide() {
        return canSlide;
    }

    public void setCanSlide(boolean canSlide) {
        this.canSlide = canSlide;
    }
}
