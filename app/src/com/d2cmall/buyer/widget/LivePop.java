package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;

import com.d2cmall.buyer.R;

/**
 * LivePop
 * Author: hrb
 * Date: 2016/12/14 15:37
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class LivePop {

    private TransparentPop mPop;
    private Animation inAnimation;
    private Animation outAnimation;
    private boolean openFinishBack = true;
    private boolean canSee = true;
    private static final int TOUCH_STATE_REST = 0;
    private static final int TOUCH_STATE_HORIZONTAL_SCROLLING = 1;
    private static final int TOUCH_STATE_VERTICAL_SCROLLING = -1;
    private float mLastMotionX;
    private float mLastMotionY;
    private int mTouchSlop;
    private int mTouchState = TOUCH_STATE_REST;
    private boolean slideRight;

    public LivePop(Context context, TransparentPop.InvokeListener listener) {
        mPop = new TransparentPop(context, listener);
        mPop.setBackGroundResource(R.color.transparent);
        mPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (liveBack != null && openFinishBack) {
                    liveBack.back();
                }
            }
        });
        mPop.setBackgroundDrawable(new BitmapDrawable());
        inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
        outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
        outAnimation.setFillAfter(true);
        mPop.setInAnimation(inAnimation);
        //mPop.setOutAnimation(outAnimation);
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mPop.getParent().setOnTouchListener(new View.OnTouchListener() {
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
                            if (slideRight && canSee) {
                                openFinishBack = false;
                                v.startAnimation(outAnimation);
                                canSee = false;
                            } else if (!slideRight && !canSee) {
                                openFinishBack = false;
                                v.startAnimation(inAnimation);
                                canSee = true;
                            } else {
                                openFinishBack = true;
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void show(View parent) {
        mPop.show(parent, false);
    }

    public void dismiss() {
        if (mPop != null) {
            mPop.dismiss(false);
        }
    }

    private LiveBack liveBack;

    public interface LiveBack {
        void back();
    }

    public void setBack(LiveBack back) {
        this.liveBack = back;
    }

    public View getPop(){
        return mPop.getParent();
    }

}
