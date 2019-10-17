package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.PersonInfoActivity;
import com.d2cmall.buyer.api.FollowApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ClickFollowBean;
import com.d2cmall.buyer.bean.MineFansBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.FansHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Fixme
 * Author: LWJ
 * desc:   粉丝的适配器
 * Date: 2017/09/06 17:32
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class FansAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    private List<MineFansBean.DataBean.MyFansBean.ListBean> fansList;
    private final UserBean.DataEntity.MemberEntity mUser;
    public FansAdapter(Context context, List<MineFansBean.DataBean.MyFansBean.ListBean> fansList) {
        mContext = context;
        this.fansList = fansList;
        mUser = Session.getInstance().getUserFromFile(mContext);
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_fans_item, parent, false);
        return new FansHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final MineFansBean.DataBean.MyFansBean.ListBean fansBean = fansList.get(position);

        FansHolder fansHolder = (FansHolder) holder;
        //隐藏最后一条分割线(最后一条需要match_parent)
        if (position==fansList.size()){
            fansHolder.mDividing.setVisibility(View.GONE);
        }
        UniversalImageLoader.displayRoundImage(mContext,fansBean.getHeadPic(),fansHolder.mIvFansHeadPic,R.mipmap.ic_default_avatar);
        fansHolder.mTvFansNickName.setText(fansBean.getNickName());
        fansHolder.mTvFansShow.setText(fansBean.getMemberShare());
        fansHolder.mIvFansHeadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转用户详情
                Intent intent = new Intent(mContext, PersonInfoActivity.class);
                intent.putExtra("memberId",Long.valueOf(fansBean.getMemberId()));
                mContext.startActivity(intent);
            }
        });
        switch (fansBean.getFollow()){
            case 0:
                fansHolder.mIvFocusType.setImageResource(R.mipmap.button_fashion_care);
                break;
            case 1:
                fansHolder.mIvFocusType.setImageResource(R.mipmap.button_fashion_cared);
                break;
            case 2:
                fansHolder.mIvFocusType.setImageResource(R.mipmap.button_fashion_mutualcare);
                break;
        }
        if (mUser != null && mUser.getMemberId() == fansBean.getMemberId()) {
            fansHolder.itemView.setVisibility(View.GONE);
        } else {
            fansHolder.itemView.setVisibility(View.VISIBLE);
        }
        //开启消息推送行为节点
        fansHolder.mIvFocusType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MineFansBean.DataBean.MyFansBean.ListBean fansBean = fansList.get(position);
                focusComplete(fansBean, (FansHolder) holder);
            }
        });
        
    }
    //关注与取消关注
    private void focusComplete(final MineFansBean.DataBean.MyFansBean.ListBean fansBean, final FansHolder holder) {
        FollowApi api=new FollowApi();
        api.setToMemberId(fansBean.getMemberId());
        if (fansBean.getFollow() > 0) {
            api.setInterPath(Constants.DELETE_MY_FOLLOWS_URL);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    fansBean.setFollow(0);
                    Util.showToast(mContext,"取消关注成功");
                    holder.mIvFocusType.setImageResource(R.mipmap.button_fashion_care);
                }
            });
        } else {
            api.setInterPath(Constants.INSERT_MY_FOLLOWS_URL);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ClickFollowBean>() {
                @Override
                public void onResponse(ClickFollowBean clickFollowBean) {
                    fansBean.setFollow(clickFollowBean.getData().getFollow());
                    if (clickFollowBean.getData().getFollow()==1){
                        holder.mIvFocusType.setImageResource(R.mipmap.button_fashion_cared);
                    }else if(clickFollowBean.getData().getFollow()==2) {
                        holder.mIvFocusType.setImageResource(R.mipmap.button_fashion_mutualcare);
                    }
                    Util.showToast(mContext,"关注成功");
                    holder.mIvFocusType.showMsgPop((Activity) mContext,mContext.getString(R.string.label_pop_focus_people));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Util.showToast(mContext,Util.checkErrorType(error));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return  fansList==null?0:fansList.size();
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }
}
