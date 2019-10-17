package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.FloatLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.GoodsBean;
import com.d2cmall.buyer.holder.RecSearchHolder;

/**
 * Created by Administrator on 2018/10/16.
 * Description : ProductListPromotionFilterAdapter
 */

public class ProductListPromotionFilterAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    private GoodsBean.DataBean.RecSearchBean recSearch;
    private boolean hasFilterPromotion=false;
    private FilterPromiotionListener filterPromiotionListener;
    private RecSearchHolder recSearchHolder;

    public ProductListPromotionFilterAdapter(Context context,GoodsBean.DataBean.RecSearchBean recSearch) {
        this.mContext=context;
        this.recSearch=recSearch;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new SingleLayoutHelper();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_recsearch, parent, false);
        return new RecSearchHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        recSearchHolder = (RecSearchHolder) holder;
        if(hasFilterPromotion){
            recSearchHolder.tvPromotionFilter.setText(mContext.getString(R.string.label_filtered_promotion,recSearch.getName()));
            recSearchHolder.tvPromotionFilter.getPaint().setFakeBoldText(true);
        }else{
            recSearchHolder.tvPromotionFilter.setText(mContext.getString(R.string.label_filter_promotion,recSearch.getName()));
            recSearchHolder.tvPromotionFilter.getPaint().setFakeBoldText(false);
        }
        recSearchHolder.tvPromotionFilter.setClickable(true);
        recSearchHolder.tvPromotionFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    recSearchHolder.tvPromotionFilter.setClickable(false);
                    if(!hasFilterPromotion){
                        recSearchHolder.tvPromotionFilter.setText(mContext.getString(R.string.label_filtered_promotion,recSearch.getName()));
                        recSearchHolder.tvPromotionFilter.getPaint().setFakeBoldText(true);
                    }else{
                        recSearchHolder.tvPromotionFilter.setText(mContext.getString(R.string.label_filter_promotion,recSearch.getName()));
                        recSearchHolder.tvPromotionFilter.getPaint().setFakeBoldText(false);
                    }
                    hasFilterPromotion=!hasFilterPromotion;
                    if(filterPromiotionListener!=null){
                        filterPromiotionListener.filterPromotion(hasFilterPromotion);
                    }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void setEmptyVisiable(boolean visiable) {
        if(recSearchHolder!=null){
            if(visiable){
                recSearchHolder.emptyImage.setVisibility(View.VISIBLE);
            }else{
                recSearchHolder.emptyImage.setVisibility(View.GONE);
            }

        }
    }

    public void reSetTvStatus() {
        hasFilterPromotion=false;
        notifyDataSetChanged();
    }

    public interface FilterPromiotionListener{
        void filterPromotion(boolean isFilter);
    }

    public void setFilterPromiotionListener(FilterPromiotionListener filterPromiotionListener) {
        this.filterPromiotionListener = filterPromiotionListener;
    }

}
