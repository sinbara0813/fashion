package com.d2cmall.buyer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.HorizontalScrollView;
import android.widget.Scroller;

import com.d2cmall.buyer.util.ScreenUtil;

/**
 * 反弹滑动
 * Anthor: hrb
 * Date: 2018/9/13 15:00
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */
public class ReboundScrollView extends HorizontalScrollView {
    private View inner;
    private boolean isLeft; //左滑
    private boolean isRight; //右滑
    private float lastX;
    private float lastY;
    private boolean isHandler;
    private Scroller scroller;
    private int mTouchSlop;

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            inner = getChildAt(0);
        }
        super.onFinishInflate();
    }

    public ReboundScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller=new Scroller(context);
        final ViewConfiguration vc = ViewConfiguration.get(context);
        mTouchSlop = vc.getScaledTouchSlop();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            inner.scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX=ev.getX();
                lastY=ev.getY();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (inner == null) {
            return super.onTouchEvent(ev);
        } else {
            int deltaX=0;
            switch (ev.getAction()){
                case MotionEvent.ACTION_MOVE:
                    deltaX=(int)(lastX-ev.getX());
                    if (Math.abs(lastX-ev.getX())>1.5*Math.abs(lastY-ev.getY())&&Math.abs(deltaX)>mTouchSlop){
                        if (deltaX>0){
                            isRight=false;
                            isLeft=true;
                        }else {
                            isRight=true;
                            isLeft=false;
                        }
                    }
                    lastX=ev.getX();
                    lastY=ev.getY();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if (isHandler){
                        int scrollX=inner.getScrollX();
                        scroller.startScroll(scrollX,0,-scrollX,0,300);
                        //inner.scrollTo(0,0);
                        invalidate();
                    }
                    break;
            }
            if ((isRight&&getScrollX()==0)||(isLeft&&getScrollX()==inner.getWidth()-getWidth()+ ScreenUtil.dip2px(16))){
                isHandler=true;
                inner.scrollBy(deltaX/3,0);
            }else {
                isHandler=false;
            }
        }

        return super.onTouchEvent(ev);
    }
}
