package com.d2cmall.buyer.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.fragment.GroupMarketFragment;
import com.d2cmall.buyer.fragment.MineGroupFragment;
import com.d2cmall.buyer.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2018/6/21.
 */

public class GroupBuyActivity extends BaseActivity {

    @Bind(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @Bind(R.id.tv_group_market)
    TextView tvGroupMarket;
    @Bind(R.id.tv_mine_group)
    TextView tvMineGroup;

    private GroupMarketFragment groupMarketFragment;
    private MineGroupFragment mineGroupFragment;
    private FragmentManager fragmentManager;
    private int currentIndex = 0;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_buy_list);
        ButterKnife.bind(this);
        initFragment();
        position = getIntent().getIntExtra("position", 0);
        if (position == 1) {
            changeFragment(1);
        }
    }

    private void initFragment() {
        groupMarketFragment = new GroupMarketFragment();
        mineGroupFragment = new MineGroupFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (!groupMarketFragment.isAdded()) {
            ft.add(R.id.fragment_container, groupMarketFragment);
        }
        if (!mineGroupFragment.isAdded()) {
            ft.add(R.id.fragment_container, mineGroupFragment);
        }
        ft.hide(mineGroupFragment);
        ft.show(groupMarketFragment);
        ft.commit();
    }

    private void changeFragment(int index) {
        //传入index判断切换哪个fragment
        if (currentIndex == index) {
            return;
        }
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if (index == 0) {
            tvGroupMarket.setTextColor(Color.parseColor("#FFF23365"));
            tvMineGroup.setTextColor(getResources().getColor(R.color.trans_87_color_black));
            ft.hide(mineGroupFragment);
            ft.show(groupMarketFragment);
        } else {
            tvGroupMarket.setTextColor(getResources().getColor(R.color.trans_87_color_black));
            tvMineGroup.setTextColor(Color.parseColor("#FFF23365"));
            ft.hide(groupMarketFragment);
            ft.show(mineGroupFragment);
        }
        currentIndex = index;
        ft.commit();
    }

    @OnClick({R.id.tv_group_market, R.id.tv_mine_group})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_mine_group:
                if (Util.loginChecked(GroupBuyActivity.this, 999)) {
                    changeFragment(1);
                }
                break;
            case R.id.tv_group_market:
                changeFragment(0);
                break;
        }
    }

}
