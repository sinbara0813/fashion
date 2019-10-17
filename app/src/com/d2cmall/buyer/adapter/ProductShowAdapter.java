package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.PersonInfoActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.activity.ShowDetailActivity;
import com.d2cmall.buyer.activity.TopicDetailActivity;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.FollowApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.AdShareBean;
import com.d2cmall.buyer.bean.ClickFollowBean;
import com.d2cmall.buyer.bean.ProductShowBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.fragment.ProductFragment;
import com.d2cmall.buyer.holder.ProductCommendHolder;
import com.d2cmall.buyer.holder.ProductHasPicHolder;
import com.d2cmall.buyer.holder.ProductItemTitleHolder;
import com.d2cmall.buyer.holder.ProductShareAdHolder;
import com.d2cmall.buyer.holder.ProductShowHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.nicevideo.TxVideoPlayerController;
import com.d2cmall.buyer.widget.ninegrid.ImageInfo;
import com.d2cmall.buyer.widget.ninegrid.NineGridView;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

import static com.d2cmall.buyer.Constants.DELETE_SHARE_LIKE;
import static com.d2cmall.buyer.Constants.LIKE_SHARE_URL;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/4 14:40
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductShowAdapter extends DelegateAdapter.Adapter {

    private Context context;
    private List<ProductShowBean.DataEntity.MembersharesEntity.ListEntity> list;
    private boolean isShort; //是否全部展示
    private UserBean.DataEntity.MemberEntity user;
    private AdShareBean.DataBean.AdResourceBean adResource;
    private ClickListener clickListener;
    private boolean hideList;
    private long id;
    private String pic;
    private String name;

    public ProductShowAdapter(Context context, List<ProductShowBean.DataEntity.MembersharesEntity.ListEntity> data){
        this.context=context;
        this.list=data;
        user=Session.getInstance().getUserFromFile(context);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper=new LinearLayoutHelper();
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        switch (viewType){
            case 12:
                view=LayoutInflater.from(context).inflate(R.layout.layout_product_ad_share,parent,false);
                return new ProductShareAdHolder(view);
            case 13:
                view=LayoutInflater.from(context).inflate(R.layout.layout_product_item_title,parent,false);
                return new ProductItemTitleHolder(view);
            case 14:
                view=LayoutInflater.from(context).inflate(R.layout.layout_product_show_tag,parent,false);
                return new ProductHasPicHolder(view);
            case 15:
                view=LayoutInflater.from(context).inflate(R.layout.layout_product_commend_item,parent,false);
                return new ProductCommendHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)){
            case 12:
                ProductShareAdHolder shareAdHolder= (ProductShareAdHolder) holder;
                if (isShort){
                    holder.itemView.setPadding(0,ScreenUtil.dip2px(10),0,0);
                }
                if(adResource!=null) {
                    UniversalImageLoader.displayImage(context,adResource.getPic(),shareAdHolder.ivAd);
                }
                shareAdHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Util.urlAction(context,adResource.getUrl()+"?id="+id);
                    }
                });
                break;
            case 13:
                ProductItemTitleHolder titleHolder= (ProductItemTitleHolder) holder;
                if (isShort){
                    titleHolder.titleMore.setVisibility(View.VISIBLE);
                }else {
                    titleHolder.titleMore.setVisibility(View.GONE);
                }
                if (Session.getInstance().getUser()!=null&&Session.getInstance().getUser().getPartnerId()>0){
                    titleHolder.titleName.setText(Util.getProductTitle("素材",ProductFragment.totalCount));
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
                break;
            case 14:
                final ProductHasPicHolder hasPicHolder= (ProductHasPicHolder) holder;
                if(ProductFragment.picCount<=0) {
                    hasPicHolder.picTv.setVisibility(View.GONE);
                }
                if(ProductFragment.shareCount<=0) {
                    hasPicHolder.showTv.setVisibility(View.GONE);
                }
                hasPicHolder.picTv.setText("有图("+(ProductFragment.picCount)+")");
                hasPicHolder.showTv.setText("买家秀("+ProductFragment.shareCount+")");

                hasPicHolder.allTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickListener!=null){
                            clickListener.clickAll();
                        }
                        hasPicHolder.allTv.setEnabled(false);
                        hasPicHolder.allTv.setTextColor(context.getResources().getColor(R.color.color_white));
                        hasPicHolder.picTv.setEnabled(true);
                        hasPicHolder.showTv.setEnabled(true);
                        hasPicHolder.picTv.setTextColor(context.getResources().getColor(R.color.color_black50));
                        hasPicHolder.showTv.setTextColor(context.getResources().getColor(R.color.color_black50));
                        hasPicHolder.allTv.setBackgroundColor(context.getResources().getColor(R.color.color_black_bg1));
                        hasPicHolder.picTv.setBackgroundColor(context.getResources().getColor(R.color.color_white5));
                        hasPicHolder.showTv.setBackgroundColor(context.getResources().getColor(R.color.color_white5));
                    }
                });
                hasPicHolder.picTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickListener!=null){
                            clickListener.clickHasPic();
                        }
                        hasPicHolder.allTv.setEnabled(true);
                        hasPicHolder.picTv.setEnabled(false);
                        hasPicHolder.picTv.setTextColor(context.getResources().getColor(R.color.color_white));
                        hasPicHolder.showTv.setEnabled(true);
                        hasPicHolder.allTv.setTextColor(context.getResources().getColor(R.color.color_black50));
                        hasPicHolder.showTv.setTextColor(context.getResources().getColor(R.color.color_black50));
                        hasPicHolder.picTv.setBackgroundColor(context.getResources().getColor(R.color.color_black_bg1));
                        hasPicHolder.allTv.setBackgroundColor(context.getResources().getColor(R.color.color_white5));
                        hasPicHolder.showTv.setBackgroundColor(context.getResources().getColor(R.color.color_white5));
                    }
                });
                hasPicHolder.showTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickListener!=null){
                            clickListener.clickOnlyShow();
                        }
                        hasPicHolder.allTv.setEnabled(true);
                        hasPicHolder.picTv.setEnabled(true);
                        hasPicHolder.showTv.setEnabled(false);
                        hasPicHolder.showTv.setTextColor(context.getResources().getColor(R.color.color_white));
                        hasPicHolder.allTv.setTextColor(context.getResources().getColor(R.color.color_black50));
                        hasPicHolder.picTv.setTextColor(context.getResources().getColor(R.color.color_black50));
                        hasPicHolder.showTv.setBackgroundColor(context.getResources().getColor(R.color.color_black_bg1));
                        hasPicHolder.allTv.setBackgroundColor(context.getResources().getColor(R.color.color_white5));
                        hasPicHolder.picTv.setBackgroundColor(context.getResources().getColor(R.color.color_white5));
                    }
                });
                break;
            case 15:
                int offer=0;
                if (isShort){
                    if (adResource!=null){
                        offer++;
                    }
                    offer++;
                }else {
                    if (adResource!=null){
                        offer++;
                    }
                    offer++;
                }
                final ProductShowBean.DataEntity.MembersharesEntity.ListEntity listEntity;
                if(list.size()!=0) {
                    listEntity=list.get(position-offer);
                }else{
                    return;
                }

                final ProductCommendHolder showItemHolder= (ProductCommendHolder) holder;

                UniversalImageLoader.displayRoundImage(context, Util.getD2cPicUrl(listEntity.getMemberHead()),showItemHolder.imgAvatar, R.mipmap.ic_default_avatar);
                showItemHolder.nameTv.setText(listEntity.getMemberName());
                showItemHolder.timeTv.setText(DateUtil.getFriendlyTime2(listEntity.getCreateDate()));
                /*if(user!=null){
                    if(listEntity.getMemberId()==user.getMemberId()){
                        showItemHolder.ivFocus.setVisibility(View.GONE);
                    }
                }*/
                showItemHolder.imgAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, PersonInfoActivity.class);
                        intent.putExtra("memberId",listEntity.getMemberId());
                        context.startActivity(intent);
                    }
                });
                if (listEntity.getDesignerId()>0){
                    showItemHolder.imgTag.setVisibility(View.VISIBLE);
                }else {
                    showItemHolder.imgTag.setVisibility(View.GONE);
                }
                /*switch (listEntity.getFollow()){
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
                        focusComplete(listEntity,showItemHolder);
                    }
                });*/
                if (!Util.isEmpty(listEntity.getDescription())||!Util.isEmpty(listEntity.getTopicName())) {
                    showItemHolder.contentTv.setVisibility(View.VISIBLE);
                    StringBuilder builder=new StringBuilder();
                    if (!Util.isEmpty(listEntity.getTopicName())){
                        int end=listEntity.getTopicName().length()+2;
                        builder.append("#"+listEntity.getTopicName()+"#");
                        if (!Util.isEmpty(listEntity.getDescription())){
                            builder.append(listEntity.getDescription());
                        }
                        SpannableString sb=new SpannableString(builder.toString());
                        sb.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View widget) {
                                Intent intent=new Intent(context, TopicDetailActivity.class);
                                intent.putExtra("id",listEntity.getTopicId());
                                context.startActivity(intent);
                            }
                        },0,listEntity.getTopicName().length()+2,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        sb.setSpan(new ForegroundColorSpan(Color.BLACK),0,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        showItemHolder.contentTv.setMovementMethod(LinkMovementMethod.getInstance());
                        showItemHolder.contentTv.setText(sb);
                    }else {
                        showItemHolder.contentTv.setText(listEntity.getDescription());
                    }
                } else {
                    showItemHolder.contentTv.setVisibility(View.GONE);
                }

                String videoUrl1 = listEntity.getVideo();
                List<String> imgList = listEntity.getPics();
                if(Util.isEmpty(videoUrl1)) {
                    showItemHolder.nineGrid.setVisibility(View.VISIBLE);
                    setPictures(listEntity,showItemHolder.nineGrid,imgList);
                    showItemHolder.videoLayout.setVisibility(View.GONE);
                }else {
                    if (!videoUrl1.startsWith("http")) {
                        videoUrl1 = Constants.IMG_HOST + videoUrl1;
                    }
                    /*TxVideoPlayerController txVideoPlayerController=new TxVideoPlayerController(mContext);
                    showItemHolder.niceVideoPlayer.setController(txVideoPlayerController);*/
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
                showItemHolder.likeNum.setText(listEntity.getLikeMeCount()+"");
                if(listEntity.getLiked()>0){
                    Drawable nav_up=context.getResources().getDrawable(R.mipmap.icon_goodsdetail_dianzan1);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    showItemHolder.likeNum.setCompoundDrawables(nav_up, null, null, null);
                }else {
                    Drawable nav_up=context.getResources().getDrawable(R.mipmap.icon_goodsdetail_dianzan0);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    showItemHolder.likeNum.setCompoundDrawables(nav_up, null, null, null);
                }
                showItemHolder.likeNum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        likeShow(listEntity,showItemHolder);
                    }
                });
                showItemHolder.commentNum.setText(listEntity.getCommentCount()+"");
                showItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, ShowDetailActivity.class);
                        intent.putExtra("id",listEntity.getId());
                        context.startActivity(intent);
                    }
                });
                break;
        }
    }

    private void focusComplete(ProductShowBean.DataEntity.MembersharesEntity.ListEntity memberBean, final ProductShowHolder holder) {
        FollowApi api=new FollowApi();
        api.setToMemberId(memberBean.getMemberId());
        if (memberBean.getFollow() > 0) {
            memberBean.setFollow(0);
            holder.ivFocus.setImageResource(R.mipmap.button_fashion_care);
            api.setInterPath(Constants.DELETE_MY_FOLLOWS_URL);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    Util.showToast(context,"取消关注成功");
                }
            });
        } else {

            memberBean.setFollow(1);
            api.setInterPath(Constants.INSERT_MY_FOLLOWS_URL);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ClickFollowBean>() {
                @Override
                public void onResponse(ClickFollowBean clickFollowBean) {
                    if (clickFollowBean.getData().getFollow()==2){
                        holder.ivFocus.setImageResource(R.mipmap.button_fashion_mutualcare);
                    }else if (clickFollowBean.getData().getFollow()==1){
                        holder.ivFocus.setImageResource(R.mipmap.button_fashion_cared);
                    }
                    Util.showToast(context,"关注成功");
                    holder.ivFocus.showMsgPop((Activity) context,context.getString(R.string.label_pop_focus_people));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Util.showToast(context,Util.checkErrorType(error));
                }
            });
        }
    }

    private void setPictures(final ProductShowBean.DataEntity.MembersharesEntity.ListEntity listEntity, NineGridView nineGridView, List<String> imgList) {

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
        nineGridView.setAdapter(new ClickNineGridViewAdapter(context, imageInfos){
            @Override
            protected void onImageItemClick(Context context, NineGridView nineGridView, int index, List<ImageInfo> imageInfo) {
                if (index==2){
                    Intent intent=new Intent(context,ShowDetailActivity.class);
                    intent.putExtra("id",listEntity.getId());
                    context.startActivity(intent);
                }else {
                    super.onImageItemClick(context, nineGridView, index, imageInfo);
                }
            }
        });
    }

    private void likeShow(final ProductShowBean.DataEntity.MembersharesEntity.ListEntity data, final ProductCommendHolder holder) {
        if (Util.loginChecked((ProductDetailActivity)context, Constants.Login.EXPLORE_DETAIL_LOGIN)) {
            if (data != null) {
                if (data.getLiked() > 0) {
                    SimpleApi api = new SimpleApi();
                    api.setMethod(BaseApi.Method.POST);
                    api.setInterPath(String.format(DELETE_SHARE_LIKE, data.getId()));
                    final Drawable nav_dowm=context.getResources().getDrawable(R.mipmap.icon_goodsdetail_dianzan0);
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
                            Util.showToast(context,Util.checkErrorType(error));
                        }
                    });
                } else {
                    final Drawable nav_up=context.getResources().getDrawable(R.mipmap.icon_goodsdetail_dianzan1);
                    nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                    SimpleApi api=new SimpleApi();
                    api.setMethod(BaseApi.Method.POST);
                    api.setInterPath(String.format(LIKE_SHARE_URL,data.getId()));
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

    @Override
    public int getItemCount() {
        if (isShort){
            int count=0;
            if (adResource!=null){
                count++;
            }
            if (list.size()>0){
                count++;
            }
            count+=list.size()>1?1:list.size();
            return count;
        }else {
            int count=0;
            if (adResource!=null){
                count++;
            }
            if (list.size()>0||ProductFragment.picCount>0){
                count++;
            }
//            count++;
            if (!hideList){
                count+=list.size();
            }
            return count;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShort){
            if (adResource!=null){
                if (position==0){
                    return 12; //广告
                }else if (position==1){
                    return 13; //title
                }else {
                    return 15; //内容
                }
            }else {
                if (position==0){
                    return 13;
                }else {
                    return 15;
                }
            }
        }else {
            int bannerIndex=-1;
            int hasPicIndex=-1;
            int titleIndex=-1;
            if (adResource!=null){
                bannerIndex=0;
            }
            if (list.size()>0||ProductFragment.picCount>0){
                hasPicIndex=bannerIndex+1;
            }
//            titleIndex=hasPicIndex+1;
            if (bannerIndex==position){
                return 12; //广告
            }else if (hasPicIndex==position){
                return 14; //有图买家秀
            }else {
                return 15;
            }
        }
    }

    public void isShort(boolean is){
        this.isShort=is;
    }

    public void setAdResource(AdShareBean.DataBean.AdResourceBean adResource) {
        this.adResource=adResource;
    }

    public boolean hasAd(){
        return adResource!=null;
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setHideList(boolean hideList) {
        this.hideList = hideList;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setName(String name) {
        this.name = name;
    }

    public interface ClickListener{
        void clickAll();
        void clickHasPic();
        void clickOnlyShow();
    }
}
