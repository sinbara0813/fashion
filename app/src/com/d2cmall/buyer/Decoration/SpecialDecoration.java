package com.d2cmall.buyer.Decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.d2cmall.buyer.util.ScreenUtil;


/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/13 10:59
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class SpecialDecoration extends RecyclerView.ItemDecoration {
    int space;

    public SpecialDecoration(){
        space= ScreenUtil.dip2px(24);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left=0;
        outRect.right=0;
        outRect.bottom=0;
        outRect.top = space;
    }
}
