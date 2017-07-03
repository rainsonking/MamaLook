package com.tubb.calendarselector.library;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tubb.calendarselector.custom.DayViewHolder;

/**
 * Created by tubingbing on 16/4/13.
 */
public final class DefaultDayViewHolder extends DayViewHolder {

    protected TextView tvDay;
    protected LinearLayout ll_day;
    private int mPrevMonthDayTextColor;
    private int mNextMonthDayTextColor;
    private int mAttendMonthDayTextColor;
    private int mMonthDayTextColor;

    public DefaultDayViewHolder(View dayView) {
        super(dayView);
        tvDay = (TextView) dayView.findViewById(R.id.tvDay);
        ll_day = (LinearLayout) dayView.findViewById(R.id.ll_day);
        int targetSDKVersion = 0;
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            targetSDKVersion = packageInfo.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
        }

        if (targetSDKVersion >= 23) {
            mPrevMonthDayTextColor = ContextCompat.getColor(mContext, R.color.c_999999);
        } else {
            mPrevMonthDayTextColor = mContext.getResources().getColor(R.color.c_999999);
        }

        if (targetSDKVersion >= 23) {
            mNextMonthDayTextColor = ContextCompat.getColor(mContext, R.color.c_999999);
        } else {
            mNextMonthDayTextColor = mContext.getResources().getColor(R.color.c_999999);
        }
        if (targetSDKVersion >= 23) {
            mAttendMonthDayTextColor = ContextCompat.getColor(mContext, R.color.c_ffffff);
            mMonthDayTextColor = ContextCompat.getColor(mContext, R.color.c_000000);
        } else {
            mAttendMonthDayTextColor = mContext.getResources().getColor(R.color.c_ffffff);
            mMonthDayTextColor = mContext.getResources().getColor(R.color.c_000000);
        }
    }

    /**
     * @param day
     * @param attendCode 根据没有课3，请假2、已上课时1、未上课时0，设置textview
     * @param isSelected
     */

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void setCurrentMonthDayText(FullDay day, int attendCode, boolean isSelected) {
        tvDay.setText(String.valueOf(day.getDay()));
        tvDay.setSelected(isSelected);
        ll_day.setBackground(mContext.getResources().getDrawable(R.drawable.coursecard_tv_bg_date1));

        if (attendCode == 0) {
            tvDay.setBackground(mContext.getResources().getDrawable(R.drawable.shape_dayview_text_bg_selected1));
            tvDay.setTextColor(mAttendMonthDayTextColor);
        } else if (attendCode == 1) {
            tvDay.setBackground(mContext.getResources().getDrawable(R.drawable.shape_dayview_text_bg_selected));
            tvDay.setTextColor(mAttendMonthDayTextColor);
        } else if (attendCode == 2) {
            tvDay.setBackground(mContext.getResources().getDrawable(R.drawable.shape_dayview_text_bg_selected2));
            tvDay.setTextColor(mAttendMonthDayTextColor);
        } else if (attendCode == 3) {
            tvDay.setBackground(mContext.getResources().getDrawable(R.drawable.coursecard_tv_bg_date1));
            tvDay.setTextColor(mMonthDayTextColor);
        }
    }

    /**
     * @param day
     * @param attendCode
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void setAttendMonthDayText(FullDay day, int attendCode) {
        tvDay.setText(String.valueOf(day.getDay()));
        tvDay.setTextColor(mAttendMonthDayTextColor);
        ll_day.setBackground(mContext.getResources().getDrawable(R.drawable.shape_dayview_text_bg_normal));
        if (attendCode == 0) {

        } else if (attendCode == 1) {
            tvDay.setBackground(mContext.getResources().getDrawable(R.drawable.shape_dayview_text_bg_selected));
        } else if (attendCode == 2) {
//            tvDay.setBackground(mContext.getResources().getDrawable(R.drawable.shape_dayview_text_bg_selected));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void setPrevMonthDayText(FullDay day) {
        tvDay.setTextColor(mPrevMonthDayTextColor);
        tvDay.setText(String.valueOf(day.getDay()));
        ll_day.setBackground(mContext.getResources().getDrawable(R.drawable.shape_dayview_text_bg_normal));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void setNextMonthDayText(FullDay day) {
        tvDay.setTextColor(mNextMonthDayTextColor);
        tvDay.setText(String.valueOf(day.getDay()));
        ll_day.setBackground(mContext.getResources().getDrawable(R.drawable.shape_dayview_text_bg_normal));
    }

}
