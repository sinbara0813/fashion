package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.fragment.RankListFragment;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.nicevideo.NiceVideoPlayerManager;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/8/10.
 * 排行榜页面
 */

public class RankActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    LinearLayout titleLayout;
    @Bind(R.id.rank_sliding)
    SlidingTabLayout rankSliding;
    @Bind(R.id.rank_viewpager)
    ViewPager rankViewpager;
    private List<RankListFragment> fragmentList=new ArrayList<>();
    private String [] strings={"达人","设计师","图文","视频","直播"};
    private int position=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_layout);
        ButterKnife.bind(this);
        position=getIntent().getIntExtra("position",0);
        doBusiness();
    }

    public void doBusiness() {
        initTitle();
        titleRight.setText(R.string.label_refund_explain);
        initFragment();
        rankViewpager.setAdapter(new TabAdapter(getSupportFragmentManager()));
        rankSliding.setViewPager(rankViewpager);
        rankViewpager.setOffscreenPageLimit(5);
        rankViewpager.setCurrentItem(position);
    }

    private void initTitle() {
        nameTv.setText("排行榜");
    }

    private void initFragment() {
        fragmentList.add(RankListFragment.newInstance(0));
        fragmentList.add(RankListFragment.newInstance(1));
        fragmentList.add(RankListFragment.newInstance(2));
        fragmentList.add(RankListFragment.newInstance(3));
        fragmentList.add(RankListFragment.newInstance(4));
    }


    @OnClick({R.id.back_iv, R.id.title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.title_right:
                Util.urlAction(this,"/page/rankdeti",999);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }

    class TabAdapter extends FragmentPagerAdapter {

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return strings.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return strings[position];
        }
    }
}
