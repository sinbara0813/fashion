package com.d2cmall.buyer.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import com.d2cmall.buyer.AppProfile;
import com.d2cmall.buyer.BuildConfig;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.widget.ExpandViewPager;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/21 16:47
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class SearchBehavior extends ViewOffsetBehavior {

    private static final String TAG = "SearchBehavior";
    public static final int DURATION_SHORT = 300;
    private OverScroller mOverScroller;

    public SearchBehavior() {
        init();
    }

    public SearchBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mOverScroller = new OverScroller(AppProfile.getContext());
    }

    @Override
    protected void layoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        ((CoordinatorLayout.LayoutParams)child.getLayoutParams()).topMargin= ScreenUtil.dip2px(56);
        super.layoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes,int type) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "onStartNestedScroll: ");
        }
        //return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0 && canScroll(child, 0)&&isExpand(coordinatorLayout);
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0 && canScroll(child, 0);
    }

    private boolean isExpand(CoordinatorLayout coordinatorLayout){
        ExpandViewPager viewPager= (ExpandViewPager) coordinatorLayout.findViewById(R.id.main_view_pager);
        return viewPager.isExpand();
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        if (!isExpand(coordinatorLayout)){
            return false;
        }else {
            if ((child.getTranslationY()==0&&velocityY>0)||(child.getTranslationY()==getHeaderOffsetRange()&&velocityY<0)){
                return false;
            }else {
                return true;
            }
        }
        // consumed the flinging behavior until Closed
    }


    private boolean isClosed(View child) {
        boolean isClosed = child.getTranslationY() == getHeaderOffsetRange();
        return isClosed;
    }

    private boolean canScroll(View child, float pendingDy) {
        int pendingTranslationY = (int) (child.getTranslationY() - pendingDy);
        if (pendingTranslationY >=getHeaderOffsetRange() && pendingTranslationY <= 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, final View child, MotionEvent ev) {
        /*if (ev.getAction() == MotionEvent.ACTION_UP&&child.getTranslationY()>getHeaderOffsetRange()&&child.getTranslationY()<0) {
            handleActionUp(parent, child);
        }*/
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target) {
        if (child.getTranslationY()>getHeaderOffsetRange()&&child.getTranslationY()<0){
            handleActionUp(coordinatorLayout,child);
        }
        super.onStopNestedScroll(coordinatorLayout, child, target);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        //dy>0 scroll up;dy<0,scroll down
        float halfOfDis = dy / 2.0f;
        if (child.getTranslationY()==getHeaderOffsetRange()&&dy>0){ //滑到顶
            consumed[1]=0;
        }else if (child.getTranslationY()==0&&dy<0){
            consumed[1]=0;
        }else {
            if (!canScroll(child, halfOfDis)) {
                if (halfOfDis>0){
                    child.setTranslationY(getHeaderOffsetRange());
                }else {
                    child.setTranslationY(0);
                }
                //child.setTranslationY(halfOfDis > 0 ? getHeaderOffsetRange() : 0);
            } else {
                child.setTranslationY(child.getTranslationY() - halfOfDis);
            }
            //consumed all scroll behavior after we started Nested Scrolling
            consumed[1] = dy;
        }
    }


    private int getHeaderOffsetRange() {
        return -ScreenUtil.dip2px(40);
    }

    private void handleActionUp(CoordinatorLayout parent, final View child) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "handleActionUp: ");
        }
        if (mFlingRunnable != null) {
            child.removeCallbacks(mFlingRunnable);
            mFlingRunnable = null;
        }
        mFlingRunnable = new FlingRunnable(parent, child);
        if (child.getTranslationY() < getHeaderOffsetRange() / 3.0f) {
            mFlingRunnable.scrollToClosed(DURATION_SHORT);
        } else {
            mFlingRunnable.scrollToOpen(DURATION_SHORT);
        }

    }

    private FlingRunnable mFlingRunnable;

    private class FlingRunnable implements Runnable {
        private final CoordinatorLayout mParent;
        private final View mLayout;

        FlingRunnable(CoordinatorLayout parent, View layout) {
            mParent = parent;
            mLayout = layout;
        }

        public void scrollToClosed(int duration) {
            float curTranslationY = ViewCompat.getTranslationY(mLayout);
            float dy = getHeaderOffsetRange() - curTranslationY;
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "scrollToClosed:offest:" + getHeaderOffsetRange());
                Log.d(TAG, "scrollToClosed: cur0:" + curTranslationY + ",end0:" + dy);
                Log.d(TAG, "scrollToClosed: cur:" + Math.round(curTranslationY) + ",end:" + Math.round(dy));
                Log.d(TAG, "scrollToClosed: cur1:" + (int) (curTranslationY) + ",end:" + (int) dy);
            }
            mOverScroller.startScroll(0, Math.round(curTranslationY - 0.1f), 0, Math.round(dy + 0.1f), duration);
            start();
        }

        public void scrollToOpen(int duration) {
            float curTranslationY = ViewCompat.getTranslationY(mLayout);
            mOverScroller.startScroll(0, (int) curTranslationY, 0, (int) -curTranslationY, duration);
            start();
        }

        private void start() {
            if (mOverScroller.computeScrollOffset()) {
                mFlingRunnable = new FlingRunnable(mParent, mLayout);
                ViewCompat.postOnAnimation(mLayout, mFlingRunnable);
            } else {
            }
        }


        @Override
        public void run() {
            if (mLayout != null && mOverScroller != null) {
                if (mOverScroller.computeScrollOffset()) {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "run: " + mOverScroller.getCurrY());
                    }
                    ViewCompat.setTranslationY(mLayout, mOverScroller.getCurrY());
                    ViewCompat.postOnAnimation(mLayout, this);
                } else {
                }
            }
        }
    }

}
