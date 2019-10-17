package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.WeatherBean;
import com.d2cmall.buyer.holder.WearWeatherHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;


/**
 * Fixme
 * Author: LWJ
 * desc:   我的穿搭-精选穿搭
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class WearWeatherAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    private String temp;
    private String wind;
    private String sc;
    private String drsg;
    private String cond;
    private WeatherBean weatherBean;

    public WearWeatherAdapter(Context context) {
        mContext = context;
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        return singleLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_wear_weather, parent, false);
        return new WearWeatherHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WearWeatherHolder wearWeatherHolder = (WearWeatherHolder) holder;
        if(weatherBean!=null && weatherBean.getHeWeather5()!=null){
            temp = weatherBean.getHeWeather5().get(0).getNow().getTmp();
            wind = weatherBean.getHeWeather5().get(0).getNow().getWind().getDir();
            sc = weatherBean.getHeWeather5().get(0).getNow().getWind().getSc();
            cond = weatherBean.getHeWeather5().get(0).getNow().getCond().getTxt();
            if (weatherBean.getHeWeather5().get(0).getSuggestion() != null && weatherBean.getHeWeather5().get(0).getSuggestion().getDrsg() != null) {
                drsg = weatherBean.getHeWeather5().get(0).getSuggestion().getDrsg().getTxt();
            }
            wearWeatherHolder.rlWeather.setVisibility(View.VISIBLE);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(temp);
            stringBuilder.append("° ");
            stringBuilder.append(cond);
            stringBuilder.append("  ");
            stringBuilder.append(wind);
            stringBuilder.append("  ");
            stringBuilder.append(sc);
            stringBuilder.append("级");
            String string = stringBuilder.toString();
            int index = string.indexOf("°");
            int length = string.length();
            SpannableString textSpan = new SpannableString(string);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(36)), 0, index + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(12)), index + 1, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            wearWeatherHolder.tvWeather.setText(textSpan);
            wearWeatherHolder.tvScroll.setText(drsg);
            if (Util.isEmpty(cond)) {
                return;
            } else if (cond.contains("晴")) {
                wearWeatherHolder.tvWeather.setBackgroundResource(R.mipmap.pic_weather_sunny);
            } else if (cond.contains("雨")) {
                wearWeatherHolder.tvWeather.setBackgroundResource(R.mipmap.pic_weather_rainy);
            } else if (cond.contains("雪")) {
                wearWeatherHolder.tvWeather.setBackgroundResource(R.mipmap.pic_weather_snowy);
            } else if (cond.contains("雾") || cond.contains("霾")) {
                wearWeatherHolder.tvWeather.setBackgroundResource(R.mipmap.pic_weather_foggy);
            } else if (cond.contains("阴")) {
                wearWeatherHolder.tvWeather.setBackgroundResource(R.mipmap.pic_weather_overcast);
            } else if (cond.contains("云")) {
                wearWeatherHolder.tvWeather.setBackgroundResource(R.mipmap.pic_weather_cloudy);
            }

        }
    }


    @Override
    public int getItemCount() {
       return 1;
    }

    public void setWeather(WeatherBean weatherBean) {
        this.weatherBean = weatherBean;
        notifyDataSetChanged();
    }
}
