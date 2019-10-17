package com.d2cmall.buyer.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.DeleteAccountApi;
import com.d2cmall.buyer.api.SendAudioApi;
import com.d2cmall.buyer.api.SendCodeApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/11/21.
 */

public class PhoneVerificationActivity extends BaseActivity {

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
    @Bind(R.id.tv_number)
    TextView tvNumber;
    @Bind(R.id.et_bind_security_code)
    ClearEditText etBindSecurityCode;
    @Bind(R.id.register_security_tv)
    TextView registerSecurityTv;
    @Bind(R.id.register_security_rl)
    RelativeLayout registerSecurityRl;
    @Bind(R.id.btn_next)
    Button btnNext;
    @Bind(R.id.tv_get_voice)
    TextView tvGetVoice;
    @Bind(R.id.iv_service)
    ImageView ivService;
    private boolean isActivityFinish = false;
    private TimeDown timeDown;
    private UserBean.DataEntity.MemberEntity user;
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_layout);
        ButterKnife.bind(this);
        flag=getIntent().getIntExtra("flag",0);
        btnNext.setText(flag==0?"下一步":"确定");
        nameTv.setText("身份验证");
        user = Session.getInstance().getUserFromFile(this);
        tvNumber.setText(String.format(getString(R.string.label_send_code_phone), Util.hidePhoneNum(user.getLoginCode())));
        etBindSecurityCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = etBindSecurityCode.getText().toString().trim();
                if (str.equals("")) {
                    btnNext.setEnabled(false);
                    btnNext.setBackgroundColor(Color.parseColor("#61DDDDDD"));
                } else {
                    btnNext.setEnabled(true);
                    btnNext.setBackgroundColor(Color.parseColor("#DE232427"));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        isActivityFinish = true;
        if (timeDown != null) {
            timeDown.cancel();
            timeDown = null;
        }
        super.onDestroy();
    }

    public class TimeDown extends CountDownTimer {

        public TextView tv;

        public TimeDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            if (tv != null) {
                tv.setEnabled(true);
                if (tv == tvGetVoice) {
                    tv.setText(R.string.label_try_code);
                } else {
                    tv.setText(R.string.label_get_code);
                }
            }
            if (!tvGetVoice.isEnabled()) {
                tvGetVoice.setEnabled(true);
                tvGetVoice.setTextColor(Color.parseColor("#FFFDC33E"));
                Drawable nav_dowm = getResources().getDrawable(R.mipmap.icon_login_voice);
                nav_dowm.setBounds(0, 0, nav_dowm.getMinimumWidth(), nav_dowm.getMinimumHeight());
                tvGetVoice.setCompoundDrawables(nav_dowm, null, null, null);
                tvGetVoice.setText(R.string.label_try_code);
            }
            if (!registerSecurityTv.isEnabled()) {
                registerSecurityTv.setEnabled(true);
                registerSecurityTv.setText(R.string.label_get_code);
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int secUntilFinished = (int) (millisUntilFinished / 1000);
            if (!isActivityFinish) {
                if (tv != null) {
                    tv.setText(getString(R.string.label_reload_code,
                            secUntilFinished > 9 ? secUntilFinished
                                    : ("0" + secUntilFinished)));
                }
            }
        }
    }

    private void sendAudio(final TextView codeTv) {
        hideKeyboard(null);
        timeDown = new TimeDown(60 * 1000, 1000);
        timeDown.tv = codeTv;
        timeDown.start();
        codeTv.setEnabled(false);
        registerSecurityTv.setEnabled(false);
        SendAudioApi api = new SendAudioApi();
        api.setMobile(user.getLoginCode());
        api.setSource(user.getLoginCode());
        api.setType("MemberMobile");
        api.setNationCode(user.getNationCode());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                Util.showToast(PhoneVerificationActivity.this, R.string.msg_send_code_ok);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(PhoneVerificationActivity.this, Util.checkErrorType(error));
            }
        });
    }

    //发送验证码
    private void sendCode(final TextView codeTv) {
        hideKeyboard(null);
        timeDown = new TimeDown(60 * 1000, 1000);
        timeDown.tv = codeTv;
        timeDown.start();
        codeTv.setEnabled(false);
        tvGetVoice.setEnabled(false);
        tvGetVoice.setTextColor(Color.parseColor("#61000000"));
        Drawable nav_dowm = getResources().getDrawable(R.mipmap.icon_login_novoice);
        nav_dowm.setBounds(0, 0, nav_dowm.getMinimumWidth(), nav_dowm.getMinimumHeight());
        tvGetVoice.setCompoundDrawables(nav_dowm, null, null, null);
        SendCodeApi api = new SendCodeApi();
        api.setMobile(user.getLoginCode());
        api.setSource(user.getLoginCode());
        api.setType("MemberMobile");
        api.setNationCode(user.getNationCode());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                Util.showToast(PhoneVerificationActivity.this, R.string.msg_send_code_ok);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(PhoneVerificationActivity.this, Util.checkErrorType(error));
            }
        });
    }

    @OnClick({R.id.iv_service,R.id.back_iv, R.id.register_security_tv, R.id.btn_next, R.id.tv_get_voice})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_service:
                if (Util.loginChecked(this, 999)) {
                    String title = "线上客服";
                    String url = "http://www.d2cmall.com";
                    ConsultSource source = new ConsultSource(url, title, "手机号认证");
                    source.groupId = Constants.QIYU_AF_GROUP_ID;
                    source.robotFirst = true;
                    Unicorn.openServiceActivity(this, "D2C客服", source);

                    //合力亿捷
//                    Intent intent = new Intent(this,CustomServiceActivity.class);
//                    intent.putExtra("skillGroupId", Constants.HLYJ_BF_AF_GROUP_ID);
//                    startActivity(intent);
                }
                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.register_security_tv:
                sendCode(registerSecurityTv);
                break;
            case R.id.btn_next:
                if(flag==0){
                    Intent intent = new Intent(PhoneVerificationActivity.this, LastChangePhoneActivity.class);
                    startActivityForResult(intent, 1123);
                }else {
                    deleteAccount();
                }
                break;
            case R.id.tv_get_voice:
                new AlertDialog.Builder(this)
                        .setMessage(R.string.msg_voice_code)
                        .setPositiveButton("现在接听", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendAudio(tvGetVoice);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
        }
    }

    private void deleteAccount(){
        DeleteAccountApi api=new DeleteAccountApi();
        api.setCode(etBindSecurityCode.getText().toString().trim());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean response) {
                Session.getInstance().logout(PhoneVerificationActivity.this);
                Intent intent=new Intent(PhoneVerificationActivity.this,FinishDeleteAccountActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(PhoneVerificationActivity.this, Util.checkErrorType(error));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1123 && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }
}
