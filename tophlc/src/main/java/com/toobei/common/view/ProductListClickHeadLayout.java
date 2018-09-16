package com.toobei.common.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toobei.common.R;


/**
 * 公司: tophlc
 * 类说明：产品列表 头部点击控件
 *
 * @date 2016-2-2
 */
public class ProductListClickHeadLayout extends LinearLayout implements View.OnClickListener {
    private TextView[] titles = new TextView[3];
    private ImageView[] imgs = new ImageView[3];
    private boolean isDown = true;//倒序
    private int curIndex = 0;

    private OnHeadTitleClickListener listener;

    public interface OnHeadTitleClickListener {
        /**
         * 功能：
         *
         * @param index  从1开始
         * @param isDown
         */
        void headTitleClicked(int index, boolean isDown);
    }

    public ProductListClickHeadLayout(Context context) {
        super(context);
        init();
    }

    public ProductListClickHeadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater mInflater = LayoutInflater.from(getContext());
        View header = mInflater.inflate(R.layout.layout_product_list_click_head, null, false);
        header.findViewById(R.id.title1_ll).setOnClickListener(this);
        header.findViewById(R.id.title2_ll).setOnClickListener(this);
        header.findViewById(R.id.title3_ll).setOnClickListener(this);
        titles[0] = (TextView) header.findViewById(R.id.title_text1);
        titles[1] = (TextView) header.findViewById(R.id.title_text2);
        titles[2] = (TextView) header.findViewById(R.id.title_text3);
        imgs[0] = (ImageView) header.findViewById(R.id.title_img1);
        imgs[1] = (ImageView) header.findViewById(R.id.title_img2);
        imgs[2] = (ImageView) header.findViewById(R.id.title_img3);

        addView(header);
    }

    private void changeView(boolean isInitView, int index) {
        for (int i = 0; i < titles.length; i++) {
            int color = ContextCompat.getColor(getContext(),R.color.text_black_common);
            titles[i].setTextColor(color);
        }
        for (int i = 0; i < titles.length; i++) {
            imgs[i].setImageResource(R.drawable.img_customer_click_head_btn_normal);
        }
        if (!isInitView && curIndex == index) {
            isDown = !isDown;
        } else {
            isDown = true;
            curIndex = index;
        }
        titles[curIndex].setTextColor(ContextCompat.getColor(getContext(),R.color.text_red_common));
        if (isDown) {
            imgs[curIndex].setImageResource(R.drawable.img_customer_click_head_btn_down);
        } else {
            imgs[curIndex].setImageResource(R.drawable.img_customer_click_head_btn_up);
        }
        if (!isInitView && listener != null) {
            listener.headTitleClicked(index + 1, isDown);
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.title1_ll) {
            changeView(false, 0);
        } else if (v.getId() == R.id.title2_ll) {
            changeView(false, 1);
        } else if (v.getId() == R.id.title3_ll) {
            changeView(false, 2);
        }

    }

    /**
     * 功能：
     *
     * @param selectIndex  从1开始计算
     *                     (1:注册时间，2:直接收益，3:间接收益)
     * @param selectIsDown
     */
    public void initHeadView(int selectIndex, boolean selectIsDown) {
        this.curIndex = selectIndex - 1;
        this.isDown = selectIsDown;
        changeView(true, curIndex);
    }

    public void setListener(OnHeadTitleClickListener listener) {
        this.listener = listener;
    }

}
