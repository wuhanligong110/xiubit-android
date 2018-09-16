package com.toobei.common.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.toobei.common.R;
import com.toobei.common.TopBaseActivity;
import com.toobei.common.utils.PhotoUtil;

import java.io.File;

/**
 * 公司: tophlc
 * 类说明：此类用于点击图片消失，双击图片放大
 * @date 2016-5-13
 */
@SuppressLint("InflateParams")
public abstract class TopPopuImgViewActivity extends TopBaseActivity implements OnClickListener {

	private View contentView;
	private File imageFile;
	private String url, path, originUrl;
	private ImageView img;

	//	private PhotoViewAttacher mAttacher;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		path = getIntent().getStringExtra("path");
		url = getIntent().getStringExtra("url");
		originUrl = getIntent().getStringExtra("originUrl");
		contentView = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.chat_image_brower_layout, null);
		img = (ImageView) contentView.findViewById(R.id.imageView);
		img.setOnClickListener(this);
		PhotoUtil.displayImageByUri(ctx,img, path, url, originUrl, null);
		setContentView(contentView);
	}

	@Override
	protected void onDestroy() {
		img.destroyDrawingCache();
		contentView = null;
		System.gc();
		super.onDestroy();
	}

	/*
		ImageLoadingListener loadinglistener = new ImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
			}

			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				if (null != mAttacher) {
					mAttacher.update();
				}
			}

			public void onLoadingCancelled(String imageUri, View view) {

			}
		};*/

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.imageView) {
			finish();
		}
		/*	case R.id.btn_download:
				if (FileUtils.copyFile(imageFile,
						new File(PathUtils.getCacheDir() + imageFile.getName() + ".png")))
					Utils.showToast(this, "文件保存至" + PathUtils.getCacheDir() + imageFile.getName()
							+ ".png", Toast.LENGTH_SHORT, true);
				break;*/

	}

}
