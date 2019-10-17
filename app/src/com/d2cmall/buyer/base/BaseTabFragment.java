package com.d2cmall.buyer.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.d2cmall.buyer.util.Util;

import butterknife.ButterKnife;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/13 16:39
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public abstract class BaseTabFragment extends Fragment {

    public View rootView;
    public boolean isVisibleToUser;
    public boolean isCreated;
    public Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView==null){
            rootView = getRootView(inflater,container,savedInstanceState);
        }
        ButterKnife.bind(this, rootView);
        isCreated=true;
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        mContext=context;
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext=null;
    }

    public abstract View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        doBusiness();
    }

    /**
     * 当前用户可见
     */
    public void show(){
        isVisibleToUser=true;
    }

    /**
     * 当前用户不可见
     */
    public void hide(){
        isVisibleToUser=false;
        if (isCreated){
            releaseOnInVisible();
        }
    }

    public abstract void doBusiness();

    public void releaseOnInVisible(){};

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        if (rootView instanceof ViewGroup){
            ((ViewGroup)rootView).removeAllViews();
        }
        ViewParent parent=rootView.getParent();
        if (parent!=null){
            ((ViewGroup) parent).removeView(rootView);
        }
        rootView=null;
        super.onDestroyView();
    }
}
