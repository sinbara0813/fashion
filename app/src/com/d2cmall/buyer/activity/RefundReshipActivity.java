package com.d2cmall.buyer.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.ObjectBindAdapter;
import com.d2cmall.buyer.api.KaoLaSaleAfterListApi;
import com.d2cmall.buyer.api.PostRefundApi;
import com.d2cmall.buyer.api.PostReshipApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.bean.JsonPic;
import com.d2cmall.buyer.bean.KaoLaSameWarehouseBean;
import com.d2cmall.buyer.bean.OrdersBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.CashierInputFilter;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.TitleUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.CheckableLinearLayoutButton;
import com.d2cmall.buyer.widget.CheckableLinearLayoutGroup;
import com.d2cmall.buyer.widget.ClearEditText;
import com.d2cmall.buyer.widget.SelectReasonPop;
import com.d2cmall.buyer.widget.SingleSelectPop;
import com.d2cmall.buyer.widget.TransparentPop;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;
import com.upyun.library.common.Params;
import com.upyun.library.common.UploadManager;
import com.upyun.library.listener.SignatureListener;
import com.upyun.library.listener.UpCompleteListener;
import com.upyun.library.utils.UpYunUtils;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by rookie on 2017/9/9.
 * 申请退款以及退货退换页面
 */

