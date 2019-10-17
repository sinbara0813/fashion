package com.d2cmall.buyer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d2cmall.buyer.base.BaseTabFragment;

/**
 * Created by Administrator on 2018/9/20.
 * Description : MineSuperFragment
 *
 */

public abstract class MineSuperFragment extends BaseTabFragment {
    protected ChangeFragmentListener changeFragmentListener;
    public interface ChangeFragmentListener{
        void chaneFragment(int type);
    }
    public void setChangeFragmentListener(ChangeFragmentListener changeFragmentListener) {
        this.changeFragmentListener = changeFragmentListener;
    }
    public ChangeFragmentListener getChangeFragmentListener() {
        return changeFragmentListener;
    }

}
