package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.TabPagerAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.ShareTagBean;
import com.d2cmall.buyer.fragment.ProductReportFragment;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品报告列表
 * Author: LWJ
 * Date: 17/9/6 16:55
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class ProductReportActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView mBackIv;
    @Bind(R.id.name_tv)
    TextView mNameTv;
    @Bind(R.id.title_right)
    TextView mTitleRight;
    @Bind(R.id.title_layout)
    RelativeLayout mTitleLayout;
    @Bind(R.id.sliding_tab)
    SlidingTabLayout mSlidingTab;
    @Bind(R.id.top_ll)
    RelativeLayout mTopLl;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;
    @Bind(R.id.tag)
    View mTag;
    private List<Fragment> fragments;
    private List<String> titles;
    private TabPagerAdapter tabPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_report);
        ButterKnife.bind(this);
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        titles.add("待审核");
        titles.add("审核不通过");
        titles.add("审核通过");
        titles.add("已取消");
        fragments.add(ProductReportFragment.newInstance(1));//待审核
        fragments.add(ProductReportFragment.newInstance(-1));//审核不通过
        fragments.add(ProductReportFragment.newInstance(2));//审核通过
        fragments.add(ProductReportFragment.newInstance(0));//已取消
        initView();
    }

    private void initView() {
        mNameTv.setText("商品报告");
        if (Util.isLowThanAndroid5()){
            mTag.setVisibility(View.VISIBLE);
        }
        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(tabPagerAdapter);
        mSlidingTab.setViewPager(mViewPager);
        tabPagerAdapter.notifyDataSetChanged();
        mSlidingTab.setViewPager(mViewPager);
    }
    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}
