package com.d2cmall.buyer.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.MarqueeTextView;
import com.d2cmall.buyer.widget.RoundLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/11/19.
 * Description : WardrobeTopHolder
 */

public class WearWeatherHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.tv_weather)
    public TextView tvWeather;
    @Bind(R.id.tv_scroll)
    public  MarqueeTextView tvScroll;
    @Bind(R.id.rl_weather)
    public RoundLayout rlWeather;

    public WearWeatherHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
