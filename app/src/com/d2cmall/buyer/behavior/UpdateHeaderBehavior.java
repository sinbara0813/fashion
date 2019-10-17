package com.d2cmall.buyer.behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import com.d2cmall.buyer.AppProfile;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.ExpandViewPager;

import java.lang.ref.WeakReference;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2018/09/06 14:50
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class UpdateHeaderBehavior extends ViewOffsetBehavior {
    private static final String TAG = "UpdateHeaderBehavior";
    public static final int STATE_OPENED = 0;
    public static final int STATE_CLOSED = 1;
    public static final int DURATION_SHORT = 300;
    public static final int DURATION_LONG = 600;

    private int mCurState = STATE_OPENED;
    private OnPagerStateListener mPagerStateListener;

    private OverScroller mOverScroller;

    private WeakReference<CoordinatorLayout> mParent;
    private WeakReference<View> mChild;
    private float mvelocityY;


    public void setPagerStateListener(OnPagerStateListener pagerStateListener) {
        mPagerStateListener = pagerStateListener;
    }

    public UpdateHeaderBehavior() {
        init();
    }


    public UpdateHeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mOverScroller = new OverScroller(AppProfile.getContext());
    }

    @Override
    protected void layoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        super.layoutChild(parent, child, layoutDirection);
        mParent = new WeakReference<CoordinatorLayout>(parent);
        mChild = new WeakReference<View>(child);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes,int type) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0 && canScroll(child, 0)&&isExpand();
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        // consumed the flinging behavior until Closed
        mvelocityY=velocityY;
        if (!isExpand()){
            return false;
        }else {
            return true;
        }
    }

    private boolean isClosed(View child) {
        boolean isClosed = child.getTranslationY() == getHeaderOffsetRange();
        return isClosed;
    }

    public boolean isClosed() {
        return mCurState == STATE_CLOSED;
    }

    private void changeState(int newState) {
        if (mCurState != newState) {
            mCurState = newState;
            if (mPagerStateListener==null)
                return;
            if (mCurState == STATE_OPENED) {
                mPagerStateListener.onPagerOpened();
            } else {
                mPagerStateListener.onPagerClosed();
            }
        }

    }

    private boolean canScroll(View child, float pendingDy) {
        int pendingTranslationY = (int) (child.getTranslationY() - pendingDy);
        if (pendingTranslationY >= getHeaderOffsetRange() && pendingTranslationY <= 0) {
            return true;
        }
        return false;
    }

    private boolean isExpand(){
        ExpandViewPager viewPager= mParent.get().findViewById(R.id.view_pager);
        if (viewPager!=null){
            return viewPager.isExpand();
        }else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, final View child, MotionEvent ev) {
        //效果不好
        /*if (ev.getAction() == MotionEvent.ACTION_UP||ev.getAction()==MotionEvent.ACTION_CANCEL) {
            if (BuildConfig.DEBUG){
                Log.d("han","action=="+ev.getAction());
            }
            handleActionUp(parent, child);
        }*/
        Log.d("han","onInterceptTouchEvent is called");
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        Log.d("han","onTouchEvent is called");
        return super.onTouchEvent(parent, child, ev);
    }

    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int type) {
        if (child.getTranslationY()>getHeaderOffsetRange()&&child.getTranslationY()<0){
            //handleActionUp(coordinatorLayout,child);
            handleAction(coordinatorLayout,child);
        }
        super.onStopNestedScroll(coordinatorLayout, child, target, type);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed,int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed,type);
        //dy>0 scroll up;dy<0,scroll down
        float halfOfDis = dy;
        if (child.getTranslationY()==getHeaderOffsetRange()&&dy>0){ //滑到顶
            consumed[1]=0;
        }else if (child.getTranslationY()==0&&dy<0){
            consumed[1]=0;
        }else {
            if (!canScroll(child, halfOfDis)) {
                child.setTranslationY(halfOfDis > 0 ? getHeaderOffsetRange() : 0);
            } else {
                child.setTranslationY(child.getTranslationY() - halfOfDis);
            }
            //child.setScaleX(1-child.getTranslationY()*0.1f/getHeaderOffsetRange());
            //consumed all scroll behavior after we started Nested Scrolling
            consumed[1] = dy;
        }
    }

    private int getHeaderOffsetRange() {
        return AppProfile.getContext().getResources().getDimensionPixelOffset(R.dimen.update_header_offset);
    }

    private void handleAction(CoordinatorLayout parent, final View child){
        Log.d("han","mvelocityY="+mvelocityY);
        float dy=mvelocityY/1000*mvelocityY/100*10.0f;
        if (mvelocityY>0){//scrollUp
            dy=-dy;
        }
        if (mFlingRunnable != null) {
            child.removeCallbacks(mFlingRunnable);
            mFlingRunnable = null;
        }
        float curTranslationY = child.getTranslationY();
        if (dy>-curTranslationY){
            dy=-curTranslationY;
        }
        if (dy<getHeaderOffsetRange()-curTranslationY){
            dy=getHeaderOffsetRange()-curTranslationY;
        }
        mFlingRunnable = new FlingRunnable(parent, child);
        Log.d("han","mOverScroller.getFinalY()=="+dy);
        mFlingRunnable.scrollTo(dy,Math.abs((int)dy*700/getHeaderOffsetRange()));
    }

    private void handleActionUp(CoordinatorLayout parent, final View child) {
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

    private void onFlingFinished(CoordinatorLayout coordinatorLayout, View layout) {
        changeState(isClosed(layout) ? STATE_CLOSED : STATE_OPENED);
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
            float curTranslationY = mLayout.getTranslationY();
            float dy = getHeaderOffsetRange() - curTranslationY;
            mOverScroller.startScroll(0, Math.round(curTranslationY - 0.1f), 0, Math.round(dy + 0.1f), duration);
            start();
        }

        public void scrollTo(float dy,int duration){
            float curTranslationY = mLayout.getTranslationY();
            mOverScroller.startScroll(0, Math.round(curTranslationY - 0.1f), 0, Math.round(dy + 0.1f), duration);
            start();
        }

        public void scrollToOpen(int duration) {
            float curTranslationY = mLayout.getTranslationY();
            mOverScroller.startScroll(0, (int) curTranslationY, 0, (int) -curTranslationY, duration);
            start();
        }

        private void start() {
            if (mOverScroller.computeScrollOffset()) {
                mFlingRunnable = new FlingRunnable(mParent, mLayout);
                ViewCompat.postOnAnimation(mLayout, mFlingRunnable);
            } else {
                onFlingFinished(mParent, mLayout);
            }
        }


        @Override
        public void run() {
            if (mLayout != null && mOverScroller != null) {
                if (mOverScroller.computeScrollOffset()) {
                    mLayout.setTranslationY(mOverScroller.getCurrY());
                    ViewCompat.postOnAnimation(mLayout, this);
                } else {
                    onFlingFinished(mParent, mLayout);
                }
            }
        }
    }

    public interface OnPagerStateListener {
        void onPagerClosed();

        void onPagerOpened();
    }
}
