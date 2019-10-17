package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.TabPagerAdapter;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.fragment.FashionUnFocusChildFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/8 11:32
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class UnFollowActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.big_gun)
    TextView bigGun;
    @Bind(R.id.design_gun)
    TextView designGun;
    @Bind(R.id.top)
    LinearLayout top;
    @Bind(R.id.tag)
    TextView tag;
    @Bind(R.id.top_rl)
    RelativeLayout topRl;
    @Bind(R.id.view_pager)
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unfollow);
        ButterKnife.bind(this);
        doBusiness();
    }

    private void doBusiness() {
        List<Fragment> unFocusChildFragments=new ArrayList<>();
        FashionUnFocusChildFragment fragment1=FashionUnFocusChildFragment.newInstance("member");
        FashionUnFocusChildFragment fragment2=FashionUnFocusChildFragment.newInstance("designer");
        unFocusChildFragments.add(fragment1);
        unFocusChildFragments.add(fragment2);
        setTagWidth(true);
        TabPagerAdapter tabPagerAdapter=new TabPagerAdapter(getSupportFragmentManager(),unFocusChildFragments,null);
        viewPager.setAdapter(tabPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    bigGun.setTextColor(getResources().getColor(R.color.color_black87));
                    designGun.setTextColor(getResources().getColor(R.color.color_black60));
                    setTagWidth(true);
                }else {
                    bigGun.setTextColor(getResources().getColor(R.color.color_black60));
                    designGun.setTextColor(getResources().getColor(R.color.color_black87));
                    setTagWidth(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setTagWidth(boolean is){
        int left;
        int right;
        if (is){
            left=bigGun.getLeft();
            right=bigGun.getRight();
        }else {
            left=designGun.getLeft();
            right=designGun.getRight();
        }
        RelativeLayout.LayoutParams rl= (RelativeLayout.LayoutParams) tag.getLayoutParams();
        rl.width=right-left;
        rl.setMargins(left,0,0,0);
        tag.setLayoutParams(rl);
    }

    @OnClick({R.id.big_gun, R.id.design_gun,R.id.back_iv})
    public void click(View view){
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.big_gun:
                viewPager.setCurrentItem(0);
                break;
            case R.id.design_gun:
                viewPager.setCurrentItem(1);
                break;
        }
    }
}
