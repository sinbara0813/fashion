package com.d2cmall.buyer.widget.calendarview.weiget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.d2cmall.buyer.D2CApplication;
import com.d2cmall.buyer.R;
import com.d2cmall.buyer.activity.LooksDetailActivity;
import com.d2cmall.buyer.activity.VideoRecordActivity;
import com.d2cmall.buyer.util.ScreenUtil;
import com.d2cmall.buyer.util.UniversalImageLoader;
import com.d2cmall.buyer.util.Util;
import com.d2cmall.buyer.widget.calendarview.bean.AttrsBean;
import com.d2cmall.buyer.widget.calendarview.bean.DateBean;
import com.d2cmall.buyer.widget.calendarview.listener.CalendarViewAdapter;
import com.d2cmall.buyer.widget.calendarview.listener.OnMultiChooseListener;
import com.d2cmall.buyer.widget.calendarview.listener.OnSingleChooseListener;
import com.d2cmall.buyer.widget.calendarview.utils.CalendarUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MonthView extends ViewGroup {

    private static final int ROW = 6;
    private static final int COLUMN = 7;

    private static final int COLOR_RESET = 0;//重置文字颜色
    private static final int COLOR_SET = 1;//设置文字颜色

    private Context mContext;

    private View lastClickedView;//记录上次点击的Item
    private int currentMonthDays;//记录当月天数
    private int lastMonthDays;//记录当月显示的上个月天数
    private int nextMonthDays;//记录当月显示的下个月天数

    private int item_layout;
    private CalendarViewAdapter calendarViewAdapter;
    private Set<Integer> chooseDays = new HashSet<>();//记录多选时当前页选中的日期
    private AttrsBean mAttrsBean;
    private AlertDialog alertDialog;

    public List<DateBean> getDates() {
        return dates;
    }

    private List<DateBean> dates;

    public MonthView(Context context) {
        this(context, null);
    }

    public MonthView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

        mContext = context;
        setBackgroundColor(Color.WHITE);
    }

    /**
     * @param dates            需要展示的日期数据
     * @param currentMonthDays 当月天数
     */
    public void setDateList(List<DateBean> dates, int currentMonthDays) {
        this.dates=dates;
        if (getChildCount() > 0) {
            removeAllViews();
        }
        lastMonthDays = 0;
        nextMonthDays = 0;
        boolean findSingleDate = false;//是否找到单选时默认选中的日期
        chooseDays.clear();

        this.currentMonthDays = currentMonthDays;
        for (int i = 0; i < dates.size(); i++) {
            final DateBean date = dates.get(i);

            if (date.getType() == 0) {
                lastMonthDays++;
                if (!mAttrsBean.isShowLastNext()) {
                    View view = new View(mContext);
                    view.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    addView(view, i);
                    continue;
                }
            }

            if (date.getType() == 2) {
                nextMonthDays++;
                if (!mAttrsBean.isShowLastNext()) {
                    View view = new View(mContext);
                    view.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    addView(view, i);
                    continue;
                }
            }

            View view;
            TextView solarDay;//阳历TextView
            TextView lunarDay;//阴历TextView(节假日、节气同样使用阴历TextView来显示)
            if (item_layout != 0 && calendarViewAdapter != null) {
                view = LayoutInflater.from(mContext).inflate(item_layout, null);
                TextView[] views = calendarViewAdapter.convertView(view, date);
                solarDay = views[0];
                lunarDay = views[1];
            } else {
                view = LayoutInflater.from(mContext).inflate(R.layout.item_month_layout, null);
                solarDay = (TextView) view.findViewById(R.id.solar_day);
                lunarDay = (TextView) view.findViewById(R.id.lunar_day);
            }

            if(!Util.isEmpty(date.getPic())){
                ImageView ivWear = view.findViewById(R.id.iv_day_wear);
                UniversalImageLoader.displayImage(mContext,date.getPic(),ivWear);
            }

            solarDay.setTextColor(mAttrsBean.getColorSolar());
            solarDay.setTextSize(mAttrsBean.getSizeSolar());
            lunarDay.setTextColor(mAttrsBean.getColorLunar());
            lunarDay.setTextSize(mAttrsBean.getSizeLunar());

            //设置上个月和下个月的阳历颜色
            if (date.getType() == 0 || date.getType() == 2) {
                solarDay.setTextColor(mAttrsBean.getColorLunar());
                solarDay.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            }
            solarDay.setText(String.valueOf(date.getSolar()[2]));

            //设置农历（节假日显示）
            if (mAttrsBean.isShowLunar()) {
                if ("初一".equals(date.getLunar()[1])) {
                    lunarDay.setText(date.getLunar()[0]);
                    if ("正月".equals(date.getLunar()[0]) && mAttrsBean.isShowHoliday()) {
                        lunarDay.setTextColor(mAttrsBean.getColorHoliday());
                        lunarDay.setText("春节");
                    }
                } else {
                    if (!TextUtils.isEmpty(date.getSolarHoliday()) && mAttrsBean.isShowHoliday()) {//阳历节日
                        setLunarText(date.getSolarHoliday(), lunarDay, date.getType());
                    } else if (!TextUtils.isEmpty(date.getLunarHoliday()) && mAttrsBean.isShowHoliday()) {//农历节日
                        setLunarText(date.getLunarHoliday(), lunarDay, date.getType());
                    } else if (!TextUtils.isEmpty(date.getTerm()) && mAttrsBean.isShowTerm()) {//节气
                        setLunarText(date.getTerm(), lunarDay, date.getType());
                    } else {
                        if (TextUtils.isEmpty(date.getLunar()[1])) {
                            lunarDay.setVisibility(GONE);
                        } else {
                            lunarDay.setText(date.getLunar()[1]);//农历日期
                        }
                    }
                }

            } else {
                lunarDay.setVisibility(GONE);
            }

            //找到单选时默认选中的日期，并选中（如果有）
            if (mAttrsBean.getChooseType() == 0
                    && mAttrsBean.getSingleDate() != null
                    && !findSingleDate
                    && date.getType() == 1
                    && mAttrsBean.getSingleDate()[0] == date.getSolar()[0]
                    && mAttrsBean.getSingleDate()[1] == date.getSolar()[1]
                    && mAttrsBean.getSingleDate()[2] == date.getSolar()[2]) {
                lastClickedView = view;
                setDayColor(view, COLOR_SET);
                findSingleDate = true;
            }

            //找到多选时默认选中的多个日期，并选中（如果有）
            if (mAttrsBean.getChooseType() == 1 && mAttrsBean.getMultiDates() != null) {
                for (int[] d : mAttrsBean.getMultiDates()) {
                    if (date.getType() == 1
                            && d[0] == date.getSolar()[0]
                            && d[1] == date.getSolar()[1]
                            && d[2] == date.getSolar()[2]) {
                        setDayColor(view, COLOR_SET);
                        chooseDays.add(d[2]);
                        break;
                    }
                }
            }

            //设置禁用日期
            if (date.getType() == 1) {
                view.setTag(date.getSolar()[2]);
                if (mAttrsBean.getDisableStartDate() != null
                        && (CalendarUtil.dateToMillis(mAttrsBean.getDisableStartDate()) > CalendarUtil.dateToMillis(date.getSolar()))) {
                    solarDay.setTextColor(mAttrsBean.getColorLunar());
                    lunarDay.setTextColor(mAttrsBean.getColorLunar());
                    view.setTag(-1);
                    view.findViewById(R.id.iv_day_wear).setVisibility(GONE);
                    view.setBackgroundColor(Color.parseColor("#E8E8E8"));
                    addView(view, i);
                    continue;
                }

                if (mAttrsBean.getDisableEndDate() != null
                        && (CalendarUtil.dateToMillis(mAttrsBean.getDisableEndDate()) < CalendarUtil.dateToMillis(date.getSolar()))) {
                    solarDay.setTextColor(Color.parseColor("#B2B2B2"));
                    view.findViewById(R.id.iv_day_wear).setVisibility(GONE);
                    view.findViewById(R.id.solar_day).setBackgroundColor(Color.parseColor("#E8E8E8"));
                    lunarDay.setTextColor(mAttrsBean.getColorLunar());
                    view.setTag(-1);
                    addView(view, i);
                    continue;
                }
            }

            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int day = date.getSolar()[2];
                    CalendarView calendarView = (CalendarView) getParent();
                    OnSingleChooseListener clickListener = calendarView.getSingleChooseListener();
                    OnMultiChooseListener chooseListener = calendarView.getMultiChooseListener();
                    if (date.getType() == 1) { //点击当月
                        if (mAttrsBean.getChooseType() == 1 && chooseListener != null) {//多选的情况
                            boolean flag;
                            if (chooseDays.contains(day)) {
                                setDayColor(v, COLOR_RESET);
                                chooseDays.remove(day);
                                flag = false;
                            } else {
                                setDayColor(v, COLOR_SET);
                                chooseDays.add(day);
                                flag = true;
                            }
                            calendarView.setChooseDate(day, flag, -1);
                            chooseListener.onMultiChoose(v, date, flag);
                        } else {
                            calendarView.setLastClickDay(day);
                            if (lastClickedView != null) {
                                setDayColor(lastClickedView, COLOR_RESET);
                            }
                            setDayColor(v, COLOR_SET);
                            lastClickedView = v;

                            if (clickListener != null) {
                                clickListener.onSingleChoose(v, date);
                            }
                        }
                    } else if (date.getType() == 0) {//点击上月
                        if (mAttrsBean.isSwitchChoose()) {
                            calendarView.setLastClickDay(day);
                        }
                        calendarView.lastMonth();
                        if (clickListener != null) {
                            clickListener.onSingleChoose(v, date);
                        }
                    } else if (date.getType() == 2) { //点击下月
                        if (mAttrsBean.isSwitchChoose()) {
                            calendarView.setLastClickDay(day);
                        }
                        calendarView.nextMonth();
                        if (clickListener != null) {
                            clickListener.onSingleChoose(v, date);
                        }
                    }
                    Intent intent;
                    int[] solar = date.getSolar();
                    //"2018-11-23 00:00:00"
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(solar[0]);
                    stringBuilder.append("-");
                    stringBuilder.append(solar[1]);
                    stringBuilder.append("-");
                    stringBuilder.append(solar[2]);
                    stringBuilder.append(" 00:00:00");
                    if(!Util.isEmpty(date.getPic())){
                        intent=new Intent(mContext, LooksDetailActivity.class);
                        intent.putExtra("date",stringBuilder.toString());
                        mContext.startActivity(intent);
                    }else {
                        boolean hasShowTakePhotoGuide = D2CApplication.mSharePref.getSharePrefBoolean("hasShowTakePhotoGuide", false);
                        if(!hasShowTakePhotoGuide){
                            showDialog(stringBuilder);
                            D2CApplication.mSharePref.putSharePrefBoolean("hasShowTakePhotoGuide", true);
                        }else {
                            intent=new Intent(mContext, VideoRecordActivity.class);
                            intent.putExtra("transactionTime",stringBuilder.toString());
                            intent.putExtra("channel","wardrobe");
                            mContext.startActivity(intent);
                        }

                    }

                }

            });
            addView(view, i);
        }
        requestLayout();
    }

    private void setLunarText(String str, TextView text, int type) {
        text.setText(str);
        if (type == 1) {
            text.setTextColor(mAttrsBean.getColorHoliday());
        }
        text.setTag("holiday");
    }

    private void setDayColor(View v, int type) {
        TextView solarDay = (TextView) v.findViewById(R.id.solar_day);
        TextView lunarDay = (TextView) v.findViewById(R.id.lunar_day);
        solarDay.setTextSize(mAttrsBean.getSizeSolar());
        lunarDay.setTextSize(mAttrsBean.getSizeLunar());

        if (type == 0) {
            v.setBackgroundResource(0);
            solarDay.setTextColor(mAttrsBean.getColorSolar());
            if ("holiday".equals(lunarDay.getTag())) {
                lunarDay.setTextColor(mAttrsBean.getColorHoliday());
            } else {
                lunarDay.setTextColor(mAttrsBean.getColorLunar());
            }
        } else if (type == 1) {
            solarDay.setTextColor(mAttrsBean.getColorChoose());
            lunarDay.setTextColor(mAttrsBean.getColorChoose());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        int itemWidth = widthSpecSize / COLUMN;

        //计算日历的最大高度
        if (heightSpecSize > itemWidth * ROW) {
            heightSpecSize = itemWidth * ROW;
        }

        setMeasuredDimension(widthSpecSize, heightSpecSize);

        int itemHeight = heightSpecSize / ROW;

        int itemSize = Math.min(itemWidth, itemHeight);
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            childView.measure(MeasureSpec.makeMeasureSpec(itemSize, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(itemSize, MeasureSpec.EXACTLY));
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() == 0) {
            return;
        }

        View childView = getChildAt(0);
        int width = (ScreenUtil.getDisplayWidth() - ScreenUtil.dip2px(56)-8)/7;
        int itemWidth = width;
        int itemHeight = width;
        //计算列间距
        int dx = 1;
        Log.d("LayoutitemWidth",itemWidth+"");
        //当显示五行时扩大行间距
        int dy = 1;
//        int parentHeight;
//        if (getChildCount() > 35) {
//            parentHeight = itemWidth * 6 + 7 + ScreenUtil.dip2px(20);
//        }else{
//            parentHeight= itemWidth * 5 + 6 + ScreenUtil.dip2px(20);
//        }
//        CalendarView parent = (CalendarView)getParent();
//        LayoutParams layoutParams = parent.getLayoutParams();
//        layoutParams.height=parentHeight;
//        parent.setLayoutParams(layoutParams);
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            int left = i % COLUMN * itemWidth + ((2 * (i % COLUMN) + 1)) * dx;
            int top = i / COLUMN * (itemHeight + dy);
            int right = left + itemWidth;
            int bottom = top + itemHeight;
            view.layout(left, top, right, bottom);
        }
    }

    public void refresh(int day, boolean flag) {
        if (lastClickedView != null) {
            setDayColor(lastClickedView, COLOR_RESET);
        }
        if (!flag) {
            return;
        }
        View destView = findDestView(day);
        if (destView == null) {
            return;
        }
        setDayColor(destView, COLOR_SET);
        lastClickedView = destView;
        invalidate();
    }

    /**
     * 多选时刷新日期，以恢复之前选中的日期
     *
     * @param set
     */
    public void multiChooseRefresh(HashSet<Integer> set) {
        for (Integer day : set) {
            setDayColor(findDestView(day), COLOR_SET);
            chooseDays.add(day);
        }
        invalidate();
    }

    /**
     * 查找要跳转到的页面需要展示的日期View
     *
     * @param day
     * @return
     */
    private View findDestView(int day) {
        View view = null;
        for (int i = lastMonthDays; i < getChildCount() - nextMonthDays; i++) {
            if ((Integer) getChildAt(i).getTag() == day) {
                view = getChildAt(i);
                break;
            }
        }

        if (view == null) {
            view = getChildAt(currentMonthDays + lastMonthDays - 1);
        }

        if ((Integer) view.getTag() == -1) {
            view = null;
        }
        return view;
    }

    public void setAttrsBean(AttrsBean attrsBean) {
        mAttrsBean = attrsBean;
    }

    public void setOnCalendarViewAdapter(int item_layout, CalendarViewAdapter calendarViewAdapter) {
        this.item_layout = item_layout;
        this.calendarViewAdapter = calendarViewAdapter;
    }

    private void showDialog(StringBuilder stringBuilder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = ((Activity)mContext).getLayoutInflater().inflate(R.layout.layout_guide_take_photo, null);
        TextView tvType = view.findViewById(R.id.tv_type);
        ImageView ivGuide = view.findViewById(R.id.iv_guide);
        TextView tvStep = view.findViewById(R.id.tv_step);
        TextView tvDismiss = view.findViewById(R.id.tv_dismiss);
        TextView tvNext = view.findViewById(R.id.tv_next);
        LinearLayout llAction = view.findViewById(R.id.ll_action);
        TextView tvTakePhoto = view.findViewById(R.id.tv_take_photo);
        tvDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
            }
        });
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llAction.setVisibility(View.GONE);
                tvTakePhoto.setVisibility(View.VISIBLE);
                ivGuide.setImageResource(R.mipmap.pic_pszn02);
                tvStep.setText("2/2");
                tvType.setText("背景整洁");
            }
        });
        tvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null ) {
                    alertDialog.dismiss();
                }
                Intent intent=new Intent(mContext, VideoRecordActivity.class);
                intent.putExtra("channel","wardrobe");
                intent.putExtra("transactionTime",stringBuilder.toString());
                mContext.startActivity(intent);
            }
        });
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.show();
    }
}
