package com.d2cmall.buyer.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.OverScroller;

import com.d2cmall.buyer.AppProfile;
import com.d2cmall.buyer.BuildConfig;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.widget.ExpandViewPager;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2018/09/06 15:00
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class UpdateTabBehavior extends HeaderScrollingViewBehavior{

    private static final String TAG = "UpdateTabBehavior";

    public UpdateTabBehavior(){};

    public UpdateTabBehavior(Context context, AttributeSet attributeSet){
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
        float offsetRange = dependency.getTop() + getFinalHeight() - child.getTop();
        int headerOffsetRange = getHeaderOffsetRange();
        if (dependency.getTranslationY() == headerOffsetRange) {
            child.setTranslationY(offsetRange);
        } else if (dependency.getTranslationY() == 0) {
            child.setTranslationY(0);
        } else {
            child.setTranslationY((int) (dependency.getTranslationY() / (getHeaderOffsetRange() * 1.0f) * offsetRange));
        }
    }

    @Override
    protected View findFirstDependency(List<View> views) {
        for (View view:views) {
            if (isDependOn(view))
                return view;
        }
        return null;
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
