package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.LiveAnchorActivity;
import com.d2cmall.buyer.activity.LiveAudienceActivity;
import com.d2cmall.buyer.activity.PersonInfoActivity;
import com.d2cmall.buyer.activity.StartLiveActivity;
import com.d2cmall.buyer.activity.VideoActivity;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.LiveListBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.BannerHolder;
import com.d2cmall.buyer.holder.LiveItemHolder;
import com.d2cmall.buyer.holder.LiveTitleHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.GuideLayout;
import com.flyco.banner.widget.Banner.base.BaseBanner;
import com.qiniu.pili.droid.streaming.StreamingProfile;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.List;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/18 10:28
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class LiveAdapter extends DelegateAdapter.Adapter {

    private Context mContext;
    private int type; //1表示直播 2表示回放
    private boolean hasBanner;
    private List<LiveListBean.DataBean.LivesBean.ListBean> list;
    private List<String> pics;
    private List<String> picUrls;
    private UserBean.DataEntity.MemberEntity user;

    public LiveAdapter(Context context, List<LiveListBean.DataBean.LivesBean.ListBean> list, int type) {
        this.mContext = context;
        this.list = list;
        this.type = type;
        user = Session.getInstance().getUserFromFile(mContext);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setPaddingBottom(ScreenUtil.dip2px(16));
        linearLayoutHelper.setBgColor(Color.WHITE);
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 1: //banner
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_loop_banner, parent, false);
                return new BannerHolder(view);
            case 2: //title
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_live_title, parent, false);
                return new LiveTitleHolder(view);
            case 3:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_live_item, parent, false);
                return new LiveItemHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 1:
                if (pics == null || pics.size() == 0) {
                    return;
                }
                BannerHolder bannerHolder = (BannerHolder) holder;
                List<String> bannerItems = new ArrayList<>();
                int size = pics.size();
                for (int i = 0; i < size; i++) {
                    bannerItems.add(Util.getD2cPicUrl(pics.get(i)));
                }
                int height = ScreenUtil.dip2px(128);
                VirtualLayoutManager.LayoutParams Ll = new VirtualLayoutManager.LayoutParams(-1, -2);
                Ll.mAspectRatio = (float) ScreenUtil.getDisplayWidth() / height;
                bannerHolder.itemView.setLayoutParams(Ll);
                if (size == 1) {
                    bannerHolder.banner.setSource(bannerItems).setIndicatorHeight(0).setAutoScrollEnable(false).startScroll();
                } else {
                    bannerHolder.banner.setSource(bannerItems).setIndicatorHeight(0).setAutoScrollEnable(true).startScroll();
                }
                bannerHolder.banner.setOnItemClickL(new BaseBanner.OnItemClickL() {
                    @Override
                    public void onItemClick(int position) {
                        Util.urlAction(mContext, picUrls.get(position));
                    }
                });
                break;
            case 2:
                LiveTitleHolder liveTitleHolder = (LiveTitleHolder) holder;
                liveTitleHolder.titleTv.setText("往期回顾");
                break;
            case 3:
                int index = position;
                if (hasBanner || type == 2) {
                    index = position - 1;
                }
                final LiveListBean.DataBean.LivesBean.ListBean listBean = list.get(index);
                LiveItemHolder itemHolder = (LiveItemHolder) holder;
                UniversalImageLoader.displayRoundImage(mContext, Util.getD2cPicUrl(listBean.getHeadPic()), itemHolder.imgAvatar, R.mipmap.ic_default_avatar);
                itemHolder.name.setText(list.get(index).getNickname());
                itemHolder.imgAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toPersonActivity(listBean.getMemberId());
                    }
                });
                itemHolder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toPersonActivity(listBean.getMemberId());
                    }
                });

                itemHolder.watchCount.setText(mContext.getString(R.string.label_total_view_count, listBean.getVcount() + listBean.getVfans()));
                RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) itemHolder.image.getLayoutParams();
                rl.height = ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(32);
                UniversalImageLoader.displayImage(mContext, listBean.getCover(), itemHolder.image);
                if (type == 1) {
                    itemHolder.tagTv.setText("");
                    itemHolder.tagTv.setBackgroundResource(R.mipmap.icon_boardcast_mark);
                } else {
                    itemHolder.tagTv.setText("回放");
                    itemHolder.tagTv.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
                }
                itemHolder.des.setText(listBean.getTitle());
                itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (type == 1) {
                            if (Util.loginChecked((Activity) mContext, 100)) {
                                if (GuideLayout.getInstance(mContext).isAddView()) {
                                    GuideLayout.getInstance(mContext).hide();
                                } else {
//                                    LivePlayActivity.actionStart((Activity) mContext, listBean);
//                                    stat("直播间","直播间");
                                    UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(mContext);
                                    long id;
                                    if(user!=null){
                                        id=user.getMemberId();
                                    }else {
                                        id=0;
                                    }
                                    if (id == listBean.getMemberId()) {
                                        LiveAnchorActivity.actionStart((Activity) mContext, listBean.getTitle(), true,
                                                false, 0, 0, 0,
                                                StreamingProfile.VIDEO_QUALITY_HIGH3,
                                                listBean);
                                    } else {
                                        Intent intent = new Intent(mContext, LiveAudienceActivity.class);
                                        intent.putExtra("bean", listBean);
                                        mContext.startActivity(intent);
                                    }
                                }
                            }
                        } else {
                            if (Util.loginChecked((Activity) mContext, 100)) {
                                Intent intent = new Intent(mContext, VideoActivity.class);
                                intent.putExtra("bean", listBean);
                                mContext.startActivity(intent);
                            }
                        }
                    }
                });
                //deleteLive(listBean.getId());
                break;
        }
    }

    private void deleteLive(long id) {
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.POST);
        api.setInterPath(String.format(Constants.DELETE_LIVE, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                Log.e("live", "删除成功");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("live", Util.checkErrorType(error));
            }
        });
    }

    private void toPersonActivity(long memberId) {
        Intent intent = new Intent(mContext, PersonInfoActivity.class);
        intent.putExtra("memberId", memberId);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (type == 1) {
            int size = list.size() == 0 ? 0 : list.size();
            return hasBanner ? size + 1 : size;
        } else if (type == 2) {
            return list.size() == 0 ? 0 : list.size() + 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (type == 1 && hasBanner && position == 0) {
            return 1;
        } else if (type == 2 && position == 0) {
            return 2;
        } else {
            return 3;
        }
    }

    public void setPic(List<String> pics) {
        this.pics = pics;
        if (pics != null && pics.size() > 0) {
            hasBanner = true;
        }
    }

    public void setPicUrls(List<String> picUrls) {
        this.picUrls = picUrls;
    }
}
