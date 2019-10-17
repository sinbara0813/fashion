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
import com.d2cmall.buyer.fragment.FashionLooksFragment;
import com.d2cmall.buyer.fragment.MyWardrobeFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者:Created by sinbara on 2018/11/12.
 * 邮箱:hrb940258169@163.com
 */

public class FashionMatchTabLayout extends LinearLayout {

    /*    @Bind(R.id.fashion_report_iv)
        ImageView fashionReportIv;*/
    @Bind(R.id.fashion_looks_iv)
    ImageView fashionLooksIv;
    @Bind(R.id.wardrobe_iv)
    ImageView wardrobeIv;
    //private BaseTabFragment reportFragment;
    private BaseTabFragment mWardRobeFragment;
    private BaseTabFragment looksFragment;

    private int lastShowIndex;
    private BaseTabFragment lastShowFragment;
    private FragmentManager fragmentManager;
    private String city;

    public FashionMatchTabLayout(Context context) {
        super(context);
    }

    public FashionMatchTabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.layout_fashion_tab_layout, this);
        ButterKnife.bind(this, this);
        if (context instanceof FragmentActivity) {
            fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        } else {
            throw new NullPointerException("context must be FragmentActivity");
        }
    }

    @OnClick({R.id.wardrobe_iv, R.id.fashion_looks_iv})
    public void onClick(View view) {
        int index = 2;
        switch (view.getId()) {
            case R.id.wardrobe_iv:
                index = 2;
                break;
            case R.id.fashion_looks_iv:
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
            case 1:
                /*fashionReportIv.setSelected(true);
                if (reportFragment == null) {
                    reportFragment = new FashionReportFragment();
                    ft.add(R.id.fragment_container, reportFragment);
                } else {
                    ft.show(reportFragment);
                }
                lastShowFragment = reportFragment;*/
                break;
            case 2:
                wardrobeIv.setSelected(true);
                if (mWardRobeFragment == null) {
                    mWardRobeFragment = new MyWardrobeFragment();
                    ft.add(R.id.fragment_container, mWardRobeFragment);
                } else {
                    ft.show(mWardRobeFragment);
                }
                lastShowFragment = mWardRobeFragment;
                break;
            case 3:
                fashionLooksIv.setSelected(true);
                if (looksFragment == null) {
                    looksFragment = new FashionLooksFragment();
                    ((FashionLooksFragment)looksFragment).loadWeather(city);
                    ft.add(R.id.fragment_container, looksFragment);
                } else {
                    ft.show(looksFragment);
                }
                lastShowFragment = looksFragment;
                break;
        }
        lastShowFragment.show();
        ft.commitAllowingStateLoss();
        lastShowIndex = index;
    }

    public void loadWeather(String city){
        this.city=city;
        if (looksFragment!=null){
            ((FashionLooksFragment)looksFragment).loadWeather(city);
        }
    }

    private void setUnSelect() {
        //fashionReportIv.setSelected(false);
        wardrobeIv.setSelected(false);
        fashionLooksIv.setSelected(false);
    }
}
