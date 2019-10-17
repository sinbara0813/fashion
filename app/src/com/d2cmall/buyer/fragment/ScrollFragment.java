package com.d2cmall.buyer.fragment;

import android.view.View;

import com.d2cmall.buyer.widget.HeaderScrollHelper;

/**
 * Created by rookie on 2017/7/14.
 */

public  class ScrollFragment extends RefreshFragment implements HeaderScrollHelper.ScrollableContainer {
    @Override
    public View getScrollableView() {
        return null;
    }

    @Override
    public void refresh(Object... params) {

    }
}
