package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.CollectAdapter;
import com.d2cmall.buyer.adapter.TabPagerAdapter;
import com.d2cmall.buyer.api.MyCollectProductBean;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.PartnerInfoBean;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.bean.ShareTagBean;
import com.d2cmall.buyer.fragment.BuyerSaleFragment;
import com.d2cmall.buyer.fragment.PartnerCashFragment;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/12.
 * Description : PartnerCashActivity
 */

public class PartnerCashActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.sliding_tab)
    SlidingTabLayout slidingTab;
    @Bind(R.id.top_ll)
    RelativeLayout topLl;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    private List<Fragment> fragments;
    private List<String> titles;
    private TabPagerAdapter tabPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_report);
        ButterKnife.bind(this);
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        titles.add("全部");
        titles.add("申请中");
        titles.add("已完成");
        titles.add("已关闭");
        fragments.add(PartnerCashFragment.newInstance(0));//全部
        fragments.add(PartnerCashFragment.newInstance(1));//申请中
        fragments.add(PartnerCashFragment.newInstance(8));//已完成
        fragments.add(PartnerCashFragment.newInstance(-1));//已关闭
        initView();
    }


    private void initView() {
        nameTv.setText("提现明细");
        tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(tabPagerAdapter);
        slidingTab.setViewPager(viewPager);
        tabPagerAdapter.notifyDataSetChanged();
    }


    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}
