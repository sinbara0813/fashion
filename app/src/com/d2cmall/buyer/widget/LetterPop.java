package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.d2cmall.buyer.Constants;
import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.FiltrationLetterAdapter;
import com.d2cmall.buyer.api.SimpleApi;
import com.d2cmall.buyer.bean.DesignerLettreBean;
import com.d2cmall.buyer.bean.ScreenBrandBean;
import com.d2cmall.buyer.bean.ScreenNewBean;
import com.d2cmall.buyer.bean.SectionBean;
import com.d2cmall.buyer.http.BeanRequest;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.headerview.StickyListHeadersListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设计师品牌选择pop
 * Author: Blend
 * Date: 2016/11/05 12:59
 * Copyright (c) 2016 d2c. All rights reserved.
 */
public class LetterPop implements TransparentPop.InvokeListener {

    @Bind(R.id.listView)
    StickyListHeadersListView listView;
    @Bind(R.id.sideBar)
    SideBar sideBar;
    @Bind(R.id.dialog_text)
    TextView dialogText;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.tv_right)
    ImageView tvRight;
    @Bind(R.id.tv_title_label)
    TextView tvTitleLabel;

    private Context context;
    private TransparentPop mPop;
    private View rootView;
    private ArrayList<SectionBean> sectionBeans;
    private FiltrationLetterAdapter adapter;
    private List<String> selectedList;
    private List<DesignerLettreBean.DataBean.LetterBean> list;
    private ArrayList<SectionBean> selectedSectionBeans;

    public LetterPop(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_letter, new LinearLayout(context), false);
        ButterKnife.bind(this, rootView);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        mPop = new TransparentPop(context, -1, point.y - Util.getStatusHeight(context), true, this);
        Animation inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
        Animation outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
        mPop.setInAnimation(inAnimation);
        mPop.setOutAnimation(outAnimation);
        mPop.dismissFromOut();
        mPop.setRootLayoutBackground(context.getResources().getColor(R.color.transparent));
        sectionBeans = new ArrayList<>();
        selectedSectionBeans = new ArrayList<>();
        adapter = new FiltrationLetterAdapter(context, tvTitleLabel);
        sideBar.setTextView(dialogText);
        listView.setAdapter(adapter);
        sideBar.setListView(listView);
        selectedList = new ArrayList<>();
    }

    public void show(View parent) {
        mPop.show(parent, 0, Util.getStatusHeight(context), true);
    }

    public void dismiss() {
        if (mPop != null) {
            mPop.dismiss(true);
        }
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.addView(rootView);
    }

    @Override
    public void releaseView(LinearLayout v) {

    }

    private void requestDesignersTask() {
        SimpleApi api = new SimpleApi();
        api.setInterPath(Constants.DESIGNER_LETTRE_URL);
        D2CApplication.httpClient.loadingRequest(api, new BeanRequest.SuccessListener<DesignerLettreBean>() {
            @Override
            public void onResponse(DesignerLettreBean designerLettreBean) {
                progressBar.setVisibility(View.GONE);
                sideBar.setVisibility(View.VISIBLE);
                list = designerLettreBean.getData().getLetter();
                sectionBeans.clear();
                selectedSectionBeans.clear();
                for (int i = 0; i < list.size(); i++) {
                    if (designerLettreBean.getData().getLetter().get(i).getList() != null && designerLettreBean.getData().getLetter().get(i).getList().size() > 0) {
                        for (int j = 0; j < designerLettreBean.getData().getLetter().get(i).getList().size(); j++) {
                            DesignerLettreBean.DataBean.LetterBean.ListBean listEntity = designerLettreBean.getData().getLetter().get(i).getList().get(j);
                            SectionBean sectionBean = new SectionBean();
                            sectionBean.setId(listEntity.getId());
                            sectionBean.setName(listEntity.getDesigner());
                            sectionBean.setBrand(listEntity.getName());
                            sectionBean.setLettre(list.get(i).getLetter());
                            sectionBean.setGroupId(i);
                            sectionBean.setRecommend(listEntity.getRecommend());
                            if (selectedList.contains(String.valueOf(listEntity.getId()))) {
                                sectionBean.setChecked(true);
                                selectedSectionBeans.add(sectionBean);
                            } else {
                                sectionBean.setChecked(false);
                            }
                            sectionBeans.add(sectionBean);
                        }
                    }
                }
                adapter.refresh(sectionBeans, selectedSectionBeans);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Util.showToast(context, Util.checkErrorType(error));
            }
        });
    }

    public void setData(List<ScreenNewBean.DataBean.FilterBean.DesignersBean> designerSelectedList) {
        selectedList.clear();
        for (ScreenNewBean.DataBean.FilterBean.DesignersBean bean : designerSelectedList) {
            selectedList.add(String.valueOf(bean.getSourceId()));
        }
        tvTitleLabel.setText(context.getString(R.string.label_xiegang, selectedList.size(), 3));
        progressBar.setVisibility(View.VISIBLE);
        requestDesignersTask();
        listView.setSelection(0);
    }

    @OnClick({R.id.content_layout, R.id.btn_back, R.id.tv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.content_layout:
                //不处理
                break;
            case R.id.btn_back:
                dismiss();
                break;
            case R.id.tv_right:
                if (mOnSelectListener != null) {
                    mOnSelectListener.onSelected(adapter.getSelectedList());
                }
                dismiss();
                break;
        }
    }

    public interface OnSelectListener {
        void onSelected(ArrayList<SectionBean> selectList);
    }

    private OnSelectListener mOnSelectListener;

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }
}
