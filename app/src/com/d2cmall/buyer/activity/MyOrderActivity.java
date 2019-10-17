package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.TaskPointBean;
import com.d2cmall.buyer.fragment.Order1Fragment;
import com.d2cmall.buyer.fragment.OtherOrderFragment;
import com.d2cmall.buyer.http.BeanRequest;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/8/18.
 * 我的订单页面
 */

public class MyOrderActivity extends BaseActivity {


    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.img_search)
    ImageView imgSearch;
    @Bind(R.id.sliding_tab)
    SlidingTabLayout slidingTab;
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private List<Fragment> fragmentList;
    private String[] titles = {"全部", "待付款", "待发货", "待收货", "待评价"};
    public static Integer point=0;//评价晒单的奖励D币数


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        ButterKnife.bind(this);
        nameTv.setText("我的订单");
        fragmentList = new ArrayList<>();
        int position = getIntent().getIntExtra("position", 0);
        fragmentList = new ArrayList<>();
        fragmentList.add(Order1Fragment.newInstance(0));//所有
        fragmentList.add(Order1Fragment.newInstance(1));//待付款
//        fragmentList.add(OtherOrder1Fragment.newInstance(2));//待发货
//        fragmentList.add(OtherOrder1Fragment.newInstance(3));//待收货
//        fragmentList.add(OtherOrder1Fragment.newInstance(4));//待评价
        fragmentList.add(OtherOrderFragment.newInstance(2));//待发货
        fragmentList.add(OtherOrderFragment.newInstance(3));//待收货
        fragmentList.add(OtherOrderFragment.newInstance(4));//待评价
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), fragmentList, titles);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(5);
        slidingTab.setViewPager(pager);
        pager.setCurrentItem(position);
        loadPoint();
    }

    private void loadPoint() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.COMMENT_ORDER_POINT,"PRODUCT_COMMENT"));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<TaskPointBean>() {
            @Override
            public void onResponse(TaskPointBean taskPointBean) {
                if(taskPointBean!=null && taskPointBean.getList()!=null && taskPointBean.getList().get(0)!=null){
                    point=taskPointBean.getList().get(0).getPoint();
                }

            }
        });
    }

    @OnClick({R.id.back_iv, R.id.img_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.img_search:
                Intent intent = new Intent(this, OrderSearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void refreshList(int index) {
        //fragmentList.get(index).refresh(0);
    }


    public static class TabAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;
        private String[] titles;

        public TabAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titles) {
            super(fm);
            this.fragmentList = fragmentList;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(point!=null){
            point=null;
        }
    }
}
