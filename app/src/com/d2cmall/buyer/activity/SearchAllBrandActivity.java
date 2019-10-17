package com.d2cmall.buyer.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.SearchAllBrandAdapter;
import com.d2cmall.buyer.api.SearchBrandApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.DesignerBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2018/1/30.
 */

public class SearchAllBrandActivity extends BaseActivity {

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
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    private String keyWord;
    private int page=1;
    private SearchAllBrandAdapter searchAllBrandAdapter;
    private ArrayList<DesignerBean.DataEntity.DesignersEntity.ListEntity> list;
    private LinearLayoutManager linearLayoutManager;
    private boolean hasNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_all_brand);
        ButterKnife.bind(this);
        nameTv.setText("品牌");
        keyWord=getIntent().getStringExtra("keyword");
        list=new ArrayList<>();
        searchAllBrandAdapter=new SearchAllBrandAdapter(list,this);
        linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(searchAllBrandAdapter);
        loadData();
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int last = linearLayoutManager.findLastVisibleItemPosition();
                        if (searchAllBrandAdapter != null) {
                            if (last > searchAllBrandAdapter.getItemCount() - 3 && hasNext) {
                                page++;
                                loadData();
                            }
                        }
                }
            }
        });
    }

    private void loadData(){
        SearchBrandApi api = new SearchBrandApi();
        if (Util.isEmpty(keyWord)) {
            return;
        }
        api.setKeyword(keyWord);
        api.setPageNumber(page);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<DesignerBean>() {
            @Override
            public void onResponse(DesignerBean response) {
                if(response.getData().getDesigners().getList()!=null&&response.getData().getDesigners().getList().size()>0){
                    list.addAll(response.getData().getDesigners().getList());
                }
                hasNext=response.getData().getDesigners().isNext();
                searchAllBrandAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @OnClick(R.id.back_iv)
    public void onViewClicked() {
        finish();
    }
}
