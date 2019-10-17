package com.d2cmall.buyer.widget.slide;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.fragment.ProductWebFragment;

public class SlideBottom extends LinearLayout implements SlideView.SnapPage {

    private Context context;
    private ProductWebFragment webFragment;

    public SlideBottom(Context context) {
        this(context, null);
    }

    public SlideBottom(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public void init(BaseFragment parentFragment, long id) {
        FragmentManager fm = parentFragment.getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        webFragment = ProductWebFragment.newInstance(id);
        ft.add(R.id.bottom_page, webFragment);
        ft.commitAllowingStateLoss();
    }

    @Override
    public View getRootView() {
        return this;
    }

    @Override
    public boolean isAtTop() {
        return true;
        //return webFragment.isTop();
    }

    @Override
    public boolean isAtBottom() {
        return false;
    }

    public void changeState(boolean isShow){
        webFragment.setUserVisibleHint(isShow);
    }
}
