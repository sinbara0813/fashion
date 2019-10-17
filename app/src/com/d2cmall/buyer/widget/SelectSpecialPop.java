package com.d2cmall.buyer.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.d2cmall.buyer.Decoration.SpecialDecoration;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.SpecialGalleryAdapter;
import com.d2cmall.buyer.bean.ThemeTagBean;
import com.d2cmall.buyer.listener.OnItemClickListener;
import com.d2cmall.buyer.util.ScreenUtil;

import java.util.List;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/7/11 15:10
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class SelectSpecialPop implements TransparentPop.InvokeListener {

    SpecialGalleryAdapter adapter;
    private RecyclerView recyclerView;
    private View rootView;
    private TransparentPop pop;
    private Context mContext;
    private List<ThemeTagBean.DataBean.CountTagsBean> list2;
    private OnItemClickListener onItemClickListner;
    private TransparentPop.DismissListener dismissListener2;

    public SelectSpecialPop(Context context,OnItemClickListener onItemClickListner,List<ThemeTagBean.DataBean.CountTagsBean> list,TransparentPop.DismissListener dismissListener2) {
        this.mContext=context;
        this.onItemClickListner=onItemClickListner;
        this.list2=list;
        this.dismissListener2=dismissListener2;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_select_specail, null);
        recyclerView=(RecyclerView) rootView;
        //int height=ScreenUtil.screenHeight-ScreenUtil.statusbarheight-ScreenUtil.dip2px(101)-5;
        int height= ScreenUtil.screenHeight-ScreenUtil.statusbarheight-ScreenUtil.dip2px(56);
        pop=new TransparentPop(context,-1,height,true,this);
        pop.setFocusable(false);
        /*pop.setBackgroundDrawable(new BitmapDrawable(mContext.getResources(),
                FastBlur.getBlurBackgroundDrawer((Activity) context,ScreenUtil.dip2px(56))));*/
        pop.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.top_slide_in));
        pop.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.top_slide_out));
        adapter=new SpecialGalleryAdapter(mContext,list2,onItemClickListner);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,3));
        recyclerView.addItemDecoration(new SpecialDecoration());
        recyclerView.setAdapter(adapter);
        pop.setDismissListener(dismissListener2);
        //loadData();
    }




    public void show(View view){
        //pop.show(view,-1,ScreenUtil.dip2px(11)-5,true);
        pop.show(view,-1,ScreenUtil.dip2px(56),true);
    }

    public void dismiss(){
        pop.dismiss(true);
    }

    public boolean isShow(){
        if (pop!=null){
            return pop.isShowing();
        }
        return false;
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.TOP);
        v.addView(rootView);
    }

    @Override
    public void releaseView(LinearLayout v) {
        v.removeAllViews();
        rootView=null;
    }
}
