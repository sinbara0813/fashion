package com.d2cmall.buyer.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.d2cmall.buyer.AppProfile;
import com.d2cmall.buyer.BuildConfig;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.ScreenUtil;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2018/09/06 14:55
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class UpdateContentBehavior extends HeaderScrollingViewBehavior {

    private static final String TAG = "UpdateContentBehavior";

    public UpdateContentBehavior(){};

    public UpdateContentBehavior(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return isDependOn(dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        offsetChildAsNeeded(parent, child, dependency);
        return false;
    }

    private void offsetChildAsNeeded(CoordinatorLayout parent, View child, View dependency) {
        child.setTranslationY((int) (-dependency.getTranslationY() / (getHeaderOffsetRange() * 1.0f) * getScrollRange(dependency)));
    }

    @Override
    protected View findFirstDependency(List<View> views) {
        for (View view:views) {
            if (isDependOn(view))
                return view;
        }
        return null;
    }

    @Override
    protected int getScrollRange(View v) {
        if (isDependOn(v)) {
            return Math.max(0, v.getMeasuredHeight() - getFinalHeight());
        } else {
            return super.getScrollRange(v);
        }
    }

    private int getHeaderOffsetRange() {
        return AppProfile.getContext().getResources().getDimensionPixelOffset(R.dimen.update_header_offset);
    }

    private int getFinalHeight() {
        return AppProfile.getContext().getResources().getDimensionPixelOffset(R.dimen.update_header_title_height);
    }

    private boolean isDependOn(View dependency) {
        return dependency != null && dependency.getId() == R.id.update_header;
    }
}
