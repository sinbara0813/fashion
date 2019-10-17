package com.d2cmall.buyer.calculator;


import com.d2cmall.buyer.scrollUtils.ItemsPositionGetter;

/**
 * Name: nice
 * Anthor: hrb
 * Date: 2018/1/31 11:37
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public interface ListItemsVisibilityCalculator {
    void onScrollStateIdle(ItemsPositionGetter var1, int var2, int var3, int var4);

    void onScroll(ItemsPositionGetter var1, int var2, int var3, int var4);
}
