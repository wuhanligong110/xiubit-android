package com.v5ent.xiubit.view.calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;

import com.haibin.calendarview.BaseCalendarCardView;
import com.haibin.calendarview.Calendar;
import com.v5ent.xiubit.R;

/**
 * 简单的不带农历的日历控件
 * Created by huanghaibin on 2017/11/15.
 */

public class SignCalendarCardView extends BaseCalendarCardView {

    private int mRadius;
    private Bitmap selectBgBitMap;

    public SignCalendarCardView(Context context) {
        super(context);
        selectBgBitMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.sign_calendar_select_bg);
        clickEnable = false;
    }

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;
    }

    @Override
    protected void onLoopStart(int x, int y) {

    }

    @Override
    protected void onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {

    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {

    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        if (hasScheme && calendar.isCurrentMonth()){
            int bx = x + (mItemWidth - selectBgBitMap.getWidth()) / 2;
            int by = y + (mItemHeight - selectBgBitMap.getHeight()) / 2;
            canvas.drawBitmap(selectBgBitMap, bx, by, new Paint());
        }
        float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;
        mCurDayTextPaint.setColor(ContextCompat.getColor(getContext(),R.color.text_blue_common));
        if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                            calendar.isCurrentMonth() ? calendar.isCurrentDay() ? mCurDayTextPaint: mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                            calendar.isCurrentMonth() ? calendar.isCurrentDay() ? mCurDayTextPaint :mCurMonthTextPaint
                                    : mOtherMonthTextPaint);
        }
    }

    public SignCalendarCardView setClickEnable(boolean enable) {
        clickEnable = enable;
        return this;
    }
}
