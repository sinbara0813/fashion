package com.d2cmall.buyer.widget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.d2cmall.buyer.fragment.RefreshFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 为ViewPager添加布局（Fragment），绑定和处理fragments和viewpager之间的逻辑关系
 * Author: Blend
 * Date: 16/9/7 11:05
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class PreViewPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {
    private List<RefreshFragment> fragments;
    private FragmentManager fragmentManager;
    private List<String> titles = new ArrayList<>();
    private ViewPager viewPager; // viewPager对象
    private int currentPageIndex = 0; // 当前page索引（切换之前）
    private OnExtraPageChangeListener onExtraPageChangeListener;

    public PreViewPagerAdapter(FragmentManager fragmentManager, ViewPager viewPager,
                               List<RefreshFragment> fragments, List<String> titles) {
        this.fragments = fragments;
        this.fragmentManager = fragmentManager;
        this.viewPager = viewPager;
        this.viewPager.setAdapter(this);
        this.titles = titles;
        this.viewPager.setOnPageChangeListener(this);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //移出viewpager两边之外的page布局
        container.removeView(fragments.get(position).getView());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = fragments.get(position);
        //如果fragment还没有added
        if (!fragment.isAdded()) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(fragment, fragment.getClass().getSimpleName());
            ft.commitAllowingStateLoss();
            /**
             * 在用FragmentTransaction.commit()方法提交FragmentTransaction对象后
             * 会在进程的主线程中，用异步的方式来执行。
             * 如果想要立即执行这个等待中的操作，就要调用这个方法（只能在主线程中调用）。
             * 要注意的是，所有的回调和相关的行为都会在这个调用中被执行完成，因此要仔细确认这个方法的调用位置。
             */
            fragmentManager.executePendingTransactions();
        }
        if (fragment.getView().getParent() == null) {
            container.addView(fragment.getView()); // 为viewpager增加布局
        }
        return fragment.getView();
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void setCurrentPageIndex(int index) {
        this.currentPageIndex = index;
    }

    public OnExtraPageChangeListener getOnExtraPageChangeListener() {
        return onExtraPageChangeListener;
    }

    public void setOnExtraPageChangeListener(OnExtraPageChangeListener onExtraPageChangeListener) {
        this.onExtraPageChangeListener = onExtraPageChangeListener;
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {
        if (null != onExtraPageChangeListener) { //如果设置了额外功能接口
            onExtraPageChangeListener.onExtraPageScrolled(i, v, i2);
        }
    }

    @Override
    public void onPageSelected(int i) {
        fragments.get(currentPageIndex).onPause(); //调用切换前Fargment的onPause()
        if (fragments.get(i).isAdded()) {
            fragments.get(i).onResume(); //调用切换后Fargment的onResume()
        }
        currentPageIndex = i;
        if (null != onExtraPageChangeListener) { //如果设置了额外功能接口
            onExtraPageChangeListener.onExtraPageSelected(i);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
        if (null != onExtraPageChangeListener) { //如果设置了额外功能接口
            onExtraPageChangeListener.onExtraPageScrollStateChanged(i);
        }
    }

    static class OnExtraPageChangeListener {
        public void onExtraPageScrolled(int i, float v, int i2) {
        }

        public void onExtraPageSelected(int i) {
        }

        public void onExtraPageScrollStateChanged(int i) {
        }
    }
}