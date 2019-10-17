package com.d2cmall.buyer.Decoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.d2cmall.buyer.adapter.TestAdapter;

/**
 * Created by rookie on 2017/10/12.
 */

public class CatagoryDecoration extends RecyclerView.ItemDecoration{

    private int spanCount;
    private int spacing;
    private boolean includeEdge;
    private TestAdapter categoryAdapter;

    public CatagoryDecoration(int spanCount, int spacing, boolean includeEdge, TestAdapter categoryAdapter) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
        this.categoryAdapter=categoryAdapter;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        if(categoryAdapter.getItemViewType(position)==1) {

            int column = (position-1) % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    }

