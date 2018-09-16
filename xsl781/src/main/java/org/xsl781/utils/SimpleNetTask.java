package org.xsl781.utils;

import android.content.Context;

public abstract class SimpleNetTask extends NetAsyncTask {
	protected SimpleNetTask(Context cxt) {
		super(cxt);
	}

	protected SimpleNetTask(Context cxt, boolean openDialog) {
		super(cxt, openDialog);
	}

	@Override
	protected void onPost(Exception e) {
		if (e != null) {
			e.printStackTrace();
		//	Utils.toast(ctx, R.string.pleaseCheckNetwork);
		} else {
			onSucceed();
		}
	}

	protected abstract void doInBack() throws Exception;

	protected abstract void onSucceed();
}
