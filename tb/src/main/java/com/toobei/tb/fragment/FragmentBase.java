package com.toobei.tb.fragment;

import org.xsl781.utils.Utils;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.toobei.common.view.HeaderLayout;

public class FragmentBase extends Fragment implements OnTouchListener {
	HeaderLayout headerLayout;
	Activity ctx;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = getActivity();
	}

	@Override
	public void onResume() {
		Utils.hideSoftInputView(getActivity());
		super.onResume();
	}

	// onTouch事件 将上层的触摸事件拦截
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return true;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// 拦截触摸事件，防止泄露下去
		view.setOnTouchListener(this);
	}
}
