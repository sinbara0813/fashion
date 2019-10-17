package com.d2cmall.buyer.binder;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.bumptech.glide.RequestManager;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.HomeActivity;
import com.d2cmall.buyer.activity.PersonInfoActivity;
import com.d2cmall.buyer.activity.ShowDetailActivity;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.base.BaseViewBinder;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.MainShareBean;
import com.d2cmall.buyer.holder.MainShowHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.RoundedImageView;
import com.tendcloud.tenddata.TCAgent;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

import static com.d2cmall.buyer.Constants.DELETE_SHARE_LIKE;
import static com.d2cmall.buyer.Constants.LIKE_SHARE_URL;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/8/31 18:51
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MainShowBinder implements BaseViewBinder<MainShowHolder> {

    private Context context;
    private List<MainShareBean.DataBean.SharesBean> list;
    private RequestManager requestManager;

    public MainShowBinder(Context context, List<MainShareBean.DataBean.SharesBean> data) {
        this.context = context;
        this.list = data;
    }

    public MainShowBinder(Context context,RequestManager requestManager, List<MainShareBean.DataBean.SharesBean> data) {
        this.context = context;
        this.requestManager=requestManager;
        this.list = data;
    }

    @Override
    public MainShowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_recycleview, parent, false);
        return new MainShowHolder(view);
    }

    @Override
    public void onBindViewHolder(MainShowHolder mainShowHolder, int position) {
        mainShowHolder.titleAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionBean actionBean=new ActionBean(Constants.ActionType.CHANGE_PAGE);
                actionBean.put("firstIndex",3);
                actionBean.put("secondIndex",0);
                EventBus.getDefault().post(actionBean);
            }
        });
        mainShowHolder.recycleView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        mainShowHolder.recycleView.setAdapter(new MyAdapter(list));
    }

    @Override
    public void onBindViewHolderWithOffer(MainShowHolder mainShowHolder, int position, int offsetTotal) {

    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private List<MainShareBean.DataBean.SharesBean> list2;

        public MyAdapter(List<MainShareBean.DataBean.SharesBean> list) {
            this.list2 = list;
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_main_show_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return list2.size();
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            final MainShareBean.DataBean.SharesBean listEntity=list2.get(position);
            if (listEntity.getPics().size()>0){
                if (requestManager!=null){
                    requestManager.load(Util.getD2cPicUrl(listEntity.getPics().get(0))).into(holder.ivCover);
                }else {
                    UniversalImageLoader.displayImage(context,listEntity.getPics().get(0),holder.ivCover);
                }
            }
            holder.tvUserName.setText(listEntity.getMemberName());
            holder.tvLikeNum.setText(listEntity.getLikeMeCount()+"");
            holder.tvContent.setText(listEntity.getDescription());
            if(listEntity.getLiked()>0){
                holder.ivLike.setImageResource(R.mipmap.icon_fashion_liked_small);
            }else {
                holder.ivLike.setImageResource(R.mipmap.icon_fashion_like_small);
            }
            if (requestManager!=null){
                requestManager.load(Util.getD2cPicUrl(listEntity.getMemberHead())).placeholder(R.mipmap.ic_default_avatar).into(holder.ivUserHeadPic);
            }else {
                UniversalImageLoader.displayRoundImage(context,listEntity.getMemberHead(),holder.ivUserHeadPic,R.mipmap.ic_default_avatar);
            }
            holder.ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    likeShow(listEntity,holder);
                }
            });
            holder.tvLikeNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    likeShow(listEntity,holder);
                }
            });
            holder.ivUserHeadPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, PersonInfoActivity.class);
                    intent.putExtra("memberId",(long)listEntity.getMemberId());
                    context.startActivity(intent);
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ShowDetailActivity.class);
                    intent.putExtra("id",(long)listEntity.getId());
                    context.startActivity(intent);
                }
            });
        }

        private void likeShow(final MainShareBean.DataBean.SharesBean data, final MyViewHolder holder) {
            if (Util.loginChecked((HomeActivity)context, Constants.Login.EXPLORE_DETAIL_LOGIN)) {
                if (data != null) {
                    if (data.getLiked() > 0) {
                        SimpleApi api = new SimpleApi();
                        api.setMethod(BaseApi.Method.POST);
                        api.setInterPath(String.format(DELETE_SHARE_LIKE, data.getId()));
                        final Drawable nav_dowm=context.getResources().getDrawable(R.mipmap.icon_fashion_like);
                        nav_dowm.setBounds(0, 0, nav_dowm.getMinimumWidth(), nav_dowm.getMinimumHeight());
                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                            @Override
                            public void onResponse(BaseBean response) {
                                holder.tvLikeNum.setText(String.valueOf(Integer.valueOf(holder.tvLikeNum.getText().toString()) - 1));
                                holder.ivLike.setImageResource(R.mipmap.icon_fashion_like);
                                data.setLiked(0);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Util.showToast(context,Util.checkErrorType(error));
                            }
                        });
                    } else {
                        final Drawable nav_up=context.getResources().getDrawable(R.mipmap.icon_fashion_liked);
                        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
                        SimpleApi api=new SimpleApi();
                        api.setMethod(BaseApi.Method.POST);
                        api.setInterPath(String.format(LIKE_SHARE_URL,data.getId()));
                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                            @Override
                            public void onResponse(BaseBean response) {
                                //ivLike.setImageResource(R.mipmap.icon_fashion_liked);
                                holder.tvLikeNum.setText(String.valueOf(Integer.valueOf(holder.tvLikeNum.getText().toString()) + 1));
                                holder.ivLike.setImageResource(R.mipmap.icon_fashion_liked);
                                data.setLiked(1);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Util.showToast(context,Util.checkErrorType(error));
                            }
                        });
                    }
                }
            }
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            @Bind(R.id.iv_cover)
            ImageView ivCover;
            @Bind(R.id.tv_user_name)
            TextView tvUserName;
            @Bind(R.id.tv_like_num)
            TextView tvLikeNum;
            @Bind(R.id.iv_like)
            ImageView ivLike;
            @Bind(R.id.tv_content)
            TextView tvContent;
            @Bind(R.id.iv_user_head_pic)
            RoundedImageView ivUserHeadPic;

            public MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }
    }
}
