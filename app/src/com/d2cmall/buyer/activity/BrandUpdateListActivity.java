package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.Switch;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.BrandFocusAdapter;
import com.d2cmall.buyer.adapter.TabPagerAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.BrandUpdateListBean;
import com.d2cmall.buyer.bean.UpdateBrandCategoryBean;
import com.d2cmall.buyer.binder.ScrollEndBinder;
import com.d2cmall.buyer.fragment.BrandUpdateListFragment;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.TitleUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import q.rorbin.badgeview.QBadgeView;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2018/1/3 15:17
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class BrandUpdateListActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.cart_iv)
    ImageView cartIv;
    @Bind(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;

    private TabPagerAdapter tabPagerAdapter;
    private QBadgeView cartNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_list_update);
        ButterKnife.bind(this);
        setCartNum();
        loadData();
    }

    private void setCartNum() {
        if (HomeActivity.count>0){
            if (cartNum==null){
                cartNum = (QBadgeView) new QBadgeView(this).bindTarget(cartIv).setBadgeTextSize(8, true).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeBackgroundColor(Color.parseColor("#fff23365")).setGravityOffset(3,3,true);
            }
            if (HomeActivity.count > 9) {
                cartNum.setBadgeText("9+");
            } else {
                cartNum.setBadgeNumber(HomeActivity.count);
            }
        }
    }

    private void loadData() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.UPDATE_BRAND_CATEGORY);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<UpdateBrandCategoryBean>() {
            @Override
            public void onResponse(UpdateBrandCategoryBean response) {
                if (isFinishing()){
                    return;
                }
                if (response.getData().getTopCateArray()!=null&&response.getData().getTopCateArray().size()>0){
                    List<String> titles=new ArrayList<>();
                    List<Fragment> fragments=new ArrayList<>();
                    int size=response.getData().getTopCateArray().size();
                    for (int i=0;i<size;i++){
                        fragments.add(BrandUpdateListFragment.newInstance(response.getData().getTopCateArray().get(i).getId()));
                        titles.add(response.getData().getTopCateArray().get(i).getName());
                    }
                    tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), fragments, titles);
                    viewPager.setAdapter(tabPagerAdapter);
                    tabLayout.setViewPager(viewPager);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (isFinishing()) {
                    return;
                }
                Util.showToast(BrandUpdateListActivity.this,Util.checkErrorType(error));
            }
        });
    }

    @OnClick({R.id.back_iv,R.id.cart_iv})
    public void click(View view){
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.cart_iv:
                startActivity(new Intent(this,CartActivity.class));
                break;
        }
    }

}
