package com.d2cmall.buyer.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.bean.MaLongClassifyBean;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.loopview.LoopView;
import com.d2cmall.buyer.widget.loopview.OnItemSelectedListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;

/**
 * Name: d2c
 * 衣橱找相似
 * Copyright (c) 2016 d2cmall. All rights reserved.
 */
public class SelectTypePop implements TransparentPop.InvokeListener {


    private TextView popCancel;
    private TextView popSure;
    private LoopView idTypeFirst;
    private LoopView idSecond;
    private View rootView;
    private TransparentPop pop;
    private Context mContext;
    private Map<String, ArrayList<Map>> categoryMap;
    private ArrayList<String> categoryFirst;
    private int curSelectTypeId;
    private ArrayList<Map> curSelectSecondList;
    private SelectIdListener selectIdListener;
    private MaLongClassifyBean maLongClassifyBean;

    public SelectTypePop(Context context, Map<String, ArrayList<Map>> categoryMap, MaLongClassifyBean maLongClassifyBean) {
        this.mContext = context;
        this.categoryMap=categoryMap;
        this.maLongClassifyBean=maLongClassifyBean;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_select_type_pop, new LinearLayout(context), false);
        popCancel= (TextView) rootView.findViewById(R.id.pop_cancel);
        popSure= (TextView) rootView.findViewById(R.id.pop_sure);
        idTypeFirst= (LoopView) rootView.findViewById(R.id.id_type_first);
        idSecond= (LoopView) rootView.findViewById(R.id.id_second);
        pop = new TransparentPop(context, this);
        pop.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_up));
        pop.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_down));
        try {
            initCategory();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        init();
    }

    private void initCategory() throws JSONException {
        if(categoryMap==null){
            return;
        }
        //初始化一级分类
        categoryFirst = new ArrayList<>();
        for (Map.Entry<String, ArrayList<Map>> entry : categoryMap.entrySet()) {
            categoryFirst.add(entry.getKey());
            entry.getValue();
        }
        idTypeFirst.setItems(categoryFirst);
        //初始化二级分类
        curSelectSecondList = categoryMap.get(categoryFirst.get(0));
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < curSelectSecondList.size() ; i++) {
              String topName = (String) curSelectSecondList.get(i).get("name");
                strings.add(topName);
                if(i==0){
                    double id = (double) curSelectSecondList.get(i).get("id");
                    curSelectTypeId= (int)id;
                }
          }
        idSecond.setItems(strings);
        //对比码隆和后台分类,重复则自动选中
        contrastCategory();
    }

    private void contrastCategory() {
        if (maLongClassifyBean == null || maLongClassifyBean.getResults() == null || maLongClassifyBean.getResults().size() == 0) {
            return;
        }
        for (int i = 0; i < maLongClassifyBean.getResults().size(); i++) {
            String category = maLongClassifyBean.getResults().get(i).getCategory();
            String[] categorys = category.split("\\|");
            for (int j = 0; j < categoryFirst.size(); j++) {
                if (categorys != null && !Util.isEmpty(categorys[0]) && categorys[0].equals(categoryFirst.get(j))) {
                    ArrayList<Map> maps = categoryMap.get(categoryFirst.get(j));
                      for (int  k= 0; k < maps.size(); k++) {
                          String topName = (String) maps.get(k).get("name");
                          if(!Util.isEmpty(topName) && topName.equals(categorys[1])){
                              idTypeFirst.setCurrentPosition(j);
                              changeSecondType(categoryFirst.get(j));
                              idSecond.setCurrentPosition(k);
                              String typeName = categoryFirst.get(idTypeFirst.getSelectedItem()) + "/" + curSelectSecondList.get(idSecond.getSelectedItem()).get("name");
                              if(selectIdListener!=null){
                                  selectIdListener.selectId(curSelectTypeId,typeName);
                              }
                          }
                      }
                }
            }
        }
    }

    private ArrayList<String> changeSecondType(String firstType){
        ArrayList<Map> jsonObjects = categoryMap.get(firstType);
        curSelectSecondList=jsonObjects;
        ArrayList<String> strings = new ArrayList<>();

        try {
          for (int i = 0; i <jsonObjects.size() ; i++) {
              String topName = (String) curSelectSecondList.get(i).get("name");
              strings.add(topName);
          }
            idSecond.setItems(strings);
            double id = (double) curSelectSecondList.get(idSecond.getSelectedItem()).get("id");
            curSelectTypeId= (int)id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> secondTypes = new ArrayList<>();
        return secondTypes;
    }

    private void init() {
        popCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        popSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String typeName = categoryFirst.get(idTypeFirst.getSelectedItem()) + "/" + curSelectSecondList.get(idSecond.getSelectedItem()).get("name");
                if(selectIdListener!=null){
                    selectIdListener.selectId(curSelectTypeId,typeName);
                }
                dismiss();
            }
        });
        idTypeFirst.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                changeSecondType(categoryFirst.get(index));
            }
        });
        idSecond.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                try {
                    double id = (double) curSelectSecondList.get(index).get("id");
                    curSelectTypeId=(int)id;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拦截点击
            }
        });
    }


    @Override
    public void invokeView(LinearLayout v) {
        v.setGravity(Gravity.BOTTOM);
        v.addView(rootView);
    }

    public void show(View view) {
        pop.show(view);
    }

    public void dismiss() {
        pop.dismiss(true);
    }

    public boolean isShow() {
        return pop.isShowing();
    }

    public void setDissMissListener(TransparentPop.DismissListener dissMissListener) {
        pop.setDismissListener(dissMissListener);
    }

    public interface SelectIdListener{
        void selectId(int id,String typeName);
    }

    public void setSelectIdListener(SelectIdListener selectIdListener) {
        this.selectIdListener = selectIdListener;
    }

    @Override
    public void releaseView(LinearLayout v) {
        v.removeAllViews();
        ((ViewGroup) rootView).removeAllViews();
        rootView = null;
    }

}
