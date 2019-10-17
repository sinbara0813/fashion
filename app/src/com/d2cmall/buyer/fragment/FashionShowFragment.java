package com.d2cmall.buyer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.TabPagerAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.ShareTagBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.FashionTabPop;
import com.flyco.tablayout.SlidingTabLayout;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/16 13:56
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FashionShowFragment extends BaseFragment {

    @Bind(R.id.image_open)
    ImageView imageOpen;
    @Bind(R.id.sliding_tab)
    SlidingTabLayout slidingTab;
    @Bind(R.id.tv_make_choice)
    TextView tvMakeChoice;
    @Bind(R.id.top_ll)
    RelativeLayout topLl;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.tag)
    View tag;

    private List<Fragment> fragments ;
    private List<String> titles ;
    private TabPagerAdapter tabPagerAdapter;

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fashion_show, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
    }

    @Override
    public void prepareView() {
        if (Util.isLowThanAndroid5()){
            tag.setVisibility(View.VISIBLE);
        }
        tabPagerAdapter = new TabPagerAdapter(getChildFragmentManager(), fragments, titles);
        viewPager.setAdapter(tabPagerAdapter);
        slidingTab.setViewPager(viewPager);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(titles!=null&&titles.size()>0 ) {
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void doBusiness() {
        lazyLoad();
    }

    private void lazyLoad() {
        if (titles.size() == 0) {
            loadData();
        }else {
            if (fragments!=null&&viewPager!=null&&fragments.size()>viewPager.getCurrentItem()){
                fragments.get(viewPager.getCurrentItem()).setUserVisibleHint(true);
            }
        }
    }

    private void loadData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.SHARE_TAG_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ShareTagBean>() {
            @Override
            public void onResponse(ShareTagBean showTagBean) {
                if (!isVisibleToUser){
                    return;
                }
                if (showTagBean != null && showTagBean.getData().getTagList() != null && showTagBean.getData().getTagList().size() > 0) {
                    titles.clear();
                    fragments.clear();
                    int size = showTagBean.getData().getTagList().size();
                    for (int i = 0; i < size; i++) {
                        if (i==1){
                            titles.add("关注");
                            fragments.add(new FashionFocusFragment());
                        }
                        titles.add(showTagBean.getData().getTagList().get(i).getName());
                        if (showTagBean.getData().getTagList().get(i).getId() == 1) {
                            fragments.add(FashionSubFragment.newInstance(showTagBean.getData().getTagList().get(i).getId(), true,false));
                        } else {
                            fragments.add(FashionSubFragment.newInstance(showTagBean.getData().getTagList().get(i).getId(), false,false));
                        }
                    }
                    if (fragments.size()==1){
                        titles.add("直播");
                        fragments.add(new FashionLiveFragment());
                    }else {
                        titles.add(2,"直播");
                        fragments.add(2,new FashionLiveFragment());
                    }
                    tabPagerAdapter.notifyDataSetChanged();
                    if (Session.getInstance().getUser()==null){
                        viewPager.setCurrentItem(1);
                    }
                    slidingTab.setViewPager(viewPager);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    @OnClick({R.id.image_open})
    public void click(View view){
        switch (view.getId()){
            case R.id.image_open:
                FashionTabPop pop = new FashionTabPop(getContext(),slidingTab, imageOpen,tvMakeChoice,88);
                pop.setData(titles,slidingTab.getCurrentTab());
                pop.makeBackImage();
                pop.setHeight(88);
                imageOpen.clearAnimation();
                imageOpen.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.arrow_up));
                slidingTab.setVisibility(View.GONE);
                tvMakeChoice.setVisibility(View.VISIBLE);
                pop.show(topLl);
                break;
        }
    }

    @Override
    public void releaseOnInVisible() {
        if (fragments!=null&&viewPager!=null&&fragments.size()>viewPager.getCurrentItem()){
            fragments.get(viewPager.getCurrentItem()).setUserVisibleHint(false);
        }
    }
}
