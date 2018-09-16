package com.toobei.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class MyVerticalViewGroup extends ViewGroup {

	Scroller mScroller;

	VelocityTracker velocityTracker;
	int SNAP_VELOCITY = 600;

	float mLastMotionY = 0;
	int mCurrentScreen = 0;
	int TOUCH_REST = 0;
	int TOUCH_SCROLL = 1;
	int TOUCH_STATE = TOUCH_REST;
	int snapToSlop = 0;
	private boolean intercepterFlag = false;

	public boolean isIntercepterFlag() {
		return intercepterFlag;
	}

	public void setIntercepterFlag(boolean intercepterFlag) {
		this.intercepterFlag = intercepterFlag;
	}

	public MyVerticalViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(context);
		snapToSlop = ViewConfiguration.get(context).getScaledEdgeSlop();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int height = 0;
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View childView = getChildAt(i);
			int childWidth = childView.getMeasuredWidth();
			int childHeight = childView.getMeasuredHeight();
			childView.layout(0, height, childWidth, height + childHeight);
			height += childHeight;
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width = measureWidth(widthMeasureSpec);
		int height = measureHeight(heightMeasureSpec);
		setMeasuredDimension(width, height);
		measureChildren(widthMeasureSpec, heightMeasureSpec);
	}

	public int measureWidth(int widthMeasureSpec) {
		int result = 0;
		int measureMode = MeasureSpec.getMode(widthMeasureSpec);
		int measureSize = MeasureSpec.getSize(widthMeasureSpec);
		switch (measureMode) {
		case MeasureSpec.AT_MOST:
		case MeasureSpec.EXACTLY:
			result = measureSize;
			break;
		default:
			break;
		}
		return result;
	}

	public int measureHeight(int heightMeasureSpec) {
		int result = 0;
		int measureMode = MeasureSpec.getMode(heightMeasureSpec); //��ȡ��View�ļ���ģʽ
		int measureSize = MeasureSpec.getSize(heightMeasureSpec);
		switch (measureMode) {
		case MeasureSpec.AT_MOST:
		case MeasureSpec.EXACTLY:
			result = measureSize;
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(intercepterFlag){
			return false;
		}
		float y = ev.getY();
		int action = ev.getAction();
		if (action == MotionEvent.ACTION_MOVE && TOUCH_STATE == TOUCH_SCROLL) {
			TOUCH_STATE = TOUCH_SCROLL;
		}
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastMotionY = y;
			TOUCH_STATE = mScroller.isFinished() ? TOUCH_REST : TOUCH_SCROLL;
			break;
		case MotionEvent.ACTION_MOVE:
			int diff = (int) (mLastMotionY - y);
			if (diff > snapToSlop) {
				TOUCH_STATE = TOUCH_SCROLL;
			}
			break;
		case MotionEvent.ACTION_UP:
			TOUCH_STATE = TOUCH_REST;
			break;

		default:
			break;
		}
		return TOUCH_STATE == TOUCH_SCROLL;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (velocityTracker == null) {
			velocityTracker = VelocityTracker.obtain();
		}
		velocityTracker.addMovement(event);
		int action = event.getAction();
		float y = event.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			System.out.println("MyVerticalViewGroup=====>ACTION_DOWN");
			if (mScroller != null) {
				if (!mScroller.isFinished()) {
					mScroller.abortAnimation();
				}
			}
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			System.out.println("MyVerticalViewGroup=====>ACTION_MOVE");
			int diff = (int) (mLastMotionY - y);
			if (diff < 0 && getScrollY() + diff < 0) {
				diff = 0 - getScrollY();
			} else if (diff > 0 && (getScrollY() + diff) > (getChildCount() - 1) * getHeight()) {//����
				diff = (getChildCount() - 1) * getHeight() - getScrollY();
			}
			scrollBy(0, diff);
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_UP:
			System.out.println("MyVerticalViewGroup=====>ACTION_UP");
			velocityTracker.computeCurrentVelocity(1000);
			int velocityY = (int) velocityTracker.getYVelocity();
			if (velocityY > SNAP_VELOCITY && mCurrentScreen > 0) {
				snapToScreen(mCurrentScreen - 1);
			} else if (velocityY < -SNAP_VELOCITY && mCurrentScreen < (getChildCount() - 1)) {
				snapToScreen(mCurrentScreen + 1);
			} else {
				snapToDistination();
			}
			if (velocityTracker != null) {
				velocityTracker.recycle();
				velocityTracker = null;
			}
			break;
		default:
			break;
		}

		return true;
	}

	@Override
	public void computeScroll() {
		super.computeScroll();

		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}

	}

	public void snapToScreen(int whichScreen) {
		mCurrentScreen = whichScreen;
		int dy = mCurrentScreen * getHeight() - getScrollY();
		mScroller.startScroll(0, getScrollY(), 0, dy, Math.abs(dy));
		postInvalidate();
	}

	public void snapToDistination() {
		int whichScreen = (getScrollY() + getHeight() / 2) / getHeight();
		snapToScreen(whichScreen);
	}

}
