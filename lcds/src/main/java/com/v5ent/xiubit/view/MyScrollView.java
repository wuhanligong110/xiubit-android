package com.v5ent.xiubit.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.toobei.common.view.MyVerticalViewGroup;

/**
 * 可以监听ScrollView的上下滑动 ，实现ScrollListener接口，调用setScrollListener(ScrollListener
 * l)方法。 SCROLL_UP :ScrollView正在向上滑动 SCROLL_DOWN :ScrollView正在向下滑动
 * 
 * @author xm
 */
@SuppressLint("NewApi")
public class MyScrollView extends ScrollView {
	private ScrollViewListener scrollViewListener = null;

	private int downX;
	private int downY;
	private int upX;
	private int upY;

	private MyVerticalViewGroup myVerticalView;

	public MyScrollView(Context context) {
		super(context, null);
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
	}

	public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyVerticalViewGroup getMyVerticalView() {
		return myVerticalView;
	}

	public void setMyVerticalView(MyVerticalViewGroup myVerticalView) {
		this.myVerticalView = myVerticalView;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = (int) ev.getX();
			downY = (int) ev.getY();
			System.out.println("ACTION_DOWN===>" + "downX====>" + downX + "downY=====>" + downY);
			break;
		case MotionEvent.ACTION_MOVE:
			//			int y = (int) ev.getY();
			//			int deltaY = (int) (y - downY);// 滑动距离   
			//			scrollTo(0, deltaY);
			break;

		case MotionEvent.ACTION_UP:
			upX = (int) ev.getX();
			upY = (int) ev.getY();
			System.out.println("ACTION_UP===>" + "upX====>" + upX + "upY=====>" + upY);
			if (Math.abs(upY - downY) > Math.abs(upX - downX) && getScrollY() == 0
					&& myVerticalView != null) {
				myVerticalView.snapToScreen(0);
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	public void setScrollViewListener(ScrollViewListener scrollViewListener) {
		this.scrollViewListener = scrollViewListener;
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		if (scrollViewListener != null) {
			scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
		}
	}

	public interface ScrollViewListener {

		void onScrollChanged(MyScrollView scrollView, int x, int y,
							 int oldx, int oldy);

	}

}
