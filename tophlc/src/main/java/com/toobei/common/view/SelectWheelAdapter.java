package com.toobei.common.view;

import android.content.Context;
import android.widget.TextView;

import com.toobei.common.view.wheel.adapters.AbstractWheelTextAdapter;

import org.xsl781.utils.PixelUtil;

import java.util.List;

public abstract class SelectWheelAdapter extends AbstractWheelTextAdapter {
    private List datas;
    private int mTextSize = 18;
    private Context context;

    public SelectWheelAdapter(Context context, List datas) {
        super(context);
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getItemsCount() {
        return datas.size();
    }

    public abstract CharSequence getItemText(int index);

    @Override
    protected void configureTextView(TextView view) {
        super.configureTextView(view);
        view.setTextSize(mTextSize);
        view.setHeight(PixelUtil.dip2px(context, 47));
    }

}