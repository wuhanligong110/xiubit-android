package com.toobei.tb.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toobei.tb.R;

public class CustomerClickHeadLayout extends LinearLayout implements View.OnClickListener {
	private TextView[] titles = new TextView[4];
	private ImageView[] imgs = new ImageView[4];
	private boolean isDown = true;//倒序
	private int curIndex = 0;

	private OnHeadTitleClickListener listener;

	public interface OnHeadTitleClickListener {
		/**
		 * 功能：
		 * @param index 从1开始
		 * @param isDown
		 */
		void headTitleClicked(int index, boolean isDown);
	}

	public CustomerClickHeadLayout(Context context) {
		super(context);
		init();
	}

	public CustomerClickHeadLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		LayoutInflater mInflater = LayoutInflater.from(getContext());
		View header = mInflater.inflate(R.layout.layout_customer_click_head, null, false);
		header.findViewById(R.id.title1_ll).setOnClickListener(this);
		header.findViewById(R.id.title2_ll).setOnClickListener(this);
		header.findViewById(R.id.title3_ll).setOnClickListener(this);
		header.findViewById(R.id.title4_ll).setOnClickListener(this);
		titles[0] = (TextView) header.findViewById(R.id.title_text1);
		titles[1] = (TextView) header.findViewById(R.id.title_text2);
		titles[2] = (TextView) header.findViewById(R.id.title_text3);
		titles[3] = (TextView) header.findViewById(R.id.title_text4);
		imgs[0] = (ImageView) header.findViewById(R.id.title_img1);
		imgs[1] = (ImageView) header.findViewById(R.id.title_img2);
		imgs[2] = (ImageView) header.findViewById(R.id.title_img3);
		imgs[3] = (ImageView) header.findViewById(R.id.title_img4);

		addView(header);
	}

	private void changeView(int index) {
		for (int i = 0; i < titles.length; i++) {
			titles[i].setTextColor(ContextCompat.getColor(getContext(),R.color.text_gray_common));
		}
		for (int i = 0; i < titles.length; i++) {
			imgs[i].setImageResource(R.drawable.img_customer_click_head_btn_normal);
		}
		if (curIndex == index) {
			isDown = !isDown;
		} else {
			isDown = true;
			curIndex = index;
		}
		titles[curIndex].setTextColor(ContextCompat.getColor(getContext(),R.color.text_blue_common));
		if (isDown) {
			imgs[curIndex].setImageResource(R.drawable.img_customer_click_head_btn_down);
		} else {
			imgs[curIndex].setImageResource(R.drawable.img_customer_click_head_btn_up);
		}
		if (listener != null) {
			listener.headTitleClicked(index + 1, isDown);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title1_ll:
			changeView(0);
			break;
		case R.id.title2_ll:
			changeView(1);
			break;
		case R.id.title3_ll:
			changeView(2);
			break;
		case R.id.title4_ll:
			changeView(3);
			break;

		default:
			break;
		}
	}

	/**
	 * 功能：
	 * @param selectIndex 从1开始计算
	 * 	1 投资额 
	 *  2 注册时间
	 *  3 投资时间
	 *  4 到期 时间
	 * @param selectIsDown
	 */
	public void initHeadView(int selectIndex, boolean selectIsDown) {
		this.curIndex = selectIndex - 1;
		this.isDown = selectIsDown;
		imgs[curIndex].setImageResource(R.drawable.img_customer_click_head_btn_down);
		titles[curIndex].setTextColor(ContextCompat.getColor(getContext(),R.color.text_blue_common));
	}

	public void setListener(OnHeadTitleClickListener listener) {
		this.listener = listener;
	}

}
