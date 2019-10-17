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
 * Created by rookie on 2018/6/25.
 */

public class GroupOrderHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.tv_order_id)
    public TextView tvOrderId;
    @Bind(R.id.tv_status)
    public TextView tvStatus;
    @Bind(R.id.iv_image)
    public ImageView ivImage;
    @Bind(R.id.tv_product_name)
    public TextView tvProductName;
    @Bind(R.id.tv_product_style)
    public TextView tvProductStyle;
    @Bind(R.id.tv_group_info)
    public TextView tvGroupInfo;
    @Bind(R.id.order_container_layout)
    public LinearLayout orderContainerLayout;
    @Bind(R.id.line_layout)
    public View lineLayout;
    @Bind(R.id.btn_order_detail)
    public TextView btnOrderDetail;
    @Bind(R.id.btn_group_detail)
    public TextView btnGroupDetail;
    @Bind(R.id.cancel_order_layout)
    public LinearLayout cancelOrderLayout;
    @Bind(R.id.order_bottom_layout)
    public LinearLayout orderBottomLayout;
    @Bind(R.id.order_item)
    public LinearLayout orderItem;
    @Bind(R.id.tv_count)
    public TextView tvCount;
    @Bind(R.id.btn_go_buy)
    public TextView btnGoToBuy;

    public GroupOrderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
