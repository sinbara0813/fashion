package com.d2cmall.buyer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.WardrobeListActivity;
import com.d2cmall.buyer.base.BaseTabFragment;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.widget.RoundedImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者:Created by sinbara on 2018/11/13.
 * 邮箱:hrb940258169@163.com
 * 我的衣橱
 */

public class MyWardrobeFragment extends BaseTabFragment {

    @Bind(R.id.iv_back_first)
    ImageView ivBackFirst;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    @Bind(R.id.iv_clothing)
    RoundedImageView ivClothing;
    @Bind(R.id.iv_shose)
    RoundedImageView ivShose;
    @Bind(R.id.iv_bag)
    RoundedImageView ivBag;
    @Bind(R.id.iv_jewelry)
    RoundedImageView ivJewelry;
    @Bind(R.id.iv_beauty)
    RoundedImageView ivBeauty;
    @Bind(R.id.top_ll)
    LinearLayout topLl;
    @Bind(R.id.bottom_ll)
    LinearLayout bottomLl;

    @Override
    public View getRootView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_wardrob, container, false);
    }

    @Override
    public void doBusiness() {
        int height=ScreenUtil.getDisplayHeight()-ScreenUtil.getStatusBarHeight(mContext)-ScreenUtil.dip2px(128);
        LinearLayout.LayoutParams topLL= (LinearLayout.LayoutParams) topLl.getLayoutParams();
        topLL.height=3*height/5;
        LinearLayout.LayoutParams bottomLL= (LinearLayout.LayoutParams) bottomLl.getLayoutParams();
        bottomLL.height=2*height/5;
        boolean is=ScreenUtil.isAllScreenDevice(mContext);
        String url=is?"/2018/12/01/home_pic_clothing_l.png":"/2018/12/01/home_pic_clothing.png";
        UniversalImageLoader.displayRoundedCornerImage(mContext,url,ivClothing,ScreenUtil.dip2px(4),0,0);
        url=is?"/2018/12/01/home_pic_bag_l.png":"/2018/12/01/home_pic_bag.png";
        UniversalImageLoader.displayRoundedCornerImage(mContext,url,ivBag,ScreenUtil.dip2px(4),0,0);
        url=is?"/2018/12/01/home_pic_jewelry_l.png":"/2018/12/01/home_pic_jewelry.png";
        UniversalImageLoader.displayRoundedCornerImage(mContext,url,ivJewelry,ScreenUtil.dip2px(4),0,0);
        url=is?"/2018/12/01/home_pic_shose_l.png":"/2018/12/01/home_pic_shose.png";
        UniversalImageLoader.displayRoundedCornerImage(mContext,url,ivShose,ScreenUtil.dip2px(4),0,0);
        url=is?"/2018/12/01/home_pic_beauty_l.png":"/2018/12/01/home_pic_beauty.png";
        UniversalImageLoader.displayRoundedCornerImage(mContext,url,ivBeauty,ScreenUtil.dip2px(4),0,0);
    }

    @OnClick({R.id.iv_back_first, R.id.iv_clothing, R.id.iv_shose, R.id.iv_bag, R.id.iv_jewelry, R.id.iv_beauty})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_first:
                getActivity().finish();
                break;
            case R.id.iv_clothing:
                mContext.startActivity(new Intent(mContext, WardrobeListActivity.class).putExtra("type", "上装"));
                break;
            case R.id.iv_shose:
                mContext.startActivity(new Intent(mContext, WardrobeListActivity.class).putExtra("type", "鞋履"));
                break;
            case R.id.iv_bag:
                mContext.startActivity(new Intent(mContext, WardrobeListActivity.class).putExtra("type", "包包"));
                break;
            case R.id.iv_jewelry:
                mContext.startActivity(new Intent(mContext, WardrobeListActivity.class).putExtra("type", "配饰"));
                break;
            case R.id.iv_beauty:
                mContext.startActivity(new Intent(mContext, WardrobeListActivity.class).putExtra("type", "美妆"));
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
