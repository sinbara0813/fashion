package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.TabPagerAdapter;
import com.d2cmall.buyer.api.StarTagApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.StarTagBean;
import com.d2cmall.buyer.fragment.StarStyleFragment;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.widget.FashionTabPop;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: LWJ
 * desc:    明星风范的二级界面
 * Date: 2017/09/14 15:45
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class StarStyleActivity extends BaseActivity {
    @Bind(R.id.back_iv)
    ImageView mBackIv;
    @Bind(R.id.name_tv)
    TextView mNameTv;
    @Bind(R.id.title_layout)
    RelativeLayout mTitleLayout;
    @Bind(R.id.image_open)
    ImageView mImageOpen;
    @Bind(R.id.sliding_tab)
    SlidingTabLayout mSlidingTab;
    @Bind(R.id.tv_make_choice)
    TextView mTvMakeChoice;
    @Bind(R.id.top_ll)
    RelativeLayout mTopLl;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    private List<Fragment> fragments ;
    private List<String> titles ;
    private TabPagerAdapter tabPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_style);
        ButterKnife.bind(this);
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        prepareView();
        doBusiness();
    }

    public void prepareView() {
        mNameTv.setText("明星风范");
        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(tabPagerAdapter);
    }

    public void doBusiness() {
        lazyLoad();
    }

    private void lazyLoad() {
        if (titles.size() == 0) {
            loadData();
        }
    }
    private void loadData() {
        StarTagApi api = new StarTagApi();
        api.setType("STAR");
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<StarTagBean>() {
            @Override
            public void onResponse(StarTagBean starTagBean) {
                if(isFinishing()) {
                    return;
                }
                if (starTagBean != null && starTagBean.getData().getTags() != null && starTagBean.getData().getTags().size() > 0) {
                    titles.clear();
                    fragments.clear();
                    int size = starTagBean.getData().getTags().size();
                    for (int i = 0; i < size; i++) {
                        titles.add(starTagBean.getData().getTags().get(i).getName());
                            fragments.add(StarStyleFragment.newInstance(starTagBean.getData().getTags().get(i).getId(), false,false));
                    }
                    tabPagerAdapter.notifyDataSetChanged();
                    mSlidingTab.setViewPager(mViewPager);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }


    @OnClick({R.id.back_iv, R.id.image_open})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.image_open:
                FashionTabPop pop = new FashionTabPop(this,mSlidingTab, mImageOpen,mTvMakeChoice,0);
                pop.setData(titles,mSlidingTab.getCurrentTab());
                pop.makeBackImage();
                mImageOpen.clearAnimation();
                mImageOpen.startAnimation(AnimationUtils.loadAnimation(this, R.anim.arrow_up));
                mSlidingTab.setVisibility(View.GONE);
                mTvMakeChoice.setVisibility(View.VISIBLE);
                pop.show(mTopLl,false);
                break;
        }
    }
}
