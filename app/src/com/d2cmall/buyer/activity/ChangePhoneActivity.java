package com.d2cmall.buyer.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
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
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.MyPacketBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.listener.Clickable;
import com.d2cmall.buyer.util.FileUtil;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/11/20.
 */

public class ChangePhoneActivity extends BaseActivity {

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
    @Bind(R.id.number)
    TextView number;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.tv_delete)
    TextView tvDelete;
    private UserBean.DataEntity.MemberEntity user;
    private MyPacketBean.DataEntity.AccountEntity accountEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_bind_phone);
        ButterKnife.bind(this);
        nameTv.setText("绑定手机号");
        requestWalletInfoTask();
        user = Session.getInstance().getUserFromFile(this);
        if (user.getLoginCode() != null) {
            number.setText(Util.hidePhoneNum(user.getLoginCode()));
        }
    }

    private void requestWalletInfoTask() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.MY_WALLET_INFO_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<MyPacketBean>() {
            @Override
            public void onResponse(MyPacketBean myPacketBean) {
                accountEntity = myPacketBean.getData().getAccount();
                SpannableString sb = new SpannableString(getResources().getString(R.string.label_delete));
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#61000000"));
                sb.setSpan(colorSpan, 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 8, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); //粗体
                sb.setSpan(new Clickable(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (accountEntity.getAvailableAmount() > 0) {
                            new AlertDialog.Builder(ChangePhoneActivity.this)
                                    .setMessage(String.format(getString(R.string.msg_advice_delete_account), String.valueOf(accountEntity.getAvailableAmount())))
                                    .setPositiveButton("继续注销", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(ChangePhoneActivity.this, PhoneVerificationActivity.class);
                                            intent.putExtra("flag", 1);
                                            ChangePhoneActivity.this.startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .show();
                        } else {
                            Intent intent = new Intent(ChangePhoneActivity.this, PhoneVerificationActivity.class);
                            intent.putExtra("flag", 1);
                            ChangePhoneActivity.this.startActivity(intent);
                        }
                    }
                }), 8, sb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tvDelete.setMovementMethod(LinkMovementMethod.getInstance());
                tvDelete.setText(sb);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
    }

    @OnClick({R.id.back_iv, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.button:
                Intent intent = new Intent(this, PhoneVerificationActivity.class);
                startActivityForResult(intent, 1123);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1123 && resultCode == RESULT_OK) {
            UserBean.DataEntity.MemberEntity userInfo = Session.getInstance().getUserFromFile(this);
            number.setText(Util.hidePhoneNum(userInfo.getLoginCode()));
            setResult(RESULT_OK);
            finish();
        }
    }
}
