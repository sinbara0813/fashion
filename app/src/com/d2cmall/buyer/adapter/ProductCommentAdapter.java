package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.PersonInfoActivity;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.ProductCommentListBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.fragment.ProductFragment;
import com.d2cmall.buyer.holder.ProductCommendHolder;
import com.d2cmall.buyer.holder.ProductItemTitleHolder;
import com.d2cmall.buyer.holder.ProductShowHolder;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.nicevideo.TxVideoPlayerController;
import com.d2cmall.buyer.widget.ninegrid.ImageInfo;
import com.d2cmall.buyer.widget.ninegrid.NineGridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/5 19:27
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductCommentAdapter extends DelegateAdapter.Adapter {

    private Context context;
    private List<ProductCommentListBean.DataEntity.CommentsEntity.ListEntity> list;
    private UserBean.DataEntity.MemberEntity user;
    private boolean hasTitle;

    public ProductCommentAdapter(Context context,List<ProductCommentListBean.DataEntity.CommentsEntity.ListEntity> list){
        this.context=context;
        this.list=list;
        user= Session.getInstance().getUserFromFile(context);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper=new LinearLayoutHelper();
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        if (viewType==40){
            view= LayoutInflater.from(context).inflate(R.layout.layout_product_commend_item,parent,false);
            return new ProductCommendHolder(view);
        }else {
            view=LayoutInflater.from(context).inflate(R.layout.layout_product_item_title,parent,false);
            return new ProductItemTitleHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==40){
            ProductCommendHolder showItemHolder= (ProductCommendHolder) holder;
            int offer=hasTitle?1:0;
            final ProductCommentListBean.DataEntity.CommentsEntity.ListEntity listEntity=list.get(position-offer);
            UniversalImageLoader.displayRoundImage(context, Util.getD2cPicUrl(listEntity.getHeadPic()),showItemHolder.imgAvatar, R.mipmap.ic_default_avatar);
            showItemHolder.nameTv.setText(listEntity.getNickname());
            showItemHolder.timeTv.setText(DateUtil.getFriendlyTime2(listEntity.getCreateDate()));

            if (!Util.isEmpty(listEntity.getContent())) {
                showItemHolder.contentTv.setVisibility(View.VISIBLE);
                showItemHolder.contentTv.setText(listEntity.getContent());
            } else {
                showItemHolder.contentTv.setVisibility(View.GONE);
            }
            String videoUrl1 = listEntity.getVideo();
            List<String> imgList = listEntity.getPics();
            if(Util.isEmpty(videoUrl1)) {
                showItemHolder.nineGrid.setVisibility(View.VISIBLE);
                showItemHolder.videoLayout.setVisibility(View.GONE);
                if (listEntity.getPics()!=null&&listEntity.getPics().size()>0){
                    showItemHolder.nineGrid.setVisibility(View.VISIBLE);
                    setPictures(listEntity,showItemHolder.nineGrid,listEntity.getPics());
                }else{
                    showItemHolder.nineGrid.setVisibility(View.GONE);
                }
            }else {
                if (!videoUrl1.startsWith("http")) {
                    videoUrl1 = Constants.IMG_HOST + videoUrl1;
                }
                TxVideoPlayerController txVideoPlayerController=new TxVideoPlayerController(context);
                showItemHolder.niceVideoPlayer.setController(txVideoPlayerController);
                if (imgList != null && imgList.size() > 0) {
                    UniversalImageLoader.displayImage(context,Util.getD2cPicUrl(imgList.get(0)),txVideoPlayerController.getImage());
                }
                showItemHolder.niceVideoPlayer.setUp(videoUrl1,null);
                showItemHolder.videoLayout.setVisibility(View.VISIBLE);
                showItemHolder.nineGrid.setVisibility(View.GONE);
                LinearLayout.LayoutParams rl= (LinearLayout.LayoutParams) showItemHolder.videoLayout.getLayoutParams();
                rl.height= ScreenUtil.getDisplayWidth()*200/355;
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
            showItemHolder.imgAvatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PersonInfoActivity.class);
                    intent.putExtra("memberId",listEntity.getMemberId());
                    context.startActivity(intent);
                }
            });
            showItemHolder.likeNum.setVisibility(View.GONE);
            showItemHolder.commentNum.setVisibility(View.GONE);
            int replySize=listEntity.getReplys().size();
            if (replySize>0){
                for (int i = 0; i < listEntity.getReplys().size(); i++) {
                    ProductCommentListBean.DataEntity.CommentsEntity.ListEntity.ReplysEntity replysEntity = listEntity.getReplys().get(i);
                    if (replysEntity.getType().equals("CUSTOMER")) {
                        showItemHolder.addReviewLl.setVisibility(View.VISIBLE);
                        showItemHolder.addReviewTime.setText(DateUtil.getAddReViewTime(listEntity.getCreateDate(),replysEntity.getCreateDate())+"追评:");
                        showItemHolder.addReviewContentTv.setText(replysEntity.getContent());
                        setPictures(listEntity,showItemHolder.addReviewNineGrid,replysEntity.getPic());
                    }else if (replysEntity.getType().equals("SYSTEM")) {
                        showItemHolder.reply.setVisibility(View.VISIBLE);
                        showItemHolder.reply.setText("客服回复:"+replysEntity.getContent());
                    }
                }
            }else {
                showItemHolder.addReviewLl.setVisibility(View.GONE);
                showItemHolder.reply.setVisibility(View.GONE);
            }
        }else {
            ProductItemTitleHolder titleHolder= (ProductItemTitleHolder) holder;
            titleHolder.titleMore.setVisibility(View.VISIBLE);
            if (Session.getInstance().getUser()!=null&&Session.getInstance().getUser().getPartnerId()>0){
                titleHolder.titleName.setText(Util.getProductTitle("素材", ProductFragment.totalCount));
            }else {
                titleHolder.titleName.setText(Util.getProductTitle("晒单评价",ProductFragment.totalCount));
            }
            titleHolder.titleMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActionBean actionBean=new ActionBean(Constants.ActionType.CHANGE_PRODUCT_PAGE);
                    actionBean.put("position",2);
                    actionBean.put("second",0);
                    EventBus.getDefault().post(actionBean);
                }
            });
        }
    }

    private List<ProductCommentListBean.DataEntity.CommentsEntity.ListEntity.ReplysEntity> sortReplys(List<ProductCommentListBean.DataEntity.CommentsEntity.ListEntity.ReplysEntity> replys) {
        Collections.sort(replys, new Comparator<ProductCommentListBean.DataEntity.CommentsEntity.ListEntity.ReplysEntity>() {
            @Override
            public int compare(ProductCommentListBean.DataEntity.CommentsEntity.ListEntity.ReplysEntity lhs, ProductCommentListBean.DataEntity.CommentsEntity.ListEntity.ReplysEntity rhs) {
                return DateUtil.strToDateLong(lhs.getCreateDate()).getTime() < DateUtil.strToDateLong(rhs.getCreateDate()).getTime() ? 1 : 0;
            }
        });
        return replys;
    }

    private void setPictures(ProductCommentListBean.DataEntity.CommentsEntity.ListEntity listEntity, NineGridView nineGridView, List<String> imgList) {

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
        return hasTitle?list.size()+1:list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0&&hasTitle){
            return 41;
        }
        return 40;
    }

    public void setHasTitle(boolean hasTitle) {
        this.hasTitle = hasTitle;
    }
}
