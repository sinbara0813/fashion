package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.UpTopBean;
import com.d2cmall.buyer.util.ScreenUtil;
import com.flyco.tablayout.SlidingTabLayout;
import com.tendcloud.tenddata.TCAgent;

import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/12/27 11:28
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CustomTabAdapter extends TabPagerAdapter implements SlidingTabLayout.CustomTabProvider {

    private List<UpTopBean.DataBean.TopCateArrayBean> cateList;
    private Context context;

    public CustomTabAdapter(Context context,FragmentManager fm, List<Fragment> fragments, List<UpTopBean.DataBean.TopCateArrayBean> cateList) {
        super(fm,fragments,null);
        this.cateList=cateList;
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public View getCustomTabView(ViewGroup parent, int position) {
        View tabView=LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_brand_update_tab,new LinearLayout(parent.getContext()),false);
        TextView name= tabView.findViewById(R.id.name);
        TextView updateTv= tabView.findViewById(R.id.update_num);
        if (position==0){
            name.setTextColor(context.getResources().getColor(R.color.color_orange));
            updateTv.setTextColor(context.getResources().getColor(R.color.color_white));
            updateTv.setBackgroundResource(R.drawable.update_sp_round9_orange);
            tabView.setPadding(ScreenUtil.dip2px(16),0,ScreenUtil.dip2px(16),0);
        }
        name.setText(cateList.get(position).getName());
        updateTv.setText("上新"+cateList.get(position).getCount()+"件");
        return tabView;
    }

    @Override
    public void tabSelect(View tab) {
        TextView name= tab.findViewById(R.id.name);
        TextView updateTv= tab.findViewById(R.id.update_num);
        name.setTextColor(context.getResources().getColor(R.color.color_orange));
        updateTv.setTextColor(context.getResources().getColor(R.color.color_white));
        updateTv.setBackgroundResource(R.drawable.update_sp_round9_orange);
    }

    @Override
    public void tabUnselect(View tab) {
        TextView name= tab.findViewById(R.id.name);
        TextView updateTv= tab.findViewById(R.id.update_num);
        name.setTextColor(context.getResources().getColor(R.color.color_black85));
        updateTv.setTextColor(context.getResources().getColor(R.color.color_black85));
        updateTv.setBackgroundResource(R.drawable.update_sp_round9_gray);
    }
}
