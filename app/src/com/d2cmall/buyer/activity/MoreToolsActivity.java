package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.mobstat.StatService;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.ModuleGridAdapter;
import com.d2cmall.buyer.api.BaseApi;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.AppMenuBean;
import com.d2cmall.buyer.bean.ModuleBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.LineGridView;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreToolsActivity extends BaseActivity implements ModuleGridAdapter.OnModuleItemClickListener {
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
    @Bind(R.id.grid_layout)
    LineGridView gridLayout;
    private ArrayList<AppMenuBean.DataEntity.MenuEntity> menuEntities;
    private ModuleGridAdapter moduleGridAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_tools);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        nameTv.setText("更多");
        menuEntities = new ArrayList<>();
        moduleGridAdapter = new ModuleGridAdapter(this,true);
        gridLayout.setAdapter(moduleGridAdapter);
        moduleGridAdapter.setOnModuleItemClickListener(this);
        requestAppMenuTask();
    }

    private void requestAppMenuTask() {
        SimpleApi api = new SimpleApi();
        api.setMethod(BaseApi.Method.GET);
        api.setInterPath(Constants.APPMENU_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<AppMenuBean>() {
            @Override
            public void onResponse(AppMenuBean appMenuBean) {
                if (isFinishing()) {
                    return;
                }
                menuEntities.clear();
                menuEntities.addAll(appMenuBean.getData().getMenuList());
                moduleGridAdapter.addWebItem(menuEntities);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(MoreToolsActivity.this, Util.checkErrorType(error));
            }
        });
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onModuleItemClick(ModuleBean moduleBean) {
        Intent intent = null;
        int tagId = moduleBean.getTagId();

        if (tagId > 2) {
            Util.urlAction(this, moduleBean.getUrl());
        } else {
            switch (tagId) {
                case 0://T设置
                    intent = new Intent(this, TshirtActivity.class);
                    startActivity(intent);
                    break;
                case 1://商品报告
                    intent = new Intent(this, ProductReportActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }
    private void clearAllWebItem() {
        if (!menuEntities.isEmpty()) {
            for (int i = 0; i < menuEntities.size(); i++) {
                moduleGridAdapter.removeItemByTagId(8 + i);
            }
            menuEntities.clear();
        }
    }
}
