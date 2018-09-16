package com.toobei.common;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.toobei.common.utils.ToastUtil;
import com.toobei.common.view.FirstGuideView;
import com.toobei.common.view.HeaderLayout;

public abstract class TopTitleBaseActivity extends TopBaseActivity {
	protected HeaderLayout headerLayout;
	protected ProgressDialog mRefreshDialog;  //刷新时使用进度dialog

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title_base);
		/*ViewStub stub = (ViewStub) findViewById(R.id.title_stub);
		View view = stub.inflate();*/
		headerLayout = (HeaderLayout) findViewById(R.id.headerLayout);

		//	view.setVisibility(View.GONE);
		onContentCreate(savedInstanceState, loadContent());
	}

	@Override
	protected void initStatusBarStyle() {
		super.initStatusBarStyle();
		headerLayout.setHeadViewCoverStateBar();
	}

	protected void addFirstGuide(String viewType, int drawableId) {
		ViewGroup vgFirstGuide = (ViewGroup) findViewById(R.id.root_view);
		vgFirstGuide.addView(new FirstGuideView(ctx, viewType, drawableId), -1, -1);
	}

	protected void initTitleView() {
		headerLayout.setVisibility(View.VISIBLE);
	}

	protected void hideTitleView() {
		headerLayout.setVisibility(View.GONE);
	}

	private View loadContent() {
		int rid = getContentLayout();
		if (rid <= 0)
			return null;

		LinearLayout parent = (LinearLayout) findViewById(R.id.view_content);

		LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(rid, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		parent.addView(view, lp);
		return view;
	}

	protected void onContentCreate(Bundle savedInstanceState, View content) {

	}

	protected abstract int getContentLayout();

	/**
	 * 用于数据页面刷新时展示
	 */
	public void showRefreshProgress() {
		if (this != null && !this.isFinishing()) {
			if (mRefreshDialog == null) {
				mRefreshDialog = ToastUtil.showCustomDialog(ctx);
			} else if (!mRefreshDialog.isShowing()) {
				mRefreshDialog.show();
			}
		}
	}

	/**
	 * /**
	 * 用于数据页面刷新结束后展示
	 */

	public void dismissRefreshProgress() {
		if (mRefreshDialog != null && mRefreshDialog.isShowing() && this != null && !this.isFinishing()) {
			mRefreshDialog.dismiss();
		}
	}
}
