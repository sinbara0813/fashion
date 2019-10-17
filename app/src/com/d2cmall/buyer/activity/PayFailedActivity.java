package com.d2cmall.buyer.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.ActionBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.SharePrefConstant;
import com.d2cmall.buyer.widget.GuideLayout;
import com.qiyukf.unicorn.api.ConsultSource;
import com.qiyukf.unicorn.api.Unicorn;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by rookie on 2017/8/29.
 */

public class PayFailedActivity extends BaseActivity {
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.iv_fail)
    ImageView ivFail;
    @Bind(R.id.tv_fail)
    TextView tvFail;
    @Bind(R.id.tv_explain)
    TextView tvExplain;
    @Bind(R.id.btn_go_on_pay)
    Button btnGoOnPay;
    @Bind(R.id.ll_back_main)
    LinearLayout llBackMain;
    @Bind(R.id.ll_look_order)
    LinearLayout llLookOrder;
    @Bind(R.id.ll_tel)
    LinearLayout llTel;
    @Bind(R.id.ll_online)
    LinearLayout llOnline;

    private String orderSn;
    private boolean isApply;
    private String subject;
    private String payStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_fail);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        ViewCompat.setElevation(titleLayout,0);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        D2CApplication.mSharePref.removeKey(SharePrefConstant.PRODUCT_ID);
        orderSn = getIntent().getStringExtra("orderSn");
        subject = getIntent().getStringExtra("subject");
        payStyle=getIntent().getStringExtra("payStyle");
        isApply=getIntent().getBooleanExtra("isApply",false);
    }

    @OnClick({R.id.back_iv, R.id.btn_go_on_pay, R.id.ll_back_main, R.id.ll_look_order, R.id.ll_tel, R.id.ll_online})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_go_on_pay:
                GuideLayout.back();
                EventBus.getDefault().post(new ActionBean(Constants.ActionType.CLEAR_ALL_ACTIVITY));
                intent = new Intent(this, CashierActivity.class);
                intent.putExtra("id", orderSn);
                intent.putExtra("isApply",isApply);
                intent.putExtra("subject",subject);
                intent.putExtra("payStyle",payStyle);
                startActivity(intent);
                break;
            case R.id.ll_back_main:
                GuideLayout.back();
                EventBus.getDefault().post(new ActionBean(Constants.ActionType.CLEAR_ALL_ACTIVITY));
                intent=new Intent(this,HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_look_order:
                GuideLayout.back();
                EventBus.getDefault().post(new ActionBean(Constants.ActionType.CLEAR_ALL_ACTIVITY));
                intent=new Intent(this,MyOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_tel:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                                Constants.RequestCode.PERMISSION);
                    } else {
                        call();
                    }
                } else {
                    call();
                }
                break;
            case R.id.ll_online:
                UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(this);
                if (user == null) {
                    Intent i = new Intent(this, LoginActivity.class);
                    startActivityForResult(i, Constants.Login.MAIN_LOGIN);
                    overridePendingTransition(R.anim.slide_in_up, R.anim.activity_anim_default);
                } else {
                    toChat();
                    finish();
                }
                break;
        }
    }

    private void call() {
        Intent call = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + getString(R.string.label_green_phone));
        call.setData(data);
        startActivity(call);
    }

    private void toChat() {
        String title = "线上客服";
        String url = "http://www.d2cmall.com";
        ConsultSource source = new ConsultSource(url, title, "支付失败");
        source.groupId = Constants.QIYU_AF_GROUP_ID;
        source.robotFirst = true;
        Unicorn.openServiceActivity(this, "D2C客服", source);
        //合力亿捷
//        Intent intent = new Intent(this,CustomServiceActivity.class);
//        intent.putExtra("skillGroupId",Constants.HLYJ_BF_AF_GROUP_ID);
//        startActivity(intent);
    }
}
