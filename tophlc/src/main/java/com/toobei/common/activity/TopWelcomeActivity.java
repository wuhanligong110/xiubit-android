package com.toobei.common.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.toobei.common.R;
import com.toobei.common.TopApp;
import com.toobei.common.TopBaseActivity;

import org.xsl781.data.BasePagerAdapter;
import org.xsl781.utils.SystemTool;

import java.util.ArrayList;

public abstract class TopWelcomeActivity extends TopBaseActivity implements OnPageChangeListener,
		OnClickListener, OnTouchListener {
	private ViewPager viewPager;
	private PagerAdapter pagerAdapter;
	private Button startButton;
	private ArrayList<View> views;
	private boolean isClicked = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		initView();
		setTranslucentStatus(true);
	}

	protected abstract void skipGestureActivity();

	protected abstract void skipLoginActivity();

	protected abstract int[] getImagesResources();

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.start_Button && !isClicked) {
			isClicked = true;
			TopApp.getInstance().getDefaultSp().setAppFirstRun(false);
			TopApp.getInstance()
					.getDefaultSp()
					.setCurVersionFirstRun(SystemTool.getAppVersion(getApplicationContext()), false);
			if (TopApp.getInstance().getLoginService().isCachPhoneExist()) {
				skipGestureActivity();
				//	skipActivity(this, GestureActivity.class);
			} else {
				skipMainActivity();
				//	startActivity(new Intent(TopWelcomeActivity.this, LoginActivity.class));
			}
			this.finish();
		}
	}

	protected abstract void skipMainActivity();

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	// 监听viewpage
	@Override
	public void onPageSelected(int position) {
		// 显示最后一个图片时显示按钮
		if (position == views.size() - 1) {
			startButton.setVisibility(View.VISIBLE);
		} else {
			startButton.setVisibility(View.INVISIBLE);
		}
	}

	int lastX;

	@Override
	public boolean onTouch(View v, MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			if ((lastX - event.getX()) > 100 && (viewPager.getCurrentItem() == views.size() - 1)
					&& !isClicked) {
				onClick(startButton);
				isClicked = true;
				return true;
			}
			break;
		default:
			break;
		}
		return false;
	}

	private void initView() {
		int[] images = getImagesResources();
		viewPager = (ViewPager) findViewById(R.id.viewpage);
		startButton = (Button) findViewById(R.id.start_Button);
		startButton.setOnClickListener(this);
		views = new ArrayList<View>();
		for (int i = 0; i < images.length; i++) {
			// 循环加入图片
			ImageView image = new ImageView(ctx);
			image.setImageResource(images[i]);
			image.setScaleType(ScaleType.FIT_XY);
			views.add(image);
		}
		pagerAdapter = new BasePagerAdapter(views);
		viewPager.setAdapter(pagerAdapter);
		viewPager.setOnPageChangeListener(this);
		viewPager.setOnTouchListener(this);
	}
}
