package com.toobei.common.utils;

import org.xsl781.http.core.KJAsyncTask;

public abstract class NetAsyncTaskCallBack<T> extends KJAsyncTask<Void, Void, Void> {
	Exception exception;
	T o;
	UpdateViewCallBack<T> updateViewCallBack;

	public NetAsyncTaskCallBack(UpdateViewCallBack<T> updateViewCallBack) {
		this.updateViewCallBack = updateViewCallBack;
	}

	public void removeCallBack() {
		if (updateViewCallBack != null)
			updateViewCallBack = null;
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			o = doInBack();
		} catch (Exception e) {
			e.printStackTrace();
			exception = e;
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void aVoid) {
		super.onPostExecute(aVoid);
		if (updateViewCallBack != null) {
			updateViewCallBack.updateView(exception, o);
			removeCallBack();
		}
	}

	protected abstract T doInBack() throws Exception;

}
