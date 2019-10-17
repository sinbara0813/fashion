package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.MyPacketBean;
import com.d2cmall.buyer.bean.RelevanceAccountBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/9/4.
 * 账户安全页面，可以修改登录密码以及支付密码还有修改绑定手机号
 */

public class AccountSafeActivity extends BaseActivity {
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.login_password_layout)
    RelativeLayout loginPasswordLayout;
    @Bind(R.id.pay_password_layout)
    RelativeLayout payPasswordLayout;
    @Bind(R.id.line_layout)
    View lineLayout;
    @Bind(R.id.bind_phone_layout)
    LinearLayout bindPhoneLayout;
    @Bind(R.id.content_layout)
    LinearLayout contentLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.tag)
    View tag;
    @Bind(R.id.bind_phone_text)
    TextView bindPhoneText;
    @Bind(R.id.relevance_layout)
    LinearLayout relevanceLayout;
    @Bind(R.id.red_point_1)
    TextView redPoint1;
    @Bind(R.id.red_point_2)
    TextView redPoint2;
    private String bindedAccount;
    private UserBean.DataEntity.MemberEntity user;
    private MyPacketBean.DataEntity.AccountEntity accountEntity;
    private RelevanceAccountBean relevanceAccountBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe_layout);
        ButterKnife.bind(this);
        nameTv.setText("账户安全");
        user = Session.getInstance().getUserFromFile(this);
        if (user.getLoginCode() != null) {
            bindPhoneText.setText(Util.hidePhoneNum(user.getLoginCode()));
        }
        loadRelevance();
        if (user.isD2c()) {
            progressBar.setVisibility(View.VISIBLE);
            contentLayout.setVisibility(View.VISIBLE);
            loginPasswordLayout.setVisibility(View.VISIBLE);
            requestWalletInfoTask();
        } else {
            contentLayout.setVisibility(View.VISIBLE);
            loginPasswordLayout.setVisibility(View.GONE);
            payPasswordLayout.setVisibility(View.GONE);
            bindPhoneLayout.setVisibility(View.GONE);
            Util.showToast(this, "请重新登录绑定手机号");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        user = Session.getInstance().getUserFromFile(this);
        if (user!=null){
            if (user.isDanger()==1){
                redPoint1.setVisibility(View.VISIBLE);
            }
            if (user.isPayDanger()==21){
                redPoint2.setVisibility(View.VISIBLE);
            }
        }
    }

    private void requestWalletInfoTask() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.MY_WALLET_INFO_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MyPacketBean>() {
            @Override
            public void onResponse(MyPacketBean myPacketBean) {
                accountEntity = myPacketBean.getData().getAccount();
                progressBar.setVisibility(View.GONE);
                bindedAccount = accountEntity.getMobile();
                if (accountEntity.isSetPassword()) {
                    payPasswordLayout.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(AccountSafeActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void loadRelevance() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.RELEVANCE_ACCOUNT_LIST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RelevanceAccountBean>() {
            @Override
            public void onResponse(RelevanceAccountBean response) {
                relevanceAccountBean = response;
                if (response.getData().getChildren() != null && response.getData().getChildren().size() > 0) {
                    relevanceLayout.setVisibility(View.VISIBLE);
                } else {
                    relevanceLayout.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @OnClick({R.id.relevance_layout, R.id.back_iv, R.id.login_password_layout, R.id.pay_password_layout, R.id.bind_phone_layout})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.relevance_layout:
                intent = new Intent(this, RelevanceActivity.class);
                startActivityForResult(intent, 1995);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.login_password_layout:
                intent = new Intent(this, ChangeLoginActivity.class);
                startActivityForResult(intent, 100);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
            case R.id.pay_password_layout:
                intent = new Intent(this, SetPayPasswordActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
            case R.id.bind_phone_layout:
                intent = new Intent(this, ChangePhoneActivity.class);
                startActivityForResult(intent, 1123);
                overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1123 && resultCode == RESULT_OK) {
            UserBean.DataEntity.MemberEntity userInfo = Session.getInstance().getUserFromFile(this);
            bindPhoneText.setText(Util.hidePhoneNum(userInfo.getLoginCode()));
        } else if (requestCode == 1995 && resultCode == RESULT_OK) {
            if (data != null) {
                boolean isAll = data.getBooleanExtra("isAll", false);
                if (isAll) {
                    relevanceLayout.setVisibility(View.GONE);
                } else {
                    relevanceLayout.setVisibility(View.VISIBLE);
                }
            }
        } else if (requestCode == 100 && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }
}
