package com.d2cmall.buyer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.TabPagerAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.MainTagBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.ExpandListener;
import com.d2cmall.buyer.listener.UpListener;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.MyViewPager;
import com.flyco.tablayout.SlidingTabLayout;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/21 15:27
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainWomenFragment extends BaseFragment implements ExpandListener,UpListener {

    @Bind(R.id.sliding_tab)
    SlidingTabLayout slidingTab;
    @Bind(R.id.tag)
    View tag;
    @Bind(R.id.view_pager)
    MyViewPager viewPager;
    private int id; //请求tag标签的id;
    private String name;
    private List<Fragment> fragments;
    private List<String> titles;
    private TabPagerAdapter tabPagerAdapter;
    private boolean isExpand=true;
    private String statName;

    public static MainWomenFragment newInstance(int id, String name) {
        MainWomenFragment mainWomenFragment = new MainWomenFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("name", name);
        mainWomenFragment.setArguments(bundle);
        return mainWomenFragment;
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_women, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt("id");
            name = getArguments().getString("name");
        }
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
    }

    @Override
    public void prepareView() {
        if (Util.isLowThanAndroid5()){
            tag.setVisibility(View.VISIBLE);
        }
        tabPagerAdapter = new TabPagerAdapter(getChildFragmentManager(), fragments, titles);
        viewPager.setNestedpParent((ViewGroup) viewPager.getParent());
        viewPager.setAdapter(tabPagerAdapter);
        slidingTab.setViewPager(viewPager);
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
        api.setInterPath(String.format(Constants.GET_MAIN_SUB_TAG, "" + id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MainTagBean>() {
            @Override
            public void onResponse(MainTagBean showTagBean) {
                if (showTagBean != null && showTagBean.getData().getSubModules() != null && showTagBean.getData().getSubModules().size() > 0) {
                    titles.clear();
                    fragments.clear();
                    int size = showTagBean.getData().getSubModules().size();
                    for (int i = 0; i < size; i++) {
                        titles.add(showTagBean.getData().getSubModules().get(i).getTitle());
                        if (i == 0 && ("女士".equals(name)||"男士".equals(name))) {
                            fragments.add(MainManFragment.newInstance(showTagBean.getData().getSubModules().get(i).getId(), true, true, true, name).setStatName(statName+"_"+showTagBean.getData().getSubModules().get(i).getTitle()));
                        } else {
                            if (!Util.isEmpty(showTagBean.getData().getSubModules().get(i).getWebUrl())){
                                fragments.add(WebFragment.newInstance(showTagBean.getData().getSubModules().get(i).getWebUrl()));
                            }else {
                                fragments.add(MainManFragment.newInstance(showTagBean.getData().getSubModules().get(i).getId(), false, false, i == 0, name).setStatName(statName+"_"+showTagBean.getData().getSubModules().get(i).getTitle()));
                            }
                        }
                    }
                    tabPagerAdapter.notifyDataSetChanged();
                    slidingTab.setViewPager(viewPager);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    @Override
    public boolean isExpand() {
        if (tabPagerAdapter.getItem(viewPager.getCurrentItem()) instanceof ExpandListener) {
            return ((ExpandListener) tabPagerAdapter.getItem(viewPager.getCurrentItem())).isExpand();
        }
        return false;
    }

    @Override
    public void canRefresh(boolean is) {
        isExpand=is;
        if (tabPagerAdapter.getCount() > 0 && tabPagerAdapter.getItem(viewPager.getCurrentItem()) instanceof ExpandListener) {
            ((ExpandListener) tabPagerAdapter.getItem(viewPager.getCurrentItem())).canRefresh(is);
        }
    }

    @Override
    public void toUp() {
        Fragment fragment= fragments.get(viewPager.getCurrentItem());
        if (fragment instanceof UpListener){
            ((UpListener)fragment).toUp();
        }
    }

    @Override
    public void releaseOnInVisible() {
        if (fragments!=null&&viewPager!=null&&fragments.size()>viewPager.getCurrentItem()){
            fragments.get(viewPager.getCurrentItem()).setUserVisibleHint(false);
        }
    }

    public MainWomenFragment setStatName(String statName) {
        this.statName = statName;
        return this;
    }
}
