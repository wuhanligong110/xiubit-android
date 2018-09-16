package com.toobei.common.utils;

import android.os.AsyncTask;

public abstract class NetAsyncTaskNoCtx extends AsyncTask<Void, Void, Void> {
	Exception exception;

	protected NetAsyncTaskNoCtx() {
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
		onPost(exception);
	}

	protected abstract void onPost(Exception e);

	protected abstract void doInBack() throws Exception;

}
