package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.util.ScreenUtil;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.List;

/**
 * Created by rookie on 2017/8/7.
 */

public class FashionTabPop implements TransparentPop.InvokeListener  {
    private RecyclerView recyclerView;
    private View rootView;
    private TransparentPop pop;
    private Context mContext;
    private List<String> list;
    private TabPopupWindowAdapter adapter;
    private int position=0;
    private SlidingTabLayout slidingTab;
    private ImageView imageOpen;
    private TextView tvMakeChoice;
    private boolean isCallDismiss;
    private boolean expand;
    private int realHeight;

    public FashionTabPop(Context context, SlidingTabLayout slidingTab, ImageView imageOpen, TextView tvMakeChoice,int height){
        this.mContext=context;
        this.slidingTab=slidingTab;
        this.imageOpen=imageOpen;
        this.tvMakeChoice=tvMakeChoice;
        this.realHeight=height;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_popup_tablayout, null);
        recyclerView=(RecyclerView) rootView;
        int height2= ScreenUtil.screenHeight-ScreenUtil.statusbarheight-ScreenUtil.dip2px(realHeight);
        pop=new TransparentPop(context,-1,height2,true,this);
        pop.setFocusable(true); //获取焦点
        pop.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.top_slide_in));
        pop.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.top_slide_out));
        pop.setDismissListener(new TransparentPop.DismissListener() {
            @Override
            public void dismissStart() {
                dismiss();
            }

            @Override
            public void dismissEnd() {
                dismiss();
            }
        });
        imageOpen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pop.dismiss(true);
                return true;
            }
        });
    }

    public void setExpand(boolean expand){
        this.expand=expand;
    }

    public void setHeight(int height){
        this.realHeight=height;
    }

    public void setData(List<String> list,int position){
        this.list=list;
        this.position=position;
        adapter=new TabPopupWindowAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(mContext,4));
        recyclerView.setAdapter(adapter);
    }

    public void show(View view){
        //pop.show(view,-1,ScreenUtil.dip2px(realHeight),true);
        pop.showAsParent(view,true);
    }
    public void show(View view,boolean hasAnimation){
        //pop.show(view,-1,ScreenUtil.dip2px(realHeight),true);
        pop.showAsParent(view,hasAnimation);
    }
    public void dismiss(){
        if (isCallDismiss)
            return;
        imageOpen.clearAnimation();
        imageOpen.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.arrow_down));
        imageOpen.setOnTouchListener(null);
        tvMakeChoice.setVisibility(View.GONE);
        slidingTab.setVisibility(View.VISIBLE);
        isCallDismiss=true;
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

    public int getDistanceTop(){
        pop.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popHeight=pop.getContentView().getMeasuredHeight();
        return  popHeight;
    }

    public void makeBackImage(){
        rootView.setBackgroundColor(Color.WHITE);
        /*Drawable drawable=new BitmapDrawable( FastBlur.getBlurBackgroundDrawer((Activity) mContext,ScreenUtil.dip2px(88),getDistanceTop()));
        rootView.setBackground(drawable);*/
    }

    @Override
    public void releaseView(LinearLayout v) {
        v.removeAllViews();
        rootView=null;
    }

    public class TabPopupWindowAdapter extends RecyclerView.Adapter<TabPopupWindowAdapter.TabPopupViewHolder>{

        @Override
        public TabPopupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView=LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_popup_item,parent,false);
            return new TabPopupWindowAdapter.TabPopupViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(TabPopupViewHolder holder, int position) {
            holder.textView.setText(list.get(position));
            if(position==FashionTabPop.this.position){
                holder.textView.setBackground(mContext.getResources().getDrawable(R.drawable.popup_text_seleced_background));
                holder.textView.setTextColor(mContext.getResources().getColor(R.color.trans_87_color_black));
                holder.ivSelected.setVisibility(View.VISIBLE);
            }else{
                holder.textView.setBackground(mContext.getResources().getDrawable(R.drawable.popup_text_background));
                holder.textView.setTextColor(mContext.getResources().getColor(R.color.trans_50_color_black));
                holder.ivSelected.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class TabPopupViewHolder extends RecyclerView.ViewHolder{
            TextView textView;
            ImageView ivSelected;

            public TabPopupViewHolder(View itemView) {
                super(itemView);
                textView= (TextView) itemView.findViewById(R.id.tv_popup);
                ivSelected= (ImageView) itemView.findViewById(R.id.iv_select_icon);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        slidingTab.setCurrentTab(getAdapterPosition());
                        notifyDataSetChanged();
                        pop.dismiss(true);
                    }
                });
            }
        }

    }
}
