package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BrandDetailActivity;
import com.d2cmall.buyer.activity.HomeActivity;
import com.d2cmall.buyer.activity.PersonInfoActivity;
import com.d2cmall.buyer.activity.ShowDetailActivity;
import com.d2cmall.buyer.activity.TopicDetailActivity;
import com.d2cmall.buyer.activity.UnFollowActivity;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.FollowApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ClickFollowBean;
import com.d2cmall.buyer.bean.ShareBean;
import com.d2cmall.buyer.bean.UnFocusMemberBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.FocusHolder;
import com.d2cmall.buyer.holder.ShowItemHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.Clickable;
import com.d2cmall.buyer.listener.ProgressCallBack;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ShowPopImageView;
import com.d2cmall.buyer.widget.nicevideo.TxVideoPlayerController;
import com.d2cmall.buyer.widget.ninegrid.ImageInfo;
import com.d2cmall.buyer.widget.ninegrid.NineGridView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.d2cmall.buyer.Constants.DELETE_SHARE_LIKE;
import static com.d2cmall.buyer.Constants.LIKE_SHARE_URL;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/31 15:52
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ShowAdapter extends DelegateAdapter.Adapter {

    private Context mContext;
    public boolean hasFocus;//判断是否有感兴趣的人模块
    public boolean hasBanner;//判断是否有Banner轮播图模块
    public boolean hasPadding;
    public int padTop;
    private List<ShareBean.DataEntity.MemberSharesEntity.ListEntity> datas;
    public List<UnFocusMemberBean.DataBean.ActiveMemberBean> unFocusBean;
    private UserBean.DataEntity.MemberEntity user;
    private ProgressCallBack progressCallBack;
    private int lastProgress;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (progressCallBack != null) {
                progressCallBack.setProgress(msg.arg1, (Boolean) msg.obj);
            }
        }
    };
    private int IMAGE_SIZE = 0;
    private long parentId;
    private int firstPosition=-1;
    private boolean showTip;

    public ShowAdapter(Context context, List<ShareBean.DataEntity.MemberSharesEntity.ListEntity> datas, boolean hasFocus, boolean hasBanner) {
        this.mContext = context;
        this.datas = datas;
        this.hasFocus = hasFocus;
        this.hasBanner = hasBanner;
        user = Session.getInstance().getUserFromFile(context);
        showTip=D2CApplication.mSharePref.getSharePrefBoolean(SharePrefConstant.SHOW_SHOP_TIP,true);
    }

    public void refreshUser() {
        user = Session.getInstance().getUserFromFile(mContext);
    }

    public void setProgressCallBack(ProgressCallBack progressCallBack) {
        this.progressCallBack = progressCallBack;
    }

    public void destroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        if (hasPadding) {
            if (padTop > 0) {
                linearLayoutHelper.setPaddingTop(ScreenUtil.dip2px(padTop));
            } else {
                linearLayoutHelper.setPaddingTop(ScreenUtil.dip2px(16));
            }
        }
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 5:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_focus, new LinearLayout(mContext), false);
                return new FocusHolder(view);
            case 6:
                view = LayoutInflater.from(mContext).inflate(R.layout.layout_show_item, new LinearLayout(mContext), false);
                return new ShowItemHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case 5:
                final FocusHolder focusHolder = (FocusHolder) holder;
                if (unFocusBean != null && unFocusBean.size() > 0) {
                    focusHolder.focusLL.setVisibility(View.VISIBLE);
                    focusHolder.focus1Ll.setVisibility(View.VISIBLE);
                    UniversalImageLoader.displayRoundImage(mContext, unFocusBean.get(0).getHeadPic(), focusHolder.image1, R.mipmap.ic_default_avatar);
                    if (unFocusBean.size() > 1) {
                        focusHolder.focus2Ll.setVisibility(View.VISIBLE);
                        UniversalImageLoader.displayRoundImage(mContext, unFocusBean.get(1).getHeadPic(), focusHolder.image2, R.mipmap.ic_default_avatar);
                        if (unFocusBean.size() > 2) {
                            focusHolder.focus3Ll.setVisibility(View.VISIBLE);
                            UniversalImageLoader.displayRoundImage(mContext, unFocusBean.get(2).getHeadPic(), focusHolder.image3, R.mipmap.ic_default_avatar);
                            if (unFocusBean.size() > 3) {
                                focusHolder.focus4Ll.setVisibility(View.VISIBLE);
                                UniversalImageLoader.displayRoundImage(mContext, unFocusBean.get(3).getHeadPic(), focusHolder.image4, R.mipmap.ic_default_avatar);
                                focusHolder.tv4.setText(unFocusBean.get(3).getNickName());
                                setFocusState(focusHolder.focus4, unFocusBean.get(3).getFollow());
                                focusHolder.image4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(mContext, PersonInfoActivity.class);
                                        intent.putExtra("memberId", unFocusBean.get(3).getMemberId());
                                        mContext.startActivity(intent);
                                    }
                                });
                                //开启消息推送行为节点
                                focusHolder.focus4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        focusComplete(unFocusBean.get(3), focusHolder.focus4);
                                    }
                                });
                            }
                            focusHolder.tv3.setText(unFocusBean.get(2).getNickName());
                            setFocusState(focusHolder.focus3, unFocusBean.get(2).getFollow());
                            focusHolder.image3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(mContext, PersonInfoActivity.class);
                                    intent.putExtra("memberId", unFocusBean.get(2).getMemberId());
                                    mContext.startActivity(intent);
                                }
                            });
                            focusHolder.focus3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    focusComplete(unFocusBean.get(2), focusHolder.focus3);
                                }
                            });
                        }
                        focusHolder.tv2.setText(unFocusBean.get(1).getNickName());
                        setFocusState(focusHolder.focus2, unFocusBean.get(1).getFollow());
                        focusHolder.image2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, PersonInfoActivity.class);
                                intent.putExtra("memberId", unFocusBean.get(1).getMemberId());
                                mContext.startActivity(intent);
                            }
                        });
                        focusHolder.focus2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                focusComplete(unFocusBean.get(1), focusHolder.focus2);
                            }
                        });
                    }
                    focusHolder.tv1.setText(unFocusBean.get(0).getNickName());
                    setFocusState(focusHolder.focus1, unFocusBean.get(0).getFollow());
                    focusHolder.image1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, PersonInfoActivity.class);
                            intent.putExtra("memberId", unFocusBean.get(0).getMemberId());
                            mContext.startActivity(intent);

                        }
                    });
                    focusHolder.focus1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            focusComplete(unFocusBean.get(0), focusHolder.focus1);
                        }
                    });
                    focusHolder.recommendMemberTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, UnFollowActivity.class);
                            mContext.startActivity(intent);
                        }
                    });
                }
                break;
            case 6:
                int index = 0;
                if (hasBanner || position > 5 && hasFocus) {
                    index = position - 1;
                } else {
                    index = position;
                }
                final ShareBean.DataEntity.MemberSharesEntity.ListEntity listEntity = datas.get(index);
                final ShowItemHolder showItemHolder = (ShowItemHolder) holder;
                if (user != null) {
                    if (listEntity.getMemberId() == user.getMemberId()) {
                        showItemHolder.ivFocus.setVisibility(View.GONE);
                    }
                }
                if (listEntity.getDesignerId() > 0) {
                    showItemHolder.imgTag.setVisibility(View.VISIBLE);
                } else {
                    showItemHolder.imgTag.setVisibility(View.GONE);
                }
                switch (listEntity.getRole()) {
                    case 0://普通会员
                        showItemHolder.imgTag.setVisibility(View.GONE);
                        break;
                    case 1://设计师
                        showItemHolder.imgTag.setVisibility(View.VISIBLE);
                        showItemHolder.imgTag.setImageResource(R.mipmap.icon_all_designer);
                        if (firstPosition<0){
                            firstPosition=position;
                        }
                        break;
                    case 2://D2C官方
                        showItemHolder.imgTag.setVisibility(View.VISIBLE);
                        showItemHolder.imgTag.setImageResource(R.mipmap.icon_all_d2c);
                        break;
                    case 5://达人标识
                        showItemHolder.imgTag.setVisibility(View.VISIBLE);
                        showItemHolder.imgTag.setImageResource(R.mipmap.icon_all_fashion);
                        break;
                    default:
                        showItemHolder.imgTag.setVisibility(View.GONE);
                        break;
                }
                if (showTip&&firstPosition==position){
                    showItemHolder.tvTip.setVisibility(View.VISIBLE);
                }else {
                    showItemHolder.tvTip.setVisibility(View.GONE);
                }
                showItemHolder.imgAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listEntity.getDesignerId()>0){
                            Intent intent=new Intent(mContext, BrandDetailActivity.class);
                            intent.putExtra("id",listEntity.getDesignerId());
                            mContext.startActivity(intent);
                            showItemHolder.tvTip.setVisibility(View.GONE);
                            D2CApplication.mSharePref.putSharePrefBoolean(SharePrefConstant.SHOW_SHOP_TIP,false);
                            showTip=false;
                        }else {
                            Intent intent = new Intent(mContext, PersonInfoActivity.class);
                            intent.putExtra("memberId", listEntity.getMemberId());
                            mContext.startActivity(intent);
                        }
                    }
                });

                showItemHolder.timeTv.setText(DateUtil.getFriendlyTime2(listEntity.getCreateDate()));
                showItemHolder.commentNum.setText(String.valueOf(listEntity.getCommentCount()));
                if (listEntity.getVerifyDate() == null) {
                    showItemHolder.infoTv.setText(String.valueOf(listEntity.getWatchCount()));
                } else {
                    if (Util.getFakeWatchCount(listEntity.getVerifyDate()) + listEntity.getWatchCount() > 10000) {
                        DecimalFormat df = new DecimalFormat("0.0");
                        showItemHolder.infoTv.setText(df.format(((int) Util.getFakeWatchCount(listEntity.getVerifyDate()) + listEntity.getWatchCount()) / (float) 10000.0) + "万");
                    } else {
                        if (Util.getFakeWatchCount(listEntity.getVerifyDate()) + listEntity.getWatchCount() < 0) {
                            showItemHolder.infoTv.setText(String.valueOf(0));
                        } else {
                            showItemHolder.infoTv.setText(String.valueOf(Util.getFakeWatchCount(listEntity.getVerifyDate()) + listEntity.getWatchCount()));
                        }
                    }
                }
                if (user != null && user.getPartnerId() > 0) {
                    //说明是分销者
                    showItemHolder.tvDownNum.setVisibility(View.VISIBLE);
                    int fakeDownCount = Util.getFakeDownLoadNum((int) (Util.getFakeWatchCount(listEntity.getVerifyDate()) + listEntity.getWatchCount()), listEntity.getDownCount());
                    showItemHolder.tvDownNum.setText(String.valueOf(fakeDownCount));
                    int fakeShareCount = Util.getFakeShareNum((int) (Util.getFakeWatchCount(listEntity.getVerifyDate()) + listEntity.getWatchCount()), listEntity.getShareCount());
                    showItemHolder.tvSharaNum.setVisibility(View.VISIBLE);
                    showItemHolder.tvSharaNum.setText(String.valueOf(fakeShareCount));
                    showItemHolder.tvDownNum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            postDownLoadRequest(listEntity.getId());
                            if (!Util.isEmpty(listEntity.getVideo())) {
                                shareVideo(listEntity, false);
                            } else {
                                sharePics(listEntity.getPics(), listEntity.getDescription(), false);
                            }
                        }
                    });
                    showItemHolder.tvSharaNum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            postShareRequest(listEntity.getId());
                            if (!Util.isEmpty(listEntity.getVideo())) {
                                shareVideo(listEntity, true);
                            } else {
                                sharePics(listEntity.getPics(), listEntity.getDescription(), true);
                            }
                        }
                    });
                } else {
                    showItemHolder.tvDownNum.setVisibility(View.INVISIBLE);
                    showItemHolder.tvSharaNum.setVisibility(View.INVISIBLE);
                }
                switch (listEntity.getFollow()) {
                    case 0:
                        showItemHolder.ivFocus.setImageResource(R.mipmap.button_fashion_care);
                        break;
                    case 1:
                        showItemHolder.ivFocus.setImageResource(R.mipmap.button_fashion_cared);
                        break;
                    case 2:
                        showItemHolder.ivFocus.setImageResource(R.mipmap.button_fashion_mutualcare);
                        break;
                }
                showItemHolder.ivFocus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        focusComplete(listEntity, showItemHolder);
                    }
                });
                showItemHolder.commentNum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ShowDetailActivity.class);
                        intent.putExtra("id", listEntity.getId());
                        mContext.startActivity(intent);
                    }
                });
                showItemHolder.likeNum.setText(String.valueOf(listEntity.getLikeMeCount()));
                if (listEntity.getLiked() > 0) {
                    Drawable nav_up = mContext.getResources().getDrawable(R.mipmap.icon_fashion_liked);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    showItemHolder.likeNum.setCompoundDrawables(nav_up, null, null, null);
                } else {
                    Drawable nav_up = mContext.getResources().getDrawable(R.mipmap.icon_fashion_like_small);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    showItemHolder.likeNum.setCompoundDrawables(nav_up, null, null, null);
                }
                showItemHolder.likeNum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        likeShow(listEntity, showItemHolder);
                    }
                });
                UniversalImageLoader.displayRoundImage(mContext, Util.getD2cPicUrl(listEntity.getMemberHead()), showItemHolder.imgAvatar, R.mipmap.ic_default_avatar);
                showItemHolder.nameTv.setText(listEntity.getMemberName());
                if (!Util.isEmpty(listEntity.getDescription()) || !Util.isEmpty(listEntity.getTopicName())) {
                    showItemHolder.contentTv.setVisibility(View.VISIBLE);
                    StringBuilder builder = new StringBuilder();
                    if (!Util.isEmpty(listEntity.getTopicName())) {
                        int end = listEntity.getTopicName().length() + 2;
                        builder.append("#" + listEntity.getTopicName() + "#" + " ");
                        if (!Util.isEmpty(listEntity.getDescription())) {
                            builder.append(listEntity.getDescription());
                        }
                        SpannableString sb = new SpannableString(builder.toString());
                        sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, listEntity.getTopicName().length() + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //粗体
                        sb.setSpan(new Clickable(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, TopicDetailActivity.class);
                                intent.putExtra("id", listEntity.getTopicId());
                                mContext.startActivity(intent);
                            }
                        }), 0, listEntity.getTopicName().length() + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.setSpan(new ForegroundColorSpan(Color.BLACK), 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        showItemHolder.contentTv.setMovementMethod(LinkMovementMethod.getInstance());
                        showItemHolder.contentTv.setText(sb);
                    } else {
                        showItemHolder.contentTv.setText(listEntity.getDescription());
                    }
                } else {
                    showItemHolder.contentTv.setVisibility(View.GONE);
                }

                String videoUrl1 = listEntity.getVideo();
                List<String> imgList = listEntity.getPics();
                if (Util.isEmpty(videoUrl1)) {
                    showItemHolder.nineGrid.setVisibility(View.VISIBLE);
                    setPictures(listEntity, showItemHolder.nineGrid, imgList);
                    showItemHolder.videoLayout.setVisibility(View.GONE);
                } else {
                    if (!videoUrl1.startsWith("http")) {
                        videoUrl1 = Constants.IMG_HOST + videoUrl1;
                    }
                    /*TxVideoPlayerController txVideoPlayerController=new TxVideoPlayerController(mContext);
                    showItemHolder.niceVideoPlayer.setController(txVideoPlayerController);*/
                    TxVideoPlayerController txVideoPlayerController = new TxVideoPlayerController(mContext);
                    showItemHolder.niceVideoPlayer.setController(txVideoPlayerController);
                    if (imgList != null && imgList.size() > 0) {
                        UniversalImageLoader.displayImage(mContext, Util.getD2cPicUrl(imgList.get(0)), txVideoPlayerController.getImage());
                    }
                    showItemHolder.niceVideoPlayer.setUp(videoUrl1, null);
                    showItemHolder.videoLayout.setVisibility(View.VISIBLE);
                    showItemHolder.nineGrid.setVisibility(View.GONE);
                    LinearLayout.LayoutParams rl = (LinearLayout.LayoutParams) showItemHolder.videoLayout.getLayoutParams();
                    rl.height = ScreenUtil.getDisplayWidth() * 200 / 355;
                    int timeLong = listEntity.getTimeLength();
                    if (timeLong > 0) {
                        int m = timeLong / 60 % 60;
                        int s = timeLong % 60;
                        StringBuffer sb = new StringBuffer();

                        sb.append(String.format("%2d:", m));

                        sb.append(String.format("%02d", s));
                        txVideoPlayerController.setTime(sb.toString());
                    }
                }

                showItemHolder.tvDate.setText(DateUtil.getFriendlyTime2(listEntity.getCreateDate()));
                if (!Util.isEmpty(listEntity.getStreet())) {
                    showItemHolder.tvLocation.setVisibility(View.VISIBLE);
                    showItemHolder.tvLocation.setText(listEntity.getStreet());
                } else {
                    showItemHolder.tvLocation.setVisibility(View.GONE);
                }
                showItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ShowDetailActivity.class);
                        intent.putExtra("id", listEntity.getId());
                        mContext.startActivity(intent);
                    }
                });
                break;
        }
    }

    private void postDownLoadRequest(long id) {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.DOWN_LOAD_SHOW_URL, id));
        api.setMethod(BaseApi.Method.POST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {

            }
        });
    }

    private void postShareRequest(long id) {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.SHARE_SHOW_URL, id));
        api.setMethod(BaseApi.Method.POST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {

            }
        });
    }

    private void sharePics(List<String> pics, String text, boolean isShare) {
        if (pics == null || pics.size() == 0) {
            Util.showToast(mContext, "没有内容可分享");
            return;
        }
        savePics(pics, text, isShare);
    }

    private File saveFile(Bitmap bigBitmap, int type) {
        if (bigBitmap.getByteCount() > 8485760) {
            float scale = 1;
            while (bigBitmap.getByteCount() > 8485760) {
                scale -= 0.2;
                bigBitmap = BitmapUtils.getScaleBitmap(bigBitmap, scale, scale);
            }
        }
        Canvas canvas = new Canvas(bigBitmap);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        if (canvas.getWidth() <= 100) {
            bigBitmap.recycle();
            return null;
        }
        String root = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera";
        File file = null;
        if (type == 1) {
            file = new File(root, IMAGE_SIZE + ".png");
        } else {
            file = new File(root, IMAGE_SIZE + ".jpg");
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if (type == 1) {
                bigBitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
            } else {
                bigBitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            }

            FileOutputStream os = new FileOutputStream(file);
            os.write(stream.toByteArray());
            stream.flush();
            stream.close();
            os.flush();
            os.close();
            Util.UpdatePic(mContext, file);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (bigBitmap != null) {
                bigBitmap.recycle();
                bigBitmap = null;
            }
            canvas = null;
        }
        return file;
    }

    private void savePics(final List<String> pics, final String text, final boolean isShare) {
        IMAGE_SIZE = 0;
        progressCallBack.letProgressVisible();
        int size = pics.size();
        final List<File> files = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int index = pics.get(i).lastIndexOf(".");
            int type = 1;
            if (index + 2 < pics.get(i).length()) {
                String s = pics.get(i).substring(index + 1, index + 2);
                if (s.equals("j")) {
                    type = 2;
                }
            }
            final int finalType = type;
            Glide.with(mContext).load(Util.getD2cPicUrl(pics.get(i),360,480)).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    if (resource != null) {
                        IMAGE_SIZE++;
                    }
                    Bitmap bitmap = resource.copy(Bitmap.Config.RGB_565, true);
                    files.add(saveFile(bitmap, finalType));
                    if (IMAGE_SIZE == pics.size()) {
                        try {
                            if (!isShare) {
                                Util.showToast(mContext, "图片已保存在相册中");
                                return;
                            }
                            Intent intent = new Intent();
                            ComponentName comp = new ComponentName("com.tencent.mm",
                                    "com.tencent.mm.ui.tools.ShareToTimeLineUI");
                            intent.setComponent(comp);
                            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                            intent.setType("image/*");

                            ArrayList<Uri> imageUris = new ArrayList<Uri>();
                            for (File f : files) {
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                                    imageUris.add(Uri.fromFile(f));
                                } else {
                                    Uri uri = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(mContext.getContentResolver(), f.getAbsolutePath(), f.getName(), null));
                                    imageUris.add(uri);
                                }
                            }
                            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                            intent.putExtra("Kdescription", text);
                            mContext.startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("tag3", e.getMessage());
                            e.printStackTrace();
                        } finally {
                            progressCallBack.letProgressGone();
                        }
                    }
                }
            });
        }
    }

    private void setFocusState(ImageView image, int focus) {
        switch (focus) {
            case 0:
                image.setImageResource(R.mipmap.button_recommend_care_samll);
                break;
            case 1:
                image.setImageResource(R.mipmap.button_recommend_cared_small);
                break;
            case 2:
                image.setImageResource(R.mipmap.button_recommend_mutualcared_small);
                break;
        }
    }

    private void focusComplete(final ShareBean.DataEntity.MemberSharesEntity.ListEntity memberBean, final ShowItemHolder holder) {
        if (Util.loginChecked((Activity) mContext, 999)) {
            FollowApi api = new FollowApi();
            api.setToMemberId(memberBean.getMemberId());
            if (memberBean.getFollow() > 0) {
                memberBean.setFollow(0);
                holder.ivFocus.setImageResource(R.mipmap.button_fashion_care);
                api.setInterPath(Constants.DELETE_MY_FOLLOWS_URL);
                D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                    @Override
                    public void onResponse(BaseBean response) {
                        Util.showToast(mContext, "取消关注成功");
                    }
                });
            } else {
                memberBean.setFollow(1);
                api.setInterPath(Constants.INSERT_MY_FOLLOWS_URL);
                D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ClickFollowBean>() {
                    @Override
                    public void onResponse(ClickFollowBean clickFollowBean) {
                        memberBean.setFollow(clickFollowBean.getData().getFollow());
                        if (clickFollowBean.getData().getFollow() == 0) {
                            holder.ivFocus.setImageResource(R.mipmap.button_fashion_care);
                        } else if (clickFollowBean.getData().getFollow() == 1) {
                            holder.ivFocus.setImageResource(R.mipmap.button_fashion_cared);
                        } else if (clickFollowBean.getData().getFollow() == 2) {
                            holder.ivFocus.setImageResource(R.mipmap.button_fashion_mutualcare);
                        }
                        Util.showToast(mContext, "关注成功");
                        holder.ivFocus.showMsgPop((Activity) mContext, mContext.getString(R.string.label_pop_focus_people));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Util.showToast(mContext, Util.checkErrorType(error));
                    }
                });
            }
        }
    }


    private void shareVideo(ShareBean.DataEntity.MemberSharesEntity.ListEntity listEntity, boolean isShare) {
        String fileFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera";
        String fileName = listEntity.getId() + ".mp4";
        File file = new File(fileFolder, fileName);
        if (file.exists()) {
            Util.showToast(mContext, "视频已保存在相册下,快去分享吧~");
            PackageManager packageManager = mContext.getPackageManager();
            String packageName = "com.tencent.mm";
            Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(packageName);
            if (launchIntentForPackage != null)
                mContext.startActivity(launchIntentForPackage);
            else
                Util.showToast(mContext, "未安装微信");
        } else {
            saveVideo(listEntity.getVideo(), file, isShare);
        }
    }

    private void saveVideoFromNet(final String videoUrl, final File file, boolean isShare) {
        float totalSize;// 文件总大小
        float downloadCount = 0;// 已经下载好的大小
        InputStream inputStream;
        OutputStream outputStream;
        URL url = null;
        try {
            url = new URL(Constants.IMG_HOST + videoUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5 * 1000);
            httpURLConnection.setReadTimeout(5 * 1000);
            httpURLConnection.setUseCaches(false);
            // 获取下载文件的size
            if (httpURLConnection.getResponseCode() == 404) {
                throw new Exception("fail!");
            }
            totalSize = httpURLConnection.getContentLength();
            inputStream = httpURLConnection.getInputStream();
            outputStream = new FileOutputStream(file);// 文件存在则覆盖掉
            byte buffer[] = new byte[1024];
            int readsize = 0;
            while ((readsize = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, readsize);
                downloadCount += readsize;// 时时获取下载到的大小
                int progress = (int) ((downloadCount / totalSize) * 100);//获取进度条
                lastProgress = progress;
                Message message = Message.obtain();
                message.arg1 = progress;
                message.obj = isShare;
                handler.sendMessage(message);
            }
            if (lastProgress == 99) {
                Message message = Message.obtain();
                message.arg1 = 100;
                message.obj = isShare;
                handler.sendMessage(message);
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            inputStream.close();
            outputStream.flush();
            outputStream.close();
            Util.updateVideo(file.getAbsolutePath());
        } catch (Exception e) {
            file.delete();
            Log.e("tag", "错误" + e.getMessage());
        }
    }

    private void saveVideo(final String videoUrl, final File file, final boolean isShare) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                saveVideoFromNet(videoUrl, file, isShare);
            }
        }).start();
    }

    private void focusComplete(final UnFocusMemberBean.DataBean.ActiveMemberBean memberBean, final ShowPopImageView imageView) {
        if (Util.loginChecked((Activity) mContext, 999)) {
            FollowApi api = new FollowApi();
            api.setToMemberId((long) memberBean.getMemberId());
            if (memberBean.getFollow() > 0) {
                api.setInterPath(Constants.DELETE_MY_FOLLOWS_URL);
                D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                    @Override
                    public void onResponse(BaseBean response) {
                        memberBean.setFollow(0);
                        Util.showToast(mContext, "取消关注成功");
                        imageView.setImageResource(R.mipmap.button_recommend_care_samll);
                    }
                });
            } else {
                api.setInterPath(Constants.INSERT_MY_FOLLOWS_URL);
                D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ClickFollowBean>() {
                    @Override
                    public void onResponse(ClickFollowBean clickFollowBean) {
                        memberBean.setFollow(clickFollowBean.getData().getFollow());
                        if (clickFollowBean.getData().getFollow() == 0) {
                            imageView.setImageResource(R.mipmap.button_recommend_care_samll);
                        } else if (clickFollowBean.getData().getFollow() == 1) {
                            imageView.setImageResource(R.mipmap.button_recommend_cared_small);
                        } else if (clickFollowBean.getData().getFollow() == 2) {
                            imageView.setImageResource(R.mipmap.button_recommend_mutualcared_small);
                        }
                        Util.showToast(mContext, "关注成功");
                        imageView.showMsgPop((Activity) mContext, mContext.getString(R.string.label_pop_focus_people));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Util.showToast(mContext, Util.checkErrorType(error));
                    }
                });
            }
        }
    }

    private void likeShow(final ShareBean.DataEntity.MemberSharesEntity.ListEntity data, final ShowItemHolder holder) {
        if (Util.loginChecked((HomeActivity) mContext, Constants.Login.EXPLORE_DETAIL_LOGIN)) {
            if (data != null) {
                if (data.getLiked() > 0) {
                    SimpleApi api = new SimpleApi();
                    api.setMethod(BaseApi.Method.POST);
                    api.setInterPath(String.format(DELETE_SHARE_LIKE, data.getId()));
                    final Drawable nav_dowm = mContext.getResources().getDrawable(R.mipmap.icon_fashion_like);
                    nav_dowm.setBounds(0, 0, nav_dowm.getMinimumWidth(), nav_dowm.getMinimumHeight());
                    D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                        @Override
                        public void onResponse(BaseBean response) {
                            holder.likeNum.setText(String.valueOf(Integer.valueOf(holder.likeNum.getText().toString()) - 1));
                            holder.likeNum.setCompoundDrawables(nav_dowm, null, null, null);
                            data.setLiked(0);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Util.showToast(mContext, Util.checkErrorType(error));
                        }
                    });
                } else {
                    final Drawable nav_up = mContext.getResources().getDrawable(R.mipmap.icon_fashion_liked);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    SimpleApi api = new SimpleApi();
                    api.setMethod(BaseApi.Method.POST);
                    api.setInterPath(String.format(LIKE_SHARE_URL, data.getId()));
                    D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                        @Override
                        public void onResponse(BaseBean response) {
                            //ivLike.setImageResource(R.mipmap.icon_fashion_liked);
                            holder.likeNum.setText(String.valueOf(Integer.valueOf(holder.likeNum.getText().toString()) + 1));
                            holder.likeNum.setCompoundDrawables(nav_up, null, null, null);
                            data.setLiked(1);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                }
            }
        }
    }



    private void setPictures(final ShareBean.DataEntity.MemberSharesEntity.ListEntity listEntity, NineGridView nineGridView, List<String> imgList) {

        ArrayList<ImageInfo> imageInfos = new ArrayList<>();
        if (imgList != null) {
            for (String picUrl : imgList) {
                ImageInfo info = new ImageInfo();
                info.setSingleUrl(Util.getD2cPicUrl(picUrl));//单张图
                info.setFourUrl(Util.getD2cPicUrl(picUrl));//四张图
                info.setThumbnailUrl(Util.getD2cPicUrl(picUrl));//多张缩略图
                info.setBigImageUrl(Util.getD2cPicUrl(picUrl));//大图
                String pic = Util.getD2cPicUrl(picUrl);
                imageInfos.add(info);
            }
        }
        nineGridView.setAdapter(new ClickNineGridViewAdapter(mContext, imageInfos) {
            @Override
            protected void onImageItemClick(Context context, NineGridView nineGridView, int index, List<ImageInfo> imageInfo) {
                if (index == 2 && imageInfo.size() > 3) {
                    Intent intent = new Intent(mContext, ShowDetailActivity.class);
                    intent.putExtra("id", listEntity.getId());
                    mContext.startActivity(intent);
                } else {
                    super.onImageItemClick(context, nineGridView, index, imageInfo);
                }
            }
        }.setFromShow(true));
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : hasFocus || hasBanner ? datas.size() + 1 : datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (hasFocus && (position == 5 || getItemCount() <= 5 && position == getItemCount() - 1)) {
            return 5;
        } else {
            return 6;
        }
    }

}
