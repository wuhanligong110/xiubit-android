package com.toobei.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.toobei.common.R;

import java.util.ArrayList;

/**
 * 文本自动垂直轮播
 * <p/>
 * Created by zhenguo on 3/4/15.
 */
public class AutoScrollTextView extends TextSwitcher implements ViewSwitcher.ViewFactory {

    private static final int FLAG_START_AUTO_SCROLL = 1001;
    private static final int FLAG_STOP_AUTO_SCROLL = 1002;
    public static final int FLAG_SPAN_START_AUTO_SCROLL = 1003;
    private static final int TEXT_GRAVITY_RIGHT = 0x01;
    private static final int TEXT_GRAVITY_LEFT = 0x02;
    private final boolean isUnderLine; //下划线
    private int textGravity = TEXT_GRAVITY_LEFT;  //文字的方向

    /**
     * 轮播时间间隔
     */
    private int scrollDuration = 3000;
    /**
     * 动画时间
     */
    private int animDuration = 300;

    /**
     * 文字大小
     */
    private float mTextSize = 12;
    /**
     * 文字Padding
     */
    private int mPadding = 20;
    /**
     * 文字颜色
     */
    private int textColor = Color.BLACK;

    private OnItemClickListener itemClickListener;
    private Context mContext;
    /**
     * 当前显示Item的ID
     */
    private int currentId = -1;
    private ArrayList<String> textList;
    private ArrayList<SpannableString> textSpanList = new ArrayList<>();
    private Handler handler;

    public AutoScrollTextView(Context context) {
        this(context, null);
        mContext = context;
    }

    public AutoScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.autoScrollHeight);
        mTextSize = a.getDimension(R.styleable.autoScrollHeight_autoScrolltextSize,12);
        //  mTextSize =12;
        mPadding = (int) a.getDimension(R.styleable.autoScrollHeight_padding, 20);
        scrollDuration = a.getInteger(R.styleable.autoScrollHeight_scrollDuration, 2000);
        animDuration = a.getInteger(R.styleable.autoScrollHeight_animDuration, 1000);
        isUnderLine = a.getBoolean(R.styleable.autoScrollHeight_isUnderLine, true);
        textColor = a.getColor(R.styleable.autoScrollHeight_autoScrolltextColor, Color.BLACK);
        textGravity = a.getInt(R.styleable.autoScrollHeight_textGravity, TEXT_GRAVITY_LEFT);
        //  textColor = Color.BLUE;
        a.recycle();
        init();
    }

    private void init() {
        textList = new ArrayList<String>();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case FLAG_START_AUTO_SCROLL:
                        if(handler.hasMessages( FLAG_START_AUTO_SCROLL)){
                            handler.removeMessages(FLAG_START_AUTO_SCROLL);
                        }
                        if (textList.size() > 0) {
                            currentId++;
                            String text = textList.get(currentId % textList.size());
                            SpannableString spannableString = new SpannableString(text);
                            UnderlineSpan underlineSpan = new UnderlineSpan();
                            spannableString.setSpan(underlineSpan, 0, text.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                            if (isUnderLine) {
                                //是否显示下划线
                                setText(spannableString);
                            } else {
                                setText(text);
                            }
                        }
                        handler.sendEmptyMessageDelayed(FLAG_START_AUTO_SCROLL, scrollDuration);
                        break;
                    case FLAG_SPAN_START_AUTO_SCROLL:
                        if(handler.hasMessages( FLAG_SPAN_START_AUTO_SCROLL)){
                            handler.removeMessages(FLAG_SPAN_START_AUTO_SCROLL);
                        }
                        if (textSpanList.size() > 0) {
                            currentId++;
                            SpannableString text = textSpanList.get(currentId % textSpanList.size());
                            setText(text);
                        }
                        handler.sendEmptyMessageDelayed(FLAG_SPAN_START_AUTO_SCROLL, scrollDuration);
                        break;
                    case FLAG_STOP_AUTO_SCROLL:
                        handler.removeMessages(FLAG_START_AUTO_SCROLL);
                        break;
                }
            }
        };

        setFactory(this);
        Animation in = new TranslateAnimation(0, 0, 100, 0);
        in.setDuration(animDuration);
        in.setInterpolator(new AccelerateInterpolator());
        Animation out = new TranslateAnimation(0, 0, 0, -100);
        out.setDuration(animDuration);
        out.setInterpolator(new AccelerateInterpolator());
        setInAnimation(in);
        setOutAnimation(out);
    }

    /**
     * 设置数据源
     *
     * @param titles
     */
    public void setTextList(ArrayList<String> titles) {
        textList.clear();
        textList.addAll(titles);
        currentId = -1;
    }

    /**
     * 设置数据源
     *
     * @param titles
     */
    public void setTextSpanList(ArrayList<SpannableString> titles) {
        textSpanList.clear();
        textSpanList.addAll(titles);
        currentId = -1;
    }

    /**
     * 开始轮播
     */
    public void startAutoScroll() {
        handler.sendEmptyMessage(FLAG_START_AUTO_SCROLL);
    }

    public void startAutoScroll(int flag) {
        handler.sendEmptyMessage(flag);
    }
    /**
     * 停止轮播
     */
    public void stopAutoScroll() {
        handler.sendEmptyMessage(FLAG_STOP_AUTO_SCROLL);
    }

    @Override
    public View makeView() {
        TextView t = new TextView(mContext);
        t.setGravity(Gravity.CENTER);
        t.setMaxLines(1);
        t.setPadding(mPadding, mPadding, mPadding, mPadding);
        t.setTextColor(textColor);
        t.setTextSize(mTextSize);
        t.setClickable(true);
        switch (textGravity){
            case TEXT_GRAVITY_LEFT:
                t.setGravity(Gravity.LEFT);
                break;
            case TEXT_GRAVITY_RIGHT:
                t.setGravity(Gravity.RIGHT);
                break;
            default:
                t.setGravity(Gravity.CENTER);
                break;
        }
        t.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null && textList.size() > 0 && currentId != -1) {
                    itemClickListener.onItemClick(currentId % textList.size());
                }
            }
        });

        return t;
    }

    /**
     * 设置点击事件监听
     *
     * @param itemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 轮播文本点击监听器
     */
    public interface OnItemClickListener {

        /**
         * 点击回调
         *
         * @param position 当前点击ID
         */
        public void onItemClick(int position);

    }

}