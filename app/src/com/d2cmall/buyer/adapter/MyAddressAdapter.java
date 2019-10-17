package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.AddressListBean;
import com.d2cmall.buyer.holder.MyAddressHolder;
import com.d2cmall.buyer.listener.OnAddressDeleteClickListener;
import com.d2cmall.buyer.listener.OnAddressEditClickListener;
import com.d2cmall.buyer.listener.OnItemClickListener;
import com.d2cmall.buyer.util.Util;

import java.util.List;


/**
 * Created by rookie on 2017/8/30.
 */

public class MyAddressAdapter extends DelegateAdapter.Adapter<MyAddressHolder> {
    private Context context;
    private List<AddressListBean.DataEntity.AddressesEntity.ListEntity> list;
    private int type;
    private OnItemClickListener onItemClickListener;
    private OnAddressEditClickListener onAddressEditClickListener;
    private OnAddressDeleteClickListener onAddressDeleteClickListener;

    public MyAddressAdapter(Context context,List<AddressListBean.DataEntity.AddressesEntity.ListEntity> list,int type,
    OnItemClickListener onItemClickListener,OnAddressEditClickListener onAddressEditClickListener,OnAddressDeleteClickListener onAddressDeleteClickListener){
        this.context=context;
        this.list=list;
        this.type=type;
        this.onItemClickListener=onItemClickListener;
        this.onAddressDeleteClickListener=onAddressDeleteClickListener;
        this.onAddressEditClickListener=onAddressEditClickListener;
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public MyAddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item=LayoutInflater.from(context).inflate(R.layout.layout_address_item,parent,false);
        MyAddressHolder holder=new MyAddressHolder(item);
        if(viewType==0){
            holder.footer.setVisibility(View.VISIBLE);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(MyAddressHolder holder, final int position) {
        AddressListBean.DataEntity.AddressesEntity.ListEntity data=list.get(position);
        if (Util.isLowThanAndroid5()){
            holder.rl.setBackgroundResource(R.drawable.border_black);
        }
        if (data.isIsdefault()) {
            holder.ivAddressDefault.setVisibility(View.VISIBLE);
            holder.imgCheck.setImageResource(R.mipmap.icon_shopcart_aselected);
        }else {
            holder.ivAddressDefault.setVisibility(View.GONE);
            holder.imgCheck.setImageResource(R.mipmap.icon_shopcart_unaselected);
        }
        holder.tvName.setText(data.getName());
        holder.tvPhone.setText(Util.hidePhoneNum(data.getMobile()));
        holder.tvAddress.setText(context.getString(R.string.label_address_format, data.getProvince(), data.getCity(),
                data.getDistrict(), data.getStreet()));
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddressDeleteClickListener.clickDelete(v,position);
            }
        });
        if (type == 0) {
            holder.defaultAddressLayout.setVisibility(View.VISIBLE);
        } else {
            holder.defaultAddressLayout.setVisibility(View.GONE);
        }
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddressEditClickListener.click(v,position);
            }
        });
        holder.defaultAddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.itemClick(v,position);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddressEditClickListener.clickAll(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position==(list.size()-1)){
            return 0;
        }else {
            return 1;
        }
    }
}
