package com.d2cmall.buyer.activity;

import android.app.Dialog;
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
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.SendAudioApi;
import com.d2cmall.buyer.api.SendCodeApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.ClearEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/9/4.
 * 修改支付密码获取验证码页面
 */

public class SendCodeChangePayPsdActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.edit_man_name)
    ClearEditText editManName;
    @Bind(R.id.btn_code)
    TextView btnCode;
    @Bind(R.id.tv_hint)
    TextView tvHint;
    @Bind(R.id.tag)
    View tag;
    @Bind(R.id.tv_get_voice)
    TextView tvGetVoice;
    @Bind(R.id.btn_next)
    Button btnNext;
    private TimeDown timeDown;
    private Dialog loadingDialog;
    private UserBean.DataEntity.MemberEntity user;
    private int type;
    private String bindedAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pay);
        ButterKnife.bind(this);
        nameTv.setText("修改支付密码");
        type = getIntent().getIntExtra("type", 1);
        if (type == 4) {
            nameTv.setText("设置支付密码");
        }
        user = Session.getInstance().getUserFromFile(this);
        bindedAccount = user.getLoginCode();
        tvHint.setText(String.format(getString(R.string.label_send_code_phone), Util.hidePhoneNum(bindedAccount)));
        loadingDialog = DialogUtil.createLoadingDialog(this);
        editManName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!Util.isEmpty(editManName.getText().toString())){
                    btnNext.setEnabled(true);
                    btnNext.setBackgroundColor(getResources().getColor(R.color.color_black));
                }else {
                    btnNext.setEnabled(false);
                    btnNext.setBackgroundColor(Color.parseColor("#FFDDDDDD"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
            if (!btnCode.isEnabled()) {
                btnCode.setEnabled(true);
                btnCode.setText(R.string.label_get_code);
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int secUntilFinished = (int) (millisUntilFinished / 1000);
            tv.setText(getString(R.string.label_reload_code,
                    secUntilFinished > 9 ? secUntilFinished : ("0" + secUntilFinished)));
        }
    }

    @OnClick({R.id.back_iv, R.id.title_right, R.id.btn_code, R.id.tv_get_voice,R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_next:
                nextStep();
                break;
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_code:
                sendCode(btnCode);
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

    private void nextStep() {
        hideKeyboard(null);
        String code = editManName.getText().toString().trim();
        if (Util.isEmpty(code)) {
            Util.showToast(SendCodeChangePayPsdActivity.this, R.string.msg_code_empty);
            return;
        }
        Intent intent = new Intent(SendCodeChangePayPsdActivity.this, SetPayPasswordActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("code",code);
        intent.putExtra("bindedAccount", bindedAccount);
        startActivityForResult(intent, 100);
        overridePendingTransition(R.anim.slide_in_right, R.anim.activity_anim_default);
        if (type == 1) {
            finish();
        }
//        CheckCodeApi api = new CheckCodeApi();
//        api.setMobile(bindedAccount);
//        api.setCode(code);
//        api.setNationCode(user.getNationCode());
//        loadingDialog.show();
//        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
//            @Override
//            public void onResponse(BaseBean baseBean) {
//                loadingDialog.dismiss();
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                loadingDialog.dismiss();
//                Util.showToast(SendCodeChangePayPsdActivity.this, Util.checkErrorType(error));
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        if (timeDown != null) {
            timeDown.cancel();
            timeDown = null;
        }
        super.onDestroy();
    }

    private void sendAudio(final TextView codeTv) {
        hideKeyboard(null);
        timeDown = new TimeDown(60 * 1000, 1000);
        timeDown.start();
        timeDown.tv = codeTv;
        codeTv.setEnabled(false);
        btnCode.setEnabled(false);
        SendAudioApi api = new SendAudioApi();
        api.setMobile(bindedAccount);
        api.setSource(bindedAccount);
        api.setType("MemberMobile");
        api.setNationCode(user.getNationCode());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                Util.showToast(SendCodeChangePayPsdActivity.this, R.string.msg_send_code_ok);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(SendCodeChangePayPsdActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void sendCode(final TextView codeTv) {
        hideKeyboard(null);
        timeDown = new TimeDown(60 * 1000, 1000);
        timeDown.start();
        timeDown.tv = codeTv;
        codeTv.setEnabled(false);
        tvGetVoice.setEnabled(false);
        tvGetVoice.setTextColor(Color.parseColor("#61000000"));
        Drawable nav_dowm = getResources().getDrawable(R.mipmap.icon_login_novoice);
        nav_dowm.setBounds(0, 0, nav_dowm.getMinimumWidth(), nav_dowm.getMinimumHeight());
        tvGetVoice.setCompoundDrawables(nav_dowm, null, null, null);
        SendCodeApi api = new SendCodeApi();
        api.setMobile(bindedAccount);
        api.setSource(bindedAccount);
        api.setType("MemberMobile");
        api.setNationCode(user.getNationCode());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                Util.showToast(SendCodeChangePayPsdActivity.this, R.string.msg_send_code_ok);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(SendCodeChangePayPsdActivity.this, Util.checkErrorType(error));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        }
    }
}
