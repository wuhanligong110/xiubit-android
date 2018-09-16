package org.xsl781.ui.xlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import org.xsl781.R;

@SuppressLint("ClickableViewAccessibility")
public class XListView extends ListView implements OnScrollListener {

	protected final static int SCROLLBACK_HEADER = 0;
	protected final static int SCROLLBACK_FOOTER = 1;
	// 滑动时长
	protected final static int SCROLL_DURATION = 400;
	// 加载更多的距离
	protected final static int PULL_LOAD_MORE_DELTA = 100;
	// 滑动比例
	protected final static float OFFSET_RADIO = 2f;
	// 记录按下点的y坐标
	protected float lastY;
	// 用来回滚
	protected Scroller scroller;
	protected IXListViewListener mListViewListener;
	protected OnScrollListener mScrollListener; // user's scroll listener

	protected XListViewHeader headerView;
	protected RelativeLayout headerViewContent;
	// header的高度
	protected int headerHeight;

	protected boolean enableRefresh = true;// 是否能够刷新

	protected boolean enableLoadMore = true;// 是否可以加载更多

	protected boolean isAutoLoadMore = true;//是否到底部自动加载

	private boolean isStartAutoLoad = true;//进入界面是否自动加载

	private boolean isTouchValided = true;//触摸是否有效

	// 是否正在刷新
	protected boolean isRefreashing = false;
	// footer
	protected XListViewFooter footerView;
	// 是否正在加载
	protected boolean isLoadingMore;
	// 是否footer准备状态
	protected boolean isFooterAdd = false;
	// total list items, used to detect is at the bottom of listview.
	protected int totalItemCount;
	// 记录是从header还是footer返回
	protected int mScrollBack;

	protected ListAdapter adapter;
	// 加载更多时，是显示ProgressBar，还是 文字：查看以前的消息
	protected boolean isProgressBar = false;

	//@SuppressWarnings("unused")
	//	private static final String TAG = "XListView";

	public XListView(Context context) {
		super(context);
		initView(context);
	}

