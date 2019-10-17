package com.d2cmall.buyer.binder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductListActivity;
import com.d2cmall.buyer.base.BaseViewBinder;
import com.d2cmall.buyer.bean.KindBean;
import com.d2cmall.buyer.holder.CategoryDetailHolder;
import com.d2cmall.buyer.util.UniversalImageLoader;

import java.util.List;

/**
 * Created by rookie on 2017/7/28.
 */

public class CategoryDetailGridBinder implements BaseViewBinder<CategoryDetailHolder> {
    private Context context;
    private int layoutId;
    private List<KindBean.DataEntity.NavigationsEntity.ItemsEntity> list;

    public CategoryDetailGridBinder(Context context, int layoutId, List<KindBean.DataEntity.NavigationsEntity.ItemsEntity> source){
        this.context=context;
        this.layoutId=layoutId;
        this.list=source;
    }

    @Override
    public CategoryDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_category_item,null);
        return new CategoryDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryDetailHolder categoryDetailHolder, int position) {
        UniversalImageLoader.displayImage(context, Constants.IMG_HOST+list.get(position).getPic(),categoryDetailHolder.image);
        categoryDetailHolder.text.setText(list.get(position).getTitle());
        categoryDetailHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProductListActivity.class);
                intent.putExtra("log","135");
                intent.putExtra("subName","下装");
                intent.putExtra("type",1);
                intent.putExtra("keyword","小黑裙");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onBindViewHolderWithOffer(CategoryDetailHolder categoryDetailHolder, int position, int offsetTotal) {

    }


}
