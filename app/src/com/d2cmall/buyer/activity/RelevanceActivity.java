package com.d2cmall.buyer.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.api.UnbindRelevanceApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.base.BaseBean;
import com.d2cmall.buyer.bean.RelevanceAccountBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2018/1/30.
 */

public class RelevanceActivity extends BaseActivity {

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
    @Bind(R.id.iv_weixin)
    ImageView ivWeixin;
    @Bind(R.id.tv_un_bind_weixin)
    TextView tvUnBindWeixin;
    @Bind(R.id.rl_weixin)
    RelativeLayout rlWeixin;
    @Bind(R.id.iv_qq)
    ImageView ivQq;
    @Bind(R.id.tv_un_bind_qq)
    TextView tvUnBindQq;
    @Bind(R.id.rl_qq)
    RelativeLayout rlQq;
    @Bind(R.id.iv_weibo)
    ImageView ivWeibo;
    @Bind(R.id.tv_un_bind_weibo)
    TextView tvUnBindWeibo;
    @Bind(R.id.rl_weibo)
    RelativeLayout rlWeibo;
    private RelevanceAccountBean relevanceAccountBean;
    private RelevanceAccountBean.DataBean.ChildrenBean weixinBean,QQbean,WeiboBean;
    private int totalCount=0;
    private boolean isAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relevance_activity);
        ButterKnife.bind(this);
        loadRelevance();
        nameTv.setText("关联账号");
    }

    private void loadRelevance(){
        SimpleApi api=new SimpleApi();
        api.setInterPath(Constants.RELEVANCE_ACCOUNT_LIST);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RelevanceAccountBean>() {
            @Override
            public void onResponse(RelevanceAccountBean response) {
                relevanceAccountBean=response;
                showLayout();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(RelevanceActivity.this,Util.checkErrorType(error));
            }
        });
    }

    private void showLayout() {
        if(relevanceAccountBean==null){
            return;
        }
        for(int i=0;i<relevanceAccountBean.getData().getChildren().size();i++){
            RelevanceAccountBean.DataBean.ChildrenBean bean=relevanceAccountBean.getData().getChildren().get(i);
            if(bean.getSource().equals("QQ")){
                QQbean=bean;
                rlQq.setVisibility(View.VISIBLE);
            }else if(bean.getSource().equals("Weibo")){
                WeiboBean=bean;
                rlWeibo.setVisibility(View.VISIBLE);
            }else if(bean.getSource().contains("Weixin")){
                weixinBean=bean;
                rlWeixin.setVisibility(View.VISIBLE);
            }
        }
    }

    private void unBind(final String uid, final String type){
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.msg_unbind_relevance))
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UnbindRelevanceApi api=new UnbindRelevanceApi();
                        api.setUnionId(uid);
                        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<BaseBean>() {
                            @Override
                            public void onResponse(BaseBean response) {
                                switch (type){
                                    case "weixin":
                                        rlWeixin.setVisibility(View.GONE);
                                        totalCount++;
                                        break;
                                    case "QQ":
                                        rlQq.setVisibility(View.GONE);
                                        totalCount++;
                                        break;
                                    case "Weibo":
                                        rlWeibo.setVisibility(View.GONE);
                                        totalCount++;
                                        break;
                                }
                                if(totalCount==relevanceAccountBean.getData().getChildren().size()){
                                    isAll=true;
                                    setResult(RESULT_OK,new Intent().putExtra("isAll",isAll));
                                    finish();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Util.showToast(RelevanceActivity.this,Util.checkErrorType(error));
                            }
                        });
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK,new Intent().putExtra("isAll",isAll));
        finish();
    }

    @OnClick({R.id.back_iv, R.id.tv_un_bind_weixin, R.id.tv_un_bind_qq, R.id.tv_un_bind_weibo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                setResult(RESULT_OK,new Intent().putExtra("isAll",isAll));
                finish();
                break;
            case R.id.tv_un_bind_weixin:
                unBind(weixinBean.getUnionId(),"weixin");
                break;
            case R.id.tv_un_bind_qq:
                unBind(QQbean.getUnionId(),"QQ");
                break;
            case R.id.tv_un_bind_weibo:
                unBind(WeiboBean.getUnionId(),"Weibo");
                break;
        }
    }
}
