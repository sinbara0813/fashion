package com.d2cmall.buyer.scrollUtils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.VirtualLayoutManager;

/**
 * Name: nice
 * Anthor: hrb
 * Date: 2018/1/31 11:08
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class RecyclerViewItemsPositionGetter implements ItemsPositionGetter {
    private static final boolean SHOW_LOGS = true;
    private static final String TAG = RecyclerViewItemsPositionGetter.class.getSimpleName();
    private VirtualLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;

    public RecyclerViewItemsPositionGetter(VirtualLayoutManager layoutManager, RecyclerView recyclerView) {
        this.mLayoutManager = layoutManager;
        this.mRecyclerView = recyclerView;
    }

    @Override
    public View getChildAt(int position) {
        if (SHOW_LOGS){
            Logger.v(TAG, "getChildAt, mRecyclerView.getChildCount " + this.mRecyclerView.getChildCount());
            Logger.v(TAG, "getChildAt, mLayoutManager.getChildCount " + this.mLayoutManager.getChildCount());
        }
        View view = this.mLayoutManager.getChildAt(position);
        if (SHOW_LOGS){
            Logger.v(TAG, "mRecyclerView getChildAt, position " + position + ", view " + view);
            Logger.v(TAG, "mLayoutManager getChildAt, position " + position + ", view " + this.mLayoutManager.getChildAt(position));
        }
        return view;
    }

    @Override
    public int indexOfChild(View view) {
        int indexOfChild = this.mRecyclerView.indexOfChild(view);
        if (SHOW_LOGS)
            Logger.v(TAG, "indexOfChild, " + indexOfChild);
        return indexOfChild;
    }

    @Override
    public int getChildCount() {
        int childCount = this.mRecyclerView.getChildCount();
        if (SHOW_LOGS){
            Logger.v(TAG, "getChildCount, mRecyclerView " + childCount);
            Logger.v(TAG, "getChildCount, mLayoutManager " + this.mLayoutManager.getChildCount());
        }
        return childCount;
    }

    @Override
    public int getLastVisiblePosition() {
        return this.mLayoutManager.findLastVisibleItemPosition();
    }

    @Override
    public int getFirstVisiblePosition() {
        if (SHOW_LOGS)
            Logger.v(TAG, "getFirstVisiblePosition, findFirstVisibleItemPosition " + this.mLayoutManager.findFirstVisibleItemPosition());
        return this.mLayoutManager.findFirstVisibleItemPosition();
    }
}
