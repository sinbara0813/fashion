package com.d2cmall.buyer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BuyConsultActivity;
import com.d2cmall.buyer.activity.Explore1PublishActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.adapter.TabPagerAdapter;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/26 16:13
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductCommendFragment extends BaseFragment {

    @Bind(R.id.product_show_tv)
    TextView productShowTv;
    @Bind(R.id.product_consult_tv)
    TextView productConsultTv;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.consult_iv)
    ImageView consultIv;
    @Bind(R.id.post_iv)
    ImageView postIv;

    private long id;
    private boolean hasSetAdapter;
    public static String name;
    public static String productPic;

    public static ProductCommendFragment newInstance(long id) {
        ProductCommendFragment webFragment = new ProductCommendFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id", id);
        webFragment.setArguments(bundle);
        return webFragment;
    }

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_commend, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong("id");
        }
    }

    @Override
    public void prepareView() {
        if (Session.getInstance().getUser()!=null&&Session.getInstance().getUser().getPartnerId()>0){
            postIv.setImageResource(R.mipmap.pic_goodsdetail_fbsc);
        }
    }

    public void setPage(int index) {
        viewPager.setCurrentItem(index);
    }

    private void changeTvColor(boolean is) {
        if (is) {
            consultIv.setVisibility(View.VISIBLE);
            productShowTv.setBackgroundResource(R.color.color_black_bg);
            productShowTv.setTextColor(getActivity().getResources().getColor(R.color.color_white));
            productConsultTv.setBackgroundResource(R.drawable.sp_promotion);
            productConsultTv.setTextColor(getActivity().getResources().getColor(R.color.color_black87));
        } else {
            consultIv.setVisibility(View.VISIBLE);
            productShowTv.setBackgroundResource(R.drawable.sp_promotion);
            productShowTv.setTextColor(getActivity().getResources().getColor(R.color.color_black87));
            productConsultTv.setBackgroundResource(R.color.color_black_bg);
            productConsultTv.setTextColor(getActivity().getResources().getColor(R.color.color_white));
        }
    }

    @Override
    public void doBusiness() {
        if (!hasSetAdapter) {
            hasSetAdapter = true;
            //productShowTv.setText("晒单("+ProductFragment.totalCount+")");
            //productConsultTv.setText("购买咨询("+ProductFragment.consultCount+")");
            List<Fragment> fragments = new ArrayList<>();
            fragments.add(ProductShowFragment.newInstance(id));
            //fragments.add(ProductConsultFragment.newInstance(id));
            TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getChildFragmentManager(), fragments, null);
            viewPager.setAdapter(tabPagerAdapter);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    String tabName = "";
                    if (position == 0) {
                        tabName = "晒单";
                    } else {
                        tabName = "购买咨询";
                    }
                    changeTvColor(position == 0);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    @OnClick({R.id.product_show_tv, R.id.product_consult_tv, R.id.consult_iv,R.id.post_iv})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.product_show_tv:
                viewPager.setCurrentItem(0);
                break;
            case R.id.product_consult_tv:
                viewPager.setCurrentItem(1);
                break;
            case R.id.consult_iv:
                Intent intent = new Intent(getActivity(), BuyConsultActivity.class);
                intent.putExtra("id", id);
                if (!Util.isEmpty(name)) {
                    intent.putExtra("name", name);
                }
                if (!Util.isEmpty(productPic)) {
                    intent.putExtra("pic", productPic);
                }
                getActivity().startActivity(intent);
                break;
            case R.id.post_iv:
                if (Util.loginChecked(getActivity(), Constants.Login.EXPLORE_CAMERA_LOGIN)) {
                    Intent intent1 = new Intent(getActivity(), Explore1PublishActivity.class);
                    intent1.putExtra("sn",((ProductDetailActivity)getActivity()).getProductSn());
                    getActivity().startActivity(intent1);
                }
                break;
        }
    }

    @Override
    public void releaseOnInVisible() {
        super.releaseOnInVisible();
    }
}
