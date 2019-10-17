package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/8/30.
 */

public class MyAddressHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.iv_address_default)
    public TextView ivAddressDefault;
    @Bind(R.id.tv_name)
    public TextView tvName;
    @Bind(R.id.tv_phone)
    public TextView tvPhone;
    @Bind(R.id.tv_address)
    public TextView tvAddress;
    @Bind(R.id.img_check)
    public ImageView imgCheck;
    @Bind(R.id.default_address_layout)
    public LinearLayout defaultAddressLayout;
    @Bind(R.id.iv_delete)
    public ImageView ivDelete;
    @Bind(R.id.iv_edit)
    public ImageView ivEdit;
    @Bind(R.id.footer)
    public View footer;
    @Bind(R.id.rl_content)
    public RelativeLayout rl;

    public MyAddressHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
