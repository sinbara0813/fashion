package com.d2cmall.buyer.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.UpdateAccountApi;
import com.d2cmall.buyer.api.UpdatePartnerInfoApi;
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
import de.greenrobot.event.EventBus;

/**
 * Created by rookie on 2017/8/30.
 * 修改昵称页面
 */

public class EditNickNameActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.edit)
    ClearEditText edit;
    private Dialog loadingDialog;
    private int type;
    private long partnerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nick);
        ButterKnife.bind(this);
        nameTv.setText("修改昵称");
        titleRight.setText("保存");
        titleRight.setTextColor(Color.parseColor("#61000000"));
        titleRight.setEnabled(false);
        loadingDialog = DialogUtil.createLoadingDialog(this);
        edit.setText(getIntent().getStringExtra("content"));
        type = getIntent().getIntExtra("type",0);
        partnerId = getIntent().getLongExtra("partnerId",0);
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = edit.getText().toString();
                if (!Util.isEmpty(string)) {
                    titleRight.setEnabled(true);
                    titleRight.setTextColor(Color.parseColor("#DE000000"));
                } else {
                    titleRight.setEnabled(false);
                    titleRight.setTextColor(Color.parseColor("#61000000"));
                }
            }
        });
    }

    private void requestUpdateAccountTask(final String newNickName, final String newEmail) {
        UpdateAccountApi api = new UpdateAccountApi();
        api.setNickname(newNickName);
        api.setEmail(newEmail);
        loadingDialog.show();
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                loadingDialog.dismiss();
                UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(EditNickNameActivity.this);
                user.setNickname(newNickName);
                Session.getInstance().saveUserToFile(EditNickNameActivity.this, user);
                EventBus.getDefault().post(user);
                setResult(RESULT_OK);
                EditNickNameActivity.super.onBackPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Util.showToast(EditNickNameActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void requestUpdatePartnerTask(String name) {
        UpdatePartnerInfoApi api = new UpdatePartnerInfoApi();
        if(partnerId!=0){
            api.setId(partnerId);
        }
        api.setName(name);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(EditNickNameActivity.this, Util.checkErrorType(error));
            }
        });
    }

    @OnClick({R.id.back_iv, R.id.title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.title_right:
                hideKeyboard(null);
                String content = edit.getText().toString().trim();
                if (Util.isEmpty(content)) {
                    Util.showToast(this, R.string.msg_nick_error2);
                    return;
                }
                if (content.length() < 4 || content.length() > 20) {
                    Util.showToast(this, R.string.msg_nick_error);
                    return;
                }
                if(type==2){//改partner昵称
                    requestUpdatePartnerTask(content);
                }else{//改member昵称
                    requestUpdateAccountTask(content, null);
                }

                break;
        }
    }
}
