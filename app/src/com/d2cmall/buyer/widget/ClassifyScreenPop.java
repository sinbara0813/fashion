package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.adapter.ClassifyScreenAdapter;
import com.d2cmall.buyer.bean.ClassifyScreenBean;
import com.d2cmall.buyer.bean.ScreenNewBean;
import com.d2cmall.buyer.bean.SectionBean;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.TransparentPop.InvokeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2018/3/23.
 */

public class ClassifyScreenPop implements InvokeListener {

    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.iv_over)
    ImageView ivOver;
    @Bind(R.id.rl_top)
    RelativeLayout rlTop;
    @Bind(R.id.recycle_view)
    RecyclerView recycleView;
    private Context context;
    private TransparentPop mPop;
    private View rootView;
    private List<ScreenNewBean.DataBean.FilterBean.TopsBean> list;
    private List<ScreenNewBean.DataBean.FilterBean.CategorysBean> selectList;
    private ClassifyScreenAdapter adapter;

    public ClassifyScreenPop(Context context, List<ScreenNewBean.DataBean.FilterBean.TopsBean> list) {
        this.context = context;
        this.list = new ArrayList<>(list);
        init();
    }

    public void setData(List<ScreenNewBean.DataBean.FilterBean.TopsBean> list) {

    }

    private void init() {
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_classify_screen, new LinearLayout(context), false);
        ButterKnife.bind(this, rootView);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        selectList = new ArrayList<>();
        mPop = new TransparentPop(context, -1, point.y - Util.getStatusHeight(context), true, this);
        adapter = new ClassifyScreenAdapter(context, list);
        recycleView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recycleView.setAdapter(adapter);
        Animation inAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
        Animation outAnimation = AnimationUtils.loadAnimation(context, R.anim.slide_out_right);
        mPop.setInAnimation(inAnimation);
        mPop.setOutAnimation(outAnimation);
        mPop.setDismissListener(new TransparentPop.DismissListener() {
            @Override
            public void dismissStart() {
                selectList.clear();
                selectList.addAll(adapter.getSelectedList());
                if (mOnSelectListener != null) {
                    mOnSelectListener.onSelected(selectList);
                }
            }

            @Override
            public void dismissEnd() {

            }
        });
    }

    public void setSelectedList(List<ScreenNewBean.DataBean.FilterBean.CategorysBean> selectList) {
        adapter.setSelectedList(selectList);
    }


    public void show(View parent) {
        mPop.show(parent, 0, Util.getStatusHeight(context), true);
    }

    @Override
    public void invokeView(LinearLayout v) {
        v.addView(rootView);
    }

    @Override
    public void releaseView(LinearLayout v) {

    }

    public interface OnSelectOverListener {
        void onSelected(List<ScreenNewBean.DataBean.FilterBean.CategorysBean> selectList);
    }

    private OnSelectOverListener mOnSelectListener;

    public void setOnSelectListener(OnSelectOverListener onSelectListener) {
        mOnSelectListener = onSelectListener;
    }

    @OnClick({R.id.iv_close, R.id.iv_over})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
            case R.id.iv_over:
                mPop.dismiss(true);
                break;
        }
    }
}
