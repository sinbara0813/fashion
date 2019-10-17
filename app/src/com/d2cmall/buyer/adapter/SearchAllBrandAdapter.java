package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BrandDetailActivity;
import com.d2cmall.buyer.bean.DesignerBean;
import com.d2cmall.buyer.holder.SearchBrandAllHolder;
import com.d2cmall.buyer.util.UniversalImageLoader;

import java.util.ArrayList;

/**
 * Created by rookie on 2018/1/30.
 */

public class SearchAllBrandAdapter extends RecyclerView.Adapter<SearchBrandAllHolder> {
    private ArrayList<DesignerBean.DataEntity.DesignersEntity.ListEntity> list;
    private Context context;

    public SearchAllBrandAdapter(ArrayList<DesignerBean.DataEntity.DesignersEntity.ListEntity> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public SearchBrandAllHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_search_all_item, parent, false);
        return new SearchBrandAllHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchBrandAllHolder holder, int position) {
        final DesignerBean.DataEntity.DesignersEntity.ListEntity data=list.get(position);
        UniversalImageLoader.displayImage(context,data.getHeadPic(),holder.logo,R.mipmap.ic_default_avatar);
        holder.tvName.setText(data.getName());
        holder.tvNum.setText("作品 " + data.getProductTotal());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, BrandDetailActivity.class);
                intent.putExtra("id",(int)data.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
