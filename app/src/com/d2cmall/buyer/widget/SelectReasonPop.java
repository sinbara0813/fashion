package com.d2cmall.buyer.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.d2cmall.buyer.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rookie on 2017/10/18.
 */

public class SelectReasonPop implements TransparentPop.InvokeListener {
    @Bind(R.id.list_view)
    ListView listView;
    @Bind(R.id.text)
    TextView textView;
    @Bind(R.id.close)
    ImageView image;

    private View rootView;
    private TransparentPop pop;
    private List<String> promotions;
    private Context mContext;
    private CheckBox lastCheckBox;
    private String promotionName;
    private int currentPosition = -1;

    public SelectReasonPop(Context context, List<String> promotions, String promotionName) {
        this.mContext = context;
        this.promotions = promotions;
        this.promotionName = promotionName;
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_reason, new LinearLayout(context), false);
        ButterKnife.bind(this, rootView);
        textView.setTextColor(Color.parseColor("#DE000000"));
        textView.setTextSize(16);
        listView.setAdapter(new MyAdapter());
        pop = new TransparentPop(context, this);
        pop.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_in_up));
        pop.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_out_down));
    }

    public void setTitle(String string){
        textView.setText(string);
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

    @Override
    public void releaseView(LinearLayout v) {
        v.removeAllViews();
        ((ViewGroup) rootView).removeAllViews();
        rootView = null;
    }

    @OnClick(R.id.close)
    public void onViewClicked() {
        dismiss();
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return promotions == null ? 0 : promotions.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_reason_item, null);
            final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
            if (promotions.get(position).equals(promotionName)) {
                checkBox.setChecked(true);
                lastCheckBox = checkBox;
            }
            checkBox.setEnabled(false);
            TextView textView = (TextView) view.findViewById(R.id.promotion_name);
            textView.setText(promotions.get(position));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lastCheckBox != null) {
                        lastCheckBox.setChecked(false);
                    }
                    checkBox.setChecked(true);
                    promotionName = promotions.get(position);
                    currentPosition = position;
                    dismiss();
                }
            });
            return view;
        }
    }

    public String getPromotion() {
        return promotionName;
    }

    public int getPosition() {
        return currentPosition;
    }
}
