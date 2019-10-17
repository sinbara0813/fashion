package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.PersonInfoActivity;
import com.d2cmall.buyer.api.LiveIdApi;
import com.d2cmall.buyer.api.PreviewRemindApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.LiveListBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.bean.WatchInfoBean;
import com.d2cmall.buyer.holder.LivePreviewFirstHolder;
import com.d2cmall.buyer.holder.LivePreviewHolder;
import com.d2cmall.buyer.holder.LiveTitleHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/18 10:31
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class LivePreviewAdapter extends DelegateAdapter.Adapter {

    private Context mContext;
    private List<LiveListBean.DataBean.LivesBean.ListBean> list;
    public boolean isSpecial;
    private long liveId;
    private final UserBean.DataEntity.MemberEntity mUser;

    public LivePreviewAdapter(Context context, List<LiveListBean.DataBean.LivesBean.ListBean> list) {
        this.mContext = context;
        this.list = list;
        mUser = Session.getInstance().getUserFromFile(mContext);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        linearLayoutHelper.setPaddingBottom(ScreenUtil.dip2px(32));
        linearLayoutHelper.setBgColor(Color.WHITE);
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 11:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_live_title, parent, false);
                return new LiveTitleHolder(view);
            case 12:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_live_preview, parent, false);
                return new LivePreviewFirstHolder(view);
            case 13:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_live_preview1, parent, false);
                return new LivePreviewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 11:
                LiveTitleHolder titleHolder = (LiveTitleHolder) holder;
                titleHolder.titleTv.setText("预告");
                break;
            case 12:
                final LiveListBean.DataBean.LivesBean.ListBean firstBean = list.get(position - 1);
                LivePreviewFirstHolder livePreviewFirstHolder = (LivePreviewFirstHolder) holder;
                UniversalImageLoader.displayBlurImage(mContext, firstBean.getCover(), livePreviewFirstHolder.image, 90);
                UniversalImageLoader.displayRoundImage(mContext, firstBean.getHeadPic(), livePreviewFirstHolder.imgAvatar, R.mipmap.ic_default_avatar);
                livePreviewFirstHolder.name.setText(firstBean.getNickname());
                livePreviewFirstHolder.imgAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toPersonActivity(firstBean.getMemberId());
                    }
                });
                livePreviewFirstHolder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toPersonActivity(firstBean.getMemberId());
                    }
                });
                livePreviewFirstHolder.info.setText(firstBean.getTitle());
                //livePreviewFirstHolder.timeTv.setText(DateUtil.getFriendlyTime2(firstBean.getPreviewDate()));
                break;
            case 13:

                final LiveListBean.DataBean.LivesBean.ListBean listBean = list.get(position - 1);

                final LivePreviewHolder livePreviewHolder = (LivePreviewHolder) holder;
                UniversalImageLoader.displayImage(mContext, listBean.getCover(), livePreviewHolder.image);
                UniversalImageLoader.displayRoundImage(mContext, listBean.getHeadPic(), livePreviewHolder.imgAvatar, R.mipmap.ic_default_avatar);
                livePreviewHolder.name.setText(listBean.getNickname());
                livePreviewHolder.imgAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toPersonActivity(listBean.getMemberId());
                    }
                });
                livePreviewHolder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toPersonActivity(listBean.getMemberId());
                    }
                });
                livePreviewHolder.info.setText(listBean.getTitle());
                //livePreviewHolder.timeTv.setText(DateUtil.getFriendlyTime2(listBean.getPreviewDate()));
                //boolean hasRemind = listBean.getRemind() == 1;
                boolean hasRemind=true;
                if (hasRemind) {
                    livePreviewHolder.noticeIv.setImageResource(R.mipmap.button_forecast_unremind);
                } else {
                    livePreviewHolder.noticeIv.setImageResource(R.mipmap.button_forecast_remind);
                }

                if (mUser != null && mUser.getMemberId() == listBean.getMemberId()) {
                    showTimeDownForHost(livePreviewHolder.noticeIv, livePreviewHolder.tvStart, listBean);
                } else {
                    showTimeDownForUser(livePreviewHolder.noticeIv, livePreviewHolder.tvStart);
                }
                livePreviewHolder.noticeIv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Util.loginChecked((Activity) mContext, 333)) {
                            liveId = listBean.getId();
                            //int remind = listBean.getRemind();
                            int remind = 0;
                            if (remind == 0) {
                                requestRemindPreview(listBean, livePreviewHolder.noticeIv);
                            } else {
                                requestUnremindPreview(listBean, livePreviewHolder.noticeIv);
                            }
                        }
                    }
                });
                livePreviewHolder.tvStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listBean == null) {
                            return;
                        }
                        Util.loginChecked((Activity) mContext, 999);
                        int flag = 1;
                                //DateUtil.isThisMinutes2(listBean.getPreviewDate(), 15);
                        if (flag == 1) {
                            checkWatchStatusTask(listBean);
                        } else {
                            Util.showToast(mContext, R.string.msg_into_live_too_late);
                        }
                    }
                });
                break;
        }
    }

    private void showTimeDownForHost(ImageView noticeIv, TextView tvStart, LiveListBean.DataBean.LivesBean.ListBean listBean) {
        noticeIv.setVisibility(View.GONE);
        tvStart.setVisibility(View.VISIBLE);
        int flag =1;
                //DateUtil.isThisMinutes2(listBean.getPreviewDate(), 15);
        if (flag == 1) {
            tvStart.setEnabled(true);
        } else {
            tvStart.setEnabled(false);
        }
    }

    private void showTimeDownForUser(ImageView noticeIv, TextView tvStart) {
        noticeIv.setVisibility(View.VISIBLE);
        tvStart.setVisibility(View.GONE);
    }

    private void checkWatchStatusTask(final LiveListBean.DataBean.LivesBean.ListBean listBean) {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.WATCH_INFO_URL, liveId));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<WatchInfoBean>() {
            @Override
            public void onResponse(WatchInfoBean watchInfoBean) {
                int status = watchInfoBean.getData().getLive().getStatus();
                if (status == -3) {//主播丢失
                    Util.showToast(mContext, "您的直播预告已被下架");
                } else if (status == -2) {//正常
                    startPreviewLive(listBean);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    public void requestRemindPreview(LiveListBean.DataBean.LivesBean.ListBean listBean, final ImageView noticeIv) {
        if (listBean == null) {
            return;
        }
        //listBean.setRemind(1);
        PreviewRemindApi api = new PreviewRemindApi();
        api.liveId = listBean.getId();
        api.interPath = Constants.PREVIEW_REMIND_URL;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                Util.showToast(mContext, R.string.msg_remind_ok);
                noticeIv.setImageResource(R.mipmap.button_forecast_unremind);
                EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.REFRESH_LIVE));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    private void startPreviewLive(final LiveListBean.DataBean.LivesBean.ListBean listBean) {
        LiveIdApi api = new LiveIdApi();
        api.liveId = liveId;
        api.setInterPath(Constants.PREVIEW_START_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
//                LivePublishActivity.actionStart((Activity) mContext, listBean.getTitle(), true,
//                        false, 0, 0, 0, Surface.ROTATION_0, listBean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    public void requestUnremindPreview(final LiveListBean.DataBean.LivesBean.ListBean listBean, final ImageView noticeIv) {
        if (listBean == null) {
            return;
        }

        PreviewRemindApi api = new PreviewRemindApi();
        api.liveId = listBean.getId();
        api.interPath = Constants.PREVIEW_UNREMIND_URL;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                //listBean.setRemind(0);
                noticeIv.setImageResource(R.mipmap.button_forecast_remind);
                Util.showToast(mContext, R.string.msg_cancel_remind_ok);
                EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.REFRESH_LIVE));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(mContext, Util.checkErrorType(error));
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
        return list.size() == 0 ? 0 : list.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 11;
        } else if (position == 1 && !isSpecial) {
            return 12;
        } else {
            return 13;
        }
    }


}
