package com.d2cmall.buyer.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.ScreenUtil;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/21 17:21
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainTitleBehavior extends CoordinatorLayout.Behavior {

    public MainTitleBehavior(){};

    public MainTitleBehavior(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return isDependOn(dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        changeAlpha(parent, child, dependency);
        return false;
    }

    private void changeAlpha(CoordinatorLayout parent, View child, View dependency) {
        if (dependency.getTranslationY()==0){
            child.setAlpha(0);
        }else if (dependency.getTranslationY()==-getHeadOffer()){
            child.setAlpha(1);
        }else {
            child.setAlpha(Math.abs(dependency.getTranslationY()*7/5)/getHeadOffer());
        }
    }

    public int getHeadOffer(){
        return ScreenUtil.dip2px(56);
    }

    private boolean isDependOn(View dependency) {
        return dependency != null && dependency.getId() == R.id.search_ll;
    }
}
