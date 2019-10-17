package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
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
import com.d2cmall.buyer.bean.BrandFollowBean;
import com.d2cmall.buyer.bean.ClickFollowBean;
import com.d2cmall.buyer.bean.ShowLikeBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.holder.FansHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Created by rookie on 2017/9/18.
 */

public class BrandFollowAdapter extends DelegateAdapter.Adapter<FansHolder> {

    private List<Object> list;
    private Context context;

    public BrandFollowAdapter(List<Object> list, Context context) {
        this.list = list;
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
    public void onBindViewHolder(final FansHolder fansHolder, final int position) {
        if (list.get(position) instanceof BrandFollowBean.DataBean.AttentionsBean.ListBean) {
            final BrandFollowBean.DataBean.AttentionsBean.ListBean data = (BrandFollowBean.DataBean.AttentionsBean.ListBean) list.get(position);
            UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(context);
            if (user != null) {
                if (user.getId() == data.getMemberId()) {
                    fansHolder.mIvFocusType.setVisibility(View.GONE);
                }else {
                    fansHolder.mIvFocusType.setVisibility(View.VISIBLE);
                }
            }
            UniversalImageLoader.displayRoundImage(context, data.getHeadPic(), fansHolder.mIvFansHeadPic, R.mipmap.ic_default_avatar);
            fansHolder.mTvFansNickName.setText(data.getNickName());
            fansHolder.mTvFansShow.setVisibility(View.GONE);
            fansHolder.mIvFansHeadPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转用户详情
                    Intent intent = new Intent(context, PersonInfoActivity.class);
                    intent.putExtra("memberId", data.getMemberId());
                    context.startActivity(intent);
                }
            });
            switch (data.getFollow()) {
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
                    if (Util.loginChecked((Activity) context, 999)) {
                        focusComplete(data, fansHolder);
                    }
                }
            });
        } else if (list.get(position) instanceof ShowLikeBean.DataBean.LikesBean.ListBean) {
            final ShowLikeBean.DataBean.LikesBean.ListBean fansBean = (ShowLikeBean.DataBean.LikesBean.ListBean) list.get(position);
            UniversalImageLoader.displayRoundImage(context, fansBean.getHeadPic(), fansHolder.mIvFansHeadPic, R.mipmap.ic_default_avatar);
            UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(context);
            if (user != null) {
                if (user.getId() == fansBean.getMemberId()) {
                    fansHolder.mIvFocusType.setVisibility(View.GONE);
                }else {
                    fansHolder.mIvFocusType.setVisibility(View.VISIBLE);
                }
            }
            fansHolder.mTvFansNickName.setText(fansBean.getNickName());
            if(TextUtils.isEmpty(fansBean.getMemberShare())) {
                fansHolder.mTvFansShow.setText("暂无动态");
            }else{
                fansHolder.mTvFansShow.setText(fansBean.getMemberShare());
            }


            fansHolder.mIvFansHeadPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转用户详情
                    Intent intent = new Intent(context, PersonInfoActivity.class);
                    intent.putExtra("memberId", fansBean.getMemberId());
                    context.startActivity(intent);
                }
            });
            switch (fansBean.getFollow()) {
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
                    if (Util.loginChecked((Activity) context, 999)) {
                        focusComplete(fansBean, fansHolder);
                    }
                }
            });
        }
    }

    private void focusComplete(final BrandFollowBean.DataBean.AttentionsBean.ListBean fansBean, final FansHolder holder) {
        FollowApi api = new FollowApi();
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
                    holder.mIvFocusType.showMsgPop((Activity) context,context.getString(R.string.label_pop_focus_brand));
                    notifyDataSetChanged();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Util.showToast(context, Util.checkErrorType(error));
                }
            });
        }
    }

    private void focusComplete(ShowLikeBean.DataBean.LikesBean.ListBean fansBean, final FansHolder holder) {
        FollowApi api = new FollowApi();
        api.setToMemberId(fansBean.getMemberId());
        if (fansBean.getFollow() > 0) {
            fansBean.setFollow(0);
            holder.mIvFocusType.setImageResource(R.mipmap.button_fashion_care);
            api.setInterPath(Constants.DELETE_MY_FOLLOWS_URL);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean response) {
                    Util.showToast(context, "取消关注成功");
                }
            });
        } else {
            fansBean.setFollow(1);
            api.setInterPath(Constants.INSERT_MY_FOLLOWS_URL);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<ClickFollowBean>() {
                @Override
                public void onResponse(ClickFollowBean clickFollowBean) {
                    if (clickFollowBean.getData().getFollow() == 1) {
                        holder.mIvFocusType.setImageResource(R.mipmap.button_fashion_cared);
                    } else {
                        holder.mIvFocusType.setImageResource(R.mipmap.button_fashion_care);
                    }
                    Util.showToast(context, "关注成功");
                    holder.mIvFocusType.showMsgPop((Activity) context,context.getString(R.string.label_pop_focus_brand));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Util.showToast(context, Util.checkErrorType(error));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
