package com.d2cmall.buyer.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.AddCounselorActivity;
import com.d2cmall.buyer.adapter.TabPagerAdapter;
import com.d2cmall.buyer.base.BaseTabFragment;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Dec: 买手-经营中心
 * Author: hrb
 * Date: 2018/4/13 13:42
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class BusinessCenterFragment extends BaseTabFragment {

    @Bind(R.id.sliding_tab)
    TabLayout slidingTab;
    @Bind(R.id.top_rl)
    RelativeLayout topRl;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.rl_adviser)
    RelativeLayout rlAdviser;

    private List<Fragment> fragments;
    private List<String> titles;
    private TabPagerAdapter tabPagerAdapter;
    private int subPosition;
    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_business_center, container, false);
    }

    @Override
    public void doBusiness() {
        PartnerMemberBean.DataBean.PartnerBean partnerBean = Session.getInstance().getPartnerFromFile(mContext);
        if (partnerBean == null || partnerBean.getStatusX() == -1 || partnerBean.getStatusX() == -9)
            return;
        if(Util.isEmpty(partnerBean.getCounselorId()) || "0".equals(partnerBean.getCounselorId())){//运营顾问
            rlAdviser.setVisibility(View.VISIBLE);
        }
        titles = new ArrayList<>();
        fragments = new ArrayList<>();
        titles.add("概况");
        titles.add("店铺");
        fragments.add(BuyerSurveyFragment.newInstance(partnerBean.getLevel() != 2));
        fragments.add(new BuyerShopFragment());
        if (partnerBean.getLevel() == 0 || partnerBean.getLevel() == 1) {//AM或DM
            titles.add("团队");
            fragments.add(new BuyerTeamFragment());
        }
        viewPager.setOffscreenPageLimit(2);
        tabPagerAdapter = new TabPagerAdapter(getChildFragmentManager(), fragments, titles);
        viewPager.setAdapter(tabPagerAdapter);
        slidingTab.setupWithViewPager(viewPager);
        if(subPosition>=titles.size()){
            subPosition=0;
        }
        if(subPosition>=1){
            viewPager.setCurrentItem(subPosition);
        }

        //初始化tabLayout的选中状态
        for (int i = 0; i < titles.size(); i++) {
            //把tablayout的默认view替换掉
            slidingTab.getTabAt(i).setCustomView(getTabView(i));
            if (i==(subPosition==0?0:subPosition) ){    //如果进来直接选中店铺或者团队
                //初始化默认tab 使他颜色设置成选中状态
                TextView tabViewDefault = (TextView) slidingTab.getTabAt(subPosition==0?0:subPosition).getCustomView().findViewById(R.id.tabText);
                tabViewDefault.setTextColor(Color.parseColor("#DE000000"));
                tabViewDefault.setTextSize(18);
            }else{
                TextView tabViewDefault = (TextView) slidingTab.getTabAt(i).getCustomView().findViewById(R.id.tabText);
                tabViewDefault.setTextColor(Color.parseColor("#61000000"));
                tabViewDefault.setTextSize(14);
            }
        }
        //选中未选中改变字体颜色大小
        slidingTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                final TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tabText);
                textView.setTextColor(Color.parseColor("#DE000000"));
                textView.setTextSize(18);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                final TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tabText);
                textView.setTextColor(Color.parseColor("#61000000"));
                textView.setTextSize(14);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    //这个是自己写的tabview item
    public View getTabView(int position) {
        //获得view
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_tab_view_main, null);
        TextView tv = (TextView) v.findViewById(R.id.tabText);
        tv.setText(titles.get(position));
        return v;
    }
    @OnClick({R.id.back,R.id.rl_adviser})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                getActivity().finish();
                break;
            case R.id.rl_adviser://运营顾问
                mContext.startActivity(new Intent(mContext, AddCounselorActivity.class));
                break;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void setSubPosition(int subPosition) {
        this.subPosition = subPosition;
    }
}
