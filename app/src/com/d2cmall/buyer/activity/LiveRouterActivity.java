package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.WatchInfoBean;
import com.d2cmall.buyer.http.BeanRequest;

import butterknife.ButterKnife;

public class LiveRouterActivity extends BaseActivity {

    private long id;
    private BeanRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_router);
        ButterKnife.bind(this);
        id = getIntent().getLongExtra("id", -1);
        //((D2CApplication)getApplication()).initLiveSDK();
        requestWatchInfoTask();
    }

    private void requestWatchInfoTask() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(String.format(Constants.LIVE_STATUS_INFO, id));
        request = D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<WatchInfoBean>() {
            @Override
            public void onResponse(WatchInfoBean watchInfoBean) {
                int status = watchInfoBean.getData().getLive().getStatus();
                if (status == 0) {
                    Toast.makeText(LiveRouterActivity.this, "直播初始创建", Toast.LENGTH_LONG).show();
                } else if (status == 4) {//直播中
                    Intent intent = new Intent(LiveRouterActivity.this, LiveAudienceActivity.class);
                    intent.putExtra("bean", watchInfoBean.getData().getLive());
                    startActivity(intent);
                } else if (status == 8) {//往期回顾
                    Intent intent = new Intent(LiveRouterActivity.this, VideoActivity.class);
                    intent.putExtra("bean", watchInfoBean.getData().getLive());
                    startActivity(intent);
                } else if (status == -1) {//视频转码中
                    Intent intent = new Intent(LiveRouterActivity.this, LiveAudienceActivity.class);
                    intent.putExtra("bean", watchInfoBean.getData().getLive());
                    startActivity(intent);
                } else if (status == -3 || status == -4) {//主播丢失
                    Intent intent = new Intent(LiveRouterActivity.this, LiveLostActivity.class);
                    intent.putExtra("status", -9);
                    startActivity(intent);
                } else if (status == -2) {//直播预告
                    /*Intent intent = new Intent(LiveRouterActivity.this, LivePreviewInfoActivity.class);
                    intent.putExtra("id", watchInfoBean.getData().getLive().getId());
                    startActivity(intent);*/
                }
                finish();
                overridePendingTransition(0, 0);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Intent intent = new Intent(LiveRouterActivity.this, LiveLostActivity.class);
                intent.putExtra("status", -9);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        D2CApplication.httpClient.cancelRequest(request);
        super.onDestroy();
    }
}
