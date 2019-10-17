package com.d2cmall.buyer.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.d2cmall.buyer.AppProfile;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.ScreenUtil;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2018/09/06 15:00
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class UpdateTitleBehavior extends CoordinatorLayout.Behavior {

    public UpdateTitleBehavior(){};

    public UpdateTitleBehavior(Context context, AttributeSet attributeSet){
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
        child.setAlpha(dependency.getTranslationY()*1.0f/getHeadOffer());
    }

    public int getHeadOffer(){
        return AppProfile.getContext().getResources().getDimensionPixelOffset(R.dimen.update_header_offset);
    }

    private boolean isDependOn(View dependency) {
        return dependency != null && dependency.getId() == R.id.update_header;
    }
}
