package com.d2cmall.buyer.calculator;


import com.d2cmall.buyer.scrollUtils.ItemsPositionGetter;
import com.d2cmall.buyer.scrollUtils.Logger;
import com.d2cmall.buyer.scrollUtils.ScrollDirectionDetector;

/**
 * Name: nice
 * Anthor: hrb
 * Date: 2018/1/31 11:36
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public abstract class BaseItemsVisibilityCalculator implements ListItemsVisibilityCalculator,ScrollDirectionDetector.OnDetectScrollListener {

    private static final boolean SHOW_LOGS = true;
    private static final String TAG = BaseItemsVisibilityCalculator.class.getSimpleName();
    private final ScrollDirectionDetector mScrollDirectionDetector = new ScrollDirectionDetector(this);
    public int firstVisibleItem;
    public int lastVisibleItem;
    public int scrollState;

    public BaseItemsVisibilityCalculator() {
    }

    public void onScroll(ItemsPositionGetter itemsPositionGetter, int firstVisibleItem, int lastVisibleItem, int scrollState) {
        this.firstVisibleItem=firstVisibleItem;
        this.lastVisibleItem=lastVisibleItem;
        this.scrollState=scrollState;
        if (SHOW_LOGS){
            Logger.v(TAG, ">> onScroll");
            Logger.v(TAG, "onScroll, firstVisibleItem " + firstVisibleItem + ", lastVisibleItem " + lastVisibleItem + ", scrollState " + this.scrollStateStr(scrollState));
        }
        this.mScrollDirectionDetector.onDetectedListScroll(itemsPositionGetter, firstVisibleItem);
        switch(scrollState) {
            case 0:
                if (SHOW_LOGS)
                    Logger.v(TAG, "onScroll, SCROLL_STATE_IDLE. ignoring");
                break;
            case 1:
                this.onStateTouchScroll(itemsPositionGetter);
                break;
            case 2:
                this.onStateTouchScroll(itemsPositionGetter);
        }

    }

    protected abstract void onStateFling(ItemsPositionGetter var1);

    protected abstract void onStateTouchScroll(ItemsPositionGetter var1);

    private String scrollStateStr(int scrollState) {
        switch(scrollState) {
            case 0:
                return "SCROLL_STATE_IDLE";
            case 1:
                return "SCROLL_STATE_TOUCH_SCROLL";
            case 2:
                return "SCROLL_STATE_FLING";
            default:
                throw new RuntimeException("wrong data, scrollState " + scrollState);
        }
    }
}
