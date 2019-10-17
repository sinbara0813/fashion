package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.KaoLaSaleAfterListApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.KaoLaSameWarehouseBean;
import com.d2cmall.buyer.bean.OrdersBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/9/9.
 * 申请售后页面,可申请退款退货以及退款
 */

public class ApplyAfterSaleActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.tag)
    View tag;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.apply_exchange_layout)
    RelativeLayout applyExchangeLayout;
    @Bind(R.id.apply_reship_layout)
    RelativeLayout applyReshipLayout;
    @Bind(R.id.apply_refund_layout)
    RelativeLayout applyRefundLayout;
    @Bind(R.id.ll_items_container)
    LinearLayout llItemsContainer;
    @Bind(R.id.scroll_view)
    ScrollView scrollView;
    @Bind(R.id.img_product)
    ImageView imgProduct;
    @Bind(R.id.tv_product_title)
    TextView tvProductTitle;
    @Bind(R.id.tv_product_style)
    TextView tvProductStyle;
    @Bind(R.id.tv_product_num)
    TextView tvProductNum;
    @Bind(R.id.tv_product_status)
    TextView tvProductStatus;
    private int intentFlag;
    private long orderId;
    private String orderSn;
    private OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity bean;
    private int productQuantity;
    private boolean isKaoLa;
    private int statusCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_after_sale);
        ButterKnife.bind(this);
        nameTv.setText("申请售后");
        intentFlag = getIntent().getIntExtra("intentFlag", 0);
        orderId = getIntent().getLongExtra("orderId", -1);
        statusCode = getIntent().getIntExtra("statusCode", -1);
        orderSn = getIntent().getStringExtra("orderSn");
        titleRight.setBackgroundResource(R.mipmap.tab_all_service);
        bean = (OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity) getIntent().getSerializableExtra("bean");
        isKaoLa = getIntent().getBooleanExtra("isKaoLa", false);
        //判断能否支持退款退货以及退款
        if (bean.getAfterApply().getReship() > 0) {
            applyReshipLayout.setVisibility(View.VISIBLE);
        }
        if (bean.getAfterApply().getRefund() > 0) {
            applyRefundLayout.setVisibility(View.VISIBLE);
        }
        if(isKaoLa){
            applyRefundLayout.setVisibility(View.VISIBLE);      //考拉商品显示退款,让用户自己操作
            applyReshipLayout.setVisibility(View.GONE);         //考拉商品不允许退货
        }
        Glide.with(this)
                .load(Util.getD2cProductPicUrl(
                        bean.getProductImg(), ScreenUtil.dip2px(48),ScreenUtil.dip2px(72)))
                .error(R.mipmap.ic_logo_empty5)
                .listener(null)
                .into(imgProduct);
            tvProductTitle.setText(bean.getProductName());
            tvProductStatus.setText(bean.getItemStatus());
            tvProductStyle.setText(getString(R.string.label_kongge, bean.getColor(), bean.getSize()));
            tvProductStatus.setText(getString(R.string.label_final_price, Util.getNumberFormat(bean.getActualAmount())));
            tvProductNum.setText(getString(R.string.label_product_quantity, bean.getQuantity()));
            if(isKaoLa){
                loadKaoLaRelate(); //拉取考拉商品的同仓商品
            }

    }

    //拉取考拉商品的同仓商品
    private void loadKaoLaRelate() {
        KaoLaSaleAfterListApi kaoLaSaleAfterListApi = new KaoLaSaleAfterListApi();
        kaoLaSaleAfterListApi.setOrderItemId(bean.getId());
        D2CApplication.httpClient.loadingRequest(kaoLaSaleAfterListApi, new BeanRequest.SuccessListener<KaoLaSameWarehouseBean>() {
            @Override
            public void onResponse(KaoLaSameWarehouseBean kaoLaSameWarehouseBean) {
                //考拉商品将相关商品(同仓商品,展示出来)
                if(kaoLaSameWarehouseBean.getData().getItems().size()>1){
                    addRelateProduct(kaoLaSameWarehouseBean);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(ApplyAfterSaleActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void addRelateProduct(KaoLaSameWarehouseBean kaoLaSameWarehouseBean) {
        llItemsContainer.setVisibility(View.VISIBLE);
        List<KaoLaSameWarehouseBean.DataBean.ItemsBean> items = kaoLaSameWarehouseBean.getData().getItems();
        for (int i = 0; i < items.size(); i++) {
            if(items.get(i).getId()==bean.getId()){
                continue;
            }
            View itemView = getLayoutInflater().inflate(R.layout.layout_after_sale_list_item, llItemsContainer, false);
            ImageView itemImgProduct = (ImageView) itemView.findViewById(R.id.img_product);
            TextView itemTvProductTitle = (TextView) itemView.findViewById(R.id.tv_product_title);
            TextView itemTvProductStyle = (TextView) itemView.findViewById(R.id.tv_product_style);
            TextView itemTvProductStatus = (TextView) itemView.findViewById(R.id.tv_product_status);
            TextView itemTvProductNum = (TextView) itemView.findViewById(R.id.tv_product_num);
            View line = itemView.findViewById(R.id.line_layout);
            UniversalImageLoader.displayImage(this, Util.getD2cProductPicUrl(
                    items.get(i).getProductImg(), ScreenUtil.dip2px(48),ScreenUtil.dip2px(72)), itemImgProduct,
                    R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
                itemTvProductTitle.setText(items.get(i).getProductName());
                itemTvProductStyle.setText(getString(R.string.label_kongge, items.get(i).getColor(), items.get(i).getSize()));
                itemTvProductStatus.setText(getString(R.string.label_final_price, Util.getNumberFormat(items.get(i).getActualAmount())));
                itemTvProductNum.setText(getString(R.string.label_product_quantity, items.get(i).getQuantity()));
                line.setVisibility(View.VISIBLE);
            llItemsContainer.addView(itemView);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999 && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick({R.id.img_product, R.id.back_iv, R.id.apply_exchange_layout, R.id.apply_reship_layout, R.id.apply_refund_layout, R.id.title_right})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.img_product:
                intent = new Intent(this, ProductDetailActivity.class);
                intent.putExtra("id", bean.getProductId());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.apply_exchange_layout:
                break;
            case R.id.apply_reship_layout:
                intent = new Intent(this, RefundReshipActivity.class);
                intent.putExtra("type", 0);
                intent.putExtra("orderSn", orderSn);
                intent.putExtra("orderId", orderId);
                intent.putExtra("orderItemId", bean.getId());
                intent.putExtra("skuSn", bean.getSkuSn());
                intent.putExtra("quantity", bean.getQuantity());
                intent.putExtra("paymentType", bean.getPaymentType());
                intent.putExtra("actualAmount", bean.getActualAmount());
                intent.putExtra("reminded", bean.getReminded());
                intent.putExtra("intentFlag", intentFlag);
                intent.putExtra("bean", bean);
                intent.putExtra("statusCode", statusCode);
                startActivityForResult(intent, 999);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
            case R.id.apply_refund_layout:
                intent = new Intent(this, RefundReshipActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("orderSn", orderSn);
                intent.putExtra("orderId", orderId);
                intent.putExtra("orderItemId", bean.getId());
                intent.putExtra("skuSn", bean.getSkuSn());
                intent.putExtra("quantity", bean.getQuantity());
                intent.putExtra("paymentType", bean.getPaymentType());
                intent.putExtra("actualAmount", bean.getActualAmount());
                intent.putExtra("reminded", bean.getReminded());
                intent.putExtra("intentFlag", intentFlag);
                intent.putExtra("bean", bean);
                intent.putExtra("statusCode", statusCode);
                startActivityForResult(intent, 999);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
            case R.id.title_right:
                toChat();
                break;
        }
    }

    private void toChat() {
        String title = "线上客服";
        String url = "http://www.d2cmall.com";
        ConsultSource source = new ConsultSource(url, title, "售后");
        source.groupId = Constants.QIYU_AF_GROUP_ID;
        source.robotFirst = true;
        Unicorn.openServiceActivity(this, "D2C客服", source);
        //合力亿捷
//        Intent intent = new Intent(this,CustomServiceActivity.class);
//        intent.putExtra("skillGroupId", Constants.HLYJ_BF_AF_GROUP_ID);
//        startActivity(intent);
    }
}
