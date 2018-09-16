package com.toobei.common.utils;

import android.app.Activity;
import android.content.Context;

import com.toobei.common.TopBaseActivity;

import org.xsl781.utils.Logger;
import org.xsl781.utils.NetAsyncTask;

public abstract class MyNetAsyncTask extends NetAsyncTask {
	TopBaseActivity activity;
	protected String strResponse;

	protected MyNetAsyncTask(Context ctx) {
		super(ctx);
		activity = (TopBaseActivity) ctx;
	}

	protected MyNetAsyncTask(Context ctx, boolean openDialog) {
		super(ctx, openDialog);
		activity = (TopBaseActivity) ctx;
	}

	@Override
	protected void onPreExecute() {
		if (openDialog && dialog == null) {
			dialog = ToastUtil.showCustomDialog((Activity) ctx);
		}
	}

	@Override
	protected void onPostExecute(Void aVoid) {
		//	super.onPostExecute(aVoid);
		if (openDialog) {
			if (dialog.isShowing() && activity != null && !activity.isFinishing()) {
				dialog.dismiss();
			}
		}
		onPost(exception);
		if(exception!=null){
			Logger.e("onPostExecute",exception.toString());
		}
	}
}