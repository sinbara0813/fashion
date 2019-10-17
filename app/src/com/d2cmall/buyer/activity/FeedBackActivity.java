package com.d2cmall.buyer.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.FeedBackApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedBackActivity extends BaseActivity {
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
    @Bind(R.id.tv_your_name)
    ClearEditText tvYourName;
    @Bind(R.id.line_layout)
    View lineLayout;
    @Bind(R.id.tv_your_phone)
    ClearEditText tvYourPhone;
    @Bind(R.id.tv_your_email)
    ClearEditText tvYourEmail;
    @Bind(R.id.tv_content)
    EditText tvContent;
    @Bind(R.id.btn_commit)
    Button btnCommit;
    private Dialog loadingDialog;
    //目前登录成功没返回nationCode，暂时不做手机号码的校验，那么邮箱也不做校验

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
        initTitle();
        loadingDialog = DialogUtil.createLoadingDialog(this);
    }

    private void initTitle() {
        nameTv.setText("意见反馈");
        TextWatcher textWatcher = new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(tvContent.getText().toString().trim())) {
                    btnCommit.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
                    btnCommit.setEnabled(false);
                }else{
                    btnCommit.setBackgroundColor(Color.parseColor("#DE232427"));
                    btnCommit.setEnabled(true);
                }
            }
        };
        tvContent.addTextChangedListener(textWatcher);
    }

    public void onOkButtonClick() {
        super.onOkButtonClick();
        hideKeyboard(null);
        String yourName = tvYourName.getText().toString().trim();
        String yourPhone = tvYourPhone.getText().toString().trim();
        String yourEmail = tvYourEmail.getText().toString().trim();
        String yourContent = tvContent.getText().toString().trim();
        if (Util.isEmpty(yourName)) {
            Util.showToast(this, R.string.hint_feed_back_name);
            return;
        }
        if (Util.isEmpty(yourPhone)) {
            Util.showToast(this, R.string.hint_feed_back_phone);
            return;
        }
        if (Util.isEmpty(yourContent)) {
            Util.showToast(this, R.string.msg_feed_back_content);
            return;
        }
        if (yourName.length() < 2 || yourName.length() > 10) {
            Util.showToast(this, R.string.msg_name_error);
            return;
        }
        if (yourPhone.length() < 5 || yourPhone.length() > 20) {
            Util.showToast(this, R.string.msg_phone_error);
            return;
        }
        if(!Util.isEmpty(yourEmail)){
            if (yourEmail.length() < 5 || yourEmail.length() > 30) {
                Util.showToast(this, R.string.msg_mail_error);
                return;
            }
        }
        if (yourContent.length() > 100) {
            Util.showToast(this, R.string.msg_feedback_content_error);
            return;
        }
        FeedBackApi api = new FeedBackApi();
        api.setMemberName(yourName);
        api.setMobile(yourPhone);
        api.setEmail(yourEmail);
        api.setContent(yourContent);
        loadingDialog.show();
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                loadingDialog.dismiss();
                Util.showToast(FeedBackActivity.this, R.string.msg_feed_back_ok);
                FeedBackActivity.super.onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Util.showToast(FeedBackActivity.this, Util.checkErrorType(error));
            }
        });
    }

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

    @OnClick({R.id.back_iv, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_commit:
                onOkButtonClick();
                break;
        }
    }
}
