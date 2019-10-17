package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.d2cmall.buyer.api.UpdateAccountApi;
import com.d2cmall.buyer.api.UpdatePartnerInfoApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.PartnerInfoBean;
import com.d2cmall.buyer.bean.UserBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Session;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.AddressPop;
import com.d2cmall.buyer.widget.BirthdayPop;
import com.d2cmall.buyer.widget.ClearEditText;
import com.d2cmall.buyer.widget.SingleSelectPop;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//买手更多信息界面
public class PartnerMoreInfoActivity extends BaseActivity implements SingleSelectPop.CallBack, BirthdayPop.CallBack, AddressPop.CallBack {

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
    @Bind(R.id.tv_birthday)
    TextView tvBirthday;
    @Bind(R.id.ll_partner_birthday)
    LinearLayout llPartnerBirthday;
    @Bind(R.id.tv_location)
    TextView tvLocation;
    @Bind(R.id.ll_partner_location)
    LinearLayout llPartnerLocation;
    @Bind(R.id.et_job)
    ClearEditText etJob;
    @Bind(R.id.ll_partner_job)
    LinearLayout llPartnerJob;
    @Bind(R.id.et_old_job)
    ClearEditText etOldJob;
    @Bind(R.id.ll_partner_old_job)
    LinearLayout llPartnerOldJob;
    @Bind(R.id.et_wx_friend)
    ClearEditText etWxFriend;
    @Bind(R.id.ll_partner_wx_friend)
    LinearLayout llPartnerWxFriend;
    @Bind(R.id.et_wb_friend)
    ClearEditText etWbFriend;
    @Bind(R.id.ll_partner_wb_friend)
    LinearLayout llPartnerWbFriend;
    @Bind(R.id.tv_consume)
    TextView tvConsume;
    @Bind(R.id.ll_partner_consume)
    LinearLayout llPartnerConsume;
    @Bind(R.id.btn_commit)
    Button btnCommit;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    private String provinceStrs;
    private String provinceName;
    private String cityName;
    private String countyName;
    private AddressPop mPop;
    private SingleSelectPop singleSelectPop;
    private String consumeValue;
    private PartnerInfoBean mPartnerInfoBean;
    private BirthdayPop birthdayPopView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_more_info);
        ButterKnife.bind(this);
        String[] titles = getResources().getStringArray(R.array.label_partner_consumes);
        singleSelectPop = new SingleSelectPop(this, titles);
        singleSelectPop.setCallBack(this);
        nameTv.setText("更多信息");
        loadData();
    }


    @Override
    protected void onResume() {
        new Thread() {
            @Override
            public void run() {
                try {
                    InputStream input = getResources().openRawResource
                            (R.raw.area);
                    provinceStrs = Util.readStreamToString(input);
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        Util.onResume(this);
        super.onResume();
    }

    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        SimpleApi simpleApi = new SimpleApi();
        simpleApi.setInterPath(Constants.GET_PARTNER_CENTER_URL);
        D2CApplication.httpClient.loadingRequest(simpleApi, new BeanRequest.SuccessListener<PartnerInfoBean>() {
            @Override
            public void onResponse(PartnerInfoBean partnerInfoBean) {
                progressBar.setVisibility(View.GONE);
                mPartnerInfoBean = partnerInfoBean;
                setUserInfo();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(PartnerMoreInfoActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void setUserInfo() {

        if (mPartnerInfoBean == null) {
            return;
        }
        if (!Util.isEmpty(mPartnerInfoBean.getData().getPartner().getBirthday())) {
            Date birthDate = Util.getDate(mPartnerInfoBean.getData().getMember().getBirthDay());
            DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_SHORT);
            tvBirthday.setText(dateFormat.format(birthDate));
            String[] dates = dateFormat.format(birthDate).split("/");
            if (dates.length == 3) {
                birthdayPopView = new BirthdayPop(this, dates[0], dates[1], dates[2]);
            } else {
                birthdayPopView = new BirthdayPop(this);
            }
        } else {
            birthdayPopView = new BirthdayPop(this);
        }
        birthdayPopView.setCallBack(this);
        if (!Util.isEmpty(mPartnerInfoBean.getData().getPartner().getAddress())) {
            tvLocation.setText(mPartnerInfoBean.getData().getPartner().getAddress());
        }
        if (!Util.isEmpty(mPartnerInfoBean.getData().getPartner().getOccupation())) {
            etJob.setText(mPartnerInfoBean.getData().getPartner().getOccupation());
        }
        if (!Util.isEmpty(mPartnerInfoBean.getData().getPartner().getExperience())) {
            etOldJob.setText(mPartnerInfoBean.getData().getPartner().getExperience());
        }
        if (!Util.isEmpty(mPartnerInfoBean.getData().getPartner().getWeixinCount())) {
            etWxFriend.setText(mPartnerInfoBean.getData().getPartner().getWeixinCount());
        }
        if (!Util.isEmpty(mPartnerInfoBean.getData().getPartner().getWeiboCount())) {
            etWbFriend.setText(mPartnerInfoBean.getData().getPartner().getWeiboCount());
        }
        if (!Util.isEmpty(mPartnerInfoBean.getData().getPartner().getConsumption())) {
            tvConsume.setText(mPartnerInfoBean.getData().getPartner().getConsumption());
        }
    }

    @OnClick({R.id.back_iv, R.id.tv_birthday, R.id.tv_location, R.id.tv_consume, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.tv_birthday:
                birthdayPopView.show(getWindow().getDecorView(), tvBirthday);
                break;
            case R.id.tv_location:
                if (mPop == null) {
                    mPop = new AddressPop(this, provinceStrs);
                    mPop.setCallBack(this);
                    if (!Util.isEmpty(provinceName) && !Util.isEmpty(cityName)) {
                        mPop.refreshDataByProvinceCity(provinceName, cityName);
                    }
                }
                mPop.show(getWindow().getDecorView());
                break;
            case R.id.tv_consume:
                singleSelectPop.show(getWindow().getDecorView(), tvConsume);
                break;
            case R.id.btn_commit:
                requestUpdatePartnerTask();
                break;
        }
    }

    @Override
    public void callback(View trigger, int index, String value) {
        tvConsume.setText(value);
        consumeValue = value;
    }

    @Override
    public void callback(View trigger, int[] index, String[] value) {
        String year = value[0];
        String month = value[1];
        String day = value[2];
        Calendar calendar1 = Calendar.getInstance();
        if (!Util.isEmpty(year) && !Util.isEmpty(month) && !Util.isEmpty(day)) {
            calendar1.set(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day));
            Calendar calendar = Calendar.getInstance();
            if (calendar1.compareTo(calendar) > 0) {
                Util.showToast(this, "不能设置当日之后的时间");
                return;
            }
        }
        String birthStr = String.format(getString(R.string.label_year_month_day), year, addZero(month), addZero(day));
        tvBirthday.setText(birthStr);
        requestUpdateMemberTask(null, null, birthStr);
    }

    private void requestUpdateMemberTask(final String newHeadPic, final String newSex, final String newBirthday) {
        UpdateAccountApi api = new UpdateAccountApi();
        api.setBirthDay(newBirthday);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                UserBean.DataEntity.MemberEntity user = Session.getInstance().getUserFromFile(PartnerMoreInfoActivity.this);
                if (!Util.isEmpty(newBirthday)) {
                    user.setBirthDay(newBirthday);
                }
                Session.getInstance().saveUserToFile(PartnerMoreInfoActivity.this, user);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(PartnerMoreInfoActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private void requestUpdatePartnerTask() {
        progressBar.setVisibility(View.VISIBLE);
        UpdatePartnerInfoApi api = new UpdatePartnerInfoApi();
        if (mPartnerInfoBean.getData() != null) {
            api.setId(mPartnerInfoBean.getData().getPartner().getId());
        }
        api.setAddress(tvLocation.getText().toString().trim());
        api.setBirthday(tvBirthday.getText().toString().trim());
        api.setExperience(etOldJob.getText().toString().trim());
        api.setOccupation(etJob.getText().toString().trim());
        api.setConsumption(tvConsume.getText().toString().trim());
        api.setWeixinCount(etWxFriend.getText().toString().trim());
        api.setWeiboCount(etWbFriend.getText().toString().trim());
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(PartnerMoreInfoActivity.this, "更新完成");
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(PartnerMoreInfoActivity.this, Util.checkErrorType(error));
            }
        });
    }

    private String addZero(String str) {
        String result = str;
        if (result.length() == 1) {
            result = "0" + result;
        }
        return result;
    }

    @Override
    public void callback(String provinceName, int provinceCode, String cityName, int cityCode, String districtName, int districtCode) {
        this.provinceName = provinceName;
        this.cityName = cityName;
        countyName = districtName;
        tvLocation.setText(getString(R.string.label_address_format2, provinceName, cityName, countyName));
    }
}
