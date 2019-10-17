package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.DCoinProductDetaiActivity;
import com.d2cmall.buyer.activity.TopicDetailActivity;
import com.d2cmall.buyer.bean.DCionProductListBean;
import com.d2cmall.buyer.holder.DCoinShopProductHolder;
import com.d2cmall.buyer.listener.Clickable;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/6.
 * Description : DCoinShopAdapter
 */

public class DCoinShopProductAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    private ArrayList<DCionProductListBean.DataBean.ListBeanX.ListBean> listBeans;
    private int itemWidth;

    public DCoinShopProductAdapter(Context context, ArrayList<DCionProductListBean.DataBean.ListBeanX.ListBean> listBeans, int itemWidth) {
        super();
        this.mContext = context;
        this.listBeans = listBeans;
        this.itemWidth=itemWidth;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_dcoin_product_item, parent, false);
                return new DCoinShopProductHolder(view,itemWidth);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        DCoinShopProductHolder dCoinShopProductHolder = (DCoinShopProductHolder) holder;
        final DCionProductListBean.DataBean.ListBeanX.ListBean listBean = listBeans.get(position);
        UniversalImageLoader.displayImage(mContext, listBean.getIntroPic(),dCoinShopProductHolder.ivCollectGoodsImg,R.mipmap.ic_logo_empty5);
        dCoinShopProductHolder.tvProductName.setText(listBean.getName());
        if("RED".equals(listBean.getType())){
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#C39B53"));
            dCoinShopProductHolder.tvProductPrice.setTextColor(mContext.getResources().getColor(R.color.color_black85));
            String str = mContext.getString(R.string.label_point_exchange,listBean.getPoint(), Util.getNumberFormat(listBean.getAmount()));
            int length = str.length();
            int dIndex = str.indexOf("D");
            int yuanIndex = str.indexOf("元");
            int huanIndex = str.indexOf("换");
            SpannableString textSpan = new SpannableString(str);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(16)), 0, dIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textSpan.setSpan(colorSpan, 0, dIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(12)), dIndex, yuanIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(16)), huanIndex+1, yuanIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            colorSpan= new ForegroundColorSpan(Color.parseColor("#C39B53"));
            textSpan.setSpan(colorSpan, huanIndex+1, yuanIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(12)), yuanIndex, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            dCoinShopProductHolder.tvProductPrice.setText(textSpan);
        }else{
            String string = mContext.getString(R.string.label_dcion_price, listBean.getPoint());
            int length = string.length();
            //字体颜色不一样
            StringBuilder builder = new StringBuilder();
            builder.append(mContext.getString(R.string.label_dcion_price, listBean.getPoint()));
            SpannableString sb = new SpannableString(builder.toString());
            sb.setSpan(new ForegroundColorSpan(Color.parseColor("#FFC29A5C")), 0, length-3, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            sb.setSpan(new ForegroundColorSpan(Color.parseColor("#DE000000")), length-3, length, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            dCoinShopProductHolder.tvProductPrice.setText(sb);
        }
        if(listBean.getCount()<=0){
            dCoinShopProductHolder.tvNoStore.setVisibility(View.VISIBLE);
        }else{
            dCoinShopProductHolder.tvNoStore.setVisibility(View.GONE);
        }
        dCoinShopProductHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, DCoinProductDetaiActivity.class).putExtra("id",listBean.getId()));
            }
        });
    }



    @Override
    public int getItemCount() {
        return listBeans ==null?0: listBeans.size();
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        gridLayoutHelper.setPaddingLeft(ScreenUtil.dip2px(16));
        gridLayoutHelper.setPaddingRight(ScreenUtil.dip2px(16));
        gridLayoutHelper.setPaddingBottom(ScreenUtil.dip2px(16));
        gridLayoutHelper.setAutoExpand(false);
        gridLayoutHelper.setHGap(ScreenUtil.dip2px(16));
        return gridLayoutHelper;
    }
}
