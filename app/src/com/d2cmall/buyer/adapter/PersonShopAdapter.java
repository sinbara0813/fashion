package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BrandDetailActivity;
import com.d2cmall.buyer.bean.PersonInfoBean;
import com.d2cmall.buyer.holder.PersonShopHolder;
import com.d2cmall.buyer.util.UniversalImageLoader;

import java.util.List;

/**
 * Created by rookie on 2017/9/5.
 */

public class PersonShopAdapter extends RecyclerView.Adapter<PersonShopHolder> {
    private Context context;
    private List<PersonInfoBean.DataBean.BrandsBean> list;

    public PersonShopAdapter(Context context,List<PersonInfoBean.DataBean.BrandsBean> list){
        this.context=context;
        this.list=list;
    }
    @Override
    public PersonShopHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item= LayoutInflater.from(context).inflate(R.layout.layout_shop_person_item,parent,false);
        return new PersonShopHolder(item);
    }

    @Override
    public void onBindViewHolder(PersonShopHolder holder, final int position) {
        UniversalImageLoader.displayImage(context,list.get(position).getHeadPic(),holder.image,R.mipmap.ic_logo_empty1,R.mipmap.ic_logo_empty1);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, BrandDetailActivity.class);
                intent.putExtra("id",list.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
