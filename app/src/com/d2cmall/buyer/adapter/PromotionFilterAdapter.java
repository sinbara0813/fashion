package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.holder.PromotionFilterHolder;

/**
 * Created by Administrator on 2018/3/27.
 * Description : PromotionFilterAdapter
 */

public class PromotionFilterAdapter extends DelegateAdapter.Adapter {
    private Context mContext;
    private final int NONE = 1995;
    private final int DESC = 11;
    private final int ASC = 23;
    private int currentPriceState = NONE;
    private String sortValue = "";
    public void setFilterClickListener(FilterClickListener filterClickListener) {
        this.filterClickListener = filterClickListener;
    }

    private FilterClickListener filterClickListener;
    public PromotionFilterAdapter(Context context) {
        this.mContext=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.layout_promotion_filter, parent, false);
        PromotionFilterHolder promotionFilterHolder = new PromotionFilterHolder(itemView);
        return promotionFilterHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final PromotionFilterHolder promotionFilterHolder = (PromotionFilterHolder) holder;
        promotionFilterHolder.tvComprehensive.setOnClickListener(new View.OnClickListener() {//综合
            @Override
            public void onClick(View v) {
                if(filterClickListener!=null){
                    selectComprehensive(promotionFilterHolder);
                    filterClickListener.filterListener(0,sortValue);
                }

            }
        });

        promotionFilterHolder.tvNear.setOnClickListener(new View.OnClickListener() {//最新
            @Override
            public void onClick(View v) {
                if(filterClickListener!=null){
                    selectNear(promotionFilterHolder);
                    filterClickListener.filterListener(1,sortValue);
                }
            }
        });
        promotionFilterHolder.tvHot.setOnClickListener(new View.OnClickListener() {//热销
            @Override
            public void onClick(View v) {
                if(filterClickListener!=null){
                    selectHot(promotionFilterHolder);
                    filterClickListener.filterListener(2,sortValue);
                }
            }
        });
        promotionFilterHolder.tvPrice.setOnClickListener(new View.OnClickListener() { //价格
            @Override
            public void onClick(View v) {
                if(filterClickListener!=null){
                    selectPrice(promotionFilterHolder);
                    filterClickListener.filterListener(3,sortValue);
                }
            }
        });
        promotionFilterHolder.tvScreen.setOnClickListener(new View.OnClickListener() {//筛选
            @Override
            public void onClick(View v) {
                if(filterClickListener!=null){
                    filterClickListener.filterListener(4,sortValue);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        StickyLayoutHelper stickyLayoutHelper = new StickyLayoutHelper();
        return stickyLayoutHelper;
    }


    private void selectPrice(PromotionFilterHolder promotionFilterHolder) {
        promotionFilterHolder.tvPrice.setTextColor(mContext.getResources().getColor(R.color.color_black));
        switch (currentPriceState) {
            case NONE:
                currentPriceState = ASC;
                promotionFilterHolder.ivPriceState.setImageResource(R.mipmap.icon_shoplist_rank_up);
                sortValue = "pa";
                break;
            case ASC:
                currentPriceState = DESC;
                promotionFilterHolder.ivPriceState.setImageResource(R.mipmap.icon_shoplist_rank_down);
                sortValue = "pd";
                break;
            case DESC:
                currentPriceState = ASC;
                promotionFilterHolder.ivPriceState.setImageResource(R.mipmap.icon_shoplist_rank_up);
                sortValue = "pa";

                break;
        }

        promotionFilterHolder.tvHot.setTextColor(mContext.getResources().getColor(R.color.color_black60));
        promotionFilterHolder.tvNear.setTextColor(mContext.getResources().getColor(R.color.color_black60));
        promotionFilterHolder.tvComprehensive.setTextColor(mContext.getResources().getColor(R.color.color_black60));
    }

    private void selectHot(PromotionFilterHolder promotionFilterHolder) {
        promotionFilterHolder.tvHot.setTextColor(mContext.getResources().getColor(R.color.color_black));
        sortValue = "sd";
        promotionFilterHolder.tvComprehensive.setTextColor(mContext.getResources().getColor(R.color.color_black60));
        promotionFilterHolder.tvNear.setTextColor(mContext.getResources().getColor(R.color.color_black60));
        promotionFilterHolder.tvPrice.setTextColor(mContext.getResources().getColor(R.color.color_black60));
        promotionFilterHolder.ivPriceState.setImageResource(R.mipmap.icon_shoplist_rank);
        currentPriceState = NONE;
    }

    private void selectNear(PromotionFilterHolder promotionFilterHolder) {
        promotionFilterHolder.tvNear.setTextColor(mContext.getResources().getColor(R.color.color_black));
        sortValue = "dd";
        promotionFilterHolder.tvHot.setTextColor(mContext.getResources().getColor(R.color.color_black60));
        promotionFilterHolder.tvComprehensive.setTextColor(mContext.getResources().getColor(R.color.color_black60));
        promotionFilterHolder.tvPrice.setTextColor(mContext.getResources().getColor(R.color.color_black60));
        promotionFilterHolder.ivPriceState.setImageResource(R.mipmap.icon_shoplist_rank);
        currentPriceState = NONE;
    }

    private void selectComprehensive(PromotionFilterHolder promotionFilterHolder) {
        promotionFilterHolder.tvComprehensive.setTextColor(mContext.getResources().getColor(R.color.color_black));
        sortValue = "";
        promotionFilterHolder.tvHot.setTextColor(mContext.getResources().getColor(R.color.color_black60));
        promotionFilterHolder.tvNear.setTextColor(mContext.getResources().getColor(R.color.color_black60));
        promotionFilterHolder.tvPrice.setTextColor(mContext.getResources().getColor(R.color.color_black60));
        promotionFilterHolder.ivPriceState.setImageResource(R.mipmap.icon_shoplist_rank);
        currentPriceState = NONE;
    }

    public interface FilterClickListener{
        void filterListener(int type,String sortValue);
    }
}
