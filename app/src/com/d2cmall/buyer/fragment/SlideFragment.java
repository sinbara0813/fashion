package com.d2cmall.buyer.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.widget.slide.SlideBottom;
import com.d2cmall.buyer.widget.slide.SlideTop;
import com.d2cmall.buyer.widget.slide.SlideView;

import butterknife.Bind;

/**
 * Dec: D2CNEW
 * Author: hrb
 * Date: 2018/5/17 13:16
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class SlideFragment extends BaseFragment {

    @Bind(R.id.top_page)
    SlideTop topPage;
    @Bind(R.id.bottom_page)
    SlideBottom bottomPage;
    @Bind(R.id.slide_view)
    SlideView slideView;

    private long id;
    private int collageId;//拼团id

    public static SlideFragment newInstance(long id,int collageId){
        SlideFragment slideFragment=new SlideFragment();
        Bundle bundle=new Bundle();
        bundle.putLong("id",id);
        bundle.putInt("collageId",collageId);
        slideFragment.setArguments(bundle);
        return slideFragment;
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_slide, container, false);
    }

    @Override
    public void prepareView() {
        if (getArguments()!=null){
            id=getArguments().getLong("id");
            collageId=getArguments().getInt("collageId");
        }
        topPage.init(this,id,collageId);
        bottomPage.init(this,id);
        slideView.setPageChangeListener(new SlideView.PageChangeListener() {
            @Override
            public void pageChange(int nextPage) {
                if (nextPage == 0) {
                    scrollTop();
                } else if (nextPage == 1) {
                    scrollBottom();
                }
            }
        });
    }

    @Override
    public void doBusiness() {
    }

    private void scrollTop() {
        bottomPage.changeState(false);
    }

    private void scrollBottom() {
        bottomPage.changeState(true);
    }

    @Override
    public void onDestroyView() {
        topPage.recycle();
        super.onDestroyView();
    }
}
