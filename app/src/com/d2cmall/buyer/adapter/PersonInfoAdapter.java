package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.LiveAudienceActivity;
import com.d2cmall.buyer.activity.PersonInfoActivity;
import com.d2cmall.buyer.activity.PersonLiveActivity;
import com.d2cmall.buyer.activity.ShowDetailActivity;
import com.d2cmall.buyer.activity.TopicDetailActivity;
import com.d2cmall.buyer.activity.VideoActivity;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.LiveListBean;
import com.d2cmall.buyer.bean.PersonContentBean;
import com.d2cmall.buyer.holder.PersonInfoShowItemHolder;
import com.d2cmall.buyer.holder.PersonLiveHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.Clickable;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.GuideLayout;
import com.d2cmall.buyer.widget.nicevideo.TxVideoPlayerController;
import com.d2cmall.buyer.widget.ninegrid.ImageInfo;
import com.d2cmall.buyer.widget.ninegrid.NineGridView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.d2cmall.buyer.Constants.DELETE_SHARE_LIKE;
import static com.d2cmall.buyer.Constants.LIKE_SHARE_URL;

/**
 * Created by rookie on 2017/9/5.
 */

public class PersonInfoAdapter extends DelegateAdapter.Adapter {
    private Context context;
    private List<Object> list;
    private List<LiveListBean.DataBean.LivesBean.ListBean> liveList;

