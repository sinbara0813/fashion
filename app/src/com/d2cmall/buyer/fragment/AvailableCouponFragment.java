package com.d2cmall.buyer.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.BrandListActivity;
import com.d2cmall.buyer.activity.CouponRelationBrandsActivity;
import com.d2cmall.buyer.activity.CouponRelationProductListActivity;
import com.d2cmall.buyer.activity.ProductListActivity;
import com.d2cmall.buyer.adapter.ObjectBindAdapter;
import com.d2cmall.buyer.api.CouponApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.BrandDetailPromotionBean;
import com.d2cmall.buyer.bean.CouponRangeBean;
import com.d2cmall.buyer.bean.CouponsBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.AnimUtil;
import com.d2cmall.buyer.util.BitmapUtils;
import com.d2cmall.buyer.util.CouponBgSpan;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.d2cmall.buyer.widget.ShareCouponPop;

import java.net.URL;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class AvailableCouponFragment extends BaseFragment implements
        ObjectBindAdapter.ViewBinder<CouponsBean.DataEntity.MyCouponsEntity.ListEntity>,
        AbsListView.OnScrollListener, AdapterView.OnItemClickListener {

    private ListView listView;
    private PtrStoreHouseFrameLayout ptr;
    private View progressBar;
    private View rootView;
    private ArrayList<CouponsBean.DataEntity.MyCouponsEntity.ListEntity> listEntities;
    private ObjectBindAdapter<CouponsBean.DataEntity.MyCouponsEntity.ListEntity> adapter;
    private View endView;
    private View loadView;
    private RelativeLayout rl_layout;
    private View footView;
    private static final String ARG_PARAM1 = "param1";
    private String status;
    private boolean isEnd;
    private boolean isLoad;
    private int currentPage = 1;
    private boolean isPrepared;
    private byte[] miniData;

    public static AvailableCouponFragment newInstance(String status) {
        AvailableCouponFragment fragment = new AvailableCouponFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listEntities = new ArrayList<>();
        adapter = new ObjectBindAdapter<>(getActivity(), listEntities, R.layout.layout_coupon_item);
        footView = getActivity().getLayoutInflater().inflate(R.layout.list_foot_no_more2, null);
        endView = footView.findViewById(R.id.no_more);
        loadView = footView.findViewById(R.id.loading);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_listview, container, false);
        listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setPadding(ScreenUtil.dip2px(16), 0, ScreenUtil.dip2px(16), 0);
        rl_layout = (RelativeLayout) rootView.findViewById(R.id.rl_layout);
        ptr = (PtrStoreHouseFrameLayout) rootView.findViewById(R.id.ptr);
        progressBar = rootView.findViewById(R.id.progressBar);
        listView.addFooterView(footView);
        adapter.setViewBinder(this);
        listView.setOnItemClickListener(this);
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
        if (progressBar == null) {
            return;
        }
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
                ptr.refreshComplete();
                if (currentPage == 1) {
                    listEntities.clear();
                }
                if (rl_layout != null) {
                    rl_layout.setBackgroundColor(Color.WHITE);
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

    private void showPop(CouponsBean.DataEntity.MyCouponsEntity.ListEntity listEntity) {
        ShareCouponPop pop = new ShareCouponPop(getActivity());
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(getActivity());
        pop.setData(listEntity);
        //pages/coupon/transfer?couponId=&fromId=&name=&avatar
        //转赠优惠券小程序路径
        String miniPath = "pages/coupon/transfer?code=" + listEntity.getCode() + "&fromId=" + user.getId() + "&name=" + Util.toURLEncode(user.getNickname()) + "&avatar=" + Util.toURLEncode(Constants.IMG_HOST + user.getHead());
        pop.setMiniPath(miniPath);
        //转赠优惠券wap路径
        String webUrl = "/coupon/transfer/" + listEntity.getCode() + "?fromId=" + user.getId();
        pop.setMiniWebUrl(Constants.SHARE_URL + webUrl);
        if (miniData == null) {
            Bitmap mini = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pc_share), 300, 300, true);
            miniData = BitmapUtils.getBitmapData(mini);
            mini.recycle();
        }
        pop.setMiniPicData(miniData);
        String shareTitle;
        if (listEntity.getType().equals("DISCOUNT")) {//折扣券
            double amount = (double) listEntity.getAmount() / 10;
            shareTitle = user.getNickname() + "送你一张" + amount + "折优惠券";
        } else {
            shareTitle = user.getNickname() + "送你一张" + listEntity.getAmount() + "元优惠券";
        }

        pop.setDescription(shareTitle);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1.0f);
            }
        });
        pop.showAtLocation(rl_layout, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.4f);
    }

    private void backgroundAlpha(float f) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = f;
        getActivity().getWindow().setAttributes(lp);
    }

    private void setEmptyView(int type) {
        if (listEntities.isEmpty()) {
            View emptyView = listView.getEmptyView();
            if (emptyView == null) {
                emptyView = rootView.findViewById(R.id.empty_hint_layout);
                listView.setEmptyView(emptyView);
            }
            if (rl_layout != null) {
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
        holder.llRemind.setBackgroundResource(R.mipmap.bg_coupon_newmiddle);
        if (listEntity.getType().equals("DISCOUNT")) {
            holder.rlMain.setBackgroundResource(R.mipmap.bg_coupon_newtop2);
            double amount = (double) listEntity.getAmount() / 10;
            //字体不一样大
            String str = getString(R.string.label_discount, Util.getNumberFormat(amount));
            int length = str.length();
            SpannableString textSpan = new SpannableString(str);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(34)), 0, length - 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(18)), length - 1, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            holder.tvPrice.setText(textSpan);
        } else {
            holder.rlMain.setBackgroundResource(R.mipmap.bg_coupon_newtop);
            holder.tvPrice.setText(String.valueOf(listEntity.getAmount()));
        }
        holder.tvLimit.setText(String.format(getString(R.string.label_need_amount), listEntity.getNeedAmount()));

        if (listEntity.getPrice() != null) {
            StringBuilder stringBuilder = new StringBuilder();
            String text = "¥" + listEntity.getPrice() + "元购买";
            stringBuilder.append(text).append(listEntity.getName());
            SpannableString spannableString = new SpannableString(stringBuilder.toString());
            spannableString.setSpan(new CouponBgSpan(), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.tvTitle.setText(spannableString);
        } else {
            holder.tvTitle.setText(listEntity.getName());
        }
        if (listEntity.getTransfer() == 1) {//可赠送
            holder.tvSignCanGive.setVisibility(View.VISIBLE);
            holder.tvGiveFriend.setVisibility(View.VISIBLE);
            holder.tvGiveFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPop(listEntity);
                }
            });
        } else {//不可赠送
            holder.tvSignCanGive.setVisibility(View.GONE);
            holder.tvGiveFriend.setVisibility(View.GONE);
        }
        holder.tvDate.setText(String.format(getString(R.string.label_pozhe), listEntity.getEnabledate(), listEntity.getExpiredate()));
        holder.tvDescribe.setText(listEntity.getRemark());
