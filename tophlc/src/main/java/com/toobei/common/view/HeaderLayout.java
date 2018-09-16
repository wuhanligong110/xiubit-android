package com.toobei.common.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toobei.common.R;
import com.toobei.common.TopApp;

import org.xsl781.utils.PixelUtil;
import org.xsl781.utils.SystemTool;

public class HeaderLayout extends LinearLayout {
    LayoutInflater mInflater;
    RelativeLayout header;
    TextView titleView;
    LinearLayout leftContainer, rightContainer;
    ImageView backBtn;
    int themeType = 1;  // 1蓝色主题  2 白色主题
    private RelativeLayout headRl;
    private ImageButton mRightImageButton;
    private TextView leftTv;

    public HeaderLayout(Context context) {
        super(context);
        init();
    }

    public HeaderLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        themeType = 1;
        mInflater = LayoutInflater.from(getContext());
        header = (RelativeLayout) mInflater.inflate(R.layout.common_header, null, false);
        titleView = (TextView) header.findViewById(R.id.titleView);
        leftContainer = (LinearLayout) header.findViewById(R.id.leftContainer);
        rightContainer = (LinearLayout) header.findViewById(R.id.rightContainer);
        leftTv = (TextView) header.findViewById(R.id.leftTv);
        headRl = (RelativeLayout) header.findViewById(R.id.headRl);
        backBtn = (ImageView) header.findViewById(R.id.backBtn);
        headRl.setBackgroundResource(TopApp.getInstance().getHeadBgColor());
        addView(header);
    }

    public void setWhiteTheme(){
        titleView.setTextColor(TopApp.getInstance().getResources().getColor(R.color.text_black_common));
        headRl.setBackgroundColor(Color.parseColor("#ffffff"));
        backBtn.setImageResource(R.drawable.btn_back_black);
        themeType = 2;
    }

    public void setHeadViewCoverStateBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getLayoutParams().height = getHeadHeight();
        }
    }

    public int getHeadHeight(){
        return PixelUtil.dip2px(getContext(), 44) + SystemTool.getStatusBarHeight(getContext());
    }

    public ImageView showTitleDownIcon(OnClickListener listener) {
        ImageView imgTitleDown = (ImageView) header.findViewById(R.id.img_title_down);
        imgTitleDown.setVisibility(View.VISIBLE);
        header.findViewById(R.id.centerContainer).setOnClickListener(listener);
        return imgTitleDown;
    }

    /**
     * 显示带问号的title
     *
     * @param imgId    要显示的图标
     * @param listener listener
     * @return 要显示的图标
     */
    public ImageView showTitleQuestion(int imgId, OnClickListener listener) {
        ImageView imgTitleDown = (ImageView) header.findViewById(R.id.img_title_question);
        imgTitleDown.setImageResource(imgId);
        imgTitleDown.setVisibility(View.VISIBLE);
        imgTitleDown.setOnClickListener(listener);
        return imgTitleDown;
    }

    public void showTitle(int titleId) {
        titleView.setText(titleId);
    }

    public TextView showTitle(String s) {
        titleView.setText(s);
        return titleView;
    }


    public LinearLayout showLeftBackButton(OnClickListener listener) {
        return showLeftBackButton(R.string.emptyStr, listener);
    }

    public LinearLayout showLeftBackButton() {
        return showLeftBackButton(null);
    }

    public LinearLayout showLeftBackButton(int backTextId, OnClickListener listener) {
        backBtn.setVisibility(View.VISIBLE);
        leftTv.setText(backTextId);
        if (listener == null) {
            listener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((Activity) getContext()).finish();
                }
            };
        }

        backBtn.setOnClickListener(listener);
        return leftContainer;
    }

    public void hideLeftBackButton(){backBtn.setVisibility(View.INVISIBLE);}

    public ImageButton showRightImageButton(int rightResId, OnClickListener listener) {
        View imageViewLayout = mInflater.inflate(R.layout.header_right_image_btn, null, false);
        mRightImageButton = (ImageButton) imageViewLayout.findViewById(R.id.imageBtn);
        mRightImageButton.setImageResource(rightResId);
        mRightImageButton.setClickable(false);
        rightContainer.setOnClickListener(listener);
        setRightView(imageViewLayout);
        rightContainer.setClickable(true);
        return mRightImageButton;
    }

    public ImageButton addRightImageButton(int rightResId, OnClickListener listener) {
        View imageViewLayout = mInflater.inflate(R.layout.header_right_image_btn, null, false);
        ImageButton rightImageButton = (ImageButton) imageViewLayout.findViewById(R.id.imageBtn);
        rightImageButton.setImageResource(rightResId);
        rightImageButton.setClickable(true);
        rightImageButton.setOnClickListener(listener);
        addRightView(imageViewLayout);
        return rightImageButton;
    }

    private void addRightView(View view) {
        rightContainer.addView(view);
    }


    /**
     * 功能：返回右边textView
     *
     * @param rightResId
     * @param listener
     * @return
     */
    public TextView showRightTextButton(int rightResId, OnClickListener listener) {
        View imageViewLayout = mInflater.inflate(R.layout.header_right_btn, null, false);
        TextView rightButton = (TextView) imageViewLayout.findViewById(R.id.textBtn);
        rightButton.setBackgroundColor(Color.TRANSPARENT);
        rightButton.setTextColor(themeType == 1?TopApp.getInstance().getResources().getColor(R.color.text_white_common): TopApp.getInstance().getResources().getColor(R.color.text_black_common));
        rightButton.setText(rightResId);
        rightButton.setClickable(false);
        rightContainer.setOnClickListener(listener);
        setRightView(imageViewLayout);
        rightContainer.setClickable(true);
        return rightButton;
    }

    private void setRightView(View imageViewLayout) {
        removeRightView();
        rightContainer.addView(imageViewLayout);
    }

    /**
     * 功能：返回右边textView
     *
     * @param rightText
     * @param listener
     * @return
     */
    public TextView showRightTextButton(String rightText, OnClickListener listener) {
        View imageViewLayout = mInflater.inflate(R.layout.header_right_btn, null, false);
        TextView rightButton = (TextView) imageViewLayout.findViewById(R.id.textBtn);
        rightButton.setBackgroundColor(Color.TRANSPARENT);
        rightButton.setTextColor(themeType == 1?TopApp.getInstance().getResources().getColor(R.color.text_white_common): TopApp.getInstance().getResources().getColor(R.color.text_black_common));
        rightButton.setText(rightText);
        rightButton.setClickable(false);
        rightContainer.setOnClickListener(listener);
        setRightView(imageViewLayout);
        rightContainer.setClickable(true);
        return rightButton;
    }

    public ImageButton getRightImageButton() {
        if (mRightImageButton != null) {
            return mRightImageButton;
        }

        View imageViewLayout = mInflater.inflate(R.layout.header_right_image_btn, null, false);
        return (ImageButton) imageViewLayout.findViewById(R.id.imageBtn);
    }

    public void removeRightView() {
        if (rightContainer.getChildCount() > 0) {
            rightContainer.removeAllViews();
            rightContainer.setClickable(false);
        }
    }

}
