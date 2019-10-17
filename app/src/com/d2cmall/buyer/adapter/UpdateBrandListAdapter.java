package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BrandDetailActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.bean.UpdateBrandCategoryListBean;
import com.d2cmall.buyer.holder.UpdateBrandHolder;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;

/**
 * Created by Administrator on 2018/9/10.
 */

public class UpdateBrandListAdapter extends DelegateAdapter.Adapter<UpdateBrandHolder> {

    private Context mContext;
    private List<UpdateBrandCategoryListBean.DataBean.BrandsBean.ListBean> list;

    public UpdateBrandListAdapter(Context context, List<UpdateBrandCategoryListBean.DataBean.BrandsBean.ListBean> data){
        this.mContext=context;
        this.list=data;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public UpdateBrandHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.layout_update_brand_item,parent,false);
        return new UpdateBrandHolder(view);
    }

    @Override
    public void onBindViewHolder(UpdateBrandHolder brandHolder, int position) {
        UpdateBrandCategoryListBean.DataBean.BrandsBean.ListBean listBean=list.get(position);

        UniversalImageLoader.displayImage(mContext,listBean.getIntroPic().split(",")[0],brandHolder.brandIntroIv);
        UniversalImageLoader.displayImage(mContext,listBean.getHeadPic(),brandHolder.brandHeadIv);
        brandHolder.brandName.setText(listBean.getBrand());
        brandHolder.brandUpdateCountTv.setText(listBean.getProducts().size()+"款上新");

        int size=listBean.getProducts().size();
        brandHolder.contentLl.removeAllViews();
        for (int i=0;i<size;i++){
            View view=LayoutInflater.from(mContext).inflate(R.layout.layout_update_brand_product_item,new LinearLayout(mContext),false);
            ImageView imageView=view.findViewById(R.id.product_iv);
            TextView priceTv=view.findViewById(R.id.product_price);
            TextView orgPriceTv=view.findViewById(R.id.product_org_price);
            UniversalImageLoader.displayImage(mContext,listBean.getProducts().get(i).getImg(),imageView);
            priceTv.setText("¥"+ Util.getNumberFormat(listBean.getProducts().get(i).getMinPrice()));
            if (listBean.getProducts().get(i).getPromotionId()!=null&&listBean.getProducts().get(i).getPromotionId()>0){
                if (listBean.getProducts().get(i).getSellPrice()>listBean.getProducts().get(i).getMinPrice()){
                    orgPriceTv.setText("¥"+Util.getNumberFormat(listBean.getProducts().get(i).getSellPrice()));
                    orgPriceTv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
                }
            }else {
                if (listBean.getProducts().get(i).getOriginalPrice()>listBean.getProducts().get(i).getMinPrice()){
                    orgPriceTv.setText("¥"+Util.getNumberFormat(listBean.getProducts().get(i).getOriginalPrice()));
                    orgPriceTv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
                }
            }
            int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toDetail(listBean.getProducts().get(finalI).getId());
                }
            });
            brandHolder.contentLl.addView(view);
        }

        brandHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, BrandDetailActivity.class);
                intent.putExtra("selectType","update");
                intent.putExtra("id",listBean.getId());
                mContext.startActivity(intent);
            }
        });
    }

    private void toDetail(long id){
        Intent intent=new Intent(mContext, ProductDetailActivity.class);
        intent.putExtra("id",id);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }
}