	public XListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public XListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	private void initView(Context context) {

		scroller = new Scroller(context, new DecelerateInterpolator());

		headerView = new XListViewHeader(context);
		footerView = new XListViewFooter(context);

		//	headerViewContent = (RelativeLayout) headerView.findViewById(R.id.xlistview_header_content);
		headerViewContent = (RelativeLayout) headerView.findViewById(R.id.xlistview_header_content);
		headerView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				headerHeight = headerViewContent.getHeight();
				getViewTreeObserver().removeGlobalOnLayoutListener(this);
			}
		});
		addHeaderView(headerView);
		this.setOverScrollMode(View.OVER_SCROLL_NEVER);   //去除hold
		super.setOnScrollListener(this);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		// 确保footer最后添加并且只添加一次
		if (isFooterAdd == false) {
			isFooterAdd = true;
			addFooterView(footerView);
		}
		this.adapter = adapter;
		super.setAdapter(adapter);

	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		if (!isTouchValided) {
			return false;
		}

		if (getAdapter() != null) {
			totalItemCount = getAdapter().getCount();
			if (totalItemCount == 0)
				return super.onTouchEvent(ev);
		}

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 记录按下的坐标
			lastY = ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			// 计算移动距离
			float deltaY = ev.getRawY() - lastY;
			lastY = ev.getRawY();
			// 是第一项并且标题已经显示或者是在下拉
			if (getFirstVisiblePosition() == 0
					&& (headerView.getVisiableHeight() > 0 || deltaY > 20) && enableRefresh) {
				updateHeaderHeight(deltaY / OFFSET_RADIO);
			} else if (!isAutoLoadMore && getLastVisiblePosition() == totalItemCount - 1
					&& (footerView.getBottomMargin() > 0 || deltaY < -20) && enableLoadMore) {
				updateFooterHeight(-deltaY / OFFSET_RADIO);
			}
			break;

		case MotionEvent.ACTION_UP:

			if (getFirstVisiblePosition() == 0) {
				if (enableRefresh && headerView.getVisiableHeight() > headerHeight
						&& !isRefreashing) {
					isRefreashing = true;
					headerView.setState(XListViewHeader.STATE_REFRESHING);
					if (mListViewListener != null) {
						mListViewListener.onRefresh();
					}
				}
				resetHeaderHeight();
			} else if (getLastVisiblePosition() == totalItemCount - 1) {
				if (enableLoadMore && !isLoadingMore && !isAutoLoadMore
						&& footerView.getBottomMargin() > PULL_LOAD_MORE_DELTA) {
					startLoadMore();
				}
				resetFooterHeight();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public void computeScroll() {

		// 松手之后调用
		if (scroller.computeScrollOffset()) {

			if (mScrollBack == SCROLLBACK_HEADER) {
				headerView.setVisiableHeight(scroller.getCurrY());
			} else {
				footerView.setBottomMargin(scroller.getCurrY());
			}
			postInvalidate();
		}
		super.computeScroll();

	}

	/**
	 * 设置下拉刷新功能
	 * 默认有
	 */
	public void setPullRefreshEnable(boolean enable) {
		enableRefresh = enable;

		if (!enableRefresh) {
			headerView.hide();
		} else {
			headerView.show();
		}
	}

	/**
	 * 设置下拉是否能加载
	 * 默认为有下拉功能
	 */
	public void setPullLoadEnable(boolean enable) {
		enableLoadMore = enable;
		if (!enableLoadMore) {
			footerView.hide();
			footerView.setOnClickListener(null);
		} else {
			isLoadingMore = false;
			footerView.show();
			footerView.setState(XListViewFooter.STATE_NORMAL);
			footerView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startLoadMore();
				}
			});
		}
	}

	public boolean getPullLoading() {
		return this.isLoadingMore;
	}

	public boolean getPullRefreshing() {
		return this.isRefreashing;
	}

	/*
		public void pullRefreshing() {
			if (!enableRefresh) {
				return;
			}
			headerView.setVisiableHeight(headerHeight);
			isRefreashing = true;
			headerView.setState(XListViewHeader.STATE_REFRESHING);
		}*/

	public void stopRefresh() {
		if (isRefreashing == true) {
			isRefreashing = false;
			resetHeaderHeight();
		}
	}

	public void stopLoadMore() {
		if (isLoadingMore == true) {
			isLoadingMore = false;
			footerView.setState(XListViewFooter.STATE_NORMAL);
		}
	}

	private void updateHeaderHeight(float delta) {
		headerView.setVisiableHeight((int) delta + headerView.getVisiableHeight());
		// 未处于刷新状态，更新箭头
		if (enableRefresh && !isRefreashing) {
			if (headerView.getVisiableHeight() > headerHeight) {
				headerView.setState(XListViewHeader.STATE_READY);
			} else {
				headerView.setState(XListViewHeader.STATE_NORMAL);
			}
		}

	}

	private void resetHeaderHeight() {
		// 当前的可见高度
		int height = headerView.getVisiableHeight();
		// 如果正在刷新并且高度没有完全展示
		if ((isRefreashing && height <= headerHeight) || (height == 0)) {
			return;
		}
		// 默认会回滚到header的位置
		int finalHeight = 0;
		// 如果是正在刷新状态，则回滚到header的高度
		if (isRefreashing && height > headerHeight) {
			finalHeight = headerHeight;
		}
		mScrollBack = SCROLLBACK_HEADER;
		// 回滚到指定位置
		scroller.startScroll(0, height, 0, finalHeight - height, SCROLL_DURATION);
		// 触发computeScroll
		invalidate();
	}

	private void updateFooterHeight(float delta) {
		int height = footerView.getBottomMargin() + (int) delta;
		if (enableLoadMore && !isLoadingMore) {
			if (height > PULL_LOAD_MORE_DELTA) {
				footerView.setState(XListViewFooter.STATE_READY);
			} else {
				footerView.setState(XListViewFooter.STATE_NORMAL);
			}
		}
		footerView.setBottomMargin(height);

	}

	private void resetFooterHeight() {
		int bottomMargin = footerView.getBottomMargin();
		if (bottomMargin > 0) {
			mScrollBack = SCROLLBACK_FOOTER;
			scroller.startScroll(0, bottomMargin, 0, -bottomMargin, SCROLL_DURATION);
			invalidate();
		}
	}

	private void startLoadMore() {
		isLoadingMore = true;
		footerView.setState(XListViewFooter.STATE_LOADING);
		if (mListViewListener != null) {
			mListViewListener.onLoadMore();
		}
	}

	public void setXListViewListener(IXListViewListener l) {
		mListViewListener = l;
	}

	/**
	 * 设置滑动到底部自动加载
	 * true为自动加载 默认
	 * false 为手动向上拉加载
	 */
	public void setAutoLoadMore(boolean isAutoLoadMore) {
		this.isAutoLoadMore = isAutoLoadMore;
		footerView.setAutoLoadMore(isAutoLoadMore);
	}

	/**
	 * 功能：是否进入界面就开始加载数据,默认为不加载
	 * @param isStartAutoLoad
	 */
	public void setStartAutoLoad(boolean isStartAutoLoad) {
		this.isStartAutoLoad = isStartAutoLoad;
	}

	/**
	 * 设置刷新时间
	 */
	public void setRefreshTime(String refreshTime) {
		if (headerView.getVisibility() == View.VISIBLE)
			headerView.setLastUpdateTime(refreshTime);
	}

	public interface IXListViewListener {

		void onRefresh();

		void onLoadMore();
	}

	@Override
	public void setOnScrollListener(OnScrollListener l) {
		mScrollListener = l;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (mScrollListener != null) {
			mScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
			int totalItemCount) {
		//设置为自动加载 
		//totalItemCount 包含头部，尾部，还有第一项,默认第一次进来不自动加载则
		if (isAutoLoadMore && getLastVisiblePosition() == totalItemCount - 1 && !isLoadingMore
				&& enableLoadMore) {
			if ((isStartAutoLoad && totalItemCount > 2) || (!isStartAutoLoad && totalItemCount > 3)) {
				startLoadMore();
				resetFooterHeight();
			}
		}
		if (mScrollListener != null) {
			mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}

	/**
	 * 功能：设置触摸是否有效，true表示该控件吸收手势，false时表示不消耗手势事件
	 * @param isTouchValided
	 */
	public void setTouchValided(boolean isTouchValided) {
		this.isTouchValided = isTouchValided;
	}

}
