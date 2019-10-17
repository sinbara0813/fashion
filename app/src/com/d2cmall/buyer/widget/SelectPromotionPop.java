package com.d2cmall.buyer.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.CartListBean;
import com.d2cmall.buyer.util.Util;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Name: d2c
 * Anthor: hrb
 * Date: 2017/8/3 15:49
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class SelectPromotionPop implements TransparentPop.InvokeListener {

    @Bind(R.id.list_view)
    ListView listView;

    private View rootView;
    private TransparentPop pop;
    private List<CartListBean.DataBean.CartBean.OrderPromotionBean> promotions;
    private Context mContext;
    private CheckBox lastCheckBox;
    private String promotionName;
    private int currentPosition=-1;

    public SelectPromotionPop(Context context, List<CartListBean.DataBean.CartBean.OrderPromotionBean> promotions,String promotionName) {
        this.mContext = context;
        this.promotions = promotions;
        this.promotionName=promotionName;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_select_promotion, new LinearLayout(context),false);
        ButterKnife.bind(this,rootView);
        listView.setAdapter(new MyAdapter());
        pop = new TransparentPop(context, this);
        pop.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_up));
        pop.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_down));
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.BOTTOM);
        v.addView(rootView);
    }

    public void show(View view) {
        pop.show(view);
    }

    public void dismiss() {
        pop.dismiss(true);
    }

    public boolean isShow() {
        return pop.isShowing();
    }

    public void setDissMissListener(TransparentPop.DismissListener dissMissListener) {
        pop.setDismissListener(dissMissListener);
    }

    @Override
    public void releaseView(LinearLayout v) {
        v.removeAllViews();
        ((ViewGroup) rootView).removeAllViews();
        rootView = null;
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return promotions == null ? 0 : promotions.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_promotion_item, null);
            final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            if (!Util.isEmpty(promotions.get(position).getName())&&promotions.get(position).getName().equals(promotionName)){
                checkBox.setChecked(true);
                lastCheckBox=checkBox;
            }
            checkBox.setEnabled(false);
            TextView textView= (TextView) view.findViewById(R.id.promotion_name);
            TextView textView1= (TextView) view.findViewById(R.id.product_discount_tv);
            textView.setText(promotions.get(position).getName());
            switch (promotions.get(position).getPromotionType()) {
                case "0":
                    textView1.setText("折扣");
                    break;
                case "1":
                    textView1.setText("直减");
                    break;
                case "2":
                    textView1.setText("满减");
                    break;
                case "3":
                    textView1.setText("满减");
                    break;
                case "4":
                    textView1.setText("特价");
                    break;
                case "5":
                    textView1.setText("满件折");
                    break;
                case "6":
                    textView1.setText("满件减");
                    break;
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lastCheckBox!=null){
                        lastCheckBox.setChecked(false);
                    }
                    checkBox.setChecked(true);
                    promotionName=promotions.get(position).getName();
                    currentPosition=position;
                    dismiss();
                }
            });
            return view;
        }
    }

    public String getPromotion() {
        return promotionName;
    }

    public int getPosition(){
        return currentPosition;
    }

}
