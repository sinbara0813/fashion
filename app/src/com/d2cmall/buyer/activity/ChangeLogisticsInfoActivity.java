package com.d2cmall.buyer.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.PostExchangeLogisticApi;
import com.d2cmall.buyer.api.PostReshipLogisticApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;
import com.d2cmall.buyer.widget.SingleSelectPop;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
//填写修改物流信息(退换货)

public class ChangeLogisticsInfoActivity extends BaseActivity implements SingleSelectPop.CallBack {

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
    @Bind(R.id.tv_reason_label)
    TextView tvReasonLabel;
    @Bind(R.id.tv_company)
    TextView tvCompany;
    @Bind(R.id.ll_logistics_company)
    LinearLayout llLogisticsCompany;
    @Bind(R.id.line_layout)
    View lineLayout;
    @Bind(R.id.tv_back_code_desc)
    TextView tvBackCodeDesc;
    @Bind(R.id.tv_back_code)
    ClearEditText tvBackCode;
    @Bind(R.id.tv_desc)
    TextView tvDesc;
    @Bind(R.id.tv_back_desc)
    ClearEditText tvBackDesc;
    @Bind(R.id.btn_apply)
    Button btnApply;
    private int id;
    private String img;
    private String name;
    private String size;
    private String color;
    private SingleSelectPop singleSelectPop;
    private Dialog loadingDialog;
    private String logisticCompany;
    private String action;
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_logistic);
        ButterKnife.bind(this);
        id = getIntent().getIntExtra("id", 0);
        img = getIntent().getStringExtra("img");
        name = getIntent().getStringExtra("name");
        size = getIntent().getStringExtra("size");
        color = getIntent().getStringExtra("color");
        action = getIntent().getStringExtra("action");
        type = getIntent().getIntExtra("type", 0);
        init();
    }

    private void init() {
        tvProductTitle.setText(name);
        tvProductStyle.setText(getString(R.string.label_product_style, color, size));
        UniversalImageLoader.displayImage(this, img, imgProduct
                , R.mipmap.ic_logo_empty5, R.mipmap.ic_logo_empty5);

        String[] titles = getResources().getStringArray(R.array.label_logistic_titles);
        singleSelectPop = new SingleSelectPop(this, titles);
        singleSelectPop.setCallBack(this);
        tvBackCode.addTextChangedListener(textWatcher);
        tvCompany.addTextChangedListener(textWatcher);
        loadingDialog = DialogUtil.createLoadingDialog(this);
        nameTv.setText(action);
    }


    @Override
    public void callback(View trigger, int index, String value) {
        tvCompany.setText(value);
        buttonEnableOrNot();
        logisticCompany = value;
    }

    private void changeExchangeLogistic() {
        hideKeyboard(null);
        String deliverySn = tvBackCode.getText().toString().trim();
        PostExchangeLogisticApi api = new PostExchangeLogisticApi();
        api.setDeliveryCorpName(logisticCompany);
        api.setDeliverySn(deliverySn);
        api.setExchangeId(id);
        if(!Util.isEmpty(tvBackDesc.getText().toString().trim())){
            api.setMemo(tvBackDesc.getText().toString().trim());
        }
        loadingDialog.show();
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                loadingDialog.dismiss();
                setResult(RESULT_OK);
                Util.showToast(ChangeLogisticsInfoActivity.this, baseBean.getMsg());
                ChangeLogisticsInfoActivity.super.onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Util.showToast(ChangeLogisticsInfoActivity.this, Util.checkErrorType(error));
            }
        });
    }
    private void changeReshipLogistic() {
        hideKeyboard(null);
        String deliverySn = tvBackCode.getText().toString().trim();
        PostReshipLogisticApi postReshipLogisticApi = new PostReshipLogisticApi();
        postReshipLogisticApi.setDeliveryCorpName(logisticCompany);
        postReshipLogisticApi.setDeliverySn(deliverySn);
        postReshipLogisticApi.setReshipId(id);
        if(!Util.isEmpty(tvBackDesc.getText().toString().trim())){
            postReshipLogisticApi.setMemo(tvBackDesc.getText().toString().trim());
        }
        loadingDialog.show();
        D2CApplication.httpClient.loadingRequest(postReshipLogisticApi, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                loadingDialog.dismiss();
                setResult(RESULT_OK);
                Util.showToast(ChangeLogisticsInfoActivity.this, baseBean.getMsg());
                ChangeLogisticsInfoActivity.super.onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Util.showToast(ChangeLogisticsInfoActivity.this, Util.checkErrorType(error));
            }
        });
    }


    private void buttonEnableOrNot() {
        boolean logisticNoTyped = tvBackCode.getText().toString().trim().length() > 0;
        boolean logisticCompanyTyped = tvCompany.getText().toString().trim().length() > 0;
        if (logisticNoTyped && logisticCompanyTyped) {
            btnApply.setEnabled(true);
            btnApply.setBackgroundColor(getResources().getColor(R.color.color_black87));
        } else {
            btnApply.setBackgroundColor(getResources().getColor(R.color.gray_color));
            btnApply.setEnabled(false);
        }
    }

    @OnClick({R.id.back_iv,R.id.btn_apply,R.id.ll_logistics_company})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_apply:
                if(type==0){
                    changeReshipLogistic();
                }else{
                    changeExchangeLogistic();
                }

                break;
            case R.id.ll_logistics_company:
                hideKeyboard(null);
                singleSelectPop.show(getWindow().getDecorView(), tvCompany);
                break;
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
}
