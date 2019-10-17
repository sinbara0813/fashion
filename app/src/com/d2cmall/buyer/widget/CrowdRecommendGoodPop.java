package com.d2cmall.buyer.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.CartActivity;
import com.d2cmall.buyer.activity.ProductDetailActivity;
import com.d2cmall.buyer.adapter.ObjectBindAdapter;
import com.d2cmall.buyer.api.CrowdGoodApi;
import com.d2cmall.buyer.bean.LiveProductListBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fixme
 * Author: hrb
 * Date: 2017/01/03 16:16
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CrowdRecommendGoodPop implements TransparentPop.InvokeListener, ObjectBindAdapter.ViewBinder<LiveProductListBean.DataBean.ProductsBean.ListBean>, AbsListView.OnScrollListener {

    ListView mListView;
    ImageView emptyTv;
    FloatingActionButton btn_cart;
    private TransparentPop mPop;
    private Context mContext;
    private View rootView;
    private List<LiveProductListBean.DataBean.ProductsBean.ListBean> list;
    private ObjectBindAdapter<LiveProductListBean.DataBean.ProductsBean.ListBean> adapter;
    private int p = 1;
    private boolean isEnd;
    private boolean isLoad;
    private View endView;
    private View loadView;
    private View footView;
    private ShoppingPop shoppingPop;
    private String designNameStr;
    private long liveId;
    public boolean isFinish;


    public CrowdRecommendGoodPop(Context context, long liveId) {
        this.mContext = context;
        this.liveId = liveId;
        init();
    }

    public void setBackCoverColor(int color) {
        mPop.setRootLayoutBackground(color);
    }

    private void init() {
        rootView = LayoutInflater.from(mContext).inflate(R.layout.live_crowd_recommend_good, new RelativeLayout(mContext), false);
        mListView = (ListView) rootView.findViewById(R.id.m_list_view);
        emptyTv = (ImageView) rootView.findViewById(R.id.tv_tag);
        btn_cart= (FloatingActionButton) rootView.findViewById(R.id.btn_cart);
        mPop = new TransparentPop(mContext, this);
        mPop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Animation inAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_up);
        Animation outAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_down);
        mPop.setInAnimation(inAnimation);
        mPop.setOutAnimation(outAnimation);
        mPop.dismissFromOut();
        mPop.setFocusable(false);
        list = new ArrayList<>();
        adapter = new ObjectBindAdapter<>(mContext, list, R.layout.layout_live_recommend_item, this);
        footView = LayoutInflater.from(mContext).inflate(R.layout.list_foot_no_more2, null);
        endView = footView.findViewById(R.id.no_more);
        loadView = footView.findViewById(R.id.loading);
        mListView.addFooterView(footView);
        mListView.setOnScrollListener(this);
        mListView.setAdapter(adapter);
        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buyBack != null) {
                    buyBack.toOtherBack();
                }
                if (Util.loginChecked((Activity) mContext, 100)) {
                    Intent intent = new Intent( mContext, CartActivity.class);
                    mContext.startActivity(intent);
                    dismiss();
                }
            }
        });
        loadData();
    }

    public void loadData() {
        isLoad = true;
        CrowdGoodApi api = new CrowdGoodApi();
        api.setPageNumber(p);
        api.setLiveId(liveId);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<LiveProductListBean>() {
            @Override
            public void onResponse(LiveProductListBean response) {
                if (response.getData().getProducts().getList() != null && response.getData().getProducts().getList().size() > 0) {
                    if (p == 1) {
                        list.clear();
                    }
                    list.addAll(response.getData().getProducts().getList());
                    if (!response.getData().getProducts().isNext()) {
                        isEnd = true;
                        loadView.setVisibility(View.GONE);
                    } else {
                        isEnd = false;
                        loadView.setVisibility(View.INVISIBLE);
                    }
                    isLoad = false;
                    mListView.setVisibility(View.VISIBLE);
                    emptyTv.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                } else {
                    if (list.size() == 0) {
                        mListView.setVisibility(View.GONE);
                        emptyTv.setVisibility(View.VISIBLE);
                        if (buyBack != null) {
                            buyBack.hasRecommendData(false);
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isLoad = false;
            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_IDLE:
                if (view.getLastVisiblePosition() >= (view.getCount() - 5) && !isEnd && !isLoad) {
                    loadView.setVisibility(View.VISIBLE);
                    p++;
                    loadData();
                } else {
                    break;
                }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if ((totalItemCount > visibleItemCount) && isEnd) {
            endView.setVisibility(View.VISIBLE);
        } else {
            endView.setVisibility(View.GONE);
        }
    }

    public void show(View parent) {
        mPop.show(parent);
    }

    public void dismiss() {
        if (mPop != null) {
            mPop.dismiss(true);
        }
    }

    public boolean isShow() {
        return mPop.isShowing();
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.BOTTOM);
        v.addView(rootView);
    }

    @Override
    public void releaseView(LinearLayout v) {
        v.removeAllViews();
    }

    @Override
    public void setViewValue(View view, final LiveProductListBean.DataBean.ProductsBean.ListBean listBean, int position) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        holder.bottomTv.setText("加入购物车");
//        if(listBean.getIsCart()==1){
//            holder.bottomTv.setVisibility(View.GONE);
//        }else {
//            holder.bottomTv.setVisibility(View.VISIBLE);
//        }
        if (!Util.isEmpty(listBean.getImg())) {
            if(isFinish){
                return;
            }
            UniversalImageLoader.displayImage(mContext, Util.getD2cPicUrl(listBean.getImg()), holder.ivProduct, R.mipmap.ic_logo_empty6, R.mipmap.ic_logo_empty6);
        }
        holder.ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buyBack != null) {
                    buyBack.toOtherBack();
                }
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("id", listBean.getId());
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                dismiss();
            }
        });
        holder.tvProductName.setText(listBean.getName());
        holder.tvProductName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buyBack != null) {
                    buyBack.toOtherBack();
                }
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("id", listBean.getId());
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                dismiss();
            }
        });
        holder.tvMinPrice.setText("¥" + Util.getNumberFormat(listBean.getMinPrice()));
        if (listBean.getOriginalPrice() > listBean.getPrice()) {
            holder.tvDeletePrice.setVisibility(View.VISIBLE);
            holder.tvDeletePrice.setText("￥" + Util.getNumberFormat(listBean.getOriginalPrice()));
            holder.tvDeletePrice.getPaint().setAntiAlias(true);
            holder.tvDeletePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint
                    .ANTI_ALIAS_FLAG);
        } else {
            holder.tvDeletePrice.setVisibility(View.GONE);
        }
        holder.bottomTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCart(listBean);
            }
        });
    }

    private void addCart(final LiveProductListBean.DataBean.ProductsBean.ListBean listBean) {
        shoppingPop = new ShoppingPop(mContext);
        shoppingPop.setProductId(listBean.getId());
        shoppingPop.setColors(listBean.getColors());
        shoppingPop.setSize(listBean.getSizes());
        shoppingPop.isLive = true;
        shoppingPop.setLiveBuyListener(new ShoppingPop.LiveBuyListener() {
            @Override
            public void liveBuyBack(long id, String productName) {

            }

            @Override
            public void liveAddCart(long id) {
                if (buyBack != null) {
                    buyBack.liveAddCartBack(id, listBean.getName());
                }
            }
        });

        /*shoppingPop.setPrice(listBean.getPrice());
        if (listBean.getOriginalPrice() > listBean.getPrice()) {
            shoppingPop.setOrgPrice(listBean.getOriginalPrice());
        }
        shoppingPop.setColors(listBean.getColors());
        shoppingPop.setComdityTitle(listBean.getName());
        shoppingPop.setMark(listBean.getRemark());
        shoppingPop.setSize(listBean.getSizes());
        shoppingPop.setDesignName(designNameStr);
        shoppingPop.setLiveBottomLl();
        shoppingPop.setLive(true);
        shoppingPop.setLiveBuyListener(new ShoppingPop.LiveBuyListener() {
            @Override
            public void liveBuyBack(long id,String productName) {
                if (buyBack!=null){
                    buyBack.liveBuyBack(id,productName);
                }
            }

            @Override
            public void liveAddCart(long id,String productName) {
                if (buyBack!=null){
                    buyBack.liveAddCartBack(id,productName);
                }
            }
        });*/
        shoppingPop.show(1, ((Activity) mContext).getWindow().getDecorView());
    }

    static class ViewHolder {
        @Bind(R.id.iv_product)
        public ImageView ivProduct;
        @Bind(R.id.tv_product_name)
        public TextView tvProductName;
        @Bind(R.id.tv_min_price)
        public TextView tvMinPrice;
        @Bind(R.id.tv_delete_price)
        public TextView tvDeletePrice;
        @Bind(R.id.bottom_tv)
        public TextView bottomTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setDissBack(TransparentPop.DismissListener dismissListener) {
        mPop.setDismissListener(dismissListener);
    }

    public void setDesignName(String name) {
        designNameStr = name;
    }

    public void setLiveId(long id) {
        this.liveId = id;
    }

    private int getGoodIndex(long goodId) {
        int index = -1;
        int count = list.size();
        for (int i = 0; i < count; i++) {
            if (list.get(i).getId() == goodId) {
                index = i;
                break;
            }
        }
        return index;
    }

    public void stick(long goodId) {
        int index = getGoodIndex(goodId);
        if (index > 0) {
            LiveProductListBean.DataBean.ProductsBean.ListBean listBean = list.remove(index);
            list.add(0, listBean);
            adapter.notifyDataSetChanged();
        } else {
            p = 1;
            loadData();
        }
    }

    private BuyBack buyBack;

    public interface BuyBack {
        void liveAddCartBack(long id, String productName);

        void liveBuyBack(long id, String productName);

        void toOtherBack();

        void hasRecommendData(boolean is);
    }

    public BuyBack getBuyBack() {
        return buyBack;
    }

    public void setBuyBack(BuyBack buyBack) {
        this.buyBack = buyBack;
    }
}
