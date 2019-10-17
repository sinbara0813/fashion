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
 * Created by rookie on 2018/3/20.
 */

public class SingleOrderHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_order_timeout)
    public TextView tvOrderTimeout;
    @Bind(R.id.timeout_layout)
    public LinearLayout timeoutLayout;
    @Bind(R.id.tv_order_id)
    public TextView tvOrderId;
    @Bind(R.id.tv_status)
    public TextView tvStatus;
    @Bind(R.id.empty_view)
    public View emptyView;
    @Bind(R.id.iv_image)
    public ImageView ivImage;
    @Bind(R.id.tv_product_name)
    public TextView tvProductName;
    @Bind(R.id.tv_product_style)
    public TextView tvProductStyle;
    @Bind(R.id.tv_price)
    public TextView tvPrice;
    @Bind(R.id.tv_count)
    public TextView tvCount;
    @Bind(R.id.order_container_layout)
    public LinearLayout orderContainerLayout;
    @Bind(R.id.line_layout)
    public View lineLayout;
    @Bind(R.id.tv_counter)
    public TextView tvCounter;
    @Bind(R.id.tv_order_total)
    public TextView tvOrderTotal;
    @Bind(R.id.btn_show_after_sale)
    public TextView btnShowAfterSale;
    @Bind(R.id.btn_apply_after_sale)
    public TextView btnApplyAfterSale;
    @Bind(R.id.btn_show_logistical)
    public TextView btnShowLogistical;
    @Bind(R.id.btn_confirm_receive)
    public TextView btnConfirmReceive;
    @Bind(R.id.btn_goto_comment)
    public TextView btnGotoComment;
    @Bind(R.id.cancel_order_layout)
    public LinearLayout cancelOrderLayout;
    @Bind(R.id.order_bottom_layout)
    public LinearLayout orderBottomLayout;
    @Bind(R.id.order_item)
    public LinearLayout orderItem;
    @Bind(R.id.btn_buy_again)
    public TextView btnBuyAgain;

    public SingleOrderHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
