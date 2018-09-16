package com.toobei.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toobei.common.R;

/**
 * 公司: tophlc
 * 类说明：list为空时，显示的空白视图
 *
 * @date 2015-11-18
 */
public class ListBlankLayout extends RelativeLayout {
    private Context context;
    //	private int contentLayout;
    private View contentView, blankView, rootView;
    private View emptyView;

    //	private ViewStub mBlankStub;

    public ListBlankLayout(Context context) {
        super(context);
        this.context = context;
    }

    public ListBlankLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public ListBlankLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public ListBlankLayout(Context context, int contentLayout) {
        super(context);
        this.context = context;
        initContentView(contentLayout);
    }

    /**
     * 功能：要先初始化
     *
     * @param contentLayout
     */
    public View initContentView(int contentLayout) {
        if (contentView == null) {
            rootView = LayoutInflater.from(getContext()).inflate(R.layout.list_blank_layout, null, false);
            ViewStub mContentStub = (ViewStub) rootView.findViewById(R.id.stub_list_content);
            mContentStub.setLayoutResource(contentLayout);
            contentView = mContentStub.inflate();
            addView(rootView);
            showContentView();
        }
        return contentView;
    }

    public void showBlank(int blankTextResid) {
        if (blankView == null) {
            ViewStub mBlankStub = (ViewStub) rootView.findViewById(R.id.stub_blank);
            blankView = mBlankStub.inflate();
            TextView textView = (TextView) blankView.findViewById(R.id.text_blank);
            textView.setText(blankTextResid);
        }
        contentView.setVisibility(View.INVISIBLE);
        blankView.setVisibility(View.VISIBLE);
    }

    /**
     * 功能：设置空白时的图片
     *
     * @param blankTextResid 空白文字资源
     * @param blankImgResid  空白图片的资源
     */
    public void showBlank(int blankTextResid, int blankImgResid) {
        if (blankView == null) {
            ViewStub mBlankStub = (ViewStub) rootView.findViewById(R.id.stub_blank);
            blankView = mBlankStub.inflate();
            TextView textView = (TextView) blankView.findViewById(R.id.text_blank);
            textView.setText(blankTextResid);
            ImageView imgBlank = (ImageView) blankView.findViewById(R.id.img_blank);
            imgBlank.setImageResource(blankImgResid);
            imgBlank.setVisibility(View.VISIBLE);
        }
        contentView.setVisibility(View.INVISIBLE);
        blankView.setVisibility(View.VISIBLE);
    }

    /**
     * 功能：设置空白时的图片
     *
     * @param blankImgResid 空白图片资源
     * @param des           空白文字描述
     */
    public void showBlankImageAndText(int blankImgResid, String des) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (emptyView == null) {
            emptyView = View.inflate(context, R.layout.layout_blank_redeem_empty, null);
            ImageView imgIV = (ImageView) emptyView.findViewById(R.id.blank_img);
            TextView textIV = (TextView) emptyView.findViewById(R.id.blank_text);
            imgIV.setImageResource(blankImgResid);
            textIV.setText(des);

        }
        removeView(emptyView);
        emptyView.setVisibility(VISIBLE);
        addView(emptyView, params);

    }


    public void showBlankImageAndText(int blankImgResid, String des, boolean isTop) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (isTop){
            params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.topMargin = Util.dip2px(context,50);
        }
        if (emptyView == null) {
            emptyView = View.inflate(context, R.layout.layout_blank_redeem_empty, null);
            ImageView imgIV = (ImageView) emptyView.findViewById(R.id.blank_img);
            TextView textIV = (TextView) emptyView.findViewById(R.id.blank_text);
            imgIV.setImageResource(blankImgResid);
            textIV.setText(des);

        }
        removeView(emptyView);
        emptyView.setVisibility(VISIBLE);
        addView(emptyView, params);

    }

    /**
     * 功能：设置空白时的图片
     *
     * @param blankImgResid 空白图片资源
     * @param des           空白文字描述
     * @param listener      点击事件
     * @param btnText       按钮文字
     */
    public void showBlankImageAndTextAndButton(int blankImgResid, String des, String btnText, OnClickListener listener) {
        if (blankView == null) {
            ViewStub mBlankStub = (ViewStub) rootView.findViewById(R.id.stub_blank);
            blankView = mBlankStub.inflate();
            ImageView img = (ImageView) blankView.findViewById(R.id.img_blank);
            img.setVisibility(VISIBLE);
            img.setImageResource(blankImgResid);
            TextView textView = (TextView) blankView.findViewById(R.id.text_blank);
            textView.setText(des);
            Button button = (Button) blankView.findViewById(R.id.btn_blank);
            button.setOnClickListener(listener);
            button.setText(btnText);
            button.setVisibility(View.VISIBLE);
        }
        contentView.setVisibility(View.INVISIBLE);
        blankView.setVisibility(View.VISIBLE);

    }

    /**
     * 功能：设置空白时的文字
     *
     * @param des 空白文字
     */
    public void showBlankText(String des) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (emptyView == null) {
            emptyView = View.inflate(context, R.layout.layout_blank_redeem_empty, null);
            ImageView imgIV = (ImageView) emptyView.findViewById(R.id.blank_img);
            TextView textIV = (TextView) emptyView.findViewById(R.id.blank_text);
            imgIV.setVisibility(GONE);
            textIV.setText(des);

        }
        removeView(emptyView);
        emptyView.setVisibility(VISIBLE);
        addView(emptyView, params);

    }

    /**
     * 功能 : 设置数据空白时点击操作
     *
     * @param blankTextResid 空白提示内容
     * @param btnText        按钮显示内容
     * @param listener       按钮点击操作
     */
    public void showBlank(int blankTextResid, String btnText, OnClickListener listener) {
        if (blankView == null) {
            ViewStub mBlankStub = (ViewStub) rootView.findViewById(R.id.stub_blank);
            blankView = mBlankStub.inflate();
            TextView textView = (TextView) blankView.findViewById(R.id.text_blank);
            textView.setText(blankTextResid);
            Button button = (Button) blankView.findViewById(R.id.btn_blank);
            button.setOnClickListener(listener);
            button.setText(btnText);
            button.setVisibility(View.VISIBLE);
        }
        contentView.setVisibility(View.INVISIBLE);
        blankView.setVisibility(View.VISIBLE);

    }

    public void showBlank(String blankText) {
        if (blankView == null) {
            ViewStub mBlankStub = (ViewStub) rootView.findViewById(R.id.stub_blank);
            blankView = mBlankStub.inflate();
            TextView textView = (TextView) blankView.findViewById(R.id.text_blank);
            textView.setText(blankText);
        }
        contentView.setVisibility(View.INVISIBLE);
        blankView.setVisibility(View.VISIBLE);
    }

    public void showContentView() {
        contentView.setVisibility(View.VISIBLE);
        if (emptyView != null) {
            emptyView.setVisibility(GONE);
            removeView(emptyView);
        }
        if (blankView != null) blankView.setVisibility(View.GONE);
    }

    public View getContentView() {
        return contentView;
    }

}
