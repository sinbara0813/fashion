package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.RemoveChildApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.PartnerMemberListBean;
import com.d2cmall.buyer.holder.PartnerTeamHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;

/**
 * Fixme
 * Author: LWJ
 * desc:   买手团队Adapter
 * Date: 2017/09/06 19:20
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */

public class PartnerTeamAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    private ArrayList<PartnerMemberListBean.DataBean.ChildrenBean.ListBean> partnerList;
    int itemWidth;


    public PartnerTeamAdapter(Context context, ArrayList<PartnerMemberListBean.DataBean.ChildrenBean.ListBean> partnerList) {
        mContext = context;
        this.partnerList=partnerList;
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper layoutHelper = new LinearLayoutHelper();
        return layoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_partner_team_del_item, parent, false);
        return new PartnerTeamHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PartnerMemberListBean.DataBean.ChildrenBean.ListBean listBean = partnerList.get(position);
        final PartnerTeamHolder partnerTeamHolder = (PartnerTeamHolder) holder;
        if(!Util.isEmpty(listBean.getName())){
            partnerTeamHolder.tvFansNickName.setText(listBean.getName());
        }else{
            partnerTeamHolder.tvFansNickName.setText("匿名_"+listBean.getName());
        }
        partnerTeamHolder.tvFansShow.setText(mContext.getString(R.string.msg_partner_achivement_num,Util.getNumberFormat(listBean.getTotalOrderAmount())));
        partnerTeamHolder.tvPosition.setText(String.valueOf(position+1));
        partnerTeamHolder.btRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setTitle("确认解绑?")
                        .setMessage(Html.fromHtml(mContext.getResources().getString(R.string.lable_remove_partner_tip)))
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                removeMember(partnerTeamHolder.swipeMenu,listBean);
                            }
                        })
                        .show();
            }
        });
        UniversalImageLoader.displayRoundImage(mContext, listBean.getHeadPic(), partnerTeamHolder.ivPersonHead, R.mipmap.ic_default_avatar);
    }

    private void removeMember(final View swipeMenu, final PartnerMemberListBean.DataBean.ChildrenBean.ListBean listBean) {//移除成员
        swipeMenu.setEnabled(false);
        RemoveChildApi api = new RemoveChildApi();
        api.setChildId(listBean.getId());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                swipeMenu.setEnabled(true);
                partnerList.remove(listBean);
                notifyDataSetChanged();
                Util.showToast(mContext, "解绑成功");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeMenu.setEnabled(true);
                Util.showToast(mContext, Util.checkErrorType(error));
            }
        });
    }

    @Override
    public int getItemCount() {
        return partnerList.size();
    }
}
