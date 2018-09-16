package com.toobei.tb.activity;

import android.content.Context;
import android.content.Intent;

import com.toobei.common.TopBaseActivity;
import com.toobei.common.activity.TopImageSelectActivity;
import com.toobei.common.utils.BitmapUtil;
import com.toobei.common.utils.PathUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xsl781.utils.MD5Util;

import java.io.File;

public class ImageSelectActivity extends TopImageSelectActivity {

	@Override
	protected Intent getGestureActivityIntent(TopBaseActivity activity) {
		return new Intent(activity, GestureActivity.class);
	}

	@Override
	public void EventCount() {
		MobclickAgent.onEvent(ImageSelectActivity.this,"my_avatar");
	}

	@Override
	protected void skipCropActivity(Context ctx, String imagePath) {
		Intent intent = new Intent(ctx,CropImgActivity.class);
		intent.putExtra("imagePath", imagePath);
		startActivity(intent);
	}


	@Subscribe(threadMode = ThreadMode.MAIN)
	public void cropImgFinish(CropImgActivity.Companion.CropEvent event) {
		if (event.getBitmap() == null) return;
		String filename = MD5Util.MD5(System.currentTimeMillis() + "");
		final File file = BitmapUtil.saveBitmap(PathUtils.getImagePath(), filename, event.getBitmap(), false);
		reSetHeadImage(file.getAbsolutePath());
	}

}
