package com.d2cmall.buyer.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import butterknife.ButterKnife;

/**
 * Name: D2CNEW
 * Anthor: hrb
 * Date: 2017/7/3 15:28
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public abstract class BaseFragment extends Fragment {

    public Context mContext;
    public View rootView;
    public boolean isCreated;
    public boolean isPrepare;
    public boolean isVisibleToUser;
    public RequestManager requestManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = getRootView(inflater,container,savedInstanceState);
        ButterKnife.bind(this, rootView);
        isCreated = true;
        return rootView;
    }

    public abstract View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prepareView();
        isPrepare=true;
        if (isVisibleToUser){
            doBusiness();
        }
    }

    public RequestManager getRequestManager(){
        if (requestManager==null){
            requestManager=Glide.with(this);
        }
        return requestManager;
    }

    @Override
    public void onLowMemory() {
        if (requestManager!=null){
            requestManager.onLowMemory();
        }
        super.onLowMemory();
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

    public void onVisible() {
        if (!isCreated || !isVisibleToUser)
            return;
        if (!isPrepare){
            prepareView();
            isPrepare=true;
        }
        doBusiness();
    }

    /**
     * 预处理view 对象初始化,为滚动view设置适配器等
     */
    public abstract void prepareView();

    public void onInVisible() {
        if (!isCreated)
            return;
        releaseOnInVisible();
    }

    /**
     * 处理业务 加载数据等
     */
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
        isCreated=false;
        isPrepare=false;
        super.onDestroyView();
    }
}
