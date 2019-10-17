package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ImagePreviewActivity;
import com.d2cmall.buyer.holder.ShowCommendHolder;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.widget.ninegrid.ImageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rookie on 2017/9/21.
 */

public class ShowCommendAdapter extends RecyclerView.Adapter<ShowCommendHolder> {
    private Context context;
    private List<ImageInfo> list;

    public ShowCommendAdapter(Context context, List<ImageInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ShowCommendHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(context).inflate(R.layout.layout_showcommend,parent,false);
        return new ShowCommendHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShowCommendHolder holder, final int position) {
        UniversalImageLoader.displayImage(context,list.get(position).bigImageUrl, holder.image
                , R.mipmap.ic_logo_empty4, R.mipmap.ic_logo_empty4);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImagePreviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) list);
                bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, position);
                intent.putExtras(bundle);
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
