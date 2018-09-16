package com.v5ent.xiubit.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.v5ent.xiubit.R;

/**
 * 公司: tophlc
 * 类说明:
 *
 * @author yangLin
 * @time 2017/6/21
 */

public class ProductListSortHeadLayout extends LinearLayout implements View.OnClickListener {
    private TextView[] titles = new TextView[3];
    private ImageView[] imgs = new ImageView[3];
    private boolean[] isDowns = new boolean[]{false,false,false};

    private int curIndex = 0;
    private View header;

    private OnHeadTitleClickListener listener;

    public interface OnHeadTitleClickListener {
        /**
         * 功能：
         * @param index 从1开始
         * @param isDown
         */
        void headTitleClicked(int index, boolean isDown);
    }

    public ProductListSortHeadLayout(Context context) {
        super(context);
        init();
    }

    public ProductListSortHeadLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void hideFilterBtn() {
            header.findViewById(R.id.title3_ll).setVisibility(View.GONE);
            header.findViewById(R.id.line2).setVisibility(View.GONE);
    }

    private void init() {
        LayoutInflater mInflater = LayoutInflater.from(getContext());
        header = mInflater.inflate(R.layout.layout_product_list_sort_head, null, false);
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
        for (int i = 0; i < titles.length -1 ; i++) {
            int color = ContextCompat.getColor(getContext(),R.color.text_black_common);
            titles[i].setTextColor(color);
        }
        for (int i = 1; i < titles.length -1 ; i++) {
            imgs[i].setImageResource(R.drawable.img_customer_click_head_btn_normal);
        }
        if (!isInitView && curIndex == index) {
            isDowns[curIndex] = !isDowns[curIndex];
        } else {
            isDowns[curIndex] = false;
            curIndex = index;
        }
        titles[curIndex]
                .setTextColor(ContextCompat.getColor(getContext(),R.color.text_blue_common));
        if (isDowns[curIndex]) {
            imgs[curIndex].setImageResource(R.drawable.img_customer_click_head_btn_down);
        } else {
            imgs[curIndex].setImageResource(R.drawable.img_customer_click_head_btn_up);
        }
        if (!isInitView && listener != null) {
            listener.headTitleClicked(index + 1, isDowns[curIndex]);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title1_ll:
                changeView(false, 0);
                break;
            case R.id.title2_ll:
                changeView(false, 1);
                break;
            case R.id.title3_ll:
//                changeView(false, 2);
                if (!false && listener != null) {
                    listener.headTitleClicked(2 + 1, isDowns[curIndex]);
                }
                break;

            default:
                break;
        }
    }

    /**
     * 功能：
     * @param selectIndex 从1开始计算
     * 	(1:注册时间，2:直接收益，3:间接收益)
     * @param selectIsDown
     */
    public void initHeadView(int selectIndex, boolean selectIsDown) {
        this.curIndex = selectIndex - 1;
        isDowns[curIndex] = selectIsDown;
        changeView(true, curIndex);
        setFilterBtnStatus(false);
    }

    public void setListener(OnHeadTitleClickListener listener) {
        this.listener = listener;
    }

    public void setFilterBtnStatus(boolean isFilter){
        titles[2].setTextColor(ContextCompat.getColor(getContext(),isFilter?R.color.text_blue_common:R.color.text_black_common));
        imgs[2].setImageResource(isFilter?R.drawable.img_product_list_sort_press:R.drawable.img_product_list_sort);
    }
    
    
}

