package com.d2cmall.buyer.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
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
import com.d2cmall.buyer.api.PostReshipLogisticApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ReshipsBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.TitleUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;
import com.d2cmall.buyer.widget.SingleSelectPop;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 退货退款填写物流信息
 * Author: Blend
 * Date: 2016/06/29 10:22
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ReshipLogisticActivity extends BaseActivity implements SingleSelectPop.CallBack {


    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.first_layout)
    LinearLayout firstLayout;
    @Bind(R.id.second_layout)
    LinearLayout secondLayout;
    @Bind(R.id.third_layout)
    LinearLayout thirdLayout;
    @Bind(R.id.forth_layout)
    LinearLayout forthLayout;
    @Bind(R.id.tv_alipay_label)
    TextView tvAlipayLabel;
    @Bind(R.id.et_alipay_account)
    ClearEditText etAlipayAccount;
    @Bind(R.id.et_alipay_name)
    ClearEditText etAlipayName;
    @Bind(R.id.top_layout)
    LinearLayout topLayout;
    @Bind(R.id.tv_logistic_company)
    TextView tvLogisticCompany;
    @Bind(R.id.logistic_company_layout)
    LinearLayout logisticCompanyLayout;
    @Bind(R.id.line_layout)
    View lineLayout;
    @Bind(R.id.et_logistic_no)
    ClearEditText etLogisticNo;
    @Bind(R.id.btn_apply)
    Button btnApply;

    private SingleSelectPop singleSelectPop;
    private Dialog loadingDialog;
    private String logisticCompany;
    private ReshipsBean.DataBean.ReshipsDataBean.ListBean listEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reship_logistic);
        ButterKnife.bind(this);
        listEntity = (ReshipsBean.DataBean.ReshipsDataBean.ListBean) getIntent().getSerializableExtra("item");
        nameTv.setText("退款退货申请");
        String[] titles = getResources().getStringArray(R.array.label_logistic_titles);
        singleSelectPop = new SingleSelectPop(this, titles);
        singleSelectPop.setCallBack(this);
        etLogisticNo.addTextChangedListener(textWatcher);
        loadingDialog = DialogUtil.createLoadingDialog(this);
        if (listEntity.getOrderPayType() != 3) {//不是货到付款
            topLayout.setVisibility(View.GONE);
        } else {
            topLayout.setVisibility(View.VISIBLE);
            tvAlipayLabel.setText(Html.fromHtml(getString(R.string.label_reship_label2)));
            etAlipayAccount.setText(listEntity.getBackAccountSn());
            etAlipayName.setText(listEntity.getBackAccountName());
            if (!Util.isEmpty(listEntity.getBackAccountName())) {
                etAlipayName.requestFocus();
                etAlipayName.setSelection(listEntity.getBackAccountName().length());
            }
        }
    }

    private void initTitle() {
        TitleUtil.setBack(this);
        TitleUtil.setTitle(this, R.string.label_reship_detail);
    }

    @OnClick(R.id.logistic_company_layout)
    void onClick() {
        hideKeyboard(null);
        singleSelectPop.show(getWindow().getDecorView(), tvLogisticCompany);
    }

    @OnClick(R.id.btn_apply)
    void onApply() {
        hideKeyboard(null);
        String deliverySn = etLogisticNo.getText().toString().trim();
        String alipayAccount = etAlipayAccount.getText().toString().trim();
        String alipayName = etAlipayName.getText().toString().trim();
        PostReshipLogisticApi api = new PostReshipLogisticApi();
        api.setDeliveryCorpName(logisticCompany);
        api.setDeliverySn(deliverySn);
        api.setMemo("");
        api.setReshipId(listEntity.getId());
        if (listEntity.getOrderPayType() == 3) {
            api.setBackAccountSn(alipayAccount);
            api.setBackAccountName(alipayName);
        }
        loadingDialog.show();
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                loadingDialog.dismiss();
                Util.showToast(ReshipLogisticActivity.this, baseBean.getMsg());
                Intent intent = getIntent();
                setResult(RESULT_OK, intent);
                ReshipLogisticActivity.super.onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Util.showToast(ReshipLogisticActivity.this, Util.checkErrorType(error));
            }
        });
    }

    @Override
    public void callback(View trigger, int index, String value) {
        tvLogisticCompany.setText(value);
        buttonEnableOrNot();
        logisticCompany = value;
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

    private void buttonEnableOrNot() {
        boolean logisticNoTyped = etLogisticNo.getText().toString().trim().length() > 0;
        boolean logisticCompanyTyped = tvLogisticCompany.getText().length() > 0;
        boolean alipayAccountTyped = etAlipayAccount.getText().toString().trim().length() > 0;
        boolean alipayNameTyped = etAlipayName.getText().toString().trim().length() > 0;
        if (listEntity.getOrderPayType() == 3) {//货到付款
            if (logisticNoTyped && logisticCompanyTyped && alipayAccountTyped && alipayNameTyped) {
                btnApply.setEnabled(true);
            } else {
                btnApply.setEnabled(false);
            }
        } else {
            if (logisticNoTyped && logisticCompanyTyped) {
                btnApply.setEnabled(true);
            } else {
                btnApply.setEnabled(false);
            }
        }
    }
}
