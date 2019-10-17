package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BrandDetailActivity;
import com.d2cmall.buyer.bean.DesignerBean;
import com.d2cmall.buyer.holder.BrandSearchHolder;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Created by rookie on 2017/12/12.
 */

public class BeandSearchAdapter extends RecyclerView.Adapter<BrandSearchHolder> {
    private Context context;
    private List<DesignerBean.DataEntity.DesignersEntity.ListEntity> list;

    public BeandSearchAdapter(Context context, List<DesignerBean.DataEntity.DesignersEntity.ListEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public BrandSearchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_brand_search_item, parent, false);
        return new BrandSearchHolder(view);
    }

    @Override
    public void onBindViewHolder(BrandSearchHolder holder, int position) {
        final DesignerBean.DataEntity.DesignersEntity.ListEntity data = list.get(position);
        holder.nameTv.setText(data.getName());
        if (!Util.isEmpty(data.getProductTotal())) {
            holder.countTv.setText("作品 " + data.getProductTotal());
        }
        String imgUrl;
        if (data.getSignPics() != null && data.getSignPics().size() > 0) {
            imgUrl = data.getSignPics().get(0);
        } else {
            imgUrl = data.getIntroPic();
        }
        if (!Util.isEmpty(data.getHeadPic())) {
            UniversalImageLoader.displayImage(context, data.getHeadPic(), holder.imageBrandLogo, R.mipmap.ic_logo_empty1);
        }
        if (!Util.isEmpty(data.getIntroPic())) {
            UniversalImageLoader.displayImage(context, imgUrl, holder.itemImage, R.mipmap.ic_logo_empty2);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BrandDetailActivity.class);
                intent.putExtra("id", (int) data.getId());
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
