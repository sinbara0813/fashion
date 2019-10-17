package com.d2cmall.buyer.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseTabFragment;
import com.d2cmall.buyer.fragment.BusinessCenterFragment;
import com.d2cmall.buyer.fragment.PartnerCenterFragment;
import com.d2cmall.buyer.fragment.SaleDetailFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Dec: 买手-底部导航栏
 * Author: hrb
 * Date: 2018/4/13 13:30
 * Copyright (c) 2018 d2cmall. All rights reserved.
 */

public class PartnerTabLayout extends LinearLayout {

    @Bind(R.id.business_center_iv)
    ImageView businessCenterIv;
    @Bind(R.id.sales_detail_iv)
    ImageView salesDetailIv;
    @Bind(R.id.partner_center_iv)
    ImageView partnerCenterIv;

    private BaseTabFragment businessCenterFragment;
    private BaseTabFragment saleDetailFragment  ;
    private BaseTabFragment partnerCenterFragment;

    private int lastShowIndex;
    private BaseTabFragment lastShowFragment;
    private int subPosition;//直接跳转到经营中心的店铺或团队Fragment
    private FragmentManager fragmentManager;
    public void setSubPosition(int subPosition) {//url跳转到具体界面,比如经营中心的团队
        this.subPosition=subPosition;
    }

    public PartnerTabLayout(Context context) {
        super(context);
    }

    public PartnerTabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.layout_partner_tab_layout, this);
        ButterKnife.bind(this, this);
        if (context instanceof FragmentActivity) {
            fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        } else {
            throw new NullPointerException("context must be FragmentActivity");
        }
    }

    @OnClick({R.id.business_center_iv, R.id.sales_detail_iv, R.id.partner_center_iv})
    public void onClick(View view) {
        int index = 1;
        switch (view.getId()) {
            case R.id.business_center_iv:
                index = 1;
                break;
            case R.id.sales_detail_iv:
                index = 2;
                break;
            case R.id.partner_center_iv:
                index = 3;
                break;
        }
        switchFragment(index);
    }

    public void init(int... position) {
        switchFragment(position);
    }

    private void switchFragment(int... position) {
        if (position.length == 0) {
            return;
        }
        int index = position[0];
        if (index == lastShowIndex) {
            return;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (lastShowFragment != null) {
            lastShowFragment.hide();
            ft.hide(lastShowFragment);
        }
        setUnSelect();
        switch (index) {
            case 1://经营中心
                businessCenterIv.setSelected(true);
                if (businessCenterFragment == null) {
                    businessCenterFragment = new BusinessCenterFragment();
                    ((BusinessCenterFragment)businessCenterFragment).setSubPosition(subPosition);
                    ft.add(R.id.fragment_container, businessCenterFragment);
                } else {
                    ft.show(businessCenterFragment);
                }
                lastShowFragment = businessCenterFragment;
                break;
            case 2://售货明细
                salesDetailIv.setSelected(true);
                if (saleDetailFragment == null) {
                    saleDetailFragment = new SaleDetailFragment();
                    ft.add(R.id.fragment_container, saleDetailFragment);
                } else {
                    ft.show(saleDetailFragment);
                }
                lastShowFragment = saleDetailFragment;
                break;
            case 3://买手中心
                partnerCenterIv.setSelected(true);
                if (partnerCenterFragment == null) {
                    partnerCenterFragment = new PartnerCenterFragment();
                    ft.add(R.id.fragment_container, partnerCenterFragment);
                } else {
                    ft.show(partnerCenterFragment);
                }
                lastShowFragment = partnerCenterFragment;
                break;
        }
        lastShowFragment.show();
        ft.commitAllowingStateLoss();
        lastShowIndex = index;
    }

    private void setUnSelect(){
        businessCenterIv.setSelected(false);
        salesDetailIv.setSelected(false);
        partnerCenterIv.setSelected(false);
    }
}
