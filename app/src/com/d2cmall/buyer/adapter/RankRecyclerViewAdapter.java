package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.d2cmall.buyer.activity.PersonInfoActivity;
import com.d2cmall.buyer.activity.RankActivity;
import com.d2cmall.buyer.activity.ShowDetailActivity;
import com.d2cmall.buyer.activity.TopicDetailActivity;
import com.d2cmall.buyer.activity.VideoActivity;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.FollowApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ClickFollowBean;
import com.d2cmall.buyer.bean.LiveListBean;
import com.d2cmall.buyer.bean.RankDesignerBean;
import com.d2cmall.buyer.bean.RankMemberBean;
import com.d2cmall.buyer.bean.RankPicBean;
import com.d2cmall.buyer.bean.RankVideoBean;
import com.d2cmall.buyer.bean.ShareBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.LiveItemHolder;
import com.d2cmall.buyer.holder.ShowDetailTopHolder;
import com.d2cmall.buyer.holder.ShowItemHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.Clickable;
import com.d2cmall.buyer.listener.ProgressCallBack;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.RoundedImageView;
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

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.d2cmall.buyer.Constants.DELETE_SHARE_LIKE;
import static com.d2cmall.buyer.Constants.LIKE_SHARE_URL;

/**
 * Created by rookie on 2017/8/10.
 */

public class RankRecyclerViewAdapter extends DelegateAdapter.Adapter<RecyclerView.ViewHolder> {

