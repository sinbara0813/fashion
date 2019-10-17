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
 * Created by rookie on 2017/9/9.
 */

public class AfterSaleHolder extends RecyclerView.ViewHolder {

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
    @Bind(R.id.btn_cancel_after_sale)
    public TextView btnCancelAfterSale;
    @Bind(R.id.btn_confirm_after_sale)
    public TextView btnConfirmAfterSale;
    @Bind(R.id.btn_edit_after_sale)
    public TextView btnEditAfterSale;
    @Bind(R.id.order_bottom_layout)
    public LinearLayout orderBottomLayout;
    @Bind(R.id.order_item)
    public LinearLayout orderItem;
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

    public AfterSaleHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
