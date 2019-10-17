package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.fragment.BrandListFragment;
import com.d2cmall.buyer.fragment.ProductListFragment;
import com.d2cmall.buyer.fragment.TopicFragment;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/8/18.
 */

public class SearchResultActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.et_account)
    ClearEditText etAccount;
    @Bind(R.id.iv_reset)
    ImageView ivReset;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.sliding_tab)
    SlidingTabLayout slidingTab;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.drawer)
    DrawerLayout drawer;
    private String[] titles = {"商品", "设计师/品牌", "专题"};
    private List<BaseFragment> fragments = new ArrayList<>();
    private String keyword;
    private ProductListFragment productListFragment;
    private BrandListFragment brandListFragment;
    private TopicFragment topicFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        doBusiness();
    }


    public void doBusiness() {
        initFragments();
        etAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!Util.isEmpty(etAccount.getText().toString())) {
                    ivReset.setVisibility(View.VISIBLE);
                } else {
                    ivReset.setVisibility(View.GONE);
                }
            }
        });
        viewPager.setAdapter(new TabAdapter(getSupportFragmentManager()));
        slidingTab.setViewPager(viewPager);
    }

    private void initFragments() {
        productListFragment= ProductListFragment.newInstance(keyword);
        brandListFragment= BrandListFragment.newInstance(keyword);
        topicFragment=TopicFragment.newInstance(keyword);
        fragments.add(productListFragment);
        fragments.add(brandListFragment);
        fragments.add(topicFragment);
    }

    public void showScreen() {
        drawer.openDrawer(GravityCompat.END);
    }


    @OnClick({R.id.back_iv, R.id.iv_reset, R.id.title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.iv_reset:
                etAccount.setText("");
                ivReset.setVisibility(View.GONE);
                break;
            case R.id.title_right:
                break;
        }
    }


    class TabAdapter extends FragmentPagerAdapter {

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
