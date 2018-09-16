package com.v5ent.xiubit.view.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;

import com.haibin.calendarview.BaseCalendarCardView;
import com.haibin.calendarview.Calendar;
import com.v5ent.xiubit.R;
import com.way.util.DisplayUtil;

/**
 * 简单的不带农历的日历控件
 * Created by huanghaibin on 2017/11/15.
 */

public class PaymentCalendarCardView extends BaseCalendarCardView {

    private int mRadius;
    private Paint currentDayCirclePaint;
    private Paint selectDayCirclePaint;
    private Paint schemeTextBgCirclePaint;
    private Paint schemeTextPaint;
    private final int schemeTextSize = 10;

    public PaymentCalendarCardView(Context context) {
        super(context);
        initPaint();
    }

    private void initPaint() {
        //今日
        currentDayCirclePaint = new Paint();
        currentDayCirclePaint.setAntiAlias(true);//抗锯齿
        currentDayCirclePaint.setStrokeWidth(DisplayUtil.dip2px(getContext(), 1));
        currentDayCirclePaint.setStyle(Paint.Style.STROKE);
        currentDayCirclePaint.setColor(ContextCompat.getColor(getContext(), R.color.text_blue_common));
        //被选择日期
        selectDayCirclePaint = new Paint();
        selectDayCirclePaint.setAntiAlias(true);//抗锯齿
        selectDayCirclePaint.setColor(ContextCompat.getColor(getContext(), R.color.text_blue_common));
        //标记背景
        schemeTextBgCirclePaint = new Paint();
        schemeTextBgCirclePaint.setAntiAlias(true);//抗锯齿
        schemeTextBgCirclePaint.setColor(ContextCompat.getColor(getContext(), R.color.text_red_common));
        //标记文案
        schemeTextPaint = new Paint();
        schemeTextPaint.setAntiAlias(true);
        schemeTextPaint.setTextAlign(Paint.Align.CENTER);
        schemeTextPaint.setTextSize(DisplayUtil.dip2px(getContext(), schemeTextSize));
        schemeTextPaint.setColor(ContextCompat.getColor(getContext(), R.color.text_white_common));
        schemeTextPaint.setFakeBoldText(true);

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
        float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        int schemeX = cx + mRadius;
        int schemeBaselineY = (int) (baselineY - mRadius / 2) - DisplayUtil.dip2px(getContext(), 4); //基准线，并不是文字底端，也不是文字中心
        int schemeCy =(int) (schemeBaselineY + schemeTextPaint.getFontMetrics().bottom
                - (schemeTextPaint.getFontMetrics().bottom - schemeTextPaint.getFontMetrics().top)/2)
                ;

        if (calendar.isCurrentDay() && calendar.isCurrentMonth() && !isSelected) {
            canvas.drawCircle(cx, cy, mRadius, currentDayCirclePaint);
        }

        if (isSelected &&  calendar.isCurrentMonth()) {
            canvas.drawCircle(cx, cy, mRadius, selectDayCirclePaint);

        }
        if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                            calendar.isCurrentMonth() ? calendar.isCurrentDay() ? mCurDayTextPaint :mSchemeTextPaint
                                    : mOtherMonthTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                            calendar.isCurrentMonth() ? calendar.isCurrentDay() ? mCurDayTextPaint :mCurMonthTextPaint
                                    : mOtherMonthTextPaint);
        }

        if (hasScheme && calendar.isCurrentMonth()) {

            int schemeBgHeight = DisplayUtil.dip2px(getContext(), schemeTextSize + 2);
            int schemeBgRadius = schemeBgHeight / 2;
            int textWith = (int) schemeTextPaint.measureText(calendar.getScheme());
            int schemeBgWith = schemeBgHeight;
            if (calendar.getScheme().length() >= 2) {
                schemeBgWith = textWith + DisplayUtil.dip2px(getContext(), 6);
            }
            RectF rectF = new RectF(schemeX - schemeBgWith/2, schemeCy - schemeBgHeight/2, schemeX + schemeBgWith/2, schemeCy + schemeBgHeight/2);
            canvas.drawRoundRect(rectF, schemeBgRadius, schemeBgRadius, schemeTextBgCirclePaint);
            canvas.drawText(calendar.getScheme(), schemeX, schemeBaselineY, schemeTextPaint);

        }
    }

    public PaymentCalendarCardView setClickEnable(boolean enable) {
        clickEnable = enable;
        return this;
    }
}