public class RefundReshipActivity extends BaseActivity implements
        AdapterView.OnItemClickListener, ObjectBindAdapter.ViewBinder<JsonPic>, SingleSelectPop.CallBack {


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
    @Bind(R.id.iv_received)
    ImageView ivReceived;
    @Bind(R.id.received_layout)
    CheckableLinearLayoutButton receivedLayout;
    @Bind(R.id.iv_unreceived)
    ImageView ivUnreceived;
    @Bind(R.id.unreceived_layout)
    CheckableLinearLayoutButton unreceivedLayout;
    @Bind(R.id.status_menu)
    CheckableLinearLayoutGroup statusMenu;
    @Bind(R.id.status_layout)
    LinearLayout statusLayout;
    @Bind(R.id.status_line_view)
    View statusLineView;
    @Bind(R.id.tv_reason_label)
    TextView tvReasonLabel;
    @Bind(R.id.tv_reason)
    TextView tvReason;
    @Bind(R.id.reason_layout)
    LinearLayout reasonLayout;
    @Bind(R.id.tv_num_product)
    TextView tvNumProduct;
    @Bind(R.id.et_count)
    ClearEditText etCount;
    @Bind(R.id.rl_num_product)
    RelativeLayout rlNumProduct;
    @Bind(R.id.tv_money_product)
    TextView tvMoneyProduct;
    @Bind(R.id.et_money)
    ClearEditText etMoney;
    @Bind(R.id.et_remark)
    ClearEditText etRemark;
    @Bind(R.id.gridView)
    GridView gridView;
    @Bind(R.id.btn_apply)
    Button btnApply;
    @Bind(R.id.ll_items_container)
    LinearLayout llItemsContainer;
    @Bind(R.id.line_layout)
    View lineLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.rb_all)
    RadioButton rbAll;
    @Bind(R.id.rb_freight)
    RadioButton rbFreight;
    @Bind(R.id.rb_gap_price)
    RadioButton rbGapPrice;
    @Bind(R.id.radio_group)
    RadioGroup radioGroup;
    @Bind(R.id.tv_back_desc)
    TextView tvBackDesc;
    @Bind(R.id.tv_back_reason)
    TextView tvBackReason;
    @Bind(R.id.scroll_view)
    ScrollView scrollView;
    private ArrayList<JsonPic> photos;
    private ArrayList<JsonPic> jsonPics;
    private ObjectBindAdapter<JsonPic> adapter;
    private JsonPic emptyPic;
    private int imageSize;
    private SingleSelectPop singleSelectPop;
    private Dialog loadingDialog;
    private String reason = "";
    private int type;//type=0 申请退货退款; type=1 申请退款
    private int intentFlag;//intentFlag=0 列表进入的;intentFlag=1 详情进入的
    private String orderSn;
    private long orderId;
    private long orderItemId;
    private String skuSn;
    private long quantity;
    private String paymentType;
    private double actualAmount;
    private int upLoadIndex;
    private ArrayList<String> imgUpyunPaths;
    private String alipayAccount;
    private String alipayName;
    private String money;
    private String remark;
    private String reminded;
    private boolean isReceived = true;
    private SelectReasonPop selectPromotionPop;
    private int maxSelected = 3;
    private OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity bean;
    private String allItemAmount = null;//考拉订单同仓所有商品的金额,逗号分隔
    private String allItemId = null;//考拉订单同仓所有商品的id,逗号分隔
    private double otherAmount = 0;//考拉订单同仓其它商品的金额
    private int statusCode;     //订单状态用来退款的(已发货且未完成)radioButton显示的
    private ArrayList<RadioButton> rbs;
    private int allRefund = 1;            //1:全额退款，-1:退差价，-2:退运费

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_reship);
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", 0);
        intentFlag = getIntent().getIntExtra("intentFlag", 0);
        orderSn = getIntent().getStringExtra("orderSn");
        orderId = getIntent().getLongExtra("orderId", -1);
        orderItemId = getIntent().getLongExtra("orderItemId", -1);
        skuSn = getIntent().getStringExtra("skuSn");
        quantity = getIntent().getIntExtra("quantity", -1);
        paymentType = getIntent().getStringExtra("paymentType");
        actualAmount = getIntent().getDoubleExtra("actualAmount", 0);
        reminded = getIntent().getStringExtra("reminded");
        statusCode = getIntent().getIntExtra("statusCode", -1);
        bean = (OrdersBean.DataEntity.OrdersEntity.ListEntity.ItemsEntity) getIntent().getSerializableExtra("bean");
        initProductView();
        initRefundList();
        initReshipList();
        initTypeInfo();
        photos = new ArrayList<>();
        jsonPics = new ArrayList<>();
        emptyPic = new JsonPic();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        imageSize = Math.round(50 * dm.density);
        int gridViewWidth = Math.round(10 * 2 * dm.density + 50 * 3 * dm.density);
        gridView.getLayoutParams().width = gridViewWidth;
        tvReason.addTextChangedListener(textWatcher);

        etMoney.addTextChangedListener(textWatcher);
        InputFilter[] filters = {new CashierInputFilter()};
        etMoney.setFilters(filters);
        loadingDialog = DialogUtil.createLoadingDialog(this);
        photos.add(emptyPic);
        adapter = new ObjectBindAdapter<>(this, photos, R.layout.list_item_post_image, this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        imgUpyunPaths = new ArrayList<>();
        titleRight.setBackgroundResource(R.mipmap.tab_all_service);
    }

    private void initProductView() {
        UniversalImageLoader.displayImage(this, Util.getD2cProductPicUrl(
                bean.getProductImg(), ScreenUtil.dip2px(48), ScreenUtil.dip2px(72)), imgProduct,
                R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
        tvProductTitle.setText(bean.getProductName());


        tvProductStatus.setText(bean.getItemStatus());
        tvProductStyle.setText(getString(R.string.label_kongge, bean.getColor(), bean.getSize()));
        tvProductStatus.setText(getString(R.string.label_final_price, Util.getNumberFormat(actualAmount)));
        tvProductNum.setText(getString(R.string.label_product_quantity, bean.getQuantity()));
        if (bean != null && "KAOLA".equals(bean.getProductSource())) {
            loadKaoLaRelate();
        }
    }

    //拉取考拉商品的同仓商品
    private void loadKaoLaRelate() {
        progressBar.setVisibility(View.VISIBLE);
        KaoLaSaleAfterListApi kaoLaSaleAfterListApi = new KaoLaSaleAfterListApi();
        kaoLaSaleAfterListApi.setOrderItemId(bean.getId());
        D2CApplication.httpClient.loadingRequest(kaoLaSaleAfterListApi, new BeanRequest.SuccessListener<KaoLaSameWarehouseBean>() {
            @Override
            public void onResponse(KaoLaSameWarehouseBean kaoLaSameWarehouseBean) {
                if (isFinishing() || progressBar == null) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                //考拉商品将相关商品(同仓商品,展示出来)
                if (kaoLaSameWarehouseBean.getData().getItems().size() > 1) {
                    addRelateProduct(kaoLaSameWarehouseBean);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(RefundReshipActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void addRelateProduct(KaoLaSameWarehouseBean kaoLaSameWarehouseBean) {
        llItemsContainer.setVisibility(View.VISIBLE);
        lineLayout.setVisibility(View.VISIBLE);
        List<KaoLaSameWarehouseBean.DataBean.ItemsBean> items = kaoLaSameWarehouseBean.getData().getItems();
        StringBuilder idBuilder = new StringBuilder();
        StringBuilder priceBuilder = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            //拼接考拉同仓商品所有id和金额
//            if(i==0){
//                priceBuilder.append(items.get(i).getActualAmount());
//            }else{
//                priceBuilder.append(","+items.get(i).getActualAmount());
//            }
//            if(i==0){
//                idBuilder.append(items.get(i).getId());
//            }else{
//                idBuilder.append(","+items.get(i).getId());
//            }

            if (items.get(i).getId() == bean.getId()) {
                continue;
            }

            otherAmount += items.get(i).getActualAmount() * items.get(i).getQuantity();
            View itemView = getLayoutInflater().inflate(R.layout.layout_after_sale_list_item, llItemsContainer, false);
            ImageView itemImgProduct = (ImageView) itemView.findViewById(R.id.img_product);
            TextView itemTvProductTitle = (TextView) itemView.findViewById(R.id.tv_product_title);
            TextView itemTvProductStyle = (TextView) itemView.findViewById(R.id.tv_product_style);
            TextView itemTvProductStatus = (TextView) itemView.findViewById(R.id.tv_product_status);
            TextView itemTvProductNum = (TextView) itemView.findViewById(R.id.tv_product_num);
            View line = itemView.findViewById(R.id.line_layout);
            UniversalImageLoader.displayImage(this, Util.getD2cProductPicUrl(
                    items.get(i).getProductImg(), ScreenUtil.dip2px(48), ScreenUtil.dip2px(72)), itemImgProduct,
                    R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);
            itemTvProductTitle.setText(items.get(i).getProductName());
            itemTvProductStyle.setText(getString(R.string.label_kongge, items.get(i).getColor(), items.get(i).getSize()));
            itemTvProductStatus.setText(getString(R.string.label_final_price, Util.getNumberFormat(items.get(i).getActualAmount())));
            itemTvProductNum.setText(getString(R.string.label_product_quantity, items.get(i).getQuantity()));
            line.setVisibility(View.VISIBLE);
            llItemsContainer.addView(itemView);
        }
        allItemAmount = priceBuilder.toString();
        allItemId = idBuilder.toString();
        etMoney.setHint(getString(R.string.hint_refund_money, Util.getNumberFormat(actualAmount + otherAmount, false)));

    }


    public void showPop() {
        if (photos.contains(emptyPic)) {
            photos.remove(emptyPic);
        }
        PackageManager pkgManager = getPackageManager();
        boolean cameraPermission =
                pkgManager.checkPermission(Manifest.permission.CAMERA, getPackageName()) == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= 23 && !cameraPermission) {
            requestPermission();
        } else {
            choosePic();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                Constants.RequestCode.REQUEST_PERMISSION);
    }

    private void choosePic() {
        Matisse.from(RefundReshipActivity.this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .theme(R.style.Matisse_Dracula)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "com.d2cmall.buyer.fileprovider"))
                .maxSelectable(maxSelected)
                .gridExpectedSize(ScreenUtil.dip2px(120))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(456);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.RequestCode.REQUEST_PERMISSION) {
            if ((grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                choosePic();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void initTypeInfo() {
        TitleUtil.setBack(this);
        if (type == 0) {//退货退款
            rlNumProduct.setVisibility(View.VISIBLE);
            TitleUtil.setTitle(this, R.string.label_reship_info);
            tvReasonLabel.setText(R.string.label_reship_reason);
            etCount.setHint(String.format(getString(R.string.hint_refund_count), String.valueOf(bean.getQuantity())));
            //申请金额根据用户所填退货件数变化
            if(bean.getQuantity()==1){
                etCount.setText("1");
                etCount.setFocusable(false);
            }
            if(bean.getQuantity()>1){
                TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if(!Util.isEmpty(etCount.getText().toString())){
                            Integer applyCount = Integer.valueOf(etCount.getText().toString());
                            if(applyCount!=null && bean.getQuantity() > applyCount && applyCount>0){
                                etMoney.setText(Util.getNumberFormat((bean.getActualAmount()*applyCount)/bean.getQuantity()));
                            }else{
                                etMoney.setText(Util.getNumberFormat(bean.getActualAmount()));
                            }
                            if(applyCount!=null && (applyCount<=0 || applyCount>bean.getQuantity())){
                                etCount.setText(bean.getQuantity()+"");
                                etMoney.setText(Util.getNumberFormat(bean.getActualAmount()));
                            }
                        }else{
                            etMoney.setText(Util.getNumberFormat(bean.getActualAmount()));
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                };
                etCount.addTextChangedListener(textWatcher);
            }

            if (!"COD".equals(paymentType)) {
                statusLayout.setVisibility(View.VISIBLE);
                statusLineView.setVisibility(View.VISIBLE);
            } else {
                statusLayout.setVisibility(View.GONE);
                statusLineView.setVisibility(View.GONE);
            }
            etMoney.setText(Util.getNumberFormat(bean.getActualAmount()));
            etMoney.setFocusable(false);
//            tvMoneyLabel.setText(R.string.label_reship_money);
//            tvRemarkLabel.setText(R.string.label_reship_remark);
            singleSelectPop = new SingleSelectPop(this, getResources().getStringArray(R.array.label_exchange_titles));
        } else {//退款
            if (statusCode >= 2 && statusCode < 8) {
                radioGroup.setVisibility(View.VISIBLE);
                rbs = new ArrayList<>();
                rbs.add(rbAll);
                rbs.add(rbFreight);
                rbs.add(rbGapPrice);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.rb_all://退全款
                                changeRbStatus(checkedId);
                                allRefund = 1;
                                break;
                            case R.id.rb_gap_price: //退差价
                                changeRbStatus(checkedId);
                                allRefund = -1;
                                break;
                            case R.id.rb_freight: //退差价
                                changeRbStatus(checkedId);
                                allRefund = -2;
                                break;
                        }
                    }
                });
            }
            TitleUtil.setTitle(this, R.string.label_refund_info);
            tvReasonLabel.setText(R.string.label_refund_reason);
            tvMoneyProduct.setText("退款金额");
            rlNumProduct.setVisibility(View.GONE);
            statusLayout.setVisibility(View.GONE);
            statusLineView.setVisibility(View.GONE);
//            if (!"COD".equals(paymentType)) {
//                tvLabel.setText(reminded);
//            } else {
//                tvLabel.setText(R.string.label_is_cod);
//            }
//            tvMoneyLabel.setText(R.string.label_refund_money);
//            tvRemarkLabel.setText(R.string.label_refund_remark);
            singleSelectPop = new SingleSelectPop(this, getResources().getStringArray(R.array.label_refund_titles));
        }
        singleSelectPop.setCallBack(this);
        etMoney.setHint(getString(R.string.hint_refund_money, Util.getNumberFormat(actualAmount, false)));
    }

    //改变rb的显示状态
    private void changeRbStatus(int checkedId) {
        if (rbs == null) {
            return;
        }
        for (int i = 0; i < rbs.size(); i++) {
            if (checkedId == rbs.get(i).getId()) {
                rbs.get(i).setBackgroundResource(R.drawable.sp_round4_stroke_red);
                rbs.get(i).setTextColor(getResources().getColor(R.color.color_red));
            } else {
                rbs.get(i).setBackgroundResource(R.drawable.sp_round4_stroke_black3);
                rbs.get(i).setTextColor(getResources().getColor(R.color.trans_50_color_black));
            }
        }
    }

    @OnClick({R.id.back_iv, R.id.received_layout, R.id.unreceived_layout, R.id.title_right, R.id.tv_back_desc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.received_layout:
                isReceived = true;
                ivReceived.setImageResource(R.mipmap.icon_shopcart_aselected);
                ivUnreceived.setImageResource(R.mipmap.icon_shopcart_unaselected);
                break;
            case R.id.unreceived_layout:
                isReceived = false;
                ivReceived.setImageResource(R.mipmap.icon_shopcart_unaselected);
                ivUnreceived.setImageResource(R.mipmap.icon_shopcart_aselected);
                break;
            case R.id.title_right:
                toChat();
                break;
            case R.id.tv_back_desc:
                //退款说明
                Util.urlAction(RefundReshipActivity.this, "http://d2cmall.com/page/refunds");
                break;
        }
    }

    private class ViewHolder {
        View addView;
        View deleteView;
        ImageView imageView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        JsonPic jsonPic = (JsonPic) parent.getAdapter().getItem(position);
        if (jsonPic != null) {
            if (Util.isEmpty(jsonPic.getMediaPath())) {
                showPop();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 456) {
            if (resultCode == RESULT_OK) {
                for (String string : Matisse.obtainPathResult(data)) {
                    JsonPic jsonPic = new JsonPic();
                    jsonPic.setMediaPath(string);
                    photos.add(jsonPic);
                    jsonPics.add(jsonPic);
                }
                if (!photos.contains(emptyPic) && jsonPics.size() < 3) {
                    photos.add(emptyPic);
                }
                if (jsonPics.size() >= 3 && photos.contains(emptyPic)) {
                    photos.remove(emptyPic);
                }
                maxSelected = 3 - jsonPics.size();
                adapter.notifyDataSetChanged();
            } else {
                if (!photos.contains(emptyPic) && jsonPics.size() < 3) {
                    photos.add(emptyPic);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setViewValue(View view, JsonPic jsonPic, int position) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.addView = view.findViewById(R.id.add_btn);
            holder.deleteView = view.findViewById(R.id.delete);
            holder.imageView = (ImageView) view.findViewById(R.id.image);
            view.getLayoutParams().width = imageSize;
            view.getLayoutParams().height = imageSize;
            view.setTag(holder);
        }
        if (Util.isEmpty(jsonPic.getMediaPath())) {
            holder.imageView.setVisibility(View.GONE);
            holder.deleteView.setVisibility(View.GONE);
            holder.addView.setVisibility(View.VISIBLE);
        } else {
            holder.addView.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.VISIBLE);
            holder.deleteView.setVisibility(View.VISIBLE);
            holder.deleteView.setOnClickListener(new OnPhotoDeleteClickListener(jsonPic));
            Glide.with(this)
                    .load(Uri.fromFile(new File(jsonPic.getMediaPath())))
                    .error(R.mipmap.ic_default_pic)
                    .into(holder.imageView);
        }
    }


    @OnClick(R.id.reason_layout)
    void onClick() {
        showPopupMenu(tvReasonLabel);
    }

    private List<String> reshipList = new ArrayList<>();

    private void initReshipList() {
        reshipList.add("商品需要维修");
        reshipList.add("收到商品破损");
        reshipList.add("商品错发/漏发");
        reshipList.add("收到商品描述不符");
        reshipList.add("商品质量问题");
        reshipList.add("七天无理由退货");
    }

    private void showPopupMenu(View view) {
        if (type == 0) {
            selectPromotionPop = new SelectReasonPop(this, reshipList, tvReasonLabel.getText().toString());
            selectPromotionPop.setTitle("退货退款原因");
            selectPromotionPop.setDissMissListener(new TransparentPop.DismissListener() {
                @Override
                public void dismissStart() {

                }

                @Override
                public void dismissEnd() {
                    if (!Util.isEmpty(selectPromotionPop.getPromotion())) {
                        tvReason.setText(selectPromotionPop.getPromotion());
                        getParams(selectPromotionPop.getPosition());
                    }
                }
            });
            selectPromotionPop.show(tvReasonLabel);
        } else {
            selectPromotionPop = new SelectReasonPop(this, refundList, tvReasonLabel.getText().toString());
            selectPromotionPop.setTitle("退款原因");
            selectPromotionPop.setDissMissListener(new TransparentPop.DismissListener() {
                @Override
                public void dismissStart() {

                }

                @Override
                public void dismissEnd() {
                    if (!Util.isEmpty(selectPromotionPop.getPromotion())) {
                        tvReason.setText(selectPromotionPop.getPromotion());
                        getParams(selectPromotionPop.getPosition());
                    }
                }
            });
            selectPromotionPop.show(tvReasonLabel);
        }
    }

    private List<String> refundList = new ArrayList<>();

    private void initRefundList() {
        refundList.add("拍错了");
        refundList.add("不想要了");
        refundList.add("商品缺货");
        refundList.add("协商一致退款");
        refundList.add("订单信息错误");
    }

    private void getParams(int index) {
        buttonEnableOrNot();
        if (type == 0) {//退货退款
            switch (index) {
                case 0:
                    reason = "repair";
                    break;
                case 1:
                    reason = "damaged";
                    break;
                case 2:
                    reason = "wrong";
                    break;
                case 3:
                    reason = "not";
                    break;
                case 4:
                    reason = "quality";
                    break;
                case 5:
                    reason = "noReason";
                    break;
            }
        } else {//退款
            switch (index) {
                case 0:
                    reason = "wrong";
                    break;
                case 1:
                    reason = "no";
                    break;
                case 2:
                    reason = "stock";
                    break;
                case 3:
                    reason = "consensus";
                    break;
                case 4:
                    reason = "error";
                    break;
            }
        }
    }

    @OnClick(R.id.btn_apply)
    void onApply() {
        hideKeyboard(null);
        money = etMoney.getText().toString().trim();
        remark = etRemark.getText().toString().trim();
        if (Util.isEmpty(money)) {
            Util.showToast(this, "请输入金额");
            return;
        }
        if (Util.isEmpty(reason)) {
            Util.showToast(this, "请选择退货退款原因");
            return;
        }
        if(money.contains(",")){
            money = money.replaceAll(",","");
        }
        double moneyDouble = Double.parseDouble(money);
        if (moneyDouble > actualAmount + otherAmount) {
            Util.showToast(this, R.string.msg_apply_money_limit);
            return;
        }
//        if ("COD".equals(paymentType)) {
//            if (Util.isEmpty(alipayAccount)) {
//                Util.showToast(this, R.string.msg_alipay_account_empty);
//                return;
//            }
//            if (Util.isEmpty(alipayName)) {
//                Util.showToast(this, R.string.msg_alipay_name_empty);
//                return;
//            }
//            if (alipayAccount.length() > 20) {
//                Util.showToast(this, R.string.msg_alipay_account_error);
//                return;
//            }
//            if (alipayName.length() < 2 || alipayName.length() > 10) {
//                Util.showToast(this, R.string.msg_alipay_name_error);
//                return;
//            }
//        }
        if (remark.length() > 100) {
            Util.showToast(this, R.string.msg_mark_error);
            return;
        }
        if ((reason.equals("repair") || reason.equals("damaged") || reason.equals("quality")) && jsonPics.isEmpty()) {
            Util.showToast(this, "请上传图片凭证");
            return;
        }
        loadingDialog.show();
        if (!jsonPics.isEmpty()) {
            upLoadIndex = 0;
            uploadFile();
        } else {
            requestTask();
        }
    }

    private class OnPhotoDeleteClickListener implements View.OnClickListener {
        private JsonPic jsonPic;

        private OnPhotoDeleteClickListener(JsonPic jsonPic) {
            this.jsonPic = jsonPic;
        }

        @Override
        public void onClick(View v) {
            photos.remove(jsonPic);
            jsonPics.remove(jsonPic);
            maxSelected = 3 - jsonPics.size();
            if (!photos.contains(emptyPic)) {
                photos.add(emptyPic);
            }
            adapter.notifyDataSetChanged();
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            buttonEnableOrNot();
        }

        public void afterTextChanged(Editable s) {
        }
    };

    private void requestTask() {
        if (type == 0) {
            PostReshipApi api = new PostReshipApi();
            //api.setQuantity(quantity);
            if (!Util.isEmpty(etCount.getText().toString())) {
                api.setReceiveQuantity(Integer.valueOf(etCount.getText().toString()));
            } else {
                api.setReceiveQuantity((int) quantity);
            }
            api.setOrderItemId(orderItemId);
            if (Util.isEmpty(reason)) {
                Util.showToast(RefundReshipActivity.this, "请选择退款原因");
                return;
            } else {
                api.setReshipReason(reason);
            }
            api.setApplyAmount(money);
            if (!"COD".equals(paymentType)) {
                if (isReceived) {
                    api.setReceived(1);
                } else {
                    api.setReceived(0);
                }
            } else {
                api.setBackAccountSn(alipayAccount);
                api.setBackAccountName(alipayName);
            }
            api.setEvidences(Util.join(imgUpyunPaths.toArray(), ","));
            api.setMemo(remark);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean baseBean) {
                    loadingDialog.dismiss();
                    Util.showToast(RefundReshipActivity.this, baseBean.getMsg());
//                    if (intentFlag == 0) {
//                        Intent intent = new Intent(RefundReshipActivity.this, MyOrderActivity.class);
//                        startActivity(intent);
//                    } else {
//                        Intent intent = new Intent(RefundReshipActivity.this, OrderDetailActivity.class);
//                        intent.putExtram  ("orderSn", orderSn);
//                        startActivity(intent);
//                    }
                    Intent intent = new Intent();//跳转到退货
                    intent.putExtra("position", 0);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loadingDialog.dismiss();
                    Util.showToast(RefundReshipActivity.this, Util.checkErrorType(error));
                }
            });
        } else {
            PostRefundApi api = new PostRefundApi();
//            if (!Util.isEmpty(etCount.getText().toString())) {
//                api.setQuantity(Integer.valueOf(etCount.getText().toString()));
//            } else {
//                api.setQuantity((int) quantity);
//            }
            api.setAllRefund(allRefund);
            api.setOrderItemId(orderItemId);
            api.setApplyAmount(money);
            if (Util.isEmpty(reason)) {
                Util.showToast(RefundReshipActivity.this, "请选择退款原因");
                return;
            } else {
                api.setRefundReason(reason);
            }
//            if ("COD".equals(paymentType)) {
//                api.setBackAccountSn(alipayAccount);
//                api.setBackAccountName(alipayName);
//            }
            api.setEvidences(Util.join(imgUpyunPaths.toArray(), ","));
            api.setMemo(remark);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                @Override
                public void onResponse(BaseBean baseBean) {
                    loadingDialog.dismiss();
                    Util.showToast(RefundReshipActivity.this, baseBean.getMsg());
                    EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.APPLY_AFTER));
//                    if (intentFlag == 0) {
//                        Intent intent = new Intent(RefundReshipActivity.this, MyOrderActivity.class);
//                        startActivity(intent);
//                    } else {
//                        Intent intent = new Intent(RefundReshipActivity.this, OrderDetailActivity.class);
//                    intent.putExtra("orderSn",orderSn);
//                        startActivity(intent);
//                    }
                    Intent intent = new Intent();
                    intent.putExtra("position", 2);
                    setResult(RESULT_OK, intent);
                    finish();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loadingDialog.dismiss();
                    Util.showToast(RefundReshipActivity.this, Util.checkErrorType(error));
                }
            });
        }
    }

    private void buttonEnableOrNot() {
        boolean reasonTyped = tvReason.getText().length() > 0;
        boolean isMoney = false;
        if (!Util.isEmpty(etMoney.getText().toString())) {
            String amount = etMoney.getText().toString();
            if(amount.contains(",")){
                amount = amount.replaceAll(",","");
            }
            double moneyDouble = Double.parseDouble(amount);
            if (moneyDouble > 0) {
                isMoney = true;
            }
        }
//        if ("COD".equals(paymentType)) {
//            if (reasonTyped && alipayAccountTyped && alipayNameTyped && isMoney) {
//                btnApply.setEnabled(true);
//            } else {
//                btnApply.setEnabled(false);
//            }
//        } else {
//            if (reasonTyped && isMoney) {
//                btnApply.setEnabled(true);
//            } else {
//                btnApply.setEnabled(false);
//            }
//        }
    }

    private void uploadFile() {
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
        JsonPic jsonPic = jsonPics.get(upLoadIndex);
        File file = new File(jsonPic.getMediaPath());
        final Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(Params.BUCKET, Constants.UPYUN_SPACE);
        paramsMap.put(Params.SAVE_KEY, Util.getUPYunSavePath(user.getId(), Constants.TYPE_CUSTOMER));
        paramsMap.put(Params.RETURN_URL, "httpbin.org/post");
        UpCompleteListener completeListener = new UpCompleteListener() {
            @Override
            public void onComplete(boolean isSuccess, String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    imgUpyunPaths.add(jsonObject.optString("url"));
                } catch (JSONException e) {
                }
                upLoadIndex++;
                if (upLoadIndex < jsonPics.size()) {
                    uploadFile();
                } else {
                    requestTask();
                }
            }
        };
        SignatureListener signatureListener = new SignatureListener() {
            @Override
            public String getSignature(String raw) {
                return UpYunUtils.md5(raw + Constants.UPYUN_KEY);
            }
        };
        UploadManager.getInstance().blockUpload(file, paramsMap, signatureListener, completeListener, null);
    }

    @Override
    public void callback(View trigger, int index, String value) {
        tvReason.setText(value);
        buttonEnableOrNot();
        if (type == 0) {//退货退款
            switch (index) {
                case 0:
                    reason = "repair";
                    break;
                case 1:
                    reason = "damaged";
                    break;
                case 2:
                    reason = "wrong";
                    break;
                case 3:
                    reason = "not";
                    break;
                case 4:
                    reason = "quality";
                    break;
                case 5:
                    reason = "noReason";
                    break;
            }
        } else {//退款
            switch (index) {
                case 0:
                    reason = "wrong";
                    break;
                case 1:
                    reason = "no";
                    break;
                case 2:
                    reason = "stock";
                    break;
                case 3:
                    reason = "consensus";
                    break;
                case 4:
                    reason = "error";
                    break;
            }
        }
    }

    private void toChat() {
        String title = "线上客服";
        String url = "http://www.d2cmall.com";
        ConsultSource source = new ConsultSource(url, title, "售后详情");
        source.groupId = Constants.QIYU_AF_GROUP_ID;
        source.robotFirst = true;
        Unicorn.openServiceActivity(this, "D2C客服", source);
        //合力亿捷
//        Intent intent = new Intent(this,CustomServiceActivity.class);
//        intent.putExtra("skillGroupId",Constants.HLYJ_BF_AF_GROUP_ID);
//        startActivity(intent);
    }

}
