package com.d2cmall.buyer.activity;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
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
import com.d2cmall.buyer.bean.AdviserBean;
import com.d2cmall.buyer.bean.AdviserListBean;
import com.d2cmall.buyer.bean.PartnerMemberBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;

import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//添加运营顾问界面
public class AddCounselorActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.tag)
    View tag;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.iv_headpic)
    ImageView ivHeadpic;
    @Bind(R.id.tv_wx_nickname)
    TextView tvWxNickname;
    @Bind(R.id.tv_wx_code)
    TextView tvWxCode;
    @Bind(R.id.btn_add_adviser)
    ImageView btnAddAdviser;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.tv_sv_time)
    TextView tvSvTime;
    @Bind(R.id.img_hint)
    ImageView imgHint;
    @Bind(R.id.btn_reload)
    TextView btnReload;
    @Bind(R.id.empty_hint_layout)
    LinearLayout emptyHintLayout;
    @Bind(R.id.ll_root)
    LinearLayout llRoot;
    private String wxCode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_counselor);
        ButterKnife.bind(this);
        nameTv.setText("运营顾问");
        loadPartnerInfo();

    }

    private void loadPartnerInfo() {
        SimpleApi simpleApi = new SimpleApi();
        simpleApi.setInterPath(Constants.GET_PARTNER_CENTER_URL);
        D2CApplication.httpClient.loadingRequest(simpleApi, new BeanRequest.SuccessListener<PartnerMemberBean>() {
            @Override
            public void onResponse(PartnerMemberBean partnerBean) {
                    if (Util.isEmpty(partnerBean.getData().getPartner().getCounselorId()) || "0".equals(partnerBean.getData().getPartner().getCounselorId())) {
                        loadAdviserList();
                    } else {
                        loadAdviserInfo(Integer.valueOf(partnerBean.getData().getPartner().getCounselorId()));
                    }

            }
        });

    }

    private void loadAdviserInfo(Integer id) {
        progressBar.setVisibility(View.VISIBLE);
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.BUYER_ADVISER_INFO, id));
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<AdviserBean>() {
            @Override
            public void onResponse(AdviserBean adviserBean) {
                if (isFinishing() || progressBar == null) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                if (adviserBean == null) {
                    setEmptyView(Constants.NO_DATA);
                    return;
                }
                llRoot.setVisibility(View.VISIBLE);
                btnAddAdviser.setClickable(true);
                wxCode = adviserBean.getData().getPartnerCounselor().getWeixin();
                tvWxCode.setText(getString(R.string.adviser_wx_code, adviserBean.getData().getPartnerCounselor().getWeixin()));
                tvWxNickname.setText(adviserBean.getData().getPartnerCounselor().getName());
                tvSvTime.setText(adviserBean.getData().getPartnerCounselor().getDescription() + "");
                UniversalImageLoader.displayImage(AddCounselorActivity.this, adviserBean.getData().getPartnerCounselor().getHeadPic(), ivHeadpic, R.mipmap.ic_default_avatar);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(AddCounselorActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void loadAdviserList() {
        progressBar.setVisibility(View.VISIBLE);
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.BUYER_ADVISER_LIST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<AdviserListBean>() {
            @Override
            public void onResponse(AdviserListBean adviserListBean) {
                if (isFinishing() || progressBar == null) {
                    return;
                }
                progressBar.setVisibility(View.GONE);
                btnAddAdviser.setClickable(true);
                setDataToView(adviserListBean);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setEmptyView(Constants.NET_DISCONNECT);
                progressBar.setVisibility(View.GONE);
                Util.showToast(AddCounselorActivity.this, Util.checkErrorType(error));
            }
        });
    }

    //请求到的是一个顾问的列表,随机取一个给用户
    private void setDataToView(AdviserListBean adviserListBean) {
        List<AdviserListBean.DataBean.CounselorsBean> counselors = adviserListBean.getData().getCounselors();
        if (counselors == null || counselors.size() == 0) {
            setEmptyView(Constants.NO_DATA);
            return;
        }
        llRoot.setVisibility(View.VISIBLE);
        int randomInt = new Random().nextInt(counselors.size());
        AdviserListBean.DataBean.CounselorsBean counselorsBean = counselors.get(randomInt);
        wxCode = counselorsBean.getWeixin();
        tvWxCode.setText(getString(R.string.adviser_wx_code, counselorsBean.getWeixin()));
        tvWxNickname.setText(counselorsBean.getName());
        tvSvTime.setText(counselorsBean.getDescription() + "");
        UniversalImageLoader.displayImage(this, counselorsBean.getHeadPic(), ivHeadpic, R.mipmap.ic_default_avatar);
    }

    @OnClick({R.id.back_iv, R.id.btn_add_adviser, R.id.btn_reload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.btn_add_adviser:
                if (Util.isEmpty(wxCode)) {
                    return;
                }
                ClipboardManager cm = (ClipboardManager) AddCounselorActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setPrimaryClip(ClipData.newPlainText("code", wxCode));
                //弹窗提示是否跳转微信
                new AlertDialog.Builder(AddCounselorActivity.this)
                        .setMessage("复制成功，请打开微信添加好友")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                jumpToWX();
                            }
                        })
                        .show();
                break;
            case R.id.btn_reload:
                imgHint.setVisibility(View.GONE);
                btnReload.setVisibility(View.GONE);
                llRoot.setVisibility(View.VISIBLE);
                loadPartnerInfo();
                break;
        }
    }

    private void setEmptyView(int type) {
        llRoot.setVisibility(View.GONE);
        if (type == Constants.NO_DATA) {
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.icon_empty_adviser);
            btnReload.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams layoutParams = btnReload.getLayoutParams();
            layoutParams.width= ScreenUtil.dip2px(120);
            layoutParams.height=ScreenUtil.dip2px(27);
            btnReload.setLayoutParams(layoutParams);
            btnReload.setText("暂无运营顾问哦~");
            btnReload.setBackgroundColor(getResources().getColor(R.color.transparent));
        } else {
            btnReload.setText("重新加载");
            btnReload.setBackgroundResource(R.drawable.sp_line);
            btnReload.setVisibility(View.VISIBLE);
            imgHint.setVisibility(View.VISIBLE);
            imgHint.setImageResource(R.mipmap.ic_no_net);
        }
    }

    private void jumpToWX() {
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");

            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            this.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Util.showToast(this, "抱歉您尚未安装微信");
        }
    }
}
