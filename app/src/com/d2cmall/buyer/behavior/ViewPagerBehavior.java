package com.d2cmall.buyer.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.widget.ExpandViewPager;

import java.util.List;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/21 16:40
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ViewPagerBehavior extends HeaderScrollingViewBehavior {

    private static final String TAG = "ViewPagerBehavior";

    public ViewPagerBehavior(){};

    public ViewPagerBehavior(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    @Override
    public View findFirstDependency(List<View> views) {
        for (View view : views) {
            if (isHeadView(view))
                return view;
        }
        return null;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return isHeadView(dependency);
    }

    @Override
    protected int getScrollRange(View v) {
        if (isHeadView(v)) {
            return v.getMeasuredHeight()-getTitleOffer();
        } else {
            return super.getScrollRange(v);
        }
    }

    private int getTitleOffer(){
        return ScreenUtil.dip2px(56);
    }

    public boolean isHeadView(View view){
        return view!=null&&view.getId()== R.id.search_ll;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        child.setTranslationY(dependency.getTranslationY());
        if (child instanceof ExpandViewPager){
            if (dependency.getTranslationY()==0){
                ((ExpandViewPager)child).canRefresh(true);
            }else {
                ((ExpandViewPager)child).canRefresh(false);
            }
        }

        return super.onDependentViewChanged(parent, child, dependency);
    }

}