    public PersonInfoAdapter(Context context, List<Object> list, List<LiveListBean.DataBean.LivesBean.ListBean> liveList) {
        this.context = context;
        this.list = list;
        this.liveList = liveList;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == 0) {
            itemView = LayoutInflater.from(context).inflate(R.layout.layout_person_live, parent, false);
            return new PersonLiveHolder(itemView);
        } else {
            itemView = LayoutInflater.from(context).inflate(R.layout.layout_person_info_show, parent, false);
            return new PersonInfoShowItemHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PersonLiveHolder) {
            PersonLiveHolder personLiveHolder = (PersonLiveHolder) holder;
            personLiveHolder.tvMoreLive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PersonLiveActivity.class);
                    intent.putExtra("memberId", liveList.get(0).getMemberId());
                    context.startActivity(intent);
                }
            });
            int size = liveList.size();
            switch (size) {
                case 1:
                    switch (liveList.get(0).getStatus()) {
                        case -2://预告
                            personLiveHolder.bgRl.setVisibility(View.VISIBLE);
                            personLiveHolder.info.setText(liveList.get(0).getTitle());
                            //personLiveHolder.timeTv.setText(DateUtil.getFriendlyTime2(liveList.get(0).getPreviewDate()));
                            UniversalImageLoader.displayImage(context, liveList.get(0).getCover(), personLiveHolder.image, R.mipmap.ic_logo_empty1, R.mipmap.ic_logo_empty1);
                            break;
                        case 8://回放
                            personLiveHolder.llSingleBoss.setVisibility(View.VISIBLE);
                            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) personLiveHolder.ivSingle.getLayoutParams();
                            params.height = (ScreenUtil.getDeviceSize(context).x - ScreenUtil.dip2px(32));
                            personLiveHolder.ivSingle.setLayoutParams(params);
                            UniversalImageLoader.displayImage(context, liveList.get(0).getCover(), personLiveHolder.ivSingle, R.mipmap.ic_logo_empty1, R.mipmap.ic_logo_empty1);
                            personLiveHolder.tvStatusSingle.setVisibility(View.VISIBLE);
                            personLiveHolder.tvStatusSingle.setText("回放");
                            //personLiveHolder.tvNumLook.setText("已有" + liveList.get(0).getWatched() + "观看");
                            personLiveHolder.tvDescribe.setText(liveList.get(0).getTitle());
                            personLiveHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(context, VideoActivity.class);
                                    intent.putExtra("bean", liveList.get(0));
                                    context.startActivity(intent);
                                }
                            });
                            break;
                        case 4://直播
                            personLiveHolder.llSingleBoss.setVisibility(View.VISIBLE);
                            RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) personLiveHolder.ivSingle.getLayoutParams();
                            params2.height = (ScreenUtil.getDeviceSize(context).x - ScreenUtil.dip2px(32));
                            personLiveHolder.ivSingle.setLayoutParams(params2);
                            UniversalImageLoader.displayImage(context, liveList.get(0).getCover(), personLiveHolder.ivSingle, R.mipmap.ic_logo_empty1, R.mipmap.ic_logo_empty1);
                            personLiveHolder.ivLive.setVisibility(View.VISIBLE);
                           // personLiveHolder.tvNumLook.setText("有" + liveList.get(0).getPlayerCount() + "正在观看");
                            personLiveHolder.tvDescribe.setText(liveList.get(0).getTitle());
                            personLiveHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (GuideLayout.getInstance(context).isAddView()) {
                                        GuideLayout.getInstance(context).hide();
                                    } else {
                                        Intent intent=new Intent(context, LiveAudienceActivity.class);
                                        intent.putExtra("bean",liveList.get(0));
                                        context.startActivity(intent);
                                    }
                                }
                            });
                            break;
                    }
                    break;
                case 2:
                    personLiveHolder.llThree.setVisibility(View.VISIBLE);
                    personLiveHolder.rlThree.setVisibility(View.GONE);
                    UniversalImageLoader.displayImage(context, liveList.get(0).getCover(), personLiveHolder.ivOne, R.mipmap.ic_logo_empty1, R.mipmap.ic_logo_empty1);
                    UniversalImageLoader.displayImage(context, liveList.get(1).getCover(), personLiveHolder.ivTwo, R.mipmap.ic_logo_empty1, R.mipmap.ic_logo_empty1);
                    personLiveHolder.tvTitleOne.setText(liveList.get(0).getTitle());
                    personLiveHolder.tvTitleTwo.setText(liveList.get(1).getTitle());
                    personLiveHolder.tvStatusOne.setText(getStatus(liveList.get(0).getStatus()));
                    personLiveHolder.tvStatusTwo.setText(getStatus(liveList.get(1).getStatus()));
                    personLiveHolder.rlOne.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent;
                            switch (liveList.get(0).getStatus()) {
                                case 8:
                                    intent = new Intent(context, VideoActivity.class);
                                    intent.putExtra("bean", liveList.get(0));
                                    context.startActivity(intent);
                                    break;
                                case -2:

                                    break;
                                case 4:
                                    if (GuideLayout.getInstance(context).isAddView()) {
                                        GuideLayout.getInstance(context).hide();
                                    } else {
                                        Intent intent1=new Intent(context, LiveAudienceActivity.class);
                                        intent1.putExtra("bean",liveList.get(0));
                                        context.startActivity(intent1);
                                    }
                                    break;
                            }

                        }
                    });
                    personLiveHolder.rlTwo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent;
                            switch (liveList.get(1).getStatus()) {
                                case 8:
                                    intent = new Intent(context, VideoActivity.class);
                                    intent.putExtra("bean", liveList.get(1));
                                    context.startActivity(intent);
                                    break;
                                case -2:

                                    break;
                                case 4:
                                    if (GuideLayout.getInstance(context).isAddView()) {
                                        GuideLayout.getInstance(context).hide();
                                    } else {
                                        Intent intent1=new Intent(context, LiveAudienceActivity.class);
                                        intent1.putExtra("bean",liveList.get(1));
                                        context.startActivity(intent1);
                                    }
                                    break;
                            }
                        }
                    });
                    break;
                case 3:
                    personLiveHolder.llThree.setVisibility(View.VISIBLE);
                    UniversalImageLoader.displayImage(context, liveList.get(0).getCover(), personLiveHolder.ivOne, R.mipmap.ic_logo_empty1, R.mipmap.ic_logo_empty1);
                    UniversalImageLoader.displayImage(context, liveList.get(1).getCover(), personLiveHolder.ivTwo, R.mipmap.ic_logo_empty1, R.mipmap.ic_logo_empty1);
                    UniversalImageLoader.displayImage(context, liveList.get(2).getCover(), personLiveHolder.ivThree, R.mipmap.ic_logo_empty1, R.mipmap.ic_logo_empty1);
                    personLiveHolder.tvTitleOne.setText(liveList.get(0).getTitle());
                    personLiveHolder.tvTitleTwo.setText(liveList.get(1).getTitle());
                    personLiveHolder.tvTitleThree.setText(liveList.get(2).getTitle());
                    personLiveHolder.tvStatusOne.setText(getStatus(liveList.get(0).getStatus()));
                    personLiveHolder.tvStatusTwo.setText(getStatus(liveList.get(1).getStatus()));
                    personLiveHolder.tvStatusThree.setText(getStatus(liveList.get(2).getStatus()));
                    personLiveHolder.rlOne.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent;
                            switch (liveList.get(0).getStatus()) {
                                case 8:
                                    intent = new Intent(context, VideoActivity.class);
                                    intent.putExtra("bean", liveList.get(0));
                                    context.startActivity(intent);
                                    break;
                                case -2:

                                    break;
                                case 4:
                                    if (GuideLayout.getInstance(context).isAddView()) {
                                        GuideLayout.getInstance(context).hide();
                                    } else {
                                        Intent intent1=new Intent(context, LiveAudienceActivity.class);
                                        intent1.putExtra("bean",liveList.get(0));
                                        context.startActivity(intent1);
                                    }
                                    break;
                            }

                        }
                    });
                    personLiveHolder.rlTwo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent;
                            switch (liveList.get(1).getStatus()) {
                                case 8:
                                    intent = new Intent(context, VideoActivity.class);
                                    intent.putExtra("bean", liveList.get(1));
                                    context.startActivity(intent);
                                    break;
                                case -2:

                                    break;
                                case 4:
                                    if (GuideLayout.getInstance(context).isAddView()) {
                                        GuideLayout.getInstance(context).hide();
                                    } else {
                                        Intent intent1=new Intent(context, LiveAudienceActivity.class);
                                        intent1.putExtra("bean",liveList.get(1));
                                        context.startActivity(intent1);
                                    }
                                    break;
                            }
                        }
                    });
                    personLiveHolder.rlThree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent;
                            switch (liveList.get(2).getStatus()) {
                                case 8:
                                    intent = new Intent(context, VideoActivity.class);
                                    intent.putExtra("bean", liveList.get(2));
                                    context.startActivity(intent);
                                    break;
                                case -2:

                                    break;
                                case 4:
                                    if (GuideLayout.getInstance(context).isAddView()) {
                                        GuideLayout.getInstance(context).hide();
                                    } else {
                                        Intent intent1=new Intent(context, LiveAudienceActivity.class);
                                        intent1.putExtra("bean",liveList.get(2));
                                        context.startActivity(intent1);
                                    }
                                    break;
                            }
                        }
                    });
                    break;
            }
        } else if (holder instanceof PersonInfoShowItemHolder) {
            final PersonInfoShowItemHolder showItemHolder = (PersonInfoShowItemHolder) holder;
            final PersonContentBean.DataBean.MemberSharesBean.ListBean data = (PersonContentBean.DataBean.MemberSharesBean.ListBean) list.get(position);
            showItemHolder.tvCreateTime.setText(DateUtil.getFriendlyTime2(data.getCreateDate()));
            showItemHolder.commentNum.setText(String.valueOf(data.getCommentCount()));
            showItemHolder.likeNum.setText(String.valueOf(data.getLikeMeCount()));
            if (data.getVerifyDate()==null) {
                showItemHolder.infoTv.setText(String.valueOf(data.getWatchCount()));
            } else {
                if (Util.getFakeWatchCount(data.getVerifyDate()) + data.getWatchCount() > 10000) {
                    DecimalFormat df = new DecimalFormat("0.0");
                    showItemHolder.infoTv.setText(df.format(((int) Util.getFakeWatchCount(data.getVerifyDate()) + data.getWatchCount()) / (float) 10000.0) + "万");
                } else {
                    if (Util.getFakeWatchCount(data.getVerifyDate()) + data.getWatchCount() < 0) {
                        showItemHolder.infoTv.setText(String.valueOf(data.getWatchCount()));
                    } else {
                        showItemHolder.infoTv.setText(String.valueOf(Util.getFakeWatchCount(data.getVerifyDate()) + data.getWatchCount()));
                    }
                }
            }
            if (data.getLiked() > 0) {
                Drawable nav_up = context.getResources().getDrawable(R.mipmap.icon_fashion_liked);
                nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                showItemHolder.likeNum.setCompoundDrawables(nav_up, null, null, null);
            } else {
                Drawable nav_up = context.getResources().getDrawable(R.mipmap.icon_fashion_like);
                nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                showItemHolder.likeNum.setCompoundDrawables(nav_up, null, null, null);
            }
            showItemHolder.likeNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    likeShow(data, showItemHolder);
                }
            });
            showItemHolder.tvNickName.setText(data.getMemberName());
            if (!Util.isEmpty(data.getDescription()) || !Util.isEmpty(data.getTopicName())) {
                showItemHolder.contentTv.setVisibility(View.VISIBLE);
                StringBuilder builder = new StringBuilder();
                if (!Util.isEmpty(data.getTopicName())) {
                    int end = data.getTopicName().length() + 2;
                    builder.append("#" + data.getTopicName() + "#" + " ");
                    if (!Util.isEmpty(data.getDescription())) {
                        builder.append(data.getDescription());
                    }
                    SpannableString sb = new SpannableString(builder.toString());
                    sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, data.getTopicName().length() + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //粗体
                    sb.setSpan(new Clickable(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, TopicDetailActivity.class);
                            intent.putExtra("id", (long) data.getTopicId());
                            context.startActivity(intent);
                        }
                    }), 0, data.getTopicName().length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    sb.setSpan(new ForegroundColorSpan(Color.BLACK), 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    showItemHolder.contentTv.setMovementMethod(LinkMovementMethod.getInstance());
                    showItemHolder.contentTv.setText(sb);
                } else {
                    showItemHolder.contentTv.setText(data.getDescription());
                }
            } else {
                showItemHolder.contentTv.setVisibility(View.GONE);
            }

            String videoUrl1 = data.getVideo();
            List<String> imgList = data.getPics();
            if (Util.isEmpty(videoUrl1)) {
                showItemHolder.nineGrid.setVisibility(View.VISIBLE);
                setPictures(showItemHolder.nineGrid, imgList);
                showItemHolder.videoLayout.setVisibility(View.GONE);
            } else {
                if (!videoUrl1.startsWith("http")) {
                    videoUrl1 = Constants.IMG_HOST + videoUrl1;
                }
                    /*TxVideoPlayerController txVideoPlayerController=new TxVideoPlayerController(mContext);
                    showItemHolder.niceVideoPlayer.setController(txVideoPlayerController);*/
                TxVideoPlayerController txVideoPlayerController = new TxVideoPlayerController(context);
                showItemHolder.niceVideoPlayer.setController(txVideoPlayerController);
                if (imgList != null && imgList.size() > 0) {
                    UniversalImageLoader.displayImage(context, Util.getD2cPicUrl(imgList.get(0)), txVideoPlayerController.getImage());
                }
                showItemHolder.niceVideoPlayer.setUp(videoUrl1, null);
                showItemHolder.videoLayout.setVisibility(View.VISIBLE);
                showItemHolder.nineGrid.setVisibility(View.GONE);
                LinearLayout.LayoutParams rl = (LinearLayout.LayoutParams) showItemHolder.videoLayout.getLayoutParams();
                rl.height = ScreenUtil.getDisplayWidth() * 200 / 355;
                int timeLong = data.getTimeLength();
                if (timeLong > 0) {
                    int m = timeLong / 60 % 60;
                    int s = timeLong % 60;
                    StringBuffer sb = new StringBuffer();

                    sb.append(String.format("%2d:", m));

                    sb.append(String.format("%02d", s));
                    txVideoPlayerController.setTime(sb.toString());
                }
            }

            showItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowDetailActivity.class);
                    intent.putExtra("id", (long) data.getId());
                    context.startActivity(intent);
                }
            });
        }
    }

    private void likeShow(final PersonContentBean.DataBean.MemberSharesBean.ListBean data, final PersonInfoShowItemHolder holder) {
        if (Util.loginChecked((PersonInfoActivity) context, Constants.Login.EXPLORE_DETAIL_LOGIN)) {
            if (data != null) {
                if (data.getLiked() > 0) {
                    SimpleApi api = new SimpleApi();
                    api.setMethod(BaseApi.Method.POST);
                    api.setInterPath(String.format(DELETE_SHARE_LIKE, data.getId()));
                    final Drawable nav_dowm = context.getResources().getDrawable(R.mipmap.icon_fashion_like);
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
                            Util.showToast(context, Util.checkErrorType(error));
                        }
                    });
                } else {
                    final Drawable nav_up = context.getResources().getDrawable(R.mipmap.icon_fashion_liked);
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

    private void setPictures(NineGridView nineGridView, List<String> imgList) {

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
        nineGridView.setAdapter(new ClickNineGridViewAdapter(context, imageInfos));
    }

    private String getStatus(int status) {
        if (status == -2) {
            return "预告";
        } else if (status == 4) {
            return "直播";
        } else {
            return "回放";
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof Integer) {
            return 0;
        } else {
            return 1;
        }
    }
}
