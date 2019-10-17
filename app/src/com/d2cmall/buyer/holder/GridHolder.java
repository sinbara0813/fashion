package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;

import com.d2cmall.buyer.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rookie on 2017/10/12.
 */

public class GridHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.grid_layout)
    public GridLayout gridLayout;

    public GridHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
