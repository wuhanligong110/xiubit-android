package com.toobei.common.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 公司: tophlc
 * 类说明:  使用自定义数字字体的TextView
 *
 * @author qingyechen
 * @time 2017/2/8 0008 下午 3:33
 */
public class MyTextView extends TextView {
    public MyTextView(Context context) {
        super(context);
        //加载自定义字体
        AssetManager assets = getContext().getAssets();
        Typeface fromAsset = Typeface.createFromAsset(assets, "fonts/DINOT-Medium.ttf");
        setTypeface(fromAsset);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //加载自定义字体
        AssetManager assets = getContext().getAssets();
        Typeface fromAsset = Typeface.createFromAsset(assets, "fonts/DINOT-Medium.ttf");
        setTypeface(fromAsset);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //加载自定义字体
        AssetManager assets = getContext().getAssets();
        Typeface fromAsset = Typeface.createFromAsset(assets, "fonts/DINOT-Medium.ttf");
        setTypeface(fromAsset);
    }
}
