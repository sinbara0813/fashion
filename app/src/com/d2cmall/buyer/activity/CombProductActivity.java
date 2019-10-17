package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.CombProductAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseVirtualAdapter;
import com.d2cmall.buyer.bean.CombProductBean;
import com.d2cmall.buyer.binder.CombProductInfoBinder;
import com.d2cmall.buyer.binder.VideoBannerBinder;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.OnItemClickListener;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.PtrStoreHouseFrameLayout;
import com.d2cmall.buyer.widget.SharePop;
import com.d2cmall.buyer.widget.ninegrid.ImageInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 组合商品
 * Author: hrb
 * Date: 2016/06/29 15:39
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class CombProductActivity extends BaseActivity implements CombProductAdapter.ChangeNumListener {
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.ptr)
    PtrStoreHouseFrameLayout ptr;
    @Bind(R.id.back_b)
    ImageView mBackB;
    @Bind(R.id.share_b)
    ImageView mShareB;
    @Bind(R.id.back_fisrst)
    ImageView backFisrst;
    @Bind(R.id.product_title_name_first)
    TextView productTitleNameFirst;
    @Bind(R.id.share_first)
    ImageView shareFirst;
    @Bind(R.id.second_title_ll_first)
    LinearLayout secondTitleLlFirst;
    @Bind(R.id.second_title_ll)
    LinearLayout secondTitleLl;
    @Bind(R.id.tv_sale_price)
    TextView mTvSalePrice;
    @Bind(R.id.tv_combo_price)
    TextView mTvComboPrice;
    @Bind(R.id.tv_save_money)
    TextView mTvSaveMoney;
    @Bind(R.id.ll_comb_price)
    LinearLayout llCombPrice;
    @Bind(R.id.bottom_cart_ll)
    LinearLayout bottomCartLl;
    @Bind(R.id.tv_sale_down)
    TextView tvSaleDown;
    @Bind(R.id.bottom_add_cart)
    TextView mBottomAddCart;
    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.iv_empty)
    ImageView mIvEmpty;


    private long productCombId;
    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;
    private CombProductBean mCombProductBean;
    private List<String> bannerItems;
    private BaseVirtualAdapter<? extends RecyclerView.ViewHolder> bannerAdapter;
    private BaseVirtualAdapter<? extends RecyclerView.ViewHolder> productInfoAdapter;
    private CombProductInfoBinder productInfoBinder;
    private boolean hasAdapter;
    private int lastPosition = -1;
    private int lastOffer;
    private float lastAlpha;
    private CombProductAdapter combProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comb_product);
        ButterKnife.bind(this);
        initListener();
        doBussnis();
        bannerItems = new ArrayList<>();
    }

    private void initListener() {
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                getLastLocation();
                float alpha = virtualLayoutManager.getOffsetToStart() / (float) ScreenUtil.dip2px(150);
                changeAlpha(alpha);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }
        });
    }

    private void getLastLocation() {
        View topView = virtualLayoutManager.getChildAt(0);
        if (topView != null) {
            //获取与该view的顶部的偏移量
            lastOffer = topView.getTop();
            //得到该View的数组位置
            lastPosition = virtualLayoutManager.getPosition(topView);
        }
    }

    public void changeAlpha(float alpha) {
        secondTitleLl.setAlpha(alpha);
        lastAlpha = alpha;
    }

    private void doBussnis() {
        ptr.setHeadLabel(getString(R.string.label_d2c_go));
        ptr.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                requestCombProductTask();
            }
        });
        virtualLayoutManager = new VirtualLayoutManager(this);
        delegateAdapter = new DelegateAdapter(virtualLayoutManager, true);
        RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
        recycledViewPool.setMaxRecycledViews(10, 2);
        recycledViewPool.setMaxRecycledViews(20, 2);
        recycledViewPool.setMaxRecycledViews(0, 2);
        recycleView.setRecycledViewPool(recycledViewPool);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(delegateAdapter);
        productCombId = getIntent().getLongExtra("productCombId", 0);
        requestCombProductTask();
    }


    private void requestCombProductTask() {
        mProgressBar.setVisibility(View.VISIBLE);
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.COMB_PRODUCT_URL, productCombId));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<CombProductBean>() {

            @Override
            public void onResponse(CombProductBean combProductBean) {
                ptr.refreshComplete();
                mProgressBar.setVisibility(View.GONE);
                if (mCombProductBean == null) {
                    mCombProductBean = combProductBean;
                }
                if (mCombProductBean.getData().getProductComb().getProducts() == null || mCombProductBean.getData().getProductComb().getProducts().size() == 0) {
                    Util.showToast(CombProductActivity.this, "商品不存在或已下架!");
                    mIvEmpty.setVisibility(View.VISIBLE);
                    mBottomAddCart.setEnabled(false);
                    mShareB.setEnabled(false);
                    return;
                }
                if (mCombProductBean.getData().getProductComb().getMark() < 1) {    //已下架
                    tvSaleDown.setVisibility(View.VISIBLE);
                    llCombPrice.setVisibility(View.GONE);
                    bottomCartLl.setVisibility(View.GONE);
                    tvSaleDown.setText("暂无报价");
                    mBottomAddCart.setBackgroundColor(getResources().getColor(R.color.gray_color1));
                    mBottomAddCart.setText("已下架");
                    mBottomAddCart.setEnabled(false);
                } else if (mCombProductBean.getData().getProductComb().getStore() < 1) {     //已售罄(一件已售罄即为组合售罄)
                    llCombPrice.setVisibility(View.VISIBLE);
                    bottomCartLl.setVisibility(View.VISIBLE);
                    tvSaleDown.setVisibility(View.GONE);
                    mBottomAddCart.setBackgroundColor(getResources().getColor(R.color.gray_color1));
                    mBottomAddCart.setText("已售罄");
                    mBottomAddCart.setEnabled(false);
                } else {
                    mBottomAddCart.setBackgroundColor(getResources().getColor(R.color.color_black_bg));
                    llCombPrice.setVisibility(View.VISIBLE);
                    bottomCartLl.setVisibility(View.VISIBLE);
                    tvSaleDown.setVisibility(View.GONE);
                    mBottomAddCart.setText("立即购买");
                    mBottomAddCart.setEnabled(true);

                }
                if (!(mCombProductBean.getData().getProductComb().getOriginalCost() > mCombProductBean.getData().getProductComb().getPrice())) {
                    mTvSalePrice.setVisibility(View.INVISIBLE);
                    mTvSaveMoney.setVisibility(View.INVISIBLE);
                } else {
                    mTvSalePrice.setText("总价: ¥" + Util.getNumberFormat(mCombProductBean.getData().getProductComb().getOriginalCost()));
                    mTvSalePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    mTvSaveMoney.setText("立省" + Util.getNumberFormat(mCombProductBean.getData().getProductComb().getOriginalCost() - mCombProductBean.getData().getProductComb().getPrice()));
                }
                String priceStr = "套装价: ¥" + Util.getNumberFormat(mCombProductBean.getData().getProductComb().getPrice());
                int length = priceStr.length();
                SpannableString textSpan = new SpannableString(priceStr);
                textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(12)), 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(18)), 6, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                mTvComboPrice.setText(textSpan);
                if (mCombProductBean.getData().getProductComb().getProducts() != null && mCombProductBean.getData().getProductComb().getProducts().size() > 0) {
                    int imgSize = mCombProductBean.getData().getProductComb().getProducts().size();
                    bannerItems.clear();
                    for (int i = 0; i < imgSize; i++) {
                        bannerItems.add(mCombProductBean.getData().getProductComb().getProducts().get(i).getImg());
                    }
                    if (!hasAdapter) {
                        setBanner();
                        productInfoBinder = new CombProductInfoBinder(CombProductActivity.this, mCombProductBean);
                        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
                        linearLayoutHelper.setMarginTop(-ScreenUtil.dip2px(38));
                        productInfoAdapter = new BaseVirtualAdapter<>(productInfoBinder, linearLayoutHelper, 20);
                        delegateAdapter.addAdapter(productInfoAdapter);
                        combProductAdapter = new CombProductAdapter(CombProductActivity.this, mCombProductBean);
                        delegateAdapter.addAdapter(combProductAdapter);
                        hasAdapter = true;
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mIvEmpty.setVisibility(View.VISIBLE);
                mBottomAddCart.setEnabled(false);
                mShareB.setEnabled(false);
                ptr.refreshComplete();
                mProgressBar.setVisibility(View.GONE);
                Util.showToast(CombProductActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void setBanner() {
        int width = ScreenUtil.getDisplayWidth();
        VideoBannerBinder bannerBinder = new VideoBannerBinder(this, R.mipmap.ic_logo_empty5, bannerItems);
        bannerBinder.needSetBottom(true);
        bannerBinder.setVideoUrl(mCombProductBean.getData().getProductComb().getProducts().get(0).getVideo());
        bannerBinder.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void itemClick(View v, int position) {
                Intent intent = new Intent(CombProductActivity.this, ImagePreviewActivity.class);
                Bundle bundle = new Bundle();
                ArrayList<ImageInfo> imageInfos = new ArrayList<>();
                List<String> imgList = new ArrayList<>();
                for (int i = 0; i < mCombProductBean.getData().getProductComb().getProducts().size(); i++) {
                    if (mCombProductBean.getData().getProductComb().getProducts() == null) {
                        continue;
                    }
                    imgList.addAll(mCombProductBean.getData().getProductComb().getProducts().get(i).getImgs());
                }

                if (imgList != null) {
                    for (String picUrl : imgList) {
                        ImageInfo info = new ImageInfo();
                        info.isProduct = true;
                        info.productUrl = Constants.SHARE_URL + "/product/" + mCombProductBean.getData().getProductComb().getId();
                        info.setBigImageUrl(Util.getD2cPicUrl(picUrl));//大图
                        imageInfos.add(info);
                    }
                }
                bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfos);
                bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, position);
                intent.putExtras(bundle);
                startActivity(intent);
                CombProductActivity.this.overridePendingTransition(0, 0);
            }
        });
        SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
        if (mCombProductBean.getData().getProductComb().getProducts().get(0).getPicStyle() == 1) {
            singleLayoutHelper.setAspectRatio((float) 1);
        } else {
            singleLayoutHelper.setAspectRatio(width / (float) ScreenUtil.dip2px(475));
        }
        bannerAdapter = new BaseVirtualAdapter<>(bannerBinder, singleLayoutHelper, 1);
        delegateAdapter.addAdapter(bannerAdapter);
    }


    @Override
    protected void onResume() {
        Util.onResume(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        Util.onPause(this);
        super.onPause();
    }


    @OnClick({R.id.back_b, R.id.share_b, R.id.bottom_add_cart, R.id.back_fisrst, R.id.share_first})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_b:
                finish();
                break;
            case R.id.share_b:
                share();
                break;
            case R.id.back_fisrst:
                finish();
                break;
            case R.id.share_first:
                share();
                break;
            case R.id.bottom_add_cart:
                if (Util.loginChecked(CombProductActivity.this, 999)) {
                    requestBuy();
                }

                break;
        }
    }

    private void share() {
        SharePop sharePop = new SharePop(this, false);
        if (mCombProductBean != null && mCombProductBean.getData().getProductComb().getName() != null) {
            sharePop.setTitle(mCombProductBean.getData().getProductComb().getName());
            sharePop.setWebUrl(Constants.SHARE_URL + "/productComb/" + mCombProductBean.getData().getProductComb().getId());
        }
        if (mCombProductBean.getData().getProductComb().getSubTitle() != null) {
            sharePop.setDescription(mCombProductBean.getData().getProductComb().getSubTitle());
        }
        if (mCombProductBean.getData().getProductComb().getProducts().get(0).getImg() != null) {
            sharePop.setImage(Util.getD2cPicUrl(mCombProductBean.getData().getProductComb().getProducts().get(0).getImg(), 100, 100), false);
            sharePop.setImage(Util.getD2cPicUrl(mCombProductBean.getData().getProductComb().getProducts().get(0).getImg(), 360, 500), true);
        }
        sharePop.show(CombProductActivity.this.getWindow().getDecorView());
    }

    private void requestBuy() {

        if (combProductAdapter != null) {
            combProductAdapter.checkState();
        } else {
            return;
        }
        if (combProductAdapter.hasColorAndSize() == false) {
            Util.showToast(this, "请选择尺码和颜色");
            return;
        }
        Intent intent;
        if (mCombProductBean.getData().getProductComb().getProducts().get(0) != null && "CROSS".equals(mCombProductBean.getData().getProductComb().getProducts().get(0).getProductTradeType())) {
            intent = new Intent(this, GlobalOrderBalanceActivity.class);
        } else {
            intent = new Intent(this, OrderBalanceActivity.class);
        }
        intent.putExtra("productCombId", (long) mCombProductBean.getData().getProductComb().getId());
        intent.putExtra("skuId", combProductAdapter.getSkuIds());
        intent.putExtra("num", combProductAdapter.getBuyNum());
        intent.putExtra("source", OrderBalanceActivity.COMBBUYNOW);
        startActivity(intent);
    }

    @Override
    public void changeNum(int num) {
        if (mCombProductBean == null) {
            return;
        }
        if (!(mCombProductBean.getData().getProductComb().getOriginalCost() > mCombProductBean.getData().getProductComb().getPrice())) {
            mTvSalePrice.setVisibility(View.INVISIBLE);
            mTvSaveMoney.setVisibility(View.INVISIBLE);
        } else {
            mTvSalePrice.setText("总价: ¥" + Util.getNumberFormat(mCombProductBean.getData().getProductComb().getOriginalCost()));
            mTvSalePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            mTvSaveMoney.setText("立省" + Util.getNumberFormat(mCombProductBean.getData().getProductComb().getOriginalCost() - mCombProductBean.getData().getProductComb().getPrice()));
        }
        String priceStr = "套装价: ¥" + Util.getNumberFormat(mCombProductBean.getData().getProductComb().getPrice());
        int length = priceStr.length();
        SpannableString textSpan = new SpannableString(priceStr);
        textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(12)), 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(18)), 6, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mTvComboPrice.setText(textSpan);
    }
}
