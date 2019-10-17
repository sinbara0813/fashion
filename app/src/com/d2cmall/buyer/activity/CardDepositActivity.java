package com.d2cmall.buyer.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.DepositApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * D2C卡充值
 * Author: Blend
 * Date: 16/8/17 9:53
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class CardDepositActivity extends BaseActivity {
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
    @Bind(R.id.et_card_no)
    ClearEditText etCardNo;
    @Bind(R.id.et_card_password)
    ClearEditText etCardPassword;
    @Bind(R.id.btn_deposit)
    Button btnDeposit;
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_deposit);
        ButterKnife.bind(this);
        nameTv.setText(R.string.label_d2c_pay);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loadingDialog = DialogUtil.createLoadingDialog(this);
        etCardNo.addTextChangedListener(textWatcher);
        etCardPassword.addTextChangedListener(textWatcher);
    }

    @OnClick(R.id.btn_deposit)
    public void onClick() {
        hideKeyboard(null);
        String cardNo = etCardNo.getText().toString().trim();
        String cardPassword = etCardPassword.getText().toString().trim();
        if (Util.isEmpty(cardNo)) {
            Util.showToast(this, R.string.hint_d2c_card_no);
            return;
        }
        if (Util.isEmpty(cardPassword)) {
            Util.showToast(this, R.string.hint_d2c_card_password);
            return;
        }
        DepositApi api = new DepositApi();
        api.setSn(cardNo);
        api.setPassword(cardPassword);
        api.setPayChannel("CARDPAY");
        loadingDialog.show();
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                loadingDialog.dismiss();
                Util.showToast(CardDepositActivity.this, R.string.msg_deposit_ok);
                setResult(RESULT_OK);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Util.showToast(CardDepositActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {

            boolean cardNoTyped = etCardNo.getText().toString().trim().length() > 0;
            boolean cardPasswordTyped = etCardPassword.getText().toString().trim().length() > 0;
            if (cardNoTyped && cardPasswordTyped) {
                btnDeposit.setEnabled(true);
            } else {
                btnDeposit.setEnabled(false);
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };

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

}
