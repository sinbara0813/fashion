package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.RedPacketBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DateUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/10/24.
 * 我的红包页面
 */

public class RedPacketActivity extends BaseActivity {

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
    @Bind(R.id.tv_explain)
    TextView tvExplain;
    @Bind(R.id.tv_total)
    TextView tvTotal;
    @Bind(R.id.income_detail)
    RelativeLayout incomeDetail;
    @Bind(R.id.ll_detail)
    LinearLayout llDetail;
    @Bind(R.id.web_view)
    WebView webView;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.tv_red_date)
    TextView tvRedDate;
    private String bindedAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_packet);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        progressBar.setVisibility(View.VISIBLE);
        nameTv.setText("我的红包");
        requestWalletInfoTask();
    }

    private void requestWalletInfoTask() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.MY_RED_PACKET);
        api.setMethod(BaseApi.Method.GET);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RedPacketBean>() {
            @Override
            public void onResponse(RedPacketBean myPacketBean) {
                bindedAccount = myPacketBean.getData().getAccount().getMobile();
                if (DateUtil.checkRedTime(myPacketBean.getData().getAccount().getRedDate())) {
                    tvTotal.setText(getString(R.string.label_price,
                            Util.getNumberFormat(myPacketBean.getData().getAccount().getRedAmount(), true, true)));
                } else {
                    tvTotal.setText(getString(R.string.label_price,
                            Util.getNumberFormat(0, true, true)));
                }
                if (!Util.isEmpty(myPacketBean.getData().getAccount().getRedDate())){
                    tvRedDate.setText("到期时间："+myPacketBean.getData().getAccount().getRedDate());
                }
                addDetailView(myPacketBean.getData().getRedPacketsItems().getList());
                loadWeb();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(RedPacketActivity.this, Util.checkErrorType(error));
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
                return Util.urlAction(RedPacketActivity.this, mUrl);
            }

            @Override
            public void onPageFinished(WebView view, String url) {

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
        String url = Constants.SHARE_URL + "/page/RedEnvelopeRules";
        String token = "";
        UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
        if (user != null) {
            token = user.getUserToken();
        }
        webView.loadUrl(String.format(Constants.INVOKE_URL, token, Util.toURLEncode(url)));
    }

    private void addDetailView(List<RedPacketBean.DataBean.RedPacketsItemsBean.ListBean> list) {
        if (list != null && list.size() > 0) {
            llDetail.removeAllViews();
            for (int i = 0; i < list.size(); i++) {
                if (i > 2) {
                    return;
                }
                View view = LayoutInflater.from(this).inflate(R.layout.layout_income_detail, llDetail, false);
                TextView nameText = (TextView) view.findViewById(R.id.tv_name_detail);
                nameText.setText(list.get(i).getBusinessName());
                TextView time = (TextView) view.findViewById(R.id.tv_time);
                time.setText(list.get(i).getCreateDate());
                TextView money = (TextView) view.findViewById(R.id.tv_money);
                if (list.get(i).getAmount() > 0) {
                    money.setText("+" + String.valueOf(list.get(i).getAmount()));
                } else {
                    money.setText(String.valueOf(list.get(i).getAmount()));
                }
                llDetail.addView(view);
            }
        }
        progressBar.setVisibility(View.GONE);
    }

    @OnClick({R.id.back_iv, R.id.income_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.income_detail:
                Intent intent = new Intent(this, RedPacketListActivity.class);
                startActivity(intent);
                break;
        }
    }
}
