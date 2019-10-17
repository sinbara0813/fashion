package com.d2cmall.buyer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.TabPagerAdapter;
import com.d2cmall.buyer.listener.ExpandListener;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/22 14:14
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ExpandViewPager extends ViewPager {

    private boolean canSlide = false;
    private boolean isExpand = true;
    private TouchListener touchListener;

    public ExpandViewPager(Context context) {
        this(context, null);
    }

    public ExpandViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.expandView);
        isExpand=ta.getBoolean(R.styleable.expandView_isExpand,true);
        canSlide=ta.getBoolean(R.styleable.expandView_canSlide,true);
        ta.recycle();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!canSlide){
            if (touchListener!=null){
                touchListener.onTouch(ev);
            }
            return canSlide;
        }else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!canSlide){
            return canSlide;
        }else {
            return super.onTouchEvent(ev);
        }
    }

    public boolean canSlide() {
        return canSlide;
    }

    public void setCanSlide(boolean canSlide) {
        this.canSlide = canSlide;
    }

    public boolean isExpand() {
        TabPagerAdapter mainPageAdapter= (TabPagerAdapter) getAdapter();
        Fragment fragment= null;
        if (mainPageAdapter!=null){
            fragment=mainPageAdapter.getItem(getCurrentItem());
        }
        if (fragment!=null&&fragment instanceof ExpandListener){
            isExpand=((ExpandListener)fragment).isExpand();
        }
        return isExpand;
    }

    public void canRefresh(boolean is){
        if (getAdapter()!=null){
            TabPagerAdapter mainPageAdapter= (TabPagerAdapter) getAdapter();
            Fragment fragment=mainPageAdapter.getItem(getCurrentItem());
            if (fragment!=null&&fragment instanceof ExpandListener){
                ((ExpandListener)fragment).canRefresh(is);
            }
        }
    }

    public void setTouchListener(TouchListener touchListener) {
        this.touchListener = touchListener;
    }

    public interface TouchListener{
        void onTouch(MotionEvent ev);
    }
}