    private List<Object> list;
    private Context context;
    private LayoutInflater layoutInflater;
    private int id;
    private int IMAGE_SIZE = 0;
    private ProgressCallBack progressCallBack;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (progressCallBack != null) {
                progressCallBack.setProgress(msg.arg1, (Boolean) msg.obj);
            }
        }
    };

    public RankRecyclerViewAdapter(List<Object> list, Context context, int id) {
        this.list = list;
        this.context = context;
        this.id = id;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public void setProgressCallBack(ProgressCallBack progressCallBack) {
        this.progressCallBack = progressCallBack;
    }

    @Override
    public int getItemViewType(int position) {
        switch (id) {
            case 0://达人
            case 1://设计师
                switch (position) {
                    case 0:
                        return 0;
                    case 1:
                        return 1;
                    case 2:
                        return 2;
                    default:
                        return 3;
                }
            case 2://图文
            case 3://视屏
                return 4;
            case 4:
                return 5;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case 0:
                itemView = layoutInflater.inflate(R.layout.layout_rank_person_top, parent, false);
                TopPersonViewHolder holder = new TopPersonViewHolder(itemView);
                holder.ivPersonHeadBorder.setImageResource(R.mipmap.icon_top1);
                return holder;
            case 1:
                itemView = layoutInflater.inflate(R.layout.layout_rank_person_top, parent, false);
                TopPersonViewHolder holder1 = new TopPersonViewHolder(itemView);
                holder1.ivPersonHeadBorder.setImageResource(R.mipmap.icon_top2);
                return holder1;
            case 2:
                itemView = layoutInflater.inflate(R.layout.layout_rank_person_top, parent, false);
                TopPersonViewHolder holder2 = new TopPersonViewHolder(itemView);
                holder2.line.setVisibility(View.VISIBLE);
                return holder2;
            case 3:
                itemView = layoutInflater.inflate(R.layout.layout_rank_person_common, parent, false);
                return new CommonPersonViewHolder(itemView);
            case 4:
                itemView = layoutInflater.inflate(R.layout.layout_show_item, parent, false);
                return new ShowItemHolder(itemView);
            case 5:
                itemView = layoutInflater.inflate(R.layout.layout_live_item, parent, false);
                return new LiveItemHolder(itemView);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(context);
        if (holder instanceof TopPersonViewHolder && id == 0) {//达人
            final TopPersonViewHolder topPersonViewHolder = (TopPersonViewHolder) holder;
            final RankMemberBean.DataBean.HotMemberBean data = (RankMemberBean.DataBean.HotMemberBean) list.get(position);
            topPersonViewHolder.tvRanking.setText(String.valueOf(position + 1));
            topPersonViewHolder.tvPersonHot.setText(String.valueOf(data.getHot()));
            topPersonViewHolder.tvPersonName.setText(data.getNickName());
            switch (data.getFollow()) {
                case 0:
                    topPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_care);
                    break;
                case 1:
                    topPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_cared);
                    break;
                case 2:
                    topPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_mutualcare);
                    break;
            }
            if (user != null) {
                if (data.getMemberId() == user.getMemberId()) {
                    topPersonViewHolder.ivFocus.setVisibility(View.GONE);
                }
            }
            UniversalImageLoader.displayImage(context, data.getHeadPic(), topPersonViewHolder.ivPersonHead, R.mipmap.ic_default_avatar);
            topPersonViewHolder.ivPersonHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PersonInfoActivity.class);
                    intent.putExtra("memberId", (long) data.getMemberId());
                    context.startActivity(intent);
                }
            });
            topPersonViewHolder.ivFocus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FollowApi api = new FollowApi();
                    api.setToMemberId(data.getMemberId());
                    if (data.getFollow() > 0) {
                        api.setInterPath(Constants.DELETE_MY_FOLLOWS_URL);
                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                            @Override
                            public void onResponse(BaseBean response) {
                                Util.showToast(context, "取消关注成功");
                                data.setFollow(0);
                                topPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_care);
                            }
                        });
                    } else {
                        api.setInterPath(Constants.INSERT_MY_FOLLOWS_URL);
                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ClickFollowBean>() {
                            @Override
                            public void onResponse(ClickFollowBean clickFollowBean) {
                                Util.showToast(context, "关注成功");
                                topPersonViewHolder.ivFocus.showMsgPop((Activity) context, context.getString(R.string.label_pop_focus_people));
                                topPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_cared);
                                data.setFollow(clickFollowBean.getData().getFollow());
                                if (clickFollowBean.getData().getFollow() == 0) {
                                    topPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_care);
                                } else if (clickFollowBean.getData().getFollow() == 1) {
                                    topPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_cared);
                                } else if (clickFollowBean.getData().getFollow() == 2) {
                                    topPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_mutualcare);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Util.showToast(context, Util.checkErrorType(error));
                            }
                        });
                    }
                }
            });
        } else if (holder instanceof CommonPersonViewHolder && id == 0) {//达人普通
            final CommonPersonViewHolder commonPersonViewHolder = (CommonPersonViewHolder) holder;
            final RankMemberBean.DataBean.HotMemberBean data = (RankMemberBean.DataBean.HotMemberBean) list.get(position);
            commonPersonViewHolder.tvRanking.setText(String.valueOf(position + 1));
            commonPersonViewHolder.tvPersonHot.setText(String.valueOf(data.getHot()));
            commonPersonViewHolder.tvPersonName.setText(data.getNickName());
            switch (data.getFollow()) {
                case 0:
                    commonPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_care);
                    break;
                case 1:
                    commonPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_cared);
                    break;
                case 2:
                    commonPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_mutualcare);
                    break;
            }
            if (user != null) {
                if (data.getMemberId() == user.getMemberId()) {
                    commonPersonViewHolder.ivFocus.setVisibility(View.GONE);
                }
            }
            UniversalImageLoader.displayImage(context, data.getHeadPic(), commonPersonViewHolder.ivPersonHead, R.mipmap.ic_default_avatar);
            commonPersonViewHolder.ivPersonHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PersonInfoActivity.class);
                    intent.putExtra("memberId", (long) data.getMemberId());
                    context.startActivity(intent);
                }
            });
            commonPersonViewHolder.ivFocus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FollowApi api = new FollowApi();
                    api.setToMemberId(data.getMemberId());
                    if (data.getFollow() > 0) {
                        api.setInterPath(Constants.DELETE_MY_FOLLOWS_URL);
                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                            @Override
                            public void onResponse(BaseBean response) {
                                data.setFollow(0);
                                commonPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_care);
                                Util.showToast(context, "取消关注成功");
                            }
                        });
                    } else {
                        api.setInterPath(Constants.INSERT_MY_FOLLOWS_URL);
                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ClickFollowBean>() {
                            @Override
                            public void onResponse(ClickFollowBean clickFollowBean) {
                                commonPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_cared);
                                data.setFollow(clickFollowBean.getData().getFollow());
                                if (clickFollowBean.getData().getFollow() == 0) {
                                    commonPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_care);
                                } else if (clickFollowBean.getData().getFollow() == 1) {
                                    commonPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_cared);
                                } else if (clickFollowBean.getData().getFollow() == 2) {
                                    commonPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_mutualcare);
                                }
                                Util.showToast(context, "关注成功");
                                commonPersonViewHolder.ivFocus.showMsgPop((Activity) context, context.getString(R.string.label_pop_focus_people));
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Util.showToast(context, Util.checkErrorType(error));
                            }
                        });
                    }
                }
            });
        } else if (holder instanceof TopPersonViewHolder && id == 1) {//设计师
            final TopPersonViewHolder topPersonViewHolder = (TopPersonViewHolder) holder;
            final RankDesignerBean.DataBean.HotDesignerBean data = (RankDesignerBean.DataBean.HotDesignerBean) list.get(position);
            topPersonViewHolder.tvRanking.setText(String.valueOf(position + 1));
            topPersonViewHolder.tvPersonHot.setText(String.valueOf(data.getHot()));
            topPersonViewHolder.tvPersonName.setText(data.getNickName());
            switch (data.getFollow()) {
                case 0:
                    topPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_care);
                    break;
                case 1:
                    topPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_cared);
                    break;
                case 2:
                    topPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_mutualcare);
                    break;
            }
            if (user != null) {
                if (data.getMemberId() == user.getMemberId()) {
                    topPersonViewHolder.ivFocus.setVisibility(View.GONE);
                }
            }
            UniversalImageLoader.displayImage(context, data.getHeadPic(), topPersonViewHolder.ivPersonHead, R.mipmap.ic_default_avatar);
            topPersonViewHolder.ivPersonHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PersonInfoActivity.class);
                    intent.putExtra("memberId", (long) data.getMemberId());
                    context.startActivity(intent);
                }
            });
            topPersonViewHolder.ivFocus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FollowApi api = new FollowApi();
                    api.setToMemberId(data.getMemberId());
                    if (data.getFollow() > 0) {
                        api.setInterPath(Constants.DELETE_MY_FOLLOWS_URL);
                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                            @Override
                            public void onResponse(BaseBean response) {
                                data.setFollow(0);
                                topPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_care);
                                Util.showToast(context, "取消关注成功");
                            }
                        });
                    } else {
                        api.setInterPath(Constants.INSERT_MY_FOLLOWS_URL);
                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ClickFollowBean>() {
                            @Override
                            public void onResponse(ClickFollowBean clickFollowBean) {
                                topPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_cared);
                                data.setFollow(clickFollowBean.getData().getFollow());
                                if (clickFollowBean.getData().getFollow() == 0) {
                                    topPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_care);
                                } else if (clickFollowBean.getData().getFollow() == 1) {
                                    topPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_cared);
                                } else if (clickFollowBean.getData().getFollow() == 2) {
                                    topPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_mutualcare);
                                }
                                Util.showToast(context, "关注成功");
                                topPersonViewHolder.ivFocus.showMsgPop((Activity) context, context.getString(R.string.label_pop_focus_people));
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Util.showToast(context, Util.checkErrorType(error));
                            }
                        });
                    }
                }
            });
        } else if (holder instanceof CommonPersonViewHolder && id == 1) {//设计师普通
            final CommonPersonViewHolder commonPersonViewHolder = (CommonPersonViewHolder) holder;
            final RankDesignerBean.DataBean.HotDesignerBean data = (RankDesignerBean.DataBean.HotDesignerBean) list.get(position);
            commonPersonViewHolder.tvRanking.setText(String.valueOf(position + 1));
            commonPersonViewHolder.tvPersonHot.setText(String.valueOf(data.getHot()));
            commonPersonViewHolder.tvPersonName.setText(data.getNickName());
            switch (data.getFollow()) {
                case 0:
                    commonPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_care);
                    break;
                case 1:
                    commonPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_cared);
                    break;
                case 2:
                    commonPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_mutualcare);
                    break;
            }
            if (user != null) {
                if (data.getMemberId() == user.getMemberId()) {
                    commonPersonViewHolder.ivFocus.setVisibility(View.GONE);
                }
            }
            UniversalImageLoader.displayImage(context, data.getHeadPic(), commonPersonViewHolder.ivPersonHead, R.mipmap.ic_default_avatar);
            commonPersonViewHolder.ivPersonHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PersonInfoActivity.class);
                    intent.putExtra("memberId", (long) data.getMemberId());
                    context.startActivity(intent);
                }
            });
            commonPersonViewHolder.ivFocus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FollowApi api = new FollowApi();
                    api.setToMemberId(data.getMemberId());
                    if (data.getFollow() > 0) {
                        api.setInterPath(Constants.DELETE_MY_FOLLOWS_URL);
                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                            @Override
                            public void onResponse(BaseBean response) {
                                data.setFollow(0);
                                commonPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_care);
                                Util.showToast(context, "取消关注成功");
                            }
                        });
                    } else {
                        api.setInterPath(Constants.INSERT_MY_FOLLOWS_URL);
                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ClickFollowBean>() {
                            @Override
                            public void onResponse(ClickFollowBean clickFollowBean) {
                                commonPersonViewHolder.ivFocus.setImageResource(R.mipmap.button_fashion_cared);
                                data.setFollow(1);
                                Util.showToast(context, "关注成功");
                                commonPersonViewHolder.ivFocus.showMsgPop((Activity) context, context.getString(R.string.label_pop_focus_people));
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Util.showToast(context, Util.checkErrorType(error));
                            }
                        });
                    }
                }
            });
        } else if (holder instanceof ShowItemHolder && id == 2) {//图文
            final RankPicBean.DataBean.HotPicBean.ListBean data = (RankPicBean.DataBean.HotPicBean.ListBean) list.get(position);
            final ShowItemHolder showItemHolder = (ShowItemHolder) holder;

            if (user != null) {
                if (data.getMemberId() == user.getMemberId()) {
                    showItemHolder.ivFocus.setVisibility(View.GONE);
                }
            }
            if (user != null && user.getPartnerId() > 0) {
                //说明是分销者
                showItemHolder.tvDownNum.setVisibility(View.VISIBLE);
                showItemHolder.tvSharaNum.setVisibility(View.VISIBLE);
                showItemHolder.tvSharaNum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        postShareRequest((long) data.getId());
                        sharePics(data.getPics(), data.getDescription(), true);
                    }
                });
                showItemHolder.tvDownNum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        postDownLoadRequest((long) data.getId());
                        sharePics(data.getPics(), data.getDescription(), false);
                    }
                });
            } else {
                showItemHolder.tvDownNum.setVisibility(View.INVISIBLE);
                showItemHolder.tvSharaNum.setVisibility(View.INVISIBLE);
            }

            showItemHolder.timeTv.setText(DateUtil.getFriendlyTime2(data.getCreateDate()));
            showItemHolder.imgAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PersonInfoActivity.class);
                    intent.putExtra("memberId", (long) data.getMemberId());
                    context.startActivity(intent);
                }
            });
            showItemHolder.tvDate.setText(DateUtil.getFriendlyTime2(data.getCreateDate()));
            showItemHolder.commentNum.setText(String.valueOf(data.getCommentCount()));
            switch (data.getFollow()) {
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
                    FollowApi api = new FollowApi();
                    api.setToMemberId(data.getMemberId());
                    if (data.getFollow() > 0) {
                        api.setInterPath(Constants.DELETE_MY_FOLLOWS_URL);
                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                            @Override
                            public void onResponse(BaseBean response) {
                                Util.showToast(context, "取消关注成功");
                                data.setFollow(0);
                                showItemHolder.ivFocus.setImageResource(R.mipmap.button_fashion_care);
                            }
                        });
                    } else {
                        api.setInterPath(Constants.INSERT_MY_FOLLOWS_URL);
                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ClickFollowBean>() {
                            @Override
                            public void onResponse(ClickFollowBean clickFollowBean) {
                                Util.showToast(context, "关注成功");
                                showItemHolder.ivFocus.showMsgPop((Activity) context, context.getString(R.string.label_pop_focus_people));
                                data.setFollow(clickFollowBean.getData().getFollow());
                                if (clickFollowBean.getData().getFollow() == 0) {
                                    showItemHolder.ivFocus.setImageResource(R.mipmap.button_fashion_care);
                                } else if (clickFollowBean.getData().getFollow() == 1) {
                                    showItemHolder.ivFocus.setImageResource(R.mipmap.button_fashion_cared);
                                } else if (clickFollowBean.getData().getFollow() == 2) {
                                    showItemHolder.ivFocus.setImageResource(R.mipmap.button_fashion_mutualcare);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Util.showToast(context, Util.checkErrorType(error));
                            }
                        });
                    }
                }
            });
            showItemHolder.likeNum.setText(String.valueOf(data.getLikeMeCount()));
            if (data.getVerifyDate() == null) {
                showItemHolder.infoTv.setText(String.valueOf(data.getWatchCount()));
            } else {
                if (Util.getFakeWatchCount(data.getVerifyDate()) + data.getWatchCount() > 10000) {
                    DecimalFormat df = new DecimalFormat("0.0");
                    showItemHolder.infoTv.setText(df.format(((int) Util.getFakeWatchCount(data.getVerifyDate()) + data.getWatchCount()) / (float) 10000.0) + "万");
                } else {
                    showItemHolder.infoTv.setText(String.valueOf(Util.getFakeWatchCount(data.getVerifyDate()) + data.getWatchCount()));
                }
            }
            int fakeDownCount = Util.getFakeDownLoadNum((int) (Util.getFakeWatchCount(data.getVerifyDate()) + data.getWatchCount()), data.getDownCount());
            showItemHolder.tvDownNum.setText(String.valueOf(fakeDownCount));

            int fakeShareCount = Util.getFakeShareNum((int) (Util.getFakeWatchCount(data.getVerifyDate()) + data.getWatchCount()), data.getShareCount());
            showItemHolder.tvSharaNum.setText(String.valueOf(fakeShareCount));
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
            UniversalImageLoader.displayRoundImage(context, Util.getD2cPicUrl(data.getMemberHead()), showItemHolder.imgAvatar, R.mipmap.ic_default_avatar);
            showItemHolder.nameTv.setText(data.getMemberName());
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
                setPictures(data, showItemHolder.nineGrid, imgList);
                showItemHolder.videoLayout.setVisibility(View.GONE);
            }
            showItemHolder.tvDate.setText(DateUtil.getFriendlyTime2(data.getCreateDate()));
            if (!Util.isEmpty(data.getStreet())) {
                showItemHolder.tvLocation.setVisibility(View.VISIBLE);
                showItemHolder.tvLocation.setText(data.getStreet());
            } else {
                showItemHolder.tvLocation.setVisibility(View.GONE);
            }
            showItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowDetailActivity.class);
                    intent.putExtra("id", (long) data.getId());
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof ShowItemHolder && id == 3) {//视屏
            final RankVideoBean.DataBean.HotVideoBean.ListBean data = (RankVideoBean.DataBean.HotVideoBean.ListBean) list.get(position);
            final ShowItemHolder showItemHolder = (ShowItemHolder) holder;

            if (user != null) {
                if (data.getMemberId() == user.getMemberId()) {
                    showItemHolder.ivFocus.setVisibility(View.GONE);
                }
            }
            if (user != null && user.getPartnerId() > 0) {
                //说明是分销者
                showItemHolder.tvDownNum.setVisibility(View.VISIBLE);
                showItemHolder.tvSharaNum.setVisibility(View.VISIBLE);
                showItemHolder.tvSharaNum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        postShareRequest((long) data.getId());
                        shareVideo(data, true);
                    }
                });
                showItemHolder.tvDownNum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        postDownLoadRequest((long) data.getId());
                        shareVideo(data, false);
                    }
                });
            } else {
                showItemHolder.tvDownNum.setVisibility(View.INVISIBLE);
                showItemHolder.tvSharaNum.setVisibility(View.INVISIBLE);
            }

            showItemHolder.timeTv.setText(DateUtil.getFriendlyTime2(data.getCreateDate()));
            showItemHolder.imgAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PersonInfoActivity.class);
                    intent.putExtra("memberId", (long) data.getMemberId());
                    context.startActivity(intent);
                }
            });
            showItemHolder.tvDate.setText(DateUtil.getFriendlyTime2(data.getCreateDate()));
            showItemHolder.commentNum.setText(String.valueOf(data.getCommentCount()));
            switch (data.getFollow()) {
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
                    FollowApi api = new FollowApi();
                    api.setToMemberId(data.getMemberId());
                    if (data.getFollow() > 0) {
                        api.setInterPath(Constants.DELETE_MY_FOLLOWS_URL);
                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                            @Override
                            public void onResponse(BaseBean response) {
                                Util.showToast(context, "取消关注成功");
                                data.setFollow(0);
                                showItemHolder.ivFocus.setImageResource(R.mipmap.button_fashion_care);
                            }
                        });
                    } else {
                        api.setInterPath(Constants.INSERT_MY_FOLLOWS_URL);
                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ClickFollowBean>() {
                            @Override
                            public void onResponse(ClickFollowBean clickFollowBean) {
                                Util.showToast(context, "关注成功");
                                showItemHolder.ivFocus.showMsgPop((Activity) context, context.getString(R.string.label_pop_focus_people));
                                data.setFollow(clickFollowBean.getData().getFollow());
                                if (clickFollowBean.getData().getFollow() == 0) {
                                    showItemHolder.ivFocus.setImageResource(R.mipmap.button_fashion_care);
                                } else if (clickFollowBean.getData().getFollow() == 1) {
                                    showItemHolder.ivFocus.setImageResource(R.mipmap.button_fashion_cared);
                                } else if (clickFollowBean.getData().getFollow() == 2) {
                                    showItemHolder.ivFocus.setImageResource(R.mipmap.button_fashion_mutualcare);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Util.showToast(context, Util.checkErrorType(error));
                            }
                        });
                    }
                }
            });
            showItemHolder.likeNum.setText(String.valueOf(data.getLikeMeCount()));
            if (data.getVerifyDate() == null) {
                showItemHolder.infoTv.setText(String.valueOf(data.getWatchCount()));
            } else {
                if (Util.getFakeWatchCount(data.getVerifyDate()) + data.getWatchCount() > 10000) {
                    DecimalFormat df = new DecimalFormat("0.0");
                    showItemHolder.infoTv.setText(df.format(((int) Util.getFakeWatchCount(data.getVerifyDate()) + data.getWatchCount()) / (float) 10000.0) + "万");
                } else {
                    showItemHolder.infoTv.setText(String.valueOf(Util.getFakeWatchCount(data.getVerifyDate()) + data.getWatchCount()));
                }
            }
            int fakeDownCount = Util.getFakeDownLoadNum((int) (Util.getFakeWatchCount(data.getVerifyDate()) + data.getWatchCount()), data.getDownCount());
            showItemHolder.tvDownNum.setText(String.valueOf(fakeDownCount));

            int fakeShareCount = Util.getFakeShareNum((int) (Util.getFakeWatchCount(data.getVerifyDate()) + data.getWatchCount()), data.getShareCount());
            showItemHolder.tvSharaNum.setText(String.valueOf(fakeShareCount));
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
            UniversalImageLoader.displayRoundImage(context, Util.getD2cPicUrl(data.getMemberHead()), showItemHolder.imgAvatar, R.mipmap.ic_default_avatar);
            showItemHolder.nameTv.setText(data.getMemberName());
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

            showItemHolder.tvDate.setText(DateUtil.getFriendlyTime2(data.getCreateDate()));
            if (!Util.isEmpty(data.getStreet())) {
                showItemHolder.tvLocation.setVisibility(View.VISIBLE);
                showItemHolder.tvLocation.setText(data.getStreet());
            } else {
                showItemHolder.tvLocation.setVisibility(View.GONE);
            }
            showItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowDetailActivity.class);
                    intent.putExtra("id", (long) data.getId());
                    context.startActivity(intent);
                }
            });
        } else if (holder instanceof LiveItemHolder && id == 4) {
            LiveItemHolder itemHolder = (LiveItemHolder) holder;
            final LiveListBean.DataBean.LivesBean.ListBean data = (LiveListBean.DataBean.LivesBean.ListBean) list.get(position);
            UniversalImageLoader.displayRoundImage(context, Util.getD2cPicUrl(data.getHeadPic()), itemHolder.imgAvatar, R.mipmap.ic_default_avatar);
            itemHolder.name.setText(data.getNickname());
            itemHolder.watchCount.setText(context.getString(R.string.label_total_view_count, data.getVcount() + data.getVfans()));
            UniversalImageLoader.displayImage(context, data.getCover(), itemHolder.image);

            itemHolder.tagTv.setText("回放");

            itemHolder.des.setText(data.getTitle());
            itemHolder.imgAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PersonInfoActivity.class);
                    intent.putExtra("memberId", data.getMemberId());
                    context.startActivity(intent);
                }
            });
            itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Util.loginChecked((Activity) context, 100)) {
                        Intent intent = new Intent(context, VideoActivity.class);
                        intent.putExtra("bean", data);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    private void sharePics(List<String> pics, String text, boolean isShare) {
        if (pics == null || pics.size() == 0) {
            Util.showToast(context, "没有内容可分享");
            return;
        }
        savePics(pics, text, isShare);
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
            Glide.with(context).load(Util.getD2cPicUrl(pics.get(i),360,480)).asBitmap().into(new SimpleTarget<Bitmap>() {
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
                                Util.showToast(context, "图片已保存在相册中");
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
                                    Uri uri = Uri.parse(android.provider.MediaStore.Images.Media.insertImage(context.getContentResolver(), f.getAbsolutePath(), f.getName(), null));
                                    imageUris.add(uri);
                                }
                            }
                            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                            intent.putExtra("Kdescription", text);
                            context.startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void focusComplete(final ShareBean.DataEntity.MemberSharesEntity.ListEntity memberBean, final ShowDetailTopHolder showDetailTopHolder) {
        FollowApi api = new FollowApi();
        api.setToMemberId(memberBean.getMemberId());
        if (memberBean.getFollow() > 0) {
            api.setInterPath(Constants.DELETE_MY_FOLLOWS_URL);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    Util.showToast(context, "取消关注成功");
                    memberBean.setFollow(0);
                    showDetailTopHolder.ivUserFocus.setImageResource(R.mipmap.button_fashion_care);
                }
            });
        } else {
            api.setInterPath(Constants.INSERT_MY_FOLLOWS_URL);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ClickFollowBean>() {
                @Override
                public void onResponse(ClickFollowBean clickFollowBean) {
                    Util.showToast(context, "关注成功");
                    memberBean.setFollow(clickFollowBean.getData().getFollow());
                    if (clickFollowBean.getData().getFollow() == 0) {
                        showDetailTopHolder.ivUserFocus.setImageResource(R.mipmap.button_fashion_care);
                    } else if (clickFollowBean.getData().getFollow() == 1) {
                        showDetailTopHolder.ivUserFocus.setImageResource(R.mipmap.button_fashion_cared);
                    } else if (clickFollowBean.getData().getFollow() == 2) {
                        showDetailTopHolder.ivUserFocus.setImageResource(R.mipmap.button_fashion_mutualcare);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Util.showToast(context, Util.checkErrorType(error));
                }
            });
        }
    }

    private void likeShow(final RankVideoBean.DataBean.HotVideoBean.ListBean data, final ShowItemHolder holder) {
        if (Util.loginChecked((RankActivity) context, Constants.Login.EXPLORE_DETAIL_LOGIN)) {
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

    private void likeShow(final RankPicBean.DataBean.HotPicBean.ListBean data, final ShowItemHolder holder) {
        if (Util.loginChecked((RankActivity) context, Constants.Login.EXPLORE_DETAIL_LOGIN)) {
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

    private void shareVideo(RankVideoBean.DataBean.HotVideoBean.ListBean data, boolean isShare) {
        String fileFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/Camera";
        String fileName = data.getId() + ".mp4";
        File file = new File(fileFolder, fileName);
        if (file.exists()) {
            Util.showToast(context, "视频已保存在/DCIM/Camera下,快去分享吧~");
            PackageManager packageManager = context.getPackageManager();
            String packageName = "com.tencent.mm";
            Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(packageName);
            if (launchIntentForPackage != null)
                context.startActivity(launchIntentForPackage);
            else
                Util.showToast(context, "未安装微信");
        } else {
            saveVideo(data.getVideo(), file, isShare);
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
                Message message = Message.obtain();
                message.arg1 = progress;
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

    public void destroy() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    private void setPictures(RankPicBean.DataBean.HotPicBean.ListBean listEntity, NineGridView nineGridView, List<String> imgList) {

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

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper layoutHelper = new LinearLayoutHelper();
        if (id == 2 || id == 3) {
            layoutHelper.setMarginTop(ScreenUtil.dip2px(16));
        }
        return layoutHelper;
    }

    class TopPersonViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_ranking)
        TextView tvRanking;
        @Bind(R.id.iv_person_head)
        RoundedImageView ivPersonHead;
        @Bind(R.id.tv_person_name)
        TextView tvPersonName;
        @Bind(R.id.tv_person_hot)
        TextView tvPersonHot;
        //开启消息推送行为节点
        @Bind(R.id.iv_focus)
        ShowPopImageView ivFocus;
        @Bind(R.id.iv_person_head_border)
        ImageView ivPersonHeadBorder;
        @Bind(R.id.line_layout)
        View line;

        TopPersonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class CommonPersonViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_ranking)
        TextView tvRanking;
        @Bind(R.id.iv_person_head)
        RoundedImageView ivPersonHead;
        @Bind(R.id.tv_person_name)
        TextView tvPersonName;
        @Bind(R.id.tv_person_hot)
        TextView tvPersonHot;
        //开启消息推送行为节点
        @Bind(R.id.iv_focus)
        ShowPopImageView ivFocus;

        CommonPersonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
