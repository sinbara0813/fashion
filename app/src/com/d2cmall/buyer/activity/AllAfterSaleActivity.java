package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.fragment.ExchangeFragment;
import com.d2cmall.buyer.fragment.Refund1Fragment;
import com.d2cmall.buyer.fragment.Reship1Fragment;
import com.d2cmall.buyer.util.Util;
import com.flyco.tablayout.SlidingTabLayout;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/9/9.
 * 所有售后页面,包括换货，退款退货，退款
 */

public class AllAfterSaleActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    ImageView titleRight;
    @Bind(R.id.title_layout)
    LinearLayout titleLayout;
    @Bind(R.id.sliding_tab)
    SlidingTabLayout slidingTab;
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private String[] titles;
    private ArrayList<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_sale_list);
        ButterKnife.bind(this);
        int position = getIntent().getIntExtra("position", 0);
        titles = getResources().getStringArray(R.array.label_after_sale_tabs);
        titleRight.setBackgroundResource(R.mipmap.tab_all_service);
        fragmentList = new ArrayList<>();
        fragmentList.add(Reship1Fragment.newInstance());//退款退货申请
        fragmentList.add(ExchangeFragment.newInstance());//换货申请
        fragmentList.add(Refund1Fragment.newInstance());//退款申请
        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager(),fragmentList,titles);
        pager.setAdapter(adapter);
        slidingTab.setViewPager(pager);
        pager.setCurrentItem(position);
    }

    @OnClick({R.id.back_iv, R.id.title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.title_right://跳转客服
                String title = "线上客服";
                String url = "http://www.d2cmall.com";
                ConsultSource source = new ConsultSource(url, title, "售后");
                source.groupId = Constants.QIYU_AF_GROUP_ID;
                source.robotFirst = true;
                Unicorn.openServiceActivity(this, "D2C客服", source);
                //合力亿捷
//                Intent intent = new Intent(this,CustomServiceActivity.class);
//                intent.putExtra("skillGroupId", Constants.HLYJ_BF_AF_GROUP_ID);
//                startActivity(intent);
                break;
        }
    }

    private static class TabPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;
        private String[] titles;

        public TabPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titles) {
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
    protected void onResume() {
        Util.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        Util.onPause(this);
        super.onPause();
    }
}
