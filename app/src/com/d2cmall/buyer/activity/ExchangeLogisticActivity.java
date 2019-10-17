package com.d2cmall.buyer.activity;

import android.app.Dialog;
import android.content.Intent;
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
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.ExchangesBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;
import com.d2cmall.buyer.widget.SingleSelectPop;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 换货填写物流信息
 * Author: Blend
 * Date: 2016/06/29 10:22
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class ExchangeLogisticActivity extends BaseActivity implements SingleSelectPop.CallBack {

    @Bind(R.id.tv_logistic_company)
    TextView tvLogisticCompany;
    @Bind(R.id.et_logistic_no)
    ClearEditText etLogisticNo;
    @Bind(R.id.btn_apply)
    Button btnApply;
    @Bind(R.id.btn_back)
    ImageView btnBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.btn_right)
    TextView btnRight;
    @Bind(R.id.tag)
    View tag;
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
    @Bind(R.id.logistic_company_layout)
    LinearLayout logisticCompanyLayout;
    @Bind(R.id.line_layout)
    View lineLayout;
    private SingleSelectPop singleSelectPop;
    private Dialog loadingDialog;
    private String logisticCompany;
    private ExchangesBean.DataBean.ExchangeListBean.ListBean listEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_logistic);
        ButterKnife.bind(this);
        listEntity = (ExchangesBean.DataBean.ExchangeListBean.ListBean) getIntent().getSerializableExtra("item");
        initTitle();
        String[] titles = getResources().getStringArray(R.array.label_logistic_titles);
        singleSelectPop = new SingleSelectPop(this, titles);
        singleSelectPop.setCallBack(this);
        etLogisticNo.addTextChangedListener(textWatcher);
        loadingDialog = DialogUtil.createLoadingDialog(this);
    }

    private void initTitle() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        PostExchangeLogisticApi api = new PostExchangeLogisticApi();
        api.setDeliveryCorpName(logisticCompany);
        api.setDeliverySn(deliverySn);
        api.setExchangeId(listEntity.getId());
        loadingDialog.show();
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                loadingDialog.dismiss();
                Util.showToast(ExchangeLogisticActivity.this, baseBean.getMsg());
                Intent intent = getIntent();
                setResult(RESULT_OK, intent);
                ExchangeLogisticActivity.super.onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Util.showToast(ExchangeLogisticActivity.this, Util.checkErrorType(error));
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
        if (logisticNoTyped && logisticCompanyTyped) {
            btnApply.setEnabled(true);
        } else {
            btnApply.setEnabled(false);
        }
    }

}
