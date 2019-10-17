package com.d2cmall.buyer.widget.slide;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.base.BaseFragment;
import com.d2cmall.buyer.fragment.ProductFragment;

public class SlideTop extends LinearLayout implements SlideView.SnapPage {

    private Context mContext;
    private ProductFragment productFragment;

    public SlideTop(Context context) {
        this(context, null);
    }

    public SlideTop(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void init(BaseFragment parentFragment, long id,int collageId) {
        FragmentManager fm = parentFragment.getChildFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        productFragment = ProductFragment.newInstance(id,collageId);
        ft.add(R.id.top_page, productFragment);
        productFragment.setUserVisibleHint(true);
        ft.commitAllowingStateLoss();
    }

    @Override
    public View getRootView() {
        return this;
    }

    @Override
    public boolean isAtTop() {
        return true;
    }

    public void recycle(){
        productFragment.setUserVisibleHint(false);
    }

    @Override
    public boolean isAtBottom() {
        return false;
        // return productFragment.isBottom();
    }

}
