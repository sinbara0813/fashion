package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.LikeThisActivity;
import com.d2cmall.buyer.activity.PersonInfoActivity;
import com.d2cmall.buyer.activity.TopicDetailActivity;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.FollowApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ClickFollowBean;
import com.d2cmall.buyer.bean.ShareBean;
import com.d2cmall.buyer.bean.ShowReviewListBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.ReplyShowHolder;
import com.d2cmall.buyer.holder.ShowDetailTopHolder;
import com.d2cmall.buyer.holder.ShowTitleHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.Clickable;
import com.d2cmall.buyer.listener.DeleteItemClickListner;
import com.d2cmall.buyer.listener.OnItemClickListener;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.RoundedImageView;
import com.d2cmall.buyer.widget.nicevideo.TxVideoPlayerController;
import com.d2cmall.buyer.widget.ninegrid.ImageInfo;
import com.d2cmall.buyer.widget.ninegrid.NineGridView;

import java.util.ArrayList;
import java.util.List;

import static com.d2cmall.buyer.Constants.LIKE_COMMENT;

/**
 * Created by rookie on 2017/8/24.
 */

public class ShowDetailAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Object> list;
    private OnItemClickListener onItemClickListener;
    private DeleteItemClickListner deleteItemClickListner;
    List<ShowReviewListBean.DataBean.CommentsBean.ListBean> allReviewList;

    public ShowDetailAdapter(Context context, List<Object> list, List<ShowReviewListBean.DataBean.CommentsBean.ListBean> allReviewList) {
        this.context = context;
        this.list = list;
        this.allReviewList = allReviewList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setDeleteItemClickListner(DeleteItemClickListner deleteItemClickListner) {
        this.deleteItemClickListner = deleteItemClickListner;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case 1:
                itemView = LayoutInflater.from(context).inflate(R.layout.layout_show_detail_content2, parent, false);
                return new ShowDetailTopHolder(itemView);
            case 2:
            case 4:
                itemView = LayoutInflater.from(context).inflate(R.layout.layout_reply_show, parent, false);
                return new ReplyShowHolder(itemView);
            case 3:
                itemView = LayoutInflater.from(context).inflate(R.layout.layout_show_detail_textview, parent, false);
                return new ShowTitleHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ShowDetailTopHolder) {
            ShowDetailTopHolder showDetailTopHolder = (ShowDetailTopHolder) holder;
            ShareBean.DataEntity.MemberSharesEntity.ListEntity showDetailBean = (ShareBean.DataEntity.MemberSharesEntity.ListEntity) list.get(position);
            setDataToTopView(showDetailTopHolder, showDetailBean, position);
        } else {
            if (holder instanceof ReplyShowHolder) {
                ReplyShowHolder replyShowHolder = (ReplyShowHolder) holder;
                if (list.get(position) instanceof ShowReviewListBean.DataBean.CommentsBean.ListBean) {
                    ShowReviewListBean.DataBean.CommentsBean.ListBean reviewBean = (ShowReviewListBean.DataBean.CommentsBean.ListBean) list.get(position);
                    setDataToReviewView(replyShowHolder, reviewBean, position);
                } else if (list.get(position) instanceof ShareBean.DataEntity.MemberSharesEntity.ListEntity.CommentsEntity) {
                    ShareBean.DataEntity.MemberSharesEntity.ListEntity.CommentsEntity reviewData = (ShareBean.DataEntity.MemberSharesEntity.ListEntity.CommentsEntity) list.get(position);
                    setDataToReviewData(replyShowHolder, reviewData, position);
                }

            } else if (holder instanceof ShowTitleHolder) {
                ShowTitleHolder showTitleHolder = (ShowTitleHolder) holder;
                String title = (String) list.get(position);
                showTitleHolder.tvText.setText(title);
            }
        }
    }

    private void setDataToReviewData(final ReplyShowHolder replyShowHolder, final ShareBean.DataEntity.MemberSharesEntity.ListEntity.CommentsEntity reviewBean, final int position) {
        UniversalImageLoader.displayRoundImage(context, reviewBean.getHeadPic(), replyShowHolder.ivUserHeadPic, R.mipmap.ic_default_avatar);
        replyShowHolder.ivUserHeadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PersonInfoActivity.class);
                intent.putExtra("memberId", reviewBean.getMemberId());
                context.startActivity(intent);
            }
        });
        replyShowHolder.tvLikeNum.setText(String.valueOf(0));
        replyShowHolder.tvUserName.setText(reviewBean.getNickName());
        replyShowHolder.tvPostTime.setText(DateUtil.getFriendlyTime2(reviewBean.getCreateDate()));
        SpannableStringBuilder preContent = new SpannableStringBuilder();
        replyShowHolder.tvLikeNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) context, 999)) {
                    SimpleApi api = new SimpleApi();
                    api.setMethod(BaseApi.Method.POST);
                    api.setInterPath(String.format(LIKE_COMMENT, reviewBean.getId()));
                    final Drawable nav_dowm = context.getResources().getDrawable(R.mipmap.icon_comment_liked);
                    nav_dowm.setBounds(0, 0, nav_dowm.getMinimumWidth(), nav_dowm.getMinimumHeight());
                    D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                        @Override
                        public void onResponse(BaseBean response) {
                            replyShowHolder.tvLikeNum.setCompoundDrawables(nav_dowm, null, null, null);
                            replyShowHolder.tvLikeNum.setText(String.valueOf(1));
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
        if (!Util.isEmpty(reviewBean.getToNickName()) && reviewBean.getToCommentId() > 0 && !Util.isEmpty(reviewBean.getContent())) {
            preContent.append("回复");
            preContent.append(reviewBean.getToNickName() + ":");
            if (!Util.isEmpty(reviewBean.getContent())) {
                preContent.append(reviewBean.getContent());
                preContent.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF23365")), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                preContent.setSpan(new ForegroundColorSpan(Color.parseColor("#DE000000")), 2, 2 + reviewBean.getToNickName().length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                preContent.setSpan(new ForegroundColorSpan(Color.parseColor("#61000000")), 2 + reviewBean.getToNickName().length() + 1, preContent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } else {
            if (!Util.isEmpty(reviewBean.getContent())) {
                preContent.append(reviewBean.getContent());
                preContent.setSpan(new ForegroundColorSpan(Color.parseColor("#61000000")), 0, preContent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                preContent.append("  ");
                preContent.setSpan(new ForegroundColorSpan(Color.parseColor("#61000000")), 0, preContent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

        }

        replyShowHolder.tvReplyContent.setText(preContent);
        if (Util.isEmpty(reviewBean.getToCommentContent())) {
            replyShowHolder.tvReplyOriginalContent.setVisibility(View.GONE);
        } else {
            replyShowHolder.tvReplyOriginalContent.setVisibility(View.VISIBLE);
            String text = reviewBean.getToNickName() + ":" + reviewBean.getToCommentContent();
            replyShowHolder.tvReplyOriginalContent.setText(text);
        }
        replyShowHolder.tvReplyClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.itemClick(v, position);
            }
        });
    }

    private void setDataToReviewView(final ReplyShowHolder replyShowHolder, final ShowReviewListBean.DataBean.CommentsBean.ListBean reviewBean, final int position) {
        UniversalImageLoader.displayRoundImage(context, reviewBean.getHeadPic(), replyShowHolder.ivUserHeadPic, R.mipmap.ic_default_avatar);
        replyShowHolder.ivUserHeadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PersonInfoActivity.class);
                intent.putExtra("memberId", (long) reviewBean.getMemberId());
                context.startActivity(intent);
            }
        });
        replyShowHolder.tvLikeNum.setText(String.valueOf(reviewBean.getLikes()));
        replyShowHolder.tvUserName.setText(reviewBean.getNickName());
        replyShowHolder.tvPostTime.setText(DateUtil.getFriendlyTime2(reviewBean.getCreateDate()));
        //LIKE_COMMENT
        replyShowHolder.tvLikeNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) context, 999)) {
                    SimpleApi api = new SimpleApi();
                    api.setMethod(BaseApi.Method.POST);
                    api.setInterPath(String.format(LIKE_COMMENT, reviewBean.getId()));
                    final Drawable nav_dowm = context.getResources().getDrawable(R.mipmap.icon_comment_liked);
                    nav_dowm.setBounds(0, 0, nav_dowm.getMinimumWidth(), nav_dowm.getMinimumHeight());
                    D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                        @Override
                        public void onResponse(BaseBean response) {
                            replyShowHolder.tvLikeNum.setCompoundDrawables(nav_dowm, null, null, null);
                            replyShowHolder.tvLikeNum.setText(reviewBean.getLikes() + 1 + "");
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
        SpannableStringBuilder preContent = new SpannableStringBuilder();
        if (!Util.isEmpty(reviewBean.getToNickName()) && reviewBean.getToCommentId() > 0 && !Util.isEmpty(reviewBean.getContent())) {
            preContent.append("回复");
            preContent.append(reviewBean.getToNickName() + ":");
            if (!Util.isEmpty(reviewBean.getContent())) {
                preContent.append(reviewBean.getContent());
                preContent.setSpan(new ForegroundColorSpan(Color.parseColor("#FFF23365")), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                preContent.setSpan(new ForegroundColorSpan(Color.parseColor("#DE000000")), 2, 2 + reviewBean.getToNickName().length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                preContent.setSpan(new ForegroundColorSpan(Color.parseColor("#8A000000")), 2 + reviewBean.getToNickName().length() + 1, preContent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } else {
            if (!Util.isEmpty(reviewBean.getContent())) {
                preContent.append(reviewBean.getContent());
                preContent.setSpan(new ForegroundColorSpan(Color.parseColor("#61000000")), 0, preContent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                preContent.append("  ");
                preContent.setSpan(new ForegroundColorSpan(Color.parseColor("#61000000")), 0, preContent.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        replyShowHolder.tvReplyContent.setText(preContent);
        if (Util.isEmpty(reviewBean.getToCommentContent())) {
            replyShowHolder.tvReplyOriginalContent.setVisibility(View.GONE);
        } else {
            replyShowHolder.tvReplyOriginalContent.setVisibility(View.VISIBLE);
            String text = reviewBean.getToNickName() + ":" + reviewBean.getToCommentContent();
            replyShowHolder.tvReplyOriginalContent.setText(text);
        }
        final Point point = Util.getDeviceSize(context);
        replyShowHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                View contentView = View.inflate(context, R.layout.layout_copy_and_delete, null);
                final PopupWindow popupWindow = new PopupWindow(contentView, -2, -2, true);
                popupWindow.setBackgroundDrawable(new ColorDrawable());
                popupWindow.setFocusable(false);
                popupWindow.setOutsideTouchable(true);
                popupWindow.showAsDropDown(v, (point.x / 2 - v.getLeft() - contentView.getMeasuredWidth()), -v.getMeasuredHeight());
                TextView tvCopy = (TextView) contentView.findViewById(R.id.tv_copy);
                View tvDelete = contentView.findViewById(R.id.tv_delete);
                View view1 = contentView.findViewById(R.id.view);
                UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(context);
                if (user != null && user.getMemberId() == reviewBean.getMemberId()) {
                    tvDelete.setVisibility(View.VISIBLE);
                    view1.setVisibility(View.VISIBLE);
                }
                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                        requestCommentDeleteId(position, reviewBean.getId());
                    }
                });
                tvCopy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setPrimaryClip(ClipData.newPlainText("copy", reviewBean.getContent()));
                        popupWindow.dismiss();
                    }
                });
                return true;
            }
        });
        replyShowHolder.tvReplyClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.itemClick(v, position);
            }
        });
    }

    public void notifySetListDataChanged(List<Object> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    private void requestCommentDeleteId(final int position, long id) {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.COMMENT_DELETE_URL, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                list.remove(position);
//                memberShareEntity.setCommentCount(memberShareEntity.getCommentCount() > 0 ? memberShareEntity.getCommentCount() - 1 : 0);
//                tvComment.setText(String.format(getString(R.string.label_kuohao), memberShareEntity.getCommentCount()));
                deleteItemClickListner.onDelete(position);
                notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(context, Util.checkErrorType(error));
            }
        });
    }

    private void focusComplete(final ShareBean.DataEntity.MemberSharesEntity.ListEntity memberBean, final ShowDetailTopHolder showDetailTopHolder) {
        FollowApi api = new FollowApi();
        api.setToMemberId(memberBean.getMemberId());
        if (memberBean.getFollow() > 0) {
            memberBean.setFollow(0);
            showDetailTopHolder.ivUserFocus.setImageResource(R.mipmap.button_fashion_care);
            api.setInterPath(Constants.DELETE_MY_FOLLOWS_URL);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    Util.showToast(context, "取消关注成功");
                    String deleteFocus = D2CApplication.mSharePref.getSharePrefString("unfocus", "");
                    if (Util.isEmpty(deleteFocus)) {
                        D2CApplication.mSharePref.putSharePrefString("unfocus", memberBean.getMemberId() + ",");
                    } else {
                        String[] strings = deleteFocus.split(",");
                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i < strings.length; i++) {
                            if (!strings[i].equals(memberBean.getMemberId() + "")) {
                                builder.append(strings[i]);
                            }
                        }
                        builder.append(memberBean.getMemberId() + ",");
                        D2CApplication.mSharePref.putSharePrefString("unfocus", builder.toString());
                    }
                }
            });
        } else {
            showDetailTopHolder.ivUserFocus.setImageResource(R.mipmap.button_fashion_cared);
            memberBean.setFollow(1);
            api.setInterPath(Constants.INSERT_MY_FOLLOWS_URL);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ClickFollowBean>() {
                @Override
                public void onResponse(ClickFollowBean clickFollowBean) {
                    Util.showToast(context, "关注成功");
                    showDetailTopHolder.ivUserFocus.showMsgPop((Activity) context,context.getString(R.string.label_pop_focus_people));
                    String deleteFocus = D2CApplication.mSharePref.getSharePrefString("focus", "");
                    if (Util.isEmpty(deleteFocus)) {
                        D2CApplication.mSharePref.putSharePrefString("focus", memberBean.getMemberId() + ",");
                    } else {
                        String[] strings = deleteFocus.split(",");
                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i < strings.length; i++) {
                            if (!strings[i].equals(memberBean.getMemberId() + "")) {
                                builder.append(strings[i]);
                            }
                        }
                        builder.append(memberBean.getMemberId() + ",");
                        D2CApplication.mSharePref.putSharePrefString("focus", builder.toString());
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

    private void setDataToTopView(final ShowDetailTopHolder showDetailTopHolder, final ShareBean.DataEntity.MemberSharesEntity.ListEntity showDetailBean, int position) {
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(context);
        if (user != null) {
            if (showDetailBean.getMemberId() == user.getMemberId()) {
                showDetailTopHolder.ivUserFocus.setVisibility(View.GONE);
            }
        }

        if (list.size() == 1) {
            showDetailTopHolder.llEmptyImage.setVisibility(View.VISIBLE);
        } else {
            showDetailTopHolder.llEmptyImage.setVisibility(View.GONE);
        }
        List<ShareBean.DataEntity.MemberSharesEntity.ListEntity.LikesBean> picList = null;
        if (!showDetailBean.getLikes().isEmpty() &&
                showDetailBean.getLikes().size() > 5) {
            picList = showDetailBean.getLikes().subList(0, 5);
        } else {
            picList = showDetailBean.getLikes();
        }
        if (picList != null && !picList.isEmpty()) {
            showDetailTopHolder.focusPicsLayout.removeAllViews();
            for (ShareBean.DataEntity.MemberSharesEntity.ListEntity.LikesBean picUrl : showDetailBean.getLikes()) {
                View view = LayoutInflater.from(context).inflate(R.layout.list_item_image, showDetailTopHolder.focusPicsLayout, false);
                RoundedImageView imageView = (RoundedImageView) view.findViewById(R.id.img_avatar);
                UniversalImageLoader.displayImage(context, Util.getD2cPicUrl(picUrl.getHeadPic()), imageView, R.mipmap.ic_default_avatar);
                showDetailTopHolder.focusPicsLayout.addView(view);
            }
            showDetailTopHolder.tvAllFocus.setText(context.getString(R.string.label_all_like, showDetailBean.getLikeMeCount()));
            showDetailTopHolder.focusPicsLayout.setVisibility(View.VISIBLE);
            showDetailTopHolder.fansLayout.setVisibility(View.VISIBLE);
        }
        showDetailTopHolder.fansLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LikeThisActivity.class);
                intent.putExtra("showId", showDetailBean.getId());
                intent.putExtra("type", 1);
                context.startActivity(intent);
            }
        });
        if (showDetailBean.getLikes() == null ||
                showDetailBean.getLikeMeCount() == 0) {
            showDetailTopHolder.focusPicsLayout.setVisibility(View.GONE);
            showDetailTopHolder.fansLayout.setVisibility(View.GONE);
        } else if (showDetailBean.getLikeMeCount() > 0) {
            showDetailTopHolder.tvAllFocus.setText(context.getString(R.string.label_all_like, showDetailBean.getLikeMeCount()));
            showDetailTopHolder.focusPicsLayout.setVisibility(View.VISIBLE);
            showDetailTopHolder.fansLayout.setVisibility(View.VISIBLE);
        }

        if (showDetailBean.getProductRelations() != null && !showDetailBean.getProductRelations().isEmpty()) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            ShowRelationAdapter showRelationAdapter = new ShowRelationAdapter(context, showDetailBean.getProductRelations());
            showDetailTopHolder.recycleView.setLayoutManager(linearLayoutManager);
            showDetailTopHolder.recycleView.setAdapter(showRelationAdapter);
            showDetailTopHolder.llRecycler.setVisibility(View.VISIBLE);
            showDetailTopHolder.recycleView.setVisibility(View.VISIBLE);
        }
        if (!Util.isEmpty(showDetailBean.getDescription())) {
            showDetailTopHolder.contentTv.setVisibility(View.VISIBLE);
            StringBuilder builder = new StringBuilder();
            if (!Util.isEmpty(showDetailBean.getTopicName())) {
                int end = showDetailBean.getTopicName().length() + 2;
                builder.append("#" + showDetailBean.getTopicName() + "#" + " ");
                if (!Util.isEmpty(showDetailBean.getDescription())) {
                    builder.append(showDetailBean.getDescription());
                }
                SpannableString sb = new SpannableString(builder.toString());
                sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, showDetailBean.getTopicName().length() + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //粗体
                sb.setSpan(new Clickable(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, TopicDetailActivity.class);
                        intent.putExtra("id", showDetailBean.getTopicId());
                        context.startActivity(intent);
                    }
                }), 0, showDetailBean.getTopicName().length() + 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                sb.setSpan(new ForegroundColorSpan(Color.BLACK), 0, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                showDetailTopHolder.contentTv.setMovementMethod(LinkMovementMethod.getInstance());
                showDetailTopHolder.contentTv.setText(sb);
            } else {
                showDetailTopHolder.contentTv.setText(showDetailBean.getDescription());
            }
        }
        UniversalImageLoader.displayRoundImage(context, Constants.IMG_HOST + showDetailBean.getMemberHead(), showDetailTopHolder.ivUserHeadPic, R.mipmap.ic_default_avatar);
        showDetailTopHolder.ivUserHeadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PersonInfoActivity.class);
                intent.putExtra("memberId", showDetailBean.getMemberId());
                context.startActivity(intent);
            }
        });
        switch (showDetailBean.getFollow()) {
            case 0:
                showDetailTopHolder.ivUserFocus.setImageResource(R.mipmap.button_fashion_care);
                break;
            case 1:
                showDetailTopHolder.ivUserFocus.setImageResource(R.mipmap.button_fashion_cared);
                break;
            case 2:
                showDetailTopHolder.ivUserFocus.setImageResource(R.mipmap.button_fashion_mutualcare);
                break;
        }
        showDetailTopHolder.ivUserFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showDetailBean == null) {
                    return;
                }
                if (Util.loginChecked((Activity) context, Constants.Login.EXPLORE_DETAIL_LOGIN)) {
                    focusComplete(showDetailBean, showDetailTopHolder);
                }
            }
        });
        showDetailTopHolder.tvPostTime.setText(DateUtil.getFriendlyTime2(showDetailBean.getCreateDate()));
        showDetailTopHolder.tvPostAddress.setText(showDetailBean.getCity() + showDetailBean.getStreet());
        if (showDetailBean.getCity() == null && showDetailBean.getStreet() == null) {
            showDetailTopHolder.llPosition.setVisibility(View.GONE);
        }
        showDetailTopHolder.tvUserName.setText(showDetailBean.getMemberName());
        String videoUrl = showDetailBean.getVideo();
        List<String> imgList = showDetailBean.getPics();
        if (Util.isEmpty(videoUrl)) {
            showDetailTopHolder.nineGrid.setVisibility(View.VISIBLE);
            setPictures(showDetailTopHolder.nineGrid, showDetailBean.getPics());
            showDetailTopHolder.videoLayout.setVisibility(View.GONE);
        } else {
            showDetailTopHolder.nineGrid.setVisibility(View.GONE);
            showDetailTopHolder.videoLayout.setVisibility(View.VISIBLE);
            if (!videoUrl.startsWith("http")) {
                videoUrl = Constants.IMG_HOST + videoUrl;
            }
            TxVideoPlayerController txVideoPlayerController = new TxVideoPlayerController(context);
            showDetailTopHolder.niceVideoPlayer.setController(txVideoPlayerController);
            if (imgList != null && imgList.size() > 0) {
                UniversalImageLoader.displayImage(context, Util.getD2cPicUrl(imgList.get(0)), txVideoPlayerController.getImage());
            }
            showDetailTopHolder.niceVideoPlayer.setUp(videoUrl, null);
            showDetailTopHolder.videoLayout.setVisibility(View.VISIBLE);
            showDetailTopHolder.nineGrid.setVisibility(View.GONE);
            LinearLayout.LayoutParams rl = (LinearLayout.LayoutParams) showDetailTopHolder.videoLayout.getLayoutParams();
            rl.height = ScreenUtil.getDisplayWidth() * 200 / 355;
            int timeLong = showDetailBean.getTimeLength();
            if (timeLong > 0) {
                int m = timeLong / 60 % 60;
                int s = timeLong % 60;
                StringBuffer sb = new StringBuffer();

                sb.append(String.format("%2d:", m));

                sb.append(String.format("%02d", s));
                txVideoPlayerController.setTime(sb.toString());
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof ShareBean.DataEntity.MemberSharesEntity.ListEntity) {
            return 1;
        } else if (list.get(position) instanceof ShowReviewListBean.DataBean.CommentsBean.ListBean) {
            return 2;
        } else if (list.get(position) instanceof String) {
            return 3;
        } else if (list.get(position) instanceof ShareBean.DataEntity.MemberSharesEntity.ListEntity.CommentsEntity) {
            return 4;
        }
        return 3;
    }
}
