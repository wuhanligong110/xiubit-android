package org.xsl781.ui.xlist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xsl781.R;

public class XListViewFooter extends LinearLayout {

	// 正常状态
	public final static int STATE_NORMAL = 0;
	// 准备状态
	public final static int STATE_READY = 1;
	// 加载状态
	public final static int STATE_LOADING = 2;

	private View mContentView;
	private View mProgressBar;
	private TextView mHintView;
	private boolean isAutoLoadMore = true;

	public XListViewFooter(Context context) {
		super(context);
		initView(context);
	}

	public XListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {

		LinearLayout moreView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.xsl781_xlistview_footer, null);

		addView(moreView);
		moreView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

		mContentView = moreView.findViewById(R.id.xlistview_footer_content);
		mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar);
		mHintView = (TextView) moreView.findViewById(R.id.xlistview_footer_hint_textview);
	}

	/**
	 * 设置当前的状态
	 * 
	 * @param state
	 */
	public void setState(int state) {

		mProgressBar.setVisibility(View.INVISIBLE);
		mHintView.setVisibility(View.INVISIBLE);

		switch (state) {
		case STATE_READY:
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.xlistview_footer_hint_ready);
			break;

		case STATE_NORMAL:
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.xlistview_footer_hint_normal);
			break;

		case STATE_LOADING:
			mProgressBar.setVisibility(View.VISIBLE);
			break;

		}
		if(isAutoLoadMore ){
			mHintView.setVisibility(View.GONE);
		}

	}

	public void setBottomMargin(int height) {
		if (height > 0) {

			LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView
					.getLayoutParams();
			lp.bottomMargin = height;
			mContentView.setLayoutParams(lp);
		}
	}

	public int getBottomMargin() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
		return lp.bottomMargin;
	}

	public void hide() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
		lp.height = 0;
		mContentView.setLayoutParams(lp);
	}

	public void show() {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mContentView.setLayoutParams(lp);
	}

	public void setAutoLoadMore(boolean isAutoLoadMore) {
		this.isAutoLoadMore = isAutoLoadMore;
	}

}
