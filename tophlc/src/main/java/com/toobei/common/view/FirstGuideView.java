package com.toobei.common.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.toobei.common.R;
import com.toobei.common.TopApp;

/**
 * 公司: tophlc
 * 类说明：引导页面，第一次进入时
 * @date 2015-11-11
 */
public class FirstGuideView extends LinearLayout implements View.OnClickListener {
	private View view;
	private String viewType;
	private int drawableId;

	public FirstGuideView(Context context, String viewType, int drawableId) {
		super(context);
		this.viewType = viewType;
		this.drawableId = drawableId;
		boolean isNeedInit = TopApp.getInstance().getCurUserSp().isFirstGuide(viewType);
		if (isNeedInit) {
			init();
		}
	}

	private void init() {
		view = LayoutInflater.from(getContext()).inflate(R.layout.layout_first_guide_view, null,
				false);
		ImageView img = (ImageView) view.findViewById(R.id.img_frist_guide_bg);
		img.setImageResource(drawableId);
		img.setOnClickListener(this);
		addView(view, LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
	}

	@Override
	public void onClick(View v) {
		TopApp.getInstance().getCurUserSp().setFirstGuide(viewType, false);
		view.setVisibility(View.INVISIBLE);
		removeView(view);
	}

}
