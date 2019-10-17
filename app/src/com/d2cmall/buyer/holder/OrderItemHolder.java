package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/8/31.
 */

public class OrderItemHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_order_timeout)
    public TextView tvOrderTimeout;
    @Bind(R.id.timeout_layout)
    public LinearLayout timeoutLayout;
    @Bind(R.id.tv_order_id)
    public TextView tvOrderId;
    @Bind(R.id.tv_status)
    public TextView tvStatus;
    @Bind(R.id.order_container_layout)
    public LinearLayout orderContainerLayout;
    @Bind(R.id.line_layout)
    public View lineLayout;
    @Bind(R.id.tv_counter)
    public TextView tvCounter;
    @Bind(R.id.tv_order_total)
    public TextView tvOrderTotal;
    @Bind(R.id.btn_cancel_order)
    public TextView leftButton;
    @Bind(R.id.btn_goto_pay)
    public TextView rightButton;
    @Bind(R.id.cancel_order_layout)
    public LinearLayout cancelOrderLayout;
    @Bind(R.id.order_bottom_layout)
    public LinearLayout orderBottomLayout;
    @Bind(R.id.order_item)
    public LinearLayout orderItem;
    @Bind(R.id.ll_maybe_gone_bottom)
    public LinearLayout llMayBeGoneBottom;
    @Bind(R.id.btn_buy_again)
    public TextView btnBuyAgain;
    @Bind(R.id.another_pay_tv)
    public TextView anotherPayTv;

    public OrderItemHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
