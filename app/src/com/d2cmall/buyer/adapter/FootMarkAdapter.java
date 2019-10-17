package com.d2cmall.buyer.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.FootMarkBean;
import com.d2cmall.buyer.holder.FootMarkItemHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.swipeLayout.SwipeLayout;

import java.util.ArrayList;

/**
 * Created by rookie on 2018/3/7.
 */

public class FootMarkAdapter extends RecyclerView.Adapter<FootMarkItemHolder> {
    private ArrayList<FootMarkBean.DataBean.ProductsBean.ListBean> list;
    private Context context;

    public FootMarkAdapter(ArrayList<FootMarkBean.DataBean.ProductsBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public FootMarkItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_footmark_item, parent, false);
        return new FootMarkItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FootMarkItemHolder holder, final int position) {
        final FootMarkBean.DataBean.ProductsBean.ListBean data = list.get(position);
        Integer promotionId = data.getPromotionId();
        holder.tvPrice.setText("¥" + Util.getNumberFormat(data.getMinPrice()));
        if (promotionId != null && promotionId > 0) {
            if (data.getMinPrice() < data.getSalePrice()) {
                holder.tvHighPrice.setVisibility(View.VISIBLE);
                holder.tvHighPrice.setText("¥" + Util.getNumberFormat(data.getSalePrice()));
                holder.tvHighPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                holder.tvHighPrice.setVisibility(View.GONE);
            }
        } else {
            if (data.getMinPrice() < data.getOriginalPrice()) {
                holder.tvHighPrice.setVisibility(View.VISIBLE);
                holder.tvHighPrice.setText("¥" + Util.getNumberFormat(data.getOriginalPrice()));
                holder.tvHighPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                holder.tvHighPrice.setVisibility(View.GONE);
            }
        }
        holder.tvProductName.setText(data.getName());
        Glide.with(context)
                .load(Util.getD2cProductPicUrl(data.getImg(), ScreenUtil.dip2px(72), ScreenUtil.dip2px(128)))
                .placeholder(R.mipmap.ic_logo_empty5)
                .dontAnimate()
                .error(R.mipmap.ic_logo_empty5)
                .override(ScreenUtil.dip2px(72), ScreenUtil.dip2px(128))
                .into(holder.ivProduct);
        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("确认删除?")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteFootMark(data.getHistoryId(), data, position, holder.sideslipDelet);
                            }
                        })
                        .show();

            }
        });
        holder.sideslipDelet.setOnSwipeLayoutClickListener(new SwipeLayout.OnSwipeLayoutClickListener() {
            @Override
            public void onClick() {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id", data.getId());
                context.startActivity(intent);
            }
        });
    }

    private void deleteFootMark(String id, final FootMarkBean.DataBean.ProductsBean.ListBean data, int position, SwipeLayout sideslipDelet) {
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.POST);
        api.setInterPath(String.format(Constants.DELETE_FOOTMARK_URL, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                sideslipDelet.closeDeleteMenu();
                list.remove(data);
                notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(context, Util.checkErrorType(error));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