//        if (!Util.isEmpty(listEntity.getUrl())) {
//            holder.arrowDotLayout.setVisibility(View.VISIBLE);
//        } else {
//            holder.arrowDotLayout.setVisibility(View.GONE);
//        }
        holder.imgArrow.clearAnimation();
        if (listEntity.isExpand()) {
            holder.llRemind.setVisibility(View.VISIBLE);
            //holder.tvLabel.setText(R.string.label_retract_detail);
            holder.imgArrow.setRotation(-180);
        } else {
            holder.llRemind.setVisibility(View.GONE);
            //holder.tvLabel.setText(R.string.label_view_detail);
            holder.imgArrow.setRotation(0);
        }
        holder.imgArrow.setOnClickListener(new DescribeClickListener(listEntity,
                holder.imgArrow, holder.llRemind));
    }


    private class DescribeClickListener implements View.OnClickListener {

        private CouponsBean.DataEntity.MyCouponsEntity.ListEntity listEntity;
        private ImageView imgArrow;
        private View describeLayout;

        public DescribeClickListener(CouponsBean.DataEntity.MyCouponsEntity.ListEntity listEntity,
                                     ImageView imgArrow, View describeLayout) {
            super();
            this.listEntity = listEntity;
            //this.tvLabel = tvLabel;
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
                imgArrow.clearAnimation();
                //tvLabel.setText(R.string.label_view_detail);
                imgArrow.startAnimation(AnimUtil.getAnimArrowDown(getActivity()));
                describeLayout.setVisibility(View.GONE);
            } else {
                listEntity.setExpand(true);
                imgArrow.clearAnimation();
                //tvLabel.setText(R.string.label_retract_detail);
                imgArrow.startAnimation(AnimUtil.getAnimArrowUp(getActivity()));
                describeLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if (position >= listEntities.size()) {
            return;
        }
        CouponsBean.DataEntity.MyCouponsEntity.ListEntity listEntity = listEntities.get(position);
        if (listEntity != null && !Util.isEmpty(listEntity.getUrl())) {
            Util.urlAction(getActivity(), listEntity.getUrl());
        }else {//查看优惠券可使用的范围,跳转商品列表或店铺列表
            loadCouponUseRange(listEntity);
        }

    }

    private void loadCouponUseRange(final CouponsBean.DataEntity.MyCouponsEntity.ListEntity listEntity) {
        if(listEntity!=null){
            SimpleApi api = new SimpleApi();
            api.setInterPath(String.format(Constants.COUPONS_PRODUCTS_RANGE,listEntity.getId()));
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CouponRangeBean>() {
                @Override
                public void onResponse(CouponRangeBean couponRangeBean) {
                    if(progressBar==null){
                        return;
                    }
                    if(couponRangeBean!=null){
                        //全场或者(商品店铺均未绑定的优惠券)不跳
                        if(couponRangeBean.getData().getCoupon()!=null && Util.isEmpty(couponRangeBean.getData().getCoupon().getCheckAssociation())){
                            if("ALL".equals(couponRangeBean.getData().getCoupon().getCheckAssociation())){
                                return;
                            }
                        }
                        if(couponRangeBean.getData().getBrands()!=null && couponRangeBean.getData().getBrands().getList()!=null && couponRangeBean.getData().getBrands().getList().size()>0 && couponRangeBean.getData().getCoupon()!=null && couponRangeBean.getData().getCoupon().isToRedirect()){
                            startActivity(new Intent(getActivity(), CouponRelationBrandsActivity.class)
                                    .putExtra("couponName",listEntity.getName())
                                    .putExtra("couponId",listEntity.getId()));
                        }else if(couponRangeBean.getData().getProductsX()!=null && couponRangeBean.getData().getProductsX().getList().size()>0 && couponRangeBean.getData().getCoupon()!=null && couponRangeBean.getData().getCoupon().isToRedirect()){
                            startActivity(new Intent(getActivity(), CouponRelationProductListActivity.class)
                                    .putExtra("couponId",listEntity.getId())
                                    .putExtra("couponName",listEntity.getName()));
                        }
                    }

                }
            });

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
        TextView tvPrice;
        @Bind(R.id.tv_discount)
        TextView tvDiscount;
        @Bind(R.id.tv_limit)
        TextView tvLimit;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.img_arrow)
        ImageView imgArrow;
        @Bind(R.id.tv_get)
        TextView tvGet;
        @Bind(R.id.iv_state)
        ImageView ivState;
        @Bind(R.id.rl_main)
        RelativeLayout rlMain;
        @Bind(R.id.tv_describe)
        TextView tvDescribe;
        @Bind(R.id.ll_remind)
        LinearLayout llRemind;
        @Bind(R.id.view_bottom)
        View viewBottom;
        @Bind(R.id.buy_price_tv)
        TextView buyPriceTv;
        @Bind(R.id.tv_sign_can_give)
        TextView tvSignCanGive;
        @Bind(R.id.tv_give_friend)
        TextView tvGiveFriend;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}