package com.d2cmall.buyer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.TabPagerAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.MyFollowBean;
import com.d2cmall.buyer.bean.ShareBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Name: 时尚圈关注页
 * Anthor: hrb
 * Date: 2017/8/16 13:34
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FashionUnFocusFragment extends BaseFragment {

    @Bind(R.id.big_gun)
    TextView bigGun;
    @Bind(R.id.design_gun)
    TextView designGun;
    @Bind(R.id.tag)
    View tag;
    @Bind(R.id.top_rl)
    RelativeLayout topRl;
    @Bind(R.id.top)
    LinearLayout top;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.tag_line)
    View tagLine;
    @Bind(R.id.fragment_container)
    FrameLayout frameLayout;

    private List<Fragment> unFocusChildFragments;
    private boolean hasFocus = true;
    private MyFollowBean myFollowBean;
    //private MyTabPagerAdapter tabPagerAdapter;

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_unfocus, container, false);
    }

    @Override
    public void prepareView() {
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        //unFocusChildFragments = new ArrayList<>();
    }

    private void setTagWidth(boolean is) {
        tag.setVisibility(View.VISIBLE);
        int left;
        int right;
        if (is) {
            left = bigGun.getLeft();
            right = bigGun.getRight();
        } else {
            left = designGun.getLeft();
            right = designGun.getRight();
        }
        RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) tag.getLayoutParams();
        rl.width = right - left;
        rl.setMargins(left, 0, 0, 0);
        tag.setLayoutParams(rl);
    }

    @Subscribe
    public void onEvent(GlobalTypeBean bean) {
        if (bean.getType() == Constants.GlobalType.LOGOUT || bean.getType() == Constants.GlobalType.LOGIN_OK) {//登出操作
            hasFocus = true;
            myFollowBean = null;
            if (unFocusChildFragments != null) {
                unFocusChildFragments.clear();
            }
            viewPager.setVisibility(View.GONE);
            doBusiness();
        }
    }

    private void initViewPager(final boolean hasFocus, List<ShareBean.DataEntity.MemberSharesEntity.ListEntity> list) {
        unFocusChildFragments = new ArrayList<>();
        if (hasFocus) {
            unFocusChildFragments.add(MyFollowFragment.newInstance(list));
            topRl.setVisibility(View.GONE);
            if (getParentFragment() instanceof FashionFragment) {
                ((FashionFragment) getParentFragment()).setElevation();
            }
        } else {
            if (Util.isLowThanAndroid5()) {
                tagLine.setVisibility(View.VISIBLE);
            }
            topRl.setVisibility(View.VISIBLE);
            FashionUnFocusChildFragment fragment1 = FashionUnFocusChildFragment.newInstance("member");
            FashionUnFocusChildFragment fragment2 = FashionUnFocusChildFragment.newInstance("designer");
            unFocusChildFragments.add(fragment1);
            unFocusChildFragments.add(fragment2);
        }
        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getChildFragmentManager(), unFocusChildFragments, null);
        viewPager.setAdapter(tabPagerAdapter);
        //tabPagerAdapter.notifyDataSetChanged();
        viewPager.setVisibility(View.VISIBLE);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (hasFocus) {
                } else {
                    if (position == 0) {
                        bigGun.setTextColor(getResources().getColor(R.color.color_black87));
                        designGun.setTextColor(getResources().getColor(R.color.color_black60));
                        setTagWidth(true);
                    } else {
                        bigGun.setTextColor(getResources().getColor(R.color.color_black60));
                        designGun.setTextColor(getResources().getColor(R.color.color_black87));
                        setTagWidth(false);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                setTagWidth(true);
            }
        }, 250);
    }

    @Override
    public void doBusiness() {
        if (hasFocus && myFollowBean == null) {
            loadMyFollow();
        } else {
            /*if (unFocusChildFragments!=null&&unFocusChildFragments.size()==1){
                if (Session.getInstance().getUserFromFile(getActivity())==null){
                    unFocusChildFragments.get(0).setUserVisibleHint(false);
                    unFocusChildFragments.clear();
                    myFollowBean=null;
                    hasFocus=false;
                    initViewPager(hasFocus,null);
                }else {
                    unFocusChildFragments.get(0).setUserVisibleHint(true);
                }
            }else if (unFocusChildFragments!=null&&unFocusChildFragments.size()==2){
                if (Session.getInstance().getUserFromFile(getActivity())!=null&&myFollowBean==null){
                    loadMyFollow();
                }
            }*/
            if (unFocusChildFragments != null && viewPager != null && unFocusChildFragments.size() > viewPager.getCurrentItem()) {
                unFocusChildFragments.get(viewPager.getCurrentItem()).setUserVisibleHint(true);
            }
        }
    }

    private void loadMyFollow() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.GET_MY_FOLLOW_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MyFollowBean>() {
            @Override
            public void onResponse(MyFollowBean response) {
                myFollowBean = response;
                if (response.getData().getMemberShares().getList().size() > 0) {
                    hasFocus = true;
                    initViewPager(hasFocus, response.getData().getMemberShares().getList());
                } else {
                    hasFocus = false;
                    initViewPager(hasFocus, null);
                }
            }
        });
    }


    @OnClick({R.id.big_gun, R.id.design_gun})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.big_gun:
                viewPager.setCurrentItem(0);
                break;
            case R.id.design_gun:
                viewPager.setCurrentItem(1);
                break;
        }
    }

//    public class MyTabPagerAdapter extends FragmentPagerAdapter {
//
//        public List<Fragment> fragments;
//        public List<String> titles;
//        private FragmentManager fm;
//
//        public MyTabPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
//            super(fm);
//            this.fm = fm;
//            this.fragments = fragments;
//            this.titles = titles;
//        }
//
//        public void setFragments(List<Fragment> fragments) {
//            if (this.fragments != null) {
//                FragmentTransaction ft = fm.beginTransaction();
//                for (Fragment f : this.fragments) {
//                    ft.remove(f);
//                }
//                ft.commit();
//                ft = null;
//                fm.executePendingTransactions();
//            }
//            this.fragments = fragments;
//            notifyDataSetChanged();
//        }
//
//        @Override
//        public int getItemPosition(Object object) {
//            return POSITION_NONE;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return fragments.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return fragments.size();
//        }
//
//    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void releaseOnInVisible() {
        if (unFocusChildFragments != null && viewPager != null && unFocusChildFragments.size() > viewPager.getCurrentItem()) {
            unFocusChildFragments.get(viewPager.getCurrentItem()).setUserVisibleHint(false);
        }
    }
}
