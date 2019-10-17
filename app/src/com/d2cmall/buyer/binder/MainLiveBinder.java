package com.d2cmall.buyer.binder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.LiveAudienceActivity;
import com.d2cmall.buyer.activity.VideoActivity;
import com.d2cmall.buyer.base.BaseViewBinder;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.LiveListBean;
import com.d2cmall.buyer.holder.MainLiveHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.GuideLayout;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/8/31 19:16
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainLiveBinder implements BaseViewBinder<MainLiveHolder> {

    private Context context;
    private List<LiveListBean.DataBean.LivesBean.ListBean> list;
    private RequestManager requestManager;

    public MainLiveBinder(Context context, List<LiveListBean.DataBean.LivesBean.ListBean> data) {
        this.context = context;
        this.list = data;
    }

    public MainLiveBinder(Context context,RequestManager requestManager,List<LiveListBean.DataBean.LivesBean.ListBean> data) {
        this.requestManager=requestManager;
        this.context = context;
        this.list = data;
    }

    @Override
    public MainLiveHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_main_live_item, parent, false);
        return new MainLiveHolder(view);
    }

    @Override
    public void onBindViewHolder(MainLiveHolder mainLiveHolder, int position) {
        mainLiveHolder.titleAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionBean actionBean=new ActionBean(Constants.ActionType.CHANGE_PAGE);
                actionBean.put("firstIndex",3);
                actionBean.put("secondIndex",2);
                EventBus.getDefault().post(actionBean);
            }
        });
        if (list != null && list.size() > 2) {
            int smallWidth= (ScreenUtil.getDisplayWidth()-ScreenUtil.dip2px(48))/3;
            int bigWidth=smallWidth*2+ScreenUtil.dip2px(8);
            RelativeLayout.LayoutParams bigRl= (RelativeLayout.LayoutParams) mainLiveHolder.rlBig.getLayoutParams();
            bigRl.width=bigWidth;
            bigRl.height=bigWidth;
            RelativeLayout.LayoutParams smallRl1= (RelativeLayout.LayoutParams) mainLiveHolder.rlSmall1.getLayoutParams();
            smallRl1.width=smallWidth;
            smallRl1.height=smallWidth;
            RelativeLayout.LayoutParams smallRl2= (RelativeLayout.LayoutParams) mainLiveHolder.rlSmall2.getLayoutParams();
            smallRl2.width=smallWidth;
            smallRl2.height=smallWidth;
            if (requestManager!=null){
                requestManager.load(Util.getD2cPicUrl(list.get(0).getCover())).into(mainLiveHolder.ivBigImage);
                requestManager.load(Util.getD2cPicUrl(list.get(1).getCover())).into(mainLiveHolder.ivSmall1);
                requestManager.load(Util.getD2cPicUrl(list.get(2).getCover())).into(mainLiveHolder.ivSmall2);
            }else {
                UniversalImageLoader.displayImage(context, list.get(0).getCover(), mainLiveHolder.ivBigImage);
                UniversalImageLoader.displayImage(context, list.get(1).getCover(), mainLiveHolder.ivSmall1);
                UniversalImageLoader.displayImage(context, list.get(2).getCover(), mainLiveHolder.ivSmall2);
            }
            mainLiveHolder.tvBigRightTop.setText(String.valueOf(list.get(0).getVcount()+list.get(0).getVfans()));
            mainLiveHolder.tvBigBottomLeft.setText(list.get(0).getTitle());
            mainLiveHolder.tvSmall1Bottom.setText(list.get(1).getTitle() + "");
            mainLiveHolder.tvSmall2Bottom.setText(list.get(2).getTitle() + "");
            setTopLeft(list.get(0).getStatus(), mainLiveHolder.tvBigLeftTop);
            setTopLeft(list.get(1).getStatus(), mainLiveHolder.tvSmall1LeftTop);
            setTopLeft(list.get(2).getStatus(), mainLiveHolder.tvSmall2LeftTop);
            mainLiveHolder.ivBigImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toActivity(list.get(0).getStatus(), list.get(0));
                }
            });
            mainLiveHolder.ivSmall1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toActivity(list.get(1).getStatus(), list.get(1));
                }
            });
            mainLiveHolder.ivSmall2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toActivity(list.get(2).getStatus(), list.get(2));
                }
            });
        }
    }

    private void toActivity(int status, LiveListBean.DataBean.LivesBean.ListBean listBean) {
        switch (status) {
            case 4:
                if (Util.loginChecked((Activity) context,100)){
                    if (GuideLayout.getInstance(context).isAddView()) {
                        GuideLayout.getInstance(context).hide();
                    } else {
                        Intent intent1=new Intent(context, LiveAudienceActivity.class);
                        intent1.putExtra("bean",listBean);
                        context.startActivity(intent1);
                    }
                }
                break;
            case 8:
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("bean", listBean);
                context.startActivity(intent);
                break;
        }
    }

    @Override
    public void onBindViewHolderWithOffer(MainLiveHolder mainLiveHolder, int position, int offsetTotal) {

    }

    private void setTopLeft(int type, TextView textView) {
        switch (type) {
            case 4:
                textView.setText("");
                textView.setBackgroundResource(R.mipmap.icon_boardcast_mark);
                break;
            case 8:
                textView.setText("回放");
                break;
        }
    }
}
