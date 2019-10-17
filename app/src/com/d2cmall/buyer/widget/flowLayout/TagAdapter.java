package com.d2cmall.buyer.widget.flowLayout;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public abstract class TagAdapter<T> {
    private List<T> mTagDatas;
    private OnDataChangedListener mOnDataChangedListener;
    private LinkedHashSet<Integer> mCheckedPosList = new LinkedHashSet<>();

    public TagAdapter(List<T> datas) {
        mTagDatas = datas;
    }

    public TagAdapter(T[] datas) {
        mTagDatas = new ArrayList<T>(Arrays.asList(datas));
    }

    static interface OnDataChangedListener {
        void onChanged();
    }

    void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    public void setSelectedList(int... pos) {
        for (int i = 0; i < pos.length; i++) {
            mCheckedPosList.add(pos[i]);
        }
        notifyDataChanged();
    }

    //清空所有选中的
    public void clearSelectdList(){
        mCheckedPosList.clear();
        notifyDataChanged();
    }

    public void setSelectedList(Set<Integer> set) {

        mCheckedPosList.clear();
        if (set != null) {
            mCheckedPosList.addAll(set);
        }
        notifyDataChanged();
    }

    LinkedHashSet<Integer> getPreCheckedList() {
        return mCheckedPosList;
    }

    public int getCount() {
        return mTagDatas == null ? 0 : mTagDatas.size();
    }

    public void notifyDataChanged() {
        mOnDataChangedListener.onChanged();
    }

    public T getItem(int position) {
        return mTagDatas.get(position);
    }

    public abstract View getView(FlowLayout parent, int position, T t);

    public boolean setSelected(int position, T t) {
        return false;
    }


}