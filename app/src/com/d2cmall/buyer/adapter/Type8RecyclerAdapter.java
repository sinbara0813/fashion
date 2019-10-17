package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.MainBean;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;


public class Type8RecyclerAdapter extends RecyclerView.Adapter<Type8RecyclerAdapter.ViewHolder> {

    private List<MainBean.DataEntity.ContentEntity.SectionValuesEntity.SectionValuesListEntity> items;
    private OnItemClickListener onItemClickListener;
    private OnMoreClickListener onMoreClickListener;
    private int width;
    private int height;
    private Context context;

    public Type8RecyclerAdapter(Context context,int width, int height,List<MainBean.DataEntity.ContentEntity.SectionValuesEntity.SectionValuesListEntity> entryItems) {
        this.context=context;
        this.items = new ArrayList<>();
        this.width = width;
        this.height = height;
        this.items=entryItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_horizontal, parent,
                false);
        return new ViewHolder(v, width, height);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        UniversalImageLoader.displayImage(context,Util.getD2cPicUrl(
                items.get(position).getFrontPic(), width), holder.cover
                , R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        if (items.get(position).getPrice()>0){
            holder.price.setText(Util.getNumberFormat(items.get(position).getPrice()));
        }
        holder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(items.get(position));
            }
        });
        holder.footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMoreClickListener.onMoreClick();
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView cover;
        private TextView price;
        private View headView;
        private View footerView;

        public ViewHolder(View itemView, int width, int height) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.img_cover);
            headView = itemView.findViewById(R.id.head_view);
            price= (TextView) itemView.findViewById(R.id.price);
            footerView = itemView.findViewById(R.id.footer_view);
            cover.getLayoutParams().width = width;
            cover.getLayoutParams().height = height;
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }


    public interface OnItemClickListener {
        void onItemClick(Object item);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnMoreClickListener {
        void onMoreClick();
    }

    public void setOnMoreClickListener(OnMoreClickListener onMoreClickListener) {
        this.onMoreClickListener = onMoreClickListener;
    }
}
