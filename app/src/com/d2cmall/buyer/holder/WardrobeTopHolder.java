package com.d2cmall.buyer.holder;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.RoundLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/11/19.
 * Description : WardrobeTopHolder
 */

public class WardrobeTopHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.vp)
    public ViewPager vp;
    @Bind(R.id.iv_background)
    public ImageView ivBackground;
    @Bind(R.id.iv_cover)
    public ImageView ivCover;
    @Bind(R.id.iv_prev)
    public ImageView ivPrev;
    @Bind(R.id.tv_date_month)
    public TextView tvDateMonth;
    @Bind(R.id.iv_next)
    public ImageView ivNext;
    @Bind(R.id.calendar)
    public com.d2cmall.buyer.widget.calendarview.weiget.CalendarView calendar;
    @Bind(R.id.ll_calendar)
    public LinearLayout llCalendar;
    @Bind(R.id.tv_weather)
    public TextView tvWeather;
    @Bind(R.id.tv_scroll)
    public TextView tvScroll;
    @Bind(R.id.rl_weather)
    public RoundLayout rlWeather;
    @Bind(R.id.progressBar)
    public ProgressBar progressBar;

    public WardrobeTopHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
