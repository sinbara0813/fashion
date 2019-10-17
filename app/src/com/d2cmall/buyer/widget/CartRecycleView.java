package com.d2cmall.buyer.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.d2cmall.buyer.listener.OnGroupClickListener;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.widget.swipemenulistview.SwipeMenuLayout;
import com.d2cmall.buyer.widget.swipemenulistview.SwipeMenuListView;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/2 16:40
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CartRecycleView extends RecyclerView {

    private static final int TOUCH_STATE_NONE = 0;
    private static final int TOUCH_STATE_X = 1;
    private static final int TOUCH_STATE_Y = 2;

    public static final int DIRECTION_LEFT = 1;
    public static final int DIRECTION_RIGHT = -1;
    private int mDirection = 1;//swipe from right to left by default

    private int MAX_Y = 5;
    private int MAX_X = 3;
    private float mDownX;
    private float mDownY;
    private int mTouchState;
    private int mTouchPosition;
    private SwipeMenuLayout mTouchView;
    private SwipeMenuListView.OnSwipeListener mOnSwipeListener;

    private SwipeMenuListView.OnMenuStateChangeListener mOnMenuStateChangeListener;
    private VirtualLayoutManager virtualLayoutManager;
    public OnGroupClickListener onGroupClickListener;

    public CartRecycleView(Context context) {
        super(context);
        init();
    }

    public CartRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CartRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        init();
    }

    private void init() {
        MAX_X = ScreenUtil.dip2px(MAX_X);
        MAX_Y = ScreenUtil.dip2px(MAX_Y);
        mTouchState = TOUCH_STATE_NONE;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (virtualLayoutManager==null){
            virtualLayoutManager= (VirtualLayoutManager) getLayoutManager();
        }
        if (gestureDetector == null) {
            gestureDetector = new GestureDetector(getContext(), gestureListener);
        }
        if (gestureDetector.onTouchEvent(ev)){
            return true;
        }
        //在拦截处处理，在滑动设置了点击事件的地方也能swip，点击时又不能影响原来的点击事件
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                boolean handled = super.onInterceptTouchEvent(ev);
                mTouchState = TOUCH_STATE_NONE;
                mTouchPosition = pointToPosition(ev.getX(), ev.getY());
                /*View view = getChildAt(mTouchPosition - virtualLayoutManager.findFirstVisibleItemPosition());*/
                View view=findChildViewUnder(ev.getX(),ev.getY());

                //只在空的时候赋值 以免每次触摸都赋值，会有多个open状态
                if (view instanceof SwipeMenuLayout) {
                    //如果有打开了 就拦截.
                    if (mTouchView != null && mTouchView.isOpen() && !inRangeOfView(mTouchView.getMenuView(), ev)) {
                        mTouchView.smoothCloseMenu();
                        return true;
                    }
                    mTouchView = (SwipeMenuLayout) view;
                    mTouchView.setSwipeDirection(mDirection);
                }else {
                    mTouchView=null;

                }
                //如果摸在另外个view
                if (mTouchView != null && mTouchView.isOpen() && view != mTouchView) {
                    handled = true;
                }

                if (mTouchView != null) {
                    mTouchView.onSwipe(ev);
                }
                return handled;
            case MotionEvent.ACTION_MOVE:
                float dy = Math.abs((ev.getY() - mDownY));
                float dx = Math.abs((ev.getX() - mDownX));
                if (Math.abs(dx)>MAX_X&&dx>dy){
                    mTouchState=TOUCH_STATE_X;
                    if (ev.getX()<mDownX){
                        if (mOnSwipeListener != null) {
                            mOnSwipeListener.onSwipeStart(mTouchPosition);
                        }
                        if(mTouchView!=null){
                            mTouchView.smoothOpenMenu();
                            return true;
                        }
                    }

                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 记录每个头部和悬浮头部的坐标信息【用于点击事件】
     * 位置由子类添加
     */
    public SparseArray<Integer> stickyHeaderPosArray = new SparseArray<>();
    private GestureDetector gestureDetector;
    private GestureDetector.OnGestureListener gestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            for (int i = 0; i < stickyHeaderPosArray.size(); i++) {
                int value = stickyHeaderPosArray.valueAt(i);
                float y = e.getY();
                float x = e.getX();
                if (value - ScreenUtil.dip2px(50) <= y && y <= value&&x<ScreenUtil.dip2px(56)) {
                    //如果点击到分组头
                    //onGroupClick(stickyHeaderPosArray.keyAt(i));
                    if (onGroupClickListener!=null){
                        onGroupClickListener.onClick(stickyHeaderPosArray.keyAt(i));
                    }
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    };

    public void smoothOpenMenu(int position) {
        VirtualLayoutManager virtualLayoutManager= (VirtualLayoutManager) getLayoutManager();
        if (position >= virtualLayoutManager.findFirstVisibleItemPosition()
                && position <= virtualLayoutManager.findLastVisibleItemPosition()) {
            View view = getChildAt(position - virtualLayoutManager.findFirstVisibleItemPosition());
            if (view instanceof SwipeMenuLayout) {
                mTouchPosition = position;
                if (mTouchView != null && mTouchView.isOpen()) {
                    mTouchView.smoothCloseMenu();
                }
                mTouchView = (SwipeMenuLayout) view;
                mTouchView.setSwipeDirection(mDirection);
                mTouchView.smoothOpenMenu();
            }
        }
    }

    public void smoothCloseMenu() {
        if (mTouchView != null && mTouchView.isOpen()) {
            mTouchView.smoothCloseMenu();
        }
    }

    /**
     * 判断点击事件是否在某个view内
     *
     * @param view
     * @param ev
     * @return
     */
    public static boolean inRangeOfView(View view, MotionEvent ev) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        if (ev.getRawX() < x || ev.getRawX() > (x + view.getWidth()) || ev.getRawY() < y || ev.getRawY() > (y + view.getHeight())) {
            return false;
        }
        return true;
    }

    public int pointToPosition(float x,float y){
        return getChildAdapterPosition(findChildViewUnder(x,y));
        /*int first=virtualLayoutManager.findFirstVisibleItemPosition();
        int end=virtualLayoutManager.findLastVisibleItemPosition();
        Rect frame=new Rect();
        for (int i=first;i<=end;i++){
            View view=virtualLayoutManager.findViewByPosition(i);
            int top=view.getTop();
            int bottom=view.getBottom();
            if (y>top&&y<bottom){
                return i;
            }
        }
        return -1;*/
    }
}
