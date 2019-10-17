package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.ObjectBindAdapter;
import com.d2cmall.buyer.api.CrowdGoodApi;
import com.d2cmall.buyer.api.RecommendGoodApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.LiveProductListBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.CommandNotificationMessage;

/**
 * Fixme
 * Author: hrb
 * Date: 2017/01/03 16:16
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public abstract class AnchorRecommendGoodPop implements TransparentPop.InvokeListener, ObjectBindAdapter.ViewBinder<LiveProductListBean.DataBean.ProductsBean.ListBean>, AbsListView.OnScrollListener {

    @Bind(R.id.et_search)
    ClearEditText etSearch;
    @Bind(R.id.m_list_view)
    ListView mListView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;
    private TransparentPop mPop;
    private Context mContext;
    private View rootView;
    private List<LiveProductListBean.DataBean.ProductsBean.ListBean> list;
    private ObjectBindAdapter<LiveProductListBean.DataBean.ProductsBean.ListBean> adapter;
    private boolean hasRecommendProduct;
    private int p = 1;
    private boolean isEnd;
    private boolean isLoad;
    private View endView;
    private View loadView;
    private View footView;
    private String keyWord;
    private String mChannel;
    private long liveId;

    public AnchorRecommendGoodPop(Context context, long liveId) {
        this.mContext = context;
        this.liveId = liveId;
        init();
    }

    private void init() {
        rootView = LayoutInflater.from(mContext).inflate(R.layout.live_anchor_recommend_good, new RelativeLayout(mContext), false);
        ButterKnife.bind(this, rootView);
        mPop = new TransparentPop(mContext, this);
        Animation inAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_up);
        Animation outAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_down);
        mPop.setInAnimation(inAnimation);
        mPop.setOutAnimation(outAnimation);
        mPop.dismissFromOut();
        list = new ArrayList<>();
        adapter = new ObjectBindAdapter<>(mContext, list, R.layout.layout_live_recommend_item, this);
        footView = LayoutInflater.from(mContext).inflate(R.layout.list_foot_no_more2, null);
        endView = footView.findViewById(R.id.no_more);
        loadView = footView.findViewById(R.id.loading);
        //mListView.addFooterView(footView);
        mListView.setOnScrollListener(this);
        mListView.setAdapter(adapter);
        emptyHintLayout.setVisibility(View.GONE);
        loadData();
        initListener();
    }

    private void initListener() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String key = etSearch.getText().toString();
                    if (!Util.isEmpty(key)) {
                        search(key);
                        KeyboardUtil.hideKeyboard(etSearch);
                    } else {
                        Util.showToast(mContext, "请输入内容！");
                    }
                    return true;
                }
                return false;
            }
        });
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void search(String key) {
        keyWord = key;
        p = 1;
        loadData();
    }

    public void loadData() {
        isLoad = true;
        progressBar.setVisibility(View.VISIBLE);
        CrowdGoodApi api = new CrowdGoodApi();
        api.setPageNumber(p);
        api.setLiveId(liveId);
        if (!Util.isEmpty(keyWord)) {
            api.setKeyword(keyWord);
        }
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<LiveProductListBean>() {
            @Override
            public void onResponse(LiveProductListBean response) {
                progressBar.setVisibility(View.GONE);
                if (p == 1) {
                    list.clear();
                }
                int size = response.getData().getProducts().getList().size();
                if (size > 0) {
                    if (Util.isEmpty(keyWord)) {
                        hasRecommendProduct = true;
                    }
                    list.addAll(response.getData().getProducts().getList());
                    if (!response.getData().getProducts().isNext()) {
                        isEnd = true;
                        loadView.setVisibility(View.GONE);
                    } else {
                        isEnd = false;
                        loadView.setVisibility(View.INVISIBLE);
                    }
                }
                adapter.notifyDataSetChanged();
                isLoad = false;
                setEmptyView(Constants.NO_DATA);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isLoad = false;
                progressBar.setVisibility(View.GONE);
                setEmptyView(Constants.NET_DISCONNECT);
            }
        });
    }

    private void setEmptyView(int type) {
        if (list.isEmpty()) {
            emptyHintLayout.setVisibility(View.VISIBLE);
            if (!hasRecommendProduct && Util.isEmpty(keyWord)) {
                imgHint.setVisibility(View.VISIBLE);
            } else {
                imgHint.setVisibility(View.VISIBLE);
                btnReload.setVisibility(View.GONE);
                if (type == Constants.NO_DATA) {
                    imgHint.setImageResource(R.mipmap.ic_no_data);
                } else {
                    imgHint.setImageResource(R.mipmap.ic_no_net);
                }
            }
        } else {
            emptyHintLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_IDLE:
                if (view.getLastVisiblePosition() >= (view.getCount() - 5) && !isEnd && !isLoad) {
                    if (mListView.getFooterViewsCount() == 0) {
                        mListView.addFooterView(footView);
                    }
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
            if (mListView.getFooterViewsCount() == 0) {
                mListView.addFooterView(footView);
            }
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
    public void setViewValue(View view, final LiveProductListBean.DataBean.ProductsBean.ListBean listBean, final int position) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        if (!Util.isEmpty(listBean.getImg())) {
            UniversalImageLoader.displayImage(mContext, Util.getD2cPicUrl(listBean.getImg()), holder.ivProduct, R.mipmap.ic_logo_empty6, R.mipmap.ic_logo_empty6);
        }
        holder.tvProductName.setText(listBean.getName());
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
        holder.ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                toOtherActivity();
//                Intent intent = new Intent(mContext, ProductDetailActivity.class);
//                intent.putExtra("id", listBean.getId());
//                mContext.startActivity(intent);
//                ((Activity) mContext).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            }
        });
        holder.tvProductName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                toOtherActivity();
//                Intent intent = new Intent(mContext, ProductDetailActivity.class);
//                intent.putExtra("id", listBean.getId());
//                mContext.startActivity(intent);
//                ((Activity) mContext).overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
            }
        });
        if (listBean.isIsRec()) {
            changeViewState(holder.bottomTv, true);
        } else {
            changeViewState(holder.bottomTv, false);
        }
        final ViewHolder finalHolder = holder;
        holder.bottomTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recommend(position, listBean.getId(), 1, listBean.getImg(), listBean.getDesignerId(), listBean.getPrice());
                listBean.setIsRec(true);
                changeViewState(finalHolder.bottomTv, true);
            }
        });
    }

    private void recommend(final int position, final long id, final int status, final String url, long designId, final double price) {
        RecommendGoodApi api = new RecommendGoodApi();
        api.setProductId(id);
        api.setStatus(status);
        api.setLiveId(liveId);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                if (status == 1) {
                    recommend(url, id, price);
                    LiveProductListBean.DataBean.ProductsBean.ListBean listBean = list.remove(position);
                    list.add(0, listBean);
                    dismiss();
                    adapter.notifyDataSetChanged();
                }
            }
        });
        if (status == 1) {
            StringBuilder builder = new StringBuilder();
            builder.append("{");
            builder.append("\"productId\":");
            builder.append(id);
            builder.append(",");
            builder.append("\"productImg\":");
            builder.append("\"");
            builder.append(url);
            builder.append("\"");
            builder.append(",");
            builder.append("\"productPrice\":");
            builder.append(price);
            builder.append(",");
            builder.append("\"designerId\":");
            builder.append(designId);
            builder.append("}");
            CommandNotificationMessage message = CommandNotificationMessage.obtain(":RG", builder.toString());
            RongIMClient.getInstance().sendMessage(Conversation.ConversationType.CHATROOM, mChannel, message, null, null, new IRongCallback.ISendMessageCallback() {
                @Override
                public void onAttached(Message message) {
                }

                @Override
                public void onSuccess(Message message) {
                    Log.d("", "");
                }

                @Override
                public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                }
            });
        }
    }

    private void changeViewState(TextView tv, boolean isRecommend) {
        if (isRecommend) {
            tv.setText(mContext.getResources().getString(R.string.label_cancel_recommend));
        } else {
            tv.setText(mContext.getResources().getString(R.string.label_recommend));
        }
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

    public void setChannel(String channel) {
        this.mChannel = channel;
    }

    public abstract void recommend(String url, long id, double price);

    public abstract void toOtherActivity();

    public void setLiveId(long id) {
        this.liveId = id;
    }
}
