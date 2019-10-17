package com.d2cmall.buyer.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.TabPagerAdapter;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.BrandKindBean;
import com.d2cmall.buyer.fragment.BrandListFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/7/29.
 * 品牌列表页面
 */

public class BrandListActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.img_search)
    ImageView imgSearch;
    @Bind(R.id.brand_list_tabLayout)
    SlidingTabLayout brandListTabLayout;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<>();
    int position = 0;
    private List<BrandKindBean.DataBean.DatasBean> list;
    private int type = -1;
    private int tagId = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_list);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            type = getIntent().getIntExtra("type", -1);
            if (type == 1) {
                position = getIntent().getIntExtra("position", 0);
                list = (List<BrandKindBean.DataBean.DatasBean>) getIntent().getSerializableExtra("tab");
                initData();
            } else if (type == 0) {
                tagId = getIntent().getIntExtra("id", -1);
                loadData();
            }
        }
    }

    private void loadData() {
        //从链接点进来，没有tablayout，只有一个列表
        brandListTabLayout.setVisibility(View.GONE);
        List<String> TabName = new ArrayList<>();
        TabName.add("");
        fragmentList.add(BrandListFragment.newInstance("", tagId));
        TabPagerAdapter tabPagerAdapter=new TabPagerAdapter(getSupportFragmentManager(),fragmentList,TabName);
        viewPager.setAdapter(tabPagerAdapter);
        brandListTabLayout.setViewPager(viewPager);
        viewPager.setCurrentItem(position);
    }

    private void initData() {
        //将传过来的数据取出id，并将id传入列表Fragment中,从而拉取数据
        List<String> TabName = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            TabName.add(list.get(i).getName());
            fragmentList.add(BrandListFragment.newInstance(list.get(i).getType(), list.get(i).getId()));
        }
        TabPagerAdapter tabPagerAdapter=new TabPagerAdapter(getSupportFragmentManager(),fragmentList,TabName);
        viewPager.setAdapter(tabPagerAdapter);
        brandListTabLayout.setViewPager(viewPager);
        viewPager.setCurrentItem(position);
    }


    @OnClick({R.id.back_iv, R.id.img_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.img_search:
                Intent intent = new Intent(this, SearchTopicBrandActivity.class);
                intent.putExtra("type", "brand");
                startActivity(intent);
                break;
        }
    }

}
