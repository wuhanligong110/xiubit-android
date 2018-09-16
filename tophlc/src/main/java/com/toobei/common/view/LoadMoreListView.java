package com.toobei.common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.toobei.common.R;


public class LoadMoreListView extends ListView implements OnScrollListener {
	private View footer;

	private int totalItem;
	private int lastItem;
	public boolean isLoading;
	private OnLoadMore onLoadMore;
	private LayoutInflater inflater;

	public boolean onLoadMoreFlag = true;
	public boolean onSlide = false;
	private int downY;
	private int upY;
	private MyVerticalViewGroup myVerticalView;

	public LoadMoreListView(Context context) {
		super(context);
		init(context);
	}

	public LoadMoreListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public MyVerticalViewGroup getMyVerticalView() {
		return myVerticalView;
	}

	public void setMyVerticalView(MyVerticalViewGroup myVerticalView) {
		this.myVerticalView = myVerticalView;
		this.myVerticalView.setIntercepterFlag(true);
	}

	@SuppressLint("InflateParams")
	private void init(Context context) {
		inflater = LayoutInflater.from(context);
		footer = inflater.inflate(R.layout.load_more_footer, null, false);
		footer.setVisibility(View.GONE);
		this.addFooterView(footer);
		this.setOnScrollListener(this);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
			int totalItemCount) {
		onSlide = firstVisibleItem == 0;
		this.lastItem = firstVisibleItem + visibleItemCount;
		this.totalItem = totalItemCount;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (onLoadMoreFlag) {
			if (this.totalItem == lastItem && scrollState == SCROLL_STATE_IDLE) {
				Log.v("isLoading", "yes");
				if (!isLoading) {
					isLoading = true;
					footer.setVisibility(View.VISIBLE);
					onLoadMore.loadMore();
					onSlide = true;
				}
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			break;

		case MotionEvent.ACTION_UP:
			if (onSlide) {
				upY = (int) ev.getY();
				System.out.println("upY====>" + upY + "\ndownY=====>" + downY);
				if (upY - downY > 10 && this.getFirstVisiblePosition() == 0) {
					myVerticalView.snapToScreen(0);
				}
				if (upY - downY < 10 && getLastVisiblePosition() == (getCount() - 1)) {
					if (onLoadMoreFlag) {
						if (!isLoading) {
							isLoading = true;
							footer.setVisibility(View.VISIBLE);
							onLoadMore.loadMore();
						}
					}
				}
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	public void setLoadMoreListen(OnLoadMore onLoadMore) {
		this.onLoadMore = onLoadMore;
	}

	/**
	 * 加载完成调用此方法
	 */
	public void onLoadComplete() {
		footer.setVisibility(View.GONE);
		isLoading = false;
	}

	public void setOnLoadMore(boolean onLoadMoreFlag) {
		this.onLoadMoreFlag = onLoadMoreFlag;
	}

	public interface OnLoadMore {
		void loadMore();
	}

}