package com.d2cmall.buyer.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.AddressApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.AddressBean;
import com.d2cmall.buyer.bean.AddressListBean;
import com.d2cmall.buyer.bean.GlobalTypeBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.DialogUtil;
import com.d2cmall.buyer.util.LoctionUtil;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.AddressPop;
import com.d2cmall.buyer.widget.ClearEditText;

import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by rookie on 2017/8/31.
 * 编辑地址页面
 */

public class EditAddressActivity extends BaseActivity implements AddressPop.CallBack {

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
    @Bind(R.id.line_layout)
    View lineLayout1;
    @Bind(R.id.edit_phone)
    ClearEditText editPhone;
    @Bind(R.id.edit_province)
    ClearEditText editProvince;
    @Bind(R.id.ll_province)
    LinearLayout llProvince;
    @Bind(R.id.edit_address)
    ClearEditText editAddress;
    @Bind(R.id.switch_default)
    Switch switchDefault;
    @Bind(R.id.image_arrow)
    ImageView imageArrow;
    @Bind(R.id.tag)
    View tag;
    @Bind(R.id.edit_email)
    ClearEditText editEmail;
    @Bind(R.id.rl_email)
    RelativeLayout rlEmail;
    @Bind(R.id.edit_wechat)
    ClearEditText editWechat;
    @Bind(R.id.rl_wechat)
    RelativeLayout rlWechat;
    @Bind(R.id.tv_oversea_notice)
    TextView tv_notice;
    @Bind(R.id.ll_oversea)
    LinearLayout llOversea;
    @Bind(R.id.line_layout1)
    View line;
    private AddressPop mPop;
    private String provinceStrs;
    private String provinceName;
    private String cityName;
    private String countyName;
    private int province;
    private int city;
    private int district;
    private boolean isDefault;
    private Dialog loadingDialog;
    private int type; //0新增;1编辑;2下单
    private AddressListBean.DataEntity.AddressesEntity.ListEntity entity;
    private LoctionUtil mLoctionUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_address);
        ButterKnife.bind(this);
        LoctionUtil.checklocationpermiss(this);
        type = getIntent().getIntExtra("type", 0);
        titleRight.setText("保存");
        entity = (AddressListBean.DataEntity.AddressesEntity.ListEntity) getIntent().getSerializableExtra("address");
        switchDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isDefault = isChecked;
            }
        });
        switch (type) {
            case 2:
            case 0:
                nameTv.setText("新增地址");
                break;
            case 1:
                nameTv.setText("编辑地址");
                editManName.setText(entity.getName());
                editManName.setSelection(entity.getName().length());
                editPhone.setText(entity.getMobile());
                initEditListener();
                editAddress.setText(entity.getStreet());
                switchDefault.setChecked(entity.isIsdefault());
                editProvince.setText(getString(R.string.label_address_format2, entity.getProvince(), entity.getCity(),
                        entity.getDistrict()));
                province = entity.getProvinceCode();
                city = entity.getCityCode();
                district = entity.getDistrictCode();
                provinceName = entity.getProvince();
                cityName = entity.getCity();
                countyName = entity.getDistrict();

                if (provinceName.equals("国外地区") || provinceName.equals("香港特别行政区") ||
                        provinceName.equals("澳门特别行政区") || provinceName.equals("台湾省")) {
                    rlEmail.setVisibility(View.VISIBLE);
                    rlWechat.setVisibility(View.VISIBLE);
                    llOversea.setVisibility(View.VISIBLE);
                    if (!Util.isEmpty(entity.getWeixin())) {
                        editWechat.setText(entity.getWeixin());
                    }
                    if (!Util.isEmpty(entity.getEmail())) {
                        editEmail.setText(entity.getEmail());
                    }
                } else {
                    rlEmail.setVisibility(View.GONE);
                    rlWechat.setVisibility(View.GONE);
                    llOversea.setVisibility(View.GONE);
                }
                break;
        }
        loadingDialog = DialogUtil.createLoadingDialog(this);
    }

    private void initEditListener() {
        editPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    String phone = editPhone.getText().toString().trim();
                    if(!Util.isEmpty(phone) ){
                        editPhone.setSelectAllOnFocus(true);
                        ((ClearEditText)v).selectAll();
                    }
                }

            }
        });
    }

    //判断是否个字符
    private boolean isNormalCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9)
                || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    @Override
    protected void onStart() {
        mLoctionUtil = LoctionUtil.getLoctionUtil(this);
        mLoctionUtil.setIhasCity(new LoctionUtil.IhasCity() {
            @Override
            public String getAddress(String province, String cityName, String street) {
                if (province != null) {
                    if (Util.isEmpty(provinceName)) {
                        provinceName = province;
                    }
                }
                if (cityName != null) {
                    if (Util.isEmpty(EditAddressActivity.this.cityName)) {
                        EditAddressActivity.this.cityName = cityName;
                    }
                }
                return null;
            }
        });
        super.onStart();
    }

    //检测是否有emoji表情
    private boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isNormalCharacter(codePoint)) {
                //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onPause() {
        Util.onPause(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        mLoctionUtil.destroyLocation();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mLoctionUtil.destroyLocation();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        hideKeyboard(null);
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i < permissions.length; i++) {
            if (permissions[i].equals("android.permission.ACCESS_COARSE_LOCATION") &&
                    grantResults[i] == -1) {
                //Util.showToast(this, getString(R.string.msg_locationpermission));

            }
        }

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

    @OnClick({R.id.back_iv, R.id.title_right, R.id.edit_province, R.id.image_arrow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.title_right:
                save();
                break;
            case R.id.edit_province:
            case R.id.image_arrow:
                hideKeyboard(null);
                if (mPop == null) {
                    mPop = new AddressPop(this, provinceStrs);
                    mPop.setCallBack(this);
                    if (!Util.isEmpty(provinceName) && !Util.isEmpty(cityName)) {
                        mPop.refreshDataByProvinceCity(provinceName, cityName);
                    }
                }
                mPop.show(getWindow().getDecorView());
                break;
        }
    }

    private void save() {
        final String name = editManName.getText().toString().trim();
        String phone = editPhone.getText().toString().trim();
        final String street = editAddress.getText().toString().trim();
        String mail = editEmail.getText().toString().trim();
        String wechat = editWechat.getText().toString().trim();
        if (Util.isEmpty(name)) {
            Util.showToast(this, R.string.hint_receive_name);
            return;
        }
        phone = phone.replaceAll("-", "");
        if (Util.isEmpty(phone)) {
            Util.showToast(this, R.string.hint_receive_phone);
            return;
        }
        if (rlWechat.getVisibility() == View.GONE && phone.length() != 11) {
            Util.showToast(this, "请输入11位手机号！");
            return;
        }
        //防止用户修改过手机号依然带有*号
        if( type==1 && !Util.isEmpty(entity.getMobile())&& !entity.getMobile().equals(phone) &&  phone.contains("*")){
            Util.showToast(this, "手机号码已修改,请输入正确的手机号");
            return;
        }
        if (province == 0 || city == 0) {
            Util.showToast(this, R.string.hint_receive_location);
            return;
        }
        if (Util.isEmpty(street)) {
            Util.showToast(this, R.string.hint_receive_street);
            return;
        }
        if (name.length() < 2 || name.length() > 20) {
            Util.showToast(this, R.string.msg_name_error);
            return;
        }
        if (containsEmoji(name)) {
            Util.showToast(this, R.string.hint_not_emoji);
            return;
        }
        if (!(provinceName.equals("香港特别行政区") || provinceName.equals("澳门特别行政区") || provinceName.equals("台湾省")
                || provinceName.equals("国外地区"))) {
            //大陆用户
            if (phone.length() != 11) {
                Util.showToast(this, R.string.msg_phone_error);
                return;
            }
        } else {
            //海外用户
            if (phone.length() < 5 || phone.length() > 20) {
                Util.showToast(this, R.string.msg_phone_error);
                return;
            }
            if (Util.isEmpty(mail)) {
                Util.showToast(this, R.string.msg_mail_error2);
                return;
            }
            if (Util.isEmpty(wechat)) {
                Util.showToast(this, R.string.msg_wechat_error);
                return;
            }
            if (!Util.isMail(mail)) {
                Util.showToast(this, R.string.msg_mail_error);
                return;
            }
        }

        if (street.length() > 100) {
            Util.showToast(this, R.string.msg_street_error);
            return;
        }
        final AddressApi api = new AddressApi();
        api.setDefault(isDefault);
        api.setRegionPrefix(String.valueOf(province));
        api.setRegionMiddle(String.valueOf(city));
        api.setRegionSuffix(String.valueOf(district));
        api.setName(name);
        api.setMobile(phone);
        api.setStreet(street);
        LoctionUtil.getLatLonPoint(this, provinceName + cityName + street, cityName, new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                if (i == 1000) {//1000为成功
                    if (geocodeResult.getGeocodeAddressList() != null && geocodeResult.getGeocodeAddressList().size() > 0) {
                        LatLonPoint point = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint();
                        if (point.getLatitude()!=0){
                            api.latitude=point.getLatitude();
                        }
                        if (point.getLongitude()!=0){
                            api.longitude=point.getLongitude();
                        }
                        goOnApi(api);
                    } else {
                        goOnApi(api);
                    }
                } else {
                    goOnApi(api);
                }
            }
        });

    }

    private void goOnApi(final AddressApi api) {
        String mail = editEmail.getText().toString().trim();
        String wechat = editWechat.getText().toString().trim();
        if (provinceName.equals("国外地区") || provinceName.equals("香港特别行政区") ||
                provinceName.equals("澳门特别行政区") || provinceName.equals("台湾省")) {
            api.setEmail(mail);
            api.setWeixin(wechat);
        }
        loadingDialog.show();
        if (type == 0 || type == 2) {//新增收货地址
            api.setInterPath(Constants.INSERT_ADDRESS_URL);
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<AddressBean>() {
                @Override
                public void onResponse(AddressBean bean) {
                    loadingDialog.dismiss();
                    Util.showToast(EditAddressActivity.this, R.string.msg_save_ok);
                    if (type == 2) {//下单那儿
                        Intent intent = new Intent();
                        intent.putExtra("name", bean.getData().getAddress().getName());
                        intent.putExtra("phone", bean.getData().getAddress().getMobile());
                        intent.putExtra("address", getString(R.string.label_address_format, provinceName,
                                cityName, countyName,
                                bean.getData().getAddress().getStreet()));
                        intent.putExtra("id", bean.getData().getAddress().getId());
                        intent.putExtra("province",provinceName);
                        intent.putExtra("city",cityName);
                        intent.putExtra("district",bean.getData().getAddress().getDistrict());
                        intent.putExtra("street",bean.getData().getAddress().getStreet());
                        intent.putExtra("isDefault", isDefault);
                        if (api.latitude!=null){
                            intent.putExtra("lat",api.latitude.doubleValue());
                        }
                        if (api.longitude!=null){
                            intent.putExtra("lon",api.longitude.doubleValue());
                        }
                        setResult(RESULT_OK, intent);
                    } else {
                        EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.REFRESH_ADDRESSES));
                    }
                    onBackPressed();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loadingDialog.dismiss();
                    Util.showToast(EditAddressActivity.this, Util.checkErrorType(error));
                }
            });
        } else if (type == 1) {//更新收货地址
            api.setInterPath(Constants.UPDATE_ADDRESS_URL);
            api.setId((int) entity.getId());
            D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<AddressBean>() {
                @Override
                public void onResponse(AddressBean bean) {
                    loadingDialog.dismiss();
                    Util.showToast(EditAddressActivity.this, R.string.msg_save_ok);
                    EventBus.getDefault().post(new GlobalTypeBean(Constants.GlobalType.REFRESH_ADDRESSES));
                    onBackPressed();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loadingDialog.dismiss();
                    Util.showToast(EditAddressActivity.this, Util.checkErrorType(error));
                }
            });
        }
    }

    @Override
    public void callback(String pName, int provinceCode, String cName, int cityCode, String dName, int districtCode) {
        provinceName = pName;
        cityName = cName;
        countyName = dName;
        editProvince.setText(getString(R.string.label_address_format2, provinceName, cityName, countyName));
        province = provinceCode;
        city = cityCode;
        district = districtCode;
        if (provinceName.equals("国外地区") || provinceName.equals("香港特别行政区") ||
                provinceName.equals("澳门特别行政区") || provinceName.equals("台湾省")) {
            rlWechat.setVisibility(View.VISIBLE);
            rlEmail.setVisibility(View.VISIBLE);
            llOversea.setVisibility(View.VISIBLE);
            line.setVisibility(View.GONE);
        } else {
            rlWechat.setVisibility(View.GONE);
            rlEmail.setVisibility(View.GONE);
            llOversea.setVisibility(View.GONE);
            line.setVisibility(View.VISIBLE);
        }
    }
}
