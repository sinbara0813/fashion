package com.d2cmall.buyer.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
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
import com.d2cmall.buyer.api.PointProductExchangeApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.DCionProductBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ConversionSuccessPop;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/8/6.
 * Description : DCoinProductDetaiActivity
 */

public class DCoinProductDetaiActivity extends BaseActivity {
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
    @Bind(R.id.iv_product)
    ImageView ivProduct;
    @Bind(R.id.tv_product_name)
    TextView tvProductName;
    @Bind(R.id.tv_product_price)
    TextView tvProductPrice;
    @Bind(R.id.web_view)
    WebView webView;
    @Bind(R.id.btn_buy)
    Button btnBuy;
    @Bind(R.id.scroll_view)
    ScrollView scrollView;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.minus)
    ImageView minus;
    @Bind(R.id.et_num)
    EditText etNum;
    @Bind(R.id.add)
    ImageView add;
    @Bind(R.id.num_ll)
    LinearLayout numLl;
    @Bind(R.id.tv_no_store)
    TextView tvNoStore;
    private int type = 0;//0内部,1外部合作
    private int id;
    private UserBean.DataEntity.MemberEntity user;
    private ConversionSuccessPop conversionSuccessPop;
    private DCionProductBean mDCionProductBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_dcoin_product_detail);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        id = getIntent().getIntExtra("id", -1);
        user = Session.getInstance().getUserFromFile(this);
        if (id < 0) {
            return;
        }
        int width = ScreenUtil.getDisplayWidth();
        ViewGroup.LayoutParams layoutParams = ivProduct.getLayoutParams();
        layoutParams.height = width;
        ivProduct.setLayoutParams(layoutParams);
        nameTv.setLines(1);
        nameTv.setEllipsize(TextUtils.TruncateAt.END);
        loadProductInfo();
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.isEmpty(etNum.getText().toString().trim())) {
                    return;
                }
                if(etNum.getText().toString().trim().startsWith("0") && etNum.getText().toString().trim().length()>1){
                    etNum.setText(etNum.getText().toString().trim().substring(1,etNum.getText().toString().trim().length()));
                }
                if (user != null && mDCionProductBean != null && Integer.valueOf(etNum.getText().toString().trim()) * mDCionProductBean.getData().getPointProduct().getPoint() > user.getIntegration() && Integer.valueOf(etNum.getText().toString().trim())>1) {
                    if(user.getIntegration() / mDCionProductBean.getData().getPointProduct().getPoint()>0){
                        etNum.setText(user.getIntegration() / mDCionProductBean.getData().getPointProduct().getPoint() + "");
                    }else{
                        etNum.setText("1");
                    }
                    Util.showToast(DCoinProductDetaiActivity.this, "兑换所需D币数大于当前D币数");
                }
                if (mDCionProductBean!=null && Integer.valueOf(etNum.getText().toString().trim())  > mDCionProductBean.getData().getPointProduct().getCount() && mDCionProductBean.getData().getPointProduct().getCount()>0) {
                    etNum.setText(mDCionProductBean.getData().getPointProduct().getCount() + "");
                    Util.showToast(DCoinProductDetaiActivity.this, "库存不足");
                }
                if (Integer.valueOf(etNum.getText().toString().trim()) < 1) {
                    etNum.setText("1");
                }
                if (mDCionProductBean != null) {
                    setRedText(mDCionProductBean);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        etNum.addTextChangedListener(textWatcher);

    }

    private void loadWeb(String description) {
        if (progressBar == null) {
            return;
        }
        WebSettings setting = webView.getSettings();
        setting.setUseWideViewPort(true);
        setting.setLoadWithOverviewMode(true);
        setting.setAllowFileAccess(true);
        setting.setDefaultFontSize(ScreenUtil.dip2px(14));
        setting.setJavaScriptEnabled(true);
        webView.setVisibility(View.VISIBLE);

        webView.loadDataWithBaseURL(null, description, "text/html", "utf-8", null);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return Util.urlAction(DCoinProductDetaiActivity.this, url);
            }
        });
    }

    private void loadProductInfo() {
        progressBar.setVisibility(View.VISIBLE);
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(String.format(Constants.DCION_PROCUCT_URL, id)));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<DCionProductBean>() {

            @Override
            public void onResponse(DCionProductBean dCionProductBean) {
                if (progressBar == null) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                btnBuy.setVisibility(View.VISIBLE);
                mDCionProductBean = dCionProductBean;
                if ("RED".equals(dCionProductBean.getData().getPointProduct().getType())) {
                    setRedText(dCionProductBean);
                    numLl.setVisibility(View.VISIBLE);
                } else {
                    numLl.setVisibility(View.GONE);
                    tvProductPrice.setText(getString(R.string.label_dcion_price, dCionProductBean.getData().getPointProduct().getPoint()));
                }
                nameTv.setText(dCionProductBean.getData().getPointProduct().getName());
                tvProductName.setText(dCionProductBean.getData().getPointProduct().getName());
                UniversalImageLoader.displayImage(DCoinProductDetaiActivity.this, dCionProductBean.getData().getPointProduct().getDetailPic(), ivProduct, R.mipmap.ic_logo_empty2);

                if (!Util.isEmpty(dCionProductBean.getData().getPointProduct().getDescription())) {
                    loadWeb(dCionProductBean.getData().getPointProduct().getDescription());
                }
                if (mDCionProductBean.getData().getPointProduct().getCount() <= 0) {
                    btnBuy.setEnabled(false);
                    btnBuy.setText("已售罄");
                    tvNoStore.setVisibility(View.VISIBLE);
                    etNum.setEnabled(false);
                    add.setEnabled(false);
                    minus.setEnabled(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (progressBar == null) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                setEmptyView(Constants.NET_DISCONNECT);
                Util.showToast(DCoinProductDetaiActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void setRedText(DCionProductBean dCionProductBean) {
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#C39B53"));
        tvProductPrice.setTextColor(getResources().getColor(R.color.color_black85));
        Integer num = Integer.valueOf(etNum.getText().toString().trim());
        String str = getString(R.string.label_point_exchange, dCionProductBean.getData().getPointProduct().getPoint() * num, Util.getNumberFormat(dCionProductBean.getData().getPointProduct().getAmount() * num));
        int length = str.length();
        int dIndex = str.indexOf("D");
        int yuanIndex = str.indexOf("元");
        int huanIndex = str.indexOf("换");
        SpannableString textSpan = new SpannableString(str);
        textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(16)), 0, dIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textSpan.setSpan(colorSpan, 0, dIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(12)), dIndex, yuanIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(16)), huanIndex + 1, yuanIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        colorSpan = new ForegroundColorSpan(Color.parseColor("#C39B53"));
        textSpan.setSpan(colorSpan, huanIndex + 1, yuanIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textSpan.setSpan(new AbsoluteSizeSpan(ScreenUtil.dip2px(12)), yuanIndex, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        tvProductPrice.setText(textSpan);
    }

    @OnClick({R.id.back_iv, R.id.btn_buy, R.id.btn_reload, R.id.minus, R.id.add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_reload:
                loadProductInfo();
                break;
            case R.id.btn_buy:
                if (Util.loginChecked(this, 123)) {
                    requestBuy();
                }
                break;
            case R.id.minus:
                if (Util.isEmpty(etNum.getText().toString().trim())) {
                    etNum.setText("1");
                } else if (Integer.valueOf(etNum.getText().toString().trim()) <= 1) {
                    etNum.setText("1");
                } else {
                    etNum.setText(Integer.valueOf(etNum.getText().toString().trim()) - 1 + "");
                }
                break;
            case R.id.add:
                if (Util.isEmpty(etNum.getText().toString().trim())) {
                    etNum.setText("1");
                } else {
                    etNum.setText(Integer.valueOf(etNum.getText().toString().trim()) + 1 + "");
                }
                break;
        }
    }

    private void requestBuy() {
        btnBuy.setEnabled(false);
        if (Util.isEmpty(etNum.getText().toString().trim()) && mDCionProductBean != null && "RED".equals(mDCionProductBean.getData().getPointProduct().getType())) {
            Util.showToast(this, "请填写兑换数量");
            return;
        }
        PointProductExchangeApi api = new PointProductExchangeApi();
        api.setInterPath(String.format(String.format(Constants.DCION_PROCUCT_EXCHANGE_URL, id)));
        api.setQuantity(Integer.valueOf(etNum.getText().toString().trim()));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<DCionProductBean>() {
            @Override
            public void onResponse(DCionProductBean dCionProductBean) {
                btnBuy.setEnabled(true);
                if (dCionProductBean != null) {
                    if (conversionSuccessPop == null) {
                        conversionSuccessPop = new ConversionSuccessPop(DCoinProductDetaiActivity.this, dCionProductBean);
                    }
                    conversionSuccessPop.show(getWindow().getDecorView());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btnBuy.setEnabled(true);
                Util.showToast(DCoinProductDetaiActivity.this, Util.checkErrorType(error));
            }
        });

    }

    private void setEmptyView(int type) {
        scrollView.setVisibility(View.GONE);
        btnBuy.setVisibility(View.GONE);
        if (type == Constants.NO_DATA) {
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.icon_empty_default);
            btnReload.setVisibility(View.VISIBLE);
            btnReload.setText("暂无数据");
            btnReload.setBackgroundColor(getResources().getColor(R.color.transparent));
        } else {
            btnReload.setText("重新加载");
            btnReload.setBackgroundResource(R.drawable.sp_line);
            btnReload.setVisibility(View.VISIBLE);
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.ic_no_net);
        }
    }
}
