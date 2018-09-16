package org.xsl781.ui.view;

import org.xsl781.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;

/**
 * 公司: tophlc
 * 类说明：悬浮条的xlistview
 * @date 2016-5-27
 */
public class SuspendHeaderLayout extends RelativeLayout {

	private View rootView, contentView, headerView;
	private SuspendHeaderLayoutAdapter suspendHeaderLayoutAdapter;

	public interface SuspendHeaderLayoutAdapter {

		int PINNED_HEADER_GONE = 0;

		int PINNED_HEADER_VISIBLE = 1;

		int PINNED_HEADER_PUSHED_UP = 2;

		int getPinnedHeaderState(int position);

		void configurePinnedHeader(View headerView, int position, int alpaha);
	}

	public SuspendHeaderLayout(Context context) {
		super(context);
	}

	/**
	 * 功能：要先初始化
	 * @param headerLayout 悬浮头的布局
	 * @param contentLayout
	 */
	public View initContentView(int headerLayout, int contentLayout) {
		if (rootView == null) {
			rootView = LayoutInflater.from(getContext()).inflate(
					R.layout.xsl781_suspend_header_layout, null, false);
			ViewStub headerStub = (ViewStub) rootView.findViewById(R.id.stub_suspend_header);
			ViewStub mContentStub = (ViewStub) rootView.findViewById(R.id.stub_content);
			headerStub.setLayoutResource(headerLayout);
			mContentStub.setLayoutResource(contentLayout);
			headerView = headerStub.inflate();
			contentView = mContentStub.inflate();
			addView(rootView);
		}
		return rootView;
	}

	public View getContentView() {
		return contentView;
	}

	public View getHeaderView() {
		return headerView;
	}

	public void setAdapter(ListAdapter adapter) {
		suspendHeaderLayoutAdapter = (SuspendHeaderLayoutAdapter) adapter;
	}

}
