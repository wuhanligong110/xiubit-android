package com.toobei.common.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

public class ActionItem {
	public Drawable pointDrawbleID;
	public Drawable mDrawable;
	public CharSequence mTitle;
	
	public ActionItem(Drawable drawable, CharSequence title){
		this.mDrawable = drawable;
		this.mTitle = title;
	}
	
	public ActionItem(Context context, int titleId, int drawableId){
		this.mTitle = context.getResources().getText(titleId);
		this.mDrawable = ContextCompat.getDrawable(context,drawableId);
	}
	public ActionItem(Context context, CharSequence title){
		this.mTitle = title;
		this.mDrawable = null;
	}
	
	public ActionItem(Context context, CharSequence title, int drawableId) {
		this.mTitle = title;
		this.mDrawable = ContextCompat.getDrawable(context,drawableId);
	}
	public ActionItem(Context context, CharSequence title, int drawableId, int pointDrawbleID) {
		this.mTitle = title;
		this.mDrawable = ContextCompat.getDrawable(context,drawableId);
		this.pointDrawbleID = ContextCompat.getDrawable(context,pointDrawbleID);
	}
}
