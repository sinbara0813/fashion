package com.d2cmall.buyer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.RelationProductAdapter;
import com.d2cmall.buyer.api.SelectProductApi;
import com.d2cmall.buyer.base.BaseActivity;
import com.d2cmall.buyer.bean.RelationProductBean;
import com.d2cmall.buyer.bean.SelectListBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.d2cmall.buyer.Constants.RELATION_PRODUCT_URL;

/**
 * Created by rookie on 2017/9/4.
 * 选择关联商品页面
 */

public class RelationSelectProductActivity extends BaseActivity {

    @Bind(R.id.back_iv)
    ImageView backIv;
    @Bind(R.id.name_tv)
    TextView nameTv;
    @Bind(R.id.title_right)
    TextView titleRight;
    @Bind(R.id.title_layout)
    RelativeLayout titleLayout;
    @Bind(R.id.search_layout)
    LinearLayout searchLayout;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    @Bind(R.id.edit)
    EditText edit;
    private LinearLayoutManager virtualLayoutManager;
    private DelegateAdapter delegateAdapter;
    private List<RelationProductBean.DataBean.ProductsBean.ListBean> list, hasSelectdList;
    int p = 1;
    private boolean hasNext;
    private RelationProductAdapter relationAdapter;
    private String keyword;
    private int selectedSice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation_product);
        ButterKnife.bind(this);
        nameTv.setText("关联商品");
        titleRight.setText("添加");
        hasSelectdList = new ArrayList<>();
        SelectListBean bean = (SelectListBean) getIntent().getSerializableExtra("selectedList");
        hasSelectdList.addAll(bean.getList());
        selectedSice = getIntent().getIntExtra("size", 0);
        edit.clearFocus();
        list = new ArrayList<>();
        initRecyclerView();
    }


    private void initRecyclerView() {
        virtualLayoutManager = new LinearLayoutManager(this);
        relationAdapter = new RelationProductAdapter(this, list, 0);
        relationAdapter.setHasSelectedList(hasSelectdList);
        relationAdapter.setHasSelectedCount(selectedSice);
        recycleView.setLayoutManager(virtualLayoutManager);
        recycleView.setAdapter(relationAdapter);
        edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    p = 1;
                    hideKeyboard(null);
                    edit.clearFocus();
                    virtualLayoutManager.scrollToPosition(0);
                    keyword = edit.getText().toString();
                    edit.setHint(keyword);
                    initData();
                    edit.setText("");
                    return true;
                }
                return false;
            }
        });
        initData();
    }

    private void initData() {
        SelectProductApi api = new SelectProductApi();
        if (!Util.isEmpty(keyword)) {
            api.setKeyword(keyword);
        }
        api.setPage(p);
        api.setInterPath(RELATION_PRODUCT_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<RelationProductBean>() {
            @Override
            public void onResponse(RelationProductBean response) {
                if (p == 1) {
                    list.clear();
                }
                hasNext = response.getData().getProducts().isNext();
                if (response.getData().getProducts().getList() != null && response.getData().getProducts().getList().size() != 0) {
                    list.addAll(response.getData().getProducts().getList());
                }
                if (relationAdapter.getSelectList() != null && relationAdapter.getSelectList().size() > 0) {
                    List<RelationProductBean.DataBean.ProductsBean.ListBean> selectList = relationAdapter.getSelectList();
                    for (int i = 0; i < selectList.size(); i++) {
                        RelationProductBean.DataBean.ProductsBean.ListBean selectedBean = selectList.get(i);
                        for (int j = 0; j < list.size(); j++) {
                            RelationProductBean.DataBean.ProductsBean.ListBean bean = list.get(j);
                            if (selectedBean.getId() == bean.getId()) {
                                list.get(j).setSelected(true);
                            }
                        }
                    }
                }
                relationAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Util.showToast(RelationSelectProductActivity.this, Util.checkErrorType(error));
            }
        });
    }

    @OnClick({R.id.back_iv, R.id.title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_iv:
                finish();
                break;
            case R.id.title_right:
                List<RelationProductBean.DataBean.ProductsBean.ListBean> list = relationAdapter.getSelectList();
                SelectListBean bean = new SelectListBean();
                bean.setList(list);
                Intent intent = getIntent();
                intent.putExtra("selectList", bean);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
