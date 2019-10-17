package com.d2cmall.buyer.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.GetCouponApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.fragment.AvailableCouponFragment;
import com.d2cmall.buyer.fragment.OtherCouponFragment;
import com.d2cmall.buyer.fragment.RefreshFragment;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.TitleUtil;
import com.d2cmall.buyer.util.Util;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 优惠券列表
 * Author: Blend
 * Date: 16/4/21 15:01
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class MyCouponsActivity extends BaseActivity {

    @Bind(R.id.sliding_tab)
    SlidingTabLayout slidingTab;
    @Bind(R.id.pager)
    ViewPager pager;
    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    LinearLayout titleLayout;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private String[] titles;
    private ArrayList<RefreshFragment> fragmentList;
    private Dialog pwdDialog, loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_list);
        ButterKnife.bind(this);
        initTitle();
        titles = getResources().getStringArray(R.array.label_coupon_tabs);
        loadingDialog = DialogUtil.createLoadingDialog(this);
        fragmentList = new ArrayList<>();
        fragmentList.add(AvailableCouponFragment.newInstance("CLAIMED"));//可使用
        fragmentList.add(OtherCouponFragment.newInstance("USED"));//已使用
        fragmentList.add(OtherCouponFragment.newInstance("INVALID"));//已过期
        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        slidingTab.setViewPager(pager);
    }

    private void initTitle() {
        TitleUtil.setBack(this);
        TitleUtil.setTitle(this, R.string.label_coupon_list);
        TitleUtil.setOkText(this, R.string.label_get);
    }

    private class TabPagerAdapter extends FragmentPagerAdapter {

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    private void showDialog() {
        if (pwdDialog != null && pwdDialog.isShowing()) {
            return;
        }
        pwdDialog = new Dialog(this, R.style.bubble_dialog);
        Point point = Util.getDeviceSize(this);
        View dialogView = getLayoutInflater().inflate(R.layout.layout_coupons_dialog, null);
        final EditText etNumber = (EditText) dialogView.findViewById(R.id.et_number);
        dialogView.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwdDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.confirm_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = etNumber.getText().toString();
                if (Util.isEmpty(pwd)) {
                    Util.showToast(MyCouponsActivity.this, "兑换码不能为空");
                } else {
                    pwdDialog.dismiss();
                    callBack(pwd);
                }
            }
        });
        CheckBox checkBox = (CheckBox) dialogView.findViewById(R.id.check_box);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etNumber.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etNumber.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                CharSequence text1 = etNumber.getText();
                if (text1 instanceof Spannable) {
                    Spannable spanText = (Spannable) text1;
                    Selection.setSelection(spanText, text1.length());
                }
            }
        });
        pwdDialog.setContentView(dialogView);
        Window win = pwdDialog.getWindow();
        ViewGroup.LayoutParams params = win.getAttributes();
        params.width = Math.round(point.x * 3 / 4);
        win.setGravity(Gravity.CENTER);
        pwdDialog.show();
    }

    private void callBack(String password) {
        if (Util.isEmpty(password)) {
            Util.showToast(MyCouponsActivity.this, R.string.hint_get_coupon);
            return;
        }
        hideKeyboard(null);
        GetCouponApi api = new GetCouponApi();
        api.setPassword(password);
        loadingDialog.show();
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                loadingDialog.dismiss();
                Util.showToast(MyCouponsActivity.this, R.string.msg_get_coupon_ok);
                pager.setCurrentItem(0);
                fragmentList.get(0).refresh(0);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.dismiss();
                Util.showToast(MyCouponsActivity.this, Util.checkErrorType(error));
            }
        });
    }

    @Override
    public void onOkButtonClick() {
        super.onOkButtonClick();
        showDialog();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case Constants.RequestCode.GET_COUPON:
                    pager.setCurrentItem(0);
                    fragmentList.get(0).refresh(0);
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
}