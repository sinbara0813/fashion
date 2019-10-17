package com.d2cmall.buyer.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.CartActivity;
import com.d2cmall.buyer.activity.HomeActivity;
import com.d2cmall.buyer.activity.Search1Activity;
import com.d2cmall.buyer.base.BaseTabFragment;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.NoSlideViewPager;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.tendcloud.tenddata.TCAgent;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import q.rorbin.badgeview.QBadgeView;

/**
 * Name: D2CNEW
 * Anthor: hrb
 * Date: 2017/6/5 15:30
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ShoppingFragment extends BaseTabFragment {

    @Bind(R.id.tv_search)
    TextView tvSearch;
    @Bind(R.id.search_layout)
    LinearLayout searchLayout;
    @Bind(R.id.img_scan)
    ImageView imgScan;
    @Bind(R.id.action_layout)
    RelativeLayout actionLayout;
    @Bind(R.id.sliding_tab)
    SlidingTabLayout slidingTab;
    @Bind(R.id.viewpager)
    NoSlideViewPager viewpager;
    @Bind(R.id.tab_rl)
    RelativeLayout tabRl;
    private String[] titles;
    private TabPagerAdapter tabPagerAdapter;
    private ShoppingCommodityFragment shoppingCommodityFragment;
    private BrandDetailFragment brandDetailFragment;
    private QBadgeView cartNum;

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shopping, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void doBusiness() {
        setCartNum();
        tvSearch.setText(D2CApplication.mSharePref.getSharePrefString("defaultHint", "请输入商品名称"));
        shoppingCommodityFragment = new ShoppingCommodityFragment();
        brandDetailFragment = new BrandDetailFragment();
        titles = getResources().getStringArray(R.array.label_category_tabs);
        tabPagerAdapter = new TabPagerAdapter(getChildFragmentManager());
        viewpager.setAdapter(tabPagerAdapter);
        slidingTab.setViewPager(viewpager);
        slidingTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
//                if(position==0){
//                    start("分类_商品","分类_商品");
//                }else {
//                    start("分类_品牌","分类_品牌");
//                }
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

    }

    private void stat(String event,String label){
        StatService.onEvent(getActivity(),event,label);
        TCAgent.onEvent(getActivity(),event,label);
    }

    @OnClick({R.id.tv_search, R.id.search_layout, R.id.img_scan})
    public void onViewClicked(View view) {
        if (!isVisibleToUser){
            return;
        }
        Intent intent;
        int id = D2CApplication.mSharePref.getSharePrefInteger("defaultId", -1);
        String hint =D2CApplication.mSharePref.getSharePrefString("defaultHint", "请输入商品名称");
        switch (view.getId()) {
            case R.id.tv_search:
                intent = new Intent(getActivity(), Search1Activity.class);
                intent.putExtra("id", id);
                intent.putExtra("name",hint);
                getActivity().startActivity(intent);
                break;
            case R.id.search_layout:
                stat("V3搜索","分类搜索");
                intent = new Intent(getActivity(), Search1Activity.class);
                intent.putExtra("id", id);
                intent.putExtra("name",hint);
                getActivity().startActivity(intent);
                break;
            case R.id.img_scan:
                if (Util.loginChecked(getActivity(), 100)) {
                    stat("V3购物车入口","分类购物车");
                    intent = new Intent(getActivity(), CartActivity.class);
                    getActivity().startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void show() {
        if (viewpager != null) {
            if (viewpager.getCurrentItem() == 0) {
                if (shoppingCommodityFragment != null) {
                    shoppingCommodityFragment.setUserVisibleHint(true);
                }
            } else {
                if (brandDetailFragment != null) {
                    brandDetailFragment.setUserVisibleHint(true);
                }
            }
        }
        super.show();
    }

    private class TabPagerAdapter extends FragmentPagerAdapter {

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return shoppingCommodityFragment;
            } else {
                return brandDetailFragment;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    @Subscribe
    public void onEvent(ActionBean bean) {
        if (bean.type == Constants.ActionType.REFRESH_CART_COUNT) {
            setCartNum();
        }
    }

    private void setCartNum() {
        if (HomeActivity.count>0){
            if (cartNum==null){
                cartNum = (QBadgeView) new QBadgeView(getActivity()).bindTarget(imgScan).setBadgeTextSize(10, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(2, 2, true);
                imgScan.setId(R.id.img_scan);
            }
            if (HomeActivity.count > 9) {
                cartNum.setBadgeText("9+");
            } else {
                cartNum.setBadgeNumber(HomeActivity.count);
            }
        }else {
            if (cartNum !=null){
                ViewParent parent= cartNum.getParent();
                if (parent!=null){
                    ((ViewGroup) parent).removeView(cartNum);
                    cartNum =null;
                }
            }
        }
    }

    @Override
    public void releaseOnInVisible() {
        if (viewpager != null) {
            if (viewpager.getCurrentItem() == 0) {
                if (shoppingCommodityFragment != null) {
                    shoppingCommodityFragment.setUserVisibleHint(false);
                }
            } else {
                if (brandDetailFragment != null) {
                    brandDetailFragment.setUserVisibleHint(false);
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
