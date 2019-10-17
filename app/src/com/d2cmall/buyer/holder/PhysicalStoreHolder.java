package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/8/23.
 */

public class PhysicalStoreHolder extends RecyclerView.ViewHolder {


    @Bind(R.id.iv_store)
    public ImageView ivStore;
    @Bind(R.id.tv_store_name)
    public TextView tvStoreName;
    @Bind(R.id.tv_distance)
    public TextView tvDistance;
    @Bind(R.id.tv_unit)
    public TextView tvUnit;
    @Bind(R.id.ll_main_info)
    public LinearLayout llMainInfo;
    @Bind(R.id.tv_address)
    public TextView tvAddress;
    @Bind(R.id.tv_look_map)
    public TextView tvLookMap;
    @Bind(R.id.tv_tel)
    public TextView tvTel;
    @Bind(R.id.tv_wechat)
    public  TextView tvWechat;
    @Bind(R.id.ll_contact_way)
    public LinearLayout llContactWay;
    @Bind(R.id.ll_phone)
    public LinearLayout llPhone;
    @Bind(R.id.wx_ll)
    public LinearLayout wxLL;

    public PhysicalStoreHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
