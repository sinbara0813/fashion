package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.util.TitleUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dreamtobe.kpswitch.util.KeyboardUtil;
import cn.dreamtobe.kpswitch.widget.KPSwitchPanelLinearLayout;

/**
 * 开具发票
 * Author: Blend
 * Date: 2016/12/06 18:14
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class CreateInvoiceActivity extends BaseActivity {

    @Bind(R.id.et_invoice)
    ClearEditText etInvoice;
    @Bind(R.id.web_view)
    WebView webView;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.btn_ok)
    Button btnOk;
    @Bind(R.id.togglebutton)
    Switch togglebutton;
    @Bind(R.id.tv_invoice)
    TextView tvInvoice;
    @Bind(R.id.panel_layout)
    KPSwitchPanelLinearLayout panelLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invoice);
        ButterKnife.bind(this);
        initTitle();
        progressBar.setVisibility(View.VISIBLE);
        webView.loadUrl("file:///android_asset/www/invoice.html");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                btnOk.setVisibility(View.VISIBLE);
            }
        });
        String content = getIntent().getStringExtra("content");
        if (!Util.isEmpty(content)) {
            togglebutton.setChecked(true);
            tvInvoice.setVisibility(View.GONE);
            etInvoice.setVisibility(View.VISIBLE);
            etInvoice.setText(content);
            etInvoice.setSelection(content.length());
        } else {
            togglebutton.setChecked(false);
            tvInvoice.setVisibility(View.VISIBLE);
            etInvoice.setVisibility(View.GONE);
        }
        etInvoice.addTextChangedListener(textWatcher);
        togglebutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tvInvoice.setVisibility(View.GONE);
                    etInvoice.setVisibility(View.VISIBLE);
                    etInvoice.addTextChangedListener(textWatcher);
                    if (!Util.isEmpty(etInvoice.getText().toString())) {
                        btnOk.setEnabled(true);
                    } else {
                        btnOk.setEnabled(false);
                    }
                } else {
                    hideKeyboard(null);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tvInvoice.setVisibility(View.VISIBLE);
                            etInvoice.setVisibility(View.GONE);
                            etInvoice.removeTextChangedListener(textWatcher);
                            btnOk.setEnabled(true);
                        }
                    }, 200);
                }
            }
        });
        KeyboardUtil.attach(this, panelLayout, new KeyboardUtil.OnKeyboardShowingListener() {
            @Override
            public void onKeyboardShowing(boolean isShowing) {
                if (isShowing) {
                    btnOk.setVisibility(View.GONE);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btnOk.setVisibility(View.VISIBLE);
                        }
                    }, 200);
                }
            }
        });
    }

    private void initTitle() {
        TitleUtil.setBack(this);
        TitleUtil.setTitle(this, R.string.label_create_invoice);
    }

    @OnClick(R.id.btn_ok)
    void clickOk() {
        Intent intent = getIntent();
        if (togglebutton.isChecked()) {
            String invoiceTopContent = etInvoice.getText().toString().trim();
            intent.putExtra("invoice", invoiceTopContent);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    private TextWatcher textWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            boolean invoiceTopTyped = etInvoice.getText().toString().trim().length() > 0;
            if (invoiceTopTyped) {
                btnOk.setEnabled(true);
            } else {
                btnOk.setEnabled(false);
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
