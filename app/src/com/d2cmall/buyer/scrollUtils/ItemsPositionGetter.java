package com.d2cmall.buyer.scrollUtils;

import android.view.View;

/**
 * Name: nice
 * Anthor: hrb
 * Date: 2018/1/31 10:51
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public interface ItemsPositionGetter {

    View getChildAt(int var1);

    int indexOfChild(View var1);

    int getChildCount();

    int getLastVisiblePosition();

    int getFirstVisiblePosition();

}
