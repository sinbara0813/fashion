package com.d2cmall.buyer.scrollUtils;

import android.view.View;

/**
 * Name: nice
 * Anthor: hrb
 * Date: 2018/1/31 10:49
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ScrollDirectionDetector {
    private static final boolean SHOW_LOGS = true;
    public final static int UP=1;
    public final static int DOWN=2;
    private static final String TAG = ScrollDirectionDetector.class.getSimpleName();
    private final ScrollDirectionDetector.OnDetectScrollListener mOnDetectScrollListener;
    private int mOldTop;
    private int mOldFirstVisibleItem;
    private int mOldScrollDirection;

    public ScrollDirectionDetector(ScrollDirectionDetector.OnDetectScrollListener onDetectScrollListener) {
        this.mOnDetectScrollListener = onDetectScrollListener;
    }

    public void onDetectedListScroll(ItemsPositionGetter itemsPositionGetter, int firstVisibleItem) {
        if (SHOW_LOGS)
            Logger.v(TAG, ">> onDetectedListScroll, firstVisibleItem " + firstVisibleItem + ", mOldFirstVisibleItem " + this.mOldFirstVisibleItem);
        View view = itemsPositionGetter.getChildAt(0);
        int top = view == null?0:view.getTop();
        if (SHOW_LOGS)
            Logger.v(TAG, "onDetectedListScroll, view " + view + ", top " + top + ", mOldTop " + this.mOldTop);
        if(firstVisibleItem == this.mOldFirstVisibleItem) {
            if(top > this.mOldTop) {
                this.onScrollUp();
            } else if(top < this.mOldTop) {
                this.onScrollDown();
            }
        } else if(firstVisibleItem < this.mOldFirstVisibleItem) {
            this.onScrollUp();
        } else {
            this.onScrollDown();
        }

        this.mOldTop = top;
        this.mOldFirstVisibleItem = firstVisibleItem;
        if (SHOW_LOGS)
            Logger.v(TAG, "<< onDetectedListScroll");
    }

    private void onScrollDown() {
        if (SHOW_LOGS)
            Logger.v(TAG, "onScroll Down");
        if(this.mOldScrollDirection != DOWN) {
            this.mOldScrollDirection = DOWN;
            this.mOnDetectScrollListener.onScrollDirectionChanged(DOWN);
        } else {
            if (SHOW_LOGS)
                Logger.v(TAG, "onDetectedListScroll, scroll state not changed " + this.mOldScrollDirection);
        }

    }

    private void onScrollUp() {
        if (SHOW_LOGS)
            Logger.v(TAG, "onScroll Up");
        if(this.mOldScrollDirection != UP) {
            this.mOldScrollDirection = UP;
            this.mOnDetectScrollListener.onScrollDirectionChanged(UP);
        } else {
            if (SHOW_LOGS)
                Logger.v(TAG, "onDetectedListScroll, scroll state not changed " + this.mOldScrollDirection);
        }

    }

    public interface OnDetectScrollListener {
        void onScrollDirectionChanged(int scrollDirection);
    }
}
