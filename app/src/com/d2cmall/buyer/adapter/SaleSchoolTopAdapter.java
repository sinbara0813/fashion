package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.SaleSchoolListActivity;
import com.d2cmall.buyer.bean.SaleSchoolTagsBean;
import com.d2cmall.buyer.util.UniversalImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/2/2.
 * Description : SaleSchoolAdapter
 */

public class SaleSchoolTopAdapter extends DelegateAdapter.Adapter {
    private List<SaleSchoolTagsBean.DataBean.ThemeTagsBean> themeTagsBeen;
    private Context context;
    public SaleSchoolTopAdapter(Context context, List<SaleSchoolTagsBean.DataBean.ThemeTagsBean> themeTags) {
        this.context = context;
        this.themeTagsBeen = themeTags;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_partner_sale_school_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final SaleSchoolTagsBean.DataBean.ThemeTagsBean themeTagsBean = themeTagsBeen.get(position);
        MyViewHolder viewHoler = (MyViewHolder)holder;
        UniversalImageLoader.displayImage(context,themeTagsBean.getPic(),viewHoler.imageView);
        viewHoler.tvBuyerNew.setText(themeTagsBean.getName());
        viewHoler.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, SaleSchoolListActivity.class).putExtra("id",themeTagsBean.getId()).putExtra("title",themeTagsBean.getName()));
            }
        });
    }



    @Override
    public int getItemCount() {
        if(themeTagsBeen==null || themeTagsBeen.size()==0){
            return 0;
        }else{
            return themeTagsBeen.size();
        }

    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3);
        gridLayoutHelper.setAutoExpand(false);
        return gridLayoutHelper;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_buyer_new)
        public TextView tvBuyerNew;
        @Bind(R.id.image)
        public ImageView imageView;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
