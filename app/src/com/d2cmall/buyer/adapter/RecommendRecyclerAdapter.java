package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.bean.RecommendListBean;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

public class RecommendRecyclerAdapter extends RecyclerView.Adapter<RecommendRecyclerAdapter.RecommendViewHolder> {

    private List<GoodsBean.DataBean.ProductsBean.ListBean> recommendList;
    private Context context;
    private int width;
    private int height;

    public RecommendRecyclerAdapter(Context context, int width, int height) {
        this.context = context;
        this.width = width;
        this.height = height;
        recommendList = new ArrayList<>();
    }

    public void setData(List<GoodsBean.DataBean.ProductsBean.ListBean> data) {
        if (data != null && data.size() > 0) {
            recommendList.clear();
            recommendList.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public RecommendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_item_recommend_good, parent, false);
        return new RecommendViewHolder(v, width, height);
    }

    @Override
    public void onBindViewHolder(final RecommendViewHolder holder, final int position) {
        UniversalImageLoader.displayImage(context,Util.getD2cPicUrl(recommendList.get(position).getImg()), holder.image, R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        holder.tv.setText(recommendList.get(position).getName());
        holder.price.setText(context.getString(R.string.label_price, Util.getNumberFormat(recommendList.get(position).getMinPrice())));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id", recommendList.get(position).getId());
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            }
        });
        if (listener != null) {
            listener.preDrawBack(holder.tv.getLineHeight() * 2 + holder.price.getLineHeight() + height + Util.dip2px(context, 30));
        }
    }

    @Override
    public int getItemCount() {
        return recommendList == null ? 0 : recommendList.size();
    }

    class RecommendViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView tv;
        TextView price;

        public RecommendViewHolder(View itemView, int width, int height) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            tv = (TextView) itemView.findViewById(R.id.tv);
            price = (TextView) itemView.findViewById(R.id.price);
            image.getLayoutParams().width = width;
            image.getLayoutParams().height = height;
        }
    }

    PreDrawListener listener;

    public void setPreDrawListener(PreDrawListener drawListener) {
        this.listener = drawListener;
    }

    public interface PreDrawListener {
        void preDrawBack(int height);
    }
}
