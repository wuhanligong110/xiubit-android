package com.toobei.common.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.view.wheel.adapters.NumericWheelAdapter;

import org.xsl781.utils.PixelUtil;

class SelectDateNumericAdapter extends NumericWheelAdapter {
    // Index of current item
    int currentItem;
    // Index of item to be highlighted
    int currentValue;
    private int mTextSize = 18;
    private Context context;

    /**
     * Constructor
     */
    public SelectDateNumericAdapter(Context context, int minValue, int maxValue, int current) {
        super(context, minValue, maxValue);
        this.currentValue = current;
        this.context = context;
    }

    @Override
    protected void configureTextView(TextView view) {
        super.configureTextView(view);
        //	view.setTypeface(Typeface.SANS_SERIF);
        view.setTextSize(mTextSize);
        view.setHeight(PixelUtil.dip2px(context, 47));
        view.setTextColor(ContextCompat.getColor(context,R.color.BLACK));
    }

    @Override
    public CharSequence getItemText(int index) {
        currentItem = index;
        return super.getItemText(index);
    }

}