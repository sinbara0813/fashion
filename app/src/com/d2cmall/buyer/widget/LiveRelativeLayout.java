package com.d2cmall.buyer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.d2cmall.buyer.R;

/**
 * Fixme
 * Author: hrb
 * Date: 2017/01/16 09:42
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class LiveRelativeLayout extends RelativeLayout {

    private Animation inAnimation;
    private Animation outAnimation;
    private boolean canSee = true;
    private static final int TOUCH_STATE_REST = 0;
    private static final int TOUCH_STATE_HORIZONTAL_SCROLLING = 1;
    private static final int TOUCH_STATE_VERTICAL_SCROLLING = -1;
    private float mLastMotionX;
    private float mLastMotionY;
    private int mTouchSlop;
    private int mTouchState = TOUCH_STATE_REST;
    private boolean slideRight;


    public LiveRelativeLayout(Context context) {
        this(context,null);
    }

    public LiveRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
        outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
        inAnimation.setFillAfter(true);
        outAnimation.setFillAfter(true);
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mTouchState = TOUCH_STATE_REST;
                        mLastMotionY = ev.getY();
                        mLastMotionX = ev.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        final float x = ev.getX();
                        final int xDiff = (int) Math.abs(x - mLastMotionX);
                        boolean xMoved = xDiff > mTouchSlop;

                        final float y = ev.getY();
                        final int yDiff = (int) Math.abs(y - mLastMotionY);
                        boolean yMoved = yDiff > mTouchSlop;

                        if (xMoved) {
                            if (xDiff >= yDiff) {
                                mTouchState = TOUCH_STATE_HORIZONTAL_SCROLLING;
                                if (x > mLastMotionX) {
                                    slideRight = true;
                                } else {
                                    slideRight = false;
                                }
                            }
                            mLastMotionX = x;
                        }
                        if (yMoved) {
                            if (yDiff > xDiff) {
                                mTouchState = TOUCH_STATE_VERTICAL_SCROLLING;
                            }
                            mLastMotionY = y;
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        if (mTouchState == TOUCH_STATE_HORIZONTAL_SCROLLING) {
                            if (slideRight&&canSee) {
                                v.startAnimation(outAnimation);
                                canSee = false;
                                if (slideListener!=null){
                                    slideListener.slide(true);
                                }
                            }else {
                                if (!slideRight&&!canSee){
                                    v.startAnimation(inAnimation);
                                    canSee=true;
                                    if (slideListener!=null){
                                        slideListener.slide(false);
                                    }
                                }
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    public SlideListener slideListener;

    public void setSlideListener(SlideListener listener){
        this.slideListener=listener;
    }

    public interface SlideListener{
        void slide(boolean isRight);
    }

}
