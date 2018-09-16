package org.xsl781.ui.xlist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xsl781.R;

public class XListViewHeader extends LinearLayout {
	// 正常状态
	public final static int STATE_NORMAL = 0;
	// 准备刷新状态，也就是箭头方向发生改变之后的状态
	public final static int STATE_READY = 1;
	// 刷新状态，箭头变成了progressBar
	public final static int STATE_REFRESHING = 2;
	// 布局容器，也就是根布局
	private LinearLayout container;
	// 箭头图片
	private ImageView mArrowImageView;
	// 刷新状态显示
	private ProgressBar mProgressBar;
	// 说明文本
	private TextView mHintTextView;
	private TextView mLastUpdateTime;//最近更新时间
	private ViewGroup headTimeLL;
	// 记录当前的状态
	private int mState;
	// 用于改变箭头的方向的动画
	private Animation mRotateUpAnim;
	private Animation mRotateDownAnim;
	// 动画持续时间
	private final int ROTATE_ANIM_DURATION = 180;

	public XListViewHeader(Context context) {
		super(context);
		initView(context);
	}

	public XListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		mState = STATE_NORMAL;
		// 初始情况下，设置下拉刷新view高度为0
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
		container = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.xsl781_xlistview_header,
				null);
		addView(container, lp);
		// 初始化控件
		mArrowImageView = (ImageView) findViewById(R.id.xlistview_header_arrow);
		mHintTextView = (TextView) findViewById(R.id.xlistview_header_hint_textview);
		mLastUpdateTime = (TextView) findViewById(R.id.xlistview_header_time);
		headTimeLL = (ViewGroup) findViewById(R.id.xlistview_header_time_ll);

		mProgressBar = (ProgressBar) findViewById(R.id.xlistview_header_progressbar);
		// 初始化动画
		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
	}

	// 设置header的状态
	public void setState(int state) {
		if (state == mState)
			return;

		// 显示进度
		if (state == STATE_REFRESHING) {
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.INVISIBLE);
			mProgressBar.setVisibility(View.VISIBLE);
		} else {
			// 显示箭头
			mArrowImageView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.INVISIBLE);
		}

		switch (state) {
		case STATE_NORMAL:
			if (mState == STATE_READY) {
				mArrowImageView.startAnimation(mRotateDownAnim);
			}
			if (mState == STATE_REFRESHING) {
				mArrowImageView.clearAnimation();
			}
			mHintTextView.setText(R.string.xlistview_header_hint_normal);
			break;
		case STATE_READY:
			if (mState != STATE_READY) {
				mArrowImageView.clearAnimation();
				mArrowImageView.startAnimation(mRotateUpAnim);
				mHintTextView.setText(R.string.xlistview_header_hint_ready);
			}
			break;
		case STATE_REFRESHING:
			mHintTextView.setText(R.string.xlistview_header_hint_loading);
			break;
		default:
		}

		mState = state;
	}

	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) container.getLayoutParams();
		lp.height = height;
		container.setLayoutParams(lp);
	}

	public int getVisiableHeight() {
		return container.getHeight();
	}

	public void show() {
		container.setVisibility(View.VISIBLE);
	}

	public void hide() {
		container.setVisibility(View.INVISIBLE);
		headTimeLL.setVisibility(View.GONE);
	}

	public void setLastUpdateTime(String updateTime) {
		if (mState == STATE_REFRESHING) {
			headTimeLL.setVisibility(View.VISIBLE);
			mLastUpdateTime.setText(updateTime);
		}
	}
}
