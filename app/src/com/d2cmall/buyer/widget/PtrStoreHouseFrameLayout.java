package com.d2cmall.buyer.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * D2C风格的下拉刷新
 * Author: Blend
 * Date: 16/4/14 17:23
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class PtrStoreHouseFrameLayout extends PtrHTFrameLayout {

    private D2CFrameLayout ptrHeader;

    public PtrStoreHouseFrameLayout(Context context) {
        super(context);
        initViews(context);
    }

    public PtrStoreHouseFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public PtrStoreHouseFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(context);
    }

    private void initViews(Context context) {
        ptrHeader = new D2CFrameLayout(context);
        setHeaderView(ptrHeader);
        addPtrUIHandler(ptrHeader);
    }

    public D2CFrameLayout getHeader() {
        return ptrHeader;
    }

    public void setHeadLabel(String headLabel) {
        ptrHeader.setLabel(headLabel);
    }
}
