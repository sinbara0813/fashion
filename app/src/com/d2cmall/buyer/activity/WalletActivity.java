package com.d2cmall.buyer.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.MyPacketBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/9/6.
 * 钱包页面
 */

public class WalletActivity extends BaseActivity {
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.tv_total)
    TextView tvTotal;
    @Bind(R.id.tv_explain)
    TextView tvExplain;
    @Bind(R.id.tv_detail)
    TextView tvDetail;
    @Bind(R.id.pay_password_layout)
    LinearLayout payPasswordLayout;
    @Bind(R.id.online_pay_layout)
    LinearLayout onlinePayLayout;
    @Bind(R.id.line_layout)
    View lineLayout;
    @Bind(R.id.d2c_pay_layout)
    LinearLayout d2cPayLayout;
    @Bind(R.id.web_view)
    WebView webView;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private String bindedAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_layout);
        ButterKnife.bind(this);
        nameTv.setText("钱包");
        progressBar.setVisibility(View.VISIBLE);
        requestWalletInfoTask();
    }

    private void requestWalletInfoTask() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.MY_WALLET_INFO_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MyPacketBean>() {
            @Override
            public void onResponse(MyPacketBean myPacketBean) {
                if (myPacketBean.getData().getAccount().isSetPassword()) {
                    bindedAccount = myPacketBean.getData().getAccount().getMobile();
                    tvTotal.setText(getString(R.string.label_price,
                            Util.getNumberFormat(myPacketBean.getData().getAccount().getTotalAmount(), true, true)));
                    tvExplain.setText(getString(R.string.label_money1_remind,
                            Util.getNumberFormat(myPacketBean.getData().getAccount().getAvailableAmount(), true, true),
                            Util.getNumberFormat(myPacketBean.getData().getAccount().getFreezeAmount(), true, true)));
                    tvExplain.setVisibility(View.VISIBLE);
                    loadWeb();
                } else {
                    Intent intent = new Intent(WalletActivity.this, SetPayPasswordActivity.class);
                    intent.putExtra("type", 4);
                    startActivityForResult(intent, 100);
                    overridePendingTransition(0, 0);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(WalletActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void loadWeb() {
        WebSettings setting = webView.getSettings();
        setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);
        setting.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String mUrl) {
                return Util.urlAction(WalletActivity.this, mUrl);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                super.onPageFinished(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String url) {
                webView.stopLoading();
                webView.setVisibility(View.GONE);
            }

        });
        String url = Constants.SHARE_URL + "/page/wallet";
        String token = "";
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
        if (user != null) {
            token = user.getUserToken();
        }
        webView.loadUrl(String.format(Constants.INVOKE_URL, token, Util.toURLEncode(url)));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case Constants.RequestCode.DEPOSIT:
                    requestWalletInfoTask();
                    break;
                case 100:
                    requestWalletInfoTask();
                    break;
            }
        } else {
            if (requestCode == 100) {
                finish();
            }
        }
    }

    @OnClick({R.id.back_iv, R.id.tv_detail, R.id.pay_password_layout, R.id.online_pay_layout, R.id.d2c_pay_layout})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.tv_detail:
                intent = new Intent(this, DepositDetailActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
            case R.id.pay_password_layout:
                intent = new Intent(this, SetPayPasswordActivity.class);
                intent.putExtra("type", 0);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
            case R.id.online_pay_layout:
                intent = new Intent(this, RechargeActivity.class);
                startActivityForResult(intent, Constants.RequestCode.DEPOSIT);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
            case R.id.d2c_pay_layout:
                intent = new Intent(this, CardDepositActivity.class);
                startActivityForResult(intent, Constants.RequestCode.DEPOSIT);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
        }
    }
}
