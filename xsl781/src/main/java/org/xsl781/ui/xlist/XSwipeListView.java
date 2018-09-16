package org.xsl781.ui.xlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class XSwipeListView extends XListView {

	private Boolean mIsHorizontal;
	private View mPreItemView;
	private View mCurrentItemView;
	private float mFirstX;
	private float mFirstY;
	private int mRightViewWidth;
	// private boolean mIsInAnimation = false;
	private final int mDuration = 100;
	private final int mDurationStep = 10;
	private boolean mIsShown;
	private int swipleViewId;//滑动视图的id
	private boolean isFirstItemNoSwiple = true;//首条不滑动

	public XSwipeListView(Context context) {
		super(context);
	}

	public XSwipeListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public XSwipeListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		float lastX = ev.getX();
		float lastY = ev.getY();
		int motionPosition = pointToPosition((int) lastX, (int) lastY);
		
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
//			mFirstX=ev.getX();
//			mFirstY=ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:

			float dx = lastX - mFirstX;
			float dy = lastY - mFirstY;
			if (Math.abs(dy)<Math.abs(dx)&&dx<0&&isFirstItemNoSwiple && motionPosition == 1) {
				return true;
			}
			// confirm is scroll direction
			if (mIsHorizontal == null) {
				if (!judgeScrollDirection(dx, dy)) {
					break;
				}
			}
			
			if (mIsHorizontal) {
				
				if (mIsShown && mPreItemView != mCurrentItemView) {
					System.out.println("2---> hiddenRight");
					/**
					 * 情况二：
					 * <p>
					 * 一个Item的右边布局已经显示，
					 * <p>
					 * 这时候左右滑动另外一个item,那个右边布局显示的item隐藏其右边布局
					 * <p>
					 * 向左滑动只触发该情况，向右滑动还会触发情况五
					 */
					hiddenRight(mPreItemView);
				}

				if (mIsShown && mPreItemView == mCurrentItemView) {
					dx = dx - mRightViewWidth;
				//	System.out.println("======dx " + dx);
				}

				// can't move beyond boundary
				if (dx < 0 && dx > -mRightViewWidth && mCurrentItemView != null) {
					mCurrentItemView.scrollTo((int) (-dx), 0);
				}

					return true;
			} else {
				if (mIsShown) {
				//	System.out.println("3---> hiddenRight");
					/**
					 * 情况三：
					 * <p>
					 * 一个Item的右边布局已经显示，
					 * <p>
					 * 这时候上下滚动ListView,那么那个右边布局显示的item隐藏其右边布局
					 */
					hiddenRight(mPreItemView);
				}
			}

			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
		//	System.out.println("============ACTION_UP");
			clearPressedState();

			if (mIsShown) {
			//	System.out.println("4---> hiddenRight");
				/**
				 * 情况四：
				 * <p>
				 * 一个Item的右边布局已经显示，
				 * <p>
				 * 这时候左右滑动当前一个item,那个右边布局显示的item隐藏其右边布局
				 */
				hiddenRight(mPreItemView);
			}

			if (mIsHorizontal != null && mIsHorizontal) {
				if (mFirstX - lastX > mRightViewWidth / 2) {
					showRight(mCurrentItemView);
				} else {
				//	System.out.println("5---> hiddenRight");
					/**
					 * 情况五：
					 * <p>
					 * 向右滑动一个item,且滑动的距离超过了右边View的宽度的一半，隐藏之。
					 */
					hiddenRight(mCurrentItemView);
				}

					return true;
			}

			break;

		default:

			break;
		}
		//	return false;
		return super.onTouchEvent(ev);
	}

	/**
	* return true, deliver to listView. return false, deliver to child. if
	* move, return true
	*/
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		float lastX = ev.getX();
		float lastY = ev.getY();
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mIsHorizontal = null;
		//	System.out.println("onInterceptTouchEvent----->ACTION_DOWN");
			mFirstX = lastX;
			mFirstY = lastY;
			int motionPosition = pointToPosition((int) mFirstX, (int) mFirstY);
			if (motionPosition >= 0) {
				View currentItemView = getChildAt(motionPosition - getFirstVisiblePosition());
				mPreItemView = mCurrentItemView;
				//如果设置了滑动视图id
				if (swipleViewId > 0) {
					mCurrentItemView = currentItemView.findViewById(swipleViewId);
				}
				if (swipleViewId == 0 || mCurrentItemView == null) {
					mCurrentItemView = currentItemView;
				}
			}
			break;

		case MotionEvent.ACTION_MOVE:
			/*float dx = lastX - mFirstX;
			float dy = lastY - mFirstY;
			// confirm is scroll direction
			if (mIsHorizontal == null) {
				if (judgeScrollDirection(dx, dy)) {
					if (Math.abs(dx) >= 5 && Math.abs(dy) >= 5) {
						return true;
					}
				}
			}
			
			break;*/

		case MotionEvent.ACTION_UP:
			/*System.out.println("手指抬起了------------>");
			if (lastX == mFirstX && lastY == mFirstY) {
					hiddenRight(mPreItemView);
			}
			break;*/
		case MotionEvent.ACTION_CANCEL:
		//	System.out.println("onInterceptTouchEvent----->ACTION_UP");
			if (mIsShown && (mPreItemView != mCurrentItemView || isHitCurItemLeft(lastX))) {
		//		System.out.println("1---> hiddenRight");
				/**
				 * 情况一：
				 * <p>
				 * 一个Item的右边布局已经显示，
				 * <p>
				 * 这时候点击任意一个item, 那么那个右边布局显示的item隐藏其右边布局
				 */
				hiddenRight(mPreItemView);
			}

			break;
		}

		return super.onInterceptTouchEvent(ev);
	}

	private boolean isHitCurItemLeft(float x) {
		return x < getWidth() - mRightViewWidth;
	}

	/**
	* @param dx
	* @param dy
	* @return 判断滑动方向 
	*/
	private boolean judgeScrollDirection(float dx, float dy) {
		boolean canJudge = true;

		if (Math.abs(dx) > 30 && Math.abs(dx) > 2 * Math.abs(dy)) {
			mIsHorizontal = true;
			//   System.out.println("mIsHorizontal---->" + mIsHorizontal);
		} else if (Math.abs(dy) > 30 && Math.abs(dy) > 2 * Math.abs(dx)) {
			mIsHorizontal = false;
			//    System.out.println("mIsHorizontal---->" + mIsHorizontal);
		} else {
			canJudge = false;
		}

		return canJudge;
	}

	private void clearPressedState() {
		if (mCurrentItemView != null)
			mCurrentItemView.setPressed(false);
		setPressed(false);
		refreshDrawableState();
		// invalidate();
	}

	private void showRight(View view) {
		if(view == null)
			return;
		Message msg = new MoveHandler().obtainMessage();
		msg.obj = view;
		msg.arg1 = view.getScrollX();
		msg.arg2 = mRightViewWidth;
		msg.sendToTarget();
		mIsShown = true;
	}

	private void hiddenRight(View view) {
	//	System.out.println("=========hiddenRight");
		if (mCurrentItemView == null) {
			return;
		}
		Message msg = new MoveHandler().obtainMessage();//
		msg.obj = view;
		msg.arg1 = view.getScrollX();
		msg.arg2 = 0;
		msg.sendToTarget();
		mIsShown = false;
	}

	/**
	 * 功能：为了删除当前条目时候，防止下面的条目显示右边菜单
	 */
	public void hiddenRightItemView() {
	//	System.out.println("=========hiddenRight");
		if (mCurrentItemView == null) {
			return;
		}
		Message msg = new MoveHandler().obtainMessage();//
		msg.obj = mPreItemView;
		msg.arg1 = mPreItemView.getScrollX();
		msg.arg2 = 0;

		msg.sendToTarget();

		mIsShown = false;
	}

	/**
	 * show or hide right layout animation
	 */
	@SuppressLint("HandlerLeak")
	class MoveHandler extends Handler {
		int stepX = 0;

		int fromX;

		int toX;

		View view;

		private boolean mIsInAnimation = false;

		private void animatioOver() {
			mIsInAnimation = false;
			stepX = 0;
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (stepX == 0) {
				if (mIsInAnimation) {
					return;
				}
				mIsInAnimation = true;
				view = (View) msg.obj;
				fromX = msg.arg1;
				toX = msg.arg2;
				stepX = (int) ((toX - fromX) * mDurationStep * 1.0 / mDuration);
				if (stepX < 0 && stepX > -1) {
					stepX = -1;
				} else if (stepX > 0 && stepX < 1) {
					stepX = 1;
				}
				if (Math.abs(toX - fromX) < 10) {
					view.scrollTo(toX, 0);
					animatioOver();
					return;
				}
			}

			fromX += stepX;
			boolean isLastStep = (stepX > 0 && fromX > toX) || (stepX < 0 && fromX < toX);
			if (isLastStep) {
				fromX = toX;
			}

			view.scrollTo(fromX, 0);
			invalidate();

			if (!isLastStep) {
				this.sendEmptyMessageDelayed(0, mDurationStep);
			} else {
				animatioOver();
			}
		}
	}

	public int getRightViewWidth() {
		return mRightViewWidth;
	}

	public void setRightViewWidth(int mRightViewWidth) {
		this.mRightViewWidth = mRightViewWidth;
	}

	/**
	 * 功能：设置左滑视图的id，必须包含在item视图对象中
	 * @param swipleViewId
	 */
	public void setSwipleViewId(int swipleViewId) {
		this.swipleViewId = swipleViewId;
	}

}
