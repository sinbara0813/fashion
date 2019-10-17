package com.d2cmall.buyer.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.CityBean;
import com.d2cmall.buyer.bean.DistrictBean;
import com.d2cmall.buyer.bean.ProvinceBean;
import com.d2cmall.buyer.widget.loopview.LoopView;
import com.d2cmall.buyer.widget.loopview.OnItemSelectedListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProvinceCityPop implements TransparentPop.InvokeListener {

    @Bind(R.id.pop_cancel)
    TextView popCancel;
    @Bind(R.id.pop_sure)
    TextView popSure;
    @Bind(R.id.id_province)
    LoopView idProvince;
    @Bind(R.id.id_city)
    LoopView idCity;
    @Bind(R.id.id_district)
    LoopView idDistrict;
    private TransparentPop mPop;
    private Context mContext;
    private View bottomView;
    private List<ProvinceBean> provinces;
    private int mProvinceIndex;
    private int mCityIndex;
    private int mDistrictIndex;

    public ProvinceCityPop(Context context, String str) {
        this.mContext = context;
        init();
        parseStr(str);
    }

    private void init() {
        bottomView = LayoutInflater.from(mContext).inflate(R.layout.layout_address_pop1, new LinearLayout(mContext), false);
        ButterKnife.bind(this, bottomView);
        mPop = new TransparentPop(mContext, this);
        Animation inAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_up);
        Animation outAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_down);
        mPop.setInAnimation(inAnimation);
        mPop.setOutAnimation(outAnimation);
        mPop.dismissFromOut();
        idDistrict.setVisibility(View.GONE);
        initListener();
    }

    private void parseStr(String str) {
        try {
            provinces=new ArrayList<>();
            JSONArray array = new JSONArray(str);
            int arraySize = array.length();
            ProvinceBean province = null;
            for (int i = 0; i < arraySize; i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                Gson gson = new Gson();
                province = gson.fromJson(jsonObject.toString(), ProvinceBean.class);
                provinces.add(province);
            }
            if (arraySize > 0) {
                ArrayList<String> provinceList = new ArrayList<>();
                for (int i = 0; i < arraySize; i++) {
                    ProvinceBean provinceBean = provinces.get(i);
                    provinceList.add(provinceBean.getName());
                    if (i == 0) {
                        int citySize = provinceBean.getChildren().size();
                        if (citySize > 0) {
                            ArrayList<String> cityList = new ArrayList<>();
                            for (int j = 0; j < citySize; j++) {
                                CityBean cityBean = provinceBean.getChildren().get(j);
                                cityList.add(cityBean.getName());
                                if (j == 0) {
                                    int districtSize = cityBean.getChildren().size();
                                    if (districtSize > 0) {
                                        ArrayList<String> districtList = new ArrayList<>();
                                        for (int k = 0; k < districtSize; k++) {
                                            DistrictBean districtBean = cityBean.getChildren().get(k);
                                            districtList.add(districtBean.getName());
                                        }
                                        idDistrict.setItems(districtList);
                                    }
                                }
                            }
                            idCity.setItems(cityList);
                        }
                    }
                }
                idProvince.setItems(provinceList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initListener() {
        idProvince.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mProvinceIndex = index;
                updateCities();
            }
        });

        idCity.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mCityIndex = index;
                updateAreas();
            }
        });

        idDistrict.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                mDistrictIndex = index;
            }
        });

        popCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        popSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                String provinceName = provinces.get(mProvinceIndex).getName();
                int provinceCode = provinces.get(mProvinceIndex).getCode();
                String cityName = provinces.get(mProvinceIndex).getChildren().get(mCityIndex).getName();
                int cityCode = provinces.get(mProvinceIndex).getChildren().get(mCityIndex).getCode();
                String districtName = provinces.get(mProvinceIndex).getChildren().get(mCityIndex).getChildren().get(mDistrictIndex).getName();
                int districtCode = provinces.get(mProvinceIndex).getChildren().get(mCityIndex).getChildren().get(mDistrictIndex).getCode();
                if (callBack != null) {
                    callBack.callback(provinceName, provinceCode, cityName, cityCode, districtName, districtCode);
                }
            }
        });
    }


    public void refreshDataByProvinceCity(String province, String city) {//根据定位的省市，刷新数据
        if (provinces==null||provinces.size()==0){
            return;
        }
        int provinceSize=provinces.size();
        for (int i=0;i<provinceSize;i++){
            ProvinceBean provinceBean=provinces.get(i);
            if (provinceBean.getName().equals(province)){
                mProvinceIndex=i;
                int citySize=provinceBean.getChildren().size();
                ArrayList<String> cityList=new ArrayList<>();
                for (int j=0;j<citySize;j++){
                    CityBean cityBean=provinceBean.getChildren().get(j);
                    cityList.add(cityBean.getName());
                    if (cityBean.getName().equals(city)){
                        mCityIndex=j;
                        updateAreas();
                    }
                }
                idCity.setItems(cityList);
                idCity.setCurrentPosition(mCityIndex);
                idProvince.setCurrentPosition(mProvinceIndex);
                break;
            }
        }
    }

    public void show(View parent) {
        mPop.show(parent, true);
    }

    public void dismiss() {
        if (mPop != null) {
            mPop.dismiss(true);
        }
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.BOTTOM);
        v.addView(bottomView);
    }

    @Override
    public void releaseView(LinearLayout v) {

    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        CityBean cityBean = provinces.get(mProvinceIndex).getChildren().get(mCityIndex);
        int districtSize = cityBean.getChildren().size();
        if (districtSize > 0) {
            ArrayList<String> districtList = new ArrayList<>();
            for (int i=0;i<districtSize;i++){
                districtList.add(cityBean.getChildren().get(i).getName());
            }
            idDistrict.setItems(districtList);
            int selectItem=idDistrict.getSelectedItem();
            if (selectItem+1>=districtList.size()){
                selectItem=0;
            }
            idDistrict.setCurrentPosition(selectItem);
            mDistrictIndex=selectItem;
        }
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        ProvinceBean provinceBean = provinces.get(mProvinceIndex);
        int citySize = provinceBean.getChildren().size();
        if (citySize > 0) {
            ArrayList<String> cityList = new ArrayList<>();
            for (int i = 0; i < citySize; i++) {
                cityList.add(provinceBean.getChildren().get(i).getName());
            }
            idCity.setItems(cityList);
            int selectItem = idCity.getSelectedItem();
            if (selectItem + 1 >= cityList.size()) {
                selectItem = 0;
            }
            idCity.setCurrentPosition(selectItem);
            mCityIndex = selectItem;
            updateAreas();
        }
    }

    public interface CallBack {
        void callback(String provinceName, int provinceCode, String cityName, int cityCode, String districtName, int districtCode);
    }

    private CallBack callBack;

    public void setCallBack(CallBack callback) {
        this.callBack = callback;
    }
}
