package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.d2cmall.buyer.R;
import com.d2cmall.buyer.widget.headerview.StickyListHeadersListView;

public class SideBar extends View {
    private char[] l;
    private SectionIndexer sectionIndexter = null;
    private StickyListHeadersListView list;
    private TextView mDialogText;
    private float m_nItemHeight = 16;

    public SideBar(Context context) {
        super(context);
        init();
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        l = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
                'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '#'};
    }

    public void setListView(StickyListHeadersListView _list) {
        list = _list;
        if (_list.getAdapter() instanceof HeaderViewListAdapter) {
            HeaderViewListAdapter ha = (HeaderViewListAdapter) _list.getAdapter();
            sectionIndexter = (SectionIndexer) ha.getWrappedAdapter();
        } else {
            sectionIndexter = (SectionIndexer) _list.getWrappedAdapter();
        }
    }

    public void setTextView(TextView mDialogText) {
        this.mDialogText = mDialogText;
    }

    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int i = (int) event.getY();
        int idx = (int) (i / m_nItemHeight);
        if (idx >= l.length) {
            idx = l.length - 1;
        } else if (idx < 0) {
            idx = 0;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            mDialogText.setVisibility(View.VISIBLE);
            if (l[idx] == '#') {
                mDialogText.setText("0-9");
            } else {
                mDialogText.setText(String.valueOf(l[idx]));
            }
            if (sectionIndexter == null) {
                sectionIndexter = (SectionIndexer) list.getAdapter();
            }
            int position = sectionIndexter.getPositionForSection(l[idx]);
            if (position == -1) {
                return true;
            }
            list.setSelection(position);
        } else {
            mDialogText.setVisibility(View.INVISIBLE);
        }
        return true;
    }

    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(0xff000000);
        paint.setTextSize(getResources().getDimension(R.dimen.small_text_size));
        paint.setTextAlign(Paint.Align.CENTER);
        float widthCenter = getMeasuredWidth() / 2;
        m_nItemHeight = (float) getMeasuredHeight() * 4 / 5 / 22;
        for (int i = 0; i < l.length; i++) {
            if (l[i] == '#') {
                canvas.drawText("0-9", widthCenter, (i + 1) * m_nItemHeight, paint);
            } else {
                canvas.drawText(String.valueOf(l[i]), widthCenter, (i + 1) * m_nItemHeight, paint);
            }
        }
        super.onDraw(canvas);
    }
}
