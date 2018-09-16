package org.xsl781.utils;

import org.xsl781.http.core.KJAsyncTask;
import org.xsl781.ui.ViewInject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * 类说明：{@link KJAsyncTask}
 * @date 2015-9-23
 */
public abstract class NetAsyncTask extends KJAsyncTask<Void, Void, Void> {
	protected ProgressDialog dialog;
	protected Context ctx;
	protected boolean openDialog = false;
	protected Exception exception;

	protected NetAsyncTask(Context ctx) {
		this.ctx = ctx;
	}

	protected NetAsyncTask(Context ctx, boolean openDialog) {
		this.ctx = ctx;
		this.openDialog = openDialog;
	}

	public NetAsyncTask setOpenDialog(boolean openDialog) {
		this.openDialog = openDialog;
		return this;
	}

	public void setDialog(ProgressDialog dialog) {
		this.dialog = dialog;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (openDialog && dialog == null) {
			dialog = ViewInject.showSpinnerDialog((Activity) ctx);
		}
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			doInBack();
		} catch (Exception e) {
			e.printStackTrace();
			exception = e;
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid) {
		super.onPostExecute(aVoid);
		if (openDialog && dialog != null) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}
		onPost(exception);
	}

	protected abstract void doInBack() throws Exception;

	protected abstract void onPost(Exception e);

}
