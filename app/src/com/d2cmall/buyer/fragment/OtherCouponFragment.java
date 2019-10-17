package com.d2cmall.buyer.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.ObjectBindAdapter;
import com.d2cmall.buyer.api.CouponApi;
import com.d2cmall.buyer.bean.CouponsBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.AnimUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class OtherCouponFragment extends BaseFragment implements
        ObjectBindAdapter.ViewBinder<CouponsBean.DataEntity.MyCouponsEntity.ListEntity>,
        AbsListView.OnScrollListener {

    private ListView listView;
    private PtrStoreHouseFrameLayout ptr;
    private RelativeLayout rl_layout;
    private View progressBar;
    private View rootView;
    private ArrayList<CouponsBean.DataEntity.MyCouponsEntity.ListEntity> listEntities;
    private ObjectBindAdapter<CouponsBean.DataEntity.MyCouponsEntity.ListEntity> adapter;
    private View endView;
    private View loadView;
    private View footView;
    private static final String ARG_PARAM1 = "param1";
    private String status;
    private boolean isEnd;
    private boolean isLoad;
    private int currentPage = 1;
    private boolean isPrepared;

    public static OtherCouponFragment newInstance(String status) {
        OtherCouponFragment fragment = new OtherCouponFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listEntities = new ArrayList<>();
        adapter = new ObjectBindAdapter<>(getActivity(), listEntities, R.layout.list_item_coupon1);
        footView = getActivity().getLayoutInflater().inflate(R.layout.list_foot_no_more2, null);
        endView = footView.findViewById(R.id.no_more);
        loadView = footView.findViewById(R.id.loading);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_listview, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        rl_layout= (RelativeLayout) rootView.findViewById(R.id.rl_layout);
        listView.setPadding(ScreenUtil.dip2px(16),0,ScreenUtil.dip2px(16),0);
        ptr = (PtrStoreHouseFrameLayout) rootView.findViewById(R.id.ptr);
        progressBar = rootView.findViewById(R.id.progressBar);
        listView.addFooterView(footView);
        adapter.setViewBinder(this);
        listView.setOnScrollListener(this);
        listView.setAdapter(adapter);
        if (getArguments() != null) {
            status = getArguments().getString(ARG_PARAM1);
        }
        isPrepared = true;
        lazyLoad();
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (!isLoad) {
                    currentPage = 1;
                    requestCouponsTask();
                }
            }
        });
        return rootView;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        if (listEntities.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            endView.setVisibility(View.GONE);
            loadView.setVisibility(View.GONE);
            requestCouponsTask();
        }
    }

    @Override
    public void refresh(Object... params) {
        progressBar.setVisibility(View.VISIBLE);
        endView.setVisibility(View.GONE);
        loadView.setVisibility(View.GONE);
        currentPage = 1;
        requestCouponsTask();
    }

    private void requestCouponsTask() {
        CouponApi api = new CouponApi();
        api.setP(currentPage);
        api.setPageSize(20);
        api.setStatus(status);
        isLoad = true;
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CouponsBean>() {
            @Override
            public void onResponse(CouponsBean couponsBean) {
                progressBar.setVisibility(View.GONE);
                if(rl_layout!=null){
                    rl_layout.setBackgroundColor(Color.parseColor("#f0f0f0"));
                }
                ptr.refreshComplete();
                if (currentPage == 1) {
                    listEntities.clear();
                }
                int size = couponsBean.getData().getMyCoupons().getList().size();
                if (size > 0) {
                    listEntities.addAll(couponsBean.getData().getMyCoupons().getList());
                }
                adapter.notifyDataSetChanged();
                if (!couponsBean.getData().getMyCoupons().isNext()) {
                    isEnd = true;
                    loadView.setVisibility(View.GONE);
                } else {
                    isEnd = false;
                    loadView.setVisibility(View.INVISIBLE);
                }
                isLoad = false;
                setEmptyView(Constants.NO_DATA);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                ptr.refreshComplete();
                setEmptyView(Constants.NET_DISCONNECT);
            }
        });
    }

    private void setEmptyView(int type) {
        if (listEntities.isEmpty()) {
            View emptyView = listView.getEmptyView();
            if (emptyView == null) {
                emptyView = rootView.findViewById(R.id.empty_hint_layout);
                listView.setEmptyView(emptyView);
            }
            if(rl_layout!=null){
                rl_layout.setBackgroundColor(Color.WHITE);
            }
            emptyView.setVisibility(View.VISIBLE);
            ImageView imgHint = (ImageView) emptyView.findViewById(R.id.img_hint);
            imgHint.setVisibility(View.VISIBLE);
            if (type == Constants.NO_DATA) {
                imgHint.setImageResource(R.mipmap.ic_empty_coupon);
            } else {
                imgHint.setImageResource(R.mipmap.ic_no_net);
            }
        }
    }

    @Override
    public void setViewValue(View view, final CouponsBean.DataEntity.MyCouponsEntity.ListEntity listEntity, int position) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        if (listEntity.getType().equals("DISCOUNT")) {
            holder.rlMain.setBackgroundResource(R.mipmap.bg_coupon_oldtop2);
            double amount = (double) listEntity.getAmount() / 10;
            //字体不一样大
            String str = getString(R.string.label_discount, Util.getNumberFormat(amount));
            int length = str.length();
            SpannableString textSpan = new SpannableString(str);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(34)),0,length-1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(18)),length-1,length,Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            holder.tvPrice.setText(textSpan);
        } else {
            holder.rlMain.setBackgroundResource(R.mipmap.bg_coupon_oldtop);
            holder.tvPrice.setText(String.valueOf(listEntity.getAmount()));
        }
        holder.tvLimit.setText(String.format(getString(R.string.label_need_amount), listEntity.getNeedAmount()));
        holder.tvTitle.setText(listEntity.getName());
        holder.tvDate.setText(String.format(getString(R.string.label_pozhe), listEntity.getEnabledate(), listEntity.getExpiredate()));
        holder.tvDescribe.setText(listEntity.getRemark());
        holder.ivState.setVisibility(View.VISIBLE);
        switch (status) {
            case "INVALID":
                holder.ivState.setImageResource(R.mipmap.icon_coupon_overdue);
                break;
            default:
                holder.ivState.setImageResource(R.mipmap.icon_coupon_used);
                break;
        }
        holder.imgArrow.clearAnimation();
        if (listEntity.isExpand()) {
            holder.llRemind.setVisibility(View.VISIBLE);
            holder.imgArrow.setRotation(-180);
        } else {
            holder.llRemind.setVisibility(View.GONE);
            holder.imgArrow.setRotation(0);
        }
        holder.imgArrow.setOnClickListener(new DescribeClickListener(listEntity,
                holder.imgArrow, holder.llRemind));
    }

    private class DescribeClickListener implements View.OnClickListener {

        private CouponsBean.DataEntity.MyCouponsEntity.ListEntity listEntity;
        private TextView tvLabel;
        private ImageView imgArrow;
        private View describeLayout;

        public DescribeClickListener(CouponsBean.DataEntity.MyCouponsEntity.ListEntity listEntity,
                                     ImageView imgArrow, View describeLayout) {
            super();
            this.listEntity = listEntity;
            this.imgArrow = imgArrow;
            this.describeLayout = describeLayout;
        }

        @Override
        public void onClick(View v) {
            imgArrow.setRotation(0);
            if (imgArrow.getAnimation() != null && !imgArrow.getAnimation().hasEnded()) {
                return;
            }
            if (listEntity.isExpand()) {
                listEntity.setExpand(false);
                imgArrow.startAnimation(AnimUtil.getAnimArrowDown(getActivity()));
                describeLayout.setVisibility(View.GONE);
            } else {
                listEntity.setExpand(true);
                imgArrow.startAnimation(AnimUtil.getAnimArrowUp(getActivity()));
                describeLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_IDLE:
                if (view.getLastVisiblePosition() >= (view.getCount() - 5) && !isEnd && !isLoad) {
                    loadView.setVisibility(View.VISIBLE);
                    currentPage++;
                    requestCouponsTask();
                } else {
                    break;
                }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        if ((totalItemCount > visibleItemCount) && isEnd) {
            endView.setVisibility(View.VISIBLE);
        } else {
            endView.setVisibility(View.GONE);
        }
    }


    static class ViewHolder {
        @Bind(R.id.tv_price)
        public TextView tvPrice;
        @Bind(R.id.tv_discount)
        public TextView tvDiscount;
        @Bind(R.id.tv_limit)
        public TextView tvLimit;
        @Bind(R.id.tv_title)
        public TextView tvTitle;
        @Bind(R.id.tv_date)
        public TextView tvDate;
        @Bind(R.id.img_arrow)
        public ImageView imgArrow;
        @Bind(R.id.tv_get)
        public TextView tvGet;
        @Bind(R.id.iv_state)
        public ImageView ivState;
        @Bind(R.id.rl_main)
        public RelativeLayout rlMain;
        @Bind(R.id.tv_describe)
        public TextView tvDescribe;
        @Bind(R.id.ll_remind)
        public LinearLayout llRemind;
        @Bind(R.id.view_bottom)
        public View viewBottom;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}