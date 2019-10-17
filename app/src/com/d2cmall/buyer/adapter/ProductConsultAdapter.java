package com.d2cmall.buyer.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.ConsultListBean;
import com.d2cmall.buyer.fragment.ProductFragment;
import com.d2cmall.buyer.holder.ProductConsultHolder;
import com.d2cmall.buyer.holder.ProductItemTitleHolder;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Name: D2CMALL
 * Anthor: hrb
 * Date: 2017/9/5 14:27
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ProductConsultAdapter extends DelegateAdapter.Adapter {

    private Context context;
    private List<ConsultListBean.DataEntity.ConsultsEntity.ListEntity> consultList;
    private boolean isShort;

    public ProductConsultAdapter(Context context,List<ConsultListBean.DataEntity.ConsultsEntity.ListEntity> list){
        this.context=context;
        this.consultList=list;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper=new LinearLayoutHelper();
        if (!isShort){
            linearLayoutHelper.setDividerHeight(ScreenUtil.dip2px(10));
            linearLayoutHelper.setBgColor(Color.parseColor("#FAFAFC"));
        }
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=null;
        switch (viewType){
            case 16:
                view= LayoutInflater.from(context).inflate(R.layout.layout_product_item_title,parent,false);
                return new ProductItemTitleHolder(view);
            case 17:
                view=LayoutInflater.from(context).inflate(R.layout.layout_product_shili,parent,false);
                return new HFRecyclerViewAdapter.ViewHolder(view);
            case 18:
                view=LayoutInflater.from(context).inflate(R.layout.layout_consult_item,parent,false);
                return new ProductConsultHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case 16:
                ProductItemTitleHolder titleHolder= (ProductItemTitleHolder) holder;
                titleHolder.titleName.setText(Util.getProductTitle("购买咨询",ProductFragment.consultCount));
                titleHolder.titleMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActionBean actionBean=new ActionBean(Constants.ActionType.CHANGE_PRODUCT_PAGE);
                        actionBean.put("position",2);
                        actionBean.put("second",1);
                        EventBus.getDefault().post(actionBean);
                    }
                });
                break;
            case 17:
                /*TextView tv1= (TextView) holder.itemView.findViewById(R.id.tv1);
                tv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setPrimaryClip(ClipData.newPlainText("code", "D2C8001"));
                        Util.showToast(context, R.string.msg_copy_ok);
                    }
                });*/
                break;
            case 18:
                ProductConsultHolder consultHolder= (ProductConsultHolder) holder;
                int offer=0;
                if (isShort){
                    offer=1;
                    consultHolder.firstLl.setVisibility(View.GONE);
                    LinearLayout.LayoutParams LL= (LinearLayout.LayoutParams) consultHolder.secondRl.getLayoutParams();
                    LL.setMargins(0,0,0,0);
                    LL= (LinearLayout.LayoutParams) consultHolder.thirdRl.getLayoutParams();
                    LL.setMargins(0,ScreenUtil.dip2px(8),0,0);
                }
                ConsultListBean.DataEntity.ConsultsEntity.ListEntity consultsEntity=consultList.get(position-offer);
                UniversalImageLoader.displayRoundImage(context,consultsEntity.getHeadPic(),consultHolder.imgAvatar,R.mipmap.ic_default_avatar);
                consultHolder.name.setText(consultsEntity.getNickName());
                consultHolder.timeTv.setText(consultsEntity.getCreateDate());
                consultHolder.answerTv.setText(consultsEntity.getQuestion());
                consultHolder.daTv.setText(consultsEntity.getReply());
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (isShort){
            return 2;
        }else {
            return consultList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShort){
            if (position==0){
                return 16; //title
            }else if (position==1){
                return 18; //content
            }else {
                return 17; //死布局
            }
        }else {
            return 18;
        }
    }

    public void isShort(boolean is){
        this.isShort=is;
    }
}
