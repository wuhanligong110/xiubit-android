package com.toobei.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/11/23 0023.
 */

public class MarqueeTextView extends TextView {
    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        setSingleLine(true);
//        setEllipsize(TextUtils.TruncateAt.MARQUEE);// 设置跑马灯
//        setFocusable(true); //可以有焦点
//        setFocusableInTouchMode(true); //触摸的时候也有焦点
//        setMarqueeRepeatLimit(-1); //无限循环
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

  //  @Override
//    public void onWindowFocusChanged(boolean hasWindowFocus) {
//        super.onWindowFocusChanged(true);
//    }

//    //当同一个window内的某个控件拿到焦点之后，会执行这个方法
//    protected void onFocusChanged(boolean focused, int direction,
//                                  Rect previouslyFocusedRect) {
//        super.onFocusChanged(true, direction, previouslyFocusedRect);
//    }
    @Override
    public boolean isFocused() {//让它以为自己有焦点
        return true;
    }
}
