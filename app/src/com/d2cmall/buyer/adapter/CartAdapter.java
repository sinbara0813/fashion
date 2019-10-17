package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.CartListBean;
import com.d2cmall.buyer.holder.DefaultHolder;
import com.d2cmall.buyer.listener.CheckStateChangeListener;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.widget.SliderView1;
import com.d2cmall.buyer.widget.swipemenulistview.SwipeMenu;
import com.d2cmall.buyer.widget.swipemenulistview.SwipeMenuItem;
import com.d2cmall.buyer.widget.swipemenulistview.SwipeMenuLayout;
import com.d2cmall.buyer.widget.swipemenulistview.SwipeMenuView;

import java.util.List;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/2 15:50
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CartAdapter extends DelegateAdapter.Adapter{

    private Context context;
    private List<CartListBean.DataBean.CartBean> cartList;
    private List<CartListBean.DataBean.CartBean> selectCrossList;
    private List<CartListBean.DataBean.CartBean> selectCommonList;
    private SwipeMenuView.OnSwipeItemClickListener listener;
    private SliderView1.CartListener cartListener;
    private CheckCallBack checkStateChangeListener;
    private View.OnClickListener clearListener;
    private boolean isEditState;
    private boolean hasInvalid;
    private int limitStore;

    public CartAdapter(Context context, List<CartListBean.DataBean.CartBean> cartList, List<CartListBean.DataBean.CartBean> selectCrossList, List<CartListBean.DataBean.CartBean> selectCommonList,SwipeMenuView.OnSwipeItemClickListener listener, SliderView1.CartListener cartListener){
        this.context=context;
        this.cartList=cartList;
        this.selectCrossList=selectCrossList;
        this.selectCommonList=selectCommonList;
        this.listener=listener;
        this.cartListener = cartListener;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        LinearLayoutHelper linearLayoutHelper=new LinearLayoutHelper();
        return linearLayoutHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==1){
            View view= LayoutInflater.from(context).inflate(R.layout.layout_cart_foot_view,new LinearLayout(context),false);
            TextView clearTv= (TextView) view.findViewById(R.id.clear_tv);
            if (clearListener!=null){
                clearTv.setOnClickListener(clearListener);
            }
            return new DefaultHolder(view);
        }else if (viewType==2){
            View view= LayoutInflater.from(context).inflate(R.layout.layout_empty_cart,new LinearLayout(context),false);
            return new DefaultHolder(view);
        } else {
            SliderView1 sliderView=new SliderView1(context, cartListener);
            SwipeMenu menu = new SwipeMenu(context);
            createMenu(menu);
            SwipeMenuView menuView = new SwipeMenuView(menu, null);
            menuView.setOnSwipeItemClickListener(listener);
            SwipeMenuLayout layout = new SwipeMenuLayout(sliderView, menuView, null, null);
            return new DefaultHolder(layout);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position)==0){
            SwipeMenuLayout swipeMenuLayout= (SwipeMenuLayout) holder.itemView;
            swipeMenuLayout.setPosition(position);
            SliderView1 sliderView= (SliderView1) swipeMenuLayout.getContentView();

            final CartListBean.DataBean.CartBean entity=cartList.get(position);
            sliderView.isEdit(isEditState);
            if (position==cartList.size()-1){
                sliderView.setBg(true);
            }else {
                sliderView.setBg(false);
            }
            sliderView.setLimitStore(limitStore);
            sliderView.setData(entity);
            if (selectCrossList.contains(entity)||selectCommonList.contains(entity)) {
                sliderView.setChecked(true);
            } else {
                sliderView.setChecked(false);
            }
            sliderView.setSelectListener(new CheckStateChangeListener() {
                @Override
                public void onStatusChanged(View v, boolean isChecked) {
                    if ("CROSS".equals(entity.getProductTradeType())){
                        if (isChecked && !selectCrossList.contains(entity)) {
                            selectCrossList.add(entity);
                        }
                        if (!isChecked && selectCrossList.contains(entity)) {
                            selectCrossList.remove(entity);
                        }
                    }else if ("COMMON".equals(entity.getProductTradeType())){
                        if (isChecked && !selectCommonList.contains(entity)) {
                            selectCommonList.add(entity);
                        }
                        if (!isChecked && selectCommonList.contains(entity)) {
                            selectCommonList.remove(entity);
                        }
                    }
                    if (checkStateChangeListener!=null){
                        checkStateChangeListener.onStatusChanged(v,isChecked,entity);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return cartList.size()==0?1: hasInvalid ?cartList.size()+1:cartList.size();
    }

    public void createMenu(SwipeMenu menu) {
        SwipeMenuItem item1 = new SwipeMenuItem(
                context);
        item1.setBackground(R.color.color_white5);
        item1.setWidth(ScreenUtil.dip2px(56));
        item1.setTitle("移入\n收藏");
        item1.setTitleColor(context.getResources().getColor(R.color.trans_87_color_black));
        item1.setTitleSize(12);
        menu.addMenuItem(item1);
        SwipeMenuItem item2 = new SwipeMenuItem(
                context);
        item2.setBackground(R.color.color_black6);
        item2.setWidth(ScreenUtil.dip2px(56));
        item2.setTitle("删除");
        item2.setTitleColor(context.getResources().getColor(R.color.color_white));
        item2.setTitleSize(12);
        menu.addMenuItem(item2);
    }

    @Override
    public int getItemViewType(int position) {
        if (hasInvalid &&position==cartList.size()){
            return 1;
        }
        if (cartList==null||cartList.size()==0){
            return 2;
        }
        return super.getItemViewType(position);
    }

    public void setCheckStateChangeListener(CheckCallBack checkStateChangeListener) {
        this.checkStateChangeListener = checkStateChangeListener;
    }

    public void setEditState(boolean editState) {
        isEditState = editState;
    }

    public void setClearListener(View.OnClickListener listener){
        this.clearListener=listener;
    }

    public void setHasInvalid(boolean is){
        this.hasInvalid=is;
    }

    public interface CheckCallBack{
        void onStatusChanged(View v,boolean checked,CartListBean.DataBean.CartBean cartBean);
    }

    public void setLimitStore(int limitStore) {
        this.limitStore = limitStore;
    }
}
