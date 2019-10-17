package com.d2cmall.buyer.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BrandDetailActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.BrandFollowApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.DesignerBean;
import com.d2cmall.buyer.holder.BrandListHolder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.OpenMsgPushPop;

import java.util.List;

/**
 * Created by rookie on 2017/8/29.
 */

public class BrandListAdapter extends DelegateAdapter.Adapter<BrandListHolder> {
    private Context context;
    private List<DesignerBean.DataEntity.DesignersEntity.ListEntity> list;

    public BrandListAdapter(Context context, List<DesignerBean.DataEntity.DesignersEntity.ListEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return new LinearLayoutHelper();
    }

    @Override
    public BrandListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_brand_list_item, parent, false);
        return new BrandListHolder(view);
    }

    @Override
    public void onBindViewHolder(final BrandListHolder holder, final int position) {
        UniversalImageLoader.displayImage(context, Constants.IMG_HOST + list.get(position).getHeadPic(), holder.imgAvatar);
        holder.imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        switch (list.get(position).getAttentioned()) {
            case 0:
                holder.imgFocus.setImageResource(R.mipmap.button_fashion_care);
                break;
            case 1:
                holder.imgFocus.setImageResource(R.mipmap.button_fashion_cared);
                break;
        }
        holder.imgFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Util.loginChecked((Activity) context, 999)) {
                    if (list.get(position).getAttentioned() > 0) {//关注了
                        BrandFollowApi api = new BrandFollowApi();
                        api.setInterPath(Constants.FOLLOW_BRAND_DELETE);
                        api.setBrandId((int) list.get(position).getId());
                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                            @Override
                            public void onResponse(BaseBean response) {
                                list.get(position).setAttentioned(0);
                                holder.imgFocus.setImageResource(R.mipmap.button_fashion_care);
                                Util.showToast(context, "取消关注成功");
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Util.showToast(context, Util.checkErrorType(error));
                            }
                        });
                    } else {
                        SimpleApi api1 = new SimpleApi();
                        api1.setMethod(BaseApi.Method.POST);
                        api1.setInterPath(String.format(Constants.FOLLOW_BRAND_URL, list.get(position).getId()));
                        D2CApplication.httpClient.loadingRequest(api1, new BeanRequest.SuccessListener<BaseBean>() {
                            @Override
                            public void onResponse(BaseBean response) {
                                list.get(position).setAttentioned(1);
                                holder.imgFocus.setImageResource(R.mipmap.button_fashion_cared);
                                Util.showToast(context, "关注成功");
                                holder.imgFocus.showMsgPop((Activity) context,context.getString(R.string.label_pop_focus_brand));
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Util.showToast(context, Util.checkErrorType(error));
                            }
                        });
                    }
                }
            }
        });
        UniversalImageLoader.displayImage(context, Constants.IMG_HOST + list.get(position).getIntroPic(), holder.imgPic);
        holder.tvContent.setText(Html.fromHtml(list.get(position).getIntro()));
        holder.tvName.setText(list.get(position).getName());
        if (list.get(position).getProducts() != null&&list.get(position).getProducts().size() != 0 ) {
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            holder.recyclerView.setAdapter(new MyAdapter(list.get(position).getProducts()));
            holder.recyclerView.setVisibility(View.VISIBLE);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BrandDetailActivity.class);
                intent.putExtra("id", (int) list.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private List<DesignerBean.DataEntity.DesignersEntity.ListEntity.ProductsEntity> list2;

        public MyAdapter(List<DesignerBean.DataEntity.DesignersEntity.ListEntity.ProductsEntity> list) {
            this.list2 = list;
        }


        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.layout_brand_list_image, parent, false);
            return new MyAdapter.MyViewHolder(view);
        }

        @Override
        public int getItemCount() {
            return list2.size();
        }

        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, final int position) {
            DesignerBean.DataEntity.DesignersEntity.ListEntity.ProductsEntity productsEntity = list2.get(position);
            UniversalImageLoader.displayImage(context, productsEntity.getImg(), holder.imageView);
            //显示价格()
            if (productsEntity.getPromotionId() > 0) {
                holder.textView.setText("¥" + Util.getNumberFormat(productsEntity.getMinPrice()));
                if (productsEntity.getSalePrice() > productsEntity.getPrice()) {
                    holder.textView1.setVisibility(View.VISIBLE);
                    holder.textView1.setText("¥" + Util.getNumberFormat(productsEntity.getSalePrice()));
                    holder.textView1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
            } else {
                if (productsEntity.getMinPrice() >= productsEntity.getOriginalPrice()) {
                    holder.textView1.setVisibility(View.GONE);
                } else {
                    holder.textView1.setVisibility(View.VISIBLE);
                    holder.textView1.setText("¥" + Util.getNumberFormat(productsEntity.getOriginalPrice()));
                    holder.textView1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
                holder.textView.setText("¥" + Util.getNumberFormat(productsEntity.getMinPrice()));
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("id", list2.get(position).getId());
                    context.startActivity(intent);
                }
            });
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;
            public TextView textView;
            public TextView textView1;

            public MyViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.imageView);
                textView = (TextView) itemView.findViewById(R.id.product_price);
                textView1 = (TextView) itemView.findViewById(R.id.product_drop_price);
            }
        }
    }
}
