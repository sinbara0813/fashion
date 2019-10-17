package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.AdBean;
import com.d2cmall.buyer.bean.FlashSessionListBean;
import com.d2cmall.buyer.holder.BannerHolder;
import com.d2cmall.buyer.holder.FlashTimeHolder;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.flyco.banner.widget.Banner.base.BaseBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/12/12 11:23
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class FlashHeadAdapter extends DelegateAdapter.Adapter {

    private AdBean adBean;
    private List<FlashSessionListBean.DataBean.ListBean> list;
    private int currentId;
    private int defaultId;
    private Context context;
    private Handler mHandler;
    private long endTime;
    private long startTime;
    private CallBack callBack;
    private int type; //1 是商品 2是品牌

    public FlashHeadAdapter(Context context) {
        this.context = context;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) { //广告
            view = LayoutInflater.from(context).inflate(R.layout.layout_loop_banner, parent, false);
            return new BannerHolder(view);
        } else { //倒计时
            view = LayoutInflater.from(context).inflate(R.layout.layout_flash_time, parent, false);
            return new FlashTimeHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == 1) { //广告
            BannerHolder bannerHolder = (BannerHolder) holder;
            int size = adBean.getData().getAdResource().getPics().length;
            List<String> bannerItems = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                bannerItems.add(adBean.getData().getAdResource().getPics()[i]);
            }
            int height = ScreenUtil.dip2px(152);
            VirtualLayoutManager.LayoutParams Ll = new VirtualLayoutManager.LayoutParams(-1, -2);
            Ll.mAspectRatio = (float) ScreenUtil.getDisplayWidth() / height;
            bannerHolder.itemView.setLayoutParams(Ll);

            bannerHolder.banner.setIndicatorType(1).setBottomPadding(8).setScaleType(true).setLoadingPic(R.mipmap.ic_logo_empty7).setSource(bannerItems).setAutoScrollEnable(true).startScroll();
            if (size == 1) {
                bannerHolder.banner.pauseScroll();
            }
            bannerHolder.banner.setOnItemClickL(new BaseBanner.OnItemClickL() {
                @Override
                public void onItemClick(int position) {
                    Util.urlAction(context, adBean.getData().getAdResource().getPicsUrl()[position]);
                }
            });
        } else if (getItemViewType(position) == 2) { //倒计时
            final FlashTimeHolder timeHolder = (FlashTimeHolder) holder;
            int size = list.size();
            timeHolder.soonContent.removeAllViews();
            for (int i = 0; i < size; i++) {
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.CENTER);
                TextView textView1;
                TextView textView2;
                LinearLayout.LayoutParams LL = new LinearLayout.LayoutParams(-2, -2);
                if (list.get(i).getId() == currentId) {
                    if (!Util.isEmpty(list.get(i).getSessionRemark())){
                        timeHolder.flashTagTv.setText(list.get(i).getSessionRemark());
                    }else {
                        timeHolder.flashTagTv.setText("精选大牌，限时快抢");
                    }
                    linearLayout.setBackgroundResource(R.mipmap.bg_rob_selected);

                    textView1 = new TextView(context);
                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    textView1.setTextColor(context.getResources().getColor(R.color.color_red));
                    if (DateUtil.compareToday(list.get(i).getStartTimeStamp()) == 0) {
                        textView1.setText("今日" + list.get(i).getSession());
                    } else if (DateUtil.compareToday(list.get(i).getStartTimeStamp()) < 0) {
                        textView1.setText("昨日" + list.get(i).getSession());
                    } else {
                        textView1.setText("明日" + list.get(i).getSession());
                    }
                    LinearLayout.LayoutParams l1 = new LinearLayout.LayoutParams(-2, -2);
                    l1.setMargins(0, ScreenUtil.dip2px(5), 0, 0);
                    l1.weight = 1;
                    linearLayout.addView(textView1, l1);

                    textView2 = new TextView(context);
                    textView2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
                    textView2.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
                    textView2.setTextColor(context.getResources().getColor(R.color.color_red));
                    LinearLayout.LayoutParams l2 = new LinearLayout.LayoutParams(-2, -2);
                    l2.setMargins(0, 0, 0, ScreenUtil.dip2px(12));
                    linearLayout.addView(textView2, l2);

                    startTime = list.get(i).getStartTimeStamp();
                    endTime = list.get(i).getEndTimeStamp();
                    if (startTime - System.currentTimeMillis() > 0) {//未开始
                        endTime = startTime;
                        timeHolder.tag.setText("距开始");
                    } else {
                        timeHolder.tag.setText("距结束");
                    }
                    if (currentId == defaultId&&(startTime-System.currentTimeMillis())<0) {
                        textView2.setText("开抢中");
                    } else {
                        if (!Util.isEmpty(list.get(i).getSessionName())) {
                            textView2.setText(list.get(i).getSessionName());
                        } else {
                            textView2.setText(list.get(i).getStatusName());
                        }
                    }
                    setPromotionTime(endTime, timeHolder.flashHourTv, timeHolder.flashMinuteTv, timeHolder.flashMouseTv);
                    if (mHandler != null) {
                        mHandler.removeCallbacksAndMessages(null);
                        mHandler = null;
                    }
                    mHandler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            //更改时间
                            setPromotionTime(endTime, timeHolder.flashHourTv, timeHolder.flashMinuteTv, timeHolder.flashMouseTv);
                            mHandler.sendEmptyMessageDelayed(1, 1000);
                        }
                    };
                    mHandler.sendEmptyMessageDelayed(1, 1000);
                } else {
                    LL = new LinearLayout.LayoutParams(ScreenUtil.dip2px(104), ScreenUtil.dip2px(48));
                    linearLayout.setBackgroundResource(R.drawable.sp_round4_stroke_line_white);

                    textView1 = new TextView(context);
                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    textView1.setTextColor(context.getResources().getColor(R.color.color_black60));
                    if (DateUtil.compareToday(list.get(i).getStartTimeStamp()) == 0) {
                        textView1.setText("今日" + list.get(i).getSession());
                    } else if (DateUtil.compareToday(list.get(i).getStartTimeStamp()) < 0) {
                        textView1.setText("昨日" + list.get(i).getSession());
                    } else {
                        textView1.setText("明日" + list.get(i).getSession());
                    }
                    LinearLayout.LayoutParams l1 = new LinearLayout.LayoutParams(-2, -2);
                    l1.setMargins(0, ScreenUtil.dip2px(5), 0, 0);
                    l1.weight = 1;
                    linearLayout.addView(textView1, l1);

                    textView2 = new TextView(context);
                    textView2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
                    textView2.setTextColor(context.getResources().getColor(R.color.color_black60));
                    if (list.get(i).getId() == defaultId&&(list.get(i).getStartTimeStamp()-System.currentTimeMillis())<0) {
                        textView2.setText("开抢中");
                    } else {
                        if (!Util.isEmpty(list.get(i).getSessionName())) {
                            textView2.setText(list.get(i).getSessionName());
                        } else {
                            textView2.setText(list.get(i).getStatusName());
                        }
                    }
                    LinearLayout.LayoutParams l2 = new LinearLayout.LayoutParams(-2, -2);
                    l2.setMargins(0, 0, 0, ScreenUtil.dip2px(8));
                    linearLayout.addView(textView2, l2);
                }
                LL.setMargins(ScreenUtil.dip2px(8), 0, 0, 0);
                linearLayout.setTag(list.get(i).getId());
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        currentId = (int) v.getTag();
                        notifyDataSetChanged();
                        if (callBack != null) {
                            callBack.selectSession(type, currentId);
                        }
                    }
                });
                timeHolder.soonContent.addView(linearLayout, LL);
            }
        }
    }

    private void setPromotionTime(long startTime, TextView hourTv, TextView minuteTv, TextView mouseTv) {
        long offer = startTime - System.currentTimeMillis();
        long hour = offer / (60 * 60 * 1000);
        long minute = (offer / (60 * 1000)) % 60;
        long mouse = (offer / 1000) % 60;
        hourTv.setText(addZero((int) hour));
        minuteTv.setText(addZero((int) minute));
        mouseTv.setText(addZero((int) mouse));
    }

    private String addZero(int num) {
        if (num < 10) {
            return "0" + num;
        } else {
            return String.valueOf(num);
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (adBean != null && adBean.getData().getAdResource() != null) {
            count++;
        }
        if (list != null && list.size() > 0) {
            count++;
        }
        return count;
    }

    public void recycle() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (adBean != null && adBean.getData().getAdResource() != null && position == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    public void setAdBean(AdBean adBean) {
        this.adBean = adBean;
    }

    public void setList(List<FlashSessionListBean.DataBean.ListBean> list) {
        this.list = list;
    }

    public void setCurrentId(int currentId) {
        this.currentId = currentId;
    }

    public void setDefaultId(int defaultId){
        this.defaultId=defaultId;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public void setType(int type) {
        this.type = type;
    }

    public interface CallBack {
        void selectSession(int type, int promotionId);
    }
}
