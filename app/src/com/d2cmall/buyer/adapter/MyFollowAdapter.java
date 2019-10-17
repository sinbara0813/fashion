package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.d2cmall.buyer.bean.FollowsBean;
import com.d2cmall.buyer.holder.FansHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Created by rookie on 2017/9/14.
 */

public class MyFollowAdapter extends DelegateAdapter.Adapter<FansHolder> {

    private List<FollowsBean.DataBean.MyFollowsBean.ListBean> listEntities;
    private Context context;

    public MyFollowAdapter(List<FollowsBean.DataBean.MyFollowsBean.ListBean> listEntities, Context context) {
        this.listEntities = listEntities;
        this.context = context;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public FansHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_fans_item, parent, false);
        return new FansHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FansHolder holder, final int position) {
        final FollowsBean.DataBean.MyFollowsBean.ListBean fansBean = listEntities.get(position);

        FansHolder fansHolder =holder;
        //隐藏最后一条分割线(最后一条需要match_parent)
        if (position==listEntities.size()){
            fansHolder.mDividing.setVisibility(View.GONE);
        }
        UniversalImageLoader.displayRoundImage(context,fansBean.getHeadPic(),fansHolder.mIvFansHeadPic,R.mipmap.ic_default_avatar);
        fansHolder.mTvFansNickName.setText(fansBean.getNickName());
        fansHolder.mTvFansShow.setText(fansBean.getMemberShare());
        fansHolder.mTvFansNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PersonInfoActivity.class);
                intent.putExtra("memberId",(long)fansBean.getMemberId());
                context.startActivity(intent);
            }
        });
        fansHolder.mIvFansHeadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转用户详情
                Intent intent=new Intent(context, PersonInfoActivity.class);
                intent.putExtra("memberId",(long)fansBean.getMemberId());
                context.startActivity(intent);
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
        fansHolder.mIvFocusType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FollowsBean.DataBean.MyFollowsBean.ListBean fansBean = listEntities.get(position);
                if(Util.loginChecked((Activity) context,999)){
                    focusComplete(fansBean,holder);
                }
            }
        });
    }

    private void focusComplete(final FollowsBean.DataBean.MyFollowsBean.ListBean fansBean, final FansHolder holder) {
        FollowApi api=new FollowApi();
        api.setToMemberId(fansBean.getMemberId());
        if (fansBean.getFollow() > 0) {
            api.setInterPath(Constants.DELETE_MY_FOLLOWS_URL);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    fansBean.setFollow(0);
                    holder.mIvFocusType.setImageResource(R.mipmap.button_fashion_care);
                    Util.showToast(context, "取消关注成功");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Util.showToast(context,Util.checkErrorType(error));
                }
            });
        } else {
            api.setInterPath(Constants.INSERT_MY_FOLLOWS_URL);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ClickFollowBean>() {
                @Override
                public void onResponse(ClickFollowBean clickFollowBean) {
                    fansBean.setFollow(clickFollowBean.getData().getFollow());
                    if (clickFollowBean.getData().getFollow()==0){
                        holder.mIvFocusType.setImageResource(R.mipmap.button_fashion_care);
                    }else if(clickFollowBean.getData().getFollow()==1) {
                        holder.mIvFocusType.setImageResource(R.mipmap.button_fashion_cared);
                    }else if(clickFollowBean.getData().getFollow()==2) {
                        holder.mIvFocusType.setImageResource(R.mipmap.button_fashion_mutualcare);
                    }
                    Util.showToast(context,"关注成功");
                    holder.mIvFocusType.showMsgPop((Activity) context,context.getString(R.string.label_pop_focus_people));
                    notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Util.showToast(context,Util.checkErrorType(error));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listEntities==null?0:listEntities.size();
    }
}
